package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;
import ru.lionzxy.teleportitems.exceptions.MinecraftTextFormattedException;
import ru.lionzxy.teleportitems.storage.DeathStorage;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BackTeleportationItem extends BaseTeleportationItem {
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.teleportitems_back.description"));
    }

    @Nonnull
    @Override
    DimensionBlockPos getPortalPoint(World world, EntityPlayer player) throws MinecraftTextFormattedException {
        final DeathStorage storage = DeathStorage.getInstance(player.world);
        DimensionBlockPos blockPos = storage.getLastPlayerDeath(player);
        if (blockPos == null) {
            throw new MinecraftTextFormattedException(TextComponentHelper.createComponentTranslation(player, "teleportitems.notback_text"));
        }
        return blockPos;
    }
}
