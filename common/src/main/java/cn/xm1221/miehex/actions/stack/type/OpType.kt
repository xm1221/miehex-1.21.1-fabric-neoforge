package cn.xm1221.miehex.actions.stack.type

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import cn.xm1221.miehex.api.casting.mishap.MishapBrokenMishap
import cn.xm1221.miehex.iota.MishapIota
import cn.xm1221.miehex.iota.TypeIota
import cn.xm1221.miehex.util.MapUtil
import net.minecraft.network.chat.Component
import kotlin.jvm.javaClass

class OpTypes(){

    val iotatype: ConstMediaAction = object:ConstMediaAction {
    override val argc: Int
        get() = 1
    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): List<Iota> {
        return listOf(TypeIota.create(args[0].javaClass))
    }
}
    val mishaptype: ConstMediaAction = object:ConstMediaAction {
        override val argc: Int
            get() = 1

        override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
        ): List<Iota> {
            if (args[0] is MishapIota) {
                val iota = args[0] as MishapIota
                if (MapUtil.MISHAPS.get(iota.key) != null) {
                    return listOf(TypeIota.create(MapUtil.MISHAPS.get(iota.key)?.javaClass))
                }
                return listOf(TypeIota.create(MishapBrokenMishap(
                    Component.literal(" ")
                ).javaClass))

            }
            throw MishapInvalidIota.ofType(args[0],0,"mishap")
        }

    }

    /*val blocktype: ConstMediaAction = object:ConstMediaAction {
        override val argc: Int
        get() = 1

        override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
           val pos = args.getBlockPos(0,argc)
            val blockid = env.world.getBlockState(pos).block.descriptionId
            return listOf(TypeIota(blockid))
        }
    }*/



}