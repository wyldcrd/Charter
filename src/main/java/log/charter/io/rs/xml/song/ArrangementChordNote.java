package log.charter.io.rs.xml.song;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("chordNote")
public class ArrangementChordNote extends ArrangementNote {
	public ArrangementChordNote(final int time, final int string, final int fret) {
		this.time = time;
		this.string = string;
		this.fret = fret;
	}
}