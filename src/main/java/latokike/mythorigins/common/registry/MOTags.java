package latokike.mythorigins.common.registry;

import latokike.mythorigins.common.MythOrigins;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class MOTags {
    public static final Tag<Item> BOW = TagRegistry.item(new Identifier(MythOrigins.MODID, "bow"));
}
