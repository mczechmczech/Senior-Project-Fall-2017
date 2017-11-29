package taskManager;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TaskTable extends JTable{
	public TaskTable(DefaultTableModel model)
	{
		super(model);
		MainWindow.tables.add(this);
	}
}
