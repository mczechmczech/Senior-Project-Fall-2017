package taskManager;

import javax.swing.table.DefaultTableModel;

public class MessageTableModel extends DefaultTableModel {

	/**
	 * @param columnNames
	 * @param i
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