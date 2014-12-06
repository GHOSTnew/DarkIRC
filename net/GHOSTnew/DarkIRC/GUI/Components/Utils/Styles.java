/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.GUI.Components.Utils
 * File: Styles.java
 * Created on: 6 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * Styles.java is part of DarkIRC.
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

package net.GHOSTnew.DarkIRC.GUI.Components.Utils;

import java.awt.Color;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class Styles {
        public static HTMLEditorKit kit = new HTMLEditorKit();

        public static Document doc() {

                StyleConstants.setForeground(sjoin, new Color(Integer.parseInt("5e8d5e", 16))); // http://www.color-hex.com/color/5e8d5e
                StyleConstants.setBold(sjoin, true);

                StyleConstants.setForeground(spart_quit, new Color(Integer.parseInt("8d8d5e", 16))); // http://www.color-hex.com/color/8d8d5e
                StyleConstants.setBold(spart_quit, true);

                style();
                return kit.createDefaultDocument();
        }

        public static void style() {
                StyleSheet styleSheet = kit.getStyleSheet();
                styleSheet.addRule("body {color: #000000; margin: 4px; }");
        }

        public static Style sjoin = new DefaultStyledDocument().addStyle("join Style Bitch", null);
        public static Style spart_quit = new DefaultStyledDocument().addStyle("part/quit Style Bitch", null);

}
