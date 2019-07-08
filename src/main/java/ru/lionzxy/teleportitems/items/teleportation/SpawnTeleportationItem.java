package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.lionzxy.teleportitems.exceptions.MinecraftTextFormattedException;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SpawnTeleportationItem extends BaseTeleportationItem {
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.teleportitems_spawn.description"));
    }

    @Nonnull
    @Override
    DimensionBlockPos getPortalPoint(World world, EntityPlayer player) throws MinecraftTextFormattedException {
        World overworld = DimensionManager.getWorld(0);
        int spawnX = overworld.getSpawnPoint().getX();
        int spawnY = overworld.getSpawnPoint().getY() + 1;
        int spawnZ = overworld.getSpawnPoint().getZ();
        return new DimensionBlockPos(spawnX, spawnY, spawnZ, overworld.provider.getDimension());
    }
}
