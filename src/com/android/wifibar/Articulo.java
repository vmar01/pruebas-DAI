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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class Articulo extends Familia {
	private String familia;
	private String[] datos = new String[10];
	
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.articulo);
      
      // guardamos el id de la familia de la ventana anterior
      Bundle bundle = getIntent().getExtras();
      this.setFamilia(bundle.getString("posSelec"));
      
      //Crear el adapador    
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

  	//Crar el intent
      Intent intento = new Intent(Articulo.this, Comanda.class);
      
      //Coger el item elegido
      String num= String.valueOf(pos);
      Bundle bundle = new Bundle();

      
      bundle.putString("posSelec",num);
      

      intento.putExtras(bundle);
      startActivity(intento);
  }


public String getFamilia() {
	return familia;
}


public void setFamilia(String familia) {
	this.familia = familia;
}

}
//Comentado de su archivo xml

//<Button android:text="Elegir" android:id="@+id/button1"
//	android:onClick="onClick" android:layout_width="wrap_content"
//	android:layout_height="wrap_content"></Button>
//<Button android:layout_width="wrap_content" android:id="@+id/button3"
//	android:text="Atras" android:layout_height="wrap_content"></Button>
//<Button android:text="Cancelar" android:id="@+id/button2"
//	android:layout_width="wrap_content" android:layout_height="wrap_content"></Button>
