package net.blay09.mods.prettybeaches;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PrettyBeaches.MOD_ID)
public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    public static Logger logger = LogManager.getLogger(MOD_ID);

    private final FloodingHandler floodingHandler = new FloodingHandler();

    public PrettyBeaches() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // TODO Awaiting Forge config COMMON fix
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PrettyBeachesConfig.commonSpec);
    }

    public void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(floodingHandler);
        MinecraftForge.EVENT_BUS.register(new HarvestBlockHandler(floodingHandler));
    }

}
