/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel.Utils
 * File: Version.java
 * Created on: 6 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * Version.java is part of DarkIRC.
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

/**
 * Donne la version de l'application
 * 
 * @author ghostnew
 */
public class Version {

        public static int MAJOR = 2;
        public static int MINOR = 0;
        public static int MICRO = 0;
        public static String STATUS = "Beta";

        public static String getVersion() {
                return MAJOR + "." + MINOR + "." + MICRO + "-" + STATUS;
        }
}
