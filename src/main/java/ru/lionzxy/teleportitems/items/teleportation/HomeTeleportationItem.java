package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;
import ru.lionzxy.teleportitems.exceptions.MinecraftTextFormattedException;
import ru.lionzxy.teleportitems.storage.HomeStorage;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class HomeTeleportationItem extends BaseTeleportationItem {
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.teleportitems_home.description"));
    }

    @Nonnull
    @Override
    DimensionBlockPos getPortalPoint(World world, EntityPlayer player) throws MinecraftTextFormattedException {
        final HomeStorage homeStorage = HomeStorage.getInstance(player.getEntityWorld());
        DimensionBlockPos blockPos = homeStorage.getHome(player);
        if (blockPos == null) {
            throw new MinecraftTextFormattedException(TextComponentHelper.createComponentTranslation(player, "teleportitems.nothome_text"));

        }
        return new DimensionBlockPos(blockPos.getX(), blockPos.getY() + 2, blockPos.getZ(), blockPos.getDimension());
    }
}
