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

package com.alkaidmc.alkaid.common.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class Try<T> {

    volatile T value;
    volatile Throwable throwable;

    public Try(T value) {
        this.value = value;
    }

    public Try(Throwable throwable) {
        this.throwable = throwable;
    }

    public static <T> Try<T> of(Supplier<T> trying) {
        try {
            return new Try<>(trying.get());
        } catch (Throwable throwable) {
            return new Try<>(throwable);
        }
    }

    public boolean success() {
        return throwable == null;
    }

    public Try<T> success(Consumer<T> consumer) {
        if (success()) {
            consumer.accept(value);
        }
        return this;
    }

    public Try<T> fail(Consumer<Throwable> consumer) {
        if (!success()) {
            consumer.accept(throwable);
        }
        return this;
    }
}
