package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class FunctionIotaType extends IotaType<FunctionIota> {
    public static final FunctionIotaType INSTANCE = new FunctionIotaType();

    private FunctionIotaType() {}

    @Override
    public MapCodec<FunctionIota> codec() {
        return FunctionIota.CODEC.fieldOf("value");
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, FunctionIota> streamCodec() {
        return StreamCodec.composite(
                IotaType.TYPED_STREAM_CODEC, FunctionIota::getId,
                IotaType.TYPED_STREAM_CODEC, f -> f.getCode(),
                IotaType.TYPED_STREAM_CODEC, FunctionIota::getResult,
                (id, code, result) -> {
                    if (!(code instanceof ListIota)) {
                        code = new ListIota(List.of());
                    }
                    return new FunctionIota(id, (ListIota) code, result);
                }
        );
    }

    @Override
    public int color() {
        return 0xFFD700;
    }
}