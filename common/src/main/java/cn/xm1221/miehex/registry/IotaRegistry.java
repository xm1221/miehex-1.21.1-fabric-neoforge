package cn.xm1221.miehex.registry;

import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import cn.xm1221.miehex.MieHexMod;
import cn.xm1221.miehex.iota.*;
//import cn.xm1221.miehex.iota.FunctionIotaType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class IotaRegistry {
    public static void init() {
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
}
