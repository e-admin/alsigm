/*
 * Created on 21-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.util.xml;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Clase para gestionar la información de un contexto
 * @author Ildefonso Noreña
 *
 */
public abstract class Context {

	/**
	 * Comment for <code>facade</code>
	 */
	protected XmlFacade facade;

	/**
	 * Documento xml
	 */
	private String xml;

	/**
	 * Nombre del nodo que se toma como raiz del documento
	 */
	protected String root;
	
	/**
	 * Construye un nuevo Context inicializando.
	 * @param xml
	 */
	public Context(String xml, String root){
	    this.xml = xml;
	    this.root = root;
	    if (StringUtils.isNotBlank(xml)) {
    		facade = new XmlFacade(xml);
	    }
	}
	
	protected abstract int getInt(String property);
	protected abstract Date getDate(String property);
	protected abstract Timestamp getTimestamp(String property);
	protected abstract String get(String property);
	
	public String toString(){
	    return xml;
	}	
}
