/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.GUI
 * File: GUIOption.java
 * Created on: juin 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * GUIOption.java is part of DarkIRC.
 * 
 * DarkIRC is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * DarkIRC is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with DarkIRC. If not, see
 * <http://www.gnu.org/licenses/>
 */

package net.GHOSTnew.DarkIRC.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import net.GHOSTnew.DarkIRC.DarkIRCmain;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Ressources;
import net.GHOSTnew.DarkIRC.Kernel.DarkIRC;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Language;

public class GUIOption extends JDialog {
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
        public GUIOption(final JFrame parent, String title, boolean modal) {
                super(parent, title, modal);
                this.setSize(535, 494);
                this.setLocationRelativeTo(null);
                this.setResizable(false);
                this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                this.setIconImage(Ressources.getImage("logo.png"));
                final GUIOption thisInstance = this;
                // JFrame root = (JFrame) this.getParent();
                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                                Properties prop = new Properties();
                                OutputStream output = null;

                                try {

                                        output = new FileOutputStream(DarkIRCmain.getConfigFile());

                                        // set the properties value
                                        prop.setProperty("nick", nickField.getText());
                                        prop.setProperty("langue", langBox.getSelectedItem().toString());
                                        prop.setProperty("style", styleBox.getSelectedItem().toString());
                                        prop.setProperty("proxy_host", proxyHostField.getText());
                                        prop.setProperty("proxy_port", ProxyPortSpin.getValue().toString());
                                        prop.setProperty("proxy_type", proxyTypeBox.getSelectedItem().toString());
                                        prop.setProperty("proxy_password", new String(passwordField.getPassword()));
                                        DarkIRC.bot.setNick(nickField.getText());
                                        DarkIRC.AppLang = ResourceBundle.getBundle("net.GHOSTnew.DarkIRC.GUI.Resources.i18n.darkirc", Language.getLocale(langBox.getSelectedItem().toString()));

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
                tabbedPane.addTab(DarkIRC.AppLang.getString("gui_option_tab_global"), null, layeredPane, null);

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
                tabbedPane.addTab(DarkIRC.AppLang.getString("gui_option_tab_network"), null, layeredPane_1, null);

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
                tabbedPane.addTab(DarkIRC.AppLang.getString("gui_option_tab_appearance"), null, layeredPane_2, null);

                JLabel lblStyle = new JLabel("Style:");
                // lblStyle.setFont(new Font("DejaVu Sans", Font.PLAIN, 16));
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
                                                        SwingUtilities.updateComponentTreeUI(thisInstance);
                                                        GUIMain.jTabbedPane2.setUI(new BasicTabbedPaneUI());
                                                        break;
                                                }
                                        }
                                } catch (Exception exception) {
                                        exception.printStackTrace();
                                        JOptionPane.showMessageDialog(parent, "Can't change look and feel", "Invalid PLAF", JOptionPane.ERROR_MESSAGE);
                                }
                        }

                });

                Properties prop = new Properties();
                InputStream input = null;

                try {

                        input = new FileInputStream(DarkIRCmain.getConfigFile());

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

        public void showGUI() {
                this.setVisible(true);
        }
}
