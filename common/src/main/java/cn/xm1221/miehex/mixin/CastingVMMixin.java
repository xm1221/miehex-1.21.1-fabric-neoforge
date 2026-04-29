package cn.xm1221.miehex.mixin;

import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;

import cn.xm1221.miehex.api.casting.frame.FrameCatch;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CastingVM.class)
public class CastingVMMixin {
    @WrapMethod(method = "executeInner")
    private CastResult wrapExecuteInner(
            Iota iota,
            ServerLevel world,
            SpellContinuation continuation,
            Operation<CastResult> original
    ) {
        CastResult result = original.call(iota, world, continuation);
        // 尝试捕获事故
        CastResult caught = FrameCatch.handleMishapCatch(
                iota, (CastingVM) (Object) this, world, continuation, result
        );
        return caught != null ? caught : result;
    }
}