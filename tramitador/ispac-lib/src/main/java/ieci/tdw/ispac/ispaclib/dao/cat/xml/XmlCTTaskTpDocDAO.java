package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskTpDocDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

public class XmlCTTaskTpDocDAO extends CTTaskTpDocDAO {

	/**
	 * @throws ISPACException
	 */
	public XmlCTTaskTpDocDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTTaskTpDocDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTTaskTpDocDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export() throws ISPACException
	{
		return XmlTag.newTag(ExportProcedureMgr.TAG_TP_DOC, null, XmlTag.newAttr(ExportProcedureMgr.ATR_ID, getString("ID_TPDOC")));
	}
	
	public Integer getIdTpDoc() throws ISPACException
	{
		String idTpDoc = getString("ID_TPDOC");
		return Integer.valueOf(idTpDoc);
	}
	
}