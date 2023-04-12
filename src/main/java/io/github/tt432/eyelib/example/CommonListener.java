package io.github.tt432.eyelib.example;

import io.github.tt432.eyelib.Eyelib;
import io.github.tt432.eyelib.example.registry.EntityRegistry;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Eyelib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonListener {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        if (ExampleMod.shouldRegisterExamples()) {
            event.put(EntityRegistry.BIKE_ENTITY.get(), PathfinderMob.createMobAttributes()
                    .add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 1.0D).build());
            event.put(EntityRegistry.CAR_ENTITY.get(), PathfinderMob.createMobAttributes()
                    .add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 1.0D).build());
            event.put(EntityRegistry.GEO_EXAMPLE_ENTITY.get(), PathfinderMob.createMobAttributes()
                    .add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 1.0D).build());
            event.put(EntityRegistry.EXTENDED_RENDERER_EXAMPLE.get(), PathfinderMob.createMobAttributes()
                    .add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 1.0D).build());
            event.put(EntityRegistry.TEXTURE_PER_BONE_EXAMPLE.get(),
                    PathfinderMob.createMobAttributes().add(Attributes.FOLLOW_RANGE, 16.0D)
                            .add(Attributes.MAX_HEALTH, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.25f).build());
        }
    }
}
