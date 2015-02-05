package ieci.tdw.ispac.ispaclib.entity.def;
import ieci.tdw.ispac.api.EntityStatus;
import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.EntityAppConstants;
import ieci.tdw.ispac.ispaclib.app.EntityParameterDef;
import ieci.tdw.ispac.ispaclib.app.ParametersDef;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;

public class EntityDef extends XmlObject {
	
	public final static String TRUE = "S";
	public final static String FALSE = "N";
	private static final String KEY = "ID";
	private static final Properties PROPERTIES = new Properties();
	
	private final static Logger logger = Logger.getLogger(EntityDef.class);
	private static final String DEFAULT_VERSION = "1.00";
	private String editable = TRUE;
	private String dropable = TRUE;
	private EntityStatus status = EntityStatus.VIGENTE;
	//private String tableSystem = FALSE;
	
	/** Versión de la entidad. */
	private String version = DEFAULT_VERSION;
	
	/** Lista de campos de la entidad. */
	private List fields = new ArrayList();
	
	/** Lista de índices de la entidad. */
	private List indexes = new ArrayList();
	
	/** Lista de validaciones de la entidad. */
	private List validations = new ArrayList();
	
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "VERSION"),
				new Property(2, "FIELDS"),
				new Property(3, "INDEXES"),
				new Property(4, "VALIDATIONS")
		});
	}
	/**
	 * Constructor.
	 *
	 */
	public EntityDef(int type) {
		super(PROPERTIES, KEY);
		if (type == EntityType.PROCESS_ENTITY_TYPE.getId()){
        	// Id
        	fields.add(getIdFieldInstace(fields.size()+1));			

       		// numexp
        	fields.add(getNumExpFieldInstace(fields.size()+1));
		}
	}

	public EntityDef() {
		super(PROPERTIES, KEY);
	}	
	
	
	
    private EntityField getIdFieldInstace(int id){
        EntityField ID_FIELD = new EntityField();
        ID_FIELD.setId(id);
        //ID_FIELD.setLogicalName(ID_FIELD_NAME);
        ID_FIELD.setPhysicalName(ICatalogAPI.ID_FIELD_NAME);
        ID_FIELD.setDescripcion("Campos Clave de la entidad (Uso interno del sistema)");
        //ID_FIELD.setSize(10);
        ID_FIELD.setType(InternalDataType.LONG_INTEGER);
        ID_FIELD.setNullable(false);
        return ID_FIELD;
    }

    private EntityField getNumExpFieldInstace(int id){ 
        EntityField NUMEXP_FIELD = new EntityField();
        NUMEXP_FIELD = new EntityField();
        NUMEXP_FIELD.setId(id);
        //NUMEXP_FIELD.setLogicalName(NUMEXP_FIELD_NAME);
        NUMEXP_FIELD.setPhysicalName(ICatalogAPI.NUMEXP_FIELD_NAME);
        NUMEXP_FIELD.setSize(30);
        NUMEXP_FIELD.setDescripcion("Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)");
        NUMEXP_FIELD.setType(InternalDataType.SHORT_TEXT);
        NUMEXP_FIELD.setNullable(false);
        return NUMEXP_FIELD;
    }
	
	
	

	public boolean containsFieldByName(String nameField)
	{
		int i=0; 
		
		for(i=0; i<fields.size(); i++){
			
			EntityField ent= (EntityField) fields.get(i);
			if(ent.getPhysicalName().equalsIgnoreCase(nameField))
				return true;
			
		}
		return false;
	}
	
	public static EntityDef parseEntityDef(String xml){
		EntityDef ret = null;
		try {
			URL rulesUrl = EntityDef.class.getClassLoader().getResource(
					"ieci/tdw/ispac/ispaclib/entity/rulesEntityDef.xml");
            
            //URL rulesURL2 = new URL("file:/C:/rules.xml");
            
			Digester digester = DigesterLoader.createDigester(rulesUrl);
			//digester.parse(xmlDefinition);
			//ret = (EntityDef)digester.parse(new FileInputStream("C://entidad.xml"));
			ret = (EntityDef) digester.parse(new StringReader(xml));
            
			
		} catch (MalformedURLException e1) {
			logger.error(e1);
			
		} catch (Exception e2) {
			logger.error(e2);
		}
		return ret;
	}
    
	public boolean isEditable() {
		return editable.equalsIgnoreCase(TRUE);
	}
	
	public String getEditable(){
		return editable;
	}
	
	public void setEditable(boolean editable){
		if (editable) 
			this.editable = TRUE;
		else
			this.editable = FALSE;
	}
	
	public void setEditable(String editableString){
		setEditable (TRUE.equalsIgnoreCase(editableString));
	}
	
	public void setDropable(boolean dropable){
		if (dropable) 
			this.dropable = TRUE;
		else
			this.dropable = FALSE;
	}
	
	public void setDropable(String dropableString){
		setDropable (TRUE.equalsIgnoreCase(dropableString));
	}
	
	public boolean isDropable() {
		return dropable.equalsIgnoreCase(TRUE);
	}
	
	public String getDropable(){
		return dropable;
	}
	
