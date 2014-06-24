package fr.GHOSTnew.darkirc.utils.userListAction;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.GHOSTnew.darkirc.DarkIRC;
import fr.GHOSTnew.darkirc.utils.Command;

public class DeopAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DeopAction(String text) {
		super(text);
	}

	public void actionPerformed(ActionEvent e) {
		String nick = DarkIRC.userList.getSelectedValue().toString();
		if(nick.startsWith("@")){
			nick = nick.replace("@", "");
		}else if(nick.startsWith("$")){
			nick = nick.replace("$", "");
		}else if(nick.startsWith("+")){
			nick = nick.replace("+", "");
		}else if(nick.startsWith("%")){
			nick = nick.replace("%", "");
		}else if(nick.startsWith("~")){
			nick = nick.replace("~", "");
		}
		Command.deop(nick);
	}

}