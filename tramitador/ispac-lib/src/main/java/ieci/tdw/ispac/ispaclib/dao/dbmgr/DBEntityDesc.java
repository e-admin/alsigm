package ieci.tdw.ispac.ispaclib.dao.dbmgr;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBEntityDesc extends DBTableDesc {

	private static final long serialVersionUID = 1L;
	
	EntityDef entityDefinition = null;
	
	public DBEntityDesc(String tablename) {
		super(tablename);
	}

	
	public DBEntityDesc(String tablename, IEntityDef entityDef,String prefix) throws ISPACException
	{
		super(tablename);
		processDefinition(entityDef, prefix);
	}	

	private void processDefinition(IEntityDef entityDef,String prefix) throws ISPACException{
		// Obtener los campos de la definición de la entidad
		setEntityDefinition(EntityDef.parseEntityDef(entityDef.getDefinition()));
		
		DBColumn column = null;
		for (Iterator iter = entityDefinition.getFields().iterator(); iter.hasNext();) {
			EntityField field = (EntityField) iter.next();
			column = createColumn(entityDef.getName(), field, prefix);
			put(column.getName().toUpperCase(),column);
		}
	}
	
	
	
	public DBEntityDesc(String tablename, IEntityDef entityDef) throws ISPACException
	{
		this(tablename, entityDef, (String)null);
	}

	

	
	public DBEntityDesc(String tablename, IEntityDef entityDef, String prefix, Property[] tableColumns)
	throws ISPACException
	{
		super(tablename);
		processDefinition(entityDef,prefix,tableColumns);
	}	
	
	
	
	
	
	private void processDefinition(IEntityDef entityDef, String prefix, Property[] tableColumns) 
	throws ISPACException {
		// Obtener los campos de la definición de la entidad
		setEntityDefinition(EntityDef.parseEntityDef(entityDef.getDefinition()));
		DBColumn column = null;
		for (int i = 0; i < tableColumns.length; i++) {
			Property property = ((Property)tableColumns[i]);
			EntityField field = entityDefinition.getField(property.getName());
			if (field != null){
				column = createColumn(entityDef.getName(), field, prefix);
				put(column.getName().toUpperCase(),column);
			}else{
				column = new DBColumn(entityDef.getName(), i +1, property);
				put( column.getName(), column);
			}
		}
	}


	private DBColumn createColumn(String tablename, EntityField field,String prefix)
	{
	    String name=null;
	    String rawname=null;
	    if (prefix ==null)
	    {
	        name=field.getPhysicalName();
		    rawname=name;
	    }
	    else
	    {
	        name=PrefixBuilder.buildName(prefix,field.getPhysicalName());
	        rawname=PrefixBuilder.buildRawName(prefix,field.getPhysicalName());
	    }
	
		return new DBColumn(tablename,
				field.getId(),
				name,
				rawname,
				field.getType().getJdbcType(),
				field.getSize(),
				field.getPrecision(),
				field.getDescripcion(),
				field.isNullable(),
				field.isMultivalue()
		);
	}

	public EntityDef getEntityDefinition() {
		return entityDefinition;
	}

	public void setEntityDefinition(EntityDef entityDefinition) {
		this.entityDefinition = entityDefinition;
	}

	
	
	public List getMultivalueCols() {
		Iterator it = iterator();
		List list = new ArrayList();
		while (it.hasNext()){
			DBColumn dbColumn = getColumn(it);
			if (dbColumn.isMultivalue()){
				list.add(new String[]{mtablename, dbColumn.getName()});
			}
		}
		return list;		
	}
	
	
}
