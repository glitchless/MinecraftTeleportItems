package ru.lionzxy.teleportitems.handlers;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod.EventBusSubscriber
public class TickHandler {
    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        while (!queue.isEmpty()) {
            queue.poll().run();
        }
    }

    public void handle(Runnable runnable) {
        queue.add(runnable);
    }


}
