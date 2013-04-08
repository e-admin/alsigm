package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

public class ISPACTabsTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	/**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources
            .getMessageResources(Constants.Package + ".LocalStrings");

   protected List tabs=new ArrayList();
   protected static String CLASS_TABACTIVE="tabOn";
   protected static String CLASS_TABINACTIVO="tabOff";
   protected static String ID_DIV_CONTENIDO="contenido"; 
   protected static String ID_DIV_TABS="tabs";
   protected static String CLASS_DIV_FICHA_TABS="ficha_tabs";
   protected static String CLASS_DIV_ENCABEZADO_FICHA="encabezado_ficha";
   protected static String ID_PREFIJO_TAB="tab";
   protected static String ID_PREFIJO_CUERPO_FICHA="cuerpo";
   protected static String CLASS_CUERPO_FICHA_VISIBLE="cuerpo_ficha visible";
   protected static String CLASS_CUERPO_FICHA_OCULTO="cuerpo_ficha oculto";
   protected static String CLASS_SECCION_FICHA="seccion_ficha";
   protected static String CLASS_FORM_SECCION="form_seccion";
   protected static String UNDEFINED_TITLE="title.undefined";


    /**
     * Process the start of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException
    {
    	
        return EVAL_BODY_INCLUDE;
    }

    

    
    protected void addTab(Tab tab){
    
    	tabs.add(tab);
    }
    
    public String getIdTab(){
    	return tabs.size()+1+"";
    }
    public int doEndTag() throws JspException {
   
    	
        if (tabs.size()>0) {
        	
	        JspWriter out = pageContext.getOut();
	        try {
	            out.write(getPage());
	        }
	        catch (IOException e) {
	            pageContext.setAttribute(Globals.EXCEPTION_KEY, e, PageContext.REQUEST_SCOPE);
	            throw new JspException(messages.getMessage("common.io", e.toString()));
	        }
           
        }
        release();
        return EVAL_PAGE;
      
    }
    
    private String getPage(){
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        MessageResources resources = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
    	String result="<div id=\"contenido\"><div id=\""+ID_DIV_TABS+"\"><ul>";
    	String fichas="";
    	for(int i=0; i<tabs.size(); i++){
    		Tab tab=(Tab) tabs.get(i);
    		result+="<li id=\""+ID_PREFIJO_TAB+tab.getId()+"\" class=\" ";
    		if(i==0){
    			fichas="<div class=\" "+CLASS_DIV_FICHA_TABS+"\"><div class=\""+CLASS_DIV_ENCABEZADO_FICHA+"\"><h3></h3></div>";
    			fichas+="<div id=\""+ID_PREFIJO_CUERPO_FICHA+tab.getId()+"\" class=\""+CLASS_CUERPO_FICHA_VISIBLE+"\">";
    			result+=CLASS_TABACTIVE;
    		}
    		else{
    			fichas+="<div id=\""+ID_PREFIJO_CUERPO_FICHA+tab.getId()+"\" class=\""+CLASS_CUERPO_FICHA_OCULTO+"\">";
    			result+=CLASS_TABINACTIVO;
    		}
    		fichas+="<div class=\""+CLASS_SECCION_FICHA+"\"><div class=\""+CLASS_FORM_SECCION+"\">"+tab.getContenido_tab()+"</div></div></div>";
    		
    		//result+="\"><h3><a href=\"javascript:showTab("+tab.getId()+");\">";
    		result+="\" onclick=\"javascript:showTab("+tab.getId()+");\"><h3>";
    		
    		if (StringUtils.isNotEmpty(tab.getTitle())) {
    			result+=tab.getTitle();
    		} else if(StringUtils.isNotEmpty(tab.getTitleKey())) {
    			result+=resources.getMessage(request.getLocale(), tab.getTitleKey());
    		} else {
    			result+=resources.getMessage(request.getLocale(), UNDEFINED_TITLE);
    		}

    		//result+="</a></h3></li>";
    		result+="</h3></li>";
    	}
    	
    	fichas+="</div></div>";
    	result+="</ul></div>"+fichas;
    	
    	return result;
    	
    }



	/**
     * Release all allocated resources.
     */
    public void release() {
    	for(int i=tabs.size()-1; i>=0; i--){
    		tabs.remove(i);
    	}
        super.release();

    
      
    }




	public List getTabs() {
		return tabs;
	}




	public void setTabs(List tabs) {
		this.tabs = tabs;
	}
    
}