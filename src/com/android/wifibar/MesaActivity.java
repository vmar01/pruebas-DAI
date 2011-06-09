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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MesaActivity extends Activity {
	/** Called when the activity is first created. */
	private String camarero;
	private int camareroId;
	private ListView lvMesa;
	private static MesaHandler mesasData;
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
			populateListView();

			paquete = new Bundle();

			TextView ctlCam = (TextView) findViewById(R.id.tvCamarero);
			paquete = getIntent().getExtras();
			ctlCam.setText("Camarero: "+paquete.getString("camarero"));

			// Para meter el camarero elegido como atributo de la comanda
			this.setCamarero(paquete.getString("camarero"));
			this.setCamareroId(paquete.getInt("camareroId"));
			
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

	private void populateListView() {
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.lv_mesas, null);

			TextView mesasNom = (TextView) item.findViewById(R.id.lbMesa);
			mesasNom.setText(getString(R.string.stringMesa) + " " + mesasData.getId()[position]);
			
			
			final Button comandaButton = (Button) item.findViewById(R.id.hacerComandaBtn);
			comandaButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
               Intent mesa = null;
               mesa = new Intent(MesaActivity.this,
                     ComandaActivity.class);

               // Pasamos al Activity comanda el camarero elegido
               paquete.putString("camarero", getCamarero());
               paquete.putInt("camareroId", getCamareroId());

               // Pasamos al Activity comanda la mesa elegida
               paquete.putString("mesa", mesasData.getId()[position]);

               // CREAR LA INSTANCIA DE COMANDA
               int nComanda = wifiBarActivity.db.generaComanda(
                     paquete.getInt("factura"),
                     Integer.parseInt(mesasData.getId()[position]),
                     paquete.getInt("camareroId"));
               if (nComanda != -1) {
                  paquete.putInt("idComanda", nComanda);
                  mesa.putExtras(paquete);
                  startActivity(mesa);
               } else
                  Toast.makeText(MesaActivity.this,
                        R.string.noComandaGenerada, Toast.LENGTH_LONG)
                        .show();
            }
         });
			
			final Button verLineaButton = (Button) item.findViewById(R.id.verLineaBtn);
			verLineaButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
               Intent recuperaComanda = new Intent(MesaActivity.this, ComandaActivity.class);
               
               // Pasamos al Activity comanda el camarero elegido
               paquete.putString("camarero", getCamarero());
               paquete.putInt("camareroId", getCamareroId());

               // Pasamos al Activity comanda la mesa elegida
               paquete.putString("mesa", mesasData.getId()[position]);
               
               if (wifiBarActivity.db.recuperarLineas(Integer.parseInt(mesasData.getId()[position])) != -1){
                  paquete.putInt("BotonVerLinea", 1);
                  recuperaComanda.putExtras(paquete);
                  startActivity(recuperaComanda);
               }
               else
                  Toast.makeText(MesaActivity.this, R.string.noPedidos,
                        Toast.LENGTH_LONG).show();
            }
         });
			
			if (mesasData.getAbierta()[position]){
			   comandaButton.setVisibility(comandaButton.VISIBLE);
			   verLineaButton.setVisibility(verLineaButton.VISIBLE);
			}else {
			   comandaButton.setVisibility(comandaButton.GONE);
            verLineaButton.setVisibility(verLineaButton.GONE);
			}
         
         ToggleButton estadoToggle = (ToggleButton) item.findViewById(R.id.estadoTb);
         estadoToggle.setChecked(mesasData.getAbierta()[position]);
         estadoToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){ 
                  operacionMesa(position+1, "S");
                  comandaButton.setVisibility(comandaButton.VISIBLE);
                  verLineaButton.setVisibility(verLineaButton.VISIBLE);
                }
               else{ 
                  operacionMesa(position+1, "N");
                  comandaButton.setVisibility(comandaButton.INVISIBLE);
                  verLineaButton.setVisibility(verLineaButton.INVISIBLE);
               }
            }
         });
			
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
	
	private void operacionMesa(int mesa, String estado){
	   int numeroFactura = wifiBarActivity.db.generaFactura();
      if (numeroFactura != -1)
         paquete.putInt("factura", numeroFactura);
      if (wifiBarActivity.db.updateMesa(mesa, estado)){
         Toast.makeText(MesaActivity.this, R.string.opMesaOk,
               Toast.LENGTH_LONG).show();
         wifiBarActivity.db.consultarMesas("Mesas"); // Actualizo la BD
         MesasAdapter adaptador = new MesasAdapter(this);
         lvMesa = (ListView) findViewById(R.id.lvMesa);
         lvMesa.setAdapter(adaptador); //Relleno el LV de nuevo
      } else
         Toast.makeText(MesaActivity.this, R.string.eAbrirMesa,
               Toast.LENGTH_LONG).show();
	}
}