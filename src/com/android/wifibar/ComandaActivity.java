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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ComandaActivity extends Activity {

   // Datos miembros
   public static ComandaHandler comanda;
   private static TableLayout tabla;
   private static ImageView marcharButton;
   private static ImageView addButton;
   private static ImageView borrarButton;
   private static Bundle bundle;
   private int contSel;
  


/** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.comanda);

      if (wifiBarActivity.db.isConnected()) {
         // Poner los atributos a comanda
         bundle = getIntent().getExtras();

         // PONER LOS ATRIBUTOS A COMANDA
         if (comanda == null ){ 
            comanda = new ComandaHandler(Integer.parseInt(bundle.getString("mesa")), bundle.getInt("factura"),bundle.getInt("idComanda") , bundle.getInt("camareroId"), bundle.getString("camarero"));
         }
         
         if (bundle.getInt("idComanda") != 0){
            comanda.setCamareroId(bundle.getInt("camareroId"));
            comanda.setMesa(Integer.parseInt(bundle.getString("mesa")));
            comanda.setnComanda(bundle.getInt("idComanda"));
            comanda.setFactura(bundle.getInt("factura"));
         }
         
         // Capturar los controles y ver los atributos en los TextView
         TextView textCam  = (TextView) findViewById(R.id.tvCam);
         TextView textMesa = (TextView) findViewById(R.id.tvMesa);

         textCam.setText("Camarero: "+ comanda.getCamareroNom());
         textMesa.setText("Mesa: "+ String.valueOf(comanda.getMesa()));
         //iniciar el contador de seleccionados
         this.setContSel(0);
         
         marcharButton = (ImageView) findViewById(R.id.btMarchar);
         marcharButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               if(comanda.arrLineas.length > 0){
               
                AlertDialog.Builder msj = new AlertDialog.Builder(ComandaActivity.this);
                msj.setMessage("¿ Desea ENVIAR la Comanda ?");
                msj.setCancelable(false);
                
                msj.setPositiveButton("Si", new DialogInterface.OnClickListener() { 
               
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < comanda.arrLineas.length; i++)
                            wifiBarActivity.db.generaLineaComanda(i+1,comanda.getnComanda(), comanda.arrLineas[i].getCant(), comanda.arrLineas[i].getcArticulo(), "S", "0");
                         Intent volverAMesa = new Intent(ComandaActivity.this, MesaActivity.class);
                         bundle.putString("camarero", comanda.getCamareroNom());
                         bundle.putInt("camareroId", comanda.getCamareroId());
                         bundle.putInt("factura", comanda.getFactura());
                         volverAMesa.putExtras(bundle);
                         comanda = null;
                         finish();
                         startActivity(volverAMesa);
                     
                    }
                });
                msj.setNegativeButton("No", new DialogInterface.OnClickListener() {//Boton negativo
                    
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();//Se cancela el AlertDialog
                    }
                });
                msj.show();//Se muestra el AlertDialog

             }  
               else{
                AlertDialog.Builder msjNoLin = new AlertDialog.Builder(ComandaActivity.this);
                msjNoLin.setMessage("No existen lineas para enviar. Adjunte lineas antes de enviar la comanada"); 
                msjNoLin.setCancelable(true);
                msjNoLin.setNeutralButton(" Aceptar ", new DialogInterface.OnClickListener() {
                     
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.cancel();
                     }
                 });
                msjNoLin.show();  
                  
                  
               }
            }
         });

         addButton = (ImageView) findViewById(R.id.btAnadir);
         addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               // Creamos el intent
               Intent comanda = new Intent(ComandaActivity.this,
                     FamiliaActivity.class);
               // LLamamiento a la ventana Familia
               startActivity(comanda);
            }
         });
         
         borrarButton = (ImageView) findViewById(R.id.btBorrar);
         borrarButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ComandaActivity.this.getContSel() > 0){
                AlertDialog.Builder msjSel = new AlertDialog.Builder(ComandaActivity.this);
                msjSel.setMessage("¿ Desea BORRAR las Lineas Seleccionadas ?"); 
                msjSel.setCancelable(false);

                msjSel.setPositiveButton("Si", new DialogInterface.OnClickListener() { 
                    public void onClick(DialogInterface dialog, int which) {
                      
                        if (comanda != null){
                            comanda.borrarLdComanda();
                            borrarTabla();
                            pintarComanda(); 
                         }
                    }
                });
                msjSel.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                msjSel.show();
               }
               else{
                   AlertDialog.Builder msjNoSel = new AlertDialog.Builder(ComandaActivity.this);
                   msjNoSel.setMessage("No hay ninguna linea seleccionada para borrar"); 
                   msjNoSel.setCancelable(true);
                   msjNoSel.setNeutralButton(" Aceptar ", new DialogInterface.OnClickListener() {
                         
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.cancel();
                         }
                     });
                   msjNoSel.show();                
               }
            }
            
         });
         
 
         
         // Cuando se ha seleccionado un articulo
         String artRecibido = getIntent().getExtras().getString("articulo");
         if (artRecibido != null) pintarComanda();
         }
         else {
         Toast.makeText(ComandaActivity.this, R.string.noConectionActive, Toast.LENGTH_LONG).show();
         this.finish();
      }
   }
   
   private void borrarTabla(){
      tabla = (TableLayout)findViewById(R.id.TablaComanda);
      tabla.removeAllViews();
   }
   
   private void pintarComanda() {

      tabla = (TableLayout) findViewById(R.id.TablaComanda);
      // cabecera
      TableRow cab = new TableRow(this);
      TextView nlin = new TextView(this);
      nlin.setText("Nº Lin");
      nlin.setWidth(LayoutParams.FILL_PARENT);
      cab.addView(nlin);
      // articulo
      TextView art = new TextView(this);
      art.setText("Articulo");
      art.setWidth(LayoutParams.FILL_PARENT);
      cab.addView(art);
      // Cantidad
      TextView cant = new TextView(this);
      cant.setText("Cant");
      cant.setWidth(25);
      cab.addView(cant);
      // Estado
      TextView est = new TextView(this);
      est.setText("Estado");
      est.setWidth(25);
      cab.addView(est);
      //
      tabla.addView(cab);
      // Bucle

      LineaComandaHandler[] array = comanda.getArrLineas();
     
      for (int i = 0; i < comanda.getIdLinea(); i++) {
         // creacion fila
         TableRow row = new TableRow(this);
         row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
               LayoutParams.WRAP_CONTENT));
         // CHECK BOX
         CheckBox ck = new CheckBox(this);
         ck.setText(String.valueOf(i + 1));
         ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                  boolean isChecked) {
               if (buttonView.isChecked()) { // si el checbox esta pulsado
                  int indice = Integer.parseInt((String) buttonView.getText()) - 1;
                  comanda.arrLineas[indice].setBorrar("S");
                  ComandaActivity.this.setContSel(ComandaActivity.this.getContSel()+1);
               } else {
                  int indice = Integer.parseInt((String) buttonView.getText()) - 1;
                  comanda.arrLineas[indice].setBorrar("N");
                  ComandaActivity.this.setContSel(ComandaActivity.this.getContSel()-1);
               }
            }

         });
         row.addView(ck);
         // TEXTVIEW
         TextView txt = new TextView(this);
         txt.setText(array[i].getArticuloDesc());
         txt.setWidth(100);
         row.addView(txt);
         // SPINNER CANTIDAD
         EditText cantidadEditText = new EditText(this);
         cantidadEditText.setId(i);
         cantidadEditText.setWidth(35);
         
         InputFilter[] FilterArray = new InputFilter[1];
         FilterArray[0] = new InputFilter.LengthFilter(2);
         cantidadEditText.setFilters(FilterArray);
         
         DigitsKeyListener MyDigitKeyListener =  new DigitsKeyListener(true, true); // first true : is signed, second one : is decimal
         cantidadEditText.setKeyListener( MyDigitKeyListener );
         
         cantidadEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
         cantidadEditText.setOnKeyListener(new OnKeyListener() {
       
         @Override
         public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
               EditText tv = (EditText)v;
               comanda.arrLineas[tv.getId()].setCant(Integer.parseInt(tv.getText().toString()));
               return true;
            }else
            return false;
         }
      });
         cantidadEditText.setText(Integer.toString(comanda.arrLineas[i].getCant()));
         
         row.addView(cantidadEditText);

         // BOTON ESTADO
         Button bt = new Button(this);
         bt.setWidth(70);
         bt.setId(i);
         row.addView(bt);
         
         bt.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
            // TODO Auto-generated method stub
            Button boton=(Button)v;
            Toast.makeText(ComandaActivity.this,"click : "+ boton.getId() , Toast.LENGTH_SHORT).show();
            
         }
       });

         // ANADIR LA FILA A LA TABLA
         tabla.addView(row);
      }
   }


   // Evento de pulsar el boton atras
   protected void onRestart() {
      super.onRestart();
      Toast.makeText(ComandaActivity.this,
            "onRestart: No se ha ejecutado ninguna acciÃ³n", Toast.LENGTH_LONG)
            .show();
   }
   public int getContSel() {
      return contSel;
   }

   public void setContSel(int contSel) {
      this.contSel = contSel;
   }
}