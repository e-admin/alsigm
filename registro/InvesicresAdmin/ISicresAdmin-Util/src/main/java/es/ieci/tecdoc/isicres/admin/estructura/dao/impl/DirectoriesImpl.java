package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.database.DirsTable;
import es.ieci.tecdoc.isicres.admin.database.DynamicFns;
import es.ieci.tecdoc.isicres.admin.database.DynamicRow;
import es.ieci.tecdoc.isicres.admin.database.DynamicRows;
import es.ieci.tecdoc.isicres.admin.database.DynamicTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.BasicDirectories;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Directories;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminArchiveKeys;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBasicException;

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
   public BasicDirectories getChildrenFormDirectory(int dirId, String entidad) throws Exception
   {
      BasicDirsImpl dirs = null;

      if (_logger.isDebugEnabled())
         _logger.debug("getChildrensFromDirectory");

      DbConnection dbConn=new DbConnection();
      try
      {
    	  dbConn.open(DBSessionManager.getSession());

         if (!isValidParentId(dirId, entidad))
            ISicresAdminBasicException.throwException(ISicresAdminArchiveKeys.EC_PARENT_NO_ID);

         dirs = loadDirsParentId(dirId);

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

   private static final Logger _logger = Logger.getLogger(DirectoriesImpl.class);
}

