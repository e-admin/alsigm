package es.ieci.tecdoc.isicres.document.connector.alfresco.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

import es.ieci.tecdoc.isicres.document.connector.vo.ISicresDatosEspecificos;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AlfrescoDatosEspecificosVO implements ISicresDatosEspecificos {
	
	protected Map values;
	protected List listAspects;
	protected String pathSpace;
	protected String nameStore;
	protected String FileKey;
	
	public AlfrescoDatosEspecificosVO(){
		this.values=new HashMap();
		this.listAspects = new ArrayList();		
	}
	
	
		
	/**
	 * @return the fileKey
	 */
	public String getFileKey() {
		return FileKey;
	}



	/**
	 * @param fileKey the fileKey to set
	 */
	public void setFileKey(String fileKey) {
		FileKey = fileKey;
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
	 * @return the listAspects
	 */
	public List getListAspects() {
		return listAspects;
	}

	/**
	 * @param listAspects the listAspects to set
	 */
	public void setListAspects(List listAspects) {
		this.listAspects = listAspects;
	}



	public void fromXml(String xml) {
		XStream xstream =configureXstream();
		AlfrescoDatosEspecificosVO objectFromXml = (AlfrescoDatosEspecificosVO)xstream.fromXML(xml);
		this.values.clear();
		this.values.putAll(objectFromXml.getValues());
	}

	public String toXml() {
		String result="";
		XStream xstream = configureXstream();
		xstream.alias("DatosEspecificos", this.getClass());
		xstream.alias("DatoEspecifico", AlfrescoDatosEspecificosValueVO.class);
		
		result=xstream.toXML(this);
		return result;
	}
	
	protected XStream configureXstream(){
		XStream result = new XStream();
		result.alias("DatosEspecificos", this.getClass());
		result.alias("DatoEspecifico", AlfrescoDatosEspecificosValueVO.class);
		return result;	
	}
	
	public Map getValues() {
		return values;
	}

	public void setValues(Map values) {
		this.values = values;
	}
}
