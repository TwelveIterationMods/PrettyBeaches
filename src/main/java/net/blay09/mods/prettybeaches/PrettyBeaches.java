package net.blay09.mods.prettybeaches;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = PrettyBeaches.MOD_ID, name = "Pretty Beaches", acceptedMinecraftVersions = "[1.12]")
public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";
    private static final int MAX_DEPTH = 2;

    @Mod.Instance(MOD_ID)
    public static PrettyBeaches instance;

    public static Logger logger = LogManager.getLogger(MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public void onHarvestBlock(BlockEvent.HarvestDropsEvent event) {
        if (event.getState().getBlock() == Blocks.SAND && event.getHarvester() != null && !(event.getHarvester() instanceof FakePlayer)) {
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                IBlockState state = event.getWorld().getBlockState(event.getPos().offset(facing));
                if (state.getMaterial() == Material.WATER) {
                    populateWater(event.getWorld(), event.getPos(), 0);
                    return;
                }
            }
        }
    }

    private void populateWater(World world, BlockPos pos, int depth) {
        world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
        if (depth <= MAX_DEPTH) {
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                BlockPos other = pos.offset(facing);
                IBlockState state = world.getBlockState(other);
                if (state.getBlock() == Blocks.AIR) {
                    populateWater(world, other, depth + 1);
                    return;
                }
            }
        }
    }

}
