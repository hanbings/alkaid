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
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Milkory
 */
@Accessors(fluent = true)
public record ContainerComponentStream(ContainerComponentStream root, NBTCompound compound, MetadataContainer owner) {

    public ContainerComponentStream(ContainerComponentStream root, NBTCompound compound, MetadataContainer owner) {
        this.root = root == null ? this : root;
        this.compound = compound;
        this.owner = owner;
    }

    public ContainerComponentStream access(String path) {
        return new ContainerComponentStream(root, compound.getCompound(path), owner);
    }

    public ContainerComponentStream access(String path, Consumer<ContainerComponentStream> then) {
        then.accept(new ContainerComponentStream(root, compound.getCompound(path), owner));
        return this;
    }

    public ContainerListStream accessList(String path) {
        return new ContainerListStream(root, compound.getList(path), owner);
    }

    public ContainerComponentStream accessList(String path, Consumer<ContainerListStream> then) {
        then.accept(new ContainerListStream(root, compound.getList(path), owner));
        return this;
    }

    public ContainerComponentStream merge(NBTCompound compound) {
        compound.merge(compound);
        return this;
    }

    public ContainerComponentStream set(String path, byte value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, boolean value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, short value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, int value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, long value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, float value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, double value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, byte[] value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, String value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, List<NBTData> value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, NBTCompound value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, int[] value) {
        compound.set(path, value);
        return this;
    }

    public ContainerComponentStream set(String path, long[] value) {
        compound.set(path, value);
        return this;
    }

    public void save() {
        if (root == this) {
            compound.saveTo(owner);
        } else {
            root.save();
        }
    }

}
