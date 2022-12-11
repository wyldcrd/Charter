package log.charter.sound;

public class RotatingRepeatingPlayer {

	private int nextPlayer = 0;
	private final RepeatingPlayer[] players;

	public RotatingRepeatingPlayer(final MusicData musicData, final int players) {
		this.players = new RepeatingPlayer[players];
		for (int i = 0; i < players; i++) {
			this.players[i] = new RepeatingPlayer(musicData);
		}
	}

	public void play() {
		players[nextPlayer++].queuePlaying();
		nextPlayer = nextPlayer % players.length;
	}
}