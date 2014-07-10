package fr.GHOSTnew.darkirc.utils;

import java.util.Locale;

public class langage {
	public static Locale getLocale(String language) {
		Locale lang = new Locale("fr", "");
		if(language.equalsIgnoreCase("Français")){
			lang = new Locale("fr", "");
		}else if(language.equalsIgnoreCase("English")){
			lang = new Locale("en", "");
		}else if(language.equalsIgnoreCase("Español")){
			lang = new Locale("es", "");
		}else if(language.equalsIgnoreCase("日本語")){
			lang = new Locale("ja", "");
		}
		return lang;
	}

}
