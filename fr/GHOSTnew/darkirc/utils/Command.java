package fr.GHOSTnew.darkirc.utils;

import fr.GHOSTnew.darkirc.DarkIRC;

public class Command {
	
	public static void join(String channel, String password){
		boolean NoExist = true;
    	for(int i = 0; i < DarkIRC.channelsList.size(); i++){
			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(channel)){
				NoExist = false;
				logs.makeLog(DarkIRC.currentChan + ".log", DarkIRC.jTextPane1.getText());
				DarkIRC.currentChan = DarkIRC.channelsList.get(i);
				DarkIRC.jTabbedPane2.setSelectedIndex(i);
				DarkIRC.jTextPane1.setText(logs.readLog(DarkIRC.currentChan + ".log"));
			}
    	}
    	if(NoExist){
    		if(password != ""){
    			DarkIRC.bot.joinChannel(channel, password);
    		}else{
    			DarkIRC.bot.joinChannel(channel);
    		}
    		logs.makeLog(DarkIRC.currentChan + ".log", DarkIRC.jTextPane1.getText());
    		DarkIRC.jTextPane1.setText("");
    		DarkIRC.currentChan = channel;
    		DarkIRC.channelsList.add(channel);
    		DarkIRC.topicField.setVisible(true);
    		DarkIRC.jScrollPane2.setVisible(true);
    		DarkIRC.jTabbedPane2.addTab(DarkIRC.currentChan, null);
    		System.out.println(DarkIRC.channelsList.size()-1);
    		DarkIRC.jTabbedPane2.setSelectedIndex(DarkIRC.channelsList.size()-1);
    	}
	}
	public static void join(String channel){
		join(channel, "");
	}
	public static void Query(String nick){
		boolean NoExist = true;
    	for(int i = 0; i < DarkIRC.channelsList.size(); i++){
			if(DarkIRC.channelsList.get(i).equalsIgnoreCase(nick)){
				NoExist = false;
				logs.makeLog(DarkIRC.currentChan + ".log", DarkIRC.jTextPane1.getText());
				DarkIRC.currentChan = DarkIRC.channelsList.get(i);
				DarkIRC.jTabbedPane2.setSelectedIndex(i);
				DarkIRC.jTextPane1.setText(logs.readLog(DarkIRC.currentChan + ".log"));
			}
    	}
    	if(NoExist){
    		logs.makeLog(DarkIRC.currentChan + ".log", DarkIRC.jTextPane1.getText());
    		DarkIRC.jTextPane1.setText("");
    		DarkIRC.currentChan = nick;
    		DarkIRC.channelsList.add(nick);
    		DarkIRC.topicField.setVisible(false);
    		DarkIRC.jScrollPane2.setVisible(false);
    		DarkIRC.channelsTopic.add("");
    		DarkIRC.jTabbedPane2.addTab(DarkIRC.currentChan, null);
    		System.out.println(DarkIRC.channelsList.size()-1);
    		DarkIRC.jTabbedPane2.setSelectedIndex(DarkIRC.channelsList.size()-1);
    	}
	}
	
	public static void kick(String nick, String reason){
		if(reason != ""){
			DarkIRC.bot.kick(DarkIRC.currentChan, nick, reason);
		}else{
			DarkIRC.bot.kick(DarkIRC.currentChan, nick);
		}
	}
	public static void kick(String nick){
		kick(nick, "");
	}
	public static void ban(String nick){
		DarkIRC.bot.ban(DarkIRC.currentChan, nick + "!*@*");
	}
	public static void voice(String nick){
		DarkIRC.bot.voice(DarkIRC.currentChan, nick);
	}
	public static void devoice(String nick){
		DarkIRC.bot.deVoice(DarkIRC.currentChan, nick);
	}
	public static void op(String nick){
		DarkIRC.bot.op(DarkIRC.currentChan, nick);
	}
	public static void deop(String nick){
		DarkIRC.bot.deOp(DarkIRC.currentChan, nick);
	}
}
