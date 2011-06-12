package com.android.wifibar;

public class ComandaHandler {

   // Datos miembros
   private int nComanda;
   private int factura;
   // public Date fecha;
   private int mesa;
   private int camareroId;
   private String camareroNom;
   // Para poner la linea
   private int idLinea;
   // Array de Lineas de Articulo
   public LineaComandaHandler[] arrLineas = new LineaComandaHandler[0];

   public ComandaHandler(int mes,/* int nCom, */int camId, String camNom) {
      mesa = mes;
      // nComanda = nCom; // la ponemos al hacer la comanda
      // factura=0; // la ponemos despues
      idLinea = 0;
      camareroId = camId;
      camareroNom = camNom;
   }

   public ComandaHandler() {
   }

   // Añadir linea de Comanda
   public void anadirLdComanda(LineaComandaHandler ldc) {

      if (arrLineas != null) {
         LineaComandaHandler[] arrayAumentado = new LineaComandaHandler[arrLineas.length + 1];
         System.arraycopy(arrLineas, 0, arrayAumentado, 0, arrLineas.length);
         arrayAumentado[arrayAumentado.length - 1] = ldc;
         this.setIdLinea(this.idLinea + 1);
         this.setArrLineas(arrayAumentado);
      }
   }

   public LineaComandaHandler[] getLineasAModificar() {
      int z = 0;
      for (int i = 0; i < this.arrLineas.length; i++)
         if (this.arrLineas[i].getModificar() == "S")
            z++;

      LineaComandaHandler[] arrayResul = new LineaComandaHandler[z];

      int c = 0;
      for (int i = 0; i < arrLineas.length; i++)
         if (this.arrLineas[i].getModificar() == "S")
            arrayResul[c++] = this.arrLineas[i].getArrayElement();

      return arrayResul;
   }

   public LineaComandaHandler[] getLineasABorrar() {
      int z = 0;
      for (int i = 0; i < this.arrLineas.length; i++)
         if (this.arrLineas[i].getBorrar() == "S")
            z++;

      LineaComandaHandler[] arrayResul = new LineaComandaHandler[z];

      int c = 0;
      for (int i = 0; i < arrLineas.length; i++)
         if (this.arrLineas[i].getBorrar() == "S")
            arrayResul[c++] = this.arrLineas[i].getArrayElement();

      return arrayResul;
   }

   public void borrarLdComanda() {
      int z = 0;
      for (int i = 0; i < this.arrLineas.length; i++)
         if (this.arrLineas[i].getBorrar() == "N")
            z++;

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

   public int getCamareroId() {
      return camareroId;
   }

   public void setCamareroId(int camareroId) {
      this.camareroId = camareroId;
   }

   public String getCamareroNom() {
      return camareroNom;
   }

   public void setCamareroNom(String camareroNom) {
      this.camareroNom = camareroNom;
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