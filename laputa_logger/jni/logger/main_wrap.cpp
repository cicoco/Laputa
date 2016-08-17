//
// Created by tafiagu on 2016/6/27.
//
#include <stdlib.h>
#include <stdio.h>
#include <jni.h>

#include "main_wrap.h"
// custom import
#include "Log.h"
#include "UnicLog.h"

#define CLASSNAME "unic/cicoco/laputa/logger/UnicLogger"
#define THIS_FILE "main_wrap.cpp"

// Global variable
JavaVM* g_pJvm = NULL;

/**
* register the native methods
*/
static int registerNatives(JNIEnv *env, jclass clazz, JNINativeMethod *methods,
		int numMethods)
{
	if (env->RegisterNatives(clazz, methods, numMethods) < 0)
		return -1;

	return 0;
}

/**
 * when System.load(*.so), this method will execute;
 */
jint JNI_OnLoad(JavaVM* vm, void* reserved)
{

	g_pJvm = vm;
	JNIEnv* env;

	LOGI(THIS_FILE, "JNI_OnLoad");

	if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK)
	{
		LOGE(THIS_FILE, "ERROR:GetEnv failed\n");
		return JNI_ERR;
	}

	jclass cls = env->FindClass(CLASSNAME);
	if (cls == NULL)
	{
		LOGE(THIS_FILE, "ERROR:cls is null \n");
		return (JNI_ERR);
	}

	registerNatives(env, cls, methods, sizeof(methods) / sizeof(methods[0]));

	return JNI_VERSION_1_4;
}

void JNI_OnUnload(JavaVM *vm, void *reserved)
{
	LOGI(THIS_FILE, "JNI_OnUnload");
}






