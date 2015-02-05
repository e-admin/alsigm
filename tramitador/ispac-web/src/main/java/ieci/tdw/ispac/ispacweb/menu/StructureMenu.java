package ieci.tdw.ispac.ispacweb.menu;

import java.util.List;

/**
 * @author marisa
 *
 * Clase que estructura los datos para poder usarlos por dos vistas distintas
 * @deprecated
 */
public class StructureMenu
{
    private String id; // identificador del nivel 0

    private String title; // título del nivel 0

    private String url; // url para los elementos

    private List/* Object */items; // lista con los elementos (nivel 1)

    private String itemId; // identificador de cada elemento del nivel 1

    private String itemName; // nombre que aparece en el nivel 1

    private String varUrl; // nombre de la variable para unir a la url

    // constructores
    public StructureMenu()
    {
    }

    public StructureMenu(String id, String title, String url, List items,
            String itemId, String itemName, String varUrl)
    {
        this.id = id;
        this.title = title;
        this.url = url;
        this.items = items;
        this.itemId = itemId;
        this.itemName = itemName;
        this.varUrl = varUrl;
    }

    // métodos get y set

    /**
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return Returns the itemId.
     */
    public String getItemId()
    {
        return itemId;
    }

    /**
     * @param itemId
     *            The itemId to set.
     */
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    /**
     * @return Returns the itemName.
     */
    public String getItemName()
    {
        return itemName;
    }

    /**
     * @param itemName
     *            The itemName to set.
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    /**
     * @return Returns the items.
     */
    public List getItems()
    {
        return items;
    }

    /**
     * @param items
     *            The items to set.
     */
    public void setItems(List items)
    {
        this.items = items;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *            The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url
     *            The url to set.
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return Returns the varUrl.
     */
    public String getVarUrl()
    {
        return varUrl;
    }

    /**
     * @param varUrl
     *            The varUrl to set.
     */
    public void setVarUrl(String varUrl)
    {
        this.varUrl = varUrl;
    }

    public String toString()
    {
        return "id =" + id + " itemId =" + itemId + " itemName =" + itemName
                + " title =" + title + " url =" + url + " varUrl =" + varUrl
                + " items =" + items;
    }

}
