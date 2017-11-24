package prototype_MinimalFunctionality;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;

public class EditTaskWindow
{
	private JFrame frmEditTaskWindow;
	private JPanel editTaskPanel = new JPanel();
	private JTextField projectNumTextField;
	private JTextField nameTextField;
	private JTextField dueDateTextField;
	private JTextField descriptionTextField;
	private JTextField notesTextField;
	private JComboBox assignedUserTextField;
	private DatePicker dp;
	private String[] completion = { "0%", "25%", "50%", "75%", "100%"};
	private Integer[] priority = {1, 2, 3, 4, 5};
	private final JComboBox<String> cbPercentComplete = new JComboBox(completion);
	private final JComboBox<Integer> cbPriority = new JComboBox(priority);
	
	private java.util.Date javaDate;
	private java.sql.Date sqlDate;
	
	//this constructor is for editing tasks
	/**
	 * @wbp.parser.constructor 
	 * @param task
	 * @param pWindow
	 */
	public EditTaskWindow(Task task, PrototypeWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//System.out.println("Connecting database...");

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
	
	//this constructor is for new tasks
	public EditTaskWindow(int userID, PrototypeWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//System.out.println("Connecting database...");

				try {
					initialize(pWindow);
					initializeNew(userID, pWindow);
					frmEditTaskWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//initialize method for when tasks are going to be edited
	private void initializeEdit(Task t, PrototypeWindow pWin) 
	{
		frmEditTaskWindow.setTitle("Edit Task");
		projectNumTextField.setText(t.getProjectNum());
		nameTextField.setText(t.getName());
		dp.setDate(t.getDateDue().toLocalDate());
		assignedUserTextField.setSelectedItem(t.getAssignedUserName());
		descriptionTextField.setText(t.getDescription());
		notesTextField.setText(t.getNotes());
		cbPercentComplete.setSelectedItem(t.getPercentComplete());
		cbPriority.getSelectedItem();
		
		JLabel lblPriority_1 = new JLabel("Priority:");
		GridBagConstraints gbc_lblPriority_1 = new GridBagConstraints();
		gbc_lblPriority_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority_1.gridx = 1;
		gbc_lblPriority_1.gridy = 8;
		editTaskPanel.add(lblPriority_1, gbc_lblPriority_1);
		
		JComboBox<String> comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 8;
		editTaskPanel.add(comboBox, gbc_comboBox);
		
		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 9;
		editTaskPanel.add(btnSave, gbc_btnSave);
		
		btnSave.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  if(!(nameTextField.getText().equals("")))
				  {		
					  t.edit(projectNumTextField.getText(), nameTextField.getText(), java.sql.Date.valueOf(dp.getDate()), 
							  			assignedUserTextField.getEditor().getItem().toString(), descriptionTextField.getText(), 
							  			notesTextField.getText(), (String) cbPercentComplete.getSelectedItem(), (Integer)cbPriority.getSelectedItem());
					  new SQLQueryBuilder(t).editTask(t.getTaskID());
					  new SQLQueryBuilder(t).retrieveFromTrash(t.getTaskID());
					  pWin.getTasks();
					  frmEditTaskWindow.dispose();
				  }
				  else
				  {
					  JOptionPane.showMessageDialog(null, "The task must be named");
				  }
				} 
				} );
	}
	
