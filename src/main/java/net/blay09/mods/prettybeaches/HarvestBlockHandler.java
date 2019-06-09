package net.blay09.mods.prettybeaches;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HarvestBlockHandler {

    private final FloodingHandler floodingHandler;

    public HarvestBlockHandler(FloodingHandler floodingHandler) {
        this.floodingHandler = floodingHandler;
    }

    @SubscribeEvent
    public void onHarvestBlock(BlockEvent.HarvestDropsEvent event) {
        if (PrettyBeachesConfig.isBlockAffected(event.getState().getBlock()) && event.getHarvester() != null && !(event.getHarvester() instanceof FakePlayer)) {
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                mutPos.setPos(event.getPos()).move(facing);
                IFluidState fluidState = event.getWorld().getFluidState(mutPos);
                if (fluidState.getFluid() == Fluids.WATER || fluidState.getFluid() == Fluids.FLOWING_WATER) {
                    event.getWorld().setBlockState(event.getPos(), Blocks.WATER.getDefaultState(), 11);
                    floodingHandler.scheduleForFlooding((World) event.getWorld(), event.getPos(), 0);
                    return;
                }
            }
        }
    }

}
