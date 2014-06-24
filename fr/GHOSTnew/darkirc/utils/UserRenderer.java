package fr.GHOSTnew.darkirc.utils;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

public class UserRenderer extends DefaultListCellRenderer {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 99999999999999999L;

	public Component getListCellRendererComponent(JList list,  Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
		    setBackground(list.getSelectionBackground());
		    setForeground(list.getSelectionForeground());
		} else {
		    setBackground(list.getBackground());
		    setForeground(list.getForeground());
		}
		
        if(value.toString().startsWith("@")){
        	ImageIcon imageIcon = new ImageIcon(getClass().getResource("op.png"));
        	setIcon(imageIcon);
        	setText(value.toString().replace("@", ""));
        }else if(value.toString().startsWith("$")){
        	ImageIcon imageIcon = new ImageIcon(getClass().getResource("red.png"));
        	setIcon(imageIcon);
        	setText(value.toString().replace("$", ""));
        }else if(value.toString().startsWith("+")){
        	ImageIcon imageIcon = new ImageIcon(getClass().getResource("voiced.png"));
        	setIcon(imageIcon);
        	setText(value.toString().replace("+", ""));
        }else if(value.toString().startsWith("%")){
        	ImageIcon imageIcon = new ImageIcon(getClass().getResource("hop.png"));
        	setIcon(imageIcon);
        	setText(value.toString().replace("%", ""));
        }else if(value.toString().startsWith("~")){
        	ImageIcon imageIcon = new ImageIcon(getClass().getResource("owner.png"));
        	setIcon(imageIcon);
        	setText(value.toString().replace("~", ""));
        }else {
        	setText(value.toString());
        	ImageIcon imageIcon = new ImageIcon(getClass().getResource("normal.png"));
        	setIcon(imageIcon);
        }
        
        
         
        return this;
    }
}