package ru.lionzxy.teleportitems.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CraftItem extends Item {
    public CraftItem() {
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < CraftItemEnum.values().length; i++) {
            ItemStack subItemStack = new ItemStack(this, 1, i);
            subItems.add(subItemStack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int metadata = stack.getMetadata();
        return super.getUnlocalizedName(stack) + "_" + CraftItemEnum.getEnumByMeta(metadata).getName();
    }
}
