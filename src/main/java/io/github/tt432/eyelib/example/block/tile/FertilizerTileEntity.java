package io.github.tt432.eyelib.example.block.tile;

import io.github.tt432.eyelib.example.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import io.github.tt432.eyelib.api.bedrock.animation.Animatable;
import io.github.tt432.eyelib.api.bedrock.animation.PlayState;
import io.github.tt432.eyelib.common.bedrock.animation.builder.AnimationBuilder;
import io.github.tt432.eyelib.api.bedrock.animation.LoopType.Impl;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationController;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationEvent;
import io.github.tt432.eyelib.common.bedrock.animation.manager.AnimationData;
import io.github.tt432.eyelib.common.bedrock.animation.manager.AnimationFactory;
import io.github.tt432.eyelib.util.GeckoLibUtil;

public class FertilizerTileEntity extends BlockEntity implements Animatable {
	public AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private <E extends BlockEntity & Animatable> PlayState predicate(AnimationEvent<E> event) {
		AnimationController controller = event.getController();
		controller.transitionLengthTicks = 0;
		if (event.getAnimatable().getLevel().isRaining()) {
			controller.setAnimation(new AnimationBuilder().addAnimation("fertilizer.animation.deploy", Impl.LOOP)
					.addAnimation("fertilizer.animation.idle", Impl.LOOP));
		} else {
			controller.setAnimation(new AnimationBuilder().addAnimation("Botarium.anim.deploy", Impl.LOOP)
					.addAnimation("Botarium.anim.idle", Impl.LOOP));
		}
		return PlayState.CONTINUE;
	}

	public FertilizerTileEntity(BlockPos pos, BlockState state) {
		super(TileRegistry.FERTILIZER.get(), pos, state);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
