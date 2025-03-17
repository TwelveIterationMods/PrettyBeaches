package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.*;

public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    public static void initialize() {
        PrettyBeachesConfig.initialize();

        Balm.getEvents().onTickEvent(TickType.ServerLevel, TickPhase.End, FloodingManager::onWorldTick);

        Balm.getEvents().onEvent(BreakBlockEvent.class, BreakBlockHandler::onBreakBlock, EventPriority.Lowest);
        Balm.getEvents().onEvent(UseItemEvent.class, BucketHandler::onItemUse);
    }


}
