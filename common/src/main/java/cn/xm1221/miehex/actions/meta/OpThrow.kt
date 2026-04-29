package cn.xm1221.miehex.actions.meta

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import cn.xm1221.miehex.api.casting.mishap.MishapThrow


class OpThrow: Action{

    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        if(image.stack.isEmpty()){
            throw MishapNotEnoughArgs(1,image.stack.size)
        }
        val iota = image.stack[image.stack.size-1]
        throw MishapThrow(iota)
    }

}

