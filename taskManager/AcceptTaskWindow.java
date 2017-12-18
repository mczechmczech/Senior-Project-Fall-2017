package taskManager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
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
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

public class AcceptTaskWindow {
	private JFrame frmAcceptTaskWindow;
	private JPanel acceptTaskPanel = new JPanel();
	private JTextField projectNumTextField;
	private JTextField nameTextField;
	private JTextField dueDateTextField;
	private JTextField descriptionTextField;
	private JTextField notesTextField;
	private JTextField assignedUserTextField;
	private JTextField cbCategory;
	private String[] completion = { "0%", "25%", "50%", "75%", "100%" };
	private Integer[] priority = { 1, 2, 3, 4, 5 };
	private final JComboBox<String> cbPercentComplete = new JComboBox(completion);
	private final JComboBox<Integer> cbPriority = new JComboBox(priority);
	private static Image iconImg;

	// this constructor is for editing tasks
	public AcceptTaskWindow(Task task, MainWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// System.out.println("Connecting database...");

				try {
					initialize(task, pWindow);
					frmAcceptTaskWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// initialize method for any new EditTaskWindow object
	/**
	 * 
	 * @param t
	 * @param pWin
	 */
	private void initialize(Task t, MainWindow pWin) {
		frmAcceptTaskWindow = new JFrame();
		frmAcceptTaskWindow.setTitle("Accept/Decline");
		frmAcceptTaskWindow.setBounds(100, 100, 450, 340);
		frmAcceptTaskWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAcceptTaskWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		GridBagLayout gbl_acceptTaskPanel = new GridBagLayout();
		gbl_acceptTaskPanel.columnWidths = new int[] { 30, 0, 30, 0, 0 };
		gbl_acceptTaskPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_acceptTaskPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_acceptTaskPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		acceptTaskPanel.setBackground(Color.LIGHT_GRAY);
		acceptTaskPanel.setLayout(gbl_acceptTaskPanel);
		frmAcceptTaskWindow.getContentPane().add(acceptTaskPanel);

		JLabel lblProjectNum = new JLabel("Project Number");
		lblProjectNum.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_ProjectNum = new GridBagConstraints();
		gbc_ProjectNum.insets = new Insets(0, 0, 5, 5);
		gbc_ProjectNum.gridx = 1;
		gbc_ProjectNum.gridy = 1;
		acceptTaskPanel.add(lblProjectNum, gbc_ProjectNum);

		projectNumTextField = new JTextField();
		projectNumTextField.setEditable(false);
		projectNumTextField.setEnabled(false);
		projectNumTextField.setColumns(10);
		GridBagConstraints gbc_projectNumTextField = new GridBagConstraints();
		gbc_projectNumTextField.insets = new Insets(0, 0, 5, 0);
		gbc_projectNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNumTextField.gridx = 3;
		gbc_projectNumTextField.gridy = 1;
		acceptTaskPanel.add(projectNumTextField, gbc_projectNumTextField);
		projectNumTextField.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		acceptTaskPanel.add(lblName, gbc_lblName);

		nameTextField = new JTextField();
		nameTextField.setEditable(false);
		nameTextField.setEnabled(false);
		nameTextField.setColumns(10);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 3;
		gbc_nameTextField.gridy = 2;
		acceptTaskPanel.add(nameTextField, gbc_nameTextField);

		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		acceptTaskPanel.add(lblDueDate, gbc_label);

		dueDateTextField = new JTextField();
		dueDateTextField.setEditable(false);
		dueDateTextField.setEnabled(false);
		dueDateTextField.setColumns(10);
		GridBagConstraints gbc_dueDateTextField = new GridBagConstraints();
		gbc_dueDateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dueDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dueDateTextField.gridx = 3;
		gbc_dueDateTextField.gridy = 3;
		acceptTaskPanel.add(dueDateTextField, gbc_dueDateTextField);

		JLabel lblAssignedUser = new JLabel("Assigned User");
		lblAssignedUser.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		acceptTaskPanel.add(lblAssignedUser, gbc_label_1);

		assignedUserTextField = new JTextField();
		assignedUserTextField.setColumns(10);
		assignedUserTextField.setEditable(false);
		assignedUserTextField.setEnabled(false);
		GridBagConstraints gbc_assignedUserTextField = new GridBagConstraints();
		gbc_assignedUserTextField.insets = new Insets(0, 0, 5, 0);
		gbc_assignedUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_assignedUserTextField.gridx = 3;
		gbc_assignedUserTextField.gridy = 4;
		acceptTaskPanel.add(assignedUserTextField, gbc_assignedUserTextField);

		JLabel lblDescrip = new JLabel("Description");
		lblDescrip.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 5;
		acceptTaskPanel.add(lblDescrip, gbc_label_2);

		descriptionTextField = new JTextField();
		descriptionTextField.setEditable(false);
		descriptionTextField.setEnabled(false);
		descriptionTextField.setColumns(10);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 3;
		gbc_descriptionTextField.gridy = 5;
		acceptTaskPanel.add(descriptionTextField, gbc_descriptionTextField);

		JLabel lblNotes = new JLabel("Notes");
		lblNotes.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 6;
		acceptTaskPanel.add(lblNotes, gbc_label_3);

		notesTextField = new JTextField();
		notesTextField.setEditable(false);
		notesTextField.setEnabled(false);
		notesTextField.setColumns(10);
		GridBagConstraints gbc_notesTextField = new GridBagConstraints();
		gbc_notesTextField.insets = new Insets(0, 0, 5, 0);
		gbc_notesTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_notesTextField.gridx = 3;
		gbc_notesTextField.gridy = 6;
		acceptTaskPanel.add(notesTextField, gbc_notesTextField);

		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_Category = new GridBagConstraints();
		gbc_Category.gridx = 1;
		gbc_Category.gridy = 7;
		gbc_Category.insets = new Insets(0, 0, 5, 5);
		acceptTaskPanel.add(lblCategory, gbc_Category);

		cbCategory = new JTextField();
		cbCategory.setEditable(false);
		cbCategory.setEnabled(false);
		GridBagConstraints gbc_cbCategory = new GridBagConstraints();
		gbc_cbCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cbCategory.gridx = 3;
		gbc_cbCategory.gridy = 7;
		acceptTaskPanel.add(cbCategory, gbc_cbCategory);

		cbPercentComplete.setEditable(false);
		cbPercentComplete.setEnabled(false);
		cbPercentComplete.setBounds(107, 65, 123, 25);
		cbPercentComplete.setVisible(true);
		acceptTaskPanel.add(cbPercentComplete);

		// only allows digits to be entered in the percent complete combo box
		cbPercentComplete.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (cbPercentComplete.getEditor().getItem().toString().length() < 4) {
					if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						frmAcceptTaskWindow.getToolkit().beep();
						e.consume();
					}
				} else {
					e.consume();
				}

				// check to see if percent symbol is still in combo box string
				// if it isn't, automatically append it to combo box string
				if (!((cbPercentComplete.getEditor().getItem().toString()).contains("%"))) {
					frmAcceptTaskWindow.getToolkit().beep();
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
		acceptTaskPanel.add(lblPercentComplete, gbc_PercentComplete);
		GridBagConstraints gbc_cbPercentComplete = new GridBagConstraints();
		gbc_cbPercentComplete.insets = new Insets(0, 0, 5, 0);
		gbc_cbPercentComplete.gridx = 3;
		gbc_cbPercentComplete.gridy = 8;
		acceptTaskPanel.add(cbPercentComplete, gbc_cbPercentComplete);

		JLabel lblPriority = new JLabel("Priority:");
		lblPriority.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 9;
		acceptTaskPanel.add(lblPriority, gbc_lblPriority);

		cbPriority.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		cbPriority.setEditable(false);
		cbPriority.setEnabled(false);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 9;
		acceptTaskPanel.add(cbPriority, gbc_comboBox);

		projectNumTextField.setText(t.getProjectNum());
		nameTextField.setText(t.getName());
		dueDateTextField.setText(t.getDateDue().toString());
		assignedUserTextField.setText(t.getAssignedUserName());
		descriptionTextField.setText(t.getDescription());
		notesTextField.setText(t.getNotes());
		cbPercentComplete.setSelectedItem(t.getPercentComplete());
		cbCategory.setText(t.getCategory());
		cbPriority.setSelectedItem(Integer.toString(t.getPriority()));

		JButton btnAccept = new JButton("Accept");
		btnAccept.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAccept.setForeground(new Color(0, 102, 0));
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 10;
		acceptTaskPanel.add(btnAccept, gbc_btnAccept);

		// the user has hit the button to accept a task
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SQLQueryBuilder().taskAccepted(t.getTaskID());
				pWin.getTasks();
				frmAcceptTaskWindow.dispose();
			}
		});

