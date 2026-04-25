package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FunctionIota extends Iota {
    public static final Codec<FunctionIota> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IotaType.TYPED_CODEC.fieldOf("id").forGetter(f -> f.id),
                    IotaType.TYPED_CODEC.fieldOf("code").forGetter(f -> f.code),
                    IotaType.TYPED_CODEC.fieldOf("result").forGetter(f -> f.result)
            ).apply(instance, (id, code, result) -> {
                if (!(code instanceof ListIota)) {
                    code = new ListIota(List.of());
                }
                return new FunctionIota(id, (ListIota) code, result);
            })
    );

    private final Iota id;
    private final ListIota code;
    private final Iota result;

    public FunctionIota(Iota id, ListIota code, Iota result) {
        super(() -> FunctionIotaType.INSTANCE);
        this.id = id;
        this.code = code;
        this.result = result;
    }

    public Iota getId() { return id; }
    public ListIota getCode() { return code; }
    public Iota getResult() { return result; }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        if (!(that instanceof FunctionIota other)) return false;
        return Iota.tolerates(this.id, other.id)
                && Iota.tolerates(this.code, other.code)
                && Iota.tolerates(this.result, other.result);
    }

    @Override
    public boolean executable() {
        return true;
    }

    @Override
    public @NotNull CastResult execute(CastingVM vm, ServerLevel world, SpellContinuation continuation) {
        CastingImage image = vm.getImage();
        List<Iota> stack = image.getStack();
        stack.add(this);
        return new CastResult(this, continuation, null, List.of(), ResolvedPatternType.EVALUATED, HexEvalSounds.NOTHING);
    }

    @Override
    public Component display() {
        Component idDisplay = id.display();
        Component resultDisplay = result.display();
        return Component.literal("Function: ")
                .withStyle(ChatFormatting.GOLD)
                .append("(")
                .append(idDisplay)
                .append(")")
                .append(" → ")
                .append(resultDisplay);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result;
        return result;
    }
}