package cn.xm1221.miehex.actions.idea

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.getInt
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import cn.xm1221.miehex.iota.IdeaIota
import org.jetbrains.annotations.NotNull

class OpIdeaModify: ConstMediaAction {
    override val argc: Int
        get() = 3

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): List<Iota> {
        var idea = args[0]
        val idx = args.getInt(1)
        val value = args.getDouble(2)
        if (idea is IdeaIota) {
            val list = mutableListOf(idea.maxHealth,idea.movementSpeed,idea.attackDamage,idea.armor)
            list[idx-1] = value
            return listOf(
                IdeaIota(
                    idea.entityTypeId,
                    list[0],
                    list[1],
                    list[2],
                    list[3]
                )
            )
        }
        throw MishapInvalidIota.of(idea,2,"class.idea")

    }
}