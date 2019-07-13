package ru.lionzxy.teleportitems.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import ru.lionzxy.teleportitems.blocks.HomeBlockEnum;
import ru.lionzxy.teleportitems.items.CraftItemEnum;

public class ClientInit extends CommonInit {

    @Override
    public void preInit() {
        super.preInit();
        initItemTexture();
        initBlockTexture();
    }

    private void initItemTexture() {
        addItemTexture(backTeleportationItem, "back");
        addItemTexture(homeTeleportationItem, "home");
        addItemTexture(randomTeleportationItem, "random");
        addItemTexture(spawnTeleportationItem, "spawn");

        for (CraftItemEnum craftItemEnum : CraftItemEnum.values()) {
            addItemTexture(craftItem, craftItemEnum.getMetadata(), "craft_" + craftItemEnum.getName());
        }
    }

    private void addItemTexture(Item item, String modelKey) {
        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_" + modelKey, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
    }

    private void addItemTexture(Item item, int metadata, String modelKey) {
        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_" + modelKey, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }

    private void initBlockTexture() {
        ModelResourceLocation homeModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_homeset", "inventory");
        ModelLoader.setCustomModelResourceLocation(homeItemBlock, HomeBlockEnum.DISACTIVATE.getId(), homeModelResourceLocation);
        ModelResourceLocation activeHomeModelResourceLocation = new ModelResourceLocation("teleportitems:teleportitems_homeset_active", "inventory");
        ModelLoader.setCustomModelResourceLocation(homeItemBlock, HomeBlockEnum.ACTIVATE.getId(), activeHomeModelResourceLocation);
    }
}
