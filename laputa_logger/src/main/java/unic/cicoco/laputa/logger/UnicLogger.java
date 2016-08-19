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

/**
 * Created by tafiagu on 16-6-28.
 */
public class UnicLogger {

    static native void setLogLevel(int logLevel);

    static native void setLogWriteType(int writeType);

    static native void setLogDestPath(String destPath);

    static native void v(String tag, String msg);

    static native void d(String tag, String msg);

    static native void i(String tag, String msg);

    static native void w(String tag, String msg);

    static native void e(String tag, String msg);

    static native void writeToFile(String destPath, String log);


}
