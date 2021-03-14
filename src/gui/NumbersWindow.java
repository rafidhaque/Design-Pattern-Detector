package gui;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import patterns.Pattern;

/**
 * First window of the Pattern Creation Tool. Here the Numbers of the members and connections are defined.
 * Also the pattern's name.
 */
@SuppressWarnings("serial")
public class NumbersWindow extends JDialog {

	private JLabel label1, label2, label3;
	private JButton ok, cancel;
	private JTextField textfield;
	private JComboBox<String> cb1;
	private JComboBox<String> cb2;

	// Constructor
	public NumbersWindow() {
		setSize(330, 240);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Pattern Creator");
		setLayout(null);
		label1 = new JLabel("Number of Members: ");
		label1.setSize(165, 25);
		label1.setLocation(35, 30);
		add(label1);

		label2 = new JLabel("Number of Connections: ");
		label2.setSize(165, 25);
		label2.setLocation(35, 75);
		add(label2);

		label3 = new JLabel("Name of Pattern: ");
		label3.setSize(165, 25);
		label3.setLocation(35, 125);
		add(label3);

		String[] choices = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		cb1 = new JComboBox<String>(choices);
		cb1.setSize(40, 25);
		cb1.setLocation(200, 27);
		cb1.setEditable(true);
		cb1.getEditor().getEditorComponent().setFocusable(false);
		cb1.setVisible(true);
		add(cb1);

		cb2 = new JComboBox<String>(choices);
		cb2.setSize(40, 25);
		cb2.setLocation(200, 77);
		cb2.setEditable(true);
		cb2.getEditor().getEditorComponent().setFocusable(false);
		cb2.setVisible(true);
		add(cb2);

		textfield = new JTextField(15);
		textfield.setSize(130, 25);
		textfield.setLocation(155, 127);
		add(textfield);

		ok = new JButton("OK");
		ok.setSize(90, 25);
		ok.setLocation(100, 175);
		add(ok);

		cancel = new JButton("CANCEL");
		cancel.setSize(90, 25);
		cancel.setLocation(200, 175);
		add(cancel);

		event_ok e = new event_ok();
		ok.addActionListener(e);

		event_cancel e2 = new event_cancel();
		cancel.addActionListener(e2);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to close this window?",
						"Really Closing?", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});

		setModal(true);
		setVisible(true);

	}

	/**
	 * Event when OK button is pressed. If certain conditions are met, continues to the next frame.
	 */
	public class event_ok implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			File folder = new File(MainWindow.patternfolder);
			ArrayList<String> Patterns = MainWindow.listPatternFilesForFolder(folder);
			int repl = JOptionPane.YES_OPTION;
			for (String s : Patterns) {
				if (textfield.getText().equals(s)) {
					repl = JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite " + s + " pattern?",
							s + " pattern already exists!", JOptionPane.YES_NO_OPTION);
					break;
				}

			}
			if (repl == JOptionPane.YES_OPTION) {
				if (textfield.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please Enter Pattern Name.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				} else if (Integer.parseInt(cb1.getSelectedItem().toString()) == 1) {
					JOptionPane.showMessageDialog(new JFrame(), "Need at least 2 members.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				} else {
					MainWindow.MemberNum = Integer.parseInt(cb1.getSelectedItem().toString());
					MainWindow.ConnNum = Integer.parseInt(cb2.getSelectedItem().toString());
					MainWindow.p = new Pattern(textfield.getText());
					dispose();
					new MemberWindow();
				}
			}
		}
	}

	/**
	 * Event when Cancel button is pressed. A Yes/No Dialog appears asking for cancel confirmation.
	 */
	public class event_cancel implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel Pattern Creation?",
					"Cancel?", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
}
