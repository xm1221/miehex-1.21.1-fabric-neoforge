package cn.xm1221.miehex.actions.stack.type

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.getItemEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import cn.xm1221.miehex.api.casting.mishap.MishapBrokenMishap
import cn.xm1221.miehex.iota.MishapIota
import cn.xm1221.miehex.iota.TypeIota
import cn.xm1221.miehex.util.MapUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
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

    val blocktype: ConstMediaAction = object:ConstMediaAction {
        override val argc: Int
            get() = 1

        override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
        ): List<Iota> {
            val pos= args.getBlockPos(0)
            val block = BuiltInRegistries.BLOCK.getKey(env.world.getBlockState(pos).block).toString()
            return listOf(TypeIota(block))
        }
    }

    val itemtype: ConstMediaAction = object:ConstMediaAction {
        override val argc: Int
            get() = 1

        override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
        ): List<Iota> {
            val item = BuiltInRegistries.ITEM.getKey(args.getItemEntity(env.world,0,argc).item.item).toString()
            return listOf(TypeIota(item))
        }

    }

    val entitytype: ConstMediaAction = object:ConstMediaAction {
        override val argc: Int
            get() = 1

        override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
        ): List<Iota> {
            val entity = args.getEntity(env.world,0,argc)
            val type= BuiltInRegistries.ENTITY_TYPE.getKey(entity.type).toString()
            return listOf(TypeIota(type))

        }
    }

    val itemtype_hand: ConstMediaAction = object:ConstMediaAction {
        override val argc: Int
            get() = 0

        override fun execute(
            args: List<Iota>,
            env: CastingEnvironment
        ): List<Iota> {
            if(env.castingEntity != null){
                env.castingEntity?.let {
                    if(it.getItemInHand(env.otherHand)!=null)
                        return listOf(TypeIota(env.castingEntity?.getItemInHand(env.otherHand)?.let { it1 -> BuiltInRegistries.ITEM.getKey(it1.item) }
                            .toString()))
                }
            }
            return listOf(NullIota())
        }
    }




}