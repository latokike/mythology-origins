package latokike.mythorigins.common;

import latokike.mythorigins.common.power.factory.condition.EntityConditions;
import latokike.mythorigins.common.registry.*;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.util.registry.Registry;

public class MythOrigins implements ModInitializer {
    public static final String MOD_ID = "mythorigins";
    public static final String MODID = "mythorigins";
    public static String VERSION = "";
    public static int[] SEMVER;
    public static final Logger LOGGER = LogManager.getLogger("Myth Origins");

    public static final PowerType<Power> THREE_LIVES = new PowerTypeReference<>(new Identifier(MOD_ID, "three_lives"));
    public static final PowerType<Power> SECOND_WIND = new PowerTypeReference<>(new Identifier(MOD_ID, "second_wind"));
    public static final EntityAttribute LIVES = make();

    public static double getLivesAmount(final LivingEntity entity) {
        final EntityAttributeInstance lives = entity.getAttributeInstance(LIVES);
        return lives.getValue();
    }

    private static EntityAttribute make() {
        return new ClampedEntityAttribute("attribute.name.generic." + MOD_ID + '.' + "lives", 0, 0, 3).setTracked(true);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ATTRIBUTE, new Identifier(MOD_ID, "lives"), LIVES);
        FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer -> {
            VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
            if (VERSION.contains("+")) {
                VERSION = VERSION.split("\\+")[0];
            }
            if (VERSION.contains("-")) {
                VERSION = VERSION.split("-")[0];
            }
            String[] splitVersion = VERSION.split("\\.");
            SEMVER = new int[splitVersion.length];
            for (int i = 0; i < SEMVER.length; i++) {
                SEMVER[i] = Integer.parseInt(splitVersion[i]);
            }
        });
        LOGGER.warn("Myth Origins " + VERSION + " is initializing. Have fun!");

        MOEntities.init();
        MOItems.init();
        MOScaleTypes.init();
        MOPowers.init();
        EntityConditions.init();
    }

    public static Identifier identifier(String path) {
        return new Identifier(MythOrigins.MODID, path);
    }
}
