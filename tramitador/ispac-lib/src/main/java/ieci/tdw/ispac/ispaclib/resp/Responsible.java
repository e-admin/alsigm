package ieci.tdw.ispac.ispaclib.resp;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.session.persistence.Persistable;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Información de un responsable. 
 */
public abstract class Responsible implements IResponsible, Persistable {

	protected final LinkedHashMap mmembermap;

	public Responsible()
	throws ISPACException
	{
		mmembermap = new LinkedHashMap();

		mmembermap.put(PROPERTY_UID, null);
		mmembermap.put(PROPERTY_NAME, null);
		mmembermap.put(PROPERTY_RESPNAME, null);
		
		mmembermap.put(PROPERTY_GROUP, null);
		mmembermap.put(PROPERTY_USER, null);
		mmembermap.put(PROPERTY_ORGUNIT, null);
	}
	
	public Responsible(String UID, String DN, String RelativeDN, String name)
	throws ISPACException
	{
		mmembermap= new LinkedHashMap();

		mmembermap.put(PROPERTY_UID, UID);
		mmembermap.put(PROPERTY_NAME, name);
		mmembermap.put(PROPERTY_RESPNAME, name);
		
		mmembermap.put(PROPERTY_GROUP, new Boolean(isGroup()));
		mmembermap.put(PROPERTY_USER, new Boolean(isUser()));
		mmembermap.put(PROPERTY_ORGUNIT, new Boolean(isOrgUnit()));
		
	}
	
	public Responsible(String UID, String name)
	throws ISPACException
	{
		mmembermap = new LinkedHashMap();

		mmembermap.put(PROPERTY_UID, UID);
		mmembermap.put(PROPERTY_NAME, name);
		mmembermap.put(PROPERTY_RESPNAME, name);
		
		mmembermap.put(PROPERTY_GROUP, new Boolean(isGroup()));
		mmembermap.put(PROPERTY_USER, new Boolean(isUser()));
		mmembermap.put(PROPERTY_ORGUNIT, new Boolean(isOrgUnit()));
	}
	
	public Responsible(String UID, String name, String respName)
	throws ISPACException
	{
		mmembermap = new LinkedHashMap();

		mmembermap.put(PROPERTY_UID, UID);
		mmembermap.put(PROPERTY_NAME, name);
		mmembermap.put(PROPERTY_RESPNAME, (respName != null ? respName : name));
		
		mmembermap.put(PROPERTY_GROUP, new Boolean(isGroup()));
		mmembermap.put(PROPERTY_USER, new Boolean(isUser()));
		mmembermap.put(PROPERTY_ORGUNIT, new Boolean(isOrgUnit()));
	}

	public Responsible(IDirectoryEntry entry)
	throws ISPACException
	{
		mmembermap = new LinkedHashMap();
		
		setDirectoryEntry(entry);
	}
	
	public void setDirectoryEntry(IDirectoryEntry entry)
	throws ISPACException
	{
		mmembermap.put(PROPERTY_UID, entry.getUID());
		mmembermap.put(PROPERTY_NAME, entry.getName());
		mmembermap.put(PROPERTY_RESPNAME, (entry.getLongName() != null ? entry.getLongName() : entry.getName()));
		
		mmembermap.put(PROPERTY_GROUP, new Boolean(isGroup()));
		mmembermap.put(PROPERTY_USER, new Boolean(isUser()));
		mmembermap.put(PROPERTY_ORGUNIT, new Boolean(isOrgUnit()));
	}

	public String getUID()
	{
		return (String) mmembermap.get(PROPERTY_UID);
	}

	public String getName()
	{
		return (String) mmembermap.get(PROPERTY_NAME);
	}
	
	public String getRespName()
	{
		String respName = (String) mmembermap.get(PROPERTY_RESPNAME);
		if (respName != null) {
			return respName;
		} else {
			return getName();
		}
	}

	public abstract Responsible getOrgUnit() throws ISPACException;
	public abstract Collection getOrgUnits() throws ISPACException;
	public abstract Collection getUsers() throws ISPACException;
	public abstract Collection getUserGroups() throws ISPACException;

	public abstract List getRespList() throws ISPACException;

	public abstract String getRespString() throws ISPACException;
	public abstract boolean isInResponsibleList(String sUID) throws ISPACException;

	 ///////////////////////////
	// Interface IItem

	public abstract boolean isUser ();
	public abstract boolean isGroup ();
	public abstract boolean isOrgUnit ();
	public abstract boolean isDomain ();

	public Object get(String sProperty) throws ISPACException
	{
		if (sProperty.equals(PROPERTY_USER)) {
			return new Boolean(isUser());
		}
		else if (sProperty.equals(PROPERTY_GROUP)) {
			return new Boolean(isGroup());
		}
		else if (sProperty.equals(PROPERTY_ORGUNIT)) {
			return new Boolean(isOrgUnit());
		}
		
		return mmembermap.get(sProperty);
	}

	public Object getKey() throws ISPACException
	{
		return mmembermap.get(PROPERTY_UID);
	}

	public Integer getKeyInteger() throws ISPACException
	{
		return null;
	}

	public Long getKeyLong() throws ISPACException
	{
		return null;
	}

	public int getKeyInt() throws ISPACException
	{
		return 0;
	}
	
