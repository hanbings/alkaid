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

import com.alkaidmc.alkaid.message.text.JSONTextBuilder;
import net.md_5.bungee.api.chat.hover.content.Content;

/**
 * @author Milkory
 */
public interface ContentBuilder<T extends Content> {
    T buildContent();

    static Provider provider() {
        return Provider.instance;
    }

    class Provider {
        static Provider instance = new Provider();

        public EntityContentBuilder entity() {
            return new EntityContentBuilder();
        }

        public ItemContentBuilder item() {
            return new ItemContentBuilder();
        }

        public JSONTextBuilder text() {
            return new JSONTextBuilder();
        }
    }
}
