
package es.ieci.tecdoc.isicres.admin.base.dbex;


import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInsertStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbUpdateStatement;
import es.ieci.tecdoc.isicres.admin.base.miscelanea.ClassLoader;

public class DynamicFns 
{
   /**
    * Selecciona una fila de una o varias tablas.
    * 
    * @param qual Qual de la búsqueda.
    * @param tableInfo Información de las tablas involucradas.
    * @param rowsInfo Información de las filas de las tablas involucradas.
    * @return Si se ha encontrado algún elemento o no.
    * @throws java.lang.Exception Si se produce alguna excepción.
    */
    
   public static boolean select(DbConnection connection, String qual, DynamicTable tableInfo, 
                                DynamicRows rowsInfo) throws Exception
   {
      DbSelectStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;
      boolean found = false;
      
      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

         stmt = new DbSelectStatement(connection);
         stmt.create(tblNames, colNames, qual, true);
         stmt.execute();

         if (stmt.next())
         {
            found = true;
            index = 1;
            for (counter = 0; counter < rowsInfo.count(); counter++) 
            {
               rowInfo = rowsInfo.get(counter);
               index = Invocation.invokeOutputStatementValuesMethod(rowInfo,
                                                                   stmt, index);
            }
            
         }

         stmt.release();

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbSelectStatement.ensureRelease(stmt, e);
      }
      
