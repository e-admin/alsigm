package ieci.tdw.ispac.ispacmgr.search;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MultiListTable {

    protected static Logger logger = Logger.getLogger(MultiListTable.class);
    /**
     * Nombre de la tabla de la cual se obtienen los valores 
     */
    private String table;
   
    /**
     * @param table Nombre de la lista
     * @throws ISPACException
     */
    public MultiListTable(String table) throws ISPACException {
        this.table = table;
    }

    public String format(String field, String label, String value, String defaultValue, String height, Map params, String dependantColumn, String dependantField, String dependatColumnBbdd, String orderBy){
        return format(field, label, value, null, defaultValue, height, params, dependantColumn , dependantField , dependatColumnBbdd , orderBy);
    }
    public String format(String field, String label, String value, String defaultValue, String height, String orderBy){
        return format(field, label, value, null, defaultValue, height, orderBy);
    }
    
    
    /**
     * @param field nombre del control HTML
     * @param label nombre del campo de cada registro a mostrar como etiqueta en el control HTML.
     * @param value nombre del campo de cada registro a situar como valor en el control HTML.
     * @param clause restricción a aplicar en la consulta.
     * @return una cadena que contiene un control SELECT de HTML con los 
     * valores de la tabla de validación. 
     */
    public String format(String field, String label, String value, String clause, String defaultValue, String height, String orderBy){
        StringBuffer result = new StringBuffer();
        DbCnt cnt = new DbCnt();
        DbResultSet dbRs = null;
        try {
            //cnt = new DbCnt(poolName);
            cnt.getConnection();
            //Componemos la consulta a aplicar
            String query = "SELECT "+label+", "+value+" FROM "+table+" ";
            if (StringUtils.isNotBlank(clause))
                query += clause +" AND "+label+" IS NOT NULL ";
            else
                query += " WHERE "+label+" IS NOT NULL ";
            if(StringUtils.isNotBlank(orderBy)){
            	query +=" ORDER BY "+orderBy;
            }
            
            if(logger.isDebugEnabled()){
           	 logger.debug("Se procederá a ejecuta la consulta: "+query);
            }
            //Ejecutamos la consulta
            dbRs = cnt.executeQuery(query);
            ResultSet rs = dbRs.getResultSet();
            
            
            if(StringUtils.isEmpty(height)){
            	height="100px";
            }
            height="height:"+height;
     		//Componemos el control
            result.append("<div class='group_checkBox'  style='"+ height +"'>");
            while(rs.next())
                result.append("<input type='checkbox' name='multivalues' id='"+field+"' value='"+field+"ValuesMulti="+rs.getString(value)+"'>"+rs.getString(label)+"</input></br>");
     		result.append("</div>");
        } catch (Exception e) {
            logger.error(e);
        }finally{
            if (dbRs != null)
                try {
                    //Cerramos el DbResultSet
                    dbRs.close();
                } catch (ISPACException e) {logger.error(e);}
                finally{
                    //Cerramos la conexión
        		    if (cnt != null)
        			    cnt.closeConnection();
                }
		}
		return result.toString();
    }
    
    public String format(String field, String label, String value, String clause, String defaultValue, String height, Map params, String dependantColumn, String dependantField, String dependantColumnBbdd, String orderBy){
    	return  format(field, label, value, clause, defaultValue,  height, params, dependantColumn, dependantField, dependantColumnBbdd, orderBy,null);
    }
    public String format(String field, String label, String value, String clause, String defaultValue, String height, Map params, String dependantColumn, String dependantField, String dependantColumnBbdd, String orderBy, Map values){
        StringBuffer result = new StringBuffer();
        DbCnt cnt = new DbCnt();
        DbResultSet dbRs = null;
        ArrayList seleccionUsr=new ArrayList();
        try {
            //cnt = new DbCnt(poolName);
            cnt.getConnection();
            //Componemos la consulta a aplicar
            String nombreTabla=((String[])dependantColumnBbdd.split(":"))[0];
            dependantColumnBbdd=dependantColumnBbdd.replaceAll(":", ".");
            String query = "SELECT DISTINCT "+table+"."+label+", "+table+"."+value+" FROM "+table+" ";
            if(StringUtils.isNotEmpty(nombreTabla)){
            	query+=" , "+nombreTabla+" ";
            }
            if (StringUtils.isNotBlank(clause))
                query += clause +" AND "+label+" IS NOT NULL ";
            else
                query += " WHERE "+label+" IS NOT NULL ";

            if(StringUtils.isNotBlank(dependantColumn) && params.get(dependantColumn)!=null){
            	
            	query+="AND "+dependantColumnBbdd+"="+table+"."+dependantField+" AND "+table+"."+dependantField+"="+((String[])params.get(dependantColumn))[0];
            }
            
            
            if(StringUtils.isNotBlank(orderBy)){
            	query+=" ORDER BY "+orderBy;
            }
            //Ejecutamos la consulta
            dbRs = cnt.executeQuery(query);
            ResultSet rs = dbRs.getResultSet();
            
            
            if(StringUtils.isEmpty(height)){
            	height="100px";
            }
            height="height:"+height;
            
            if(values!=null && values.containsKey(field)){
            	seleccionUsr=(ArrayList) values.get(field);
            }
     		//Componemos el control
            result.append("<div class='group_checkBox'  style='"+ height +"'>");
            String valueMulti="";
            while(rs.next()){
            	valueMulti=rs.getString(value);
            	if(seleccionUsr.contains(valueMulti)){
            		result.append("<input type='checkbox' name='multivalues' id='"+field+"' value='"+field+"ValuesMulti="+valueMulti+"' checked>"+rs.getString(label)+"</input></br>");
            	}
            	else{
            		result.append("<input type='checkbox' name='multivalues' id='"+field+"' value='"+field+"ValuesMulti="+valueMulti+"'>"+rs.getString(label)+"</input></br>");
                	
            	}
            }
            result.append("</div>");
        } catch (Exception e) {
            logger.error(e);
        }finally{
            if (dbRs != null)
                try {
                    //Cerramos el DbResultSet
                    dbRs.close();
                } catch (ISPACException e) {logger.error(e);}
                finally{
                    //Cerramos la conexión
        		    if (cnt != null)
        			    cnt.closeConnection();
                }
		}
		return result.toString();
    }
}
