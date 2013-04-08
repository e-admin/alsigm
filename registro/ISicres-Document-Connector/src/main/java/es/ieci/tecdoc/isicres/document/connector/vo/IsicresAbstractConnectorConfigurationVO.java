package es.ieci.tecdoc.isicres.document.connector.vo;
/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

/**
 *  Clase abstracta para almacenar la configuracion necesario por el connector
 */
public abstract class IsicresAbstractConnectorConfigurationVO {
	public abstract String toXml();		
	
	public abstract void fromXml(String xml);		

}
