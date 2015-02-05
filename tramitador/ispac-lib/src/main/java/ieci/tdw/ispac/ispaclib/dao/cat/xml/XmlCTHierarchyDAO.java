package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTHierarchyDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Map;

public class XmlCTHierarchyDAO extends CTHierarchyDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * @throws ISPACException
	 */
	public XmlCTHierarchyDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTHierarchyDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTHierarchyDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export(DbCnt cnt, Map ctValidationTableNames, Map ctHierarchicalTableNames) throws ISPACException{
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		//buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_TABLES));
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
         		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));

        CTEntityDAO ctEntityDAO = new CTEntityDAO(cnt, getInt("ID_ENTIDAD_PADRE"));
        String entiddadPadre = ctEntityDAO.getString("NOMBRE");
        ctValidationTableNames.put(entiddadPadre, null);
        
        ctEntityDAO = new CTEntityDAO(cnt, getInt("ID_ENTIDAD_HIJA"));
        String entiddadHija = ctEntityDAO.getString("NOMBRE");
        ctValidationTableNames.put(entiddadHija, null);
        
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_ID_PADRE, getString("ID_ENTIDAD_PADRE")))
  	  		.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_ID_HIJA, getString("ID_ENTIDAD_HIJA")))
  	  		.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_NOMBRE_PADRE, entiddadPadre, true))
  	  		.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_NOMBRE_HIJA, entiddadHija, true))
  	  		.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_TIPO, getString("TIPO"), true))
  	  		.append(XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_FIELD_DESCRIPCION, getString("DESCRIPCION"), true));
		
		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_HIERARCHICAL_TABLE, buffer.toString(), attributes.toString());
		return sXml.toString();
	}
}