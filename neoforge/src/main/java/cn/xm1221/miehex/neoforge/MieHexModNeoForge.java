package cn.xm1221.miehex.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import cn.xm1221.miehex.MieHexMod;
import net.neoforged.neoforge.registries.RegisterEvent;


@Mod(MieHexMod.MOD_ID)
public final class MieHexModNeoForge {
    public  MieHexModNeoForge(ModContainer modContainer) {
        // Run our common setup.
        var  modBus = modContainer.getEventBus();
        modBus.addListener((RegisterEvent event) -> {
            MieHexMod.init();
                });


};
}
