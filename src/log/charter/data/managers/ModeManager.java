package log.charter.data.managers;

import java.util.HashMap;
import java.util.Map;

import log.charter.data.ChartData;
import log.charter.data.EditMode;
import log.charter.data.managers.modes.GuitarModeHandler;
import log.charter.data.managers.modes.ModeHandler;
import log.charter.data.managers.modes.VocalModeHandler;
import log.charter.data.managers.selection.SelectionManager;
import log.charter.data.undoSystem.UndoSystem;
import log.charter.gui.CharterFrame;
import log.charter.gui.handlers.KeyboardHandler;

public class ModeManager {
	public EditMode editMode = EditMode.GUITAR;

	private final GuitarModeHandler guitarModeHandler = new GuitarModeHandler();
	private final VocalModeHandler vocalModeHandler = new VocalModeHandler();

	private final Map<EditMode, ModeHandler> modeHandlers = new HashMap<>();

	public ModeManager() {
		modeHandlers.put(EditMode.GUITAR, guitarModeHandler);
		modeHandlers.put(EditMode.VOCALS, vocalModeHandler);
	}

	public void init(final ChartData data, final CharterFrame frame, final KeyboardHandler keyboardHandler,
			final SelectionManager selectionManager, final UndoSystem undoSystem) {
		guitarModeHandler.init(data, frame, keyboardHandler);
		vocalModeHandler.init(data, frame, keyboardHandler, selectionManager, undoSystem);
	}

	public ModeHandler getHandler() {
		return modeHandlers.get(editMode);
	}
}