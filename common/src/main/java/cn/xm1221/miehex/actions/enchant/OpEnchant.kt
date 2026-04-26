package cn.xm1221.miehex.actions.enchant

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getItemEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.misc.MediaConstants
import cn.xm1221.miehex.iota.EnchantIota
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentHelper

class OpEnchant : SpellAction {
    override val argc: Int
        get() = 2

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val enchantIota = args[1]
        if (enchantIota !is EnchantIota) {
            throw MishapInvalidIota.of(enchantIota,0,"class.enchant")
        }
        val entity = args.getItemEntity(env.world, 0, argc)
        val stack = entity.item

        val enchantId = enchantIota.id
        val level = enchantIota.level.toInt()

        // 正确获取 Holder<Enchantment>
        val registry = env.world.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
        val holder: Holder<Enchantment> = registry.getHolder(
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse(enchantId))
        ).orElseThrow {
            IllegalStateException("Unknown enchantment: $enchantId")
        }

        // 使用 EnchantmentHelper 添加附魔，避免手动操作 Object2IntMap
        /*EnchantmentHelper.updateEnchantments(stack) { ench ->
            ench.set(holder, level)
        }

        // 更新物品实体
        entity.item = stack*/

        val cost = ((level * level * level) + 5).toLong() * MediaConstants.DUST_UNIT

        return SpellAction.Result(
            object : RenderedSpell {
                override fun cast(env: CastingEnvironment) {
                    EnchantmentHelper.updateEnchantments(stack) { ench ->
                        ench.set(holder, level)
                    }

                    // 更新物品实体
                    entity.item = stack
                    // 可以留空，或播放额外效果
                }
            },
            cost,
            listOf(ParticleSpray.burst(entity.eyePosition, 2.0, 5)),
            1
        )
    }
}

