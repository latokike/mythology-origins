package latokike.mythorigins.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(Context context, PlayerEntityModel<AbstractClientPlayerEntity> playerModel, float playerScale) {
        super(context, playerModel, playerScale);
    }

/*
    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At("RETURN"))
    private void construct(EntityRenderDispatcher renderDispatcher, boolean slimChar, CallbackInfo cir) {
        this.addFeature(new HeadFeatureRenderer<>(this));
        this.addFeature(new WerewolfFeatureRenderer<>(this));
        this.addFeature(new LionsManeFeatureRenderer<>(this));
        this.addFeature(new MinotaurHornsFeatureRenderer<>(this));
        this.addFeature(new HydraHeadsFeatureRenderer<>(this));
        this.addFeature(new FeatherFeatureRenderer<>(this));
        this.addFeature(new SnakeFeatureRenderer<>(this));
    }
*/
}