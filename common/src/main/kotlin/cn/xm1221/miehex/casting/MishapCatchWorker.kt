package cn.xm1221.miehex.casting

import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import cn.xm1221.MieLib.worker.CastingVMCall
import cn.xm1221.MieLib.worker.CastingVMWorker
import cn.xm1221.miehex.api.casting.frame.FrameCatch
import net.minecraft.server.level.ServerLevel

/**
 * After worker for [CastingVM.executeInner]. MieLib owns the only wrap of that method;
 * this rewrites a mishap result when [FrameCatch] can catch it, and does not cancel.
 */
object MishapCatchWorker : CastingVMWorker(isAfter = true) {
    override fun execute(
        iota: Iota,
        world: ServerLevel,
        cont: SpellContinuation,
        call: CastingVMCall,
        vm: CastingVM,
    ): CastingVMCall {
        val current = call.result ?: return call
        val caught = FrameCatch.handleMishapCatch(iota, vm, world, cont, current)
        if (caught != null) call.setResult(caught)
        return call
    }
}
