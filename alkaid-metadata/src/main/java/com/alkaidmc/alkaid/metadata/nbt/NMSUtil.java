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

import net.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Milkory
 */
public class NMSUtil {

    static List<NBTData> toListFrom(NBTTagList list) {
        var result = new ArrayList<NBTData>();
        for (NBTBase item : list) {
            switch (item.b()) {
                case 1 -> // byte
                        result.add(new NBTData(NBTDataType.BYTE, ((NBTTagByte) item).i()));
                case 2 -> // short
                        result.add(new NBTData(NBTDataType.SHORT, ((NBTTagShort) item).h()));
                case 3 -> // int
                        result.add(new NBTData(NBTDataType.INT, ((NBTTagInt) item).g()));
                case 4 -> // long
                        result.add(new NBTData(NBTDataType.LONG, ((NBTTagLong) item).f()));
                case 5 -> // float
                        result.add(new NBTData(NBTDataType.FLOAT, ((NBTTagFloat) item).k()));
                case 6 -> // double
                        result.add(new NBTData(NBTDataType.DOUBLE, ((NBTTagDouble) item).j()));
                case 7 -> // byte array
                        result.add(new NBTData(NBTDataType.BYTE_ARRAY, ((NBTTagByteArray) item).e()));
                case 8 -> // string
                        result.add(new NBTData(NBTDataType.STRING, item.f_()));
                case 9 -> // list
                        result.add(new NBTData(NBTDataType.LIST, NMSUtil.toListFrom((NBTTagList) item)));
                case 10 -> // compound
                        result.add(new NBTData(NBTDataType.COMPOUND, NBTCompound.from((NBTTagCompound) item)));
                case 11 -> // int array
                        result.add(new NBTData(NBTDataType.INT_ARRAY, ((NBTTagIntArray) item).g()));
                case 12 -> // long array
                        result.add(new NBTData(NBTDataType.LONG_ARRAY, ((NBTTagLongArray) item).g()));
            }
        }
        return result;
    }

    static NBTTagList toNMSFrom(List<NBTData> list) {
        var result = new NBTTagList();
        for (NBTData data : list) {
            result.add(data.nms());
        }
        return result;
    }

}
