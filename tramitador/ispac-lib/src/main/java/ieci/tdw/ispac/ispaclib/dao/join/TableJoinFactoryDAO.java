/*
 * Created on 25-feb-2005
 *
 */
package ieci.tdw.ispac.ispaclib.dao.join;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableDesc;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableJoinDesc;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableMgrFactory;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;
import ieci.tdw.ispac.ispaclib.worklist.WLObjectDef;
import ieci.tdw.ispac.ispaclib.worklist.WLSubstituteDef;
import ieci.tdw.ispac.ispaclib.worklist.WLTableJoinDef;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author juanin
 *
 */
public class TableJoinFactoryDAO
{
    private LinkedHashMap mtablemap;
    private LinkedHashMap mpropertymap;
    private LinkedHashMap mtablejoinedmap;

    public TableJoinFactoryDAO()
    {
        mtablemap = new LinkedHashMap();
        mpropertymap = new LinkedHashMap();
        mtablejoinedmap = new LinkedHashMap();
    }

    public void addEntity(DbCnt cnt,int nIdEntity,String prefix) throws ISPACException
    {
        CTEntityDAO catentity=EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt,nIdEntity);
        String tblname=catentity.getString("NOMBRE");
        mtablemap.put(prefix,tblname);
    }

    public void addEntity(IEntityDef catentity,String prefix) throws ISPACException
    {
        String tblname=catentity.getName();
        mtablemap.put(prefix,tblname);
    }

    public void addTableJoined(WLSubstituteDef sustituteDef, String prefix, Property[] properties){

    	mtablejoinedmap.put(prefix, sustituteDef);
    	mpropertymap.put(prefix,properties);
    }

    // Tabla agregada a la consulta con LEFT OUTER JOIN
    public void addTableJoined(WLTableJoinDef tableJoinDef, String tablename,String prefix){

    	mtablejoinedmap.put(prefix, tableJoinDef);
    }

    public void addTable(String tablename,String prefix) throws ISPACException
    {
        mtablemap.put(prefix,tablename);
    }

    public void addTable(String tablename,String prefix,Property[] properties)
    throws ISPACException
    {
        mtablemap.put(prefix,tablename);
        mpropertymap.put(prefix,properties);
    }

    public CollectionDAO newCollectionDAO(DbCnt cnt)
    throws ISPACException
    {
        DBTableJoinDesc tablejoindesc=createTableJoin(cnt);

        Object[] args=new Object[1];
        args[0]=tablejoindesc;

        return new CollectionDAO(TableJoinDAO.class,args);
    }

    public CollectionDAO newCollectionDAO(DbCnt cnt, String keyProperty)
    throws ISPACException
    {
        DBTableJoinDesc tablejoindesc=createTableJoin(cnt);

        Object[] args=new Object[2];
        args[0]=tablejoindesc;
        args[1]=keyProperty;

        return new CollectionDAO(TableJoinDAO.class,args);
    }

    private DBTableJoinDesc createTableJoin(DbCnt cnt) throws ISPACException
    {
        StringBuffer jointablename=new StringBuffer();
        ArrayList tabledesclist=new ArrayList();
        HashMap tablasEnOuter = new HashMap();
        DBTableMgrFactory tblmgrfactory=DBTableMgrFactory.getInstance();

        // Tablas que se relacionarán mediante LEFT OUTER JOIN
        // Recorrer para agrupar las de sustituto de la entidad principal
        // o cada entidad agregada junto con sus respectivas de sustituto
        Iterator ittablesjoined=mtablejoinedmap.entrySet().iterator();
        HashMap map = new HashMap();
        while (ittablesjoined.hasNext())
        {
            String name = null;
            ArrayList joinList = null;

            Map.Entry entry=(Map.Entry)ittablesjoined.next();
            boolean isSustituto = entry.getValue() instanceof WLSubstituteDef;

            // Tabla de sustituto relacionada con la entidad principal o con una agregada
            if (isSustituto) {
            	WLSubstituteDef sustituteDef = (WLSubstituteDef)entry.getValue();
            	// Entidad en la que se guarda el valor del sustituto
            	name = sustituteDef.getName();

            	joinList = (ArrayList)map.get(name);
                if (joinList == null){
                	joinList = new ArrayList();
                }

                joinList.add(sustituteDef);
            }
            // Tabla de entidad agregada a la principal
            else if (entry.getValue() instanceof WLTableJoinDef){
            	WLTableJoinDef tablejoinDef = (WLTableJoinDef)entry.getValue();
            	// Entidad
            	name = tablejoinDef.getTable().getTableName();

                joinList = (ArrayList)map.get(name);
                if (joinList == null){
                	joinList = new ArrayList();
                }

                // La tabla de entidad agregada al principio de la lista
                // para que sea luego la primera en aparecer en el LEFT OUTER JOIN
                // y que sus respectivas tablas de sustituto se relacionen a continuación
                joinList.add(0, tablejoinDef);
            }

            map.put(name, joinList);
        }

        // Recorrer las tablas para generar el LEFT OUTER JOIN
        // a partir del Map anterior que agrupa entidades con sus relaciones de sutituto
        for (Iterator itNames = map.keySet().iterator(); itNames.hasNext();) {

        	// Entidad principal o agregada
			String name = (String) itNames.next();
			// Tablas relacionadas con la entidad a incluir en el LEFT OUTER JOIN
			ArrayList listJoinings = (ArrayList)map.get(name);

			StringBuffer leftOuter = new StringBuffer();
			String prefix = null;

			// Recorrer las tablas relacionadas
			for (Iterator itJoinings = listJoinings.iterator(); itJoinings.hasNext();) {

				String leftTable = null;
				String rightTable = null;

				WLObjectDef objDef = (WLObjectDef)itJoinings.next();

				 // Tabla de sustituto relacionada con la entidad
				if (objDef instanceof WLSubstituteDef) {

					WLSubstituteDef sustituteDef = (WLSubstituteDef)objDef;
					leftTable = sustituteDef.getName();

					// Nombre de la tabla de sustituto
					// de la que más adelante se obtendrá su descripción (tblmgrfactory.getTableDesc...)
					name = sustituteDef.getCodeTable().getTable();
					// Prefijo para las columnas de la entidad
					prefix = PrefixBuilder.buildName(sustituteDef.getCodeTable().getName(), leftTable);

					// Generar el LEFT OUTER JOIN
					if (leftOuter.length()>0) leftOuter.append(" ");
					if (tablasEnOuter.get(leftTable) == null) {
						leftOuter.append(mtablemap.get(leftTable)).append(" ").append(leftTable);
					}
					leftOuter.append(" LEFT OUTER JOIN ")
							 .append(name)
							 .append(" ")
							 .append(prefix.replace(':','_'))
							 .append(" ON ").append(objDef.getJoinBind(cnt, null));
				}
				// Tabla de entidad agregada a la principal
				else if (objDef instanceof WLTableJoinDef) {

					WLTableJoinDef tablejoinDef = (WLTableJoinDef)objDef;
					leftTable = tablejoinDef.getName();

					// Si hay sustitutos de campos de esta tabla
					// dicha tabla no tiene que aparece en el LEFT OUTER JOIN de las tablas de sustituto
					// ya que ya está incluida en la consulta
					rightTable = name;
					// Prefijo para las columnas de la entidad
					prefix = rightTable;

					// Generar el LEFT OUTER JOIN
					if (leftOuter.length()>0) leftOuter.append(" ");
					if (tablasEnOuter.get(leftTable) == null) {
						leftOuter.append(mtablemap.get(leftTable)).append(" ").append(leftTable);
					}
					leftOuter.append(" LEFT OUTER JOIN ").append(rightTable).append(" ").append(rightTable);
					leftOuter.append(" ON ").append(objDef.getJoinBind(cnt, null));
				}

				Property[] properties = (Property[]) mpropertymap.get(prefix);

	            //DBTableDesc tabledesc = tblmgrfactory.getTableDesc(cnt,name.split(" ")[0], prefix, properties, null, true);
				DBTableDesc tabledesc = null;
				CTEntityDAO ctEntityDAO = null;
				try{
					//ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, name.split(" ")[0]);
					ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, name);
				}catch(ISPACException e){
					if (! (e.getCause() instanceof SQLException) )
						throw e;
				}
				Date timestamp = null;
				if (ctEntityDAO != null){
					timestamp = ctEntityDAO.getTimestamp();
				}
				if (ctEntityDAO != null && ctEntityDAO.getType() == EntityType.PROCESS_ENTITY_TYPE.getId()){
					//tabledesc = tblmgrfactory.getEntityDesc(cnt,name.split(" ")[0], prefix, properties, timestamp);
					tabledesc = tblmgrfactory.getEntityDesc(cnt,name, prefix, properties, timestamp);
				}else{
					//tabledesc = tblmgrfactory.getTableDesc(cnt,name.split(" ")[0], prefix, properties, timestamp, false);
					tabledesc = tblmgrfactory.getTableDesc(cnt,name, prefix, properties, timestamp, false);
				}

	            //DBTableDesc tabledesc2= new DBTableDesc(name.split(" ")[1],properties);
	            tabledesclist.add(tabledesc);

	            /*
				if ((tablasEnOuter.get(leftTable) == null)
						&& (jointablename.length()>0)) {
					jointablename.append(", ");
				}
				*/

				tablasEnOuter.put(leftTable, leftTable);
				tablasEnOuter.put(rightTable, rightTable);
			}

			if (jointablename.length()>0) jointablename.append(" ");
			jointablename.append(leftOuter);
		}

        //composicion del FROM para tablas sin relacion
        Iterator it=mtablemap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry=(Map.Entry)it.next();
            String tblname = (String)entry.getValue();
            String prefix=(String)entry.getKey();

            //solo se debera de añadir si no esta en un left outer
            if (tablasEnOuter.get(prefix)==null){
				if (jointablename.length()>0)
					jointablename.append(", ");
            	jointablename.append(tblname+" "+PrefixBuilder.buildRawPrefix(prefix)+" ");
            }

            Property[] properties = (Property[]) mpropertymap.get(prefix);

            //DBTableDesc tabledesc=tblmgrfactory.getTableDesc(cnt,tblname,prefix,properties, null, true);
            DBTableDesc tabledesc = null;
			//CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, tblname);
			CTEntityDAO ctEntityDAO = null;
			try{
				ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, tblname);
			}catch(ISPACException e){
				if (! (e.getCause() instanceof SQLException) )
					throw e;
			}
			Date timestamp = null;
			if (ctEntityDAO != null){
				timestamp = ctEntityDAO.getTimestamp();
			}
			if (ctEntityDAO != null && ctEntityDAO.getType() == EntityType.PROCESS_ENTITY_TYPE.getId()){
				tabledesc = tblmgrfactory.getEntityDesc(cnt,tblname, prefix, properties, timestamp);
			}else{
				tabledesc = tblmgrfactory.getTableDesc(cnt,tblname, prefix, properties, timestamp, false);
			}

            tabledesclist.add(tabledesc);
        }

        DBTableDesc[] tbldescarr=(DBTableDesc[])tabledesclist.toArray(new DBTableDesc[]{});
        return new DBTableJoinDesc(jointablename.toString(),tbldescarr);
    }

