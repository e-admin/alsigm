package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Este tag es la clase base que se utiliza como interface entre
 * el formulario activo y la acción que se quiere realizar.
 * Existen dos tipos: imageframe y linkframe.
 *
 * 	id: nombre del actionframe.
 * 	target: nombre del iframe contra el que se ejecuta la acción.
 * 	action: Acción asociada a la ejecución del tag.
 * 	image: Imagen asociada al tag cuando se trata de un imageframe.
 * 	titleKey: Clave del mensaje que se visualiza al situarse sobre la imagen.
 * 	showFrame: true si la acción muestra interface.
 * 	inputField: Nombre de la propiedad del formulario que se utiliza como parámetro
 * 				de entrada.
 * 				Se pasa con el nombre de parámetro 'field' en la url asociada a la
 * 				acción.
 * captionKey: Clave del mensaje que se pasa como título a la acción.
 * 			   El valor del mensaje se pasa con el nombre de parámetro caption en
 *             la url asociada a la acción
 *
 *	Anidado a este tag se define el tag 'parameter' cuya función es asociar una propiedad
 *	del formulario activo con una propiedad del Bean intermedio que se obtiene en la acción.
 *	Se declaran tantos tags como propiedades del formulario se quiera modificar.
 *	name: Nombre del actionframe al que se asocia.
 *	id: Nombre de la propiedad del formulario.
 *	property: Nombre de la propiedad del bean intermedio cuyo valor sustituye
 *            al valor de la propiedad del formulario.
 *
 * 	El conjunto de todos los tags parameter constituye un mapa que se salva como una
 * 	variable de sesión y cuyo nombre se pasa con el nombre de parámetro 'parameters'
 *  en la url asociada a la	acción.
 *
 * 	Para volcar las propiedades del bean intermedio en las propiedades del formulario se
 *  ha definido el tag 'updatefields'.
 *
 * 	name: Nombre del bean intermedio.
 * 	parameters: nombre del mapa que contiene la definición de los parámetros.
 *
 *  Este tag se debe declarar en la página jsp que se ejecuta como respuesta a la acción
 *  del tag actionframe.
 *
 * 	La propiedades id del tag 'actionframe' name del tag 'parameter' y parameters del
 * 	tag 'updatefields' tienen que coincidir.
 *
 * 	Para ocultar el interface una vez que se haya ejecutado la acción hay que utilizar el tag
 * 	'hideframe'.
 *
 * 	event: true por defecto e indica que el código asociado al tag está
 * 	asociado a un evento javascript (onclick='<ispac:hideframe refresh="false"/>').
 *
 * 	Como ejemplo de utilización del tag 'imageframe' podemos fijarnos en la página jsp
 * 	/forms/expedientform.jsp en como se obtiene un sustituto.
 *
 * <code>
 *
 *	<html:hidden property="property(SPAC_EXPEDIENTES:CONCEJO)"/>
 *	<html:text property="property(SPAC_DT_CONCEJOS:SUSTITUTO)"
 *			   styleClass="inputReadOnly"
 *			   readonly="true"
 *			   size="30"/>
 *
 *	<ispac:imageframe id="SPAC_DT_CONCEJOS"
 *					  target="workframe"
 *					  action="selectSubstitute.do?entity=8"
 *					  image="img/search-mg.gif"
 *					  titleKey="select.concejo"
 *					  showFrame="true"
 *					  inputField="property(SPAC_EXPEDIENTES:CONCEJO)"
 *					  captionKey="caption.concejo">
 *		<ispac:parameter name="SPAC_DT_CONCEJOS"
 *						 id="property(SPAC_EXPEDIENTES:CONCEJO)"
 *						 property="VALOR"/>
 *		<ispac:parameter name="SPAC_DT_CONCEJOS"
 *						 id="property(SPAC_DT_CONCEJOS:SUSTITUTO)" property="SUSTITUTO"/>
 *	</ispac:imageframe>
 *
 *	El valor del campo de entrada lo podemos obtener en la acción con la siguiente
 *	instrucción.
 *
 *		String field = request.getParameter("field");
 *
 *	El valor del título lo podemos obtener en la acción con la siguiente instrucción.
 *
 *		String caption = request.getParameter("caption");
 * </code>
 *
 * Como se puede ver en la url asociada a la acción podemos pasar mas parámetros y los
 * podemos recoger en la acción.
 *
 * Para obtener los parámetros del tag parameter en la acción y pasárselos
 * a la página jsp donde se ejecuta el tag updatefields:
 *
 * <code>
 *  // Nombre de la variable de sesión ispac que contiene los parámetros del tag
 *  String parameters = request.getParameter("parameters");
 *  // Obtiene el documento XML que describe la structura de parámetros
 *	String sParameters = session.getClientContext().getSsVariable( parameters);
 *	if (sParameters != null)
 *	{
 *		// Convierte el documento XML en un mapa y lo salva en la petición
 *		request.setAttribute(parameters, ActionFrameTag.toMap( sParameters));
 *	}
 * <code>
  */
