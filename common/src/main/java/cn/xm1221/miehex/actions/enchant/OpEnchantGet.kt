package cn.xm1221.miehex.actions.enchant

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getItemEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.common.lib.HexRegistries
import cn.xm1221.miehex.iota.EnchantIota
import net.minecraft.world.item.ItemStack
import kotlin.collections.plus

class OpEnchantGet() : ConstMediaAction {
    override val argc = 1

    override val mediaCost: Long
        get() = MediaConstants.DUST_UNIT

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val entity=args.getItemEntity(env.world,0,argc)
        val stack: ItemStack = entity.item;
        var list: List<Iota> = mutableListOf<Iota>();
        stack.enchantments.keySet().forEach { value ->list=list.plus(
            EnchantIota(
                value.registeredName,
                stack.enchantments.getLevel(value).toShort()
            )
        ) }
        val res = listOf(ListIota(list))
            return res
    }


}