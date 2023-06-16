package com.alkaidmc.alkaid.metadata.stream;

/**
 * @author Milkory
 */
public class StreamUtil {
    @SuppressWarnings("all")
    static ContainerComponentStream access(ContainerStream begin, Object[] keys) {
        ContainerStream current = begin;
        for (int i = 0; i < keys.length; i++) {
            var key = keys[i];
            var nextKey = i + 1 < keys.length ? keys[i + 1] : null;
            if (key instanceof Integer) {
                if (nextKey instanceof Integer) {
                    current = ((ContainerListStream) current).accessList((int) key);
                } else {
                    current = ((ContainerListStream) current).access((int) key);
                }
            } else if (key instanceof String) {
                if (nextKey instanceof Integer) {
                    current = ((ContainerComponentStream) current).accessList((String) key);
                } else {
                    current = ((ContainerComponentStream) current).access((String) key);
                }
            } else {
                throw new IllegalArgumentException("Only string and integer are allowed");
            }
        }
        return (ContainerComponentStream) current;
    }

    @SuppressWarnings("all")
    static ContainerListStream accessList(ContainerStream begin, Object... keys) {
        ContainerStream current = begin;
        for (int i = 0; i < keys.length; i++) {
            var key = keys[i];
            var nextKey = i + 1 < keys.length ? keys[i + 1] : null;
            if (key instanceof Integer) {
                if (nextKey instanceof String) {
                    current = ((ContainerListStream) current).access((int) key);
                } else {
                    current = ((ContainerListStream) current).accessList((int) key);
                }
            } else if (key instanceof String) {
                if (nextKey instanceof String) {
                    current = ((ContainerComponentStream) current).access((String) key);
                } else {
                    current = ((ContainerComponentStream) current).accessList((String) key);
                }
            } else {
                throw new IllegalArgumentException("Only string and integer are allowed");
            }
        }
        return (ContainerListStream) current;
    }
}
