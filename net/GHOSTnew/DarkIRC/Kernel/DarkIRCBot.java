/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel
 * File: DarkIRCBot.java
 * Created on: juin 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * DarkIRCBot.java is part of DarkIRC.
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
package net.GHOSTnew.DarkIRC.Kernel;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.GHOSTnew.DarkIRC.DarkIRCmain;
import net.GHOSTnew.DarkIRC.GUI.GUIMain;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Ressources;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Channel;
import net.GHOSTnew.DarkIRC.Kernel.Utils.DccChatThread;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Logs;
import net.GHOSTnew.DarkIRC.Kernel.Utils.ObjectDccChat;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;


/**
 * 
 * @author GHOSTnew
 */
public class DarkIRCBot extends PircBot {
        public DarkIRCBot() {
                Properties prop = new Properties();
                InputStream input = null;
                try {

                        input = new FileInputStream(DarkIRCmain.getConfigFile());

                        // load a properties file
                        prop.load(input);
                        this.setName(prop.getProperty("nick", "DarkUser"));
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
                this.setLogin("DarkIRC");
                this.setVersion("DarkIRC v0.1");
                this.setAutoNickChange(true);
                this.setVerbose(false);
                try {
                        this.setEncoding("UTF-8");
                } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public void setNick(String nick) {
                this.setName(nick);
        }

        public void onTopic(String channel, String topic, String setBy, long date, boolean changed) {
                if (channel.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        String topic_message =
                                        DarkIRC.AppLang.getString("topic_msg").replace("{~channel~}", channel).replace("{~topic~}", topic).replace("{~nick~}", setBy)
                                                        .replace("{~date~}", new Date(date).toString());
                        GUIMain.add_line(topic_message);
                        GUIMain.topicField.setText(topic);
                }
                for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                        if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                DarkIRC.channelsList.get(i).topic = topic;
                        }
                }
        }

