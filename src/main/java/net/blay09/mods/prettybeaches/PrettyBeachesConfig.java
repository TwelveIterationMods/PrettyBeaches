package net.blay09.mods.prettybeaches;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Config;
import scala.actors.threadpool.Arrays;

@Config(modid = PrettyBeaches.MOD_ID)
public class PrettyBeachesConfig {

    @Config.Name("Affected Blocks")
    @Config.Comment("List of blocks that should be affected by the adjusted water physics. If you want to include all the blocks, just put a '*' without the quotes into the list.")
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
        return Arrays.asList(affectedBlocks).contains("*") || affectedBlocksList.contains(block);
    }
}