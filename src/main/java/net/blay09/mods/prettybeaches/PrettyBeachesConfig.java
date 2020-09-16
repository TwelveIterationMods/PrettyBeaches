package net.blay09.mods.prettybeaches;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Config;

@Config(modid = PrettyBeaches.MOD_ID)
public class PrettyBeachesConfig {

    @Config.Name("Affected Blocks")
    @Config.Comment("List of blocks that should be affected by the adjusted water physics.")
    public static String[] affectedBlocks = {"minecraft:sand"};

    @Config.Name("Animated Flooding")
    @Config.Comment("Whether the flooding of adjacent air blocks should be animated or instant.")
    public static boolean animatedFlooding = true;

    private static List<Block> affectedBlocksList = new ArrayList<>();

    public static void onConfigReload() {
        affectedBlocksList.clear();

        for (String block : affectedBlocks) {
            affectedBlocksList.add(Block.getBlockFromName(block));
        }
    }

    public static boolean isBlockAffected(Block block) {
        return affectedBlocksList.contains(block);
    }
}