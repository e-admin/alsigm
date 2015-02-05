package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class TemplateDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_PLANTDOC";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PPLANTILLAS";
	static final String IDKEY 		= "ID";
		
	//private final static int CHUNK_SIZE = 10240;

	/**
	 * Constructor vacio
	 * @throws ISPACException
	 */
	public TemplateDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public TemplateDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * Contructor que carga una determinada plantilla
	 * @param cnt conexion
	 * @param id identificador de la plantilla
	 * @throws ISPACException
	 */
	 public TemplateDAO(DbCnt cnt, int id) throws ISPACException {		
	 	super(cnt,id,null);
	 }	
	
	/**
	 * Devuelve la query por defecto
	 * @return query
	 * @throws ISPACException 
	 */
	 protected String getDefaultSQL(int nActionDAO) throws ISPACException
	 {
	 	return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	 }
  
	 /**
	  * Devuelve el nombre del campo clave primaria
	  * @return nombre de la clave primaria
	  * @throws ISPACException 
	  */
	 public String getKeyName() throws ISPACException
	 {
	 	return IDKEY;
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
	  * Para crear un nuevo registro con identificador proporcionado
	  * por la secuencia correspondiente
	  * @param cnt conexion
	  * @throws ISPACException
	  */
	 public void newObject(DbCnt cnt) throws ISPACException
	 {
	 	set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	 }	
  
	 /**
	  * Devuelve la plantilla de nombre name que esta asociada al tipo de documento
	  * con identificador idTpDoc
	  * @param cnt conexion
	  * @param name nombre de la plantilla
	  * @param idTpDoc identificador del tipo de documento
	  * @return la plantilla
	  * @throws ISPACException
	  */
	 public static TemplateDAO getTemplate(DbCnt cnt, String name, int idTpDoc)
	 throws ISPACException
	 {
	 	TemplateDAO template = null;
	 	
	 	CollectionDAO collection = new CollectionDAO (TemplateDAO.class);
	 	collection.query (cnt, "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "' AND ID_TPDOC = " + idTpDoc);
	 	if (collection.next())
	 	{
	 		template = (TemplateDAO) collection.value();
	 	}
	 	
	 	return template;
	 }
  
	 
	 /**
	  * Devuelve la plantilla de nombre name que esta asociada al tipo de documento
	  * con identificador idTpDoc
	  * @param cnt conexion
	  * @param name nombre de la plantilla
	  * @param idTpDoc identificador del tipo de documento
	  * @param expresion expresión
	  * @return la plantilla
	  * @throws ISPACException
	  */
	 public static TemplateDAO getTemplate(DbCnt cnt, String name, int idTpDoc, String expresion)
	 throws ISPACException
	 {
	 	TemplateDAO template = null;
	 	
	 	CollectionDAO collection = new CollectionDAO (TemplateDAO.class);
	 	collection.query (cnt, "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "' AND ID_TPDOC = " + idTpDoc + " AND EXPRESION = '" + DBUtil.replaceQuotes(expresion) + "'");
	 	if (collection.next())
	 	{
	 		template = (TemplateDAO) collection.value();
	 	}
	 	
	 	return template;
	 }
  	 
	 
	 /**
	  * Devuelve la plantilla de nombre name que esta asociada al tipo de documento
	  * con identificador idTpDoc
	  * @param cnt conexion
	  * @param name nombre de la plantilla
	  * @param idTpDoc identificador del tipo de documento
	  * @param idPcd identificador del procedimiento
	  * @return la plantilla
	  * @throws ISPACException
	  */
	 public static TemplateDAO getTemplate(DbCnt cnt, String name, int idTpDoc, int idPcd)
	 throws ISPACException
	 {
	 	TemplateDAO template = null;
	 	
	 	CollectionDAO collection = new CollectionDAO (TemplateDAO.class);
	 	collection.query (cnt, "WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "' AND ID_TPDOC = " + idTpDoc + " AND ID IN ( SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD = " + idPcd + " )");
	 	if (collection.next())
	 	{
	 		template = (TemplateDAO) collection.value();
	 	}
	 	
	 	return template;
	 }	 

	 /**
	  * Devuelve una plantilla a partir de su código.
	  * @param cnt Conexión
	  * @param code Código de la plantilla.
	  * @return Plantilla
	  * @throws ISPACException si ocurre algún error.
	  */
	 public static TemplateDAO getTemplateByCode(DbCnt cnt, String code) throws ISPACException {
		 
	 	TemplateDAO template = null;
	 	
	 	CollectionDAO collection = new CollectionDAO (TemplateDAO.class);
	 	collection.query (cnt, "WHERE COD_PLANT = '" + DBUtil.replaceQuotes(code) + "'");
	 	if (collection.next()) {
	 		template = (TemplateDAO) collection.value();
	 	}
	 	
	 	return template;
	 }	 
	 
	 /**
	  * Borra la plantilla y los los enventos asociados a esta
	  */
	 public void delete(DbCnt cnt) throws ISPACException
	 {
	 	String sQuery = "DELETE FROM spac_p_eventos WHERE id_obj=" + getString("ID") + " AND tp_obj = 4";
	 	cnt.directExec(sQuery);		
	 	sQuery = "DELETE FROM spac_p_blp WHERE id = " + getString ("ID");
 		cnt.directExec(sQuery);
		super.delete(cnt);
	 }
	
	 public static void delete(DbCnt cnt, int id) throws ISPACException
	 {
	 	String sQuery = "DELETE FROM spac_p_eventos WHERE id_obj=" + id + " AND tp_obj = 4";
	 	cnt.directExec(sQuery);		
	 	sQuery = "DELETE FROM spac_p_blp WHERE id = " + id;
 		cnt.directExec(sQuery);
	 	sQuery = "DELETE FROM spac_p_plantdoc WHERE id = " + id;
 		cnt.directExec(sQuery);
	 }
	/**
	 * Obtiene la colección de plantillas asociadas a un tipo de documento
	 * @param cnt manejador de la conexión
	 * @param documentId tipo de documento 
	 * @return una colección de objetos CTTemplateDAO
	 * @throws ISPACException
	 */
	public static IItemCollection getDocumentTemplates(DbCnt cnt,int documentId)
	throws ISPACException
	{
		String sql = "WHERE ID_TPDOC = " + documentId + " ORDER BY NOMBRE ";
			
		CollectionDAO collection = new CollectionDAO(TemplateDAO.class);
		collection.query(cnt,sql);
		return collection.disconnect();
	}
	
	/**
	 * Obtiene el contenido de la plantilla
	 * @param cnt conexion con base de datos
	 * @param stream donde se vuelca el archivo
	 * @throws ISPACException
	 */
	public void getTemplate(DbCnt cnt, OutputStream stream) 
	throws ISPACException
	{
		TemplateDataDAO.getBLOB(cnt, this.getKeyInt(), stream);
	}

	/**
	 * Obtiene el contenido de la plantilla
	 * @param cnt conexion con base de datos
	 * @param id identificador de la plantilla
	 * @param stream donde se vuelca el archivo
	 * @throws ISPACException
	 */
	public static void getTemplate(DbCnt cnt, int id, OutputStream stream) 
	throws ISPACException
	{
		TemplateDataDAO.getBLOB(cnt, id, stream);
	}

	/**
	 * Cambia el contenido de la plantilla
	 * @param cnt conexion con base de datos
	 * @param stream origen de datos
	 * @param mimetype mimetype del archivo
	 * @return devuelve el numero de bytes del archivo
	 * @throws ISPACException
	 */
	public void setTemplate (DbCnt cnt, InputStream stream, int length, String mimetype)
	throws ISPACException
	{
		TemplateDataDAO.setBLOB(cnt, this.getKeyInt(), stream, length, mimetype);
		set("FECHA", new Date(System.currentTimeMillis()));
		store( cnt);
	}

	/**
	 * Cambia el contenido de la plantilla
	 * @param cnt conexion con base de datos
	 * @param id identificador de la plantilla
	 * @param stream origen de datos
	 * @param mimetype mimetype del archivo
	 * @return devuelve el numero de bytes del archivo
	 * @throws ISPACException
	 */
	public static void setTemplate (DbCnt cnt, int id, InputStream stream, int length, String mimetype)
	throws ISPACException
	{
		TemplateDAO template = new TemplateDAO(cnt, id);
		template.set("FECHA", new Date(System.currentTimeMillis()));
		template.store(cnt);
		TemplateDataDAO.setBLOB(cnt, id, stream, length, mimetype);
	}
}
