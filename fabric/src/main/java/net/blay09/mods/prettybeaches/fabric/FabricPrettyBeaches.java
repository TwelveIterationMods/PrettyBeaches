package net.blay09.mods.prettybeaches.fabric;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.blay09.mods.prettybeaches.PrettyBeaches;
import net.fabricmc.api.ModInitializer;

public class FabricPrettyBeaches implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(PrettyBeaches.MOD_ID, FabricLoadContext.INSTANCE, PrettyBeaches::initialize);
    }
}
