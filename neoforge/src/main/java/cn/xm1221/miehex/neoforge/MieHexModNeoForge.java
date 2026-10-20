package cn.xm1221.miehex.neoforge;

import at.petrak.hexcasting.common.lib.hex.HexActions;
import at.petrak.hexcasting.common.lib.hex.HexContinuationTypes;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import cn.xm1221.miehex.MieHexMod;
import cn.xm1221.miehex.registry.ActionRegisry;
import cn.xm1221.miehex.registry.IotaRegistry;
import net.neoforged.neoforge.registries.RegisterEvent;


@Mod(MieHexMod.MOD_ID)
public final class MieHexModNeoForge {
    public  MieHexModNeoForge(ModContainer modContainer) {
        // Run our common setup.
        var  modBus = modContainer.getEventBus();
        // NeoForge fires RegisterEvent once per registry, so only react to the
        // registries we actually populate. Calling MieHexMod.init() here would
        // re-register our iotas for every registry and crash on duplicate keys.
        modBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(HexIotaTypes.REGISTRY.key())) {
                IotaRegistry.initIotas();
            }
            if (event.getRegistryKey().equals(HexContinuationTypes.REGISTRY.key())) {
                IotaRegistry.initContinuations();
            }
            if (event.getRegistryKey().equals(HexActions.REGISTRY.key())) {
                ActionRegisry.init();
            }
        });


};
}
