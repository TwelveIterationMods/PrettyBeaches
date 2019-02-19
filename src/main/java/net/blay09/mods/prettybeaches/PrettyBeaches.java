package net.blay09.mods.prettybeaches;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(PrettyBeaches.MOD_ID)
public class PrettyBeaches {

    public static final String MOD_ID = "prettybeaches";

    public PrettyBeaches() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PrettyBeachesConfig.commonSpec);

        FloodingHandler floodingHandler = new FloodingHandler();
        MinecraftForge.EVENT_BUS.register(floodingHandler);
        MinecraftForge.EVENT_BUS.register(new HarvestBlockHandler(floodingHandler));
    }

}
