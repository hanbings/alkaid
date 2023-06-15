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
import com.alkaidmc.alkaid.metadata.stream.ContainerComponentStream;

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

    default ContainerComponentStream stream() {
        return new ContainerComponentStream(null, getMetadata(), this);
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
