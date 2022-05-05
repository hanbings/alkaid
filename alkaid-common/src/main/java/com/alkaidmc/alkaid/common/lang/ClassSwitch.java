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

package com.alkaidmc.alkaid.common.lang;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ClassSwitch {
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<String> classes;


    Consumer<String> success;
    Consumer<Class<?>> nothing;
    Predicate<String> fail = x -> false;

    @Setter(AccessLevel.NONE)
    @Accessors(fluent = true)
    Class<?> clazz = null;

    ClassLoader loader = Class.class.getClassLoader();

    public ClassSwitch in(String classname) {
        classes.add(classname);
        return this;
    }

    public ClassSwitch load() {
        for (String classname : classes) {
            try {
                clazz = loader.loadClass(classname);
                Optional.ofNullable(success).ifPresent(s -> s.accept(classname));
                return this;
            } catch (ClassNotFoundException e) {
                if (fail.test(classname)) return this;
            }
        }
        Optional.ofNullable(nothing).ifPresent(n -> n.accept(clazz));
        return this;
    }
}
