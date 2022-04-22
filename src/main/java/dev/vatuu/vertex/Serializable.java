package dev.vatuu.vertex;

@FunctionalInterface
public interface Serializable<K> {
    K serialize();
}
