package taskManager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
import java.util.Arrays;
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
import javax.swing.JCheckBox;

import org.mindrot.BCrypt;
import java.awt.Font;
import java.awt.Color;

public class Registration {

	private int userID;
	private JFrame frmRegistration;
	private JTextField firstNameTxtField;
	private JTextField lastNameTxtField;
	private JTextField userNameTxtField;
	private JPasswordField passwordTxtField;
	private JPasswordField retypePasswordTxtField;
	private JCheckBox chckbxAdministrator;
	private JButton btnRegister;
	private JButton btnCancel;
	private JLabel lblrequiredFields;

	/**
	 * Create the application.
	 * 
	 * @param name The username of the logged in user
	 */
	public Registration() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frmRegistration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistration = new JFrame();
		frmRegistration.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmRegistration.setTitle("Registration");
		frmRegistration.setBounds(100, 100, 450, 300);
		frmRegistration.setSize(400, 277);
		frmRegistration.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmRegistration.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblFirstName = new JLabel("* First Name:");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblFirstName = new GridBagConstraints();
		gbc_lblFirstName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFirstName.gridx = 1;
		gbc_lblFirstName.gridy = 2;
		frmRegistration.getContentPane().add(lblFirstName, gbc_lblFirstName);
		
		firstNameTxtField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 2;
		frmRegistration.getContentPane().add(firstNameTxtField, gbc_textField);
		firstNameTxtField.setColumns(10);
		
		JLabel lblLastName = new JLabel("* Last Name:");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblLastName = new GridBagConstraints();
		gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastName.gridx = 1;
		gbc_lblLastName.gridy = 3;
		frmRegistration.getContentPane().add(lblLastName, gbc_lblLastName);
		
		lastNameTxtField = new JTextField();
		GridBagConstraints gbc_lastNameTxtField = new GridBagConstraints();
		gbc_lastNameTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_lastNameTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameTxtField.gridx = 3;
		gbc_lastNameTxtField.gridy = 3;
		frmRegistration.getContentPane().add(lastNameTxtField, gbc_lastNameTxtField);
		
		JLabel lblUserName = new JLabel("* User Name:");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 1;
		gbc_lblUserName.gridy = 4;
		frmRegistration.getContentPane().add(lblUserName, gbc_lblUserName);
		
		userNameTxtField = new JTextField();
		GridBagConstraints gbc_userNameTxtField = new GridBagConstraints();
		gbc_userNameTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_userNameTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameTxtField.gridx = 3;
		gbc_userNameTxtField.gridy = 4;
		frmRegistration.getContentPane().add(userNameTxtField, gbc_userNameTxtField);
		userNameTxtField.setColumns(10);
		
		JLabel lblPassword = new JLabel("* Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 5;
		frmRegistration.getContentPane().add(lblPassword, gbc_lblPassword);
		
		passwordTxtField = new JPasswordField();
		GridBagConstraints gbc_passwordTxtField = new GridBagConstraints();
		gbc_passwordTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordTxtField.gridx = 3;
		gbc_passwordTxtField.gridy = 5;
		frmRegistration.getContentPane().add(passwordTxtField, gbc_passwordTxtField);
		passwordTxtField.setColumns(10);
		
		JLabel lblRetypePassword = new JLabel("* Retype Password:");
		lblRetypePassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblRetypePassword = new GridBagConstraints();
		gbc_lblRetypePassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblRetypePassword.gridx = 1;
		gbc_lblRetypePassword.gridy = 6;
		frmRegistration.getContentPane().add(lblRetypePassword, gbc_lblRetypePassword);
		
		retypePasswordTxtField = new JPasswordField();
		GridBagConstraints gbc_retypePasswordTxtField = new GridBagConstraints();
		gbc_retypePasswordTxtField.insets = new Insets(0, 0, 5, 0);
		gbc_retypePasswordTxtField.fill = GridBagConstraints.HORIZONTAL;
		gbc_retypePasswordTxtField.gridx = 3;
		gbc_retypePasswordTxtField.gridy = 6;
		frmRegistration.getContentPane().add(retypePasswordTxtField, gbc_retypePasswordTxtField);
		retypePasswordTxtField.setColumns(10);
		
		/*chckbxAdministrator = new JCheckBox("Administrator");
		chckbxAdministrator.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_chckbxAdministrator = new GridBagConstraints();
		gbc_chckbxAdministrator.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxAdministrator.gridx = 3;
		gbc_chckbxAdministrator.gridy = 7;
		frmRegistration.getContentPane().add(chckbxAdministrator, gbc_chckbxAdministrator);*/
		
		btnRegister = new JButton("Register");
		btnRegister.setForeground(new Color(51, 102, 0));
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegister.gridx = 1;
		gbc_btnRegister.gridy = 9;
		frmRegistration.getContentPane().add(btnRegister, gbc_btnRegister);
		
		btnRegister.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  if(firstNameTxtField.getText().equals("") || lastNameTxtField.getText().equals("") || userNameTxtField.getText().equals("")
						  || passwordTxtField.getPassword().length == 0 || retypePasswordTxtField.getPassword().length == 0)
				  {
					  JOptionPane.showMessageDialog(null, "Information was not entered for all required fields." + "\n" + "Please enter the missing information.");
				  }
				  else if(!(Arrays.equals(passwordTxtField.getPassword(),retypePasswordTxtField.getPassword())))
				  {
					  JOptionPane.showMessageDialog(null, "The passwords typed do not match." + "\n" + "Please reenter your password.");
					  passwordTxtField.setText("");
					  retypePasswordTxtField.setText("");
				  }
				  else if(new SQLQueryBuilder().getUsers().contains(userNameTxtField.getText()))
				  {
					  JOptionPane.showMessageDialog(null, "The user name entered is already taken." + "\n" + "Please enter another user name.");
					  userNameTxtField.setText("");
				  }
				  else
				  {
					  String firstNm = firstNameTxtField.getText();
					  String lastNm = lastNameTxtField.getText();
					  String userNm = userNameTxtField.getText();
					  String hashed = BCrypt.hashpw(String.valueOf(passwordTxtField.getPassword()), BCrypt.gensalt());
					  boolean state = chckbxAdministrator.isSelected();
					  new SQLQueryBuilder().addUser(userNm, hashed, firstNm, lastNm, state);
					  frmRegistration.dispose();
				  }
			  }} );
		
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.RED);
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 9;
		frmRegistration.getContentPane().add(btnCancel, gbc_btnCancel);
		
		btnCancel.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  frmRegistration.dispose();
			  }} );
		
		lblrequiredFields = new JLabel("* Required Fields");
		GridBagConstraints gbc_lblrequiredFields = new GridBagConstraints();
		gbc_lblrequiredFields.insets = new Insets(0, 0, 0, 5);
		gbc_lblrequiredFields.gridx = 1;
		gbc_lblrequiredFields.gridy = 11;
		frmRegistration.getContentPane().add(lblrequiredFields, gbc_lblrequiredFields);
	}
}
