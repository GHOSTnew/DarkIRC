/*
 * Project: DarkIRC
 * Package: net.GHOSTnew.DarkIRC.Kernel
 * File: DarkIRC.java
 * Created on: juin 2014
 * =================================================================================================
 * (c) Copyright 2014 ~ Team Mondial
 * 
 * Author: ghostnew
 * E-Mail: GHOSTnew.geek@gmail.com
 * 
 * License: GNU Public License
 * =================================================================================================
 * DarkIRC.java is part of DarkIRC.
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

package net.GHOSTnew.DarkIRC.Kernel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.UnsupportedLookAndFeelException;

import net.GHOSTnew.DarkIRC.DarkIRCmain;
import net.GHOSTnew.DarkIRC.GUI.GUIMain;
import net.GHOSTnew.DarkIRC.GUI.Components.Utils.Ressources;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Channel;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Language;
import net.GHOSTnew.DarkIRC.Kernel.Utils.ObjectDccChat;
import net.GHOSTnew.DarkIRC.Kernel.Utils.Version;

import org.jibble.pircbot.DccFileTransfer;


/**
 * @author GHOSTnew
 */
@SuppressWarnings("serial")
public class DarkIRC extends javax.swing.JFrame {

        // VARIABLES GLOBALES
        public static DarkIRCBot bot; // afin de pouvoir l'appeler plutart dans nos
        // GUI par exemple
        public static Channel currentChan;
        public static List<Channel> channelsList = new ArrayList<Channel>();
        public static DccFileTransfer currentTransfer;
        public static List<File> queulist = new ArrayList<File>();
        public static List<ObjectDccChat> DccChatList = new ArrayList<ObjectDccChat>();
        public static ResourceBundle AppLang;

        // FIN DES VARIABLES GLOBALES

        /**
         * main function :D
         * 
         * @throws UnsupportedLookAndFeelException
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws ClassNotFoundException
         * @throws FileNotFoundException
         * @throws IOException
         */
        public static void run() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileNotFoundException, IOException {
                Locale locale = null; // pour l'internationalisation
                currentChan = new Channel("Status", Channel.STATUS);
                channelsList.add(currentChan);
                bot = new DarkIRCBot();
                // chargement de la configuration
                Properties prop = new Properties();
                InputStream input = null;
                input = new FileInputStream(DarkIRCmain.getConfigFile());
                prop.load(input);
                locale = Language.getLocale(prop.getProperty("langue", "Français"));
                AppLang = ResourceBundle.getBundle("net.GHOSTnew.DarkIRC.GUI.Resources.i18n.darkirc", locale);
                // ici on definis le style de fenetre par defaut
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if (prop.getProperty("style", "Nimbus").equals(info.getName())) {
                                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                break;
                        }
                }
                if (input != null) {
                        input.close();
                }

                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                GUIMain main = new GUIMain();
                                main.setResizable(false);
                                main.setTitle("DarkIRC (v" + Version.getVersion() + ")");
                                main.setIconImage(Ressources.getImage("logo.png"));
                                main.setVisible(true);

                        }
                });
        }
}