        public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
                GUIMain.add_line("*Notice <" + sourceNick + ">:" + notice);

        }

        public void onMessage(String channel, String sender, String login, String hostname, String message) {
                if (channel.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        GUIMain.add_line("<" + sender + ">:" + message);
                } else {
                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                // System.out.println(DarkIRC.channelsList.get(i).name + "  =  "
                                // + channel);
                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "<" + sender + ">:" + message + "\n");
                                        GUIMain.jTabbedPane2.setForegroundAt(i, Color.RED);
                                        // GUIMain.jTabbedPane2.setIconAt(i, new
                                        // ImageIcon(this.getClass().getResource("Black-Internet-icon.png")));
                                }
                        }
                }
        }

        public void onJoin(String channel, String sender, String login, String hostname) {
                String join_message = DarkIRC.AppLang.getString("join_msg").replace("{~nick~}", sender).replace("{~channel~}", channel);
                // System.out.println(join_message);
                if (channel.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        GUIMain.userListModel.removeAllElements();
                        User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                        for (int i = 0; i < u.length; i++) {
                                GUIMain.userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                        }
                        GUIMain.add_line(join_message);
                } else {
                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                // System.out.println(DarkIRC.channelsList.get(i).name + "  =  "
                                // + channel);
                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + join_message + "\n");
                                }
                        }
                }

        }

        public void onPart(String channel, String sender, String login, String hostname) {
                String part_message = DarkIRC.AppLang.getString("part_msg").replace("{~nick~}", sender).replace("{~channel~}", channel);
                if (!sender.equalsIgnoreCase(DarkIRC.bot.getNick())) {
                        if (channel.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                                GUIMain.add_line(part_message);

                                GUIMain.userListModel.removeAllElements();
                                User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                                for (int i = 0; i < u.length; i++) {
                                        GUIMain.userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                                }
                        } else {
                                for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                        // System.out.println(DarkIRC.channelsList.get(i).name +
                                        // "  =  " + channel);
                                        if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                                Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + part_message + "\n");
                                        }
                                }
                        }
                }
        }

        public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
                String kick_message =
                                DarkIRC.AppLang.getString("kick_msg").replace("{~nick~}", recipientNick).replace("{~channel~}", channel).replace("{~kickerNick~}", kickerNick)
                                                .replace("{~reason~}", reason);
                if (channel.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        GUIMain.add_line(kick_message);

                        GUIMain.userListModel.removeAllElements();
                        User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                        for (int i = 0; i < u.length; i++) {
                                GUIMain.userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                        }
                } else {
                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                // System.out.println(DarkIRC.channelsList.get(i).name + "  =  "
                                // + channel);
                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + kick_message + "\n");
                                }
                        }
                }
        }

        public void onPrivateMessage(String sender, String login, String hostname, String message) {
                // check if exist
                if (sender.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        GUIMain.add_line("<" + sender + ">:" + message);
                } else {
                        boolean Noexist = true;
                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(sender)) {
                                        Noexist = false;
                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "<" + sender + ">:" + message + "\n");
                                        GUIMain.jTabbedPane2.setForegroundAt(i, Color.RED);
                                        // GUIMain.jTabbedPane2.setIconAt(i, new
                                        // ImageIcon(this.getClass().getResource("Black-Internet-icon.png")));
                                }
                        }
                        if (Noexist) {
                                DarkIRC.channelsList.add(new Channel(sender, Channel.PRIVATE));
                                Logs.makeLog(sender + ".log", "<" + sender + ">:" + message + "\n");
                                GUIMain.jTabbedPane2.addTab(sender, null);
                                GUIMain.jTabbedPane2.setForegroundAt((DarkIRC.channelsList.size() - 1), Color.RED);
                        }
                }
        }

        public void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
                String quit_message = DarkIRC.AppLang.getString("user_quit_msg").replace("{~nick~}", sourceNick).replace("{~reason~}", reason);
                GUIMain.add_line(quit_message);
                if (DarkIRC.currentChan.type != Channel.STATUS) {
                        GUIMain.userListModel.removeAllElements();
                        User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                        for (int i = 0; i < u.length; i++) {
                                GUIMain.userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                        }
                }
        }

        public void onNickChange(String oldNick, String login, String hostname, String newNick) {
                String nick_change_message = DarkIRC.AppLang.getString("nick_change_msg").replace("{~oldnick~}", oldNick).replace("{~newnick~}", newNick);
                for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                        if (DarkIRC.channelsList.get(i).name.startsWith("#")) {
                                User[] uchan = DarkIRC.bot.getUsers(DarkIRC.channelsList.get(i).name);
                                // for(int n = 0; i < uchan.length; n++){
                                for (User us : uchan) {
                                        if (us.getNick().equalsIgnoreCase(newNick)) {
                                                if (DarkIRC.channelsList.get(i).equals(DarkIRC.currentChan)) {
                                                        GUIMain.add_line(nick_change_message);
                                                } else {
                                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + nick_change_message + "\n");
                                                }
                                        }
                                }
                        }
                }
                if (DarkIRC.currentChan.type != Channel.STATUS) {
                        GUIMain.userListModel.removeAllElements();
                        User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                        for (int i = 0; i < u.length; i++) {
                                GUIMain.userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                        }
                }
        }

        public void onAction(String sender, String login, String hostname, String target, String action) {
                if (target.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        GUIMain.add_line("*" + sender + " " + action);
                } else {
                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                // System.out.println(DarkIRC.channelsList.get(i).name + "  =  "
                                // + target);
                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(target)) {
                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "*" + sender + " " + action + "\n");
                                        GUIMain.jTabbedPane2.setForegroundAt(i, Color.RED);
                                        // GUIMain.jTabbedPane2.setIconAt(i, new
                                        // ImageIcon(this.getClass().getResource("Black-Internet-icon.png")));
                                }
                        }
                }
        }

        public void onUserList(String channel, User[] users) {
                if (DarkIRC.currentChan.type != Channel.STATUS) {
                        GUIMain.userListModel.removeAllElements();
                        for (int i = 0; i < users.length; i++) {
                                GUIMain.userListModel.addElement(users[i].getPrefix() + users[i].getNick());
                        }
                }
        }

        public void onChannelInfo(String channel, int userCount, String topic) {
                if (channel.startsWith("#")) {
                        if (DarkIRC.currentChan.type == Channel.STATUS) {
                                GUIMain.add_line(channel + " | " + userCount + " | " + topic);
                        } else {
                                Logs.makeLog("status.log", Logs.readLog("status.log") + channel + " | " + userCount + " | " + topic + "\n");
                                GUIMain.jTabbedPane2.setForegroundAt(0, Color.RED);
                        }
                }
        }

        public void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
                if (channel.equalsIgnoreCase(DarkIRC.currentChan.name)) {
                        GUIMain.add_line("*" + sourceNick + " set mode " + mode + "\n");
                        User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan.name);
                        GUIMain.userListModel.removeAllElements();
                        for (int i = 0; i < u.length; i++) {
                                GUIMain.userListModel.addElement(u[i].getPrefix() + u[i].getNick());
                        }
                } else {
                        for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                // System.out.println(DarkIRC.channelsList.get(i).name + "  =  "
                                // + channel);
                                if (DarkIRC.channelsList.get(i).name.equalsIgnoreCase(channel)) {
                                        Logs.makeLog(DarkIRC.channelsList.get(i) + ".log", Logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "*" + sourceNick + " set mode " + mode + "\n");
                                }
                        }
                }
                // System.out.println("channel:" + channel + " SourceNick:" + sourceNick
                // + " mode:" + mode);
        }

        public void onDisconnect() {
                GUIMain.add_line(DarkIRC.AppLang.getString("quit_msg"));
                while (!isConnected()) {
                        try {
                                reconnect();
                                Thread.sleep(3000L);
                                for (int i = 0; i < DarkIRC.channelsList.size(); i++) {
                                        if (DarkIRC.channelsList.get(i).name.startsWith("#")) {
                                                this.joinChannel(DarkIRC.channelsList.get(i).name);
                                        }
                                }
                        } catch (Exception e) {
                                try {
                                        Thread.sleep(10000);
                                } catch (Exception e1) {
                                }
                        }
                }
        }

        public void onIncomingFileTransfer(DccFileTransfer transfer) {
                int choix =
                                JOptionPane.showConfirmDialog(null, transfer.getNick() + " veux vous envoyer " + transfer.getFile() + ", voulez vous accepter?", transfer.getNick()
                                                + " -  DCC transfer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choix == JOptionPane.OK_OPTION) {
                        File dir = new File("Download");
                        if (!dir.exists() && !dir.isDirectory()) {
                                dir.mkdir();
                        }
                        transfer.receive(new File("Download" + File.separator + transfer.getFile().getName()), true);
                        DarkIRC.currentTransfer = transfer;
                } else {
                        transfer.close();
                }
        }

        public void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
                if (e != null) {
                        System.out.println(e.getMessage());
                        JOptionPane.showMessageDialog(null, transfer.getFile() + " n'a pas put etre téléchargé car: " + e.getMessage(), "Erreur, Echec du téléchargement", JOptionPane.ERROR_MESSAGE);
                } else {
                        JOptionPane.showMessageDialog(null, transfer.getFile().getName() + " à été téléchargé avec succes", "Téléchargement terminé", JOptionPane.PLAIN_MESSAGE);
                }
        }

        public void onIncomingChatRequest(DccChat chat) {
                int choix =
                                JOptionPane.showConfirmDialog(null, chat.getNick() + " veux lancer un chat DCC avec vous, voulez vous accepter?", chat.getNick() + " -  DCC Chat",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choix == JOptionPane.OK_OPTION) {
                        Channel dccsession = new Channel(chat.getNick(), Channel.DCCCHAT);
                        DarkIRC.channelsList.add(dccsession);
                        GUIMain.jTabbedPane2.addTab(chat.getNick(), new ImageIcon(Ressources.getImage("DCC.png")), null);
                        try {
                                chat.accept();
                                Logs.makeLog(dccsession + ".log", "Session DCC avec " + chat.getNick() + "\n");
                                Thread t = new Thread(new DccChatThread(chat));
                                DarkIRC.DccChatList.add(new ObjectDccChat(chat, t));
                                t.start();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        @Override
        protected void onServerResponse(int code, String response) {
                if (code == 372 || code <= 5 || code == 375 || code == 376 || code == 396) {
                        if (DarkIRC.currentChan.type == Channel.STATUS) {
                                GUIMain.add_line(response);
                        } else {
                                Logs.makeLog("status.log", Logs.readLog("status.log") + response + "\n");
                                GUIMain.jTabbedPane2.setForegroundAt(0, Color.RED);
                        }
                }
        }
}
