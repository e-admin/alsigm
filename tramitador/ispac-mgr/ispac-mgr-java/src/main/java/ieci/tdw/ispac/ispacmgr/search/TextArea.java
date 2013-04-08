package ieci.tdw.ispac.ispacmgr.search;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Map;

import org.apache.log4j.Logger;

public class TextArea {
	
	protected static Logger logger = Logger.getLogger(TextArea.class);
	
	
	private String name;
	
	public TextArea(String name) {
		super();
		
		this.name=name;
		
	}

	public TextArea() {
		super();
	
	}
	
	public String format(String rows, String cols, String defaultValue, Map values){
		String value="";
		if(values!=null && values.containsKey(name)){
			value+=values.get(name);
		}
		else if(StringUtils.isNotBlank(defaultValue)){
			value+=defaultValue;
		}
		String cadena="<textarea class='input' name='values("+name+")' cols='"+cols+"' rows='"+rows+"'>"+value+"</textarea>";
		
		
		return cadena;
		
	}
	

}
