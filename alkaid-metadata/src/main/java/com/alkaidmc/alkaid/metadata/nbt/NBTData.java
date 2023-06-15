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
import net.minecraft.nbt.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Milkory
 */
public record NBTData(@Getter NBTDataType type, @Getter Object data) {

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

    NBTBase nms() {
        return switch (type) {
            case BYTE -> NBTTagByte.a(asByte());
            case SHORT -> NBTTagShort.a(asShort());
            case INT -> NBTTagInt.a(asInt());
            case LONG -> NBTTagLong.a(asLong());
            case FLOAT -> NBTTagFloat.a(asFloat());
            case DOUBLE -> NBTTagDouble.a(asDouble());
            case BYTE_ARRAY -> new NBTTagByteArray(asByteArray());
            case STRING -> NBTTagString.a(asString());
            case LIST -> NMSUtil.toNMSFrom(asList());
            case COMPOUND -> asCompound().toNMSCompound();
            case INT_ARRAY -> new NBTTagIntArray(asIntArray());
            case LONG_ARRAY -> new NBTTagLongArray(asLongArray());
        };
    }

    public static NBTData of(byte value) {
        return new NBTData(NBTDataType.BYTE, value);
    }

    public static NBTData of(boolean value) {
        return new NBTData(NBTDataType.BYTE, value ? 1 : 0);
    }

    public static NBTData of(short value) {
        return new NBTData(NBTDataType.SHORT, value);
    }

    public static NBTData of(int value) {
        return new NBTData(NBTDataType.INT, value);
    }

    public static NBTData of(long value) {
        return new NBTData(NBTDataType.LONG, value);
    }

    public static NBTData of(float value) {
        return new NBTData(NBTDataType.FLOAT, value);
    }

    public static NBTData of(double value) {
        return new NBTData(NBTDataType.DOUBLE, value);
    }

    public static NBTData of(byte[] value) {
        return new NBTData(NBTDataType.BYTE_ARRAY, value);
    }

    public static NBTData of(String value) {
        return new NBTData(NBTDataType.STRING, value);
    }

    public static NBTData of(List<NBTData> value) {
        return new NBTData(NBTDataType.LIST, value);
    }

    public static NBTData of(NBTCompound value) {
        return new NBTData(NBTDataType.COMPOUND, value);
    }

    public static NBTData of(int[] value) {
        return new NBTData(NBTDataType.INT_ARRAY, value);
    }

    public static NBTData of(long[] value) {
        return new NBTData(NBTDataType.LONG_ARRAY, value);
    }

}
