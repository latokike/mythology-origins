package latokike.mythorigins.common.registry;

import latokike.mythorigins.common.MythOrigins;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class MOItems {
    public static final Item POISON_BREATH_ITEM = new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    public static final Item NONE_ITEM = new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    public static final Item HARPY_FEATHER_ITEM = new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    public static final Item FLAME_1_ITEM = new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    public static final Item FLAME_2_ITEM = new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(MythOrigins.MODID, "poison_breath_item"), POISON_BREATH_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MythOrigins.MODID, "none_item"), NONE_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MythOrigins.MODID, "harpy_feather_item"), HARPY_FEATHER_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MythOrigins.MODID, "flame_1_item"), FLAME_1_ITEM);
        Registry.register(Registry.ITEM, new Identifier(MythOrigins.MODID, "flame_2_item"), FLAME_2_ITEM);
    }
}