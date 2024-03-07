package log.charter.gui.chartPanelDrawers.instruments.guitar;

import java.awt.Graphics2D;
import java.util.Optional;

import log.charter.data.config.GraphicalConfig;
import log.charter.gui.chartPanelDrawers.data.EditorNoteDrawingData;
import log.charter.gui.chartPanelDrawers.data.HighlightData.HighlightLine;
import log.charter.gui.chartPanelDrawers.instruments.guitar.theme.modern.ModernHighwayDrawer;
import log.charter.song.Anchor;
import log.charter.song.ChordTemplate;
import log.charter.song.EventPoint;
import log.charter.song.HandShape;
import log.charter.song.Phrase;
import log.charter.song.SectionType;
import log.charter.song.ToneChange;
import log.charter.song.notes.ChordOrNote;

public interface HighwayDrawer {
	public static void reloadGraphics() {
		ModernHighwayDrawer.reloadGraphics();
	}

	public static HighwayDrawer getHighwayDrawer(final Graphics2D g, final int strings, final int time) {
		return switch (GraphicalConfig.theme) {
			case BASIC -> new DefaultHighwayDrawer(g, strings, time);
			case SQUARE -> new SquareHighwayDrawer(g, strings, time);
			case MODERN -> new ModernHighwayDrawer(g, strings, time);
		};
	}

	void addCurrentSection(Graphics2D g, SectionType section);

	void addCurrentSection(Graphics2D g, SectionType section, int nextSectionX);

	void addCurrentPhrase(Graphics2D g, Phrase phrase, String phraseName, int nextEventPointX);

	void addCurrentPhrase(Graphics2D g, Phrase phrase, String phraseName);

	void addEventPoint(Graphics2D g, EventPoint eventPoint, Phrase phrase, int x, boolean selected,
			boolean highlighted);

	void addEventPointHighlight(int x);

	void addToneChange(ToneChange toneChange, int x, boolean selected, boolean highlighted);

	void addToneChangeHighlight(int x);

	void addAnchor(Anchor anchor, int x, boolean selected, boolean highlighted);

	void addAnchorHighlight(int x);

	void addChordName(int x, String chordName);

	void addNote(final EditorNoteDrawingData note);

	void addSoundHighlight(int x, Optional<ChordOrNote> originalSound, Optional<ChordTemplate> template, int string);

	void addNoteAdditionLine(HighlightLine line);

	void addHandShape(int x, int length, boolean selected, boolean highlighted, HandShape handShape,
			ChordTemplate chordTemplate);

	void addHandShapeHighlight(int x, int length);

	void draw(Graphics2D g);

}
