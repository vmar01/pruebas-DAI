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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MesaActivity extends Activity {
	/** Called when the activity is first created. */
	private String camarero;
	private int camareroId;
	private ListView lvMesa;
	private Button altaMesaButton;
	private Button hacerComandaButton;
	private static MesaHandler mesasData;
	private int updateMesa;
	private String estadoMesa;
	private Bundle paquete;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesa);

		if (wifiBarActivity.db.isConnected()) {
			if (wifiBarActivity.db.consultarMesas("Mesas") == -1) {
				Toast.makeText(MesaActivity.this, R.string.emptyTable,
						Toast.LENGTH_LONG).show();
				this.finish();
			}
			// Rellenar el spinner
			populateSpinner();

			paquete = new Bundle();

			TextView ctlCam = (TextView) findViewById(R.id.tvCamarero);
			paquete = getIntent().getExtras();
			ctlCam.setText(paquete.getString("camarero"));

			altaMesaButton = (Button) findViewById(R.id.btAbrir);
			altaMesaButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int numeroFactura = wifiBarActivity.db.generaFactura();
					if (numeroFactura != -1)
						paquete.putInt("factura", numeroFactura);
					// paquete.putInt("mesa", value)
					onAccionMesa(altaMesaButton);
				}
			});

			hacerComandaButton = (Button) findViewById(R.id.btElegirMesa);
			hacerComandaButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent mesa = new Intent(MesaActivity.this,
							ComandaActivity.class);

					// Pasamos al Activity comanda el camarero elegido
					paquete.putString("camarero", getCamarero());
					paquete.putInt("camareroId", getCamareroId());

					// Pasamos al Activity comanda la mesa elegida
					String mesaEle = new String();
					//final Spinner lvMesa = (ListView) findViewById(R.id.lvMesa);
					// Para coger solo el numero de la cadena Mesa N
					//mesaEle = spMesa.getSelectedItem().toString().substring(5);
					paquete.putString("mesa", mesaEle);

					// CREAR LA INSTANCIA DE COMANDA
					int nComanda = wifiBarActivity.db.generaComanda(
							paquete.getInt("factura"),
							Integer.parseInt(mesaEle),
							paquete.getInt("camareroId"));
					if (nComanda != -1) {
						paquete.putInt("idComanda", nComanda);
						mesa.putExtras(paquete);
						startActivity(mesa);
					} else
						Toast.makeText(MesaActivity.this,
								R.string.noComandaGenerada, Toast.LENGTH_LONG)
								.show();
					// mesa.putExtras(paquete);

				}
			});

			// Para meter el camarero elegido como atributo de la comanda
			this.setCamarero(paquete.getString("camarero"));
			this.setCamareroId(paquete.getInt("camareroId"));

			// Evento spinner
			lvMesa.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					comprobarMesa(arg2);
				}
			});
			
		} else {
			Toast.makeText(MesaActivity.this, R.string.noConectionActive,
					Toast.LENGTH_LONG).show();
			this.finish();
		}

	}

	private void setCamareroId(int id) {
		this.camareroId = id;
	}

	private int getCamareroId() {
		return this.camareroId;
	}

	private void populateSpinner() {
		// DAtos de las mesas
		mesasData = wifiBarActivity.db.getMesas();
		
		MesasAdapter adaptador = new MesasAdapter(this);
		lvMesa = (ListView) findViewById(R.id.lvMesa);
		lvMesa.setAdapter(adaptador);
	}

	class MesasAdapter extends ArrayAdapter {
		Activity context;

		public MesasAdapter(Activity context) {
			super(context, R.layout.lv_mesas, mesasData.getId());
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.lv_mesas, null);

			TextView mesasNom = (TextView) item.findViewById(R.id.lbMesa);
			//mesasNom.setText(informacionMesas[position].getMesa());
			mesasNom.setText(mesasData.getId()[position]);
			
			TextView mesasEstado = (TextView) item.findViewById(R.id.lbEstado);
			//mesasEstado.setText(informacionMesas[position].getEstado());
			mesasEstado.setText(mesasData.getAbierta()[position] ? "Abierta" : "Cerrada");
			return item;
		}
	}

	// Getters y Setters
	public String getCamarero() {
		return camarero;
	}

	public void setCamarero(String camarero) {
		this.camarero = camarero;
	}

	public void onAccionMesa(View v) {
		// posición de la mesa seleccionada
		int seleccionado = lvMesa.getSelectedItemPosition();
		if (wifiBarActivity.db.updateMesa(updateMesa, estadoMesa)) {
			Toast.makeText(MesaActivity.this, R.string.opMesaOk,
					Toast.LENGTH_LONG).show();
			wifiBarActivity.db.consultarMesas("Mesas"); // Actualizo la BD
			populateSpinner(); // Relleno el spinner con los cambios
			comprobarMesa(seleccionado);
		} else
			Toast.makeText(MesaActivity.this, R.string.eAbrirMesa,
					Toast.LENGTH_LONG).show();
		// Vuelvo a colocar la mesa seleccionada en el spinner
		lvMesa.setSelection(seleccionado);

	}

	public void comprobarMesa(int pos) {
		// android.widget.Toast.makeText(Mesa.this,
		// "Selecionada mesa: "+pos,1).show();
		Button bComanda = (Button) findViewById(R.id.btElegirMesa);
		Button bCerrar = (Button) findViewById(R.id.btCerrar);
		Button bAbrir = (Button) findViewById(R.id.btAbrir);
		// Array con el estado de las mesas (Abierta:true, Cerrada:false)
		final boolean[] mesasStates = mesasData.getAbierta();
		final String[] idMesas = mesasData.getId();
		updateMesa = Integer.parseInt(idMesas[pos].substring(5));

		if (mesasStates[pos]) {
			bComanda.setEnabled(true);
			bCerrar.setEnabled(true);
			bAbrir.setEnabled(false);
			// Si se pulsa el boton bCerrar, pasamos a la BD el estado de la
			// mesa a
			// Abierta:N;
			estadoMesa = "N";
		} else {
			bComanda.setEnabled(false);
			bCerrar.setEnabled(false);
			bAbrir.setEnabled(true);
			// Si se pulsa el boton bAbrir, pasamos a la BD el estado de la mesa
			// a
			// Abierta:S;
			estadoMesa = "S";
		}
	}
}