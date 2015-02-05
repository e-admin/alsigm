package es.ieci.plusvalias.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.DocumentException;
import org.dom4j.Element;

public class W3cXmlUtils {
	public static void setNode(org.dom4j.Document doc, String xpath, String value) throws DocumentException
	{
		if (value != null)
		{
			((Element)doc.selectSingleNode(xpath)).setText(value.trim());
		}
	}

	public static void setNode(org.dom4j.Document doc, String xpath, Date value) throws DocumentException
	{
		if (value != null)
		{
			setNode(doc, xpath, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
		}
	}

	public static void setNode(org.dom4j.Document doc, String xpath, int value) throws DocumentException
	{
		setNode(doc, xpath, String.valueOf(value));
	}

	public static void setNode(org.dom4j.Document doc, String xpath, double value) throws DocumentException
	{
		setNode(doc, xpath, new DecimalFormat("#.##").format(value).replaceAll(",", "."));
	}

}
