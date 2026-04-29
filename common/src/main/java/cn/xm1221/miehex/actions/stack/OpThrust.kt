package cn.xm1221.miehex.actions.stack

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getInt
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota

class OpThrust (val OP: Boolean): ConstMediaAction {

    override val argc: Int
        get() = 3

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): List<Iota> {
        if(OP){
            val list = args.getList(0, argc).toList()
            val idx1 = args.getInt(1, argc)
            val num = args.getInt(2, argc)
            if(idx1 > list.size){
                return listOf(ListIota(listOf()))
            }
            if(idx1+num > list.size){
                return listOf(ListIota(listOf()))
            }
            val newlist=list.subList(idx1, idx1+num)
            return listOf(ListIota(newlist))
        }
        else {
            val list = (args.getList(0, argc)).toMutableList()
            val idx1 = args.getInt(2, argc)
            val list2 = args.getList(1, argc)
            if(idx1+1 > list.size){
                list.addAll(list2)
                val newlist: List<Iota> = list.toList()
                return listOf(ListIota(newlist))
            }
            list.addAll(idx1+1, list2.toList())
            val newlist: List<Iota> = list.toList()
            return listOf(ListIota(newlist))
        }
    }

}