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

package com.alkaidmc.alkaid.metadata.nbt;

import lombok.RequiredArgsConstructor;

/**
 * @author Milkory
 */
@RequiredArgsConstructor
public enum NBTDataType {

    BYTE(1),

    SHORT(2),

    INT(3),

    LONG(4),

    FLOAT(5),

    DOUBLE(6),

    BYTE_ARRAY(7),

    STRING(8),

    LIST(9),

    COMPOUND(10),

    INT_ARRAY(11),

    LONG_ARRAY(12);

    final int id;

}
