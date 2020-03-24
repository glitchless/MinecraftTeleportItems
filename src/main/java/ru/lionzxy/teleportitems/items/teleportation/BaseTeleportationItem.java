package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDigging;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.lionzxy.teleportitems.TeleportItemsMod;
import ru.lionzxy.teleportitems.exceptions.MinecraftTextFormattedException;
import ru.lionzxy.teleportitems.items.CraftItemEnum;
import ru.lionzxy.teleportitems.storage.TeleportItemsConfig;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;
import ru.lionzxy.teleportitems.utils.WorldBlockHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public abstract class BaseTeleportationItem extends Item {
    public BaseTeleportationItem() {
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public static void teleportPlayer(EntityPlayer entity, DimensionBlockPos dimensionBlockPos) {
        final DimensionBlockPos emptyPos = WorldBlockHelper.findFirstEmptyBlockByY(dimensionBlockPos);
        emptyPos.teleport(entity);

        World world = DimensionManager.getWorld(dimensionBlockPos.getDimension());
        SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport"));
        if (event == null) {
            return;
        }
        world.playSound(null, dimensionBlockPos.getX(), dimensionBlockPos.getY(), dimensionBlockPos.getZ(), event, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.splash_potion.break"));
        if (event != null) {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, event, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }

        if (!worldIn.isRemote) {
            stack.shrink(1);
            DimensionBlockPos pos;
            try {
                pos = getPortalPoint(worldIn, playerIn);
            } catch (MinecraftTextFormattedException e) {
                playerIn.sendMessage(e.getReason());
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
            }
            WorldBlockHelper.prepareForTeleportation(pos);
            scheduleTeleport(playerIn, pos, stack);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Nonnull
    abstract DimensionBlockPos getPortalPoint(World world, EntityPlayer player) throws MinecraftTextFormattedException;


    // Instant teleport
    protected void scheduleTeleport(EntityPlayer playerIn, DimensionBlockPos pos,
                                    @Nullable ItemStack itemStack) {
        if (isInstantTeleport(itemStack)) {
            teleportPlayer(playerIn, pos);
            return;
        }

        new TeleportDelayThread(playerIn, new Runnable() {
            @Override
            public void run() {
                teleportPlayer(playerIn, pos);
            }
        }).start();
    }

    private boolean isInstantTeleport(@Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        return itemStack.getMetadata() == 1;
    }

    // Sub items
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (isInstantTeleport(stack)) {
            final String prefix = I18n.translateToLocal("item.instant_prefix.name").trim();
            return prefix + " " + super.getItemStackDisplayName(stack);
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return isInstantTeleport(stack);
    }
}
