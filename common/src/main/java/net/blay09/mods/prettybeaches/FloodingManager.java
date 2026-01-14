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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import java.util.WeakHashMap;

public class FloodingManager {

    public static final int FLOOD_TIME = 10;
    private static final int MAX_DEPTH = 6;

    private static final Map<Level, List<ScheduledFloodingEntry>> scheduledFloods = Collections.synchronizedMap(new WeakHashMap<>());

    public static void scheduleForFlooding(Level level, BlockPos pos, int depth) {
        if (PrettyBeachesConfig.getActive().animatedFlooding) {
            if (level instanceof ServerLevel serverLevel) {
                List<ScheduledFloodingEntry> floods = scheduledFloods.computeIfAbsent(serverLevel, k -> new ArrayList<>());
                floods.add(new ScheduledFloodingEntry(pos, depth));
            }
        } else {
            populateWater(level, pos, depth);
        }
    }

    public static void onWorldTick(Level level) {
        List<ScheduledFloodingEntry> floods = scheduledFloods.get(level);
        if (floods == null || floods.isEmpty()) {
            return;
        }
        for (int i = floods.size() - 1; i >= 0; i--) {
            ScheduledFloodingEntry entry = floods.get(i);
            entry.ticksExisted++;
            if (entry.ticksExisted >= FLOOD_TIME) {
                populateWater(level, entry.pos, entry.depth);
                floods.remove(i);
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
