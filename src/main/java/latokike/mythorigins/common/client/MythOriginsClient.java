package latokike.mythorigins.common.client;

import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.entity.HarpyFeatherArrowEntity;
import latokike.mythorigins.common.entity.renderer.HarpyFeatherArrowEntityRenderer;
import latokike.mythorigins.common.registry.MOEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
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

        EntityRendererRegistry.register(MOEntities.ARROW_HARPY_FEATHER, (context) -> {
            return new HarpyFeatherArrowEntityRenderer(context) {
                public Identifier getTexture(HarpyFeatherArrowEntity entity) {
                    return HarpyFeatherArrowEntityRenderer.TEXTURE;
                }
            };
        });
    }
}