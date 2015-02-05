package ieci.tdw.ispac.deadline;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

public class HolydayDef extends XmlObject {

	String name;
	String date;
	
	private static final Properties PROPERTIES = new Properties();
	private static final String KEY = "NAME";
	
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "NAME"),
				new Property(2, "DATE"),
		});
	}
	
	public HolydayDef(){super(PROPERTIES, KEY);};
	
	public HolydayDef(String name, String date) throws ISPACException {
		super(PROPERTIES, KEY);
		this.name = name;
		this.date = date;
	}

	public String getXmlValues() {
		StringBuffer buff = new StringBuffer();
		buff.append("<holyday>");
		buff.append("<name>").append(name).append("</name>");
		buff.append("<date>").append(date).append("</date>");
		buff.append("</holyday>");
		return buff.toString();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
