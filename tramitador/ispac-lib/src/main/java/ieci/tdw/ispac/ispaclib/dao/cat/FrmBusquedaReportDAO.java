package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class FrmBusquedaReportDAO extends ObjectDAO
{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLENAME 	= "SPAC_CT_FRMBUSQ_INFORMES";
	static final String ID_FRMBUSQUEDA		= "id_fmrbusqueda";	
	static final String ID_INFORME		= "id_informe";	


	/**
	 * Constructor vacio
	 * @throws ISPACException
	 */
	public FrmBusquedaReportDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public FrmBusquedaReportDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * Contructor que carga una determinada plantilla
	 * @param cnt conexion
	 * @param id identificador de la plantilla
	 * @throws ISPACException
	 */
	 public FrmBusquedaReportDAO(DbCnt cnt, int id) throws ISPACException {		
	 	super(cnt,id,null);
	 }	
	
	/**
	 * Devuelve la query por defecto
	 * @return query
	 * @throws ISPACException 
	 */
	 protected String getDefaultSQL(int nActionDAO) throws ISPACException
	 {
	 	return " WHERE " + ID_FRMBUSQUEDA + " = " + getInt(ID_FRMBUSQUEDA);
	 }
  
	 /**
	  * Devuelve el nombre del campo clave primaria
	  * @return nombre de la clave primaria
	  * @throws ISPACException 
	  */
	 public String getKeyName() throws ISPACException
	 {
	 	return ID_FRMBUSQUEDA;
	 }
  
	 /**
	  * Devuelve el nombre de la tabla
	  * @return Nombre de la tabla
	  * @throws ISPACException 
	  */
	 public String getTableName()
	 {
	 	return TABLENAME;
	 }
  
	 /**
	  * Elimina las asociaciones  entre el formulario y los informes
	  * @param cnt
	  * @param idfrm identificador del formulario
	  * @param idinforme identificador del informe
	  * @throws ISPACException
	 */
			 
	 public static void delete(DbCnt cnt, int idFrm ) throws ISPACException
	 {
	 	String sQuery = "DELETE FROM " +TABLENAME +" WHERE "+ID_FRMBUSQUEDA+" = " + idFrm ;
 		cnt.directExec(sQuery);
	 }
	
	 public static void newObject(DbCnt cnt, int idfrm, int idinforme)throws ISPACException{
		String sQuery = "INSERT INTO " +TABLENAME +" VALUES(" + idfrm +" , "+idinforme+" ) ";
	 	cnt.directExec(sQuery);
	 }
	 
	 public static CollectionDAO getReportByIdFormSearch(DbCnt cnt, int idFrm)throws ISPACException{
		 String sql = "WHERE "+ID_FRMBUSQUEDA+" = " + idFrm;

		CollectionDAO collection = new CollectionDAO(FrmBusquedaReportDAO.class);
		collection.query(cnt,sql);
		return collection;
		
	 }

	protected void newObject(DbCnt cnt) throws ISPACException {
		// TODO Auto-generated method stub
		
	}
		
	}
