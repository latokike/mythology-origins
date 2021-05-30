package latokike.mythorigins.common.power.factory.action;

import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;
import net.minecraft.entity.*;

import latokike.mythorigins.common.MythOrigins;

public class EntityActions {

    @SuppressWarnings("unchecked")
    public static void init() {
        register(new ActionFactory<>(MythOrigins.identifier("sound"), new SerializableData()
            .add("sound", SerializableDataType.SOUND_EVENT)
            .add("volume", SerializableDataType.FLOAT, 1F)
            .add("pitch", SerializableDataType.FLOAT, 1F),
            (data, entity) -> {
                if(entity instanceof PlayerEntity) {
                    (entity).world.playSound((PlayerEntity) null, (entity).getX(), (entity).getY(), (entity).getZ(), new SoundEvent(new Identifier((String) data.get("sound"))),
                	SoundCategory.PLAYERS, data.getFloat("volume"), data.getFloat("pitch"));
                }
            }));
    }
    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
