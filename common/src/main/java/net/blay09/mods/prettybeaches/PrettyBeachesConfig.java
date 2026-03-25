package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;
import net.blay09.mods.prettybeaches.tag.ModBlockTags;
import net.minecraft.world.level.block.state.BlockState;

@Config(PrettyBeaches.MOD_ID)
public class PrettyBeachesConfig {

    @Comment("By default, only blocks with the tag prettybeaches:preserve_pretty will cause adjusted water physics. Set to true to run Pretty Beaches on all blocks.")
    public boolean affectAllBlocks = false;

    @Comment("Whether buckets should be able to retrieve infinite water without destroying sources as well.")
    public boolean infiniteBucketWater = false;

    @Comment("Whether the flooding of adjacent air blocks should be animated or instant.")
    public boolean animatedFlooding = true;

    @Comment("Whether the Pretty Beaches behavior should also be enabled in creative mode.")
    public boolean enableInCreative = false;

    public static PrettyBeachesConfig getActive() {
        return Balm.config().getActiveConfig(PrettyBeachesConfig.class);
    }

    public static void initialize() {
        Balm.config().registerConfig(PrettyBeachesConfig.class);
    }

    public static boolean isBlockAffected(BlockState state) {
        if (getActive().affectAllBlocks) {
            return true;
        }

        return state.is(ModBlockTags.PRESERVE_PRETTY);
    }
}
