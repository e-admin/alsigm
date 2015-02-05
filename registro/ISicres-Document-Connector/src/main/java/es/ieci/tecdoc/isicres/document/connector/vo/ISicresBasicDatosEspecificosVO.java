package es.ieci.tecdoc.isicres.document.connector.vo;
/** 
 * @author Iecisa 
 * @version $$Revision$$ 
 * 
 * ${tags} 
 */ 


import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

/**
 * Implementacion básica del interfaz <code>IsicresDatosEspecificos</code>
 *
 */
public class ISicresBasicDatosEspecificosVO implements
		ISicresDatosEspecificos {
	
	/**
	 * De objetos de tipo <code>ISicresBasicDatosEspecificosValueVO</code>
	 */
	protected Map values;
	
	 
	
	public ISicresBasicDatosEspecificosVO(){
		this.values=new HashMap();
	}

	public void fromXml(String xml) {
		XStream xstream =configureXstream();
		ISicresBasicDatosEspecificosVO objectFromXml = (ISicresBasicDatosEspecificosVO)xstream.fromXML(xml);
		this.values.clear();
		this.values.putAll(objectFromXml.getValues());
	}

	public String toXml() {
		String result="";
		XStream xstream = configureXstream();
		xstream.alias("DatosEspecificos", this.getClass());
		result=xstream.toXML(this);
		return result;
	}
	
	protected XStream configureXstream(){
		XStream result = new XStream();
		result.alias("DatosEspecificos", this.getClass());
		result.alias("DatoEspecifico", ISicresBasicDatosEspecificosValueVO.class);
		return result;		
		
	}

	public Map getValues() {
		return values;
	}

	public void setValues(Map values) {
		this.values = values;
	}

}
