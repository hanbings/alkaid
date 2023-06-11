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

import com.alkaidmc.alkaid.metadata.container.BlockContainer;
import com.alkaidmc.alkaid.metadata.container.EntityContainer;
import com.alkaidmc.alkaid.metadata.container.ItemContainer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class AlkaidMetadata {

    public ContainerStream of(Block block) {
        return new BlockContainer(block).stream();
    }

    public ContainerStream of(Entity entity) {
        return new EntityContainer(entity).stream();
    }

    public ContainerStream of(ItemStack item) {
        return new ItemContainer(item).stream();
    }

    public static void test() {
        new AlkaidMetadata().of(new ItemStack(Material.AIR))
                .set("123", true)
                .save();
    }

}