//	public boolean isTableSystem() {
//		return tableSystem.equalsIgnoreCase(TRUE);
//	}
	
//	public String getTableSystem(){
//		return tableSystem;
//	}
	
//	public void setTableSystem(boolean tableSystem){
//		if (tableSystem) 
//			this.tableSystem = TRUE;
//		else
//			this.tableSystem = FALSE;
//	}
//	
//	public void setTableSystem(String tableSystemString){
//		setTableSystem ("S".equalsIgnoreCase(tableSystemString));
//	}

	public EntityStatus getStatus() {
		return status;
	}

	public void setStatus(String statusId){
		this.status = EntityStatus.getInstance(Integer.parseInt(statusId));
	}
	
	public void setStatus(EntityStatus status) {
		this.status = status;
	}



	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {
		
		StringBuffer buffer = new StringBuffer();

		if (StringUtils.isNotBlank(editable)) {
			buffer.append(XmlTag.newTag("editable", editable, false));
		}
		
		if (StringUtils.isNotBlank(dropable)) {
			buffer.append(XmlTag.newTag("dropable", dropable, false));
		}		
		
		buffer.append(XmlTag.newTag("status", new Integer(status.getId()).toString(), false));
		//buffer.append(XmlTag.newTag("tableSystem", tableSystem, false));

		buffer.append(XmlTag.newTag("fields", fields));

		if (!CollectionUtils.isEmpty(indexes)) {
			buffer.append(XmlTag.newTag("indexes", indexes));
		}

		if (!CollectionUtils.isEmpty(validations)) {
			buffer.append(XmlTag.newTag("validations", validations));
		}

		return XmlTag.newTag("entity", buffer.toString(), 
				XmlTag.newAttr("version", version));
	}

	
