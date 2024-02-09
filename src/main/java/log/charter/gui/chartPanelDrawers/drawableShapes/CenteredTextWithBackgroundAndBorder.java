package log.charter.gui.chartPanelDrawers.drawableShapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import log.charter.util.Position2D;

public class CenteredTextWithBackgroundAndBorder implements DrawableShape {
	public static ShapePositionWithSize getExpectedPositionAndSize(final Graphics g, final Position2D position,
			final Font font, final String text) {
		return CenteredTextWithBackground.getExpectedPositionAndSize(g, position, font, text).resized(-1, -1, 2, 2);
	}

	public static ShapeSize getExpectedSize(final Graphics g, final Font font, final String text) {
		final ShapeSize innerSize = CenteredTextWithBackground.getExpectedSize(g, font, text);
		return new ShapeSize(innerSize.width + 2, innerSize.height + 2);
	}

	private final CenteredTextWithBackground centeredTextWithBackground;
	private final Color borderColor;

	public CenteredTextWithBackgroundAndBorder(final Position2D position, final Font font, final String text,
			final Color textColor, final Color backgroundColor, final Color borderColor) {
		centeredTextWithBackground = new CenteredTextWithBackground(position, font, text, textColor, backgroundColor);
		this.borderColor = borderColor;
	}

	@Override
	public void draw(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		draw(g2, getPositionAndSize(g));
	}

	public ShapePositionWithSize getPositionAndSize(final Graphics g) {
		return centeredTextWithBackground.getPositionAndSize(g).resized(-1, -1, 2, 2);
	}

	public void draw(final Graphics2D g, final ShapePositionWithSize positionAndSize) {
		if (borderColor != null) {
			g.setColor(borderColor);
			g.fillRect(positionAndSize.x, positionAndSize.y, positionAndSize.width, positionAndSize.height);
		}

		centeredTextWithBackground.draw(g, positionAndSize.resized(1, 1, -2, -2));
	}

}