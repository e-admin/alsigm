package es.ieci.tecdoc.isicres.api.compulsa.business.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;

/**
 * Implementacion basica del interfaz {@link ConfiguracionCompulsaVO}
 * @author IECISA
 *
 */
public class BasicConfiguracionCompulsaVO implements ConfiguracionCompulsaVO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4499238240468058550L;
	/**
	 * De objetos de tipo <code>ISicresBasicDatosEspecificosValueVO</code>
	 */
	protected Map<String, String> values;
	
	
	public BasicConfiguracionCompulsaVO(){
		this.values=new HashMap();
	}

	public void fromXml(String xml) {
		XStream xstream =configureXstream();
		BasicConfiguracionCompulsaVO objectFromXml = (this.getClass().cast(xstream.fromXML(xml)));
		this.values.clear();
		this.values.putAll(objectFromXml.getValues());
	}

	public String toXml() {
		String result="";
		XStream xstream = configureXstream();
		xstream.alias("ConfiguracionCompulsa", this.getClass());
		result=xstream.toXML(this);
		return result;
	}
	
	protected XStream configureXstream(){
		XStream result = new XStream();
		result.alias("ConfiguracionCompulsa", this.getClass());
		return result;		
		
	}

	public Map <String, String>  getValues() {
		return values;
	}

	public void setValues(Map <String, String> values) {
		this.values = values;
	}

}
