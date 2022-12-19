package latokike.mythorigins.common.entity;

import latokike.mythorigins.common.client.MythOriginsClient;
import latokike.mythorigins.common.registry.MOEntities;
import latokike.mythorigins.common.registry.MOItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class Flame2Entity extends ThrownItemEntity {

    public Flame2Entity(EntityType<? extends Flame2Entity> entityType, World world) {
        super(entityType, world);
    }

    public Flame2Entity(World world, LivingEntity owner) {
        super(MOEntities.FLAME_2, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public Flame2Entity(World world, double x, double y, double z) {
        super(MOEntities.FLAME_2, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return MOItems.FLAME_2_ITEM;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 1.0F);
        entityHitResult.getEntity().setFireTicks(10);
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