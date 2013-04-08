<!--
JSP para obtener la información completa de personas.
-->


<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="true"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" %>



<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.common.exception.AttributesException" %>
<%@ page import="com.ieci.tecdoc.common.exception.SessionException" %>
<%@ page import="com.ieci.tecdoc.common.exception.ValidationException" %>

<%@ page import="org.dom4j.Document" %>
<%@ page import="org.dom4j.io.DocumentSource" %>

<%@ page import="javax.xml.transform.Transformer" %>
<%@ page import="javax.xml.transform.TransformerConfigurationException" %>
<%@ page import="javax.xml.transform.TransformerException" %>
<%@ page import="javax.xml.transform.TransformerFactory" %>
<%@ page import="javax.xml.transform.TransformerFactoryConfigurationError" %>
<%@ page import="javax.xml.transform.stream.StreamResult" %>
<%@ page import="javax.xml.transform.stream.StreamSource" %>
<%@ page import="javax.xml.transform.Templates" %>

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.validationlist.ValidationListUseCase" %>
<%@ page import="org.apache.log4j.*"%>

<%@ page import="es.ieci.tecdoc.fwktd.core.config.web.ContextUtil" %>

<%! Logger _logger = Logger.getLogger(getClass()); %>

<%! 
	ValidationListUseCase validationListUseCase = new ValidationListUseCase(); 
	TransformerFactory factory = TransformerFactory.newInstance();
