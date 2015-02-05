package ieci.tecdoc.idoc.admin.internal;

import ieci.tecdoc.core.datetime.DatePattern;
import ieci.tecdoc.core.datetime.DateTimeUtil;
import ieci.tecdoc.core.db.DBSessionManager;

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.exception.AdminException;
import ieci.tecdoc.idoc.admin.api.exception.VolumeListErrorCodes;
import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.api.volume.Volumes;
import ieci.tecdoc.idoc.admin.database.VolumesTable;
import ieci.tecdoc.sbo.fss.core.FssDaoListVolTbl;
import ieci.tecdoc.sbo.util.nextid.NextId;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.base.dbex.DbDeleteFns;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;
import ieci.tecdoc.sgm.base.dbex.DbSelectFns;
import ieci.tecdoc.sgm.base.dbex.DbUpdateFns;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.base.dbex.DynamicFns;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.dbex.DynamicRows;
import ieci.tecdoc.sgm.base.dbex.DynamicTable;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Representa una lista de volúmenes
 */
public class VolumeListImpl implements VolumeList
{
	/**
	 * Construye un objeto de la clase por el identificador del usuario.
	 * 
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 */
	public VolumeListImpl(int userConnected)
	{
		init(userConnected);
	}
	
	/**
	 * Construye un objeto de la clase por defecto.
	 */
	public VolumeListImpl()
	{
		init(Defs.NULL_ID);
	}
	
