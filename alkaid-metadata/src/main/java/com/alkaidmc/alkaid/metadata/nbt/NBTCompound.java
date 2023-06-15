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

import com.alkaidmc.alkaid.metadata.MetadataContainer;
import com.alkaidmc.alkaid.metadata.util.NMSUtil;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.nbt.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Milkory
 */
@Accessors(fluent = true, chain = true)
public class NBTCompound {

    @Getter private final Map<String, NBTData> data;

    public NBTCompound() {
        this.data = new HashMap<>();
    }

    public NBTCompound(Map<String, NBTData> data) {
        this.data = data;
    }

    public NBTCompound(NBTCompound compound) {
        this(compound.data);
    }

    /**
     * Save the compound to a metadata container.
     *
     * @param container The container
     */
    public void saveTo(MetadataContainer container) {
        container.saveMetadata(this);
    }

    /**
     * Merge other data compound into this one.
     *
     * @param compound The data to merge with
     * @return Self
     */
    public NBTCompound merge(NBTCompound compound) {
        data.putAll(compound.data);
        return this;
    }

    public void set(String path, byte value) {
        data.put(path, new NBTData(NBTDataType.BYTE, value));
    }

    public void set(String path, boolean value) {
        data.put(path, new NBTData(NBTDataType.BYTE, value ? 1 : 0));
    }

    public void set(String path, short value) {
        data.put(path, new NBTData(NBTDataType.SHORT, value));
    }

    public void set(String path, int value) {
        data.put(path, new NBTData(NBTDataType.INT, value));
    }

    public void set(String path, long value) {
        data.put(path, new NBTData(NBTDataType.LONG, value));
    }

    public void set(String path, float value) {
        data.put(path, new NBTData(NBTDataType.FLOAT, value));
    }

    public void set(String path, double value) {
        data.put(path, new NBTData(NBTDataType.DOUBLE, value));
    }

    public void set(String path, byte[] value) {
        data.put(path, new NBTData(NBTDataType.BYTE_ARRAY, value));
    }

    public void set(String path, String value) {
        data.put(path, new NBTData(NBTDataType.STRING, value));
    }

    public void set(String path, List<NBTData> value) {
        data.put(path, new NBTData(NBTDataType.LIST, value));
    }

    public void set(String path, NBTCompound value) {
        data.put(path, new NBTData(NBTDataType.COMPOUND, value));
    }

    public void set(String path, int[] value) {
        data.put(path, new NBTData(NBTDataType.INT_ARRAY, value));
    }

    public void set(String path, long[] value) {
        data.put(path, new NBTData(NBTDataType.LONG, value));
    }

    public NBTData get(String path) {
        return data.get(path);
    }

    public byte getByte(String path) {
        return data.get(path).asByte();
    }

    public boolean getBoolean(String path) {
        return data.get(path).asBoolean();
    }

    public short getShort(String path) {
        return data.get(path).asShort();
    }

    public int getInt(String path) {
        return data.get(path).asInt();
    }

    public long getLong(String path) {
        return data.get(path).asLong();
    }

    public float getFloat(String path) {
        return data.get(path).asFloat();
    }

    public double getDouble(String path) {
        return data.get(path).asDouble();
    }

    public byte[] getByteArray(String path) {
        return data.get(path).asByteArray();
    }

    public String getString(String path) {
        return data.get(path).asString();
    }

    public List<NBTData> getList(String path) {
        return data.get(path).asList();
    }

    public NBTCompound getCompound(String path) {
        return data.get(path).asCompound();
    }

    public int[] getIntArray(String path) {
        return data.get(path).asIntArray();
    }

    public long[] getLongArray(String path) {
        return data.get(path).asLongArray();
    }

    public NBTTagCompound toNMSCompound() {
        return null;
    }

    /**
     * @return An empty compound
     */
    public static NBTCompound of() {
        return new NBTCompound();
    }

    public static NBTCompound from(NBTTagCompound compound) {
        var result = NBTCompound.of();
        for (String key : compound.e()) {
            var value = compound.c(key);
            switch (value.b()) {
                case 1: // byte
                    result.set(key, ((NBTTagByte) value).i());
                    break;
                case 2: // short
                    result.set(key, ((NBTTagShort) value).h());
                    break;
                case 3: // int
                    result.set(key, ((NBTTagInt) value).g());
                    break;
                case 4: // long
                    result.set(key, ((NBTTagLong) value).f());
                    break;
                case 5: // float
                    result.set(key, ((NBTTagFloat) value).k());
                    break;
                case 6: // double
                    result.set(key, ((NBTTagDouble) value).j());
                    break;
                case 7: // byte array
                    result.set(key, ((NBTTagByteArray) value).e());
                    break;
                case 8: // string
                    result.set(key, value.f_());
                    break;
                case 9: // list
                    result.set(key, NMSUtil.toListFrom((NBTTagList) value));
                    break;
                case 10: // compound
                    result.set(key, NBTCompound.from((NBTTagCompound) value));
                    break;
                case 11: // int array
                    result.set(key, ((NBTTagIntArray) value).g());
                    break;
                case 12: // long array
                    result.set(key, ((NBTTagLongArray) value).g());
                    break;
            }
        }
        return null;
    }

}
