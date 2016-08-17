//
// Created by tafiagu on 16-6-27.
//

#ifndef LIB_LOGGER_UNICLOG_H
#define LIB_LOGGER_UNICLOG_H

#ifdef ANDROID
#include <android/log.h>
#include <jni.h>

#define  LOGI(tag, ...)  __android_log_print(ANDROID_LOG_INFO, tag, __VA_ARGS__)
#define  LOGV(tag, ...)  __android_log_print(ANDROID_LOG_VERBOSE, tag, __VA_ARGS__)
#define  LOGE(tag, ...)  __android_log_print(ANDROID_LOG_ERROR,  tag, __VA_ARGS__)
#define  LOGD(tag, ...)  __android_log_print(ANDROID_LOG_DEBUG, tag, __VA_ARGS__)
#define  LOGF(tag, ...)  __android_log_print(ANDROID_LOG_FATAL, tag, __VA_ARGS__)
#define  LOGW(tag, ...)  __android_log_print(ANDROID_LOG_WARN, tag, __VA_ARGS__)

#else

#define LOGI(tag,...)
#define LOGV(tag,...)
#define LOGE(tag,...)
#define LOGD(tag,...)
#define LOGF(tag,...)
#define LOGW(tag,...)

#endif


enum LOGTYPE{
	ERROR= 1,
	WARN,
	INFO,
	DEBUG,
	VERBOSE
};

enum WRITETYPE{
	MEMORY = 1,
	FILES,
	BOTH
};

class UnicLog
{
	public:
		static void setLogLevel(int level);
		static void setLogWriteType(WRITETYPE type);
		static void setLogDestPath(const char *destPath);

		static void v(const char *tag,  const char *msg);
		static void d(const char *tag,  const char *msg);
		static void i(const char *tag,  const char *msg);
		static void w(const char *tag,  const char *msg);
		static void e(const char *tag,  const char *msg);
		static void log(int level, const char *tag, const char *format, ...);
		static void writeLog(const char *cLevel, int nLevel, const char *tag, const char *buffer, int len = 0);
	private:
		static void writeToFile(const char *buffer, int len =0);
		static void writeToMemory(int level, const char *tag, const char *buf);
};



#endif //LIB_LOGGER_UNICLOG_H
