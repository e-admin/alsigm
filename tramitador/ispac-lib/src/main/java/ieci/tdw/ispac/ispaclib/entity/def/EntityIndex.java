package ieci.tdw.ispac.ispaclib.entity.def;

import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityIndex extends XmlObject {
	
	private static final String KEY = "ID";
	private static final Properties PROPERTIES = new Properties();
	
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "ID"),
				new Property(2, "NAME"),
				new Property(3, "PRIMARYKEY"),
				new Property(4, "UNIQUE"),
				new Property(5, "DOCUMENTAL"),
				new Property(6, "FIELDIDS")
		});
	}
	
	public final static String TRUE = "S";
	public final static String FALSE = "N";
	
	/** Identificador del índice. */
	private int id = 1;
	
	/** Nombre del índice. */
	private String name = null;
	
	/** Indica si es índice de clave primaria. */
	private String primaryKey = FALSE;
	
	/** Indica si es índice de clave única. */
	private String unique = FALSE;
	
	/** Indica si es índice documental. */
	private String documental = FALSE;
	
	/** Lista de identificadores de campos que componen el índice. */
	private List fieldIds = new ArrayList();
	
	/**
	 * Constructor.
	 *
	 */
	public EntityIndex() {
		super(PROPERTIES, KEY);
	}
	
	public void addFieldId(int fieldId){
		fieldIds.add(new Integer(fieldId));
	}

	public void addFieldId(String fieldId){
		addFieldId(Integer.parseInt(fieldId));
	}
    
    public void addFieldIdInDigester(int id){
        addFieldId(id);
    }

    public void addId(String fieldId){
        addFieldId(Integer.parseInt(fieldId));
    }
	
	public EntityIndex(int id, String nombre, boolean primaryKey, boolean uniqueKey, boolean documental, List fieldsIds) {
		super(PROPERTIES, KEY);
		setId(id);
		setName(nombre);
		setPrimaryKey(primaryKey);
		setUnique(uniqueKey);
		setDocumental(documental);
		setFieldIds(fieldIds);
	}

	public EntityIndex(int id, String nombre, boolean primaryKey, boolean uniqueKey, boolean documental) {
		super(PROPERTIES, KEY);
		setId(id);
		setName(nombre);
		setPrimaryKey(primaryKey);
		setUnique(uniqueKey);
		setDocumental(documental);
	}
	
	
	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {
	
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(XmlTag.newTag("name", name, true));

		if (primaryKey.equals(TRUE)) {
			buffer.append(XmlTag.newTag("primaryKey", primaryKey));
		} 

		if (unique.equals(TRUE)) {
			buffer.append(XmlTag.newTag("uniqueKey", unique));
		}

		if (documental.equals(TRUE)) {
			buffer.append(XmlTag.newTag("documental", documental));
		}

		if (!CollectionUtils.isEmpty(fieldIds)) {
			StringBuffer fieldsBuff = new StringBuffer();
			for (int i = 0; i < fieldIds.size(); i++) {
				fieldsBuff.append(XmlTag.newTag("field", null, 
						XmlTag.newAttr("idField", String.valueOf(fieldIds.get(i)))));
			}
			buffer.append(XmlTag.newTag("fields", fieldsBuff.toString()));
		}
		
		return XmlTag.newTag("index", buffer.toString(), id);
	}


	public boolean isDocumental() {
		return documental.equals(TRUE);
	}

	public void setDocumental(String primaryKey) {
		setDocumental(documental.equalsIgnoreCase(TRUE));
	}
	
	public void setDocumental(boolean documental) {
		if (documental)
			this.documental = TRUE;
		else
			this.documental = FALSE;
	}


	public List getFieldIds() {
		return fieldIds;
	}


	public void setFieldIds(List fieldIds) {
		this.fieldIds = fieldIds;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

    /** METODO PARA EL DIGESTER **/
    public void setIdField(int fieldId){
        addFieldId(fieldId);
    }

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isPrimaryKey() {
		return primaryKey.equals(TRUE);
	}

	public void setPrimaryKey(String primaryKey) {
		setPrimaryKey(primaryKey.equalsIgnoreCase(TRUE));
	}
	public void setPrimaryKey(boolean primaryKey) {
		if (primaryKey)
			this.primaryKey = TRUE;
		else
			this.primaryKey = FALSE;
	}

	public boolean isUnique() {
		return this.unique.equals(TRUE); 
	}

	public void setUnique(String uniqueKey) {
		setUnique(uniqueKey.equalsIgnoreCase(TRUE));
	}

	public void setUnique(boolean uniqueKey) {
		if (uniqueKey)
			this.unique = TRUE;
		else
			this.unique = FALSE;
	}
	
	public boolean isOverField(int fieldId){
		List ids = getFieldIds();
		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			Integer aId = (Integer) iterator.next();
			if (aId.equals(new Integer(fieldId))){
				return true;
			}
		}
		return false;
	}
}
