package cn.xm1221.miehex.registry;

import at.petrak.hexcasting.api.casting.math.HexDir;
import cn.xm1221.miehex.actions.stack.OpTest;
import cn.xm1221.miehex.api.ActionRegistryHelper;

public class ActionRegisry {
    public static void init(){
        ActionRegistryHelper.register("test","adaw", HexDir.SOUTH_EAST, new OpTest());
    }
}
