package latokike.mythorigins.common.entities.renderer;

import latokike.mythorigins.common.MythOrigins;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

import latokike.mythorigins.common.entities.HarpyFeatherArrowEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class HarpyFeatherArrowEntityRenderer extends ProjectileEntityRenderer<HarpyFeatherArrowEntity> {
    public static final Identifier TEXTURE = new Identifier(MythOrigins.MODID, "textures/entity/harpy/harpy_feather.png");

    public HarpyFeatherArrowEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    public Identifier getTexture() {
        return TEXTURE;
    }
}
