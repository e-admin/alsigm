package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class EntityApp extends ItemBean
{
    protected ClientContext mContext;

    protected int mAppId;

    protected String mAppName;

    // Página JSP encargada de la presentación del formulario
    private String msURL = null;
    
    // Nombre del formateador para la lista
    private String msFormatter = null;
    
    // XML del formateador
    private String msFormatterXML = null;
    
    // Nombre del formateador para la lista de documentos
    // asociados al registro de la entidad
    private String msEntityDocumentsFormatter = "/ispac/digester/entitydocumentsformatter.xml";
    
    // XML con los parámetros de configuración
    private ParametersDef parameters = null;
    
    // Lista de errores
    private ArrayList mErrorList = new ArrayList();
    
    // Identificador de la entidad principal
    protected int mMainEntityId = 0;
    
    // Identificador de la entidad principal
    protected String mainEntityName = null;
            
	// Numero del expediente
	protected String msExpedient;
	
    protected int urlKey;
    
    protected String path = null;
    
    protected Map labels = new HashMap();
    
    protected Map required = new HashMap();
    
    protected Map fieldDefs = new HashMap();

	public IItem getMainItem() throws ISPACException {
		
		if (getItem() instanceof CompositeItem) {
			
			CompositeItem compositeItem = (CompositeItem) getItem();
			return compositeItem.getItemWithPrefix(getMainEntityName());
		}
		
		return getItem();
	}
	
	public Locale getLocale(){
		return mContext.getLocale();
	}
	
    public abstract void initiate() throws ISPACException;
    public abstract boolean validate() throws ISPACException;
    public abstract void setValue( String key, String value) throws ISPACException;
    public abstract void setValue( String key, String[] value, boolean multivalue) throws ISPACException;
    public abstract String getValue( String key) throws ISPACException;
    public abstract Object[] getMultivalue(String key)throws ISPACException;
	public abstract void setLabels(String locale) throws ISPACException;
	public abstract List getEntityDocuments() throws ISPACException;

    public EntityApp(ClientContext context) {
        this.mContext = context;
    }

    public void setMainEntity(int entityId) {
        this.mMainEntityId = entityId;
    }
    public int getMainEntity() {
        return mMainEntityId;
    }

	public void setMainEntityName(String entityName) {
        this.mainEntityName = entityName;
    }
	public String getMainEntityName() {
		return mainEntityName;
	}


    public int getEntityRegId() throws ISPACException {
        return mitem.getKeyInt();
    }

    public void store() throws ISPACException {
        mitem.store(mContext);
    }
    
    public Map clone(IItem newExpedient, Map entityFields) throws ISPACException {
    	return clone(newExpedient, entityFields, null);
    }

    public Map clone(IItem newExpedient, Map entityFields, Map noCloneSecondaryCtEntityIds) throws ISPACException {
    	return null;
    }

    public void delete()throws ISPACException {
        mitem.delete(mContext);
    }
    public void delete(IClientContext ctx)throws ISPACException {
        mitem.delete(ctx);
    }

    public String getURL() {
    	return msURL;
    }
    public void setURL(String sURL) {
    	msURL = sURL;
    }

    public String getFormatter() {
    	return msFormatter;
    }
    public void setFormatter(String sFormatter) {
    	msFormatter = sFormatter;
    }

    public String getFormatterXML() {
    	return msFormatterXML;
    }
    public void setFormatterXML(String sFormatterXML) {
    	msFormatterXML = sFormatterXML;
    }
    
    public String getEntityDocumentsFormatter() {
		return msEntityDocumentsFormatter;
	}
	public void setEntityDocumentsFormatter(String msEntityDocumentsFormatter) {
		this.msEntityDocumentsFormatter = msEntityDocumentsFormatter;
	}

	public ParametersDef getParameters() {
    	return parameters;
    }

    public void setXmlParameters(String sParameters) throws ISPACException {
    	
    	if (!StringUtils.isEmpty(sParameters)) {
    		//xmlParameters = XMLDocUtil.newDocument(sParameters);
    		parameters = ParametersDef.parseParametersDef(sParameters);
    	}
    }

    /**
     * @return Returns the mAppId.
     */
    public int getAppId() {
        return mAppId;
    }
    /**
     * @param appId The mAppId to set.
     */
    public void setAppId(int appId) {
        mAppId = appId;
    }
    
    /**
     * @return Returns the mAppName.
     */
    public String getAppName() {
        return mAppName;
    }
    /**
     * @param appName The mAppName to set.
     */
    public void setAppName(String appName) {
        mAppName = appName;
    }

    public void processActionParams(Map parameters) throws ISPACException {
    }

    public void setContext(String numexp, int stageId, int taskId) throws ISPACException {
    }

    /*
     * Obtiene los errores encontrados al validar.
     */
    public List getErrors() {
    	return mErrorList;
    }
    public void addError(Object error) {
    	mErrorList.add(error);
    }
	
    public String getLabel(String keyname) throws ISPACException {
    	
        String label = (String) labels.get(keyname);
        
        if (StringUtils.isEmpty(label)) {
        	
        	// Etiquetas en las partes comunes del formulario
        	// que no empiezan con el nombre de la entidad
        	if (!keyname.startsWith(getMainEntityName())) {
        		
        		label = (String) labels.get(getMainEntityName() + ":" + keyname);
        	}
        }
        
        return label;
    }

    public void setLabel(String key, String value) {
        labels.put(key,value);
    }
    
    public boolean isRequired(String property) {
    	return required.containsKey(property);
    }

    public void setRequired(String property) {
    	required.put(property, null);
    }
    
    public EntityField getFieldDef(String property) {
    	return (EntityField) fieldDefs.get(property);
    }
    
    public void setFieldDef(String property, EntityField fieldDef) {
    	fieldDefs.put(property, fieldDef);
    }
    
    public void setFieldDefs(String entityName, EntityDef entityDefinicion) {
    	
    	if (entityDefinicion != null) {
    		
    		List fields = entityDefinicion.getFields();
    		
            for (Iterator iter = fields.iterator(); iter.hasNext();) {
            	
                EntityField fieldDef = (EntityField) iter.next();
                setFieldDef(entityName + ":" + fieldDef.getPhysicalName().toUpperCase(), fieldDef);
            }
    	}
    }
    
	/**
	 * @return Returns the urlKey.
	 */
	public int getUrlKey() {
		return urlKey;
	}
	/**
	 * @param urlKey The urlKey to set.
	 */
	public void setUrlKey(int urlKey) {
		this.urlKey = urlKey;
	}
	
	/**
	 * @param msExpedient The msExpedient to set.
	 */
	public void setExpedient(String msExpedient) {
		this.msExpedient = msExpedient;
	}

	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	public void clearItem() throws ISPACException {
		if (mitem != null)
			mitem.reset();
	}

	public void clearItem(boolean keepBasicFields) throws ISPACException {
		if (mitem != null)
			mitem.reset(keepBasicFields);
	}
	
	public void clearMultivalues() throws ISPACException {
		Property prop = null;
		Iterator iter = mitem.getProperties().iterator();
		while (iter.hasNext()){
			 prop = (Property) iter.next();
			 if (prop.isMultivalue()){
				 mitem.set(prop.getName(), (Object)null);
			 }
		}
	}

		
	
	
	
}