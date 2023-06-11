package com.alkaidmc.alkaid.metadata.container;

import com.alkaidmc.alkaid.metadata.MetadataContainer;
import com.alkaidmc.alkaid.metadata.nbt.NBTCompound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;

/**
 * @author Milkory
 */
@RequiredArgsConstructor
public class BlockContainer implements MetadataContainer {

    @Getter private final Block owner;

    @Override public NBTCompound getMetadata() {
        // TODO
        return NBTCompound.of();
    }

    @Override public void saveMetadata(NBTCompound data) {
        // TODO
    }

}
