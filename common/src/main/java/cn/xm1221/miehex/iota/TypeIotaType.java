package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class TypeIotaType extends IotaType<TypeIota> {

    public static final TypeIotaType INSTANCE = new TypeIotaType();
    private TypeIotaType() {}

    @Override
    public MapCodec<TypeIota> codec() {
        return TypeIota.CODEC.fieldOf("classname");
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, TypeIota> streamCodec() {
        return StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8,TypeIota::getClassname,
                TypeIota::new
        );
    }

    @Override
    public int color() {
        return 0X66CDAA;
    }
}
