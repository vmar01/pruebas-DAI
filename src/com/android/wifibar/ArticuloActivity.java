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

public class ArticuloActivity extends Activity {
   // Datos miembros
   public static ArticuloHandler articulosData;
   private GridView grdArticulos;

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.articulo);
      
      if (wifiBarActivity.db.isConnected()) {
         Bundle bundle = getIntent().getExtras();
         if (wifiBarActivity.db.consultarArticulos("Articulos", bundle.getString("familia")) == -1) {
            Toast.makeText(ArticuloActivity.this, R.string.emptyTable,
                  Toast.LENGTH_LONG).show();
            this.finish();
         }
         populateGrid();
      } else {
         Toast.makeText(ArticuloActivity.this, R.string.noConectionActive,
               Toast.LENGTH_LONG).show();
         this.finish();
      }
   }

   private void populateGrid() {
      
      //Rellenos los datos de las familias
      articulosData = wifiBarActivity.db.getArticulos();
      
      ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, articulosData.getNombre());

      // definir el gridView
      grdArticulos = (GridView) findViewById(R.id.gridOpciones);
      grdArticulos.setAdapter(adaptador);

      // Evento de pulsacion de una familia del gridView
      grdArticulos.setOnItemClickListener(new OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View v, int position,
               long id) {
            introArticulo(grdArticulos.getItemAtPosition(position).toString(), position);
         }
      });
   }
   
   private void introArticulo(String art, int indice) {
      // Crear el intent
      Intent intento = new Intent(ArticuloActivity.this, ComandaActivity.class);

      // Creamos un budle para pasar todos los datos
      Bundle bundle = new Bundle();

      // Coger el item elegido
      bundle.putString("articulo", articulosData.getIdArticulo()[indice]);

      //CREAR LA LINEA DE COMANDA
      //TODO: Pasarle a linaComandaHandler el objeto articulosDAta
      LineaComandaHandler lineNew = new LineaComandaHandler(articulosData.getIdArticulo()[indice], articulosData.getNombre()[indice], ComandaActivity.comanda.getnComanda());
      // Agregar esa linea a la comanda
      ComandaActivity.comanda.anadirLdComanda(lineNew);
      intento.putExtras(bundle);
      startActivity(intento);
      //Finish por probar
      //Articulo.this.finish();
   }
}