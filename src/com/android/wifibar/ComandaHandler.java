package com.android.wifibar;

public class ComandaHandler {
   // Constante   
   private static final int MAX_LINE = 50;
   
   //Datos miembros
   public int nComanda;
   public int factura;
// public Date fecha;
   public int mesa;
   public int camarero;
   //Para poner la linea
   public int idLinea;
   //Array de Lineas de Articulo
   public LineaComandaHandler[] arrLineas;

   public ComandaHandler(){
      mesa=0;  // se puede poner
      nComanda=0; // hacer consulta BBDD. Max+1 del numero de comandas
      factura=0; //  Max+1 de facturas en DB
      idLinea=0; // para ver cuantas linea tiene esta comanda. Se utiliza para el IdLineaComanda
   // fecha= Date; //poner la fecha hora
      arrLineas= new LineaComandaHandler[MAX_LINE];
   }
   //Añadir linea de Comanda
   public void anadirLdComanda(LineaComandaHandler ldc){
      arrLineas[this.getIdLinea()] = ldc;
      this.setIdLinea(this.idLinea+1);//Al añadir una linea, se aumenta el contador
   }
   
   public void generaFactura(){
      
   }
   
   // GETTERS AND SETTERS
   public int getnComanda() {
      return nComanda;
   }

   public void setnComanda(int nComanda) {
      this.nComanda = nComanda;
   }

   public int getFactura() {
      return factura;
   }

   public void setFactura(int factura) {
      this.factura = factura;
   }

   public int getMesa() {
      return mesa;
   }

   public void setMesa(int mesa) {
      this.mesa = mesa;
   }

   public int getCamarero() {
      return camarero;
   }

   public void setCamarero(int camarero) {
      this.camarero = camarero;
   }
   public int getIdLinea() {
      return idLinea;
   }
   public void setIdLinea(int idLinea) {
      this.idLinea = idLinea;
   }
   public LineaComandaHandler[] getArrLineas() {
      return arrLineas;
   }
   public void setArrLineas(LineaComandaHandler[] arrLineas) {
      this.arrLineas = arrLineas;
   }
   

}

/*
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
   private static final int MAX_LINE = 50;
   

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
*/