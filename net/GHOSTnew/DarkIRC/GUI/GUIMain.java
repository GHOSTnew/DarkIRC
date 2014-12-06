/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.GUI
 * File: GUIMain.java
 * Created on: 5 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * GUIMain.java is part of DarkIRC.
 * 
 * DarkIRC is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * DarkIRC is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with DarkIRC. If
 * not, see <http://www.gnu.org/licenses/>
 */

package net.GHOSTnew.DarkIRC.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.GHOSTnew.DarkIRC.DarkIRCmain;
import net.GHOSTnew.DarkIRC.GUI.Components.DarkIRCEditorPane;
import net.GHOSTnew.DarkIRC.GUI.Components.UserJListRenderer;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.BanAction;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.DeopAction;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.DevoiceAction;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.ExpulseAction;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.OpAction;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.QueryAction;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Ressources;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Styles;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.VoiceAction;
import net.GHOSTnew.DarkIRC.Kernel.DarkIRC;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Channel;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Command;
import net.GHOSTnew.DarkIRC.Kernel.Utils.DccChatThread;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Logs;
import net.GHOSTnew.DarkIRC.Kernel.Utils.ObjectDccChat;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.TrustingSSLSocketFactory;
import org.jibble.pircbot.User;


/**
 * @author GHOSTnew
 */
@SuppressWarnings("serial")
public class GUIMain extends javax.swing.JFrame {

        // PUBLICS VARS
        public static DefaultListModel<String> userListModel = new DefaultListModel<String>();

        // PRIVATES VARS
        public static JTabbedPane jTabbedPane2;
        // /public static JTextPane jTextPane1;
        private static DarkIRCEditorPane jTextPane1;
        public static JList<String> userList;
        public static JPanel chanPanel;
        public static JScrollPane jScrollPane2;
        public static javax.swing.JTextField topicField;
        public static javax.swing.JButton connectDisconnectButton;
        public static javax.swing.JButton joinButton;

        /**
         * private vars
         */
        private GUIMain main;
        private javax.swing.JButton jButton1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JButton scriptingButton;
        private javax.swing.JButton configButton;
        private javax.swing.JPanel jPanel1;

        public GUIMain() {
                initComponents();
                main = this;
        }


