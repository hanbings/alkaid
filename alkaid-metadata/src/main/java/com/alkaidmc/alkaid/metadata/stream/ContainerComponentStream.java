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
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Milkory
 */
@Accessors(fluent = true)
public record ContainerComponentStream(ContainerComponentStream root, NBTCompound data, MetadataContainer owner) implements ContainerStream {

    public ContainerComponentStream(ContainerComponentStream root, NBTCompound data, MetadataContainer owner) {
        this.root = root == null ? this : root;
        this.data = data;
        this.owner = owner;
    }

    public ContainerComponentStream access(String key) {
        return new ContainerComponentStream(root, data.getCompound(key), owner);
    }

    public ContainerComponentStream access(String key, Consumer<ContainerComponentStream> then) {
        then.accept(new ContainerComponentStream(root, data.getCompound(key), owner));
        return this;
    }

    public ContainerListStream accessList(String key) {
        return new ContainerListStream(root, data.getList(key), owner);
    }

    public ContainerComponentStream accessList(String key, Consumer<ContainerListStream> then) {
        then.accept(new ContainerListStream(root, data.getList(key), owner));
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

    public ContainerComponentStream merge(NBTCompound compound) {
        compound.merge(compound);
        return this;
    }

    public ContainerComponentStream set(String key, byte value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, boolean value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, short value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, int value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, long value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, float value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, double value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, byte[] value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, String value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, List<NBTData> value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, NBTCompound value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, NBTCompound.Builder builder) {
        data.set(key, builder.build());
        return this;
    }

    public ContainerComponentStream set(String key, int[] value) {
        data.set(key, value);
        return this;
    }

    public ContainerComponentStream set(String key, long[] value) {
        data.set(key, value);
        return this;
    }

    public NBTCompound fullData() {
        return root.data();
    }

    public void save() {
        if (root == this) {
            data.saveTo(owner);
        } else {
            root.save();
        }
    }

}
