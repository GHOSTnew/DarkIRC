/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.GUI.Components
 * File: UserJListRenderer.java
 * Created on: 6 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * UserJListRenderer.java is part of DarkIRC.
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

package net.GHOSTnew.DarkIRC.GUI.Components;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Ressources;

public class UserJListRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (isSelected) {
                        setBackground(list.getSelectionBackground());
                        setForeground(list.getSelectionForeground());
                } else {
                        setBackground(list.getBackground());
                        setForeground(list.getForeground());
                }

                if (value.toString().startsWith("@")) {
                        ImageIcon imageIcon = new ImageIcon(Ressources.getImage("Users/op.png"));
                        setIcon(imageIcon);
                        setText(value.toString().replace("@", ""));
                } else if (value.toString().startsWith("$")) {
                        ImageIcon imageIcon = new ImageIcon(Ressources.getImage("Users/red.png"));
                        setIcon(imageIcon);
                        setText(value.toString().replace("$", ""));
                } else if (value.toString().startsWith("+")) {
                        ImageIcon imageIcon = new ImageIcon(Ressources.getImage("Users/voiced.png"));
                        setIcon(imageIcon);
                        setText(value.toString().replace("+", ""));
                } else if (value.toString().startsWith("%")) {
                        ImageIcon imageIcon = new ImageIcon(Ressources.getImage("Users/hop.png"));
                        setIcon(imageIcon);
                        setText(value.toString().replace("%", ""));
                } else if (value.toString().startsWith("~")) {
                        ImageIcon imageIcon = new ImageIcon(Ressources.getImage("Users/owner.png"));
                        setIcon(imageIcon);
                        setText(value.toString().replace("~", ""));
                } else {
                        setText(value.toString());
                        ImageIcon imageIcon = new ImageIcon(Ressources.getImage("Users/normal.png"));
                        setIcon(imageIcon);
                }

                return this;
        }
}
