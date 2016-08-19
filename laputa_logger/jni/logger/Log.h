//
// Created by tafiagu on 2016/6/27.
//

#ifndef LIB_LOGGER_LOG_H
#define LIB_LOGGER_LOG_H

extern "C" {
        void printfLog(JNIEnv *env, const char *cLevel, jint level, jstring tag, jstring msg);

        void setLogLevel(JNIEnv *env, jobject obj, jint level);
       	void setLogWriteType(JNIEnv *env, jobject obj, jint t);
       	void setLogDestPath(JNIEnv *env, jobject obj, jstring destPath);

       	void v(JNIEnv *env, jobject obj, jstring tag, jstring msg);
       	void d(JNIEnv *env, jobject obj, jstring tag, jstring msg);
       	void i(JNIEnv *env, jobject obj, jstring tag, jstring msg);
       	void w(JNIEnv *env, jobject obj, jstring tag, jstring msg);
       	void e(JNIEnv *env, jobject obj, jstring tag, jstring msg);

};

static JNINativeMethod methods[] = {
        {"setLogLevel", "(I)V", (void*)setLogLevel},
      	{"setLogWriteType", "(I)V", (void*)setLogWriteType},
      	{"setLogDestPath", "(Ljava/lang/String;)V", (void*)setLogDestPath},

      	{"v", "(Ljava/lang/String;Ljava/lang/String;)V", (void*)v},
      	{"d", "(Ljava/lang/String;Ljava/lang/String;)V", (void*)d},
      	{"i", "(Ljava/lang/String;Ljava/lang/String;)V", (void*)i},
      	{"w", "(Ljava/lang/String;Ljava/lang/String;)V", (void*)w},
      	{"e", "(Ljava/lang/String;Ljava/lang/String;)V", (void*)e},
};


#endif //LIB_LOGGER_LOG_H



