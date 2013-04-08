package es.ieci.tecdoc.isicres.api.compulsa.business.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BasicDatosEspecificosCompulsaVO implements
		DatosEspecificosCompulsaVO , Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -1577025884393497890L;
	protected Map values;
	
	public BasicDatosEspecificosCompulsaVO(){
		this.values=new HashMap();
	}
	
	public Object getValue(String key){
		Object result=this.getValues().get(key);
		return result;
	}
	public void putValue(String key, Object value){
		this.getValues().put(key, value);
	}

	public Map getValues() {
		return values;
	}

	public void setValues(Map values) {
		this.values = values;
	}
	

}
