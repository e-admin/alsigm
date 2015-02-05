//
// FileName: AxSf.java
//
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.ieci.tecdoc.common.invesdoc.Idocfmt;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;

/**
 * @author lmvicente
 * @version @since @creationDate 05-mar-2004
 */

public class AxSf extends AbstractAx implements Serializable {

	// Oficina de registro
	private ScrOfic fld5 = null;
	// Nombre Oficina de registro
	private String fld5Name = null;

	// Origen
	private ScrOrg fld7 = null;
	// Nombre Origen
	private String fld7Name = null;

	// Destino
	private ScrOrg fld8 = null;
	// Nombre Destino
	private String fld8Name = null;

	// ///////////////////////////TODO
	// bookTypeConf///////////////////////////////////
	// literal de tipo de libro :Registro de entrada o Registro de salida según
	// el locale
	private String literalBookType = null;
	// ////////////////////////////////////////////////////
	// ////////////////
	private Locale locale = null;

	private static final String dateShortFormat = "date.shortFormat";

	private static final String stringLiteralBookType = "register.type.literal.{0}";

	// ////////////////
	private static final String TABLE = "table.";

	private static final String QUERY = "query.";

	private static final String FORM = "form.";

	private static final String PAGE = "page.";

	public static final String FLD = "fld";

	public static final String FLD2_FIELD = "fld2";
	public static final String FLD4_FIELD = "fld4";
	public static final String FLD11_FIELD = "fld11";
	public static final String FLD6_FIELD = "fld6";
	public static final String FLD18_FIELD = "fld18";
	public static final String FLD14_FIELD = "fld14";
	public static final String FLD5_FIELD = "fld5";
	public static final String FLD7_FIELD = "fld7";
	public static final String FLD8_FIELD = "fld8";
	public static final String FLD12_FIELD = "fld12";
	public static final String FLD13_FIELD = "fld13";
	public static final String FLD16_FIELD = "fld16";
	public static final String FLD1_FIELD = "fld1";
	// Añadidos 22 06 2010
	public static final String FLD3_FIELD = "fld3";
	public static final String FLD10_FIELD = "fld10";
	public static final String FLD15_FIELD = "fld15";
	public static final String FLD19_FIELD = "fld19";
	public static final String FLD9_FIELD = "fld9";
	public static final String FDRID_FIELD = "fdrid";
	//Intercambio Registral
	public static final String FLD503_FIELD = "fld503";
	public static final String FLD504_FIELD = "fld504";
	public static final String FLD505_FIELD = "fld505";
	public static final String FLD506_FIELD = "fld506";

	public static final int FLD2_FIELD_ID = 2;
	public static final int FLD4_FIELD_ID = 4;
	public static final int FLD11_FIELD_ID = 11;
	public static final int FLD6_FIELD_ID = 6;
	public static final int FLD18_FIELD_ID = 18;
	public static final int FLD14_FIELD_ID = 14;
	public static final int FLD5_FIELD_ID = 5;
	public static final int FLD7_FIELD_ID = 7;
	public static final int FLD8_FIELD_ID = 8;
	public static final int FLD12_FIELD_ID = 12;
	public static final int FLD13_FIELD_ID = 13;
	public static final int FLD16_FIELD_ID = 16;

	
	/**
	 * campo expone (intercambio registral)
	 */
	public static final int FLD501_FIELD_ID = 501;
	
	/**
	 * campo solicita  (intercambio regisltra)
	 */
	public static final int FLD502_FIELD_ID = 502;
	
	/**
	 * 
	 */
	public static final int FLD503_FIELD_ID = 503;
	/**
	 * campo documentacion fisica requerida  (intercambio regisltra)
	 */
	public static final int FLD504_FIELD_ID = 504;
	/**
	 * campo documentacion fisica complementaria (intercambio regisltra)
	 */
	public static final int FLD505_FIELD_ID = 505;
	/**
	 * campo sin documentacion fisica (intercambio regisltra)
	 */
	public static final int FLD506_FIELD_ID = 506;
	
	

