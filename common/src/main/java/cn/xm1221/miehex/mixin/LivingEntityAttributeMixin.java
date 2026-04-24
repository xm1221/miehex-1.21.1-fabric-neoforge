package cn.xm1221.miehex.mixin;


import at.petrak.hexcasting.common.lib.HexAttributes;
import cn.xm1221.miehex.registry.MieHexAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityAttributeMixin extends Entity {

    public LivingEntityAttributeMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract AttributeMap getAttributes();

    @Inject(method = "createLivingAttributes", at = @At("RETURN"))
    private static void hex$addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();
        builder.add(MieHexAttributes.MOB_MEDIA);
        builder.add(MieHexAttributes.MOB_AMBIT_RADIUS);
        builder.add(HexAttributes.MEDIA_CONSUMPTION_MODIFIER);
    }
}