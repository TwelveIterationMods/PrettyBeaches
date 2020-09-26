package net.blay09.mods.prettybeaches;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class PrettyBeachesConfig {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> affectedBlocks;
        public final ForgeConfigSpec.BooleanValue animatedFlooding;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Configuration for Pretty Beaches").push("common");

            affectedBlocks = builder
                    .comment("List of blocks that should be affected by the adjusted water physics. If you want to have all blocks affected, just include \"*\" in the list.")
                    .translation("prettybeaches.config.affectedBlocks")
                    .defineList("affectedBlocks", Lists.newArrayList("minecraft:sand"), it -> it instanceof String);

            animatedFlooding = builder
                    .comment("Whether the flooding of adjacent air blocks should be animated or instant.")
                    .translation("prettybeaches.config.affectedBlocks")
                    .define("animatedFlooding", true);
        }
    }

    public static boolean isBlockAffected(Block block) {
        if (COMMON.affectedBlocks.get().contains("*")) {
            return true;
        }

        ResourceLocation resourceLocation = block.getRegistryName();
        if (resourceLocation != null) {
            return COMMON.affectedBlocks.get().contains(resourceLocation.toString());
        }

        return false;
    }

    static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
