package ru.lionzxy.teleportitems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import ru.lionzxy.teleportitems.handlers.DeathHandler;
import ru.lionzxy.teleportitems.handlers.TickHandler;
import ru.lionzxy.teleportitems.proxy.ClientInit;
import ru.lionzxy.teleportitems.proxy.ISide;
import ru.lionzxy.teleportitems.proxy.ServerInit;

@Mod(modid = TeleportItemsMod.MODID, name = TeleportItemsMod.NAME, version = TeleportItemsMod.VERSION)
public class TeleportItemsMod {
    public static final String MODID = "teleportitems";
    public static final String NAME = "Teleport Items";
    public static final String VERSION = "1.0.0";
    private static TeleportItemsMod INSTANCE;
    private Logger logger;

    private ISide side;

    private DeathHandler deathHandler = new DeathHandler();
    private TickHandler tickHandler = new TickHandler();

    public TeleportItemsMod() {
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            side = new ClientInit();
        } else {
            side = new ServerInit();
        }
        side.preInit();
    }

    public ISide getSide() {
        return side;
    }

    public TickHandler getTickHandler() {
        return tickHandler;
    }

    public DeathHandler getDeathHandler() {
        return deathHandler;
    }

    public Logger getLogger() {
        return logger;
    }

    public static TeleportItemsMod getInstance() {
        return INSTANCE;
    }
}