	protected Map attributesClasses = new HashMap();
	protected Map attributesValues = new HashMap();
	protected Map attributesSQLTypes = new HashMap();

	// protected Map attributesNullable = new HashMap();
	protected Map attributesSQLNames = new HashMap();
	protected Map attributesSQLScale = new HashMap();

	protected List attributesNames = new ArrayList();

	protected Idocfmt format = null;

	protected AxXf axxf = null;

	protected Map lenFields = new HashMap();

	protected Map extendedFields = new HashMap();

	protected transient ResourceBundle rb = null;

	protected static final String ISICRES_FILE_NAME = "resources/ISicres-Server";

	protected static final String AXSF_PREFIX = "a{0}sf.{1}";


	protected boolean isModified = false;

	protected List proposedExtendedFieldIds = new ArrayList();

	protected Map precisions = new HashMap();

	public AxSf() {
	}

	public List getProposedExtendedFields() {
		return proposedExtendedFieldIds;
	}

	public void addProposedExtendedFiels(Integer fldid) {
		proposedExtendedFieldIds.add(fldid);
	}

	public Map getPrecisions() {
		return precisions;
	}

	public void addPrecision(String name, int precision) {
		precisions.put(name, new Integer(precision));
	}

	/**
	 * @return Returns the lenFields.
	 */
	public Map getLenFields() {
		return lenFields;
	}

	/**
	 * @param lenFields
	 *            The lenFields to set.
	 */
	public void setLenFields(Map lenFields) {
		this.lenFields = lenFields;
	}

	/**
	 * @return Returns the extendedFields.
	 */
	public Map getExtendedFields() {
		return extendedFields;
	}

	/**
	 * @param extendedFields
	 *            The extendedFields to set.
	 */
	public void setExtendedFields(Map extendedFields) {
		this.extendedFields = extendedFields;
	}

	public void addExtendedField(Integer fldid, AxXf axxsf) {
		extendedFields.put(fldid, axxsf);
	}

	/**
	 * @return Returns the isModified.
	 */
	public boolean isModified() {
		return true;
	}

	/**
	 * @param isModified
	 *            The isModified to set.
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	// public int hashCode() {
	// return attributesClasses.hashCode() + attributesValues.hashCode() +
	// attributesSQLTypes.hashCode()
	// + attributesSQLScale.hashCode() + attributesNames.hashCode();
	// }

	public Object clone() {
		return null;
	}

	/**
	 * @return Returns the attributesClasses.
	 */
	public Map getAttributesClasses() {
		return attributesClasses;
	}

	/**
	 * @param attributesClasses
	 *            The attributesClasses to set.
	 */
	public void setAttributesClasses(Map attributesClasses) {
		this.attributesClasses = attributesClasses;
	}

	/**
	 * @return Returns the attributesSQLScale.
	 */
	public Map getAttributesSQLScale() {
		return attributesSQLScale;
	}

	/**
	 * @param attributesSQLScale
	 *            The attributesSQLScale to set.
	 */
	public void setAttributesSQLScale(Map attributesSQLScale) {
		this.attributesSQLScale = attributesSQLScale;
	}

	/**
	 * @return Returns the attributesSQLTypes.
	 */
	public Map getAttributesSQLTypes() {
		return attributesSQLTypes;
	}

	/**
	 * @param attributesSQLTypes
	 *            The attributesSQLTypes to set.
	 */
	public void setAttributesSQLTypes(Map attributesSQLTypes) {
		this.attributesSQLTypes = attributesSQLTypes;
	}

	/**
	 * @return Returns the attributesValues.
	 */
	public Map getAttributesValues() {
		return attributesValues;
	}

	public void setAttributeValue(String field, Object value) {
		if (attributesNames.contains(field)) {
			attributesNames.remove(field);
			attributesValues.remove(field);
		}
		attributesNames.add(field);
		attributesValues.put(field, value);
		isModified = true;
	}

