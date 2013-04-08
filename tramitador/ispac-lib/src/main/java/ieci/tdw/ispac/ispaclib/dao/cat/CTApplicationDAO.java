package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.IApplicationDef;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class CTApplicationDAO extends ObjectDAO implements IApplicationDef
{

	static final String TABLENAME 				= "SPAC_CT_APLICACIONES";
	static final String IDSEQUENCE 				= "SPAC_SQ_ID_CTAPLICACIONES";
	static final String IDKEY 					= "ID";

	static final String NAMEKEY 				= "NOMBRE";
	static final String DESCKEY					= "DESCRIPCION";
	static final String TYPEKEY					= "TYPE";
	static final String CLASSNAMEKEY			= "CLASE";
	static final String PAGEKEY 				= "PAGINA";
	static final String PARAMKEY 				= "PARAMETROS";
	static final String FORMATKEY				= "FORMATEADOR";
	static final String XMLFORMATKEY			= "XML_FORMATEADOR";
	static final String FRMJSPKEY				= "FRM_JSP";
	static final String FRMVERSIONKEY			= "FRM_VERSION";
	static final String ENTPRINCIPALNOMBREKEY	= "ENT_PRINCIPAL_NOMBRE";
	static final String ENTPRINCIPALIDKEY		= "ENT_PRINCIPAL_ID";
	
	
	public static final String DOCUMENTS_ACTIVATE="SI";
	/**
	 * 
	 * @throws ISPACException
	 */
	public CTApplicationDAO() throws ISPACException	{
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTApplicationDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTApplicationDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}


	public String getTableName()
	{
		return TABLENAME;
	}

	protected void newObject(DbCnt cnt)
	throws ISPACException
	{
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	protected String getDefaultSQL(int nActionDAO)
	throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName()
	throws ISPACException
	{
		return IDKEY;
	}

	public int getId() throws ISPACException
	{
	    return getInt(IDKEY);
	}
	
	public int getType() throws ISPACException
	{
	    return getInt(TYPEKEY);
	}
	
	public String getName() throws ISPACException
	{
	    return getString(NAMEKEY);
	}
	
	public String getDescription() throws ISPACException
	{
	    return getString(DESCKEY);
	}
	
	public String getClassName() throws ISPACException
	{
	    return getString(CLASSNAMEKEY);
	}
	
	public String getPage() throws ISPACException
	{
	    return getString(PAGEKEY);
	}
	
	public String getParameters() throws ISPACException
	{
	    return getString(PARAMKEY);
	}
	
	public String getFormatter() throws ISPACException
	{
	    return getString(FORMATKEY);
	}

	public String getFormatterXML() throws ISPACException
	{
	    return getString(XMLFORMATKEY);
	}

	public String getFrmJsp() throws ISPACException
	{
	    return getString(FRMJSPKEY);
	}
	
	public int getFrmVersion() throws ISPACException
	{
	    return getInt(FRMVERSIONKEY);
	}
	
	public String getEntPrincipalNombre() throws ISPACException
	{
	    return getString(ENTPRINCIPALNOMBREKEY);
	}
	
	public static void deleteApp(DbCnt cnt, 
			int entityId) throws ISPACException {

		String sql = "WHERE " + ENTPRINCIPALIDKEY + " = " + entityId + " ";
		
		CollectionDAO collection = new CollectionDAO(CTApplicationDAO.class);
		collection.query(cnt,sql);
		while (collection.next()) {
		
			CTApplicationDAO entityResource = (CTApplicationDAO) collection.value();
			entityResource.delete(cnt);

		}
	
	}
}