package dev.vatuu.vertex.config.codec;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import dev.vatuu.vertex.EnumUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

public final class Codecs {

    public static final Codec<NamespacedKey> NAMESPACED_KEY = Codec.STRING.xmap(NamespacedKey::fromString, NamespacedKey::toString);
    public static final Codec<Vector> VECTOR = Codec.DOUBLE.listOf().xmap(l -> new Vector(l.get(0), l.get(1), l.get(2)), v -> Lists.newArrayList(v.getX(), v.getY(), v.getZ()));

    public static final Codec<Material> MATERIAL = NAMESPACED_KEY.xmap(
            k -> EnumUtils.getEnumOrDefault(Material.class, Material.AIR, e -> k.equals(e.getKey())),
            Material::getKey);

    public static final Codec<Sound> SOUND = NAMESPACED_KEY.xmap(
            k -> EnumUtils.getEnumOrDefault(Sound.class, Sound.BLOCK_GLASS_BREAK, e -> k.equals(e.getKey())),
            Sound::getKey);
}
