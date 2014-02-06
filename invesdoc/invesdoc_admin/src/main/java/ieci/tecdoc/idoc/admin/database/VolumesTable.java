package ieci.tecdoc.idoc.admin.database;

import ieci.tecdoc.sbo.fss.core.FssDaoActDirTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoFileTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoListTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoListVolTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoRepTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoArchListTbl;

import org.apache.log4j.Logger;

public class VolumesTable
{
	private static final Logger _logger = Logger.getLogger(VolumesTable.class);

	public VolumesTable()
	{
	}

	/**
	 * Devuelve el nombre tabla de repositorios (IVOLREPHDR).
	 * 
	 * @return Las tabla mencionada.
	 */
	public String getRepositoryTableName()
	{
		return FssDaoRepTbl.TN;
	}

	/**
	 * Devuelve el nombre tabla de volúmenes (IVOLVOLHDR).
	 * 
	 * @return Las tabla mencionada.
	 */
	public String getVolumeTableName()
	{
		return FssDaoVolTbl.TN;
	}

	/**
	 * Devuelve el nombre tabla de lista de volúmenes (IVOLLISTVOL).
	 * 
	 * @return Las tabla mencionada.
	 */
	public String getListVolumeTableName()
	{
		return FssDaoListVolTbl.TN;
	}
	
	/**
	 * Devuelve el nombre de la tabla de los directorios actuales
	 *  de volumen (IVOLACTDIR).
	 * 
	 * @return Las tabla mencionada.
	 */
	public String getVolActDirTableName()
	{
		return FssDaoActDirTbl.TN;
	}
	
	/**
	 * Devuelve el nombre de la tabla del ficheros de volúmenes (IVOLFILEHDR).
	 * 
	 * @return
	 */
	public String getFileTableName()
	{
		return FssDaoFileTbl.TN;
	}
	/**
	 * Devuelve el nombre de la tabla de listas (IVOLLISTHDR).
	 * 
	 * @return
	 */
	public String getListTableName()
	{
		return FssDaoListTbl.TN;
	}
	
	/**
	 * Devuelve el nombre de la tabla que asocia listas con archivadores (IVOLARCHLIST).
	 * 
	 * @return
	 */
	public String getArchListTableName()
	{
		return DaoArchListTbl.getTblName();
	}
	
	/**
	 * Construye una expresión de búsqueda para la tabla de repositorios a
	 * partir del identificador.
	 * 
	 * @param id El identificador de repositorio.
	 * @return La expresión mencionada.
	 */
	public String getLoadRepositoryQual(int id)
	{
		String qual;
		qual = "WHERE " + getRepositoryTableName() + "."
				+ FssDaoRepTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadRepositoryQual: " + qual);
		return qual;
	}

	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de repositorios que hay con el mismo nombre.
	 * 
	 * @param name El nombre del repositorio.
	 * @return La expresión mencionada.
	 */
	public String getCountRepNameQual(String name)
	{
		String qual;
		qual = "WHERE " + getRepositoryTableName() + "."
				+ FssDaoRepTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountRepNameQual: " + qual);
		return qual;
	}
	