//	public String getLogicalName() {
//		return logicalName;
//	}
//
//
//	public void setLogicalName(String logicalName) {
//		this.logicalName = logicalName;
//	}
//
//
//	public String getPhysicalName() {
//		return physicalName;
//	}
//
//
//	public void setPhysicalName(String physicalName) {
//		this.physicalName = physicalName;
//	}


	public List getFields() {
		return fields;
	}

	public EntityField getField(String fieldName) {
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			EntityField aField = (EntityField) iter.next();
			if (StringUtils.equalsIgnoreCase(aField.getPhysicalName(),fieldName))
				return aField;
		}
		return null;
	}	
	
	public List getSimpleFields(){
		return getFields(false);
	}
	
	public List getMultivalueFields() {
		return getFields(true);
	}

	public List getMultivalueFields(List dataType) {
		List ret = new ArrayList();
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			EntityField aField = (EntityField) iter.next();
			if (aField.isMultivalue() && dataType.contains(aField.getType()))
				ret.add(aField);
		}
		return ret;
	}	
	
	private List getFields(boolean isMultivalue){
		List list = new ArrayList();
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			EntityField field = (EntityField) iter.next();
			if (field.isMultivalue() == isMultivalue)
				list.add(field);
		}
		return list;		
	}
	
	
	public List getFields(InternalDataType dataType){
		List ret = new ArrayList();
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			EntityField aField = (EntityField) iter.next();
			if (aField.getType().equals(dataType))
				ret.add(aField);
			
		}
		return ret;
	}

	public List getSimpleFieldsNotLOB(){
		List ret = new ArrayList();
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			EntityField aField = (EntityField) iter.next();
			if (!aField.isMultivalue() && !aField.getType().equals(InternalDataType.LONG_BIN) && !aField.getType().equals(InternalDataType.LONG_TEXT))
				ret.add(aField);
			
		}
		return ret;
	}
	
	
	public EntityField findField(int fieldId) {
		EntityField field = null;
		
		EntityField aux = null;
		for (int i = 0; (field == null) && (i < fields.size()); i++) {
			aux = (EntityField) fields.get(i);
			if (fieldId == aux.getId()) {
				field = aux;
			}
		}
		return field;
	}

	public EntityField findField(String fieldName) {
		EntityField field = null;
		
		EntityField aux = null;
		for (int i = 0; (field == null) && (i < fields.size()); i++) {
			aux = (EntityField) fields.get(i);
			if (StringUtils.equalsIgnoreCase(fieldName, aux.getPhysicalName())){
				field = aux;
				break;
			}
		}
		return field;
	}
	
	public void addField(EntityField field) {
			fields.add(field);
	}
	
	
	public void saveField(EntityField field){
		
		int i;
		boolean encontrado=false;
		for(i=0; i<fields.size() &&!encontrado; i++){
			if(((EntityField)fields.get(i)).getId()==field.getId()){
				fields.set(i, field);
				encontrado=true;
			}
		}
	}
	public void removeField(EntityField field) {
		List fields = getFields();
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
			EntityField aField = (EntityField) iter.next();
			if (aField.getId()==field.getId()){
				fields.remove(aField);
				break;
            }
		}
	}

	public void removeIndex(EntityIndex index) {
		List indexes = getIndexes();
		for (Iterator iter = indexes.iterator(); iter.hasNext();) {
            EntityIndex aIndex = (EntityIndex) iter.next();
			if (aIndex.getId()==index.getId()){
				indexes.remove(aIndex);
				break;
            }
		}
	}

	public void removeIndexes(){
		indexes = new ArrayList();
	}

	public void removeValidations(){
		validations = new ArrayList();
	}

	public void removeValidation(EntityValidation validation) {
		List validations = getValidations();
		for (Iterator iter = validations.iterator(); iter.hasNext();) {
			EntityValidation aValidation = (EntityValidation) iter.next();
			if (aValidation.getFieldId()==validation.getFieldId()){
                validations.remove(aValidation);
				break;
            }
		}
	}

	public List getIndexes() {
		return indexes;
	}


	public void addIndex(EntityIndex index) {
		indexes.add(index);
	}


	public List getValidations() {
		return validations;
	}


	public void addValidation(EntityValidation validation) {
		validations.add(validation);
	}

	
	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}
    
    public void setFields(List fields){
        this.fields = fields;
    }
    public void setIndexes(List indexes){
        this.indexes = indexes;
    }
    public void setValidations(List validations){
        this.validations = validations;
    }
    
    public HashMap fieldsToMap(){
        HashMap mapFields = new HashMap();
        for (Iterator iter = fields.iterator(); iter.hasNext();) {
            EntityField field = (EntityField) iter.next();
            mapFields.put(new Integer(field.getId()), field);
        }
        return mapFields;
    }

    public HashMap validationsToMapByIdField(){
        HashMap mapValidations = new HashMap();
        for (Iterator iter = validations.iterator(); iter.hasNext();) {
            EntityValidation validation = (EntityValidation) iter.next();
            mapValidations.put(new Integer(validation.getFieldId()), validation);
        }
        return mapValidations;
    }
    
    public Map mandatoryValidationIdsMap(){
        HashMap mapMandatoryValidationIds = new HashMap();
        for (Iterator iter = validations.iterator(); iter.hasNext();) {
            EntityValidation validation = (EntityValidation) iter.next();
            if (validation.isMandatory()) {
            	mapMandatoryValidationIds.put(String.valueOf(validation.getFieldId()), validation);
            }
        }
        return mapMandatoryValidationIds;
    }
    
	public String generateXmlParameters() {
		
		// Generar los parámetros para las validaciones de sustituto de la entidad
		ParametersDef parametersDef = generateParametersDef();
		
		if (!parametersDef.getEntities().isEmpty()) {
			
			return parametersDef.toXml();
		}

		return "";
	}
	
	public ParametersDef generateParametersDef() {
		
		// Generar los parámetros para las validaciones de sustituto de la entidad
		ParametersDef parametersDef = new ParametersDef();
		
		List validations = getValidations();
		if ((validations != null) &&
			(!validations.isEmpty())) {
			
			Iterator it = validations.iterator();
			while (it.hasNext()) {
				
				EntityValidation validation = (EntityValidation) it.next();
				if (!StringUtils.isEmpty(validation.getTable())) {
					
					// Comprobar si la tabla es de sustituto
					String tableType = validation.getTableType();
					if (!StringUtils.isEmpty(tableType)) {
						
						EntityType entityType = EntityType.getInstance(Integer.parseInt(tableType));
						if ((entityType.equals(EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE) ||
							(entityType.equals(EntityType.SYSTEM_VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE)))) {
							
	 						// Obtener el campo de la entidad que guardará el valor
	 						EntityField entityField = findField(validation.getFieldId());
	 						
	 						EntityParameterDef entityParameterDef = new EntityParameterDef(EntityAppConstants.ENTITY_TYPE_VALIDATION,
	 																			  		   validation.getTable(),
	 																			  		   EntityAppConstants.RELATION_TYPE_FIELD,
	 																			  		   entityField.getPhysicalName().toUpperCase(),
	 																			  		   ICatalogAPI.VALOR_FIELD_NAME.toUpperCase());
	 						
	 						parametersDef.addEntity(entityParameterDef);
						}
					}
				}
			}
		}
		
		return parametersDef;
	}

	public int getMaxId(){
        int max = 0;
		for (Iterator iter = fields.iterator(); iter.hasNext();) {
            EntityField field = (EntityField) iter.next();
            if (field.getId() > max)
            	max = field.getId();
        }
		return max;
	}
	
}