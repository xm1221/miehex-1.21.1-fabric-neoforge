package cn.xm1221.miehex.actions.stack.mishapiota

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.env.StaffCastEnv
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import cn.xm1221.miehex.api.casting.MobCastEnv
import cn.xm1221.miehex.iota.MishapIota
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

class OpMakeMishap: SpellAction {
    override val argc: Int
        get() = 2

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val iota= args[0]
        val target = args.getEntity(env.world,1,argc)
        if(iota is MishapIota && target is Player) {
            val otherenv = StaffCastEnv(target as ServerPlayer?, InteractionHand.MAIN_HAND)
            return SpellAction.Result(
                object : RenderedSpell {
                    override fun cast(env: CastingEnvironment) {
                        CastingVM.empty(otherenv).performSideEffects(listOf(OperatorSideEffect.DoMishap(iota.value, Mishap.Context(
                            HexPattern.fromAngles("", HexDir.NORTH_EAST), Component.literal("")))))
                    }
                },
                cost = 20* MediaConstants.CRYSTAL_UNIT,
                particles = listOf()
            )
        }
        else if(iota is MishapIota && target is LivingEntity) {
            val otherenv = MobCastEnv(target,InteractionHand.MAIN_HAND)
            return SpellAction.Result(
                object : RenderedSpell {
                    override fun cast(env: CastingEnvironment) {
                        CastingVM.empty(otherenv).performSideEffects(listOf(OperatorSideEffect.DoMishap(iota.value, Mishap.Context(
                            HexPattern.fromAngles("", HexDir.NORTH_EAST), Component.literal("")))))
                    }
                },
                cost = 20* MediaConstants.CRYSTAL_UNIT,
                particles = listOf()
            )
        }
        throw MishapInvalidIota.of(iota,1,"mishap")
    }
}