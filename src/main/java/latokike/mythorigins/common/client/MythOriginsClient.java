package latokike.mythorigins.common.client;

import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.entities.HarpyFeatherArrowEntity;
import latokike.mythorigins.common.entities.renderer.HarpyFeatherArrowEntityRenderer;
import latokike.mythorigins.common.networking.packet.EntitySpawnPacket;
import latokike.mythorigins.common.registry.MOEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.AreaEffectCloudEntityRenderer;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import java.util.UUID;

public class MythOriginsClient implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(MythOrigins.MODID, "spawn_packet");

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(MOEntities.POISON_BREATH,
                (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(MOEntities.ICE_BALL,
                (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(MOEntities.NONE,
                (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        receiveEntityPacket();
        EntityRendererRegistry.INSTANCE.register(MOEntities.MYTH_AREA_EFFECT_CLOUD,
                (dispatcher, context) -> new AreaEffectCloudEntityRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(MOEntities.FLAME_1,
                (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(MOEntities.FLAME_2,
                (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));

        EntityRendererRegistry.INSTANCE.register(MOEntities.ARROW_HARPY_FEATHER, (dispatcher, context) -> {
            return new HarpyFeatherArrowEntityRenderer(dispatcher) {
                @Override
                public Identifier getTexture(HarpyFeatherArrowEntity entity) {
                    return HarpyFeatherArrowEntityRenderer.TEXTURE;
                }
            };
        });
    }

    @Environment(EnvType.CLIENT)
    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.pitch = pitch;
                e.yaw = yaw;
                e.setEntityId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}