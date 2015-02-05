package ieci.tdw.ispac.ispaclib.dao.entity;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

public class XmlEntityResourceDAO extends EntityResourceDAO {

	/**
	 * @throws ISPACException
	 */
	public XmlEntityResourceDAO () throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlEntityResourceDAO (DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlEntityResourceDAO (DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}

	public String export() throws ISPACException {
		
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_LANGUAGE, getString("IDIOMA")))
         		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_CLAVE, getString("CLAVE")));
        
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_VALUE, getString("VALOR"), true));

		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_RESOURCE, buffer.toString(), attributes.toString());
		return sXml;		
	}
	
}