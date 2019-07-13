package ru.lionzxy.teleportitems.items;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;

public enum CraftItemEnum {
    PEARL_DUST(0, null, "pearl_dust"),
    PERIDOT_DUST(1, null, "peridot_dust"),
    HOME_CORE(2, null, "home_core"),
    BACK_DUST(3, null, "back_dust"),
    TOPAZ_DUST(4, null, "topaz_dust"),
    SPAWN_CORE(5, null, "spawn_core"),
    RANDOM_CORE(6, null, "random_core"),
    RUBY_DUST(7, null, "ruby_dust"),
    HOME_DUST(8, null, "home_dust"),
    BACK_CORE(9, null, "back_core"),
    SAPPHIRE_DUST(10, null, "sapphire_dust"),
    DETAIL_HALF(11, null, "detail_half"),
    SPAWN_DUST(12, null, "spawn_dust"),
    RANDOM_DUST(13, null, "random_dust");

    private static HashMap<Integer, CraftItemEnum> metaMap = new HashMap<>();

    private final int metadata;
    private final Color tintColor;
    private final String name;

    CraftItemEnum(int metadata, @Nullable Color tintColor, String name) {
        this.metadata = metadata;
        this.tintColor = tintColor;
        this.name = name;
    }

    @Nullable
    public static CraftItemEnum getEnumByMeta(int metadata) {
        if (metaMap.isEmpty()) {
            for (CraftItemEnum craftItemEnum : values()) {
                metaMap.put(craftItemEnum.metadata, craftItemEnum);
            }
        }
        return metaMap.get(metadata);
    }

    public int getMetadata() {
        return metadata;
    }

    public Color getTintColor() {
        return tintColor;
    }

    public String getName() {
        return name;
    }
}
