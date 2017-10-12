package prototype;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JTable;

public class PrototypeWindow {

	private JFrame frmMainwindow;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private ArrayList<Task> tasks = new ArrayList<>();
	private JTable table;
	private String[] columnNames = {"Name", "Date Due", "Assigned User", "Description", "Notes"};
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		@Override
		public String getColumnName(int col) {
		    return columnNames[col];
		}
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrototypeWindow window = new PrototypeWindow();
					window.frmMainwindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the application.
	 */
	public PrototypeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMainwindow = new JFrame();
		frmMainwindow.setTitle("MainWindow");
		frmMainwindow.setBounds(100, 100, 450, 300);
		frmMainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainwindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frmMainwindow.getContentPane().add(tabbedPane);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("TASKS", null, tabbedPane_1, null);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("ALL TASKS", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(model);
		panel.add(table, BorderLayout.CENTER);
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("IN PROGRESS", null, panel_4, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("COMPLETED TASKS", null, panel_2, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("CREATE NEW..", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 1;
		panel_1.add(lblName, gbc_lblName);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("Due Date");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 2;
		panel_1.add(label, gbc_label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		panel_1.add(textField_1, gbc_textField_1);
		
		JLabel label_1 = new JLabel("Assigned User");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 3;
		panel_1.add(label_1, gbc_label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 3;
		panel_1.add(textField_2, gbc_textField_2);
		
		JLabel label_2 = new JLabel("Description");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 4;
		panel_1.add(label_2, gbc_label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 4;
		panel_1.add(textField_3, gbc_textField_3);
		
		JLabel label_3 = new JLabel("Notes");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 5;
		panel_1.add(label_3, gbc_label_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 5;
		panel_1.add(textField_4, gbc_textField_4);
		
		JButton btnAddTask = new JButton("Add Task");
		GridBagConstraints gbc_btnAddTask = new GridBagConstraints();
		gbc_btnAddTask.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddTask.gridx = 1;
		gbc_btnAddTask.gridy = 7;
		panel_1.add(btnAddTask, gbc_btnAddTask);
		
		btnAddTask.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    tasks.add(new Task(textField.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText()));
				    getTasks();
				  } 
				} );
	
		JPanel panel_8 = new JPanel();
		tabbedPane.addTab("LOGOUT", null, panel_8, null);
		panel_8.setLayout(null);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(112, 105, 81, 25);
		panel_8.add(btnLogout);
	}

	void getTasks() {
		model.setRowCount(0);
		for(int i = 0; i < tasks.size(); i++)
		{
			String name = tasks.get(i).getName();
			String dateDue = tasks.get(i).getDateDue();
			String assignedUser = tasks.get(i).getAssignedUser();
			String description = tasks.get(i).getDescription();
			String notes = tasks.get(i).getNotes();
			
			Object[] entry = {name, dateDue, assignedUser, description, notes};
			
			model.addRow(entry);
		}
	}
}
