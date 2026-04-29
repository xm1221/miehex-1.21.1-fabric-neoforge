package cn.xm1221.miehex.api.casting.mishap

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.pigment.FrozenPigment
import net.minecraft.network.chat.Component

class MishapBrokenMishap(val errormess: Component): Mishap() {
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
        val newstack = stack.subList(0, stack.size - 1)
        stack.clear()
        stack.addAll(newstack)
    }

    override fun errorMessage(
        ctx: CastingEnvironment,
        errorCtx: Context
    ): Component? {
        return Component.translatable("hexcasting.mishap.broken").append(":\"").append(errormess).append("\"")
    }
}