public class ActionFrameTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(ActionFrameTag.class);
	
  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
  MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

  /**
   * Nombre del frame
   */
  protected String target = "workframe";
  protected String action = null;
  protected String width = "640";
  protected String height = "480";
  protected String titleKey = null;
  protected String showFrame = "true";
  protected String inputField = null;
  protected String styleClass = null;
  protected String captionKey = null;
  
  protected String msgConfirmKey = null;
  protected String needToConfirm = "false";
  
  protected String visible = null;
  protected boolean readonlyTag = false;
  protected String propertyReadonly = "readonly";

  protected String jsShowFrame = "showFrame";
  protected String jsExecuteFrame = "executeFrame";
  
  /**
   * Nombre del formulario en el que está la propiedad de sólo lectura
   */
  protected String formName = "defaultForm";
  
  protected String doSubmit = "true";

  public ActionFrameTag()
  {
      super();
      setId("workframe");
  }
  
  public String getTarget() {
    return this.target;
  }
  public void setTarget( String target) {
    this.target = target;
  }

  public String getAction() {
    return this.action;
  }
  public void setAction( String action) {
    this.action = action;
  }

  public String getWidth() {
    return this.width;
  }
  public void setWidth( String width) {
    this.width = width;
  }

  public String getHeight() {
    return this.height;
  }
  public void setHeight( String height) {
    this.height = height;
  }

  public String getTitleKey() {
    return this.titleKey;
  }
  public void setTitleKey( String titleKey) {
    this.titleKey = titleKey;
  }

  public String getShowFrame() {
    return this.showFrame;
  }
  public void setShowFrame( String showFrame) {
    this.showFrame = showFrame;
  }

  public String getInputField() {
    return this.inputField;
  }
  public void setInputField( String inputField) {
    this.inputField = inputField;
  }

  public String getStyleClass() {
    return this.styleClass;
  }
  public void setStyleClass( String styleClass) {
    this.styleClass = styleClass;
  }

  public String getCaptionKey() {
    return this.captionKey;
  }
  public void setCaptionKey( String captionKey) {
    this.captionKey = captionKey;
  }

  public String getDoSubmit() {
	return doSubmit;
  }
  public void setDoSubmit(String doSubmit) {
	this.doSubmit = doSubmit;
  }
  
  public String getPropertyReadonly() {
	return propertyReadonly;
  }
  public void setPropertyReadonly(String propertyReadonly) {
	this.propertyReadonly = propertyReadonly;
  }
  
  public String getVisible() {
	return visible;
  }
  public void setVisible(String visible) {
	this.visible = visible;
  }
  
  public boolean isReadonlyTag() {
	return readonlyTag;
  }
  public void setReadonlyTag(boolean readonlyTag) {
	this.readonlyTag = readonlyTag;
  }

  public String getFormName() {
	return formName;
  }
  public void setFormName(String formName) {
	this.formName = formName;
  }
  
  /**
	 * @return Returns the jsShowFrame.
	 */
	public String getJsShowFrame() {
		return jsShowFrame;
	}
	/**
	 * @param jsShowFrame The jsShowFrame to set.
	 */
	public void setJsShowFrame(String jsShowFrame) {
		this.jsShowFrame = jsShowFrame;
	}
	
	/**
	 * @return Returns the jsExecuteFrame.
	 */
	public String getJsExecuteFrame() {
		return jsExecuteFrame;
	}
	/**
	 * @param jsExecuteFrame The jsExecuteFrame to set.
	 */
	public void setJsExecuteFrame(String jsExecuteFrame) {
		this.jsExecuteFrame = jsExecuteFrame;
	}

