package ieci.tecdoc.idoc.admin.internal;


import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.idoc.admin.api.archive.Archives;
import ieci.tecdoc.idoc.admin.api.archive.BasicArchives;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.ArchiveErrorCodes;
import ieci.tecdoc.idoc.admin.database.ArchivesTable;
import ieci.tecdoc.idoc.admin.database.DirsTable;
import ieci.tecdoc.idoc.core.database.DynamicFns;
import ieci.tecdoc.idoc.core.database.DynamicRow;
import ieci.tecdoc.idoc.core.database.DynamicRows;
import ieci.tecdoc.idoc.core.database.DynamicTable;
import ieci.tecdoc.sbo.config.CfgMdoConfig;



import org.apache.log4j.Logger;

/**
 * Implementación de la clase Archives. 
 * Maneja la lista de archivadores de un directorio invesDoc.
 */
public class ArchivesImpl implements Archives
{
   public ArchivesImpl()
   {      
   }

   public BasicArchives getArchivesByDirectory(int dirId) throws Exception
   {
      BasicArchsImpl archs = null;
      
      if (_logger.isDebugEnabled())
         _logger.debug("getArchivesByDirectory");
      
      try
      {
         DbConnection.open(CfgMdoConfig.getDbConfig());
         
         if (!isValidParentId(dirId))
            AdminException.throwException(ArchiveErrorCodes.EC_PARENT_NO_ID);
         
         archs = loadArchsParentId(dirId);         
         
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
      
      return (BasicArchives)archs;
   }
   
   private BasicArchsImpl loadArchsParentId(int parentId) throws Exception
   {
      BasicArchsImpl archs = new BasicArchsImpl();
      DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      ArchivesTable table = new ArchivesTable();       
      int            counter;
      BasicArchImpl  arch;
      String         qual;
      
      //    Cargamos archivadores

      try
      {
	      qual = table.getLoadArchHdrParentIdQual(parentId);
	      
	      tableInfo.setTableObject(table);	 
	      tableInfo.setClassName(ArchivesTable.class.getName());
	      tableInfo.setTablesMethod("getArchHdrTableName");
	      tableInfo.setColumnsMethod("getIdNameArchHdrColNames");
	      
	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();
	      rowInfo.setClassName(BasicArchImpl.class.getName());
	      rowInfo.setValuesMethod("loadValues");
	      rowsInfo.add(rowInfo);
	      
	      DynamicFns.selectMultiple(qual, false, tableInfo, rowsInfo);
	                                
	      for (counter = 0; counter < rowInfo.getRowCount(); counter++)
	      {
	         arch = (BasicArchImpl)rowInfo.getRow(counter);
	         archs.add(arch);
	      }
	           
	      
	   }
	   catch (Exception e)
		{
	      _logger.error(e);
	      throw e;
		}
	   
	   return archs;
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
   
   private static final Logger _logger = Logger.getLogger(ArchivesImpl.class);
}
