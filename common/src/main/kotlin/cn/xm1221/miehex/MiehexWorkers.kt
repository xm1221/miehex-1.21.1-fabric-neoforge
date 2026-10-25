package cn.xm1221.miehex

import cn.xm1221.MieLib.registry.CastingVMHook
import cn.xm1221.miehex.casting.MishapCatchWorker

object MiehexWorkers {
    @JvmStatic
    fun init() {
        CastingVMHook.register(MieHexMod.id("mishap_catch"), MishapCatchWorker)
    }
}
