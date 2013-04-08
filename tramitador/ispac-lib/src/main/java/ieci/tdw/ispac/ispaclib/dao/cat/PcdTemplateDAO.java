package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class PcdTemplateDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_PLANTILLAS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_P_PLANTILLAS";
	static final String IDKEY 		= "ID";
		
	/**
	 * Constructor vacio
	 * @throws ISPACException
	 */
	public PcdTemplateDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PcdTemplateDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * Contructor que carga una determinada plantilla
	 * @param cnt conexion
	 * @param id identificador de la plantilla
	 * @throws ISPACException
	 */
	 public PcdTemplateDAO(DbCnt cnt, int id) throws ISPACException {		
	 	super(cnt,id,null);
	 }	
	
	/**
	 * Devuelve la query por defecto
	 * @return query
	 * @throws ISPACException 
	 */
	 protected String getDefaultSQL(int nActionDAO) throws ISPACException {
	 	return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	 }
  
	 /**
	  * Devuelve el nombre del campo clave primaria
	  * @return nombre de la clave primaria
	  * @throws ISPACException 
	  */
	 public String getKeyName() throws ISPACException {
	 	return IDKEY;
	 }
  
	 /**
	  * Devuelve el nombre de la tabla
	  * @return Nombre de la tabla
	  * @throws ISPACException 
	  */
	 public String getTableName() {
	 	return TABLENAME;
	 }
  
	 /**
	  * Para crear un nuevo registro con identificador proporcionado
	  * por la secuencia correspondiente
	  * @param cnt conexion
	  * @throws ISPACException
	  */
	 public void newObject(DbCnt cnt) throws ISPACException {
	 	set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	 }	
  
}
