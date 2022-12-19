package latokike.mythorigins.mixin;

import latokike.mythorigins.common.entity.renderer.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void construct(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new WerewolfFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new LionsManeFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new MinotaurHornsFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new HydraHeadsFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new FeatherFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new SnakeFeatureRenderer<>(this, ctx.getModelLoader()));
    }
}