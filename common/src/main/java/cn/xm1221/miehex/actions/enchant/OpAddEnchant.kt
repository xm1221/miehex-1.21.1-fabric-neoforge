package cn.xm1221.miehex.actions.enchant

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import cn.xm1221.miehex.iota.EnchantIota
import net.minecraft.world.item.enchantment.Enchantment

class OpAddEnchant() : ConstMediaAction {
    override val argc = 2

    override val mediaCost: Long
        get() = MediaConstants.DUST_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val ench1 = args.get(0)
        val ench2 = args.get(1)
        if (ench1 is EnchantIota && ench2 is EnchantIota) {
            if(ench1.id==ench2.id) {
                val lvl: Int = ench2.level+ench1.level
                val iota = EnchantIota(ench2.id,lvl.toShort())
                return listOf(iota)
            }
            throw MishapInvalidIota.of(ench1,0,"class.same_enchant")

        }
        throw MishapInvalidIota.of(ench1,0,"class.same_enchant")
    }


}