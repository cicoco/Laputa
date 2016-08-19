//
// Created by tafiagu on 2016/6/27.
//
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>

#include "Log.h"
#include "UnicLog.h"

#define THIS_FILE "Log.cpp"


void setLogLevel(JNIEnv *env, jobject obj, jint level)
{
	UnicLog::setLogLevel(level);
}

void setLogWriteType(JNIEnv *env, jobject obj, jint t)
{
	UnicLog::setLogWriteType((WRITETYPE)t);
}

void setLogDestPath(JNIEnv *env, jobject obj, jstring destPath){


    const char* cDestPath = env->GetStringUTFChars(destPath, 0);

    UnicLog::setLogDestPath(cDestPath);

	env->ReleaseStringUTFChars(destPath, cDestPath);

}

void v(JNIEnv *env, jobject obj, jstring tag, jstring msg)
{
	printfLog(env, "V", 5, tag, msg);
}

void d(JNIEnv *env, jobject obj, jstring tag, jstring msg)
{
	printfLog(env, "D", 4, tag, msg);
}

void i(JNIEnv *env, jobject obj, jstring tag, jstring msg)
{
	printfLog(env, "I", 3, tag, msg);
}

void w(JNIEnv *env, jobject obj, jstring tag, jstring msg)
{
	printfLog(env, "W", 2, tag, msg);
}

void e(JNIEnv *env, jobject obj, jstring tag, jstring msg)
{
	printfLog(env, "E", 1, tag, msg);

}

void printfLog(JNIEnv *env, const char* cLevel, jint level, jstring tag, jstring msg)
{
	if(tag == NULL)
	{
	    UnicLog::log(1, THIS_FILE, "tag is null");
		return;
	}

	if(msg == NULL)
	{
	    UnicLog::log(1, THIS_FILE, "msg is null");
		return;
	}

	const char* cMsg= env->GetStringUTFChars(msg, 0);
	const char* cTag= env->GetStringUTFChars(tag, 0);

    UnicLog::writeLog(cLevel, level, cTag, cMsg);

	env->ReleaseStringUTFChars(msg, cMsg);
	env->ReleaseStringUTFChars(tag, cTag);

}