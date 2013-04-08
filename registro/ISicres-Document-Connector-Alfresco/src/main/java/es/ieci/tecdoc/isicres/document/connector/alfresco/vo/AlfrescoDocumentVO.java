package es.ieci.tecdoc.isicres.document.connector.alfresco.vo;

import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AlfrescoDocumentVO extends ISicresAbstractDocumentVO{
	public AlfrescoDocumentVO()
	{
		super();
		this.datosEspecificos	= new AlfrescoDatosEspecificosVO();
		this.configuration		= new AlfrescoConnectorConfigurationVO();
	}
}
