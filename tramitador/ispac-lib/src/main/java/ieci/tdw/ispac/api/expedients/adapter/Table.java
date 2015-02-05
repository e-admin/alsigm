package ieci.tdw.ispac.api.expedients.adapter;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;


/**
 * Clase que contiene la información de mapeo de una tabla.
 */
public class Table
{

    /** Logger de la clase. */
    private static final Logger logger = Logger.getLogger(Table.class);
    
    /** Nombre de la tabla. */
    private String name = null;
    
    /** Iterador para múltiples registros dentro de la misma tabla. */
    private String iterator = null;
    
    /** Propiedades para el mapeo de la tabla. */
    private Properties properties = null;
    
    /** Mapeos de campos y entidades de la tabla. */
    private Mappings mappings = null;
    
    /** Propiedades para transformar el valor de un campo. */
    private HashMap transformationProperties = null; 

    /** Tablas auxiliares a la tabla actual*/
    private Map compositeTables = null; 
    
    /** Indica si la tabla actual ha recibido valores, ya sean valores de la misma tabla o de tablas auxiliares, para saber si se debe crear un registro de la misma, ya que si no recibe valores no debería crearse  */
    private boolean hasValues = false;
    
    /**
     * Constructor.
     * @param name Nombre de la tabla.
     */
    public Table(String name)
    {
        setName(name);
        setProperties(new Properties());
        setMappings(new Mappings());
        transformationProperties = new HashMap();
        compositeTables = new HashMap();
    }

    
    /**
     * Devuelve el nombre de la tabla.
     * @return Nombre de la tabla.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Establece el nombre de la tabla.
     * @param name Nombre de la tabla.
     */
    public void setName(String name)
    {
        this.name = name;
    }
     
    /**
     * Devuelve el iterador para múltiples registros dentro de la misma tabla.
     * @return Iterador para múltiples registros dentro de la misma tabla.
     */
    public String getIterator() {
		return iterator;
	}

    /**
     * Establece el iterador para múltiples registros dentro de la misma tabla.
     * @param iterator Iterador para múltiples registros dentro de la misma tabla.
     */
	public void setIterator(String iterator) {
		this.iterator = iterator;
	}

	/**
     * Devuelve las propiedades para el mapeo de la tabla.
     * @return Propiedades para el mapeo de la tabla.
     */
    public Properties getProperties()
    {
        return properties;
    }
    
    /**
     * @param propertyName Nombre de la propiedad
     * @return el valor de una propiedad
     */
    public String getProperty(String propertyName){
    	return properties.getProperty(propertyName);
    }
    
    /**
     * Establece las propiedades para el mapeo de la tabla.
     * @param props Propiedades para el mapeo de la tabla.
     */
    public void setProperties(Properties props)
    {
        properties = props;
    }
    
    /**
     * Devuelve los mapeos de campos y entidades de la tabla.
     * @return Mapeos de campos y entidades de la tabla.
     */
    public Mappings getMappings()
    {
        return mappings;
    }
    
    /**
     * Establece los mapeos de campos y entidades de la tabla.
     * @param maps Mapeos de campos y entidades de la tabla.
     */
    public void setMappings(Mappings maps)
    {
        mappings = maps;
    }
      
    /**
     * Representación de la instancia en formato XML.
     */
    public String toString()
    {
        StringBuffer xml = new StringBuffer();
        
        xml.append("<table name=\"");
        xml.append(name);
        xml.append("\"");
        
        if (StringUtils.isNotBlank(iterator)) {
            xml.append(" iterator=\"");
            xml.append(iterator);
            xml.append("\"");
        }
        
        xml.append(">\r\n");
        
        // Propiedades
        xml.append("  <properties");
        if (properties.size() > 0)
        {
            xml.append(">\r\n");
            
            String propertyName = null;
            for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
            {
                propertyName = (String)e.nextElement();
                
                xml.append("    <property name=\"");
                xml.append(propertyName);
                xml.append("\">");
                xml.append(properties.getProperty(propertyName));
                xml.append("</property>\r\n");
            }
            
            xml.append("  </properties>\r\n");
        }
        else
            xml.append("/>\r\n");
        
        // Mappings
        xml.append(mappings.toString());
        
        xml.append("</application>\r\n");
        
        return xml.toString();   
    }

