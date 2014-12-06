/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel.Utils
 * File: Language.java
 * Created on: 6 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * Language.java is part of DarkIRC.
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

import java.util.Locale;

public class Language {

        /**
         * 
         * @param language
         * @return le "Locale" de la langue selectionné
         */
        public static Locale getLocale(String language) {
                Locale lang = new Locale("fr", "");
                if (language.equalsIgnoreCase("Français")) {
                        lang = new Locale("fr", "");
                } else if (language.equalsIgnoreCase("English")) {
                        lang = new Locale("en", "");
                } else if (language.equalsIgnoreCase("Español")) {
                        lang = new Locale("es", "");
                } else if (language.equalsIgnoreCase("日本語")) {
                        lang = new Locale("ja", "");
                }
                return lang;
        }

}
