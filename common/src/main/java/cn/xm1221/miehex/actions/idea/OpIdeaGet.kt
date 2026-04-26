package cn.xm1221.miehex.actions.idea

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getLivingEntityButNotArmorStand
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import cn.xm1221.miehex.iota.IdeaIota
import cn.xm1221.miehex.util.PushUtils
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
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
        val eIdea=PushUtils.EMPTY_IDEA
        if(idea == eIdea) {
            val id = env.world.registryAccess().registryOrThrow(Registries.ENTITY_TYPE).getKey(entity.type).toString()
            var maxHealth = 0.0
            var speed =0.0
            var atk =0.0
            var def = 0.0
            val attr  = entity.getAttribute(Attributes.MAX_HEALTH)
            if (attr != null) {
                 maxHealth = attr.baseValue
            }
            val attr1  = entity.getAttribute(Attributes.MOVEMENT_SPEED)
            if (attr1 != null) {
                 speed = attr1.baseValue
            }
            val attr2  = entity.getAttribute(Attributes.ATTACK_DAMAGE)
            if (attr2 != null) {
                atk = attr2.baseValue
            }
            val attr3 = entity.getAttribute(Attributes.ARMOR)
            if (attr3 != null) {
                def = attr3.baseValue
            }
            return listOf(IdeaIota(id,maxHealth,speed,atk,def))

        }
        throw MishapInvalidIota.of(idea,1,"class.empty_idea")
    }
}