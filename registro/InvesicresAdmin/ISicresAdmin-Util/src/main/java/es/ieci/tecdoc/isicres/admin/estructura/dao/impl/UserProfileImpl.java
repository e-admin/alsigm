
package es.ieci.tecdoc.isicres.admin.estructura.dao.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbSelectFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicFns;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRow;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicRows;
import es.ieci.tecdoc.isicres.admin.base.dbex.DynamicTable;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextBuilder;
import es.ieci.tecdoc.isicres.admin.database.UserProfilesTable;
import es.ieci.tecdoc.isicres.admin.estructura.dao.UserProfile;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;


public class UserProfileImpl implements UserProfile
{
   /**
    * Construye un objeto de la clase.
    *
    */

   public UserProfileImpl()
   {
      _userId = ISicresAdminDefsKeys.NULL_ID;
      _product = ISicresAdminUserDefsKeys.PRODUCT_IDOC;
      _profile = ISicresAdminUserDefsKeys.PROFILE_NONE;
   }

   /**
    * Construye un objeto de la clase.
    *
    * @param userId El identificador del usuario.
    * @param product El identificador del producto.
    * @param profile El perfil asociado al producto.
    */

   public UserProfileImpl(int userId, int product, int profile)
   {
      _userId = userId;
      _product = product;
      _profile = profile;
   }

   public int getUserId()
   {
      return _userId;
   }

   public int getProduct()
   {
      return _product;
   }

   public int getProfile()
   {
      return _profile;
   }

   public void setProfile(int profile)
   {
      _profile = profile;
   }

	public String toString()
	{
      return toXML(false);
   }

   public String toXML(boolean header)
   {
      XmlTextBuilder bdr;
      String tagName = "Profile";

      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);

//      bdr.addSimpleElement("UserId", Integer.toString(_userId));
      bdr.addSimpleElement("ProductId", Integer.toString(_product));
      bdr.addSimpleElement("Profile", Integer.toString(_profile));

      bdr.addClosingTag(tagName);

