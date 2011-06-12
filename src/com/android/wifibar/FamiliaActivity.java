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
         } else
            populateGrid();

      } else {
         Toast.makeText(FamiliaActivity.this, R.string.noConectionActive,
               Toast.LENGTH_LONG).show();
         this.finish();
      }
   }

   private void populateGrid() {

      // Rellenos los datos de las familias
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
      // Valor de la familia seleccionada
      String[] idFamilia = familiasData.getIdFamilia();

      // Coger el item elegido
      bundle.putString("familia", idFamilia[pos]);
      intento.putExtras(bundle);

      startActivity(intento);
   }
}
