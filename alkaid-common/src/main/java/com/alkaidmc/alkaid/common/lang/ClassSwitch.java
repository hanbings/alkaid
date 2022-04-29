package com.alkaidmc.alkaid.common.lang;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ClassSwitch {
    // todo： 处理函数接口
    List<String> classes;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<String> success;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<String> nothing;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<String> fail;

    @Getter
    @Accessors(fluent = true, chain = true)
    Class<?> clazz = null;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    ClassLoader loader = Class.class.getClassLoader();

    public ClassSwitch in(String classname) {
        classes.add(classname);
        return this;
    }

    public ClassSwitch load() {
        for (String classname : classes) {
            try {
                clazz = loader.loadClass(classname);
                break;
            } catch (ClassNotFoundException e) {
                Optional.ofNullable(fail).ifPresent(c -> c.accept(classname));
            }
        }
        return this;
    }
}
