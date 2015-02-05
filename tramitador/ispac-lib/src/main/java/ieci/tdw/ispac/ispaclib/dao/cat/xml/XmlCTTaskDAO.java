package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Map;

public class XmlCTTaskDAO extends CTTaskDAO {
	
	/**
	 * @throws ISPACException
	 */
	public XmlCTTaskDAO() throws ISPACException {
		super();
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTTaskDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTTaskDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export(DbCnt cnt, Map ctTpDocIds) throws ISPACException
	{
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_CODIGO, getString("COD_TRAM"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getString("DESCRIPCION"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_OBSERVACIONES, getString("OBSERVACIONES"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO, getString("TIPO"), true));
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_ID_SUBPROCESO, getString("ID_SUBPROCESO")));
		
		// Tipos de documento
		StringBuffer tpDocs = new StringBuffer();
		IItemCollection collection = getDocTypes(cnt).disconnect();
		while (collection.next()) {
			
			XmlCTTaskTpDocDAO xmlCTTaskTpDocDAO = (XmlCTTaskTpDocDAO) collection.value();
			tpDocs.append(xmlCTTaskTpDocDAO.export());
			
			Integer idTpDoc = xmlCTTaskTpDocDAO.getIdTpDoc();
			if (!ctTpDocIds.containsKey(idTpDoc)) {
				ctTpDocIds.put(idTpDoc, null);
			}
		}
		buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TP_DOCS, tpDocs.toString()));
		
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
        		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));
		
		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_TASK, buffer.toString(), attributes.toString());
		return sXml;
	}
	
	/**
	 * Obtiene los tipos de documento generables desde el trámite.
	 * 
	 * @param cnt manejador de la conexión
	 * @return una colección de objetos XmlCTTaskTpDocDAO
	 * @throws ISPACException
	 */
	public CollectionDAO getDocTypes(DbCnt cnt) throws ISPACException
	{
		String sql = " WHERE ID_TRAMITE = " + getKeyInt();

		CollectionDAO collection = new CollectionDAO(XmlCTTaskTpDocDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
	
}