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

//import com.gridView.Grid_2;
//import com.gridView.R;
//import com.gridView.gridViewMain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class Familia extends Activity {
	
	private String[] datos = new String[10];
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.familia);
      
      //Crear el adapador    
      datos[0]="Refrescos";    
      datos[1]="Carnes"; 
      datos[2]="Pescados"; 
      datos[3]="Tostadas"; 
      datos[4]="Zumos"; 
      datos[5]="Combinados";
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
              irArticulo(position);
          }
      });
      
   }

//   public void onClick(View v) {
//      irArticulo((Button) v);
//   }

   private void irArticulo(int pos) {
//      Intent articulo = new Intent(Familia.this, Articulo.class);
//      startActivity(articulo);
   	//Crar el intent
       Intent intento = new Intent(Familia.this, Articulo.class);
       
       //Coger el item elegido
       String num= String.valueOf(pos);
       Bundle bundle = new Bundle();
       
       bundle.putString("posSelec",num);
       intento.putExtras(bundle);
       startActivity(intento);
   }

}

// Comentado de su archivo xml

//<Button android:text="Elegir" android:id="@+id/button1"
//	android:onClick="onClick" android:layout_height="wrap_content"
//	android:layout_width="wrap_content"></Button>
//<Button android:text="Atras" android:id="@+id/button3"
//	android:layout_width="wrap_content" android:layout_height="wrap_content"></Button>
//<Button android:text="Cancelar" android:id="@+id/button2"
//	android:layout_width="wrap_content" android:layout_height="wrap_content"></Button>
//
