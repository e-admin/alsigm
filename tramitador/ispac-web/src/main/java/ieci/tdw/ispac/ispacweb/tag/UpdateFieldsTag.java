package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

public class UpdateFieldsTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_FORMNAME = "defaultForm";
  
  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
  MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

  /**
   * Nombre del bean del que se obtienen los datos
   */
  protected String name = null;
  /**
   * Nombre del mapa que contiene las relaciones campo/propiedad
   */
  protected String parameters = "workframe";

  /**
   * Indica si se debe componer el código como una funcion
   */
  protected String function = "false";
  
  /**
   * Nombre del formulario en el que están los fields
   */
  protected String formName = DEFAULT_FORMNAME;

  protected String multivalueId = null;
  
  /**
   * Cadena que representa al objeto frame para luego poder acceder a sus propiedades
   */
  protected String frame =null;
  
  public String getFrame() {
	return frame;
}

public void setFrame(String frame) {
	this.frame = frame;
}

public String getParameters() {
    return this.parameters;
  }

  public void setParameters( String parameters) {
    this.parameters = parameters;
  }

  public String getName() {
    return this.name;
  }

  public void setName( String name) {
    this.name = name;
  }


  public int doStartTag() throws JspException {

      if (pageContext.findAttribute(name) instanceof List)
          setListParams();
      else
          setParams();
      return EVAL_BODY_INCLUDE;
  }

  /**
   * Genera el código javascript necesario para actualizar los valores
   * de los parámetros con son de tipo Multivalue
   * ademas de enviarlo a la página de salida.
   * @throws JspException
   */

  private void setListParams() throws JspException {

      List list = (List) pageContext.findAttribute(name);
      Iterator it = list.iterator();
      try {

          Map params = (HashMap) pageContext.findAttribute(parameters);

          String param = getParamType(params, "IDS");
          String listIds = getList(list, param, "", ",");

          param = getParamType(params, "VALUES");
          String listValues = getList(list, param, "- ", "\\r\\n");

          StringBuffer javascript = new StringBuffer();
          JspWriter writer = pageContext.getOut();

          it = params.entrySet().iterator();
          Entry entry = null;
          while (it.hasNext()) {
              entry = (Entry) it.next();
              javascript.append("<script>\n");
              if (function.equals("true"))
                  javascript.append("function setValues() {");
              if(StringUtils.isBlank(frame)){
              javascript.append("var element = null;")
              			.append("element = parent.document.")
              			.append(getFormName())
              			.append(".elements['"+ entry.getKey() + "'];\n")
              			.append("if (element == null) { element = top.document.")
              			.append(getFormName())
              			.append(".elements['"+ entry.getKey() + "']; }\n")
              			.append("if (element != null)\n")
              			.append("{\n");
              }
              else{
            	  javascript.append("var element = null;")
        			.append("element = "+frame+".document.")
        			.append(getFormName())
        			.append(".elements['"+ entry.getKey() + "'];\n")
        			.append("if (element != null)\n")
        			.append("{\n");
              }
              javascript.append(getTextSetElementValue("" + entry.getValue(), listIds, listValues));

              javascript.append("}\n");
              if (function.equals("true"))
                  javascript.append("}");
              javascript.append("</script>\n");
          }
          writer.print(javascript.toString());

      } catch (Exception e) {
          TagUtils tagUtils = TagUtils.getInstance();
          tagUtils.saveException(pageContext, e);
          throw new JspException(e.toString());
      }

  }


  /**
   * En función del contenido del elemento, especificado de la siguiente forma
   * TYPE:NAME o $FLAG$, en donde TYPE puede ser IDS, VALUES, se devuelveb los siguientes
   * valores:
   *
   * TYPE = IDS -> valor a devolver 'ids'
   * TYPE = VALUES -> valor a devolver 'values'
   *
   *
   * @param element
   * @param ids Identificadores
   * @param values Valores
   * @return Cadena que se corresponde con el valor del elemento
   */
  private String getTextSetElementValue(String element, String ids, String values) {
      StringBuffer textValue = new StringBuffer();
      if (element.startsWith("IDS:"))
          textValue.append("  element.value = '" + ids + "';\n");
      else if (element.startsWith("VALUES:"))
          textValue.append("  element.value = '" + values+ "';\n");
      else if (element.equals("$FLAG$"))
          textValue.append("  element.value = 'true';\n");

      return textValue.toString();

  }

  /**
   * Dentro del mapa de par??metros existir??n algunos de la forma
   * TYPE:NAME, donde TYPE indica el tipo del par??metro (IDS, VALUES)
   * y NAME el nombre del campo que a localizar dentro del IItem.
   *
   * Este m??todo buscar?? dentro del mapa de par??metros aquel
   * cuyo tipo se corresponda con el valor de <code>type</code>
   * @param params Mapa de par??metros
   * @param type Tipo del par??metro a buscar dentro de <code>params</code>
   * @return Nombre del par??metro que se corresponde con el tipo indicado en <code>type</code>
   */
  private String getParamType(Map params, String type) {

      Iterator it = params.entrySet().iterator();
      Map.Entry entry  = null;
      String value = null;
      while(it.hasNext()){
          entry = (Map.Entry)it.next();
           value =""+entry.getValue();
          if (value.startsWith(type))
              return value.substring(value.indexOf(":")+1);
      }
      return null;
  }


  /**
   * Devuelve una cadena de elementos, donde cada elemento es el campo
   * llamado <code>param</code> de cada Item de la lista <code>list</code>.
   *
   * En la cadena de retorno, a cada elemento se le antepondrá el <code>prefijo</code>
   * y se le añadirá al final el <code>sufijo</code>
   * @param list Lista de objetos de tipo Item
   * @param param Nombre del campo a buscar dentro de cada Item
   * @param prefijo Prefijo a añadir a cada elemento de la cadena resultante
   * @param sufijo Sufijo a añadir a cada elemento de la cadena resultante
   * @return Cada de elementos separados por coma.
   * @throws ISPACException Si se produce una Excepción de ISPAC
   */

  private String getList(List list, String param, String prefijo, String sufijo) throws ISPACException {
      Iterator it = list.iterator();
      ItemBean item = null;
      StringBuffer strList = new StringBuffer();
      boolean first = true;
      while (it.hasNext()) {
          if (first == true)
              first = false;
          else
              strList.append(sufijo);
          item = (ItemBean) it.next();
          strList.append(prefijo+item.getString(param));
      }
      return strList.toString();

  }

  /**
   * Genera el c??digo javascript necesario para actualizar los valores
   * de los par??metros, ademas de enviarlo a la p??gina de salida.
   * @throws JspException
   */
  private void setParams() throws JspException {

      ItemBean item = (ItemBean) pageContext.findAttribute(name);
      
      HashMap params = (HashMap) pageContext.findAttribute(parameters);
      if (params != null) {

      Iterator iterator = params.entrySet().iterator();

      StringBuffer javascript = new StringBuffer();
      StringBuffer js = new StringBuffer();

      JspWriter writer = pageContext.getOut();

      List listById = (List)params.get(ParameterMultivalueTag.PARAMETER_PROPERTIES_BYID);
      try {
          javascript.append("<script>\n");
          if (function.equals("true"))
              javascript.append("function setValues() {");
          javascript.append("var element = null;");
          while (iterator.hasNext()) {
              Entry entry = (Entry) iterator.next();
              String sId = (String) entry.getKey();
              if (sId.equals(ParameterMultivalueTag.PARAMETER_PROPERTIES_BYID))
            	  continue;
              String sProperty = (String) entry.getValue();
              
              if (sId.equals(ParameterTag.PARAMETER_ID_JAVASCRIPT)) {
            	 
            	  // Función Javascript
            	  js.append("if (parent." + sProperty + ")\n")
            	  	.append("{\n")
            	  	.append("  parent." + sProperty + "();\n")
            	  	.append("}\n")
            	  	.append("else if (top." + sProperty + ")\n")
            	  	.append("{\n")
            	  	.append("  top." + sProperty + "();\n")
            	  	.append("}\n");
              }
              else {
            	  // Valor para propiedad del formulario
	              String sValue = item.getString(sProperty);
	              if (sValue == null) {
	                  sValue = "";
	              }
	              sValue = StringUtils.replace(sValue, "\\", "\\\\");
	              sValue = StringUtils.replace(sValue, "'", "\\'");
	              sValue = StringUtils.replace(sValue, "\n", "\\n");
	              
	              if (listById != null && listById.contains(sId)){
		              //sId = StringUtils.replace(sId, JSPBuilder.COUNTER_REPLACE, multivalueId);
	            	  sId = StringUtils.replace(sId, ")", "_"+multivalueId+")");
	            	 if(StringUtils.isBlank(frame)){
	            	  javascript.append("element = parent.document.getElementById('" + sId + "');\n")
		              		.append("if (element == null) { element = top.document.getElementById('" + sId + "');\n}")
		            		.append("if (element != null){\n")
		                	.append("  element.value = '" + sValue + "';\n")
		                	.append("}\n");
	            	  }
	            	 else{
	            		  javascript.append("element = "+frame+".document.getElementById('" + sId + "');\n")
		            		.append("if (element != null){\n")
		                	.append("  element.value = '" + sValue + "';\n")
		                	.append("}\n");
	            	 }
	              }else{
	            	  if(StringUtils.isBlank(frame)){
		              javascript.append("element = parent.document.")
		              			.append(getFormName())
		              			.append(".elements['" + sId + "'];\n")
		              			.append("if (element == null) { element = top.document.")
		              			.append(getFormName())
		              			.append(".elements['" + sId + "'];}\n")
		              			.append("if (element != null)\n")
		              			.append("{\n")
		              			.append("  element.value = '" + sValue + "';\n")
		              			.append("}\n");
	            	  }
	            	  else{
	            		  javascript.append("element = "+frame+".document.")
	              			.append(getFormName())
	              			.append(".elements['" + sId + "'];\n")
	              			.append("if (element != null)\n")
	              			.append("{\n")
	              			.append("  element.value = '" + sValue + "';\n")
	              			.append("}\n");
	            	  }
	              }
              }
          }
          
          // Ejecutar la función Javascript al final
          javascript.append(js.toString());
          
          if (function.equals("true"))
              javascript.append("}");
          javascript.append("</script>\n");

          writer.print(javascript.toString());
      }
      catch (Exception e) {
          TagUtils tagUtils = TagUtils.getInstance();
          tagUtils.saveException(pageContext, e);
          throw new JspException(e.toString());
      }
     }
  }


  public int doEndTag()
  throws JspException {

    return EVAL_PAGE;
  }

  public void release() {

    super.release();
    id = null;
    name = null;
  }

  /**
   * @return Returns the function.
   */
  public String getFunction() {
      return function;
  }

  /**
   * @param function
   *            The function to set.
   */
  public void setFunction(String function) {
      this.function = function;
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
		if (StringUtils.isEmpty(formName)){
			this.formName = DEFAULT_FORMNAME;
		}else{
			this.formName = formName;
		}
	}

	public String getMultivalueId() {
		return multivalueId;
	}

	public void setMultivalueId(String multivalueId) {
		this.multivalueId = multivalueId;
	}


  /*
  public int doStartTag()
  throws JspException {

    ItemBean item = (ItemBean) pageContext.findAttribute(name);
    HashMap params = (HashMap) pageContext.findAttribute(parameters);

	Iterator iterator = params.entrySet().iterator();

	StringBuffer javascript = new StringBuffer();

	JspWriter writer = pageContext.getOut();

	try
	{
		javascript.append("<script>\n");
		javascript.append("var element = null;");
		while (iterator.hasNext())
		{
			Entry entry = (Entry) iterator.next();
			String sId = (String) entry.getKey();
			String sProperty = (String) entry.getValue();
			String sValue = item.getString( sProperty);
			if (sValue == null)
			{
				sValue = "";
			}
			javascript.append("element = top.document.");
			javascript.append(getFormName());
			javascript.append(".elements['" + sId + "'];\n");
			javascript.append("if (element != null)\n");
			javascript.append("{\n");
			javascript.append("  element.value = '" + sValue + "';\n");
			javascript.append("}\n");
		}
		javascript.append("</script>\n");

		writer.print(javascript.toString());
	}
	catch (Exception e)
	{
		TagUtils.getInstance().saveException(pageContext, e);
		throw new JspException(e.toString());
	}

	return EVAL_BODY_INCLUDE;
  }
  */
  
  
}
