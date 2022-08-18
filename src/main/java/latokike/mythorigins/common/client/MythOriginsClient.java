package latokike.mythorigins.common.client;

import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.entities.HarpyFeatherArrowEntity;
import latokike.mythorigins.common.entities.renderer.HarpyFeatherArrowEntityRenderer;
import latokike.mythorigins.common.registry.MOEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.Identifier;

public class MythOriginsClient implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(MythOrigins.MODID, "spawn_packet");

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {

        EntityRendererRegistry.register(MOEntities.POISON_BREATH,
                FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(MOEntities.ICE_BALL,
                FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(MOEntities.NONE,
                FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(MOEntities.FLAME_1,
                FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(MOEntities.FLAME_2,
                FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(MOEntities.ARROW_HARPY_FEATHER, (context) -> new HarpyFeatherArrowEntityRenderer(context) {
            @Override
            public Identifier getTexture(HarpyFeatherArrowEntity entity) {
                return HarpyFeatherArrowEntityRenderer.TEXTURE;
            }
        });
    }
    /*@Environment(EnvType.CLIENT)
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
                e.setPitch(pitch);
                e.setBodyYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
            EntityRendererRegistry.register(MOEntities.ARROW_HARPY_FEATHER, (context) -> new HarpyFeatherArrowEntityRenderer(context) {
                @Override
                public Identifier getTexture(HarpyFeatherArrowEntity entity) {
                    return HarpyFeatherArrowEntityRenderer.TEXTURE;
                }
            });
        }
    }*/
}