    public List createItems(ClientContext ctx, String numexp, XMLFacade especificData) throws ISPACException {
    	
    	List items = new ArrayList();
    	
		// Comprobar si hay que leer múltiples registros
		if (StringUtils.isBlank(getIterator())) { // 1 solo registro en la tabla
		
			IItem item = createItem(ctx, numexp, especificData, null);
    		if (item != null){
    			items.add(item);
    		}

		} else { // n registros en la tabla
		
	        NodeIterator nodeIt = especificData.getNodeIterator(getIterator());
	        if (nodeIt != null) {
		        for (Node nd = nodeIt.nextNode(); nd != null; nd = nodeIt.nextNode()) {
					IItem item = createItem(ctx, numexp, especificData, nd);
					if (item != null){
						items.add(item);
					}
		        }
	        }
		}

		return items;
    }
    
    protected IItem createItem(ClientContext ctx, String numexp, XMLFacade especificData, Node node) throws ISPACException {
    	
		IItem item = null;

		IInvesflowAPI invesFlowAPI = ctx.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

    	Map tableMap = map(especificData, null, node);
    	//Si no viene ningun valor para la entidad no creamos el registro correspondiente 
    	if (!hasValues){
    		return null;
    	}
    	boolean registerCreated = true;
    	//Se comprueba si se ha de crear un nuevo registro o se va a actualizar uno existente 
    	//en funcion de si viene establecido el valor del campo clave primaria
    	String fieldPrimaryKey = getProperty(Constants.PROPERTY_PRIMARY_KEY);
    	if (StringUtils.isNotEmpty(fieldPrimaryKey)){
    		String valuePrimaryKey = (String)tableMap.get(fieldPrimaryKey);
    		//Si en el campo clave primaria viene el valor '0' se entiende que se quiere actualizar el primero y unico registro de la entidad
    		if (StringUtils.equals(valuePrimaryKey, ""+Constants.VALUE_DEFAULT_KEY)){
    			IItemCollection collection = entitiesAPI.getEntities(name, numexp);
    			if (collection.next()){
    				item = collection.value();
    			}
    		}else if (StringUtils.isNotEmpty(valuePrimaryKey)){
    			item = entitiesAPI.getEntity(getName(), Integer.parseInt(valuePrimaryKey));
    			registerCreated = false;
    		}
    	}
    	
    	if (item == null) {
    		
    		//Si la entidad es la de expedinetes no se crea registro porque ya existe, solo se obtiene
    		if (StringUtils.equalsIgnoreCase(name, Constants.SPAC_EXPEDIENTES)){
    			item = entitiesAPI.getExpedient(numexp);
    		} else {
    			item = entitiesAPI.createEntity(name, numexp);
    		}
    	}
    	
		// Establecer los datos en la entidad
    	if (tableMap != null) {
        	Iterator infoMapIt = tableMap.keySet().iterator();
        	String key;
    		while (infoMapIt.hasNext()) {
    			key = (String) infoMapIt.next();
    			//Si es el campo de la clave primaria no se actualiza
    			if (!StringUtils.equals(fieldPrimaryKey, key)){
    				item.set(key, tableMap.get(key));
    			}
    		}
    	}
    	
		// Guardar la entidad
		item.store(ctx);
		
		//Ahora procedemos con las entidades agregadas
		Map values = null;
		String entityAux,key,value,valueToCompareFK = null;
		CompositeTable compositeTable;
		IItem itemAux = null;

		//Obtenemos el campo clave primaria de la entidad principal
		
		if (!compositeTables.isEmpty()){
			//Se crea un composite item para retornarlo como resultado de la operacion 
			CompositeItem composite = new CompositeItem(name + ":" + entitiesAPI.getCatalogEntity(name).getString("CAMPO_PK"));
			composite.addItem(item, name+":");
			
			//Se recorren todas las entidades agregadas
			for (Iterator iterator = compositeTables.entrySet().iterator(); iterator.hasNext();) {
				itemAux = null;
				Map.Entry entry = (Map.Entry) iterator.next();
				entityAux = (String)entry.getKey();
				compositeTable = (CompositeTable)entry.getValue();
				values = compositeTable.getMapValues();
				//Si se ha creado un registro de la entidad principal, habra que crearlo tambien para la entidad agregada 
				if (registerCreated){
					itemAux = entitiesAPI.createEntity(entityAux, numexp);
				}else{
					//Consulta para obtener el registro ya existente de la entidad agregada
					if (StringUtils.isNotEmpty(compositeTable.getFieldPrimaryKey())){
						valueToCompareFK = item.getString(compositeTable.getFieldPrimaryKey());
					}else{
						valueToCompareFK = ""+item.getKeyInt();
					}
					String query = compositeTable.getFieldForeignKey() + " = " + valueToCompareFK;
					IItemCollection itemcol = entitiesAPI.getEntities(entityAux, numexp, query);
					if (itemcol.next()){
						itemAux = itemcol.value();
					}
				}
				if (itemAux != null){
					//Se procede a establecer los valores del registro de la entidad agregada
					for(Iterator iterator2 = values.entrySet().iterator(); iterator2.hasNext();){
						Map.Entry entry2 = (Map.Entry) iterator2.next();
						key = (String)entry2.getKey();
						value = (String)entry2.getValue();
						itemAux.set(key, value);
					}
					//Se obtiene el valor del campo que ejerce de clave ajena en la entidad agregada 
					String valueFK = compositeTable.getValueForeignKey();
					if (StringUtils.isEmpty(valueFK)){
						if (StringUtils.isNotEmpty(compositeTable.getFieldPrimaryKey())){
							valueFK = item.getString(compositeTable.getFieldPrimaryKey());
						}else{
							valueFK = ""+item.getKeyInt();
						}
					}
					itemAux.set(compositeTable.getFieldForeignKey(), valueFK);
					itemAux.store(ctx);
					composite.addItem(itemAux, entityAux+":");
				}
			}
			return composite;
		}
		
		return item;
    }
    
