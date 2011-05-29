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

public class FamiliaHandler {
   // Datos mienbro
   private static String[] idFamilia;
   private static String[] descripcion;
   private static int INDICE_ID_FAMILIA;
   private static int INDICE_DESCRIPCION;

   // Constructor
   public FamiliaHandler(int tama) {
      INDICE_ID_FAMILIA = tama;
      INDICE_DESCRIPCION = tama;
      idFamilia = new String[tama];
      descripcion = new String[tama];
   }

   // métodos
   public void setIdFamilia(String fam) {idFamilia[--INDICE_ID_FAMILIA] = fam;}
   public void setDescripcion(String desc) {descripcion[--INDICE_DESCRIPCION] = desc;}

   // Gets
   public String[] getIdFamilia() {return idFamilia;}
   public String[] getDescripcion() {return descripcion;}
}