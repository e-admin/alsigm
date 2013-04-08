package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTpDocDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.List;

public class XmlCTTpDocDAO extends CTTpDocDAO {
	
	/**
	 * @throws ISPACException
	 */
	public XmlCTTpDocDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTTpDocDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTTpDocDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export(DbCnt cnt, int procedureid, List ltTemplates) throws ISPACException
	{
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CODIGO, getString("COD_TPDOC"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getString("DESCRIPCION"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_OBSERVACIONES, getString("OBSERVACIONES"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO, getString("TIPO"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO_REG, getString("TIPOREG"), true));
		
		// Plantillas asociadas al tipo documental y las específicas al procedimiento
		StringBuffer templates = new StringBuffer();
		IItemCollection collection = getTemplates(cnt, procedureid).disconnect();
		while (collection.next()) {
			
			XmlTemplateDAO xmlTemplateDAO = (XmlTemplateDAO) collection.value();
			String xmlTemplate = xmlTemplateDAO.export(cnt);
			if (xmlTemplate != null) {
				
				templates.append(xmlTemplate);
				// Guardar las plantillas para exportar su contenido
				ltTemplates.add(xmlTemplateDAO);
			}
		}
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TEMPLATES, templates.toString()));
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));
		
		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_TP_DOC, buffer.toString(), attributes.toString());
		return sXml;
	}
	
    public CollectionDAO getTemplates(DbCnt cnt, int procedureid) throws ISPACException
	{
    	// Plantillas asociadas al tipo documental y las específicas al procedimiento
    	// Una plantilla específica puede estar asociada a varios procedimientos
    	String sql = " WHERE ID_TPDOC = " + getKeyInt()
    			   + " AND ID NOT IN (SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD != " + procedureid
    			   + " AND ID_P_PLANTDOC NOT IN (SELECT ID_P_PLANTDOC FROM SPAC_P_PLANTILLAS WHERE ID_PCD = " + procedureid + "))";
    	
		CollectionDAO collection = new CollectionDAO(XmlTemplateDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
	
}