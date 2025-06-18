package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.forge.ForgeLoadContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PrettyBeaches.MOD_ID)
public class ForgePrettyBeaches {

    public ForgePrettyBeaches(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        Balm.initializeMod(PrettyBeaches.MOD_ID, loadContext, PrettyBeaches::initialize);
    }

}
