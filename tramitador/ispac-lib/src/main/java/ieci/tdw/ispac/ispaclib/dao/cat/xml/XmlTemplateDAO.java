package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDataDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

public class XmlTemplateDAO extends TemplateDAO {

	/**
	 * @throws ISPACException
	 */
	public XmlTemplateDAO() throws ISPACException {
		super();
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlTemplateDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlTemplateDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}

	public String export(DbCnt cnt) throws ISPACException
	{
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_EXPRESION, getString("EXPRESION"), true));
	    buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CODIGO, getString("COD_PLANT"), true));

		// Información del fichero de la plantilla
		TemplateDataDAO templateDataDAO = null;
		try {
			templateDataDAO = new TemplateDataDAO(cnt, getKeyInt());
		}
		catch (ISPACNullObject ino) {
			return null;
		}

		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_NBYTES, templateDataDAO.getString("NBYTES")));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_MIMETYPE, templateDataDAO.getString("MIMETYPE"), true));

        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));


		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_TEMPLATE, buffer.toString(), attributes.toString());
		return sXml;
	}

}