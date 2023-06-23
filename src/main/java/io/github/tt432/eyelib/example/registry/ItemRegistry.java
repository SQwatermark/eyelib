package io.github.tt432.eyelib.example.registry;

import io.github.tt432.eyelib.Eyelib;
import io.github.tt432.eyelib.example.item.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Eyelib.MOD_ID);

    public static final RegistryObject<BlockItem> HABITAT = ITEMS.register("habitat",
            () -> new BlockItem(BlockRegistry.HABITAT_BLOCK.get(),
                    new Item.Properties()));
    public static final RegistryObject<BlockItem> FERTILIZER_ITEM = ITEMS.register("fertilizer",
            () -> new BlockItem(BlockRegistry.FERTILIZER_BLOCK.get(),
                    new Item.Properties()));
    public static final RegistryObject<BlockItem> GAMING_CHAIR = ITEMS.register("gaming_chair",
            () -> new BlockItem(BlockRegistry.GAMING_CHAIR.get(),
                    new Item.Properties()));

    public static final RegistryObject<JackInTheBoxItem> JACK_IN_THE_BOX = ITEMS.register("jackintheboxitem",
            () -> new JackInTheBoxItem(new Item.Properties()));

    public static final RegistryObject<FistItem> FIST = ITEMS.register("fist",
            () -> new FistItem(new Item.Properties()));

    public static final RegistryObject<DebugItem> DEBUG = ITEMS.register("debug",
            () -> new DebugItem(new Item.Properties()));

    public static final RegistryObject<PistolItem> PISTOL = ITEMS.register("pistol", () -> new PistolItem());

    public static final RegistryObject<GeckoArmorItem> GECKOARMOR_HEAD = ITEMS.register("geckoarmor_head",
            () -> new GeckoArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<GeckoArmorItem> GECKOARMOR_CHEST = ITEMS.register("geckoarmor_chest",
            () -> new GeckoArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<GeckoArmorItem> GECKOARMOR_LEGGINGS = ITEMS.register("geckoarmor_leggings",
            () -> new GeckoArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<GeckoArmorItem> GECKOARMOR_BOOTS = ITEMS.register("geckoarmor_boots",
            () -> new GeckoArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS, new Item.Properties()));

}
