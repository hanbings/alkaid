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

package com.alkaidmc.alkaid.metadata.stream;

import com.alkaidmc.alkaid.metadata.MetadataContainer;
import com.alkaidmc.alkaid.metadata.nbt.NBTCompound;
import com.alkaidmc.alkaid.metadata.nbt.NBTData;
import com.alkaidmc.alkaid.metadata.nbt.NBTDataType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Milkory
 */
public record ContainerListStream(@Getter ContainerComponentStream root, List<NBTData> list, MetadataContainer owner) {

    public ContainerComponentStream access(int index) {
        return new ContainerComponentStream(root, list.get(index).asCompound(), owner);
    }

    public ContainerListStream access(int index, Consumer<ContainerComponentStream> then) {
        then.accept(new ContainerComponentStream(root, list.get(index).asCompound(), owner));
        return this;
    }

    public ContainerListStream accessList(int index) {
        return new ContainerListStream(root, list.get(index).asList(), owner);
    }

    public ContainerListStream accessList(int index, Consumer<ContainerListStream> then) {
        then.accept(new ContainerListStream(root, list.get(index).asList(), owner));
        return this;
    }

    public ContainerListStream add(byte value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(boolean value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(short value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(long value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(float value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(double value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(byte[] value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(String value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(List<NBTData> value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(NBTCompound value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int[] value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(long[] value) {
        list.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, byte value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, boolean value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, short value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, int value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, long value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, float value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, double value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, byte[] value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, String value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, List<NBTData> value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, NBTCompound value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, int[] value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, long[] value) {
        list.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, byte value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, boolean value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, short value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, int value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, long value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, float value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, double value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, byte[] value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, String value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, List<NBTData> value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, NBTCompound value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, int[] value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, long[] value) {
        list.set(index, NBTData.of(value));
        return this;
    }

    public void save() {
        root.save();
    }

}
