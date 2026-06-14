package net.blay09.mods.prettybeaches.config;

import net.blay09.mods.balm.api.Balm;
import net.minecraft.resources.ResourceLocation;
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

        ResourceLocation resourceLocation = Balm.getRegistries().getKey(block);
        if (resourceLocation != null) {
            return affectedBlocks.contains(resourceLocation.toString());
        }

        return false;
    }

}
