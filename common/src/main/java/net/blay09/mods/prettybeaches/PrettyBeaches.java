package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.balm.platform.event.EventPhases;
import net.blay09.mods.balm.platform.event.callback.BlockCallback;
import net.blay09.mods.balm.platform.event.callback.ItemCallback;
import net.blay09.mods.balm.platform.event.callback.ServerTickCallback;
import net.minecraft.resources.Identifier;

public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    public static void initialize(BalmRegistrars registrars) {
        PrettyBeachesConfig.initialize();

        ServerTickCallback.ServerLevelTick.AFTER.register(FloodingManager::onWorldTick);
        BlockCallback.Break.Before.EVENT.register(EventPhases.LOWEST, BreakBlockHandler::onBreakBlock);
        ItemCallback.Use.EVENT.register(BucketHandler::onItemUse);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

}
