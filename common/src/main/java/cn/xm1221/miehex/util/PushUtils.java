package cn.xm1221.miehex.util;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import cn.xm1221.miehex.iota.IdeaIota;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public final class PushUtils {
    public static final @NotNull  ListIota QUNIE = new ListIota(List.of(
            new PatternIota(HexPattern.fromAngles("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngles("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngles("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngles("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngles("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngles("qwaeawq", HexDir.NORTH_WEST)),
            new PatternIota(HexPattern.fromAngles("aaeaa", HexDir.NORTH_EAST)),
            new PatternIota(HexPattern.fromAngles("aaeaa", HexDir.NORTH_EAST)),
            new PatternIota(HexPattern.fromAngles("ddewedd", HexDir.SOUTH_EAST)),
            new PatternIota(HexPattern.fromAngles("aawdd", HexDir.NORTH_WEST)),
            new PatternIota(HexPattern.fromAngles("edqde", HexDir.SOUTH_EAST)),
            new PatternIota(HexPattern.fromAngles("qqq", HexDir.WEST)),
            new PatternIota(HexPattern.fromAngles("aadaa", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngles("deaqq", HexDir.SOUTH_EAST)),
            new PatternIota(HexPattern.fromAngles("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngles("waaw", HexDir.NORTH_EAST)),
            new PatternIota(HexPattern.fromAngles("eee", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngles("aadaa", HexDir.EAST)),
            new PatternIota(HexPattern.fromAngles("deaqq", HexDir.SOUTH_EAST))

    ));

    public static final @NotNull EntityIota SIMP = new EntityIota(UUID.randomUUID(), Component.literal("simple"));

    public static final @NotNull IdeaIota EMPTY_IDEA = new IdeaIota("EMPTY",0,0,0,0);


}

