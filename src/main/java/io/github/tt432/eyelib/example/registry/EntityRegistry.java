package io.github.tt432.eyelib.example.registry;

import io.github.tt432.eyelib.Eyelib;
import io.github.tt432.eyelib.example.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Eyelib.MOD_ID);

    public static final RegistryObject<EntityType<GeoExampleEntity>> GEO_EXAMPLE_ENTITY = buildEntity(
            GeoExampleEntity::new, GeoExampleEntity.class, .7F, 1.3F);
    public static final RegistryObject<EntityType<BikeEntity>> BIKE_ENTITY = buildEntity(BikeEntity::new,
            BikeEntity.class, 0.5f, 0.6F);
    public static final RegistryObject<EntityType<CarEntity>> CAR_ENTITY = buildEntity(CarEntity::new, CarEntity.class,
            1.5f, 1.5F);
    public static final RegistryObject<EntityType<ExtendedRendererEntity>> EXTENDED_RENDERER_EXAMPLE = buildEntity(
            ExtendedRendererEntity::new, ExtendedRendererEntity.class, 0.5F, 1.9F);
    public static final RegistryObject<EntityType<TexturePerBoneTestEntity>> TEXTURE_PER_BONE_EXAMPLE = buildEntity(
            TexturePerBoneTestEntity::new, TexturePerBoneTestEntity.class, 0.75F, 0.75F);

    public static <T extends Entity> RegistryObject<EntityType<T>> buildEntity(EntityType.EntityFactory<T> entity,
                                                                               Class<T> entityClass, float width, float height) {
        String name = entityClass.getSimpleName().toLowerCase();
        return ENTITIES.register(name,
                () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));
    }
}
