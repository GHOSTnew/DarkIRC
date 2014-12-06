/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC
 * File: DarkIRCmain.java
 * Created on: 4 déc. 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * DarkIRCmain.java is part of DarkIRC.
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

package net.GHOSTnew.DarkIRC;

import java.io.File;
import java.io.IOException;

import javax.swing.UnsupportedLookAndFeelException;

import net.GHOSTnew.DarkIRC.Kernel.DarkIRC;

public class DarkIRCmain {
        /**
         * Cette fonction indique le chemin vers le dossier de configuration (et le crée si
         * nécéssaire) en fonction de l'OS
         * 
         * @return le chemin vers le dossier de configuration
         */
        public static String getConfigDir() {
                String dir = null;
                String os = System.getProperty("os.name").toUpperCase();
                if (os.startsWith("WINDOWS")) {
                        dir = System.getenv("APPDATA") + File.separator + "DarkIRC";
                } else if (os.startsWith("LINUX")) {
                        dir = System.getProperty("user.home") + File.separator + ".DarkIRC";
                } else if (os.startsWith("mac")) {
                        dir = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + ".DarkIRC";
                } else {
                        System.out.println("Error System inconnue");
                        System.exit(1);
                }
                if (!(new File(dir).exists())) {
                        new File(dir).mkdirs();
                }
                return dir;
        }

        /**
         * Cette fonction indique le chemin vers le fichier de configuration (et le crée si
         * nécéssaire)
         * 
         * @return le chemain vers le fichier de configuration
         * @throws IOException
         */
        public static String getConfigFile() throws IOException {
                String file = getConfigDir() + File.separator + "config.properties";
                if (!(new File(file).exists())) {
                        new File(file).createNewFile();
                }
                return file;
        }

        public static void main(String args[]) {
                try {
                        DarkIRC.run();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | IOException e) {
                        e.printStackTrace();
                }
        }
}
