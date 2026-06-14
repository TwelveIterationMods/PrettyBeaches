package net.blay09.mods.prettybeaches;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ScheduledFloodingEntry {
    public Level level;
    public BlockPos pos;
    public int depth;
    public int ticksExisted;

    public ScheduledFloodingEntry(Level level, BlockPos pos, int depth) {
        this.level = level;
        this.pos = pos;
        this.depth = depth;
    }
}
