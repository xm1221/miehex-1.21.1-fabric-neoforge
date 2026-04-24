package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import cn.xm1221.miehex.util.SgaUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class EnchantIota extends Iota {
    public static final Codec<EnchantIota> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(i -> i.id),
                    Codec.SHORT.fieldOf("lvl").forGetter(i -> i.level)
            ).apply(instance, EnchantIota::new)
    );

    private final String id;
    private final short level;

    public EnchantIota(String id, short level) {
        super(() -> EnchantIotaType.INSTANCE);
        this.id = id;
        this.level = level;
    }

    public String getId() { return id; }
    public short getLevel() { return level; }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        if (!(that instanceof EnchantIota other)) return false;
        return this.id.equals(other.id) && this.level == other.level;
    }

    @Override
    public Component display() {
        int colonIndex = id.lastIndexOf(':');
        String shortId = colonIndex >= 0 ? id.substring(colonIndex + 1) : id;
        String raw = (shortId + " level: " + level).toUpperCase();
        String sga = SgaUtils.toStandardGalactic(raw);
        return Component.literal(sga).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode() + level;
    }
}