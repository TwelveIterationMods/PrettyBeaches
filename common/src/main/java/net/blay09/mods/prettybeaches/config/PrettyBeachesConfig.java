package net.blay09.mods.prettybeaches.config;

import net.blay09.mods.balm.api.Balm;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PrettyBeachesConfig {

    public static PrettyBeachesConfigData getActive() {
        return Balm.getConfig().getActive(PrettyBeachesConfigData.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(PrettyBeachesConfigData.class, null);
    }

    public static boolean isBlockAffected(Block block) {
        List<String> affectedBlocks = getActive().affectedBlocks;
        if (affectedBlocks.contains("*")) {
            return true;
        }

        return affectedBlocks.contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }

}
