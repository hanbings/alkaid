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

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class HappyLazy<T> {
    final Supplier<T> supplier;
    volatile T value;

    public HappyLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> HappyLazy<T> of(Supplier<T> supplier) {
        return new HappyLazy<>(supplier);
    }

    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = supplier.get();
                }
            }
        }
        return value;
    }

    public boolean done() {
        return value != null;
    }
}
