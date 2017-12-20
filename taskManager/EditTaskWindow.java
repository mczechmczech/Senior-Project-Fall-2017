package taskManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

public class EditTaskWindow {
	private JFrame frmEditTaskWindow;
	private JPanel editTaskPanel = new JPanel();
	private JTextField projectNumTextField;
	private JTextField nameTextField;
	private JTextField dueDateTextField;
	private JTextField descriptionTextField;
	private JTextField notesTextField;
	private JComboBox assignedUserTextField;
	private DatePicker dp;
	private String[] completion = { "0%", "25%", "50%", "75%", "100%" };
	private Integer[] priority = { 1, 2, 3, 4, 5 };
	private JComboBox<String> cbCategory;
	private final JComboBox<String> cbPercentComplete = new JComboBox(completion);
	private final JComboBox<Integer> cbPriority = new JComboBox(priority);
	private String[] taskColumnNames = { "Task ID", "#", "Name", "Date Due", "Assigned User", "Description", "Notes",
			"Completion", "Priority" };
	private DefaultTableModel tasksModel = new TaskTableModel(taskColumnNames, 0);
	private java.util.Date javaDate;
	private java.sql.Date sqlDate;
	private JTable table;
	private Task t;
	private int userID;
	private ArrayList<Task> tasks;
	private ArrayList<String> categories = new ArrayList<>();
	private int parentID;

