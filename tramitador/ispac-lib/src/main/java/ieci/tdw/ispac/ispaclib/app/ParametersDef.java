package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;

public class ParametersDef extends XmlObject {
	
	private final static Logger logger = Logger.getLogger(ParametersDef.class);
	
	private String listOrder = null;
	
	/**
	 * Constructor.
	 *
	 */
	public ParametersDef() {
		super(null, null);
	}
	
	/**
	 *  Lista de entidades para composición (COMPOSITE) y validación (VALIDATION)
	 */
	private List entities = new ArrayList();
	
	/**
	 * Entidad para maestro-detalle (DETAIL)
	 */
	private EntityParameterDef entityDetail = null;
	
	/**
	 * @return Returns the listOrder.
	 */
	public String getListOrder() {
		return listOrder;
	}
	/**
	 * @param listOrder The listOrder to set.
	 */
	public void setListOrder(String listOrder) {
		if (!StringUtils.isEmpty(listOrder)) {
			this.listOrder = listOrder.toUpperCase();
		}
	}
	
	public void addEntity(EntityParameterDef entity, String primaryTable) {
		
		entity.setPrimaryTable(primaryTable);
		addEntity(entity);
	}
	
	public void addEntity(EntityParameterDef entity) {
				
		if (entity.getType().equals(EntityAppConstants.ENTITY_TYPE_DETAILS)) {
			
			setEntityDetail(entity);
		}
		else {
			entities.add(entity);
		}
	}
	
	public void addEntities(List entities) {
		
		if ((entities != null) &&
			(!entities.isEmpty())) {
			
			this.entities.addAll(entities);
		}
	}

	public static ParametersDef parseParametersDef(String xml) {
		
		ParametersDef ret = null;
		
		try {
			URL rulesUrl = ParametersDef.class.getClassLoader().getResource("ieci/tdw/ispac/ispaclib/app/rulesParametersDef.xml");
            
			Digester digester = DigesterLoader.createDigester(rulesUrl);
			ret = (ParametersDef) digester.parse(new StringReader(xml));	
		}
		catch (MalformedURLException e1) {
			logger.error(e1);	
		}
		catch (Exception e2) {
			logger.error(e2);
		}
		
		return ret;
	}
	
	/**
	 * @return Returns the entities.
	 */
	public List getEntities() {
		return entities;
	}
	
	/**
	 * @return Returns the entities.
	 */
	public List getCompositeMultipleRelationEntities() {
		
		List compositeEntities = new ArrayList();
		
		Iterator it = entities.iterator();
		while (it.hasNext()) {
			
			EntityParameterDef entity = (EntityParameterDef) it.next();
			if ((entity.getType().equals(EntityAppConstants.ENTITY_TYPE_COMPOSITE)) ||
				(entity.getType().equals(EntityAppConstants.ENTITY_TYPE_MULTIPLE_RELATION))) {
				
				compositeEntities.add(entity);
			}
		}
		
		return compositeEntities;
	}
	
	/**
	 * @return Returns the entityDetail.
	 */
	public EntityParameterDef getEntityDetail() {
		return entityDetail;
	}
	/**
	 * @param entityDetail The entityDetail to set.
	 */
	public void setEntityDetail(EntityParameterDef entityDetail) {
		this.entityDetail = entityDetail;
	}
	
	/**
	 * Obtiene el contenido XML del objeto.
	 * @return Contenido XML.
	 */
	public String getXmlValues() {
		
		StringBuffer buffer = new StringBuffer();
		
		if (!StringUtils.isEmpty(getListOrder())) {
			
			buffer.append(XmlTag.newTag(EntityAppConstants.NODE_LIST_ORDER, getListOrder()));
		}
		
		if (getEntityDetail() != null) {
			
			buffer.append(toXmlEntityParameterDef(getEntityDetail()));
		}
		
		if (!getEntities().isEmpty()) {
			
			Iterator it = getEntities().iterator();
			while (it.hasNext()) {
				
				EntityParameterDef entity = (EntityParameterDef) it.next();
				buffer.append(toXmlEntityParameterDef(entity));
			}
		}
		
		return buffer.toString();
	}
	
	public static String toXmlEntityParameterDef(EntityParameterDef entity) {
		
		String attributes = XmlTag.newAttr(EntityAppConstants.ATTRIBUTE_TYPE, entity.getType());
		
		if (!StringUtils.isEmpty(entity.getPrimaryTable())) {
			attributes += XmlTag.newAttr(EntityAppConstants.ATTRIBUTE_PRIMARY_TABLE, entity.getPrimaryTable());
		}
		
		if (entity.isReadonly()) {
//			attributes += XmlTag.newAttr(EntityAppConstants.NODE_READONLY, "TRUE");
			attributes += XmlTag.newAttr(EntityAppConstants.ATTRIBUTE_READONLY, "TRUE"); 
		}
		
		if (entity.isNoDelete()) {
			attributes += XmlTag.newAttr(EntityAppConstants.ATTRIBUTE_NO_DELETE, "TRUE");
		}
		
		return XmlTag.newTag(EntityAppConstants.NODE_ENTITY,
					  		 entity.getXmlValues(),
					  		 attributes);
	}
	
	public String toString() {
		
		return XmlTag.newTag(EntityAppConstants.NODE_PARAMETERS, getXmlValues());
	}

}