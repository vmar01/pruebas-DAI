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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
   private static CamareroHandler datosCamareros;
   private static MesaHandler datosMesas;
   private static FamiliaHandler datosFamilias;
   private static ArticuloHandler datosArticulos;
   private static ComandaHandler comandaTotal = null;
   private java.sql.Connection connection = null;
   private final String url = "jdbc:sqlserver://";
   private final String serverName = "192.168.1.39";
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
   private static final String COL_ID_FAMILIA = "cIdFamilia";
   private static final String COL_NOMBRE_FAMILIA = "cNombre";
   private static final String COL_ID_ARTICULO = "cIdArticulo";
   private static final String COL_NOMBRE_ARTICULO = "cNombre";
   
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
            + ";integratedSecurity=true;encrypt=true;trustServerCertificate=false;";
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
            datosCamareros = new CamareroHandler(count);
            while (result.next() && count >= 0) {
               datosCamareros.setNombre(result.getString(COL_CNAME));
               datosCamareros.setApellido(result.getString(COL_CAPELLIDO));
               datosCamareros.setID(Integer.parseInt(result.getString(COL_IDCAMARERO)));
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
   
   public int consultarFamilias(String table) {
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
         datosFamilias = new FamiliaHandler(count);
         while (result.next() && count >= 0) {
            datosFamilias.setIdFamilia(result.getString(COL_ID_FAMILIA));
            datosFamilias.setDescripcion(result.getString(COL_NOMBRE_FAMILIA));
         }
         result.close();
         result = null;
         return 0;
      } catch (Exception e) {
         return -2;
      }
   }
 
   public int consultarArticulos(String table, String familia) {
      try {
         java.sql.ResultSet result = null;
         PreparedStatement select = connection.prepareStatement("SELECT * FROM [wifiBar_DB].[dbo].[Articulos] WHERE cIdFamilia=?;");
         select.setString(1, familia);
         result = select.executeQuery();
   
         // Recojo cuantos registros hay en la tabla
         int count = getRowCount("SELECT COUNT(*) as cont FROM wifiBar_DB.dbo."+table+" WHERE cIdFamilia='"+familia+"';");
         // devulevo -1 si la tabla está vacía
         if (count == 0)
            return -1;
         // Creo el objeto del mismo tamaño que el count
         datosArticulos = new ArticuloHandler(count);
         while (result.next() && count >= 0) {
            datosArticulos.setIdArticulo(result.getString(COL_ID_ARTICULO));
            datosArticulos.setNombre(result.getString(COL_NOMBRE_ARTICULO));
         }
         result.close();
         result = null;
         return 0;
      } catch (Exception e) {
         return -2;
      }
   }
//////////////////////////CAMBIO //////////////////////////////////////////
   public int recuperarLineas(int mesa){
      try{
         java.sql.ResultSet result = null;
         PreparedStatement select = connection.prepareStatement("select " +
         		"Camareros.cNombre as nomCama,  LinComanda.nIdLinComanda as nLinea, Comandas.nIdCamarero as camId, " +
         		"Comandas.nIdComanda as idComanda, Comandas.nIdFactura as numFactura, " +
         		"Comandas.nIdMesa as Mesa, LinComanda.cIdArticulo as idArticulo,Articulos.cNombre as NomArticulo, " +
         		"LinComanda.nCantidad as cantArticulo " +
         		"from Comandas join LinComanda on Comandas.nIdComanda=LinComanda.nIdComanda " +
         		"join Articulos on Articulos.cIdArticulo=LinComanda.cIdArticulo " +
         		"join Camareros on Comandas.nIdCamarero=Camareros.nIdCamarero " +
         		"where Comandas.nIdMesa=? order by numFactura;");
         select.setInt(1, mesa);
         result = select.executeQuery();
         comandaTotal = new ComandaHandler();
         //pedidoMesa = new PedidoMesaHandler();
         int i = 0;
         while (result.next()){
            i++;
            LineaComandaHandler linea = new LineaComandaHandler();
            linea.setArticuloDesc(result.getString("NomArticulo"));
            linea.setcArticulo(result.getString("idArticulo"));
            linea.setCant(result.getInt("cantArticulo"));
            linea.setnComanda(result.getInt("idComanda"));
            linea.setnLinea(result.getInt("nLinea"));
            
            comandaTotal.setCamareroId(result.getInt("camId"));
            comandaTotal.setFactura(result.getInt("numFactura"));
            comandaTotal.setMesa(result.getInt("Mesa"));
            comandaTotal.setnComanda(result.getInt("idComanda"));
            comandaTotal.anadirLdComanda(linea);
            comandaTotal.setCamareroNom(result.getString("nomCama"));
         }
         //pedidoMesa.setArrayPedidas(lineas);
         result.close();
         result =null;
         if (i == 0) {comandaTotal=null; return -1;}
         return 1;
      }catch (Exception e) {
         comandaTotal= null;
         return -1;
      }
   }
