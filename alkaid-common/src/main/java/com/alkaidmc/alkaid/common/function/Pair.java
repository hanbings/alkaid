package com.alkaidmc.alkaid.common.function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
@Accessors(fluent = true)
public class Pair<F, S> {
    final F first;
    final S second;

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }
}


