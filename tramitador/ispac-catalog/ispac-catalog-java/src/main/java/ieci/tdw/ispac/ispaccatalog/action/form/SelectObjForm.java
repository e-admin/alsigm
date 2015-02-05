/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaccatalog/src/ieci/tdw/ispac/ispaccatalog/action/form/SelectObjForm.java,v $
 * $Revision: 1.4 $
 * $Date: 2007/11/14 15:22:13 $
 * $Author: abelrl $
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.form;

import java.util.HashMap;

import org.apache.struts.action.ActionForm;

/**
 * SubstituteForm
 *
 *
 * @version $Revision: 1.4 $ $Date: 2007/11/14 15:22:13 $
 */
public class SelectObjForm extends ActionForm
{
	// Propiedades del formulario
	private HashMap property = new HashMap();
	
    String codetable;
    String codefield;
    String valuefield;
    String decorator;
    String searchvalue;
    String parameters;
    String querystring;
    String caption;
    String field;
    String sqlquery;
    String orderBy;
    String[] multibox;
    
    public SelectObjForm()
    {
        super();
        codetable=null;
        codefield=null;
        valuefield=null;
        decorator=null;
        searchvalue=null;
        parameters=null;
        querystring=null;
        caption=null;
        field = null;
        sqlquery = null;
    }
    
    public String[] getMultibox() {
	    return multibox;
	}
	public void setMultibox(String[] newMultibox) {
	    multibox = newMultibox;
	}
    /**
     * @return Devuelve el valor de la propiedad codefield.
     */
    public String getCodefield()
    {
        return codefield;
    }
    /**
     * @param codefield Cambia el valor de la propiedad codefield.
     */
    public void setCodefield(String codefield)
    {
        this.codefield = codefield;
    }
    /**
     * @return Devuelve el valor de la propiedad codetable.
     */
    public String getCodetable()
    {
        return codetable;
    }
    /**
     * @param codetable Cambia el valor de la propiedad codetable.
     */
    public void setCodetable(String codetable)
    {
        this.codetable = codetable;
    }
    /**
     * @return Devuelve el valor de la propiedad decorator.
     */
    public String getDecorator()
    {
        return decorator;
    }
    /**
     * @param decorator Cambia el valor de la propiedad decorator.
     */
    public void setDecorator(String decorator)
    {
        this.decorator = decorator;
    }
    /**
     * @return Devuelve el valor de la propiedad parameters.
     */
    public String getParameters()
    {
        return parameters;
    }
    /**
     * @param parameters Cambia el valor de la propiedad parameters.
     */
    public void setParameters(String parameters)
    {
        this.parameters = parameters;
    }
    /**
     * @return Devuelve el valor de la propiedad searchvalue.
     */
    public String getSearchvalue()
    {
        return searchvalue;
    }
    /**
     * @param searchvalue Cambia el valor de la propiedad searchvalue.
     */
    public void setSearchvalue(String searchvalue)
    {
        this.searchvalue = searchvalue;
    }
    /**
     * @return Devuelve el valor de la propiedad valuefield.
     */
    public String getValuefield()
    {
        return valuefield;
    }
    /**
     * @param valuefield Cambia el valor de la propiedad valuefield.
     */
    public void setValuefield(String valuefield)
    {
        this.valuefield = valuefield;
    }
    public String getQuerystring()
    {
        return querystring;
    }
    public void setQuerystring(String querystring)
    {
        this.querystring = querystring;
    }
    public String getCaption()
    {
        return caption;
    }
    public void setCaption(String caption)
    {
        this.caption = caption;
    }
    public String getField()
    {
        return field;
    }
    public void setField(String field)
    {
        this.field = field;
    }
	public String getSqlquery() {
		return sqlquery;
	}
	public void setSqlquery(String sqlquery) {
		this.sqlquery = sqlquery;
	}
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

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
