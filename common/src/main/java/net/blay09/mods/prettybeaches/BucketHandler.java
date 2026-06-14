package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.UseItemEvent;
import net.blay09.mods.prettybeaches.config.PrettyBeachesConfig;
import net.blay09.mods.prettybeaches.mixin.ItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class BucketHandler {

    public static void onItemUse(UseItemEvent event) {
        Level level = event.getLevel();
        Player player = event.getPlayer();
        ItemStack heldItem = player.getItemInHand(event.getHand());
        if (!PrettyBeachesConfig.getActive().infiniteBucketWater) {
            return;
        }

        if (heldItem.getItem() == Items.BUCKET && !Balm.getHooks().isFakePlayer(player)) {
            BlockHitResult blockHitResult = ItemAccessor.callGetPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            for (Direction facing : Direction.Plane.HORIZONTAL) {
                final BlockPos hitPos = BlockPos.containing(blockHitResult.getLocation());
                mutPos.set(hitPos).move(facing);
                FluidState fluidState = level.getFluidState(mutPos);
                if (fluidState.getType() == Fluids.WATER || fluidState.getType() == Fluids.FLOWING_WATER) {
                    level.setBlock(hitPos, Blocks.WATER.defaultBlockState(), 11);
                    FloodingManager.scheduleForFlooding(level, hitPos, 0);
                    return;
                }
            }
        }
    }

}
