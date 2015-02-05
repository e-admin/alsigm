package ieci.tdw.ispac.ispaclib.entity.def;

import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityValidation extends XmlObject {

	private static final String KEY = "ID";
	private static final Properties PROPERTIES = new Properties();
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "ID"),
				new Property(2, "FIELDID"),
				new Property(3, "TABLE"),
				new Property(3, "TABLETYPE"),
				new Property(4, "CLAZZ"),
				new Property(5, "MANDATORY"),
				new Property(6, "HIERARCHICALID"),
				new Property(7, "HIERARCHICALNAME")
				
		});
		
	}
	public final static String TRUE = "S";
	public final static String FALSE = "N";

	/** Identificador de la validación. */
	private int id = 0;

	private int fieldId;

	private String table;
	private String tableType;

	private String clazz;

	private String mandatory;
	private int hierarchicalId;
	private String hierarchicalName;

	/**
	 * Constructor.
	 *
	 */
	public EntityValidation() {
		super(PROPERTIES, KEY);
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public void setFieldId(String fieldId) {
		setFieldId(Integer.parseInt(fieldId));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
	public void setMandatory(String primaryKey) {
		setMandatory(primaryKey.equalsIgnoreCase(TRUE));
	}
	
	public void setMandatory(boolean mandatory) {
		if (mandatory)
			this.mandatory = TRUE;
		else
			this.mandatory = FALSE;
	}

	public boolean isMandatory() {
		return TRUE.equals(mandatory);
	}
	
	/**
	 * @return Returns the tableType.
	 */
	public String getTableType() {
		return tableType;
	}
	/**
	 * @param tableType The tableType to set.
	 */
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {

		StringBuffer buffer = new StringBuffer();
		buffer.append(XmlTag.newTag("fieldId", new Integer(fieldId).toString(), false));
		buffer.append(XmlTag.newTag("table", table, false));
		buffer.append(XmlTag.newTag("tableType", tableType, false));
		buffer.append(XmlTag.newTag("class", clazz, true));
		buffer.append(XmlTag.newTag("mandatory", mandatory, false));
		buffer.append(XmlTag.newTag("hierarchicalId", (hierarchicalId == 0 ? "": ""+hierarchicalId), false));
		buffer.append(XmlTag.newTag("hierarchicalName", hierarchicalName, false));
		
		return XmlTag.newTag("validation", buffer.toString(), id);
	}
	
    public static List getEntityValidationExtendedList(EntityDef entityDef, 
    												   HashMap tablasValidacion, 
    												   String entityId, 
    												   ISessionAPI session) throws ISPACException {
    	
        List validationList = CollectionBean.getBeanList(new ListCollection(entityDef.getValidations()));
    	Map entitiesLabelMap = null;
    	
    	if (entityId != null) {
    		
			StringBuffer query = new StringBuffer();
			query.append(" WHERE ID_ENT=")
				 .append(entityId)
				 .append(" AND ")
				 .append(" IDIOMA='")
				 .append(session.getClientContext().getAppLanguage())
				 .append("' ");
			
			IItemCollection col = session.getAPI().getEntitiesAPI().queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
			entitiesLabelMap = col.toMap("CLAVE");
    	}
    	
        ItemBean bean;
        int fieldId;
        String table;
        EntityField field;
        
        // Obtener los recursos para la lista de validaciones
        for (int i = 0; i < validationList.size(); i++) {
        	
            bean = (ItemBean) validationList.get(i);
            
            // Información del campo
            fieldId = ((EntityValidation)bean.getItem()).getFieldId();
            field = entityDef.findField(fieldId);
            
            if (field != null) {
                if (entitiesLabelMap != null) {
                	
    	            EntityDAO entResource = ((EntityDAO)entitiesLabelMap.get(field.getPhysicalName().toUpperCase()));
    	            if (entResource != null) {
    					bean.setProperty("FIELD", entResource.get("VALOR"));
    	            }
    	            else {
    	            	String label = field.getLogicalName();
    	            	if (label == null) {
    	            		label = field.getPhysicalName();
    	            	}
    	            	bean.setProperty("FIELD", label);
    	            }
                }
                else {
	            	String label = field.getLogicalName();
	            	if (label == null) {
	            		label = field.getPhysicalName();
	            	}
	            	bean.setProperty("FIELD", label);
                }
            }
            
            // Recurso del nombre de tabla
            table = ((EntityValidation)bean.getItem()).getTable();
            if (StringUtils.isNotBlank(table)) {
                String tableName = (String)tablasValidacion.get(String.valueOf(table));
                if (StringUtils.isNotBlank(tableName))
                    bean.setProperty("TBL_NAME",tableName);
            }
            
        }
        return validationList;
    }

	public int getHierarchicalId() {
		return hierarchicalId;
	}

	public void setHierarchicalId(int hierarchicalId) {
		this.hierarchicalId = hierarchicalId;
	}

	public void setHierarchicalId(String hierarchicalId) {
		if (StringUtils.isNotEmpty(hierarchicalId)){
			this.hierarchicalId = Integer.parseInt(hierarchicalId);
		}
	}
	
	
	public String getHierarchicalName() {
		return hierarchicalName;
	}

	public void setHierarchicalName(String hierarchicalName) {
		this.hierarchicalName = hierarchicalName;
	}

}