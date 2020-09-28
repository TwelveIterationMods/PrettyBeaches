package net.blay09.mods.prettybeaches;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Config;

@Config(modid = PrettyBeaches.MOD_ID)
public class PrettyBeachesConfig {

    @Config.Name("Affected Blocks")
    @Config.Comment("List of blocks that should be affected by the adjusted water physics. If you want to have all blocks affected, just include \"*\" in the list.")
    public static String[] affectedBlocks = {"minecraft:sand"};
    
    @Config.Name("Affected Biomes")
    @Config.Comment("List of biomes that should be affected by the adjusted water physics. If you want to have all biomes affected, just include \"*\" in the list.")
    public static String[] affectedBiomes = {
    		"minecraft:ocean",
    		"minecraft:deep_ocean",
    		"minecraft:frozen_ocean",
    		"minecraft:beach",
    		"minecraft:stone_beach",
    		"minecraft:cold_beach",
    		"minecraft:beaches",
    		"minecraft:river",
    		"minecraft:frozen_river",
    		"minecraft:mushroom_island_shore",
    		"minecraft:swampland"};

    @Config.Name("Infinite Bucket Water")
    @Config.Comment("Whether buckets should be able to retrieve infinite water without destroying sources as well.")
    public static boolean infiniteBucketWater = false;

    @Config.Name("Animated Flooding")
    @Config.Comment("Whether the flooding of adjacent air blocks should be animated or instant.")
    public static boolean animatedFlooding = true;

    private static List<Block> affectedBlocksList = new ArrayList<>();
    private static List<String> affectedBiomesList = new ArrayList<>();

    public static void onConfigReload() {
        affectedBlocksList.clear();
        affectedBiomesList.clear();

        for (String block : affectedBlocks) {
            affectedBlocksList.add(Block.getBlockFromName(block));
        }
        for (String biome : affectedBiomes) {
        	affectedBiomesList.add(biome);
        }
    }

    public static boolean isBlockAffected(Block block) {
        return ArrayUtils.contains(affectedBlocks, "*") || affectedBlocksList.contains(block);
    }
    
    public static boolean isBiomeAffected(String biome) {
    	return ArrayUtils.contains(affectedBiomes, "*") || affectedBiomesList.contains(biome);
    }
}