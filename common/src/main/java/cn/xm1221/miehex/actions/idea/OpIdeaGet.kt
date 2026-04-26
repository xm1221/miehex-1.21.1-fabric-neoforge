package cn.xm1221.miehex.actions.idea

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getLivingEntityButNotArmorStand
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import cn.xm1221.miehex.iota.IdeaIota
import cn.xm1221.miehex.util.PushUtils
import net.minecraft.world.entity.ai.attributes.Attributes

class OpIdeaGet : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): List<Iota> {
        val entity=args.getLivingEntityButNotArmorStand(env.world,1,argc)
        val idea = args[0]
        val e_idea=PushUtils.EMPTY_IDEA
        if(idea == e_idea) {
            val id = entity.type.toString()
            var maxHealth: Double = 0.0
            var speed =0.0
            var atk =0.0
            var def = 0.0
            val attr  = entity.getAttribute(Attributes.MAX_HEALTH)
            if (attr != null) {
                 maxHealth = attr.value
            }
            val attr1  = entity.getAttribute(Attributes.MOVEMENT_SPEED)
            if (attr1 != null) {
                 speed = attr1.value
            }
            val attr2  = entity.getAttribute(Attributes.ATTACK_DAMAGE)
            if (attr2 != null) {
                atk = attr2.value
            }
            val attr3 = entity.getAttribute(Attributes.ARMOR)
            if (attr3 != null) {
                def = attr3.value
            }
            return listOf(IdeaIota(id,maxHealth,speed,atk,def))

        }
        throw MishapInvalidIota.of(idea,1,"class.empty_idea")
    }
}