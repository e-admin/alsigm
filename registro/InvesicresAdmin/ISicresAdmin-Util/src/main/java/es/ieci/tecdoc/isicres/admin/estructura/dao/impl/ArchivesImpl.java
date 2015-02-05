package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;


import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.database.ArchivesTable;
import es.ieci.tecdoc.isicres.admin.database.DirsTable;
import es.ieci.tecdoc.isicres.admin.database.DynamicFns;
import es.ieci.tecdoc.isicres.admin.database.DynamicRow;
import es.ieci.tecdoc.isicres.admin.database.DynamicRows;
import es.ieci.tecdoc.isicres.admin.database.DynamicTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Archives;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicArchive;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicArchives;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminArchiveKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;

/**
 * Implementación de la clase Archives.
 * Maneja la lista de archivadores de un directorio invesDoc.
 */
public class ArchivesImpl implements Archives
{
   public ArchivesImpl()
   {
   }

   public BasicArchives getArchivesByDirectory(int dirId, String entidad) throws Exception
   {
      BasicArchsImpl archs = null;

      if (_logger.isDebugEnabled())
         _logger.debug("getArchivesByDirectory");

      DbConnection dbConn=new DbConnection();
      try
      {
         dbConn.open(DBSessionManager.getSession());

         if (!isValidParentId(dirId, entidad))
            ISicresAdminBasicException.throwException(ISicresAdminArchiveKeys.EC_PARENT_NO_ID);

         archs = loadArchsParentId(dirId);

      }
      catch(Exception e)
      {
         _logger.error(e);
			throw e;
      }
      finally
		{

			dbConn.close();
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
	         archs.add((BasicArchive)arch);
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
   private boolean isValidParentId(int parentId, String entidad) throws Exception
   {

	 String        tblName, qual = null;
	 boolean       valid = false;
	 int           count = 0;
	 DirsTable table = new DirsTable();

	 DbConnection dbConn=new DbConnection();
	 try{
	 	  dbConn.open(DBSessionManager.getSession());
	      tblName = table.getDirTableName();
	      qual = table.getLoadDirQual(parentId);

	      if (parentId == 0)
	         valid = true;
	      else
	      {
		      count = DbSelectFns.selectCount(dbConn, tblName, qual);
		      if (count > 0)
		         valid = true;
	      }
     }catch (Exception e) {
			_logger.error(e);
			throw e;
	}finally{
		  dbConn.close();
	}

      return valid;

   }

   private BasicArchsImpl _archs;

   private static final Logger _logger = Logger.getLogger(ArchivesImpl.class);
}
