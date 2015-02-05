package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.EntityApp;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * BeanPropertyFmt
 *
 *
 * @version $Revision: 1.9 $ $Date: 2008/07/11 11:53:18 $
 */
public  abstract class BeanPropertyFmt implements Serializable
{
    static public final String PROP_PREPAREDVALUE="preparedvalue";
    static public final String PROP_PROPERTYVALUE="propertyValue";
    static public final String PROP_PREPAREDPARAMS="preparedParams";
	/**
	 *  Propiedad a mostrar en el jsp
	 */
	protected String property;
	/**
	 * <code>true</code> si el campo a mostrar es sólo de lectura
	 */
	protected boolean readOnly;

    /**
     * <code>true</code> si los campos <code>title</code>, <code>tooltipTitle</code> y
     * <code>propertyValue</code> representan claves de recursos en lugar del
     * texto a mostrar.
     */
    protected boolean useBundle;

	/**
	 * Patrón de formateo para fechas,números,...
	 */
	protected String format;

	/**
	 * Título del campo
	 */
	protected String title;

    /**
     * Clave de recurso con el título del campo
     */
    protected String titleKey;

	/**
	 * Tipo de campo para diferenciar su presentación en la página JSP
	 */
	protected String fieldType;

	/**
	 * Especifica que campo del bean se debe utilizar como clave de un enlace.
	 * Por defecto, las páginas JSP del framework ISPAC utilizan este valor
	 * cuando el tipo de campo <code>fieldType</code> contien el valor <code>LINK</code>
	 */
	protected String fieldLink;
	
	/**
	 * Estilo CSS del formateador
	 */
	protected String styleClass;

	/**
	 * @deprecated Utilizar en su lugar un estilo CSS
	 * Anchura del campo.
	 */
	protected String width;

	/////////////////////////////////////////
	// Propiedades adicionales para utilizar con displaytag
	// y en especial <code>display:column</code>

	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Clase de la cabecera
	 */
	protected String headerClass;	// clase de la cabecera

	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Clase de la columna
	 */
	protected String columnClass;	// clase de las celdas no cabecera

	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Determina si se puede ordenar la columna con las propiedades especificadas
	 *  en <code>property</code>. Recibe los valores true, false
	 */
	protected boolean sortable;
	
	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Posibilita la exportación de los datos y si se debe mostrar en html
	 * Valores: html csv excel xml pdf
	 */
	protected String media;
	
	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Clase decorador para el displaytag.
	 */
	protected String decorator;

	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Clase comparador para el displaytag.
	 */
	protected String comparator;

	
	/**
	 * URL base del link dinámico
	 */
	protected String url;
	
	/**
	 * Destino del link dinámico
	 */
	protected String urlTarget;
	
	/**
	 * Nombre del parámetro que se añade a la URL
	 */
	protected String id;

	protected Map linkParams;

	/**
	 * Indica si se creará un tooltip
	 */
	protected boolean tooltip;
	
	/**
	 * Identificador de estilo del elemento.
	 * Necesario para el tooltip.
	 */
	protected String styleId;
	
	/**
	 * Título para el tooltip.
	 */
	protected String tooltipTitle;

    /**
     * Nombre del recurso con el título para el tooltip.
     */
    protected String tooltipTitleKey;

	/**
	 * Nombre de la propiedad cuyo contenido conformará el cuerpo del tooltip
	 */
	protected String tooltipText;

	/**
	 * Valor constante que devolverá este formateador en lugar del
	 * valor de la propiedad. Permite sustituir los valores de propiedades
	 * por valores constantes lo cual es útil a la hora de confeccionar
	 * campos con enlaces
	 *
	 */
	protected String propertyValue;

    /**
     * Nombre del recurso con el texto que devolverá este formateador en lugar del
     * valor de la propiedad. Permite sustituir los valores de propiedades
     * por constantes, lo cual es útil a la hora de confeccionar
     * campos con enlaces.
     *
     */
    protected String propertyValueKey;

