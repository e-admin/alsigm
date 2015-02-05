package ieci.tdw.ispac.ispacmgr.tag;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.bean.scheme.RegEntityBean;
import ieci.tdw.ispac.ispacmgr.bean.scheme.SchemeEntityBean;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;

/**
 * Tag Menu Bar.
 */
public class MenuTaskTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// Constantes --------------------------------------------------------------
    /** Nombre del atributo en el contexto de página que contiene los
     * identificadores de barras de menú existentes */
    public static final String ID_MAP = "ieci.tdw.ispac.ispacweb.menu.ID_MAP";

    private static final Logger logger = Logger.getLogger(MenuTaskTag.class);

    /** Nombre del atributo que contiene el esquema */
    private String name;
    
   

 // Fichero de recursos de la aplicación
	private static final String BUNDLE_NAME = "ieci.tdw.ispac.ispacmgr.resources.ApplicationResources";
	
   

    // Métodos -----------------------------------------------------------------
   
  
    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
       
        return super.doStartTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {

        try {
            drawTask();
            pageContext.getOut().flush();

        } catch (IOException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e);
        }
        catch (ISPACException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e);
        }
      
        return EVAL_PAGE;
    }

    
   private void drawTask()throws IOException,ISPACException{
  	  
 	
 	 
 	  HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
 	  String actuales="";
 	  String anteriores="";
 	  

 	 ClientContext cct =((SessionAPI)getSession()).getClientContext();
 	 List schemeList = (List)pageContext.findAttribute(getName());
 	 actuales+="<ul class=\"menu_grupo\">";
 	 anteriores+="<ul class=\"menu_grupo\">";
 	 ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME,cct.getLocale() );
 	  
	 actuales+="<li id=\"itemCurrentTask\" class=\"item_plegado\"> <a href=\"#\" onclick=\"javascript:showItem('CurrentTask');return false;\"> ";
	 actuales+=bundle.getString("menu.expTramites")+"</a>";

	 anteriores+="<li id=\"itemHistoryTask\" class=\"item_plegado\"> <a href=\"#\" onclick=\"javascript:showItem('HistoryTask');return false;\"> ";
	 anteriores+=bundle.getString("menu.expTramitesClose")+"</a>";

 	  	 actuales+="<ul>";
 	  	 anteriores+="<ul>";
	     if(schemeList!=null && schemeList.size()>0){
	    	for(int i=0; i<schemeList.size(); i++){
	    		SchemeEntityBean item=(SchemeEntityBean) schemeList.get(i);
	    		String entityId=item.getItem().getString("ID");
	    		if(StringUtils.equals(entityId, ""+ISPACEntities.DT_ID_TRAMITES)){
	    			//Recorremos cada uno de sus registros
	    			List registros=item.getRegs();
	    			if(registros!=null){
	    				for(int j=0; j<registros.size(); j++){
	    					RegEntityBean reg=(RegEntityBean) registros.get(j);
	    					//int key=((Integer) reg.getProperty("SCHEME_ID")).intValue();
	    					
	    					int id_fase_exp=((Integer) reg.getProperty("ID_FASE_EXP")).intValue();
	    					String stageId=request.getSession().getAttribute("stageId").toString();
	    					
	    					//Compruebo que estamo tratando un tramite de la fase actual
	    					if(Integer.parseInt(stageId)==id_fase_exp ){
	    						actuales=generateHtmlTask(actuales,reg,true);
	    					}
	    					//Fases anteriores
	    					else if(Integer.parseInt(stageId)!=id_fase_exp ){
	    						
	    						anteriores=generateHtmlTask(anteriores,reg,false);	
	    					}
	    				}
	    			}
	    		}
	    	} 
	     }
	     anteriores+="</ul>";
	     actuales+="</ul>";
	     anteriores+="</li>";
	     actuales+="</li>";
	     anteriores+="</ul>";
	     actuales+="</ul>";
	     JspWriter out = pageContext.getOut();
	     out.println(actuales);
	     out.println(anteriores);
	     
    }

   
   private String generateHtmlTask(String salida, RegEntityBean reg,boolean actual) throws ISPACException{
	   
	   HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	   String context =request.getContextPath();
	   salida+="<li class=\""+reg.getClase()+"\" >";
		
		if(StringUtils.isEmpty(reg.getUrl())){
			 salida+=reg.getProperty("SCHEME_EXPR").toString();
		}
		else{
			String url=context+"/"+reg.getUrl()+"?";
			
		
			int taskIdReg=((Integer) reg.getParams().get("taskId")).intValue();
			Object taskIdParam=request.getParameter("taskId");
			
				url=makeUrl(url, reg.getParams());
				
				if(taskIdParam!=null && taskIdReg==Integer.parseInt(taskIdParam.toString())){
					 salida+="<a   class=\"selectedTask\" href=\""+url+"\"><p class=\"withoutMarginTopBottom\"  >"+
							 reg.getString("SCHEME_EXPR")+"</p></a><p class=\"infoTramite\" >"+
							 reg.getString("FECHA_INICIO")+"</p>";
					 if(actual){
						 salida+="<script>javascript: hide_expand('7', \"img/arrow_down_sch.gif\", \"img/arrow_right_sch.gif\");</script>";	
					}
					else{
						salida+="<script>javascript: hide_expand('7Close', \"img/arrow_down_sch.gif\", \"img/arrow_right_sch.gif\");</script>";
						}
				}
				else{
					 salida+="<a  href=\""+url+"\"><p class=\"withoutMarginTopBottom\">"+
							 reg.getString("SCHEME_EXPR")+"</p></a><p class=\"infoTramite\" >"+
							 reg.getString("FECHA_INICIO")+"</p>";
				}
			}


	
	//Actividades de un subproceso
	if(reg.getLtRegEntityBean()!=null && reg.getLtRegEntityBean().size()>0){
		
		 salida+="<ul>";
		for(int k=0; k<reg.getLtRegEntityBean().size(); k++){
			RegEntityBean registro = (RegEntityBean) reg.getLtRegEntityBean().get(k);
			 salida+="<li class=\"actividad\" >";
			String url=context+"/"+registro.getUrl()+"&";
			url=makeUrl(url, reg.getParams());
			 salida+="<a  href=\""+url+"\"><p class=\"withoutMarginTopBottom\"  >"
			+registro.getString("NAME_STAGE")+"</p></a><p class=\"infoTramite\" >"+ reg.getString("FECHA_INICIO")+"</p>";
			 salida+="</li>";
		}
		 salida+="</ul>";
	
	}
	 salida+="</li>";
	 
	 return salida;
   }
/**
 * Construe la urr con los parámetros
 * @param url
 * @param params
 * @return
 */
   
   private String makeUrl(String url, Map params){
		Iterator itr=params.keySet().iterator();
		boolean primero=true;
		while(itr.hasNext()){
			String param=(String) itr.next();
			if(primero){
				url+=param+"="+params.get(param);
				primero=false;
			}
			else{
				url+="&"+param+"="+params.get(param);
			}
		}
		
		return url;
	   
   }
    
   
    /**
     * @return Devuelve el valor de <code>name</code>.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Establece el valor de <code>name</code>.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la sessionApi
     */
    protected SessionAPI getSession() {

	  	SessionAPI sessionAPI = null;

	  	try	{
	  		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	  		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
	  		sessionAPI = SessionAPIFactory.getSessionAPI(request, response);
	    } catch( ISPACException e) {
	    	logger.warn("Error al obtener el SessionAPI", e);
	    }

	    return sessionAPI;
  }


}
