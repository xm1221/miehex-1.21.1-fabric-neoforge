package cn.xm1221.miehex.util;

import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import cn.xm1221.miehex.iota.IdeaIota;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public final class PushUtils {
    public static final @NotNull  ListIota QUNIE = new ListIota(List.of(
            new PatternIota(HexPattern.fromAngleString("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngleString("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngleString("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngleString("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngleString("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngleString("qwaeawq", HexDir.NORTH_WEST)),
            new PatternIota(HexPattern.fromAngleString("aaeaa", HexDir.NORTH_EAST)),
            new PatternIota(HexPattern.fromAngleString("aaeaa", HexDir.NORTH_EAST)),
            new PatternIota(HexPattern.fromAngleString("ddewedd", HexDir.SOUTH_EAST)),
            new PatternIota(HexPattern.fromAngleString("aawdd", HexDir.NORTH_WEST)),
            new PatternIota(HexPattern.fromAngleString("edqde", HexDir.SOUTH_EAST)),
            new PatternIota(HexPattern.fromAngleString("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngleString("aadaa", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngleString("deaqq", HexDir.SOUTH_EAST)),
            new PatternIota(HexPattern.fromAngleString("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngleString("waaw", HexDir.NORTH_EAST)),
            new PatternIota(HexPattern.fromAngleString("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngleString("aadaa", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngleString("deaqq", HexDir.SOUTH_EAST))

    ));

    //public static final @NotNull EntityIota SIMP = new EntityIota(UUID.randomUUID(), Component.literal("simple"));

    public static final @NotNull IdeaIota EMPTY_IDEA = new IdeaIota("EMPTY",0,0,0,0);

    public static final @NotNull ListIota EVOLUTION = new ListIota(List.of(new PatternIota(HexPattern.fromAngleString("dadawaaw",HexDir.NORTH_EAST))));


}

