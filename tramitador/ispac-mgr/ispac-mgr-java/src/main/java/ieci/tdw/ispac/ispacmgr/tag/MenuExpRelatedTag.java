package ieci.tdw.ispac.ispacmgr.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;
import java.util.List;
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
public class MenuExpRelatedTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	// Constantes --------------------------------------------------------------
    /** Nombre del atributo en el contexto de página que contiene los
     * identificadores de barras de menú existentes */
    public static final String ID_MAP = "ieci.tdw.ispac.ispacweb.menu.ID_MAP";

    private static final Logger logger = Logger.getLogger(MenuExpRelatedTag.class);

    /** Nombre del atributo que los expedientes relacionados hijos*/
    private String hijos;
    
    /** Nombre del atributo que los expedientes relacionados padres*/
    private String padres; 

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
            drawExpRelated();
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

    
   private void drawExpRelated()throws IOException,ISPACException{
  	  
	 HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	 String context =request.getContextPath();
	 String buffer="";
 	 List subExp = (List)pageContext.findAttribute(getHijos());
 	 List supExp = (List)pageContext.findAttribute(getPadres());
 	 ClientContext cct =((SessionAPI)getSession()).getClientContext();
 	 buffer+="<ul class=\"menu_grupo\">";
 	 ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME,cct.getLocale());
 	  
	 buffer+="<li id=\"itemExpRelated\" class=\"item_plegado\"> <a href=\"#\" onclick=\"javascript:showItem('ExpRelated');return false;\"> ";
	 buffer+=bundle.getString("menu.expRelacionados")+"</a>";
	 buffer+="<ul>";
	  
	 
	 buffer=generateHtmlExpsRelated(buffer, "expRel", subExp);
	 buffer=generateHtmlExpsRelated(buffer,"expRelPadre", supExp);
	
		 
	 
	 String url=context+"/showRelatedExpedients.do";
	 String urlImg = StaticContext.rewriteHref(pageContext, context, "img/search-mg.gif");
	 //Ver todos
	 buffer+="<li  class=\"menuBold\">"+bundle.getString("menu.expRel.verTodos");
	 buffer+="<a href=\"javascript: showFrame(\'workframe\', \'"+url+"\', '', '', '', false, false);\">";
	 buffer+="<img title=\""+bundle.getString("menu.expRel.verTodos")+"\"  src=\""+urlImg+"\"/>";
	 buffer+="</a></li>";

     //Relacionar expediente, solo si no estamos en modo solo lectura
	 //Object readOnly= request.getAttribute("readOnly");
	//if(readOnly==null || StringUtils.isEmpty(readOnly.toString())){
	 String readonlyState = (String)request.getAttribute(ActionsConstants.READONLYSTATE);
	 if (readonlyState == null){
		 url=context+"/relateExpedient.do?method=enter";
		 
		 buffer+="<li  class=\"menuBold\">"+bundle.getString("menu.expRelacionar");
		 buffer+="<a href=\"javascript: showFrame(\'workframe\', \'"+url+"\', '', '', '', false, false);\">";
		 buffer+="<img title=\""+bundle.getString("menu.expRelacionar")+"\"  src=\""+urlImg+"\"/>";
		 buffer+="</a></li>";
		 
		
	 }


	 buffer+="</ul>";
     buffer+="</li>";
     buffer+="</ul>";
     JspWriter out = pageContext.getOut();
     out.println(buffer);
 
    }

 	
   private String   generateHtmlExpsRelated(String buffer, String tipo, List lista) throws NumberFormatException, ISPACException{
	   
		 
	   if(lista!=null && lista.size()>0){
		   HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		   String context =request.getContextPath();
			//Solo se muestran los dos primeros , luego ya se ponen puntos suspensivos
			 for(int i=0; i<lista.size()&& i<2 ; i++){
				 ItemBean itemBean=(ItemBean) lista.get(i);
				
				 if(itemBean.getProperty("ID_STAGE")!=null){
					 int idstage=Integer.parseInt(itemBean.getProperty("ID_STAGE").toString());
					 buffer+="<li class=\"menu "+tipo+"Abierto\">";
					 String url=context+"/showExpedient.do?stageId="+idstage;
					 buffer+="<a href=\""+url+"\"   class=\"menu\" >"+itemBean.getProperty("NUMEXP")+"  ["+itemBean.getProperty("RELACION")+"]</a>"; 	
					 buffer+="</li>";
				 }
				 //cerrado
				 else{
					 
					 	 buffer+="<li class=\"menuSelect "+tipo+"Cerrado\">";

						 //buffer+=itemBean.getProperty("NUMEXP")+"["+itemBean.getProperty("RELACION")+"]"; 	
						 String url=context+"/showData.do?numexp="+itemBean.getProperty("NUMEXP");
						 buffer+="<a href=\""+url+"\" class=\"menu\" >"+itemBean.getProperty("NUMEXP")+"  ["+itemBean.getProperty("RELACION")+"]</a>";
						 
						 buffer+="</li>";
					 
				 }
	 
			 }
	   }
	   return buffer;
   }
    /**
     * @return Devuelve el valor de <code>hijos</code>.
     */
    public String getHijos() {
        return this.hijos;
    }
    /**
     * Establece el valor de <code>hijos</code>.
     * @param name
     */
    public void setHijos(String hijos) {
        this.hijos = hijos;
    }

	public String getPadres() {
		return padres;
	}

	public void setPadres(String padres) {
		this.padres = padres;
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
