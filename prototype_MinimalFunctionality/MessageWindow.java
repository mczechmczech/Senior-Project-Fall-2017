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

public class MessageWindow
{
	private JFrame frmMessageWindow;
	private JPanel messagePanel = new JPanel();
	private JTextField messageNumTextField;
	private final JComboBox<String> cbMessageReceiver = new JComboBox();
	
	//this constructor is for editing tasks
	public MessageWindow(PrototypeWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//System.out.println("Connecting database...");

				try {
					initialize(pWindow);
					frmMessageWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//initialize method for any new EditTaskWindow object
	private void initialize(PrototypeWindow pWin)
	{
		frmMessageWindow = new JFrame();
		frmMessageWindow.setTitle("New Message");
		frmMessageWindow.setBounds(100, 100, 450, 300);
		frmMessageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMessageWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		GridBagLayout gbl_messagePanel = new GridBagLayout();
		gbl_messagePanel.columnWidths = new int[] {30, 0, 30, 0, 0};
		gbl_messagePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_messagePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_messagePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		messagePanel.setLayout(gbl_messagePanel);
		frmMessageWindow.getContentPane().add(messagePanel);
		
		JLabel lblProjectNum = new JLabel("Project Number");
		GridBagConstraints gbc_ProjectNum = new GridBagConstraints();
		gbc_ProjectNum.insets = new Insets(0, 0, 5, 5);
		gbc_ProjectNum.gridx = 1;
		gbc_ProjectNum.gridy = 1;
		messagePanel.add(lblProjectNum, gbc_ProjectNum);
		
		messageNumTextField = new JTextField();
		messageNumTextField.setEditable(false);
		messageNumTextField.setEnabled(false);
		messageNumTextField.setColumns(10);
		GridBagConstraints gbc_projectNumTextField = new GridBagConstraints();
		gbc_projectNumTextField.insets = new Insets(0, 0, 5, 0);
		gbc_projectNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNumTextField.gridx = 3;
		gbc_projectNumTextField.gridy = 1;
		messagePanel.add(messageNumTextField, gbc_projectNumTextField);
		messageNumTextField.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 2;
		messagePanel.add(lblName, gbc_lblName);
		
		nameTextField = new JTextField();
		nameTextField.setEditable(false);
		nameTextField.setEnabled(false);
		nameTextField.setColumns(10);
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 3;
		gbc_nameTextField.gridy = 2;
		messagePanel.add(nameTextField, gbc_nameTextField);
		
		JLabel lblDueDate = new JLabel("Due Date");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		messagePanel.add(lblDueDate, gbc_label);
		
		dueDateTextField = new JTextField();
		dueDateTextField.setEditable(false);
		dueDateTextField.setEnabled(false);
		dueDateTextField.setColumns(10);
		GridBagConstraints gbc_dueDateTextField = new GridBagConstraints();
		gbc_dueDateTextField.insets = new Insets(0, 0, 5, 0);
		gbc_dueDateTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dueDateTextField.gridx = 3;
		gbc_dueDateTextField.gridy = 3;
		messagePanel.add(dueDateTextField, gbc_dueDateTextField);
		
		JLabel lblAssignedUser = new JLabel("Assigned User");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		messagePanel.add(lblAssignedUser, gbc_label_1);
		
		assignedUserTextField = new JTextField();
		assignedUserTextField.setColumns(10);
		assignedUserTextField.setEditable(false);
		assignedUserTextField.setEnabled(false);
		GridBagConstraints gbc_assignedUserTextField = new GridBagConstraints();
		gbc_assignedUserTextField.insets = new Insets(0, 0, 5, 0);
		gbc_assignedUserTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_assignedUserTextField.gridx = 3;
		gbc_assignedUserTextField.gridy = 4;
		messagePanel.add(assignedUserTextField, gbc_assignedUserTextField);
		
		JLabel lblDescrip = new JLabel("Description");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 5;
		messagePanel.add(lblDescrip, gbc_label_2);
		
		descriptionTextField = new JTextField();
		descriptionTextField.setEditable(false);
		descriptionTextField.setEnabled(false);
		descriptionTextField.setColumns(10);
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 3;
		gbc_descriptionTextField.gridy = 5;
		messagePanel.add(descriptionTextField, gbc_descriptionTextField);
		
		JLabel lblNotes = new JLabel("Notes");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 6;
		messagePanel.add(lblNotes, gbc_label_3);
		
		notesTextField = new JTextField();
		notesTextField.setEditable(false);
		notesTextField.setEnabled(false);
		notesTextField.setColumns(10);
		GridBagConstraints gbc_notesTextField = new GridBagConstraints();
		gbc_notesTextField.insets = new Insets(0, 0, 5, 0);
		gbc_notesTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_notesTextField.gridx = 3;
		gbc_notesTextField.gridy = 6;
		messagePanel.add(notesTextField, gbc_notesTextField);
		
		cbMessageReceiver.setEditable(false);
		cbMessageReceiver.setEnabled(false);
		cbMessageReceiver.setBounds(107, 65, 123, 25);
		cbMessageReceiver.setVisible(true);
		messagePanel.add(cbMessageReceiver);
		
		//only allows digits to be entered in the percent complete combo box
		cbMessageReceiver.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (cbMessageReceiver.getEditor().getItem().toString().length() < 4) 
                {
                    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) 
                    {
                        frmMessageWindow.getToolkit().beep();
                        e.consume();
                    }
                } 
                else 
                { 
                    e.consume();
                }
                
                //check to see if percent symbol is still in combo box string
                //if it isn't, automatically append it to combo box string
                if(!((cbMessageReceiver.getEditor().getItem().toString()).contains("%")))
                {
                	frmMessageWindow.getToolkit().beep();
                	cbMessageReceiver.getEditor().setItem(cbMessageReceiver.getEditor().getItem().toString().concat("%"));
                }
            }
        });
		
		JLabel lblPercentComplete = new JLabel("Percent Complete:");
		GridBagConstraints gbc_PercentComplete = new GridBagConstraints();
		gbc_PercentComplete.gridx = 1;
		gbc_PercentComplete.gridy = 7;
		gbc_PercentComplete.insets = new Insets(0, 0, 5, 5);
		messagePanel.add(lblPercentComplete, gbc_PercentComplete);
		GridBagConstraints gbc_cbPercentComplete = new GridBagConstraints();
		gbc_cbPercentComplete.insets = new Insets(0, 0, 5, 0);
		gbc_cbPercentComplete.gridx = 3;
		gbc_cbPercentComplete.gridy = 7;
		messagePanel.add(cbMessageReceiver, gbc_cbPercentComplete);
		
		messageNumTextField.setText(t.getProjectNum());
		nameTextField.setText(t.getName());
		dueDateTextField.setText(t.getDateDue().toString());
		assignedUserTextField.setText(t.getAssignedUserName());
		descriptionTextField.setText(t.getDescription());
		notesTextField.setText(t.getNotes());
		cbMessageReceiver.setSelectedItem(t.getPercentComplete());
		
		JButton btnAccept = new JButton("Accept");
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 8;
		messagePanel.add(btnAccept, gbc_btnAccept);
		
		btnAccept.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  new SQLQueryBuilder().taskAccepted(t.getTaskID());
				  pWin.getTasks();
				  frmMessageWindow.dispose();
				} 
				} );
		
		JButton btnDecline = new JButton("Decline");
		GridBagConstraints gbc_btnDecline = new GridBagConstraints();
		gbc_btnDecline.gridx = 3;
		gbc_btnDecline.gridy = 8;
		messagePanel.add(btnDecline, gbc_btnDecline);
		
		btnDecline.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 

				  } 
				} );
	}
}