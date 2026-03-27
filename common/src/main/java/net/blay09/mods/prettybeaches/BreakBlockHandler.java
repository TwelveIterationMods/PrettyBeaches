package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.Balm;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.Nullable;

public class BreakBlockHandler {
    public static boolean onBreakBlock(LevelAccessor level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Player player) {
        if (player == null || (player.getAbilities().instabuild && !PrettyBeachesConfig.getActive().enableInCreative)) {
            return true;
        }

        if (PrettyBeachesConfig.isBlockAffected(state) && !Balm.hooks().isFakePlayer(player)) {
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                mutPos.set(pos).move(facing);
                FluidState fluidState = level.getFluidState(mutPos);
                if (fluidState.getType() == Fluids.WATER || fluidState.getType() == Fluids.FLOWING_WATER) {
                    if (level instanceof Level actualLevel) {
                        Block.dropResources(state, actualLevel, pos, level.getBlockEntity(pos), player, player.getMainHandItem());
                    }
                    level.setBlock(pos, Blocks.WATER.defaultBlockState(), 11);
                    FloodingManager.scheduleForFlooding(level, pos, 0);
                    return true;
                }
            }
        }

        return true;
    }
}
