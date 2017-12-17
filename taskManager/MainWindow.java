package taskManager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;

import java.util.ArrayList;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

public class MainWindow {

	private int userID;
	private JFrame frmMainwindow;
	private ArrayList<Task> tasks = new ArrayList<>();
	private ArrayList<Message> messages = new ArrayList<>();
	private ArrayList<Message> inboxMessages, sentMessages, inboxTrashMessages, sentTrashMessages = new ArrayList<>();
	private ArrayList<Task> myTasks, archiveTasks, allUserTasks, inboxTasks, sentTasks, trashReceivedTasks,
			trashSentTasks, searchTasks, allArchiveTasks = new ArrayList<>();
	private ArrayList<String> users = new ArrayList<String>();

	private JTable myTasksTable, allUserTasksTable, inboxTasksTable, inboxMessagesTable, sentTasksTable,
			sentMessagesTable, archiveTable, trashReceivedTasksTable, trashSentTasksTable, trashReceivedMessagesTable,
			trashSentMessagesTable, searchTable, allUserArchiveTable;
	private String[] taskColumnNames = { "Task ID", "#", "Name", "Category", "Date Due", "Assigned User", "Assigned By",
			"Description", "Notes", "Completion", "Priority" };

	private String[] messageReceiveColumnNames = { "From", "Message" };
	private String[] messageSentColumnNames = { "To", "Message" };

	private DefaultTableModel tasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel allTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel inboxTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel inboxMessagesModel = new MessageTableModel(messageReceiveColumnNames, 0);
	private DefaultTableModel sentTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel sentMessagesModel = new MessageTableModel(messageSentColumnNames, 0);
	private DefaultTableModel archiveModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel trashReceivedTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel trashSentTasksModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultTableModel trashReceivedMessagesModel = new TaskTableModel(messageReceiveColumnNames, 0);
	private DefaultTableModel trashSentMessagesModel = new TaskTableModel(messageSentColumnNames, 0);
	private DefaultTableModel searchModel = new TaskTableModel(taskColumnNames, 0);
	private JPanel myTasksPanel, inboxTasksPanel, inboxMessagesPanel, sentTasksPanel, sentMessagesPanel, archivePanel,
			allUserArchivePanel, trashReceivedTasksPanel, trashSentTasksPanel, trashReceivedMessagesPanel,
			trashSentMessagesPanel;
	private int inboxTasksSize = 0, inboxMessagesSize = 0;
	private int[] columnWidths;
	private JTabbedPane tabbedPane, tasksPane, archivePane, inboxPane, sentPane, trashPane;
	private JTextField searchText;
	private DefaultTableModel allArchiveModel = new TaskTableModel(taskColumnNames, 0);
	private DefaultComboBoxModel<String> assignedUserList = new DefaultComboBoxModel<String>();
	private Component horizontalGlue;

	/**
	 * Create the application.
	 * 
	 * @param name
	 *            The username of the logged in user
	 */
	public MainWindow(String name) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setUserID(new SQLQueryBuilder().getIDFromUserName(name));
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
		mnNewMenu.setFont(new Font("Tahoma", Font.BOLD, 15));
		menuBar.add(mnNewMenu);

