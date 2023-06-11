package com.alkaidmc.alkaid.metadata;

import com.alkaidmc.alkaid.metadata.nbt.NBTCompound;
import com.alkaidmc.alkaid.metadata.nbt.NBTData;

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

    public void save() {
        compound.saveTo(owner);
    }

}
