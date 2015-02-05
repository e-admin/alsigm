package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

public class UpdateFieldTag extends TagSupport {

  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
  MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

  /**
   * Nombre del campo 
   */
  protected String field = null;
  /**
   * Valor asignado al campo 
   */
  protected String value = null;

  protected String name = null;
  protected String property = null;

  /**
   * Nombre del formulario en el que están los fields
   */
  protected String formName = "defaultForm";
  
  /**
   * Cadena que representa al objeto frame para luego poder acceder a sus propiedades
   */
  protected String frame =null;
  
  public String getField() {
    return this.field;
  }

  public void setField( String field) {
    this.field = field;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue( String value) {
    this.value = value;
  }

  public String getName() {
    return this.name;
  }

  public void setName( String name) {
    this.name = name;
  }

  public String getProperty() {
    return this.property;
  }

  public void setProperty( String property) {
    this.property = property;
  }

  public int doStartTag() 
  throws JspException {
    

	JspWriter writer = pageContext.getOut();
	
	try
	{
	  	if (name != null)
	  	{
	  	    ItemBean item = (ItemBean) pageContext.findAttribute(name);
	  	    if (item != null && property != null)
	  	    {
		  	    value = item.getString(property);
	  	    }
	  	}
	  	
  	    if (value == null)
  	    {
  	    	value = "";
  	    }
	  	
		StringBuffer javascript = new StringBuffer();
		
		javascript.append("<script>");
		if(StringUtils.isNotBlank(frame)){
			javascript.append(frame);
		}
		else{
		javascript.append("top");
		}
		javascript.append(".document.")
				  .append(getFormName())
				  .append(".elements['")
				  .append(field)
				  .append("'].value = '")
				  .append(value.trim())
				  .append("';")
				  .append("</script>");
		
		writer.print(javascript.toString());
	} 
    catch (Exception e) 
	{
		TagUtils.getInstance().saveException(pageContext, e);
		throw new JspException(e.toString());
    }
	
	return EVAL_BODY_INCLUDE;
  }
  
  public int doEndTag() 
  throws JspException {
    
    return EVAL_PAGE;
  }

  public void release() {
  
    super.release();
    field = null;
    value = null;
    name = null;
    property = null;
  }

	/**
	 * @return Returns the formName.
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * @param formName The formName to set.
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}
  
}