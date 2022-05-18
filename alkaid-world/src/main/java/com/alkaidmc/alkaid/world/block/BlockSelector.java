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

package com.alkaidmc.alkaid.world.block;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;

import java.util.function.Supplier;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class BlockSelector {
    Location original;
    Location destination;

    public void original(Supplier<Location> original) {
        this.original = original.get();
    }

    public void destination(Supplier<Location> destination) {
        this.destination = destination.get();
    }
}
