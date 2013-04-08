package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.PcdTemplateDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

public class XmlPcdTemplateDAO extends PcdTemplateDAO {
	
	/**
	 * @throws ISPACException
	 */
	public XmlPcdTemplateDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlPcdTemplateDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlPcdTemplateDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	/**
	 * Obtiene la colección de plantillas asociadas a un procedimiento
	 * 
	 * @param cnt manejador de la conexión
	 * @param procedureId identificador del procedimiento 
	 * @return una colección de objetos PcdTemplateDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getTemplates(DbCnt cnt, int procedureId)
	throws ISPACException
	{
		String sql = "WHERE ID_PCD = " + procedureId;
			
		CollectionDAO collection = new CollectionDAO(XmlPcdTemplateDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
	
	public String export() throws ISPACException
	{
		return XmlTag.newTag(ExportProcedureMgr.TAG_TEMPLATE, null, XmlTag.newAttr(ExportProcedureMgr.ATR_ID, getString("ID_P_PLANTDOC")));
	}
	
}