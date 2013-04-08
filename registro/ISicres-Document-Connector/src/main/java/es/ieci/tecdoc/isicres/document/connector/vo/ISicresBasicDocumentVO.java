package es.ieci.tecdoc.isicres.document.connector.vo;
/** 
 * @author Iecisa 
 * @version $$Revision$$ 
 * 
 * ${tags} 
 */ 

/**
 * Clase basica que hereda de <code>IsicresAbstractDocumentVO</code> que no contiene datos especificos
 *
 */
public class ISicresBasicDocumentVO extends ISicresAbstractDocumentVO {
	
	public ISicresBasicDocumentVO()
	{
		super();
		this.datosEspecificos= new ISicresBasicDatosEspecificosVO();
		this.configuration= new IsicresBasicConnectorConfigurationVO();
	}

}
