package ieci.tdw.ispac.ispaclib.entity.def;

import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;


public class EntityField extends XmlObject {

	private static final String KEY = "ID";
	protected static final Properties PROPERTIES = new Properties();
	
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "ID"),
				new Property(2, "LOGICALNAME"),
				new Property(2, "PHYSICALNAME"),
				new Property(3, "TYPE"),
				new Property(4, "SIZE"),
				new Property(5, "PRECISION"),
				new Property(6, "DESCRIPCION"),
				new Property(7, "NULLABLE"),
				new Property(8, "MULTIVALUE"),
				new Property(9, "RANGOMIN"),
				new Property(10, "RANGOMAX"),
				new Property(11, "DOCUMENTARYSEARCH")
		});
	}

	
	
	
	/** Identificador del campo. */
	private int id = 1;
//	/** Nombre lógico del campo. */
	private String logicalName = null;
	
	/** Nombre físico del campo. */
	private String physicalName = null;
	
	/** Tipo del campo. */
	private InternalDataType type = InternalDataType.SHORT_TEXT;
	
	/** Tamaño del campo. */
	private int size = 0;
	
	private int precision = 0;
	
	private String descripcion = "";
	
	private boolean nullable = true;
	
	private boolean multivalue = false;
	
	/** Para aquellos casos en los que el usuario quiera que un tipo numérico se valide contra un rango**/
	private double rangoMax=Double.MAX_VALUE;
	
	private double rangoMin=Double.MIN_VALUE;
	
	/**Indica si el campo tiene asociado una busqueda documental o no*/
	
	private boolean documentarySearch=false;
	
	
	

	/**
	 * Constructor.
	 *
	 */
	public EntityField() {
		super(PROPERTIES, KEY);
	}

	public EntityField(int id, String logicalName, String physicalName, String descripcion, InternalDataType type, int size, int precision, boolean multivalue, double rangoMin, double rangoMax, boolean documentarySearch) {
		super(PROPERTIES, KEY);
		this.id = id;
		this.logicalName = logicalName;
		this.physicalName = physicalName;
		this.type = type;
		this.size = size;
		this.precision = precision;
		this.descripcion = descripcion;
		this.multivalue = multivalue;
		this.rangoMin=rangoMin;
		this.rangoMax=rangoMax;
		this.documentarySearch=documentarySearch;	
		}
	public EntityField(int id, String logicalName, String physicalName, String descripcion, InternalDataType type, int size, int precision) {
		super(PROPERTIES, KEY);
		this.id = id;
		this.logicalName = logicalName;
		this.physicalName = physicalName;
		this.type = type;
		this.size = size;
		this.precision = precision;
		this.descripcion = descripcion;

	}

	


	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {
		
		StringBuffer buffer = new StringBuffer();

//		if (StringUtils.isNotBlank(logicalName)) {
//			buffer.append(XmlTag.newTag("logicalName", logicalName, true));
//		}

		buffer.append(XmlTag.newTag("physicalName", physicalName, false));

		if (StringUtils.isNotBlank(descripcion)) {
			buffer.append(XmlTag.newTag("descripcion", descripcion, true));
		}

		buffer.append(XmlTag.newTag("type", type.getId()));
		
		if (size > 0) {
			buffer.append(XmlTag.newTag("size", size));
		}

		if (precision > 0) {
			buffer.append(XmlTag.newTag("precision", precision));
		}
		
		if (nullable) {
			buffer.append(XmlTag.newTag("nullable", EntityDef.TRUE));
		}
		else {
			buffer.append(XmlTag.newTag("nullable", EntityDef.FALSE));
		}
		
		if (documentarySearch) {
			buffer.append(XmlTag.newTag("documentarySearch", true));
		}
		else {
			buffer.append(XmlTag.newTag("documentarySearch", false));
		}
				
		if (multivalue) {
			buffer.append(XmlTag.newTag("multivalue", EntityDef.TRUE));
		}
		else {
			buffer.append(XmlTag.newTag("multivalue", EntityDef.FALSE));
		}
		
		if(rangoMin!=Double.MIN_VALUE){
		   buffer.append(XmlTag.newTag("rangoMin", Double.toString(rangoMin)));
		}
		if(rangoMax!=Double.MAX_VALUE){
			buffer.append(XmlTag.newTag("rangoMax", Double.toString(rangoMax)));
		}
		
		return XmlTag.newTag("field", buffer.toString(), id);
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLogicalName() {
		return logicalName;
	}


	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}


	public String getPhysicalName() {
		return physicalName;
	}


	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}

	public void setSize(String size) {
		this.size = Integer.parseInt(size);
	}
	
	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public void setPrecision(String precision) {
		this.precision = Integer.parseInt(precision);
	}

	public InternalDataType getType() {
		return type;
	}

	public void setType(InternalDataType type) {
		this.type = type;
	}
	
	public void setType(String typeId){
		this.type = InternalDataType.getInstance(Integer.parseInt(typeId));
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		if(StringUtils.isNotEmpty(descripcion)){
		this.descripcion = descripcion;
		}
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public void setNullable(String nullableString){
		setNullable(EntityDef.TRUE.equalsIgnoreCase(nullableString));
	}

	public double getRangoMax() {
		return rangoMax;
	}
	
	public void setRangoMin(){
		this.rangoMin=Double.MIN_VALUE;
	}
	
	public void setRangoMax(){
		this.rangoMax=Double.MAX_VALUE;
	}

	public void setRangoMax(double rangoMax) {
		this.rangoMax = rangoMax;
	}

	public double getRangoMin() {
		return rangoMin;
	}

	public void setRangoMin(double rangoMin) 
	{
		this.rangoMin = rangoMin;
	}
	
	public boolean isDocumentarySearch() {
		return documentarySearch;
	}
	
	public boolean getDocumentarySearch(){
		return documentarySearch;
	}

    public void setDocumentarySearch(String documentarySearch){
    	if(documentarySearch.equalsIgnoreCase("S")){
    		this.documentarySearch=true;
    	}
    	else
    		this.documentarySearch=false;
    }
	public void setDocumentarySearch(boolean documentarySearch) {
		
		this.documentarySearch = documentarySearch;
	}
		

	public boolean isMultivalue() {
		return multivalue;
	}

	public void setMultivalue(boolean multivalue) {
		this.multivalue = multivalue;
	}

	public void setMultivalue(String multivalueString) {
		setMultivalue(EntityDef.TRUE.equalsIgnoreCase(multivalueString));
	}

}