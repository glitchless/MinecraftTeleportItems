package ru.lionzxy.teleportitems.handlers;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.lionzxy.teleportitems.storage.DeathStorage;
import ru.lionzxy.teleportitems.storage.models.DimensionBlockPos;

@Mod.EventBusSubscriber
public class DeathHandler {
    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayerMP)) {
            return;
        }

        final EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.getEntityLiving();
        final DeathStorage deathStorage = DeathStorage.getInstance(entityPlayerMP.world);
        deathStorage.setLastDeath(entityPlayerMP, new DimensionBlockPos(entityPlayerMP));
    }
}
