package log.charter.gui;

import static log.charter.util.ColorUtils.setAlpha;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import log.charter.data.config.Localization.Label;
import log.charter.io.Logger;
import log.charter.util.ColorUtils;
import log.charter.util.RW;
import log.charter.util.Utils;

public class ChartPanelColors {
	public enum ColorLabel {
		BASE_1(0, 96, 96), //
		BASE_2(32, 128, 128), //

		BASE_BG_0(11, 12, 13), //
		BASE_BG_1(20, 21, 23), //
		BASE_BG_2(31, 32, 35), //
		BASE_BG_3(78, 81, 87), //
		BASE_BG_4(128, 128, 128), //
		BASE_BG_5(255, 255, 255), //

		BASE_DARK_TEXT(78, 81, 87), //
		BASE_TEXT(255, 255, 255), //

		BASE_HIGHLIGHT(53, 116, 240), //
		BASE_BUTTON(112, 112, 112), //
		BASE_BORDER(82, 82, 82), //

		NOTE_BACKGROUND(16, 16, 16), //
		NOTE_ADD_LINE(0, 255, 0), //
		LANE(128, 128, 128), //
		MAIN_BEAT(222, 222, 255, 50), //
		SECONDARY_BEAT(222, 222, 255, 50), //
		BEAT_MARKER(222, 222, 0), GRID(222, 222, 255, 25), //
		BOOKMARK(64, 64, 255), //
		REPEAT_MARKER(33, 217, 245), //
		MARKER(255, 255, 255), //
		MARKER_VIEW_AREA(122, 122, 122), //
		SECTION_NAME_BG(165, 54, 178), //
		PHRASE_NAME_BG(179, 118, 54), //
		SECTION_COLOR(165, 54, 178), //
		PHRASE_COLOR(179, 118, 54), //
		EVENT_BG(98, 72, 94), //
		HIGHLIGHT(122, 122, 122), //
		SELECT(41, 188, 254), //
		VOCAL_SELECT(41, 188, 254), //
		WAVEFORM_COLOR(64, 128, 128), WAVEFORM_RMS_COLOR(255, 128, 255),

		NOTE_FLAG_MARKER(255, 255, 255), //
		SLIDE_NORMAL_FRET_BG(255, 255, 255), //
		SLIDE_NORMAL_FRET_TEXT(0, 0, 0), //
		SLIDE_UNPITCHED_FRET_BG(255, 128, 128), //
		SLIDE_UNPITCHED_FRET_TEXT(0, 0, 0), //
		NOTE_FULL_MUTE(128, 128, 128), //
		HAMMER_ON(255, 255, 255), //
		PULL_OFF(255, 255, 255), //
		TAP(0, 0, 0), //
		HARMONIC(255, 255, 255), //
		PINCH_HARMONIC(0, 0, 0), //

		NOTE_0(237, 0, 0), //
		NOTE_1(242, 215, 6), //
		NOTE_2(37, 178, 255), //
		NOTE_3(255, 135, 10), //
		NOTE_4(133, 231, 71), //
		NOTE_5(210, 44, 248), //
		NOTE_6(62, 229, 223), //
		NOTE_7(182, 182, 182), //
		NOTE_8(208, 57, 57), //

		ANCHOR(208, 57, 57), //
		HAND_SHAPE(49, 87, 167), //
		HAND_SHAPE_ARPEGGIO(133, 89, 183), //
		TONE_CHANGE(182, 182, 182), //

		VOCAL_LINE_BACKGROUND(56, 56, 64), //
		VOCAL_LINE_TEXT(255, 255, 255), //
		VOCAL_TEXT(210, 210, 210), //
		VOCAL_NOTE(221, 114, 41), //
		VOCAL_NOTE_WORD_PART(173, 89, 33, 192), //

