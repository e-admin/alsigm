package ieci.tdw.ispac.ispacweb.menu;

import ieci.tdw.ispac.ispaclib.bean.ActionBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Clase Menú.
 */
public class Menu {

    // Atributos ---------------------------------------------------------------
    /** Items */
    private List/*ActionBean*/ mItems = new ArrayList();

    /** Etiqueta del menú */
    private String msLabel;

    /** Nombre del parámetro que pasamos queryString */
    private String msNameParameter;

    /** Nombre de la propiedad del identificador*/
    private String msPropertyId;

    /** Nombre de la propiedad del titulo*/
	private String msPropertyTitle;

    private ActionBean msActionBean;
    
    private String target = null;
    
    private String jscond = null;
    

    // Métodos -----------------------------------------------------------------
    /**
     * Constructor de la clase <code>Menu</code>.
     * @param label Etiqueta del menú.
     */
    public Menu(String label) {
        this.msLabel = label;
    }

    /**
     * Constructor de la clase <code>Menu</code>.
     * @param label Etiqueta del menú.
     * @param propertyTitle Nombre de la propiedad título.
     */
    public Menu(String label, String propertyTitle) {
        this.msLabel = label;
        this.msPropertyTitle = propertyTitle;
    }

    /**
     * Constructor de la clase <code>Menu</code>.
     * @param label Etiqueta del menú.
     * @param nameParameter Nombre del parámetro a pasar por queryString.
     * @param propertyId Nombre de la propiedad identificador.
     */
    public Menu(String label, String nameParameter, String propertyId) {
        this.msLabel = label;
        this.msNameParameter = nameParameter;
        this.msPropertyId = propertyId;
    }

    /**
     * Constructor de la clase <code>Menu</code>.
     * @param label Etiqueta del menú.
     * @param propertyTitle Nombre de la propiedad título.
     * @param nameParameter Nombre del parámetro a pasar por queryString.
     * @param propertyId Nombre de la propiedad identificador.
     */
    public Menu(String label, String propertyTitle, String nameParameter, String propertyId) {
        this.msLabel = label;
        this.msNameParameter = nameParameter;
        this.msPropertyId = propertyId;
        this.msPropertyTitle = propertyTitle;
    }

    /**
     * Añade un item a la lista.
     * @param item
     */
    public void addItem(ActionBean item) {
        this.mItems.add(item);
    }

    /**
     * Añade una lista de items a la lista.
     * @param item
     */
    public void addItems(Collection/*<ActionBean>*/ items) {
        this.mItems.addAll(items);
    }

    /**
     * @return Devuelve el valor de <code>items</code>.
     */
    public List getItems() {
        return this.mItems;
    }

    /**
     * Establece el valor de <code>items</code>.
     * @param items
     */
    public void setItems(List items) {
        this.mItems = items;
    }

	/**
	 * @return Devuelve el valor de la etiqueta del menú..
	 */
	public String getLabel()
	{
		return msLabel;
	}

	/**
	 * @param label Etiqueta del menú.
	 */
	public void setLabel(String label)
	{
		this.msLabel = label;
	}

	/**
	 * @return Devuelve el nombre del parámetro.
	 */
	public String getNameParameter()
	{
		return msNameParameter;
	}

	/**
	 * @param nameParameter Nombre del parámetro que se concatena con el enlace.
	 */
	public void setNameParameter(String nameParameter)
	{
		this.msNameParameter = nameParameter;
	}
	/**
	 * @return Devuelve el nombre de la propiedad del identificador.
	 */
	public String getPropertyId()
	{
		return msPropertyId;
	}
	/**
	 * @param propertyId Nombre de la propiedad del identificador.
	 */
	public void setPropertyId(String propertyId)
	{
		this.msPropertyId = propertyId;
	}
	/**
	 * @return Devuelve el nombre de la propiedad del título.
	 */
	public String getPropertyTitle()
	{
		return msPropertyTitle;
	}
	/**
	 * @param propertyTitle Nombre de la propiedad del título.
	 */
	public void setPropertyTitle(String propertyTitle)
	{
		this.msPropertyTitle = propertyTitle;
	}

    public ActionBean getMsActionBean()
    {
        return msActionBean;
    }

    public void setMsAction(ActionBean msActionBean)
    {
        this.msActionBean = msActionBean;
    }

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getJscond() {
		return jscond;
	}

	public void setJscond(String jscond) {
		this.jscond = jscond;
	}
}
