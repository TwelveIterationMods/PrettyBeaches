package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.EventBus;
import net.neoforged.fml.common.Mod;

@Mod(PrettyBeaches.MOD_ID)
public class NeoForgePrettyBeaches {

    public NeoForgePrettyBeaches(EventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modEventBus);
        Balm.initialize(PrettyBeaches.MOD_ID, context, PrettyBeaches::initialize);
    }

}
