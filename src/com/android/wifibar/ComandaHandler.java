package com.android.wifibar;

import java.sql.Date;

public class ComandaHandler {
   // Datos mienbro
   private static String[] idComanda;
   private static String[] idFactura;
   private static Date[] dFecha;
   private static String[] idMesa;
   private static String[] idCamarero;
   
   private static int INDICE_ID_COMANDA;
   private static int INDICE_ID_FACTURA;
   private static int INDICE_FECHA;
   private static int INDICE_ID_MESA;
   private static int INDICE_ID_CAMARERO;
   

   // Constructor
   public ComandaHandler(int tama) {
      INDICE_FECHA = tama;
      INDICE_ID_CAMARERO = tama;
      INDICE_ID_COMANDA = tama;
      INDICE_ID_FACTURA = tama;
      INDICE_ID_MESA = tama;
      idComanda = new String[tama];
      idFactura = new String[tama];
      dFecha = new Date[tama];
      idMesa = new String[tama];
      idCamarero = new String[tama];
   }

   // sets
   public void setIdComanda(String _id) { idComanda[--INDICE_ID_COMANDA] = _id;  }
   public void setIdFactura(String factura) { idFactura[--INDICE_ID_FACTURA] = factura;  }
   public void setFecha(Date fecha) { dFecha[--INDICE_FECHA] = fecha;  }
   public void setIdCamarero(String camarero){ idCamarero[--INDICE_ID_CAMARERO] = camarero;  }
   public void setIdMesa(String mesa){ idMesa[--INDICE_ID_MESA] = mesa; }
   
   // Gets
   public String[] getIdComanda() { return idComanda; }
   public String[] getIdFactura() { return idFactura; }
   public Date[] getFecha() { return dFecha; }
   public String[] getIdCamarero(){ return idCamarero; }
   public String[] getIdMesa(){ return idMesa; }
}
