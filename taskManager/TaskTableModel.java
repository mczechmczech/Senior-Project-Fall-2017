package taskManager;

import javax.swing.table.DefaultTableModel;

/**
 * The MessageTableModel class is an extension of DefaultTableModel
 * which uses a Vector of Vectors to store cell value objects.
 *
 *@version 12.20.2017
 */
public class TaskTableModel extends DefaultTableModel {

	/**
	 * Construct this task table model
	 * 
	 * @param columnNames the column names of this task table model
	 * @param i the number of rows in this task table model
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
