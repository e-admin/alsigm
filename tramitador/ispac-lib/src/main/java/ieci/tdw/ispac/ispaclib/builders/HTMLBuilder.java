package ieci.tdw.ispac.ispaclib.builders;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;

public class HTMLBuilder {
	
	public static final char QUOTE = '"';
	
	/**
	 * Genera el código HTML del diseño del formulario JSP.
	 * 
	 * @param jspCode Formulario JSP.
	 * @param entities Lista de entidades que se visualizan en el formulario.
	 * @param resources Recursos para las etiquetas.
	 * @return Código HTML del diseño del formulario.
	 * @throws ISPACException
	 */
	public static String generateHTML(String jspCode, List entities, Map resources) throws ISPACException {
		
		JSPBuilderConfiguration jspBuilderConfiguration = JSPBuilderConfiguration.getInstance();
		
		StringBuffer htmlCode = new StringBuffer();
		
		// Cabecera con los estilos
		htmlCode.append("<html>\r\n")
				.append("<head>\r\n")
				.append("<style type=\"text/css\">\r\n")
				.append(".")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT))
				.append("{")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_INPUT_CSS))
				.append("}\r\n")
				.append(".")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY))
				.append("{")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_READONLY_CSS))
				.append("}\r\n")
				.append(".select {")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_SELECT_CSS))
				.append("}\r\n")
				.append(".dataBlock {")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_DATABLOCK_CSS))
				.append("}\r\n")
				.append(".")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_LABEL))
				.append("{")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_LABEL_CSS))
				.append("}\r\n")
				.append("body {")
				.append(jspBuilderConfiguration.get(JSPBuilderConfiguration.STYLE_CLASS_BODY_CSS))
				.append("}\r\n")
				.append("</style>\r\n")
				.append("</head>\r\n")
				.append("<body>\r\n");
		
		try {
			
			Parser parser = new Parser(jspCode);
			// Parsear todo el jsp
			NodeList nodeListJsp = parser.parse(null);
			
			Iterator ite = entities.iterator();
 			while (ite.hasNext()) {
 				
 				String entityName = (String) ite.next();
			
				// Obtener los div con el bloque de datos de la entidad
				NodeList nodeListDataBlocks = nodeListJsp.extractAllNodesThatMatch(new EqualsAttributeValue("id", "dataBlock_" + entityName), true);
				for (int i = 0; i < nodeListDataBlocks.size(); i ++) {
				
					Div divDataBlockEntity = (Div) nodeListDataBlocks.elementAt(i);
					// Borde para el bloque de datos
					divDataBlockEntity.setAttribute("class", "dataBlock", QUOTE);
					// Obtener el contenido del div
					NodeList nodeListDivDataBlockEntity = divDataBlockEntity.getChildren();
					
					// Obtener los divs del contenido
					NodeList nodeList = nodeListDivDataBlockEntity.extractAllNodesThatMatch(new TagNameFilter("div"));
					
					for (NodeIterator it = nodeList.elements (); it.hasMoreNodes(); ) {
						
						Node node = it.nextNode();
						
						if (node instanceof Div) {
		
							TagNode divNode = (Div) node;
							
		       	         	String id = divNode.getAttribute("id");
		       	         	if (!StringUtils.isEmpty(id)) {
		       	         
			       	         	if (id.startsWith("label_")) {
			       	         		
			       	         		// Html de la etiqueta en html a partir del jsp
			       	         		divNode.setChildren(processDivLabel(divNode.getChildren(), (String) resources.get(id.substring(6, id.length()))));
			       	         	}
			       	         	else if (id.startsWith("data_")) {
			       	         		
			       	         		// Html del control de dato a partir del jsp
			       	         		String key = id.substring(5, id.length());
			       	         		String value = (String) resources.get(key);
			        				if (StringUtils.isEmpty(value)) {
			        					value = key;
			        					int index = value.indexOf(":");
			        					if (index != -1) {
			        						value = value.substring(index + 1, value.length()).replaceAll("_", " ");
			        					}
			        				}
			       	         		divNode.setChildren(processDivData(id, divNode.getChildren(), value));
			       	         	}
			       	         	else if (id.startsWith("form_")) {
			       	         		
			       	         		divNode.setChildren(processDivForm(divNode.getChildren(), resources));
			       	         	}
		       	         	}
		       	     	}
					}
					
					String idDataBlock = divDataBlockEntity.getAttribute("id");
					
					String tabLabel = (String) resources.get(entityName + ":" + idDataBlock.substring(10, idDataBlock.length()));
					if (StringUtils.isEmpty(tabLabel)) {
						tabLabel = "?";
					}
					htmlCode.append("<table border=\"0px\" cellpadding=\"0px\" cellspacing=\"0px\"><tr><td class=\"select\" align=\"center\">")
							.append(tabLabel)
							.append("</td></tr></table>\r\n");
					
					//htmlCode.append(nodeListDivDataBlockEntity.toHtml());
					htmlCode.append(divDataBlockEntity.toHtml())
							.append("<br/>");
				}
 			}
		}
		catch (ISPACException ie) {
			throw ie;
		}
		catch (Exception e) {
			htmlCode.append(e.getMessage());
		}
		
		htmlCode.append("</body>\r\n")
				.append("</html>\r\n");
		
		return htmlCode.toString();
	}
		
	/**
	 * Actualiza el formulario JSP a partir del código HTML del diseño del formulario.
	 * 
	 * @param htmlCode Código HTML del diseño del formulario.
	 * @param jspCode Formulario JSP.
	 * @param entities Lista de entidades que se visualizan en el formulario.
	 * @return Código del formulario JSP.
	 * @throws ISPACException
	 */
	public static String updateHTML(String htmlCode, String jspCode, List entities) throws ISPACException {
		
		String id = null;
		
		try {
			
			Parser parserHtml = new Parser(htmlCode);
			// Parsear todo el html
			NodeList nodeListHtml = parserHtml.parse(null);
			
			Parser parserJsp = new Parser(jspCode);
			// Parsear todo el jsp
			NodeList nodeListJsp = parserJsp.parse(null);
			
			// Obtener los estilos CSS
			String styleCss = getStyleCss(nodeListHtml);
			
			Iterator ite = entities.iterator();
 			while (ite.hasNext()) {
 				
 				String entityName = (String) ite.next();
				
				// Obtener los bloques de datos de las entidades
				Map hashHtmlDivDataBlocks = new HashMap();
				NodeList nodeListHtmlDataBlock = nodeListHtml.extractAllNodesThatMatch(new EqualsAttributeValue("id", "dataBlock_" + entityName), true);
				for (int i = 0; i < nodeListHtmlDataBlock.size(); i ++) {
					
					Div htmlDivDataBlock = (Div) nodeListHtmlDataBlock.elementAt(i);				
					hashHtmlDivDataBlocks.put(htmlDivDataBlock.getAttribute("id"), htmlDivDataBlock);
				}
				
				// Obtener los div con el bloque de datos de la entidad
				NodeList nodeListJspDataBlocks = nodeListJsp.extractAllNodesThatMatch(new EqualsAttributeValue("id", "dataBlock_" + entityName), true);
				for (int i = 0; i < nodeListJspDataBlocks.size(); i ++) {
				
					Div divJspDataBlock = (Div) nodeListJspDataBlocks.elementAt(i);				
					String idDataBlock = divJspDataBlock.getAttribute("id");
					
					// Obtener el HTML
					Div htmlDivDataBlock = (Div) hashHtmlDivDataBlocks.get(idDataBlock);
					if (htmlDivDataBlock != null) {
						
						// Obtener el JSP con los controles
						NodeList nodeListDivJspDataBlock = divJspDataBlock.getChildren();
					
						// Procesar los divs del HTML
						NodeList nodeListHtmlDivDataBlock = htmlDivDataBlock.getChildren();
						for (NodeIterator it = nodeListHtmlDivDataBlock.elements(); it.hasMoreNodes();) {
							
							Node node = it.nextNode();
							if (node instanceof Div) {
			
				    	         Div divNode = (Div) node;
				    	         
				    	         id = divNode.getAttribute("id");
				    	         if (!StringUtils.isEmpty(id)) {
				    	         
				    	        	 // Incluir los controles JSP en los divs HTML
					    	         if (id.startsWith("label_")) {
					    	        	 
					    	        	 // Obtener el div de la etiqueta en el jsp
					    	        	 NodeList nodeList = nodeListDivJspDataBlock.extractAllNodesThatMatch(new HasAttributeFilter("id", id), true);
					    	        	 Node divLabelJsp = nodeList.elementAt(0);
					    	        	 
					    	        	 // Establecer en el html el jsp de la etiqueta
					    	        	 divNode.setChildren(divLabelJsp.getChildren());
					    	         }
					    	         else if (id.startsWith("data_")) {
					    	        	 
					    	        	 // Obtener el div de dato en el jsp
					    	        	 NodeList nodeList = nodeListDivJspDataBlock.extractAllNodesThatMatch(new HasAttributeFilter("id", id), true);
					    	        	 Node divDataJsp = nodeList.elementAt(0);
					    	        	 
					    	        	 // Establecer en el html el control jsp con sus atributos actualizados
					    	        	 updateDivData(divDataJsp.getChildren(), divNode);		    	        	 
					    	         }
					    	         else if (id.startsWith("form_")) {
					    	        	 
					    	        	 // Obtener el div de la etiqueta en el jsp
					    	        	 NodeList nodeList = nodeListDivJspDataBlock.extractAllNodesThatMatch(new HasAttributeFilter("id", id), true);
					    	        	 Node divFormJsp = nodeList.elementAt(0);
					    	        	 
					    	        	 updateDivForm(divFormJsp.getChildren(), divNode, styleCss);
					    	         }
					    	         
					    	         // Obtener el estilo CSS para añadirlo al atributo style del div
					    	         setStyleFromStyleCss(divNode, id, styleCss);
				    	         }
				    	     }
						}
						
						// Establecer el diseño html que incluye los tags jsp
						divJspDataBlock.setChildren(nodeListHtmlDivDataBlock);
						
						// Actualizar el estilo del bloque de datos de la entidad
						String style = htmlDivDataBlock.getAttribute("style");
						divJspDataBlock.setAttribute("style", "position: relative; height: " + getStyleValue(style, "height") + "; width: " + getStyleValue(style, "width") + ";");
					}
				}
 			}
			
			return nodeListJsp.toHtml();
		}
		catch (Exception e) {
			
			if (StringUtils.isEmpty(id)) {
				throw new ISPACInfo("exception.entities.form.update.design", e);
			}
			else {
				throw new ISPACInfo("exception.entities.form.update.design.div", new String[] {id});
			}
		}
	}
	
	/**
	 * Actualiza el código JSP para el div de datos a partir del código HTML.
	 * 
	 * @param nodeList Lista de nodos del div de datos JSP.
	 * @param divNode Div con el código HTML a actualizar.
	 */
	public static void updateDivData(NodeList nodeList, Div divNode) {
		
		// Varios controles en el mismo div
		int iInput = 0;
		int iTextarea = 0;
		
		NodeList nodeListDivHtml = divNode.getChildren();
		
		if (nodeListDivHtml != null) {
		
	    	for (int i = 0; i < nodeList.size(); i++) {
	    		
	    		Node node = nodeList.elementAt(i);
	    		
	    		if (node instanceof TagNode) {
	    			
	    			TagNode tagNode = (TagNode) node;
	    			
	    			if (!tagNode.isEndTag()) {
	    			
	    				TagNode tagNodeHtml = null;
	    				
		    			if ((tagNode.getTagName().equals("ISPAC:HTMLTEXT")) ||
		    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTCALENDAR")) ||
		    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTIMAGEFRAME")) ||
		    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUE")) ||
		    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUECALENDAR")) ||
		    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUEIMAGEFRAME"))
		    				) {
		    				
		    				// Obtener el input de html para actualizar el atributo size en el tag jsp
		    				tagNodeHtml = (TagNode) nodeListDivHtml.extractAllNodesThatMatch(new TagNameFilter("input")).elementAt(iInput++);
		    				tagNode.setAttribute("size", tagNodeHtml.getAttribute("size"), QUOTE);
		    			}
		    			else if ((tagNode.getTagName().equals("ISPAC:HTMLTEXTAREA")) ||
		    					 (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAIMAGEFRAME")) ||
		    					 (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUE")) ||
		    					 (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUEIMAGEFRAME"))		    					 
		    					) {
		    				
		    				// Obtener el textarea de html para actualizar los atributos cols y rows en el tag jsp
		    				tagNodeHtml = (TagNode) nodeListDivHtml.extractAllNodesThatMatch(new TagNameFilter("textarea")).elementAt(iTextarea++);
		    				tagNode.setAttribute("cols", tagNodeHtml.getAttribute("cols"), QUOTE);
		    				tagNode.setAttribute("rows", tagNodeHtml.getAttribute("rows"), QUOTE);
		    			}
		    			else if (tagNode.getTagName().equals("ISPAC:HTMLCHECKBOX")) {
		    				
		    				// Obtener el input checkbox de html
		    				tagNodeHtml = (TagNode) nodeListDivHtml.extractAllNodesThatMatch(new TagNameFilter("input")).elementAt(iInput++);
		    			}
		    			
	    				if (tagNodeHtml != null) {
	    							    				
		    				// Sólo lectura
	    					String readonly = tagNodeHtml.getAttribute("readonly");
	    					if ((readonly != null) && 
	    						((readonly.equals("readonly")) ||
	    						 (readonly.equals("true")))) {
	    						
	    						if ((tagNode.getTagName().equals("ISPAC:HTMLTEXTIMAGEFRAME")) ||
	    							(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAIMAGEFRAME")) ||
	    							(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUEIMAGEFRAME")) ||
	    							(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUEIMAGEFRAME"))	    							
	    							
	    						   ) {
	    						
	    							tagNode.setAttribute("readonlyTag", "true", QUOTE);
	    						}
	    						else {
	    							tagNode.setAttribute("readonly", "true", QUOTE);
	    						}
	    					}
	    					else {
	    						if ((tagNode.getTagName().equals("ISPAC:HTMLTEXTIMAGEFRAME")) ||
		    						(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAIMAGEFRAME")) ||
	    							(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUEIMAGEFRAME")) ||
	    							(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUEIMAGEFRAME"))	    							
	    						) {
	        						
	    							tagNode.setAttribute("readonlyTag", "false", QUOTE);
	    						}
	    						else {
	    							tagNode.setAttribute("readonly", "false", QUOTE);
	    						}
	    					}
	    					
		    				// Establecer el tabindex
		    				setTabindex(tagNode, tagNodeHtml);
	    				}
	    			}
	    		}
	    	}
    	
	    	divNode.setChildren(nodeList);
		}
		else {
			divNode.setChildren(null);
		}
	}
	
	/**
	 * Actualiza el código JSP para el div de formulario a partir del código HTML.
	 * 
	 * @param nodeListDivJspForm Lista de nodos del div de formulario JSP.
	 * @param divNodeHtmlForm Div con el código HTML a actualizar.
	 * @param styleCss Estilos CSS.
	 */
	public static void updateDivForm(NodeList nodeListDivJspForm, 
									 Div divNodeHtmlForm,
									 String styleCss) throws Exception {
			
		// Procesar los divs del HTML
		NodeList nodeListHtmlDiv = divNodeHtmlForm.getChildren();
		for (NodeIterator it = nodeListHtmlDiv.elements(); it.hasMoreNodes();) {
			
			Node node = it.nextNode();
			if (node instanceof Div) {

    	         Div divNode = (Div) node;
    	         
    	         String id = divNode.getAttribute("id");
    	         if (!StringUtils.isEmpty(id)) {
    	         
    	        	 // Incluir los controles JSP en los divs HTML
	    	         if (id.startsWith("label_")) {
	    	        	 
	    	        	 // Obtener el div de la etiqueta en el jsp
	    	        	 NodeList nodeList = nodeListDivJspForm.extractAllNodesThatMatch(new HasAttributeFilter("id", id), true);
	    	        	 Node divLabelJsp = nodeList.elementAt(0);
	    	        	 
	    	        	 // Establecer en el html el jsp de la etiqueta
	    	        	 divNode.setChildren(divLabelJsp.getChildren());
	    	         }
	    	         else if (id.startsWith("data_")) {
	    	        	 
	    	        	 // Obtener el div de dato en el jsp
	    	        	 NodeList nodeList = nodeListDivJspForm.extractAllNodesThatMatch(new HasAttributeFilter("id", id), true);
	    	        	 Node divDataJsp = nodeList.elementAt(0);
	    	        	 
	    	        	 // Establecer en el html el control jsp con sus atributos actualizados
	    	        	 updateDivData(divDataJsp.getChildren(), divNode);		    	        	 
	    	         }
	    	         
	    	         // Obtener el estilo CSS para añadirlo al atributo style del div
	    	         setStyleFromStyleCss(divNode, id, styleCss);
    	         }
    	     }
		}
	}
	
	/**
	 * Establece el tabindex del control HTML en el tabindex del control JSP.
	 * Si no existe el tabindex se elimina la propiedad del control JSP.
	 * 
	 * @param tagNode Control en el JSP.
	 * @param tagNodeHtml Control en el HTML.
	 */
	protected static void setTabindex(TagNode tagNode, TagNode tagNodeHtml) {
		
		String tabindex = tagNodeHtml.getAttribute("tabindex");
		if (StringUtils.isNotBlank(tabindex)) {
			
			try {
				int iTabindex = TypeConverter.parseInt(tabindex);
				tagNode.setAttribute("tabindex", String.valueOf(iTabindex), QUOTE);
				return;
			}
			catch (ISPACException e) {}
		}
		
		// Eliminar la propiedad
		tagNode.removeAttribute("tabindex");
	}
	
	/**
	 * Genera el código HTML para el div de etiqueta de un control JSP.
	 * 
	 * @param nodeList Lista de nodos del div de datos JSP.
	 * @param label Etiqueta a mostrar
	 * @return Lista de nodos HTML para el div de etiqueta.
	 */
	public static NodeList processDivLabel(NodeList nodeList, String label) {
		
		NodeList nodeListAux = new NodeList();
		
    	for (int i = 0; i < nodeList.size(); i++) {
    		
    		Node node = nodeList.elementAt(i);
    		
    		if (node instanceof TagNode) {
    			
    			TagNode tagNode = (TagNode) node;
    			
    			if (tagNode.getTagName().equals("BEAN:WRITE")) {
    				
    				if (StringUtils.isEmpty(label)) {
    					label = "?";
    				}
    				
    				// Convertir a texto
    				TextNode textNode = new TextNode(label);
    				nodeListAux.add(textNode);
    			}
    			else {
    				nodeListAux.add(tagNode);
    			}
    		}
    		else {
    			nodeListAux.add(node);
    		}
    	}

		return nodeListAux;
	}
	
	/**
	 * Genera el código HTML para el div de datos que contiene los controles JSP.
	 * 
	 * @param id Identificador del div de datos
	 * @param nodeList Lista de nodos del div de datos JSP.
	 * @param value Valor a mostrar en el control HTML.
	 * @return Lista de nodos HTML para el div de datos.
	 */
	public static NodeList processDivData(String id, NodeList nodeList, String value) throws ISPACException {
		
		NodeList nodeListAux = new NodeList();
		
    	for (int i = 0; i < nodeList.size(); i++) {
    		
    		Node node = nodeList.elementAt(i);
    		
    		if (node instanceof TagNode) {
    			
    			TagNode tagNode = (TagNode) node;
    			
    			if (!tagNode.isEndTag()) {
    			
    				TagNode tagNodeHtml = null;
    				
	    			if ((tagNode.getTagName().equals("ISPAC:HTMLTEXT")) ||
		    			(tagNode.getTagName().equals("ISPAC:HTMLTEXTCALENDAR")) ||
		    			(tagNode.getTagName().equals("ISPAC:HTMLTEXTIMAGEFRAME")) ||
	    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUE")) ||
	    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUECALENDAR")) ||
	    				(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUEIMAGEFRAME"))
	    			   ) {
		    				
	    				// Convertir a input de html
    					tagNodeHtml = new InputTag();
    					tagNodeHtml.setAttribute("name", tagNode.getAttribute("property"), QUOTE);
    					tagNodeHtml.setAttribute("size", tagNode.getAttribute("size"), QUOTE);
	    				tagNodeHtml.setAttribute("value", value, QUOTE);
	    			}
	    			else if ((tagNode.getTagName().equals("ISPAC:HTMLTEXTAREA")) ||
	    					 (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAIMAGEFRAME")) ||
	    					 (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUE")) ||
	    					 (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUEIMAGEFRAME"))		    					 
	    			        ) {
	    				
	    				// Convertir a textarea de html
	    				tagNodeHtml = new TextareaTag();
	    				tagNodeHtml.setAttribute("name", tagNode.getAttribute("property"), QUOTE);
	    				tagNodeHtml.setAttribute("cols", tagNode.getAttribute("cols"), QUOTE);
	    				tagNodeHtml.setAttribute("rows", tagNode.getAttribute("rows"), QUOTE);
	    				tagNodeHtml.setChildren(new NodeList(new TextNode(value)));
	    				TagNode textareaEnd = new TagNode();
	    				textareaEnd.setTagName("/textarea");
	    				tagNodeHtml.setEndTag(textareaEnd);
	    			}
	    			else if (tagNode.getTagName().equals("ISPAC:HTMLCHECKBOX")) {
	    				
	    				// Convertir a checkbox de html
    					tagNodeHtml = new InputTag();
    					tagNodeHtml.setAttribute("name", tagNode.getAttribute("property"), QUOTE);
    					tagNodeHtml.setAttribute("type", "checkbox");
	    			}
	    			
    				if (tagNodeHtml != null) {
    					    					
    					// Sólo lectura
    					if ((tagNode.getTagName().equals("ISPAC:HTMLTEXTIMAGEFRAME")) ||
    						(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAIMAGEFRAME")) ||
							(tagNode.getTagName().equals("ISPAC:HTMLTEXTMULTIVALUEIMAGEFRAME")) ||
							(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUEIMAGEFRAME"))	    							
    					) {
    						
    						String readonlyTag = tagNode.getAttribute("readonlyTag");
    						if (readonlyTag.equals("true")) {
    							
    	    					if (tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAIMAGEFRAME")) {
    	    						tagNodeHtml.setAttribute("readonly", "readonly", QUOTE);
    	    					}
    	    					else {
    	    						tagNodeHtml.setAttribute("readonly", "true", QUOTE);
    	    					}
    							tagNodeHtml.setAttribute("class", tagNode.getAttribute("styleClassReadonly"), QUOTE);
    						}
    						else {
    							tagNodeHtml.setAttribute("class", tagNode.getAttribute("styleClass"), QUOTE);
    						}
    					}
    					else {
    						String readonly = tagNode.getAttribute("readonly");
    						if (readonly.equals("true")) {
    							
    							if ( tagNode.getTagName().equals("ISPAC:HTMLTEXTAREA")||
 			    					(tagNode.getTagName().equals("ISPAC:HTMLTEXTAREAMULTIVALUE"))
    							   ) {
    								tagNodeHtml.setAttribute("readonly", "readonly", QUOTE);
    							}
    							else {
    								tagNodeHtml.setAttribute("readonly", "true", QUOTE);
    							}
    							tagNodeHtml.setAttribute("class", tagNode.getAttribute("styleClassReadonly"), QUOTE);
    						}
    						else {
    							tagNodeHtml.setAttribute("class", tagNode.getAttribute("styleClass"), QUOTE);
    						}
    					}
    					
    					// Establecer el tabindex
    					String tabindex = tagNode.getAttribute("tabindex");
    					if (StringUtils.isNotBlank(tabindex)) {
    						tagNodeHtml.setAttribute("tabindex", tabindex, QUOTE);
    					}
    					
    					nodeListAux.add(tagNodeHtml);
    				}
    			}
    		}
    		/*
    		else {
    			nodeListAux.add(node);
    		}
    		*/
    	}
    	    	
    	// Si no se genera ningún tag html
    	// se imprime el valor para que se vea el div
    	if (nodeListAux.size() == 0) {
    		
			TextNode textNode = new TextNode(value);
			nodeListAux.add(textNode);
			
			//throw new ISPACInfo("exception.entities.form.download.design.divData", new String[] {value, id});
    	}

		return nodeListAux;
	}
	
	/**
	 * Genera el código HTML para el div de formulario que especifica un zona del formulario
	 * que contiene los divs de etiquetas y de datos específicos de las entidades.
	 * 
	 * @param nodeListDivForm Lista de nodos del div de formulario JSP.
	 * @param resources Recursos para las etiquetas.
	 * @return Lista de nodos HTML para el div de formulario.
	 * @throws Exception
	 */
	private static NodeList processDivForm(NodeList nodeListDivForm,
	 		   							   Map resources) throws Exception {

		// Obtener los divs del contenido
		NodeList nodeList = nodeListDivForm.extractAllNodesThatMatch(new TagNameFilter("div"));
		
		for (NodeIterator it = nodeList.elements (); it.hasMoreNodes(); ) {
		
			Node node = it.nextNode();
		
			if (node instanceof Div) {
		
				TagNode divNode = (Div) node;
				
				String id = divNode.getAttribute("id");
				if (!StringUtils.isEmpty(id)) {
				
					if (id.startsWith("label_")) {
					
						// Html de la etiqueta en html a partir del jsp
						divNode.setChildren(processDivLabel(divNode.getChildren(), (String) resources.get(id.substring(6, id.length()))));
					}
					else if (id.startsWith("data_")) {
					
						// Html del control de dato a partir del jsp
						String key = id.substring(5, id.length());
						String value = (String) resources.get(key);
						if (StringUtils.isEmpty(value)) {
							value = key;
							int index = value.indexOf(":");
							if (index != -1) {
								value = value.substring(index + 1, value.length()).replaceAll("_", " ");
							}
						}
						divNode.setChildren(processDivData(id, divNode.getChildren(), value));
					}
				}
			}
		}
		
		return nodeListDivForm;
	}
	
	/**
	 * Obtiene el valor de una propiedad del estilo.
	 * 
	 * @param style Estilo.
	 * @param property Propiedad a obtener.
	 * @return Valor de la propiedad.
	 */
	public static String getStyleValue(String style, String property) {
		
	    String separators = ";: ";
	    StringTokenizer styleTokens = new StringTokenizer(style, separators, false);
	    while (styleTokens.hasMoreTokens()) {
	    	
	        String token = styleTokens.nextToken();        
	        if (token.equals(property)) {
	        	return styleTokens.nextToken(); 
	        }
	    }

	    return null;
	}

	/**
	 * Obtener el estilo CSS de la página.
	 * 
	 * @param nodeList Lista de nodos de la página.
	 * @return Estilo CSS de la página.
	 */
	public static String getStyleCss(NodeList nodeList) {
		
		NodeList nodeListStyle = nodeList.extractAllNodesThatMatch(new TagNameFilter("style"), true);
		if ((nodeListStyle != null) &&
			(nodeListStyle.size() > 0)) {
			
			TagNode nodeStyle = (TagNode) nodeListStyle.elementAt(0);
			nodeListStyle = nodeStyle.getChildren();
			TextNode textNode = (TextNode) nodeListStyle.elementAt(0);
			
			return textNode.getText();
		}
		
		return null;
	}
	
	/**
	 * Obtener el estilo CSS para añadirlo al atributo style del div.
	 * 
	 * @param divNode Div HTML.
	 * @param id Identificador del div.
	 * @param styleCss Estilo CSS.
	 */
	public static void setStyleFromStyleCss(Div divNode,
											String id,
											String styleCss) {
		
		if (styleCss != null) {
		
	        int index = styleCss.indexOf(id);
	        if (index != -1) {
	       	 
		       	 String styleCssId = styleCss.substring(index);
		       	 int indexOpen = styleCssId.indexOf("{");
		       	 int indexClose = styleCssId.indexOf("}");
		       	 styleCssId = styleCssId.substring(indexOpen + 1, indexClose).trim();
		       	 styleCssId = StringUtils.replace(styleCssId, "\r", "");
		       	 styleCssId = StringUtils.replace(styleCssId, "\n", "");
		       	 styleCssId = StringUtils.replace(styleCssId, "\t", "");
		       	 
		       	 String style = divNode.getAttribute("style");
		       	 if (style == null) {
		       		 style = styleCssId;
		       	 }
		       	 else {
		       		 style = style + styleCssId;
		       	 }
		       	 
		       	 divNode.setAttribute("style", style, QUOTE);
	        }
		}
	}
	
}