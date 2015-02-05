/*
 * Created on 02-jun-2004
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.context;

import ieci.tdw.ispac.ispacpublicador.business.exceptions.RenderException;
import ieci.tdw.ispac.ispacpublicador.business.util.xml.Context;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Clase que se encarga de la construcción de un contexto,
 * con la información a manejar dentro de una Acción asociada con una regla.
 *  @author Ildefonso Noreña
 *
 *
*<?xml version='1.0' encoding='ISO-8859-1'?>
*<context>
*	-------CAMPOS FIJOS---------
*	<idhito>367225</idhito>	
*	<idprocedimiento>108</idprocedimiento>
*	<idfase>999999</idfase>
*	<idtramite>999999</idtramite>
*	<tipodoc>0</tipodoc>
*	<idobjeto>3672251132574898</idobjeto>
*   <fecha>2005-11-17 18:37:52.0</fecha>
*   <idevento>0</idevento>
*	-------CAMPOS EXTRA (dependen del sistema externo)------
*		<idmproc>108</idmproc>
*		<idprocmain>1031</idprocmain>
*		<idproc>1031</idproc>
*		<datos>datos</datos>
*		<idnodomain>0</idnodomain>
*</context>
*/
//TODO Reconstruir el contexto
public class MilestoneContext extends Context
{

    /**
     * Mapa que contendrá las propiedades del contexto.
     */
    private Map properties = null; 

    protected Logger logger = Logger.getLogger(MilestoneContext.class);
 
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    
    protected int getInt(String property){
	    getProperties();
        return Integer.parseInt(""+properties.get(property));
	}

    protected Date getDate(String property){
        getProperties();
        try {
            String date = ""+properties.get(property);
            if (date != null)
                return format.parse(date);
        } catch (ParseException e) {
            RenderException.show(logger, e);            
        }
        return null;
	}

    protected Timestamp getTimestamp(String property){
        getProperties();
        
        try {
            String timestamp = facade.get(root+"/"+property+"/text()");
            if (timestamp != null)
                return new Timestamp(format.parse(timestamp).getTime()) ;
        } catch (ParseException e) {
            RenderException.show(logger, e);
        }
        return null;
    }
	
    protected String get(String property){
        getProperties();
	    return ""+properties.get(property);
	}    
    
	/**
     * @param xml
     */
    public MilestoneContext(String xml, String root) {
        super(xml, root);
    }
    public int getIdHito(){
	    return this.getInt(ContextProperties.CTX_MILESTONE);
	}
	public int getIdPcd(){
	    return getInt(ContextProperties.CTX_PROCEDURE);
	}
	public int getIdFase(){
	    return getInt(ContextProperties.CTX_STAGE);
	}
	public int getIdTramite(){
	    return getInt(ContextProperties.CTX_TASK);
	}
	public int getTipoDoc(){
	    return getInt(ContextProperties.CTX_DOCTYPE);
	}
	public String getIdObjeto(){
	    return get(ContextProperties.CTX_OBJECT);
	}
	public int getIdEvento(){
	    return getInt(ContextProperties.CTX_EVENT);
	}
	public int getIdInfo(){
	    return getInt(ContextProperties.CTX_INFO);
	}
	
	public Timestamp getTimestamp(){
	    return getTimestamp(ContextProperties.CTX_DATE);
	}

	public Date getFecha(){
	    return getDate(ContextProperties.CTX_DATE);
	}
	
	public Map getProperties(){
	    if (properties == null){
	        properties = new LinkedHashMap();
	        if (facade != null) {
			    NodeIterator it = facade.getNodeIterator(root);
			    Node node = it.nextNode();
			    NodeList nodeList = node.getChildNodes();
			    for(int i =0; i<nodeList.getLength(); i++){
			        node = nodeList.item(i);
			        String value = (node.getFirstChild() == null) ? "" : node.getFirstChild().getNodeValue();
			        properties.put(node.getNodeName(), value);
			    }
	        }
	    }
	    return properties; 	    
	}  
	
	
	
}
