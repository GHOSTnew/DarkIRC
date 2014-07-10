package fr.GHOSTnew.darkirc.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Font;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import fr.GHOSTnew.darkirc.DarkIRC;
import fr.GHOSTnew.darkirc.utils.langage;

public class guiOption extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nickField;
	private JTextField proxyHostField;
	private JSpinner ProxyPortSpin;
	private JComboBox proxyTypeBox;
	private JComboBox langBox;
	private JComboBox styleBox;
	private JPasswordField passwordField;
	
	/**
	 * Create the dialog.
	 */
	public guiOption(final JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(535, 494);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    
	    //JFrame root = (JFrame) this.getParent();
	    JButton saveButton = new JButton("Save");
	    saveButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		Properties prop = new Properties();
	    		OutputStream output = null;
	    	 
	    		try {
	    	 
	    			output = new FileOutputStream("config.properties");
	    	 
	    			// set the properties value
	    			prop.setProperty("nick", nickField.getText());
				    prop.setProperty("langue", langBox.getSelectedItem().toString());
	    			prop.setProperty("style", styleBox.getSelectedItem().toString());
	    			prop.setProperty("proxy_host", proxyHostField.getText());
	    			prop.setProperty("proxy_port", ProxyPortSpin.getValue().toString());
	    			prop.setProperty("proxy_type", proxyTypeBox.getSelectedItem().toString());
	    			prop.setProperty("proxy_password", new String(passwordField.getPassword()));
	    			DarkIRC.bot.setNick(nickField.getText());
	    			DarkIRC.messageLang = ResourceBundle.getBundle("fr.GHOSTnew.darkirc.i18n.darkirc", langage.getLocale(langBox.getSelectedItem().toString()));
	    	 
	    			// save properties to project root folder
	    			prop.store(output, null);
	    	 
	    		} catch (IOException io) {
	    			io.printStackTrace();
	    		} finally {
	    			if (output != null) {
	    				try {
	    					output.close();
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    			}
	    	 
	    		}
	    	}
	    });
	    
	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    getContentPane().add(tabbedPane, BorderLayout.CENTER);
	    getContentPane().add(saveButton, BorderLayout.SOUTH);
	    
	    JLayeredPane layeredPane = new JLayeredPane();
	    tabbedPane.addTab(DarkIRC.messageLang.getString("gui_option_tab_global"), null, layeredPane, null);
	    
	    JLabel lblDefaultNick = new JLabel("Default nick:");
	    lblDefaultNick.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	    lblDefaultNick.setBounds(23, 21, 105, 15);
	    layeredPane.add(lblDefaultNick);
	    
	    nickField = new JTextField();
	    nickField.setBounds(119, 16, 122, 27);
	    layeredPane.add(nickField);
	    nickField.setColumns(10);
	    
	    JLabel lblLangue = new JLabel("Langue:");
	    lblLangue.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	    lblLangue.setBounds(23, 73, 62, 19);
	    layeredPane.add(lblLangue);
	    
	    langBox = new JComboBox();
	    langBox.setModel(new DefaultComboBoxModel(new String[] {"Français", "English", "Español", "日本語"}));
	    langBox.setBounds(97, 70, 115, 27);
	    layeredPane.add(langBox);
	    
	    
	    JLayeredPane layeredPane_1 = new JLayeredPane();
	    tabbedPane.addTab(DarkIRC.messageLang.getString("gui_option_tab_network"), null, layeredPane_1, null);
	    
	    JLabel lblProxy = new JLabel("Proxy");
	    lblProxy.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
	    lblProxy.setBounds(22, 6, 73, 18);
	    layeredPane_1.add(lblProxy);
	    
	    proxyHostField = new JTextField();
	    proxyHostField.setBounds(73, 36, 122, 27);
	    layeredPane_1.add(proxyHostField);
	    proxyHostField.setColumns(10);
	    
	    JLabel lblHost = new JLabel("Host:");
	    lblHost.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	    lblHost.setBounds(22, 42, 49, 15);
	    layeredPane_1.add(lblHost);
	    
	    JLabel lblPort = new JLabel("Port:");
	    lblPort.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	    lblPort.setBounds(207, 42, 49, 15);
	    layeredPane_1.add(lblPort);
	    
	    ProxyPortSpin = new JSpinner();
	    ProxyPortSpin.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
	    ProxyPortSpin.setBounds(250, 36, 73, 26);
	    layeredPane_1.add(ProxyPortSpin);
	    
	    JLabel lblPassword = new JLabel("Password:");
	    lblPassword.setFont(new Font("Dialog", Font.PLAIN, 16));
	    lblPassword.setBounds(22, 75, 89, 15);
	    layeredPane_1.add(lblPassword);
	    
	    passwordField = new JPasswordField();
	    passwordField.setBounds(109, 70, 122, 27);
	    layeredPane_1.add(passwordField);
	    
	    JLabel lblType = new JLabel("Type:");
	    lblType.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	    lblType.setBounds(22, 103, 49, 15);
	    layeredPane_1.add(lblType);
	    
	    proxyTypeBox = new JComboBox();
	    proxyTypeBox.setModel(new DefaultComboBoxModel(new String[] {"HTTP", "HTTPS", "Socks"}));
	    proxyTypeBox.setBounds(73, 102, 89, 26);
	    layeredPane_1.add(proxyTypeBox);
	    
	    JLayeredPane layeredPane_2 = new JLayeredPane();
	    tabbedPane.addTab(DarkIRC.messageLang.getString("gui_option_tab_appearance"), null, layeredPane_2, null);
	    
	    JLabel lblStyle = new JLabel("Style:");
	    //lblStyle.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
	    lblStyle.setBounds(17, 27, 52, 25);
	    layeredPane_2.add(lblStyle);
	    
	    
	    
	    styleBox = new JComboBox();
	    styleBox.setBounds(63, 28, 143, 25);
	    layeredPane_2.add(styleBox);

	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	    	styleBox.addItem(info.getName());
        }
	    styleBox.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			try {
	    				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	    	                if (styleBox.getSelectedItem().toString().equals(info.getName())) {
	    	                	UIManager.setLookAndFeel(info.getClassName());
	    	                	SwingUtilities.updateComponentTreeUI(parent);
	    	                	DarkIRC.jTabbedPane2.setUI(new BasicTabbedPaneUI()); 
	    	                    break;
	    	                }
	    	            }
	    	          } catch (Exception exception) {
	    	            JOptionPane.showMessageDialog(parent, "Can't change look and feel", "Invalid PLAF",
	    	                JOptionPane.ERROR_MESSAGE);
	    	          }
	    		}
	    		    
	    }  );
	    
	    Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config.properties");
	 
			// load a properties file
			prop.load(input);
			nickField.setText(prop.getProperty("nick", "DarkUser"));
			langBox.setSelectedItem(prop.getProperty("langue", "Français"));
			styleBox.setSelectedItem(prop.getProperty("style", "Nimbus"));
			proxyHostField.setText(prop.getProperty("proxy_host", ""));
			ProxyPortSpin.setValue(new Integer(prop.getProperty("proxy_port", "80")));
			proxyTypeBox.setSelectedItem(prop.getProperty("proxy_type", "HTTP"));
			passwordField.setText(prop.getProperty("proxy_password", ""));
			
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
	}
	
	public void showGUI(){
	    this.setVisible(true);     
	  }
}
