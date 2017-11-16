package prototype_MinimalFunctionality;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;

public class PrototypeWindow {

	private int userID;
	private JFrame frmMainwindow;
	private JTextField projectNumTextField;
	private JTextField nameTextField;
	private JTextField dueDateTextField;
	private JTextField descriptionTextField;
	private JTextField notesTextField;
	private ArrayList<Task> tasks = new ArrayList<>();
	private ArrayList<Task> myTasks, archiveTasks, allUserTasks, inboxTasks, trashTasks, allArchiveTasks = new ArrayList<>();
	private ArrayList<String> users = new ArrayList<>();
	private JTable myTasksTable, allUserTasksTable, inboxTable, archiveTable, trashTable, allUserArchiveTable;
	private String[] columnNames = {"Task ID", "#", "Name", "Date Due", "Assigned User", "Description", "Notes", "Completion"};
	private DefaultTableModel tasksModel = new TaskTableModel(columnNames, 0);
	private DefaultTableModel allTasksModel = new TaskTableModel(columnNames, 0);
	private DefaultTableModel inboxModel = new TaskTableModel(columnNames, 0);
	private DefaultTableModel archiveModel = new TaskTableModel(columnNames, 0);
	private DefaultTableModel defaultModel = new TaskTableModel(columnNames, 0);
	private DefaultTableModel allArchiveModel = new TaskTableModel(columnNames, 0);
	private DefaultTableModel searchModel;
	private JTextField searchText;
	private JComboBox<String> assignedUserTextField;
	private JTabbedPane tabbedPane;
	private DefaultComboBoxModel<String> assignedUserList = new DefaultComboBoxModel<String>();
	private java.util.Date javaDate;
	private java.sql.Date sqlDate;

	/**
	 * Create the application.
	 * 
	 * @param name The username of the logged in user
	 */
	public PrototypeWindow(String name) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				System.out.println("Connecting database...");
				
				userID = new SQLQueryBuilder().getIDFromUserName(name);
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
		frmMainwindow.setSize(1600, 800);
		frmMainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainwindow.getContentPane().setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frmMainwindow.getContentPane().add(tabbedPane);
		
		JTabbedPane tasksPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("TASKS", null, tasksPane, null);
		
		JPanel myTasksPanel = new JPanel();
		myTasksPanel.setLayout(new BorderLayout(0, 0));
		tasksPane.addTab("MY TASKS", null, myTasksPanel, null);
		myTasksPanel.setLayout(new BorderLayout(0, 0));
		
