package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.List;

public class XmlCTRuleDAO extends CTRuleDAO {
	
	public final String ISPAC_RULE_PACKAGE = "ieci.tdw.ispac.api.rule";
	
	/**
	 * @throws ISPACException
	 */
	public XmlCTRuleDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTRuleDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTRuleDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export(List ruleClasses) throws ISPACException
	{
        // Clase
        String clase = getString("CLASE");
        if ((StringUtils.isNotBlank(clase)) &&
        	(!clase.startsWith(ISPAC_RULE_PACKAGE))) {
        	ruleClasses.add(clase);
        }
        
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getString("DESCRIPCION"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CLASE, clase));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO, getString("TIPO")));
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));
		
		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_RULE, buffer.toString(), attributes.toString());
		return sXml;
	}
	
}