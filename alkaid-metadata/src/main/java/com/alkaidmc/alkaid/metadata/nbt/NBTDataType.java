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
