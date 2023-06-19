package net.blay09.mods.prettybeaches.config;

import com.google.common.collect.Lists;
import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;
import net.blay09.mods.balm.api.config.Config;
import net.blay09.mods.balm.api.config.ExpectedType;
import net.blay09.mods.prettybeaches.PrettyBeaches;

import java.util.List;

@Config(PrettyBeaches.MOD_ID)
public class PrettyBeachesConfigData implements BalmConfigData {

    @ExpectedType(String.class)
    @Comment("List of blocks that should be affected by the adjusted water physics. If you want to have all blocks affected, just include \"*\" in the list.")
    public List<String> affectedBlocks = Lists.newArrayList("minecraft:sand");

    @Comment("Whether buckets should be able to retrieve infinite water without destroying sources as well.")
    public boolean infiniteBucketWater = false;

    @Comment("Whether the flooding of adjacent air blocks should be animated or instant.")
    public boolean animatedFlooding = true;

}
