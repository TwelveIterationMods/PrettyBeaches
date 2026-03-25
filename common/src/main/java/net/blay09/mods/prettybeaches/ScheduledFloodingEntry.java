package net.blay09.mods.prettybeaches;

import net.minecraft.core.BlockPos;

public class ScheduledFloodingEntry {
    public final BlockPos pos;
    public final int depth;
    public int ticksExisted;

    public ScheduledFloodingEntry(BlockPos pos, int depth) {
        this.pos = pos;
        this.depth = depth;
    }
}
