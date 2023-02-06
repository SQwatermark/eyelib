/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package io.github.tt432.eyelib.example;

import io.github.tt432.eyelib.Eyelib;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationController;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationController.ModelFetcher;
import io.github.tt432.eyelib.common.bedrock.renderer.GeoArmorRenderer;
import io.github.tt432.eyelib.example.registry.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@EventBusSubscriber
public class GeckoLibMod {
	public static CreativeModeTab geckolibItemGroup;
	/**
	 * When set to true, prevents examples from being registered.
	 */
	public static final String DISABLE_EXAMPLES_PROPERTY_KEY = "geckolib.disable_examples";
	private static final boolean IS_DEVELOPMENT_ENVIRONMENT = !FMLEnvironment.production;

	public GeckoLibMod() {
		Eyelib.initialize();

		if (shouldRegisterExamples()) {
			IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
			EntityRegistry.ENTITIES.register(bus);
			ItemRegistry.ITEMS.register(bus);
			TileRegistry.TILES.register(bus);
			BlockRegistry.BLOCKS.register(bus);
			SoundRegistry.SOUNDS.register(bus);
			geckolibItemGroup = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "geckolib_examples") {
				@Override
				public ItemStack makeIcon() {
					return new ItemStack(ItemRegistry.JACK_IN_THE_BOX.get());
				}
			};
		}
	}

	@SubscribeEvent
	public static void onEntityRemoved(EntityLeaveWorldEvent event) {
		if (event.getEntity() == null) {
			return;
		}
		if (event.getEntity().getUUID() == null) {
			return;
		}
		if (event.getWorld().isClientSide)
			GeoArmorRenderer.LIVING_ENTITY_RENDERERS.values().forEach(instances -> {
				if (instances.containsKey(event.getEntity().getUUID())) {
					ModelFetcher<?> beGone = instances.get(event.getEntity().getUUID());
					AnimationController.removeModelFetcher(beGone);
					instances.remove(event.getEntity().getUUID());
				}
			});
	}

	/**
	 * Returns whether examples are to be registered. Examples are registered when:
	 * <ul>
	 *     <li>The mod is running in a development environment; <em>and</em></li>
	 *     <li>the system property defined by {@link #DISABLE_EXAMPLES_PROPERTY_KEY} is not set to "true".</li>
	 * </ul>
	 *
	 * @return whether the examples are to be registered
	 */
	static boolean shouldRegisterExamples() {
		return IS_DEVELOPMENT_ENVIRONMENT && !Boolean.getBoolean(DISABLE_EXAMPLES_PROPERTY_KEY);
	}
}
