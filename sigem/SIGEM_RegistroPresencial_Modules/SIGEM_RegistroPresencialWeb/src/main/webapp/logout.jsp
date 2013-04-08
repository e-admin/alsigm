<!--
JSP para la salida de la aplicación.
Esta JSP siempre invalida la sesión actual.
Redirecciona a la página de entrada a la aplicación.
-->


<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="true"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.common.keys.ConfigurationKeys"%>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils" %>
<%@ page import="ieci.tecdoc.sgm.registropresencial.utils.AuthenticationHelper" %>
<%@page import="com.ieci.tecdoc.common.utils.Configurator"%>

<%@ include file="__headerLocale.jsp" %>

<html>
<head>
</head>
<body>
<%
	//GotoBlank.
	Boolean gotoBlank = RequestUtils.parseRequestParameterAsBoolean(request, "GotoBlank", Boolean.FALSE);
	// Texto del idioma. Ej: EU_
	String idioma=(String)session.getAttribute(Keys.J_IDIOMA);
	// Número del idioma. Ej: 10
	Long numIdioma=(Long)session.getAttribute(Keys.J_NUM_IDIOMA);
	
	// Obtenemos el locale java para el código de idioma seleccionado por el usuario.
	Locale locale = (Locale) microsoftLocalesID2DefaultLocales.get(numIdioma);
	
	try {
        // Obtenemos el objeto de configuración del servidor de aplicaciones y el locale
        // para este usuario en el servidor de aplicaciones.
		UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
		SecurityUseCase securityUseCase = new SecurityUseCase();
        // Salida de la sessión.
        if (useCaseConf != null){
			securityUseCase.logout(useCaseConf);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

    // Se elimina la sesión en el servidor.
	session.invalidate();
	String jndiName = Configurator.getInstance().getProperty(ConfigurationKeys.KEY_DESKTOP_REPORTSDATASOURCEJNDINAME);
	jndiName = jndiName.substring(jndiName.lastIndexOf("/") + 1, jndiName.length());

    if (jndiName.equals(Keys.KEY_BUILD_TYPE_INVESICRES)){
		// Redirecciona a la página de entrada a la aplicación.
		if (!gotoBlank.booleanValue()){
			out.write("<script language=\"javascript\">");
			out.write("window.open(\"__index.jsp?Idioma=" + idioma + "&numIdioma=" + numIdioma + "\" , \"_top\",\"location=no\",true);");
		    out.write("</script>");
		} else {
			out.write("<script language=\"javascript\">");
			out.write("window.open(\"about:blank\", \"_top\",\"location=no\",true);");
		    out.write("</script>");
		}
    } else {
        out.write("<script>top.location.href='" + AuthenticationHelper.getWebAuthDesconectURL(request) + "';</script>");
    }
    
%>
</body>