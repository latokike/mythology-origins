package latokike.mythorigins.common.entities;

import latokike.mythorigins.common.client.MythOriginsClient;
import latokike.mythorigins.common.networking.packet.EntitySpawnPacket;
import latokike.mythorigins.common.registry.MOEntities;
import latokike.mythorigins.common.registry.MOItems;
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
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class NoneEntity extends ThrownItemEntity {

    public NoneEntity(EntityType<? extends NoneEntity> entityType, World world) {
        super(entityType, world);
    }

    public NoneEntity(World world, LivingEntity owner) {
        super(MOEntities.NONE, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public NoneEntity(World world, double x, double y, double z) {
        super(MOEntities.NONE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return MOItems.NONE_ITEM;
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        Entity entity = this.getOwner();
        if (hitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult)hitResult).getEntity().isPartOf(entity)) {
            if (!this.world.isClient) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        super.tick();
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, MythOriginsClient.PacketID);
    }
}