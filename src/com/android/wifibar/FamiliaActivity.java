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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FamiliaActivity extends Activity {

   // Datos miembros
   private String mesa;
   private String camarero;
   private int nLinea;
   private int nComanda;
   private static FamiliaHandler familiasData;
   private GridView grdFamilias;

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.familia);
      if (wifiBarActivity.db.isConnected()) {
         if (wifiBarActivity.db.consultarFamilias("Familias") == -1) {
            Toast.makeText(FamiliaActivity.this, R.string.emptyTable,
                  Toast.LENGTH_LONG).show();
            this.finish();
         }
         // Recoger el bundle con la informacion de comandas
         Bundle bundle = getIntent().getExtras();
        /* this.setCamarero(bundle.getString("camarero"));
         this.setMesa(bundle.getString("mesa"));
         this.setnLinea(bundle.getInt("linea"));
         this.setnComanda(bundle.getInt("Comanda"));
         */
         populateGrid();

      } else {
         Toast.makeText(FamiliaActivity.this, R.string.noConectionActive,
               Toast.LENGTH_LONG).show();
         this.finish();
      }
   }

   private void populateGrid() {
      
      //Rellenos los datos de las familias
      familiasData = wifiBarActivity.db.getFamilias();
      
      ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, familiasData.getDescripcion());

      // definir el gridView
      grdFamilias = (GridView) findViewById(R.id.gridOpciones);
      grdFamilias.setAdapter(adaptador);

      // Evento de pulsacion de una familia del gridView
      grdFamilias.setOnItemClickListener(new OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View v, int position,
               long id) {
            irArticulo(position);
         }
      });
   }

   // PASAR A ARTICULOS
   private void irArticulo(int pos) {
      // Crear el intent
      Intent intento = new Intent(FamiliaActivity.this, ArticuloActivity.class);

      // Creamos un budle para pasar todos los datos
      Bundle bundle = new Bundle();
      // le ponemos el numero de camarero
    /*  bundle.putString("camarero", this.getCamarero());
      intento.putExtras(bundle);
      // le ponemos el numero de mesa
      bundle.putString("mesa", this.getMesa());
      intento.putExtras(bundle);
      // le ponemos el numero de linea
      bundle.putInt("nLinea", this.getnLinea());
      intento.putExtras(bundle);
      // le ponemos el numero de Comanda
      bundle.putInt("nComanda", this.getnComanda());
      intento.putExtras(bundle);*/
      //Valor de la familia seleccionada
      String[] idFamilia = familiasData.getIdFamilia();

      // Coger el item elegido
      bundle.putString("familia", idFamilia[pos]);
      intento.putExtras(bundle);

      startActivity(intento);
   }
/*
   // GETTERS AND SETTERS
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
   /*
    * public String[] getDatos() { return datos; }
    * 
    * public void setDatos(String[] datos) { this.datos = datos; }
    */
}
