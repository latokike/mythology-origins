package latokike.mythorigins.common.registry;

import java.util.List;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.PowerTypeReference;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.apace100.origins.util.HudRender;
import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.power.*;
import latokike.mythorigins.common.power.ModifyBehavior.EntityBehavior;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.entity.EntityType;

import java.util.LinkedHashMap;
import java.util.Map;

public class MOPowers {
	private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

	public static void register() {
		register(
				new PowerFactory<>(MythOrigins.identifier("modify_behavior"),
						new SerializableData()
								.add("behavior", SerializableDataType.enumValue(ModifyBehavior.EntityBehavior.class))
								.add("entities", SerializableDataType.list(SerializableDataType.ENTITY_TYPE)),
						(data) -> (type, player) -> {
							return new ModifyBehavior(type, player, (EntityBehavior) data.get("behavior"),
									(List<EntityType<?>>) data.get("entities"));
						}));
	}

	public static final PowerFactory<Power> EXPLODE = create(
			new PowerFactory<>(
					new Identifier(MythOrigins.MODID, "explode"),
					new SerializableData()
							.add("cooldown", SerializableDataType.INT)
							.add("strength", SerializableDataType.FLOAT, 1.0f)
							.add("break_blocks", SerializableDataType.BOOLEAN, true)
							.add("self_damage", SerializableDataType.FLOAT, 20.0f)
							.add("hud_render", SerializableDataType.HUD_RENDER)
							.add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY)
							.add("ignitable", SerializableDataType.BOOLEAN, true),
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
							.add("scale", SerializableDataType.FLOAT),
					data -> (type, player) ->
							new ModifySizePower(type, player,
									data.getFloat("scale")))
					.allowCondition());

	public static final PowerFactory<Power> SPIKED = create(
			new PowerFactory<>(
					new Identifier(MythOrigins.MODID, "spiked"),
					new SerializableData()
							.add("spike_damage", SerializableDataType.INT, 2),
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
		POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
	}

	private static void register(PowerFactory serializer) {
		Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
	}
}