    /**
     * Realiza el mapeo de campos y entidades sobre el mensaje XML.
     * @param msg Mensaje XML.
     * @param params Parámetros externos.
     * @param node Nodo actual para múltiples registros.
     * @return Mapa de campos resultante.
     */
    public Map map(XMLFacade msg, Properties params, Node node)
    {
        Map tableMap = new HashMap();
        
        // Realizar el mapeo
        mapFields(tableMap, mappings.getNode(), msg, params, node);
        
        return tableMap;
    }        
    
    /**
     * Mapea los campos (nodos 'field') de un nodo XML.
     * @param tableMap Mapa de campos.
     * @param mappingNode Nodo con los campos de mapeo.
     * @param msg Mensaje XML.
     * @param params Parámetros externos.
     * @param node Nodo de la entidad para referencias relativas.
     */
    private void mapFields(Map tableMap, Node mappingNode, XMLFacade msg,Properties params, Node node){
        NodeIterator nodeIt = XMLFacade.getNodeIterator(mappingNode, Constants.XPATH_FIELD);
        mapField(tableMap, nodeIt, msg, params, node);
        //Comprobamos si se han recibido valores para la tabla, actualizando la variable que almacena esta evaluacion
        if (tableMap != null && tableMap.size() != 0){
        	hasValues = true;
        }
        
        //Obtener las tablas auxiliares (agregadas)
        nodeIt = XMLFacade.getNodeIterator(mappingNode,Constants.XPATH_COMPOSITE);
        String nameAuxTable,fieldForeignKey,valueForeignKey, fieldPrimaryKey;
        CompositeTable compositeTable = null;
        for (Node nd = nodeIt.nextNode(); nd != null; nd = nodeIt.nextNode()){
        	nameAuxTable = XMLFacade.getAttributeValue(nd, Constants.ATTR_NAME);
        	fieldForeignKey = XMLFacade.getAttributeValue(nd, Constants.ATTR_FIELD_FOREIGN_KEY);
        	valueForeignKey = XMLFacade.getAttributeValue(nd, Constants.ATTR_VALUE);
        	fieldPrimaryKey = XMLFacade.getAttributeValue(nd, Constants.ATTR_FIELD_PRIMARY_KEY);
        	
        	compositeTable = new CompositeTable();        	
            compositeTable.setFieldForeignKey(fieldForeignKey);
            if (StringUtils.isNotEmpty(fieldPrimaryKey)){
            	compositeTable.setFieldPrimaryKey(fieldPrimaryKey);
            }

        	NodeIterator nodeIt2 = XMLFacade.getNodeIterator(nd, Constants.XPATH_FIELD);
            Map compositeTableMap = new HashMap();
	        if (StringUtils.isNotEmpty(valueForeignKey)){
	        	compositeTable.setValueForeignKey(valueForeignKey);
	        }
            //Se mapean los datos de la entidad agregada
            mapField(compositeTableMap, nodeIt2, msg, params, node);
            //Comprobamos si se han recibido valores para la tabla auxiliar, actualizando la variable que almacena esta evaluacion si es que no esta ya con valor true
            //si ya lo esta, ya se sabe que se creara el registro correspondiente de la tabla principal y de las auxiliares
            if (!hasValues){
            	hasValues = compositeTableMap != null && compositeTableMap.size() != 0;
            }
            compositeTable.setMapValues(compositeTableMap);
            
            compositeTables.put(nameAuxTable, compositeTable);
        }
    }

