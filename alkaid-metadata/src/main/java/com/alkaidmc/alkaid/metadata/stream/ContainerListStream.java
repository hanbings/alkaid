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
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Milkory
 */
public record ContainerListStream(@Getter ContainerComponentStream root, List<NBTData> data, MetadataContainer owner) implements ContainerStream {

    public ContainerComponentStream access(int index) {
        return new ContainerComponentStream(root, data.get(index).asCompound(), owner);
    }

    public ContainerListStream access(int index, Consumer<ContainerComponentStream> then) {
        then.accept(new ContainerComponentStream(root, data.get(index).asCompound(), owner));
        return this;
    }

    public ContainerListStream accessList(int index) {
        return new ContainerListStream(root, data.get(index).asList(), owner);
    }

    public ContainerListStream accessList(int index, Consumer<ContainerListStream> then) {
        then.accept(new ContainerListStream(root, data.get(index).asList(), owner));
        return this;
    }

    @SuppressWarnings("all")
    public ContainerComponentStream access(Object... keys) {
        return StreamUtil.access(this, keys);
    }

    @SuppressWarnings("all")
    public ContainerListStream accessList(Object... keys) {
        return StreamUtil.accessList(this, keys);
    }

    public ContainerListStream add(byte value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(boolean value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(short value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(long value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(float value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(double value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(byte[] value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(String value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(List<NBTData> value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(NBTCompound value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(NBTCompound.Builder value) {
        data.add(NBTData.of(value.build()));
        return this;
    }

    public ContainerListStream add(int[] value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(long[] value) {
        data.add(NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, byte value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, boolean value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, short value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, int value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, long value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, float value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, double value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, byte[] value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, String value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, List<NBTData> value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, NBTCompound value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, int[] value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream add(int index, long[] value) {
        data.add(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, byte value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, boolean value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, short value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, int value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, long value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, float value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, double value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, byte[] value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, String value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, List<NBTData> value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, NBTCompound value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, int[] value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream set(int index, long[] value) {
        data.set(index, NBTData.of(value));
        return this;
    }

    public ContainerListStream remove(int index) {
        data.remove(index);
        return this;
    }

    public NBTCompound fullData() {
        return root.data();
    }

    public void save() {
        root.save();
    }

}
