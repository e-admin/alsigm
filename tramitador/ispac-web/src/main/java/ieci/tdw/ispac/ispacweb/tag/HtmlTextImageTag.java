package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HtmlTextImageTag extends DefaultTextTag {

	private static final long serialVersionUID = 1L;

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages =
		MessageResources.getMessageResources(Constants.Package + ".LocalStrings");


	/*Accion a ejecutar al pulsar sobre la lupa*/
	protected String action = null;
	/*Ancho de la página en la que se mostrará la información tras llamar a la acción*/
	protected String width = "640";
	/*Alto de la página en la que se mostrará la información tras llamar a la acción*/
	protected String height = "480";



	/*Tamaño del input text*/
	protected String size="25";
	/*Longitud máxima del input text*/
	protected String maxLength="";
	protected String inputField = null;
	protected String tabindex=null;

	/*Información para la imagen-enlace*/
	protected String styleClassLink = null;
	protected String titleKeyLink = null;
	protected String titleLink = null;
	protected String image = null;

	/*Indica si necesitamos lanzar el mensaje de confirmación y su texto*/
	protected String msgConfirmKey = null;
	protected String needToConfirm = "false";

	/*Indicamos si el tag estará en modo lectura y la clase de la css */
	protected boolean readonlyTag = false;
	protected String propertyReadonly = "";
	protected String styleClassReadonly = "inputReadOnly";

	/*Inofmración ara la imagen-enlace de borrado*/
	protected String showDelete = "true";
	protected String styleClassDeleteLink = "tdlink";
	protected String imageDelete = "img/borrar.gif";
	protected String confirmDeleteKey = "msg.delete.data.selection";
	protected String jsDelete = null;
	protected String titleKeyImageDelete = "title.delete.data.selection";
	protected String titleImageDelete=null;

	
	protected String styleClass="input";
	/**
	 * Obtiene el orden en la tabulación del input
	 */
	public String getTabindex() {
		return tabindex;
	}
	
	/**
	 * Modifica el orden en la tabulación del input
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}
	
	/**
	 * obtiene el estilo del input
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * Modifica el estilo del input
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	/**
	 * Obtiene la imagen que lanza la acción
	 * @return
	 */
	public String getImage() {
		return image;
	}
	/**
	 * Modifica la imagen que lanza la acción
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * Establece la longitud máxima de input
	 * @param maxLength
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	/**
	 * Obtiene la longitud máxima del tamaño del input
	 * @return maxLength
	 */
	public String getMaxLength() {
		return maxLength;
	}


	/**
	 * Obtiene el tamaño del input
	 */
	public String getSize() {
		return size;
	}
	/**
	 * Modifica el tamaño del input
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Obtiene el mensaje de confirmación
	 * @return  msgConfirmKey.
	 */
	public String getMsgConfirmKey() {
		return msgConfirmKey;
	}
	/**
	 * Modifica el mensaje de confirmación 
	 * @param msgConfirmKey .
	 */
	public void setMsgConfirmKey(String msgConfirmKey) {
		this.msgConfirmKey = msgConfirmKey;
	}

	/**
	 * Devuelve si se necesita realizar la confirmación o no
	 * @return needToConfirm.
	 */
	public String getNeedToConfirm() {
		return needToConfirm;
	}
	/**
	 * Modifica el valor del flag needToConfirm
	 * @param needToConfirm .
	 */
	public void setNeedToConfirm(String needToConfirm) {
		this.needToConfirm = needToConfirm;
	}

	/**
	 * Devuelve la acción a jecutar cuando se pulse sobre el icono-enlace
	 * @return action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * Modifica la acción a ejecutar tras pulsar el incono-enlace
	 * @param action  
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Obtiene la altura de la página que muestra la información tras ejecutar la acción
	 * @return height.
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * Modifica la altura de la página que muestra la información tras ejecutar la acción
	 * @param height 
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * Obtiene el ancho de la página que muestra la información tras ejecutar la acción
	 * @return  width.
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * Modifica el ancho de la página que muestra la información tras ejecutar la acción
	 * @param width.
	 */
	public void setWidth(String width) {
		this.width = width;
	}


	/**
	 * Indica si el tag es de solo lectura
	 * @return readonlyTag.
	 */
	public boolean isReadonlyTag() {
		return readonlyTag;
	}
	/**
	 * Modifica el flag que indica si el tag es de solo lectura o no.
	 * @param readonlyTag .
	 */
	public void setReadonlyTag(boolean readonlyTag) {
		this.readonlyTag = readonlyTag;
	}

	/**
	 * Obtiene el estilo asociado al enlace que lanza la acción
	 * @return styleClassLink.
	 */
	public String getStyleClassLink() {
		return styleClassLink;
	}
	/**
	 * Modifica la clase del enlace que lanza la acción
	 * @param styleClassLink .
	 */
	public void setStyleClassLink(String styleClassLink) {
		this.styleClassLink = styleClassLink;
	}

	/**
	 * Obtiene el titleKey del enlace
	 * @return titleKeyLink.
	 */
	public String getTitleKeyLink() {
		return titleKeyLink;
	}
	/**
	 * Modifica el title key del enlace
	 * @param titleKeyLink.
	 */
	public void setTitleKeyLink(String titleKeyLink) {
		this.titleKeyLink = titleKeyLink;
	}

	/**
	 * Indica si la propiedad esta a read only o no
	 * @return propertyReadonly.
	 */
	public String getPropertyReadonly() {
		return propertyReadonly;
	}
	/**
	 * Modifica el flag que indica si la propiedad esta a read only o no
	 * @param propertyReadonly.
	 */
	public void setPropertyReadonly(String propertyReadonly) {
		this.propertyReadonly = propertyReadonly;
	}
	/**
	 * Obtiene la clase de read only
	 * @return styleClassReadonly.
	 */
	public String getStyleClassReadonly() {
		return styleClassReadonly;
	}
	/**
	 * Modifica la clase del read only
	 * @param styleClassReadonly .
	 */
	public void setStyleClassReadonly(String styleClassReadonly) {
		this.styleClassReadonly = styleClassReadonly;
	}

	/**
	 * Obtiene el título del enlace
	 * @return titleLink.
	 */
	public String getTitleLink() {
		return titleLink;
	}
	/**
	 * Modifica el título del enlace
	 * @param titleLink .
	 */
	public void setTitleLink(String titleLink) {
		this.titleLink = titleLink;
	}

	/**
	 * Indica si se muestra en enlace de borrado o no.
	 * @return  showDelete.
	 */
	public String getShowDelete() {
		return showDelete;
	}
	/**
	 * Modifica el enlace de borrado o no
	 * @param showDelete .
	 */
	public void setShowDelete(String showDelete) {
		this.showDelete = showDelete;
	}

	/**
	 * Obtiene la clase asociada al enlace de borrado
	 * @return styleClassDeleteLink.
	 */
	public String getStyleClassDeleteLink() {
		return styleClassDeleteLink;
	}
	/**
	 * 
	 * @param styleClassDeleteLink The styleClassDeleteLink to set.
	 */
	public void setStyleClassDeleteLink(String styleClassDeleteLink) {
		this.styleClassDeleteLink = styleClassDeleteLink;
	}

	/**
	 * @return Returns the imageDelete.
	 */
	public String getImageDelete() {
		return imageDelete;
	}
	/**
	 * @param imageDelete The imageDelete to set.
	 */
	public void setImageDelete(String imageDelete) {
		this.imageDelete = imageDelete;
	}

	/**
	 * @return Returns the confirmDeleteKey.
	 */
	public String getConfirmDeleteKey() {
		return confirmDeleteKey;
	}
	/**
	 * @param confirmDeleteKey The confirmDeleteKey to set.
	 */
	public void setConfirmDeleteKey(String confirmDeleteKey) {
		this.confirmDeleteKey = confirmDeleteKey;
	}

	/**
	 * @return Returns the jsDelete.
	 */
	public String getJsDelete() {
		return jsDelete;
	}
	/**
	 * @param jsDelete The jsDelete to set.
	 */
	public void setJsDelete(String jsDelete) {
		this.jsDelete = jsDelete;
	}

	/**
	 * @return Returns the titleKeyImageDelete.
	 */
	public String getTitleKeyImageDelete() {
		return titleKeyImageDelete;
	}
	/**
	 * @param titleKeyImageDelete The titleKeyImageDelete to set.
	 */
	public void setTitleKeyImageDelete(String titleKeyImageDelete) {
		this.titleKeyImageDelete = titleKeyImageDelete;
	}

	/**
	 * @return Returns the inputField.
	 */
	public String getInputField() {
		return inputField;
	}
	/**
	 * @param inputField The inputField to set.
	 */
	public void setInputField(String inputField) {
		this.inputField = inputField;
	}

	public int doStartTag() throws JspException {

		// Sólo lectura en el campo de texto
		if (getReadonly()) {
			setStyleClass(getStyleClassReadonly());
		}

		// Sólo lectura en el control
		if (!isReadonlyTag()) {

			// Sólo lectura en el formulario
			String isReadonly = null;
			try {
				isReadonly = (String) TagUtils.getInstance().lookup(pageContext, getName(), getPropertyReadonly(), null);
			}
			catch (JspException e) {
			}

			if ((isReadonly != null) &&
					(isReadonly.equals("true"))) {

				setReadonlyTag(true);

				// Comprobar si el control permite escribir en el campo de texto (no es de sólo lectura)
				if (!getReadonly()) {
					setReadonly(true);
					setStyleClass(getStyleClassReadonly());	
				}
			}
		}
		else if (!getReadonly()) {
			setReadonly(true);
			setStyleClass(getStyleClassReadonly());
		}

		super.doStartTag();

		if (!isReadonlyTag()) {

			// Crea el mapa de parámetros asociado al tag
			pageContext.setAttribute(getId(), new HashMap());
			// Incluye en la petición el campo de entrada
			if (getInputField() != null) {
				
				// Incluye en la petición el nombre del frame
				if (getAction().indexOf("?" ) == -1)	{
					action += "?fieldValue=";
				}
				else {
					action += "&fieldValue=";
				}


			}
			
			
		}

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {

		if (!isReadonlyTag()) {
			
			Map parameters = (HashMap) pageContext.findAttribute(getId());
	
		    if (parameters != null) {
				HttpSession session = pageContext.getSession();
				if (session != null) {
					session.setAttribute(getId(), parameters);
				}
		    }
		}
		    

		return EVAL_PAGE;
	}

	public int doAfterBody() throws JspException {

		if (!isReadonlyTag()) {

			TagUtils tagUtils = TagUtils.getInstance();

			if (!StringUtils.isEmpty(getTitleKeyLink())) {
				setTitleLink(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyLink()));
			}
			
			if (!StringUtils.isEmpty(getTitleKeyImageDelete())) {
				setTitleImageDelete(tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKeyImageDelete()));
			}


			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String context = request.getContextPath();

			String baseurl;
			try {
				baseurl = StaticContext.getInstance().getBaseUrl(context);
			}
			catch (ISPACException ie) {
				pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
				throw new JspException(ie.getMessage());
			}

			// Href que incluya el ispacbase junto con el skin
			String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImage());
			String imageDeleteRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImageDelete());
			StringBuffer imageTag = new StringBuffer();	
			imageTag.append("<span id=\""+id+"\" >")
			.append("<img id=\"imgSearch_"+id+"\" " )
			.append("src=\"")
			.append(imageRealPath+"\"");

			if (!StringUtils.isEmpty(getTitleKeyLink())) {
				imageTag.append(" title=\"" + getTitleLink() + "\"");
			}

			if (!StringUtils.isEmpty(getStyleClassLink())) {

				imageTag.append(" class='");
				imageTag.append(getStyleClassLink());
				imageTag.append("'");
			}
			imageTag.append("/>");

			if("true".equalsIgnoreCase(showDelete)){

				imageTag.append("<img id=\"delete_"+id+"\" ")
				.append("src=\"")
				.append(imageDeleteRealPath+"\"");
				if (!StringUtils.isEmpty(getTitleKeyImageDelete())) {
					imageTag.append(" title=\"" + getTitleImageDelete() + "\"");
				}

				if (!StringUtils.isEmpty(getStyleClassDeleteLink())) {

					imageTag.append(" class='");
					imageTag.append(getStyleClassDeleteLink());
					imageTag.append("'");
				}
				imageTag.append("/>");

			}

			imageTag.append("</span>");


			StringBuffer contenido=new StringBuffer();
			contenido.append("<div id=\"contenido_"+getId()+"\" class=\"contenido move oculto\" width=\""+getWidth()+"\" heigth=\""+getHeight()+"\"></div>");

			String actionWithPath = StaticContext.rewriteAction((request.getRequestURL()).toString(), context, getAction());	
			StringBuffer jQueryCode=new StringBuffer();
			jQueryCode.append("<script>");
			jQueryCode.append("$(document).ready(function() {")
			.append("$(\"#contenido_"+getId()+"\").draggable();")
			.append("$(\"#imgSearch_"+getId()+"\").click(function() {")
			.append("idContenido=\"#contenido_"+getId()+"\";")
			.append("executeSearch_"+getId()+"(document.defaultForm.elements['"+getProperty()+"'].value);});")
			.append("$(\"#delete_"+getId()+"\").click(function() {")
			.append("delete_"+getId()+"();});")
			.append("});");
			
			
			jQueryCode.append("function executeSearch_"+getId()+"(value){");
			if(getMsgConfirmKey()!=null){

				jQueryCode.append("jConfirm('"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getMsgConfirmKey())+"','"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.confirm")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"', function(r){")
				.append("if(r){");
			}

			
			//Incluimos la jsp encargada de la visualizacion
			if(getInputField()!= null){
				jQueryCode.append("$.get('"+actionWithPath+"'+value , includePage);")
				.append("if(value !=''){showLayer();}")	;
			}
			else{
				jQueryCode.append("$.get('"+actionWithPath+"' , includePage);")	
				.append("showLayer();")	;
			}
				
			if(getMsgConfirmKey()!=null){
				jQueryCode.append("}});");
			}
			jQueryCode.append("}");
			//Funcion que oculta/visualiza la información
			jQueryCode.append("function includePage(datos){")
			.append("$(idContenido).append(datos);")
			.append("$(idContenido).removeClass(\"oculto\");")
			.append("$(idContenido).addClass(\"visible\");")
			.append("$(idContenido).css('left', '100px');")
			.append("$(idContenido).css('top', '150px');")
			.append("$(idContenido).css('position' ,'absolute');")
			.append("}");

			//Funcion que oculta la venta con la información del objeto encontrado
			jQueryCode.append("function hideInfo(){")
			.append("$(idContenido).children(\":first-child\").remove();") 
			.append("$(idContenido).removeClass(\"visible\");")
			.append("$(idContenido).addClass(\"oculto\");")
			.append("hideLayer();")
			.append("idContenido=\"\";")
			.append("}");

          

			jQueryCode.append("</script>");


		
			StringBuffer code=new StringBuffer();
			
			code.append(imageTag);
			code.append(contenido);
			code.append(jQueryCode);

			JspWriter writer = pageContext.getOut();

			try	{
				writer.print(code.toString());
			}
			catch (IOException e) {
				tagUtils.saveException(pageContext, e);
				throw new JspException(messages.getMessage("common.io", e.toString()));
			}
		}

		return SKIP_BODY;
	}





	public void release() {

		super.release();

		readonlyTag = false;
	}


	/*
	 * Convierte los parámetros del tag en un documento XML
	 */
	protected String toXml(Map parameters) throws ISPACException {

		String sXml = null;
		String sParameters = "";

		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {

			Entry entry = (Entry) iterator.next();
			String str = XmlTag.newTag("id", (String) entry.getKey());
			str += XmlTag.newTag("property", (String) entry.getValue());
			sParameters += XmlTag.newTag("parameter", str);
		}

		sXml = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("parameters", sParameters);

		return sXml;
	}



	/*
	 * Obtiene el mapa de parámetros del tag analizando
	 * el documento XML de parámetros.
	 */
	public static Map toMap( String sParameters) throws ISPACException {

		HashMap parameters = new HashMap();
		NodeList nodeList = null;
		Node parameter = null;

		Document document = XMLDocUtil.newDocument(sParameters);

		nodeList = XPathUtil.getNodeList(document, "//parameters/parameter");
		if (nodeList != null) {

			for (int i = 0; i < nodeList.getLength(); i++) {

				parameter = nodeList.item(i);

				if ((XPathUtil.existNode( parameter, "id")) && 
						(XPathUtil.existNode( parameter, "property"))) {

					String sId = XPathUtil.get(parameter, "id/text()");
					String sProperty = XPathUtil.get(parameter, "property/text()");
					parameters.put(sId, sProperty);
				}
			}
		}

		return parameters;
	}
	public String getTitleImageDelete() {
		return titleImageDelete;
	}
	public void setTitleImageDelete(String titleImageDelete) {
		this.titleImageDelete = titleImageDelete;
	}




}