	public int getKeyInt(String sProperty) throws ISPACException
	{
		return 0;
	}

	public int getInt(String sProperty) throws ISPACException
	{
		return 0;
	}

	public long getLong(String sProperty) throws ISPACException
	{
		return 0;
	}

	public short getShort(String sProperty) throws ISPACException
	{
		return 0;
	}

	public float getFloat(String sProperty) throws ISPACException
	{
		return 0;
	}

	public double getDouble(String sProperty) throws ISPACException
	{
		return 0;
	}

	public BigDecimal getBigDecimal(String sProperty) throws ISPACException
	{
		return null;
	}

	public byte getByte(String sProperty) throws ISPACException
	{
		return 0;
	}

	public String getString(String sProperty) throws ISPACException
	{
		return (String)get(sProperty);
	}

	public Date getDate(String sProperty) throws ISPACException
	{
		return null;
	}

	public void set(String sProperty, Object value) throws ISPACException
	{
		mmembermap.put(sProperty, value);
	}

	public void setKey(Object value) throws ISPACException
	{
		mmembermap.put(PROPERTY_UID, value);
	}

	public void setKey(int value) throws ISPACException
	{
	}

	public void set(String sProperty, int value) throws ISPACException
	{
	}

	public void set(String sProperty, long value) throws ISPACException
	{
	}

	public void set(String sProperty, short value) throws ISPACException
	{
	}

	public void set(String sProperty, float value) throws ISPACException
	{
	}

	public void set(String sProperty, double value) throws ISPACException
	{
	}

	public void set(String sProperty, BigDecimal value) throws ISPACException
	{
	}

	public void set(String sProperty, byte value) throws ISPACException
	{
	}

	public void set(String sProperty, String value) throws ISPACException
	{
		mmembermap.put(sProperty, value);
	}

	public void set(String sProperty, Date value) throws ISPACException
	{
	}

	public void store(IClientContext context) throws ISPACException
	{
		throw new ISPACException("Operación no permitida");
	}

	public void load(IClientContext context) throws ISPACException
	{
		throw new ISPACException("Operación no permitida");
	}

	public void delete(IClientContext context) throws ISPACException
	{
		throw new ISPACException("Operación no permitida");
	}

	public Properties getProperties()
	{
		Properties properties = new Properties();
		
		properties.add(new Property(1,PROPERTY_UID, Types.VARCHAR));
		properties.add(new Property(2,PROPERTY_NAME, Types.VARCHAR));
		properties.add(new Property(3,PROPERTY_RESPNAME, Types.VARCHAR));
		
		return properties;
	}

	public Property getProperty(String name)
	{
		if (name.compareToIgnoreCase(PROPERTY_UID) == 0)
			return new Property(1,PROPERTY_UID, Types.VARCHAR);
		if (name.compareToIgnoreCase(PROPERTY_NAME) == 0)
			return new Property(2,PROPERTY_NAME, Types.VARCHAR);
		if (name.compareToIgnoreCase(PROPERTY_RESPNAME) == 0)
			return new Property(3,PROPERTY_RESPNAME, Types.VARCHAR);

		if (name.compareToIgnoreCase(PROPERTY_USER) == 0)
			return new Property(4,PROPERTY_USER, Types.BOOLEAN);
		if (name.compareToIgnoreCase(PROPERTY_GROUP) == 0)
			return new Property(5,PROPERTY_GROUP, Types.BOOLEAN);
		if (name.compareToIgnoreCase(PROPERTY_ORGUNIT) == 0)
			return new Property(6,PROPERTY_ORGUNIT, Types.BOOLEAN);

		return null;
	}

	public String getXmlValues()
	{
		String sUID = "<![CDATA[" + getUID() + "]]>";
		String sXml = XmlTag.newTag("value",sUID,1);
		String sName = "<![CDATA[" + getName() + "]]>";
		sXml += XmlTag.newTag("value",sName,2);
		return  sXml;
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

	public Map toMap()
	{
		LinkedHashMap valuemap = new LinkedHashMap();
		valuemap.putAll(mmembermap);
		return valuemap;
	}

	public void reset()throws ISPACException {
		Iterator it=getProperties().iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			set(prop.getName(), (Object)null);
		}	
	}
	
	public void reset(boolean keepFieldId)throws ISPACException {
		if(!keepFieldId){
			reset();
			return;
		}
		Iterator it=getProperties().iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			if (!StringUtils.equals(prop.getName(),PROPERTY_UID)){
				set(prop.getName(), (Object)null);
			}
		}	
	}	
	
	// IResponsible

	//public IResponsible getRespDepartament()
	//{
	//	return getDepartament();
	//}

	public IResponsible getRespOrgUnit() throws ISPACException
	{
		return getOrgUnit();
	}

	public IItemCollection getRespOrgUnits() throws ISPACException
	{
		return new CustomItemCollection (getOrgUnits ());
	}

	public IItemCollection getRespUsers() throws ISPACException
	{
		return new CustomItemCollection (getUsers());
	}

	public IItemCollection getUserRespGroups() throws ISPACException
	{
		return new CustomItemCollection(getUserGroups());
	}

	/**
	 * Obtenemos un ResponsibleComparator
	 */
	public static ResponsibleComparator getRespComparator()
	{	
		return new ResponsibleComparator();
	}
	
}