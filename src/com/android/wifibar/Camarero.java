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

public class Camarero {
   // Datos mienbro
   private static String[] nombre;
   private static String[] apellido;
   private static String[] id;
   private static int INDICE_NOM;
   private static int INDICE_APE;
   private static int INDICE_DNI;

   // Constructor
   public Camarero(int tama) {
      INDICE_NOM = tama;
      INDICE_APE = tama;
      INDICE_DNI = tama;
      nombre = new String[tama];
      apellido = new String[tama];
      id = new String[tama];
   }

   // métodos
   public void setNombre(String nom) {
      nombre[--INDICE_NOM] = nom;
   }

   public void setApellido(String ape) {
      apellido[--INDICE_APE] = ape;
   }

   public void setID(String _id) {
      id[--INDICE_DNI] = _id;
   }

   // Gets
   public String[] getNombre() {
      return nombre;
   }

   public String[] getApellido() {
      return apellido;
   }

   public String[] getID() {
      return id;
   }
}