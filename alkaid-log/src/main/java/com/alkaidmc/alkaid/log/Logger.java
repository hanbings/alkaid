/*
 * Copyright 2022 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alkaidmc.alkaid.log;

import lombok.Builder;
import org.fusesource.jansi.Ansi;

@SuppressWarnings("unused")
public class Logger {
    static java.util.logging.Logger logger;
    static String prefix;
    static Ansi.Color info;
    static Ansi.Color warning;
    static Ansi.Color severe;
    static Ansi.Color reset = Ansi.Color.DEFAULT;

    static {
        logger = java.util.logging.Logger.getLogger("Alkaid");
        prefix = "[Alkaid] ";
        info = Ansi.Color.GREEN;
        warning = Ansi.Color.YELLOW;
        severe = Ansi.Color.RED;
    }

    @Builder
    public Logger(String prefix, Ansi.Color info, Ansi.Color warning, Ansi.Color severe) {
        Logger.prefix = prefix;
        Logger.info = info;
        Logger.warning = warning;
        Logger.severe = severe;
    }

    public static void info(String msg) {
        logger.info(info + prefix + reset + msg);
    }

    public static void warning(String msg) {
        logger.warning(warning + prefix + reset + msg);
    }

    public static void severe(String msg) {
        logger.severe(severe + prefix + reset + msg);
    }

}
