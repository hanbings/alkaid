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
import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.nbt.*;

import java.util.ArrayList;
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

    public void set(String key, byte value) {
        data.put(key, new NBTData(NBTDataType.BYTE, value));
    }

    public void set(String key, boolean value) {
        data.put(key, new NBTData(NBTDataType.BYTE, value ? 1 : 0));
    }

    public void set(String key, short value) {
        data.put(key, new NBTData(NBTDataType.SHORT, value));
    }

    public void set(String key, int value) {
        data.put(key, new NBTData(NBTDataType.INT, value));
    }

    public void set(String key, long value) {
        data.put(key, new NBTData(NBTDataType.LONG, value));
    }

    public void set(String key, float value) {
        data.put(key, new NBTData(NBTDataType.FLOAT, value));
    }

    public void set(String key, double value) {
        data.put(key, new NBTData(NBTDataType.DOUBLE, value));
    }

    public void set(String key, byte[] value) {
        data.put(key, new NBTData(NBTDataType.BYTE_ARRAY, value));
    }

    public void set(String key, String value) {
        data.put(key, new NBTData(NBTDataType.STRING, value));
    }

    public void set(String key, List<NBTData> value) {
        data.put(key, new NBTData(NBTDataType.LIST, value));
    }

    public void set(String key, NBTCompound value) {
        data.put(key, new NBTData(NBTDataType.COMPOUND, value));
    }

    public void set(String key, int[] value) {
        data.put(key, new NBTData(NBTDataType.INT_ARRAY, value));
    }

    public void set(String key, long[] value) {
        data.put(key, new NBTData(NBTDataType.LONG, value));
    }

    public NBTData get(String key) {
        return data.get(key);
    }

    private NBTData getOrCreate(String key, NBTData value) {
        var result = data.get(key);
        if (result == null) {
            data.put(key, value);
            return value;
        } else {
            return result;
        }
    }

    public byte getByte(String key) {
        return getOrCreate(key, NBTData.BYTE).asByte();
    }

    public boolean getBoolean(String key) {
        return getOrCreate(key, NBTData.BYTE).asBoolean();
    }

    public short getShort(String key) {
        return getOrCreate(key, NBTData.SHORT).asShort();
    }

    public int getInt(String key) {
        return getOrCreate(key, NBTData.INT).asInt();
    }

    public long getLong(String key) {
        return getOrCreate(key, NBTData.LONG).asLong();
    }

    public float getFloat(String key) {
        return getOrCreate(key, NBTData.FLOAT).asFloat();
    }

    public double getDouble(String key) {
        return getOrCreate(key, NBTData.DOUBLE).asDouble();
    }

    public byte[] getByteArray(String key) {
        return getOrCreate(key, NBTData.BYTE_ARRAY).asByteArray();
    }

    public String getString(String key) {
        return getOrCreate(key, NBTData.STRING).asString();
    }

    public List<NBTData> getList(String key) {
        return getOrCreate(key, NBTData.of(new ArrayList<>())).asList();
    }

    public NBTCompound getCompound(String key) {
        return getOrCreate(key, NBTData.of(new NBTCompound())).asCompound();
    }

    public int[] getIntArray(String key) {
        return getOrCreate(key, NBTData.INT_ARRAY).asIntArray();
    }

    public long[] getLongArray(String key) {
        return getOrCreate(key, NBTData.LONG_ARRAY).asLongArray();
    }

    public NBTTagCompound toNMSCompound() {
        var result = new NBTTagCompound();
        data.forEach((k, v) -> result.a(k, v.nms()));
        return result;
    }

    /**
     * @return An empty compound
     */
    public static NBTCompound of() {
        return new NBTCompound();
    }

    public static NBTCompound of(Map<String, NBTData> data) {
        return new NBTCompound(data);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static NBTCompound from(NBTTagCompound compound) {
        var result = NBTCompound.of();
        for (String key : compound.e()) {
            var value = compound.c(key);
            switch (value.b()) {
                case 1 -> // byte
                        result.set(key, ((NBTTagByte) value).i());
                case 2 -> // short
                        result.set(key, ((NBTTagShort) value).h());
                case 3 -> // int
                        result.set(key, ((NBTTagInt) value).g());
                case 4 -> // long
                        result.set(key, ((NBTTagLong) value).f());
                case 5 -> // float
                        result.set(key, ((NBTTagFloat) value).k());
                case 6 -> // double
                        result.set(key, ((NBTTagDouble) value).j());
                case 7 -> // byte array
                        result.set(key, ((NBTTagByteArray) value).e());
                case 8 -> // string
                        result.set(key, value.f_());
                case 9 -> // list
                        result.set(key, NMSUtil.toListFrom((NBTTagList) value));
                case 10 -> // compound
                        result.set(key, NBTCompound.from((NBTTagCompound) value));
                case 11 -> // int array
                        result.set(key, ((NBTTagIntArray) value).g());
                case 12 -> // long array
                        result.set(key, ((NBTTagLongArray) value).g());
            }
        }
        return result;
    }

    public static class Builder {
        private final NBTCompound compound = new NBTCompound();

        public Builder merge(NBTCompound compound) {
            compound.merge(compound);
            return this;
        }

        public Builder entry(String key, byte value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, boolean value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, short value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, int value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, long value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, float value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, double value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, byte[] value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, String value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, List<NBTData> value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, NBTCompound value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, int[] value) {
            compound.set(key, value);
            return this;
        }

        public Builder entry(String key, long[] value) {
            compound.set(key, value);
            return this;
        }

        public NBTCompound build() {
            return compound;
        }
    }

}