	/**
	 * @param attributesValues
	 *            The attributesValues to set.
	 */
	public void setAttributesValues(Map attributesValues) {
		this.attributesValues = attributesValues;
		isModified = true;
	}

	// ////////////////////////////
	public String getLocaleAttributeDateFormat(Locale locale,
			String defaultValue) {
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}
			return rb.getString(dateShortFormat);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	// ////////////////////////////
	public String getLocaleAttributeLiteralTypeBook(Locale locale,
			Integer bookType) {
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}
			return rb.getString(MessageFormat.format(stringLiteralBookType,
					new String[] { bookType.toString() }));
		} catch (Exception e) {
			return "";
		}

	}

	public String getLocaleAttributeName(Integer id, String name,
			String defaultValue) {
		return getLocaleAttributeName(id, name, Locale.getDefault(),
				defaultValue);
	}

	public String getLocaleAttributeName(Integer id, String name,
			Locale locale, String defaultValue) {
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}
			return rb.getString(MessageFormat.format(AXSF_PREFIX, new String[] {
					id.toString(), name }));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getLocaleAttributeNameQuery(Locale locale, String defaultValue) {
		String propertie = null;
		int pos = 0;
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}

			pos = defaultValue.indexOf(":");
			propertie = defaultValue.replaceAll(" ", ".").replaceAll(":", "");

			if (pos != -1) {
				propertie = rb.getString(propertie) + ":";
			} else {
				propertie = rb.getString(propertie);
			}

			return propertie;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getLocaleAttributeNameTable(Locale locale, String defaultValue) {
		String propertie = null;
		int pos = 0;
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}

			pos = defaultValue.indexOf(":");
			propertie = defaultValue.replaceAll(" ", ".").replaceAll(":", "");

			if (pos != -1) {
				propertie = rb.getString(propertie) + ":";
			} else {
				propertie = rb.getString(propertie);
			}

			return propertie;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getLocaleAttributeNameForm(Locale locale, String defaultValue) {
		String propertie = null;
		int pos = 0;
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}

			pos = defaultValue.indexOf(":");
			propertie = defaultValue.replaceAll(" ", ".").replaceAll(":", "");

			if (pos != -1) {
				propertie = rb.getString(propertie) + ":";
			} else {
				propertie = rb.getString(propertie);
			}

			return propertie;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getLocaleAttributePage(Locale locale, String defaultValue) {
		String propertie = null;
		int pos = 0;
		try {
			if (rb == null) {
				rb = ResourceBundle.getBundle(ISICRES_FILE_NAME, locale);
			}

			pos = defaultValue.indexOf(":");
			propertie = defaultValue.replaceAll(" ", ".").replaceAll(":", "");

			if (pos != -1) {
				propertie = rb.getString(propertie) + ":";
			} else {
				propertie = rb.getString(propertie);
			}

			return propertie;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public void clean() {
		// attributesSQLTypes.clear();
		// attributesSQLScale.clear();
	}

	/**
	 * @return Returns the format.
	 */
	public Idocfmt getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            The format to set.
	 */
	public void setFormat(Idocfmt format) {
		this.format = format;
	}

	public List getAttributesNames() {
		return attributesNames;
	}

	public void setAttributesNames(List attributesNames) {
		this.attributesNames.clear();
		for (Iterator it = attributesNames.iterator(); it.hasNext();) {
			addAttributeName(((String) it.next()));
		}
	}

	public void addAttributeName(String name) {
		if (name != null && !attributesNames.contains(name.toLowerCase())) {
			attributesNames.add(name.toLowerCase());
		}
	}

	public void addAttributeValue(String name, Object attValue) {
		if (name != null && attValue != null && attributesNames.contains(name)) {
			attributesValues.put(name.toLowerCase(), attValue);
			isModified = true;
		}
	}

	public void addAttributeClass(String name, String attClass) {
		if (name != null && attClass != null && attributesNames.contains(name)) {
			attributesClasses.put(name.toLowerCase(), attClass);
		}
	}

	public void addAttributeSQLType(String name, Integer value) {
		if (name != null && value != null && attributesNames.contains(name)) {
			attributesSQLTypes.put(name, value);
		}
	}

	// public void addAttributeSQLName(String name, String value) {
	// if (name != null && value != null && attributesNames.contains(name)) {
	// attributesSQLNames.put(name, value);
	// }
	// }

	public void addAttributeSQLScale(String name, Integer value) {
		if (name != null && value != null && attributesNames.contains(name)) {
			attributesSQLScale.put(name, value);
		}
	}

	// public void addAttributeNullable(String name, Boolean value) {
	// if (name != null && value != null && attributesNames.contains(name)) {
	// attributesNullable.put(name, value);
	// }
	// }

	public Object getAttributeValue(String name) {
		if (name != null) {
			if (attributesValues.get(name) != null
					&& attributesValues.get(name).equals("")) {
				return null;
			} else {
				return attributesValues.get(name);
			}
		} else {
			return null;
		}
	}

	public String getAttributeValueAsString(String name) {
		if (name != null) {
			if (attributesValues.get(name) != null
					&& !attributesValues.get(name).equals("")) {
				return attributesValues.get(name).toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Class getAttributeClass(String name) {
		if (name != null && attributesClasses.get(name) != null) {
			try {
				return Class.forName((String) attributesClasses.get(name));
			} catch (ClassNotFoundException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getAttributeClassName(String name) {
		if (name != null && attributesClasses.get(name) != null) {
			return (String) attributesClasses.get(name);
		} else {
			return null;
		}
	}

	public Integer getAttributeSQLType(String name) {
		if (name != null) {
			return (Integer) attributesSQLTypes.get(name);
		} else {
			return null;
		}
	}

	public Integer getAttributeSQLScale(String name) {
		if (name != null) {
			return (Integer) attributesSQLScale.get(name);
		} else {
			return null;
		}
	}

	// public String getAttributeSQLName(String name) {
	// if (name != null) {
	// return (String) attributesSQLNames.get(name);
	// } else {
	// return null;
	// }
	// }

	// public Boolean getAttributeNullable(String name) {
	// if (name != null) {
	// return (Boolean) attributesNullable.get(name);
	// } else {
	// return null;
	// }
	// }

	public void removeAttribute(String name) {
		attributesValues.remove(name);
		attributesClasses.remove(name);
		attributesNames.remove(name);
		attributesSQLScale.remove(name);
		attributesSQLTypes.remove(name);
		isModified = true;
		// attributesSQLNames.remove(name);
		// attributesNullable.remove(name);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();

		String name = null;
		buffer.append("# ");
		for (Iterator it = attributesNames.iterator(); it.hasNext();) {
			name = (String) it.next();
			buffer.append("[");
			buffer.append(name);
			buffer.append(",");
			buffer.append(attributesValues.get(name));
			// buffer.append(",");
			// if (attributesClasses.get(name) != null) {
			// buffer.append(attributesClasses.get(name).toString());
			// }
			// buffer.append(",");
			// buffer.append(attributesSQLTypes.get(name));
			// buffer.append(",");
			// buffer.append(attributesSQLScale.get(name));
			// // buffer.append(",");
			// // buffer.append(attributesNullable.get(name));
			// // buffer.append(",");
			// // buffer.append(attributesSQLNames.get(name));
			// buffer.append(",");
			// buffer.append(format);
			buffer.append(",");
			buffer.append(isModified);
			buffer.append("] ");
		}
		buffer.append(" #");
		buffer.append(" fld5 [");
		buffer.append(fld5);
		buffer.append("] fld7 [");
		buffer.append(fld7);
		buffer.append("] fld8 [");
		buffer.append(fld8);
		buffer.append("] axxf [");
		buffer.append(axxf);
		buffer.append("]");

		return buffer.toString();
	}

	public String toXML() {
		String className = getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1,
				className.length()).toUpperCase();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<");
		buffer.append(className);
		buffer.append(">");

		String name = null;
		for (Iterator it = attributesNames.iterator(); it.hasNext();) {
			name = (String) it.next();
			buffer.append("<");
			buffer.append(name);
			buffer.append(" class='");
			buffer.append(attributesClasses.get(name));
			// buffer.append("' nullable='");
			// buffer.append(attributesNullable.get(name));
			// buffer.append("' sqlname='");
			// buffer.append(attributesSQLNames.get(name));
			buffer.append("' sqltype='");
			if (attributesSQLTypes.get(name) != null) {
				buffer.append(attributesSQLTypes.get(name));
			}
			buffer.append("' sqlscale='");
			if (attributesSQLScale.get(name) != null) {
				buffer.append(attributesSQLScale.get(name));
			}
			buffer.append("'>");
			if (attributesValues.get(name) != null) {
				buffer.append(attributesValues.get(name));
			}
			buffer.append("</");
			buffer.append(name);
			buffer.append(">");
		}

		buffer.append(format.toXML());

		buffer.append("</");
		buffer.append(className);
		buffer.append(">");
		return buffer.toString();
	}

	/**
	 * @return Returns the fld5.
	 */
	public ScrOfic getFld5() {
		return fld5;
	}

	/**
	 * @param fld5
	 *            The fld5 to set.
	 */
	public void setFld5(ScrOfic fld5) {
		this.fld5 = fld5;
	}

	/**
	 * @return Returns the fld7.
	 */
	public ScrOrg getFld7() {
		return fld7;
	}

	/**
	 * @param fld7
	 *            The fld7 to set.
	 */
	public void setFld7(ScrOrg fld7) {
		this.fld7 = fld7;
	}

	// ///////////////////////////////////
	/**
	 * @return Returns the locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	// ///////////////////////////////////
	/**
	 * @return Returns the fld8.
	 */
	public ScrOrg getFld8() {
		return fld8;
	}

	/**
	 * @param fld8
	 *            The fld8 to set.
	 */
	public void setFld8(ScrOrg fld8) {
		this.fld8 = fld8;
	}

	/**
	 * @return Returns the axxf.
	 */
	public AxXf getAxxf() {
		return axxf;
	}

	/**
	 * @param axxf
	 *            The axxf to set.
	 */
	public void setAxxf(AxXf axxf) {
		this.axxf = axxf;
	}

	/**
	 * @return Returns the attributesSQLNames.
	 */
	public Map getAttributesSQLNames() {
		return attributesSQLNames;
	}

	// ///////////////////////////TODO
	// bookTypeConf///////////////////////////////////
	/**
	 * @return Returns the literalBookType.
	 */
	public String getLiteralBookType() {
		return literalBookType;
	}

	/**
	 * @param literalBookType
	 *            The literalBookType to set.
	 */
	public void setLiteralBookType(String literalBookType) {
		this.literalBookType = literalBookType;
	}

	// //////////////////////////////////////////////////////////////

	/**
	 * @return the fld5Name
	 */
	public String getFld5Name() {
		return fld5Name;
	}

	/**
	 * @param fld5Name
	 *            the fld5Name to set
	 */
	public void setFld5Name(String fld5Name) {
		this.fld5Name = fld5Name;
	}

	/**
	 * @return the fld7Name
	 */
	public String getFld7Name() {
		return fld7Name;
	}

	/**
	 * @param fld7Name
	 *            the fld7Name to set
	 */
	public void setFld7Name(String fld7Name) {
		this.fld7Name = fld7Name;
	}

	/**
	 * @return the fld8Name
	 */
	public String getFld8Name() {
		return fld8Name;
	}

	/**
	 * @param fld8Name
	 *            the fld8Name to set
	 */
	public void setFld8Name(String fld8Name) {
		this.fld8Name = fld8Name;
	}

}