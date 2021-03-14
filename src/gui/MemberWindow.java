package gui;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Second window of the Pattern Creation Tool. Here the members are defined.(Abilities, Abstractions)
 */
@SuppressWarnings("serial")
public class MemberWindow extends JDialog {
	private JLabel label1, label2, label3, label;
	private JButton ok, cancel;
	private ArrayList<JComponent> textFieldList = new ArrayList<JComponent>();
	private ArrayList<JComponent> cbList = new ArrayList<JComponent>();

	// Constructor
	@SuppressWarnings("unchecked")
	public MemberWindow() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		int a = 110;
		int b = 40;
		int y = a + MainWindow.MemberNum * b;
		setSize(400, y);
		setTitle("Pattern Creator: Members");
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		label1 = new JLabel("Members");
		label1.setSize(130, 25);
		label1.setLocation(15, 15);
		add(label1);

		label2 = new JLabel("Abilities");
		label2.setSize(130, 25);
		label2.setLocation(150, 15);
		add(label2);

		label3 = new JLabel("Abstraction");
		label3.setSize(130, 25);
		label3.setLocation(305, 15);
		add(label3);

		String[] choices = { "Abstract", "Interface", "Abstracted", "Normal", "Any" };

		for (int i = 0; i < MainWindow.MemberNum; i++) {
			textFieldList.add(new JTextField(15));
			textFieldList.get(i).setSize(200, 25);
			textFieldList.get(i).setLocation(75, 50 + i * 40);
			cbList.add(new JComboBox<String>(choices));
			cbList.get(i).setSize(93, 25);
			cbList.get(i).setLocation(292, 50 + i * 40);
			((JComboBox<String>) cbList.get(i)).setEditable(true);
			((JComboBox<String>) cbList.get(i)).getEditor().getEditorComponent().setFocusable(false);
		}

		for (int i = 0; i < MainWindow.MemberNum; i++) {
			label = new JLabel(Character.toString((char) (65 + i)));
			label.setSize(25, 25);
			label.setLocation(35, 50 + i * 40);
			add(label);

			add(textFieldList.get(i));

			cbList.get(i).setVisible(true);
			add(cbList.get(i));
		}

		ok = new JButton("OK");
		ok.setSize(90, 25);
		ok.setLocation(190, 50 + 40 * MainWindow.MemberNum);
		add(ok);

		cancel = new JButton("CANCEL");
		cancel.setSize(90, 25);
		cancel.setLocation(290, 50 + 40 * MainWindow.MemberNum);
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
		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<String> AbilityList = new ArrayList<String>();
			AbilityList.clear();
			Boolean filled = true;
			for (int i = 0; i < textFieldList.size(); i++) {
				if (((JTextField) textFieldList.get(i)).getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Ability brackets need to be filled.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					filled = false;
					break;
				} else {
					AbilityList.add(((JTextField) textFieldList.get(i)).getText());
				}
			}
			if (filled == true) {
				Boolean duplicates = false;
				for (int j = 0; j < AbilityList.size(); j++)
					for (int k = j + 1; k < AbilityList.size(); k++)
						if (k != j && AbilityList.get(k).equals(AbilityList.get(j)))
							duplicates = true;
				if (duplicates == false) {
					for (int i = 0; i < textFieldList.size(); i++) {
						MainWindow.p.insert_member(Character.toString((char) (65 + i)), MainWindow
								.StringtoAbstraction(((JComboBox) cbList.get(i)).getSelectedItem().toString()),
								((JTextField) textFieldList.get(i)).getText());
					}
					dispose();
					new ConnectionsWindow();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Abilities need to be unique.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
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
