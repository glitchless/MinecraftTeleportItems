package ru.lionzxy.teleportitems.storage;

import net.minecraftforge.common.config.Config;
import ru.lionzxy.teleportitems.TeleportItemsMod;

@Config(modid = TeleportItemsMod.MODID)
public class TeleportItemsConfig {
    @Config.Comment("delay teleport")
    public static int tpDelay = 5;

    public static boolean randomTeleportInWorldBorder = true;
    public static boolean useRandomTeleportOnlyInOverworld = true;
}
