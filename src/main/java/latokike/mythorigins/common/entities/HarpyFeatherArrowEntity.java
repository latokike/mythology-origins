package latokike.mythorigins.common.entities;

import latokike.mythorigins.common.client.MythOriginsClient;
import latokike.mythorigins.common.networking.packet.EntitySpawnPacket;
import latokike.mythorigins.common.registry.MOEntities;
import latokike.mythorigins.common.registry.MOItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class HarpyFeatherArrowEntity extends PersistentProjectileEntity {

    boolean canPickup = false;

    public HarpyFeatherArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public HarpyFeatherArrowEntity(World world, LivingEntity owner) {
        super(MOEntities.ARROW_HARPY_FEATHER, owner, world);
    }

    public HarpyFeatherArrowEntity(World world, double x, double y, double z) {
        super(MOEntities.ARROW_HARPY_FEATHER, x, y, z, world);
    }

    public void tick() {
        super.tick();
    }

    protected ItemStack asItemStack() {
        return new ItemStack(Items.AIR);
    }

    protected void onHit(LivingEntity target) {
        super.onHit(target);
        target.damage(DamageSource.MAGIC, 7);
    }
    @Override
    @Environment(EnvType.CLIENT)
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, MythOriginsClient.PacketID);
    }
}