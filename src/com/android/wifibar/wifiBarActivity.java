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
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class wifiBarActivity extends Activity {

   private Spinner SpiCamarero;
   public static database db = new database();

   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      // if (comprobarConexion(wifiBarActivity.this)) {

      if (db.consultarCamareros("Camareros") == -1) {
         Toast.makeText(wifiBarActivity.this, R.string.emptyTable,
               Toast.LENGTH_LONG).show();
         this.finish();
      } else if (db.isConnected()) {
         // db.closeConnection();
    	  
         Toast.makeText(wifiBarActivity.this, "in onCreate()",
               Toast.LENGTH_SHORT).show();

         // Elementos graficos a usar
         SpiCamarero = (Spinner) findViewById(R.id.SpiCamarero);

         // DAtos de los camareros
         final CamareroHandler camareroData = db.getCamareros();

         // Relleno el spinner
         ArrayAdapter<String> spinnerCamarero = new ArrayAdapter<String>(this,
               android.R.layout.simple_dropdown_item_1line,
               camareroData.getApellido());
         SpiCamarero.setAdapter(spinnerCamarero);

      } else {
         Toast.makeText(wifiBarActivity.this, R.string.noConectionActive,
               Toast.LENGTH_LONG).show();
         this.finish();
      }

      /*
       * } else { Toast.makeText(wifiBarActivity.this, R.string.noWifiActive,
       * Toast.LENGTH_LONG).show(); this.finish();
       */
      // }

   }

   // Para comprobar la conexión wi-Fi
   public static boolean comprobarConexion(Context context) {
      ConnectivityManager connec = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo redWifi = connec
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      if (redWifi.isAvailable()) {
         return true;
      }
      return false;
   }

   // Pasar al siguiente Activity (Mesa)
   public void irMesa(Button v) {
      Intent camarero = new Intent(wifiBarActivity.this, MesaActivity.class);
      String cama = new String();
      final Spinner spiCamarero = (Spinner) findViewById(R.id.SpiCamarero);
      // --cogemos el string del item seleccionado en el sppiner
      cama = spiCamarero.getSelectedItem().toString();
      Bundle bundle = new Bundle();
      bundle.putString("camarero", cama);
      camarero.putExtras(bundle);
      startActivity(camarero);

   }

   // Recoger el evento onClick del boton Introducir
   public void onClick(View v) {
      irMesa((Button) v);
   }

}