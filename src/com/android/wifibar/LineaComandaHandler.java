package com.android.wifibar;


public class LineaComandaHandler {
   private int nLinea;
   private int nComanda;
   private int cant;
   private String cArticulo;
   private String servida;
   private String cEstado;
   

   // constructor
   public LineaComandaHandler( String articulo){
      nLinea= 0;
      nComanda= 0;
      cant= 1;
      servida= "S";
      cArticulo= articulo;
      cEstado= "0";
   }

   public int getCant() {
      return cant;
   }

   public void setCant(int cant) {
      this.cant = cant;
   }

   public String getServida() {
      return servida;
   }

   public void setServida(String servida) {
      this.servida = servida;
   }

   public String getcArticulo() {
      return cArticulo;
   }

   public void setcArticulo(String cArticulo) {
      this.cArticulo = cArticulo;
   }

   public String getcEstado() {
      return cEstado;
   }

   public void setcEstado(String cEstado) {
      this.cEstado = cEstado;
   }

   public int getnLinea() {
      return nLinea;
   }

   public void setnLinea(int nLinea) {
      this.nLinea = nLinea;
   }

   public int getnComanda() {
      return nComanda;
   }

   public void setnComanda(int nComanda) {
      this.nComanda = nComanda;
   }


}