public int doStartTag() throws JspException {
	
	// Siempre visible
	if ((getVisible() == null) ||
		(!getVisible().equals("true"))) {
	
		// Sólo lectura en el formulario
		String isReadonly = null;
		try {
			isReadonly = (String) TagUtils.getInstance().lookup(pageContext, getFormName(), getPropertyReadonly(), null);
		}
		catch (JspException e) {
		}
	
		if ((isReadonly != null) &&
			(isReadonly.equals("true"))) {
			
			setReadonlyTag(true);
			
			return SKIP_BODY;
			
		}
	}

    // Crea el mapa de parámetros asociado al tag
	pageContext.setAttribute(getId(), new HashMap());

	// Incluye en la petición el nombre del frame
	if (getAction().indexOf("?" ) == -1)	{
		action += "?parameters=" + getId();
	}
	else {
		action += "&parameters=" + getId();
	}

	// Incluye en la petición el campo de entrada
	if (getInputField() != null) {
		action += "&field=" + getInputField();
	}

	if (getCaptionKey() != null) {
		action += "&caption=" + getCaptionKey();
	}
	
 	return EVAL_BODY_INCLUDE;
  }

  public int doEndTag() throws JspException {
	  
	  if (!isReadonlyTag()) {

	    HashMap parameters = (HashMap) pageContext.findAttribute(getId());
	
	    if (parameters != null) {
	    	
	    	/*
	    	SessionAPI sessionAPI = getSession();
	    	ClientContext client = sessionAPI.getClientContext();
	
	    	try {
	    		// Salva los parámetros del tag en una variable
	    		// de sesión.
	    		client.setSsVariable(getId(), toXml(parameters));
			}
	    	catch (Exception e) {
	    		TagUtils.getInstance().saveException(pageContext, e);
	    		throw new JspException(e.toString());
	    	}
	    	*/
	
			HttpSession session = pageContext.getSession();
			if (session != null) {
				session.setAttribute(getId(), parameters);
			}
	    }
	  }
	  
	  return EVAL_PAGE;
  }

  public void release() {

    super.release();
    id = null;
    action = null;
    width = null;
    height = null;
    titleKey = null;
    showFrame = null;
    inputField = null;
  }

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

