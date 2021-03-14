package gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import parser.ProjectASTParser;

/**
 * Action of the button Scan for Connections of mainWindow. It calls the ProjectASTParser parse function while
 * showing a please wait JDialog.
 */
@SuppressWarnings("serial")
class ShowWaitAction extends AbstractAction {

	public ShowWaitAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				ProjectASTParser.parse(MainWindow.projectfolder);
				MainWindow.parseoccured = true;
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
		if (MainWindow.projectfolder == null) {
			JDialog frame = new JDialog();
			JOptionPane.showMessageDialog(frame, "Project Folder Location Undefined!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(new JFrame(), "Connections Detected", "JOB'S DONE!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}