		JButton btnDecline = new JButton("Decline");
		btnDecline.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDecline.setForeground(new Color(153, 0, 0));
		GridBagConstraints gbc_btnDecline = new GridBagConstraints();
		gbc_btnDecline.gridx = 3;
		gbc_btnDecline.gridy = 10;
		acceptTaskPanel.add(btnDecline, gbc_btnDecline);

		// the user has hit the button to deny a task
		btnDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String currentUser = new SQLQueryBuilder().getUserNameFromID(pWin.getUserID());
				new SQLQueryBuilder().newMessage(new SQLQueryBuilder().getUserCreatedID(t.getTaskID()),
						currentUser.concat(
								" has declined your task " + "\"" + t.getName() + "\"" + " and it is now unassigned."),
						pWin.getUserID());
				t.edit(projectNumTextField.getText(), nameTextField.getText(), t.getDateDue(), "Unassigned",
						descriptionTextField.getText(), notesTextField.getText(),
						(String) cbPercentComplete.getSelectedItem(), cbCategory.getText(),
						Integer.parseInt((String) cbPriority.getSelectedItem()));
				new SQLQueryBuilder(t).editTask(t.getTaskID());
				new SQLQueryBuilder().taskAccepted(t.getTaskID());
				frmAcceptTaskWindow.dispose();
				pWin.getTasks();
			}
		});
	}
}
