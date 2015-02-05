
package ieci.tecdoc.idoc.core.database;

import ieci.tecdoc.core.db.DbSelectStatement;
import ieci.tecdoc.core.db.DbInsertStatement;
import ieci.tecdoc.core.db.DbUpdateStatement;
import ieci.tecdoc.core.miscelanea.ClassLoader;

import org.apache.log4j.Logger;

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
    
   public static boolean select(String qual, DynamicTable tableInfo, 
                                DynamicRows rowsInfo) throws Exception
   {
      DbSelectStatement stmt = null;
      String tblNames, colNames;
      int index, counter;
      DynamicRow rowInfo;
      boolean found = true;
      
      try
      {
         tblNames = Invocation.invokeMethod(tableInfo,   
                                            tableInfo.getTablesMethod());
         colNames = Invocation.invokeMethod(tableInfo,
                                            tableInfo.getColumnsMethod());

         stmt = new DbSelectStatement();
         stmt.create(tblNames, colNames, qual, false);
         stmt.execute();

         if (stmt.next())
         {
            index = 1;
            for (counter = 0; counter < rowsInfo.count(); counter++) 
            {
               rowInfo = rowsInfo.get(counter);
               index = Invocation.invokeOutputStatementValuesMethod(rowInfo,
                                                                   stmt, index);
            }
            
         }
         else
         {
            found = false;
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
    
   public static boolean selectMultiple(String qual, boolean distinct, 
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

         stmt = new DbSelectStatement();
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
    * Inserta una fila en una o varias tablas.
    * 
    * @param tableInfo Información de las tablas involucradas.
    * @param rowInfo Información de las filas de las tablas involucradas.
    * @throws Exception Si se produce alguna excepción.
    */
    
   public static void insert(DynamicTable tableInfo, DynamicRows rowsInfo) 
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
            stmt = new DbInsertStatement();
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

   /**
    * Actualiza una o varias filas en una o varias tablas.
    * 
    * @param qual Qual de la búsqueda. 
    * @param tableInfo Información de las tablas involucradas.
    * @param rowInfo Información de las filas de las tablas involucradas.
    * @throws Exception Si se produce alguna excepción.
    */

   public static void update(String qual, DynamicTable tableInfo, 
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
            stmt = new DbUpdateStatement();
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


   private DynamicFns()
   {
   }

   private static final Logger _logger = Logger.getLogger(DynamicFns.class);
   
}