//  private String getTicket() {
//	  
//  	String ticket = null;
//	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
//	Cookie[] cookies = request.getCookies();
//	
//	for (int i = 0; i < cookies.length; i++) {
//		
//		if (cookies[i].getName().equals("user")) {
//			ticket = cookies[i].getValue();
//			break;
//		}
//	}
//	
//	return ticket;
//  }

  /**
   * Convierte los parámetros del tag en un documento XML
   */
  protected String toXml(Map parameters) throws ISPACException {
	  
	String sXml = null;
	String sParameters = "";

	Iterator iterator = parameters.entrySet().iterator();
	while (iterator.hasNext()) {
		
		String str;
		Entry entry = (Entry) iterator.next();
		str = XmlTag.newTag("id", (String) entry.getKey());
		str += XmlTag.newTag("property", (String) entry.getValue());
		sParameters += XmlTag.newTag("parameter", str);
	}

	sXml = XmlTag.getXmlInstruction("ISO-8859-1")
		 + XmlTag.newTag("parameters", sParameters);

	return sXml;
  }

  /**
   * Obtiene el mapa de parámetros del tag analizando
   * el documento XML de parámetros.
   */
  public static Map toMap(String sParameters) throws ISPACException {
	  
  	HashMap parameters = new HashMap();
	NodeList nodeList = null;
	Node parameter = null;

	Document document = XMLDocUtil.newDocument( sParameters);
	nodeList = XPathUtil.getNodeList( document, "//parameters/parameter");
	if (nodeList != null) {
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			parameter = nodeList.item( i);

			if (  XPathUtil.existNode( parameter, "id")
			   && XPathUtil.existNode( parameter, "property")) {
				
				String sId = XPathUtil.get( parameter, "id/text()");
				String sProperty = XPathUtil.get( parameter, "property/text()");
				parameters.put( sId, sProperty);
			}
		}
	}

  	return parameters;
  }
  
	/**
	 * @return Returns the msgConfirmKey.
	 */
	public String getMsgConfirmKey() {
	    return msgConfirmKey;
	}
	/**
	 * @param msgConfirmKey The msgConfirmKey to set.
	 */
	public void setMsgConfirmKey(String msgConfirmKey) {
	    this.msgConfirmKey = msgConfirmKey;
	}
	
	/**
	 * @return Returns the needToConfirm.
	 */
	public String getNeedToConfirm() {
		return needToConfirm;
	}
	/**
	 * @param needToConfirm The needToConfirm to set.
	 */
	public void setNeedToConfirm(String needToConfirm) {
		this.needToConfirm = needToConfirm;
	}
	
	protected String generateShowExecuteFrameJS(TagUtils tagUtils,
												Boolean show) throws JspException {
		
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();       		
		String actionWithPath = StaticContext.rewriteAction((request.getRequestURL()).toString(), request.getContextPath(), getAction());
		
		StringBuffer html = new StringBuffer();
		
	 	if (show.booleanValue()) {
	 		
	 		html.append(getJsShowFrame() +"(")
	 			.append("'" + getTarget() +"',")
	 			//.append("'" + actionWithPath + "'");
	 			.append("'" + actionWithPath + "',")
	 			.append(getWidth() + ",")
	 			.append(getHeight());
	 			

	 		html.append(",'" + ((!StringUtils.isEmpty(getMsgConfirmKey())) ? tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey()) : "") + "'")
				.append("," + getDoSubmit())
				.append(");");
	 	}
	 	else {
	 		html.append(getJsExecuteFrame() + "(")
	 			.append("'" + getTarget() +"',")
	 			.append("'" + actionWithPath + "'");
	 		
	 		if(!StringUtils.isEmpty(getMsgConfirmKey())){
	 			html.append(",'"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey())+",'"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"'");
	 		}
	 			
	 		html.append(");");		
	 	}

	 	
//	 	Boolean show = new Boolean(getShowFrame());
//	 	if (show.booleanValue()) {
//	 		
//		 	imageTag.append(getJsShowFrame() + "('" + getTarget() + "',")
//		 			.append("'" + actionWithPath + "',")
//		 			.append(getWidth() +",")
//		 			.append(getHeight())
//		 			.append(",'" + ((!StringUtils.isEmpty(getMsgConfirmKey())) ? tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey()) : "") + "'")
//		 			.append(");");
//	 	}
//	 	else {
//		 	imageTag.append(getJsExecuteFrame() + "('" + getTarget() + "',")
//					.append("'" + actionWithPath + "',")
//					.append("'" + ((!StringUtils.isEmpty(getMsgConfirmKey())) ? tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey()) : "") + "'")
//					.append(");");
//	 	}
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	return html.toString();
	}
	
}