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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class ArticuloActivity extends Activity{
	//Datos miembros
	  
	   public String mesa;
	   public String camarero;
	   public int nLinea;
	   public int nComanda;
	   //solo en esta activity
	   private String familia;
	
	// Prueba para los controles graficos // SE PUEDE BORRAR CUANDO ESTEN LOS DATOS REALES
	private String[] datos = new String[10];
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.articulo);
      
      
      //Recogemos en budle con al informacion de familia
      Bundle bundle = getIntent().getExtras();
      this.setCamarero(bundle.getString("camarero"));
      this.setMesa(bundle.getString("mesa"));
      this.setnLinea(bundle.getInt("linea"));
      this.setnComanda(bundle.getInt("Comanda"));// ¿hay que hacer una peticion a BBDD con el nº de Comanda???????????
      //solo en esta activity
      this.setFamilia(bundle.getString("familia"));
      
      //Crear el adapador   // SE PUEDE BORRAR CUANDO ESTEN LOS DATOS REALES 
      datos[0]="Coca-Cola";    
      datos[1]="Fanta Limon"; 
      datos[2]="Fanta Naranja"; 
      datos[3]="7 up"; 
      datos[4]="Tonica Scheepes"; 
      datos[5]="Coca-Cola Light";
      for(int i=7; i<=10; i++)
              datos[i-1] = "Familia " + i;
      ArrayAdapter<String> adaptador =
              new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
     
      // definir el gridView 
      final GridView grdOpciones = (GridView)findViewById(R.id.gridOpciones);
      grdOpciones.setAdapter(adaptador);
      
      // Evento de pulsacion de una familia del gridView
      grdOpciones.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
              introArticulo(position);
          }
      }); 
   }
     
   private void introArticulo(int pos) {
  	//Crear el intent
      Intent intento = new Intent(ArticuloActivity.this, ComandaActivity.class);

      //Creamos un budle para pasar todos los datos
      Bundle bundle= new Bundle();
      // le ponemos el numero de camarero
      bundle.putString("camarero", this.getCamarero());
      intento.putExtras(bundle);
      // le ponemos el numero de mesa
      bundle.putString("mesa", this.getMesa());
      intento.putExtras(bundle);
      // le ponemos el numero de linea
      bundle.putInt("nLinea", this.getnLinea());
      intento.putExtras(bundle);
      // le ponemos el numero de Comanda
      bundle.putInt("nComanda", this.getnComanda());
      intento.putExtras(bundle);
      
      //Coger el item elegido
      String num= String.valueOf(pos);
      bundle.putString("articulo",num);
      intento.putExtras(bundle);

      startActivity(intento);
      // Finish por probar
      //Articulo.this.finish();
  }


	public String getFamilia() {
		return familia;
	}	
	public void setFamilia(String familia) {
		this.familia = familia;
	}	
	public String getMesa() {
		return mesa;
	}	
	public void setMesa(String mesa) {
		this.mesa = mesa;
	}	
	public String getCamarero() {
		return camarero;
	}	
	public void setCamarero(String camarero) {
		this.camarero = camarero;
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
	public String[] getDatos() {
		return datos;
	}	
	public void setDatos(String[] datos) {
		this.datos = datos;
	}
}