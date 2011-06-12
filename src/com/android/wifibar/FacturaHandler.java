package com.android.wifibar;

public class FacturaHandler {
   
   private int nFactura;
   private int mesa;
   private float entregado;
   private float total;
   private float devo;
   
   public FacturaHandler(int mes, int fac,float tot){
      mesa=mes;
      nFactura=fac;
      total=tot;
   }
   
   public int getnFactura() {
      return nFactura;
   }
   public void setnFactura(int nFactura) {
      this.nFactura = nFactura;
   }
   public int getMesa() {
      return mesa;
   }
   public void setMesa(int mesa) {
      this.mesa = mesa;
   }
   public float getEntregado() {
      return entregado;
   }
   public void setEntregado(float entregado) {
      this.entregado = entregado;
   }
   public float getTotal() {
      return total;
   }
   public void setTotal(float total) {
      this.total = total;
   }
   public float getDevo() {
      return devo;
   }
   public void setDevo(float devo) {
      this.devo = devo;
   }
   
   

}