package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.fabricmc.api.ModInitializer;

public class FabricPrettyBeaches implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(PrettyBeaches.MOD_ID, PrettyBeaches::initialize);
    }
}
