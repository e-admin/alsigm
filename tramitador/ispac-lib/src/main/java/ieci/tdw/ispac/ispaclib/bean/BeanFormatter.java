/*
 * Created on 01-jun-2004
 *
 *
 */
package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.ArrayList;

public class BeanFormatter extends ArrayList {

	/**
	 * Título del formateador
	 */
	protected String title;
	/**
	 * Tipo de formateador para diferenciar su presentación en la página JSP
	 */
	protected String fieldType;

	/**
	 * Estilo CSS del formateador
	 */
	protected String styleClass;


	/////////////////////////////////////////
	// Propiedades adicionales para utilizar con displaytag
	// y en especial <code>display:table</code>

	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Determina el tipo de ordenación
	 *  Valores: list,...
	 */
	protected String sort;
	
	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Posibilita la exportación de los datos
	 * Valores: true,false
	 */
	protected boolean export;

	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * URL a utilizar para la ordenación
	 */
	protected String requestURI;
	
	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Número de campo por el que se ordena al mostrar la lista (empieza en 1)
	 */
	protected int defaultSort;
	
	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Orden al mostrar la lista (ascending/descending)
	 */
	protected String defaultOrder;
	
	/**
	 * Propiedad adicional para utilizar con displaytag.
	 * Tamaño de página (empieza en 1)
	 */
	protected int pageSize;

	public BeanFormatter()
	{
	    title="";
	    fieldType="";
	    styleClass="tableDisplay";
	    sort="list";
	    export=true;
	    requestURI="";
	    defaultSort=0;
	    defaultOrder="ascending";
	    pageSize=15;
	}

	public void add(BeanPropertyFmt beanfmt) {
		super.add(beanfmt);
	}

    /**
     * @return Devuelve el valor de la propiedad export.
     */
    public boolean getExport()
    {
        return export;
    }
    /**
     * @param export Cambia el valor de la propiedad export.
     */
    public void setExport(boolean export) {
    	this.export = export;
    }
    public void setExport(String export)
    {
    	if (export.toUpperCase().equals("true")) {
    		this.export = true;
    	}
    	else {
    		this.export = false;
    	}
    }
    
    /**
     * @return Devuelve el valor de la propiedad fieldType.
     */
    public String getFieldType()
    {
        return fieldType;
    }
    /**
     * @param fieldType Cambia el valor de la propiedad fieldType.
     */
    public void setFieldType(String fieldType)
    {
        this.fieldType = fieldType;
    }
    
    /**
     * @return Devuelve el valor de la propiedad requestURI.
     */
    public String getRequestURI()
    {
        return requestURI;
    }
    /**
     * @param requestURI Cambia el valor de la propiedad requestURI.
     */
    public void setRequestURI(String requestURI)
    {
        this.requestURI = requestURI;
    }
    
    /**
     * @return Devuelve el valor de la propiedad sort.
     */
    public String getSort()
    {
        return sort;
    }
    /**
     * @param sort Cambia el valor de la propiedad sort.
     */
    public void setSort(String sort)
    {
        this.sort = sort;
    }
    
    /**
     * @return Devuelve el valor de la propiedad styleClass.
     */
    public String getStyleClass()
    {
        return styleClass;
    }
    /**
     * @param styleClass Cambia el valor de la propiedad styleClass.
     */
    public void setStyleClass(String styleClass)
    {
        this.styleClass = styleClass;
    }
    
    /**
     * @return Devuelve el valor de la propiedad title.
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * @param title Cambia el valor de la propiedad title.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

	/**
	 * @return Returns the defaultSort.
	 */
	public int getDefaultSort() {
		return defaultSort;
	}
	/**
	 * @param defaultSort The defaultSort to set.
	 */
	public void setDefaultSort(int defaultSort) {
		this.defaultSort = defaultSort;
	}
	public void setDefaultSort(String defaultSort) {
		this.defaultSort = TypeConverter.parseInt(defaultSort, 0);
	}

	/**
	 * @return Returns the defaultOrder.
	 */
	public String getDefaultOrder() {
		return defaultOrder;
	}
	/**
	 * @param defaultOrder The defaultOrder to set.
	 */
	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = TypeConverter.parseInt(pageSize, 15);
	}
    
}