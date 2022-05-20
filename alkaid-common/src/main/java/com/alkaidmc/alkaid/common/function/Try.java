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

package com.alkaidmc.alkaid.common.function;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class Try<T> {
    Supplier<T> trying;
    Consumer<T> success;
    Consumer<Throwable> fail;
    Lazy<List<Function<Throwable, Throwable>>> cases = Lazy.of(ArrayList::new);

    public Try(Supplier<T> trying) {
        this.trying = trying;
    }

    public static <T> Try<T> of(Supplier<T> trying) {
        return new Try<>(trying);
    }

    public T get() {
        try {
            T t = trying.get();
            success.accept(t);
            return t;
        } catch (Throwable e) {
            // 处理特定错误
            cases.get().forEach(f -> {
                if (f.getClass().equals(e.getClass())) {
                    f.apply(e);
                }
            });

            if (fail != null) {
                fail.accept(e);
            }
            return null;
        }
    }
}
