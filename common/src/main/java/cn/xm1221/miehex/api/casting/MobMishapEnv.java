// cn.xm1221.miehex.api.casting.MobMishapEnv.java
package cn.xm1221.miehex.api.casting;

import at.petrak.hexcasting.api.casting.eval.MishapEnvironment;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;
import at.petrak.hexcasting.common.lib.HexDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class MobMishapEnv extends MishapEnvironment {
    protected final LivingEntity mobCaster; // 存储实际的生物施法者

    public MobMishapEnv(LivingEntity caster) {
        super((ServerLevel) caster.level(), null); // 没有玩家，传入 null
        this.mobCaster = caster;
    }

    @Override
    public void yeetHeldItemsTowards(Vec3 targetPos) {
        Vec3 pos = null;
        if (caster != null) {
            pos = caster.position();
        }
        var delta = targetPos.subtract(pos).normalize().scale(0.5);
        for (var hand : InteractionHand.values()) {
            ItemStack stack = null;
            if (caster != null) {
                stack = caster.getItemInHand(hand);
            }
            if (caster != null) {
                caster.setItemInHand(hand, ItemStack.EMPTY);
            }
            yeetItem(stack, pos, delta);
        }
    }

    @Override
    public void dropHeldItems() {
        Vec3 delta = null;
        if (caster != null) {
            delta = caster.getLookAngle();
        }
        if (caster != null) {
            yeetHeldItemsTowards(caster.position().add(delta));
        }
    }

    @Override
    public void damage(float healthProportion) {
        if (caster != null) {
            Mishap.trulyHurt(caster, caster.damageSources().starve(), caster.getMaxHealth() * healthProportion);
        }
    }

    @Override
    public void drown() {
        if (caster != null && caster.getAirSupply() < 200) {
            caster.hurt(caster.damageSources().drown(), 2f);
        }
        if (caster != null) {
            caster.setAirSupply(0);
        }
    }

    @Override
    public void removeXp(int amount) {
        // 生物无经验值，忽略
    }

    @Override
    public void blind(int ticks) {
        // 可添加失明效果，但生物不需要
    }

    @Override
    public void nauseate(int i) {

    }
}