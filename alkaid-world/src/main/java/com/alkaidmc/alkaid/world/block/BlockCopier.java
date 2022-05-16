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

import com.alkaidmc.alkaid.world.block.interfaces.BlockFilter;
import com.alkaidmc.alkaid.world.block.interfaces.LocationFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class BlockCopier {
    final Plugin plugin;

    Consumer<BlockSelector> from;
    Consumer<BlockSelector> to;

    BlockFilter block;
    LocationFilter location;
    BlockClipboard clipboard;

    boolean async;
    boolean segment;
    int limit;

    // fixme 完全不可用以及过滤和每 ticks 限制功能未实现
    public BlockClipboard clipboard() {
        if (clipboard != null) {
            return clipboard;
        } else {
            clipboard = new BlockClipboard();
        }

        // 遍历源坐标方块
        from.andThen(selector -> {
            IntStream x = selector.original.getBlockX() > selector.destination().getBlockX()
                    ? IntStream.range(selector.original.getBlockX(), selector.destination().getBlockX() + 1)
                    : IntStream.range(selector.destination().getBlockX(), selector.original.getBlockX() + 1);
            IntStream y = selector.original.getBlockY() > selector.destination().getBlockY()
                    ? IntStream.range(selector.original.getBlockY(), selector.destination().getBlockY() + 1)
                    : IntStream.range(selector.destination().getBlockY(), selector.original.getBlockY() + 1);
            IntStream z = selector.original.getBlockZ() > selector.destination().getBlockZ()
                    ? IntStream.range(selector.original.getBlockZ(), selector.destination().getBlockZ() + 1)
                    : IntStream.range(selector.destination().getBlockZ(), selector.original.getBlockZ() + 1);

            // 获取世界
            World world = Optional.ofNullable(selector.original.getWorld())
                    .orElse(Bukkit.getWorlds().get(0));

            Runnable runnable = () -> {
                // 遍历目标坐标方块
                x.forEach(i ->
                        y.forEach(j ->
                                z.forEach(k -> clipboard.blocks().add(world.getBlockAt(i, j, k)))));
            };

            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
            } else {
                runnable.run();
            }

        }).accept(new BlockSelector());

        return clipboard;
    }

    public void paste() {
        BlockClipboard clipboard = Optional.ofNullable(this.clipboard)
                .orElse(this.clipboard());

        // 遍历目标坐标方块
        to.andThen(selector -> {
            IntStream x = selector.original.getBlockX() > selector.destination().getBlockX()
                    ? IntStream.range(selector.original.getBlockX(), selector.destination().getBlockX() + 1)
                    : IntStream.range(selector.destination().getBlockX(), selector.original.getBlockX() + 1);
            IntStream y = selector.original.getBlockY() > selector.destination().getBlockY()
                    ? IntStream.range(selector.original.getBlockY(), selector.destination().getBlockY() + 1)
                    : IntStream.range(selector.destination().getBlockY(), selector.original.getBlockY() + 1);
            IntStream z = selector.original.getBlockZ() > selector.destination().getBlockZ()
                    ? IntStream.range(selector.original.getBlockZ(), selector.destination().getBlockZ() + 1)
                    : IntStream.range(selector.destination().getBlockZ(), selector.original.getBlockZ() + 1);

            // 获取世界
            World world = Optional.ofNullable(selector.original.getWorld())
                    .orElse(Bukkit.getWorlds().get(0));

            Runnable runnable = () -> {
                final int[] count = {0};
                // 遍历目标坐标方块
                x.forEach(i ->
                        y.forEach(j ->
                                z.forEach(k -> {
                                    Block block = clipboard.blocks().get(count[0]);
                                    Block target = world.getBlockAt(i, j, k);

                                    target.setType(block.getType());
                                    target.setBlockData(block.getBlockData());
                                    target.setBiome(block.getBiome());

                                    count[0]++;
                                })));
            };

            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
            } else {
                runnable.run();
            }
        }).accept(new BlockSelector());
    }
}
