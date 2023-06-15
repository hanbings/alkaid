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

package com.alkaidmc.alkaid.metadata.container;

import com.alkaidmc.alkaid.metadata.MetadataContainer;
import com.alkaidmc.alkaid.metadata.nbt.NBTCompound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * @author Milkory
 */
@RequiredArgsConstructor
public class ItemContainer implements MetadataContainer {

    @Getter private final ItemStack owner;

    @Override public NBTCompound getMetadata() {
        var nms = CraftItemStack.asNMSCopy(owner);
        if (nms.t()) {
            return NBTCompound.from(nms.u());
        } else {
            return NBTCompound.of();
        }
    }

    @Override public void saveMetadata(NBTCompound data) {
        var nms = CraftItemStack.asNMSCopy(owner);
        nms.c(data.toNMSCompound());
        owner.setItemMeta(CraftItemStack.asBukkitCopy(nms).getItemMeta());
    }

}
