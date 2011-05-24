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

public class Mesas {
   // Datos mienbro
   private static String[] id;
   private static String[] comensales;
   private static boolean[] abierta;
   private static int INDICE_ID;
   private static int INDICE_COMENSALES;
   private static int INDICE_ABIERTA;

   // Constructor
   public Mesas(int tama) {
      INDICE_ID = tama;
      INDICE_COMENSALES = tama;
      INDICE_ABIERTA = tama;
      id = new String[tama];
      comensales = new String[tama];
      abierta = new boolean[tama];
   }

   // métodos
   public void setId(String _id) {
      id[--INDICE_ID] = "Mesa " + _id;
   }

   public void setComensales(String comen) {
      comensales[--INDICE_COMENSALES] = comen;
   }

   public void setAbierta(boolean abie) {
      abierta[--INDICE_ABIERTA] = abie;
   }

   // Gets
   public String[] getId() {
      return id;
   }

   public String[] getComensales() {
      return comensales;
   }

   public boolean[] getAbierta() {
      return abierta;
   }
}
