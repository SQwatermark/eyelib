package io.github.tt432.eyelib.common.bedrock.animation.manager;

import io.github.tt432.eyelib.api.bedrock.animation.Animatable;

public abstract class AnimationFactory {
    protected final Animatable animatable;

    /**
     * use {@code GeckolibUtil#createFactory(IAnimatable)}
     *
     * @param animatable The animatable object the factory is for
     */
    protected AnimationFactory(Animatable animatable) {
        this.animatable = animatable;
    }

    /**
     * This creates or gets the cached animation manager for any unique ID. For
     * itemstacks, this is typically a hashcode of their nbt. For entities it should
     * be their unique uuid. For tile entities you can use nbt or just one constant
     * value since they are not singletons.
     *
     * @param uniqueID A unique integer ID. For every ID the same animation manager
     *                 will be returned.
     * @return the animatable manager
     */
    public abstract AnimationData getOrCreateAnimationData(int uniqueID);
}
