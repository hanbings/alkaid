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
package com.alkaidmc.alkaid.world.util;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Chunk utilities
 */
@SuppressWarnings("unused")
public class Chunks {
    /**
     * 获取一个区域内的所有区块 / get all the chunks in the given area.
     *
     * @param start 区域的起始点 / start point of the area
     * @param end   区域的终点 / end point of the area
     * @return 区域内的所有区块 / all the chunks in the area
     */
    public static List<Chunk> chunks(@NotNull Location start, @NotNull Location end) {
        List<Chunk> chunks = new ArrayList<>();

        int minX = Math.min(start.getChunk().getX(), end.getChunk().getX());
        int maxX = Math.max(start.getChunk().getX(), end.getChunk().getX());
        int minZ = Math.min(start.getChunk().getZ(), end.getChunk().getZ());
        int maxZ = Math.max(start.getChunk().getZ(), end.getChunk().getZ());

        if (minX == maxX && minZ == maxZ) {
            chunks.add(start.getChunk());
            return chunks;
        }

        for (int x = minX; x < maxX; x = x + 16) {
            for (int z = minZ; z < maxZ; z = z + 16) {
                chunks.add(new Location(start.getWorld(), x, 0, z).getChunk());
            }
        }

        return chunks;
    }
}
