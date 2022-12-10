package log.charter.song;

import java.util.HashMap;
import java.util.Map;

import log.charter.io.rs.xml.song.SongArrangement;
import log.charter.io.rsc.xml.RocksmithChartProject;
import log.charter.util.CollectionUtils.ArrayList2;

public class BeatsMap {
	public int songLengthMs;

	public ArrayList2<Beat> beats = new ArrayList2<>();
	public ArrayList2<Event> events = new ArrayList2<>();
	public ArrayList2<Section> sections = new ArrayList2<>();
	public Map<String, Phrase> phrases = new HashMap<>();
	public ArrayList2<PhraseIteration> phraseIterations = new ArrayList2<>();

	/**
	 * creates beats map for new project
	 */
	public BeatsMap(final int songLength) {
		songLengthMs = songLength;

		beats.add(new Beat(0, 4, true));
		makeBeatsUntilSongEnd();

		phrases.put("COUNT", new Phrase(0, false));
		phrases.put("END", new Phrase(0, false));
		phraseIterations.add(new PhraseIteration(0, "COUNT"));
		phraseIterations.add(new PhraseIteration(songLength, "END"));
	}

	/**
	 * creates beats map for existing project
	 */
	public BeatsMap(final int songLengthMs, final RocksmithChartProject rocksmithChartProject) {
		this.songLengthMs = songLengthMs;

		beats = rocksmithChartProject.beats;
		events = rocksmithChartProject.events;
		sections = rocksmithChartProject.sections;
		phrases = rocksmithChartProject.phrases;
		phraseIterations = rocksmithChartProject.phraseIterations;
	}

	/**
	 * creates beats map for rs xml import
	 */
	public BeatsMap(final int songLengthMs, final SongArrangement songArrangement) {
		this.songLengthMs = songLengthMs;

		beats = Beat.fromEbeats(songArrangement.ebeats.list);
		events = Event.fromArrangement(beats, songArrangement.events.list);
		sections = Section.fromArrangementSections(songArrangement.sections.list);
		phrases = Phrase.fromArrangementPhrases(songArrangement.phrases.list);
		phraseIterations = PhraseIteration.fromArrangementPhraseIterations(songArrangement.phrases.list,
				songArrangement.phraseIterations.list);

		int beatsInMeasure = -1;
		int beatCount = 0;
		for (final Beat beat : beats) {
			if (beat.beatsInMeasure != beatsInMeasure) {
				beat.firstInMeasure = true;
				beatsInMeasure = beat.beatsInMeasure;
				beatCount = 0;
			}
			if (beatCount == beatsInMeasure) {
				beat.firstInMeasure = true;
				beatCount = 0;
			}

			beatCount++;
		}
	}

	private void makeBeatsUntilSongEnd() {
		Beat current = beats.getLast();
		if (current.position > songLengthMs) {
			beats.removeIf(beat -> beat.position >= songLengthMs);
			return;
		}

		Beat previous;
		int distance;
		if (beats.size() == 1) {
			previous = current;
			distance = 500;
		} else {
			previous = beats.get(beats.size() - 2);
			distance = current.position - previous.position;
			if (distance < 50) {
				distance = 50;
			}
		}

		final int beatsInMeasure = current.beatsInMeasure;
		int pos = current.position + distance;
		int beatInMeasure = 0;
		for (int i = beats.size() - 1; i >= 0; i++) {
			if (beats.get(i).firstInMeasure) {
				break;
			}
			beatInMeasure++;
		}

		while (pos < songLengthMs) {
			beatInMeasure++;
			previous = current;
			current = new Beat(pos, beatsInMeasure, beatInMeasure == beatsInMeasure);
			beats.add(current);

			pos += distance;
			if (beatInMeasure == beatsInMeasure) {
				beatInMeasure = 0;
			}
		}
	}

	public void fixFirstBeatInMeasures() {
		int previousBIM = -1;
		int count = 0;
		for (int i = 0; i < beats.size(); i++) {
			count++;
			final Beat beat = beats.get(i);
			if (beat.beatsInMeasure != previousBIM) {
				beat.firstInMeasure = true;
				previousBIM = beat.beatsInMeasure;
				count = 0;
			} else if (count == previousBIM) {
				beat.firstInMeasure = true;
				count = 0;
			}
		}
	}

	private void fixSpread(final int beatId) {
		// TODO fix spread of beats between chosen beat and last anchored one
//then from chosen to next anchored/end of song, then fix notes
		// if there's anchored, fix positions only
		// if it goes to the end, change to match distance between chosen beat and
		// previous for all next beats, then remove/add
	}

	public void moveBeat(final int beatId, final int newPosition) {
		final Beat movedBeat = beats.get(beatId);

		// TODO move beat's position, set anchor
		// TODO take notes on left and right side and move them accordingly
		// TODO don't move past 50* beats between this and last/next anchored (beat
		// shouldn't be shorter than 50 ms)
		fixSpread(beatId);
	}

	public void changeBeatsInMeasure(final int id, final int newBeatsInMeasure) {
		final Beat beat = beats.get(id);
		final int oldBeatsInMeasure = beat.beatsInMeasure;
		int count = oldBeatsInMeasure;
		while (id < beats.size() && beat.beatsInMeasure == oldBeatsInMeasure) {
			beat.beatsInMeasure = newBeatsInMeasure;
			if (count == newBeatsInMeasure) {
				beat.firstInMeasure = true;
				count = 0;
			}
			count++;
		}
	}

	public Beat getFirstBeatAfter(final int soundTime) {
		if (soundTime >= beats.getLast().position) {
			return null;
		}

		int minId = 0;
		int maxId = beats.size() - 1;
		while (minId != maxId) {
			final int id = (minId + maxId) / 2;
			if (beats.get(id).position <= soundTime) {
				minId = id + 1;
			} else {
				maxId = id;
			}
		}

		return beats.get(maxId);
	}

	public Beat getLastBeatBefore(final int soundTime) {
		if (soundTime <= beats.get(0).position) {
			return null;
		}

		int minId = 0;
		int maxId = beats.size() - 1;
		while (minId != maxId) {
			final int id = (minId + maxId) / 2;
			if (beats.get(id).position >= soundTime) {
				maxId = id - 1;
			} else {
				minId = id;
			}
		}

		return beats.get(maxId);
	}

}