package net.blay09.mods.prettybeaches;

import net.blay09.mods.balm.api.Balm;
import net.minecraftforge.fml.common.Mod;

@Mod(PrettyBeaches.MOD_ID)
public class ForgePrettyBeaches {

    public ForgePrettyBeaches() {
        Balm.initialize(PrettyBeaches.MOD_ID, PrettyBeaches::initialize);
    }

}
