package fr.GHOSTnew.darkirc.utils;

import java.awt.Color;
import java.io.IOException;

import org.jibble.pircbot.DccChat;

import fr.GHOSTnew.darkirc.DarkIRC;

public class DccChatThread implements Runnable {
	
	private DccChat chat;
	
	public DccChatThread(DccChat chat){
		this.chat = chat;
	}
	@Override
	public void run() {
		while(true){
			String msg = null;
			try {
				msg = chat.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(msg != null){
				if(DarkIRC.currentChan.equalsIgnoreCase("=" + chat.getNick())){
				    DarkIRC.jTextPane1.setText(DarkIRC.jTextPane1.getText() + "<" + chat.getNick() + ">: " + msg + "\n");
			    }else{
				    logs.makeLog("=" + chat.getNick()+ ".log", logs.readLog("="+chat.getNick()+".log") + "<" + chat.getNick() + ">: " + msg + "\n");
				    for(int i = 0; i < DarkIRC.channelsList.size(); i++){
		    		    if(DarkIRC.channelsList.get(i).equalsIgnoreCase("=" + chat.getNick())){
		    			    DarkIRC.jTabbedPane2.setForegroundAt(i, Color.RED);
		    		    }
				    }
		    	}
			}
		}
	}
	
}