	/**
	 * @deprecated
	 */
	private Object preparedvalue;

	/**
	 * @deprecated
	 */
	private Map preparedParams;


	public BeanPropertyFmt()
	{
		readOnly = false;
		propertyValue=null;
		sortable=false;
		urlTarget="";
		columnClass="";
		headerClass="";
		linkParams=null;
		comparator="org.displaytag.model.DefaultComparator";
	}

	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return Returns the property.
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property The property to set.
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	
	/*
	 * Descripción del campo propiedad
	 */
	public String getPropertyName() {
		return "property(" + property + ")";
	}

	/*
	 * Descripción del campo enlace
	 */
	public String getPropertyLink() {
		return "property(" + fieldLink + ")";
	}

	/**
	 * @return Returns the readOnly.
	 */
	public boolean isReadOnly() {
		return readOnly;
	}
	/**
	 * @param readOnly The readOnly to set.
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @return Returns the fieldType.
	 */
	public String getFieldType() {
		return fieldType;
	}
	/**
	 * @param fieldType The fieldType to set.
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle(EntityApp entityApp) {
		
		String title = "";
		
		try {
			title = entityApp.getLabel(getTitleKey());
		}
		catch (ISPACException e) {
		}
		
		return title; 
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * @return Returns the title.
     */
    public String getTitleKey() {
        return titleKey;
    }
    /**
     * @param title The title to set.
     */
    public void setTitleKey(String title) {
        this.titleKey = title;
    }
	/**
	 * @return Returns the fieldLink.
	 */
	public String getFieldLink() {
		return fieldLink;
	}
	/**
	 * @param fieldLink The fieldLink to set.
	 */
	public void setFieldLink(String fieldLink) {
		this.fieldLink = fieldLink;
	}

    /**
     * @deprecated Utilizar en su lugar un estilo CSS.
     * @return La anchura del campo.
     */
	public String getWidth ()
	{
		return this.width;
	}

    /**
     * @deprecated Utilizar en su lugar un estilo CSS
     * @param La anchura del campo.
     */
	public void setWidth (String width)
	{
		this.width = width;
	}

