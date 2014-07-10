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

package fr.GHOSTnew.darkirc;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import fr.GHOSTnew.darkirc.utils.DccChatThread;
import fr.GHOSTnew.darkirc.utils.ObjectDccChat;
import fr.GHOSTnew.darkirc.utils.logs;

/**
 *
 * @author GHOSTnew
 */
public class DarkIRCBot extends PircBot{
    public DarkIRCBot(){
    	File f = new File("config.properties");
    	if(!f.exists()){
    	  try {
			f.createNewFile();
    	  } catch (IOException e) {
			e.printStackTrace();
		  }
    	}
    	Properties prop = new Properties();
		InputStream input = null;
		try {
	 
			input = new FileInputStream("config.properties");

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
    }
    
    public void setNick(String nick){
    	this.setName(nick);
    }
    public void onTopic(String channel,String topic,String setBy,long date,boolean changed){
    	if(channel.equalsIgnoreCase(DarkIRC.currentChan)){
    		String topic_message = DarkIRC.messageLang.getString("topic_msg").replace("{~channel~}", channel).replace("{~topic~}", topic).replace("{~nick~}", setBy).replace("{~date~}", new Date(date).toString());
    		DarkIRC.topicField.setText(topic);
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() +topic_message + "\n");
    	}
    	for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    		if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
    			if((DarkIRC.channelsTopic.size() - 1) < i){
    				DarkIRC.channelsTopic.add(topic);
    			}else{
    				DarkIRC.channelsTopic.set(i, topic);
    			}
    		}
    	}
    }
    public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice){
        DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() +  "*Notice <" + sourceNick + ">:" + notice + "\n");
       
    }
    public void onMessage(String channel, String sender, String login, String hostname, String message){
    	if(channel.equalsIgnoreCase(DarkIRC.currentChan)){
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "<" + sender + ">:" + message +"\n");
    	}
    	else {
    		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    			//System.out.println(DarkIRC.channelsList.get(i) + "  =  " + channel);
    			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
    				logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "<" + sender + ">:" + message +"\n");
    				DarkIRC.jTabbedPane2.setForegroundAt(i, Color.RED);
    				//DarkIRC.jTabbedPane2.setIconAt(i, new ImageIcon(this.getClass().getResource("Black-Internet-icon.png")));
    			}
    		}
    	}
    }
    public void onJoin(String channel, String sender, String login, String hostname){
    	String join_message = DarkIRC.messageLang.getString("join_msg").replace("{~nick~}", sender).replace("{~channel~}", channel);
    	//System.out.println(join_message);
    	if(channel.equalsIgnoreCase(DarkIRC.currentChan)){
    		DarkIRC.listModel.removeAllElements();
        	User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan);
        	for(int i = 0; i < u.length; i++){
        		DarkIRC.listModel.addElement(u[i].getPrefix() + u[i].getNick());
        	}
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + join_message + "\n");
    	}
    	else {
    		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    			//System.out.println(DarkIRC.channelsList.get(i) + "  =  " + channel);
    			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
    				logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + join_message + "\n");
    			}
    		}
    	}
    	
    }
    public void onPart(String channel, String sender, String login, String hostname) {
    	String part_message = DarkIRC.messageLang.getString("part_msg").replace("{~nick~}", sender).replace("{~channel~}", channel);
    	if(!sender.equalsIgnoreCase(DarkIRC.bot.getNick())){
    		if(channel.equalsIgnoreCase(DarkIRC.currentChan)){
    			DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + part_message + "\n");
    			
    	        DarkIRC.listModel.removeAllElements();
    	    	User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan);
    	    	for(int i = 0; i < u.length; i++){
    	    		DarkIRC.listModel.addElement(u[i].getPrefix() + u[i].getNick());
    	    	}
    		}
    		else {
    			for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    				//System.out.println(DarkIRC.channelsList.get(i) + "  =  " + channel);
    				if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
    					logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + part_message + "\n");
    				}
    			}
    		}
    	}
    }
    public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
    	String kick_message = DarkIRC.messageLang.getString("kick_msg").replace("{~nick~}", recipientNick).replace("{~channel~}", channel).replace("{~kickerNick~}", kickerNick).replace("{~reason~}", reason);
    	if(channel.equalsIgnoreCase(DarkIRC.currentChan)){
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + kick_message + "\n");
    		
    		DarkIRC.listModel.removeAllElements();
	    	User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan);
	    	for(int i = 0; i < u.length; i++){
	    		DarkIRC.listModel.addElement(u[i].getPrefix() + u[i].getNick());
	    	}
    	}
    	else {
    		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    			//System.out.println(DarkIRC.channelsList.get(i) + "  =  " + channel);
    			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
    				logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log")  + kick_message + "\n");
    			}
    		}
    	}
    }
    public void onPrivateMessage(String sender,String login,String hostname,String message){
    	//check if exist
    	if(sender.equalsIgnoreCase(DarkIRC.currentChan)){
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "<" + sender + ">:" + message +"\n");
    	}
    	else {
    		boolean Noexist = true;
    		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(sender)){
    				Noexist = false;
    				logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "<" + sender + ">:" + message +"\n");
    				DarkIRC.jTabbedPane2.setForegroundAt(i, Color.RED);
    				//DarkIRC.jTabbedPane2.setIconAt(i, new ImageIcon(this.getClass().getResource("Black-Internet-icon.png")));
    			}
    		}
    		if(Noexist){
    			DarkIRC.channelsList.add(sender);
    			logs.makeLog(sender + ".log", "<" + sender + ">:" + message +"\n");
    			DarkIRC.jTabbedPane2.addTab(sender, null);
    			DarkIRC.jTabbedPane2.setForegroundAt((DarkIRC.channelsList.size()-1), Color.RED);
    		}
    	}
    }
    public void onQuit(String sourceNick, String sourceLogin, String sourceHostname,String reason){
    	String quit_message = DarkIRC.messageLang.getString("user_quit_msg").replace("{~nick~}", sourceNick).replace("{~reason~}", reason);
    	DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + quit_message + "\n");
    	if(!DarkIRC.currentChan.equalsIgnoreCase("status")){
    		DarkIRC.listModel.removeAllElements();
        	User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan);
        	for(int i = 0; i < u.length; i++){
        		DarkIRC.listModel.addElement(u[i].getPrefix() + u[i].getNick());
        	}
        }
    }
    public void onNickChange(String oldNick,String login,String hostname,String newNick){
    	String nick_change_message = DarkIRC.messageLang.getString("nick_change_msg").replace("{~oldnick~}", oldNick).replace("{~newnick~}", newNick);
    	for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    		if(DarkIRC.channelsList.get(i).startsWith("#")){
    			User[] uchan = DarkIRC.bot.getUsers(DarkIRC.channelsList.get(i));
    			//for(int n = 0; i < uchan.length; n++){
    			for(User us : uchan){
    				if(us.getNick().equalsIgnoreCase(newNick)){
    					if(DarkIRC.channelsList.get(i).equalsIgnoreCase(DarkIRC.currentChan)){
    						DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + nick_change_message + "\n");
    					}else{
    						logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + nick_change_message +"\n");
    					}
    				}
    			}
    		}
    	}
    	if(!DarkIRC.currentChan.equalsIgnoreCase("status")){
    		DarkIRC.listModel.removeAllElements();
        	User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan);
        	for(int i = 0; i < u.length; i++){
        		DarkIRC.listModel.addElement(u[i].getPrefix() + u[i].getNick());
        	}
        }
    }
    public void onAction(String sender,String login,String hostname,String target,String action){
    	if(target.equalsIgnoreCase(DarkIRC.currentChan)){
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "*" + sender + " " + action +"\n");
    	}
    	else {
    		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    			//System.out.println(DarkIRC.channelsList.get(i) + "  =  " + target);
    			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(target)){
    				logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "*" + sender + " " + action +"\n");
    				DarkIRC.jTabbedPane2.setForegroundAt(i, Color.RED);
    				//DarkIRC.jTabbedPane2.setIconAt(i, new ImageIcon(this.getClass().getResource("Black-Internet-icon.png")));
    			}
    		}
    	}
    }
    public void onUserList(String channel, User[] users){
    	if(!DarkIRC.currentChan.equalsIgnoreCase("status")){
    		DarkIRC.listModel.removeAllElements();
        	for(int i = 0; i < users.length; i++){
        		DarkIRC.listModel.addElement(users[i].getPrefix() + users[i].getNick());
        	}
        }
    }
    public void onChannelInfo(String channel, int userCount,String topic){
    	if(channel.startsWith("#")){
    		if(DarkIRC.currentChan.equalsIgnoreCase("status")){
    			DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + channel + " | " + userCount + " | " + topic + "\n");
    		}else{
    			logs.makeLog("status.log", logs.readLog("status.log") + channel + " | " + userCount + " | " + topic + "\n");
    			DarkIRC.jTabbedPane2.setForegroundAt(0, Color.RED);
    		}
        }
    }
    
    public void onMode(String channel,String sourceNick,String sourceLogin,String sourceHostname,String mode){
    	if(channel.equalsIgnoreCase(DarkIRC.currentChan)){
    		DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "*" + sourceNick + " set mode " + mode +"\n");
    		User[] u = DarkIRC.bot.getUsers(DarkIRC.currentChan);
    		DarkIRC.listModel.removeAllElements();
	    	for(int i = 0; i < u.length; i++){
	    		DarkIRC.listModel.addElement(u[i].getPrefix() + u[i].getNick());
	    	}
    	}
    	else {
    		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
    			//System.out.println(DarkIRC.channelsList.get(i) + "  =  " + channel);
    			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
    				logs.makeLog(DarkIRC.channelsList.get(i) + ".log", logs.readLog(DarkIRC.channelsList.get(i) + ".log") + "*" + sourceNick + " set mode " + mode +"\n");
    			}
    		}
    	}
    	//System.out.println("channel:" + channel + " SourceNick:" + sourceNick + " mode:" + mode);
    }
    public void onDisconnect() {
    	DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + DarkIRC.messageLang.getString("quit_msg") + "\n");
        while (!isConnected() && DarkIRC.UconnectedU) {
            try {
                reconnect();
        		Thread.sleep(3000L);
        		for(int i = 0; i < DarkIRC.channelsList.size(); i++){
        			if(DarkIRC.channelsList.get(i).startsWith("#")){
        				this.joinChannel(DarkIRC.channelsList.get(i));
        			}
        		}
            }
            catch (Exception e) {
                try {
                    Thread.sleep(10000);
                }
                catch (Exception e1) {
                }
            }
        }
    }
    
    public void onIncomingFileTransfer(DccFileTransfer transfer){		
    	int choix = JOptionPane.showConfirmDialog(null, transfer.getNick() + " veux vous envoyer " + transfer.getFile() + ", voulez vous accepter?", transfer.getNick() + " -  DCC transfer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	if(choix == JOptionPane.OK_OPTION){
    		
    	}else{
    		transfer.close();
    	}
    }
    
    public void onIncomingChatRequest(DccChat chat){
    	int choix = JOptionPane.showConfirmDialog(null, chat.getNick() + " veux lancer un chat DCC avec vous, voulez vous accepter?", chat.getNick() + " -  DCC Chat", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	if(choix == JOptionPane.OK_OPTION){
    		DarkIRC.channelsList.add("=" + chat.getNick());
    		DarkIRC.channelsTopic.add("");
    		DarkIRC.jTabbedPane2.addTab(chat.getNick(), new ImageIcon(this.getClass().getResource("DCC.png")), null);
    		try {
				chat.accept();
				logs.makeLog("=" + chat.getNick() + ".log", "Session DCC avec " + chat.getNick() + "\n");
				Thread t = new Thread(new DccChatThread(chat));
				DarkIRC.DccChatList.add(new ObjectDccChat(chat,t));
	    	    t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @Override
    protected void onServerResponse(int code, String response) {
    	if(code == 372 || code <= 5 || code == 375 || code == 376 || code ==  396) {
    		if(DarkIRC.currentChan.equalsIgnoreCase("status")){
    			DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + response + "\n");
    		}else{
    			logs.makeLog("status.log", logs.readLog("status.log") + response + "\n");
    			DarkIRC.jTabbedPane2.setForegroundAt(0, Color.RED);
    		}
		}
    }
}
