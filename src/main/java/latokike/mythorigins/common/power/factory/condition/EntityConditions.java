package latokike.mythorigins.common.power.factory.condition;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import latokike.mythorigins.common.MythOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityConditions {
    public static void init() {
        register(
                new ConditionFactory<>(
                        new Identifier(MythOrigins.MODID, "full_moon"),
                        new SerializableData(),
                        (data, player) ->
                                player.world.getMoonSize() == 1.0)
        );

        register(
                new ConditionFactory<>(
                        new Identifier(MythOrigins.MODID, "nighttime"),
                        new SerializableData(),
                        (data, player) ->
                                player.world.getTimeOfDay() % 24000L > 13000L));
    }
    private static void register(ConditionFactory<Entity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}