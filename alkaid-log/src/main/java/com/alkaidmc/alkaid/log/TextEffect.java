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
public class TextEffect {
    public static String underline(String text) {
        return Ansi.ansi().a(Ansi.Attribute.UNDERLINE).a(text).reset().toString();
    }

    public static String bold(String text) {
        return Ansi.ansi().a(Ansi.Attribute.INTENSITY_BOLD).a(text).reset().toString();
    }

    public static String negative(String text) {
        return Ansi.ansi().a(Ansi.Attribute.NEGATIVE_ON).a(text).reset().toString();
    }
}