//    private String getJoinConditions(String name, String property, String productName, String tblcodefield){
//        String join = "( "+PrefixBuilder.buildRawName(name, property)+ " = " + tblcodefield+" )";
////        if (productName.startsWith("MICROSOFT SQL SERVER"))
////            join = "( "+PrefixBuilder.buildRawName(name, property)+ " *= " + tblcodefield+" )";
////        else if(productName.startsWith("ORACLE"))
////            join = "( "+PrefixBuilder.buildRawName(name, property)+ " = " + tblcodefield+ " (+) )";
//        return join;
//    }

    public CollectionDAO queryTableJoin(DbCnt cnt,String sqljoinquery)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt);
        collection.query(cnt, sqljoinquery);
        return collection;
    }
    public CollectionDAO queryTableJoin(DbCnt cnt,String sqljoinquery , int limit)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt);
        collection.query(cnt, sqljoinquery,null, limit );
        return collection;
    }

    public CollectionDAO queryDistinctTableJoin(DbCnt cnt,String sqljoinquery)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt);
        collection.queryDistinct(cnt, sqljoinquery);
        return collection;
    }

    public int countTableJoin(DbCnt cnt,String sqljoinquery)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt);
        return collection.count(cnt, sqljoinquery);
    }

    public CollectionDAO queryTableJoin(DbCnt cnt,String sqljoinquery, String keyProperty)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt, keyProperty);
        collection.query(cnt, sqljoinquery);
        return collection;
    }

    public CollectionDAO queryDistinctTableJoin(DbCnt cnt,String sqljoinquery, String keyProperty)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt, keyProperty);
        collection.queryDistinct(cnt, sqljoinquery);
        return collection;
    }

    public int countTableJoin(DbCnt cnt,String sqljoinquery, String keyProperty)
    throws ISPACException
    {
        CollectionDAO collection = newCollectionDAO(cnt, keyProperty);
        return collection.count(cnt, sqljoinquery);
    }

}