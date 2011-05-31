package com.android.wifibar;

public class ArticuloHandler {
   // Datos mienbro
   private static String[] idArticulo;
   private static String[] nombre;
   private static int INDICE_ID_ARTICULO;
   private static int INDICE_NOMBRE;
   ///cambio/////

   // Constructor
   public ArticuloHandler(int tama) {
      INDICE_ID_ARTICULO = tama;
      INDICE_NOMBRE = tama;
      idArticulo = new String[tama];
      nombre = new String[tama];
   }

   // sets
   public void setIdArticulo(String art) { idArticulo[--INDICE_ID_ARTICULO] = art;  }
   public void setNombre(String nom) { nombre[--INDICE_NOMBRE] = nom;  }

   // Gets
   public String[] getIdArticulo() { return idArticulo; }
   public String[] getNombre() { return nombre; }
}