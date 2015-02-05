package ieci.tdw.ispac.ispaccatalog.action.form;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.app.MasterDetailEntityApp;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinDAO;
import ieci.tdw.ispac.ispaclib.entity.def.EntityIndex;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.util.PathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EntityForm extends ValidatorEntityAppForm
{
	// Propiedades del formulario
	private HashMap property = new HashMap();
	// Valores extra del formulario
	private Map valuesExtra = new HashMap();
	// Lista de ItemBean relacionados
	private List items = new ArrayList();
	// Lista de ItemBean relacionados
	private BeanFormatter formatter = null;

	private String actions;
	private String entity; 		// Identificador de la entidad en el catálogo de entidades
	private String key; 		// Identificador del registro
	private String readonly; 	// Sólo lectura
	private String filter; 		// Filtro de búsqueda
	private String[] multibox;

	private EntityApp mentityapp;

	public EntityForm()
	{
	    mentityapp=null;
		actions=null;
		entity=null;
		key=null;
		readonly=null;
		multibox=null;
	}
	
	public void setProperty(String key, String value)
	{
		if (value == null)
			property.put(key, "");
		else
			property.put(key, value);
	}
  
	
	public String getProperty(String key)
	{
		if (!property.containsKey(key))
			return "";
		return property.get(key).toString();
	}

	public String getValueExtra(String key) 
	{
		if (!valuesExtra.containsKey(key))
			return "";
		return valuesExtra.get(key).toString();
	}
	
	public void setActions(String newString)
	{
		this.actions = newString;
	}

	public String getActions()
	{
		return this.actions;
	}

	public String getEntity()
	{
		return this.entity;
	}

	public void setEntity(String entity)
	{
	    this.entity=entity;
	}

	public String getKey()
	{
		return this.key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getReadonly()
	{
		return this.readonly;
	}

	public void setReadonly(String readonly)
	{
		this.readonly = readonly;
	}

	public String getFilter()
	{
		return this.filter;
	}

	public void setFilter(String filter)
	{
		this.filter = filter;
	}

	public List getItems()
	{
		return this.items;
	}

	public void setItems(List items)
	{
		this.items = items;
	}

	public BeanFormatter getFormatter()
	{
		return this.formatter;
	}

	public void setFormatter(BeanFormatter formatter)
	{
		this.formatter = formatter;
	}

	// Asigna los valores de las propiedades de la entidad
	// al formulario.
	public void setEntityApp(EntityApp entity)
	throws ISPACException
	{
	    if (entity==null)
	        return;

	    mentityapp=entity;
	    setEntityAppName(entity.getAppName());

		// Salva el identificador de la entidad
		setEntity(Integer.toString(entity.getMainEntity()));
		// Salva el identificador del registro si el item
		// no es un join.
		if (!(entity.getItem() instanceof TableJoinDAO))
		{
			setKey(Integer.toString(entity.getEntityRegId()));
		}

		Properties properties = entity.getProperties();

		Iterator iterator = properties.iterator();

		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			setProperty(property.getName(), entity.getValue(property.getName()));
			//setProperty( property.getName(),entity.getProperty( property.getName()), property.getType());
		}
		
		// Salvar los valores extra
		valuesExtra.putAll(entity.getValuesExtra());

		// Salva en la propiedad items los objetos ItemBean relacionados
		if (entity instanceof MasterDetailEntityApp)
		{
			setItems(((MasterDetailEntityApp) entity).getItemBeanList());
		}

		// Salva en la propiedad formatter el nombre del formateador
		String sFormatter = entity.getFormatter();
		if (sFormatter != null && sFormatter.trim().length() != 0)
		{
			// Path donde se localiza la aplicación
			String sRealPath = PathUtils.getRealPath(servlet, "/");
			CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			if (sFormatter.startsWith("/"))
			    sFormatter=sFormatter.substring(1);
			setFormatter( factory.getFormatter(sRealPath+sFormatter));
		}
	}

	public EntityApp getEntityApp()
	{
	    return mentityapp;
	}

	// Asigna los valores de las propiedades del formulario
	// a las propiedades de la entidad.
	public void processEntityApp(EntityApp entity)
	throws ISPACException
	{

		Iterator iterator = property.entrySet().iterator();

		while (iterator.hasNext())
		{
			Entry entry = (Entry) iterator.next();
			entity.setValue( (String) entry.getKey(),entry.getValue().toString());
		}
	}

	public void setItemBean(ItemBean bean) throws ISPACException {
		
	    if (bean != null) {

			Properties properties = bean.getProperties();
			Iterator iterator = properties.iterator();
			Property property;
			Object value;
			
			while (iterator.hasNext()) {
				property = (Property) iterator.next();
				value = bean.getProperty(property.getName());
				setProperty( property.getName(), 
						(value != null ? value.toString() : null) );
			}
	    }
	}

	public void processItemBean(ItemBean bean) throws ISPACException {

		if (bean != null) {
			Iterator iterator = property.entrySet().iterator();
			Entry entry;
			
			while (iterator.hasNext()) {
				entry = (Entry) iterator.next();
				bean.setProperty((String) entry.getKey(), (String) entry.getValue());
			}
		}
	}

    public String[] getMultibox() {
        return multibox;
    }

    public void setMultibox(String[] newMultibox) {
    	multibox = newMultibox;
    }  
    
    public EntityIndex composeEntityIndex(int id, List fieldIds){
        EntityIndex index = new EntityIndex();
        index.setId(id);
        index.setName(this.getProperty("NAME"));
        index.setPrimaryKey(TypeConverter.toBoolean(this.getProperty("PRIMARYKEY")));
        index.setUnique(TypeConverter.toBoolean(this.getProperty("UNIQUE")));
        index.setDocumental(TypeConverter.toBoolean(this.getProperty("DOCUMENTAL")));

        index.setFieldIds(fieldIds);
        return index;
        
    }

    public void fillForm(EntityIndex index){
        this.setProperty("NAME", index.getName());
        this.setProperty("PRIMARYKEY", TypeConverter.toString(index.isPrimaryKey()));
        this.setProperty("UNIQUE", TypeConverter.toString(index.isUnique()));
        this.setProperty("DOCUMENTAL", TypeConverter.toString(index.isDocumental()));
    }
    
    // Asigna los valores extra del formulario
    // al establecer la aplicación en el formulario
	public void setValuesExtra(EntityApp entity) throws ISPACException 	{
	    
		if (entity != null) {
			mentityapp = entity;
		}
	}
}
