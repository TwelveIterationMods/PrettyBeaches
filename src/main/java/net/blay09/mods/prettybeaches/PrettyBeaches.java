package net.blay09.mods.prettybeaches;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PrettyBeaches.MOD_ID)
public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    private final FloodingHandler floodingHandler = new FloodingHandler();

    public PrettyBeaches() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PrettyBeachesConfig.commonSpec);
    }

    public void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(floodingHandler);
        MinecraftForge.EVENT_BUS.register(new HarvestBlockHandler(floodingHandler));
    }

}