		PREVIEW_3D_BACKGROUND(0, 0, 0), //
		PREVIEW_3D_FRET(32, 32, 32), //
		PREVIEW_3D_ACTIVE_FRET(192, 192, 192), //
		PREVIEW_3D_HIGHLIGHTED_FRET(255, 160, 0), //
		PREVIEW_3D_CHORD_BOX(0, 210, 213), //
		PREVIEW_3D_FULL_MUTE(255, 255, 255), //
		PREVIEW_3D_PALM_MUTE(0, 0, 0), //
		PREVIEW_3D_CHORD_FULL_MUTE(0, 128, 160), //
		PREVIEW_3D_CHORD_PALM_MUTE(0, 128, 160), //
		PREVIEW_3D_ANCHOR(0, 0, 255, 64), //
		PREVIEW_3D_LANE(37, 144, 232, 64), //
		PREVIEW_3D_LANE_DOTTED(24, 92, 148, 64), //
		PREVIEW_3D_LANE_BORDER(7, 146, 143), //
		PREVIEW_3D_ARPEGGIO(192, 64, 255), //
		PREVIEW_3D_BEAT(15, 59, 94), //
		PREVIEW_3D_LYRICS(255, 255, 255), //
		PREVIEW_3D_LYRICS_PASSED(221, 114, 41); //

		private final Color defaultColor;

		private ColorLabel(final int r, final int g, final int b) {
			defaultColor = new Color(r, g, b);
		}

		private ColorLabel(final int r, final int g, final int b, final int a) {
			defaultColor = new Color(r, g, b, a);
		}

		public Color color() {
			return colors.getOrDefault(this, defaultColor);
		}

		public void setColor(final Color color) {
			colors.put(this, color);
		}

		public Color colorWithAlpha(final int alpha) {
			return setAlpha(color(), alpha);
		}

		public String label() {
			final String labelName = "COLOR_" + name();
			try {
				return Label.valueOf(labelName).label();
			} catch (final Exception e) {
				return labelName;
			}
		}
	}

	public enum StringColorLabelType {
		LANE, LANE_BRIGHT, NOTE, NOTE_TAIL, NOTE_ACCENT
	}

	public static Color getStringBasedColor(final StringColorLabelType type, final int string, final int strings) {
		final int stringId = Utils.stringId(string, strings);

		final Color base = ColorLabel.valueOf("NOTE_" + stringId).color();

		switch (type) {
			case LANE:
				return ColorUtils.multiplyColor(base, 0.8); //
			case LANE_BRIGHT:
				return ColorUtils.multiplyColor(base, 1); //
			case NOTE:
				return base;
			case NOTE_TAIL:
				return ColorUtils.multiplyColor(base, 0.66); //
			case NOTE_ACCENT:
				return ColorUtils.multiplyColor(base, 0.8); //
			default:
				return base;
		}
	}

	private static final String colorFilePath = new File(RW.getProgramDirectory(), "colors.txt").getAbsolutePath();

	private static final Map<ColorLabel, Color> colors = new HashMap<>();

	public static void saveColors() {
		final Map<String, String> config = new HashMap<>();

		for (final Entry<ColorLabel, Color> colorEntry : colors.entrySet()) {
			final Color c = colorEntry.getValue();
			final String r = Integer.toHexString(c.getRed());
			final String g = Integer.toHexString(c.getGreen());
			final String b = Integer.toHexString(c.getBlue());
			final String a = Integer.toHexString(c.getAlpha());
			config.put(colorEntry.getKey().name(), r + " " + g + " " + b + " " + a);
		}

		RW.writeConfig(colorFilePath, config);
	}

	static {
		for (final ColorLabel colorLabel : ColorLabel.values()) {
			colors.put(colorLabel, colorLabel.defaultColor);
		}

		final Map<String, String> config = RW.readConfig(colorFilePath);
		for (final Entry<String, String> configEntry : config.entrySet()) {
			try {
				final ColorLabel colorLabel = ColorLabel.valueOf(configEntry.getKey());
				final String[] rgb = configEntry.getValue().split(" ");
				final Color color;
				if (rgb.length == 3) {
					color = new Color(Integer.valueOf(rgb[0], 16), Integer.valueOf(rgb[1], 16),
							Integer.valueOf(rgb[2], 16));
				} else if (rgb.length == 4) {
					color = new Color(Integer.valueOf(rgb[0], 16), Integer.valueOf(rgb[1], 16),
							Integer.valueOf(rgb[2], 16), Integer.valueOf(rgb[3], 16));
				} else {
					color = new Color(0, 0, 0, 255);
				}

				colors.put(colorLabel, color);
			} catch (final Exception e) {
				Logger.error("Couldn't load color " + configEntry.getKey() + "=" + configEntry.getValue(), e);
			}
		}

		saveColors();
	}
}
