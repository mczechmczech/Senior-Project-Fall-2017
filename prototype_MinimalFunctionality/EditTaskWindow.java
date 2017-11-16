package prototype_MinimalFunctionality;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EditTaskWindow
{
	private JFrame frmEditTaskWindow;
	private JTextField projectNumTextField;
	private JTextField nameTextField;
	private JTextField dueDateTextField;
	private JTextField descriptionTextField;
	private JTextField notesTextField;
	private JTextField assignedUserTextField;
	
	public EditTaskWindow(Task task, PrototypeWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//System.out.println("Connecting database...");

				try {
					initialize(task, pWindow);
					frmEditTaskWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initialize(Task t, PrototypeWindow pWin) 
	{
		frmEditTaskWindow = new JFrame();
		frmEditTaskWindow.setTitle("Edit Task");
		frmEditTaskWindow.setBounds(100, 100, 450, 300);
		frmEditTaskWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEditTaskWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel editTaskPanel = new JPanel();
		GridBagLayout gbl_editTaskPanel = new GridBagLayout();
		gbl_editTaskPanel.columnWidths = new int[] {30, 0, 30, 0, 0};
		gbl_editTaskPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_editTaskPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_editTaskPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		projectNumTextField.setText(t.getProjectNum());
		
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
		nameTextField.setText(t.getName());
		
		JLabel lblDueDate = new JLabel("Due Date");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		editTaskPanel.add(lblDueDate, gbc_label);
		
		dueDateTextField = new JTextField();
		dueDateTextField.setColumns(10);
		GridBagConstraints gbc_dueDateTextField = new GridBagConstraints();
		gbc_dueDateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dueDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dueDateTextField.gridx = 3;
		gbc_dueDateTextField.gridy = 3;
		editTaskPanel.add(dueDateTextField, gbc_dueDateTextField);
		dueDateTextField.setText(t.getDateDue());
		
		JLabel lblAssignedUser = new JLabel("Assigned User");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		editTaskPanel.add(lblAssignedUser, gbc_label_1);
		
		assignedUserTextField = new JTextField();
		assignedUserTextField.setColumns(10);
		GridBagConstraints gbc_assignedUserTextField = new GridBagConstraints();
		gbc_assignedUserTextField.insets = new Insets(0, 0, 5, 0);
		gbc_assignedUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_assignedUserTextField.gridx = 3;
		gbc_assignedUserTextField.gridy = 4;
		editTaskPanel.add(assignedUserTextField, gbc_assignedUserTextField);
		assignedUserTextField.setText(t.getAssignedUserName());
		
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
		descriptionTextField.setText(t.getDescription());
		
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
		notesTextField.setText(t.getNotes());
		
		String[] completion = { "0%", "25%", "50%", "75%", "100%"};
		final JComboBox<String> cbPercentComplete = new JComboBox<String>(completion);
		cbPercentComplete.setEditable(true);
		cbPercentComplete.setBounds(107, 65, 123, 25);
		cbPercentComplete.setVisible(true);
		editTaskPanel.add(cbPercentComplete);
		
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
		
		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 8;
		editTaskPanel.add(btnSave, gbc_btnSave);
		
		btnSave.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  if(!(nameTextField.getText().equals("")))
				  {			
					  t.edit(projectNumTextField.getText(), nameTextField.getText(), dueDateTextField.getText(), 
							  			assignedUserTextField.getText(), descriptionTextField.getText(), notesTextField.getText(), (String) cbPercentComplete.getSelectedItem());
					  new SQLQueryBuilder(t).editTask(t.getTaskID());
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
}