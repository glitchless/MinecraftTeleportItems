package ru.lionzxy.teleportitems.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;

public enum CraftItemEnum {
    PEARL_DUST(0, null, "pearl_dust", "dustEnder"),
    PERIDOT_DUST(1, null, "peridot_dust", "dustPeridot"),
    HOME_CORE(2, null, "home_core", null),
    BACK_DUST(3, null, "back_dust", "dustBack"),
    TOPAZ_DUST(4, null, "topaz_dust", "dustTopaz"),
    SPAWN_CORE(5, null, "spawn_core", null),
    RANDOM_CORE(6, null, "random_core", null),
    RUBY_DUST(7, null, "ruby_dust", "dustRuby"),
    HOME_DUST(8, null, "home_dust", "dustHome"),
    BACK_CORE(9, null, "back_core", null),
    SAPPHIRE_DUST(10, null, "sapphire_dust", "dustSapphire"),
    DETAIL_HALF(11, null, "detail_half", null),
    SPAWN_DUST(12, null, "spawn_dust", "dustSpawn"),
    RANDOM_DUST(13, null, "random_dust", "dustRandom");

    private static HashMap<Integer, CraftItemEnum> metaMap = new HashMap<>();

    private final int metadata;
    private final Color tintColor;
    private final String name;
    private final String oreDict;

    CraftItemEnum(int metadata, @Nullable Color tintColor, String name, @Nullable String oreDict) {
        this.metadata = metadata;
        this.tintColor = tintColor;
        this.name = name;
        this.oreDict = oreDict;
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

    public ItemStack getItemStack(Item item) {
        return new ItemStack(item, 1, metadata);
    }

    public String getName() {
        return name;
    }

    public String getOreDict() {
        return oreDict;
    }
}
