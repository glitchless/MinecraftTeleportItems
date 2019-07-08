package ru.lionzxy.teleportitems.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TeleportItemsCreativeTab extends CreativeTabs {
    private final Item item;

    public TeleportItemsCreativeTab(Item item) {
        super("tabTeleportItems");
        this.item = item;
    }

    public TeleportItemsCreativeTab(int index, Item item) {
        super(index, "teleportitems");
        this.item = item;
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(item);
    }
}
