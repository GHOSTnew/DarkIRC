/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel.Utils
 * File: Logs.java
 * Created on: juin 2014
 * 
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * Logs.java is part of DarkIRC.
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Logs {

        /**
         * lit et retourne le contenu d'un fichier
         * 
         * @param name
         * @return
         */
        public static String readLog(String name) {
                String tmpDir = System.getProperty("java.io.tmpdir") + File.separator + "DarkIRC";
                if (!(new File(tmpDir)).exists()) {
                        (new File(tmpDir)).mkdirs();
                }

                String text = "";
                try {
                        InputStream flux = new FileInputStream(tmpDir + File.separator + name);
                        InputStreamReader lecture = new InputStreamReader(flux);
                        BufferedReader buff = new BufferedReader(lecture);
                        String ligne;
                        while ((ligne = buff.readLine()) != null) {
                                text += ligne + "\n";
                        }
                        buff.close();
                } catch (Exception e) {
                        // System.out.println(e.toString());
                        return "";
                }
                return text;
        }

        /**
         * ecrit dans un fichier
         * 
         * @param name
         * @param text
         */
        public static void makeLog(String name, String text) {
                String tmpDir = System.getProperty("java.io.tmpdir") + File.separator + "DarkIRC";
                if (!(new File(tmpDir)).exists()) {
                        (new File(tmpDir)).mkdirs();
                }

                try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(tmpDir + File.separator + name)));
                        writer.write(text);
                        writer.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
