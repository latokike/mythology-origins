package latokike.mythorigins.mixin;

import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.registry.MOPowers;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {

  private static final Identifier FAERY_WINGS_TEXTURE = new Identifier(MythOrigins.MODID, "textures/entity/faery_wings.png");
  private static final Identifier HARPY_WINGS_TEXTURE = new Identifier(MythOrigins.MODID, "textures/entity/harpy_wings.png");

  @Redirect(
      method = "render",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;canRenderElytraTexture()Z"))
  private boolean canRenderCustomWingsTexture(AbstractClientPlayerEntity abstractClientPlayerEntity) {
    if (MOPowers.FAERY_WINGS.isActive(abstractClientPlayerEntity)) {
      return true;
    }
    if (MOPowers.HARPY_WINGS.isActive(abstractClientPlayerEntity)) {
      return true;
    }
    return abstractClientPlayerEntity.canRenderElytraTexture();
  }

  @Redirect(
      method = "render",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getElytraTexture()Lnet/minecraft/util/Identifier;"))
  private Identifier getCustomWingsTexture(AbstractClientPlayerEntity abstractClientPlayerEntity) {
    if (MOPowers.FAERY_WINGS.isActive(abstractClientPlayerEntity)) {
      return FAERY_WINGS_TEXTURE;
    }
    if (MOPowers.HARPY_WINGS.isActive(abstractClientPlayerEntity)) {
      return HARPY_WINGS_TEXTURE;
    }
    return abstractClientPlayerEntity.getElytraTexture();
  }
}