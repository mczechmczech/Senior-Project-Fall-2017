package taskManager;

import javax.swing.table.DefaultTableModel;

public class TaskTableModel extends DefaultTableModel {

	/**
	 * @param columnNames
	 * @param i
	 */
	public TaskTableModel(String[] columnNames, int i) {
		super(columnNames, i);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
		return super.getColumnName(col);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		return (column == 1) ? Integer.class : String.class;
	}
}
