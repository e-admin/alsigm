/*
 * Created on 28-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class CollectionBean
{

	Class mClassBean;
	IItemCollection mItemlist;
	Object[] marguments;
	Class[] margumentTypes;

	/**
	 *
	 */

	public CollectionBean(IItemCollection itemlist) throws ISPACException
	{
		this(null, itemlist,null);
	}

	public CollectionBean(Class cls, IItemCollection itemlist)
			throws ISPACException
	{
	    this(cls, itemlist,null);
	}

	public CollectionBean(Class cls, IItemCollection itemlist,Object[] args)
	throws ISPACException
	{
		super();
		mItemlist = itemlist;
		mClassBean = cls;

		if (mClassBean == null)
			mClassBean = ItemBean.class;

		if (!ItemBean.class.isAssignableFrom(mClassBean))
			throw new ISPACException("La clase "
					+ mClassBean.toString()
					+ " no extiende la clase ObjectBean");

		marguments=args;
		margumentTypes=null;

		if (marguments==null)
		    return;

		margumentTypes=new Class[marguments.length];
		for (int i = 0; i < marguments.length; i++)
			margumentTypes[i] = marguments[i].getClass();
	}

	protected ItemBean newItemBean(IItem item) throws ISPACException
	{
		try
		{
		    //Por defecto se utiliza el constructor sin parámetros.
			if (marguments==null)
			{
			    ItemBean obj = (ItemBean) mClassBean.newInstance();
				obj.setItem(item);
				return obj;
			}

			Constructor newbean = mClassBean.getConstructor(margumentTypes);
			ItemBean obj=(ItemBean)newbean.newInstance(marguments);
			obj.setItem(item);
			return obj;
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
	}

	public void reset() throws ISPACException
	{
		mItemlist.reset();
	}


	public boolean next() throws ISPACException
	{
		if (mItemlist == null)
			throw new ISPACException(
					"No se ha especificado una búsqueda para la colección");

		return mItemlist.next();
	}

	public ItemBean value() throws ISPACException
	{
		return newItemBean(mItemlist.value());
	}

	/**
	 * método que devuelve una lista de ObjectBean
	 *
	 * @return lista de ObjectBean
	 */
	public List toList() throws ISPACException
	{
		List list = new ArrayList();
		try
		{
			reset();
			while (next())
			{
				list.add(value());
			}
		}
		catch (ISPACException ie)
		{
			throw new ISPACException(ie);
		}
		return list;
	}

	public static List getBeanList(IItemCollection itc)throws ISPACException
	{
	    return getBeanList(null,itc);
	}

	public static List getBeanList(Class beanclass,IItemCollection itc)throws ISPACException
	{
	    CollectionBean cbean = new CollectionBean(beanclass,itc);
	    return cbean.toList();
	}

	public static List getBeanList(Class beanclass,IItemCollection itc,Object[] args)throws ISPACException
	{
	    CollectionBean cbean = new CollectionBean(beanclass,itc,args);
	    return cbean.toList();
	}


	public static List getActionList(IItemCollection itc, String sAction)
	throws ISPACException
	{
		CollectionBean cbean = new CollectionBean(ActionBean.class,itc);
		return cbean.toActionList(sAction);
	}

	protected List toActionList(String sAction)
	throws ISPACException
	{
		List list = new ArrayList();
		try
		{
			reset();
			while (next())
			{
				ActionBean bean = (ActionBean) value();
				bean.setProperty(ActionBean.ACTION, sAction);
				list.add(bean);
			}
		}
		catch (ISPACException ie)
		{
			throw new ISPACException(ie);
		}
		return list;
	}

	public static Map getItemBeanListMap(Map itemcollectionmap)throws ISPACException
	{
	    return getItemBeanListMap(null,itemcollectionmap);
	}

	public static Map getItemBeanListMap(Class beanclass,Map itemcollectionmap)throws ISPACException
	{
	    Map beanlistmap=new LinkedHashMap();
		Iterator it=itemcollectionmap.entrySet().iterator();

		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			IItemCollection itemcol=(IItemCollection)entry.getValue();
			beanlistmap.put(entry.getKey(),getBeanList(beanclass,itemcol));
		}
	    return beanlistmap;
	}

    public Map toMap(String propertykey) throws ISPACException
    {
        HashMap map = new HashMap();
        try
        {
            mItemlist.reset();
            while (next())
            {
                ItemBean bean = value();
                map.put(bean.getString(propertykey), bean);
            }

        }
        catch (ISPACException ie)
        {
            throw new ISPACException(ie);
        }
        return map;
    }

    public Map toMap() throws ISPACException
    {
        HashMap map = new HashMap();
        try
        {
            mItemlist.reset();
            while (next())
            {
                ItemBean bean = value();
                map.put(bean.getKeyProperty().toString(), bean);
            }
        }
        catch (ISPACException ie)
        {
            throw new ISPACException(ie);
        }
        return map;
    }

	public static Map getBeanMap(IItemCollection itc)throws ISPACException
	{
	    return getBeanMap(null,itc);
	}

	public static Map getBeanMap(Class beanclass,IItemCollection itc)throws ISPACException
	{
	    CollectionBean cbean = new CollectionBean(beanclass,itc);
	    return cbean.toMap();
	}
	public static Map getBeanMap(Class beanclass,IItemCollection itc,Object[] args)throws ISPACException
	{
	    CollectionBean cbean = new CollectionBean(beanclass,itc,args);
	    return cbean.toMap();
	}

	public static Map getBeanMap(IItemCollection itc,String propkey)throws ISPACException
	{
	    return getBeanMap(null,itc,propkey);
	}

	public static Map getBeanMap(Class beanclass,IItemCollection itc,String propkey)throws ISPACException
	{
	    CollectionBean cbean = new CollectionBean(beanclass,itc);
	    return cbean.toMap(propkey);
	}

	public static Map getBeanMap(Class beanclass,IItemCollection itc,String propkey,Object[] args)throws ISPACException
	{
	    CollectionBean cbean = new CollectionBean(beanclass,itc,args);
	    return cbean.toMap(propkey);
	}

	public static TreeItemBean getBeanTree(IItemCollection itemcol) throws ISPACException {
	    return getBeanTree(itemcol, "ID", "ID_PADRE");
	}

 	public static TreeItemBean getBeanTree(IItemCollection itemcol, String id, String idpadre) throws ISPACException {
 		TreeItemBean root = new TreeItemBean();

 		List ctproclist = CollectionBean.getBeanList(itemcol);
 		Map mapa = new HashMap();
 		Iterator it = ctproclist.iterator();
 		while(it.hasNext()){
 			ItemBean item = (ItemBean)it.next();
 			ArrayList listaHijos = (ArrayList)mapa.get(item.getItem().get(idpadre).toString());
 			if( listaHijos == null){
 				listaHijos = new ArrayList();
 				listaHijos.add(new TreeItemBean(item.getItem()));
 				mapa.put(item.getItem().get(idpadre).toString(), listaHijos);
 			}else{
 				listaHijos.add(new TreeItemBean(item.getItem()));
 			}
 		}
 		generateTree(mapa,root,id);

 		return root;
 	}

 	private static void generateTree(Map mapa, TreeItemBean treeBean, String id) throws ISPACException {
 		String miId;
 		if(treeBean.getItem() == null){
 			miId = "-1";
 		}else{
 			miId = String.valueOf(treeBean.getItem().get(id));
 		}

 		ArrayList hijos = (ArrayList)mapa.get(miId);
 		if(hijos!=null){
 	 		treeBean.setChildren(hijos);
 	 		Iterator it = treeBean.getChildren().iterator();
 	 		while(it.hasNext()){
 	 			generateTree(mapa,(TreeItemBean)it.next(),id);
 	 		}
 		}
 	}
}
