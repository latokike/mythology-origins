package latokike.mythorigins.common.registry;

import java.util.List;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.power.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MOPowers {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

	public static final PowerFactory<Power> EXPLODE = create(
			new PowerFactory<>(
					new Identifier(MythOrigins.MODID, "explode"),
					new SerializableData()
							.add("cooldown", SerializableDataTypes.INT)
							.add("strength", SerializableDataTypes.FLOAT, 1.0f)
							.add("break_blocks", SerializableDataTypes.BOOLEAN, true)
							.add("self_damage", SerializableDataTypes.FLOAT, 20.0f)
							.add("hud_render", ApoliDataTypes.HUD_RENDER)
							.add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY)
							.add("ignitable", SerializableDataTypes.BOOLEAN, true),
					(data) -> (type, player) -> {
						ExplodePower power = new ExplodePower(type, player,
								data.getInt("cooldown"),
								(HudRender) data.get("hud_render"),
								data.getFloat("strength"),
								data.getBoolean("break_blocks"),
								data.getFloat("self_damage"),
								data.getBoolean("ignitable"));
								power.setKey((Active.Key)
								data.get("key"));
								return power;})
					.allowCondition());

	public static final PowerFactory<Power> MODIFY_SIZE = create(
			new PowerFactory<>(
					new Identifier(MythOrigins.MODID, "modify_size"),
					new SerializableData()
							.add("scale", SerializableDataTypes.FLOAT),
					data -> (type, player) ->
							new ModifySizePower(type, player,
									data.getFloat("scale")))
					.allowCondition());

	public static final PowerFactory<Power> SPIKED = create(
			new PowerFactory<>(
					new Identifier(MythOrigins.MODID, "spiked"),
					new SerializableData()
							.add("spike_damage", SerializableDataTypes.INT, 2),
					data -> (type, player) ->
							new SpikedPower(
									type, player,
									data.getInt("spike_damage")))
					.allowCondition());

	public static final PowerType<Power> NONE = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "none"));

	public static final PowerType<Power> HYDRA = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "hydra"));
	public static final PowerType<Power> HORNS = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "horns"));
	public static final PowerType<Power> WEREWOLF_LIKE = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "werewolf_like"));
	public static final PowerType<Power> SHARP_FEATHERS = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "sharp_feathers"));
	public static final PowerType<Power> BOW_MASTER = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "bow_master"));
	public static final PowerType<Power> ACCURATE = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "accurate"));
	public static final PowerType<Power> FAERY_WINGS = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "faery_wings"));
	public static final PowerType<Power> HARPY_WINGS = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "harpy_feathers"));
	public static final PowerType<Power> LIONS_MANE = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "lions_mane"));
	public static final PowerType<Power> SNAKE_ANCESTORS = new PowerTypeReference<>(new Identifier(MythOrigins.MODID, "snake_features"));

	private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
		POWER_FACTORIES.put(factory, factory.getSerializerId());
		return factory;
	}
	
	public static void init() {
		POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ApoliRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}
}
