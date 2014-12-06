/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel.Utils
 * File: Channel.java
 * Created on: 6 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * Channel.java is part of DarkIRC.
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

public class Channel {

        public String name;
        public String topic;
        public int type;

        // //////// static
        public static final int STATUS = 0;
        public static final int CHANNEL = 1;
        public static final int PRIVATE = 2;
        public static final int DCCCHAT = 3;

        // ////////////////

        /*
         * public Channel(String name) { this(name, "", STATUS); }
         */
        public Channel(String name, int type) {
                this(name, "", type);
        }

        public Channel(String name, String topic, int type) {
                this.name = name;
                this.topic = topic;
                this.type = type;
        }

        public String toString() {
                return "[" + this.type + "]" + this.name;
        }

}
