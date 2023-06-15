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

package com.alkaidmc.alkaid.metadata;

import com.alkaidmc.alkaid.metadata.nbt.NBTCompound;
import com.alkaidmc.alkaid.metadata.nbt.NBTData;
import com.alkaidmc.alkaid.metadata.nbt.NBTDataType;

import java.util.List;

/**
 * @author Milkory
 */
public class ContainerStream {

    private final ContainerStream root;
    private final NBTCompound compound;
    private final MetadataContainer owner;

    public ContainerStream(ContainerStream root, NBTCompound compound, MetadataContainer owner) {
        this.root = root == null ? this : root;
        this.compound = compound;
        this.owner = owner;
    }

    public ContainerStream access(String path) {
        return new ContainerStream(root, compound.getCompound(path), owner);
    }

    public ContainerStream merge(NBTCompound compound) {
        compound.merge(compound);
        return this;
    }

    public ContainerStream set(String path, byte value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, boolean value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, short value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, int value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, long value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, float value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, double value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, byte[] value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, String value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, List<NBTData> value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, NBTCompound value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, int[] value) {
        compound.set(path, value);
        return this;
    }

    public ContainerStream set(String path, long[] value) {
        compound.set(path, value);
        return this;
    }

    public void save() {
        compound.saveTo(owner);
    }

}
