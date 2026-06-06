package cn.xm1221.miehex.actions.stack.mishapiota

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.casting.mishaps.MishapBadEntity
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.casting.mishaps.MishapUnescapedValue
import cn.xm1221.miehex.iota.MishapIota
import cn.xm1221.miehex.iota.TypeIota
import cn.xm1221.miehex.util.MapUtil
import net.minecraft.world.phys.Vec3

class OpMishapArgs() {
    val invaldiota: ConstMediaAction = object : ConstMediaAction {
        override val argc: Int
            get() = 1

        override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
        ): List<Iota> {
            if (args[0] is MishapIota) {
                val iota = args[0] as MishapIota
                val mishap = MapUtil.MISHAPS.get(iota.key)
                if (mishap != null) {
                    if (mishap is MishapInvalidIota) {
                        return listOf(mishap.perpetrator)
                    }
                    if (mishap is MishapInvalidOperatorArgs){
                        return mishap.perpetrators
                    }
                    if(mishap is MishapUnescapedValue){
                        return listOf(mishap.perpetrator)
                    }
                    if (mishap is MishapNotEnoughArgs){
                        return listOf(DoubleIota(mishap.expected.toDouble()))
                    }
                    if (mishap is MishapBadEntity){
                        return listOf(EntityIota(mishap.entity))
                    }
                    if (mishap is MishapBadBlock){
                        return listOf(Vec3Iota(Vec3(mishap.pos.x.toDouble(), mishap.pos.y.toDouble(),
                            mishap.pos.z.toDouble()
                        )))
                    }
                    return listOf(NullIota())

                }

            }
            throw MishapInvalidIota.ofType(args[0],1,"mishap")
        }

    }

}

