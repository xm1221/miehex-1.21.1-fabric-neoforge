package cn.xm1221.miehex.registry;

import at.petrak.hexcasting.api.casting.math.HexDir;
import cn.xm1221.miehex.actions.enchant.OpAddEnchant;
import cn.xm1221.miehex.actions.enchant.OpEnchant;
import cn.xm1221.miehex.actions.enchant.OpEnchantGet;
import cn.xm1221.miehex.actions.stack.OpPush;
import cn.xm1221.miehex.api.ActionRegistryHelper;
import cn.xm1221.miehex.util.PushUtils;

public class ActionRegisry {
    public static void init(){
        //ActionRegistryHelper.register("test","adaw", HexDir.SOUTH_EAST, new OpTest());
        ActionRegistryHelper.register("quine","qqqqqeawqwqwqwqwqwwded", HexDir.EAST, new OpPush(PushUtils.QUNIE,0));
        ActionRegistryHelper.register("get_enchant","awaeqwawq",HexDir.NORTH_EAST,new OpEnchantGet());
        ActionRegistryHelper.register("enchant_add","qawwwwaqeeeaqwwqaee",HexDir.EAST, new OpAddEnchant());
        ActionRegistryHelper.register("enchant","dwdqewdwe",HexDir.NORTH_WEST,new OpEnchant());
    }
}
