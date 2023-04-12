package io.github.tt432.eyelib.common.bedrock.model;

import com.mojang.blaze3d.Blaze3D;
import io.github.tt432.eyelib.api.bedrock.AnimatableModel;
import io.github.tt432.eyelib.api.bedrock.animation.Animatable;
import io.github.tt432.eyelib.api.bedrock.animation.AnimationHolder;
import io.github.tt432.eyelib.api.bedrock.model.Bone;
import io.github.tt432.eyelib.api.bedrock.model.GeoModelProvider;
import io.github.tt432.eyelib.common.bedrock.BedrockResourceManager;
import io.github.tt432.eyelib.common.bedrock.EyelibLoadingException;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationEvent;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationProcessor;
import io.github.tt432.eyelib.common.bedrock.animation.manager.AnimationData;
import io.github.tt432.eyelib.common.bedrock.animation.pojo.AnimationFile;
import io.github.tt432.eyelib.common.bedrock.animation.pojo.SingleAnimation;
import io.github.tt432.eyelib.common.bedrock.model.element.GeoBone;
import io.github.tt432.eyelib.common.bedrock.model.element.GeoModel;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public abstract class AnimatedGeoModel<T extends Animatable> extends GeoModelProvider<T>
        implements AnimatableModel<T>, AnimationHolder<T> {
    @Getter
    private final AnimationProcessor<T> animationProcessor;
    private GeoModel currentModel;

    protected AnimatedGeoModel() {
        this.animationProcessor = new AnimationProcessor<>();
    }

    public void registerBone(GeoBone bone) {
        registerModelRenderer(bone);

        for (GeoBone childBone : bone.childBones) {
            registerBone(childBone);
        }
    }

    @Override
    public void setCustomAnimations(T animatable, @Nullable Object entity, int instanceId, @Nullable AnimationEvent<T> animationEvent) {
        Minecraft mc = Minecraft.getInstance();
        AnimationData manager = animatable.getFactory().getOrCreateAnimationData(instanceId);
        Entity currEntity;

        if (animatable instanceof Entity e) {
            currEntity = e;
        } else if (entity instanceof Entity e) {
            currEntity = e;
        } else {
            currEntity = null;
        }

        double currentTick = currEntity != null ? currEntity.tickCount : getCurrentTick();

        if (manager.getStartTick() == -1)
            manager.setStartTick(currentTick + mc.getFrameTime());

        if (!mc.isPaused() || manager.isShouldPlayWhilePaused()) {
            if (animatable instanceof LivingEntity || entity instanceof LivingEntity) {
                manager.setTick(currentTick + MinecraftForgeClient.getPartialTick());
            } else {
                manager.setTick(currentTick - manager.getStartTick());
            }

            this.seekTime = manager.getTick();
        }

        AnimationEvent<T> predicate = animationEvent;

        if (predicate == null) {
            predicate = new AnimationEvent<>(animatable, 0, 0,
                    MinecraftForgeClient.getPartialTick(), false, Collections.emptyList());
        }

        predicate.setAnimationTick(this.seekTime);

        if (!getAnimationProcessor().getModelRendererList().isEmpty())
            getAnimationProcessor().tickAnimation(animatable, instanceId, this.seekTime, predicate);
    }

    public void registerModelRenderer(Bone modelRenderer) {
        this.animationProcessor.registerModelRenderer(modelRenderer);
    }

    @Override
    public SingleAnimation getAnimation(String name, Animatable animatable) {
        AnimationFile animationFile = BedrockResourceManager.getInstance().getAnimations().get(this.getAnimationFileLocation((T) animatable));

        if (animationFile == null) {
            throw new EyelibLoadingException(this.getAnimationFileLocation((T) animatable),
                    "Could not find animation file. Please double check name.");
        }

        return animationFile.getAnimations().get(name);
    }

    @Override
    public GeoModel getModel(ResourceLocation location) {
        GeoModel model = super.getModel(location);

        if (model == null) {
            throw new EyelibLoadingException(location,
                    "Could not find model. If you are getting this with a built mod, please just restart your game.");
        }

        if (model != this.currentModel) {
            this.animationProcessor.clearModelRendererList();
            this.currentModel = model;

            for (GeoBone bone : model.topLevelBones) {
                registerBone(bone);
            }
        }

        return model;
    }

    @Override
    public double getCurrentTick() {
        return Blaze3D.getTime() * 20;
    }
}
