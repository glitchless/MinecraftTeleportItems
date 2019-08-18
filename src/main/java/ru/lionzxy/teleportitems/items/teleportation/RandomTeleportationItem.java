package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import ru.lionzxy.teleportitems.exceptions.MinecraftTextFormattedException;
import ru.lionzxy.teleportitems.storage.TeleportItemsConfig;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RandomTeleportationItem extends BaseTeleportationItem {
    private final Random random = new Random();
    private final int DEFAULT_BORDER_RADIUS = 30000000 - 100;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (TeleportItemsConfig.useRandomTeleportOnlyInOverworld && worldIn.provider.getDimension() != 0) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    DimensionBlockPos getPortalPoint(World world, EntityPlayer player) throws MinecraftTextFormattedException {
        int startX = world.getSpawnPoint().getX() - DEFAULT_BORDER_RADIUS;
        int endX = world.getSpawnPoint().getX() + DEFAULT_BORDER_RADIUS;
        int startZ = world.getSpawnPoint().getZ() - DEFAULT_BORDER_RADIUS;
        int endZ = world.getSpawnPoint().getZ() + DEFAULT_BORDER_RADIUS;
        if (TeleportItemsConfig.randomTeleportInWorldBorder) {
            final WorldBorder border = world.getWorldBorder();
            startX = (int) (border.getCenterX() - (border.getDiameter() / 2 - 100));
            endX = (int) (border.getCenterX() + (border.getDiameter() / 2 - 100));
            startZ = (int) (border.getCenterZ() - (border.getDiameter() / 2 - 100));
            endZ = (int) (border.getCenterZ() + (border.getDiameter() / 2 - 100));
        }
        int randomX = (int) (startX + random.nextDouble() * (endX - startX));
        int randomZ = (int) (startZ + random.nextDouble() * (endZ - startZ));

        int randomY = findY(world, randomX, randomZ);
        return new DimensionBlockPos(randomX, randomY, randomZ, world.provider.getDimension());
    }

    public static int findY(World world, int x, int z) {
        for (int y = 255; y > 0; y--) {
            if (!world.isAirBlock(new BlockPos(x, y, z))) {
                return y + 1;
            }
        }
        return 255;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.teleportitems_random.description"));
    }
}
