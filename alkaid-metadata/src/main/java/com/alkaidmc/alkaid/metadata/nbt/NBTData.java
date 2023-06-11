/*
 * Copyright 2023 Alkaid
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Milkory
 */
@Getter
@RequiredArgsConstructor
public class NBTData {

    private final NBTDataType type;
    private final Object data;

    public byte asByte() {
        return (byte) data;
    }

    public boolean asBoolean() {
        return asByte() == 1;
    }

    public short asShort() {
        return (short) data;
    }

    public int asInt() {
        return (int) data;
    }

    public long asLong() {
        return (long) data;
    }

    public float asFloat() {
        return (float) data;
    }

    public double asDouble() {
        return (double) data;
    }

    public byte[] asByteArray() {
        return (byte[]) data;
    }

    public String asString() {
        return (String) data;
    }

    @SuppressWarnings("unchecked")
    public List<NBTData> asList() {
        return (List<NBTData>) data;
    }

    public NBTCompound asCompound() {
        return (NBTCompound) data;
    }

    public int[] asIntArray() {
        return (int[]) data;
    }

    public long[] asLongArray() {
        return (long[]) data;
    }

}