	/**
	 * Contruye una expresión de búsqueda para calcular el número
	 * de repositorios que hay del mismo tipo.
	 * @param type Tipo de repositorio
	 * @return La expresión mencionada
	 */
	public String getCountRepTypeQual(int type)
	{
	   String qual;
	   
	   qual = "WHERE " + getRepositoryTableName() + "." +
	          FssDaoRepTbl.CD_TYPE.getName() + "= " +
	          Integer.toString(type);
	   
	   if(_logger.isDebugEnabled())
	         _logger.debug("getCountRepTypeQual: " + qual);
	   
	   return (qual);
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de repositorios que hay con el mismo nombre y distinto 
	 * identificador de repositorio. 
	 * 
	 * @param repId Identificador del repositorio.
	 * @param name El nombre del repositorio.
	 * @return La expresión mencionada.
	 */
	public String getCountRepNameIdQual(int repId, String name)
	{
		String qual;
		qual = "WHERE " + getRepositoryTableName() + "."
				+ FssDaoRepTbl.CD_NAME.getName() + "= '" + name + "' AND " 
				+ getRepositoryTableName() + "." + FssDaoRepTbl.CD_ID.getName() 
				+ "<>" + Integer.toString(repId);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountRepNameIdQual: " + qual);
		return qual;
	}

	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de repositorios para
	 * su inserción.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getInsertRepColumnNames()
	{
		String val;
		val = getRepositoryTableName() + "." + FssDaoRepTbl.CD_ID.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_NAME.getName()
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_TYPE.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_INFO.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_STAT.getName()
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_REMS.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_CRTUSRID.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_CRTTS.getName();
		return val;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de repositorios para
	 * su actualización.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getUpdateRepColumnNames()
	{
		String val;
		val = getRepositoryTableName() + "." + FssDaoRepTbl.CD_NAME.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_STAT.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_REMS.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_INFO.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_UPDUSRID.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve las columnas de invesDoc de la tabla de repositorios.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getLoadRepColumnNames()
	{
		String val;
		val = getRepositoryTableName() + "." + FssDaoRepTbl.CD_NAME.getName()
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_INFO.getName() 
			+ "," + getRepositoryTableName()+ "." + FssDaoRepTbl.CD_STAT.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_REMS.getName() 
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_CRTUSRID.getName()
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_CRTTS.getName()
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_UPDUSRID.getName()
			+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve la columna INFO de la tabla de repositorio.
	 * 
	 * @return La columna mencionada.
	 */
	public String getLoadRepInfoColumnNames()
	{
		String val;
		val = getRepositoryTableName() + "." + FssDaoRepTbl.CD_INFO.getName();
		return val;
	}
	
	/**
	 * Devuelve todas las columnas del repositorio.
	 * 
	 * @return Las columnas mencionada.
	 */
	public String getLoadRepAllColumnNames()
	{
		String val;
		val = getRepositoryTableName() + "." + FssDaoRepTbl.CD_ID.getName()
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_NAME.getName()
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_INFO.getName() 
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_STAT.getName() 
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_REMS.getName() 
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_CRTUSRID.getName()
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_CRTTS.getName()
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_UPDUSRID.getName()
				+ "," + getRepositoryTableName() + "." + FssDaoRepTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve todas las columnas de la lista de volúmenes.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getLoadListAllColumnNames()
	{
		String val;
		val = getListTableName() + "." + FssDaoListTbl.CD_ID.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_NAME.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_FLAGS.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_STAT.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_REMS.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_CRTUSRID.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_CRTTS.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_UPDUSRID.getName()
				+ "," + getListTableName() + "." + FssDaoListTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve las columnas de los volúmenes.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getLoadVolumesByRepColumnNames()
	{
		String val;
		val = getVolumeTableName() + "." + FssDaoVolTbl.CD_ID.getName() 
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_NAME.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_INFO.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_ITEMP.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_ASIZE.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_NUMFILES.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_STAT.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_REMS.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_CRTUSRID.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_CRTTS.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_UPDUSRID.getName()
				+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve la columna de identificador de volumen.
	 * 
	 * @return La columna mencionada.
	 */
	public String getLoadVolumeIdColumnName()
	{
		String val;
		val = getListVolumeTableName() + "." + FssDaoListVolTbl.CD_VOLID.getName();
		return val;
	}
	
	/**
	 * Devuelve la columna de identificador de volumen.
	 * 
	 * @return La columna mencionada.
	 */
	public String getLoadListIdColumnName()
	{
		String val;
		val = getListVolumeTableName() + "." + FssDaoListVolTbl.CD_LISTID.getName();
		return val;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar el repositorio.
	 * 
	 * @param repId El identificador del repositorio.
	 * @return La expresión mencionada.
	 */
	public String getDeleteRepQual(int repId)
	{
		String qual;
		qual = "WHERE " + getRepositoryTableName() + "."
				+ FssDaoRepTbl.CD_ID.getName() + "=" + Integer.toString(repId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteRepQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular 
	 * el número de volúmenes que tiene el repositorio 
	 * con identificador repId.
	 * 
	 * @param repId Identificador del repositorio.
	 * @return La expresión mencionada.
	 */
	public String getCountVolQual(int repId)
	{
		String qual;
		qual = "WHERE " + getVolumeTableName() + "." 
				+ FssDaoVolTbl.CD_REPID.getName() + "=" + Integer.toString(repId);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountVolQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de volúmenes que hay con el mismo nombre.
	 * 
	 * @param name El nombre del volumen.
	 * @return La expresión mencionada.
	 */
	public String getCountVolNameQual(String name)
	{
		String qual;
		qual = "WHERE " + getVolumeTableName() + "."
				+ FssDaoVolTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountVolNameQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de volúmenes que hay con el mismo nombre y distinto 
	 * identificador de volumen. 
	 * 
	 * @param volId Identificador del volumen.
	 * @param name El nombre del volumen.
	 * @return La expresión mencionada.
	 */
	public String getCountVolNameIdQual(int volId, String name)
	{
		String qual;
		qual = "WHERE " + getVolumeTableName() + "."
				+ FssDaoVolTbl.CD_NAME.getName() + "= '" + name + "' AND " 
				+ getVolumeTableName() + "." + FssDaoVolTbl.CD_ID.getName() 
				+ "<>" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountVolNameIdQual: " + qual);
		return qual;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de volúmenes para
	 * su inserción.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getInsertVolColumnNames()
	{
		String val;
		val = getVolumeTableName() + "." + FssDaoVolTbl.CD_ID.getName() 
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_NAME.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_REPID.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_INFO.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_ITEMP.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_ASIZE.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_NUMFILES.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_STAT.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_REMS.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_CRTUSRID.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_CRTTS.getName();
		return val;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de volúmenes.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getLoadVolColumnNames()
	{
		String val;
		val = getVolumeTableName() + "." + FssDaoVolTbl.CD_NAME.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_REPID.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_INFO.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_ITEMP.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_ASIZE.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_NUMFILES.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_STAT.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_REMS.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_CRTUSRID.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_CRTTS.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_UPDUSRID.getName()
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve la columna identificador del volumen de invesDoc 
	 * de la tabla de volúmenes.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getLoadVolIdColumnNames()
	{
		String val;
		val = getListVolumeTableName() + "." + FssDaoListVolTbl.CD_VOLID.getName();
		return val;
	}
	
	/**
	 * Construye una expresión de búsqueda para la tabla de volúmenes a
	 * partir del identificador.
	 * 
	 * @param id El identificador de volumen.
	 * @return La expresión mencionada.
	 */
	public String getLoadVolumenQual(int id)
	{
		String qual;
		qual = "WHERE " + getVolumeTableName() + "."
				+ FssDaoVolTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadVolumenQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para la tabla de volúmenes
	 * según un identificador de repositorio.
	 * 
	 * @param repId El identificador del repositorio.
	 * @return La expresión mencionada.
	 */
	public String getLoadVolumesByRepQual(int repId)
	{
		String qual;
		qual = "WHERE " + getVolumeTableName() + "."
				+ FssDaoVolTbl.CD_REPID.getName() + "=" + Integer.toString(repId);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadVolumesByRepQual: " + qual);
		return qual;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de volúmenes para
	 * su actualización.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getUpdateVolColumnNames()
	{
		String val;
		val = getVolumeTableName() + "." + FssDaoVolTbl.CD_NAME.getName() 
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_INFO.getName() 
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_STAT.getName() 
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_REMS.getName() 
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_UPDUSRID.getName() 
			+ "," + getVolumeTableName() + "." + FssDaoVolTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla actual de 
	 * directorios de volúmenes (IVOLACTDIR) para su inserción.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getInsertVolActDirColumnNames()
	{
		String val;
		val = getVolActDirTableName() + "." + FssDaoActDirTbl.CD_VOLID.getName() 
			+ "," + getVolActDirTableName() + "." + FssDaoActDirTbl.CD_ACTDIR.getName()
			+ "," + getVolActDirTableName() + "." + FssDaoActDirTbl.CD_NUMFILES.getName();
		return val;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular 
	 * el número de ficheros que tiene el volumen 
	 * con identificador volId.
	 * 
	 * @param volId Identificador del volumen.
	 * @return La expresión mencionada.
	 */
	public String getCountFileQual(int volId)
	{
		String qual;
		qual = "WHERE " + getFileTableName() + "." 
				+ FssDaoFileTbl.CD_VOLID.getName() + "=" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountFileQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar el volumen.
	 * 
	 * @param volId El identificador del volumen.
	 * @return La expresión mencionada.
	 */
	public String getDeleteVolQual(int volId)
	{
		String qual;
		qual = "WHERE " + getVolumeTableName() + "."
				+ FssDaoVolTbl.CD_ID.getName() + "=" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteVolQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar en la tabla
	 * de lista de volúmenes.
	 * 
	 * @param volId El identificador del volumen.
	 * @return La expresión mencionada.
	 */
	public String getDeleteListVolQual(int volId)
	{
		String qual;
		qual = "WHERE " + getListVolumeTableName() + "."
				+ FssDaoListVolTbl.CD_VOLID.getName() + "=" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteListVolQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar en la tabla
	 * del actual directorio de volúmenes (IVOLACTDIR).
	 * 
	 * @param volId El identificador del volumen.
	 * @return La expresión mencionada.
	 */
	public String getDeleteActDirQual(int volId)
	{
		String qual;
		qual = "WHERE " + getVolActDirTableName() + "."
				+ FssDaoActDirTbl.CD_VOLID.getName() + "=" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteActDirQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de listas de volúmenes que hay con el mismo nombre.
	 * 
	 * @param name El nombre de la lista.
	 * @return La expresión mencionada.
	 */
	public String getCountListNameQual(String name)
	{
		String qual;
		qual = "WHERE " + getListTableName() + "."
				+ FssDaoListTbl.CD_NAME.getName() + "= '" + name + "'";
		if (_logger.isDebugEnabled())
			_logger.debug("getCountListNameQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de listas de volúmenes que hay con el mismo nombre y distinto 
	 * identificador de lista. 
	 * 
	 * @param listId Identificador de la lista.
	 * @param name El nombre de la lista.
	 * @return La expresión mencionada.
	 */
	public String getCountListNameIdQual(int listId, String name)
	{
		String qual;
		qual = "WHERE " + getListTableName() + "."
				+ FssDaoListTbl.CD_NAME.getName() + "= '" + name + "' AND " 
				+ getListTableName() + "." + FssDaoListTbl.CD_ID.getName() 
				+ "<>" + Integer.toString(listId);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountListNameIdQual: " + qual);
		return qual;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de lista de volúmenes para
	 * su inserción.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getInsertListColumnNames()
	{
		String val;
		val = getListTableName() + "." + FssDaoListTbl.CD_ID.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_NAME.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_FLAGS.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_STAT.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_REMS.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_CRTUSRID.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_CRTTS.getName();
		return val;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de lista de 
	 * volúmenes para su actualización.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getUpdateListColumnNames()
	{
		String val;
		val = getListTableName() + "." + FssDaoListTbl.CD_NAME.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_FLAGS.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_STAT.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_REMS.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_UPDUSRID.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Construye una expresión de búsqueda para la tabla de lista 
	 * de volúmenes a partir del identificador.
	 * 
	 * @param id El identificador de la lista de volúmenes.
	 * @return La expresión mencionada.
	 */
	public String getLoadListQual(int id)
	{
		String qual;
		qual = "WHERE " + getListTableName() + "."
				+ FssDaoListTbl.CD_ID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadListQual: " + qual);
		return qual;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de 
	 * la lista de volúmenes.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getLoadListColumnNames()
	{
		String val;
		val = getListTableName() + "." + FssDaoListTbl.CD_NAME.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_FLAGS.getName() 
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_STAT.getName()
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_REMS.getName()
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_CRTUSRID.getName()
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_CRTTS.getName()
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_UPDUSRID.getName()
			+ "," + getListTableName() + "." + FssDaoListTbl.CD_UPDTS.getName();
		return val;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular el número 
	 * de listas de volúmenes está asociada a un archivador.
	 * 
	 * @param listId Identificador de la lista.
	 * @return La expresión mencionada.
	 */
	public String getCountArchListQual(int listId)
	{
		String qual;
		qual = "WHERE " + DaoArchListTbl.getListIdColName(true) + "= " 
				+ Integer.toString(listId);
		if (_logger.isDebugEnabled())
			_logger.debug("getCountArchListQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar la lista 
	 * de volúmenes.
	 * 
	 * @param listId El identificador de la lista.
	 * @return La expresión mencionada.
	 */
	public String getDeleteListQual(int listId)
	{
		String qual;
		qual = "WHERE " + getListTableName() + "."
				+ FssDaoListTbl.CD_ID.getName() + "=" + Integer.toString(listId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteListQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar la lista 
	 * de volúmenes.
	 * 
	 * @param listId El identificador de la lista.
	 * @return La expresión mencionada.
	 */
	public String getDeleteListVolListQual(int listId)
	{
		String qual;
		qual = "WHERE " + getListVolumeTableName() + "."
				+ FssDaoListVolTbl.CD_LISTID.getName() + "=" + Integer.toString(listId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteListVolListQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para la tabla lista de volúmenes a
	 * partir del identificador.
	 * 
	 * @param id El identificador de lista de volúmenes.
	 * @return La expresión mencionada.
	 */
	public String getLoadListVolQual(int id)
	{
		String qual;
		qual = "WHERE " + getListVolumeTableName() + "."
				+ FssDaoListVolTbl.CD_LISTID.getName() + "=" + Integer.toString(id);
		if (_logger.isDebugEnabled())
			_logger.debug("getLoadListVolQual: " + qual);
		return qual;
	}
	
	/**
	 * Devuelve la columna para calculo del máximo orden de volúmenes en una lista.
	 *  
	 * @return La expresión mencionada.
	 */
	public String getMaxOrderColumnName()
	{
		String val;
		val = "MAX(" + getListVolumeTableName() + "." 
				+ FssDaoListVolTbl.CD_ORDER.getName() + ")";
		return val;
	}
	
	/**
	 * Devuelve todas las columnas de invesDoc de la tabla de lista de volúmenes 
	 *  (IVOLLISTVOL) para su inserción.
	 * 
	 * @return Las columnas mencionadas.
	 */
	public String getInsertListVolColumnNames()
	{
		String val;
		val = getListVolumeTableName() + "." + FssDaoListVolTbl.CD_LISTID.getName() 
			+ "," + getListVolumeTableName() + "." + FssDaoListVolTbl.CD_VOLID.getName() 
			+ "," + getListVolumeTableName() + "." + FssDaoListVolTbl.CD_ORDER.getName();
		return val;
	}
	
	/**
	 * Construye una expresión de búsqueda para eliminar un volumen 
	 * de la lista de volúmenes.
	 * 
	 * @param listId El identificador de la lista.
	 * @param volId El identificador del volumen.
	 * @return La expresión mencionada.
	 */
	public String getDeleteListVolQual(int listId, int volId)
	{
		String qual;
		qual = "WHERE " + getListVolumeTableName() + "."
				+ FssDaoListVolTbl.CD_LISTID.getName() + "=" + Integer.toString(listId) 
				+ " AND " + getListVolumeTableName() + "." 
				+ FssDaoListVolTbl.CD_VOLID.getName() + "=" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteListVolQual: " + qual);
		return qual;
	}
	
	/**
	 * Construye una expresión de búsqueda para calcular si ya está incluido 
	 * el volumen que queremos añadir a la lista.
	 * 
	 * @param listId Identificador de la lista.
	 * @param volId Identificador del volumen a incluir.
	 * @return La expresión mencionada.
	 */
	public String getCountVolListQual(int listId, int volId)
	{
		String qual;
		qual = "WHERE " + getListVolumeTableName() + "."
		+ FssDaoListVolTbl.CD_LISTID.getName() + "=" + Integer.toString(listId) 
		+ " AND " + getListVolumeTableName() + "." 
		+ FssDaoListVolTbl.CD_VOLID.getName() + "=" + Integer.toString(volId);
		if (_logger.isDebugEnabled())
			_logger.debug("getDeleteListVolQual: " + qual);
		return qual;
	}
}