package log.charter.gui.panes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import log.charter.data.config.Localization.Label;
import log.charter.gui.ChartPanelColors;
import log.charter.gui.ChartPanelColors.ColorLabel;
import log.charter.gui.CharterFrame;
import log.charter.gui.components.ParamsPane;
import log.charter.util.CollectionUtils.Pair;

public class ColorConfigPane extends ParamsPane {
	private static final long serialVersionUID = 1L;

	private class EmployeeTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		private static final String[] columnNames = { "Name", "R", "G", "B" };
		private static final Class<?>[] columnClasses = { String.class, Integer.class, Integer.class, Integer.class };

		@Override
		public String getColumnName(final int column) {
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(final int columnIndex) {
			return columnClasses[columnIndex];
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return colors.size();
		}

		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex) {
			final var colorLabel = colors.get(rowIndex);

			if (columnIndex == 0) {
				final String labelName = "GRAPHIC_CONFIG_" + colorLabel.a.name();
				try {
					return Label.valueOf(labelName).label();
				} catch (final Exception e) {
					return labelName;
				}
			}
			if (columnIndex == 1) {
				return colorLabel.b.getRed();
			}
			if (columnIndex == 2) {
				return colorLabel.b.getGreen();
			}
			if (columnIndex == 3) {
				return colorLabel.b.getBlue();
			}

			return null;
		}

		@Override
		public boolean isCellEditable(final int rowIndex, final int columnIndex) {
			return columnIndex > 0 && columnIndex <= 3;
		}

		@Override
		public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
			if (columnIndex <= 0 || columnIndex > 3) {
				return;
			}

			final Integer value = (Integer) aValue;
			if (value == null || value < 0 || value > 255) {
				return;
			}

			final var row = colors.get(rowIndex);
			final int[] rgbValues = { row.b.getRed(), row.b.getGreen(), row.b.getBlue() };
			rgbValues[columnIndex - 1] = value;
			row.b = new Color(rgbValues[0], rgbValues[1], rgbValues[2]);
		}
	}

	private static PaneSizes getSizes() {
		return new PaneSizes(400).rowHeight(20);
	}

	private final List<Pair<ColorLabel, Color>> colors = new ArrayList<>();

	public ColorConfigPane(final CharterFrame frame) {
		super(frame, Label.GRAPHIC_CONFIG_PANE, getSizes());

		for (final ColorLabel colorLabel : ColorLabel.values()) {
			colors.add(new Pair<>(colorLabel, colorLabel.color()));
		}

		final JTable table = new JTable(new EmployeeTableModel());
		for (int i = 1; i <= 3; i++) {
			final TableColumn column = table.getColumnModel().getColumn(1);
			column.setPreferredWidth(30);
			column.setMinWidth(30);
			column.setMaxWidth(30);
		}

		this.add(new JScrollPane(table), 20, getY(0), sizes.width - 40, getY(19) - getY(0));

		this.addDefaultFinish(20, this::onSave);
	}

	private void onSave() {
		colors.forEach(pair -> pair.a.setColor(pair.b));
		ChartPanelColors.saveColors();
	}

}
