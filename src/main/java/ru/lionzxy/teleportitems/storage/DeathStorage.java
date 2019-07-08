package ru.lionzxy.teleportitems.storage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import ru.lionzxy.teleportitems.TeleportItemsMod;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DeathStorage extends WorldSavedData {
    private static final String DATA_NAME = TeleportItemsMod.MODID + "_deaths";
    private final Map<String, DimensionBlockPos> lastDeath = new HashMap<>();

    public DeathStorage() {
        super(DATA_NAME);
    }

    public DeathStorage(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (String key : nbt.getKeySet()) {
            final DimensionBlockPos pos = DimensionBlockPos.readFromNBT(nbt.getCompoundTag(key));
            lastDeath.put(key, pos);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for (Map.Entry<String, DimensionBlockPos> entry : lastDeath.entrySet()) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            entry.getValue().writeToNBT(nbtTagCompound);
            compound.setTag(entry.getKey(), nbtTagCompound);
        }
        return compound;
    }

    public void setLastDeath(EntityPlayer entityPlayer, DimensionBlockPos pos) {
        final String key = entityPlayer.getGameProfile().getId().toString();
        lastDeath.put(key, pos);
    }

    @Nullable
    public DimensionBlockPos getLastPlayerDeath(EntityPlayer player) {
        final String key = player.getGameProfile().getId().toString();
        return lastDeath.get(key);
    }

    public static DeathStorage getInstance(World world) {
        final MapStorage mapStorage = world.getMapStorage();
        DeathStorage instance = (DeathStorage) mapStorage.getOrLoadData(DeathStorage.class, DATA_NAME);

        if (instance == null) {
            instance = new DeathStorage();
            mapStorage.setData(DATA_NAME, instance);
        }
        return instance;
    }
}
