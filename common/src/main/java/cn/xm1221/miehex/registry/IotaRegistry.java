package cn.xm1221.miehex.registry;

import at.petrak.hexcasting.common.lib.hex.HexContinuationTypes;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import cn.xm1221.miehex.MieHexMod;
import cn.xm1221.miehex.api.casting.frame.FrameCatch;
import cn.xm1221.miehex.iota.*;
//import cn.xm1221.miehex.iota.FunctionIotaType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class IotaRegistry {
    /**
     * Registers our iota types into {@link HexIotaTypes#REGISTRY}.
     * <p>
     * Kept separate from {@link #initContinuations()} because NeoForge fires its
     * {@code RegisterEvent} once per registry: registering everything from a single
     * callback would run this code again for every other registry and crash with a
     * duplicate-key error.
     */
    public static void initIotas() {
        Registry.register(
                HexIotaTypes.REGISTRY,
                ResourceLocation.tryBuild(MieHexMod.MOD_ID, "enchant"),
                EnchantIotaType.INSTANCE
        );
        Registry.register(
                HexIotaTypes.REGISTRY,
                ResourceLocation.tryBuild(MieHexMod.MOD_ID, "idea"),
                IdeaIotaType.INSTANCE

        );
        // 在此添加更多 Iota 类型
        Registry.register(
                HexIotaTypes.REGISTRY,
                ResourceLocation.fromNamespaceAndPath(MieHexMod.MOD_ID, "function"),
                FunctionIotaType.INSTANCE
        );

        Registry.register(
                HexIotaTypes.REGISTRY,
                ResourceLocation.fromNamespaceAndPath(MieHexMod.MOD_ID, "mishap"),
                MishapIotaType.INSTANCE
        );

        Registry.register(
                HexIotaTypes.REGISTRY,
                ResourceLocation.fromNamespaceAndPath(MieHexMod.MOD_ID,"type"),
                TypeIotaType.INSTANCE
        );
    }

    /**
     * Registers our continuation frame types into {@link HexContinuationTypes#REGISTRY}.
     * <p>
     * Register FrameCatch continuation type (required by hexcasting 0.12.0+ for network serialization).
     */
    public static void initContinuations() {
        Registry.register(
                HexContinuationTypes.REGISTRY,
                ResourceLocation.fromNamespaceAndPath(MieHexMod.MOD_ID, "catch"),
                FrameCatch.TYPE
        );
    }

    /**
     * Convenience method registering everything at once, for loaders that have no
     * per-registry registration event (Fabric).
     */
    public static void init() {
        initIotas();
        initContinuations();
    }
}
