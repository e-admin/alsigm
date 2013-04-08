package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.validations.CCCValidator;
import ieci.tdw.ispac.ispaclib.validations.EmailValidator;
import ieci.tdw.ispac.ispaclib.validations.EqualType;
import ieci.tdw.ispac.ispaclib.validations.IValidator;
import ieci.tdw.ispac.ispaclib.validations.NifCifNieValidator;

import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SimpleEntityApp extends EntityApp {

	private static final long serialVersionUID = -7149943252487105530L;

	public SimpleEntityApp(ClientContext context) {
		super(context);
	}

	public boolean validate() throws ISPACException { 
		
		//Comprobamos la obligatoriedad de los campos multivalor.
		//Se comprueba si siendo obligatorio no se da de alta ninguno.
		Properties properties = getItem().getProperties();
		Iterator it = properties.iterator();
		while(it.hasNext()){
			Property property = (Property)it.next();
			if (   property.isMultivalue() 
				&& isRequired(property.getName()) 
			    && ArrayUtils.isEmpty((Object[])mitem.get(property.getName()))
			   ){
				ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.required", new String[] {getLabel(property.getName())});
				addError(error);
			}
		}
		
		if ((getErrors() != null) && (!getErrors().isEmpty())) {
			return false;
		}
		else {
			return true;
		}
	}

	public void initiate() throws ISPACException {
		
		/*
		String autorNameField = null;
		String faltaNameField = null;
		if (getMainEntity() == ICatalogAPI.ENTITY_CT_TASK
				|| getMainEntity() == ICatalogAPI.ENTITY_CT_TYPEDOC
				|| getMainEntity() == ICatalogAPI.ENTITY_CT_TEMPLATE
				|| getMainEntity() == ICatalogAPI.ENTITY_CT_SEARCHFORM) {
			if (getString("ID") == null) {
				if (getMainEntity() == ICatalogAPI.ENTITY_CT_TASK) {
					autorNameField = "AUTOR";
					faltaNameField = "FCREACION";
				}else if (getMainEntity() == ICatalogAPI.ENTITY_CT_SEARCHFORM) {
					autorNameField = "AUTOR";
					faltaNameField = "FECHA";
				}
				else if (getMainEntity() == ICatalogAPI.ENTITY_CT_TYPEDOC) {
					autorNameField = "AUTOR";
					faltaNameField = "FECHA";
				}else if (getMainEntity() == ICatalogAPI.ENTITY_CT_TEMPLATE) {
					faltaNameField = "FECHA";
				}
				
				if (autorNameField != null) {
					setProperty(autorNameField, mContext.getUser().getName());
				}
				if (faltaNameField != null) {
					setProperty(faltaNameField, new Date());
				}
			}
		}
		*/
		
		String autorNameField = null;
		String faltaNameField = null;
		if (getMainEntity() == ICatalogAPI.ENTITY_CT_TEMPLATE
				|| getMainEntity() == ICatalogAPI.ENTITY_CT_SEARCHFORM || getMainEntity()==ICatalogAPI.ENTITY_CT_INFORMES) {
					
			if (getString("ID") == null) {
				
				if (getMainEntity() == ICatalogAPI.ENTITY_CT_SEARCHFORM) {
					autorNameField = "AUTOR";
					faltaNameField = "FECHA";
				}
				else if (getMainEntity() == ICatalogAPI.ENTITY_CT_TEMPLATE || getMainEntity()==ICatalogAPI.ENTITY_CT_INFORMES) {
					faltaNameField = "FECHA";
				}
				
				if (autorNameField != null) {
					setProperty(autorNameField, mContext.getUser().getName());
				}
				if (faltaNameField != null) {
					setProperty(faltaNameField, new Date());
				}
			}
		}
	}
	
	public void setLabels(String locale) throws ISPACException {}
	
	public List getEntityDocuments() throws ISPACException {
		
		return null;
	}

	public void setValue(String key, String value) throws ISPACException {
		if (mitem.getProperty(key) == null){
			valuesExtra.put(key, value);
		}else{
			setValue(key, new String[]{value}, false);
		}
	}
	
	public void setValue(String key, String[] value, boolean multivalue) throws ISPACException {

		Object[] object = new Object[value.length];
		// Obtener la definición XML del campo
    	EntityField fieldDef = (EntityField) getFieldDef(key);
    	
    	
    	// Obtener la propiedad de BD
		Property property = mitem.getProperty(key);
		if (property != null) {
		
			try {	
				for (int i = 0; i < value.length; i++) {
					//Compruebo en el caso de que sea un tipo no primitivo si esta bien definido
					
					if(!StringUtils.isEmpty(value[i]) && !validateNotPrimitiveType(key,value[i], fieldDef)){
						ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.invalid", new String[] {getLabel(property.getName())});
						addError(error);
					}
					
				
					if(!StringUtils.isEmpty(value[i]) &&  fieldDef!=null && fieldDef.getRangoMax()!=Double.MAX_VALUE ) {
						if(StringUtils.isDouble(value[i])){
						double valueUsr= Double.parseDouble(value[i]);
						double valueMax= fieldDef.getRangoMax();
						if(valueUsr> valueMax){
							
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.invalidRangoMax", new String[] {getLabel(property.getName())});
							addError(error);
							
						}
					}
				}	
			
					
					if(!StringUtils.isEmpty(value[i]) && fieldDef!=null && fieldDef.getRangoMin()!=Double.MIN_VALUE) {
					   if(StringUtils.isDouble(value[i])){
						double valueUsr= Double.parseDouble(value[i]);
						double valueMin= fieldDef.getRangoMin();
						if(valueUsr < valueMin){
							
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.invalidRangoMin", new String[] {getLabel(property.getName())});
							addError(error);
							
						}
						}
					}
					
					// Validación del tipo de dato
					object[i] = TypeConverter.parse(property.getType(), value[i]);				
						
					// Validación de dato obligatorio
					if ((isRequired(key)) &&
						(StringUtils.isEmpty(value[i]))) {
						
						ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.required", new String[] {getLabel(property.getName())});
						addError(error);
					}
					// Validación de tamaño
					// contra la definición XML del campo y contra la BD
					else 
						validateSize(fieldDef, property, value[i]);
				}

				// Tras pasar todas las validaciones
				// el dato se guarda en el registro de la entidad
				if (!multivalue)
					mitem.set(key, object[0]);
				else
					mitem.set(key, object);
			} 
			catch(ISPACException ie) {
				/*
				throw new ISPACException( "La propiedad " + property.getName() 
						+ " no admite el valor " + value,ie);
				*/
				
				ValidationError error = null;
				
				String label = getLabel(property.getName());
				if (StringUtils.isNotBlank(label)) {
					switch (property.getType()) {
					case Types.DATE: 		
						error = new ValidationError("property(" + property.getName() + ")", "errors.invalid.fieldDate", new String[] {label,TypeConverter.DATEFORMAT});
						break;
					case Types.TIME: 		
						error = new ValidationError("property(" + property.getName() + ")", "errors.invalid.fieldTime", new String[] {label,TypeConverter.TIMEFORMAT});
						break;	
					
					case Types.TIMESTAMP:	
						error = new ValidationError("property(" + property.getName() + ")", "errors.invalid.fieldTimestamp", new String[] {label,TypeConverter.TIMESTAMPFORMAT});
						break;	
					default:
							error = new ValidationError("property(" + property.getName() + ")", "errors.invalid", new String[] {label});
					}
					
				}
				else {
					switch (property.getType()) {
						
						// Fecha
						case Types.DATE:
							error = new ValidationError("property(" + property.getName() + ")", "errors.invalid.fieldDate" ,new String[] {label,TypeConverter.DATEFORMAT});
							break;
						
						case Types.TIME:
							error = new ValidationError("property(" + property.getName() + ")", "errors.invalidTime" ,new String[] {label,TypeConverter.TIMEFORMAT});
							break;
						
						case Types.TIMESTAMP:
							
							error = new ValidationError("property(" + property.getName() + ")", "errors.invalidTimestamp" ,new String[] {label,TypeConverter.TIMESTAMPFORMAT});
							break;
						
						// Número
						case Types.BIT:
						case Types.TINYINT:
						case Types.SMALLINT:
						case Types.INTEGER:
						case Types.BIGINT:
						case Types.REAL:
						case Types.FLOAT:
						case Types.DOUBLE:
						case Types.NUMERIC:
						case Types.DECIMAL:
							
							error = new ValidationError("property(" + property.getName() + ")", "errors.invalid.number");
							break;
					}
				}
				
				if (error != null) {
					addError(error);
				}
			}
		}
	}
	 private boolean validateNotPrimitiveType(String key, String value, EntityField fieldDef){
	    
	    	
	    	if (fieldDef != null) {
	    		InternalDataType type = fieldDef.getType();
	    		if(EqualType.isNotTypePrimitive(type.getId()))
	    		{
	    			//nif cif nie
	    			if(type.getId()==InternalDataType.CIF.getId() || type.getId()==InternalDataType.NIE.getId() || type.getId()==InternalDataType.NIF.getId()){
	    				
	    				IValidator nifCifNieValidator= new NifCifNieValidator();
	    				return nifCifNieValidator.validate(value);
	    				
	    				
	    			}
	    			else if(type.getId()==InternalDataType.EMAIL.getId()){
	    				IValidator emailValidator= new EmailValidator();
	    				return emailValidator.validate(value);
	    				
	    				
	    			}
	    			else if(type.getId()==InternalDataType.CCC.getId()){
	    				IValidator  cccValidator= new CCCValidator();
	    				return cccValidator.validate(value);
	    			}
	    			
	    		}
	    		
	    		
	    	}
	    	
	    	return true;
	    	
	    }
	public String getValue(String key) throws ISPACException {
		return mitem.getString(key);
	}

	public Object[] getMultivalue(String key) throws ISPACException {
		return (Object[])mitem.get(key);
	}
	
	public boolean validateSize(EntityField fieldDef,
								Property property,
		 						String value) throws ISPACException {

		if (!StringUtils.isEmpty(value)) {
			
			if (fieldDef != null) {
			
				int size = fieldDef.getSize();
				if (size > 0) {
					
					InternalDataType type = fieldDef.getType();
					
					// TEXT (SHORT / LONG)
					if ((type.equals(InternalDataType.SHORT_TEXT)) ||
						(type.equals(InternalDataType.LONG_TEXT))) {
						
						if (value.length() > size) {
							
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength", new String[] {getLabel(property.getName()), String.valueOf(size)});
							addError(error);
							
							return false;
						}
					}
					// INTEGER (SHORT / LONG)
					else if ((type.equals(InternalDataType.SHORT_INTEGER)) ||
							 (type.equals(InternalDataType.LONG_INTEGER))) {
						
						if (value.length() > size) {
							
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength.number", new String[] {getLabel(property.getName()), String.valueOf(size)});
							addError(error);
							
							return false;
						}
					}
					// DECIMAL (SHORT / LONG)
					else if ((type.equals(InternalDataType.SHORT_DECIMAL)) ||
							 (type.equals(InternalDataType.LONG_DECIMAL))) {
						
						boolean result = true;
						int intSize = size - property.getDecimalDigits();
						int indexDec = value.indexOf('.');
						
						// Validar la parte entera
						if (((indexDec == -1) && 
							 (value.length() > intSize)) ||
							(indexDec > intSize)) {
							
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength.integer", new String[] {getLabel(property.getName()), String.valueOf(intSize)});
							addError(error);
							
							result = false;
						}
						
						// Validar la parte decimal
						if (indexDec != -1) {
							
							int decimalSize = property.getDecimalDigits();
							if ((value.length() - indexDec - 1) > decimalSize) {
								
								ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength.decimal", new String[] {getLabel(property.getName()), String.valueOf(decimalSize)});
								addError(error);
								
								result = false;
							}
						}
	
						return result;
					}
				}
			}
		}

		return true;
	}
	
	public boolean validateRequiredField(String field) throws ISPACException {
		
		String value = getString(field);
		if (StringUtils.isBlank(value)) {
			
			addError(new ValidationError("property(" + field + ")", "errors.required", new String[] {getLabel(field)}));
			return false;
		}
		
		return true;
	}
	
	/*
	public boolean validateSize(Property property,
							 	String value) throws ISPACException {
		
		if (!StringUtils.isEmpty(value)) {
		
			int size = property.getSize();
			if (size > 0) {
			
				switch(property.getType()) {
				
					case Types.BIT:
					case Types.TINYINT:
					case Types.SMALLINT:
					case Types.INTEGER:
					case Types.BIGINT:
		
		            	if (value.length() > size) {
		            		
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength.number", new String[] {getLabel(property.getName()), String.valueOf(size)});
							addError(error);
							
							return false;
		            	}
		            	break;
					case Types.CHAR:
					case Types.VARCHAR:
					case Types.LONGVARCHAR:
					//case Types.CLOB:
							
		            	if (value.length() > size) {
		            		
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength", new String[] {getLabel(property.getName()), String.valueOf(size)});
							addError(error);
							
							return false;
		            	}
		            	break;
		            
					//case Types.REAL:
					//case Types.FLOAT:
					//case Types.DOUBLE:
					
					case Types.NUMERIC:
					case Types.DECIMAL:
						
						boolean result = true;
						int intSize = size - property.getDecimalDigits();
						int indexDec = value.indexOf('.');
						
						// Validar la parte entera
						if (((indexDec == -1) && 
							 (value.length() > intSize)) ||
							(indexDec > intSize)) {
							
							ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength.integer", new String[] {getLabel(property.getName()), String.valueOf(intSize)});
							addError(error);
							
							result = false;
						}
						
						// Validar la parte decimal
						if (indexDec != -1) {
							
							int decimalSize = property.getDecimalDigits();
							if ((value.length() - indexDec - 1) > decimalSize) {
								
								ValidationError error = new ValidationError("property(" + property.getName() + ")", "errors.maxlength.decimal", new String[] {getLabel(property.getName()), String.valueOf(decimalSize)});
								addError(error);
								
								result = false;
							}
						}
						
						return result;
				}
			}
		}
		
		return true;
	}
	*/
	
}