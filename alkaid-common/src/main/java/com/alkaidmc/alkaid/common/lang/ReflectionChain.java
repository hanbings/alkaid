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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ReflectionChain {
    Class<?> clazz;
    Method method;
    Field field;
    Object[] args;
    Object instance;
    Consumer<Object> result = (args) -> {
    };
    Consumer<Exception> exception = e -> {
        throw new RuntimeException(e);
    };

    ClassLoader loader = Class.class.getClassLoader();
    boolean initialize = true;

    public ReflectionChain clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ReflectionChain clazz(String classname) {
        try {
            this.clazz = Class.forName(classname, initialize, loader);
        } catch (ClassNotFoundException e) {
            exception.accept(e);
        }
        return this;
    }

    public ReflectionChain method(String method, Class<?>... parameter) {
        try {
            this.method = clazz.getMethod(method, parameter);
            this.method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            exception.accept(e);
        }
        return this;
    }

    public ReflectionChain field(String field) {
        try {
            this.field = clazz.getField(field);
            this.field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            exception.accept(e);
        }
        return this;
    }

    public void call() {
        try {
            result.accept(method.invoke(instance, args));
        } catch (Exception e) {
            exception.accept(e);
        }
    }
}
