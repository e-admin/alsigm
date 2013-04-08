package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

public class EntityParameterDef extends XmlObject {

	private String type = null;
	private String primaryTable = null;
	private boolean readonly = false;
	private boolean noDelete = false; 
	
	private String table = null;
	private EntityRelationDef relation = null;

	/**
	 * Constructor.
	 *
	 */
	public EntityParameterDef() {
		super(null, null);
	}
	
	public EntityParameterDef(String type, String table, String relationType, String primaryField, String secondaryField) {
		super(null, null);
		
		this.type = type;
		this.table = table;
		this.relation = new EntityRelationDef(relationType, primaryField, secondaryField);
	}
	
	public EntityParameterDef(String type, String table, String relationType) {
		super(null, null);
		
		this.type = type;
		this.table = table;
		this.relation = new EntityRelationDef(relationType);
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		if (!StringUtils.isEmpty(type)) {
			this.type = type.toUpperCase();
		}
	}
	
	/**
	 * @return Returns the primaryTable.
	 */
	public String getPrimaryTable() {
		return primaryTable;
	}
	/**
	 * @param primaryTable The primaryTable to set.
	 */
	public void setPrimaryTable(String primaryTable) {
		this.primaryTable = primaryTable;
	}
	
	/**
	 * @return Returns the table.
	 */
	public String getTable() {
		return table;
	}
	/**
	 * @param table The table to set.
	 */
	public void setTable(String table) {
		this.table = table;
	}
	
	/**
	 * @return Returns the relation.
	 */
	public EntityRelationDef getRelation() {
		return relation;
	}
	/**
	 * @param relation The relation to set.
	 */
	public void setRelation(EntityRelationDef relation) {
		this.relation = relation;
	}
	
	/**
	 * @return Returns the readonly.
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * @param readonly The readonly to set.
	 */
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	public void setReadonly(String readonly) {
		setReadonly(readonly.equalsIgnoreCase("TRUE"));
	}

    /** 
     * @return Returns the noDelete.
     */
	public boolean isNoDelete() {
		return noDelete;
	}
	
	/**
	 * @param noDelete The noDelete to set.
	 */
	public void setNoDelete(boolean noDelete) {
		this.noDelete = noDelete;
	}
	
	public void setNoDelete(String noDelete) {
		setNoDelete("TRUE".equalsIgnoreCase(noDelete));
	}
	
	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {
		
		StringBuffer buffer = new StringBuffer();
		
		if (getTable() != null) {
			
			buffer.append(XmlTag.newTag(EntityAppConstants.NODE_TABLE, getTable()));
		}
		
		if (getRelation() != null) {
			
			buffer.append(XmlTag.newTag(EntityAppConstants.NODE_RELATION, 
						  getRelation().getXmlValues(),
						  XmlTag.newAttr(EntityAppConstants.ATTRIBUTE_TYPE, getRelation().getType())));
		}
		
		return buffer.toString();
	}
	
}