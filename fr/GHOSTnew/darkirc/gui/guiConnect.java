package fr.GHOSTnew.darkirc.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@SuppressWarnings("serial")
public class guiConnect extends JDialog {
	private String infos[] = new String[4];
	private final JLabel lblNick = new JLabel("Nick:");
	private JTextField nickField;
	private JTextField serverField;
	public guiConnect(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(390, 239);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    this.initComponent();
	}
	private void initComponent(){
	    JLayeredPane layeredPane = new JLayeredPane();
	    getContentPane().add(layeredPane, BorderLayout.CENTER);
	    lblNick.setFont(new Font("Dialog", Font.PLAIN, 15));
	    lblNick.setBounds(12, 12, 41, 19);
	    layeredPane.add(lblNick);
	    
	    nickField = new JTextField();
	    nickField.setBounds(57, 10, 134, 25);
	    layeredPane.add(nickField);
	    nickField.setColumns(10);
	    
	    JLabel lblNewLabel = new JLabel("Serveur:");
	    lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
	    lblNewLabel.setBounds(12, 57, 76, 17);
	    layeredPane.add(lblNewLabel);
	    
	    serverField = new JTextField();
	    serverField.setBounds(77, 47, 170, 28);
	    layeredPane.add(serverField);
	    serverField.setColumns(10);
	    
	    JLabel lblNewLabel_1 = new JLabel("Port:");
	    lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 16));
	    lblNewLabel_1.setBounds(253, 57, 61, 17);
	    layeredPane.add(lblNewLabel_1);
	    
	    final JSpinner Port = new JSpinner();
	    Port.setModel(new SpinnerNumberModel(new Integer(6667), new Integer(0), null, new Integer(1)));
	    Port.setBounds(296, 56, 68, 20);
	    layeredPane.add(Port);
	    
	    final JCheckBox SSL = new JCheckBox("SSL");
	    SSL.setFont(new Font("Dialog", Font.PLAIN, 16));
	    SSL.setBounds(20, 96, 120, 25);
	    layeredPane.add(SSL);
	    
////////////////////////////////////////////////
	    Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config.properties");
	 
			// load a properties file
			prop.load(input);
			nickField.setText(prop.getProperty("nick", "DarkUser"));
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
////////////////////////////////////////////////
		
	    JButton connectButton = new JButton("Connect");
	    connectButton.addActionListener(new ActionListener() {
	    	@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
	    		if(!nickField.getText().equalsIgnoreCase("") && !serverField.getText().equalsIgnoreCase("")){
	    			infos[0] = nickField.getText();
	    			infos[1] = serverField.getText();
	    			infos[2] = Port.getValue().toString();
	    			infos[3] = String.valueOf(SSL.isSelected());
	    			setVisible(false);
	    		}else{
	    			JOptionPane errorPane = new JOptionPane();
	    			errorPane.showMessageDialog(null, "Veuillez remplir tout les champs!", "Erreur", JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    });
	    connectButton.setBounds(278, 169, 98, 33);
	    layeredPane.add(connectButton);
	    
	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		setVisible(false);
	    	}
	    });
	    cancelButton.setBounds(162, 169, 107, 33);
	    layeredPane.add(cancelButton);
	}
	
	public String[] showGUI(){
	    this.setVisible(true);      
	    return this.infos;      
	  }
}
