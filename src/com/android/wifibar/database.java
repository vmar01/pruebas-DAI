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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.util.EncodingUtils;

import android.widget.Toast;

public class database {
   private static Camarero datosCamareros;
   private static Mesas datosMesas;
   private java.sql.Connection connection = null;
   private final String url = "jdbc:sqlserver://";
   private final String serverName = "192.168.1.66";
   private final String portNumber = "1433";
   private final String databaseName = "wifiBar_DB";
   private final String userName = "algui91";
   private final String password = "1234";
   private final String statement = "select * from ";
   private static final String COL_CNAME = "cNombre";
   private static final String COL_CAPELLIDO = "cApellidos";
   private static final String COL_IDCAMARERO = "nIdCamarero";
   private static final String COL_IDMESA = "nIdMEsa";
   private static final String COL_NCOMEN = "nComensales";
   private static final String COL_ABIERTA = "cAbierta";

   private final String selectMethod = "direct";

   // Constructor
   public database() {
   }

   private String getConnectionUrl() {
      return url
            + serverName
            + ":"
            + portNumber
            + ";databaseName="
            + databaseName
            + ";user="
            + userName
            + ";password="
            + password
            + ";selectMethod="
            + selectMethod
            + ";integratedSecurity=false;encrypt=false;trustServerCertificate=false;";
   }

   private java.sql.Connection getConnection() {
      try {
         Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver"); // 2000
         // version
         connection = java.sql.DriverManager.getConnection(getConnectionUrl());
         if (connection != null)
            System.out.println("Connection Successful!");
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error Trace in getConnection() : "
               + e.getMessage());
         System.out.println("Connection FAIL!");

      }
      return connection;
   }

   /*
    * Display the driver properties, database details
    */
   public int consultarCamareros(String table) {
      java.sql.ResultSet result = null;
      try {
         connection = this.getConnection();
         if (connection != null) {
            Statement select = connection.createStatement();
            result = select.executeQuery(statement + table + ";");

            // Recojo cuantos registros hay en la tabla
            int count = getRowCount("SELECT COUNT(*) as cont FROM Camareros;");
            // devulevo -1 si la tabla está vacía
            if (count == 0)
               return -1;
            // Creo el objeto del mismo tamaño que el count
            datosCamareros = new Camarero(count);
            while (result.next() && count >= 0) {
               datosCamareros.setNombre(result.getString(COL_CNAME));
               datosCamareros.setApellido(result.getString(COL_CAPELLIDO));
               datosCamareros.setID(result.getString(COL_IDCAMARERO));
            }
            result.close();
            result = null;
            // closeConnection();
            return 0;
         } else
            return -2;
      } catch (Exception e) {
         return -3;
      }
   }

   public boolean updateMesa(int nMesa, String estado){
	   try{
		   //Preparo una consulta parametrizada (más segura)
		   PreparedStatement update = connection.prepareStatement("UPDATE [wifiBar_DB].[dbo].[Mesas] SET [cAbierta] = '"+estado+"' WHERE nIdMesa=?;");
		   update.setInt(1, nMesa);
		   update.executeUpdate();
		   return true;
	   }catch(SQLException e){
		   e.printStackTrace();
		   return false;
	   }
   }
   
   public int consultarMesas(String table) {
      try {
         java.sql.ResultSet result = null;
         Statement select = connection.createStatement();
         result = select.executeQuery(statement + table + ";");

         // Recojo cuantos registros hay en la tabla
         int count = getRowCount("SELECT COUNT(*) as cont FROM " + table + ";");
         // devulevo -1 si la tabla está vacía
         if (count == 0)
            return -1;
         // Creo el objeto del mismo tamaño que el count
         datosMesas = new Mesas(count);
         while (result.next() && count >= 0) {
            datosMesas.setId(result.getString(COL_IDMESA));
            datosMesas.setAbierta(result.getString(COL_ABIERTA)
                  .equalsIgnoreCase("s") ? true : false);
            datosMesas.setComensales(result.getString(COL_NCOMEN));
         }
         result.close();
         result = null;
         return 0;
      } catch (Exception e) {
         return -2;
      }
   }

   private int getRowCount(String tableName) {
      java.sql.ResultSet rs = null;
      try {
         Statement select = connection.createStatement();
         rs = select.executeQuery(tableName);
         rs.next();
         int i = rs.getInt("cont");
         rs.close();
         rs = null;
         return i; // devuelvo el numero de columnas
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1;
   }

   // Devolver los datos de la BD
   public Camarero getCamareros() {
      return datosCamareros;
   }

   public Mesas getMesas() {
      return datosMesas;
   }

   public boolean isConnected() {
      return connection != null ? true : false;
   }

   public void closeConnection() {
      try {
         if (connection != null)
            connection.close();
         connection = null;
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}