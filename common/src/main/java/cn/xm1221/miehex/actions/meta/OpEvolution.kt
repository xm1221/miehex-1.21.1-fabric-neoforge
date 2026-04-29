package cn.xm1221.miehex.actions.meta

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.FrameForEach
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

class OpEvolution: Action {
    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.isEmpty())
            throw MishapNotEnoughArgs(1, stack.size)

        val instrs = stack.getList(stack.lastIndex, stack.size)
        stack.removeLastOrNull()
        val frame = FrameForEach(instrs, instrs, listOf(), mutableListOf())
        val image2 = image.withUsedOp().copy(stack = stack)
         val newCont = continuation.pushFrame(frame)
        return OperationResult(image2, listOf(), newCont, HexEvalSounds.THOTH)
    }
}