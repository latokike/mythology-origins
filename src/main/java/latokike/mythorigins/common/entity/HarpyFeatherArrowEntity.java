package latokike.mythorigins.common.entity;

import latokike.mythorigins.common.registry.MOEntities;
import latokike.mythorigins.common.registry.MOItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HarpyFeatherArrowEntity extends PersistentProjectileEntity {

    boolean canPickup = false;

    public HarpyFeatherArrowEntity(EntityType<? extends HarpyFeatherArrowEntity> entityType, World world) {
        super((EntityType<? extends PersistentProjectileEntity>)entityType, world);
    }

    public HarpyFeatherArrowEntity(World world, LivingEntity owner) {
        super(MOEntities.ARROW_HARPY_FEATHER, owner, world);
    }

    public HarpyFeatherArrowEntity(World world, double x, double y, double z) {
        super(MOEntities.ARROW_HARPY_FEATHER, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    public void tick() {
        super.tick();
    }

    protected void onHit(LivingEntity target) {
        super.onHit(target);
        target.damage(DamageSource.MAGIC, 7);
    }
    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(MOItems.HARPY_FEATHER_ITEM);
    }
}