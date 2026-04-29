package cn.xm1221.miehex.api.casting.mishap

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.pigment.FrozenPigment
import net.minecraft.network.chat.Component

class MishapThrow(val iota: Iota): Mishap() {


    override fun accentColor(
        ctx: CastingEnvironment,
        errorCtx: Context
    ): FrozenPigment {
        return FrozenPigment.DEFAULT.get()
    }

    override fun execute(
        env: CastingEnvironment,
        errorCtx: Context,
        stack: MutableList<Iota>
    ) {
        stack.removeLast()
    }

    override fun errorMessage(
        ctx: CastingEnvironment,
        errorCtx: Context
    ): Component? {
        return Component.translatable("miehex.tooltip.throw").append(this.iota.display())
    }
}