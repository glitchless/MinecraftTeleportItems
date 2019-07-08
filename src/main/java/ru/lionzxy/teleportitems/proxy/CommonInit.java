package ru.lionzxy.teleportitems.proxy;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.lionzxy.teleportitems.TeleportItemsMod;
import ru.lionzxy.teleportitems.blocks.HomeBlock;
import ru.lionzxy.teleportitems.items.teleportation.BackTeleportationItem;
import ru.lionzxy.teleportitems.items.teleportation.HomeTeleportationItem;
import ru.lionzxy.teleportitems.items.teleportation.RandomTeleportationItem;
import ru.lionzxy.teleportitems.items.teleportation.SpawnTeleportationItem;

public class CommonInit implements ISide {
    protected RandomTeleportationItem randomTeleportationItem = new RandomTeleportationItem();
    protected BackTeleportationItem backTeleportationItem = new BackTeleportationItem();
    protected HomeTeleportationItem homeTeleportationItem = new HomeTeleportationItem();
    protected SpawnTeleportationItem spawnTeleportationItem = new SpawnTeleportationItem();
    protected HomeBlock homeBlock = new HomeBlock();
    protected ItemBlock homeItemBlock = new ItemBlock(homeBlock);

    @Override
    public void preInit() {
        initItem();
        initBlock();

        ConfigManager.sync(TeleportItemsMod.MODID, Config.Type.INSTANCE);
        
        MinecraftForge.EVENT_BUS.register(TeleportItemsMod.getInstance().getTickHandler());
        MinecraftForge.EVENT_BUS.register(TeleportItemsMod.getInstance().getDeathHandler());
    }

    private void initItem() {
        randomTeleportationItem.setUnlocalizedName("teleportitems_random");
        randomTeleportationItem.setRegistryName("teleportitems_random");

        backTeleportationItem.setUnlocalizedName("teleportitems_back");
        backTeleportationItem.setRegistryName("teleportitems_back");

        homeTeleportationItem.setUnlocalizedName("teleportitems_home");
        homeTeleportationItem.setRegistryName("teleportitems_home");

        spawnTeleportationItem.setUnlocalizedName("teleportitems_spawn");
        spawnTeleportationItem.setRegistryName("teleportitems_spawn");

        ForgeRegistries.ITEMS.register(randomTeleportationItem);
        ForgeRegistries.ITEMS.register(backTeleportationItem);
        ForgeRegistries.ITEMS.register(homeTeleportationItem);
        ForgeRegistries.ITEMS.register(spawnTeleportationItem);
    }

    private void initBlock() {
        homeBlock.setUnlocalizedName("teleportitems_homeset");
        homeBlock.setRegistryName("teleportitems_homeset");

        ForgeRegistries.BLOCKS.register(homeBlock);

        homeItemBlock.setRegistryName(homeBlock.getRegistryName());

        ForgeRegistries.ITEMS.register(homeItemBlock);
    }

    public HomeTeleportationItem getHomeTeleportationItem() {
        return homeTeleportationItem;
    }
}
