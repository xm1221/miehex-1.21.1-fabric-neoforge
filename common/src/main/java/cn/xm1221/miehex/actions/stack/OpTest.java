package cn.xm1221.miehex.actions.stack;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import cn.xm1221.miehex.iota.EnchantIota;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OpTest implements Action {
    @NotNull
    public static final OpTest INSTANCE = new OpTest();

    public OpTest() {
    }

    @Override
    public @NotNull OperationResult operate(@NotNull CastingEnvironment env, CastingImage img, SpellContinuation cont) {
        Iota iota = new EnchantIota("xm1221", (short) 2);
        List<Iota> stack = img.getStack();
        List<Iota> newStack = new ArrayList<>(stack);
        newStack.add(iota);
        CastingImage newImage = img.copy(
                newStack,
                img.getParenCount(),
                img.getParenthesized(),
                img.getEscapeNext(),
                img.getOpsConsumed() + 1,
                img.getUserData()
        );
        return new OperationResult(newImage,List.of(),cont, HexEvalSounds.NOTHING);
    };
}