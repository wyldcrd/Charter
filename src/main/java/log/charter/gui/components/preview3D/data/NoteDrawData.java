package log.charter.gui.components.preview3D.data;

import static java.lang.Math.min;

import java.util.List;

import log.charter.data.song.BendValue;
import log.charter.data.song.enums.HOPO;
import log.charter.data.song.enums.Harmonic;
import log.charter.data.song.enums.Mute;
import log.charter.data.song.notes.Chord;
import log.charter.data.song.notes.ChordNote;
import log.charter.data.song.notes.Note;

public class NoteDrawData {
	public final int position;
	public final int endPosition;
	public final int string;
	public final int fret;
	public final boolean accent;
	public final Mute mute;
	public final HOPO hopo;
	public final Harmonic harmonic;
	public final double prebend;
	public final List<BendValue> bendValues;
	public final Integer slideTo;
	public final boolean unpitchedSlide;
	public final boolean vibrato;
	public final boolean tremolo;
	public final boolean linkPrevious;

	public final int originalPosition;
	public final int trueLength;
	public final boolean withoutHead;
	public final boolean isChordNote;

	public NoteDrawData(final int notePosition, final int noteEndPosition, final int minPosition, final int maxPosition,
			final Note note, final boolean linkPrevious) {
		originalPosition = notePosition;
		trueLength = noteEndPosition - notePosition;

		if (originalPosition < minPosition) {
			position = minPosition;
			withoutHead = true;
		} else {
			position = originalPosition;
			withoutHead = linkPrevious;
		}
		endPosition = min(maxPosition, noteEndPosition);

		string = note.string;
		fret = note.fret;
		accent = note.accent;
		mute = note.mute;
		hopo = note.hopo;
		harmonic = note.harmonic;
		bendValues = note.bendValues;
		slideTo = note.slideTo;
		unpitchedSlide = note.unpitchedSlide;
		vibrato = note.vibrato;
		tremolo = note.tremolo;
		this.linkPrevious = linkPrevious;

		isChordNote = false;

		prebend = !bendValues.isEmpty() && bendValues.get(0).position().compareTo(note.position()) == 0 //
				? bendValues.get(0).bendValue.doubleValue()//
				: 0;
	}

	public NoteDrawData(final int chordPosition, final int chordNoteEndPosition, final int minPosition,
			final int maxPosition, final Chord chord, final int string, final int fret, final ChordNote chordNote,
			final boolean linkPrevious, final boolean shouldHaveLength) {
		originalPosition = chordPosition;
		trueLength = shouldHaveLength ? chordNoteEndPosition - chordPosition : 0;

		if (originalPosition < minPosition) {
			position = minPosition;
			withoutHead = true;
		} else {
			position = originalPosition;
			withoutHead = linkPrevious;
		}
		endPosition = min(maxPosition, chordNoteEndPosition);
		this.string = string;
		this.fret = fret;
		accent = chord.accent;
		mute = chordNote.mute;
		hopo = chordNote.hopo;
		harmonic = chordNote.harmonic;
		bendValues = chordNote.bendValues;
		slideTo = chordNote.slideTo;
		unpitchedSlide = chordNote.unpitchedSlide;
		vibrato = chordNote.vibrato;
		tremolo = chordNote.tremolo;
		this.linkPrevious = linkPrevious;

		isChordNote = !chord.splitIntoNotes;

		prebend = !bendValues.isEmpty() && bendValues.get(0).position().compareTo(chord.position()) == 0 //
				? bendValues.get(0).bendValue.doubleValue()//
				: 0;
	}

	public NoteDrawData(final int truePosition, final int position, final int endPosition, final NoteDrawData note) {
		originalPosition = truePosition;
		trueLength = note.trueLength;

		this.position = position;
		this.endPosition = endPosition;

		string = note.string;
		fret = note.fret;
		accent = note.accent;
		mute = note.mute;
		hopo = note.hopo;
		harmonic = note.harmonic;
		bendValues = note.bendValues;
		slideTo = note.slideTo;
		unpitchedSlide = note.unpitchedSlide;
		vibrato = note.vibrato;
		tremolo = note.tremolo;

		withoutHead = note.linkPrevious || position > truePosition;
		linkPrevious = note.linkPrevious;
		isChordNote = note.isChordNote;
		prebend = note.prebend;
	}
}
