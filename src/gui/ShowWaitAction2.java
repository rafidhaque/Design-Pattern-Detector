package gui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;

import parser.ProjectASTParser;
import patterns.PatternDetectionAlgorithm;
import patterns.Pattern;

/**
 * Action of the button Detect Pattern of mainWindow. It calls the BruteForce DetectPattern_Results function while
 * showing a please wait JDialog. When done, saves the results in a file at the specified exportfolder location.
 */
@SuppressWarnings("serial")
public class ShowWaitAction2 extends AbstractAction {

	public ShowWaitAction2(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				ProjectASTParser.parse(MainWindow.projectfolder);
				File file = new File(MainWindow.patternfolder + File.separator + MainWindow.cb.getSelectedItem().toString()
						+ ".pattern");
				Pattern pat = MainWindow.extractPattern(file);
				Boolean grouping;
				if (MainWindow.grouping.isSelected())
					grouping = true;
				else
					grouping = false;
				String s = PatternDetectionAlgorithm.DetectPattern_Results(pat, grouping);
				File fileEntry = new File(MainWindow.projectfolder);
				String s2 = MainWindow.exportfolder + File.separator + "detect_" + MainWindow.cb.getSelectedItem().toString()
						+ "_pattern_in_" + fileEntry.getName() + "_project" + ".txt";
				File f = new File(s2);
				MainWindow.createFile(s, f);
				ProjectASTParser.Classes.clear();
				PatternDetectionAlgorithm.clear();
				return null;
			}
		};

		Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
		final JDialog dialog = new JDialog(win, "Working", ModalityType.APPLICATION_MODAL);

		mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("state")) {
					if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
						dialog.dispose();
					}
				}
			}
		});
		if (MainWindow.exportfolder == null) {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, "Export Folder Location Undefined!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		} else if (MainWindow.patternfolder == null) {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, "Pattern Folder Location Undefined!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		} else if (MainWindow.cb.getSelectedIndex() == 0) {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, "Must choose a Pattern!", "ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			mySwingWorker.execute();
			JProgressBar progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(progressBar, BorderLayout.CENTER);
			panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
			dialog.add(panel);
			dialog.pack();
			dialog.setLocationRelativeTo(win);
			dialog.setVisible(true);
			JOptionPane.showMessageDialog(new JFrame(),
					"Patterns Detected. Check for results at the .txt file created in the export folder.",
					"JOB'S DONE!", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}