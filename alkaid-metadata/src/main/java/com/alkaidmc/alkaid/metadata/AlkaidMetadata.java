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

import com.alkaidmc.alkaid.metadata.container.EntityContainer;
import com.alkaidmc.alkaid.metadata.container.ItemContainer;
import com.alkaidmc.alkaid.metadata.stream.ContainerComponentStream;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class AlkaidMetadata {

    public ContainerComponentStream of(Entity entity) {
        return new EntityContainer(entity).stream();
    }

    public ContainerComponentStream of(ItemStack item) {
        return new ItemContainer(item).stream();
    }

}
