package latokike.mythorigins.common.registry;

import latokike.mythorigins.common.MythOrigins;
import latokike.mythorigins.common.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MOEntities {
    public static final EntityType<PoisonBreathEntity> POISON_BREATH;
    public static final EntityType<IceBallEntity> ICE_BALL;
    public static final EntityType<NoneEntity> NONE;
    public static final EntityType<MythAreaEffectCloudEntity> MYTH_AREA_EFFECT_CLOUD;
    public static final EntityType<Flame1Entity> FLAME_1;
    public static final EntityType<Flame2Entity> FLAME_2;

    public static final EntityType<HarpyFeatherArrowEntity> ARROW_HARPY_FEATHER;

    static {
        POISON_BREATH = FabricEntityTypeBuilder.<PoisonBreathEntity>create(SpawnGroup.MISC, PoisonBreathEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
        ICE_BALL = FabricEntityTypeBuilder.<IceBallEntity>create(SpawnGroup.MISC, IceBallEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
        NONE = FabricEntityTypeBuilder.<NoneEntity>create(SpawnGroup.MISC, NoneEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
        MYTH_AREA_EFFECT_CLOUD = FabricEntityTypeBuilder.<MythAreaEffectCloudEntity>create(SpawnGroup.MISC, MythAreaEffectCloudEntity::new).fireImmune().dimensions(EntityDimensions.changing(6.0F, 0.5F)).trackable(10, 2147483647).build();
        FLAME_1 = FabricEntityTypeBuilder.<Flame1Entity>create(SpawnGroup.MISC, Flame1Entity::new).fireImmune().dimensions(EntityDimensions.changing(0.1F, 0.1F)).build();
        FLAME_2 = FabricEntityTypeBuilder.<Flame2Entity>create(SpawnGroup.MISC, Flame2Entity::new).fireImmune().dimensions(EntityDimensions.changing(0.1F, 0.1F)).build();

        ARROW_HARPY_FEATHER = FabricEntityTypeBuilder.<HarpyFeatherArrowEntity>create(SpawnGroup.MISC, HarpyFeatherArrowEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build();
    }

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "poison_breath"), POISON_BREATH);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "ice_ball"), ICE_BALL);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "none"), NONE);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "myth_area_effect_cloud"), MYTH_AREA_EFFECT_CLOUD);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "flame_1"), FLAME_1);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "flame_2"), FLAME_2);

        Registry.register(Registry.ENTITY_TYPE, new Identifier(MythOrigins.MODID, "harpy_feather"), ARROW_HARPY_FEATHER);
    }
}