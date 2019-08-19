package net.blay09.mods.prettybeaches;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BreakBlockHandler {

    private final FloodingHandler floodingHandler;

    public BreakBlockHandler(FloodingHandler floodingHandler) {
        this.floodingHandler = floodingHandler;
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.abilities.isCreativeMode) {
            return;
        }

        if (PrettyBeachesConfig.isBlockAffected(event.getState().getBlock()) && !(player instanceof FakePlayer)) {
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                mutPos.setPos(event.getPos()).move(facing);
                IFluidState fluidState = event.getWorld().getFluidState(mutPos);
                if (fluidState.getFluid() == Fluids.WATER || fluidState.getFluid() == Fluids.FLOWING_WATER) {
                    Block.spawnDrops(event.getState(), (World) event.getWorld(), event.getPos(), event.getWorld().getTileEntity(event.getPos()), event.getPlayer(), event.getPlayer().getHeldItemMainhand());
                    event.getWorld().setBlockState(event.getPos(), Blocks.WATER.getDefaultState(), 11);
                    floodingHandler.scheduleForFlooding((World) event.getWorld(), event.getPos(), 0);
                    return;
                }
            }
        }
    }

}
