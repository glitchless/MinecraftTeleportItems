package ru.lionzxy.teleportitems.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

public class WorldBlockHelper {
    public static DimensionBlockPos findFirstEmptyBlockByY(DimensionBlockPos src) {
        final WorldServer sourceDim = DimensionManager.getWorld(src.getDimension());
        DimensionBlockPos current = src;
        while (!isPlayerCapaciousBlock(sourceDim, current)) {
            int x = current.getX();
            int y = current.getY() + 1;
            int z = current.getZ();
            int dimension = current.getDimension();
            current = new DimensionBlockPos(x, y, z, dimension);
        }
        return current;
    }

    public static boolean isPlayerCapaciousBlock(World worldServer, BlockPos pos) {
        if (!worldServer.isAirBlock(pos)) {
            return false;
        }

        int x = pos.getX();
        int y = pos.getY() + 1;
        int z = pos.getZ();
        return worldServer.isAirBlock(new BlockPos(x, y, z));
    }

    public static void prepareForTeleportation(DimensionBlockPos blockPos) {
        World world = DimensionManager.getWorld(blockPos.getDimension());

        world.getChunkProvider().provideChunk(blockPos.getX(), blockPos.getZ()).markDirty();
    }
}
