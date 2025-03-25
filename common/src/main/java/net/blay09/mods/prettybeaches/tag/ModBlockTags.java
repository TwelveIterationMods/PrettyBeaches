package net.blay09.mods.prettybeaches.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.blay09.mods.prettybeaches.PrettyBeaches.id;

public class ModBlockTags {
    public static final TagKey<Block> PRESERVE_PRETTY = TagKey.create(Registries.BLOCK, id("preserve_pretty"));
}