        public static void add_line(String msg) {
                Document doc = jTextPane1.getDocument();
                try {
                        doc.insertString(doc.getLength(), msg + "\n", null);
                        // doc.(matcher);
                } catch (BadLocationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public static void set_text(String msg) {
                jTextPane1.setText("");
                Document doc = jTextPane1.getDocument();
                try {
                        doc.insertString(0, msg, null);
                } catch (BadLocationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public static String get_text() {
                try {
                        return jTextPane1.getDocument().getText(0, jTextPane1.getDocument().getLength());
                } catch (BadLocationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return "";
                }
        }

        /**
         * This method is called from within the constructor to initialize the form. WARNING: Do NOT
         * modify this code. The content of this method is always regenerated by the Form Editor.
         */
        private void initComponents() {

                jPanel1 = new JPanel();
                jButton1 = new JButton();
                jTextField1 = new JTextField();
                jTabbedPane2 = new JTabbedPane();
                jTabbedPane2.setUI(new BasicTabbedPaneUI());
                jScrollPane1 = new JScrollPane();

                jTextPane1 = new DarkIRCEditorPane();// new JTextPane();
                jTextPane1.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                jTextPane1.setEditorKit(Styles.kit);
                jTextPane1.setDocument(Styles.doc());
                jTextPane1.setContentType("text/html");
                jTextPane1.setEditable(false);
                jTextPane1.initListener();

                chanPanel = new JPanel();
                topicField = new JTextField();
                jScrollPane2 = new JScrollPane();
                userList = new JList<String>();
                userList.setModel(userListModel);
                userList.setCellRenderer(new UserJListRenderer());
                connectDisconnectButton = new JButton();
                joinButton = new javax.swing.JButton();
                scriptingButton = new javax.swing.JButton();
                configButton = new javax.swing.JButton();
                connectDisconnectButton.setIcon(new ImageIcon(Ressources.getImage("connect.png")));
                joinButton.setIcon(new ImageIcon(Ressources.getImage("join.png")));
                joinButton.setEnabled(false);
                scriptingButton.setIcon(new ImageIcon(Ressources.getImage("scripting.png")));
                configButton.setIcon(new ImageIcon(Ressources.getImage("config.png")));

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setLocationByPlatform(true);

                // /////////////////////////////////////
                final JPopupMenu JPopupMenu = new JPopupMenu();
                final JMenuItem jQuery = new JMenuItem();
                jQuery.setAction(new QueryAction("Query"));
                JPopupMenu.add(jQuery);
                JPopupMenu.addSeparator();
                final JMenuItem jExpulse = new JMenuItem();
                jExpulse.setAction(new ExpulseAction("Expulser"));
                JPopupMenu.add(jExpulse);
                final JMenuItem jBannir = new JMenuItem();
                jBannir.setAction(new BanAction("Bannir"));
                JPopupMenu.add(jBannir);
                JPopupMenu.addSeparator();
                final JMenuItem jVoice = new JMenuItem();
                jVoice.setAction(new VoiceAction("Voice"));
                JPopupMenu.add(jVoice);
                JMenuItem jDevoice = new JMenuItem();
                jDevoice.setAction(new DevoiceAction("Devoice"));
                JPopupMenu.add(jDevoice);
                final JMenuItem jOp = new JMenuItem();
                jOp.setAction(new OpAction("Op"));
                JPopupMenu.add(jOp);;
                final JMenuItem jDeop = new JMenuItem();
                jDeop.setAction(new DeopAction("DeOp"));
                JPopupMenu.add(jDeop);

                userList.addMouseListener(new MouseAdapter() {

                        public void mousePressed(MouseEvent e) {
                                check(e);
                        }

                        public void mouseReleased(MouseEvent e) {
                                check(e);
                        }

                        public void check(MouseEvent e) {
                                if (SwingUtilities.isRightMouseButton(e) && e.isPopupTrigger()) {
                                        int raw = userList.locationToIndex(new Point(e.getX(), e.getY()));
                                        userList.setSelectedIndex(raw); // select the item
                                        JPopupMenu.show(userList, e.getX(), e.getY()); // and show
                                        // the menu
                                }
                        }
                });

                jButton1.setText("Send");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });

                scriptingButton.addActionListener(new java.awt.event.ActionListener() {
                        @SuppressWarnings("static-access")
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                JOptionPane jop = new JOptionPane();
                                jop.showMessageDialog(main, "Not implemented yet", "DarkIRC - Warning", JOptionPane.WARNING_MESSAGE);
                        }
                });

                configButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                GUIOption optnWindows = new GUIOption(main, DarkIRC.AppLang.getString("title_option"), true);
                                optnWindows.showGUI();
                        }
                });
                connectDisconnectButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                if (!DarkIRC.bot.isConnected()) {
                                        GUIConnect connectWindows = new GUIConnect(null, DarkIRC.AppLang.getString("title_connect"), true);
                                        String co[] = connectWindows.showGUI();
                                        if (co[1] != null && co[2] != null) {
                                                Properties prop = new Properties();
                                                InputStream input = null;
                                                try {

                                                        input = new FileInputStream(DarkIRCmain.getConfigFile());
                                                        prop.load(input);
                                                        if (prop.getProperty("proxy_host", "") != "" || prop.getProperty("proxy_host", "") != null) {
                                                                if (prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("http")) {
                                                                        System.setProperty("http.proxyHost", prop.getProperty("proxy_host", ""));
                                                                        System.setProperty("http.proxyPort", prop.getProperty("proxy_port", "80"));
                                                                } else if (prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("https")) {
                                                                        System.setProperty("https.proxyHost", prop.getProperty("proxy_host", ""));
                                                                        System.setProperty("https.proxyPort", prop.getProperty("proxy_port", "80"));
                                                                } else if (prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("socks")) {
                                                                        System.setProperty("socksProxyHost", prop.getProperty("proxy_host", ""));
                                                                        System.setProperty("socksProxyPort", prop.getProperty("proxy_port", "80"));
                                                                }
                                                        }
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
                                                set_text(get_text() + DarkIRC.AppLang.getString("connect_msg") + "\n");
                                                try {
                                                        DarkIRC.bot.setNick(co[0]);
                                                        if (Boolean.valueOf(co[3])) {
                                                                DarkIRC.bot.connect(co[1], Integer.parseInt(co[2]), new TrustingSSLSocketFactory());
                                                        } else {
                                                                DarkIRC.bot.connect(co[1], Integer.parseInt(co[2]));
                                                        }
                                                        joinButton.setEnabled(true);
                                                } catch (IOException ex) {
                                                        Logger.getLogger(GUIMain.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IrcException ex) {
                                                        Logger.getLogger(GUIMain.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                        }
                                } else {
                                        DarkIRC.bot.disconnect();
                                }
                        }
                });

                joinButton.addActionListener(new java.awt.event.ActionListener() {
                        @SuppressWarnings("static-access")
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                JOptionPane jop = new JOptionPane();
                                String channel = jop.showInputDialog(main, DarkIRC.AppLang.getString("gui_join_text"), DarkIRC.AppLang.getString("title_join"), JOptionPane.QUESTION_MESSAGE);
                                if (channel != null && channel != "" && channel.startsWith("#")) {
                                        boolean NoExist = true;
                                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                                        NoExist = false;
                                                        Logs.makeLog(DarkIRC.currentChan + ".log", get_text());
                                                        DarkIRC.currentChan = DarkIRC.channelsList.get(i);
                                                        jTabbedPane2.setSelectedIndex(i);
                                                        set_text(Logs.readLog(DarkIRC.currentChan + ".log"));
                                                }
                                        }
                                        if (NoExist) {
                                                DarkIRC.bot.joinChannel(channel);
                                                Logs.makeLog(DarkIRC.currentChan + ".log", get_text());
                                                set_text("");
                                                DarkIRC.currentChan = new Channel(channel, Channel.CHANNEL);
                                                DarkIRC.channelsList.add(DarkIRC.currentChan);
                                                GUIMain.topicField.setVisible(true);
                                                GUIMain.jScrollPane2.setVisible(true);
                                                GUIMain.jTabbedPane2.addTab(DarkIRC.currentChan.name, null);
                                                GUIMain.jTabbedPane2.setSelectedIndex(DarkIRC.channelsList.size() - 1);
                                        }
                                }
                        }
                });

                jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jTabbedPane2MouseClicked(evt);
                        }
                });
                jTextField1.addKeyListener(new KeyAdapter() {
                        public void keyReleased(KeyEvent touche) {
                                if (touche.getKeyCode() == KeyEvent.VK_ENTER) {
                                        if (jTextField1.getText() != "" || jTextField1.getText() != null) {
                                                parseCommand(jTextField1.getText());
                                                jTextField1.setText(null);
                                        }
                                }
                        }

                        public void keyTyped(KeyEvent e) {
                                if (e.isControlDown()) {
                                        if (e.getKeyChar() == '\u000B') { // ctrl + k
                                                jTextField1.setText(jTextField1.getText() + '\u0003');
                                        } else if (e.getKeyChar() == '\u0002') { // ctrl + b
                                                jTextField1.setText(jTextField1.getText() + '\u0002');
                                        } else if (e.getKeyChar() == '\u0015') {// ctrl + u
                                                jTextField1.setText(jTextField1.getText() + '\u001F');
                                        } else if (e.getKeyChar() == '\u0012') { // ctrl + r
                                                jTextField1.setText(jTextField1.getText() + '\u000F');
                                        }
                                }
                        }
                });

                jTextPane1.setEditable(false);
                jScrollPane1.setViewportView(jTextPane1);

                jTabbedPane2.addTab("Status", chanPanel/* jScrollPane1 */);
                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                jPanel1Layout.createSequentialGroup().addComponent(scriptingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(configButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 128, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(scriptingButton, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                                .addComponent(configButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                jScrollPane2.setViewportView(userList);

                javax.swing.GroupLayout chanPanelLayout = new javax.swing.GroupLayout(chanPanel);
                chanPanel.setLayout(chanPanelLayout);
                chanPanelLayout.setHorizontalGroup(chanPanelLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(topicField)
                                .addGroup(chanPanelLayout.createSequentialGroup().addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
                chanPanelLayout.setVerticalGroup(chanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                chanPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(topicField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(chanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)).addContainerGap()));

                topicField.setVisible(false);
                jScrollPane2.setVisible(false);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addContainerGap()
                                                .addComponent(connectDisconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(joinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap())
                                .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jTabbedPane2)
                                                                .addGroup(layout.createSequentialGroup().addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap()));
                layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                // //////////////////////////////////
                                .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(connectDisconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(joinButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jButton1)
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

                pack();

        }

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
                if (jTextField1.getText() != "" || jTextField1.getText() != null) {
                        parseCommand(jTextField1.getText());
                        jTextField1.setText(null);
                }
        }

        private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {
                if (!DarkIRC.channelsList.get(jTabbedPane2.getSelectedIndex()).equals(DarkIRC.currentChan)) {
                        jTabbedPane2.setForegroundAt(jTabbedPane2.getSelectedIndex(), Color.BLACK);
                        Logs.makeLog(DarkIRC.currentChan + ".log", get_text());
                        set_text("");
                        topicField.setText("");
                        DarkIRC.currentChan = DarkIRC.channelsList.get(jTabbedPane2.getSelectedIndex());
                        set_text(Logs.readLog(DarkIRC.currentChan + ".log"));
                        topicField.setText(DarkIRC.channelsList.get(jTabbedPane2.getSelectedIndex()).topic);
                }
                if (jTabbedPane2.getSelectedIndex() == 0) {
                        topicField.setVisible(false);
                        jScrollPane2.setVisible(false);
                } else if (DarkIRC.channelsList.get(jTabbedPane2.getSelectedIndex()).type == Channel.CHANNEL) {
                        topicField.setVisible(true);
                        jScrollPane2.setVisible(true);
                        userListModel.removeAllElements();
                        User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                        for (int i = 0; i < u.length; i++) {
                                userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                        }
                } else {
                        topicField.setVisible(false);
                        jScrollPane2.setVisible(false);
                }
        }

        private void parseCommand(String text) {
                String[] cmd = text.split(" ");
                if (text.startsWith("/")) {
                        if (cmd[0].equalsIgnoreCase("/server")) {
                                boolean ssl = false;
                                if (cmd.length > 1) {
                                        Properties prop = new Properties();
                                        InputStream input = null;
                                        try {

                                                input = new FileInputStream(DarkIRCmain.getConfigFile());
                                                prop.load(input);
                                                if (prop.getProperty("proxy_host", "") != "" || prop.getProperty("proxy_host", "") != null) {
                                                        if (prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("http")) {
                                                                System.setProperty("http.proxyHost", prop.getProperty("proxy_host", ""));
                                                                System.setProperty("http.proxyPort", prop.getProperty("proxy_port", "80"));
                                                        } else if (prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("https")) {
                                                                System.setProperty("https.proxyHost", prop.getProperty("proxy_host", ""));
                                                                System.setProperty("https.proxyPort", prop.getProperty("proxy_port", "80"));
                                                        } else if (prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("socks")) {
                                                                System.setProperty("socksProxyHost", prop.getProperty("proxy_host", ""));
                                                                System.setProperty("socksProxyPort", prop.getProperty("proxy_port", "80"));
                                                        }
                                                }
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
                                        int port = 6667;
                                        if (cmd.length == 3) {
                                                if (cmd[2].startsWith("+")) {
                                                        ssl = true;
                                                        cmd[2] = cmd[2].replace("+", "");
                                                }
                                                port = Integer.parseInt(cmd[2]);
                                        }
                                        try {
                                                if (ssl) {
                                                        DarkIRC.bot.connect(cmd[1], port, new TrustingSSLSocketFactory());
                                                } else {
                                                        DarkIRC.bot.connect(cmd[1], port);
                                                }
                                                joinButton.setEnabled(true);
                                        } catch (IOException ex) {
                                                Logger.getLogger(DarkIRC.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (IrcException ex) {
                                                Logger.getLogger(DarkIRC.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                } else {
                                        set_text(get_text() + DarkIRC.AppLang.getString("cmd_error_server") + "\n");
                                }
                        } else if (cmd[0].equalsIgnoreCase("/join") || cmd[0].equalsIgnoreCase("/j")) {
                                if (cmd.length > 1) {
                                        if (cmd.length == 2) {
                                                Command.join(cmd[1]);
                                        } else {
                                                Command.join(cmd[1], cmd[2]);
                                        }
                                } else {
                                        set_text(get_text() + DarkIRC.AppLang.getString("cmd_error_join") + "\n");
                                }

                        } else if (cmd[0].equalsIgnoreCase("/part") || cmd[0].equalsIgnoreCase("/p")) {
                                if (cmd.length > 1) {
                                        if (cmd.length > 2) {
                                                String reason = "";
                                                for (int i = 2; i < cmd.length; i++) {
                                                        if (reason == "") {
                                                                reason += cmd[i];
                                                        } else {
                                                                reason += " " + cmd[i];
                                                        }
                                                }
                                                DarkIRC.bot.partChannel(cmd[1], reason);
                                        } else {
                                                DarkIRC.bot.partChannel(cmd[1]);
                                        }
                                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                                System.out.println(DarkIRC.channelsList.get(i));
                                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(cmd[1])) {
                                                        GUIMain.jTabbedPane2.remove(i);
                                                        DarkIRC.channelsList.remove(cmd[1]);
                                                        DarkIRC.currentChan = DarkIRC.channelsList.get(i - 1);
                                                        if ((i - 1) == 0) {
                                                                GUIMain.jScrollPane2.setVisible(false);
                                                        }
                                                }
                                        }
                                } else {
                                        DarkIRC.bot.partChannel(DarkIRC.currentChan.name);
                                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                                System.out.println(DarkIRC.channelsList.get(i));
                                                if (DarkIRC.channelsList.get(i).equals(DarkIRC.currentChan)) {
                                                        jTabbedPane2.remove(i);
                                                        DarkIRC.channelsList.remove(DarkIRC.currentChan);
                                                        DarkIRC.currentChan = DarkIRC.channelsList.get(i - 1);
                                                        if ((i - 1) == 0) {
                                                                topicField.setVisible(false);
                                                                jScrollPane2.setVisible(false);
                                                        }

                                                }
                                        }
                                }
                                set_text(Logs.readLog(DarkIRC.currentChan + ".log"));
                        } else if (cmd[0].equalsIgnoreCase("/nick")) {
                                DarkIRC.bot.changeNick(cmd[1]);
                        } else if (cmd[0].equalsIgnoreCase("/msg")) {
                                if (cmd.length > 2) {
                                        String msg = "";
                                        for (int i = 2; i < cmd.length; i++) {
                                                if (msg == "") {
                                                        msg += cmd[i];
                                                } else {
                                                        msg += " " + cmd[i];
                                                }
                                        }
                                        DarkIRC.bot.sendMessage(cmd[1], msg);
                                }
                        } else if (cmd[0].equalsIgnoreCase("/query")) {
                                if (cmd.length == 2) {
                                        Command.Query(cmd[1]);
                                }
                        } else if (cmd[0].equalsIgnoreCase("/quit")) {
                                if (cmd.length > 1) {
                                        String reason = "";
                                        for (int i = 1; i < cmd.length; i++) {
                                                if (reason == "") {
                                                        reason += cmd[i];
                                                } else {
                                                        reason += " " + cmd[i];
                                                }
                                        }
                                        DarkIRC.bot.quitServer(reason);
                                } else {
                                        DarkIRC.bot.quitServer();
                                }
                                for (int i = 1; i < (DarkIRC.channelsList.size()); i++) {
                                        GUIMain.jTabbedPane2.remove(i);
                                        DarkIRC.channelsList.remove(i);
                                }
                                DarkIRC.currentChan = DarkIRC.channelsList.get(0);
                                set_text(Logs.readLog(DarkIRC.currentChan + ".log") + DarkIRC.AppLang.getString("quit_msg") + "\n");
                        } else if (cmd[0].equalsIgnoreCase("/list")) {
                                DarkIRC.bot.listChannels();
                        } else if (cmd[0].equalsIgnoreCase("/me")) {
                                if (cmd.length > 1) {
                                        String msg = "";
                                        for (int i = 1; i < cmd.length; i++) {
                                                if (i == 1) {
                                                        msg = cmd[i];
                                                } else {
                                                        msg += " " + cmd[i];
                                                }
                                        }
                                        if (GUIMain.jTabbedPane2.getSelectedIndex() != 0) {
                                                DarkIRC.bot.sendAction(DarkIRC.currentChan.name, msg);
                                                add_line("*" + DarkIRC.bot.getNick() + " " + msg);
                                        }
                                }
                        } else if (cmd[0].equalsIgnoreCase("/quote")) {
                                if (cmd.length > 1) {
                                        DarkIRC.bot.sendRawLine(text.split("/quote ")[1]);
                                }
                        } else if (cmd[0].equalsIgnoreCase("/dcc")) {
                                if (cmd.length > 2) {
                                        if (cmd[1].equalsIgnoreCase("chat")) {
                                                DccChat session = DarkIRC.bot.dccSendChatRequest(cmd[2], 120000); // 120000
                                                                                                                  // =
                                                                                                                  // 2
                                                                                                                  // minutes
                                                Channel dccsession = new Channel(session.getNick(), Channel.DCCCHAT);
                                                DarkIRC.channelsList.add(dccsession);
                                                jTabbedPane2.addTab(session.getNick(), new ImageIcon(DarkIRC.class.getResource("DCC.png")), null);
                                                Logs.makeLog(dccsession + ".log", "Session DCC avec " + session.getNick() + "\n");
                                                Thread t = new Thread(new DccChatThread(session));
                                                DarkIRC.DccChatList.add(new ObjectDccChat(session, t));
                                                t.start();
                                        }
                                } else if (cmd.length > 3) {
                                        if (cmd[1].equalsIgnoreCase("close")) {
                                                if (cmd[2].equalsIgnoreCase("chat")) {
                                                        for (int i = 0; DarkIRC.DccChatList.size() > i; i++) {
                                                                if (DarkIRC.DccChatList.get(i).getNick().equalsIgnoreCase(cmd[3])) {
                                                                        DarkIRC.DccChatList.get(i).kill();
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                } else if (DarkIRC.bot.isConnected() == true && DarkIRC.currentChan.type != Channel.STATUS && text != " ") {
                        if (jTabbedPane2.getIconAt(jTabbedPane2.getSelectedIndex()) != null && DarkIRC.channelsList.get(jTabbedPane2.getSelectedIndex()).type == Channel.DCCCHAT) {
                                String DCCCible = DarkIRC.channelsList.get(jTabbedPane2.getSelectedIndex()).name;
                                for (int i = 0; i < DarkIRC.DccChatList.size(); i++) {
                                        if ((DarkIRC.DccChatList.get(i).getNick()).equalsIgnoreCase(DCCCible)) {
                                                add_line("<" + DarkIRC.bot.getNick() + ">:" + text);
                                                DarkIRC.DccChatList.get(i).send(text);
                                        }
                                }
                        } else {
                                add_line("<" + DarkIRC.bot.getNick() + ">:" + text);
                                DarkIRC.bot.sendMessage(DarkIRC.currentChan.name, text);
                        }
                }
        }
}
