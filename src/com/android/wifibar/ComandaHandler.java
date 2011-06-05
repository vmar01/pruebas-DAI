package com.android.wifibar;

import java.util.concurrent.CopyOnWriteArraySet;

public class ComandaHandler {

   // Datos miembros
   private int nComanda;
   private int factura;
   // public Date fecha;
   private int mesa;
   private int camarero;
   // Para poner la linea
   private int idLinea;
   // Array de Lineas de Articulo
   public LineaComandaHandler[] arrLineas = new LineaComandaHandler[0];

   public ComandaHandler() {
      mesa = 0; 
      nComanda = 0; 
      factura = 0; 
      idLinea = 0; 
   }

   // Añadir linea de Comanda
   public void anadirLdComanda(LineaComandaHandler ldc) {
         
         if (arrLineas != null ){
            LineaComandaHandler[] arrayAumentado = new LineaComandaHandler[arrLineas.length+1];
            System.arraycopy(arrLineas, 0, arrayAumentado, 0, arrLineas.length);
            arrayAumentado[arrayAumentado.length-1] = ldc;
            this.setIdLinea(this.idLinea + 1);
            this.setArrLineas(arrayAumentado);
         }
   }

   public void borrarLdComanda() {
      int z = 0;
      for (int i = 0; i < this.arrLineas.length; i++) 
         if (this.arrLineas[i].getBorrar() == "N") z++;
      
      LineaComandaHandler[] arrayResul = new LineaComandaHandler[z];
      
      int c = 0;
      for (int i = 0; i < arrLineas.length; i++) 
         if (this.arrLineas[i].getBorrar() == "N") 
            arrayResul[c++] = this.arrLineas[i].getArrayElement();
  
      this.setIdLinea(z);
      this.setArrLineas(arrayResul);
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
 * import java.sql.Date;
 * 
 * public class ComandaHandler { // Datos mienbro private static String[]
 * idComanda; private static String[] idFactura; private static Date[] dFecha;
 * private static String[] idMesa; private static String[] idCamarero;
 * 
 * private static int INDICE_ID_COMANDA; private static int INDICE_ID_FACTURA;
 * private static int INDICE_FECHA; private static int INDICE_ID_MESA; private
 * static int INDICE_ID_CAMARERO; private static final int MAX_LINE = 50;
 * 
 * 
 * // Constructor public ComandaHandler(int tama) { INDICE_FECHA = tama;
 * INDICE_ID_CAMARERO = tama; INDICE_ID_COMANDA = tama; INDICE_ID_FACTURA =
 * tama; INDICE_ID_MESA = tama; idComanda = new String[tama]; idFactura = new
 * String[tama]; dFecha = new Date[tama]; idMesa = new String[tama]; idCamarero
 * = new String[tama]; }
 * 
 * // sets public void setIdComanda(String _id) { idComanda[--INDICE_ID_COMANDA]
 * = _id; } public void setIdFactura(String factura) {
 * idFactura[--INDICE_ID_FACTURA] = factura; } public void setFecha(Date fecha)
 * { dFecha[--INDICE_FECHA] = fecha; } public void setIdCamarero(String
 * camarero){ idCamarero[--INDICE_ID_CAMARERO] = camarero; } public void
 * setIdMesa(String mesa){ idMesa[--INDICE_ID_MESA] = mesa; }
 * 
 * // Gets public String[] getIdComanda() { return idComanda; } public String[]
 * getIdFactura() { return idFactura; } public Date[] getFecha() { return
 * dFecha; } public String[] getIdCamarero(){ return idCamarero; } public
 * String[] getIdMesa(){ return idMesa; } }
 */