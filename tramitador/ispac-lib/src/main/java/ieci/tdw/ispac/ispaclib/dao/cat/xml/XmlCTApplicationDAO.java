package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.EntityAppConstants;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlCTApplicationDAO extends CTApplicationDAO {
	
	public final String ISPAC_APP_PACKAGE = "ieci.tdw.ispac.ispaclib.app.";

	/**
	 * @throws ISPACException
	 */
	public XmlCTApplicationDAO() throws ISPACException {
		super();
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTApplicationDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTApplicationDAO (DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	/**
	 * Exporta las entidades y las tablas de validación participantes en el formulario
	 * @param formatters
	 * @param formClasses
	 * @param ctEntityNamesInForms
	 * @param ctValidationTableNames
	 * @return
	 * @throws ISPACException
	 */
	
	public String export (Map formatters , List formClasses , Map ctEntityNamesInForms , Map ctValidationTableNames)throws ISPACException
	{
		
		// Formateador
		String formatter = getString("FORMATEADOR");
        if ((StringUtils.isNotBlank(formatter)) && 
        	(!formatters.containsKey(formatter))) {
        	formatters.put(formatter, null);
        }
        
        // Clase
        String clase = getString("CLASE");
        if ((StringUtils.isNotBlank(clase)) &&
        	(!clase.startsWith(ISPAC_APP_PACKAGE))) {
        	formClasses.add(clase);
        }
        
        // Procesar las entidades utilizadas en el formulario
        // cuando no son de validación
        String parameters = getString("PARAMETROS");
    	if (!StringUtils.isEmpty(parameters)) {
    		
    		List entities = ParametersDef.parseParametersDef(parameters).getEntities();
    		
    		Iterator it = entities.iterator();
    		while (it.hasNext()) {
    			
				EntityParameterDef entityParameterDef = (EntityParameterDef) it.next();
				
				if (!entityParameterDef.getType().equals(EntityAppConstants.ENTITY_TYPE_VALIDATION)) {
					String entityTable = entityParameterDef.getTable();
					if ((StringUtils.isNotBlank(entityTable)) &&
						(!ctEntityNamesInForms.containsKey(entityTable))) {
							
						ctEntityNamesInForms.put(entityTable, null);
					}
				}
				//Si es una tabla de validación la tratamos si el parámetro ctValidationTableNames es distinto de nulo
				else{
						if(ctValidationTableNames!=null){
							String validationTable = entityParameterDef.getTable();
							if ((StringUtils.isNotBlank(validationTable)) &&
									(!ctValidationTableNames.containsKey(validationTable))) {
										
								ctValidationTableNames.put(validationTable, null);
							}
						}
						
					}
				}
    	}
			
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
         		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getString("NOMBRE"))));
        
        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getString("DESCRIPCION"), true))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_CLASE, clase))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_PAGINA, getString("PAGINA")))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_PARAMETROS, ExportProcedureMgr.replaceEndCDATA(getString("PARAMETROS")), true))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_FORMATEADOR, formatter))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_XML_FORMATEADOR, ExportProcedureMgr.replaceEndCDATA(getString("XML_FORMATEADOR")), true))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_FRM_JSP, getString("FRM_JSP"), true))
        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_FRM_VERSION, getString("FRM_VERSION")));
        
		sXml = XmlTag.newTag(ExportProcedureMgr.TAG_FORM, buffer.toString(), attributes.toString());
		return sXml;
	
	}
		

	/**
	 * @deprecated usar (Map formatters , List formClasses , Map ctEntityNamesInForms , Map ctValidationTableNames)
	 * @param formatters
	 * @param formClasses
	 * @param ctEntityNamesInForms
	 * @return
	 * @throws ISPACException
	 */
	public String export(Map formatters, 
						 List formClasses,
						 Map ctEntityNamesInForms) throws ISPACException {
		
    	return export(formatters, formClasses, ctEntityNamesInForms, null);
	}
}

