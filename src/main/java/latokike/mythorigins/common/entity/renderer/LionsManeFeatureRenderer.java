package latokike.mythorigins.common.entity.renderer;

import latokike.mythorigins.common.registry.MOPowers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class LionsManeFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private final float xScale;
    private final float yScale;
    private final float zScale;
    private final HeldItemRenderer heldItemRenderer;

    public LionsManeFeatureRenderer(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        this(context, 1.0F, 1.0F, 1.0F, heldItemRenderer);
    }

    public LionsManeFeatureRenderer(FeatureRendererContext<T, M> context, float f, float g, float h, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.xScale = f;
        this.yScale = g;
        this.zScale = h;
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        this.renderHead(matrixStack, vertexConsumerProvider, i, livingEntity);
    }

    public void renderHead(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity) {
        if (MOPowers.LIONS_MANE.isActive(livingEntity)) {
            Item item = Items.BROWN_WOOL;
            ItemStack itemStack = item.getDefaultStack();
            matrixStack.push();
            matrixStack.scale(this.xScale, this.yScale, this.zScale);

            (this.getContextModel()).getHead().rotate(matrixStack);
            matrixStack.translate(0.0D, -0.25D, 0.1D);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            matrixStack.scale(0.61F, -0.61F, -0.61F);

            this.heldItemRenderer.renderItem(livingEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }
}