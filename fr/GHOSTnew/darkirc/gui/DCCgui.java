package fr.GHOSTnew.darkirc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class DCCgui extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table_wait;
	private JTable table_process;
	private JTable table_finish;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DCCgui dialog = new DCCgui();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DCCgui() {
		setBounds(100, 100, 630, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane, BorderLayout.CENTER);
			{
				table_wait = new JTable();
				table_wait.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"File", "size", "Progress", "%", "speed"
						}
					));
				tabbedPane.addTab("En attentes", null, table_wait, null);
				tabbedPane.setEnabledAt(0, true);
			}
			{
				table_process = new JTable();
				table_process.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"File", "size", "Progress", "%", "speed"
					}
				));
				tabbedPane.addTab("En cours", null, table_process, null);
				tabbedPane.setBackgroundAt(1, new Color(169, 169, 169));
				tabbedPane.setEnabledAt(1, true);
			}
			{
				table_finish = new JTable();
				table_finish.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"File", "size", "Progress", "%", "speed"
						}
					));
				tabbedPane.addTab("Finis", null, table_finish, null);
				tabbedPane.setEnabledAt(2, true);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
