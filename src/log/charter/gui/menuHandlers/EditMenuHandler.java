package log.charter.gui.menuHandlers;

import static java.awt.event.KeyEvent.VK_DELETE;

import javax.swing.JMenu;

import log.charter.data.ChartData;
import log.charter.data.managers.selection.Selection;
import log.charter.data.managers.selection.SelectionAccessor;
import log.charter.data.managers.selection.SelectionManager;
import log.charter.data.types.PositionType;
import log.charter.data.undoSystem.UndoSystem;
import log.charter.gui.CharterFrame;
import log.charter.gui.panes.GridPane;
import log.charter.gui.panes.SongOptionsPane;
import log.charter.song.Position;
import log.charter.util.CollectionUtils.ArrayList2;

class EditMenuHandler extends CharterMenuHandler {
	private ChartData data;
	private CharterFrame frame;
	private SelectionManager selectionManager;
	private UndoSystem undoSystem;

	void init(final ChartData data, final CharterFrame frame, final SelectionManager selectionManager,
			final UndoSystem undoSystem) {
		this.data = data;
		this.frame = frame;
		this.selectionManager = selectionManager;
		this.undoSystem = undoSystem;
	}

	@Override
	boolean isApplicable() {
		return !data.isEmpty;
	}

	@Override
	JMenu prepareMenu() {
		final JMenu menu = new JMenu("Edit");

		menu.add(createItem("Undo", ctrl('Z'), undoSystem::undo));
		menu.add(createItem("Redo", ctrl('R'), undoSystem::redo));

		menu.addSeparator();
		menu.add(createItem("Select all notes", ctrl('A'), this::selectAllNotes));
		menu.add(createItem("Delete", button(VK_DELETE), this::delete));
		menu.add(createItem("Copy", ctrl('C'), this::copy));
		menu.add(createItem("Paste", ctrl('V'), this::paste));

		menu.addSeparator();
		menu.add(createItem("Song options", () -> new SongOptionsPane(frame, data)));
		menu.add(createItem("Grid options", button('G'), () -> new GridPane(frame, data.songChart.beatsMap)));

		return menu;
	}

	private void selectAllNotes() {
		selectionManager.selectAllNotes();
	}

	private void delete() {
		boolean undoAdded = false;

		for (final PositionType type : PositionType.values()) {
			final SelectionAccessor<Position> selectedTypeAccessor = selectionManager.getSelectedAccessor(type);
			if (selectedTypeAccessor.isSelected()) {
				if (!undoAdded) {
					undoSystem.addUndo();
					undoAdded = true;
				}

				final ArrayList2<Selection<Position>> selected = selectedTypeAccessor.getSortedSelected();
				final ArrayList2<?> positions = type.getPositions(data);
				for (int i = selected.size() - 1; i >= 0; i--) {
					positions.remove(selected.get(i).id);
				}
			}
		}

		selectionManager.clear();
	}

	private void copy() {

	}

	private void paste() {

	}

}