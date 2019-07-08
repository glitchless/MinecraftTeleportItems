package ru.lionzxy.teleportitems.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import ru.lionzxy.teleportitems.blocks.HomeBlockEnum;

public class ClientInit extends CommonInit {

    @Override
    public void preInit() {
        super.preInit();
        initItemTexture();
        initBlockTexture();
    }

    private void initItemTexture() {
        ModelResourceLocation backModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_back", "inventory");
        ModelResourceLocation homeModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_home", "inventory");
        ModelResourceLocation randomModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_random", "inventory");
        ModelResourceLocation spawnModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_spawn", "inventory");
        ModelLoader.setCustomModelResourceLocation(backTeleportationItem, 0, backModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(homeTeleportationItem, 0, homeModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(randomTeleportationItem, 0, randomModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(spawnTeleportationItem, 0, spawnModelResourceLocation);
    }

    private void initBlockTexture() {
        ModelResourceLocation homeModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_homeset", "inventory");
        ModelLoader.setCustomModelResourceLocation(homeItemBlock, HomeBlockEnum.DISACTIVATE.getId(), homeModelResourceLocation);
        ModelResourceLocation activeHomeModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_homeset_active", "inventory");
        ModelLoader.setCustomModelResourceLocation(homeItemBlock, HomeBlockEnum.ACTIVATE.getId(), activeHomeModelResourceLocation);
    }
}