    private void mapField(Map tableMap, NodeIterator nodeIt, XMLFacade msg,
			Properties params, Node node) {

		String name, value, dateFormat, multivalue;
		int maxLength;

		for (Node nd = nodeIt.nextNode(); nd != null; nd = nodeIt.nextNode()) {

			name = XMLFacade.getAttributeValue(nd, Constants.ATTR_NAME);
			value = XMLFacade.getAttributeValue(nd, Constants.ATTR_VALUE);
			multivalue = XMLFacade.getAttributeValue(nd, Constants.ATTR_MULTIVALUE);
			maxLength = TypeConverter.parseInt(XMLFacade .getAttributeValue(nd, Constants.ATTR_MAX_LENGTH), -1);

			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {

				if ("true".equalsIgnoreCase(multivalue)) {
					String[] fieldValues = getFieldValues(value, msg, params, node);
					if (!ArrayUtils.isEmpty(fieldValues)) {

						dateFormat = XMLFacade.getAttributeValue(nd, Constants.ATTR_DATE_FORMAT);
						

						List values = new ArrayList();

						for (int i = 0; i < fieldValues.length; i++) {
							if (StringUtils.isNotBlank(fieldValues[i])) {
								if (StringUtils.isNotBlank(dateFormat)) {
									values.add(DateUtil.getDate(fieldValues[i].trim(), dateFormat));
								} else {
									if ((maxLength > -1) && (fieldValues[i].length() > maxLength)) {
										fieldValues[i] = fieldValues[i].substring(0, maxLength).trim();
									}
									values.add(fieldValues[i]);
								}
							}
						}

						tableMap.put(name, values.toArray());
					}
				} else {
					String fieldValue = getFieldValue(value, msg, params, node);
					if (StringUtils.isNotBlank(fieldValue)) {

						if ((maxLength > -1) && (fieldValue.length() > maxLength)) {
							fieldValue = fieldValue.substring(0, maxLength);
						}

						dateFormat = XMLFacade.getAttributeValue(nd, Constants.ATTR_DATE_FORMAT);
						if (dateFormat != null) {
							tableMap.put(name, DateUtil.getDate(fieldValue, dateFormat));
						} else {
							tableMap.put(name, fieldValue.trim());
						}
					}
				}
			}
		}
	}
    
    
    /**
	 * Obtiene el valor de un campo en función de si es una constante o una
	 * variable.
	 * 
	 * @param currentValue
	 *            Valor actual del campo.
	 * @param msg
	 *            Mensaje XML.
	 * @param params
	 *            Parámetros externos.
	 * @param node
	 *            Nodo raíz para referencias relativas.
	 */
    private String getFieldValue(String currentValue, XMLFacade msg, Properties params, Node node)
    {
        StringBuffer value = new StringBuffer();

        if (currentValue != null)
        {
            Pattern patt = Pattern.compile(Constants.FIELD_VARIABLE_PATTERN);
            Matcher m = patt.matcher(currentValue);
            
            while (m.find()) {
            	String varValue = "";
             	String[] varValues = getVarValue(m.group(0), msg, params, node);
             	if (!ArrayUtils.isEmpty(varValues)) {
	             	varValue = varValues[0];
	             	varValue = StringUtils.replace(varValue, "\\", "\\\\"); 
	             	varValue = StringUtils.replace(varValue, "$", "\\$");
             	}
             	m.appendReplacement(value, varValue); 
            }
            
            m.appendTail(value);
        }

        return value.toString();
    }

    private String[] getFieldValues(String currentValue, XMLFacade msg, Properties params, Node node)
    {
    	String[] varValues = null;

        if (currentValue != null) {
        	
         	varValues = getVarValue(currentValue, msg, params, node);
         	if (!ArrayUtils.isEmpty(varValues)) {
	         	for (int i = 0; i < varValues.length; i++) {
		         	varValues[i] = StringUtils.replace(varValues[i], "\\", "\\\\"); 
		         	varValues[i] = StringUtils.replace(varValues[i], "$", "\\$");
	         	}
         	}
        }

        return varValues;
    }

