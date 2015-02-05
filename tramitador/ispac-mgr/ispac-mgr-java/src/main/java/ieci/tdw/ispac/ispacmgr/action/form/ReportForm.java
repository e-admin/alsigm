package ieci.tdw.ispac.ispacmgr.action.form;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchForm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ReportForm extends BatchForm {
	
	// Valores de los parámetros del informes
	private Map values = new HashMap();

	// Tipos de datos en bbdd de cada uno de los parámetros del informes
	private Map types = new HashMap();
	
	// Expresiones SQL asociadas a los parámetros del informes
	private Map sqlqueries = new HashMap();

	// Operadores asociados a los parámetros del informes
	private Map operators = new HashMap();
	
	// METODOS ACCESORES Y MODIFICADORES
	
	// Para los operadores
	public Object getOperators(String key)
	{
		return operators.get(key);
	}
	public void setOperators(String key, Object value)
	{
		if (value!=null && !value.toString().equals(""))
			operators.put(key, value);
	}

	// Para los tipos
	public Object getTypes(String key)
	{
		return types.get(key);
	}
	public void setTypes(String key, Object value)
	{
		if (value!=null && !value.toString().equals(""))
			types.put (key, value);
	}

	// Para los valores
	public Object getValues(String key)
	{
		return values.get(key);
	}
	public void setValues(String key, Object value)
	{
		if (value!=null && !value.toString().equals(""))
			values.put (key, value);
	}
	
	// Para las expresiones SQL
	public Object getSqlqueries(String key)
	{
		return sqlqueries.get(key);
	}
	public void setSqlqueries(String key, Object value)
	{
		if (value!=null && !value.toString().equals(""))
			sqlqueries.put (key, value);
	}
	
	// Accesores para colecciones
	public Map getValuesMap()
	{
		return values;
	}
	public Map getOperatorsMap()
	{
		return operators;
	}
	public Map getTypesMap()
	{
		return types;
	}
	public Map getSqlqueriesMap()
	{
		return sqlqueries;
	}
	
	public Map getParams() {
		
		Map params = new HashMap(getValuesMap());
		Map extraParams = new HashMap();
		
		Iterator it = params.entrySet().iterator();
		while (it.hasNext()) {
			
			Entry entry = (Entry)it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			
			String sqlquery = (String) getSqlqueries(key);
			if (StringUtils.isNotBlank(sqlquery)) {
				
				sqlquery = StringUtils.replace(sqlquery, "$columnname", key);
				sqlquery = StringUtils.replace(sqlquery, "$value", value);
				
				entry.setValue(sqlquery);
				
				if (key.indexOf('.') != -1) {
					
					extraParams.put(StringUtils.replace(key, ".", "_"), value);
				}
			}
		}
		
		params.putAll(extraParams);
		
		return params;
	}

}
