package cn.claycoffee.ClayTech.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetadataUtil {
    @Nullable
    public static MetadataValue getMetadata(Block b, String key) {
        List<MetadataValue> values = b.getMetadata(key);
        return values.isEmpty() ? null : values.get(0);
    }

    @Nullable
    public static MetadataValue getMetadata(LivingEntity e, String key) {
        List<MetadataValue> values = e.getMetadata(key);
        return values.isEmpty() ? null : values.get(0);
    }

    public static boolean getMetadataAsBoolean(Block b, String key) {
        MetadataValue value = getMetadata(b, key);
        return value != null && value.asBoolean();
    }

    public static boolean getMetadataAsBoolean(LivingEntity e, String key) {
        MetadataValue value = getMetadata(e, key);
        return value != null && value.asBoolean();
    }

    @Nullable
    public static String getMetadataAsString(Block b, String key) {
        MetadataValue value = getMetadata(b, key);
        return value != null ? value.asString() : null;
    }

    @Nullable
    public static String getMetadataAsString(LivingEntity e, String key) {
        MetadataValue value = getMetadata(e, key);
        return value != null ? value.asString() : null;
    }
}