%>

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");


	    // Identificador de persona.
	    Integer idPerson  = RequestUtils.parseRequestParameterAsInteger(request, "PersonId");
        // Texto del idioma. Ej: EU_
        String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
        // Número del idioma. Ej: 10
        Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);

        // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
        // de sesión para este usuario en el servidor de aplicaciones.
        UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
        if (_logger.isDebugEnabled()) {
       	_logger.debug("idPerson jsp: " + idPerson);
	  } 

   if(idPerson.intValue()!=0) {
		try{
            Object xmlDocument = validationListUseCase.getVldDirection(useCaseConf, idPerson, false, 0);
            
        	if (_logger.isDebugEnabled()) {
            	_logger.debug("xmlDocument jsp: " + xmlDocument);
		} 
            
        	if (xmlDocument != null) {
        		if (xmlDocument instanceof String){
	            	ResponseUtils.generateJavaScriptLogVldDirection(out, RBUtil.getInstance(useCaseConf.getLocale())
	                    .getProperty((String) xmlDocument));
        		} else {
		            String xslPath = ContextUtil.getRealPath(session.getServletContext(),Keys.XSL_VLDDIRECCION_RELATIVE_PATH);
		            StreamSource s = new StreamSource(new java.io.InputStreamReader(new java.io.BufferedInputStream(
                    new java.io.FileInputStream(xslPath))));
		            Templates cachedXSLT = factory.newTemplates(s);
		            Transformer transformer = cachedXSLT.newTransformer();
		            DocumentSource source = new DocumentSource((Document) xmlDocument);
		
					StreamResult result = new StreamResult(out);
		            transformer.transform(source, result);
	            }
	        }
        } catch (ValidationException e) {
		_logger.fatal("Error de validacion",e);
            ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_EXCEPTION_VALIDATIONEXCEPTION));
        } catch (AttributesException e) {
		_logger.fatal("Error en los atributos",e);
            ResponseUtils.generateJavaScriptError(out, e);
        } catch (SessionException e) {
		_logger.fatal("Error en la sesion",e);
            ResponseUtils.generateJavaScriptLogSessionExpiredProvCityDir(out, e.getMessage());
            //ResponseUtils.generateJavaScriptError(out, e);
        } catch (TransformerConfigurationException e) {
		_logger.fatal("Error al recuperar la lista",e);
            ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        } catch (TransformerFactoryConfigurationError e) {
		_logger.fatal("Error al recuperar la lista",e);
            ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        } catch (TransformerException e) {
		_logger.fatal("Error al recuperar la lista",e);
            ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        } catch (Exception e) {
		_logger.fatal("Error al recuperar la lista",e);
            ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(useCaseConf.getLocale())
                    .getProperty(Keys.I18N_ISICRESSRV_ERR_CREATING_DIRECTION_OBJ));
        }
   }else{

%>
	<html>
	<head>
	   <title>&nbsp;</title>
	  <script language="javascript">														
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');									
	  </script>
      <LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
      <LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/cons_tam.js"></script>
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/listview.js"></script>
      <script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/vlddirinter.js"></script>
      <script language="javascript">
         var oArrInfo = top.getInfoClient();
         var TAM_VERT_LIST = TAM_VERT_LIST_DIR;
         var TAM_VERT_DIV = TAM_VERT_DIV_DIR;
         if ( (oArrInfo[0] == "Windows") && ( (oArrInfo[1] == "9x") || (oArrInfo[1] == "ME") ) ){
            TAM_VERT_LIST = TAM_VERT_LIST_DIR_W95;
            TAM_VERT_DIV = TAM_VERT_DIV_DIR_W95;
         }
         
         document.title = top.GetIdsLan("IDS_TIT_DIRINTER");
         
         delete oArrInfo;
         oArrInfo = null;
      </script>
	</head>
	<body onunload="OnUnloadNewDir();" TABINDEX="-1">
      <form name="oFrmDirInter" onsubmit="return false;">
         <input class="button" ID="btnSubir" style="position:absolute; top:45; left:470; width:20;" Name="btnSubir" Type="Button" value="+" 
            onclick="oListNewDir.UpRowSelected();" tabindex="3" DISABLED>
         <input class="button" ID="btnBajar" style="position:absolute; top:135; left:470; width:20;" Name="btnBajar" Type="Button" value="-"
            onclick="oListNewDir.DownRowSelected();" tabindex="4" DISABLED>
         <input class="button" ID="btnEliminar" style="position:absolute; top:0; left:150; width:80;" Name="btnEliminar" Type="Button" value="" 
          	onclick="oListNewDir.DelAllRowsSelected(); top.setFocus(parent.VldNewInterPob.document.getElementById('oTxtDir'));"
          	tabindex="1" disabled="true">
         <input class="button" ID="btnAdd" style="position:absolute; top:0; left:232; width:80;" Name="btnAdd" Type="button" value=""
          	onclick="AgregarDireccion();" tabindex="2" disabled="true">
         <script language="javascript">
			document.getElementById("btnEliminar").value = top.GetIdsLan("IDS_BTNELIMINAR");
			document.getElementById("btnAdd").value = top.GetIdsLan("IDS_BTNAGREGAR");
         </script>
         <script language="javascript">
            // Creamos el objeto ListView
            var oListNewDir = new ListView("ListaDirNewInter", "oListNewDir", "oFrmDirInter");
            // Generamos los arrays de eventos
            var oArrEventsList = new Array();
            //oArrEventsList[0] = new ArrOfEvents("onchange", "HabilitarBotones();");
            // Generamos las cabeceras
            oListNewDir.CreateHeadList("25", "5", "140", "15", top.GetIdsLan("IDS_DIRECCION"), "6699CC", "6699CC");
            oListNewDir.CreateHeadList("25", "143", "80", "15", top.GetIdsLan("IDS_COD_POSTAL"), "6699CC", "6699CC");
            oListNewDir.CreateHeadList("25", "221", "110", "15", top.GetIdsLan("IDS_CIUDAD"), "6699CC", "6699CC");
            oListNewDir.CreateHeadList("25", "329", "135", "15", top.GetIdsLan("IDS_POBLACION"), "6699CC", "6699CC");
            // Generamos las listas
            oListNewDir.CreateListIz("140", TAM_VERT_DIV, "40", "5", TAM_VERT_LIST, "140", "6699CC", oArrEventsList);
            oListNewDir.CreateListIz("80", TAM_VERT_DIV, "40", "143", TAM_VERT_LIST, "80", "6699CC", oArrEventsList);
            oListNewDir.CreateListIz("110", TAM_VERT_DIV, "40", "221", TAM_VERT_LIST, "110", "6699CC", oArrEventsList);
            oListNewDir.CreateListDe("135", TAM_VERT_DIV, "40", "329", TAM_VERT_LIST, "135", "6699CC", oArrEventsList);
            //destruimos los arrays creados
            //delete oArrEventsList[0];
            oArrEventsList = null;
         </script>
	   </form>
	</body>
	</html>   

<%
   }
%>