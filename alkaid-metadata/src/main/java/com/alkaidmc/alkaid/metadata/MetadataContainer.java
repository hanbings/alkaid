package com.alkaidmc.alkaid.metadata;

import com.alkaidmc.alkaid.metadata.nbt.NBTCompound;

/**
 * @author Milkory
 */
public interface MetadataContainer {

    /**
     * Get a copy of this container's metadata.
     * @return The metadata
     */
    NBTCompound getMetadata();

    /**
     * Save metadata for this container.
     * @param data The data to save
     */
    void saveMetadata(NBTCompound data);

    default ContainerStream stream() {
        return new ContainerStream(null, getMetadata(), this);
    }

    /**
     * Merge other data compound into this container's metadata.
     * @param compound The data to merge with
     * @return The merging result, which hasn't been modified by the server
     */
    default NBTCompound mergeMetadataWith(NBTCompound compound) {
        var result = getMetadata().merge(compound);
        saveMetadata(result);
        return result;
    }

}
