package cn.xm1221.miehex.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import cn.xm1221.miehex.MieHexMod;

public class MieHexAttributes {
    public static final Holder<Attribute> MOB_MEDIA = register(
            "mob_media",
            new RangedAttribute(
                    MieHexMod.MOD_ID + ".attributes.mob_media",
                    0.0, 0.0, Double.MAX_VALUE
            ).setSyncable(true)
    );

    public static final Holder<Attribute> MOB_AMBIT_RADIUS = register(
            "mob_ambit_radius",
            new RangedAttribute(
                    MieHexMod.MOD_ID + ".attributes.mob_ambit_radius",
                    16.0, 0.0, 64.0
            ).setSyncable(true)
    );

    private static Holder<Attribute> register(String name, Attribute attribute) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MieHexMod.MOD_ID, name);
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, id, attribute);
    }

    public static void register() {
        // 静态初始化块已经完成注册，此方法可留空或仅用于触发类加载
    }

}