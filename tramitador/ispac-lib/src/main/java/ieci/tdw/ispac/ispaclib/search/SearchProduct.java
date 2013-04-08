package ieci.tdw.ispac.ispaclib.search;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.search.objects.impl.Entity;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Map;

public class SearchProduct {


    /**
     * Devuelve un cadena con los joins ha incorporar en la clausa where,
     *  dependiendo del servidor de base de datos
     * @param searchinfo objeto de tipo SearchInfo con las caracteristicas de la busqueda
     * @param joins Map con las tablas para realizar las joins y como valor el tipo de
     *  relacion a establecer (INNER JOIN o LEFT JOIN)
     * @return cadena con los joins ha incorporar en la clausa where
     * @throws ISPACException
     */
    public static String getJoinConditions(SearchInfo searchinfo, Map joins) 
    		throws ISPACException {
    	
        StringBuffer conditions = new StringBuffer();
        int nEntities = searchinfo.getNumEntities();
        if (nEntities <= 1) {
            return null;
        }

        for (int i = 0; i < nEntities; i++) {
            Entity entity = (Entity)searchinfo.getEntity(i);
            String table = entity.getTable();
            if (StringUtils.equalsIgnoreCase( entity.getType(), SearchMgr.MULTIPLE_RELATION_TYPE)){
            	if (conditions.length() != 0) {
            		conditions.append(" AND ");
            	}
            	conditions.append(SearchMgr.RELATION_MULTIPLE_ENTITY);
            	conditions.append(".ENTITY_ID = ");
            	conditions.append(entity.getIdentifier()); 
            	conditions.append(" AND ");
            	conditions.append(SearchMgr.RELATION_MULTIPLE_ENTITY);
            	conditions.append(".KEY_ID = ");
            	conditions.append(table);
            	conditions.append(".ID");
            }
            else if (!table.equalsIgnoreCase(SearchMgr.MAIN_ENTITY) && !StringUtils.equalsIgnoreCase(entity.getType(),SearchMgr.MULTIPLE_RELATION_TYPE)) {
                Boolean join = (Boolean)joins.get(table.toUpperCase());
                if (join != null && !join.booleanValue()) {
                	if (conditions.length() != 0) {
                		conditions.append(" AND ");
                	}
                    conditions.append(SearchMgr.MAIN_ENTITY);
                    conditions.append(".");
                    conditions.append(SearchMgr.BINDFIELD_MAINENTITY);
            		conditions.append(" ");
					conditions.append("= ");
					conditions.append(table);
					conditions.append(".");
					if (entity.getBindField() == null) {
						conditions.append(SearchMgr.BINDFIELD_MAINENTITY);
					} else
						conditions.append(entity.getBindField());
                }
            }
        }

        if (conditions.length()==0)
            return null;

        return conditions.toString();
    }
}