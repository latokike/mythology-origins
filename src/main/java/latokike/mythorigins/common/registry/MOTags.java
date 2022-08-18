package latokike.mythorigins.common.registry;

import latokike.mythorigins.common.MythOrigins;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

public class MOTags {
    public static final TagKey<Item> BOW = TagKey.of(Registry.ITEM_KEY, new Identifier(MythOrigins.MODID, "bow"));
}
