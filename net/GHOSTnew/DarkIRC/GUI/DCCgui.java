/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.GUI
 * File: DCCgui.java
 * Created on: juin 2014
 * 
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * DCCgui.java is part of DarkIRC.
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
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DCCgui extends JDialog {

        private final JPanel contentPanel = new JPanel();
        private JTable table_dcc;

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
                        table_dcc = new JTable();
                        table_dcc.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"File", "size", "Progress", "%", "speed", "status"}));
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
