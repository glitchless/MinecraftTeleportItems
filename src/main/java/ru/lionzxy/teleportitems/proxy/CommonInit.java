package ru.lionzxy.teleportitems.proxy;

import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.teleportitems.TeleportItemsMod;
import ru.lionzxy.teleportitems.blocks.HomeBlock;
import ru.lionzxy.teleportitems.items.CraftItem;
import ru.lionzxy.teleportitems.items.CraftItemEnum;
import ru.lionzxy.teleportitems.items.teleportation.BackTeleportationItem;
import ru.lionzxy.teleportitems.items.teleportation.HomeTeleportationItem;
import ru.lionzxy.teleportitems.items.teleportation.RandomTeleportationItem;
import ru.lionzxy.teleportitems.items.teleportation.SpawnTeleportationItem;
import ru.lionzxy.teleportitems.utils.TeleportItemsCreativeTab;

public class CommonInit implements ISide {
    protected RandomTeleportationItem randomTeleportationItem = new RandomTeleportationItem();
    protected BackTeleportationItem backTeleportationItem = new BackTeleportationItem();
    protected HomeTeleportationItem homeTeleportationItem = new HomeTeleportationItem();
    protected SpawnTeleportationItem spawnTeleportationItem = new SpawnTeleportationItem();
    protected CraftItem craftItem = new CraftItem();
    protected HomeBlock homeBlock = new HomeBlock();
    protected ItemBlock homeItemBlock = new ItemBlock(homeBlock);

    protected TeleportItemsCreativeTab teleportItemsCreativeTab = new TeleportItemsCreativeTab(randomTeleportationItem);

    @Override
    public void preInit() {
        initItem();
        initBlock();

        ConfigManager.sync(TeleportItemsMod.MODID, Config.Type.INSTANCE);

        MinecraftForge.EVENT_BUS.register(TeleportItemsMod.getInstance().getTickHandler());
        MinecraftForge.EVENT_BUS.register(TeleportItemsMod.getInstance().getDeathHandler());
    }

    @Override
    public void postInit() {
        initCrafts();
    }

    private void initItem() {
        randomTeleportationItem.setUnlocalizedName("teleportitems_random");
        randomTeleportationItem.setRegistryName("teleportitems_random");
        randomTeleportationItem.setCreativeTab(teleportItemsCreativeTab);

        backTeleportationItem.setUnlocalizedName("teleportitems_back");
        backTeleportationItem.setRegistryName("teleportitems_back");
        backTeleportationItem.setCreativeTab(teleportItemsCreativeTab);

        homeTeleportationItem.setUnlocalizedName("teleportitems_home");
        homeTeleportationItem.setRegistryName("teleportitems_home");
        homeTeleportationItem.setCreativeTab(teleportItemsCreativeTab);

        spawnTeleportationItem.setUnlocalizedName("teleportitems_spawn");
        spawnTeleportationItem.setRegistryName("teleportitems_spawn");
        spawnTeleportationItem.setCreativeTab(teleportItemsCreativeTab);

        craftItem.setUnlocalizedName("teleportitems_craft");
        craftItem.setRegistryName("teleportitems_craft");
        craftItem.setCreativeTab(teleportItemsCreativeTab);

        ForgeRegistries.ITEMS.register(randomTeleportationItem);
        ForgeRegistries.ITEMS.register(backTeleportationItem);
        ForgeRegistries.ITEMS.register(homeTeleportationItem);
        ForgeRegistries.ITEMS.register(spawnTeleportationItem);
        ForgeRegistries.ITEMS.register(craftItem);

        for (CraftItemEnum craftItemEnum : CraftItemEnum.values()) {
            if (craftItemEnum.getOreDict() != null) {
                OreDictionary.registerOre(craftItemEnum.getOreDict(), craftItemEnum.getItemStack(craftItem));
            }
        }
    }

    private void initBlock() {
        homeBlock.setUnlocalizedName("teleportitems_homeset");
        homeBlock.setRegistryName("teleportitems_homeset");
        homeBlock.setCreativeTab(teleportItemsCreativeTab);

        ForgeRegistries.BLOCKS.register(homeBlock);

        homeItemBlock.setRegistryName(homeBlock.getRegistryName());

        ForgeRegistries.ITEMS.register(homeItemBlock);
    }

    private void initCrafts() {
        //GameRegistry.addShapedRecipe();
        initModdedCrafts();
    }

    private boolean initModdedCrafts() {
        if (!Loader.isModLoaded("ic2")) {
            return false;
        }

        if (OreDictionary.getOres("gemRuby").isEmpty() || OreDictionary.getOres("gemPeridot").isEmpty()) {
            return false;
        }

        if (OreDictionary.getOres("gemTopaz").isEmpty() || OreDictionary.getOres("gemSapphire").isEmpty()) {
            return false;
        }

        Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("gemRuby"), null, false,
                new ItemStack(craftItem, 8, CraftItemEnum.RUBY_DUST.getMetadata()));
        Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("gemPeridot"), null, false,
                new ItemStack(craftItem, 8, CraftItemEnum.PERIDOT_DUST.getMetadata()));
        Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("gemTopaz"), null, false,
                new ItemStack(craftItem, 8, CraftItemEnum.TOPAZ_DUST.getMetadata()));
        Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("gemSapphire"), null, false,
                new ItemStack(craftItem, 8, CraftItemEnum.SAPPHIRE_DUST.getMetadata()));
        Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("enderpearl"), null, false,
                new ItemStack(craftItem, 8, CraftItemEnum.PEARL_DUST.getMetadata()));
        Recipes.macerator.addRecipe(Recipes.inputFactory.forOreDict("dustEnder"), null, false,
                new ItemStack(craftItem, 8, CraftItemEnum.PEARL_DUST.getMetadata()));

        Recipes.compressor.addRecipe(Recipes.inputFactory.forStack(CraftItemEnum.RANDOM_DUST.getItemStack(craftItem)),
                null, false, CraftItemEnum.RANDOM_CORE.getItemStack(craftItem));
        Recipes.compressor.addRecipe(Recipes.inputFactory.forStack(CraftItemEnum.SPAWN_DUST.getItemStack(craftItem)),
                null, false, CraftItemEnum.SPAWN_CORE.getItemStack(craftItem));
        Recipes.compressor.addRecipe(Recipes.inputFactory.forStack(CraftItemEnum.HOME_DUST.getItemStack(craftItem)),
                null, false, CraftItemEnum.HOME_CORE.getItemStack(craftItem));
        Recipes.compressor.addRecipe(Recipes.inputFactory.forStack(CraftItemEnum.BACK_DUST.getItemStack(craftItem)),
                null, false, CraftItemEnum.BACK_CORE.getItemStack(craftItem));

        return true;
    }

    public HomeTeleportationItem getHomeTeleportationItem() {
        return homeTeleportationItem;
    }
}
