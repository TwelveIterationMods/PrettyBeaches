package net.blay09.mods.prettybeaches;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ScheduledFloodingEntry {
    public BlockPos pos;
    public int depth;
    public int ticksExisted;

    public ScheduledFloodingEntry(BlockPos pos, int depth) {
        this.pos = pos;
        this.depth = depth;
    }
}
