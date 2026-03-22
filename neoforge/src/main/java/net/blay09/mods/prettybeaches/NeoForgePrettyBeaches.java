package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(PrettyBeaches.MOD_ID)
public class NeoForgePrettyBeaches {

    public NeoForgePrettyBeaches(ModContainer modContainer, IEventBus modEventBus) {
        final var context = new NeoForgeLoadContext(modContainer, modEventBus);
        Balm.initializeMod(PrettyBeaches.MOD_ID, context, PrettyBeaches::initialize);
    }

}
