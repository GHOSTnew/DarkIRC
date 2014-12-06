/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel.Utils
 * File: DccChatThread.java
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
 * DccChatThread.java is part of DarkIRC.
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

import java.awt.Color;
import java.io.IOException;

import net.GHOSTnew.DarkIRC.GUI.GUIMain;
import net.GHOSTnew.DarkIRC.Kernel.DarkIRC;

import org.jibble.pircbot.DccChat;

public class DccChatThread implements Runnable {

        private DccChat chat;

        public DccChatThread(DccChat chat) {
                this.chat = chat;
        }

        @Override
        public void run() {
                while (true) {
                        String msg = null;
                        try {
                                msg = chat.readLine();
                        } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                        }
                        if (msg != null) {
                                if (DarkIRC.currentChan.name.equalsIgnoreCase(chat.getNick()) && DarkIRC.currentChan.type == Channel.DCCCHAT) {
                                        GUIMain.add_line("<" + chat.getNick() + ">: " + msg);
                                } else {
                                        Logs.makeLog("DCC_" + chat.getNick() + ".log", Logs.readLog("DCC_" + chat.getNick() + ".log") + "<" + chat.getNick() + ">: " + msg);
                                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(chat.getNick()) && DarkIRC.channelsList.get(i).type == Channel.DCCCHAT) {
                                                        GUIMain.jTabbedPane2.setForegroundAt(i, Color.RED);
                                                }
                                        }
                                }
                        }
                }
        }

}
