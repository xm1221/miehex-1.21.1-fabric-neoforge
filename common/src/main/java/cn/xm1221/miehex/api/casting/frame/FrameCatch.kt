package cn.xm1221.miehex.api.casting.frame

import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation.NotDone
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.utils.TreeList
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import cn.xm1221.miehex.api.casting.mishap.MishapThrow
import cn.xm1221.miehex.iota.MishapIota
import com.mojang.serialization.MapCodec
import net.minecraft.ChatFormatting
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.server.level.ServerLevel


class FrameCatch: ContinuationFrame {
    override val type: ContinuationFrame.Type<*>
        get() = TYPE

    override fun evaluate(
        continuation: SpellContinuation,
        level: ServerLevel,
        harness: CastingVM
    ): CastResult {
        return CastResult(
            NullIota(),
            continuation,
            null,
            listOf(),
            ResolvedPatternType.EVALUATED,
            HexEvalSounds.NOTHING,
        )
    }


    override fun breakDownwards(stack: TreeList<Iota>)= true to stack;

    override fun size(): Int {
        return 0
    }



    companion object {
        @JvmStatic
        fun handleMishapCatch(
            iota: Iota,
            harness: CastingVM,
            level: ServerLevel,
            cont: SpellContinuation,
            result: CastResult
        ): CastResult? {
          if (result.resolutionType.success) {
              return null
          }
            //if (!result.resolutionType.success)
            else  {
                val res = findPoint(cont)
              if (res == SpellContinuation.Done)return null;
              val stack: MutableList<Iota?> = harness.image.stack.toMutableList()
              val effect = result.sideEffects.find{it is OperatorSideEffect.DoMishap }
              val mishap: Mishap = (effect as OperatorSideEffect.DoMishap).mishap
              if (mishap is MishapThrow) {
                  if (stack.last()!=mishap.iota){
                      stack.add(mishap.iota)
                  }
              }
              else {
                  stack.add(
                      MishapIota(
                          mishap, mishap.errorMessageWithName(
                              harness.env, Mishap.Context(
                                  HexPattern.fromAngles("", HexDir.NORTH_EAST),
                                  Component.translatable("miehex.catch.mishap").withStyle(ChatFormatting.DARK_PURPLE)
                              )
                          )
                      )
                  )
              }
              val newimg = harness.image.copy(stack= TreeList.from(stack))
              return res?.let { CastResult(NullIota(),it, newimg,listOf(), ResolvedPatternType.EVALUATED,HexEvalSounds.MISHAP,) }
            }
        }

        @JvmStatic
        fun findPoint(cont:SpellContinuation): NotDone? {
            var newCont = cont
            while (newCont is NotDone) {
                if (newCont.frame is FrameCatch) {
                    return newCont
                }
                else
                    newCont = newCont.next
            }
            return null
        }
        @JvmField
        val TYPE: ContinuationFrame.Type<FrameCatch> = object : ContinuationFrame.Type<FrameCatch> {
            val CODEC = MapCodec.unit(FrameCatch())
            val STREAM_CODEC = StreamCodec.unit<RegistryFriendlyByteBuf, FrameCatch>(FrameCatch())

            override fun codec(): MapCodec<FrameCatch> =
                CODEC

            override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, FrameCatch> =
                STREAM_CODEC

        }
    }
}