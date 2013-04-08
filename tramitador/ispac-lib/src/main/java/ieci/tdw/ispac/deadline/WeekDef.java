package ieci.tdw.ispac.deadline;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

public class WeekDef extends XmlObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String id;
	String day;
	
	private static final Properties PROPERTIES = new Properties();
	private static final String KEY = "ID";
	
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "ID"),
				new Property(1, "DAY"),
		});
	}
	
	public WeekDef(){super(PROPERTIES, KEY);};


	public WeekDef(String id, String day) throws ISPACException {
		super(PROPERTIES, KEY);
		this.id = id;
		this.day = day;
	}

	public String getXmlValues() {
		StringBuffer buff = new StringBuffer();
		//buff.append("<week>");
		buff.append("<day>").append(day).append("</day>");
		//buff.append("</week>");
		return buff.toString();
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	



}
