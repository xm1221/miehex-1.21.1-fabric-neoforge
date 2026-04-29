package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.eval.CastResult;
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType;
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect;
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds;
import cn.xm1221.miehex.api.casting.mishap.MishapBrokenMishap;
import cn.xm1221.miehex.util.MapUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class MishapIota extends Iota {
    private final Mishap value;

    private final Component errormessage;

    public static final Codec<MishapIota> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.INT.fieldOf("key").forGetter(mishapIota -> mishapIota.value.hashCode()),
                Codec.STRING.fieldOf("message").forGetter(mishapIota -> Component.Serializer.toJson(mishapIota.errormessage,RegistryAccess.EMPTY))
        ).apply(instance,MishapIota::create)
    );


    public static MishapIota create(int key,String errormessagecode) {
        Component mess = Component.Serializer.fromJson(errormessagecode,RegistryAccess.EMPTY);
        if(mess==null){
            mess = Component.literal("");
        }
        if(MapUtil.MISHAPS.get(key)==null){

            return new MishapIota(new MishapBrokenMishap(mess),Component.Serializer.fromJson(errormessagecode,RegistryAccess.EMPTY));
        }
        return new MishapIota(MapUtil.MISHAPS.get(key),Component.Serializer.fromJson(errormessagecode,RegistryAccess.EMPTY));
    }
    public  MishapIota(Mishap mishap,Component errormessages) {
        super(() -> MishapIotaType.INSTANCE);
        value = mishap;
        MapUtil.MISHAPS.put(value.hashCode(),value);
        errormessage = errormessages;

    }
    @Override
    public boolean isTruthy() {
        return false;
    }

    @Override
    protected boolean toleratesOther(Iota iota) {
        if(iota instanceof MishapIota){
            if(this.getKey()==((MishapIota) iota).getKey()){
                return true;
            }
        }
        return false;
    }

    @Override
    public Component display() {

        if(this.value instanceof MishapBrokenMishap){
            return Component.literal("Mishap@Broken")
                    .withStyle(ChatFormatting.BOLD)
                    .withStyle(ChatFormatting.RED)
                    .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.errormessage)));

        }
        return Component.literal("Mishap@").append(String.valueOf(this.value.hashCode()))
                .withStyle(ChatFormatting.BOLD)
                .withStyle(ChatFormatting.RED)
                .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.errormessage)));
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public Mishap getValue() {
        return value;
    }

    @Override
    public @NotNull CastResult execute(CastingVM vm, ServerLevel world, SpellContinuation continuation) {
        return new CastResult(
                this,
                continuation,
                null,  // Should never matter
                List.of(
                        new OperatorSideEffect.DoMishap(
                                this.getValue(),
                                new Mishap.Context(new HexPattern(HexDir.WEST, List.of()), null)
                        )
                ),
                ResolvedPatternType.ERRORED,
                HexEvalSounds.MISHAP);
    }

    @Override
    public boolean executable() {
        return true;
    }

    public int getKey(){
        return value.hashCode();
    }

    public Component getErrormessage() {
        return errormessage;
    }

    public String getErrormessageCode() {
        return Component.Serializer.toJson(this.errormessage,RegistryAccess.EMPTY);
    }
}
