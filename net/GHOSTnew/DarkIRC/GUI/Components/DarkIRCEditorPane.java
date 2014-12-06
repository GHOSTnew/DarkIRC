/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.GUI.Components
 * File: DarkIRCEditorPane.java
 * Created on: 6 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * DarkIRCEditorPane.java is part of DarkIRC.
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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;

import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Ressources;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.smilies;
import net.GHOSTnew.DarkIRC.Kernel.DarkIRC;

public class DarkIRCEditorPane extends JEditorPane {
        public static List<smilies> smiliesList = new ArrayList<smilies>();

        private static final long serialVersionUID = 1L;

        public DarkIRCEditorPane() {
                super();
                smiliesList.add(new smilies(":-)", new ImageIcon(Ressources.getImage("smilies/smiling.png"))));
                smiliesList.add(new smilies(":)", new ImageIcon(Ressources.getImage("smilies/smiling.png"))));
                smiliesList.add(new smilies(";-)", new ImageIcon(Ressources.getImage("smilies/winking.png"))));
                smiliesList.add(new smilies(";)", new ImageIcon(Ressources.getImage("smilies/winking.png"))));
                smiliesList.add(new smilies(":-/", new ImageIcon(Ressources.getImage("smilies/unsure_2.png"))));
                smiliesList.add(new smilies(":-\\", new ImageIcon(Ressources.getImage("smilies/unsure.png"))));
                smiliesList.add(new smilies(":-D", new ImageIcon(Ressources.getImage("smilies/laughing.png"))));
                smiliesList.add(new smilies(":(", new ImageIcon(Ressources.getImage("smilies/frowning.png"))));
                smiliesList.add(new smilies(":-(", new ImageIcon(Ressources.getImage("smilies/frowning.png"))));
                smiliesList.add(new smilies(":-C", new ImageIcon(Ressources.getImage("smilies/angry.png"))));
                smiliesList.add(new smilies("8-)", new ImageIcon(Ressources.getImage("smilies/cool.png"))));
                smiliesList.add(new smilies(":-o", new ImageIcon(Ressources.getImage("smilies/surprised.png"))));
                smiliesList.add(new smilies(":-O", new ImageIcon(Ressources.getImage("smilies/surprised.png"))));
                smiliesList.add(new smilies(";-(", new ImageIcon(Ressources.getImage("smilies/crying.png"))));
                smiliesList.add(new smilies(":'(", new ImageIcon(Ressources.getImage("smilies/crying.png"))));
                smiliesList.add(new smilies("<3", new ImageIcon(Ressources.getImage("smilies/heart.png"))));
        }

        public void initListener() {
                getDocument().addDocumentListener(new DocumentListener() {
                        public void insertUpdate(DocumentEvent event) {
                                final DocumentEvent e = event;
                                SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                                if (e.getDocument() instanceof StyledDocument) {
                                                        try {
                                                                StyledDocument doc = (StyledDocument) e.getDocument();
                                                                int start = Utilities.getRowStart(DarkIRCEditorPane.this, Math.max(0, e.getOffset() - 1));
                                                                int end = Utilities.getWordStart(DarkIRCEditorPane.this, e.getOffset() + e.getLength());
                                                                String text = doc.getText(start, end - start);

                                                                for (int w = 0; w < smiliesList.size(); w++) {
                                                                        int i = text.indexOf(smiliesList.get(w).smile);
                                                                        while (i >= 0) {
                                                                                final SimpleAttributeSet attrs = new SimpleAttributeSet(doc.getCharacterElement(start + i).getAttributes());
                                                                                if (StyleConstants.getIcon(attrs) == null) {
                                                                                        StyleConstants.setIcon(attrs, smiliesList.get(w).img);
                                                                                        doc.remove(start + i, smiliesList.get(w).smile.length());
                                                                                        doc.insertString(start + i, smiliesList.get(w).smile, attrs);
                                                                                }
                                                                                i = text.indexOf(smiliesList.get(w).smile, i + smiliesList.get(w).smile.length());
                                                                        }
                                                                }
                                                        } catch (BadLocationException e1) {
                                                                e1.printStackTrace();
                                                        }
                                                }
                                                if (words.length < styles.length)
                                                        throw new ArrayIndexOutOfBoundsException();
                                                String content = null;
                                                try {
                                                        Document d = getDocument();
                                                        content = d.getText(0, d.getLength());
                                                } catch (BadLocationException e) {
                                                        return;
                                                }
                                                for (int i = 0; i < words.length; i++) {
                                                        Pattern pattern = Pattern.compile(words[i], Pattern.MULTILINE);
                                                        Matcher matcher = pattern.matcher(content);
                                                        int last = 0;
                                                        while (matcher.find()) {
                                                                if (last != matcher.start() || last == 0) {
                                                                        if (!out[i]) {
                                                                                ((DefaultStyledDocument) getDocument()).setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(),
                                                                                                styles[i], false);
                                                                        } else {
                                                                                ((DefaultStyledDocument) getDocument()).setCharacterAttributes(matcher.start() + 1,
                                                                                                matcher.end() - matcher.start() - 2, styles[i], false);
                                                                        }
                                                                        last = matcher.end();
                                                                }
                                                        }
                                                }
                                        }
                                });
                        }

                        public void removeUpdate(DocumentEvent e) {}

                        public void changedUpdate(DocumentEvent e) {}
                });
        }

        private boolean[] out;
        private static Style[] styles = new Style[4];
        {
                StyleContext sc = new StyleContext();
                styles[0] = sc.addStyle("nick", null);
                styles[0].addAttribute(StyleConstants.Foreground, new Color(Integer.parseInt("4f66b0", 16)));
                styles[0].addAttribute(StyleConstants.Bold, true);

                styles[1] = sc.addStyle("join/part", null);
                styles[1].addAttribute(StyleConstants.Foreground, new Color(Integer.parseInt("008B00", 16)));
                styles[1].addAttribute(StyleConstants.Bold, true);

                styles[2] = sc.addStyle("notice", null);
                styles[2].addAttribute(StyleConstants.Foreground, new Color(Integer.parseInt("218868", 16)));
                styles[2].addAttribute(StyleConstants.Bold, false);

                styles[3] = sc.addStyle("topic", null);
                styles[3].addAttribute(StyleConstants.Foreground, new Color(Integer.parseInt("5E2D79", 16)));
                styles[3].addAttribute(StyleConstants.Bold, false);

                out = new boolean[] {false, false, false, false};
        }
        private static String[] words = {"^([<][a-zA-Z_0-9]+[>]):", "^([*][a-zA-Z_0-9]+(.+?)([" + DarkIRC.currentChan.name + "])(.?)+)", "^([*](Notice)(.+)([<].+[>])(.+))",
                        "^([*](.+?)((t|T)opic)(.+?)[" + DarkIRC.currentChan.name + "](.+))"};
}