		myTasksTable = new JTable(tasksModel);
		myTasksPanel.add(new JScrollPane(myTasksTable), BorderLayout.CENTER);
		myTasksPanel.add(myTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		myTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
		            int row = target.getSelectedRow();
					new EditTaskWindow(myTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		myTasksPanel.add(new JScrollPane(myTasksTable), BorderLayout.CENTER);
		myTasksPanel.add(myTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		//hides taskID column from user
		TableColumnModel hiddenColMyTasks = myTasksTable.getColumnModel();
		hiddenColMyTasks.removeColumn(hiddenColMyTasks.getColumn(0));
		
		
		JPanel allUserTasksPanel = new JPanel();
		allUserTasksPanel.setLayout(new BorderLayout(0, 0));
		tasksPane.addTab("ALL USER TASKS", null, allUserTasksPanel, null);
		allUserTasksPanel.setLayout(new BorderLayout(0, 0));
		
		allUserTasksTable = new JTable(allTasksModel);
		allUserTasksPanel.add(new JScrollPane(allUserTasksTable));
		allUserTasksPanel.add(allUserTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		allUserTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
		            int row = target.getSelectedRow();
					new EditTaskWindow(allUserTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		
		//hides taskID column from user
		TableColumnModel hiddenColAllTasks = allUserTasksTable.getColumnModel();
		hiddenColAllTasks.removeColumn(hiddenColAllTasks.getColumn(0));
		
		JPanel createNewTaskPanel = new JPanel();
		tabbedPane.addTab("CREATE NEW..", null, createNewTaskPanel, null);
		GridBagLayout gbl_createNewTaskPanel = new GridBagLayout();
		gbl_createNewTaskPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_createNewTaskPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_createNewTaskPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_createNewTaskPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_dueDate = new GridBagConstraints();
		gbc_dueDate.insets = new Insets(0, 0, 5, 5);
		gbc_dueDate.gridx = 1;
		gbc_dueDate.gridy = 3;
		createNewTaskPanel.add(lblDueDate, gbc_dueDate);
		
		//dueDateTextField = new JTextField();
		//dueDateTextField.setColumns(10);
		DatePickerSettings ds = new DatePickerSettings();
		ds.setFormatForDatesCommonEra("yyyy/MM/dd");
		DatePicker dp = new DatePicker(ds);
		GridBagConstraints gbc_dueDateTextField = new GridBagConstraints();
		gbc_dueDateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dueDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dueDateTextField.gridx = 3;
		gbc_dueDateTextField.gridy = 3;
		createNewTaskPanel.add(dp, gbc_dueDateTextField);
		
		JLabel lblAssignedUser = new JLabel("Assigned User");
		GridBagConstraints gbc_assignedUser = new GridBagConstraints();
		gbc_assignedUser.insets = new Insets(0, 0, 5, 5);
		gbc_assignedUser.gridx = 1;
		gbc_assignedUser.gridy = 4;
		createNewTaskPanel.add(lblAssignedUser, gbc_assignedUser);
		
		assignedUserTextField = new JComboBox<String>();
		assignedUserTextField.setEditable(true);
		assignedUserTextField.setEnabled(true);
		AutoCompletion.enable(assignedUserTextField);
		//assignedUserTextField.setColumns(10);
		GridBagConstraints gbc_assignedUserTextField = new GridBagConstraints();
		gbc_assignedUserTextField.insets = new Insets(0, 0, 5, 0);
		gbc_assignedUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_assignedUserTextField.gridx = 3;
		gbc_assignedUserTextField.gridy = 4;
		createNewTaskPanel.add(assignedUserTextField, gbc_assignedUserTextField);
		
		JLabel lblDescrip = new JLabel("Description");
		GridBagConstraints gbc_description = new GridBagConstraints();
		gbc_description.insets = new Insets(0, 0, 5, 5);
		gbc_description.gridx = 1;
		gbc_description.gridy = 5;
		createNewTaskPanel.add(lblDescrip, gbc_description);
		
		descriptionTextField = new JTextField();
		descriptionTextField.setColumns(10);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 3;
		gbc_descriptionTextField.gridy = 5;
		createNewTaskPanel.add(descriptionTextField, gbc_descriptionTextField);
		
		JLabel lblNotes = new JLabel("Notes");
		GridBagConstraints gbc_notes = new GridBagConstraints();
		gbc_notes.insets = new Insets(0, 0, 5, 5);
		gbc_notes.gridx = 1;
		gbc_notes.gridy = 6;
		createNewTaskPanel.add(lblNotes, gbc_notes);
		
		notesTextField = new JTextField();
		notesTextField.setColumns(10);
		GridBagConstraints gbc_notesTextField = new GridBagConstraints();
		gbc_notesTextField.insets = new Insets(0, 0, 5, 0);
		gbc_notesTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_notesTextField.gridx = 3;
		gbc_notesTextField.gridy = 6;
		createNewTaskPanel.add(notesTextField, gbc_notesTextField);
		
		String[] completion = { "0%", "25%", "50%", "75%", "100%"};
		final JComboBox<String> cbPercentComplete = new JComboBox(completion);
		cbPercentComplete.setEditable(true);
		cbPercentComplete.setBounds(107, 65, 123, 25);
		cbPercentComplete.setVisible(true);
		createNewTaskPanel.add(cbPercentComplete);
		
		//only allows digits to be entered in the percent complete combo box
				cbPercentComplete.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
		            public void keyTyped(KeyEvent e) {
		                char c = e.getKeyChar();
		                if (cbPercentComplete.getEditor().getItem().toString().length() < 4) 
		                {
		                    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) 
		                    {
		                        frmMainwindow.getToolkit().beep();
		                        e.consume();
		                    }
		                } 
		                else 
		                { 
		                    e.consume();
		                }
		                
		                //check to see if percent symbol is still in combo box string
		                //if it isn't, automatically append it to combo box string
		                if(!((cbPercentComplete.getEditor().getItem().toString()).contains("%")))
		                {
		                	cbPercentComplete.getEditor().setItem(cbPercentComplete.getEditor().getItem().toString().concat("%"));
		                	frmMainwindow.getToolkit().beep();
		                }
		            }
		        });
		
		JLabel lblPercentComplete = new JLabel("Percent Complete:");
		GridBagConstraints gbc_PercentComplete = new GridBagConstraints();
		gbc_PercentComplete.gridx = 1;
		gbc_PercentComplete.gridy = 7;
		gbc_PercentComplete.insets = new Insets(0, 0, 5, 5);
		createNewTaskPanel.add(lblPercentComplete, gbc_PercentComplete);
		GridBagConstraints gbc_cbPercentComplete = new GridBagConstraints();
		gbc_cbPercentComplete.insets = new Insets(0, 0, 5, 0);
		gbc_cbPercentComplete.gridx = 3;
		gbc_cbPercentComplete.gridy = 7;
		createNewTaskPanel.add(cbPercentComplete, gbc_cbPercentComplete);
		
		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 8;
		createNewTaskPanel.add(btnCreate, gbc_btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 8;
		createNewTaskPanel.add(btnCancel, gbc_btnCancel);
		
		btnCancel.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    projectNumTextField.setText("");
				    nameTextField.setText("");
				    dueDateTextField.setText("");
				    assignedUserTextField.setSelectedItem("");
				    descriptionTextField.setText("");
				    notesTextField.setText("");
				    cbPercentComplete.setSelectedIndex(0);
				    tabbedPane.setSelectedIndex(0);
				  } 
				} );
		
