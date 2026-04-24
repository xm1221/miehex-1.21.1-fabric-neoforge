package cn.xm1221.miehex.api;


import at.petrak.hexcasting.api.casting.iota.Iota;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.nbt.CompoundTag;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import org.jetbrains.annotations.NotNull;

public class KubeJSIotaNBTHelper {


    private final Iota deserialize;
    //private final @NotNull Tag serialize;
    //private final Component display;
    //private final int color;

    public KubeJSIotaNBTHelper(Iota iota,CompoundTag tag, ServerLevel level) {
        this.deserialize = (Iota) IotaType.TYPED_CODEC;
        //this.display = IotaType.getDisplay(tag);
        //this.color =IotaType.getColor(tag);

    }


    /*public Iota getDeserialize() {
        return deserialize;
    }

    public @NotNull Tag getSerialize() {
        return serialize;
    }

    public Component getDisplay() {
        return display;
    }

    public int getColor() {
        return color;
    }*/
    public Iota getDeserialize() {
        return deserialize;
    }
}