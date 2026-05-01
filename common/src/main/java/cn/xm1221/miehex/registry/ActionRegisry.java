package cn.xm1221.miehex.registry;

import at.petrak.hexcasting.api.casting.math.HexDir;
import cn.xm1221.miehex.actions.enchant.OpAddEnchant;
import cn.xm1221.miehex.actions.enchant.OpEnchant;
import cn.xm1221.miehex.actions.enchant.OpEnchantGet;
import cn.xm1221.miehex.actions.idea.OpIdeaGet;
import cn.xm1221.miehex.actions.idea.OpIdeaModify;
import cn.xm1221.miehex.actions.idea.OpIdeaSummon;
import cn.xm1221.miehex.actions.meta.OpBraveEval;
import cn.xm1221.miehex.actions.meta.OpCatch;
import cn.xm1221.miehex.actions.meta.OpEvolution;
import cn.xm1221.miehex.actions.meta.OpThrow;
import cn.xm1221.miehex.actions.stack.OpPush;
import cn.xm1221.miehex.actions.stack.OpThrust;
import cn.xm1221.miehex.actions.stack.mishapiota.OpMishapArgs;
import cn.xm1221.miehex.actions.stack.type.OpTypes;
import cn.xm1221.miehex.api.ActionRegistryHelper;
import cn.xm1221.miehex.util.PushUtils;

public class ActionRegisry {
    public static void init(){
        //ActionRegistryHelper.register("test","adaw", HexDir.SOUTH_EAST, new OpPush(TypeIota.create(TypeIota.class),0));
        ActionRegistryHelper.register("quine","qqqqqeawqwqwqwqwqwwded", HexDir.EAST, new OpPush(PushUtils.QUNIE,0));
        ActionRegistryHelper.register("get_enchant","awaeqwawq",HexDir.NORTH_EAST,new OpEnchantGet());
        ActionRegistryHelper.register("enchant_add","qawwwwaqeeeaqwwqaee",HexDir.EAST, new OpAddEnchant());
        ActionRegistryHelper.register("enchant","dwdqewdwe",HexDir.NORTH_WEST,new OpEnchant());
        ActionRegistryHelper.register("new_idea","qwqwqwqwqwq",HexDir.EAST,new OpPush(PushUtils.EMPTY_IDEA,0));
        ActionRegistryHelper.register("idea_get","qwwwdwewdwwwqwqwwwdwewdwwwqqqwe",HexDir.EAST,new OpIdeaGet());
        ActionRegistryHelper.register("idea_modify","wewedwaqdeeaqqwqw",HexDir.NORTH_WEST,new OpIdeaModify());
        ActionRegistryHelper.register("summon_idea_entity","wqwqawdeaqqdeewew",HexDir.NORTH_EAST,new OpIdeaSummon());
        ActionRegistryHelper.register("brave_eval","deaqqw",HexDir.SOUTH_EAST,new OpBraveEval());
        ActionRegistryHelper.register("easy_thrust","wawaqw", HexDir.SOUTH_EAST, new OpThrust(false));
        ActionRegistryHelper.register("easy_extract","wedwdw", HexDir.SOUTH_WEST, new OpThrust(true));
        ActionRegistryHelper.register("evolution","dadawaaw",HexDir.NORTH_EAST,new OpEvolution());
        ActionRegistryHelper.register("catch","deaq", HexDir.SOUTH_EAST, new OpCatch());
        ActionRegistryHelper.register("throw","edqa",HexDir.SOUTH_EAST,new OpThrow());
        ActionRegistryHelper.register("type_iota","wqawdew",HexDir.NORTH_EAST,new OpTypes().getIotatype());
        ActionRegistryHelper.register("mishap_type","waqedw",HexDir.NORTH_EAST,new OpTypes().getMishaptype());
        ActionRegistryHelper.register("mishap_args","deeeew",HexDir.NORTH_EAST,new OpMishapArgs().getInvaldiota());

    }

}
