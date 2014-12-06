/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel.Utils
 * File: Command.java
 * Created on: juin 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * Command.java is part of DarkIRC.
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

package net.GHOSTnew.DarkIRC.Kernel.Utils;

import net.GHOSTnew.DarkIRC.GUI.GUIMain;
import net.GHOSTnew.DarkIRC.Kernel.DarkIRC;


public class Command {

        public static void join(String channel, String password) {
                boolean NoExist = true;
                for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                        if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                NoExist = false;
                                Logs.makeLog(DarkIRC.currentChan + ".log", GUIMain.get_text());
                                DarkIRC.currentChan = DarkIRC.channelsList.get(i);
                                GUIMain.jTabbedPane2.setSelectedIndex(i);
                                GUIMain.set_text(Logs.readLog(DarkIRC.currentChan + ".log"));
                        }
                }
                if (NoExist) {
                        if (password != "") {
                                DarkIRC.bot.joinChannel(channel, password);
                        } else {
                                DarkIRC.bot.joinChannel(channel);
                        }
                        Logs.makeLog(DarkIRC.currentChan + ".log", GUIMain.get_text());
                        DarkIRC.currentChan = new Channel(channel, Channel.CHANNEL);
                        GUIMain.set_text("");
                        DarkIRC.channelsList.add(DarkIRC.currentChan);
                        GUIMain.topicField.setVisible(true);
                        GUIMain.jScrollPane2.setVisible(true);
                        GUIMain.jTabbedPane2.addTab(DarkIRC.currentChan.name, null);
                        System.out.println(DarkIRC.channelsList.size() - 1);
                        GUIMain.jTabbedPane2.setSelectedIndex(DarkIRC.channelsList.size() - 1);
                }
        }

        public static void join(String channel) {
                join(channel, "");
        }

        public static void Query(String nick) {
                boolean NoExist = true;
                for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                        if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(nick)) {
                                NoExist = false;
                                Logs.makeLog(DarkIRC.currentChan + ".log", GUIMain.get_text());
                                DarkIRC.currentChan = DarkIRC.channelsList.get(i);
                                GUIMain.jTabbedPane2.setSelectedIndex(i);
                                GUIMain.set_text(Logs.readLog(DarkIRC.currentChan + ".log"));
                        }
                }
                if (NoExist) {
                        Logs.makeLog(DarkIRC.currentChan + ".log", GUIMain.get_text());
                        GUIMain.set_text("");
                        DarkIRC.currentChan = new Channel(nick, Channel.PRIVATE);
                        DarkIRC.channelsList.add(DarkIRC.currentChan);
                        GUIMain.topicField.setVisible(false);
                        GUIMain.jScrollPane2.setVisible(false);
                        GUIMain.jTabbedPane2.addTab(DarkIRC.currentChan.name, null);
                        System.out.println(DarkIRC.channelsList.size() - 1);
                        GUIMain.jTabbedPane2.setSelectedIndex(DarkIRC.channelsList.size() - 1);
                }
        }

        public static void kick(String nick, String reason) {
                if (reason != "") {
                        DarkIRC.bot.kick(DarkIRC.currentChan.name, nick, reason);
                } else {
                        DarkIRC.bot.kick(DarkIRC.currentChan.name, nick);
                }
        }

        public static void kick(String nick) {
                kick(nick, "");
        }

        public static void ban(String nick) {
                DarkIRC.bot.ban(DarkIRC.currentChan.name, nick + "!*@*");
        }

        public static void voice(String nick) {
                DarkIRC.bot.voice(DarkIRC.currentChan.name, nick);
        }

        public static void devoice(String nick) {
                DarkIRC.bot.deVoice(DarkIRC.currentChan.name, nick);
        }

        public static void op(String nick) {
                DarkIRC.bot.op(DarkIRC.currentChan.name, nick);
        }

        public static void deop(String nick) {
                DarkIRC.bot.deOp(DarkIRC.currentChan.name, nick);
        }
}