		btnCreate.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  if(!(nameTextField.getText().equals("")))
				  {				    
					  try {
						javaDate = (new SimpleDateFormat("yyyy/MM/dd")).parse(dp.getText());
						sqlDate = new java.sql.Date(javaDate.getTime());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					  new SQLQueryBuilder(new Task(projectNumTextField.getText(), nameTextField.getText(), sqlDate, (String)assignedUserTextField.getSelectedItem(), descriptionTextField.getText(), notesTextField.getText(), (String) cbPercentComplete.getSelectedItem(), true)).addTask(userID);
					  getTasks();
					  projectNumTextField.setText("");
					  nameTextField.setText("");
					  dp.setText("");
					  assignedUserTextField.setSelectedItem("");
					  descriptionTextField.setText("");
					  notesTextField.setText("");
					  cbPercentComplete.setSelectedIndex(0);
					  tabbedPane.setSelectedIndex(0);
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null, "A task name must be entered " + "\n" + "before a task can be created.");
				  }
				} 
				} );
		
		JPanel inboxPanel = new JPanel();
		inboxPanel.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Inbox ()", null, inboxPanel, null);
		inboxPanel.setLayout(new BorderLayout(0, 0));
		
		inboxTable = new JTable(inboxModel);
		inboxPanel.add(new JScrollPane(inboxTable), BorderLayout.CENTER);
		inboxPanel.add(inboxTable.getTableHeader(), BorderLayout.NORTH);
		
		//hides taskID column from user
		TableColumnModel hiddenColInbox = inboxTable.getColumnModel();
		hiddenColInbox.removeColumn(hiddenColInbox.getColumn(0));
		
		JTabbedPane archivePane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("ARCHIVE", null, archivePane, null);

		JPanel archivePanel = new JPanel();
		archivePanel.setLayout(new BorderLayout(0, 0));
		archivePane.addTab("MY ARCHIVED TASKS", null, archivePanel);
		archivePanel.setLayout(new BorderLayout(0, 0));
		
		
		archiveTable = new JTable(archiveModel);
		archiveTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
		            int row = target.getSelectedRow();
					new EditTaskWindow(archiveTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		archivePanel.add(new JScrollPane(archiveTable), BorderLayout.CENTER);
		archivePanel.add(archiveTable.getTableHeader(), BorderLayout.NORTH);
		
		//hides taskID column from user
		TableColumnModel hiddenColArchive = archiveTable.getColumnModel();
		hiddenColArchive.removeColumn(hiddenColArchive.getColumn(0));
		
		JPanel allUserArchivePanel = new JPanel();
		allUserArchivePanel.setLayout(new BorderLayout(0, 0));
		archivePane.addTab("ALL ARCHIVED TASKS", null, allUserArchivePanel);
		allUserArchivePanel.setLayout(new BorderLayout(0, 0));
		
		allUserArchiveTable = new JTable(allArchiveModel);
		allUserArchivePanel.add(new JScrollPane(allUserArchiveTable));
		allUserArchiveTable.add(allUserArchiveTable.getTableHeader(), BorderLayout.NORTH);
		
		allUserArchiveTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
		            int row = target.getSelectedRow();
					new EditTaskWindow(allArchiveTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		
		//hides taskID column from user
		TableColumnModel hiddenColAllArchiveTasks = allUserArchiveTable.getColumnModel();
		hiddenColAllArchiveTasks.removeColumn(hiddenColAllArchiveTasks.getColumn(0));
		
		JPanel trashPanel = new JPanel();
		trashPanel.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("TRASH", null, trashPanel, null);
		trashPanel.setLayout(new BorderLayout(0, 0));
		
		trashTable = new JTable(defaultModel);
		trashPanel.add(new JScrollPane(trashTable));
		trashPanel.add(trashTable.getTableHeader(), BorderLayout.NORTH);
		
		//hides taskID column from user
		TableColumnModel hiddenColTrash = trashTable.getColumnModel();
		hiddenColTrash.removeColumn(hiddenColTrash.getColumn(0));
		
		JPanel requestPanel = new JPanel();
		tabbedPane.addTab("REQUEST TASK", null, requestPanel, null);
		requestPanel.setLayout(null);
		
		String[] users = { "--select one--", "All Users"};
	    final JComboBox<String> cbUsers = new JComboBox(users);
	    cbUsers.setBounds(107, 65, 123, 25);
	    cbUsers.setVisible(true);
	    requestPanel.add(cbUsers);
		
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
    
		myTasksTable.setAutoCreateRowSorter(true);
		allUserTasksTable.setAutoCreateRowSorter(true);
		inboxTable.setAutoCreateRowSorter(true);
		archiveTable.setAutoCreateRowSorter(true);
		trashTable.setAutoCreateRowSorter(true);
		
		JPanel searchBar = new JPanel();
		frmMainwindow.getContentPane().add(searchBar, BorderLayout.NORTH);
		searchBar.setLayout(new BorderLayout(0, 0));
		
		searchText = new JTextField();
		searchText.setText("Search");
		searchBar.add(searchText);
		searchText.setColumns(10);
		
		JButton searchBtn = new JButton("Clear Results");
		searchBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		searchBar.add(searchBtn, BorderLayout.EAST);
		//every time a button is pressed in the search bar
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				}
			}
			});
		//user clicks inside of the search bar
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				searchText.setText("");
			}
		});
		//user hits clear results
		searchBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  searchModel = new TaskTableModel(columnNames, 0);
				  } 
				} );
		
		
		getTasks();
}
	
	/**
	 * Wrapper function for updating from the database
	 */
	void getTasks()
	{
		addTasksToUserTable(tasksModel);
		addAllTasksToTable(allTasksModel);
		addInboxTasksToTable(inboxModel);
		addArchiveTasks(archiveModel);
		addAllArchiveTasks(allArchiveModel);
		resizeColumns(myTasksTable);
		resizeColumns(allUserTasksTable);
		resizeColumns(inboxTable);
		resizeColumns(archiveTable);
		resizeColumns(trashTable);
		addUsersToList();
	}

	/**
	 * Get all the tasks that are assigned to the logged in user and add them to the tasks table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addTasksToUserTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "user");
		addTasksToTable(tasks, model);
		myTasks = tasks;
	}
	
	/**
	 * Get all of the tasks in the database and add them to the all tasks table
	 * @param model the table model that the tasks are added to
	 */
	void addAllTasksToTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "all");
		addTasksToTable(tasks, model);
		allUserTasks = tasks;
	}
	
	/**
	 * Get all the tasks that are newly assigned to the logged in user and add them to the inbox table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addInboxTasksToTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "inbox");
		addTasksToTable(tasks, model);
		tabbedPane.setTitleAt(2, "Inbox (" + tasks.size() + ")");
		inboxTasks = tasks;
	}
	
	/**
	 * Get all the completed tasks that are assigned to the logged in user and add them to the tasks table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addArchiveTasks(DefaultTableModel model)
	{
		tasks = new SQLQueryBuilder().getTasks(userID, "archive");
		addTasksToTable(tasks, model);
		archiveTasks = tasks;
	}
	
	void addAllArchiveTasks(DefaultTableModel model)
	{
		tasks = new SQLQueryBuilder().getTasks(userID, "allArchive");
		addTasksToTable(tasks, model);
		allArchiveTasks = tasks;
	}
	
	/**
	 * Add the given list of tasks to the given table model
	 * 
	 * @param tasks ArrayList of task objects that are to be added to the table
	 * @param model the table model that the tasks are added to
	 */
	void addTasksToTable(ArrayList<Task> tasks, DefaultTableModel model) {
		model.setRowCount(0);
		for(int i = 0; i < tasks.size(); i++)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String num = tasks.get(i).getProjectNum();
			String name = tasks.get(i).getName();
			Date dateDue = tasks.get(i).getDateDue();
			String assignedUser = tasks.get(i).getAssignedUserName();
			String description = tasks.get(i).getDescription();
			String notes = tasks.get(i).getNotes();
			String percentComplete = tasks.get(i).getPercentComplete();
			String id = Integer.toString(tasks.get(i).getTaskID());
			
			Object[] entry = {id, num, name, dateDue, assignedUser, description, notes, percentComplete};
			System.out.println(tasks.get(i).toString());
			model.addRow(entry);
		}
	}
	
	void addUsersToList() {
		users = new SQLQueryBuilder().getUsers();
		for(int i = 0; i < users.size(); i++)
		{
			assignedUserList.addElement(users.get(i));
		}
		assignedUserTextField.setModel(assignedUserList);
	}
	
	void resizeColumns(JTable table)
	{
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		if(!(table.getColumnCount() == 0))
		{
			for (int column = 0; column < table.getColumnCount(); column++)
			{
			    TableColumn tableColumn = table.getColumnModel().getColumn(column);
			    int minWidth = tableColumn.getMinWidth();
			    int maxWidth = tableColumn.getMaxWidth();
	
			    for (int row = 0; row < table.getRowCount(); row++)
			    {
			        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
			        Component component = table.prepareRenderer(cellRenderer, row, column);
			        int width = component.getPreferredSize().width + table.getIntercellSpacing().width;
			        minWidth = Math.max(minWidth, width);
	
			        //  We've exceeded the maximum width, no need to check other rows
	
			        if (minWidth >= maxWidth)
			        {
			            minWidth = maxWidth;
			            break;
			        }
			    }
	
			    tableColumn.setPreferredWidth( minWidth );
			}
		}
	}
}
