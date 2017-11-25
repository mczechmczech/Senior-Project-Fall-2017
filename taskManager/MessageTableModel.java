package taskManager;

import javax.swing.table.DefaultTableModel;

public class MessageTableModel extends DefaultTableModel {

	public MessageTableModel(String[] columnNames, int i) {
		super(columnNames, i);
	}

	@Override
	public String getColumnName(int col) {
	    return super.getColumnName(col);
	}
	    
	public boolean isCellEditable(int row, int column) {
	    return false;
	}
}