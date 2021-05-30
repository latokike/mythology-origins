package latokike.mythorigins.common.power.factory;

import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import net.minecraft.util.registry.Registry;

public class PowerFactories {

    @SuppressWarnings("unchecked")
    public static void register() {

    }

    private static void register(PowerFactory serializer) {
        Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}
