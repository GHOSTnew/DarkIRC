package fr.GHOSTnew.darkirc.utils;

import java.io.IOException;

import org.jibble.pircbot.DccChat;

public class ObjectDccChat {
	
	private Thread t;
	private DccChat chat;
	
	public ObjectDccChat(DccChat chat, Thread t){
		this.t = t;
		this.chat = chat;
	}
	
	public String getNick(){
		return chat.getNick();
	}
	
	public void kill(){
		t.interrupt();
	}
	
	public void send(String msg){
		try {
			chat.sendLine(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
