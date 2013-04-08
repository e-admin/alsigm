package es.ieci.tecdoc.isicres.document.connector.vo;
/** 
 * @author Iecisa 
 * @version $$Revision$$ 
 * 
 * ${tags} 
 */

/**
 * Clase abstracta que contiene la informacion necesaria de un documento y que se usará en el intefaz del conector
 *
 */
public abstract class ISicresAbstractDocumentVO extends ISicresAbstractBaseConnectorVO{

	/**
	 * identificador del documento 
	 */
	protected String id;
	
	/**
	 * nombre del documento
	 */
	protected String name;
	
	/**
	 * contenido binario del documento
	 */
	protected byte [] content; 
	
	/**
	 * datos adicionales del documento, podran contener por ejemplo metadatos, datos propios de la implementacion del conector, 
	 * dependera de la implementacion concreta final del gestor documental 
	 */
	protected ISicresDatosEspecificos datosEspecificos;
	
	/**
	 * datos de configuracion referentes al connector 
	 */
	protected IsicresAbstractConnectorConfigurationVO configuration;

	
	
	
	public ISicresAbstractDocumentVO(){
		id="";
		name="";
		content=null;
		datosEspecificos=null;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public ISicresDatosEspecificos getDatosEspecificos() {
		return datosEspecificos;
	}

	public void setDatosEspecificos(ISicresDatosEspecificos datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public IsicresAbstractConnectorConfigurationVO getConfiguration() {
		return configuration;
	}

	public void setConfiguration(
			IsicresAbstractConnectorConfigurationVO configuration) {
		this.configuration = configuration;
	}

	
}
