package latokike.mythorigins.common.registry;

import latokike.mythorigins.common.MythOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.*;

import java.util.Map;

public class MOScaleTypes {
	private static final ScaleType[] MODIFY_SIZE_TYPES = {ScaleTypes.WIDTH, ScaleTypes.HEIGHT, ScaleTypes.DROPS};
	
	public static final ScaleType MODIFY_SIZE_TYPE = register(ScaleRegistries.SCALE_TYPES, ScaleType.Builder.create().build());
	
	public static final ScaleModifier MODIFY_SIZE_MODIFIER = register(ScaleRegistries.SCALE_MODIFIERS, new ScaleModifier() {
		@Override
		public float modifyScale(final ScaleData scaleData, float modifiedScale, final float delta) {
			return MODIFY_SIZE_TYPE.getScaleData(scaleData.getEntity()).getScale(delta) * modifiedScale;
		}
	});
	
	private static <T> T register(Map<Identifier, T> registry, T entry) {
		return ScaleRegistries.register(registry, new Identifier(MythOrigins.MODID, "modify_size"), entry);
	}
	
	public static void init() {
		MODIFY_SIZE_TYPE.getScaleChangedEvent().register(event -> {
			Entity entity = event.getEntity();
			if (entity != null) {
				boolean onGround = entity.isOnGround();
				entity.calculateDimensions();
				entity.setOnGround(onGround);
				for (ScaleType type : ScaleRegistries.SCALE_TYPES.values()) {
					ScaleData data = type.getScaleData(entity);
					if (data.getBaseValueModifiers().contains(MODIFY_SIZE_MODIFIER)) {
						data.markForSync(true);
					}
				}
			}
		});
		for (ScaleType type : MODIFY_SIZE_TYPES) {
			type.getDefaultBaseValueModifiers().add(MODIFY_SIZE_MODIFIER);
		}
	}
}
