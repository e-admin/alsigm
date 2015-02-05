package ieci.tdw.ispac.ispacweb.tag;

import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

public class ParameterTag extends TagSupport {

  public static final String PARAMETER_ID_JAVASCRIPT = "JAVASCRIPT";
  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
  MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

  /**
   * Nombre del objeto
   */
  protected String name = "workframe";
  protected String property = null;

  public ParameterTag()
  {
      super();
      setId(null);
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

    HashMap parameters = (HashMap) pageContext.findAttribute(name);

    if (parameters != null)
    {
      parameters.put( id, property);
    }

    return SKIP_BODY;
  }

  public void release() {

    super.release();
    name = null;
    id = null;
    property = null;
  }
}