      return bdr.getText();
   }

   /**
    * Guarda en base de datos información almacenada por esta clase.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer insertValues(DbInputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("insertValues");

      statement.setLongInteger(index++, _userId);
      statement.setLongInteger(index++, _product);
      statement.setLongInteger(index++, _profile);

      return new Integer(index);
   }

   /**
    * Actualiza en base de datos información almacenada por esta clase.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer updateValues(DbInputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateValues");

      statement.setLongInteger(index++, _profile);

      return new Integer(index);
   }

   /**
    * Recupera de la base de datos información asociada al usuario.
    *
    * @param statement
    * @param idx
    * @return
    * @throws java.lang.Exception
    */

   public Integer loadValues(DbOutputStatement statement, Integer idx)
                  throws Exception
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("getValues");

      _product = statement.getShortInteger(index++);
      _profile = statement.getShortInteger(index++);

      return new Integer(index);
   }

   public Integer loadAllValues(DbOutputStatement statement, Integer idx) throws Exception {
		int index = idx.intValue();

		if (_logger.isDebugEnabled())
		_logger.debug("getValues");

		_userId = statement.getShortInteger(index++);
		_product = statement.getShortInteger(index++);
		_profile = statement.getShortInteger(index++);

		return new Integer(index);
	}

   /**
    * Establece el identificador del usuario.
    *
    * @param id El identificador mencionado.
    *
    */

   protected void setId(int id)
   {
      _userId = id;
   }

   /**
    * Establece el identificador del producto al que se asigna el perfil.
    *
    * @param product El identificador mencionado.
    *
    */

   public void setProduct(int product)
   {
      _product = product;
   }

	public void insert(String entidad) throws Exception{
	    DynamicTable tableInfo = new DynamicTable();
	    DynamicRows rowsInfo = new DynamicRows();
	    DynamicRow rowInfo = new DynamicRow();
	    UserProfilesTable table = new UserProfilesTable();

	    DbConnection dbConn=new DbConnection();
	    try{
	    	dbConn.open(DBSessionManager.getSession());
	    	//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");
	    	tableInfo.setTableObject(table);
	    	tableInfo.setClassName(UserProfilesTable.class.getName());
	    	tableInfo.setTablesMethod("getTableName");
	    	tableInfo.setColumnsMethod("getInsertColumnNames");

	    	rowInfo.addRow(this);
	    	rowInfo.setClassName(UserProfileImpl.class.getName());
	    	rowInfo.setValuesMethod("insertValues");
	    	rowsInfo.add(rowInfo);

	    	DynamicFns.insert(dbConn, tableInfo, rowsInfo);

	    }
	    catch (Exception e){
	       throw e;
	    }finally{
	    	dbConn.close();
	    }
	}

	public void load(int idUsuario, int idProfile, String entidad) throws Exception{
		  DynamicTable tableInfo = new DynamicTable();
	      DynamicRows rowsInfo = new DynamicRows();
	      DynamicRow rowInfo = new DynamicRow();
	      UserProfilesTable table = new UserProfilesTable();

	      DbConnection dbConn = new DbConnection();
	      try {
	    	  dbConn.open(DBSessionManager.getSession());
	    	 //dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");
	         tableInfo.setTableObject(table);
	         tableInfo.setClassName(UserProfilesTable.class.getName());
	         tableInfo.setTablesMethod("getTableName");
	         tableInfo.setColumnsMethod("getAllColumnNames");

	         rowInfo.addRow(this);
	         rowInfo.setClassName(UserProfileImpl.class.getName());
	         rowInfo.setValuesMethod("loadAllValues");
	         rowsInfo.add(rowInfo);

	         if (!DynamicFns.select(dbConn, table.getByIdUsuarioIdAplicacionQual(idUsuario, idProfile), tableInfo, rowsInfo)){
	            throw new Exception("El permiso no existe.");
	         }

	      }
	      catch (Exception e)
			{
	         throw e;
			}finally{
				dbConn.close();
			}
	}

	public boolean checkProfilesExists(String entidad) throws Exception{
		int count;
		UserProfilesTable table = new UserProfilesTable();

		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession());
			//dbConn.open("org.postgresql.Driver", "jdbc:postgresql://128.90.111.90/registro_000", "postgres", "postgres");

			count = DbSelectFns.selectCount(dbConn, table.getTableName(),
				                                table.getByIdUsuarioIdAplicacionQual(_userId, _product));
			if (count > 0)
				return true;
			else
				return false;

		}catch(Exception e){
			throw e;
		}finally{
			dbConn.close();
		}

	}

   public void update(String entidad) throws Exception{
	   UserProfilesTable table = new UserProfilesTable();
	   DynamicTable tableInfo = new DynamicTable();
	   DynamicRows rowsInfo;
	   DynamicRow rowInfo;

      if (_logger.isDebugEnabled())
         _logger.debug("update");

      DbConnection dbConn=new DbConnection();
      try{
      	  dbConn.open(DBSessionManager.getSession());
	      tableInfo.setTableObject(table);
	      tableInfo.setClassName(UserProfilesTable.class.getName());
	      tableInfo.setTablesMethod("getTableName");
	      tableInfo.setColumnsMethod("getUpdateColumnNames");

	      rowInfo = new DynamicRow();
	      rowsInfo = new DynamicRows();

	      rowInfo.addRow(this);
	      rowInfo.setClassName(UserProfileImpl.class.getName());
	      rowInfo.setValuesMethod("updateValues");
	      rowsInfo.add(rowInfo);

	      DynamicFns.update(dbConn, table.getByIdUsuarioIdAplicacionQual(_userId, _product), tableInfo,
	                        rowsInfo);
      }catch (Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{
    		dbConn.close();
    	}
   }

   public void store(String entidad) throws Exception{
	   checkProfilesExists(entidad);
	   insert(entidad);
   }


   private int _userId;
   private int _product;
   private int _profile;
   private static final Logger _logger = Logger.getLogger(UserProfileImpl.class);
}