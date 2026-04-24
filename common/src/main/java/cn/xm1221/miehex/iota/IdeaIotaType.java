package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class IdeaIotaType extends IotaType<IdeaIota> {
    public static final IdeaIotaType INSTANCE = new IdeaIotaType();

    private IdeaIotaType() {}

    @Override
    public MapCodec<IdeaIota> codec() {
        return IdeaIota.CODEC.fieldOf("value");
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, IdeaIota> streamCodec() {
        return StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, IdeaIota::getEntityTypeId,
                ByteBufCodecs.DOUBLE, IdeaIota::getMaxHealth,
                ByteBufCodecs.DOUBLE, IdeaIota::getMovementSpeed,
                ByteBufCodecs.DOUBLE, IdeaIota::getAttackDamage,
                ByteBufCodecs.DOUBLE, IdeaIota::getArmor,
                IdeaIota::new
        );
    }

    @Override
    public int color() {
        return 0xC0C0C0;
    }
}