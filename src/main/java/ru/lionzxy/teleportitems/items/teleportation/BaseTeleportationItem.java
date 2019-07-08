package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.lionzxy.teleportitems.exceptions.MinecraftTextFormattedException;
import ru.lionzxy.teleportitems.storage.TeleportItemsConfig;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

import javax.annotation.Nonnull;

public abstract class BaseTeleportationItem extends Item {
    private static final int UPDATE_RANGE = 16;


    public BaseTeleportationItem() {
        this.setCreativeTab(CreativeTabs.MISC);
    }

    public static void teleportPlayer(EntityPlayer entity, DimensionBlockPos dimensionBlockPos) {
        dimensionBlockPos.teleport(entity);

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
            prepareForTeleportation(pos);
            new TeleportDelayThread(playerIn, new Runnable() {
                @Override
                public void run() {
                    teleportPlayer(playerIn, pos);
                }
            }).start();
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Nonnull
    abstract DimensionBlockPos getPortalPoint(World world, EntityPlayer player) throws MinecraftTextFormattedException;

    private void prepareForTeleportation(DimensionBlockPos blockPos) {
        World world = DimensionManager.getWorld(blockPos.getDimension());

        world.getChunkProvider().provideChunk(blockPos.getX(), blockPos.getZ()).markDirty();
    }
}
