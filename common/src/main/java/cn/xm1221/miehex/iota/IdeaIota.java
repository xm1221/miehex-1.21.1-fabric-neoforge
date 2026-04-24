package cn.xm1221.miehex.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import cn.xm1221.miehex.util.SgaUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class IdeaIota extends Iota {
    public static final Codec<IdeaIota> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("entityTypeId").forGetter(i -> i.entityTypeId),
                    Codec.DOUBLE.fieldOf("maxHealth").forGetter(i -> i.maxHealth),
                    Codec.DOUBLE.fieldOf("movementSpeed").forGetter(i -> i.movementSpeed),
                    Codec.DOUBLE.fieldOf("attackDamage").forGetter(i -> i.attackDamage),
                    Codec.DOUBLE.fieldOf("armor").forGetter(i -> i.armor)
            ).apply(instance, IdeaIota::new)
    );

    private final String entityTypeId;
    private final double maxHealth;
    private final double movementSpeed;
    private final double attackDamage;
    private final double armor;

    public IdeaIota(String entityTypeId, double maxHealth, double movementSpeed,
                    double attackDamage, double armor) {
        super(() -> IdeaIotaType.INSTANCE);
        this.entityTypeId = entityTypeId;
        this.maxHealth = maxHealth;
        this.movementSpeed = movementSpeed;
        this.attackDamage = attackDamage;
        this.armor = armor;
    }

    public String getEntityTypeId() { return entityTypeId; }
    public double getMaxHealth() { return maxHealth; }
    public double getMovementSpeed() { return movementSpeed; }
    public double getAttackDamage() { return attackDamage; }
    public double getArmor() { return armor; }

    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        if (!(that instanceof IdeaIota other)) return false;
        return this.entityTypeId.equals(other.entityTypeId) &&
                Math.abs(this.maxHealth - other.maxHealth) < 1e-5 &&
                Math.abs(this.movementSpeed - other.movementSpeed) < 1e-5 &&
                Math.abs(this.attackDamage - other.attackDamage) < 1e-5 &&
                Math.abs(this.armor - other.armor) < 1e-5;
    }

    @Override
    public Component display() {
        String raw = String.format("IDEA || {TYPE:%s HP:%.0f SPD:%.1f DMG:%.1f ARM:%.1f}",
                entityTypeId, maxHealth, movementSpeed, attackDamage, armor);
        String sga = SgaUtils.toStandardGalactic(raw);
        return Component.literal(sga).withStyle(ChatFormatting.WHITE);
    }

    @Override
    public int hashCode() {
        int result = entityTypeId.hashCode();
        result = 31 * result + Double.hashCode(maxHealth);
        result = 31 * result + Double.hashCode(movementSpeed);
        result = 31 * result + Double.hashCode(attackDamage);
        result = 31 * result + Double.hashCode(armor);
        return result;
    }
}