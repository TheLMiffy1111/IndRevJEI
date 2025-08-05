package thelm.indrevjei.gui.render;

import com.mojang.blaze3d.vertex.PoseStack;

import me.steven.indrev.gui.widgets.machines.WCustomBarKt;
import me.steven.indrev.recipes.machines.IRRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import thelm.jeidrawables.gui.render.AnimatedDrawable;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public class ProgressBarDrawable implements IDrawable {

	public static final ResourceDrawable RIGHT_PROCESS_EMPTY = new ResourceDrawable(WCustomBarKt.getRIGHT_PROCESS_EMPTY().image(), 0, 0, 18, 18, 18, 18);
	public static final ResourceDrawable LEFT_PROCESS_EMPTY = new ResourceDrawable(WCustomBarKt.getLEFT_PROCESS_EMPTY().image(), 0, 0, 18, 18, 18, 18);
	public static final ResourceDrawable DOWN_PROCESS_EMPTY = new ResourceDrawable(WCustomBarKt.getUP_PROCESS_EMPTY().image(), 0, 0, 18, 18, 18, 18);
	public static final ResourceDrawable RIGHT_PROCESS_FULL = new ResourceDrawable(WCustomBarKt.getRIGHT_PROCESS_FULL().image(), 0, 0, 18, 18, 18, 18);
	public static final ResourceDrawable LEFT_PROCESS_FULL = new ResourceDrawable(WCustomBarKt.getLEFT_PROCESS_FULL().image(), 0, 0, 18, 18, 18, 18);
	public static final ResourceDrawable DOWN_PROCESS_FULL = new ResourceDrawable(WCustomBarKt.getUP_PROCESS_FULL().image(), 0, 0, 18, 18, 18, 18);

	public final IDrawable base;
	public final IDrawable overlay;

	public ProgressBarDrawable(Direction direction, int millisPerCycle) {
		switch(direction) {
		case RIGHT -> {
			base = RIGHT_PROCESS_EMPTY;
			overlay = new AnimatedDrawable(RIGHT_PROCESS_FULL, AnimatedDrawable.Type.LEFT_FILL, millisPerCycle);
		}
		case LEFT -> {
			base = LEFT_PROCESS_EMPTY;
			overlay = new AnimatedDrawable(LEFT_PROCESS_FULL, AnimatedDrawable.Type.RIGHT_FILL, millisPerCycle);
		}
		case DOWN -> {
			base = DOWN_PROCESS_EMPTY;
			overlay = new AnimatedDrawable(DOWN_PROCESS_FULL, AnimatedDrawable.Type.TOP_FILL, millisPerCycle);
		}
		default -> throw new IllegalArgumentException("Unexpected value: " + direction);
		}
	}

	public static ProgressBarDrawable right(int duration) {
		return new ProgressBarDrawable(Direction.RIGHT, duration);
	}

	public static ProgressBarDrawable right(IRRecipe recipe) {
		return right(recipe.getTicks() * 50);
	}

	public static ProgressBarDrawable left(int duration) {
		return new ProgressBarDrawable(Direction.LEFT, duration);
	}

	public static ProgressBarDrawable left(IRRecipe recipe) {
		return left(recipe.getTicks() * 50);
	}

	public static ProgressBarDrawable down(int duration) {
		return new ProgressBarDrawable(Direction.DOWN, duration);
	}

	public static ProgressBarDrawable down(IRRecipe recipe) {
		return down(recipe.getTicks() * 50);
	}

	@Override
	public int getWidth() {
		return 18;
	}

	@Override
	public int getHeight() {
		return 18;
	}

	@Override
	public void draw(PoseStack poseStack, int xOffset, int yOffset) {
		base.draw(poseStack, xOffset, yOffset);
		overlay.draw(poseStack, xOffset, yOffset);
	}

	public enum Direction {
		RIGHT,
		LEFT,
		DOWN;
	}
}
