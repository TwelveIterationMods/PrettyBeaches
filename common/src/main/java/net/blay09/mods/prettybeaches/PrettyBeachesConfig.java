package net.blay09.mods.prettybeaches;

import com.google.common.collect.Lists;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.config.reflection.Comment;
import net.blay09.mods.balm.api.config.reflection.Config;
import net.blay09.mods.balm.api.config.reflection.NestedType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.List;

@Config(PrettyBeaches.MOD_ID)
public class PrettyBeachesConfig {

    @NestedType(String.class)
    @Comment("List of blocks that should be affected by the adjusted water physics. If you want to have all blocks affected, just include \"*\" in the list.")
    public List<String> affectedBlocks = Lists.newArrayList("minecraft:sand");

    @Comment("Whether buckets should be able to retrieve infinite water without destroying sources as well.")
    public boolean infiniteBucketWater = false;

    @Comment("Whether the flooding of adjacent air blocks should be animated or instant.")
    public boolean animatedFlooding = true;

    @Comment("Whether the Pretty Beaches behaviour should also be enabled in creative mode.")
    public boolean enableInCreative = false;

    public static PrettyBeachesConfig getActive() {
        return Balm.getConfig().getActiveConfig(PrettyBeachesConfig.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(PrettyBeachesConfig.class);
    }

    public static boolean isBlockAffected(Block block) {
        List<String> affectedBlocks = getActive().affectedBlocks;
        if (affectedBlocks.contains("*")) {
            return true;
        }

        return affectedBlocks.contains(BuiltInRegistries.BLOCK.getKey(block).toString());
    }
}
