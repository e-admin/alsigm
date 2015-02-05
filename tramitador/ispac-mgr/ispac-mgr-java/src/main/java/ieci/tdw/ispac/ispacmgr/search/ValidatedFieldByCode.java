package ieci.tdw.ispac.ispacmgr.search;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.common.constants.HierarchicalTablesConstants;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.util.FormUtils;

import java.sql.ResultSet;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ValidatedFieldByCode {
	 protected static Logger logger = Logger.getLogger(ValidatedFieldByCode.class);
	 
	  /**
	     * Nombre de la tabla de la cual se obtienen los valores 
	     */
	    private String table;
	    //  private String imageSearch="img/search-mg.gif";
	    //	private String imageDelete="img/borrar.gif";
	    private String width = "640";
		private String height = "480";
		private String styleClassLink="btnSearchForm";
		private String imageTabIndex="false";

	    
	   
	    /**
	     * @param table Nombre de la tabla de validación
	     * @throws ISPACException
	     */
	    public ValidatedFieldByCode(String table) throws ISPACException {
	        
	        this.table = table;
	    }

	    
	    public String format(String field, String label, String value, String defaultValue, String reload, String orderBy , String sizeCode , String rowsValue, String colsValue){
	        return format(field, label, value, null, defaultValue, reload , orderBy, sizeCode , rowsValue,  colsValue);
	    }
	    
	    
	    public String format(String field, String label, String value, String clause, String defaultValue, String reload, String orderBy ,String sizeCode , String rowsValue, String colsValue){
	    	return format(field, label, value, clause, defaultValue, reload, orderBy, sizeCode,rowsValue,colsValue, null,null,null,null,null,null,null,null,null);
	    }
	    /**
	     * @param field nombre del control HTML
	     * @param label nombre del campo de cada registro a mostrar como etiqueta en el control HTML.
	     * @param value nombre del campo de cada registro a situar como valor en el control HTML.
	     * @param clause restricción a aplicar en la consulta.
	     * @param defaultValue valor por defecto
	     * @return una cadena que contiene el dos input uno para el código y otro de solo lectura para mostrar la descripción
	     */
	       public String format(String field, String label, String value, String clause, String defaultValue, String reload, String orderBy ,
	    					String sizeCode , String rowsValue, String colsValue , Map values , String rol, String parentHierarchicalName , 
	    					String parentFieldHierarchical, String childHierarchicalName , String childFieldHierarchical ,
	    					String width, String height , ResourceBundle resourceBundle){
	     
	        String cadena="";
	        int iRowsValue=1;
	        int iSizeCode=5;
	        int iColsValue=40;
	        String valorUser="";
	        String labelUser="";
	        String idUser="";
	        DbCnt cnt = new DbCnt();
	        int parentHierarchicalId=0;
	        int childHierarchicalId=0;
	        String cadenaScript="<script language='JavaScript'> ";
	        
	        String jsShowFrame="select"+field.replaceAll(":","");
	        
	        String action="selectSubstitute.do?entity="+table+"&formName=queryform";
	      
	        if(StringUtils.isNotBlank(width)){
	        	this.width=width;
	        }
	        if(StringUtils.isNotBlank(height)){
	        	this.height=height;
	        }
	       
	        if(StringUtils.isNotBlank(sizeCode) && StringUtils.isNumeric(sizeCode))
	        {
	        	iSizeCode=Integer.parseInt(sizeCode);
	        }
	        if(StringUtils.isNotBlank(rowsValue) && StringUtils.isNumeric(rowsValue))
	        {
	        	iRowsValue=Integer.parseInt(rowsValue);
	        }
	        if(StringUtils.isNotBlank(colsValue) && StringUtils.isNumeric(colsValue))
	        {
	        	iColsValue=Integer.parseInt(colsValue);
	        }
	        if(values!=null && values.containsKey(field)){
	        	valorUser=(String) values.get(field);
	        }
	        else if(StringUtils.isNotBlank(defaultValue)){
	        	valorUser=defaultValue;
	        }

	         if(StringUtils.isNotBlank(valorUser) || StringUtils.isNotBlank(parentHierarchicalName)|| StringUtils.isNotBlank(childHierarchicalName)){
	        	  DbResultSet dbRs = null;
	        	  ResultSet rs=null;
	         	try{
	         		cnt.getConnection();
	         		//Obtenemos los identificadores de las jerarquías.
	   	         if(StringUtils.isNotBlank(parentHierarchicalName)){
	   	        	 cadenaScript+=" var jsFilterId_"+parentFieldHierarchical.replaceAll(":","")+"=''; ";
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
	        	 //Si hay valor tenemos que obtener el literal asociado
	         		if(StringUtils.isNotBlank(valorUser)){
	        
			             //Componemos la consulta a aplicar
			             String query = "SELECT ID ,"+label+"  FROM "+table+" WHERE "+value+"='"+valorUser+"'";
			         
			             if(logger.isDebugEnabled()){
			            	 logger.debug("Se procederá a ejecuta la consulta: "+query);
			             }
			             //Ejecutamos la consulta
			             dbRs = cnt.executeQuery(query);
			             rs = dbRs.getResultSet();
			             if(rs.next()){
			            	labelUser=rs.getString(label);
			            	idUser=rs.getString("ID");
			             }
			             else{
			            	 if(logger.isDebugEnabled()){
				            	 logger.debug("No hay label para el valor"+valorUser);
				             }
			            	 valorUser="";
			            	 
			             }
	             
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
	         
	        }
	         
	        //Componemos el controlid
	         cadena+="<input type='text' style='visibility:hidden;display:none' name='id"+field.replaceAll(":","")+"' id='id"+field.replaceAll(":","")+"' value='"+idUser+"'  />";
	        cadena+="<input type='text' name='values("+field+")' id='code"+field.replaceAll(":","")+"'  class='inputSearchForm' size='"+iSizeCode+"' value='"+valorUser+"'/>";
	        if(iRowsValue==1){
	        	cadena+="<input type='text' name='value"+field+"' id='value"+field.replaceAll(":","")+"' readOnly='readonly' class='inputSearchFormRO' size='"+iColsValue+"' value='"+labelUser+"'  />";
	        }
	        else{
	        	cadena+="<textarea name='value"+field+"' cols='"+iColsValue+"' rows='"+iRowsValue+"' id='value"+field.replaceAll(":","")+"' readOnly='readonly' class='inputSearchFormRO' >"+labelUser+"</textarea>";
	    	    
	        }
	        
	        //Lupa para buscar
	       cadena+= FormUtils.getValidationsActionsHtml(field.replaceAll(":",""), "false", "true", jsShowFrame, "workframe", this.width, this.height, 
	        									action, null, this.imageTabIndex, "executeFrame", this.styleClassLink, null,"", "", "", "", "", "#");
	        //Si es padre en la relación jerárquica necesitamos almacenar el code seleccionado y el code anterior
	        //El código anterior se necesita conocer para no realizar la búsqueda y resetear los valores del hijo cuando el padre no ha cambiado
	        if(StringUtils.equalsIgnoreCase(HierarchicalTablesConstants.PARENT, rol)){
	        	cadena+="<input type='text' style='visibility:hidden;display:none;' id='parent"+field.replaceAll(":","")+"code' value=''  />";
	        	cadena+="<input type='text' style='visibility:hidden;display:none;' id='parent"+field.replaceAll(":","")+"codeAnt' value=''  />";
	        	cadena+="<input type='text' style='visibility:hidden;display:none' id='parent"+field.replaceAll(":","")+"id' value=''  />";
	        }
	    
	        cadenaScript+="function setValues"+field.replaceAll(":","")+"(){" +
	        		"valueId=DWRUtil.getValue('id"+field.replaceAll(":","")+"');"+
	        		"DWRUtil.setValue('parent"+field.replaceAll(":","")+"id' ,valueId);"+
	        		"codeAnt=DWRUtil.getValue('parent"+field.replaceAll(":","")+"code');"+
	        		"DWRUtil.setValue('parent"+field.replaceAll(":","")+"codeAnt' ,codeAnt);"+
	        		"code=DWRUtil.getValue('code"+field.replaceAll(":","")+"');"+
	        		"DWRUtil.setValue('parent"+field.replaceAll(":","")+"code' ,code);"+
	        		"if(codeAnt!=code){"+
	        		"	updateValuesToChild (code,'"+ childHierarchicalId+"' ,'"+childFieldHierarchical.replaceAll(":","")+"' ,'"+value+"', '"+label+"' , '"+field.replaceAll(":","")+"');"+
	        		"}}";
		    cadenaScript+="function select"+field.replaceAll(":","")+"(target, action, width, height) {";
		    cadenaScript+="actionPath= '"+action+"&parameters="+field.replaceAll(":","")+"';";
		   if(StringUtils.isNotBlank(parentFieldHierarchical)){
			 
		   	 cadenaScript+="valueId=DWRUtil.getValue('parent"+parentFieldHierarchical.replaceAll(":","")+"id');";
		   	 cadenaScript+=" if (valueId==''){";
		     cadenaScript+=" valueId=DWRUtil.getValue('id"+parentFieldHierarchical.replaceAll(":","")+"');";
			 cadenaScript+=" DWRUtil.setValue('parent"+parentFieldHierarchical.replaceAll(":","")+"id' ,valueId);";
		   	 cadenaScript+="}";
	    	 cadenaScript+="if (valueId != ''){";
			 cadenaScript+="actionPath = actionPath + '&filterId='+valueId+'&hierarchicalName="+parentHierarchicalName+"&loadNotAssociated=false';";  
			 cadenaScript+="showFrame(target, actionPath, width, height);}";
			if(resourceBundle!=null){
			 cadenaScript+="else{"+
				 				"aviso=\""+resourceBundle.getString("search.getHierarchicalValueByCode.parentEmpty")+"\";" +
			 					"ok=\""+resourceBundle.getString("common.message.ok")+"\";" +
			 					"cancel=\""+resourceBundle.getString("common.message.cancel")+"\";" +
			 					"jAlert(aviso, ok, cancel);"+
			 					"}";  
			 }
			   
		   }
		   else{
		
			cadenaScript+="showFrame(target, actionPath, width, height);";
		   }
	        
	      cadenaScript+="} $(document).ready(function() {"+	
	      			"SearchFormAPI.addJavascriptToSetValue('"+field.replaceAll(":","")+"','values("+field+")','VALOR');"+
	      			"SearchFormAPI.addJavascriptToSetValue('"+field.replaceAll(":","")+"','value"+field+"','SUSTITUTO');"+
	      			"SearchFormAPI.addJavascriptToSetValue('"+field.replaceAll(":","")+"','id"+field.replaceAll(":","")+"','ID');";
	      		   if(StringUtils.isNotBlank(childFieldHierarchical)){
	      			 cadenaScript+="SearchFormAPI.addJavascriptToSetValue('"+field.replaceAll(":","")+"','JAVASCRIPT','setValues"+field.replaceAll(":","")+"');";
	      		   }
	      			cadenaScript+="	$('#code"+field.replaceAll(":","")+"').blur(function() {" +
	        		"		loadDescripcion( $(this).val(),'"+table+"','"+clause+"','"+field.replaceAll(":","")+"','"+value+"','"+label+"','"+parentHierarchicalId+"','"+parentFieldHierarchical.replaceAll(":","")+"','"+childHierarchicalId+"','"+childFieldHierarchical.replaceAll(":","")+"');" +
	        		"})});";

	        cadenaScript+="</script>";
	        
	      return cadena+cadenaScript;
	    }

}
