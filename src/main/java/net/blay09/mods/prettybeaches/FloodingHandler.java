package net.blay09.mods.prettybeaches;

import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
        IBlockState sourceState = world.getBlockState(pos);
        if (sourceState.getBlock() == Blocks.AIR || sourceState.getBlock() == Blocks.WATER) {
            world.setBlockState(pos, Blocks.WATER.getDefaultState(), 11);
            if (depth <= MAX_DEPTH && pos.getY() == world.getSeaLevel() - 1) {
                BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
                for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                    mutPos.setPos(pos).move(facing);
                    IBlockState state = world.getBlockState(mutPos);
                    IFluidState fluidState = world.getFluidState(mutPos);
                    int waterLevel = fluidState.getLevel();
                    if (state.getBlock() == Blocks.AIR || (waterLevel > 0 && waterLevel < 11)) {
                        scheduleForFlooding(world, mutPos, depth + 1);
                        return;
                    }
                }
            }
        }
    }

}
