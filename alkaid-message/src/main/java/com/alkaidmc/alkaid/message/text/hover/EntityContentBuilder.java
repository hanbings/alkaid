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

package com.alkaidmc.alkaid.message.text.hover;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;

/**
 * @author Milkory
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class EntityContentBuilder implements ContentBuilder<Entity> {
    private String type;
    private String id;
    private BaseComponent name;

    public EntityContentBuilder entity(org.bukkit.entity.Entity entity) {
        this.type = entity.getType().getKey().toString();
        this.id = entity.getUniqueId().toString();
        this.name = new TextComponent(entity.getCustomName());
        // TODO: entity's name is a component, but bukkit only provided a String :(
        return this;
    }

    @Override public Entity buildContent() {
        return new Entity(type, id, name);
    }
}
