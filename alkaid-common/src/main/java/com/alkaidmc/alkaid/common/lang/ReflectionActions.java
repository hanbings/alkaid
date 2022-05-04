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
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ReflectionActions {
    @Getter
    @Accessors(fluent = true, chain = true)
    Class<?> clazz;
    @Getter
    @Accessors(fluent = true, chain = true)
    Method method;
    @Getter
    @Accessors(fluent = true, chain = true)
    Field field;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<Exception> error = exception -> {
        throw new RuntimeException(exception);
    };

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    ClassLoader loader = Class.class.getClassLoader();

    public ReflectionActions load(String classname, Consumer<Exception> error) {
        // forName 载入类
        try {
            this.clazz = this.loader.loadClass(classname);
        } catch (ClassNotFoundException e) {
            (Optional.ofNullable(error).orElse(this.error)).accept(e);
        }
        return this;
    }

    // 初始化操作
    public ReflectionActions load(String classname) {
        return load(classname, null);
    }

    public ReflectionActions load(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    // 过程操作
    public ReflectionActions method(String method, Consumer<Exception> error, Class<?>... args) {
        // getMethod 获取方法
        try {
            this.method = this.clazz.getMethod(method, args);
        } catch (NoSuchMethodException e) {
            (Optional.ofNullable(error).orElse(this.error)).accept(e);
        }
        return this;
    }

    public ReflectionActions method(String method, Class<?>... args) {
        return method(method, null, args);
    }

    public ReflectionActions field(String field, Consumer<Exception> error) {
        // getField 获取属性
        try {
            this.field = this.clazz.getField(field);
        } catch (NoSuchFieldException e) {
            (Optional.ofNullable(error).orElse(this.error)).accept(e);
        }
        return this;
    }

    public ReflectionActions field(String field) {
        return field(field, null);
    }

    public ReflectionActions unlock(Consumer<Exception> error) {
        Optional.ofNullable(this.method).ifPresent(m -> {
            try {
                m.setAccessible(true);
            } catch (Exception e) {
                (Optional.ofNullable(error).orElse(this.error)).accept(e);
            }
        });
        Optional.ofNullable(this.field).ifPresent(f -> {
            try {
                f.setAccessible(true);
            } catch (Exception e) {
                (Optional.ofNullable(error).orElse(this.error)).accept(e);
            }
        });
        return this;
    }

    public ReflectionActions unlock() {
        return unlock(null);
    }

    // 端点操作
    // todo 换用 method handler 执行
    public void invoke(Consumer<Exception> error, Object... args) {
        // invoke 执行方法
        try {
            this.method.invoke(null, args);
        } catch (Exception e) {
            (Optional.ofNullable(error).orElse(this.error)).accept(e);
        }
    }

    public void invoke(Object... args) {
        invoke(null, args);
    }
}
