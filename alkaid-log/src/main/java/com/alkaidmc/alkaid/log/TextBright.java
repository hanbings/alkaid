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

import org.fusesource.jansi.Ansi;

@SuppressWarnings("unused")
public class TextBright {
    public static String black(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.BLACK).a(text).reset().toString();
    }

    public static String red(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.RED).a(text).reset().toString();
    }

    public static String green(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.GREEN).a(text).reset().toString();
    }

    public static String yellow(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.YELLOW).a(text).reset().toString();
    }

    public static String blue(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.BLUE).a(text).reset().toString();
    }

    public static String magenta(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.MAGENTA).a(text).reset().toString();
    }

    public static String cyan(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.CYAN).a(text).reset().toString();
    }

    public static String white(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.WHITE).a(text).reset().toString();
    }
}
