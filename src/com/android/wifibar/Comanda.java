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
import android.widget.Button;
import android.widget.TextView;

public class Comanda extends Activity {
   private static String mesa;
   private static String camarero;

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.comanda);

      // Poner los atributos a comanda
      Bundle bundle = getIntent().getExtras();
      this.setCamarero(bundle.getString("camele"));
      this.setMesa(bundle.getString("mesa"));

      // Ver los atributos en los TextView
      TextView textCam = (TextView) findViewById(R.id.tvCam);
      TextView textMesa = (TextView) findViewById(R.id.tvMesa);

      textCam.setText("Camarero: " + bundle.getString("camarero"));
      textMesa.setText("Mesa: " + bundle.getString("mesa"));

      // Instanciar la tabla
      // TableLayout tabla=(TableLayout)findViewById(R.id.tableLayout1);
      // TableRow cabecera=(TableRow)findViewById(R.id.tableRow1);
      //
      // tabla.ad
   }

   public String getMesa() {
      return mesa;
   }

   public void setMesa(String mesa) {
      this.mesa = "Mesa " + mesa;
   }

   public String getCamarero() {
      return camarero;
   }

   public void setCamarero(String camarero) {
      this.camarero = "Camarero: " + camarero;
   }

   public void onClick(View v) {
      anadirArticulo((Button) v);
   }

   private void anadirArticulo(Button v) {
      Intent articulo = new Intent(Comanda.this, Familia.class);
      startActivity(articulo);

   }

}
// Comentado de su archivo XML
//<TableRow>
//<CheckBox android:layout_column="0"  android:text="2"></CheckBox>
//<TextView android:text="Fanta Limon"></TextView>
//<Spinner></Spinner>
//<Button></Button>
//</TableRow>
//<TableRow>
//<CheckBox android:text="3"></CheckBox>
//<TextView android:text="Nestea"></TextView>
//<Spinner></Spinner>
//<Button android:text="Mucho Hielo"></Button>
//</TableRow>
