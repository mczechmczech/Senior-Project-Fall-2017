package prototype_MinimalFunctionality;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.*;
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
import java.util.List;
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
import javax.swing.JToolBar;
import javax.swing.Box;
import javax.swing.JMenuBar;
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
	private JTextField taskPriority;
	private ArrayList<Task> tasks = new ArrayList<>();
	private ArrayList<Message> messages = new ArrayList<>();
	private ArrayList<Message> inboxMessages, sentMessages = new ArrayList<>();
	private ArrayList<Task> myTasks, archiveTasks, allUserTasks, inboxTasks, sentTasks, trashTasks, searchTasks, placeholder, allArchiveTasks = new ArrayList<>();
	private ArrayList<String> users = new ArrayList<String>();

	private JTable myTasksTable, allUserTasksTable, inboxTasksTable, inboxMessagesTable, sentTasksTable, sentMessagesTable, archiveTable, trashTable, searchTable, allUserArchiveTable;
	private String[] taskColumnNames = {"Task ID", "#", "Name", "Date Due", "Assigned User", "Description", "Notes", "Completion", "Priority"};
	private String[] messageReceiveColumnNames = {"From", "Message"};
	private String[] messageSentColumnNames = {"To", "Message"};
	
	private DefaultTableModel tasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel allTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel inboxTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel inboxMessagesModel = new MessageTableModel(messageReceiveColumnNames, 0);
	private DefaultTableModel sentTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel sentMessagesModel = new MessageTableModel(messageSentColumnNames, 0);
	private DefaultTableModel archiveModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel trashModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel searchModel = new TaskTableModel(taskColumnNames, 0);
	private JTabbedPane tasksPane;
	private JPanel myTasksPanel, allUserTasksPanel, inboxPanel, archivePanel, allUserArchivePanel, trashPanel;
	private JComboBox<String> assignedUserTextField;
	
	private int inboxTasksSize = 0, inboxMessagesSize = 0;

	private JTabbedPane tabbedPane, archivePane, inboxPane, sentPane;
	private JTextField searchText;
	private DefaultTableModel allArchiveModel = new TaskTableModel(taskColumnNames, 0);
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
		
		JPanel panel_1 = new JPanel();
		frmMainwindow.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		panel_1.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenu mnFile = new JMenu("Help");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("About");
		mnFile.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				new About(new JFrame());
			}
		});
		
		Box horizontalBox = Box.createHorizontalBox();
		panel_1.add(horizontalBox, BorderLayout.SOUTH);
		
		JButton btnCreate = new JButton("Create");
		horizontalBox.add(btnCreate);
		btnCreate.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton btnDelete = new JButton("Delete");
		horizontalBox.add(btnDelete);
		btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton btnComposeMessage = new JButton("Compose Message");
		horizontalBox.add(btnComposeMessage);
		btnComposeMessage.setHorizontalAlignment(SwingConstants.LEFT);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTasks();
			}
		});
		horizontalBox.add(btnRefresh);
		
				JButton btnLogout = new JButton("LOGOUT");
				horizontalBox.add(btnLogout);
				btnLogout.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) { 
						new LoginWindow();
						frmMainwindow.dispose();
					} 
				} );
		
		//code for search bar
		JPanel searchBar = new JPanel();
		horizontalBox.add(searchBar);
		searchBar.setLayout(new BorderLayout(0, 0));
		
		searchText = new JTextField();
		searchText.setHorizontalAlignment(SwingConstants.RIGHT);
		searchText.setText("Search");
		searchBar.add(searchText);
		searchText.setColumns(10);
		
		JButton searchBtn = new JButton("Clear Results");
		searchBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		searchBar.add(searchBtn, BorderLayout.EAST);
		
		//when a user hits enter, search
		searchText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchModel = new TaskTableModel(taskColumnNames, 0);
					
					
					searchTable=new JTable(searchModel);
					if(tabbedPane.getSelectedComponent().equals(tasksPane))
					{
						if(tasksPane.getSelectedComponent().equals(myTasksPanel))
						{
							addTasksToSearchTable(tasksModel, "user", searchText.getText());
							addTasksToTable(searchTasks, tasksModel);
						}
						else
						{
							addTasksToSearchTable(allTasksModel, "all", searchText.getText());
							addTasksToTable(searchTasks, allTasksModel);
						}
					}
					else if(tabbedPane.getSelectedComponent().equals(inboxPanel))
					{
						addTasksToSearchTable(inboxTasksModel, "inbox", searchText.getText());
						addTasksToTable(searchTasks, inboxTasksModel);
					}
					else if(tabbedPane.getSelectedComponent().equals(archivePane))
					{
						if(archivePane.getSelectedComponent().equals(archivePanel))
						{
							addTasksToSearchTable(archiveModel, "archive", searchText.getText());
							addTasksToTable(searchTasks, archiveModel);
						}
						else if(archivePane.getSelectedComponent().equals(allUserArchivePanel))
						{
							addTasksToSearchTable(allArchiveModel, "allArchive", searchText.getText());
							addTasksToTable(searchTasks, allArchiveModel);
						}
					}
					else if(tabbedPane.getSelectedComponent().equals(trashPanel))
					{
						addTasksToSearchTable(trashModel, "trash", searchText.getText());
						addTasksToTable(searchTasks, trashModel);
					}
					resizeColumns(searchTable);
					//hides taskID column from user
				}
			}
			});
		//user clicks inside of the search bar, remove text from search bar
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				searchText.setText("");
			}
		});
		//user hits clear results, remove search results and return to MY TASKS
		searchBtn.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  getTasks();
				  searchText.setText("");
				  } 
				} );
		
		btnCreate.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  new EditTaskWindow(userID, PrototypeWindow.this);
				} 
				} );
		
		btnDelete.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  Component compSel1 = tabbedPane.getSelectedComponent();
				  int tableRowSelected = -1;
				  if(compSel1 instanceof JTabbedPane)
				  {
					  int compSel2 = ((JTabbedPane) compSel1).getSelectedIndex();
					  if(((JTabbedPane) compSel1).getTitleAt(compSel2).equals("MY TASKS"))
					  {
						  tableRowSelected = myTasksTable.getSelectedRow();
						  if(tableRowSelected == -1)
						  {
							  noneSelected();
						  }
						  else
						  {
							  new SQLQueryBuilder().putInTrash(myTasks.get(tableRowSelected).getTaskID());
						  }
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).equals("ALL USER TASKS"))
					  {
						  tableRowSelected = allUserTasksTable.getSelectedRow();
						  if(tableRowSelected == -1)
						  {
							  noneSelected();
						  }
						  else
						  {
							  new SQLQueryBuilder().putInTrash(allUserTasks.get(tableRowSelected).getTaskID());
						  }
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).contains("Inbox Tasks"))
					  {
						  tableRowSelected = inboxTasksTable.getSelectedRow();
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).contains("Inbox Messages"))
					  {
						  noneSelected();
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).contains("Sent Tasks"))
					  {
						  tableRowSelected = sentTasksTable.getSelectedRow();
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).contains("Sent Messages"))
					  {
						  noneSelected();
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).equals("MY ARCHIVED TASKS"))
					  {
						  tableRowSelected = archiveTable.getSelectedRow();
						  if(tableRowSelected == -1)
						  {
							  noneSelected();
						  }
						  else
						  {
							  new SQLQueryBuilder().putInTrash(archiveTasks.get(tableRowSelected).getTaskID());
						  }
					  }
					  else if(((JTabbedPane) compSel1).getTitleAt(compSel2).equals("ALL ARCHIVED TASKS"))
					  {
						  tableRowSelected = allUserArchiveTable.getSelectedRow();
						  if(tableRowSelected == -1)
						  {
							  noneSelected();
						  }
						  else
						  {
							  new SQLQueryBuilder().putInTrash(allArchiveTasks.get(tableRowSelected).getTaskID());
						  }
					  }
				  }
				  else
				  {
					  int component1 = tabbedPane.getSelectedIndex();
					  if(tabbedPane.getTitleAt(component1).equals("TRASH"))
					  {
						  tableRowSelected = trashTable.getSelectedRow();
						  if(tableRowSelected == -1)
						  {
							  noneSelected();
						  }
						  else
						  {
							  new SQLQueryBuilder().deleteFromTrash(trashTasks.get(tableRowSelected).getTaskID());
						  }
					  }
					  else
					  {
						  noneSelected();
					  }
				  }
				  getTasks();
				} 
				} );
		
		btnComposeMessage.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  new MessageWindow(userID, PrototypeWindow.this);
				} 
				} );
		
		JPanel panel = new JPanel();
		frmMainwindow.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel.add(tabbedPane);
		
		tasksPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("TASKS", null, tasksPane, null);
		
		myTasksPanel = new JPanel();
		myTasksPanel.setLayout(new BorderLayout(0, 0));
		tasksPane.addTab("MY TASKS", null, myTasksPanel, null);
		myTasksPanel.setLayout(new BorderLayout(0, 0));
		
		myTasksTable = new JTable(tasksModel) {
			@Override
		       public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		           Component component = super.prepareRenderer(renderer, row, column);
		           int rendererWidth = component.getPreferredSize().width;
		           TableColumn tableColumn = getColumnModel().getColumn(column);
		           tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
		           return component;
		        }
		};
		myTasksPanel.add(new JScrollPane(myTasksTable), BorderLayout.CENTER);
		myTasksPanel.add(myTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		myTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
		            int row = myTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(myTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		
		//hides taskID column from user
		TableColumnModel hiddenColMyTasks = myTasksTable.getColumnModel();
		
		
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
		            int row = allUserTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(allUserTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		
		//hides taskID column from user
		TableColumnModel hiddenColAllTasks = allUserTasksTable.getColumnModel();
		

		inboxPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Inbox", null, inboxPane, null);
		
		JPanel inboxTasksPanel = new JPanel();
		inboxTasksPanel.setLayout(new BorderLayout(0, 0));
		inboxPane.addTab("Inbox Tasks", null, inboxTasksPanel);
		inboxTasksPanel.setLayout(new BorderLayout(0, 0));
		
		
		inboxTasksTable = new JTable(inboxTasksModel);
		inboxTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
		            int row = inboxTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new AcceptTaskWindow(inboxTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		inboxTasksPanel.add(new JScrollPane(inboxTasksTable), BorderLayout.CENTER);
		inboxTasksPanel.add(inboxTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		JPanel inboxMessagesPanel = new JPanel();
		inboxMessagesPanel.setLayout(new BorderLayout(0, 0));
		inboxPane.addTab("Inbox Messages", null, inboxMessagesPanel);
		inboxMessagesPanel.setLayout(new BorderLayout(0, 0));
		
		inboxMessagesTable = new JTable(inboxMessagesModel);
		inboxMessagesPanel.add(new JScrollPane(inboxMessagesTable));
		inboxMessagesTable.add(inboxMessagesTable.getTableHeader(), BorderLayout.NORTH);
		
		sentPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Sent", null, sentPane, null);
		
		JPanel sentTasksPanel = new JPanel();
		sentTasksPanel.setLayout(new BorderLayout(0, 0));
		sentPane.addTab("Sent Tasks", null, sentTasksPanel);
		sentTasksPanel.setLayout(new BorderLayout(0, 0));
		
		
		sentTasksTable = new JTable(sentTasksModel);
		sentTasksPanel.add(new JScrollPane(sentTasksTable), BorderLayout.CENTER);
		sentTasksPanel.add(sentTasksTable.getTableHeader(), BorderLayout.NORTH);
		
		JPanel sentMessagesPanel = new JPanel();
		sentMessagesPanel.setLayout(new BorderLayout(0, 0));
		sentPane.addTab("Sent Messages", null, sentMessagesPanel);
		sentMessagesPanel.setLayout(new BorderLayout(0, 0));
		
		sentMessagesTable = new JTable(sentMessagesModel);
		sentMessagesPanel.add(new JScrollPane(sentMessagesTable));
		sentMessagesTable.add(sentMessagesTable.getTableHeader(), BorderLayout.NORTH);
		
		archivePane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("ARCHIVE", null, archivePane, null);
		
		archivePanel = new JPanel();
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
		            int row = archiveTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(archiveTasks.get(row), PrototypeWindow.this);
				}
			}
		});
		archivePanel.add(new JScrollPane(archiveTable), BorderLayout.CENTER);
		archivePanel.add(archiveTable.getTableHeader(), BorderLayout.NORTH);

		//hides taskID column from user
		TableColumnModel hiddenColArchive = archiveTable.getColumnModel();

		allUserArchivePanel = new JPanel();
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
					int row = allUserArchiveTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(allArchiveTasks.get(row), PrototypeWindow.this);
				}
			}
		});

		//hides taskID column from user
		TableColumnModel hiddenColAllArchiveTasks = allUserArchiveTable.getColumnModel();

		trashPanel = new JPanel();
		trashPanel.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("TRASH", null, trashPanel, null);
		trashPanel.setLayout(new BorderLayout(0, 0));

		trashTable = new JTable(trashModel);
		trashPanel.add(new JScrollPane(trashTable));
		trashPanel.add(trashTable.getTableHeader(), BorderLayout.NORTH);

		trashTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
					int row = trashTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(trashTasks.get(row), PrototypeWindow.this);
				}
			}
		});

		//hides taskID column from user
		TableColumnModel hiddenColTrash = trashTable.getColumnModel();

		JPanel requestPanel = new JPanel();
		tabbedPane.addTab("REQUEST TASK", null, requestPanel, null);
		requestPanel.setLayout(null);
		final JComboBox<String> cbUsers = new JComboBox();
		cbUsers.setBounds(107, 65, 123, 25);
		cbUsers.setVisible(true);
		addUsersToList(cbUsers);
		requestPanel.add(cbUsers);

		JButton btnRequestTask = new JButton("REQUEST TASK");
		btnRequestTask.setBounds(107, 101, 123, 25);
		requestPanel.add(btnRequestTask);

		myTasksTable.setAutoCreateRowSorter(true);
		allUserTasksTable.setAutoCreateRowSorter(true);
		inboxTasksTable.setAutoCreateRowSorter(true);
		archiveTable.setAutoCreateRowSorter(true);
		trashTable.setAutoCreateRowSorter(true);
		hiddenColMyTasks.removeColumn(hiddenColMyTasks.getColumn(0));
		hiddenColAllTasks.removeColumn(hiddenColAllTasks.getColumn(0));
		
		//hides taskID column from user
		TableColumnModel hiddenColInbox = inboxTasksTable.getColumnModel();
		hiddenColInbox.removeColumn(hiddenColInbox.getColumn(0));
		hiddenColArchive.removeColumn(hiddenColArchive.getColumn(0));
		hiddenColAllArchiveTasks.removeColumn(hiddenColAllArchiveTasks.getColumn(0));
		hiddenColTrash.removeColumn(hiddenColTrash.getColumn(0));
		
		String[] users = { "--select one--", "All Users"};
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 4);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 8;

		
		getTasks();
}
	
	/**
	 * Wrapper function for updating from the database
	 */
	void getTasks()
	{
		addTasksToUserTable(tasksModel);
		addAllTasksToTable(allTasksModel);
		addInboxTasksToTable(inboxTasksModel);
		addArchiveTasks(archiveModel);
		addAllArchiveTasks(allArchiveModel);
		addTrashTasks(trashModel);
		addInboxMessagesToTable(inboxMessagesModel);
		addSentTasksToTable(sentTasksModel);
		addSentMessagesToTable(sentMessagesModel);
		if(inboxTasksSize > 0 || inboxMessagesSize > 0)
		{
			tabbedPane.setTitleAt(1, "Inbox (" + (inboxTasksSize + inboxMessagesSize) + ")");
		}
		resizeColumns(myTasksTable);
		resizeColumns(allUserTasksTable);
		resizeColumns(inboxTasksTable);
		resizeColumns(archiveTable);
		resizeColumns(trashTable);
	}
	
	void noneSelected()
	{
		JOptionPane.showMessageDialog(null, "No Tasks Selected.");
	}
	
	/**
	 * Get all the tasks that are assigned to the logged in user and add them to the tasks table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addTasksToUserTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "user", "");
		addTasksToTable(tasks, model);
		myTasks = tasks;
	}
	
	/**
	 * Get all the tasks that were found in search and add them to the search table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addTasksToSearchTable(DefaultTableModel model, String table, String search) {
		tasks = new SQLQueryBuilder().getTasks(userID, table, search);
		addTasksToTable(tasks, model);
		searchTasks = tasks;
		System.out.println(searchTasks.size()+" results found.");
	}
	
	/**
	 * Get all of the tasks in the database and add them to the all tasks table
	 * @param model the table model that the tasks are added to
	 */
	void addAllTasksToTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "all", "");
		addTasksToTable(tasks, model);
		allUserTasks = tasks;
	}
	
	/**
	 * Get all the tasks that are newly assigned to the logged in user and add them to the inboxTasks table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addInboxTasksToTable(DefaultTableModel model) {

		tasks = new SQLQueryBuilder().getTasks(userID, "inboxTasks", "");
		addTasksToTable(tasks, model);
		if(tasks.size() > 0)
		{
			inboxTasksSize = tasks.size();
			inboxPane.setTitleAt(0, "Inbox Tasks (" + inboxTasksSize + ")");
		}
		inboxTasks = tasks;
	}
	
	/**
	 * Get all the messages that are assigned to the logged in user and add them to the inboxMessages table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addInboxMessagesToTable(DefaultTableModel model) {
		messages = new SQLQueryBuilder().getMessages(userID, "inboxMessages");
		addMessagesToTable(messages, model, false);
		if(tasks.size() > 0)
		{
			inboxMessagesSize = messages.size();
			inboxPane.setTitleAt(1, "Inbox Messages (" + inboxMessagesSize + ")");
		}
		inboxMessages = messages;
	}
	
	/**
	 * Get all the tasks that are newly sent by the logged in user and add them to the sentTasks table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addSentTasksToTable(DefaultTableModel model) {

		tasks = new SQLQueryBuilder().getTasks(userID, "sentTasks", "");
		addTasksToTable(tasks, model);
		sentTasks = tasks;
	}
	
	/**
	 * Get all the messages that are sent by the logged in user and add them to the sentMessages table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addSentMessagesToTable(DefaultTableModel model) {
		messages = new SQLQueryBuilder().getMessages(userID, "sentMessages");
		addMessagesToTable(messages, model, true);
		sentMessages = messages;
	}
	
	/**
	 * Get all the completed tasks that are assigned to the logged in user and add them to the tasks table
	 * 
	 * @param model the table model that the tasks are added to
	 */
	void addArchiveTasks(DefaultTableModel model)
	{
		tasks = new SQLQueryBuilder().getTasks(userID, "archive", "");
		addTasksToTable(tasks, model);
		archiveTasks = tasks;
	}
	
	void addAllArchiveTasks(DefaultTableModel model)
	{
		tasks = new SQLQueryBuilder().getTasks(userID, "allArchive", "");
		addTasksToTable(tasks, model);
		allArchiveTasks = tasks;
	}
	
	void addTrashTasks(DefaultTableModel model)
	{
		tasks = new SQLQueryBuilder().getTasks(userID, "trash", "");
		addTasksToTable(tasks, model);
		trashTasks = tasks;
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
			String thisPriority = Integer.toString(tasks.get(i).getPriority());
			
			Object[] entry = {id, Integer.parseInt(num), name, dateDue, assignedUser, description, notes, percentComplete, thisPriority};
			model.addRow(entry);
		}
	}
	
	/**
	 * Add the given list of tasks to the given table model
	 * 
	 * @param messages ArrayList of message objects that are to be added to the table
	 * @param model the table model that the messages are added to
	 */
	void addMessagesToTable(ArrayList<Message> messages, DefaultTableModel model, boolean sentTab) {
		model.setRowCount(0);
		if(!sentTab)
		{
			for(int i = 0; i < messages.size(); i++)
			{
				String from = messages.get(i).getSender();
				String message = messages.get(i).getMessage();
				
				Object[] entry = {from, message};
				model.addRow(entry);
			}
		}
		else
		{
			for(int i = 0; i < messages.size(); i++)
			{
				String to = messages.get(i).getReceiver();
				String message = messages.get(i).getMessage();
				
				Object[] entry = {to, message};
				model.addRow(entry);
			}
		}
	}
	
	JComboBox<String> addUsersToList(JComboBox<String> userField) {
		 		users = new SQLQueryBuilder().getUsers();
		 		for(int i = 0; i < users.size(); i++)
		 		{
		 			userField.addItem(users.get(i));
		 		}
		 		return userField;
		 	}

	void resizeColumns(JTable table)
	{
		table.getColumnModel().getColumn(0).setMinWidth( 40 );
		table.getColumnModel().getColumn(0).setPreferredWidth( 40 );
		table.getColumnModel().getColumn(0).setMaxWidth( 40 );
		table.getColumnModel().getColumn(2).setMinWidth( 80 );
		table.getColumnModel().getColumn(2).setPreferredWidth( 80 );
		//table.getColumnModel().getColumn(2).setMaxWidth( 80 );
		table.getColumnModel().getColumn(3).setMinWidth( 80 );
		table.getColumnModel().getColumn(3).setPreferredWidth( 80 );
		//table.getColumnModel().getColumn(3).setMaxWidth( 80 );
		table.getColumnModel().getColumn(6).setMinWidth( 40 );
		table.getColumnModel().getColumn(6).setPreferredWidth( 40 );
		table.getColumnModel().getColumn(6).setMaxWidth( 40 );
	    table.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
		for (int column = 1; column < table.getColumnCount() - 1; column++)
		{
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int preferredWidth = tableColumn.getMinWidth();
		    int maxWidth = tableColumn.getMaxWidth();
			if(column == 2 || column ==3)
			{
				
			}
			else
			{
			    
			 
			    for (int row = 0; row < table.getRowCount(); row++)
			    {
			        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
			        Component c = table.prepareRenderer(cellRenderer, row, column);
			        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
			        preferredWidth = Math.max(preferredWidth, width);
			 
			        //  We've exceeded the maximum width, no need to check other rows
			 
			        if (preferredWidth >= maxWidth)
			        {
			            preferredWidth = maxWidth;
			            break;
			        }
			    }
			}
		    tableColumn.setPreferredWidth( preferredWidth );
		}
			
		/*
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
		}*/
	}
}