////////////////////////// fin CAMBIO //////////////////////////////////////////
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
         datosMesas = new MesaHandler(count);
         int i = 0;
         while (result.next()) {
            datosMesas.id[i] = result.getString(COL_IDMESA);
            datosMesas.abierta[i] = result.getString(COL_ABIERTA).equalsIgnoreCase("s") ? true : false;
            datosMesas.comensales[i++] = result.getString(COL_NCOMEN);
         }
         result.close();
         result = null;
         return 0;
      } catch (Exception e) {
         return -2;
      }
   }
   
   public int generaFactura(){
      java.sql.ResultSet rs = null;
      try {
         PreparedStatement maxFact = connection.prepareStatement("select MAX(Facturas.nIdFactura)+1 as factMax from [wifiBar_DB].[dbo].[Facturas];");
         rs = maxFact.executeQuery();
         rs.next();
         int numeroFactura = rs.getInt("factMax");
         rs.close();
         rs= null;
         
         PreparedStatement insert = connection.prepareStatement("INSERT INTO [wifiBar_DB].[dbo].[Facturas] VALUES (?,?,?,GETDATE());");
         insert.setInt(1, numeroFactura);
         insert.setInt(2, 0);
         insert.setString(3, "S");
         insert.execute();
         return  numeroFactura;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return -1;
   }
   
   public void borrarLineas(int linea, int comanda){
      try {
         // Preparo una consulta parametrizada (más segura)
         PreparedStatement delete = connection
               .prepareStatement("DELETE FROM LinComanda WHERE nIdLinComanda = ? AND nIdComanda= ?;");
         delete.setInt(1, linea);
         delete.setInt(2, comanda);
         delete.executeUpdate();
         return;
      } catch (SQLException e) {
         e.printStackTrace();
         return;
      }
   }
   
   public void modificarLineas(int linea, int comanda, int cantidad){
      try{
         //Preparo una consulta parametrizada (más segura)
         PreparedStatement update = connection.prepareStatement("UPDATE LinComanda SET nCantidad = ? WHERE nIdLinComanda = ? AND nIdComanda= ?;");
         update.setInt(1, cantidad);
         update.setInt(2, linea);
         update.setInt(3, comanda);
         update.executeUpdate();
         return;
      }catch(SQLException e){
         e.printStackTrace();
         return;
      }
   }
   
//////////////////////////CAMBIO //////////////////////////////////////////
   public float cerrarMesa(int mesa){
      try{
         CallableStatement procedimientoCerrarMesa = connection.prepareCall("{ call dbo.pr_cerrarMesa(?, ?) }");
         procedimientoCerrarMesa.setInt(1, mesa);
         procedimientoCerrarMesa.registerOutParameter(2, java.sql.Types.FLOAT);
         procedimientoCerrarMesa.execute();
         return procedimientoCerrarMesa.getFloat(2);
      }catch (Exception e) {
         e.printStackTrace();
         return -1;
      }
   }
////////////////////////// FIN CAMBIO //////////////////////////////////////////
   public int generaComanda(int idFactura, int idMesa, int idCamarero){
	   java.sql.ResultSet rs = null;
	   try {
	         PreparedStatement maxFact = connection.prepareStatement("select MAX(Comandas.nIdComanda)+1 as ComaMax from [wifiBar_DB].[dbo].[Comandas];");
	         rs = maxFact.executeQuery();
	         rs.next();
	         int numeroComanda = rs.getInt("ComaMax");
	         rs.close();
	         rs= null;
	         
	         PreparedStatement insert = connection.prepareStatement("INSERT INTO [wifiBar_DB].[dbo].[Comandas] VALUES (?,?,GETDATE(),?,?);");
	         insert.setInt(1, numeroComanda);
	         insert.setInt(2, idFactura);
	         insert.setInt(3, idMesa);
	         insert.setInt(4, idCamarero);
	         insert.execute();
	         return  numeroComanda;
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return -1;
   }

   public int generaLineaComanda(int idLineaComanda, int idComanda, int cantidad, String idArticulo, String servida, String estado){

      try {
         PreparedStatement insert = connection.prepareStatement
         ("INSERT INTO [wifiBar_DB].[dbo].[LinComanda] " +
         		"([nIdLinComanda],[nIdComanda],[nCantidad],[cServida],[cIdArticulo],[cIdEstado]) " +
         		"VALUES (?,?,?,?,?,?);");
         insert.setInt(1, idLineaComanda);
         insert.setInt(2, idComanda);
         insert.setInt(3, cantidad);
         insert.setString(4, servida);
         insert.setString(5, idArticulo);
         insert.setString(6, estado);
         insert.execute();
         return  0;
      } catch (Exception e) {
      e.printStackTrace();}
      return -1;
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

   public int consultarNumFacMesa(int mes) {
      java.sql.ResultSet rs = null;
      try {
         PreparedStatement numFac = connection
               .prepareStatement("select Comandas.nIdFactura as numFac "
                     + "from LinComanda join Comandas on LinComanda.nIdComanda=Comandas.nIdComanda "
                     + "where Comandas.nIdMesa=? group by Comandas.nIdFactura ;");
         numFac.setInt(1, mes);
         rs = numFac.executeQuery();
         rs.next();
         int existFactura = rs.getInt("numFac");
         boolean hayFactura = rs.getBoolean("numFac");

         if (existFactura > 0 && hayFactura) {// devuelve true si hay un numero
                                              // // devuelve false si el
                                              // resultado del Resulset es null
            return existFactura; // si no hay una factura para esa mesa,
                                 // devuelve 0, hay que poner Max(nFac)+1
         } else {
            existFactura = 0; // si hay factura para esa mesa, devuelve numFac

         }

         rs.close();
         rs = null;
         return existFactura;

      } catch (Exception e) {
         e.printStackTrace();
         return 0;
      }
      // return -1; // si hay una excepcion devuelve -1

   }
  ////////////////////////////////// ////////////////////////////////////////////////////////////////////////
   public int conseguirNumFac(int mesa){
      java.sql.ResultSet rs = null;
      try {

             PreparedStatement nFact = connection.prepareStatement("SELECT C.nIdFactura as nFact " +
             		"FROM LinComanda L " +
             		"JOIN Comandas C ON L.nIdComanda = C.nIdComanda " +
             		"WHERE C.nIdMesa = ? " +
             		"GROUP BY C.nIdFactura;");
             nFact.setInt(1,mesa);
             rs = nFact.executeQuery();
             rs.next();
             int fac=rs.getInt("nFact");
             
             rs.close();
             rs=null;
             
             return fac;
        }
         catch (Exception e) {
          return -1;
        }
         
}
   
   // Devolver los datos de la BD
   public CamareroHandler getCamareros() {
      return datosCamareros;
   }

   public MesaHandler getMesas() {
      return datosMesas;
   }
//////////////////////////CAMBIO //////////////////////////////////////////
   public ComandaHandler getPedido(){
      return comandaTotal;
   }
//////////////////////////fin CAMBIO //////////////////////////////////////////
   public FamiliaHandler getFamilias(){
      return datosFamilias;
   }
   
   public ArticuloHandler getArticulos(){
      return datosArticulos;
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