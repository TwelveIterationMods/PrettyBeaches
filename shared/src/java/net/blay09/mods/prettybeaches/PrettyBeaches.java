package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.BreakBlockEvent;
import net.blay09.mods.balm.api.event.TickPhase;
import net.blay09.mods.balm.api.event.TickType;
import net.blay09.mods.balm.api.event.UseItemEvent;
import net.blay09.mods.prettybeaches.config.PrettyBeachesConfig;

public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    public static void initialize() {
        PrettyBeachesConfig.initialize();

        Balm.getEvents().onTickEvent(TickType.ServerLevel, TickPhase.End, FloodingManager::onWorldTick);

        Balm.getEvents().onEvent(BreakBlockEvent.class, BreakBlockHandler::onBreakBlock);
        Balm.getEvents().onEvent(UseItemEvent.class, BucketHandler::onItemUse);
    }


}
