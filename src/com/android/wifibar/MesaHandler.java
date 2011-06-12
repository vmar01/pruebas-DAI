/*
   Copyright (C) 2011
   
   This file is part of WifiBar.

    WifiBar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WifiBar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with WifiBar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.android.wifibar;

public class MesaHandler {
   // Datos mienbro
   public static String[] id;
   public static String[] comensales;
   public static boolean[] abierta;

   // Constructor
   public MesaHandler(int tama) {

      MesaHandler.id = new String[tama];
      MesaHandler.comensales = new String[tama];
      MesaHandler.abierta = new boolean[tama];
   }

   // Gets
   public String[] getId() {return id;}
   public String[] getComensales() {return comensales;}
   public boolean[] getAbierta() {return abierta;}
}
