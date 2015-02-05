package ieci.tdw.ispac.ispacmgr.search;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.common.constants.HierarchicalTablesConstants;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.sql.ResultSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ValidationTable {

    protected static Logger logger = Logger.getLogger(ValidationTable.class);
    /**
     * Nombre de la tabla de la cual se obtienen los valores 
     */
    private String table;
    /**
     * Nombre del pool de conexiones a BD a utilizar
     */
    //private String poolName = null;
   
    /**
     * @param table Nombre de la tabla de validación
     * @throws ISPACException
     */
    public ValidationTable(String table) throws ISPACException {
        
		//ISPACConfiguration parameters = ISPACConfiguration.getInstance();
		//poolName = parameters.get(ISPACConfiguration.POOLNAME);
        this.table = table;
    }

    
    public String format(String field, String label, String value, String defaultValue, String reload, String orderBy){
        return format(field, label, value, null, defaultValue, reload , orderBy,null,null, null , null,null,null);
    }
    
    public String format(String field, String label, String value, String clause, String defaultValue, String reload, String orderBy ,Map values , String rol ,  String parentHierarchicalName , String parentFieldHierarchical,String childHierarchicalName , String childFieldHierarchical){
    	 StringBuffer result = new StringBuffer();
         DbCnt cnt = new DbCnt();
         DbResultSet dbRs = null;
         String valueSelected="";
         String valueRs="";
         String idRs="";
         String  setIdOptionSelected="";
         boolean isPadre=false;
         ResultSet rs=null;
         int parentHierarchicalId=0;
         int childHierarchicalId=0;
         StringBuffer selectIds=new StringBuffer();
         boolean selected=false;
         boolean reloadJs=false;
         try {
        	 
       	  cnt.getConnection();
         //Obtenemos los identificadores de las jerarquías.
         if(StringUtils.isNotBlank(parentHierarchicalName)){
        	//Obtenemos el id de la jerarquia padre
 			String sql="SELECT * FROM SPAC_CT_JERARQUIAS WHERE NOMBRE='"+DBUtil.replaceQuotes(parentHierarchicalName)+"'";
 			dbRs = cnt.executeQuery(sql);
 			rs  = dbRs.getResultSet();
 			if(rs.next()){
 				parentHierarchicalId=rs.getInt("ID");
 			}
         }
         if(StringUtils.isNotBlank(childHierarchicalName)){
        	//Obtenemos el id de la jerarquia hija
  			String sql="SELECT * FROM SPAC_CT_JERARQUIAS WHERE NOMBRE='"+DBUtil.replaceQuotes(childHierarchicalName)+"'";
  			dbRs = cnt.executeQuery(sql);
  			rs  = dbRs.getResultSet();
  			if(rs.next()){
  				childHierarchicalId=rs.getInt("ID");
  			}
         }
         
         
         //Valor no asignado por usuario pero con valor por defecto
         if(StringUtils.isNotBlank(defaultValue) && !values.containsKey(field)){
        	 values.put(field, defaultValue);
         }
         
         if(StringUtils.equalsIgnoreCase(HierarchicalTablesConstants.PARENT, rol)){
        	 isPadre=true;
        	 //Si es padre necesitamos conocer los id`s ya que si su hijo es un validateFieldByCode necesita el id para establecer el filtro
        	 selectIds.append("<select  style='visibility:hidden;display:none' id='selectIds"+field.replaceAll(":","")+"'>");
        	 String cadena="<input type='text' style='visibility:hidden;display:none' id='parent"+field.replaceAll(":","")+"code' value=''  />";
        	 cadena+="<input type='text' style='visibility:hidden;display:none' id='parent"+field.replaceAll(":","")+"id' value=''  />";
        	 cadena+="<input type='text' style='visibility:hidden;display:none;' id='parent"+field.replaceAll(":","")+"codeAnt' value=''  />";
        	 cadena+="<script>";
 	         cadena+=" $(document).ready(function() {$('#select"+field.replaceAll(":","")+"').change(function() {" +
 	         		" $('#select"+field.replaceAll(":","")+" option:selected ').each(function (){ " +
 	        		"	updateValuesToChild( $(this).val(),'"+childHierarchicalId+"','"+childFieldHierarchical.replaceAll(":","")+"','"+value+"','"+label+"','"+field.replaceAll(":","")+"');" +
 	        		"});" +
 	        	
 	        		" if(!$(this).val()){resetChild('"+childFieldHierarchical.replaceAll(":","")+"');}"+ 
 	        		"});";
 	         cadena+="})</script>";
 	         result.append(cadena);
        	 
         }
        result.append("<select name='values("+field+")' class='input' id='select"+field.replaceAll(":","")+"'>");
       
         //Si es padre pero a su vez no tiene relación jerárquica con otro campo, o si tiene un valor asignado o si no pertenece a una jerarquia cargaremos la lista 
         if((isPadre && StringUtils.isBlank(parentFieldHierarchical))||
        		 (StringUtils.isBlank(childHierarchicalName) && StringUtils.isBlank(parentHierarchicalName))){
        	 
        	 
        	 //cnt = new DbCnt(poolName);
           
             String query="";
             
             //Componemos la consulta a aplicar en caso que sea padre
             if (StringUtils.equalsIgnoreCase(label, value)) {
            	 query = "SELECT ID , "+label+" FROM "+table+" ";
             } else {
            	 query = "SELECT ID , "+label+", "+value+" FROM "+table+" ";
             }
             
             if (StringUtils.isNotBlank(clause))
                 query += clause +" AND "+label+" IS NOT NULL ";
             else
                 query += " WHERE "+label+" IS NOT NULL ";
             
             if(StringUtils.isNotEmpty(orderBy)){
             	query+=" ORDER BY "+ orderBy;
             }
             if(logger.isDebugEnabled()){
            	 logger.debug("Se procederá a ejecuta la consulta: "+query);
             }
             
             //Ejecutamos la consulta
             dbRs = cnt.executeQuery(query);
             rs = dbRs.getResultSet();
         }
	    //estamos en la jerarquia en un campo que juega el rol de hijo, si el padre tiene valor se han de cargar los relacionados
        else if(StringUtils.isNotBlank(parentHierarchicalName) && values.containsKey(parentFieldHierarchical)){
        	 String codeParent=(String) values.get(parentFieldHierarchical);
        	//Obtenemos el id del código del padre
			String sql="SELECT * FROM SPAC_CT_ENTIDADES WHERE ID IN (SELECT ID_ENTIDAD_PADRE FROM SPAC_CT_JERARQUIAS WHERE ID="+parentHierarchicalId+")";
			dbRs = cnt.executeQuery(sql);
			rs  = dbRs.getResultSet();
			if(rs.next()){
				String entidadPadre=rs.getString("NOMBRE");
				sql = "SELECT * FROM "+entidadPadre+" WHERE VALOR='"+DBUtil.replaceQuotes(codeParent)+"'";
				// Ejecutamos la consulta
				dbRs = cnt.executeQuery(sql);
				rs = dbRs.getResultSet();
				if(rs.next()){
					String idParent=rs.getString("ID");
					sql="SELECT * FROM SPAC_CT_ENTIDADES WHERE ID IN (SELECT ID_ENTIDAD_HIJA FROM SPAC_CT_JERARQUIAS  WHERE ID="+parentHierarchicalId+")";
	 				dbRs = cnt.executeQuery(sql);
	 				rs = dbRs.getResultSet();
	 				if(rs.next()){
	 					String entidadHija=rs.getString("NOMBRE");
	 					sql="SELECT * FROM "+entidadHija+" WHERE ID IN (SELECT ID_HIJA FROM SPAC_CT_JERARQUIA_" + parentHierarchicalId + " WHERE ID_PADRE='"+DBUtil.replaceQuotes(idParent)+"')";
	 					 if(logger.isDebugEnabled()){
	 		            	 logger.debug("Se procederá a ejecuta la consulta: "+sql);
	 		             }
	 					//Ejecutamos la consulta
	 		             dbRs = cnt.executeQuery(sql);
	 		             rs = dbRs.getResultSet();
	 		             
	 				}
				}
			}
		
         }
	            
	             
         //Componemos el control

         if (!StringUtils.equals(defaultValue, "1")) {
        	 result.append("<option value='' selected='yes'></option>");
         }

         if ((values!=null) && values.containsKey(field)) {
        	 valueSelected=(String) values.get(field);
         }

         if(!StringUtils.isEmpty(reload) && !StringUtils.equalsIgnoreCase(reload, "no")){
        	 reloadJs=true;
         }

         while(rs.next()){
        	 valueRs=rs.getString(value);
        	 idRs=rs.getString("ID");
        	 result.append("<option value='"+valueRs+"'");
        	 if(StringUtils.equals(valueSelected, valueRs)){
        		 selected=true;
        		 result.append("selected='yes'");
        	 }
        	 if(reloadJs){
        		 result.append(" onclick='javascript:recargar()' ");
        	 }
        	 result.append(" >"+rs.getString(label)+"</option></br>");

        	 if(isPadre){
        		 if(selected){
        			 setIdOptionSelected=" DWRUtil.setValue('parent"+field.replaceAll(":","")+"id' ,'"+idRs+"');";
        		 }
        		 selectIds.append("<option value='"+valueRs+"'>"+idRs+"</option></br>");
        	 }
        	 selected=false;
         }




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
         

         result.append("</select>");
         if(isPadre){
        	 result.append(selectIds+"</select>");
         }
         if(StringUtils.isNotBlank(setIdOptionSelected)){
        	 result.append("<script language='JavaScript'> "+setIdOptionSelected+"</script>");
         }
 		return result.toString();
    }
    /**
     * @param field nombre del control HTML
     * @param label nombre del campo de cada registro a mostrar como etiqueta en el control HTML.
     * @param value nombre del campo de cada registro a situar como valor en el control HTML.
     * @param clause restricción a aplicar en la consulta.
     * @return una cadena que contiene un control SELECT de HTML con los 
     * valores de la tabla de validación. 
     */
    public String format(String field, String label, String value, String clause, String defaultValue, String reload, String orderBy ){
       return format(field, label,value,clause,defaultValue,reload,orderBy,null,null,null, null,null,null);
    }
}
