package prototype_MinimalFunctionality;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class PrototypeWindow {

	private int userID;
	private JFrame frmMainwindow;
	private JTextField projectNumTextField;
	private JTextField nameTextField;
	private JTextField dueDateTextField;
	private JTextField descriptionTextField;
	private JTextField notesTextField;
	private ArrayList<Task> tasks = new ArrayList<>();
	private JTable myTasksTable, allUserTasksTable, inboxTable, archiveTable, trashTable;
	private String[] columnNames = {"Project Number", "Name", "Date Due", "Assigned User", "Description", "Notes"};
	private DefaultTableModel myTasksModel = new DefaultTableModel(columnNames, 0) {
		@Override
		public String getColumnName(int col) {
		    return columnNames[col];
		}
		public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	};
	private DefaultTableModel defaultModel = new DefaultTableModel(columnNames, 0) {
		@Override
		public String getColumnName(int col) {
		    return columnNames[col];
		}
	};
	private JTextField assignedUserTextField;

	
	

	/**
	 * Create the application.
	 */
	public PrototypeWindow() {
		String url = "jdbc:mysql://localhost:3306/senior";
		String username = "root";
		String password = "development";
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Connecting database...");

				try (Connection connection = DriverManager.getConnection(url, username, password)) {
				    System.out.println("Database connected!");
				    String query = "SELECT * FROM user WHERE ";
					//PreparedStatement s = connection.prepareStatement(query);
					//s.execute();
				} catch (SQLException e) {
				    throw new IllegalStateException("Cannot connect the database!", e);
				}
				try {
					initialize();
					frmMainwindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		
		JTabbedPane tasksPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("TASKS", null, tasksPane, null);
		
		JPanel myTasksPanel = new JPanel();
		tasksPane.addTab("MY TASKS", null, myTasksPanel, null);
		myTasksPanel.setLayout(new BorderLayout(0, 0));
		
		myTasksTable = new JTable(myTasksModel);
		myTasksPanel.add(myTasksTable, BorderLayout.CENTER);
		myTasksPanel.add(myTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		
		JPanel allUserTasksPanel = new JPanel();
		tasksPane.addTab("ALL USER TASKS", null, allUserTasksPanel, null);
		allUserTasksPanel.setLayout(new BorderLayout(0, 0));
		
		allUserTasksTable = new JTable(myTasksModel);
		allUserTasksPanel.add(allUserTasksTable);
		allUserTasksPanel.add(allUserTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		JPanel createNewTaskPanel = new JPanel();
		tabbedPane.addTab("CREATE NEW..", null, createNewTaskPanel, null);
		GridBagLayout gbl_createNewTaskPanel = new GridBagLayout();
		gbl_createNewTaskPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_createNewTaskPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_createNewTaskPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_createNewTaskPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		createNewTaskPanel.setLayout(gbl_createNewTaskPanel);
		
		JLabel lblProjectNum = new JLabel("Project Number");
		GridBagConstraints gbc_ProjectNum = new GridBagConstraints();
		gbc_ProjectNum.insets = new Insets(0, 0, 5, 5);
		gbc_ProjectNum.gridx = 1;
		gbc_ProjectNum.gridy = 1;
		createNewTaskPanel.add(lblProjectNum, gbc_ProjectNum);
		
		projectNumTextField = new JTextField();
		GridBagConstraints gbc_projectNumTextField = new GridBagConstraints();
		gbc_projectNumTextField.insets = new Insets(0, 0, 5, 0);
		gbc_projectNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNumTextField.gridx = 3;
		gbc_projectNumTextField.gridy = 1;
		createNewTaskPanel.add(projectNumTextField, gbc_projectNumTextField);
		projectNumTextField.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		createNewTaskPanel.add(lblName, gbc_lblName);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 3;
		gbc_nameTextField.gridy = 2;
		createNewTaskPanel.add(nameTextField, gbc_nameTextField);
		
		JLabel lblDueDate = new JLabel("Due Date");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		createNewTaskPanel.add(lblDueDate, gbc_label);
		
		dueDateTextField = new JTextField();
		dueDateTextField.setColumns(10);
		GridBagConstraints gbc_dueDateTextField = new GridBagConstraints();
		gbc_dueDateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dueDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dueDateTextField.gridx = 3;
		gbc_dueDateTextField.gridy = 3;
		createNewTaskPanel.add(dueDateTextField, gbc_dueDateTextField);
		
		JLabel lblAssignedUser = new JLabel("Assigned User");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		createNewTaskPanel.add(lblAssignedUser, gbc_label_1);
		
		assignedUserTextField = new JTextField();
		assignedUserTextField.setColumns(10);
		GridBagConstraints gbc_assignedUserTextField = new GridBagConstraints();
		gbc_assignedUserTextField.insets = new Insets(0, 0, 5, 0);
		gbc_assignedUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_assignedUserTextField.gridx = 3;
		gbc_assignedUserTextField.gridy = 4;
		createNewTaskPanel.add(assignedUserTextField, gbc_assignedUserTextField);
		
		JLabel lblDescrip = new JLabel("Description");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 5;
		createNewTaskPanel.add(lblDescrip, gbc_label_2);
		
		descriptionTextField = new JTextField();
		descriptionTextField.setColumns(10);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 3;
		gbc_descriptionTextField.gridy = 5;
		createNewTaskPanel.add(descriptionTextField, gbc_descriptionTextField);
		
		JLabel lblNotes = new JLabel("Notes");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 6;
		createNewTaskPanel.add(lblNotes, gbc_label_3);
		
		notesTextField = new JTextField();
		notesTextField.setColumns(10);
		GridBagConstraints gbc_notesTextField = new GridBagConstraints();
		gbc_notesTextField.insets = new Insets(0, 0, 5, 0);
		gbc_notesTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_notesTextField.gridx = 3;
		gbc_notesTextField.gridy = 6;
		createNewTaskPanel.add(notesTextField, gbc_notesTextField);
		
		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 7;
		createNewTaskPanel.add(btnCreate, gbc_btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 7;
		createNewTaskPanel.add(btnCancel, gbc_btnCancel);
		
		btnCreate.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  if(!(nameTextField.getText().equals("")))
				  {
					    //tasks.add(new Task(projectNumTextField.getText(), nameTextField.getText(), dueDateTextField.getText(), assignedUserTextField.getText(), descriptionTextField.getText(), notesTextField.getText()));
					  	new SQLQueryBuilder(new Task(projectNumTextField.getText(), nameTextField.getText(), dueDateTextField.getText(), assignedUserTextField.getText(), descriptionTextField.getText(), notesTextField.getText())).addTask();
					  	getTasks();
					    projectNumTextField.setText("");
					    nameTextField.setText("");
					    dueDateTextField.setText("");
					    assignedUserTextField.setText("");
					    descriptionTextField.setText("");
					    notesTextField.setText("");
					    tabbedPane.setSelectedIndex(0);
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null, "A task name must be entered " + "\n" + "before a task can be created.");
				  }
				} 
				} );
		
		btnCancel.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    projectNumTextField.setText("");
				    nameTextField.setText("");
				    dueDateTextField.setText("");
				    assignedUserTextField.setText("");
				    descriptionTextField.setText("");
				    notesTextField.setText("");
				    tabbedPane.setSelectedIndex(0);
				  } 
				} );
		
		JPanel inboxPanel = new JPanel();
		tabbedPane.addTab("Inbox ()", null, inboxPanel, null);
		inboxPanel.setLayout(new BorderLayout(0, 0));
		
		inboxTable = new JTable(defaultModel);
		inboxPanel.add(inboxTable);
		inboxPanel.add(inboxTable.getTableHeader(), BorderLayout.NORTH);
		
		JPanel archivePanel = new JPanel();
		tabbedPane.addTab("ARCHIVE", null, archivePanel, null);
		archivePanel.setLayout(new BorderLayout(0, 0));
		
		archiveTable = new JTable(defaultModel);
		archivePanel.add(archiveTable);
		archivePanel.add(archiveTable.getTableHeader(), BorderLayout.NORTH);
		
		JPanel trashPanel = new JPanel();
		tabbedPane.addTab("TRASH", null, trashPanel, null);
		trashPanel.setLayout(new BorderLayout(0, 0));
		
		trashTable = new JTable(defaultModel);
		trashPanel.add(trashTable);
		trashPanel.add(trashTable.getTableHeader(), BorderLayout.NORTH);
		
		JPanel requestPanel = new JPanel();
		tabbedPane.addTab("REQUEST TASK", null, requestPanel, null);
		requestPanel.setLayout(null);
		
		String[] choices = { "--select one--", "All Users"};
	    final JComboBox<String> cb = new JComboBox(choices);
	    cb.setBounds(107, 65, 123, 25);
	    cb.setVisible(true);
	    requestPanel.add(cb);
		
		JButton btnRequestTask = new JButton("REQUEST TASK");
		btnRequestTask.setBounds(107, 101, 123, 25);
		requestPanel.add(btnRequestTask);
	
		JPanel LogoutPanel = new JPanel();
		tabbedPane.addTab("LOGOUT", null, LogoutPanel, null);
		LogoutPanel.setLayout(null);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(107, 107, 123, 25);
		LogoutPanel.add(btnLogout);
		btnLogout.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  new LoginWindow();
				  frmMainwindow.dispose();
				  } 
				} );
		
		getTasks();
	}

	/**
	 * Get all the tasks that are assigned to the logged in user
	 */
	void addTasksToTable(DefaultTableModel model) {
		model.setRowCount(0);
		
		tasks = new SQLQueryBuilder().getAllTasksForUser(userID);
		for(int i = 0; i < tasks.size(); i++)
		{
			
			String num = tasks.get(i).getProjectNum();
			String name = tasks.get(i).getName();
			String dateDue = tasks.get(i).getDateDue();
			String assignedUser = tasks.get(i).getAssignedUser();
			String description = tasks.get(i).getDescription();
			String notes = tasks.get(i).getNotes();
			
			Object[] entry = {num, name, dateDue, assignedUser, description, notes};
			
			model.addRow(entry);
		}
	}
	
	void getTasks()
	{
		
	}
	}
