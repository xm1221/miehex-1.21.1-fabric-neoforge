package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class MishapIotaType extends IotaType<MishapIota>{
    public static final MishapIotaType INSTANCE = new  MishapIotaType();

    @Override
    public MapCodec<MishapIota> codec() {
        return MishapIota.CODEC.fieldOf("value");
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf,MishapIota> streamCodec() {
        return StreamCodec.composite(
                ByteBufCodecs.INT,MishapIota::getKey,
                ByteBufCodecs.STRING_UTF8, MishapIota::getErrormessageCode,
                MishapIota::create
        );
    }

    @Override
    public int color() {
        return 0;
    }

   private MishapIotaType() {}
}
