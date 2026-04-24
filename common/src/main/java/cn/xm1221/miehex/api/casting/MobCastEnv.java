// cn.xm1221.miehex.api.casting.MobCastEnv.java
package cn.xm1221.miehex.api.casting;

import at.petrak.hexcasting.api.casting.ParticleSpray;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.MishapEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.common.lib.HexDamageTypes;
import at.petrak.hexcasting.api.utils.MediaHelper;
import cn.xm1221.miehex.registry.MieHexAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class MobCastEnv extends CastingEnvironment {
    protected final LivingEntity caster;
    protected final InteractionHand castingHand;

    public MobCastEnv(LivingEntity caster, InteractionHand castingHand) {
        super((ServerLevel) caster.level());
        this.caster = caster;
        this.castingHand = castingHand;
    }

    @Override
    public LivingEntity getCastingEntity() {
        return caster;
    }

    @Override
    public MishapEnvironment getMishapEnvironment() {
        return new MobMishapEnv(caster);
    }

    @Override
    public InteractionHand getCastingHand() {
        return castingHand;
    }

    @Override
    public FrozenPigment getPigment() {
        return null; // 生物无染色剂，使用默认紫色
    }

    @Override
    public @Nullable FrozenPigment setPigment(@Nullable FrozenPigment pigment) {
        return null;
    }

    @Override
    public void produceParticles(ParticleSpray particles, FrozenPigment pigment) {
        particles.sprayParticles(world, pigment);
    }

    @Override
    public void printMessage(Component message) {
        // 生物无法阅读消息，可忽略
    }

    @Override
    public Vec3 mishapSprayPos() {
        return caster.position();
    }

    @Override
    protected long extractMediaEnvironment(long cost, boolean simulate) {
        // 从属性中获取当前媒质
        double media = caster.getAttributeValue((Holder<Attribute>) MieHexAttributes.MOB_MEDIA);
        long mediaLong = (long) media;

        if (simulate) {
            return Math.max(0, cost - mediaLong);
        }

        long extracted = Math.min(cost, mediaLong);
        double newMedia = media - extracted;
        caster.getAttribute((Holder<Attribute>) MieHexAttributes.MOB_MEDIA).setBaseValue(newMedia);
        return cost - extracted;
    }

    @Override
    protected boolean isVecInRangeEnvironment(Vec3 vec) {
        double radius = caster.getAttributeValue((Holder<Attribute>) MieHexAttributes.MOB_AMBIT_RADIUS);
        return vec.distanceToSqr(caster.position()) <= radius * radius;
    }

    @Override
    protected boolean hasEditPermissionsAtEnvironment(BlockPos pos) {
        // 生物通常不能编辑方块，除非是创造模式或特殊设定
        return true;
    }

    @Override
    protected List<ItemStack> getUsableStacks(StackDiscoveryMode mode) {
        // 生物没有物品栏，返回空列表
        return List.of();
    }

    @Override
    protected List<HeldItemInfo> getPrimaryStacks() {
        ItemStack main = caster.getItemInHand(castingHand);
        ItemStack off = caster.getItemInHand(InteractionHand.OFF_HAND);
        return List.of(new HeldItemInfo(main, castingHand), new HeldItemInfo(off, InteractionHand.OFF_HAND));
    }

    @Override
    public boolean replaceItem(Predicate<ItemStack> stackOk, ItemStack replaceWith, @Nullable InteractionHand hand) {
        if (hand != null && stackOk.test(caster.getItemInHand(hand))) {
            caster.setItemInHand(hand, replaceWith);
            return true;
        }
        return false;
    }
}