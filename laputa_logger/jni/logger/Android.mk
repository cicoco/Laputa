# 用于在开发树中查找源文件。在这个例子中，宏函数’my-dir’, 由编译系统提供，
#用于返回当前路径（即包含Android.mk file文件的目录）
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

MY_CPP_LIST := $(wildcard $(LOCAL_PATH)/*.cpp)

#when load static lib, the foundation lib must be placed later
LOCAL_LDLIBS:=-llog

# 必须，名称必须是唯一的，而且不包含任何空格。会自动生成so， libuniclog.so
LOCAL_MODULE    := uniclog

LOCAL_SRC_FILES := $(MY_CPP_LIST:$(LOCAL_PATH)/%=%)

include $(BUILD_SHARED_LIBRARY)