	public String getStyleClass()
	{
		return this.styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public String getHeaderClass()
	{
		return this.headerClass;
	}
	public void setHeaderClass(String header)
	{
		this.headerClass = header;
	}

	public boolean getSortable()
	{
		return this.sortable;
	}
	public void setSortable(boolean sortable)
	{
		this.sortable = sortable;
	}

	public String getMedia()
	{
		return this.media;
	}
	public void setMedia(String media)
	{
		this.media = media;
	}
	public String getDecorator()
	{
		return decorator;
	}
	public void setDecorator(String decorator)
	{
		this.decorator = decorator;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getUrlTarget()
	{
		return urlTarget;
	}
	public void setUrlTarget(String target)
	{
		this.urlTarget = target;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return Returns the styleId.
	 */
	public String getStyleId() {
		return styleId;
	}

	/**
	 * @param styleId The styleId to set.
	 */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * @return Returns the tooltip.
	 */
	public boolean isTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip The tooltip to set.
	 */
	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @return Returns the tooltipText.
	 */
	public String getTooltipText() {
		return "property(" + tooltipText + ")";
	}

	/**
	 * @param tooltipText The tooltipText to set.
	 */
	public void setTooltipText(String tooltipText) {
		this.tooltipText = tooltipText;
	}

	/**
	 * @return Returns the tooltipTitle.
	 */
	public String getTooltipTitle() {
		return tooltipTitle;
	}

	/**
	 * @param tooltipTitle The tooltipTitle to set.
	 */
	public void setTooltipTitle(String tooltipTitle) {
		this.tooltipTitle = tooltipTitle;
	}

    /**
     * @return Returns the tooltipTitle.
     */
    public String getTooltipTitleKey() {
        return tooltipTitleKey;
    }

    /**
     * @param tooltipTitle The tooltipTitle to set.
     */
    public void setTooltipTitleKey(String tooltipTitleKey) {
        this.tooltipTitleKey = tooltipTitleKey;
    }

	/**
	 * @return Returns the columnClass.
	 */
	public String getColumnClass() {
		if(columnClass == null){
			return "";
		}
		return columnClass;
	}

	/**
	 * @param columnClass The columnClass to set.
	 */
	public void setColumnClass(String columnClass) {
		this.columnClass = columnClass;
	}

	public void setPropertyValue(String propertyValue)
	{
	    this.propertyValue=propertyValue;
	}
	public String getPropertyValue()
	{
	    return propertyValue;
	}

    public void setPropertyValueKey(String propertyValueKey)
    {
        this.propertyValueKey=propertyValueKey;
    }
    public String getPropertyValueKey()
    {
        return propertyValueKey;
    }

    public Object getValue(Object obj)
    throws ISPACException
    {
        //Se devuelve el valor constante para la propiedad en el formateador
        // si es que ha sido definido así previamente.
        if (propertyValue!=null)
            return propertyValue;
        if (propertyValueKey!=null)
            return propertyValueKey;
        
        Object param[]={property};
        Class paramType[]={property.getClass()};
        try
        {
            Method method=obj.getClass().getMethod("getProperty",paramType);
            return method.invoke(obj,param);
        } catch (Exception e)
        {
            throw new ISPACException("BeanPropertyFmt.prepareFormat(Object obj) - El objeto no extiende ObjectBean",e);
        }
    }

    public Object getValue(ObjectBean objectbean)
    throws ISPACException
    {
        //Se devuelve el valor constante para la propiedad en el formateador
        // si es que ha sido definido así previamente.
        if (propertyValue!=null)
            return propertyValue;
        if (propertyValueKey!=null)
            return propertyValueKey;

        return objectbean.getProperty(property);
    }

    public Object formatProperty(Object obj)
    throws ISPACException
    {
        return format(getValue(obj));
    }

    public Object formatProperty(ObjectBean objectbean)
    throws ISPACException
    {
    	return format(getValue(objectbean));
    }

    public abstract Object format(Object value);

    /**
     * @return Devuelve el valor de la propiedad fieldParams.
     */
    public Map getLinkParams()
    {
        return linkParams;
    }
    /**
     * @param fieldParams Cambia el valor de la propiedad fieldParams.
     */
    public void setLinkParams(Map linkParams)
    {
        this.linkParams = linkParams;
    }

    public void addLinkParams(String idparam,String propertyvalue)
    {
        if (linkParams==null)
            linkParams=new HashMap();

        linkParams.put(idparam,propertyvalue);
    }



	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
	 * @param obj
	 * @return
	 * @throws ISPACException
	 */
	public String prepareFormat(Object obj)
	throws ISPACException
	{
	    //Se devuelve el valor constante para la propiedad en el formateador
	    // si es que ha sido definido así previamente.
	    if (propertyValue!=null)
	        return PROP_PROPERTYVALUE;
        if (propertyValueKey!=null)
            return propertyValueKey;
	    

	    Object param[]={property};
	    Class paramType[]={property.getClass()};
	    try
        {
            Method method=obj.getClass().getMethod("getProperty",paramType);
            preparedvalue = method.invoke(obj,param);

        } catch (Exception e)
        {
            throw new ISPACException("BeanPropertyFmt.prepareFormat(Object obj) - El objeto no extiende ObjectBean",e);
        }

        preparedvalue = format(preparedvalue);
		return PROP_PREPAREDVALUE;
	}

	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
	 * @param objectbean
	 * @return
	 * @throws ISPACException
	 */
	public String prepareFormat(ObjectBean objectbean)
	throws ISPACException
	{
	    //Se devuelve el valor constante para la propiedad en el formateador
	    // si es que ha sido definido así previamente.
	    if (propertyValue!=null)
	        return PROP_PROPERTYVALUE;
        if (propertyValueKey!=null)
            return propertyValueKey;
	    

		preparedvalue = objectbean.getProperty(property);
		preparedvalue = format(preparedvalue);
		return PROP_PREPAREDVALUE;
	}



	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
	 * @param obj
	 * @return
	 * @throws ISPACException
	 */
	public String prepareValue(Object obj)
	throws ISPACException
	{
	    //Se devuelve el valor constante para la propiedad en el formateador
	    // si es que ha sido definido así previamente.
	    if (propertyValue!=null)
	        return PROP_PROPERTYVALUE;
        if (propertyValueKey!=null)
            return propertyValueKey;
	    

	    Object param[]={property};
	    Class paramType[]={property.getClass()};
	    try
        {
            Method method=obj.getClass().getMethod("getProperty",paramType);
            preparedvalue = method.invoke(obj,param);

        } catch (Exception e)
        {
            throw new ISPACException("BeanPropertyFmt.prepareFormat(Object obj) - El objeto no extiende ObjectBean",e);
        }
		return PROP_PREPAREDVALUE;
	}
	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     *
     * @deprecated
	 * @param objectbean
	 * @return
	 * @throws ISPACException
	 */
	public String prepareValue(ObjectBean objectbean)
	throws ISPACException
	{
	    //Se devuelve el valor constante para la propiedad en el formateador
	    // si es que ha sido definido así previamente.
	    if (propertyValue!=null)
	        return PROP_PROPERTYVALUE;
        if (propertyValueKey!=null)
            return propertyValueKey;
	    

		preparedvalue = objectbean.getProperty(property);
		return PROP_PREPAREDVALUE;
	}

	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     *
     * @deprecated
	 * @return
	 */
	public Object getPreparedvalue()
	{
		return preparedvalue;
	}

	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
	 * @param preparedvalue
	 */
	public void setPreparedvalue(Object preparedvalue) {
		this.preparedvalue = preparedvalue;
	}


	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
	 * @return
	 */
	public Map getPreparedParams()
	{
		return preparedParams;
	}

	/**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
	 * @param preparedParams
	 */
	public void setPreparedParams(Map preparedParams) {
		this.preparedParams = preparedParams;
	}


    /**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
     * @param objectbean
     * @return
     * @throws ISPACException
     */
    public String prepareLinkParams(ObjectBean objectbean)
    throws ISPACException
    {
        preparedParams=new HashMap();
        if (linkParams==null)
            return BeanPropertyFmt.PROP_PREPAREDPARAMS;

        Iterator it=linkParams.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            Object value=objectbean.getProperty((String)entry.getValue());
            preparedParams.put(entry.getKey(),value);
        }
        return BeanPropertyFmt.PROP_PREPAREDPARAMS;
    }

    /**
     * Métodos obsoletos. Puede generar problemas si se cachea el formateador.
     * No utilizar.
     * @deprecated
     * @param obj
     * @return
     * @throws ISPACException
     */
    public String  prepareLinkParams(Object obj)
    throws ISPACException
    {
	    try
        {
		    Class paramType[]={String.class};
            Method method=obj.getClass().getMethod("getProperty",paramType);

            preparedParams=new HashMap();
            if (linkParams==null)
                return BeanPropertyFmt.PROP_PREPAREDPARAMS;

            Iterator it=linkParams.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry entry = (Map.Entry) it.next();
                Object value=method.invoke(obj,new Object[]{entry.getValue()});
                preparedParams.put(entry.getKey(),value);
            }
            return BeanPropertyFmt.PROP_PREPAREDPARAMS;
        } catch (Exception e)
        {
            throw new ISPACException("BeanPropertyFmt.preparedLinkParams(Object obj) - El objeto no extiende ObjectBean",e);
        }
    }

	public String getComparator() {
		return comparator;
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
}
