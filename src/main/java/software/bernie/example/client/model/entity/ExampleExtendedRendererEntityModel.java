package software.bernie.example.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.example.client.EntityResources;
import io.github.tt432.eyelib.api.animation.Animatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ExampleExtendedRendererEntityModel<T extends LivingEntity & Animatable> extends AnimatedGeoModel<T> {
	
	protected final ResourceLocation MODEL_RESLOC;
	protected final ResourceLocation TEXTURE_DEFAULT;
	protected final String ENTITY_REGISTRY_PATH_NAME;

	public ExampleExtendedRendererEntityModel(ResourceLocation model, ResourceLocation textureDefault,
			String entityName) {
		super();
		this.MODEL_RESLOC = model;
		this.TEXTURE_DEFAULT = textureDefault;
		this.ENTITY_REGISTRY_PATH_NAME = entityName;
	}

	@Override
	public ResourceLocation getAnimationFileLocation(T animatable) {
		return EntityResources.EXTENDED_ANIMATIONS;
	}

	@Override
	public ResourceLocation getModelLocation(T object) {
		return MODEL_RESLOC;
	}

	@Override
	public ResourceLocation getTextureLocation(T object) {
		return TEXTURE_DEFAULT;
	}

}
