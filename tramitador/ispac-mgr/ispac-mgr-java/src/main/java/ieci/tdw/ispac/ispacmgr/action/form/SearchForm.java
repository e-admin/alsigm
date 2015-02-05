/*
 * Created on Jul 7, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispacmgr.action.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchForm extends BatchForm
{
	
	// PROPIEDADES DE LA CLASE

	// Dominio de la consulta: todos los expedientes o
	// solo los de mi responsabilidad
	private int domain;

	private int expState; 
	
	// Idetificador del xml que contiene los campos de
	// tipo result
	private int idxml;

	// Valores de los campos del formulario
	private Map values = new HashMap ();

	// Tipos de datos en bbdd de cada uno de los campos
	// del formulario
	private Map types = new HashMap ();

	// Operadores asociados a los campos del formulario
	private Map operators = new HashMap ();

	// Nombre de las tablas de las entidades que intervienen
	// en el formulario
	private String[] tables;

	// Width de la tabla que presenta los resultados de la busqueda
	private String displaywidth;

	// Parámetros del displayTag que presenta los resultados de la búsqueda
	private String displayTagParams = "";
	
	// Propiedades del formulario
	private Map property = new HashMap();

	
	public void reset() {
		property.clear();
	}

	// METODOS ACCESORES Y MODIFICADORES

	// Para el dominio
	public int getDomain()
	{
		return domain;
	}
	public void setDomain(int domain)
	{
		this.domain = domain;
	}

	public String getDisplaywidth()
	{
		return displaywidth;
	}
	public void setDisplaywidth(String displaywidth)
	{
		this.displaywidth = displaywidth;
	}
	
	public String getDisplayTagParams()
	{
		return displayTagParams;
	}
	public void setDisplayTagParams(String displayTagParams)
	{
		this.displayTagParams = displayTagParams;
	}

	// Para el identificador del xml
	public int getIdxml()
	{
		return idxml;
	}
	public void setIdxml(int idxml)
	{
		this.idxml = idxml;
	}

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

	// Para las tablas
	public String getTables (int idx)
	{
		return tables[idx];
	}
	public void setTables(int idx, Object value)
	{
		if (value!=null && !value.toString().equals(""))
			tables[idx] = (String) value;
	}
	public String[] getTables ()
	{
		return tables;
	}
	public void setTables (String[] values)
	{
		tables = values;
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

	public void setMultivalues(String[] multivalues) {
		 
		   Map multivaluesMap= new HashMap();
		   for(int i=0; i<multivalues.length; i++){
			   String item=multivalues[i];
			   String [] aux= item.split("ValuesMulti=");
			  if(aux.length>0){
				  List lista;
			   if(multivaluesMap.containsKey(aux[0])){
				    lista= (List) multivaluesMap.get(aux[0]);
			   }
			   else{
				   lista=new ArrayList();
			   }
			   lista.add(aux[1]);
			   multivaluesMap.put(aux[0], lista);
			  }
		   }
		   
		  Iterator itr= multivaluesMap.keySet().iterator();
		  while(itr.hasNext()){
			  
			  String clave= itr.next().toString();
			  List values=(List) multivaluesMap.get(clave);
			  setValues(clave, values);
		  }
	}

	
	public void setValues(String key, Object value)
	{
		if (value!=null && !value.toString().equals(""))
			values.put (key, value);
	}


	///////////////////
	/*
	public Map getValues ()
	{
		return values;
	}
	public void setValues (Map values)
	{
		this.values = values;
	}
	*/
	////////////////////


	// Accesores para colecciones
	public String[] getTablesArray ()
	{
		return tables;
	}
	public Map getValuesMap ()
	{
		return values;
	}
	public Map getOperatorsMap ()
	{
		return operators;
	}
	public Map getTypesMap ()
	{
		return types;
	}
	public int getExpState() {
		return expState;
	}
	public void setExpState(int expState) {
		this.expState = expState;
	}

/*	public void printSearchForm ()
	{
		System.out.println ("-- Propiedades --");

		String result = "domain: " + getDomain();
		result += "\nidxml: " + getIdxml();
		result += "\nValores:";
		Iterator iter = values.keySet().iterator();
		while (iter.hasNext())
		{
			String key = (String) iter.next();
			String value = (String) values.get(key);
			result += "\n" + key + ": " + value;
		}
		result += "\nTipos:";
		iter = types.keySet().iterator();
		while (iter.hasNext())
		{
			String key = (String) iter.next();
			String value = (String) types.get(key);
			result += "\n" + key + ": " + value;
		}
		result += "\nOperadores:";
		iter = operators.keySet().iterator();
		while (iter.hasNext())
		{
			String key = (String) iter.next();
			String value = (String) operators.get(key);
			result += "\n" + key + ": " + value;
		}
		result += "\nTablas:";

		for (int i=0; i<tables.length; i++)
		{
			result += "\n" + tables[i];
		}
		System.out.println (result);


		System.out.println ("-- Fin Propiedades --");
	}
*/

	public void setProperty(String key, String value)
	{
		if (value == null)
			property.put(key, "");
		else
			property.put(key, value);
	}

	public String getProperty(String key)
	{
		if (!property.containsKey(key))
			return "";
		return property.get(key).toString();
	}

    public Map getMapProperties() {
        return property;
    }

}
