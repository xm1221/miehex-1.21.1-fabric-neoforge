package cn.xm1221.miehex.fabric;

import net.fabricmc.api.ModInitializer;

import cn.xm1221.miehex.MieHexMod;

public final class MieHexModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        MieHexMod.init();
        //TODO:MAKE FABRIC VERSION
    }
}
