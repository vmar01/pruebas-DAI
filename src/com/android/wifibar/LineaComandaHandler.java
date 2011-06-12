package com.android.wifibar;

public class LineaComandaHandler {
   private int nLinea;
   private int nComanda;
   private int cant;
   private String cArticulo;
   private String servida;
   private String cEstado;
   private String borrar;
   private String modificar;
   private String articuloDesc;

   // constructor
   public LineaComandaHandler(String idArticulo, String articulo,
         int idComanda, int nLineaComanda) {
      nLinea = nLineaComanda;
      nComanda = idComanda;
      cant = 1;
      servida = "S";
      cArticulo = idArticulo;
      setArticuloDesc(articulo);
      cEstado = "0";
      borrar = "N";
      setModificar("N");
   }

   public LineaComandaHandler() {
   }

   public LineaComandaHandler getArrayElement() {
      return this;
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

   public String getBorrar() {
      return borrar;
   }

   public void setBorrar(String borrar) {
      this.borrar = borrar;
   }

   public void setArticuloDesc(String articuloDesc) {
      this.articuloDesc = articuloDesc;
   }

   public String getArticuloDesc() {
      return articuloDesc;
   }

   public void setModificar(String modificar) {
      this.modificar = modificar;
   }

   public String getModificar() {
      return modificar;
   }

}