		JMenu mnFile = new JMenu("Help");
		mnFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		menuBar.add(mnFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("About");
		mntmNewMenuItem.setFont(new Font("Tahoma", Font.BOLD, 15));
		mnFile.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About(new JFrame());
			}
		});

		Box horizontalBox = Box.createHorizontalBox();
		panel_1.add(horizontalBox, BorderLayout.SOUTH);

		JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox.add(btnCreate);
		btnCreate.setHorizontalAlignment(SwingConstants.LEFT);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox.add(btnDelete);
		btnDelete.setHorizontalAlignment(SwingConstants.LEFT);

		JButton btnComposeMessage = new JButton("Compose Message");
		btnComposeMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox.add(btnComposeMessage);
		btnComposeMessage.setHorizontalAlignment(SwingConstants.LEFT);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTasks();
			}
		});
		horizontalBox.add(btnRefresh);

		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox.add(btnLogout);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginWindow();
				frmMainwindow.dispose();
			}
		});

		horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		searchText = new JTextField();
		searchText.setMaximumSize(new Dimension(100, 1000));
		horizontalBox.add(searchText);
		searchText.setFont(new Font("Tahoma", Font.BOLD, 13));
		searchText.setHorizontalAlignment(SwingConstants.LEFT);
		searchText.setText("Search");
		searchText.setColumns(10);

		// when a user hits enter, search
		searchText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchModel = new TaskTableModel(taskColumnNames, 0);
					searchTable = new JTable(searchModel);
					if (tabbedPane.getSelectedComponent().equals(tasksPane)) {
						if (tasksPane.getSelectedComponent().equals(myTasksPanel)) {
							addTasksToSearchTable(tasksModel, "user", searchText.getText());
							addTasksToTable(searchTasks, tasksModel);
						} else {
							addTasksToSearchTable(allTasksModel, "all", searchText.getText());
							addTasksToTable(searchTasks, allTasksModel);
						}
					} else if (tabbedPane.getSelectedComponent().equals(inboxPane)) {
						if (inboxPane.getSelectedComponent().equals(inboxTasksPanel)) {
							addTasksToSearchTable(inboxTasksModel, "inboxTasks", searchText.getText());
							addTasksToTable(searchTasks, inboxTasksModel);
						}
					} else if (tabbedPane.getSelectedComponent().equals(sentPane)) {
						if (sentPane.getSelectedComponent().equals(sentTasksPanel)) {
							addTasksToSearchTable(sentTasksModel, "sentTasks", searchText.getText());
							addTasksToTable(searchTasks, sentTasksModel);
						}
					} else if (tabbedPane.getSelectedComponent().equals(archivePane)) {
						if (archivePane.getSelectedComponent().equals(archivePanel)) {
							addTasksToSearchTable(archiveModel, "archive", searchText.getText());
							addTasksToTable(searchTasks, archiveModel);
						} else if (archivePane.getSelectedComponent().equals(allUserArchivePanel)) {
							addTasksToSearchTable(allArchiveModel, "allArchive", searchText.getText());
							addTasksToTable(searchTasks, allArchiveModel);
						}
					} else if (tabbedPane.getSelectedComponent().equals(trashPane)) {
						if (trashPane.getSelectedComponent().equals(trashReceivedTasksPanel)) {
							addTasksToSearchTable(trashReceivedTasksModel, "trashReceivedTasks", searchText.getText());
							addTasksToTable(searchTasks, trashReceivedTasksModel);
						} else if (trashPane.getSelectedComponent().equals(trashSentTasksPanel)) {
							addTasksToSearchTable(trashSentTasksModel, "trashSentTasks", searchText.getText());
							addTasksToTable(searchTasks, trashSentTasksModel);
						}
					}
					resizeColumns(searchTable);
				}
			}
		});
		// user clicks inside of the search bar, remove text from search bar
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchText.setText("");
			}
		});

		JButton searchBtn = new JButton("Clear Results");
		horizontalBox.add(searchBtn);
		searchBtn.setForeground(new Color(153, 0, 0));
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		searchBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		// user hits clear results, remove search results and return to MY TASKS
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTasks();
				searchText.setText("");
			}
		});

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditTaskWindow(getUserID(), MainWindow.this, 0);
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component compSel1 = tabbedPane.getSelectedComponent();
				int tableRowSelected = -1;
				if (compSel1 instanceof JTabbedPane) {
					int compSel2 = ((JTabbedPane) compSel1).getSelectedIndex();
					if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("MY TASKS")) {
						tableRowSelected = myTasksTable.convertRowIndexToModel(myTasksTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else if (new SQLQueryBuilder()
								.getUserCreatedID(myTasks.get(tableRowSelected).getTaskID()) != userID) {
							protectOtherUserTasks();
						} else {
							new SQLQueryBuilder().putInTrash(myTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("ALL USER TASKS")) {
						tableRowSelected = allUserTasksTable.convertRowIndexToModel(allUserTasksTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else if (new SQLQueryBuilder()
								.getUserCreatedID(allUserTasks.get(tableRowSelected).getTaskID()) != userID) {
							protectOtherUserTasks();
						} else {
							new SQLQueryBuilder().putInTrash(allUserTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).contains("Inbox Tasks")) {
						tableRowSelected = inboxTasksTable.convertRowIndexToModel(inboxTasksTable.getSelectedRow());
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).contains("Inbox Messages")) {
						tableRowSelected = inboxMessagesTable
								.convertRowIndexToModel(inboxMessagesTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Message");
						} else {
							new SQLQueryBuilder().putMessageInTrash(inboxMessages.get(tableRowSelected).getMessageID(),
									"inboxMessages");
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("Sent Tasks")) {
						tableRowSelected = sentTasksTable.convertRowIndexToModel(sentTasksTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else {
							new SQLQueryBuilder().putInTrash(sentTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("Sent Messages")) {
						tableRowSelected = sentMessagesTable.convertRowIndexToModel(sentMessagesTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Message");
						} else {
							new SQLQueryBuilder().putMessageInTrash(sentMessages.get(tableRowSelected).getMessageID(),
									"sentMessages");
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("MY ARCHIVED TASKS")) {
						tableRowSelected = archiveTable.convertRowIndexToModel(archiveTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else if (new SQLQueryBuilder()
								.getUserCreatedID(archiveTasks.get(tableRowSelected).getTaskID()) != userID) {
							protectOtherUserTasks();
						} else {
							new SQLQueryBuilder().putInTrash(archiveTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("ALL ARCHIVED TASKS")) {
						tableRowSelected = allUserArchiveTable
								.convertRowIndexToModel(allUserArchiveTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else if (new SQLQueryBuilder()
								.getUserCreatedID(allArchiveTasks.get(tableRowSelected).getTaskID()) != userID) {
							protectOtherUserTasks();
						} else {
							new SQLQueryBuilder().putInTrash(allArchiveTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("Tasks Received")) {
						tableRowSelected = trashReceivedTasksTable
								.convertRowIndexToModel(trashReceivedTasksTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else if (new SQLQueryBuilder()
								.getUserCreatedID(trashReceivedTasks.get(tableRowSelected).getTaskID()) != userID) {
							protectOtherUserTasks();
						} else {
							new SQLQueryBuilder().deleteFromTrash(trashReceivedTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("Tasks Sent")) {
						tableRowSelected = trashSentTasksTable
								.convertRowIndexToModel(trashSentTasksTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Task");
						} else {
							new SQLQueryBuilder().deleteFromTrash(trashSentTasks.get(tableRowSelected).getTaskID());
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("Messages Received")) {
						tableRowSelected = trashReceivedMessagesTable
								.convertRowIndexToModel(trashReceivedMessagesTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Message");
						} else {
							new SQLQueryBuilder().removeMessageFromTrash(
									inboxTrashMessages.get(tableRowSelected).getMessageID(), "inboxMessages");
						}
					} else if (((JTabbedPane) compSel1).getTitleAt(compSel2).equals("Messages Sent")) {
						tableRowSelected = trashSentMessagesTable
								.convertRowIndexToModel(trashSentMessagesTable.getSelectedRow());
						if (tableRowSelected == -1) {
							noneSelected("Message");
						} else {
							new SQLQueryBuilder().removeMessageFromTrash(
									sentTrashMessages.get(tableRowSelected).getMessageID(), "sentMessages");
						}
					}
				} else {
					noneSelected("No Item");
				}
				getTasks();
			}
		});

		btnComposeMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MessageWindow(getUserID(), MainWindow.this);
			}
		});

		JPanel panel = new JPanel();
		frmMainwindow.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.setForeground(new Color(153, 0, 0));
		panel.add(tabbedPane);

		tasksPane = new JTabbedPane(JTabbedPane.TOP);
		tasksPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tasksPane.setForeground(new Color(153, 0, 0));
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
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		myTasksPanel.add(new JScrollPane(myTasksTable), BorderLayout.CENTER);
		myTasksPanel.add(myTasksTable.getTableHeader(), BorderLayout.NORTH);

		myTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = myTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(myTasks.get(row), MainWindow.this);
				}
			}
		});

		// hides taskID column from user
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
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = allUserTasksTable.convertRowIndexToModel(target.getSelectedRow());
					Task edit = allUserTasks.get(row);
					if ((userID == edit.getAssignedUserID())
							|| ((new SQLQueryBuilder().getUserNameFromID(edit.getAssignedUserID()))
									.equals("Unassigned"))) {
						new EditTaskWindow(edit, MainWindow.this);
					}
				}
			}
		});

		// hides taskID column from user
		TableColumnModel hiddenColAllTasks = allUserTasksTable.getColumnModel();
		hiddenColAllTasks.removeColumn(hiddenColAllTasks.getColumn(0));

		inboxPane = new JTabbedPane(JTabbedPane.TOP);
		inboxPane.setForeground(new Color(153, 0, 0));
		inboxPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.addTab("Inbox", null, inboxPane, null);

		inboxTasksPanel = new JPanel();
		inboxTasksPanel.setLayout(new BorderLayout(0, 0));
		inboxPane.addTab("Inbox Tasks", null, inboxTasksPanel);
		inboxTasksPanel.setLayout(new BorderLayout(0, 0));

		inboxTasksTable = new JTable(inboxTasksModel);
		inboxTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = inboxTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new AcceptTaskWindow(inboxTasks.get(row), MainWindow.this);
				}
			}
		});
		inboxTasksPanel.add(new JScrollPane(inboxTasksTable), BorderLayout.CENTER);
		inboxTasksPanel.add(inboxTasksTable.getTableHeader(), BorderLayout.NORTH);

		// hides taskID column from user
		TableColumnModel hiddenColInbox = inboxTasksTable.getColumnModel();
		hiddenColInbox.removeColumn(hiddenColInbox.getColumn(0));

		inboxMessagesPanel = new JPanel();
		inboxMessagesPanel.setLayout(new BorderLayout(0, 0));
		inboxPane.addTab("Inbox Messages", null, inboxMessagesPanel);
		inboxMessagesPanel.setLayout(new BorderLayout(0, 0));

		inboxMessagesTable = new JTable(inboxMessagesModel);
		inboxMessagesPanel.add(new JScrollPane(inboxMessagesTable));
		inboxMessagesTable.add(inboxMessagesTable.getTableHeader(), BorderLayout.NORTH);

		sentPane = new JTabbedPane(JTabbedPane.TOP);
		sentPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		sentPane.setForeground(new Color(153, 0, 0));
		tabbedPane.addTab("Sent", null, sentPane, null);

		sentTasksPanel = new JPanel();
		sentTasksPanel.setLayout(new BorderLayout(0, 0));
		sentPane.addTab("Sent Tasks", null, sentTasksPanel);
		sentTasksPanel.setLayout(new BorderLayout(0, 0));

		sentTasksTable = new JTable(sentTasksModel);
		sentTasksPanel.add(new JScrollPane(sentTasksTable), BorderLayout.CENTER);
		sentTasksPanel.add(sentTasksTable.getTableHeader(), BorderLayout.NORTH);

		// hides taskID column from user
		TableColumnModel hiddenColSentTasks = sentTasksTable.getColumnModel();
		hiddenColSentTasks.removeColumn(hiddenColSentTasks.getColumn(0));

		sentMessagesPanel = new JPanel();
		sentMessagesPanel.setLayout(new BorderLayout(0, 0));
		sentPane.addTab("Sent Messages", null, sentMessagesPanel);
		sentMessagesPanel.setLayout(new BorderLayout(0, 0));

		sentMessagesTable = new JTable(sentMessagesModel);
		sentMessagesPanel.add(new JScrollPane(sentMessagesTable));
		sentMessagesTable.add(sentMessagesTable.getTableHeader(), BorderLayout.NORTH);

		archivePane = new JTabbedPane(JTabbedPane.TOP);
		archivePane.setForeground(new Color(153, 0, 0));
		archivePane.setFont(new Font("Tahoma", Font.BOLD, 14));
		tabbedPane.addTab("ARCHIVE", null, archivePane, null);

		archivePanel = new JPanel();
		archivePanel.setLayout(new BorderLayout(0, 0));
		archivePane.addTab("MY ARCHIVED TASKS", null, archivePanel);
		archivePanel.setLayout(new BorderLayout(0, 0));

		archiveTable = new JTable(archiveModel);
		archiveTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = archiveTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(archiveTasks.get(row), MainWindow.this);
				}
			}
		});
		archivePanel.add(new JScrollPane(archiveTable), BorderLayout.CENTER);
		archivePanel.add(archiveTable.getTableHeader(), BorderLayout.NORTH);

		// hides taskID column from user
		TableColumnModel hiddenColArchive = archiveTable.getColumnModel();
		hiddenColArchive.removeColumn(hiddenColArchive.getColumn(0));

		allUserArchivePanel = new JPanel();
		allUserArchivePanel.setLayout(new BorderLayout(0, 0));
		archivePane.addTab("ALL ARCHIVED TASKS", null, allUserArchivePanel);
		allUserArchivePanel.setLayout(new BorderLayout(0, 0));

		allUserArchiveTable = new JTable(allArchiveModel);
		allUserArchivePanel.add(new JScrollPane(allUserArchiveTable));
		allUserArchiveTable.add(allUserArchiveTable.getTableHeader(), BorderLayout.NORTH);

		allUserArchiveTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = allUserArchiveTable.convertRowIndexToModel(target.getSelectedRow());
					Task edit = allArchiveTasks.get(row);
					if ((userID == edit.getAssignedUserID())
							|| ((new SQLQueryBuilder().getUserNameFromID(edit.getAssignedUserID()))
									.equals("Unassigned"))) {
						new EditTaskWindow(allArchiveTasks.get(row), MainWindow.this);
					}
				}
			}
		});

		// hides taskID column from user
		TableColumnModel hiddenColAllArchiveTasks = allUserArchiveTable.getColumnModel();
		hiddenColAllArchiveTasks.removeColumn(hiddenColAllArchiveTasks.getColumn(0));

		trashPane = new JTabbedPane(JTabbedPane.TOP);
		trashPane.setFont(new Font("Tahoma", Font.BOLD, 14));
		trashPane.setForeground(new Color(153, 0, 0));
		tabbedPane.addTab("Trash", null, trashPane, null);

		trashReceivedTasksPanel = new JPanel();
		trashReceivedTasksPanel.setLayout(new BorderLayout(0, 0));
		trashPane.addTab("Tasks Received", null, trashReceivedTasksPanel, null);
		trashReceivedTasksPanel.setLayout(new BorderLayout(0, 0));

		trashReceivedTasksTable = new JTable(trashReceivedTasksModel);
		trashReceivedTasksPanel.add(new JScrollPane(trashReceivedTasksTable));
		trashReceivedTasksPanel.add(trashReceivedTasksTable.getTableHeader(), BorderLayout.NORTH);

		trashReceivedTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = trashReceivedTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(trashReceivedTasks.get(row), MainWindow.this);
				}
			}
		});

		// hides taskID column from user
		TableColumnModel hiddenColTrashReceived = trashReceivedTasksTable.getColumnModel();
		hiddenColTrashReceived.removeColumn(hiddenColTrashReceived.getColumn(0));

		trashSentTasksPanel = new JPanel();
		trashSentTasksPanel.setLayout(new BorderLayout(0, 0));
		trashPane.addTab("Tasks Sent", null, trashSentTasksPanel, null);
		trashSentTasksPanel.setLayout(new BorderLayout(0, 0));

		trashSentTasksTable = new JTable(trashSentTasksModel);
		trashSentTasksPanel.add(new JScrollPane(trashSentTasksTable));
		trashSentTasksPanel.add(trashSentTasksTable.getTableHeader(), BorderLayout.NORTH);

		trashSentTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = trashSentTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(trashSentTasks.get(row), MainWindow.this);
				}
			}
		});

		// hides taskID column from user
		TableColumnModel hiddenColTrashSent = trashSentTasksTable.getColumnModel();
		hiddenColTrashSent.removeColumn(hiddenColTrashSent.getColumn(0));

		trashReceivedMessagesPanel = new JPanel();
		trashReceivedMessagesPanel.setLayout(new BorderLayout(0, 0));
		trashPane.addTab("Messages Received", null, trashReceivedMessagesPanel, null);
		trashReceivedMessagesPanel.setLayout(new BorderLayout(0, 0));

		trashReceivedMessagesTable = new JTable(trashReceivedMessagesModel);
		trashReceivedMessagesPanel.add(new JScrollPane(trashReceivedMessagesTable));
		trashReceivedMessagesPanel.add(trashReceivedMessagesTable.getTableHeader(), BorderLayout.NORTH);

		trashSentMessagesPanel = new JPanel();
		trashSentMessagesPanel.setLayout(new BorderLayout(0, 0));
		trashPane.addTab("Messages Sent", null, trashSentMessagesPanel, null);
		trashSentMessagesPanel.setLayout(new BorderLayout(0, 0));

		trashSentMessagesTable = new JTable(trashSentMessagesModel);
		trashSentMessagesPanel.add(new JScrollPane(trashSentMessagesTable));
		trashSentMessagesPanel.add(trashSentMessagesTable.getTableHeader(), BorderLayout.NORTH);

		myTasksTable.setAutoCreateRowSorter(true);
		allUserTasksTable.setAutoCreateRowSorter(true);
		inboxTasksTable.setAutoCreateRowSorter(true);
		archiveTable.setAutoCreateRowSorter(true);
		trashReceivedTasksTable.setAutoCreateRowSorter(true);

		inboxMessagesTable.setAutoCreateRowSorter(true);
		sentTasksTable.setAutoCreateRowSorter(true);
		sentMessagesTable.setAutoCreateRowSorter(true);
		trashSentTasksTable.setAutoCreateRowSorter(true);
		trashReceivedMessagesTable.setAutoCreateRowSorter(true);
		trashSentMessagesTable.setAutoCreateRowSorter(true);
		allUserArchiveTable.setAutoCreateRowSorter(true);

		trashSentTasksTable.setAutoCreateRowSorter(true);
		// createdByMeTable.setAutoCreateRowSorter(true);

		myTasksTable.getRowSorter().toggleSortOrder(10);
		allUserTasksTable.getRowSorter().toggleSortOrder(10);
		inboxTasksTable.getRowSorter().toggleSortOrder(10);
		archiveTable.getRowSorter().toggleSortOrder(10);
		trashReceivedTasksTable.getRowSorter().toggleSortOrder(10);
		// createdByMeTable.getRowSorter().toggleSortOrder(10);

		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 4);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 8;

		getTasks();

		Runnable refresh = new Runnable() {
			public void run() {
				getTasks();
			}
		};
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(refresh, 0, 30, TimeUnit.SECONDS);
	}

	/**
	 * Wrapper function for updating from the database
	 */
	void getTasks() {
		if (tabbedPane.getSelectedComponent().equals(tasksPane)) {
			if (tasksPane.getSelectedComponent().equals(myTasksPanel)) {
				storeTableState(myTasksTable);
			} else {
				storeTableState(allUserTasksTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(inboxPane)) {
			if (inboxPane.getSelectedComponent().equals(inboxTasksPanel)) {
				storeTableState(inboxTasksTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(sentPane)) {
			if (sentPane.getSelectedComponent().equals(sentTasksPanel)) {
				storeTableState(sentTasksTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(archivePane)) {
			if (archivePane.getSelectedComponent().equals(archivePanel)) {
				storeTableState(archiveTable);
			} else if (archivePane.getSelectedComponent().equals(allUserArchivePanel)) {
				storeTableState(allUserArchiveTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(trashPane)) {
			if (trashPane.getSelectedComponent().equals(trashReceivedTasksPanel)) {
				storeTableState(trashReceivedTasksTable);
			} else if (trashPane.getSelectedComponent().equals(trashSentTasksPanel)) {
				storeTableState(trashSentTasksTable);
			}
		}

		addTasksToUserTable(tasksModel);
		addAllTasksToTable(allTasksModel);
		addInboxTasksToTable(inboxTasksModel);
		addArchiveTasks(archiveModel);
		addAllArchiveTasks(allArchiveModel);
		addTrashTasksReceived(trashReceivedTasksModel);
		addInboxMessagesToTable(inboxMessagesModel);
		addSentTasksToTable(sentTasksModel);
		addSentMessagesToTable(sentMessagesModel);
		addTrashTasksSent(trashSentTasksModel);
		addTrashMessagesReceived(trashReceivedMessagesModel);
		addTrashMessagesSent(trashSentMessagesModel);
		if (inboxTasksSize > 0 || inboxMessagesSize > 0) {
			tabbedPane.setTitleAt(1, "Inbox (" + (inboxTasksSize + inboxMessagesSize) + ")");
		} else {
			tabbedPane.setTitleAt(1, "Inbox");
		}
		resizeColumns(myTasksTable);
		resizeColumns(allUserTasksTable);
		resizeColumns(inboxTasksTable);
		resizeColumns(archiveTable);
		resizeColumns(trashReceivedTasksTable);
		resizeColumns(allUserArchiveTable);

		if (tabbedPane.getSelectedComponent().equals(tasksPane)) {
			if (tasksPane.getSelectedComponent().equals(myTasksPanel)) {
				restoreTableState(myTasksTable);
			} else {
				restoreTableState(allUserTasksTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(inboxPane)) {
			if (inboxPane.getSelectedComponent().equals(inboxTasksPanel)) {
				restoreTableState(inboxTasksTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(sentPane)) {
			if (sentPane.getSelectedComponent().equals(sentTasksPanel)) {
				restoreTableState(sentTasksTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(archivePane)) {
			if (archivePane.getSelectedComponent().equals(archivePanel)) {
				restoreTableState(archiveTable);
			} else if (archivePane.getSelectedComponent().equals(allUserArchivePanel)) {
				restoreTableState(allUserArchiveTable);
			}
		} else if (tabbedPane.getSelectedComponent().equals(trashPane)) {
			if (trashPane.getSelectedComponent().equals(trashReceivedTasksPanel)) {
				restoreTableState(trashReceivedTasksTable);
			} else if (trashPane.getSelectedComponent().equals(trashSentTasksPanel)) {
				restoreTableState(trashSentTasksTable);
			}
		}
		// resizeColumns(createdByMeTable);

	}

	/**
	 * Protect tasks from being deleted by users who did not assign them
	 */
	void protectOtherUserTasks() {
		JOptionPane.showMessageDialog(null,
				"You cannot delete this task because" + "\n" + "you are not the user who created it.");
	}

	/**
	 * Print error message if no item is selected
	 * 
	 * @param item 
	 * 				the item selected
	 */
	void noneSelected(String item) {
		if (item.equals("Task")) {
			JOptionPane.showMessageDialog(null, "No Tasks Selected.");
		} else if (item.equals("Message")) {
			JOptionPane.showMessageDialog(null, "No Messages Selected.");
		} else if (item.equals("Sub Task")) {
			JOptionPane.showMessageDialog(null, "No Sub Tasks Selected.");
		} else {
			JOptionPane.showMessageDialog(null, "No Items Selected.");
		}
	}

	/**
	 * Get all the tasks that are assigned to the logged in user and add them to the
	 * tasks table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addTasksToUserTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(getUserID(), "user", "");
		addTasksToTable(tasks, model);
		myTasks = tasks;
	}

	/**
	 * Get all the tasks that were found in search and add them to the search table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addTasksToSearchTable(DefaultTableModel model, String table, String search) {
		tasks = new SQLQueryBuilder().getTasks(getUserID(), table, search);
		addTasksToTable(tasks, model);
		searchTasks = tasks;
		System.out.println(searchTasks.size() + " results found.");
	}

	/**
	 * Get all of the tasks in the database and add them to the all tasks table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addAllTasksToTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(getUserID(), "all", "");
		addTasksToTable(tasks, model);
		allUserTasks = tasks;
	}

	/**
	 * Get all the tasks that are newly assigned to the logged in user and add them
	 * to the inboxTasks table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addInboxTasksToTable(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "inboxTasks", "");
		addTasksToTable(tasks, model);
		inboxTasksSize = tasks.size();
		if (inboxTasksSize > 0) {
			inboxPane.setTitleAt(0, "Inbox Tasks (" + inboxTasksSize + ")");
		} else {
			inboxPane.setTitleAt(0, "Inbox Tasks");
		}
		inboxTasks = tasks;
	}

	/**
	 * Get all the messages that are assigned to the logged in user and add them to
	 * the inboxMessages table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addInboxMessagesToTable(DefaultTableModel model) {
		messages = new SQLQueryBuilder().getMessages(userID, "inboxMessages");
		addMessagesToTable(messages, model, false);
		inboxMessagesSize = messages.size();
		if (inboxMessagesSize > 0) {
			inboxPane.setTitleAt(1, "Inbox Messages (" + inboxMessagesSize + ")");
		} else {
			inboxPane.setTitleAt(1, "Inbox Messages");
		}
		inboxMessages = messages;
	}

	/**
	 * Get all the tasks that are newly sent by the logged in user and add them to
	 * the sentTasks table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addSentTasksToTable(DefaultTableModel model) {

		tasks = new SQLQueryBuilder().getTasks(userID, "sentTasks", "");
		addTasksToTable(tasks, model);
		sentTasks = tasks;
	}

	/**
	 * Get all the messages that were sent by the logged in user and add them to the
	 * sentMessages table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addSentMessagesToTable(DefaultTableModel model) {
		messages = new SQLQueryBuilder().getMessages(userID, "sentMessages");
		addMessagesToTable(messages, model, true);
		sentMessages = messages;
	}

	/**
	 * Get all the completed tasks that are assigned to the logged in user and add
	 * them to the tasks table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addArchiveTasks(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(getUserID(), "archive", "");
		addTasksToTable(tasks, model);
		archiveTasks = tasks;
	}

	/**
	 * Get all the completed tasks that are assigned to all users and add
	 * them to the tasks table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addAllArchiveTasks(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(getUserID(), "allArchive", "");
		addTasksToTable(tasks, model);
		allArchiveTasks = tasks;
	}

	/**
	 * Get all trashed tasks that were received by the logged in user and add
	 * them to the trashReceivedTasks table
	 * 
	 * @param model
	 * 				the table model that the tasks are added to
	 */
	void addTrashTasksReceived(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "trashReceivedTasks", "");
		addTasksToTable(tasks, model);
		trashReceivedTasks = tasks;
	}

	/**
	 * Get all trashed tasks that were sent by the logged in user and add
	 * them to the trashSentTasks table
	 * 
	 * @param model
	 * 				the table model that the tasks are added to
	 */
	void addTrashTasksSent(DefaultTableModel model) {
		tasks = new SQLQueryBuilder().getTasks(userID, "trashSentTasks", "");
		addTasksToTable(tasks, model);
		trashSentTasks = tasks;
	}

	/**
	 * Get all the messages that were received by the logged in user and add them to
	 * the receivedMessagesTrash table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addTrashMessagesReceived(DefaultTableModel model) {
		messages = new SQLQueryBuilder().getMessages(userID, "inboxMessagesTrash");
		addMessagesToTable(messages, model, false);
		inboxTrashMessages = messages;
	}

	/**
	 * Get all the messages that were sent by the logged in user and add them to the
	 * sentMessagesTrash table
	 * 
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addTrashMessagesSent(DefaultTableModel model) {
		messages = new SQLQueryBuilder().getMessages(userID, "sentMessagesTrash");
		addMessagesToTable(messages, model, true);
		sentTrashMessages = messages;
	}

	/**
	 * Add the given list of tasks to the given table model
	 * 
	 * @param tasks
	 *            ArrayList of task objects that are to be added to the table
	 * @param model
	 *            the table model that the tasks are added to
	 */
	void addTasksToTable(ArrayList<Task> tasks, DefaultTableModel model) {
		model.setRowCount(0);
		for (int i = 0; i < tasks.size(); i++) {
			new SimpleDateFormat("yyyy/MM/dd");
			String num = tasks.get(i).getProjectNum();
			String name = tasks.get(i).getName();
			Date dateDue = tasks.get(i).getDateDue();
			String assignedUser = tasks.get(i).getAssignedUserName();
			String assignedBy = (new SQLQueryBuilder()).getUserNameFromID(tasks.get(i).getCreatedByID());
			String description = tasks.get(i).getDescription();
			String notes = tasks.get(i).getNotes();
			String percentComplete = tasks.get(i).getPercentComplete();
			String id = Integer.toString(tasks.get(i).getTaskID());
			String category = tasks.get(i).getCategory();
			String thisPriority = Integer.toString(tasks.get(i).getPriority());

			Object[] entry = { id, Integer.parseInt(num), name, category, dateDue.toString(), assignedUser, assignedBy,
					description, notes, percentComplete, thisPriority };

			model.addRow(entry);
		}
	}

	/**
	 * Add the given list of tasks to the given table model
	 * 
	 * @param messages
	 *            ArrayList of message objects that are to be added to the table
	 * @param model
	 *            the table model that the messages are added to
	 */
	void addMessagesToTable(ArrayList<Message> messages, DefaultTableModel model, boolean sentTab) {
		model.setRowCount(0);
		if (!sentTab) {
			for (int i = 0; i < messages.size(); i++) {
				String from = messages.get(i).getSender();
				String message = messages.get(i).getMessage();

				Object[] entry = { from, message };
				model.addRow(entry);
			}
		} else {
			for (int i = 0; i < messages.size(); i++) {
				String to = messages.get(i).getReceiver();
				String message = messages.get(i).getMessage();

				Object[] entry = { to, message };
				model.addRow(entry);
			}
		}
	}

	/**
	 * Add all users to list of users to be selected from 
	 * and return the populated list
	 * 
	 * @param userField 
	 * 				The list of users to be populated 
	 * @return userField
	 * 				The populated list of users
	 */
	JComboBox<String> addUsersToList(JComboBox<String> userField) {
		users = new SQLQueryBuilder().getUsers();
		for (int i = 0; i < users.size(); i++) {
			userField.addItem(users.get(i));
		}
		return userField;
	}

	void resizeColumns(JTable table) {
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);

		table.getColumnModel().getColumn(3).setMinWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		// table.getColumnModel().getColumn(3).setMaxWidth( 80 );
		table.getColumnModel().getColumn(4).setMinWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		// table.getColumnModel().getColumn(4).setMaxWidth( 100 );
		table.getColumnModel().getColumn(5).setMinWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		// table.getColumnModel().getColumn(5).setMaxWidth( 100 );
		table.getColumnModel().getColumn(7).setMinWidth(40);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(7).setMaxWidth(40);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		for (int column = 1; column < table.getColumnCount() - 1; column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();
			if (column == 2 || column == 3) {

			} else {

				for (int row = 0; row < table.getRowCount(); row++) {
					TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
					Component c = table.prepareRenderer(cellRenderer, row, column);
					int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
					preferredWidth = Math.max(preferredWidth, width);

					// We've exceeded the maximum width, no need to check other rows

					if (preferredWidth >= maxWidth) {
						preferredWidth = maxWidth;
						break;
					}
				}
			}
			tableColumn.setPreferredWidth(preferredWidth);
		}

		/*
		 * if(!(table.getColumnCount() == 0)) { for (int column = 0; column <
		 * table.getColumnCount(); column++) { TableColumn tableColumn =
		 * table.getColumnModel().getColumn(column); int minWidth =
		 * tableColumn.getMinWidth(); int maxWidth = tableColumn.getMaxWidth();
		 * 
		 * for (int row = 0; row < table.getRowCount(); row++) { TableCellRenderer
		 * cellRenderer = table.getCellRenderer(row, column); Component component =
		 * table.prepareRenderer(cellRenderer, row, column); int width =
		 * component.getPreferredSize().width + table.getIntercellSpacing().width;
		 * minWidth = Math.max(minWidth, width);
		 * 
		 * // We've exceeded the maximum width, no need to check other rows
		 * 
		 * if (minWidth >= maxWidth) { minWidth = maxWidth; break; } }
		 * 
		 * tableColumn.setPreferredWidth( minWidth ); } }
		 */
	}

	/**
	 * Return the user ID associated with the user currently logged in
	 * 
	 * @return userID
	 * 					the user ID of the user currently logged in
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * Set the user ID of the current user
	 * 
	 * @param userID
	 * 				the user ID of the user to be logged in
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * Store the state of a specific table's column widths
	 * 
	 * @param table
	 * 				the JTable who's column widths will be stored
	 */
	public void storeTableState(JTable table) {
		columnWidths = new int[table.getColumnCount()];
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnWidths[i] = table.getColumnModel().getColumn(i).getWidth();
		}
	}

	/**
	 * Set the state of a specific table's column widths to the widths stored in the columnWidths array
	 * 
	 * @param table
	 * 				the JTable who's column widths will be set
	 */
	private void restoreTableState(JTable table) {
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
		}
	}
}
