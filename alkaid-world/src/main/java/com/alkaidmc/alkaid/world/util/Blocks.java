package com.alkaidmc.alkaid.world.util;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Block utilities
 */
@SuppressWarnings("unused")
public class Blocks {
    /**
     * 判断一个坐标是否在给定的一对对角坐标内 / is the given coordinate in the given diagonal coordinates
     *
     * @param start    对角坐标的起始点 / start point of the diagonal coordinates
     * @param end      对角坐标的终点 / end point of the diagonal coordinates
     * @param location 需要进行判断的坐标 / coordinate to be judged
     * @return 是否在对角坐标范围内 / whether in the diagonal coordinate range
     */
    public static boolean contains(@NotNull Location start, @NotNull Location end, @NotNull Location location) {
        int x1 = Math.min(start.getBlockX(), end.getBlockX());
        int y1 = Math.min(start.getBlockY(), end.getBlockY());
        int z1 = Math.min(start.getBlockZ(), end.getBlockZ());
        int x2 = Math.max(start.getBlockX(), end.getBlockX());
        int y2 = Math.max(start.getBlockY(), end.getBlockY());
        int z2 = Math.max(start.getBlockZ(), end.getBlockZ());

        return location.getBlockX() >= x1 && location.getBlockX() <= x2
                && location.getBlockY() >= y1 && location.getBlockY() <= y2
                && location.getBlockZ() >= z1 && location.getBlockZ() <= z2;
    }
}
