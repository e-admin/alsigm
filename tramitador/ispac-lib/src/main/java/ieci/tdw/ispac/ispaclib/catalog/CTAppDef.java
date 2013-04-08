/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/CTAppDef.java,v $
 * $Revision: 1.3 $
 * $Date: 2007/10/17 11:22:41 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog;

import ieci.tdw.ispac.ispaclib.app.IApplicationDef;

/**
 * CTAppDef
 *
 * @version $Revision: 1.3 $ $Date: 2007/10/17 11:22:41 $
 */
public class CTAppDef implements IApplicationDef
{
    int id;
    String name;
    String description;
    String className;
    String page;
    String parameters;
    String formatter;
    String formatterXML;
    String frmJsp;
    int frmVersion;
    String entPrincipalNombre;

    public CTAppDef()
    {
        id=0;
        name="";
        description="";
        className="";
        page="";
        parameters="";
        formatter="";
        formatterXML="";
        frmJsp="";
        frmVersion=0;
        entPrincipalNombre="";
    }

    /**
     * @return Devuelve el valor de la propiedad className.
     */
    public String getClassName()
    {
        return className;
    }
    /**
     * @param className Cambia el valor de la propiedad className.
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
    
    /**
     * @return Devuelve el valor de la propiedad description.
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * @param description Cambia el valor de la propiedad description.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * @return Devuelve el valor de la propiedad formatter.
     */
    public String getFormatter()
    {
        return formatter;
    }
    /**
     * @param formatter Cambia el valor de la propiedad formatter.
     */
    public void setFormatter(String formatter)
    {
        this.formatter = formatter;
    }

    /**
     * @return Devuelve el valor de la propiedad formatterXML.
     */
    public String getFormatterXML()
    {
        return formatterXML;
    }
    /**
     * @param formatterXML Cambia el valor de la propiedad formatterXML.
     */
    public void setFormatterXML(String formatterXML)
    {
        this.formatterXML = formatterXML;
    }

    /**
     * @return Devuelve el valor de la propiedad id.
     */
    public int getId()
    {
        return id;
    }
    /**
     * @param id Cambia el valor de la propiedad id.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * @return Devuelve el valor de la propiedad name.
     */
    public String getName()
    {
        return name;
    }
    /**
     * @param name Cambia el valor de la propiedad name.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @return Devuelve el valor de la propiedad page.
     */
    public String getPage()
    {
        return page;
    }
    /**
     * @param page Cambia el valor de la propiedad page.
     */
    public void setPage(String page)
    {
        this.page = page;
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
     * @return Devuelve el valor de la propiedad frmJsp.
     */
	public String getFrmJsp()
	{
	    return frmJsp;
	}
    /**
     * @param frmJsp Cambia el valor de la propiedad frmJsp.
     */
    public void setFrmJsp(String frmJsp)
    {
        this.frmJsp = frmJsp;
    }
	
    /**
     * @return Devuelve el valor de la propiedad frmVersion.
     */
	public int getFrmVersion()
	{
	    return frmVersion;
	}
    /**
     * @param frmVersion Cambia el valor de la propiedad frmVersion.
     */
    public void setFrmVersion(int frmVersion)
    {
        this.frmVersion = frmVersion;
    }
    
    /**
     * @return Devuelve el valor de la propiedad entPrincipalNombre.
     */
	public String getEntPrincipalNombre()
	{
	    return entPrincipalNombre;
	}
    /**
     * @param entPrincipalNombre Cambia el valor de la propiedad entPrincipalNombre.
     */
    public void setEntPrincipalNombre(String entPrincipalNombre)
    {
        this.entPrincipalNombre = entPrincipalNombre;
    }
	
}