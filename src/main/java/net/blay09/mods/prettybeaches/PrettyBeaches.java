package net.blay09.mods.prettybeaches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = PrettyBeaches.MOD_ID, name = "Pretty Beaches", acceptedMinecraftVersions = "[1.12]", acceptableRemoteVersions = "*")
public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    @Mod.Instance(MOD_ID)
    public static PrettyBeaches instance;

    public static Logger logger = LogManager.getLogger(MOD_ID);
    private final FloodingHandler floodingHandler = new FloodingHandler();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(floodingHandler);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PrettyBeachesConfig.onConfigReload();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MOD_ID)) {
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            PrettyBeachesConfig.onConfigReload();
        }
    }

    @SubscribeEvent
    public void onHarvestBlock(BlockEvent.HarvestDropsEvent event) {
        if (PrettyBeachesConfig.isBlockAffected(event.getState().getBlock()) && event.getHarvester() != null && !(event.getHarvester() instanceof FakePlayer)) {
            BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos();
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
                mutPos.setPos(event.getPos()).move(facing);
                IBlockState state = event.getWorld().getBlockState(mutPos);
                int waterLevel = (state.getBlock() == Blocks.FLOWING_WATER) ? state.getValue(BlockLiquid.LEVEL) : -1;
                if (state.getBlock() == Blocks.WATER || waterLevel == 0) {
                    event.getWorld().setBlockState(event.getPos(), Blocks.FLOWING_WATER.getDefaultState(), 11);
                    floodingHandler.scheduleForFlooding(event.getWorld(), event.getPos(), 0);
                    return;
                }
            }
        }
    }
}