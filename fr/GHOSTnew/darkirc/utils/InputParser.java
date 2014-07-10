/*
 * This file is part of DarkIRC.
 *
 *  DarkIRC is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  DarkIRC is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with DarkIRC.  If not, see <http://www.gnu.org/licenses/>
 */

package fr.GHOSTnew.darkirc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.TrustingSSLSocketFactory;

import fr.GHOSTnew.darkirc.utils.logs;

import fr.GHOSTnew.darkirc.DarkIRC;

public class InputParser {
	
	public static void InputMSG (String text){
		String[] cmd = text.split(" ");
        if(text.startsWith("/")){
            if(cmd[0].equalsIgnoreCase("/server")){
            	boolean ssl = false;
            	if(cmd.length > 1){
            		Properties prop = new Properties();
        			InputStream input = null;
        			try {
        		 
        				input = new FileInputStream("config.properties");
        				prop.load(input);
        				if(prop.getProperty("proxy_host", "") != "" || prop.getProperty("proxy_host", "") != null){
        					if(prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("http")){
        						System.setProperty("http.proxyHost", prop.getProperty("proxy_host", ""));
        						System.setProperty("http.proxyPort", prop.getProperty("proxy_port", "80"));
        					}else if(prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("https")){
        						System.setProperty("https.proxyHost", prop.getProperty("proxy_host", ""));
        						System.setProperty("https.proxyPort", prop.getProperty("proxy_port", "80"));
        					}else if(prop.getProperty("proxy_type", "HTTP").equalsIgnoreCase("socks")){
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
            		if(cmd.length == 3){
            			if(cmd[2].startsWith("+")){
            				ssl = true;
            				cmd[2] = cmd[2].replace("+", "");
            			}
            			port = Integer.parseInt(cmd[2]);
            		}
            		try {
            			if(ssl){
            				DarkIRC.bot.connect(cmd[1],port, new TrustingSSLSocketFactory());
            			}else {
            				DarkIRC.bot.connect(cmd[1],port);
            			}
            			DarkIRC.setConnected(true);
            		}catch (IOException ex) {
            			Logger.getLogger(DarkIRC.class.getName()).log(Level.SEVERE, null, ex);
            		}catch (IrcException ex) {
            			Logger.getLogger(DarkIRC.class.getName()).log(Level.SEVERE, null, ex);
            		}
                }else {
                	DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + DarkIRC.messageLang.getString("cmd_error_server") + "\n");
                }
            }else if(cmd[0].equalsIgnoreCase("/join") || cmd[0].equalsIgnoreCase("/j")){
            	if(cmd.length > 1){
            		if(cmd.length == 2){
            			Command.join(cmd[1]);
            		}else{
            			Command.join(cmd[1], cmd[2]);
            		}
            	}else {
            		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + DarkIRC.messageLang.getString("cmd_error_join") + "\n");
            	}
            	
            }else if(cmd[0].equalsIgnoreCase("/part") || cmd[0].equalsIgnoreCase("/p")){
            	if(cmd.length > 1){
            		if(cmd.length > 2){
            			String reason = "";
            			for(int i = 2; i < cmd.length; i++){
            				if(reason == ""){
            					reason += cmd[i];
            				}else{
            					reason += " " + cmd[i];
            				}
            			}
            			DarkIRC.bot.partChannel(cmd[1], reason);
            		}else {
            			DarkIRC.bot.partChannel(cmd[1]);
            		}
            		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
            			System.out.println(DarkIRC.channelsList.get(i));
            			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(cmd[1])){
            				DarkIRC.jTabbedPane2.remove(i);
            				DarkIRC.channelsList.remove(cmd[1]);
            				DarkIRC.currentChan = DarkIRC.channelsList.get(i - 1);
            				if((i-1) == 0){
            					DarkIRC.topicField.setVisible(false);
                        		DarkIRC.jScrollPane2.setVisible(false);
            				}
            			}
            		}
            	}else{
            		DarkIRC.bot.partChannel(DarkIRC.currentChan);
            		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
            			System.out.println(DarkIRC.channelsList.get(i));
            			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(DarkIRC.currentChan)){
            				DarkIRC.jTabbedPane2.remove(i);
            				DarkIRC.channelsList.remove(DarkIRC.currentChan);
            				DarkIRC.currentChan = DarkIRC.channelsList.get(i - 1);
            				if((i-1) == 0){
            					DarkIRC.topicField.setVisible(false);
                        		DarkIRC.jScrollPane2.setVisible(false);
            				}
            				
            			}
            		}
            	}
            	DarkIRC.jTextPane1.setText(logs.readLog(DarkIRC.currentChan + ".log"));
            }else if(cmd[0].equalsIgnoreCase("/nick")){
            	DarkIRC.bot.changeNick(cmd[1]);
            }else if (cmd[0].equalsIgnoreCase("/msg")) {
            	if(cmd.length > 2){
            		String msg = "";
        			for(int i = 2; i < cmd.length; i++){
        				if(msg == ""){
        					msg += cmd[i];
        				}else{
        					msg += " " + cmd[i];
        				}
        			}
            		DarkIRC.bot.sendMessage(cmd[1], msg);
            	}
            }else if (cmd[0].equalsIgnoreCase("/query")) {
            	if(cmd.length == 2){
            		Command.Query(cmd[1]);
            	}
            }else if(cmd[0].equalsIgnoreCase("/quit")){
            	if(cmd.length > 1){
            		String reason = "";
        			for(int i = 1; i < cmd.length; i++){
        				if(reason == ""){
        					reason += cmd[i];
        				}else{
        					reason += " " + cmd[i];
        				}
        			}
        			DarkIRC.bot.quitServer(reason);
            	}else{
            		DarkIRC.bot.quitServer();
            	}
            	for(int i = 1; i < (DarkIRC.channelsList.size()); i++){
            		DarkIRC.jTabbedPane2.remove(i);
            		DarkIRC.channelsList.remove(i);
            	}
            	DarkIRC.currentChan = DarkIRC.channelsList.get(0);
            	DarkIRC.jTextPane1.setText(logs.readLog(DarkIRC.currentChan + ".log") + DarkIRC.messageLang.getString("quit_msg") + "\n");
            }else if(cmd[0].equalsIgnoreCase("/list")){
            	DarkIRC.bot.listChannels();
            }else if(cmd[0].equalsIgnoreCase("/me")){
            	if(cmd.length > 1){
            		String msg = "";
            		for(int i = 1; i < cmd.length; i++){
            			if(i == 1){
            				msg = cmd[i];
            			}else {
            				msg += " " + cmd[i];
            			}
            		}
            		if(DarkIRC.jTabbedPane2.getSelectedIndex() != 0){DarkIRC.bot.sendAction(
            				DarkIRC.currentChan, msg);
            		        DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "*" + DarkIRC.bot.getNick() + " " + msg +"\n");
            		}
            	}
            }else if(cmd[0].equalsIgnoreCase("/dcc")){
            	if(cmd.length > 2){
            		if(cmd[1].equalsIgnoreCase("chat")){
            			DccChat session = DarkIRC.bot.dccSendChatRequest(cmd[2], 120000); //120000 = 2 minutes
            			DarkIRC.channelsList.add("=" + session.getNick());
                		DarkIRC.channelsTopic.add("");
                		DarkIRC.jTabbedPane2.addTab(session.getNick(), new ImageIcon(DarkIRC.class.getResource("DCC.png")), null);
                		logs.makeLog("=" + session.getNick() + ".log", "Session DCC avec " + session.getNick() + "\n");
						Thread t = new Thread(new DccChatThread(session));
						DarkIRC.DccChatList.add(new ObjectDccChat(session,t));
						t.start();
            		}
            	}
            }
        }else if(DarkIRC.bot.isConnected() == true && !DarkIRC.currentChan.equalsIgnoreCase(DarkIRC.channelsList.get(0)) && text != " "){
        	if(DarkIRC.jTabbedPane2.getIconAt(DarkIRC.jTabbedPane2.getSelectedIndex()) != null){
        		String DCCCible = DarkIRC.channelsList.get(DarkIRC.jTabbedPane2.getSelectedIndex());
        		for(int i = 0; i < DarkIRC.DccChatList.size(); i++){
        			if(("=" + DarkIRC.DccChatList.get(i).getNick()).equalsIgnoreCase(DCCCible)){
        				DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "<" + DarkIRC.bot.getNick() + ">:" + text + "\n");
        				DarkIRC.DccChatList.get(i).send(text);
        			}
        		}
        	}else {
        		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "<" + DarkIRC.bot.getNick() + ">:" + text + "\n");
        		DarkIRC.bot.sendMessage(DarkIRC.currentChan, text);
        	}
        }
	}
}
