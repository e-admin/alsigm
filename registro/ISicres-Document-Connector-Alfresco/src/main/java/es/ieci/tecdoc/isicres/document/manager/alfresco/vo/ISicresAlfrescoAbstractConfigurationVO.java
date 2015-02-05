package es.ieci.tecdoc.isicres.document.manager.alfresco.vo;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public abstract class ISicresAlfrescoAbstractConfigurationVO {
	public abstract String toXml();		
	
	public abstract void fromXml(String xml);		
}