	// this constructor is for editing tasks
	/**
	 * @wbp.parser.constructor
	 * @param task
	 * @param pWindow
	 */
	public EditTaskWindow(Task task, MainWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize(pWindow);
					initializeEdit(task, pWindow);
					frmEditTaskWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// this constructor is for new tasks
	/**
	 * @param userID
	 * @param pWindow
	 * @param parentID
	 */
	public EditTaskWindow(int userID, MainWindow pWindow, int parentID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize(pWindow);
					initializeNew(userID, pWindow, parentID);
					frmEditTaskWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// this constructor is for new subtasks
	/**
	 * @param userID
	 * @param pWindow
	 * @param parent
	 * @param parentID
	 */
	public EditTaskWindow(int userID, MainWindow pWindow, EditTaskWindow parent, int parentID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize(pWindow);
					initializeNew(userID, pWindow, parent, parentID);
					frmEditTaskWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// initialize method for when tasks are going to be edited
	/**
	 * @param t
	 * @param pWin
	 */
	private void initializeEdit(Task t, MainWindow pWin) {
		this.t = t;
		this.parentID = t.getTaskID();
		frmEditTaskWindow.setTitle("Edit Task");
		projectNumTextField.setText(t.getProjectNum());
		projectNumTextField.setEditable((t.getParentID() == 0) ? true : false);
		projectNumTextField.setEnabled((t.getParentID() == 0) ? true : false);
		nameTextField.setText(t.getName());
		dp.setDate(t.getDateDue().toLocalDate());
		assignedUserTextField.setSelectedItem(t.getAssignedUserName());
		descriptionTextField.setText(t.getDescription());
		notesTextField.setText(t.getNotes());
		cbCategory.setSelectedItem(t.getCategory());
		cbPercentComplete.setSelectedItem(t.getPercentComplete());
		cbPriority.setSelectedItem(Integer.toString(t.getPriority()));

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(0, 102, 0));
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 10;
		editTaskPanel.setBackground(Color.LIGHT_GRAY);
		editTaskPanel.add(btnSave, gbc_btnSave);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String percent = (String) cbPercentComplete.getSelectedItem();
				if (nameTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "The task must be named");
				} else if ((percent.length() > 1) && (Character.isDigit(percent.charAt(percent.length() - 1))
						|| (Integer.parseInt(percent.substring(0, percent.length() - 1))) > 100)) {
					JOptionPane.showMessageDialog(null, "The percentage must be " + "\n" + "between 0% and 100%.");
					cbPercentComplete.setSelectedIndex(0);
				} else {
					if (percent.length() == 1) {
						cbPercentComplete.setSelectedIndex(0);
					}

					String category = (String) cbCategory.getSelectedItem();

					if (!(new SQLQueryBuilder().containsCategory(category))) {
						new SQLQueryBuilder().addCategory(category);
					}

					t.edit(projectNumTextField.getText(), nameTextField.getText(), java.sql.Date.valueOf(dp.getDate()),
							assignedUserTextField.getEditor().getItem().toString(), descriptionTextField.getText(),
							notesTextField.getText(), (String) cbPercentComplete.getSelectedItem(), category,
							Integer.parseInt((String) cbPriority.getSelectedItem()));

					new SQLQueryBuilder(t).editTask(t.getTaskID());
					new SQLQueryBuilder(t).retrieveFromTrash(t.getTaskID());
					pWin.getTasks();
					frmEditTaskWindow.dispose();
				}
			}
		});
		JPanel myTasksPanel = new JPanel();
		myTasksPanel.setLayout(new BorderLayout(0, 0));
		frmEditTaskWindow.getContentPane().add(myTasksPanel);

		JTable myTasksTable = new JTable(tasksModel) {
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

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		myTasksPanel.add(panel, BorderLayout.NORTH);
		JLabel lblSubtasks = new JLabel("Subtasks");
		lblSubtasks.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblSubtasks);

		Box horizontalBox = Box.createHorizontalBox();
		panel.add(horizontalBox);

		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		JButton btnCreateSubTask = new JButton("Create");
		btnCreateSubTask.setForeground(new Color(0, 102, 0));
		btnCreateSubTask.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox.add(btnCreateSubTask);

		EditTaskWindow edit = this;

		btnCreateSubTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditTaskWindow(pWin.getUserID(), pWin, edit, t.getTaskID());
			}
		});

		JButton btnDeleteSubTask = new JButton("Delete");
		btnDeleteSubTask.setForeground(new Color(204, 0, 0));
		btnDeleteSubTask.setFont(new Font("Tahoma", Font.BOLD, 14));
		horizontalBox.add(btnDeleteSubTask);

		btnDeleteSubTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tableRowSelected = -1;
				tableRowSelected = myTasksTable.getSelectedRow();
				if (tableRowSelected == -1) {
					pWin.noneSelected("Sub Task");
				} else {
					new SQLQueryBuilder().putInTrash(tasks.get(tableRowSelected).getTaskID());
				}

				pWin.getTasks();
			}
		});

		myTasksTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = myTasksTable.convertRowIndexToModel(target.getSelectedRow());
					new EditTaskWindow(tasks.get(row), pWin);
				}
			}
		});

		// hides taskID column from user
		TableColumnModel hiddenColMyTasks = myTasksTable.getColumnModel();
		hiddenColMyTasks.removeColumn(hiddenColMyTasks.getColumn(0));

		pWin.resizeColumns(myTasksTable);
		addSubTasksToTable(tasksModel, t.getTaskID());
	}

	// initialize method for when a new task is going to be created
	/**
	 * @param uID
	 * @param pWin
	 * @param parentID
	 */
	private void initializeNew(int uID, MainWindow pWin, int parentID) {
		this.userID = uID;
		this.parentID = parentID;
		frmEditTaskWindow.setTitle("New Task");
		assignedUserTextField.setSelectedItem(new SQLQueryBuilder().getUserNameFromID(uID));

		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 10;
		editTaskPanel.add(btnCreate, gbc_btnCreate);

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String percent = (String) cbPercentComplete.getSelectedItem();
				if (nameTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "The task must be named");
				} else if ((percent.length() > 1) && (Character.isDigit(percent.charAt(percent.length() - 1))
						|| (Integer.parseInt(percent.substring(0, percent.length() - 1))) > 100)) {
					JOptionPane.showMessageDialog(null, "The percentage must be " + "\n" + "between 0% and 100%.");
					cbPercentComplete.setSelectedIndex(0);
				} else if (projectNumTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Project Number cannot be blank.");
				} else if (dp.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Due Date cannot be blank.");
				} else {

					try {
						javaDate = (new SimpleDateFormat("yyyy/MM/dd")).parse(dp.getText());
						sqlDate = new java.sql.Date(javaDate.getTime());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					System.out.println(parentID);
					if (percent.length() == 1) {
						cbPercentComplete.setSelectedIndex(0);
					}
					String category = (String) cbCategory.getSelectedItem();
					if (!(new SQLQueryBuilder().containsCategory(category))) {
						new SQLQueryBuilder().addCategory(category);
					}

					Task newTask = new Task(projectNumTextField.getText(), parentID, nameTextField.getText(), sqlDate,
							(String) assignedUserTextField.getSelectedItem(), descriptionTextField.getText(),
							notesTextField.getText(), (String) cbPercentComplete.getSelectedItem(), true, category,
							Integer.parseInt((String) cbPriority.getSelectedItem()), userID);

					String userName = new SQLQueryBuilder().getUserNameFromID(uID);

					if (userName.equals(newTask.getAssignedUserName())) {
						new SQLQueryBuilder(newTask).addTask(uID, false);
					} else {
						new SQLQueryBuilder(newTask).addTask(uID, true);
					}

					pWin.getTasks();
					projectNumTextField.setText("");
					nameTextField.setText("");
					dueDateTextField.setText("");
					dp.setText("");
					assignedUserTextField.setSelectedItem("");

					descriptionTextField.setText("");
					notesTextField.setText("");
					cbPercentComplete.setSelectedIndex(0);
					frmEditTaskWindow.dispose();
				}
			}
		});
	}

	// initialize method for when a new subtask is going to be created
	/**
	 * @param uID
	 * @param pWin
	 * @param parentWindow
	 * @param parentID
	 */
	private void initializeNew(int uID, MainWindow pWin, EditTaskWindow parentWindow, int parentID) {
		this.userID = uID;
		this.parentID = parentID;
		frmEditTaskWindow.setTitle("New Task");
		projectNumTextField.setEditable(false);
		projectNumTextField.setEnabled(false);
		projectNumTextField.setText(parentWindow.projectNumTextField.getText());
		assignedUserTextField.setSelectedItem(new SQLQueryBuilder().getUserNameFromID(uID));

		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 10;
		editTaskPanel.add(btnCreate, gbc_btnCreate);

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String percent = (String) cbPercentComplete.getSelectedItem();
				if (nameTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "The task must be named");
				} else if ((percent.length() > 1) && (Character.isDigit(percent.charAt(percent.length() - 1))
						|| (Integer.parseInt(percent.substring(0, percent.length() - 1))) > 100)) {
					JOptionPane.showMessageDialog(null, "The percentage must be " + "\n" + "between 0% and 100%.");
					cbPercentComplete.setSelectedIndex(0);
				} else {
					try {
						javaDate = (new SimpleDateFormat("yyyy/MM/dd")).parse(dp.getText());
						sqlDate = new java.sql.Date(javaDate.getTime());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(parentID);
					if (percent.length() == 1) {
						cbPercentComplete.setSelectedIndex(0);
					}
					String category = (String) cbCategory.getSelectedItem();
					if (!(new SQLQueryBuilder().containsCategory(category))) {
						new SQLQueryBuilder().addCategory(category);
					}

					Task newTask = new Task(projectNumTextField.getText(), parentID, nameTextField.getText(), sqlDate,
							(String) assignedUserTextField.getSelectedItem(), descriptionTextField.getText(),
							notesTextField.getText(), (String) cbPercentComplete.getSelectedItem(), true, category,
							Integer.parseInt((String) cbPriority.getSelectedItem()), userID);
					String userName = new SQLQueryBuilder().getUserNameFromID(uID);

					new SQLQueryBuilder(newTask).addTask(uID,
							userName.equals(newTask.getAssignedUserName()) ? false : true);

					pWin.getTasks();
					projectNumTextField.setText("");
					nameTextField.setText("");
					dueDateTextField.setText("");
					dp.setText("");
					assignedUserTextField.setSelectedItem("");

					descriptionTextField.setText("");
					notesTextField.setText("");
					cbPercentComplete.setSelectedIndex(0);
					parentWindow.addSubTasksToTable(parentWindow.getTasksModel(), parentWindow.t.getTaskID());
					frmEditTaskWindow.dispose();
				}
			}
		});
	}

	/**
	 * initialize method for any new EditTaskWindow object
	 * 
	 * @param pWind
	 */
	private void initialize(MainWindow pWind) {
		frmEditTaskWindow = new JFrame();
		frmEditTaskWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(EditTaskWindow.class.getResource("/taskManager/Infinity_2.png")));
		frmEditTaskWindow.setBounds(100, 100, 896, 640);
		frmEditTaskWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEditTaskWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		GridBagLayout gbl_editTaskPanel = new GridBagLayout();
		gbl_editTaskPanel.columnWidths = new int[] { 30, 0, 30, 0, 0 };
		gbl_editTaskPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_editTaskPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_editTaskPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		editTaskPanel.setLayout(gbl_editTaskPanel);
		frmEditTaskWindow.getContentPane().add(editTaskPanel);

		JLabel lblProjectNum = new JLabel("Project Number");
		lblProjectNum.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_ProjectNum = new GridBagConstraints();
		gbc_ProjectNum.insets = new Insets(0, 0, 5, 5);
		gbc_ProjectNum.gridx = 1;
		gbc_ProjectNum.gridy = 1;
		editTaskPanel.add(lblProjectNum, gbc_ProjectNum);

		projectNumTextField = new JTextField();
		GridBagConstraints gbc_projectNumTextField = new GridBagConstraints();
		gbc_projectNumTextField.insets = new Insets(0, 0, 5, 0);
		gbc_projectNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNumTextField.gridx = 3;
		gbc_projectNumTextField.gridy = 1;
		editTaskPanel.add(projectNumTextField, gbc_projectNumTextField);
		projectNumTextField.setColumns(10);

		// only allows digits to be entered in the project number text field
		projectNumTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					frmEditTaskWindow.getToolkit().beep();
					e.consume();
				}
			}
		});

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		editTaskPanel.add(lblName, gbc_lblName);

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 3;
		gbc_nameTextField.gridy = 2;
		editTaskPanel.add(nameTextField, gbc_nameTextField);

		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		editTaskPanel.add(lblDueDate, gbc_label);

		dueDateTextField = new JTextField();
		dueDateTextField.setColumns(10);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		DatePickerSettings ds = new DatePickerSettings();
		ds.setFormatForDatesCommonEra("yyyy/MM/dd");
		dp = new DatePicker(ds);
		GridBagConstraints gbc_dueDateTextField = new GridBagConstraints();
		gbc_dueDateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dueDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dueDateTextField.gridx = 3;
		gbc_dueDateTextField.gridy = 3;
		editTaskPanel.add(dp, gbc_dueDateTextField);

		JLabel lblAssignedUser = new JLabel("Assigned User");
		lblAssignedUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		editTaskPanel.add(lblAssignedUser, gbc_label_1);

		assignedUserTextField = new JComboBox<String>();
		// assignedUserTextField.setColumns(10);
		assignedUserTextField.setEditable(true);
		assignedUserTextField.setEnabled(true);
		AutoCompletion.enable(assignedUserTextField);
		GridBagConstraints gbc_assignedUserTextField = new GridBagConstraints();
		gbc_assignedUserTextField.insets = new Insets(0, 0, 5, 0);
		gbc_assignedUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_assignedUserTextField.gridx = 3;
		gbc_assignedUserTextField.gridy = 4;
		assignedUserTextField = pWind.addUsersToList(assignedUserTextField);
		editTaskPanel.add(assignedUserTextField, gbc_assignedUserTextField);

		JLabel lblDescrip = new JLabel("Description");
		lblDescrip.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 5;
		editTaskPanel.add(lblDescrip, gbc_label_2);

		descriptionTextField = new JTextField();
		descriptionTextField.setColumns(10);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 3;
		gbc_descriptionTextField.gridy = 5;
		editTaskPanel.add(descriptionTextField, gbc_descriptionTextField);

		JLabel lblNotes = new JLabel("Notes");
		lblNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 6;
		editTaskPanel.add(lblNotes, gbc_label_3);

		notesTextField = new JTextField();
		notesTextField.setColumns(10);
		GridBagConstraints gbc_notesTextField = new GridBagConstraints();
		gbc_notesTextField.insets = new Insets(0, 0, 5, 0);
		gbc_notesTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_notesTextField.gridx = 3;
		gbc_notesTextField.gridy = 6;
		editTaskPanel.add(notesTextField, gbc_notesTextField);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_Category = new GridBagConstraints();
		gbc_Category.gridx = 1;
		gbc_Category.gridy = 7;
		gbc_Category.insets = new Insets(0, 0, 5, 5);
		editTaskPanel.add(lblCategory, gbc_Category);

		cbCategory = new JComboBox<String>();
		cbCategory.setEditable(true);
		cbCategory.setEnabled(true);
		GridBagConstraints gbc_cbCategory = new GridBagConstraints();
		gbc_cbCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cbCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbCategory.gridx = 3;
		gbc_cbCategory.gridy = 7;
		cbCategory = addCategoriesToList(cbCategory);
		editTaskPanel.add(cbCategory, gbc_cbCategory);

		cbPercentComplete.setEditable(true);
		cbPercentComplete.setBounds(107, 65, 123, 25);
		cbPercentComplete.setVisible(true);
		editTaskPanel.add(cbPercentComplete);

		// only allows digits to be entered in the percent complete combo box
		cbPercentComplete.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (cbPercentComplete.getEditor().getItem().toString().length() < 4) {
					if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						frmEditTaskWindow.getToolkit().beep();
						e.consume();
					}
				} else {
					e.consume();
				}

				// check to see if percent symbol is still in combo box string
				// if it isn't, automatically append it to combo box string
				if (!((cbPercentComplete.getEditor().getItem().toString()).contains("%"))) {
					frmEditTaskWindow.getToolkit().beep();
					cbPercentComplete.getEditor()
							.setItem(cbPercentComplete.getEditor().getItem().toString().concat("%"));
				}
			}
		});

		JLabel lblPercentComplete = new JLabel("Percent Complete:");
		lblPercentComplete.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_PercentComplete = new GridBagConstraints();
		gbc_PercentComplete.gridx = 1;
		gbc_PercentComplete.gridy = 8;
		gbc_PercentComplete.insets = new Insets(0, 0, 5, 5);
		editTaskPanel.add(lblPercentComplete, gbc_PercentComplete);
		GridBagConstraints gbc_cbPercentComplete = new GridBagConstraints();
		gbc_cbPercentComplete.insets = new Insets(0, 0, 5, 0);
		gbc_cbPercentComplete.gridx = 3;
		gbc_cbPercentComplete.gridy = 8;
		editTaskPanel.add(cbPercentComplete, gbc_cbPercentComplete);

		JLabel lblPriority = new JLabel("Priority:");
		lblPriority.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 9;
		editTaskPanel.add(lblPriority, gbc_lblPriority);

		cbPriority.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 9;
		editTaskPanel.add(cbPriority, gbc_comboBox);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setForeground(new Color(204, 0, 0));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 10;
		editTaskPanel.add(btnCancel, gbc_btnCancel);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectNumTextField.setText("");
				nameTextField.setText("");
				dueDateTextField.setText("");
				assignedUserTextField.getEditor().setItem("");
				descriptionTextField.setText("");
				notesTextField.setText("");
				cbPercentComplete.setSelectedIndex(0);
				frmEditTaskWindow.dispose();
			}
		});
	}

	/**
	 * @param categoryField
	 * @return
	 */
	JComboBox<String> addCategoriesToList(JComboBox<String> categoryField) {
		categories = new SQLQueryBuilder().getCategories();
		for (int i = 0; i < categories.size(); i++) {
			categoryField.addItem(categories.get(i));
		}
		return categoryField;
	}

	/**
	 * @param model
	 * @param taskID
	 */
	public void addSubTasksToTable(DefaultTableModel model, int taskID) {
		tasks = new SQLQueryBuilder().getSubTasks(taskID);
		addTasksToTable(tasks, model);
	}

	/**
	 * Add the given list of tasks to the given table model
	 * 
	 * @param tasks ArrayList of task objects that are to be added to the table
	 * @param model the table model that the tasks are added to
	 */
	void addTasksToTable(ArrayList<Task> tasks, DefaultTableModel model) {
		model.setRowCount(0);
		for (int i = 0; i < tasks.size(); i++) {
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

			Object[] entry = { id, Integer.parseInt(num), name, dateDue.toString(), assignedUser, description, notes,
					percentComplete, thisPriority };
			model.addRow(entry);
		}
	}

	/**
	 * @return
	 */
	DefaultTableModel getTasksModel() {
		return tasksModel;

	}
}
