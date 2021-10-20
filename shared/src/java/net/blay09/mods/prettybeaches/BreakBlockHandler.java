package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.BreakBlockEvent;
import net.blay09.mods.prettybeaches.config.PrettyBeachesConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class BreakBlockHandler {
    public static void onBreakBlock(BreakBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Player player = event.getPlayer();
        if (player.getAbilities().instabuild) {
            return;
        }

        if (PrettyBeachesConfig.isBlockAffected(state.getBlock()) && !Balm.getHooks().isFakePlayer(player)) {
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                mutPos.set(pos).move(facing);
                FluidState fluidState = level.getFluidState(mutPos);
                if (fluidState.getType() == Fluids.WATER || fluidState.getType() == Fluids.FLOWING_WATER) {
                    Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, player.getMainHandItem());
                    level.setBlock(pos, Blocks.WATER.defaultBlockState(), 11);
                    FloodingManager.scheduleForFlooding(level, pos, 0);
                    return;
                }
            }
        }
    }
}
