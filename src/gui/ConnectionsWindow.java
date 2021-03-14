package gui;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Third and final window of the Pattern Creation Tool. Here the connections are defined.
 * After finalization a .pattern file with the given input is created at the patternfolder location.
 */
@SuppressWarnings("serial")
public class ConnectionsWindow extends JDialog {

	private JLabel label1, label2, label3;
	private JButton ok, cancel;
	private ArrayList<JComponent> cbList1 = new ArrayList<JComponent>();
	private ArrayList<JComponent> cbList2 = new ArrayList<JComponent>();
	private ArrayList<JComponent> cbList3 = new ArrayList<JComponent>();
	private ArrayList<String> Members = new ArrayList<String>();

	// Constructor
	@SuppressWarnings("unchecked")
	public ConnectionsWindow() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		int a = 110;
		int b = 40;
		int y = a + MainWindow.ConnNum * b;
		setSize(345, y);
		setTitle("Pattern Creator: Connections");
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		label1 = new JLabel("From");
		label1.setSize(130, 25);
		label1.setLocation(30, 15);
		add(label1);

		label2 = new JLabel("Connection Type");
		label2.setSize(130, 25);
		label2.setLocation(125, 15);
		add(label2);

		label3 = new JLabel("To");
		label3.setSize(130, 25);
		label3.setLocation(280, 15);
		add(label3);

		String[] choices = { "uses", "inherits", "references", "has", "calls", "creates", "relates" };

		for (int i = 0; i < MainWindow.MemberNum; i++) {
			Members.add(Character.toString((char) (65 + i)));
		}

		String Mem[] = new String[MainWindow.MemberNum];
		for (int i = 0; i < MainWindow.MemberNum; i++) {
			Mem[i] = Members.get(i);
		}

		for (int i = 0; i < MainWindow.ConnNum; i++) {
			cbList1.add(new JComboBox<String>(Mem));
			cbList1.get(i).setSize(40, 25);
			cbList1.get(i).setLocation(25, 40 + i * 40);
			((JComboBox<String>) cbList1.get(i)).setEditable(true);
			((JComboBox<String>) cbList1.get(i)).getEditor().getEditorComponent().setFocusable(false);
			cbList2.add(new JComboBox<String>(choices));
			cbList2.get(i).setSize(150, 25);
			cbList2.get(i).setLocation(95, 40 + i * 40);
			((JComboBox<String>) cbList2.get(i)).setEditable(true);
			((JComboBox<String>) cbList2.get(i)).getEditor().getEditorComponent().setFocusable(false);
			cbList3.add(new JComboBox<String>(Mem));
			cbList3.get(i).setSize(40, 25);
			cbList3.get(i).setLocation(275, 40 + i * 40);
			((JComboBox<String>) cbList3.get(i)).setEditable(true);
			((JComboBox<String>) cbList3.get(i)).getEditor().getEditorComponent().setFocusable(false);
		}

		for (int i = 0; i < MainWindow.ConnNum; i++) {
			cbList1.get(i).setVisible(true);
			add(cbList1.get(i));

			cbList2.get(i).setVisible(true);
			add(cbList2.get(i));

			cbList3.get(i).setVisible(true);
			add(cbList3.get(i));
		}

		ok = new JButton("OK");
		ok.setSize(90, 25);
		ok.setLocation(100, 50 + 40 * MainWindow.ConnNum);
		add(ok);

		cancel = new JButton("CANCEL");
		cancel.setSize(90, 25);
		cancel.setLocation(200, 50 + 40 * MainWindow.ConnNum);
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
	 * Event when OK button is pressed. If certain conditions are met, finishes the pattern creation process.
	 */
	public class event_ok implements ActionListener {
		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < cbList1.size(); i++) {
				MainWindow.p.insert_connection(((JComboBox) cbList1.get(i)).getSelectedItem().toString(),
						MainWindow.StringtoConnectionType(((JComboBox) cbList2.get(i)).getSelectedItem().toString()),
						((JComboBox) cbList3.get(i)).getSelectedItem().toString());
			}
			File file = new File(MainWindow.patternfolder + File.separator + MainWindow.p.get_name() + ".pattern");
			MainWindow.createPatternFile(MainWindow.p, file);
			dispose();
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
