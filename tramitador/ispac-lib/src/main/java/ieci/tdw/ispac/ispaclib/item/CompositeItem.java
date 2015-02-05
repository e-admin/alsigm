package ieci.tdw.ispac.ispaclib.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CompositeItem implements IItem
{
    private Properties mproperties;
	private Map mpropertymap;
	private ArrayList  mitems;
	private ArrayList  mstoreitems;
	private ArrayList  mdeleteitems;
	private String mpropkey;

	public CompositeItem(String keyproperty)
	{
	    mproperties=new Properties();
	    mitems=new ArrayList();
	    mstoreitems=new ArrayList();
	    mdeleteitems=new ArrayList();
	    mpropkey=keyproperty;
	    mpropertymap=null;
	}

	public void clear()
	{
	    mproperties.clear();
	    mitems.clear();
	    mstoreitems.clear();
	    mdeleteitems.clear();
	    if (mpropertymap!=null)
	        mpropertymap.clear();
	}

	public void reset(String keyproperty)
	{
	    clear();
	    mpropkey=keyproperty;
	    mpropertymap=null;
	}

	public void reset()throws ISPACException{
		Property prop = null;
		Iterator iter = mproperties.iterator();
		while (iter.hasNext()){
			 prop = (Property) iter.next();
			 set(prop.getName(), (Object)null);
		}
	}

	public void reset(boolean keepFieldId)throws ISPACException{
		if (!keepFieldId){
			reset();
			return;
		}
		Property prop = null;
		Iterator iter = mproperties.iterator();
		while (iter.hasNext()){
			 prop = (Property) iter.next();
			 if (!StringUtils.equals(prop.getName(),mpropkey) && !isFieldNumExp(prop.getName())){
				 set(prop.getName(), (Object)null);
			 }
		}
	}
	
	private boolean isFieldNumExp (String propertyName){
		String[] split = StringUtils.split(propertyName, ":");
		return StringUtils.equalsIgnoreCase(split[split.length-1], "NUMEXP");
	}
	
	public void addItem(IItem item,String prefix)
	{
	    addItem(item,prefix,true,true);
	}
	
	public void addItem(IItem item,String prefix,boolean storeitem)
	{
		addItem(item,prefix,storeitem,storeitem);
	}
	
	public void addItem(IItem item,String prefix,boolean storeitem,boolean deleteitem)
	{
	    //Se añade el item y se guarda su referencia.
	    mitems.add(item);
	    int nref=mitems.indexOf(item);

	    //Si se requiere guardar el objeto se añade a la lista
	    if (storeitem)
	        mstoreitems.add(item);

	    //Si se requiere eliminar el objeto se añade a la lista
	    if (deleteitem)
	        mdeleteitems.add(item);

	    //Se añaden las propiedades del item con el prefijo adecuado.
	    Properties itemprop=item.getProperties();
	    itemprop.setReference(nref);
	    mproperties.addProperties(itemprop,prefix);

	    //Se regenera el mapa de propiedades.
	    mpropertymap=mproperties.toMap();
	}

	public IItem getItemWithPrefix (String prefix)
	throws ISPACException
	{
		Property prop = null;
		Iterator iter = mproperties.iterator();
		while (iter.hasNext())
		{
			prop = (Property) iter.next();
			if (prop.getName().startsWith(prefix)) {
				return getItem(prop.getName());
			}	
		}
		return null;
	}

	public void removeItem (String prefix)
	throws ISPACException
	{
		// Buscamos las propiedades con prefijo prefix y se guardan
		// en un ArrayList para luego ser eliminadas
		Property prop = null;
		ArrayList list = new ArrayList ();
		Iterator iter = mproperties.iterator();
		while (iter.hasNext())
		{
			prop = (Property) iter.next();
			if (prop.getName().startsWith(prefix))
				list.add(prop);
		}

		IItem item = getItemWithPrefix (prefix);
		if (item == null)
		    return;

		updateReferences(item, prefix);
		mproperties.removeAll(list);
		mitems.remove(item);
		mstoreitems.remove(item);
		mdeleteitems.remove(item);
		mpropertymap=mproperties.toMap();
	}

	private void updateReferences (IItem item, String prefix)
	throws ISPACException
	{
		Property prop = null;
		Iterator iter = item.getProperties().iterator();
		if (iter.hasNext())
			prop = (Property) iter.next();

		int ref = getMapProperty(prefix + prop.getName()).getReference();

		iter = mproperties.iterator();
		while (iter.hasNext())
		{
			prop = (Property) iter.next();
			if (prop.getReference() > ref)
				prop.setReference(prop.getReference()-1);
		}
	}

	private Property getMapProperty(String property) throws ISPACException
	{
		Property prop=(Property)mpropertymap.get(property);
		if (prop==null)
			throw new ISPACException("No existe la propiedad "+property);

		return prop;
	}

	private IItem getItem(String property) throws ISPACException
	{
		Property prop=getMapProperty(property);
		return (IItem)mitems.get(prop.getReference());
	}

	////////////////// IITEM  ////////////////////

	public Object get(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.get(getMapProperty(sProperty).getRawName());
	}

	public Object getKey() throws ISPACException
	{
		return get(mpropkey);
	}

	public Integer getKeyInteger() throws ISPACException
	{
	    return null;
	}

	public Long getKeyLong() throws ISPACException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getKeyInt() throws ISPACException
	{
	    return getKeyInt(mpropkey);
	}
	
	public int getKeyInt(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getKeyInt(getMapProperty(sProperty).getRawName());
	}

	public int getInt(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getInt(getMapProperty(sProperty).getRawName());
	}

	public long getLong(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getLong(getMapProperty(sProperty).getRawName());
	}

	public short getShort(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getShort(getMapProperty(sProperty).getRawName());
	}

	public float getFloat(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getFloat(getMapProperty(sProperty).getRawName());
	}

	public double getDouble(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getDouble(getMapProperty(sProperty).getRawName());
	}

	public BigDecimal getBigDecimal(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getBigDecimal(getMapProperty(sProperty).getRawName());
	}

	public byte getByte(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getByte(getMapProperty(sProperty).getRawName());
	}

	public String getString(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getString(getMapProperty(sProperty).getRawName());
	}

	public Date getDate(String sProperty) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		return item.getDate(getMapProperty(sProperty).getRawName());
	}

	public void set(String sProperty, Object value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void setKey(Object value) throws ISPACException
	{
		set(mpropkey,value);
	}

	public void setKey(int value) throws ISPACException
	{
	    set(mpropkey,value);
	}

	public void set(String sProperty, int value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, long value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, short value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, float value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, double value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, BigDecimal value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, byte value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, String value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void set(String sProperty, Date value) throws ISPACException
	{
	    IItem item=getItem(sProperty);
		item.set(getMapProperty(sProperty).getRawName(),value);
	}

	public void store(IClientContext context) throws ISPACException
	{
	    Iterator it = mstoreitems.iterator();
	    while(it.hasNext())
        {
            IItem item = (IItem) it.next();
            item.store(context);
        }
	}

	public void load(IClientContext context) throws ISPACException
	{
		// TODO Se ignora la carga.
	}

	public void delete(IClientContext context) throws ISPACException
	{
	    Iterator it = mdeleteitems.iterator();
	    while(it.hasNext())
        {
            IItem item = (IItem) it.next();
            item.delete(context);
        }
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
		// TODO Por ahora se ignora la generación del XML.
		return "";
	}

	public String getXmlValues()
	{
	    //TODO Por ahora se ignora la generación del XML.
		return "";
	}

	public Map toMap()
	{
	    //TODO Convertir a un mapa.
		return new HashMap();
	}
}

