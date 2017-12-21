package taskManager;

import javax.swing.table.DefaultTableModel;

/**
 * The MessageTableModel class is an extension of DefaultTableModel
 * which uses a Vector of Vectors to store cell value objects.
 * 
 * @version 12.20.2017
 */
public class MessageTableModel extends DefaultTableModel {

	/**
	 * Construct this message table model
	 * 
	 * @param columnNames the column names of this message table model
	 * @param i the number of rows in this message table model
	 */
	public MessageTableModel(String[] columnNames, int i) {
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
}