package taskManager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import java.awt.Toolkit;

public class MessageWindow {
	private JFrame frmMessageWindow;
	private JPanel messagePanel = new JPanel();
	private JTextField messageTextField;
	private final JComboBox<String> cbMessageReceiver = new JComboBox();

	/**
	 * Constructor to instantiate the MessageWindow
	 * 
	 * @param uID
	 *            the ID number of the user who is creating the message
	 * @param pWindow
	 * 			  Reference to the main window object so that functions in the MainWindow class can be called
	 */
	public MessageWindow(int uID, MainWindow pWindow) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize(uID, pWindow);
					frmMessageWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the frame
	 * 
	 * @param userID
	 *            the ID number of the user who is creating the message
	 * @param pWin
	 * 			  Reference to the main window object so that functions in the MainWindow class can be called
	 */
	private void initialize(int userID, MainWindow pWin) {
		frmMessageWindow = new JFrame();
		frmMessageWindow.setIconImage(Toolkit.getDefaultToolkit().getImage(MessageWindow.class.getResource("/taskManager/Infinity_2.png")));
		frmMessageWindow.setTitle("New Message");
		frmMessageWindow.setBounds(100, 100, 450, 300);
		frmMessageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMessageWindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		GridBagLayout gbl_messagePanel = new GridBagLayout();
		gbl_messagePanel.columnWidths = new int[] { 30, 0, 30, 0, 0 };
		gbl_messagePanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_messagePanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_messagePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		messagePanel.setBackground(UIManager.getColor("Button.shadow"));
		messagePanel.setLayout(gbl_messagePanel);
		frmMessageWindow.getContentPane().add(messagePanel);
		cbMessageReceiver.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

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

		// only allows digits to be entered in the percent complete combo box
		cbMessageReceiver.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
			}
		});

		JLabel lblMessageRecipient = new JLabel("Recipient:");
		lblMessageRecipient.setForeground(new Color(153, 0, 0));
		lblMessageRecipient.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_MessageRecipient = new GridBagConstraints();
		gbc_MessageRecipient.gridx = 1;
		gbc_MessageRecipient.gridy = 1;
		gbc_MessageRecipient.insets = new Insets(0, 0, 5, 5);
		messagePanel.add(lblMessageRecipient, gbc_MessageRecipient);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMessage.setForeground(new Color(153, 0, 0));
		GridBagConstraints gbc_Message = new GridBagConstraints();
		gbc_Message.gridx = 1;
		gbc_Message.gridy = 3;
		gbc_Message.insets = new Insets(0, 0, 5, 5);
		messagePanel.add(lblMessage, gbc_Message);

		messageTextField = new JTextField();
		messageTextField.setBackground(UIManager.getColor("Button.background"));
		messageTextField.setColumns(10);
		GridBagConstraints gbc_projectNumTextField = new GridBagConstraints();
		gbc_projectNumTextField.insets = new Insets(0, 0, 5, 0);
		gbc_projectNumTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_projectNumTextField.gridx = 3;
		gbc_projectNumTextField.gridy = 3;
		messagePanel.add(messageTextField, gbc_projectNumTextField);
		messageTextField.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSend.setForeground(new Color(153, 0, 0));
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 5);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 7;
		messagePanel.add(btnSend, gbc_btnSend);

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int receiverID = new SQLQueryBuilder().getIDFromUserName((String) cbMessageReceiver.getSelectedItem());
				int senderID = userID;
				String message = messageTextField.getText();
				new SQLQueryBuilder().newMessage(receiverID, message, senderID);
				frmMessageWindow.dispose();
				pWin.getTasks();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(new Color(153, 0, 0));
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 7;
		messagePanel.add(btnCancel, gbc_btnCancel);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmMessageWindow.dispose();
			}
		});
	}
}
