/*
 * Created on 22-jul-2004
 *
 */
package ieci.tdw.ispac.ispacweb.menu;

/**
 * @author Lema
 *
 *@deprecated
 */
public class MenuItem
{

    private String msName; // Nombre del menú

    private String msTitle; // Título del menú

    private String msPropertyId; // Propiedad identificador

    private String msPropertyTitle; // Propiedad título

    private String msParameter; // Nombre del parámetro que identifica la acción

    public MenuItem()
    {
    }

    public MenuItem(String sName, String sTitle, String sPropertyId,
            String sPropertyTitle, String sParameter)
    {
        this.msName = sName;
        this.msTitle = sTitle;
        this.msPropertyId = sPropertyId;
        this.msPropertyTitle = sPropertyTitle;
        this.msParameter = sParameter;
    }

    public MenuItem(String sName, String sTitle, String sPropertyTitle)
    {
        this.msName = sName;
        this.msTitle = sTitle;
        this.msPropertyId = null;
        this.msPropertyTitle = sPropertyTitle;
        this.msParameter = null;
    }

    public String getName()
    {
        return msName;
    }

    public void setName(String sName)
    {
        this.msName = sName;
    }

    public String getTitle()
    {
        return msTitle;
    }

    public void setTitle(String sTitle)
    {
        this.msTitle = sTitle;
    }

    public String getPropertyId()
    {
        return msPropertyId;
    }

    public void setPropertyId(String sPropertyId)
    {
        this.msPropertyId = sPropertyId;
    }

    public String getPropertyTitle()
    {
        return msPropertyTitle;
    }

    public void setPropertyTitle(String sPropertyName)
    {
        this.msPropertyTitle = sPropertyName;
    }

    public String getParameter()
    {
        return msParameter;
    }

    public void setParameter(String sParameter)
    {
        this.msParameter = sParameter;
    }
}
