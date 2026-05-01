package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.*;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidSpellDatumType;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FunctionIota extends Iota {
    public static final Codec<FunctionIota> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IotaType.TYPED_CODEC.fieldOf("id").forGetter(f->f.arg),
                    IotaType.TYPED_CODEC.fieldOf("code").forGetter(f -> f.code),
                    IotaType.TYPED_CODEC.fieldOf("result").forGetter(f -> f.result)
            ).apply(instance, (id, code, result) -> {
                if (!(code instanceof ListIota)) {
                    code = new ListIota(List.of());
                }
                return new FunctionIota((TypeIota) id, (ListIota) code, (TypeIota) result);
            })
    );

    private final TypeIota arg;
    private final ListIota code;
    private final TypeIota result;

    public FunctionIota(TypeIota arg, ListIota code, TypeIota result) {
        super(() -> FunctionIotaType.INSTANCE);
        this.arg = arg;
        this.code = code;
        this.result = result;
    }

    public Iota getArg() { return arg; }
    public ListIota getCode() { return code; }
    public Iota getResult() { return result; }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        if (!(that instanceof FunctionIota other)) return false;
        return Iota.tolerates(this.arg, other.arg)
                && Iota.tolerates(this.code, other.code)
                && Iota.tolerates(this.result, other.result);
    }

    @Override
    public boolean executable() {
        return true;
    }

    @Override
    public @NotNull CastResult execute(CastingVM vm, ServerLevel world, SpellContinuation continuation) {
        return super.execute(vm, world, continuation);
        /*CastingImage image = vm.getImage();
        List<Iota> stack = image.getStack();
        if(stack.getLast().getClass().getSimpleName().equals(this.arg.classname)) {
            SpellContinuation cont= continuation.pushFrame(FrameFinishEval.INSTANCE);
            return new CastResult(
                    this,
                    cont.pushFrame(new FrameEvaluate(this.code.getList(),true)),
                    null,
                    List.of(),
                    ResolvedPatternType.EVALUATED,
                    HexEvalSounds.HERMES
            );
        }
        return new CastResult(
                this,
                SpellContinuation.Done.INSTANCE,
                null,
                List.of(
                        new OperatorSideEffect.DoMishap(
                                new MishapInvalidSpellDatumType(this.arg),
                                new Mishap.Context(new HexPattern(HexDir.WEST, List.of()), null)
                        )
                ),
                ResolvedPatternType.EVALUATED,
                HexEvalSounds.MISHAP
        );*/
    }

    @Override
    public Component display() {
        Component idDisplay = arg.display();
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
        return HashCodeBuilder.reflectionHashCode(this);
    }
}