    /**
     * Obtiene el valor de una variable.
     * @param variable Variable.
     * @param msg Mensaje XML.
     * @param params Parámetros externos.
     * @param node Nodo raíz para referencias relativas.
     */
    private String[] getVarValue(String variable, XMLFacade msg, Properties params, Node node)
    {
        String paramType = null;
        String paramName = null;
        String propName = null;

        if (logger.isDebugEnabled())
            logger.debug("Variable: " + variable);


        // Definir el tipo y nombre del parámetro
        int colonIndex = variable.indexOf(Constants.VAR_TYPE_SEPARATOR);
        if (colonIndex > 0)
        {
            paramType = variable.substring(2, colonIndex);
            paramName = variable.substring(colonIndex+1, variable.length()-1);
            
            colonIndex = paramName.indexOf(Constants.VAR_TYPE_SEPARATOR);
            if (colonIndex > 0)
            {
            	propName = paramName.substring(colonIndex + 1);
            	paramName = paramName.substring(0, colonIndex);
            }
        }
        else
        {
            paramType = Constants.VAR_TYPE_PARAM;
            paramName = variable.substring(2, variable.length()-1);
        }

        return getVarValue(paramType, paramName, propName, msg, params, node);  
    }

    /**
     * Obtiene el valor de una variable.
     * @param variable Variable.
     * @param msg Mensaje XML.
     * @param params Parámetros externos.
     * @param node Nodo raíz para referencias relativas.
     */
    private String[] getVarValue(String paramType, String paramName, String propName, XMLFacade msg, Properties params, Node node)
    {
    	String[] paramValues = null;
    	
    	if (logger.isDebugEnabled())
    		logger.debug("ParamType: " + paramType + ", ParamName=" + paramName + ", PropName=" + propName);

        // Obtener el valor de la variable
    	if (Constants.VAR_TYPE_PROPERTY.equals(paramType))
        {
    		String value = properties.getProperty(paramName);
    		if (StringUtils.isNotBlank(value)) {
    			paramValues = new String[] { value };
    		}
        }
        else if (Constants.VAR_TYPE_SYSTEM.equals(paramType))
        {
            if (Constants.VAR_FUNC_CURRENT_DATETIME.equals(paramName)) {
    			paramValues = new String[] { Constants.DATETIME_FORMAT.format(new Date()) };
            }
        }
        else if (Constants.VAR_TYPE_XPATH.equals(paramType))
        {
            if (node != null) {
            	paramValues = XMLFacade.getValues(node, paramName);
            }
            
            if (paramValues == null) {
            	paramValues = msg.getValues(paramName);
            }
        }
        else //if (Constants.VAR_TYPE_PARAM.equals(paramType))
        {
            if (params != null) {
            	String value = params.getProperty(paramName);
        		if (StringUtils.isNotBlank(value)) {
        			paramValues = new String[] { value };
        		}
            }
        }

        // Transformación del valor de la variable
        if (!ArrayUtils.isEmpty(paramValues) 
        		&& StringUtils.isNotBlank(propName)) {
        	Properties props = getTransformationProperties(propName);
        	if ((props != null) && !props.isEmpty()) {
        		for (int i = 0; i < paramValues.length; i++) {
        			paramValues[i] = props.getProperty(paramValues[i], "");
        		}
        	}
        }
        	
        if (logger.isDebugEnabled())
            logger.debug(paramType + " [" + paramName + "]=[" + paramValues + "]");

        return paramValues;
    }
    
    /**
     * Obtiene las propiedades para transformar el valor de un campo.
     * @param propName Nombre de la propiedad.
     * @return Propiedades para transformar el valor de un campo.
     */
    private Properties getTransformationProperties(String propName)
    {
    	Properties props = (Properties)transformationProperties.get(propName);
    	if (props == null)
    	{
            props = new Properties();
            
            String propsMappings = properties.getProperty(propName);
            if (propsMappings != null)
            {
                StringTokenizer par = new StringTokenizer(propsMappings, ";");
                while (par.hasMoreTokens())
                {
                    StringTokenizer tok = new StringTokenizer(par.nextToken(), "=");
                    String name = null, value = null;
                    if (tok.hasMoreTokens())
                    {
                        name = tok.nextToken();
                        if (tok.hasMoreTokens())
                            value = tok.nextToken();
                            
                        props.setProperty(name, value);
                    }
                }
            }
            
            transformationProperties.put(propName, props);
    	}
    	
    	return props;
    }
   
}