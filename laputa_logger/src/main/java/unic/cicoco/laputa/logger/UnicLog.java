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

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by tafiagu on 16-6-27.
 */
public class UnicLog implements LogLevel, WriteType, LogConstant {


    private static final String TAG = "unic_watchcat";

    private static boolean mIsInited = false;
    private static boolean mDroidLog = false;

    public static void init(Builder builder) {

        if (null == builder) {
            return;
        }

        try {
            // 优先选择c库加载
            System.loadLibrary("uniclog");
            mIsInited = true;
            builder.build();
            Log.i(TAG, "CLib Load Success.");
        } catch (Throwable e) {
            mDroidLog = builder.enableIfNotInit;
            Log.e(TAG, "CLib Load Failed, open raw log:" + mDroidLog, e);
        }

    }


    public static class Builder {

        /**
         * 日志等级
         */
        private int logLevel = -1;
        /**
         * 日志类型
         */
        private int writeType = -1;
        /**
         * 持久化日志的文件路径
         */
        private String destPath = null;
        /**
         * 如果so日志加载不成功，是否加载Java层日志
         */
        private boolean enableIfNotInit = false;

        public Builder setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder setWriteType(int writeType) {
            this.writeType = writeType;
            return this;
        }

        public Builder setDestPath(String destPath) {
            this.destPath = destPath;
            return this;
        }


        public Builder setEnableIfNotInit(boolean enableIfNotInit) {
            this.enableIfNotInit = enableIfNotInit;
            return this;
        }

        private void build() throws FileNotFoundException {

            if (-1 != writeType) {
                checkInited();
                UnicLogger.setLogWriteType(writeType);
            }

            if (-1 != logLevel) {
                checkInited();
                UnicLogger.setLogLevel(logLevel);
            }

            if (null != destPath) {
                checkInited();

                File rootDir = new File(destPath).getParentFile();
                if (!rootDir.exists() && !rootDir.mkdirs()) {
                    throw new FileNotFoundException("Cannot mkdirs for:" + rootDir.getPath());
                }


                UnicLogger.setLogDestPath(destPath);
            }

        }
    }

    private static void checkInited() {

        if (!mIsInited) {
            throw new IllegalStateException("Please call init() first!!!");
        }

    }

    public static void e(String tag, String msg, Throwable tr) {
        if (mIsInited) {
            UnicLogger.e(tag, msg + '\n' + Log.getStackTraceString(tr));
        } else if (mDroidLog) {
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (mIsInited) {
            UnicLogger.e(tag, msg);
        } else if (mDroidLog) {
            Log.e(tag, msg);
        }
    }

    public static void writeToFile(String destPath, String log) {
        if (mIsInited) {
            UnicLogger.writeToFile(destPath, log);
        }
    }


    public static void i(String tag, String msg, Throwable tr) {
        if (mIsInited) {
            UnicLogger.i(tag, msg + '\n' + Log.getStackTraceString(tr));
        } else if (mDroidLog) {
            Log.i(tag, msg, tr);
        }

    }


    public static void i(String tag, String msg) {
        if (mIsInited) {
            UnicLogger.i(tag, msg);
        } else if (mDroidLog) {
            Log.i(tag, msg);
        }
    }


    public static void w(String tag, String msg, Throwable tr) {

        if (mIsInited) {
            UnicLogger.w(tag, msg + '\n' + Log.getStackTraceString(tr));
        } else if (mDroidLog) {
            Log.w(tag, msg, tr);
        }

    }


    public static void w(String tag, String msg) {
        if (mIsInited) {
            UnicLogger.w(tag, msg);
        } else if (mDroidLog) {
            Log.w(tag, msg);
        }
    }


    public static void d(String tag, String msg, Throwable tr) {

        if (mIsInited) {
            UnicLogger.d(tag, msg + '\n' + Log.getStackTraceString(tr));
        } else if (mDroidLog) {
            Log.d(tag, msg, tr);
        }

    }


    public static void d(String tag, String msg) {
        if (mIsInited) {
            UnicLogger.d(tag, msg);
        } else if (mDroidLog) {
            Log.d(tag, msg);
        }
    }


    public static void v(String tag, String msg, Throwable tr) {

        if (mIsInited) {
            UnicLogger.d(tag, msg + '\n' + Log.getStackTraceString(tr));
        } else if (mDroidLog) {
            Log.v(tag, msg, tr);
        }

    }


    public static void v(String tag, String msg) {
        if (mIsInited) {
            UnicLogger.d(tag, msg);
        } else if (mDroidLog) {
            Log.d(tag, msg);
        }
    }


}


