package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Date;

public class CTReportDAO extends ObjectDAO
{
	public static final String TABLENAME 	= "SPAC_CT_INFORMES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_CTINFORMES";
	static final String IDKEY 		= "ID";	
	private final static int CHUNK_SIZE = 10240;

	public static final int GENERIC_TYPE = 1;
	public static final int SPECIFIC_TYPE = 2;
	public static final int GLOBAL_TYPE=3;
	public static final int SEARCH_TYPE=4;
	
	
	public static final int VISIBLIDAD_PUBLICA=1;
	public static final int VISIBILIDAD_RESTRINGIDA=0;
	/**
	 * Constructor vacio
	 * @throws ISPACException
	 */
	public CTReportDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTReportDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * Contructor que carga una determinada plantilla
	 * @param cnt conexion
	 * @param id identificador de la plantilla
	 * @throws ISPACException
	 */
	 public CTReportDAO(DbCnt cnt, int id) throws ISPACException {		
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
	 * Elimina el informe cuyo id es el mismo que el que recibe como parametro
	 * @param cnt Conexion con la bbdd utilizada
	 * @param id Identificador del informe 
	 * @throws ISPACException
	 */
	 
	 public static void delete(DbCnt cnt, int id) throws ISPACException
	 {
	 	String sQuery = "DELETE FROM " +TABLENAME +" WHERE id = " + id;
 		cnt.directExec(sQuery);
	 }
	
	
	/**
	 * Obtiene el contenido de un informe
	 * @param cnt conexion con base de datos
	 * @param stream donde se vuelca el archivo
	 * @throws ISPACException
	 */
	public void getReport(DbCnt cnt, OutputStream stream) 
	throws ISPACException
	{
	   getReport(cnt, this.getKeyInt(), stream);
	}

	/**
	 * Obtiene el contenido del informe
	 * @param cnt conexion con base de datos
	 * @param id identificador del informe
	 * @param stream donde se vuelca el archivo
	 * @throws ISPACException
	 */
	public static void getReport(DbCnt cnt, int id, OutputStream stream) 
	throws ISPACException
	{
		DbQuery rs = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = new BufferedOutputStream(stream);

		try
		{
			String sSQL = "SELECT XML FROM " + TABLENAME + " WHERE ID = " + id;
			rs = cnt.executeDbQuery(sSQL);
			if (rs.next())
			{
				byte[] chunk = new byte[CHUNK_SIZE];
				int bytes;
				if (cnt.isPostgreSQLDbEngine()){
					in = new BufferedInputStream(rs.getBinaryStream("XML"));
				}else{
					Blob blob = rs.getBLOB("XML");
					in = new BufferedInputStream(blob.getBinaryStream());
				}
				while ((bytes = in.read(chunk)) != -1)
				{
					out.write(chunk, 0, bytes);
				}
				out.flush();
			}
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				if (rs != null) rs.close();
				if (in != null)	in.close();
			}
			catch (Exception e)
			{}
		}
	}

	/**
	 * Cambia el contenido del informe
	 * @param cnt conexion con base de datos
	 * @param xml contenido del informe 
	 * @return devuelve el numero de bytes del archivo
	 * @throws ISPACException
	 */
	public void setReport(DbCnt cnt, String xml)
	throws ISPACException
	{
		set("XML", xml);
		set("FECHA", new Date(System.currentTimeMillis()));
		store( cnt);
	}

	/**
	 * Cambia el contenido de un informe
	 * @param cnt conexion con base de datos
	 * @param id identificador del informe
	 * @param xml con la nueva definicion del informe
	 * @return devuelve el numero de bytes del archivo
	 * @throws ISPACException
	 */
	public static void setReport (DbCnt cnt, int id, String xml)
	throws ISPACException
	{
		CTReportDAO report = new CTReportDAO(cnt, id);
		report.set("FECHA", new Date(System.currentTimeMillis()));
		report.set("XML", xml);
		report.store(cnt);
		
	}
	
	  public static IItemCollection getReports(DbCnt cnt, String query)
	   throws ISPACException
	    {
			try
			{
				CollectionDAO collection = new CollectionDAO(CTReportDAO.class);
				collection.query(cnt,query);
				return collection.disconnect();
				
			}
			catch (ISPACException ie)
			{
				throw new ISPACException("Error en ReportsAPI:getgetReports", ie);
			}
		
	    }
}

