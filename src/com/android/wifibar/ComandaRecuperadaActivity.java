package com.android.wifibar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ComandaRecuperadaActivity extends Activity {
   
   private static ImageView marcharButton;
   private static ImageView borrarButton;
   public static ComandaHandler comanda;
   private static Bundle bundle;
   private static TableLayout tabla;
   private int contSel;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.comandas_recuperadas);
      
      //Si se recuperan datos
      //if (wifiBarActivity.db.getPedido() != null)
         comanda = wifiBarActivity.db.getPedido();
      
      TextView textCam = (TextView) findViewById(R.id.tvCam);
      TextView textMesa = (TextView) findViewById(R.id.tvMesa);

      textCam.setText("Camarero: " + comanda.getCamareroNom());
      textMesa.setText("Mesa: " + String.valueOf(comanda.getMesa()));
      // iniciar el contador de seleccionados
      this.setContSel(0);
      
      marcharButton = (ImageView) findViewById(R.id.btMarcharRecuperada);
      marcharButton.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            if (comanda.arrLineas.length > 0) {

               AlertDialog.Builder msj = new AlertDialog.Builder(
                     ComandaRecuperadaActivity.this);
               msj.setMessage("¿ Desea ENVIAR la Comanda ?");
               msj.setCancelable(false);

               msj.setPositiveButton("Si",
                     new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                           
                           //TODO: Hacer que al darle a marchar se ejecuten los updates o drop
                           
                           Intent volverAMesa = new Intent(ComandaRecuperadaActivity.this,MesaActivity.class);
                           bundle.putString("camarero",comanda.getCamareroNom());
                           bundle.putInt("camareroId",   comanda.getCamareroId());

                           volverAMesa.putExtras(bundle);
                           comanda = null;
                           startActivity(volverAMesa);
                           finish();
                        }
                     });
               msj.setNegativeButton("No",
                     new DialogInterface.OnClickListener() {// Boton
                        // negativo

                        public void onClick(DialogInterface dialog,
                              int which) {
                           // TODO Auto-generated method stub
                           dialog.cancel();// Se cancela el
                                       // AlertDialog
                        }
                     });
               msj.show();// Se muestra el AlertDialog

            } else {
               AlertDialog.Builder msjNoLin = new AlertDialog.Builder(
                     ComandaRecuperadaActivity.this);
               msjNoLin.setMessage("No existen lineas para enviar. Adjunte lineas antes de enviar la comanada");
               msjNoLin.setCancelable(true);
               msjNoLin.setNeutralButton(" Aceptar ",
                     new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog,
                              int which) {
                           dialog.cancel();
                        }
                     });
               msjNoLin.show();

            }
         }
      });
      
      
      
      borrarButton = (ImageView) findViewById(R.id.btBorrarRecuperada);
      borrarButton.setOnClickListener(new OnClickListener() {
         @Override
         public void onClick(View v) {
            if (ComandaRecuperadaActivity.this.getContSel() > 0) {
               AlertDialog.Builder msjSel = new AlertDialog.Builder(
                     ComandaRecuperadaActivity.this);
               msjSel.setMessage("¿ Desea BORRAR las Lineas Seleccionadas ?");
               msjSel.setCancelable(false);

               msjSel.setPositiveButton("Si",
                     new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                              int which) {

                           if (comanda != null) {
                              comanda.borrarLdComanda();
                              borrarTabla();
                              pintarComanda();
                           }
                        }
                     });
               msjSel.setNegativeButton("No",
                     new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog,
                              int which) {
                           dialog.cancel();
                        }
                     });
               msjSel.show();
            } else {
               AlertDialog.Builder msjNoSel = new AlertDialog.Builder(
                     ComandaRecuperadaActivity.this);
               msjNoSel.setMessage("No hay ninguna linea seleccionada para borrar");
               msjNoSel.setCancelable(true);
               msjNoSel.setNeutralButton(" Aceptar ",
                     new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog,
                              int which) {
                           dialog.cancel();
                        }
                     });
               msjNoSel.show();
            }
         }

      });
      
      pintarComanda();
   }
   
   private void borrarTabla() {
      tabla = (TableLayout) findViewById(R.id.TablaComanda);
      tabla.removeAllViews();
   }

   
   private void pintarComanda() {

      tabla = (TableLayout) findViewById(R.id.TablaComanda);

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
                  int indice = Integer.parseInt((String) buttonView
                        .getText()) - 1;
                  comanda.arrLineas[indice].setBorrar("S");
                  ComandaRecuperadaActivity.this.setContSel(ComandaRecuperadaActivity.this
                        .getContSel() + 1);
               } else {
                  int indice = Integer.parseInt((String) buttonView
                        .getText()) - 1;
                  comanda.arrLineas[indice].setBorrar("N");
                  ComandaRecuperadaActivity.this.setContSel(ComandaRecuperadaActivity.this
                        .getContSel() - 1);
               }
            }

         });
         row.addView(ck);
         // TEXTVIEW
         TextView txt = new TextView(this);
         txt.setText(array[i].getArticuloDesc());
         txt.setWidth(LayoutParams.WRAP_CONTENT);
         txt.setGravity(Gravity.CENTER_HORIZONTAL);
         row.addView(txt);
         // SPINNER CANTIDAD
         EditText cantidadEditText = new EditText(this);
         cantidadEditText.setId(i);
         cantidadEditText.setWidth(90);

         InputFilter[] FilterArray = new InputFilter[1];
         FilterArray[0] = new InputFilter.LengthFilter(2);
         cantidadEditText.setFilters(FilterArray);

         DigitsKeyListener MyDigitKeyListener = new DigitsKeyListener(true,
               true); // first true : is signed, second one : is decimal
         cantidadEditText.setKeyListener(MyDigitKeyListener);

         cantidadEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
         cantidadEditText.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               if ((event.getAction() == KeyEvent.ACTION_DOWN)
                     && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  EditText tv = (EditText) v;
                  comanda.arrLineas[tv.getId()].setCant(Integer
                        .parseInt(tv.getText().toString()));
                  return true;

               } else
                  return false;
            }
         });
         
         cantidadEditText
               .setOnFocusChangeListener(new OnFocusChangeListener() {

                  @Override
                  public void onFocusChange(View v, boolean hasFocus) {
                     EditText tv = (EditText) v;
                     if (hasFocus)
                        tv.setText("");
                  }
               });
         cantidadEditText.setText(Integer.toString(comanda.arrLineas[i]
               .getCant()));

         row.addView(cantidadEditText);

         // ANADIR LA FILA A LA TABLA
         tabla.addView(row);
      }
      if (comanda.getIdLinea() != 0) tabla.findViewById(array.length-1).requestFocus();
   }
   
   public int getContSel() {
      return contSel;
   }

   public void setContSel(int contSel) {
      this.contSel = contSel;
   }
};
