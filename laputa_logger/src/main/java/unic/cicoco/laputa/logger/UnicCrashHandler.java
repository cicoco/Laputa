/*
 * Copyright (C) 2016 Tafia Gu of The Unic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package unic.cicoco.laputa.logger;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tafiagu on 16-8-19.
 */
public class UnicCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = UnicCrashHandler.class.getSimpleName();

    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String SDK_VERSION = "sdkVersion";
    private static final String APP_MEM_LIMIT = "appMemLimit";

    private static UnicCrashHandler sInstance = null;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private Map<String, String> mDeviceInfo = new HashMap();

    private ICrashAspect mAspect = new SimpleCrashAspect();

    private UnicCrashHandler() {
    }

    public static UnicCrashHandler getInstance() {
        if (null == sInstance) {
            synchronized (UnicCrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new UnicCrashHandler();
                }
            }
        }

        return sInstance;


    }


    public void init(Context context) {
        init(context, null);

    }

    public void init(Context context, ICrashAspect aspect) {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // Thread类的静态方法，对该进程中的所有线程起作用。any thread（不只是当前线程）发生未捕获的异常时，都会跑到此处处理。
        Thread.setDefaultUncaughtExceptionHandler(this);

        if (null != aspect) {
            mAspect = aspect;
        }

        // 收集设备信息
        Map<String, String> info = mAspect.collectDeviceInfo(context);
        if (null != info) {
            mDeviceInfo = info;
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }

        mAspect.disposeException(ex, mDeviceInfo);

        return false;

    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            // 0表示jvm正常退出，非0数可以做错误码
            System.exit(10);
        }
    }


    public static class SimpleCrashAspect implements ICrashAspect {

        @Override
        public Map<String, String> collectDeviceInfo(Context context) {
            Map<String, String> deviceInfo = new HashMap();
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                deviceInfo.put(SDK_VERSION, String.valueOf(Build.VERSION.SDK_INT));
                if (pi != null) {
                    deviceInfo.put(VERSION_NAME, null == pi.versionName ? "unknown" : pi.versionName);
                    deviceInfo.put(VERSION_CODE, String.valueOf(pi.versionCode));
                }

                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                deviceInfo.put(APP_MEM_LIMIT, String.valueOf(activityManager.getMemoryClass()));

            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }

            // 使用反射来收集设备信息.在Build类中包含各种设备信息, 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    deviceInfo.put(field.getName(), String.valueOf(field.get(null)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        public String disposeException(Throwable ex, Map<String, String> deviceInfo) {
            StringBuilder sb = new StringBuilder();
            if (null != deviceInfo) {
                for (Map.Entry<String, String> entry : deviceInfo.entrySet()) {
                    sb.append(entry.getKey() + "=" + entry.getValue() + "\n");
                }
            }


            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            String log = sb.toString();
            UnicLog.e(TAG, log);

            return log;
        }
    }


    public interface ICrashAspect {

        /**
         * 收集设备信息
         *
         * @param context
         * @return 设备信息HashMap
         */
        Map<String, String> collectDeviceInfo(Context context);

        /**
         * 传递错误消息
         *
         * @param ex
         * @param deviceInfo
         * @return
         */
        String disposeException(Throwable ex, Map<String, String> deviceInfo);


    }

}
