package latokike.mythorigins.common.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import latokike.mythorigins.common.registry.MOScaleTypes;
import net.minecraft.entity.LivingEntity;
import virtuoel.pehkui.api.ScaleData;

public class ModifySizePower extends Power {

    public final float scale;

    public ModifySizePower(PowerType<?> type, LivingEntity player, float scale) {
        super(type, player);
        this.scale = scale;
    }

    @Override
    public void onAdded() {
        super.onAdded();
        ScaleData data = MOScaleTypes.MODIFY_SIZE_TYPE.getScaleData(entity);
        data.setScale(data.getBaseScale() * scale);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        ScaleData data = MOScaleTypes.MODIFY_SIZE_TYPE.getScaleData(entity);
        data.setScale(data.getBaseScale() / scale);
    }
}
