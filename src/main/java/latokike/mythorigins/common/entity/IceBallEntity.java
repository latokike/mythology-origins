package latokike.mythorigins.common.entity;

import latokike.mythorigins.common.registry.MOEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.List;

public class IceBallEntity extends ThrownItemEntity {

    public IceBallEntity(EntityType<? extends IceBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceBallEntity(World world, LivingEntity owner) {
        super(MOEntities.ICE_BALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public IceBallEntity(World world, double x, double y, double z) {
        super(MOEntities.ICE_BALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.PACKED_ICE;
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        Entity entity = this.getOwner();
        if (hitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult)hitResult).getEntity().isPartOf(entity)) {
            if (!this.world.isClient) {
                List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0D, 2.0D, 4.0D));
                AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
                if (entity instanceof LivingEntity) {
                    areaEffectCloudEntity.setOwner((LivingEntity)entity);
                }

                areaEffectCloudEntity.setParticleType(ParticleTypes.ENTITY_EFFECT);
                areaEffectCloudEntity.setRadius(2.0F);
                areaEffectCloudEntity.setDuration(300);
                areaEffectCloudEntity.setRadiusGrowth((7.0F - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 3));
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0));
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0));
                if (!list.isEmpty()) {

                    for (LivingEntity livingEntity : list) {
                        double d = this.squaredDistanceTo(livingEntity);
                        if (d < 16.0D) {
                            areaEffectCloudEntity.updatePosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            break;
                        }
                    }
                }

                this.world.syncWorldEvent(2007, this.getBlockPos(), this.isSilent() ? -1 : 1);
                this.world.spawnEntity(areaEffectCloudEntity);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.remove(RemovalReason.DISCARDED);
        } else {
            super.tick();
        }
    }
}