	/**
	 * Carga una lista de volúmenes de invesDoc.
	 * 
	 * @param listId Identificador de la lista.
	 * @throws Exception Si se produce algún error al leer la información de 
	 * la lista.
	 */
	public void load(int listId, String entidad) throws Exception
	{
		 if (_logger.isDebugEnabled())
			_logger
					.debug("load: listId = " + Integer.toString(listId));
		
		 try
		{
			_id = listId;
			loadBase(entidad);
			
			
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
	}
	
	/**
	 * Guarda la lista. Se utiliza tanto para inserciones como para
	 * actualizaciones.
	 * 
	 * @throws Exception Si se produce algún error al guardar. Por ejemplo, 
	 * la lista ya existe.
	 */
	public void store(String entidad) throws Exception
	{
		if (_logger.isDebugEnabled())
			_logger.debug("store");
		
		try
		{		
			checkListExists(entidad);
			checkRemarks();
			
			if (_id == Defs.NULL_ID)
			{
				_id = NextId.generateNextId(Defs.NEXT_ID_TBL_NAME,
						Defs.NEXT_ID_TYPE_LISTVOL, entidad);
				
				insert(entidad);
			} else
			{
				update(entidad);
			}
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} 
	}
	
	/**
	 * Elimina la lista.
	 * 
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void delete(String entidad) throws Exception
	{
		VolumesTable table = new VolumesTable();
		
		if (_logger.isDebugEnabled())
			_logger.debug("delete");
		
		DbConnection dbConn=new DbConnection();  
		try
		{
			dbConn.open(DBSessionManager.getSession(entidad));
			
			checkListArch(entidad);
			DbDeleteFns.delete(dbConn, table.getListTableName(), 
               table.getDeleteListQual(_id));
			DbDeleteFns.delete(dbConn, table.getListVolumeTableName(), 
               table.getDeleteListVolListQual(_id));
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
		finally
		{
			dbConn.close();
		}
		
	}
	/**
	 * Añade un volumen a la lista de volúmenes.
	 * 
	 * @param volId El identificador del volumen.
	 * @throws Exception Si se produce algún error al añadir.
	 */
	public void add(int volId, String entidad) throws Exception
	{
		//VolumesTable table = new VolumesTable();
		int maxSortOrder;
		
		if (_logger.isDebugEnabled())
			_logger.debug("add");
		
		try
		{
			_volId = volId;
					
			checkVolume(entidad);
			maxSortOrder = getMaxSortOrder(entidad);
			_sortOrder = maxSortOrder + 1;
			
			insertListVol(entidad);
			
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}		
	}
	
	/**
	 * Elimina un volumen de la lista de volúmenes.
	 * 
	 * @param volId El identificador del volumen.
	 * @throws Exception Si se produce algún error al eliminar.
	 */
	public void deleteVolume(int volId, String entidad) throws Exception
	{
		VolumesTable table = new VolumesTable();
		
		if (_logger.isDebugEnabled())
			_logger.debug("deleteVolume");
		
		DbConnection dbConn=new DbConnection();  
		try
		{
			_volId = volId;
			
			dbConn.open(DBSessionManager.getSession(entidad));
			
			DbDeleteFns.delete(dbConn, table.getListVolumeTableName(), 
               table.getDeleteListVolQual(_id, _volId));
			_volumes.remove(_volId);
			
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
		finally
		{
			dbConn.close();
		}
	}
	
	public int getVolumeSortOrder(int listId, int volId, String entidad) throws Exception
	{
	   int          sortOrder = 0;
	   VolumesTable table = new VolumesTable();
	   boolean      exists = false;
	   
	   if (_logger.isDebugEnabled())
			_logger.debug("getVolumeSortOrder");
		
	   DbConnection dbConn=new DbConnection();  
		try
		{		
			dbConn.open(DBSessionManager.getSession(entidad));
		
			exists = DbSelectFns.rowExists(dbConn, table.getListVolumeTableName(),table.getDeleteListVolQual(listId,volId));
			if (exists)
			   sortOrder = DbSelectFns.selectLongInteger(dbConn, table.getListVolumeTableName(),
			                                             FssDaoListVolTbl.CD_ORDER.getName(),
                                                      table.getDeleteListVolQual(listId, volId));
		   else
		      AdminException
				.throwException(VolumeListErrorCodes.EC_LISTVOL_NOT_EXIST_REL);
			
			
			  
			
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
		finally
		{
			dbConn.close();
		}
		
		return sortOrder;
	}
	
	public void setVolumeSortOrder(int listId, int volId, int sortOrder, String entidad) throws Exception
	{
	   VolumesTable table = new VolumesTable();
	   boolean     exists = false;
	   
	   if (_logger.isDebugEnabled())
			_logger.debug("setVolumeSortOrder");
		
	   DbConnection dbConn=new DbConnection();  
		try
		{		
			dbConn.open(DBSessionManager.getSession(entidad));
		
			exists = DbSelectFns.rowExists(dbConn, table.getListVolumeTableName(),table.getDeleteListVolQual(listId,volId));
			if (exists)
			   DbUpdateFns.updateLongInteger(dbConn, table.getListVolumeTableName(),FssDaoListVolTbl.CD_ORDER.getName(),
                  sortOrder, table.getDeleteListVolQual(listId, volId));			   			   
		   else
		      AdminException
				.throwException(VolumeListErrorCodes.EC_LISTVOL_NOT_EXIST_REL);
			
			
			  
			
		}
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}
		finally
		{
			dbConn.close();
		}
	}
	
	/**
	 * Obtiene el identificador de la lista de volúmenes
	 * 
	 * @return El identificador
	 */
	public int getId()
	{
	   return _id;
	}
	
	/**
    * Obtiene el nombre de la lista de volúmenes.
    *
    * @return El nombre mencionado.
    */
	public String getName()
	{
		return _name;
	}
	/**
	 * Establece el nombre de la lista de volúmenes.
	 * 
	 * @param name Nombre de la lista.
	 */
	public void setName(String name)
	{
		_name = name;
	}
	
	/**
    * Obtiene los flags de la lista de volúmenes.
    *
    * @return El dato mencionado.
    */
	public int getFlags() 
	{
		return _flags;
	}
	
	/**
	 * Establece los flags de la lista de volúmenes.
	 * @param flags El flags de la lista.
	 * @return Los flags de la lista de volúmenes.
	 */
	public void setFlags(int flags)
	{
		_flags = flags;
	}
	
	/**
    * Obtiene el estado de la lista de volúmenes.
    *
    * @return El dato mencionado.
    */
	public int getState()
	{
		return _state;
	}
	
	/**
	 * Establece el estado de la lista de volúmenes.
	 * 
	 * @param state El estado de la lista.
	 */
	public void setState(int state)
	{
		_state = state;
	}
	
	/**
	 * Obtiene los comentarios de la lista de volúmenes.
	 * 
	 * @return El dato mencionado.
	 */
	public String getRemarks()
	{
		return _remarks;
	}
	
	/**
	 * Establece los comentarios de la lista de volúmenes.
	 * 
	 * @param remarks Los comentarios de la lista.
	 */
	public void setRemarks(String remarks)
	{
		_remarks = remarks;
	}
	
	/**
	 * Obtiene el identificador del usuario que ha creado 
	 * la lista de volúmenes. 
	 * 
	 * @return El identificador mencionado.
	 */
	public int getCreatorId()
   {
      return _creatorId;   
   }
	
	/**
    * Obtiene la fecha de creación de la lista de volúmenes.
    *
    * @return La fecha mencionada.
    */
   public Date getCreationDate()
   {
      return _creationDate;   
   }
   
   /**
    * Obtiene el identificador del usuario que ha actualizado 
    * la lista de volúmenes.
    *
    * @return El identificador mencionado.
    */
   public int getUpdaterId()
   {
      return _updaterId;   
   }
   
   /**
    * Obtiene la fecha de actualización de la lista de volúmenes.
    *
    * @return La fecha mencionada.
    */
   public Date getUpdateDate()
   {
      return _updateDate;   
   }
   
   /**
    * Obtiene una lista de identificadores de volúmenes.
    * No es necesario cargar el objeto antes.
    * 
    * @param id Identificador de la lista de volúmenes.
    * @return La lista mencionada.
    */
   public Volumes getVolumes(int id, String entidad) throws Exception
	{
   	if (_logger.isDebugEnabled())
			_logger.debug("getVolumes");
		try
		{
			_id = id;		
			//la conexión se realiza en loadByVolumeList
			_volumes.loadByVolumeList(_id, entidad);
			
		} 
		catch (Exception e)
		{
			_logger.error(e);
			throw e;
		} 
		
		return _volumes;
   }
   
   /**
	 * Obtiene la información del repositorio en formato XML.
	 * 
	 * @return La información mencionada.
	 */
	public String toXML()
	{
		return toXML(true);
	}

	/**
	 * Muestra una representación de los valores de la clase en formato XML.
	 * 
	 * @return La representación mencionada.
	 */
	public String toString()
	{
		return toXML(false);
	}

	/**
	 * Obtiene la información de la lista de volúmenes en formato XML.
	 * 
	 * @param header Indica si hay que incluir la cabecera xml o no.
	 * @return La información mencionada.
	 */
	public String toXML(boolean header)
	{
		String text;
		XmlTextBuilder bdr;
		String tagName = "VolumeList";
		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();
		bdr.addOpeningTag(tagName);
		bdr.addSimpleElement("Id", Integer.toString(_id));
		bdr.addSimpleElement("Name", _name);
		bdr.addSimpleElement("Flags", Integer.toString(_flags));
		bdr.addSimpleElement("State", Integer.toString(_state));
		bdr.addSimpleElement("Remarks", _remarks);
		bdr.addSimpleElement("CreatorId", Integer.toString(_creatorId));
      bdr.addSimpleElement("CreationDate", DateTimeUtil.getDateTime(
                     _creationDate, DatePattern.XML_TIMESTAMP_PATTERN));
      if (DbDataType.isNullLongInteger(_updaterId))
      {
         text = "";
      }
      else
      {
         text = Integer.toString(_updaterId);
      }
      bdr.addSimpleElement("UpdaterId", text);
      if (DbDataType.isNullDateTime(_updateDate))
      {
         text = "";
      }
      else
      {
         text = DateTimeUtil.getDateTime(_updateDate, 
                                        DatePattern.XML_TIMESTAMP_PATTERN);
      }
      
      bdr.addSimpleElement("UpdateDate", text);
		bdr.addClosingTag(tagName);
		return bdr.getText();
	}
	
   /**
	 * Recupera de la base de datos información asociada a 
	 * la lista de volúmenes.
	 * 
	 * @param statement
	 * @param idx
	 * @return 
	 * @throws java.lang.Exception
	 */
	public Integer loadListValues(DbOutputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadListValues");
		_name = statement.getShortText(index++);
		_flags = statement.getLongInteger(index++);
		_state = statement.getLongInteger(index++);
		_remarks = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
		_updaterId = statement.getLongInteger(index++);
		_updateDate = statement.getDateTime(index++);
		return new Integer(index);
	}
	
	/**
	 * Recupera la información asociada a la lista de volúmenes.
	 * 
	 * @param statement
	 * @param idx
	 * @return 
	 * @throws java.lang.Exception
	 */
	public Integer loadListAllValues(DbOutputStatement statement, Integer idx)
	throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("loadListAllValues");
		_id = statement.getLongInteger(index++);
		_name = statement.getShortText(index++);
		_flags = statement.getLongInteger(index++);
		_state = statement.getLongInteger(index++);
		_remarks = statement.getShortText(index++);
		_creatorId = statement.getLongInteger(index++);
		_creationDate = statement.getDateTime(index++);
		_updaterId = statement.getLongInteger(index++);
		_updateDate = statement.getDateTime(index++);
		return new Integer(index);
	}
	
	/**
	 * Inserta en base de datos información almacenada por esta clase.
	 * 
	 * @param statement
	 * @param idx
	 * @return 
	 * @throws java.lang.Exception
	 */
	public Integer insertListValues(DbInputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertListValues");
		statement.setLongInteger(index++, _id);
		statement.setShortText(index++, _name);
		statement.setLongInteger(index++, _flags);
		statement.setLongInteger(index++, _state);
		statement.setShortText(index++, _remarks);
		_creatorId = _userConnected;
		statement.setLongInteger(index++, _creatorId);
		_creationDate = DbUtil.getCurrentDateTime();
		statement.setDateTime(index++, _creationDate);
		return new Integer(index);
	}
	
	/**
	 * Actualiza en base de datos información almacenada por esta clase.
	 * @param statement
	 * @param idx
	 * @return
	 * @throws Exception
	 */
	public Integer updateListValues(DbInputStatement statement, Integer idx) 
   throws Exception 
   {
      int index = idx.intValue();

      if (_logger.isDebugEnabled())
         _logger.debug("updateListValues");

      statement.setShortText(index++, _name);
      statement.setLongInteger(index++, _flags);
      statement.setLongInteger(index++, _state);
      statement.setShortText(index++, _remarks);
      _updaterId = _userConnected;
      statement.setLongInteger(index++, _updaterId);
      _updateDate = DbUtil.getCurrentDateTime();
      statement.setDateTime(index++, _updateDate);

      return new Integer(index);
   }
	
	/**
	 * Inserta en base de datos información almacenada para la 
	 * tabla de lista de volúmenes.
	 * 
	 * @param statement
	 * @param idx
	 * @return 
	 * @throws java.lang.Exception
	 */
	public Integer insertListVolValues(DbInputStatement statement, Integer idx)
			throws Exception
	{
		int index = idx.intValue();
		if (_logger.isDebugEnabled())
			_logger.debug("insertListVolValues");
		statement.setLongInteger(index++, _id);
		statement.setLongInteger(index++, _volId);
		statement.setLongInteger(index++, _sortOrder);
		return new Integer(index);
	}
   
   /**
	 * Comprueba que la lista de volúmenes tiene distinto nombre
	 * a los que ya existen.
	 * 
	 * @throws Exception Si existe ya un nombre.
	 */
	private void checkListExists(String entidad) throws Exception
	{
		int count;
		VolumesTable table = new VolumesTable();
		
		DbConnection dbConn=new DbConnection();  
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			if (_id == Defs.NULL_ID)
				count = DbSelectFns.selectCount(dbConn, table.getListTableName(), table
						.getCountListNameQual(_name));
			else 
				count = DbSelectFns.selectCount(dbConn, table.getListTableName(), table
						.getCountListNameIdQual(_id, _name));
			if (count > 0)
				AdminException.throwException(VolumeListErrorCodes.EC_LISTVOL_EXIST_NAME);
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally{	
			dbConn.close();
		}
			
	}
	
	/**
	 * Comprueba si el texto del campo Comentario tiene comillas dobles.
	 * 
	 * @throws Exception Si existe dobles comillas.
	 */
	private void checkRemarks() throws Exception
	{
		int index ;
		if (_remarks != null) {
			index = _remarks.indexOf("\"");
			if (index > -1)
				AdminException
				.throwException(VolumeListErrorCodes.EC_LISTVOL_REMARKS_EXIST_QUOTES);
		}
	}
	
	/**
	 * Verifica que una lista no esté asociada a un archivador.
	 * 
	 * @throws Exception Si la lista está asociada a un archivador.
	 */
	private void checkListArch(String entidad) throws Exception
   {
      int count;
      VolumesTable table = new VolumesTable();
      
      DbConnection dbConn=new DbConnection();  
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));
	      count = DbSelectFns.selectCount(dbConn, table.getArchListTableName(), 
	                                      table.getCountArchListQual(_id));
	      if (count > 0)
	      {
	         AdminException.throwException(VolumeListErrorCodes.EC_LISTVOL_ASSOC_ARCH);
	      }
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
      
   }
	
	/**
	 * Obtiene el máximo orden de los volúmenes para añadir un volumen
	 * a la lista de volúmenes.
	 * 
	 * @return El máximo orden de los volúmenes.
	 * @throws Exception Si se produce algún error en la obtención 
	 * de la información.
	 */
	private int getMaxSortOrder(String entidad) throws Exception
	{
		int maxOrder;
		VolumesTable table = new VolumesTable();
		
		DbConnection dbConn=new DbConnection();  
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			maxOrder = DbSelectFns.selectLongInteger(dbConn, table.getListVolumeTableName(),
			table.getMaxOrderColumnName(),table.getLoadListVolQual(_id));
			if (maxOrder < 0)
				maxOrder = 0;
		}catch(Exception e){
			_logger.error(e);
			throw e;
		}finally{	
			dbConn.close();
		}
			
		return maxOrder;
	}
	
	/**
	 * Comprueba que el volumen que vamos añadir a la lista, no esté ya incluido.
	 * 
	 * @throws Exception Si el volumen ya está incluido en la lista.
	 */
	private void checkVolume(String entidad) throws Exception
	{
		int count;
      VolumesTable table = new VolumesTable();
      
      DbConnection dbConn=new DbConnection();  
      try{
      	dbConn.open(DBSessionManager.getSession(entidad));
	      count = DbSelectFns.selectCount(dbConn, table.getListVolumeTableName(), 
	                                      table.getCountVolListQual(_id, _volId));
	      if (count > 0)
	      {
	         AdminException.throwException(VolumeListErrorCodes.EC_LISTVOL_EXIST_VOL);
	      }
      }catch(Exception e){
    		_logger.error(e);
    		throw e;
    	}finally{	
    		dbConn.close();
    	}
	      
	}
	
	/**
	 * Lee la información básica de la lista de volúmenes de invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la lectura de la
	 * información mencionada.
	 */
	private void loadBase(String entidad) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("loadBase");
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getListTableName");
			tableInfo.setColumnsMethod("getLoadListColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeListImpl.class.getName());
			rowInfo.setValuesMethod("loadListValues");
			rowsInfo.add(rowInfo);
			if (!DynamicFns.select(dbConn, table.getLoadListQual(_id), tableInfo,
					rowsInfo))
			{
				AdminException
						.throwException(VolumeListErrorCodes.EC_LISTVOL_NOT_EXIST);
			}
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}
		
	/**
	 * Inserta una nueva lista de volúmenes en invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la inserción 
	 * de la lista.
	 */
	private void insert(String entidad) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		if (_logger.isDebugEnabled())
			_logger.debug("insert");
		
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getListTableName");
			tableInfo.setColumnsMethod("getInsertListColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeListImpl.class.getName());
			rowInfo.setValuesMethod("insertListValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);
			
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}
   
	/**
	 * Actualiza una lista de volúmenes de invesDoc.
	 * 
	 * @throws Exception Si se produce algún error en la actualización de
	 * la lista.
	 */
	private void update(String entidad) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable(); 
      DynamicRows rowsInfo = new DynamicRows();
      DynamicRow rowInfo = new DynamicRow();
      VolumesTable table = new VolumesTable();
      
      if (_logger.isDebugEnabled())
			_logger.debug("update");

      DbConnection dbConn=new DbConnection();
      try{
      	 dbConn.open(DBSessionManager.getSession(entidad));
         tableInfo.setTableObject(table);
         tableInfo.setClassName(VolumesTable.class.getName());
         tableInfo.setTablesMethod("getListTableName");
         tableInfo.setColumnsMethod("getUpdateListColumnNames");
         
         rowInfo.addRow(this);
         rowInfo.setClassName(VolumeListImpl.class.getName());
         rowInfo.setValuesMethod("updateListValues");
         rowsInfo.add(rowInfo);
         
         DynamicFns.update(dbConn, table.getLoadListQual(_id), tableInfo, rowsInfo);
         
      }
      catch (Exception e)
		{
         _logger.error(e);
         throw e;
		}finally{
			dbConn.close();
		}
	}
	
	/**
	 * Inserta un volumen en la lista de volúmenes.
	 * 
	 * @throws Exception Si se produce algún error en la inserción del
	 * volumen a la lista.
	 */
	private void insertListVol(String entidad) throws Exception
	{
		DynamicTable tableInfo = new DynamicTable();
		DynamicRows rowsInfo = new DynamicRows();
		DynamicRow rowInfo = new DynamicRow();
		VolumesTable table = new VolumesTable();
		VolumeImpl volume = new VolumeImpl(entidad);
		
		if (_logger.isDebugEnabled())
			_logger.debug("insertListVol");
		DbConnection dbConn=new DbConnection();
		try{
			dbConn.open(DBSessionManager.getSession(entidad));
			tableInfo.setTableObject(table);
			tableInfo.setClassName(VolumesTable.class.getName());
			tableInfo.setTablesMethod("getListVolumeTableName");
			tableInfo.setColumnsMethod("getInsertListVolColumnNames");
			rowInfo.addRow(this);
			rowInfo.setClassName(VolumeListImpl.class.getName());
			rowInfo.setValuesMethod("insertListVolValues");
			rowsInfo.add(rowInfo);
			DynamicFns.insert(dbConn, tableInfo, rowsInfo);

      	volume.load(_volId, entidad);
         _volumes.add(volume);
			
		} catch (Exception e)
		{
			_logger.error(e);
			throw e;
		}finally{
			dbConn.close();
		}
	}
		
   /**
	 * Inicializa los valores de la lista de volúmenes por defecto.
	 * 
	 * @param userConnected Identificador del usuario conectado en la aplicación
	 * de administración.
	 */
	private void init(int userConnected)
	{
		_userConnected = userConnected;
		_id = Defs.NULL_ID;
		_name = null;
		_flags = Defs.LISTVOL_FLAG_NULL;
		_state = Defs.STATE_DEF;
		_remarks = null;
		_creatorId = userConnected;
		_creationDate = DbDataType.NULL_DATE_TIME;
		_updaterId = DbDataType.NULL_LONG_INTEGER;
      _updateDate = DbDataType.NULL_DATE_TIME;
      _volumes = new Volumes();
	}
   
	private static final Logger _logger = Logger.getLogger(VolumeListImpl.class);
	
	private int _userConnected;
	private int _id;
	private String _name;
	private int _flags;
	private int _state;
	private String _remarks;
   private int _creatorId;
   private Date _creationDate;
   private int _updaterId;
   private Date _updateDate;
   private Volumes _volumes;
   private int _volId;
   private int _sortOrder;
}
