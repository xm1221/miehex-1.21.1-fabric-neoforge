package cn.xm1221.miehex.actions.idea

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import cn.xm1221.miehex.MieHexMod.MOD_ID
import cn.xm1221.miehex.iota.IdeaIota
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.EntityTypeTags
import net.minecraft.tags.TagKey
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.phys.Vec3

class OpIdeaSummon: SpellAction {
    override val argc: Int
        get() = 2

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val pos=args.getBlockPos(0,argc)
        val idea = args.get(1)
        if (idea is IdeaIota) {
            val list = mutableListOf(idea.maxHealth,idea.movementSpeed,idea.attackDamage,idea.armor)
            val Etype = EntityType.byString(idea.entityTypeId).orElse(null) ?: throw MishapInvalidIota.of(
                idea,
                1,
                "class.bad_idea"
            )
            var entity : Entity? = Etype.create(env.world)
            if (entity != null ) {
                if(entity.type.canSummon() and !(entity.type.`is`(TagKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.tryBuild(MOD_ID,"can_not_summon"))))) {
                    entity=entity.type.spawn(env.world,pos, MobSpawnType.NATURAL)
                    if(entity is LivingEntity) {
                        val cost = MediaConstants.CRYSTAL_UNIT + MediaConstants.DUST_UNIT*(
                                list[0]*10+
                                list[1]*1000+
                                list[2]*100+
                                list[3]*50
                                )
                        val attr=entity.getAttribute(Attributes.MAX_HEALTH)
                        if (attr != null) {
                            attr.baseValue = list[0]
                        }
                        val attr1=entity.getAttribute(Attributes.MOVEMENT_SPEED)
                        if (attr1 != null) {
                            attr1.baseValue = list[1]
                        }
                        val attr2=entity.getAttribute(Attributes.ATTACK_DAMAGE)
                        if (attr2 != null) {
                            attr2.baseValue = list[2]
                        }
                        val attr3=entity.getAttribute(Attributes.ARMOR)
                        if (attr3 != null) {
                            attr3.baseValue = list[3]
                        }
                        return SpellAction.Result(
                            object : RenderedSpell{
                                override fun cast(env: CastingEnvironment) {
                                    entity.setPos(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
                                }
                            },
                            cost.toLong(),
                            listOf(ParticleSpray.burst(Vec3(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()), 2.0, 5)),
                            1
                        )
                    }
                    throw MishapInvalidIota.of(idea,1,"class.idea")
                }
                throw MishapInvalidIota.of(idea,1,"class.bad_idea")
            }
            throw MishapInvalidIota.of(idea,1,"class.idea")
        }
        throw MishapInvalidIota.of(idea,1,"class.idea")

    }
}