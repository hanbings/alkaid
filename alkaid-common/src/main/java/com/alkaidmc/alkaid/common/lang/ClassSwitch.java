package com.alkaidmc.alkaid.common.lang;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class ClassSwitch {
    List<String> classes;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<String> success;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<Class<?>> nothing;

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Predicate<String> fail = x -> false;

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
