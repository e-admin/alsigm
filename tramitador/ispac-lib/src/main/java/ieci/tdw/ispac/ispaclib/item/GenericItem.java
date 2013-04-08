/*
 * Created on 22-sep-2004
 *
 */
package ieci.tdw.ispac.ispaclib.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACPropertyUnknown;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author JUANIN
 *
 */
public class GenericItem implements IItem
{
	protected Properties mproperties;

	//Se crea mpropertymap para poder indexar por nombre de propiedad rápidamente en Properties
	protected Map mpropertymap;
	protected LinkedHashMap mvalues;
	protected String mpropkey;

	public GenericItem()
	{
	}

	public GenericItem(Properties properties,String key)
	{
	    init(properties,key);
	}

	protected void init(Properties properties,String key)
	{
		mproperties=properties;
		mvalues=new LinkedHashMap();
		mpropkey=key;
		mpropertymap=mproperties.toMap();

		Iterator it=mproperties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			mvalues.put(prop.getName(),null);
		}
	}

	protected void init(Properties properties,String key,Map values)
	{
		mproperties=properties;
		mvalues=new LinkedHashMap();
		mpropkey=key;
		mpropertymap=mproperties.toMap();

		Iterator it=mproperties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			mvalues.put(prop.getName(),values.get(prop.getName()));
		}
	}

	private Object getMember(String property) throws ISPACException
	{
		Object value=mvalues.get(property);
		if (value==null)
			throw new ISPACException("El valor requerido es nulo");
		return value;
	}

	private void setMember(String property,Object value) throws ISPACException
	{
		Property prop=(Property)mpropertymap.get(property);
		if (prop==null)
			throw new ISPACPropertyUnknown("No existe la propiedad "+property);

		mvalues.put(property,value);
	}

	///////////////// IItem ///////////////////

	public Object get(String sProperty) throws ISPACException
	{
		return mvalues.get(sProperty);
	}
	public Object getKey() throws ISPACException
	{
		return mvalues.get(mpropkey);
	}

	public Integer getKeyInteger() throws ISPACException
	{
		Number value = (Number)getMember(mpropkey);
		return  new Integer(value.intValue());
	}

	public Long getKeyLong() throws ISPACException
	{
		Number value = (Number)getMember(mpropkey);
		return  new Long(value.longValue());
	}

	public int getKeyInt() throws ISPACException
	{
		Number value = (Number)getMember(mpropkey);
		return value.intValue();
	}
	
	public int getKeyInt(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.intValue();
	}

	public int getInt(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.intValue();
	}

	public long getLong(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.longValue();
	}

	public short getShort(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.shortValue();
	}

	public float getFloat(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.floatValue();
	}

	public double getDouble(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.doubleValue();
	}

	public BigDecimal getBigDecimal(String sProperty) throws ISPACException
	{
		return (BigDecimal) getMember(sProperty);
	}

	public byte getByte(String sProperty) throws ISPACException
	{
		Number value = (Number)getMember(sProperty);
		return value.byteValue();
	}

	public String getString(String sProperty) throws ISPACException
	{
		Property prop=(Property)mpropertymap.get(sProperty);
		if (prop==null)
			throw new ISPACException("No existe la propiedad "+sProperty);
		return TypeConverter.getString(prop.getType(),get(sProperty));
	}

	public Date getDate(String sProperty) throws ISPACException
	{
		return (Date)getMember(sProperty);
	}

	public void set(String sProperty, Object value) throws ISPACException
	{
		setMember(sProperty,value);
	}

	public void setKey(Object value) throws ISPACException
	{
		setMember(mpropkey,value);
	}

	public void setKey(int value) throws ISPACException
	{
		setMember(mpropkey,new Integer(value));
	}

	public void set(String sProperty, int value) throws ISPACException
	{
		setMember(sProperty,new Integer(value));
	}

	public void set(String sProperty, long value) throws ISPACException
	{
		setMember(sProperty,new Long(value));
	}


	public void set(String sProperty, short value) throws ISPACException
	{
		setMember(sProperty,new Short(value));
	}


	public void set(String sProperty, float value) throws ISPACException
	{
		setMember(sProperty,new Float(value));
	}

	public void set(String sProperty, double value) throws ISPACException
	{
		setMember(sProperty,new Double(value));
	}

	public void set(String sProperty, BigDecimal value) throws ISPACException
	{
		setMember(sProperty,value);
	}

	public void set(String sProperty, byte value) throws ISPACException
	{
		setMember(sProperty,new Byte(value));
	}


	public void set(String sProperty, String value) throws ISPACException
	{
		setMember(sProperty,value);
	}

	public void set(String sProperty, Date value) throws ISPACException
	{
		setMember(sProperty,value);
	}


	public void store(IClientContext context) throws ISPACException
	{

	}


	public void load(IClientContext context) throws ISPACException
	{

	}


	public void delete(IClientContext context) throws ISPACException
	{

	}


	public Properties getProperties()
	{
		return mproperties;
	}


	public Property getProperty(String name)
	{
		return (Property)mpropertymap.get(name);
	}


	public String toXml()
	{
		String sXml = null;

		// Propiedades del objeto
		String sProperties = getProperties().toXML();
		// Valores del objeto
		String sValues = XmlTag.newTag("values", getXmlValues());
		sXml = XmlTag.getXmlInstruction("ISO-8859-1")
				 + XmlTag.newTag("item", sProperties + sValues);

		return sXml;
	}


	public String getXmlValues()
	{
		StringBuffer sXml = new StringBuffer();

		int i=0;
		Iterator it = mproperties.iterator();
		while (it.hasNext())
		{
			Property prop=(Property)it.next();
			Object value=mvalues.get(prop.getName());
			String svalue=TypeConverter.getString(prop.getType(),value);

			sXml.append(XmlTag.newTag("value", svalue,i++));
		}

		return  sXml.toString();
	}


	public Map toMap()
	{
		return (Map)mvalues.clone();
	}

	public void reset() throws ISPACException {
		Iterator it=mproperties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			mvalues.put(prop.getName(),null);
		}		
	}

	public void reset(boolean keepFieldId) throws ISPACException {
		if (!keepFieldId){
			reset();
			return;
		}
		Iterator it=mproperties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
		    if (!StringUtils.equals(prop.getName(),mpropkey) && !isFieldNumExp(prop.getName()) && !isFieldId(prop.getName()) ){
				mvalues.put(prop.getName(),null);
		    }
		}		
	}

	private boolean isFieldNumExp (String propertyName){
		String[] split = StringUtils.split(propertyName, ":");
		return StringUtils.equals(split[split.length-1], "NUMEXP");
	}
	
	private boolean isFieldId (String propertyName){
		String[] split = StringUtils.split(propertyName, ":");
		return StringUtils.equalsIgnoreCase(split[split.length-1], "ID");
	}	
	
	
}
