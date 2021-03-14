package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class implements a Component for browsing a file system for a folder.
 */
@SuppressWarnings("serial")
public class JFilePicker extends JPanel {
	private JLabel label;
	private JTextField textField;
	private JButton button;

	private JFileChooser fileChooser;

	private int mode;

	public enum type {
		Export, Project, Pattern
	};

	private type type;
	public static final int MODE_OPEN = 1;
	public static final int MODE_SAVE = 2;

	public JFilePicker(String textFieldLabel, String buttonLabel, type type) {
		this.type = type;

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		fileChooser.setAcceptAllFileFilterUsed(false);
		//

		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// creates the GUI
		label = new JLabel(textFieldLabel);

		Action action = new Action();
		textField = new JTextField(22);
		textField.addActionListener(action);
		button = new JButton(buttonLabel);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				buttonActionPerformed(evt);
			}
		});

		add(label);
		add(textField);
		add(button);

	}

	/**
	 * Implements the action when the button is pressed.
	 * 
	 * @param evt
	 */
	private void buttonActionPerformed(ActionEvent evt) {
		if (mode == MODE_OPEN) {
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		} else if (mode == MODE_SAVE) {
			if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				String s = fileChooser.getSelectedFile().getAbsolutePath();
				textField.setText(s);
				switch (type) {
				case Pattern:
					MainWindow.patternfolder = s;
					File folder = new File(s);
					MainWindow.refresh(folder);
					break;
				case Export:
					MainWindow.exportfolder = s;
					break;
				case Project:
					MainWindow.parseoccured = false;
					MainWindow.projectfolder = s;
					break;
				default:
					;
				}
			}
		}
	}

	/**
	 * Textfield action when pressing enter
	 */
	public class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String s;
			if (new File(s = textField.getText()).isDirectory()) {
				switch (type) {
				case Pattern:
					MainWindow.patternfolder = s;
					File folder = new File(s);
					MainWindow.refresh(folder);
					break;
				case Export:
					MainWindow.exportfolder = s;
					break;
				case Project:
					MainWindow.parseoccured = false;
					MainWindow.projectfolder = s;
					break;
				default:
					;
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Path does not correspond to a folder!", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				switch (type) {
				case Pattern:
					MainWindow.patternfolder = null;
					File folder = new File(s);
					MainWindow.refresh(folder);
					break;
				case Export:
					MainWindow.exportfolder = null;
					break;
				case Project:
					MainWindow.parseoccured = false;
					MainWindow.projectfolder = null;
					break;
				default:
					;
				}
			}
		}
	}

	/**
	 * Setting mode.
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Returns the selected file path.
	 * 
	 * @return
	 */
	public String getSelectedFilePath() {
		return textField.getText();
	}

	/**
	 * Returns the file chooser.
	 * 
	 * @return
	 */
	public JFileChooser getFileChooser() {
		return this.fileChooser;
	}
}
