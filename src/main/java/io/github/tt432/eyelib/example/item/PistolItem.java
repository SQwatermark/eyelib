package io.github.tt432.eyelib.example.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.PacketDistributor;
import io.github.tt432.eyelib.example.GeckoLibMod;
import io.github.tt432.eyelib.example.client.renderer.item.PistolRender;
import io.github.tt432.eyelib.common.bedrock.animation.util.AnimationState;
import io.github.tt432.eyelib.api.bedrock.animation.Animatable;
import io.github.tt432.eyelib.api.bedrock.animation.PlayState;
import io.github.tt432.eyelib.common.bedrock.animation.builder.AnimationBuilder;
import io.github.tt432.eyelib.api.bedrock.animation.LoopType.Impl;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationController;
import io.github.tt432.eyelib.common.bedrock.animation.AnimationEvent;
import io.github.tt432.eyelib.common.bedrock.animation.manager.AnimationData;
import io.github.tt432.eyelib.common.bedrock.animation.manager.AnimationFactory;
import io.github.tt432.eyelib.network.EyelibNetworkHandler;
import io.github.tt432.eyelib.api.Syncable;
import io.github.tt432.eyelib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class PistolItem extends Item implements Animatable, Syncable {

	public AnimationFactory factory = GeckoLibUtil.createFactory(this);
	public String controllerName = "controller";
	public static final int ANIM_OPEN = 0;

	public PistolItem() {
		super(new Properties().tab(GeckoLibMod.geckolibItemGroup).stacksTo(1).durability(201));
		EyelibNetworkHandler.registerSyncable(this);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IItemRenderProperties() {
			private final BlockEntityWithoutLevelRenderer renderer = new PistolRender();

			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			Player playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				playerentity.getCooldowns().addCooldown(this, 5);
				if (!worldIn.isClientSide) {
					Arrow abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity = customeArrow(abstractarrowentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(),
							0.0F, 1.0F * 3.0F, 1.0F);

					abstractarrowentity.setBaseDamage(2.5);
					abstractarrowentity.tickCount = 35;
					abstractarrowentity.isNoGravity();

					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.addFreshEntity(abstractarrowentity);
				}
				if (!worldIn.isClientSide) {
					final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel) worldIn);
					final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
							.with(() -> playerentity);
					EyelibNetworkHandler.syncAnimation(target, this, id, ANIM_OPEN);
				}
			}
		}
	}

	public Arrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		Arrow arrowentity = new Arrow(worldIn, shooter);
		return arrowentity;
	}

	public static float getArrowVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	public Arrow customeArrow(Arrow arrow) {
		return arrow;
	}

	public <P extends Item & Animatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == ANIM_OPEN) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.STOPPED) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("firing", Impl.PLAY_ONCE));
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1))
						.withStyle(ChatFormatting.ITALIC));
	}
}