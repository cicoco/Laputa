//
// Created by tafiagu on 16-6-27.
//
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdarg.h>
#include <sys/time.h>
#include <time.h>
#include <pthread.h>
#include <string.h>


#include "UnicLog.h"

/*  Set default value.
 *  Level default: INFO
 *  WriteType default: memory
 */
int _level = INFO;
const char *_destPath = NULL;
WRITETYPE _logType = MEMORY;
pthread_mutex_t _lock = PTHREAD_MUTEX_INITIALIZER;

#define DEFAULT_BUFFER_SIZE 10240
#define THIS_FILE "UnicLog.cpp"

void UnicLog::setLogLevel(int level)
{
	_level = level;
}

void UnicLog::setLogDestPath(const char *destPath){
	_destPath = destPath;
}


void UnicLog::setLogWriteType(WRITETYPE type)
{
	_logType = type;
}

void UnicLog::log(int level, const char *tag, const char *format, ...)
{
	if (level > _level){
		return;
	}

	char buf[DEFAULT_BUFFER_SIZE];
	va_list arg;

	va_start(arg, format);
	vsnprintf(buf, DEFAULT_BUFFER_SIZE, format, arg);
	va_end(arg);

	switch (level)
	{
		case ERROR:
			writeLog("E", level, tag, buf);
			break;

		case WARN:
			writeLog("W", level, tag, buf);
			break;

		case INFO:
			writeLog("I", level, tag, buf);
			break;

		case DEBUG:
			writeLog("D", level, tag, buf);
			break;

		case VERBOSE:
			writeLog("V", level, tag, buf);
			break;

		default:
			break;
	}
}



void UnicLog::writeToMemory(int level, const char *tag, const char *buf)
{
	switch (level)
	{
		case ERROR:
			LOGE(tag, buf);
			break;

		case WARN:
			LOGW(tag, buf);
			break;

		case INFO:
			LOGI(tag, buf);
			break;

		case DEBUG:
			LOGD(tag, buf);
			break;

		case VERBOSE:
			LOGV(tag, buf);
			break;

		default:
			break;
	}
}

void UnicLog::writeLog(const char *cLevel, int nLevel, const char *tag, const char *buffer, int len)
{
    if (nLevel > _level){
    	return;
    }

	struct tm *t;
	struct timeval tv;

	static int textLen = DEFAULT_BUFFER_SIZE + 1024;
	char text[DEFAULT_BUFFER_SIZE + 1024];

	gettimeofday(&tv, NULL);
	t = localtime(&(tv.tv_sec));

	if (len == 0)
	{
		// [2016-06-27 20:30:30.251] level/ tag text
		snprintf(text, textLen, "[%04d-%02d-%02d %02d:%02d:%02d.%03d] %s/ %s %s\n",
				t->tm_year + 1900, t->tm_mon + 1, t->tm_mday, t->tm_hour,
				t->tm_min, t->tm_sec, tv.tv_usec/1000, cLevel, tag, buffer);
		len = strlen(text);
	}
	else
	{
		if(len > DEFAULT_BUFFER_SIZE)
		{
			len = DEFAULT_BUFFER_SIZE;
		}
		memcpy(text, buffer, len);
	}

	switch (_logType)
	{
		case MEMORY:
			writeToMemory(nLevel, tag, buffer);
			break;

		case FILES:
			writeToFile(text, len);
			break;

		case BOTH:
			writeToMemory(nLevel, tag, buffer);
			writeToFile(text, len);
			break;

		default:
			break;
	}

}

void UnicLog::writeToFile(const char *buffer, int len)
{
    if(NULL == _destPath){
        LOGE(THIS_FILE, "Write file but destPath is not set.");
        return;
    }


	pthread_mutex_lock(&_lock);

	FILE *fp = fopen(_destPath, "a");

	if (fp != NULL)
	{
		fwrite(buffer, len, 1, fp);
		fclose(fp);
	}else
	{
	    LOGE(THIS_FILE ,"File open failed.");
	}
	pthread_mutex_unlock(&_lock);
}


