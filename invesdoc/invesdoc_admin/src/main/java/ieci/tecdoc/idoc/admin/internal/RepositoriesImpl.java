package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.volume.Repository;
import ieci.tecdoc.idoc.admin.database.VolumesTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Implementación de la clase Repositories. 
 * Maneja la lista de repositorios de invesDoc.
 */
public class RepositoriesImpl
{
	/**
    * Construye un objeto de la clase.
    *  
    */
	public RepositoriesImpl()
   {
      _list = new ArrayList();
   }

	/**
    * Devuelve el número de repositorios.
    * 
    * @return El número de repositorios mencionado.
    */
   public int count() 
   {
      return _list.size();
   }

   /**
    * Devuelve un repositorio de la lista.
    * 
    * @param index Indice del repositorio que se desea recuperar.
    * 
    * @return El repositorio mencionado.
    */
   public Repository get(int index) throws Exception 
   {
      return (Repository)_list.get(index);
   }
   
   /**
    * Carga la lista de repositorios con su información básica.
    * 
    * @throws Exception Si se produce algún error en la carga de los repositorios.
    */
   public void load() throws Exception
   {
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();
      int counter;
      RepositoryImpl repository;
   
      if (_logger.isDebugEnabled())
         _logger.debug("load");
      
		try
		{
			DbConnection.open(CfgMdoConfig.getDbConfig());

         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getRepositoryTableName");
         tableInfo.setColumnsMethod("getLoadRepAllColumnNames");
         
         rowInfo.setClassName(RepositoryImpl.class.getName());
         rowInfo.setValuesMethod("loadRepAllValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.selectMultiple("", false, tableInfo, 
                                 rowsInfo);
         
         for (counter = 0; counter < rowInfo.getRowCount(); counter++)
         {
         	repository = (RepositoryImpl)rowInfo.getRow(counter);
            add(repository);
         }
         
		}
		catch(Exception e)
		{
         _logger.error(e);
         throw e;
		}
      finally
      {
         DbConnection.close();
      }
        
   }
   
   /**
    * Obtiene la información de la lista de repositorios.
    * 
    * @param header <code>true</code> Incluye la cabecera xml.
    * 				  <code>false</code>  No incluye la cabecera xml.
    * @return La lista de repositorios mencionada.
    */
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Repositories";
      RepositoryImpl repository;
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

      for (int i = 0; i < count(); i++)
      {
      	repository = getImpl(i);
         bdr.addFragment(repository.toXML(false));
      }

      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

	public String toString()
	{
      return toXML(false);
   }

    /**
    * Devuelve un repositorio de la lista.
    * 
    * @param index Indice del repositorio que se desea recuperar.
    * @return El repositorio mencionado.
    */
    
   private RepositoryImpl getImpl(int index)
   {
      return (RepositoryImpl)_list.get(index);  
   }
 
   /**
    * Añade un repositorio a la lista.
    * 
    * @param repository El repositorio mencionado.
    */

   protected void add(RepositoryImpl repository) 
   {
      _list.add(repository);
   }


   private ArrayList _list;
   private static final Logger _logger = Logger.getLogger(RepositoriesImpl.class);
}
