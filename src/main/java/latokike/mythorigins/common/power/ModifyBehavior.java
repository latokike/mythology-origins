package latokike.mythorigins.common.power;

import java.util.List;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

public class ModifyBehavior extends Power {

	public ModifyBehavior(PowerType<?> type, PlayerEntity player) {
		super(type, player);
	}
}
