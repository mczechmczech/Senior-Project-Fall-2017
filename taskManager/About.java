package taskManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

public class About extends JDialog implements ActionListener {

	private String credits = "This application uses the Open Source components below, listed with their copyright and "
			+ "links to their source and license information.\n\n" + "c3p0\n"
			+ "Project code: https://github.com/swaldman/c3p0\n"
			+ "by Steve Waldman <swaldman@mchange.com> © 2015 Machinery For Change, Inc.\n"
			+ "License: (LGPL) https://github.com/swaldman/c3p0/blob/master/LICENSE-LGPL\n\n" + "jBCrypt\n"
			+ "Project code: https://github.com/jeremyh/jBCrypt\n"
			+ "Copyright (c) 2006 Damien Miller <djm@mindrot.org>\n"
			+ "License: (ISC) https://github.com/jeremyh/jBCrypt/blob/master/LICENSE\n\n" + "LGoodDatePicker\n"
			+ "Project code: https://github.com/LGoodDatePicker/LGoodDatePicker\n"
			+ "Copyright (c) 2016 LGoodDatePicker\n"
			+ "License: (MIT) https://github.com/LGoodDatePicker/LGoodDatePicker/blob/master/LICENSE\n\n" + "WebLaF\n"
			+ "Project code: https://github.com/mgarin/weblaf\n" + "© WebLaF - Java Look and Feel 2017\n"
			+ "License: (GPLv3) https://www.gnu.org/licenses/gpl-3.0.html\n\n" + "mysqlconnector\n"
			+ "Project code: https://github.com/swaldman/c3p0\n"
			+ "Copyright (c) 2000, 2017, Oracle and/or its affiliates. All rights reserved.\n"
			+ "License: (GPLv3) https://github.com/mysql/mysql-connector-j/blob/release/5.1/COPYING";

	/**
	 * @param parent
	 */
	public About(JFrame parent) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/taskManager/Infinity_2.png")));

		JPanel messagePane = new JPanel();

		JTextPane textPane = new JTextPane();
		textPane.setForeground(new Color(0, 0, 51));
		textPane.setFont(new Font("Tahoma", Font.BOLD, 13));
		messagePane.add(textPane);
		textPane.setText(credits);
		textPane.setEditable(false);
		getContentPane().add(messagePane);
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("OK");
		buttonPane.add(button);
		button.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		dispose();
	}

}
