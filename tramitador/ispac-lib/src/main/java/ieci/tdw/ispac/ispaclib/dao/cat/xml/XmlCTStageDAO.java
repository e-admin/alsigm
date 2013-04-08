package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTStageDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

public class XmlCTStageDAO extends CTStageDAO {
	
	/**
	 * @throws ISPACException
	 */
	public XmlCTStageDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTStageDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTStageDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export() throws ISPACException
	{
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CODIGO, getString("COD_FASE"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getString("DESCRIPCION"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_OBSERVACIONES, getString("OBSERVACIONES"), true));
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));
		
		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_STAGE, buffer.toString(), attributes.toString());
		return sXml;
	}
	
}