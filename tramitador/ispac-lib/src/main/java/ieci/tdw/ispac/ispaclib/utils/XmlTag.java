/*
 * XmlTag.java
 *
 * Created on June 4, 2004, 1:33 PM
 */

package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class XmlTag {

	public static String getXmlInstruction(String encoding) {
        if (encoding==null || encoding.length()==0)
            return "<?xml version='1.0'?>";
		return "<?xml version='1.0' encoding='" + encoding + "'?>";
	}


    public static String newTag(String name, String value, boolean CDATA) {
        name = name.replaceAll(":", ".");
        String tag = null;
        if (value == null || value.equals(""))
            tag = "<" + name + "/>";
        else
            tag = "<" + name + ">" + (CDATA ? newCDATA(value): value)  + "</" + name + ">";
        return tag;
    }

    
    public static String newTag(String name, String value){
        return newTag(name, value, false);
    }
    
/*    
    public static String newTag(String name, String value) {
		String tag = null;
		if (value == null || value.equals(""))
			tag = "<" + name + "/>";
		else
			tag = "<" + name + ">" + value + "</" + name + ">";
		return tag;
	}
*/
    
	public static String newTag(String name, Date value) {
		return newTag(name, TypeConverter.toString(value));
	}

	public static String newTag(String name, int value) {
		return newTag(name, Integer.toString(value));
	}

	public static String newTag(String name, long value) {
		return newTag(name, Long.toString(value));
	}

	public static String newTag(String name, float value) {
		return newTag(name, Float.toString(value));
	}

	public static String newTag(String name, boolean value) {
		return newTag(name, TypeConverter.toString(value));
	}
	
	public static String newTag(String name, Collection values) {
		StringBuffer buffer = new StringBuffer();
		
		if (!CollectionUtils.isEmpty(values)) {
			Iterator it = values.iterator();
			while (it.hasNext()) {
				buffer.append(((XmlObject)it.next()).getXmlValues());
			}
		}

		return XmlTag.newTag(name, buffer.toString());
	}

	public static String newTag(String name, Object value) {
		if (value == null)
			return newTag(name, "");

		return newTag(name, value.toString());
	}

	public static String newAttr(String attName, String attVal) {
		return new StringBuffer()
			.append(" ")
			.append(attName)
			.append("='")
			.append(StringUtils.nullToEmpty(attVal))
			.append("'")
			.toString();
	}

	public static String newTag (String name, String value, String attList)
	{
		if (attList == null || attList.equals(""))
			return newTag (name, value);

		if (value == null || value.equals(""))
			return  "<" + name + attList + "/>";

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

		return newTag(name,value.toString(),newAttr("id", Integer.toString(id)));
	}

	public static String newCDATA(String data) {
		return "<![CDATA[" + ((data == null) ? "" : data) + "]]>";
	}
}