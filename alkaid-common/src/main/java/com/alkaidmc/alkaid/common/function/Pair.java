package com.alkaidmc.alkaid.common.function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@SuppressWarnings("unused")
@RequiredArgsConstructor(staticName = "of")
public class Pair<F, S> {
    final F first;
    final S second;
}


