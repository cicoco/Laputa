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
public interface LogLevel {
    /**
     * 不打印日志
     */
    public static final int NONE = 0;
    public static final int ERROR = 1;
    public static final int WARN = 2;
    public static final int INFO = 3;
    public static final int DEBUG = 4;
    public static final int VERBOSE = 5;
    /**
     * 打印所有级别日志
     */
    public static final int ALL = Integer.MAX_VALUE;
}
