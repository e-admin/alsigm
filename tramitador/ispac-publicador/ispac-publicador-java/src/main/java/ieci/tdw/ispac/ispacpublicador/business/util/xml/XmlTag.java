package ieci.tdw.ispac.ispacpublicador.business.util.xml;

import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.Date;


public class XmlTag {

	public static String getXmlInstruction(String encoding) {
		return "<?xml version='1.0' encoding='" + encoding + "'?>";
	}

	public static String newTag(String name, String value, boolean includeCDATA) {
		String tag = null;
//		if (value == null || value.equals(""))
//			tag = "<" + name + "/>";
//		else
			tag = "<" + name + ">" + (includeCDATA ? newCDATA(value): value) + "</" + name + ">";
		return tag;
	}

	public static String newTag(String name, Date value) {
		return newTag(name, TypeConverter.toString(value), false);
	}

	public static String newTag(String name, int value) {
		return newTag(name, Integer.toString(value), false);
	}

	public static String newTag(String name, long value) {
		return newTag(name, Long.toString(value), false);
	}

	public static String newTag(String name, float value) {
		return newTag(name, Float.toString(value), false);
	}

	public static String newTag(String name, Object value) {
		if (value == null)
			return newTag(name, "", false);
		else
			//return newTag(name, newCDATA(value.toString()));
		    return newTag(name, value.toString(), false);
	}
	
	
	public static String newTag (String name, Object value, boolean includeCDATA){
		if (value == null)
			return newTag(name, "", includeCDATA);
		else
		    return newTag(name, value.toString(), includeCDATA);
	}
	

	public static String newAttr(String attName, String attVal) {
		String value;
		if (attVal == null)
			value = "";
		else
			value = "'" + attVal + "'";
		return " " + attName + "=" + value + " ";
	}

	public static String newTag (String name, String value, String attList)
	{
		if (attList == null || attList.equals(""))
			return newTag (name, value, true);

		if (value == null || value.equals(""))
			return  "<" + name + attList + "/>";
		else
			return "<" + name + attList + ">" + value + "</" + name + ">";
	}
	
	public static String newTag(String name, String value, int id) {
		return newTag(name, value, newAttr("id", Integer.toString(id)));
	}

	public static String newTag(String name, Date value, int id) {
		return newTag(name, TypeConverter.toString(value), newAttr("id", Integer.toString(id)));
	}

	public static String newTag(String name, int value, int id) {
		return newTag(name, Integer.toString(value), newAttr("id", Integer.toString(id)));
	}

	public static String newTag(String name, long value, int id) {
		return newTag(name, Long.toString(value), newAttr("id", Integer.toString(id)));
	}

	public static String newTag(String name, float value, int id) {
		return newTag(name, Float.toString(value), newAttr("id", Integer.toString(id)));
	}

	public static String newTag(String name, Object value, int id)
	{
		if (value == null)
			return newTag(name,null,newAttr("id", Integer.toString(id)));
		else
			return newTag(name,value.toString(),newAttr("id", Integer.toString(id)));
	}

	public static String newCDATA(String tag) {
		if (tag == null)
		    tag = "";
	    return "<![CDATA[" + tag + "]]>";
	}
}