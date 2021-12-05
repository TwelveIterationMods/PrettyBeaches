package net.blay09.mods.prettybeaches;

import net.blay09.mods.prettybeaches.config.PrettyBeachesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

public class FloodingManager {

    public static final int FLOOD_TIME = 10;
    private static final int MAX_DEPTH = 6;

    private static final List<ScheduledFloodingEntry> scheduledFloods = new ArrayList<>();

    public static void scheduleForFlooding(Level level, BlockPos pos, int depth) {
        if (PrettyBeachesConfig.getActive().animatedFlooding) {
            scheduledFloods.add(new ScheduledFloodingEntry(level, pos, depth));
        } else {
            populateWater(level, pos, depth);
        }
    }

    public static void onWorldTick(Level level) {
        for (int i = scheduledFloods.size() - 1; i >= 0; i--) {
            ScheduledFloodingEntry entry = scheduledFloods.get(i);
            entry.ticksExisted++;
            if (entry.ticksExisted >= FLOOD_TIME) {
                populateWater(entry.level, entry.pos, entry.depth);
                scheduledFloods.remove(i);
            }
        }
    }

    private static void populateWater(Level level, BlockPos pos, int depth) {
        FluidState sourceState = level.getFluidState(pos);
        if (sourceState.createLegacyBlock().isAir() || sourceState.getType() == Fluids.WATER || sourceState.getType() == Fluids.FLOWING_WATER) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 11);
            if (depth <= MAX_DEPTH && pos.getY() == level.getSeaLevel() - 1) {
                BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
                for (Direction facing : Direction.Plane.HORIZONTAL) {
                    mutPos.set(pos).move(facing);
                    BlockState state = level.getBlockState(mutPos);
                    FluidState fluidState = level.getFluidState(mutPos);
                    int waterLevel = fluidState.getAmount();
                    if (state.isAir() || (waterLevel > 0 && waterLevel < 8)) {
                        scheduleForFlooding(level, mutPos, depth + 1);
                        return;
                    }
                }
            }
        }
    }

}
