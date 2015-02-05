package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

public class EntityRelationDef extends XmlObject {

	private static final long serialVersionUID = 1L;
	
	private String type = null;
	
	private String primaryField = null;
	private boolean multivalue = false;
	
	private String secondaryField = null;
	private String query = null;
	
	/**
	 * Constructor.
	 *
	 */
	public EntityRelationDef() {
		super(null, null);
	}

	public EntityRelationDef(String type, String primaryField, String secondaryField) {
		this(type);
		
		this.primaryField = primaryField;
		this.secondaryField = secondaryField;
	}
	
	public EntityRelationDef(String type) {
		super(null, null);
		
		this.type = type;
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
	 * @return Returns the primaryField.
	 */
	public String getPrimaryField() {
		return primaryField;
	}
	/**
	 * @param primaryField The primaryField to set.
	 */
	public void setPrimaryField(String primaryField) {
		if (!StringUtils.isEmpty(primaryField)) {
			this.primaryField = primaryField.toUpperCase();
		}
	}
	
	/**
	 * @return Returns the secondaryField.
	 */
	public String getSecondaryField() {
		return secondaryField;
	}
	/**
	 * @param secondaryField The secondaryField to set.
	 */
	public void setSecondaryField(String secondaryField) {
		if (!StringUtils.isEmpty(secondaryField)) {
			this.secondaryField = secondaryField.toUpperCase();
		}
	}
	
	/**
	 * @return Returns the query.
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query The query to set.
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	public boolean isMultivalue() {
		return multivalue;
	}

	public void setMultivalue(boolean multivalue) {
		this.multivalue = multivalue;
	}

	public void setMultivalue(String multivalueString) {
		setMultivalue(new Boolean(multivalueString).booleanValue());
	}
	
	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {
		
		StringBuffer buffer = new StringBuffer();
		
		if (!StringUtils.isEmpty(getPrimaryField())) {
			
			buffer.append(XmlTag.newTag(EntityAppConstants.NODE_PRIMARY_FIELD, getPrimaryField()));
		}
		
		if (!StringUtils.isEmpty(getSecondaryField())) {
			
			buffer.append(XmlTag.newTag(EntityAppConstants.NODE_SECONDARY_FIELD, getSecondaryField()));
		}
		
		if (!StringUtils.isEmpty(getQuery())) {
			
			buffer.append(XmlTag.newTag(EntityAppConstants.NODE_QUERY, getQuery()));
		}
		
		return buffer.toString();
	}
	
}