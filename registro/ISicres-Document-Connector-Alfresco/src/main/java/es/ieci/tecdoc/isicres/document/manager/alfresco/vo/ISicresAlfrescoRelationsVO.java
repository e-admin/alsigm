package es.ieci.tecdoc.isicres.document.manager.alfresco.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import es.ieci.tecdoc.isicres.document.connector.alfresco.vo.AlfrescoAspectVO;


/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class ISicresAlfrescoRelationsVO extends ISicresAlfrescoAbstractConfigurationVO{

	private List listAspects;
	private HashMap  hashMapDatosEspecificos;
	private String pathSpace;
	private String nameStore;
	private String fileKey;
	
	public ISicresAlfrescoRelationsVO(){
		listAspects = new ArrayList();
		hashMapDatosEspecificos = new HashMap();
	}
		
	
	
	/**
	 * @return the fileKey
	 */
	public String getFileKey() {
		return fileKey;
	}



	/**
	 * @param fileKey the fileKey to set
	 */
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}



	/**
	 * @return the listAspects
	 */
	public List getListAspects() {
		return listAspects;
	}

	/**
	 * @return the hashMapDatosEspecificos
	 */
	public HashMap getHashMapDatosEspecificos() {
		return hashMapDatosEspecificos;
	}



	/**
	 * @return the nameStore
	 */
	public String getNameStore() {
		return nameStore;
	}

	/**
	 * @param nameStore the nameStore to set
	 */
	public void setNameStore(String nameStore) {
		this.nameStore = nameStore;
	}

	/**
	 * @param hashMapDatosEspecificos the hashMapDatosEspecificos to set
	 */
	public void setHashMapDatosEspecificos(HashMap hashMapDatosEspecificos) {
		this.hashMapDatosEspecificos = hashMapDatosEspecificos;
	}



	/**
	 * @param listAspects the listAspects to set
	 */
	public void setListAspects(List listAspects) {
		this.listAspects = listAspects;
	}



	
	/**
	 * @return the pathSpace
	 */
	public String getPathSpace() {
		return pathSpace;
	}

	/**
	 * @param pathSpace the pathSpace to set
	 */
	public void setPathSpace(String pathSpace) {
		this.pathSpace = pathSpace;
	}

	public void fromXml(String xml) {
		XStream xstream = configureXstream();		
		ISicresAlfrescoRelationsVO objectFromXml = (ISicresAlfrescoRelationsVO)xstream.fromXML(xml);
		this.listAspects.addAll(objectFromXml.getListAspects());
		this.hashMapDatosEspecificos.putAll(objectFromXml.getHashMapDatosEspecificos());		
	}

	public String toXml() {
		String result="";
		XStream xstream = configureXstream();		
		result=xstream.toXML(this);
		return result;
	}
	
	protected XStream configureXstream(){
		XStream result = new XStream();
		//result.addImplicitCollection(HashMap.class, "hashMapDatosEspecificos");
		result.alias("Configuracion", this.getClass());		
		
		result.aliasField("Aspects", this.getClass(), "listAspects");
		result.alias("Aspect", AlfrescoAspectVO.class);
		
		result.aliasField("TiposDeContenido", this.getClass(), "hashMapDatosEspecificos");
		//result.alias("Tipo de Contenido", hashMapDatosEspecificos.getClass());
		result.alias("Valores", ISicresAlfrescoDatosEspecificosValueVO.class);
		result.alias("Key", String.class);		
		return result;	
	}

}
