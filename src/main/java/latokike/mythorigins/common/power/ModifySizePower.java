package latokike.mythorigins.common.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import latokike.mythorigins.common.registry.MOScaleTypes;
import net.minecraft.entity.player.PlayerEntity;
import virtuoel.pehkui.api.ScaleData;

public class ModifySizePower extends Power {

	public final float scale;
	
	public ModifySizePower(PowerType<?> type, PlayerEntity player, float scale) {
		super(type, player);
		this.scale = scale;
	}
	
	@Override
	public void onAdded() {
		super.onAdded();
		ScaleData data = MOScaleTypes.MODIFY_SIZE_TYPE.getScaleData(player);
		data.setScale(data.getBaseScale() * scale);
	}
	
	@Override
	public void onRemoved() {
		super.onRemoved();
		ScaleData data = MOScaleTypes.MODIFY_SIZE_TYPE.getScaleData(player);
		data.setScale(data.getBaseScale() / scale);
	}
}
