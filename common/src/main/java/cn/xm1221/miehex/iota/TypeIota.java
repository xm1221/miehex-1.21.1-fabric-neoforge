package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TypeIota extends Iota {

    public final String classname;

    public static final TypeIotaType type = TypeIotaType.INSTANCE;

public static final Codec<TypeIota> CODEC = RecordCodecBuilder.create(
        typeIotaInstance ->
                typeIotaInstance.group(
                        Codec.STRING.fieldOf("classname").forGetter(TypeIota::getClassname)
                ).apply(typeIotaInstance,TypeIota::new)
);
    public TypeIota(String classname) {
        super(()->type);
        this.classname = classname;
    }

    public static TypeIota create(Class clazz) {
        return new TypeIota(clazz.getSimpleName());
    }

    public static TypeIota get(Class clazz) {
        return new TypeIota(clazz.getClass().getSimpleName());
    }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota iota) {
        if(iota instanceof TypeIota){
            return ((TypeIota) iota).classname.equals(this.classname);
        }
        return  false;
    }

    @Override
    public Component display() {
        return Component.literal(this.classname).withColor(0X66CDAA);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String getClassname() {
        return classname;
    }


}
