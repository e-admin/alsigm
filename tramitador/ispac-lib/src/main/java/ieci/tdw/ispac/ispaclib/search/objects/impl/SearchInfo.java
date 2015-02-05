package ieci.tdw.ispac.ispaclib.search.objects.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.search.objects.IEntity;
import ieci.tdw.ispac.ispaclib.search.objects.IPropertyFmt;
import ieci.tdw.ispac.ispaclib.search.objects.ISearchInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.io.InputStream;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class SearchInfo extends GenericItem implements ISearchInfo
{

	private String PROPERTY_DOMAIN = "DOMAIN";
	private String PROPERTY_EXPSTATE = "EXPSTATE";
	private String PROPERTY_DISPLAYWIDTH = "displaywidth";

	/**
	 * Entidades intervinientes en la busqueda
	 */
	private LinkedHashMap mEntities = null;

	/**
	 * Número de entidades q intervienen en la busqueda
	 */
	private int mNumEntities = 0;
	
	/**
	 * PropertyFmts de la búsqueda
	 */
	private LinkedHashMap mPropertyFmts = null;
	
	/**
	 * Numero de PropertyFmt de la búsqueda
	 */
	private int mNumPropertyFmts = 0;
	
	/**
	 * Descripción del formulario
	 */
	private String description = "";
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Constructor
	 * @param expState TODO
	 */
	public SearchInfo (String xml, int domain, int expState)
	throws ISPACException
	{
		Document document = XMLDocUtil.newDocument( xml);

		initiate( document, domain, expState);
	}

	public SearchInfo (InputStream in, int domain, int expState)
	throws ISPACException
	{
		Document document = XMLDocUtil.newDocument( in);

		initiate( document, domain, expState);
	}

	protected void initiate( Document document, int domain, int expState)
	throws ISPACException
	{

		Node queryform = XPathUtil.getNodeList(document, "/queryform").item(0);
		Properties properties = new Properties();
		HashMap values = new HashMap();
		NamedNodeMap attrs = queryform.getAttributes();
		int numAttrs = attrs.getLength();
		for (int i=0; i<numAttrs; i++)
		{
			Node attr = attrs.item(i);
			String name = attr.getNodeName ();
			String value = attr.getNodeValue();
			properties.add(new Property (i, name, Types.VARCHAR));
			values.put(name, value);
		}
		properties.add(new Property(properties.size(), PROPERTY_DOMAIN, Types.VARCHAR));
		values.put(PROPERTY_DOMAIN, Integer.toString(domain));

		properties.add(new Property(properties.size(), PROPERTY_EXPSTATE, Types.VARCHAR));
		values.put(PROPERTY_EXPSTATE, Integer.toString(expState));
		
		
		init(properties, "", values);

		mEntities = new LinkedHashMap ();
		NodeList nodeList = XPathUtil.getNodeList(queryform, "entity");
		for (int i=0; i<nodeList.getLength();i++)
		{
			Entity entity = new Entity (nodeList.item(i));
			mEntities.put(entity.getTable().toUpperCase(), entity);
			mNumEntities++;
			if (StringUtils.equalsIgnoreCase(entity.getType(),"multiple")){
				Entity multipleEntity = new Entity("SPAC_ENTIDADES_RELACION_MULTIPLE");
				mEntities.put("SPAC_ENTIDADES_RELACION_MULTIPLE", multipleEntity);
				mNumEntities++;
			}
		}
		
		mPropertyFmts = new LinkedHashMap ();
		nodeList = XPathUtil.getNodeList(queryform, "propertyfmt");
		for (int i=0; i<nodeList.getLength();i++)
		{
			PropertyFmt propertyFmt = new PropertyFmt (nodeList.item(i));
			mPropertyFmts.put(propertyFmt.getPropertyName(), propertyFmt);
			mNumPropertyFmts++;
		}
	}

	/**
	 * Devuelve el numero de entidades que intervienen en la busqueda
	 * @return numero de entidades
	 */
	public int getNumEntities ()
	{
		return mNumEntities;
	}

	/**
	 * Devuelve la entidad de indice 'i'
	 * @param i indice dentro del conjunto de entidades. Comienza en 0
	 * @return la entidad i-esima
	 */
	public IEntity getEntity (int i)
	throws ISPACException
	{
		if (i>= mNumEntities)
			throw new ISPACException ("Error en SearchInfo::getEntity (int i): No existe la entidad i-esima (i=" + i + ")");
		Collection collection = mEntities.values();
		Object[] array = collection.toArray();
		return (Entity) array[i];
	}

	/**
	 * Devuelve la entidad con un cierto nombre. El nombre corrresponde con el nombre
	 * de la tabla
	 * @param name nombre de la entidad
	 * @return entidad
	 * @throws ISPACException
	 */
	public IEntity getEntity (String name)
	{
		return (Entity) mEntities.get(name.toUpperCase());
	}

	/**
	 * Establece el valor de un campo para una determinada entidad
	 * @param entity nombre de la entidad
	 * @param name nombre del campo
	 * @param value valor de campo
	 * @throws ISPACException
	 */
	public void setFieldValueForEntity (String entity, String name, String value)
	throws ISPACException
	{
		Entity ent = (Entity)mEntities.get(entity.toUpperCase());
		if (ent == null)
			throw new ISPACException ("Error en SearchInfo::setFieldValueForEntity ("
					+ entity + "," + name + "," + value + "): no existe la entidad " + entity);
		ent.setFieldValue(name, value);
	}
	
	
	public void setFieldValueForEntity (String entity, String name, List values)
	throws ISPACException
	{
		Entity ent = (Entity)mEntities.get(entity.toUpperCase());
		if (ent == null)
			throw new ISPACException ("Error en SearchInfo::setFieldValueForEntity ("
					+ entity + "," + name + "," + values + "): no existe la entidad " + entity);
		ent.setFieldValue(name, values);
	}

	/**
	 * Establece el operador de un campo para una determinada entidad.
	 * Esta operador sera usado en la query de busqueda
	 * @param entity nombre de la entidad
	 * @param name nombre del campo
	 * @param operator operador
	 * @throws ISPACException
	 */
	public void setFieldOperatorForEntity (String entity, String name, String operator)
	throws ISPACException
	{
		Entity ent = (Entity)mEntities.get(entity.toUpperCase());
		if (ent == null)
			throw new ISPACException ("Error en SearchInfo::setFieldOperatorForEntity ("
					+ entity + "," + name + "," + operator + "): no existe la entidad " + entity);
		ent.setFieldOperator(name, operator);
	}

	/**
	 * Devuelve el dominio de una busqueda
	 * @return dominio de la busqueda
	 * @throws ISPACException
	 */
	public int getDomain ()
	throws ISPACException
	{
		int iDomain = -1;
		String domain =  getString(PROPERTY_DOMAIN);
		try
		{
			iDomain = Integer.parseInt(domain);
		}
		catch (NumberFormatException e)
		{
			throw new ISPACException ("Error en SearchInfo::getDomain (): " + e);
		}
		return iDomain;
	}

	
	public int getExpState ()
	throws ISPACException
	{
		int iExpState = SearchExpState.ALL;
		String expState =  getString(PROPERTY_EXPSTATE);
		try
		{
			iExpState = Integer.parseInt(expState);
		}
		catch (NumberFormatException e)
		{
			throw new ISPACException ("Error en SearchInfo::getExpState(): " + e);
		}
		return iExpState;
	}
	
	
	/**
	 * Devuelve el displaywidth
	 * @return displaywidth
	 * @throws ISPACException
	 */
	public String getDisplayWidth ()
	throws ISPACException
	{
		return getString (PROPERTY_DISPLAYWIDTH);
	}

	/**
	 * Establece el displaywidth
	 * @param displaywidth
	 * @throws ISPACException
	 */
	public void setDisplayWidth (int displaywidth)
	throws ISPACException
	{
		this.set (PROPERTY_DISPLAYWIDTH, Integer.toString(displaywidth));
	}
	
	/**
	 * Devuelve el numero de propertyfmt de la búsqueda
	 * @return numero de propertyfmt
	 */
	public int getNumPropertyFmts ()
	{
		return mNumPropertyFmts;
	}
	
	/**
	 * Devuelve el propertyfmt de indice 'i'
	 * @param i indice dentro del conjunto de propertyfmt. Comienza en 0
	 * @return la entidad i-esima
	 */
	public IPropertyFmt getPropertyFmt (int i)
	throws ISPACException
	{
		if (i>= mNumPropertyFmts)
			throw new ISPACException ("Error en Entity::getPropertyFmt (int i): No existe el propertyfmt i-esimo (i=" + i + ")");
		Collection collection = mPropertyFmts.values();
		Object[] array = collection.toArray();
		return (PropertyFmt) array[i];
	}
	
	/**
	 * Modifica el propertyfmt 
	 * @param property objeto a actualizar
	 * @throws ISPACException 
	 */
	public void setPropertyFmt(PropertyFmt propertyFmt) throws ISPACException {
		try {
			mPropertyFmts.put(propertyFmt.getPropertyName(), propertyFmt);
		} catch (ISPACException e) {
			throw new ISPACException ("Error en SearchInfo::setPropertyFmt: " + e);
		}
	}

}