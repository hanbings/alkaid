package com.alkaidmc.alkaid.common.lang;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;

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

    // 初始化操作
    public ReflectionActions load(String classname) {
        // forName 载入类
        try {
            this.clazz = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            this.error.accept(e);
        }
        return this;
    }

    public ReflectionActions load(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    // 过程操作
    public ReflectionActions method(String method, Class<?>... args) {
        // getMethod 获取方法
        try {
            this.method = this.clazz.getMethod(method, args);
        } catch (NoSuchMethodException e) {
            this.error.accept(e);
        }
        return this;
    }

    public ReflectionActions field(String field) {
        // getField 获取属性
        try {
            this.field = this.clazz.getField(field);
        } catch (NoSuchFieldException e) {
            this.error.accept(e);
        }
        return this;
    }

    public ReflectionActions unlock() {
        Optional.ofNullable(this.method).ifPresent(m -> {
            try {
                m.setAccessible(true);
            } catch (Exception e) {
                this.error.accept(e);
            }
        });
        Optional.ofNullable(this.field).ifPresent(f -> {
            try {
                f.setAccessible(true);
            } catch (Exception e) {
                this.error.accept(e);
            }
        });
        return this;
    }

    // 端点操作
    public void invoke(Object... args) {
        // invoke 执行方法
        try {
            this.method.invoke(null, args);
        } catch (Exception e) {
            this.error.accept(e);
        }
    }
}
