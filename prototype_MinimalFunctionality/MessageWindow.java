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
	private ArrayList<String> users = new SQLQueryBuilder().getUsers();
	
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
		
		JLabel lblMessage = new JLabel("Message:");
		GridBagConstraints gbc_Message = new GridBagConstraints();
		gbc_Message.gridx = 1;
		gbc_Message.gridy = 2;
		gbc_Message.insets = new Insets(0, 0, 5, 5);
		messagePanel.add(lblMessage, gbc_Message);
		
		messageNumTextField = new JTextField();
		messageNumTextField.setColumns(10);
		GridBagConstraints gbc_projectNumTextField = new GridBagConstraints();
		gbc_projectNumTextField.insets = new Insets(0, 0, 5, 0);
		gbc_projectNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNumTextField.gridx = 3;
		gbc_projectNumTextField.gridy = 2;
		messagePanel.add(messageNumTextField, gbc_projectNumTextField);
		messageNumTextField.setColumns(10);
		
		cbMessageReceiver.setEditable(true);
		cbMessageReceiver.setEnabled(true);
		AutoCompletion.enable(cbMessageReceiver);
		cbMessageReceiver.setVisible(true);
		pWin.addUsersToList(cbMessageReceiver);
		GridBagConstraints gbc_cbMessageReceiver = new GridBagConstraints();
		gbc_cbMessageReceiver.insets = new Insets(0, 0, 5, 0);
		gbc_cbMessageReceiver.gridx = 3;
		gbc_cbMessageReceiver.gridy = 1;
		messagePanel.add(cbMessageReceiver, gbc_cbMessageReceiver);
		
		//only allows digits to be entered in the percent complete combo box
		cbMessageReceiver.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

            }
        });
		
		JLabel lblMessageRecipient = new JLabel("Recipient:");
		GridBagConstraints gbc_MessageRecipient = new GridBagConstraints();
		gbc_MessageRecipient.gridx = 1;
		gbc_MessageRecipient.gridy = 1;
		gbc_MessageRecipient.insets = new Insets(0, 0, 5, 5);
		messagePanel.add(lblMessageRecipient, gbc_MessageRecipient);
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 8;
		messagePanel.add(btnSend, gbc_btnSend);
		
		btnSend.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				} 
				} );
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 8;
		messagePanel.add(btnCancel, gbc_btnCancel);
		
		btnCancel.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  	frmMessageWindow.dispose();
				  } 
				} );
	}
}