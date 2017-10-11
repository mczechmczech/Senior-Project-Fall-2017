import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class MainWindow {

	private JFrame frmMainwindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMainwindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMainwindow = new JFrame();
		frmMainwindow.setTitle("MainWindow");
		frmMainwindow.setBounds(100, 100, 450, 300);
		frmMainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainwindow.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		frmMainwindow.getContentPane().add(tabbedPane);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("TASKS", null, tabbedPane_1, null);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("ALL TASKS", null, panel, null);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("IN PROGRESS", null, panel_4, null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("COMPLETED TASKS", null, panel_2, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("CREATE NEW..", null, panel_1, null);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Inbox ()", null, panel_3, null);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("ARCHIVE", null, panel_5, null);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("TRASH", null, panel_6, null);
		
		JPanel panel_7 = new JPanel();
		tabbedPane.addTab("REQUEST TASK", null, panel_7, null);
		panel_7.setLayout(null);
		
		JButton btnRequestTask = new JButton("REQUEST TASK");
		btnRequestTask.setBounds(107, 101, 123, 25);
		panel_7.add(btnRequestTask);
		
	
		JPanel panel_8 = new JPanel();
		tabbedPane.addTab("LOGOUT", null, panel_8, null);
		panel_8.setLayout(null);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.setBounds(112, 105, 81, 25);
		panel_8.add(btnLogout);
	}

}
