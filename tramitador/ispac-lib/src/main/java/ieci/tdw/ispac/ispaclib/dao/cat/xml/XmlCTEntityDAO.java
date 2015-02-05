package ieci.tdw.ispac.ispaclib.dao.cat.xml;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.XmlEntityResourceDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlCTEntityDAO extends CTEntityDAO {

	/**
	 * @throws ISPACException
	 */
	public XmlCTEntityDAO() throws ISPACException {
		super();
	}
	
	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public XmlCTEntityDAO(DbCnt cnt) throws ISPACException {
		super(cnt);
	}
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public XmlCTEntityDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id);
	}
	
	public String export(DbCnt cnt,
						 Map ctValidationTableNames,
						 Map ctHierarchicalTableNames,
						 Map formatters, 
						 List formClasses,
						 boolean exportForms,
						 Map ctEntityNamesInForms) throws ISPACException {
		
		String sXml = null;
		StringBuffer buffer = new StringBuffer();
		
        StringBuffer attributes = new StringBuffer();
        attributes.append(XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getKeyInt())))
         		  .append(XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getName())));
        
        int type = getType();
        String definition = getDefinition();
        
        // Entidad de proceso
		if (EntityType.getInstance(type).equals(EntityType.PROCESS_ENTITY_TYPE)) {

	        // Procesar la definición de la entidad
	        // para obtener los nombres de las tablas de validación
	        // que están asociadas a los campos de la entidad
	        EntityDef entityDef = EntityDef.parseEntityDef(definition);
	        List validations = entityDef.getValidations();
	        Iterator it = validations.iterator();
	        while (it.hasNext()) {
	        	
				EntityValidation validation = (EntityValidation) it.next();
				String validationTable = validation.getTable();
				if ((StringUtils.isNotBlank(validationTable)) &&
					(!ctValidationTableNames.containsKey(validationTable))) {
						ctValidationTableNames.put(validationTable, null);
				}
				String hierarchicalTableName = validation.getHierarchicalName();
				if ((StringUtils.isNotBlank(hierarchicalTableName)) &&
						(!ctHierarchicalTableNames.containsKey(hierarchicalTableName))) {
					ctHierarchicalTableNames.put(hierarchicalTableName, null);
				}
				
	        }
	        
	        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO, String.valueOf(type)))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_CAMPO_PK, getKeyField()))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_CAMPO_NUMEXP, getKeyNumExp()))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_SCHEMA_EXPR, getSchemaExpr()))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getDescription(), true))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_SEC_PK, getSequence(), true))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_DEFINICION, ExportProcedureMgr.replaceEndCDATA(definition), true))
	        	  .append(XmlTag.newTag(ExportProcedureMgr.TAG_FRM_JSP, getString("FRM_JSP"), true));
	        
	        // Exportar los formularios?
	        if (exportForms) {
	        	
				StringBuffer forms = new StringBuffer();
				IItemCollection collection = getForms(cnt).disconnect();
				while (collection.next()) {
					
					XmlCTApplicationDAO xmlCTApplicationDAO = (XmlCTApplicationDAO) collection.value();		
					forms.append(xmlCTApplicationDAO.export(formatters, formClasses, ctEntityNamesInForms,ctValidationTableNames));		
				}
				buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_FORMS, forms.toString(), XmlTag.newAttr(ExportProcedureMgr.ATR_DEFAULT_ID, String.valueOf(getInt("FRM_EDIT")))));
	        }
	    	  	  
	        // Etiquetas
			buffer.append(exportResources(cnt));
			
			sXml = XmlTag.newTag(ExportProcedureMgr.TAG_ENTITY, buffer.toString(), attributes.toString());
		}
		else {
			// Tabla de validación		
	        buffer.append(XmlTag.newTag(ExportProcedureMgr.TAG_TIPO, String.valueOf(type)))
      	  		  .append(XmlTag.newTag(ExportProcedureMgr.TAG_CAMPO_PK, getKeyField()))
      	  		  .append(XmlTag.newTag(ExportProcedureMgr.TAG_DESCRIPCION, getDescription(), true))
      	  		  .append(XmlTag.newTag(ExportProcedureMgr.TAG_SEC_PK, getSequence(), true))
      	  		  .append(XmlTag.newTag(ExportProcedureMgr.TAG_DEFINICION, ExportProcedureMgr.replaceEndCDATA(definition), true));
	        
	        // Etiquetas
			buffer.append(exportResources(cnt));
			
	        sXml = XmlTag.newTag(ExportProcedureMgr.TAG_VALIDATION_TABLE, buffer.toString(), attributes.toString());
		}
		
		return sXml;
	}
	
	private String exportResources(DbCnt cnt) throws ISPACException {
		
		StringBuffer resources = new StringBuffer();
		
		IItemCollection collectionResources = getResources(cnt).disconnect();
		while (collectionResources.next()) {
			
			XmlEntityResourceDAO xmlEntityResourceDAO =  (XmlEntityResourceDAO) collectionResources.value();		
			resources.append(xmlEntityResourceDAO.export());
		}
		
		return XmlTag.newTag(ExportProcedureMgr.TAG_RESOURCES, resources.toString());
	}
	
	public CollectionDAO getForms(DbCnt cnt) throws ISPACException
	{
		String sql = " WHERE ENT_PRINCIPAL_ID = " + getKeyInt();

		CollectionDAO collection = new CollectionDAO(XmlCTApplicationDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
	
	public CollectionDAO getResources(DbCnt cnt) throws ISPACException
	{
		String sql = " WHERE ID_ENT = " + getKeyInt();

		CollectionDAO collection = new CollectionDAO(XmlEntityResourceDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
	
}