package latokike.mythorigins.mixin;

import latokike.mythorigins.common.registry.MOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import latokike.mythorigins.common.MythOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.PehkuiConfig;
import virtuoel.pehkui.api.ScaleType;

import java.util.Optional;

import static latokike.mythorigins.common.MythOrigins.LIVES;
import static java.lang.Math.floor;
import static java.lang.Math.pow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow @Nullable public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

	@Shadow public abstract void setHealth(float health);

	@Shadow public abstract boolean clearStatusEffects();

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	//Bow Master
	@ModifyVariable(method = "damage", at = @At("HEAD"))
	private float damage(float amount, DamageSource source) {
		Entity attacker = source.getAttacker();
		if (MOPowers.BOW_MASTER.isActive(attacker)) {
			Entity projectile = source.getSource();
			if (projectile instanceof PersistentProjectileEntity && ((PersistentProjectileEntity) projectile).isShotFromCrossbow()) {
				amount *= 1;
			}
			else
			{
				assert projectile != null;
				amount *= 1.75f;
			}
		}
		//Accurate
		if (MOPowers.ACCURATE.isActive(attacker)) {
			Entity projectile = source.getSource();
			if (projectile instanceof PersistentProjectileEntity) {
				projectile.setGlowing(true);
			}
		}

		return amount;
	}

	//Lives
	@Inject(method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", require = 1, allow = 1, at = @At("RETURN"))
	private static void addAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		final DefaultAttributeContainer.Builder builder = info.getReturnValue();
		builder.add(LIVES);
	}

	private void revertScale()
	{
		PehkuiConfig pehconf = new PehkuiConfig();
		if ((Boolean)Optional.ofNullable((Boolean)PehkuiConfig.COMMON.keepAllScalesOnRespawn.get()).orElse(false)) {
			int flooredSlime = (int) floor(this.getAttributeInstance(LIVES).getValue());
			if (flooredSlime != 0)
			{
				ScaleType.HEIGHT.getScaleData(this).setScale(ScaleType.HEIGHT.getScaleData(this).getScale() * 1);
				ScaleType.WIDTH.getScaleData(this).setScale(ScaleType.WIDTH.getScaleData(this).getScale() * 1);
			}
		}
	}

	@Inject(method = "tryUseTotem", at=@At("HEAD"), cancellable = true)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		if (source.isOutOfWorld()) {

			if (MythOrigins.THREE_LIVES.isActive(this)) {
				revertScale();
			}

			if (MythOrigins.SECOND_WIND.isActive(this)) {
				revertScale();
			}

			cir.setReturnValue(false);
		}
		else {
			if (MythOrigins.THREE_LIVES.isActive(this)) {
				int flooredSlime = (int) floor(this.getAttributeInstance(LIVES).getValue());
				if (flooredSlime == 0)
				{
					flooredSlime = (int) floor(this.getAttributeInstance(LIVES).getValue());
				}

				if (flooredSlime > 1) {
					EntityAttributeModifier SizeModifier = new EntityAttributeModifier(
							String.format("LivesAmount%d",flooredSlime),
							-1,
							EntityAttributeModifier.Operation.ADDITION
					);

					this.getAttributeInstance(LIVES).addPersistentModifier(SizeModifier);

					EntityAttributeModifier HealthModifier = new EntityAttributeModifier(
							String.format("LivesHealthDummy%d",flooredSlime),
							-pow(2, flooredSlime),
							EntityAttributeModifier.Operation.ADDITION
					);



					this.setHealth(1.0F);
					this.clearStatusEffects();
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 2));
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 40, 5));
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

					ScaleType.HEIGHT.getScaleData(this).setScale(ScaleType.HEIGHT.getScaleData(this).getScale()*1);
					ScaleType.WIDTH.getScaleData(this).setScale(ScaleType.WIDTH.getScaleData(this).getScale()*1);

					cir.setReturnValue(true);
					cir.cancel();
					return;
				}
				else {
					this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
							.getModifiers()
							.stream()
							.filter(x -> x.getName().contains("LivesHealthDummy"))
							.forEach(x ->
									this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
											.tryRemoveModifier(x.getId())
							);

					this.getAttributeInstance(LIVES)
							.getModifiers()
							.stream()
							.filter(x -> x.getName().contains("LivesAmount"))
							.forEach(x ->
									this.getAttributeInstance(LIVES)
											.tryRemoveModifier(x.getId())
							);
					revertScale();
				}
			}

			if (MythOrigins.SECOND_WIND.isActive(this)) {
				int flooredSlime = (int) floor(this.getAttributeInstance(LIVES).getValue());
				if (flooredSlime == 0)
				{
					flooredSlime = (int) floor(this.getAttributeInstance(LIVES).getValue());
				}

				if (flooredSlime > 1) {
					EntityAttributeModifier SizeModifier = new EntityAttributeModifier(
							String.format("LivesAmount%d",flooredSlime),
							-1,
							EntityAttributeModifier.Operation.ADDITION
					);

					this.getAttributeInstance(LIVES).addPersistentModifier(SizeModifier);

					EntityAttributeModifier HealthModifier = new EntityAttributeModifier(
							String.format("LivesHealthDummy%d",flooredSlime),
							-pow(2, flooredSlime),
							EntityAttributeModifier.Operation.ADDITION
					);



					this.setHealth(1.0F);
					this.clearStatusEffects();
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 2));
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 40, 5));
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

					ScaleType.HEIGHT.getScaleData(this).setScale(ScaleType.HEIGHT.getScaleData(this).getScale()*1);
					ScaleType.WIDTH.getScaleData(this).setScale(ScaleType.WIDTH.getScaleData(this).getScale()*1);

					cir.setReturnValue(true);
					cir.cancel();
					return;
				}
				else {
					this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
							.getModifiers()
							.stream()
							.filter(x -> x.getName().contains("LivesHealthDummy"))
							.forEach(x ->
									this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
											.tryRemoveModifier(x.getId())
							);

					this.getAttributeInstance(LIVES)
							.getModifiers()
							.stream()
							.filter(x -> x.getName().contains("LivesAmount"))
							.forEach(x ->
									this.getAttributeInstance(LIVES)
											.tryRemoveModifier(x.getId())
							);
					revertScale();
				}
			}
		}
	}
}