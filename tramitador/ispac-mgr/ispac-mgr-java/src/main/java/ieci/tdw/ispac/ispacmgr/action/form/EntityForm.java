package ieci.tdw.ispac.ispacmgr.action.form;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.app.ListEntityApp;
import ieci.tdw.ispac.ispaclib.app.MasterDetailEntityApp;
import ieci.tdw.ispac.ispaclib.app.SecondaryEntityApp;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.common.constants.EntityLockStates;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinDAO;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class EntityForm extends ValidatorEntityAppForm
{

	// Propiedades del formulario
	private HashMap property = new HashMap();

	// Lista de ItemBean de la misma entidad de catálogo
	private List items = new ArrayList();
	private BeanFormatter formatter = null;
	
	// Lista de ItemBean de documentos asociados al registro de la entidad
	private List entityDocuments = new ArrayList();
	private BeanFormatter entityDocumentsFormatter = null;


	
	/**
	 * Listado de propiedades multivalue que establecen se setean. 
	 */
	private List propertyMultiValuesSet = new ArrayList();
	
	
	private String actions;
	private String entity; 		// Identificador de la entidad en el catálogo de entidades
	private String secondaryEntity;
	private String key; 		// Identificador del registro
	private String readonly; 	// Sólo lectura
	private String readonlyReason; //Motivo por el cual el objeto está en sólo lectura
	private String[] multibox;
	private String block;
	private String criterio; 
	private String operador;
	private EntityApp mentityapp;
	
	

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public EntityForm()
	{
	    mentityapp=null;
		actions=null;
		entity=null;
		key=null;
		readonly=null;
		readonlyReason=null;
		multibox=null; 
	}
	
  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
  	propertyMultiValuesSet = new ArrayList();
  	super.reset(mapping, request);
  }		
	
	public void reset() {
		
		property.clear();
		items.clear();
		formatter=null;
	    mentityapp=null;
		actions=null;
		entity=null;
		key=null;
		readonly=null;
		readonlyReason=null;
		multibox=null;
	}

	public void setProperty(String key, String value)
	{
		if (value == null)
			property.put(key, "");
		else
			property.put(key, value);
	}

	public void setPropertyMultivalue(String key, String[] value)
	{
		if (value == null)
			property.put(key, "");
		else
			property.put(key, value);
		propertyMultiValuesSet.add(key);
	}	
	
	
	public String getProperty(String key)
	{
		if (!property.containsKey(key))
			return "";
		return property.get(key).toString();
	}

	public String[] getPropertyMultivalue(String key)
	{
		if (!property.containsKey(key))
			return new String[]{};
		return (String[])property.get(key);
	}
	
	
	public void setActions(String newString)
	{
		this.actions = newString;
	}

	public String getActions()
	{
		return this.actions;
	}

	public String getEntity()
	{
		return this.entity;
	}

	public void setEntity(String entity)
	{
	    this.entity=entity;
	}

	public String getKey()
	{
		return this.key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getReadonly()
	{
		return this.readonly;
	}

	public void setReadonly(String readonly)
	{
		this.readonly = readonly;
	}

	public List getItems()
	{
		return this.items;
	}

	public void setItems(List items)
	{
		this.items = items;
	}

	public BeanFormatter getFormatter()
	{
		return this.formatter;
	}

	public void setFormatter(BeanFormatter formatter)
	{
		this.formatter = formatter;
	}
	
	public List getEntityDocuments()
	{
		return entityDocuments;
	}

	public void setEntityDocuments(List entityDocuments)
	{
		this.entityDocuments = entityDocuments;
	}

	public BeanFormatter getEntityDocumentsFormatter()
	{
		return entityDocumentsFormatter;
	}

	public void setEntityDocumentsFormatter(BeanFormatter entityDocumentsFormatter)
	{
		this.entityDocumentsFormatter = entityDocumentsFormatter;
	}

	// Asigna los valores de las propiedades de la entidad
	// al formulario.
	public void setEntityApp(EntityApp entity, Locale locale)
	throws ISPACException
	{
	    mentityapp=entity;
	    setEntityAppName(entity.getAppName());

		// Salva el identificador de la entidad
		setEntity(Integer.toString(entity.getMainEntity()));
		
		if (entity instanceof SecondaryEntityApp) {
			setSecondaryEntity( String.valueOf(((SecondaryEntityApp)entity).getSecondaryEntityId()) );
		}
		
		// Salva el identificador del registro si el item
		// no es un join.
		if (!(entity.getItem() instanceof TableJoinDAO))
		{
			setKey(Integer.toString(entity.getEntityRegId()));
		}

		
//		// Representación de un número decimal
//		NumberFormat formatter = new DecimalFormat( "#,###.############", 
//				new DecimalFormatSymbols(locale));
		
		Properties properties = entity.getProperties();
		Iterator iterator = properties.iterator();
		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			if (property.isMultivalue()){
				//setProperty(property.getName(), entity.getMainItem().get(key));
				//Object[] array = (Object[])entity.getMainItem().get(property.getRawName());
				Object[] array = (Object[])entity.getMultivalue(property.getName());
				if(!ArrayUtils.isEmpty(array)){
					String[] st = new String[array.length];
					for (int i = 0; i < array.length; i++) {
						if (array[i] == null){
							st[i] ="";
						}else{
							st[i] = array[i].toString();
						}
					}
					setPropertyMultivalue(property.getName(), formatNumericValue(property.getName(), st, locale));
				}
				
			}else{
				String key = property.getName();
				String value = entity.getValue(key);
				
				if (!StringUtils.isEmpty(value)) {
					
					// Formateo de dato numérico (entero corto y decimal) contra la definición XML
					setProperty(property.getName(), formatNumericValue(key, value, locale));
	
					// Antes: formateo de dato numérico contra la BD
					// setProperty(property.getName(), formatNumericValue(property.getType(), value, formatter));
				}
			}
		}

		// Salva en la propiedad items los objetos ItemBean relacionados
		if (entity instanceof MasterDetailEntityApp)
		{
			MasterDetailEntityApp masterDetailEntityApp = (MasterDetailEntityApp) entity;
			
			setItems(masterDetailEntityApp.getItemBeanList());
			setFormatter(masterDetailEntityApp.getBeanFormatter());
		}
		else if (entity instanceof ListEntityApp)
		{
			ListEntityApp listEntityApp = (ListEntityApp) entity;
			
			setItems(listEntityApp.getItemBeanList());
			setFormatter(listEntityApp.getBeanFormatter());
		}
		
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();

		if (getFormatter() == null) {
		setFormatter(factory.getFormatter(entity));
		}
		
		// Documentos asociados al registro de la entidad
		setEntityDocuments(entity.getEntityDocuments());
		String sEntityDocumentsFormatter = entity.getEntityDocumentsFormatter();
		if (sEntityDocumentsFormatter != null && sEntityDocumentsFormatter.trim().length() != 0)
		{
			setEntityDocumentsFormatter(factory.getFormatter(entity.getPath() + sEntityDocumentsFormatter));
		}
		
		// Bloquear la entidad
		if (StringUtils.equals(entity.getString("BLOQUEO"), EntityLockStates.TOTAL_LOCK)) {
			setReadonly("true");
			setReadonlyReason(""+StateContext.READONLYREASON_LOCK);
		}
	}


	public EntityApp getEntityApp()
	{
	    return mentityapp;
	}

	// Asigna los valores de las propiedades del formulario
	// a las propiedades de la entidad.
	public void processEntityApp(EntityApp entity)
	throws ISPACException
	{
		Iterator iterator = property.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry entry = (Entry) iterator.next();
			String key = (String) entry.getKey();
			
			Object object = entry.getValue();
			
			if (object instanceof String[]) {
				String[] value = (String[])entry.getValue();
				if (!propertyMultiValuesSet.contains(key)){
					//Como este campo multivalor no se ha enviado en esta peticion del formulario que esta en sesion, se debe limpiar 
					//Se limpia el valor en el EntityApp
					entity.setValue(key,(String)null);
					//Se elimina la propiedad del formulario
					iterator.remove();
				}else{
					if (!StringUtils.isEmpty(value)){ 
						value = unformatNumericValue(key, value, entity.getLocale());
					}
					
					//Si hay campos sin valor no se guardan
					//entity.setValue(key, value, true);
					ArrayList list = new ArrayList(Arrays.asList(value));
					Iterator it = list.iterator();
					while(it.hasNext()){
						String valor = (String) it.next();
						if (StringUtils.isEmpty(valor))
							it.remove();
					}
					entity.setValue(key, ArrayUtils.toStringArray(list.toArray()), true);
				}
			}else{
				String value = (String) entry.getValue();
				if (!StringUtils.isEmpty(value)) {
					// Desformateo de dato numérico (entero corto y decimal) contra la definición XML
					value = unformatNumericValue(key, value, entity.getLocale());
				}
				
				entity.setValue(key, value);
			}
		}
		
		mentityapp = entity;
	}
	
	//Utilizado en el evento asociado al guardar una entidad. Dentro de la regla necesitamos estas propiedades
	
	/**
	 * @return Xml representativo de las propiedades del Form
	 */
	public String toXML(){
	    
	    String xml = "<properties></properties>";
	    XmlFacade xmlFacade = new XmlFacade(xml);

        Iterator it = property.entrySet().iterator();
        Entry entry = null;
        String key = null;
        while(it.hasNext()){
            entry = (Entry)it.next();
            key = (""+entry.getKey()).replaceAll(":",".");
            //xmlFacade.set("/properties/"+key, ""+entry.getValue());
            xmlFacade.set("/properties/"+key, "<![CDATA[" + entry.getValue() + "]]>");
        }
	    
	    return xmlFacade.toString();

	}

    public Map getMapProperties() {
        return property;
    }
    
    public String[] getMultibox() {
        return multibox;
    }

    public void setMultibox(String[] newMultibox) {
    	multibox = newMultibox;
    }
    
    /**
     * Formateo de campo numérico (entero corto y decimal)
     * a partir del tipo de dato en BD.
     * 
     * @param type Tipo de dato
     * @param value Valor
     * @param formatter Formato
     * @return Dato con formato
     */
    private String formatNumericValue(String key, String value, Locale locale) {
    	
    	// Obtener la definición XML del campo
    	if (getEntityApp() != null) {
    		
	    	EntityField fieldDef = (EntityField) getEntityApp().getFieldDef(key);
	    	if (fieldDef != null) {
	    		
	    		InternalDataType type = fieldDef.getType();
	    		if (type.equals(InternalDataType.SHORT_INTEGER)) {
	    			
	    			// Entero con separador de miles
	    			BigInteger numeric = new BigInteger(value);
					return getNumberFormat(fieldDef, locale).format(numeric.longValue());
	    		}
	    		else if ((type.equals(InternalDataType.SHORT_DECIMAL)) ||
	    				 (type.equals(InternalDataType.LONG_DECIMAL))) {
	    			
	    			// Decimal con separador de miles y decimales
	    			BigDecimal numeric = new BigDecimal(value);
					return getNumberFormat(fieldDef, locale).format(numeric.doubleValue());
	    		}
	    	}
    	}
		
		return value;
    }
    
	private NumberFormat getNumberFormat(EntityField entityField, Locale locale) {
		
		String pattern = "#,###.############"; 
			
		if ((entityField != null) && (entityField.getPrecision() > 0)) {
			pattern = "#,###." + StringUtils.leftPad("", entityField.getPrecision(), "0");
		}
		
		return new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
	}

    /**
     * Formateo de campos numéricos (entero corto y decimal)
     * a partir del tipo de dato en BD.
     * 
     * @param type Tipo de dato
     * @param value datos multivalor
     * @param formatter Formato
     * @return Dato con formato
     */
	private String[] formatNumericValue(String key, String[] value, Locale locale) {
		for (int i = 0; i < value.length; i++) {
			value[i] = formatNumericValue(key, value[i], locale);
		}
		return value;
	}    
    
    /**
     * Formateo de campo numérico a partir del tipo de dato en BD.
     * 
     * @param type Tipo de dato
     * @param value Valor
     * @param formatter Formato
     * @return Dato con formato
     */
    /*
    private String formatNumericValue(int type, String value, NumberFormat formatter) {
    	
		switch (type) {
			
			//case Types.BIT:
			//case Types.TINYINT:
			//case Types.SMALLINT:
			//case Types.INTEGER:
			//case Types.BIGINT:
			//	return formatter.format(Long.parseLong(value));

			case Types.REAL:
				Float real = new Float(Float.parseFloat(value));
				return formatter.format(real.doubleValue());
			case Types.FLOAT:
			case Types.DOUBLE:
				return formatter.format(Double.parseDouble(value));
			case Types.DECIMAL:
				BigDecimal decimal = new BigDecimal(value);
				return formatter.format(decimal.doubleValue());
			case Types.NUMERIC:
				if (value.indexOf('.') != -1) {
					BigDecimal numeric = new BigDecimal(value);
					return formatter.format(numeric.doubleValue());
				}
		}
		
		return value;
    }
    */
        
    /**
     * Desformateo de campo numérico (entero corto y decimal)
     * a partir de la definición XML.
     * 
     * @param key Clave del campo
     * @param value Valor
     * @return Dato sin formato
     */
    private String unformatNumericValue(String key, String value, Locale locale) {
    	
    	// Obtener la definición XML del campo
    	if (getEntityApp() != null) {
    		
	    	EntityField fieldDef = (EntityField) getEntityApp().getFieldDef(key);
	    	if (fieldDef != null) {
	    		
	    		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale); 
	    		
	    		InternalDataType type = fieldDef.getType();
	    			
    			if (type.equals(InternalDataType.SHORT_INTEGER) 
    					|| type.equals(InternalDataType.SHORT_DECIMAL) 
    					|| type.equals(InternalDataType.LONG_DECIMAL)) {
	    			
	    			// Eliminar separador de miles
	    			value = StringUtils.replace(value, String.valueOf(decimalFormatSymbols.getGroupingSeparator()), "");
	    			
	    			// Sustituir separador de decimales
	    			value = StringUtils.replace(value, String.valueOf(decimalFormatSymbols.getDecimalSeparator()), ".");
	    		}
	    	}
    	}
		
		return value;
    }
    
    private String[] unformatNumericValue(String key, String[] value, Locale locale) {
    	for (int i = 0; i < value.length; i++) {
			value[i] = unformatNumericValue(key, value[i], locale);
		}
    	return value;
    }
    /**
     * Desformateo de campo numérico a partir del tipo de dato en BD.
     * 
     * @param type Tipo de dato
     * @param value Valor
     * @return Dato sin formato
     */
    /*
    private String unformatNumericValue(int type, String value) {
    	
		switch (type) {
			
			//case Types.BIT:
			//case Types.TINYINT:
			//case Types.SMALLINT:
			//case Types.INTEGER:
			//case Types.BIGINT:

			case Types.REAL:
			case Types.FLOAT:
			case Types.DOUBLE:
			case Types.NUMERIC:
			case Types.DECIMAL:
				return StringUtils.replace(value, ".", "").replace(',', '.');
		}
		
		return value;
    }
    */
    
    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }

	public String getReadonlyReason() {
		return readonlyReason;
	}

	public void setReadonlyReason(String readonlyReason) {
		this.readonlyReason = readonlyReason;
	}
	
	public String isMultiboxChecked(String value) {
		
		String[] selectedIds = this.getMultibox();
		if (selectedIds != null) {
			
			for (int i = 0; i < selectedIds.length; i++) {
				
				if (selectedIds[i].equals(value)) {
					return "checked";
				}
			}
		}
		
		return "";
	}

	public String getSecondaryEntity() {
		return secondaryEntity;
	}

	public void setSecondaryEntity(String secondaryEntity) {
		this.secondaryEntity = secondaryEntity;
	}

}