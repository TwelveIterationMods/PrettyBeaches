package net.blay09.mods.prettybeaches;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BucketHandler {

    private final FloodingHandler floodingHandler;

    public BucketHandler(FloodingHandler floodingHandler) {
        this.floodingHandler = floodingHandler;
    }

    @SubscribeEvent
    public void onBucketFilled(FillBucketEvent event) {
        if (event.getEmptyBucket().getItem() == Items.BUCKET && event.getPlayer() != null && !(event.getPlayer() instanceof FakePlayer)) {
            BlockPos.Mutable mutPos = new BlockPos.Mutable();
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                final BlockPos hitPos = new BlockPos(event.getTarget().getHitVec());
                mutPos.setPos(hitPos).move(facing);
                FluidState fluidState = event.getWorld().getFluidState(mutPos);
                if (fluidState.getFluid() == Fluids.WATER || fluidState.getFluid() == Fluids.FLOWING_WATER) {
                    event.getWorld().setBlockState(hitPos, Blocks.WATER.getDefaultState(), 11);
                    floodingHandler.scheduleForFlooding(event.getWorld(), hitPos, 0);
                    return;
                }
            }
        }
    }

}
