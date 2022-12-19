package latokike.mythorigins.common.entity.renderer;

import latokike.mythorigins.common.registry.MOItems;
import latokike.mythorigins.common.registry.MOPowers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class FeatherFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private final float xScale;
    private final float yScale;
    private final float zScale;

    public FeatherFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        this(context, loader, 1.0F, 1.0F, 1.0F);
    }

    public FeatherFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader, float f, float g, float h) {
        super(context);
        this.xScale = f;
        this.yScale = g;
        this.zScale = h;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        this.renderHead(matrixStack, vertexConsumerProvider, i, livingEntity);
    }

    public void renderHead(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity) {
        if (MOPowers.SHARP_FEATHERS.isActive(livingEntity)) {
            // Right Feather
            Item item = MOItems.HARPY_FEATHER_ITEM;
            ItemStack itemStack = item.getDefaultStack();
            matrixStack.push();
            matrixStack.scale(this.xScale, this.yScale, this.zScale);

            (this.getContextModel()).getHead().rotate(matrixStack);
            matrixStack.translate(-0.1D, -0.15D, -0.4D);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270.0F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(25.0F));
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(75.0F));
            matrixStack.scale(0.74F, -0.74F, -0.74F);

            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }

        if (MOPowers.SHARP_FEATHERS.isActive(livingEntity)) {
            // Left Feather
            Item item = MOItems.HARPY_FEATHER_ITEM;
            ItemStack itemStack = item.getDefaultStack();
            matrixStack.push();
            matrixStack.scale(this.xScale, this.yScale, this.zScale);

            (this.getContextModel()).getHead().rotate(matrixStack);
            matrixStack.translate(-0.485D, 0.125D, -0.4D);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270.0F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-25.0F));
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(75.0F));
            matrixStack.scale(0.74F, -0.74F, -0.74F);

            MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }
}