package net.blay09.mods.prettybeaches;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class FloodingHandler {

    public class Entry {
        public World world;
        public BlockPos pos;
        public int depth;
        public int ticksExisted;

        public Entry(World world, BlockPos pos, int depth) {
            this.world = world;
            this.pos = pos;
            this.depth = depth;
        }
    }

    public static final int FLOOD_TIME = 10;
    private static final int MAX_DEPTH = 6;

    private List<Entry> scheduledFloods = new ArrayList<>();

    public void scheduleForFlooding(World world, BlockPos pos, int depth) {
        if (PrettyBeachesConfig.COMMON.animatedFlooding.get()) {
            scheduledFloods.add(new Entry(world, pos, depth));
        } else {
            populateWater(world, pos, depth);
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (int i = scheduledFloods.size() - 1; i >= 0; i--) {
                Entry entry = scheduledFloods.get(i);
                entry.ticksExisted++;
                if (entry.ticksExisted >= FLOOD_TIME) {
                    populateWater(entry.world, entry.pos, entry.depth);
                    scheduledFloods.remove(i);
                }
            }
        }
    }

    private void populateWater(World world, BlockPos pos, int depth) {
        FluidState sourceState = world.getFluidState(pos);
        if (sourceState.getBlockState().isAir(world, pos) || sourceState.getFluid() == Fluids.WATER || sourceState.getFluid() == Fluids.FLOWING_WATER) {
            world.setBlockState(pos, Blocks.WATER.getDefaultState(), 11);
            if (depth <= MAX_DEPTH && pos.getY() == world.getSeaLevel() - 1) {
                BlockPos.Mutable mutPos = new BlockPos.Mutable();
                for (Direction facing : Direction.Plane.HORIZONTAL) {
                    mutPos.setPos(pos).move(facing);
                    BlockState state = world.getBlockState(mutPos);
                    FluidState fluidState = world.getFluidState(mutPos);
                    int waterLevel = fluidState.getLevel();
                    if (state.isAir(world, pos) || (waterLevel > 0 && waterLevel < 8)) {
                        scheduleForFlooding(world, mutPos, depth + 1);
                        return;
                    }
                }
            }
        }
    }

}
