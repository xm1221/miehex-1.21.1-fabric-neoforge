package cn.xm1221.miehex.actions.stack


import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota

class OpPush(val IOTA: Iota,val MEDIA:Long) : ConstMediaAction{
    override val argc = 0
    override val mediaCost: Long
        get() = MEDIA
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return listOf(IOTA)
    }
}