      return found;

   }
   
   /**
    * Selecciona una fila de una o varias tablas.
    * 
    * @param qual Qual de la búsqueda.
    * @param tableInfo Información de las tablas involucradas.
    * @param rowsInfo Información de las filas de las tablas involucradas.
    * @return Si se ha encontrado algún elemento o no.
    * @throws java.lang.Exception Si se produce alguna excepción.
    */
    
   public static boolean select(DbConnection connection, String qual, DynamicTable tableInfo, 
                                DynamicRows rowsInfo, boolean distinct) throws Exception
   {
      DbSelectStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;
      boolean found = false;
      
      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

         stmt = new DbSelectStatement(connection);
         stmt.create(tblNames, colNames, qual, distinct);
         stmt.execute();

         if (stmt.next())
         {
            found = true;
            index = 1;
            for (counter = 0; counter < rowsInfo.count(); counter++) 
            {
               rowInfo = rowsInfo.get(counter);
               index = Invocation.invokeOutputStatementValuesMethod(rowInfo,
                                                                   stmt, index);
            }
            
         }

         stmt.release();

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbSelectStatement.ensureRelease(stmt, e);
      }
      
      return found;

   }

   /**
    * Selecciona una o varias filas de una o varias tablas.
    * 
    * @param qual Qual de la búsqueda.
    * @param tableInfo Información de las tablas involucradas.
    * @param rowsInfo Información de las filas de las tablas involucradas.
    * @return Si se ha encontrado algún elemento o no.
    * @throws java.lang.Exception Si se produce alguna excepción.
    */
    
   public static boolean selectMultiple(DbConnection connection, String qual, boolean distinct, 
                  DynamicTable tableInfo, DynamicRows rowsInfo) throws Exception
   {
      DbSelectStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;
      Object row;
      Class objectClass;
      boolean found = false;
      
      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

         stmt = new DbSelectStatement(connection);
         stmt.create(tblNames, colNames, qual, distinct);
         stmt.execute();

         while (stmt.next())
         {
            found = true;
            index = 1;
            for (counter = 0; counter < rowsInfo.count(); counter++) 
            {
               rowInfo = rowsInfo.get(counter);
               objectClass = ClassLoader.getClass(rowInfo.getClassName());
               row = objectClass.newInstance();
               rowInfo.addRow(row);
               
               index = Invocation.invokeOutputStatementValuesMethod(rowInfo,
                                                                   stmt, index);
            }
         }

         stmt.release();

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbSelectStatement.ensureRelease(stmt, e);
      }
      
      return found;

   }

   /**
    * Selecciona una fila de una o varias tablas.
    * 
    * @param qual Qual de la búsqueda.
    * @param tableInfo Información de las tablas involucradas.
    * @param rowsInfo Información de las filas de las tablas involucradas.
    * @return Si se ha encontrado algún elemento o no.
    * @throws java.lang.Exception Si se produce alguna excepción.
    */
    
   public static boolean selectForUpdate(DbConnection connection, String qual, DynamicTable tableInfo, 
                                DynamicRows rowsInfo) throws Exception
   {
      DbSelectStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;
      boolean found = false;
      
      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

         stmt = new DbSelectStatement(connection);
         stmt.createBlockRows(tblNames, colNames, qual);
         stmt.execute();

         if (stmt.next())
         {
            found = true;
            index = 1;
            for (counter = 0; counter < rowsInfo.count(); counter++) 
            {
               rowInfo = rowsInfo.get(counter);
               index = Invocation.invokeOutputStatementValuesMethod(rowInfo,
                                                                   stmt, index);
            }
            
         }

         stmt.release();

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbSelectStatement.ensureRelease(stmt, e);
      }
      
      return found;
   }

   /**
    * Inserta una fila en una o varias tablas.
    * 
    * @param tableInfo Información de las tablas involucradas.
    * @param rowInfo Información de las filas de las tablas involucradas.
    * @throws Exception Si se produce alguna excepción.
    */
    
   public static void insert(DbConnection connection, DynamicTable tableInfo, DynamicRows rowsInfo) 
                             throws Exception
   {

      DbInsertStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;

      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

       
         
         for (counter = 0; counter < rowsInfo.count(); counter++) 
         {
            index = 1;
            stmt = new DbInsertStatement(connection);
            stmt.create(tblNames, colNames);

            rowInfo = rowsInfo.get(counter);
            index = Invocation.invokeInputStatementValuesMethod(rowInfo, stmt,
                                                                index);
            stmt.execute();
            stmt.release();
         }

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbInsertStatement.ensureRelease(stmt, e);
      }

   }

   public static void insertBlob(DbConnection connection, DynamicTable tableInfo, DynamicRows rowsInfo, int[] blobIndexArr) 
                             throws Exception
   {

      DbInsertStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;

      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

       
         
         for (counter = 0; counter < rowsInfo.count(); counter++) 
         {
            index = 1;
            stmt = new DbInsertStatement(connection);
            stmt.createBlob(tblNames, colNames, blobIndexArr);

            rowInfo = rowsInfo.get(counter);
            index = Invocation.invokeInputStatementValuesMethod(rowInfo, stmt,
                                                                index);
            stmt.execute();
            stmt.release();
         }

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbInsertStatement.ensureRelease(stmt, e);
      }

   }
   
   /**
    * Actualiza una o varias filas en una o varias tablas.
    * 
    * @param qual Qual de la búsqueda. 
    * @param tableInfo Información de las tablas involucradas.
    * @param rowInfo Información de las filas de las tablas involucradas.
    * @throws Exception Si se produce alguna excepción.
    */

   public static void update(DbConnection connection, String qual, DynamicTable tableInfo, 
                             DynamicRows rowsInfo) throws Exception
   {
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;
      DbUpdateStatement stmt = null;

      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

       
         for (counter = 0; counter < rowsInfo.count(); counter++) 
         {
            index = 1;
            stmt = new DbUpdateStatement(connection);
            stmt.create(tblNames, colNames, qual);

            rowInfo = rowsInfo.get(counter);
            index = Invocation.invokeInputStatementValuesMethod(rowInfo, stmt,
                                                                index);

            stmt.execute();
            stmt.release();
         }

      }
      catch (Exception e)
      {
         DbUpdateStatement.ensureRelease(stmt, e);
      }

   }

   public static boolean updateBlob(DbConnection connection, String qual, DynamicTable tableInfo, 
                             DynamicRow rowInfo) throws Exception {

      String tblName, colName;
      int index;
      DbSelectStatement stmt = null;
      boolean found = false;

      try {
         tblName = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colName = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

         stmt = new DbSelectStatement(connection);
         stmt.createBlob(tblName, colName, qual);
         stmt.executeBlob();
         
         if (stmt.next())
         {
            found = true;
            index = 1;
            index = Invocation.invokeOutputStatementValuesMethod(rowInfo,
                                                    stmt, index);
         }
 
         stmt.release();

      }
      catch (Exception e)
      {
         _logger.error(e);
         DbSelectStatement.ensureRelease(stmt, e);
      }
      
      return found;
   }
   

   private DynamicFns()
   {
   }

   private static final Logger _logger = Logger.getLogger(DynamicFns.class);
   
}