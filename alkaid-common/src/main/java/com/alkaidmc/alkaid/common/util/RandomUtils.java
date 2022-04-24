package com.alkaidmc.alkaid.common.util;

public class RandomUtils {
    public static int number() {
        return (int) (Math.random() * 100);
    }

    public static int number(int max) {
        return (int) (Math.random() * max);
    }

    public static int number(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static boolean bool() {
        return Math.random() > 0.5;
    }

    public static boolean bool(double chance) {
        return Math.random() < chance;
    }

    public static String uuid() {
        return java.util.UUID.randomUUID().toString();
    }
}
