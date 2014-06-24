/*
 * This file is part of DarkIRC.
 *
 *  DarkIRC is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  DarkIRC is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with DarkIRC.  If not, see <http://www.gnu.org/licenses/>
 */

package fr.GHOSTnew.darkirc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class logs {
	public static String readLog(String name){
		String text = "";
		try{
			InputStream flux=new FileInputStream(name); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			while ((ligne=buff.readLine())!=null){
				text += ligne + "\n";
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		return text;
	}
	public static void makeLog(String name, String text){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(name)));
			writer.write(text);
			writer.close();
		 }catch (IOException e)
			{
			e.printStackTrace();
			}
	}
}
