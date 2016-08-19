package unic.cicoco.laputa;

import android.app.Application;

import unic.cicoco.laputa.logger.LogConstant;
import unic.cicoco.laputa.logger.LogLevel;
import unic.cicoco.laputa.logger.UnicCrashHandler;
import unic.cicoco.laputa.logger.UnicLog;
import unic.cicoco.laputa.logger.WriteType;

/**
 * Created by tafiagu on 16-8-15.
 */
public class MyApplication extends Application {

    /**
     * 用于运行时日志记录
     */
    static {
        UnicLog.Builder builder = new UnicLog.Builder();

        builder.setLogLevel(LogLevel.ALL) // 记录所有的日志
                .setEnableIfNotInit(true) // 如果so加载失败，则打印Java层Android日志
                .setWriteType(WriteType.BOTH) // 打印内存日志，也打印内存卡日志
                .setDestPath(LogConstant.DEF_LOG_DIR + System.currentTimeMillis() + ".log"); // 内存卡日志位置,请自己修改

        UnicLog.init(builder);


    }


    @Override
    public void onCreate() {
        super.onCreate();
        UnicCrashHandler.getInstance().init(this);
    }
}
