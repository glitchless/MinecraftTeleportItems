package ru.lionzxy.teleportitems.items.teleportation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.server.command.TextComponentHelper;
import ru.lionzxy.teleportitems.TeleportItemsMod;
import ru.lionzxy.teleportitems.storage.TeleportItemsConfig;

import java.util.concurrent.TimeUnit;

public class TeleportDelayThread extends Thread {
    final private Runnable onTeleport;
    final private int delay = TeleportItemsConfig.tpDelay;
    final private EntityPlayer entityPlayer;
    final private BlockPos initPos;
    final private float initHp;


    public TeleportDelayThread(EntityPlayer player, Runnable onTeleport) {
        this.onTeleport = onTeleport;
        this.entityPlayer = player;
        this.initPos = entityPlayer.getPosition();
        this.initHp = entityPlayer.getHealth();
    }

    @Override
    public void run() {
        super.run();
        try {
            int delayRemain = delay;
            long timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            while (delayRemain > 0) {
                delayRemain = delay - (int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timestamp);

                final BlockPos currentPos = entityPlayer.getPosition();
                if (!currentPos.equals(initPos) || initHp > entityPlayer.getHealth()) {
                    ITextComponent message = TextComponentHelper.createComponentTranslation(entityPlayer, "teleportitems.moving_teleport_text");
                    entityPlayer.sendMessage(message);
                    entityPlayer.sendStatusMessage(message, true);
                    return;
                }

                entityPlayer.sendStatusMessage(TextComponentHelper.createComponentTranslation(entityPlayer, "teleportitems.delay_teleport_text", delayRemain), true);
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
            entityPlayer.sendStatusMessage(TextComponentHelper.createComponentTranslation(entityPlayer, "teleportitems.teleporting_text"), true);
            TeleportItemsMod.getInstance().getTickHandler().handle(onTeleport);
        } catch (Exception e) {
            TeleportItemsMod.getInstance().getLogger().error(e);
        }
    }
}
