package ieci.tdw.ispac.ispacweb.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;
import org.displaytag.exception.TagStructureException;

public class ISPACTabTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	/**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources
            .getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * Identificador del tab
     */
    protected String id = null;
    /**
     * Titulo del tab 
     */
    protected String title = null;
    /**
     * Key para obtener el mensaje del titulo en función del idioma utilizado
     */
    protected String titleKey = null;


    /**
     * Process the start of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException
    {
    	
    	 ISPACTabsTag tabsTag = getTabsTag();
          if (tabsTag == null)
          {
              throw new TagStructureException(getClass(), "tab", "tabs");
          }
          
          return super.doStartTag();
    }

    public int doEndTag() throws JspException {
    	 
    	ISPACTabsTag tabs= getTabsTag();
    	
    	   Tab tab= new Tab();
    	   tab.setId(tabs.getIdTab());
    	   tab.setTitle(getTitle());
    	   tab.setTitleKey(getTitleKey());
    	   BodyContent body= this.bodyContent;
    	   if(body!=null){
    	   tab.setContenido_tab(this.bodyContent.getString());
    	   }
    	   //todo falta el cuerpo
    	   tabs.addTab(tab);
    	   
       return super.doEndTag();
    }


    
    


	/**
     * Looks up the parent tabs tag.
     * @return a tabs tag instance.
     */
    protected ISPACTabsTag getTabsTag()
    {
        return (ISPACTabsTag) findAncestorWithClass(this, ISPACTabsTag.class);
    }


    /**
     * Returns the id.
     *
     * @return String
     */
    public String getId()
    {
        return this.id;
    }
    /**
     * Sets the id.
     *
     * @param id The id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Return the title
     * 
     * @return String
     */

	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return the titleKey
	 * @return String
	 */
	public String getTitleKey() {
		return titleKey;
	}

	/**
	 * Sets the title key
	 * @param titleKey
	 */
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}

	/**
     * Release all allocated resources.
     */
    public void release() {

        super.release();

        id = null;
        title="";
        titleKey="";
       
    }
    
}