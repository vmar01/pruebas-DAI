package com.android.wifibar;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class FilaTabla extends TableRow {

   private static CheckBox ckLinea;
   private static TextView tvDescr;
   private static Spinner spCant;
   private static Spinner spEstado;

   public FilaTabla(Context context, LineaComandaHandler lc) {
      super(context);
      FilaTabla.ckLinea = new CheckBox(getContext());
      FilaTabla.tvDescr = new TextView(getContext());
      FilaTabla.spCant = new Spinner(getContext());
      FilaTabla.spEstado = new Spinner(getContext());

   }
}