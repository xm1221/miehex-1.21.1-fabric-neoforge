package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class EnchantIotaType extends IotaType<EnchantIota> {
    public static final EnchantIotaType INSTANCE = new EnchantIotaType();

    private EnchantIotaType() {}

    @Override
    public MapCodec<EnchantIota> codec() {
        return EnchantIota.CODEC.fieldOf("value");
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, EnchantIota> streamCodec() {
        return StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, EnchantIota::getId,
                ByteBufCodecs.SHORT, EnchantIota::getLevel,
                EnchantIota::new
        );
    }

    @Override
    public int color() {
        return 0x88ff88;
    }
}