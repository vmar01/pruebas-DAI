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
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ComandaActivity extends Activity {

	// Datos miembros
	public String mesa;
	public String camarero;
	public int nLinea;
	public int nComanda;
	public int camareroId;
	public int factura;
	public static ComandaHandler comanda = new ComandaHandler();
	private static TableLayout tabla;
	private static ImageView marcharButton;
	private static ImageView addButton;
	/** Called when the activity is first created. */
	@Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.comanda);

      if (wifiBarActivity.db.isConnected()) {      
         // Poner los atributos a comanda
         Bundle bundle = getIntent().getExtras();
         this.setCamarero(bundle.getString("camarero"));
         this.setMesa(bundle.getString("mesa"));
         this.setnLinea(bundle.getInt("linea"));
         this.setnComanda(bundle.getInt("Comanda"));
         this.setCamareroId(bundle.getInt("camareroId"));
         
         // Capturar los controles y ver los atributos en los TextView
         TextView textCam = (TextView) findViewById(R.id.tvCam);
         TextView textMesa = (TextView) findViewById(R.id.tvMesa);
         
         marcharButton = (ImageView) findViewById(R.id.btMarchar);
         marcharButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
               //TODO Agregar aquí las líneas de comanda a la tabla comanda
            }
         });
         
         addButton = (ImageView) findViewById(R.id.btAnadir);
         addButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
               /*******************************************************
            	// TODO Ir añadiendo cada artículo a linea de Comanda
                ********************************************************/ 
            	// Creamos el intent
        		Intent comanda = new Intent(ComandaActivity.this, FamiliaActivity.class);
        		// Creamos un budle para pasar todos los datos
        		Bundle bundle = new Bundle();
        		// le ponemos el numero de camarero
        		bundle.putString("camarero", getCamarero());
        		comanda.putExtras(bundle);
        		// le ponemos el numero de mesa
        		bundle.putString("mesa", getMesa());
        		comanda.putExtras(bundle);
        		// le ponemos el numero de linea
        		bundle.putInt("nLinea", getnLinea());
        		comanda.putExtras(bundle);
        		// le ponemos el numero de Comanda
        		bundle.putInt("nComanda", getnComanda());
        		comanda.putExtras(bundle);

        		// LLamamiento a la ventana Familia
        		startActivity(comanda);
            }
         });
         
         textCam.setText(textCam.getText()+bundle.getString("camarero"));
         textMesa.setText(textMesa.getText()+bundle.getString("mesa"));

         // PONER LOS ATRIBUTOS A COMANDA
         comanda.setCamarero(bundle.getInt("camareroId"));
         comanda.setMesa(Integer.parseInt(bundle.getString("mesa")));
         
         //Cuando se ha seleccionado un articulo
         String artRecibido=getIntent().getExtras().getString("articulo");
        if(artRecibido !=null){
            Toast.makeText(ComandaActivity.this, "Vengo de Articulos. Sel:"+artRecibido, Toast.LENGTH_LONG).show();
            Toast.makeText(ComandaActivity.this, "Nº de linea:"+comanda.getIdLinea(), Toast.LENGTH_LONG).show(); 
            
            tabla = (TableLayout)findViewById(R.id.TablaComanda);
            LineaComandaHandler[] array= comanda.getArrLineas();
            for(int i=0;i<comanda.getIdLinea();i++){
            	// creacion fila
	            TableRow row=new TableRow(this);
	            row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	            //CHECK BOX
	            CheckBox ck=new CheckBox(this);
	            ck.setText(String.valueOf(i+1));
	            row.addView(ck);
	            //TEXTVIEW
	            TextView txt = new TextView(this);
	            String[] nombresArticulos = ArticuloActivity.articulosData.getNombre();
	            int num = java.lang.Integer.parseInt(array[i].getcArticulo());
	            txt.setText(nombresArticulos[num]);
	            row.addView(txt);
	            //SPINNER CANTIDAD
	            Spinner sp=new Spinner(this);
	            final String[] datos =
	            new String[]{"1","2","3","4","5"};
	   
	            ArrayAdapter<String> adaptador =
	            new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
	            sp.setAdapter(adaptador);
	            
	            row.addView(sp);
	           
	            //SPINNER ESTADO
	            Button bt=new Button(this);
	            row.addView(bt);
	            
	            //ANADIR LA FILA A LA TABLA
	            tabla.addView(row);
            }
            
        }
      } else {
         Toast.makeText(ComandaActivity.this, R.string.noConectionActive,
               Toast.LENGTH_LONG).show();
         this.finish();
      }
   }

	void añadirLinea(LineaComandaHandler ln) {
		// ldc.
	}

	// Evento de pulsar el boton atras
	protected void onRestart() {
		super.onRestart();
		Toast.makeText(ComandaActivity.this,
            "onRestart: No se ha ejecutado ninguna acción",
            Toast.LENGTH_LONG).show();
	}

	// Getters and Setters
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
	public int getCamareroId() {
		return camareroId;
	}

	public void setCamareroId(int camareroId) {
		this.camareroId = camareroId;
	}

	public static ComandaHandler getComa() {
		return comanda;
	}

	public static void setComa(ComandaHandler coma) {
		ComandaActivity.comanda = coma;
	}
	
	public void setFactura(int fact){
		this.factura = fact;
	}
	public int getFactura(){
		return this.factura;
	}
}