	//initialize method for when a new task is going to be created
	private void initializeNew(int uID, PrototypeWindow pWin)
	{
		frmEditTaskWindow.setTitle("New Task");
		assignedUserTextField.setSelectedItem(new SQLQueryBuilder().getUserNameFromID(uID));
		
		JLabel lblPriority = new JLabel("Priority:");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 8;
		editTaskPanel.add(lblPriority, gbc_lblPriority);
		
		cbPriority.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 8;
		editTaskPanel.add(cbPriority, gbc_comboBox);
		
		JButton btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 9;
		editTaskPanel.add(btnCreate, gbc_btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 9;
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
					  Task newTask = new Task(projectNumTextField.getText(), nameTextField.getText(), sqlDate, 
							  (String)assignedUserTextField.getSelectedItem(), descriptionTextField.getText(), 
							  notesTextField.getText(), (String) cbPercentComplete.getSelectedItem(), true, 
							  Integer.parseInt((String)cbPriority.getSelectedItem()));
					  String userName = new SQLQueryBuilder().getUserNameFromID(uID);
					  if(userName.equals(newTask.getAssignedUserName()))
					  {
						  new SQLQueryBuilder(newTask).addTask(uID, false);
					  }
					  else
					  {
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
				  else
				  {
					  JOptionPane.showMessageDialog(null, "A task name must be entered " + "\n" + "before a task can be created.");
				  }
				} 
				} );
	}
	
	/**@wbp.parser.constructor 
	 * initialize method for any new EditTaskWindow object
	 * @wbp.parser.constructor 
	 * @param pWind
	 * @wbp.parser.constructor 
	 */
	private void initialize(PrototypeWindow pWind)
	{
		frmEditTaskWindow = new JFrame();
		frmEditTaskWindow.setBounds(100, 100, 450, 300);
		frmEditTaskWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEditTaskWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		GridBagLayout gbl_editTaskPanel = new GridBagLayout();
		gbl_editTaskPanel.columnWidths = new int[] {30, 0, 30, 0, 0};
		gbl_editTaskPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_editTaskPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_editTaskPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		editTaskPanel.setLayout(gbl_editTaskPanel);
		frmEditTaskWindow.getContentPane().add(editTaskPanel);
		
		JLabel lblProjectNum = new JLabel("Project Number");
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
		
		//only allows digits to be entered in the project number text field
				projectNumTextField.addKeyListener(new KeyAdapter() {
		            public void keyTyped(KeyEvent e) {
		                char c = e.getKeyChar();
		                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) 
		                {
		                	frmEditTaskWindow.getToolkit().beep();
		                    e.consume();
		                } 
		            }
		        });
		
		JLabel lblName = new JLabel("Name");
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
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		editTaskPanel.add(lblAssignedUser, gbc_label_1);
		
		assignedUserTextField = new JComboBox<String>();
		//assignedUserTextField.setColumns(10);
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
		
		cbPercentComplete.setEditable(true);
		cbPercentComplete.setBounds(107, 65, 123, 25);
		cbPercentComplete.setVisible(true);
		editTaskPanel.add(cbPercentComplete);
		
		//only allows digits to be entered in the percent complete combo box
		cbPercentComplete.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (cbPercentComplete.getEditor().getItem().toString().length() < 4) 
                {
                    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) 
                    {
                        frmEditTaskWindow.getToolkit().beep();
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
                	frmEditTaskWindow.getToolkit().beep();
                	cbPercentComplete.getEditor().setItem(cbPercentComplete.getEditor().getItem().toString().concat("%"));
                }
            }
        });
		
		JLabel lblPercentComplete = new JLabel("Percent Complete:");
		GridBagConstraints gbc_PercentComplete = new GridBagConstraints();
		gbc_PercentComplete.gridx = 1;
		gbc_PercentComplete.gridy = 7;
		gbc_PercentComplete.insets = new Insets(0, 0, 5, 5);
		editTaskPanel.add(lblPercentComplete, gbc_PercentComplete);
		GridBagConstraints gbc_cbPercentComplete = new GridBagConstraints();
		gbc_cbPercentComplete.insets = new Insets(0, 0, 5, 0);
		gbc_cbPercentComplete.gridx = 3;
		gbc_cbPercentComplete.gridy = 7;
		editTaskPanel.add(cbPercentComplete, gbc_cbPercentComplete);
		
		JButton btnCancel = new JButton("Cancel");
-		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
-		gbc_btnCancel.gridx = 3;
-		gbc_btnCancel.gridy = 8;
-		editTaskPanel.add(btnCancel, gbc_btnCancel);
-		
-		btnCancel.addActionListener(new ActionListener() { 
-			  public void actionPerformed(ActionEvent e) { 
-				    projectNumTextField.setText("");
-				    nameTextField.setText("");
-				    dueDateTextField.setText("");
-				    assignedUserTextField.getEditor().setItem("");
-				    descriptionTextField.setText("");
-				    notesTextField.setText("");
-				    cbPercentComplete.setSelectedIndex(0);
-				    frmEditTaskWindow.dispose();
-				  } 
-				} );
	}
}
