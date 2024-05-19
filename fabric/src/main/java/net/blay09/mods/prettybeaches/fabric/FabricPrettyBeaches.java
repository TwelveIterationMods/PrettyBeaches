package net.blay09.mods.prettybeaches.fabric;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.prettybeaches.PrettyBeaches;
import net.fabricmc.api.ModInitializer;

public class FabricPrettyBeaches implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(PrettyBeaches.MOD_ID, EmptyLoadContext.INSTANCE, PrettyBeaches::initialize);
    }
}
