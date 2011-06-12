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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class wifiBarActivity extends Activity {

	private ListView lvCamarero;
	public static database db = new database();
	private static CamareroHandler camareroData;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//Para comprobar que el wifi esta activo: Comentado porque en el emulador no hay wifi
		// if (comprobarConexion(wifiBarActivity.this)) {

		if (db.consultarCamareros("Camareros") == -1) {
			Toast.makeText(wifiBarActivity.this, R.string.emptyTable,
					Toast.LENGTH_LONG).show();
			this.finish();
		} else if (db.isConnected()) {

			// DAtos de los camareros
			camareroData = db.getCamareros();

			// Relleno el spinner
			ArrayAdapter<String> adapterCamarero = new ArrayAdapter<String>(
					this, android.R.layout.simple_list_item_1,
					camareroData.getApellido());
			
			lvCamarero = (ListView) findViewById(R.id.lvCamarero);
			lvCamarero.setAdapter(adapterCamarero);
			lvCamarero.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent camarero = new Intent(wifiBarActivity.this,
							MesaActivity.class);

					Bundle bundle = new Bundle();
					bundle.putString("camarero",
							camareroData.getNombre()[arg2]);
					bundle.putInt("camareroId",
							camareroData.getID()[arg2]);
					camarero.putExtras(bundle);
					startActivity(camarero);
					
				}
			});
			
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

}