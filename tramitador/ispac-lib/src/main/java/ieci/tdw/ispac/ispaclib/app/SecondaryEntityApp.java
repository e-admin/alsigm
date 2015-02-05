package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.entity.def.EntityValidation;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class SecondaryEntityApp extends SimpleEntityApp {
	
	// Información de la entidad principal
	protected String mPrefixMainEntity;	
	protected String mKeyMainEntity;
	protected IEntityDef mainEntityDef;
	
	protected int mSecondaryEntityId;
	protected int mPcdId = ISPACEntities.ENTITY_NULLREGKEYID;
	
	protected Map joinListSecondaryEntities = new HashMap();
	protected String joinSecondaryEntity;
	
	// Lista de ItemBean de documentos asociados al registro de la entidad principal
	protected List mEntityDocumentsItemBeanList = null;
	
	public SecondaryEntityApp(ClientContext context) {
		
		super(context);
		mEntityDocumentsItemBeanList = new ArrayList();
	}
	
	public List getEntityDocuments() throws ISPACException	{
		return mEntityDocumentsItemBeanList;
	}
	
	private List getEntityDocumentsItemBeanList(int entityId,
								   	  			int entityRegId) throws ISPACException {
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		
		IItemCollection collection = entitiesAPI.getEntityDocuments(entityId, entityRegId);
   	 	List list = CollectionBean.getBeanList(collection);
   	 	
   	 	return list;
	}

	public void setItem(IItem item)	throws ISPACException {
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();

	    if (!(item instanceof EntityDAO)) {
	    	throw new ISPACException("El objeto item no es una instancia de EntityDAO.");
	    }
	    EntityDAO mainEntityDAO = (EntityDAO) item;
	    
	    // Tabla y clave de la entidad principal
		mPrefixMainEntity = mainEntityDAO.getTableName();
		mKeyMainEntity = mainEntityDAO.getKeyName();
		
		// TODO Incorporar al EntityDAO campo NUMEXP y DEFINICION de la entidad para no tener que consultar la definición que ya se consulta para crear el EntityDAO
		// Obtener la definición de la entidad principal
		mainEntityDef = (IEntityDef) entitiesAPI.getCatalogEntity(mMainEntityId);
		
		// Crear un CompositeItem al que se le añadiran las entidades secundarias de
		// de sustituto, composición y relación múltiple cuando existan en los parámetros del formulario
		CompositeItem composite = new CompositeItem(mPrefixMainEntity + ":" + mKeyMainEntity);
		composite.addItem(item, mPrefixMainEntity + ":");
		this.mitem = composite;
		
		// El número de expediente se establece inicialmente por parámetro en el EntityAppFactory
		// sino se busca en el item de la entidad principal a partir de su definición
		if (StringUtils.isEmpty(msExpedient)) {
			msExpedient = item.getString(mainEntityDef.getKeyNumExp());
		}
		
		/*
		// El identificador del procedimiento se establece inicialmente por parámetro en el EntityAppFactory
		// sino se busca en el item del expediente
		if (mPcdId == ISPACEntities.ENTITY_NULLREGKEYID) {
			mPcdId = entitiesAPI.getExpedient(msExpedient).getInt("ID_PCD");
		}
		*/
		
		// Obtener los campos obligatorios para la entidad principal
		EntityDef entityDefinicion = getRequiredData(mainEntityDef);
		
		// Obtener la definición de los campos para la entidad principal
		setFieldDefs(mainEntityDef.getName(), entityDefinicion);
	}
	
	/**
	 * Establecer las etiquetas de los campos en el idioma establecido.
	 * 
	 * @param language Idioma.
	 * @throws ISPACException Si se produce algún error.
	 */
	public void setLabels(String language) throws ISPACException {
		
		// Obtener los recursos para la entidad principal
		getEntityResources(mMainEntityId, mPrefixMainEntity, language);
	}

	public void initiate() throws ISPACException {
		
		super.initiate();
		
		// Entidades secundarias asociadas a la entidad principal
		getSecondariesEntities();
		
		// Documentos asociados al registro de la entidad
		reloadEntityDocumentsItemBeanList();
	}

	protected void reloadEntityDocumentsItemBeanList() throws ISPACException {
		mEntityDocumentsItemBeanList = getEntityDocumentsItemBeanList(mMainEntityId, mitem.getKeyInt());
	}

	/**
	 * Obtener registros y recursos de las entidades secundarias
	 * declaradas en los parámetros del formulario.
	 */
	protected abstract void getSecondariesEntities() throws ISPACException;
	
	/**
	 * Obtener los datos de la definición de parámetro de entidad de relación múltiple.
	 * 
	 * @param entity Definición de parámetro de entidad.
	 * @param storeItem Indicador de si el item se guarda o no.
	 * @return definición de la entidad de relación múltiple.
	 * 
	 * @throws ISPACException Si se produce algún error.
	 */
	protected abstract IEntityDef getEntityMultipleRelation(EntityParameterDef entity, boolean storeItem) throws ISPACException;
	
	/**
	 * Obtener los recursos de una entidad,
	 * las etiquetas de los campos en el idioma establecido.
	 * 
	 * @param entityId Identificador de la entidad.
	 * @param entityName Nombre de la entidad.
	 * @param language Idioma.
	 * @throws ISPACException Si se produce algún error.
	 */
	public void getEntityResources(int entityId, String entityName, String language) throws ISPACException {
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		
		// Obtener todos los recursos de la entidad para el idioma establecido
		IItemCollection collection = entitiesAPI.getEntityResources(entityId, language);
    	while (collection.next()) {

    		// Establecer las etiquetas
    		IItem item = collection.value();
    		setLabel(entityName + ":" + item.getString("CLAVE"), item.getString("VALOR"));
    	}
	}
	
	/**
	 * Obtener los recursos de una entidad,
	 * las etiquetas de los campos en el idioma establecido.
	 * 
	 * @param entityName Nombre de la entidad.
	 * @param language Idioma.
	 * @throws ISPACException Si se produce algún error.
	 */
	public void getEntityResources(String entityName, String language) throws ISPACException {
		
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		
		// Obtener todos los recursos de la entidad para el idioma establecido
		IEntityDef entityDef = (IEntityDef) entitiesAPI.getCatalogEntity(entityName);
		getEntityResources(entityDef.getId(), entityName, language);
	}
	
	/**
	 * Establece los campos requeridos para una entidad
	 * a partir de las validaciones de obligatoriedad de su definición.
	 * 
	 * @param entityDef Definición de la entidad.
	 * @return La definición parseada de la entidad.
	 * @throws ISPACException Si se produce algún error.
	 */
	public EntityDef getRequiredData(IEntityDef entityDef) throws ISPACException {
		
		// Obtener la definición de la entidad
		String xmlDefinicion = entityDef.getDefinition();
		if (!StringUtils.isEmpty(xmlDefinicion)) {
			
			// Parsear la definición de la entidad
			EntityDef entityDefinicion = EntityDef.parseEntityDef(xmlDefinicion);
			if (entityDefinicion != null) {
			
				// Obtener las validaciones
				List validations = entityDefinicion.getValidations();
				if ((validations != null) &&
					(!validations.isEmpty())) {
					
					Map fields = entityDefinicion.fieldsToMap();
					Iterator it = validations.iterator();
					while (it.hasNext()) {
						
						// Validación de campo obligatorio
						EntityValidation entityValidation = (EntityValidation) it.next();
						if (entityValidation.isMandatory()) {
							
							// Establecer el campo como requerido
							EntityField field = (EntityField) fields.get(new Integer(entityValidation.getFieldId()));
							if (field != null) {
								
								setRequired(entityDef.getName() + ":" + field.getPhysicalName().toUpperCase());
							}
						}
					}
				}
			}
			
			return entityDefinicion;
		}
		
		return null;
	}

	public int getSecondaryEntityId() {
		return mSecondaryEntityId;
	}

	public void setSecondaryEntityId(int secondaryEntityId) {
		mSecondaryEntityId = secondaryEntityId;
	}

	/*
	public int getPcdId() {
		return mPcdId;
	}

	public void setPcdId(int pcdId) {
		mPcdId = pcdId;
	}
	*/
	
}