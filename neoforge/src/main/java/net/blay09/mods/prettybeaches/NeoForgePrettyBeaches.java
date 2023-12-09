package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.neoforged.fml.common.Mod;

@Mod(PrettyBeaches.MOD_ID)
public class NeoForgePrettyBeaches {

    public NeoForgePrettyBeaches() {
        Balm.initialize(PrettyBeaches.MOD_ID, PrettyBeaches::initialize);
    }

}
