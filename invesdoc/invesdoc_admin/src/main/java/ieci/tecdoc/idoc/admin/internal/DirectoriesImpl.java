package ieci.tecdoc.idoc.admin.internal;

import org.apache.log4j.Logger;

import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.idoc.admin.api.archive.BasicDirectories;
import ieci.tecdoc.idoc.admin.api.archive.Directories;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;
import ieci.tecdoc.idoc.admin.database.DirsTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;

public class DirectoriesImpl implements Directories
{
   public DirectoriesImpl()
   {      
   }
   
   /**
    * Obtiene información básica de los directorios hijos de un 
    * directorio padre.
    * @param dirId Identificador del directorio padre.
    * @return Estructura con la información mencionada. 
    * @throws Exception Si se produce error al obtener la información.
    */
   public BasicDirectories getChildrenFormDirectory(int dirId) throws Exception
   {
      BasicDirsImpl dirs = null;
      
      if (_logger.isDebugEnabled())
         _logger.debug("getChildrensFromDirectory");
      
      try
      {
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         if (!isValidParentId(dirId))
            AdminException.throwException(ArchiveErrorCodes.EC_PARENT_NO_ID);
         
         dirs = loadDirsParentId(dirId);         
         
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
      
      return (BasicDirectories)dirs;
   }
   
   /**
    * Obtiene información de los directorios hijos.
    * @param parentId Identificador del directorio padre.
    * @return La información mencionada.
    * @throws Exception Errores
    */
   private BasicDirsImpl loadDirsParentId(int parentId) throws Exception
   {
      BasicDirsImpl dirs = new BasicDirsImpl();
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      DirsTable table = new DirsTable();       
      int            counter;
      BasicDirImpl   dir;
      String         qual;
      
      //    Cargamos archivadores

      try
      {
	      qual = table.getLoadDirsParentIdQual(parentId);
	      
	      tableInfo.setTableObject(table);	 
	      tableInfo.setClassName(DirsTable.class.getName());
	      tableInfo.setTablesMethod("getDirTableName");
	      tableInfo.setColumnsMethod("getLiteDirColumnNames");
	      
	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();
	      rowInfo.setClassName(BasicDirImpl.class.getName());
	      rowInfo.setValuesMethod("loadValues");
	      rowsInfo.add(rowInfo);
	      
	      DynamicFns.selectMultiple(qual, false, tableInfo, rowsInfo);
	                                
	      for (counter = 0; counter < rowInfo.getRowCount(); counter++)
	      {
	         dir = (BasicDirImpl)rowInfo.getRow(counter);
	         dirs.add(dir);
	      }
	           
	      
	   }
	   catch (Exception e)
		{
	      _logger.error(e);
	      throw e;
		}
	   
	   return dirs;
   }
   
   /**
    * Comprueba que el identificador del padre corresponde a un 
    * directorio existente
    * 
    * @param parentId Identificador del padre
    * @return true / false
    * @throws Exception Errores
    */
   private boolean isValidParentId(int parentId) throws Exception
   {
      
      String        tblName, qual = null;
      boolean       valid = false;
      int           count = 0; 
      DirsTable table = new DirsTable();
      
      tblName = table.getDirTableName();
      qual = table.getLoadDirQual(parentId);
      
      if (parentId == 0)
         valid = true;
      else
      {      
	      count = DbSelectFns.selectCount(tblName, qual);
	      if (count > 0)
	         valid = true;
      }
      
      return valid;
      
   }
   
   private BasicArchsImpl _archs;
   
   private static final Logger _logger = Logger.getLogger(DirectoriesImpl.class);
}

