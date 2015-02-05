<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>


<c:set var="xml" value="${requestScope[appConstants.descripcion.FICHA_XML_KEY]}"/>
<c:set var="xsl" value="${sessionScope[appConstants.descripcion.FICHA_XSL_KEY]}"/>
<c:set var="VIEW_MODE" value="${sessionScope[appConstants.descripcion.MODO_VISUALIZACION_KEY]}"/>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript"">
	function preview()
	{
		nuevaVentana = window.open("", "preview", "");
		document.forms[0].target = "preview";
		document.forms[0].method.value = "preview";
		document.forms[0].submit();
		nuevaVentana.focus();
	}
	function save()
	{
		document.forms[0].target = "";
		document.forms[0].method.value = "save";
		document.forms[0].submit();
	}
	function edit()
	{
		document.forms[0].target = "";
		document.forms[0].method.value = "edit";
		document.forms[0].submit();
	}
	function cancel()
	{
		document.forms[0].target = "";
		document.forms[0].method.value = "goBack";
		document.forms[0].submit();
	}
</script>

<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
	<tiles:put name="boxTitle" direct="true"><bean:message key="archigest.archivo.cf.fondo"/></tiles:put>

	<tiles:put name="buttonBar" direct="true">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
			  <c:choose>
			  	<c:when test="${VIEW_MODE == appConstants.descripcion.MODO_VISUALIZACION_EDICION}">
			  		<bean:message key="archigest.archivo.descripcion.editarFicha"/>
			    </c:when>
			  	<c:otherwise>
			  		<bean:message key="archigest.archivo.descripcion.verFicha"/>
			    </c:otherwise>
			  </c:choose>
            </td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
				  <c:choose>
				  
				  	<c:when test="${VIEW_MODE == appConstants.descripcion.MODO_VISUALIZACION_SOLO_LECTURA}">
	                  <td><a class="etiquetaAzul12Bold" 
	                         href="javascript:edit()"
	                      ><html:img page="/pages/images/editDoc.gif" 
	                              altKey="archigest.archivo.editar" 
	                              titleKey="archigest.archivo.editar" 
	                              styleClass="imgTextTop" />&nbsp;<bean:message key="archigest.archivo.editar"/></a></td>
	                  <td width="10"></td>
	                  <td><a class="etiquetaAzul12Bold"
	                         href="javascript:cancel()"
	                      ><html:img page="/pages/images/close.gif" 
	                              altKey="archigest.archivo.cerrar" 
	                              titleKey="archigest.archivo.cerrar" 
	                              styleClass="imgTextTop" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
				    </c:when>

				  	<c:when test="${VIEW_MODE == appConstants.descripcion.MODO_VISUALIZACION_EDICION}">
	                  <td><a class="etiquetaAzul12Bold" 
	                         href="javascript:preview()"
	                      ><html:img page="/pages/images/searchDoc.gif" 
	                              altKey="archigest.archivo.vistaPreliminar" 
	                              titleKey="archigest.archivo.vistaPreliminar" 
	                              styleClass="imgTextTop" />&nbsp;<bean:message key="archigest.archivo.vistaPreliminar"/></a></td>
	                  <td width="10"></td>
	                  <td><a class="etiquetaAzul12Bold" 
	                         href="javascript:save()"
	                      ><html:img page="/pages/images/Ok_Si.gif" 
	                              altKey="archigest.archivo.aceptar" 
	                              titleKey="archigest.archivo.aceptar" 
	                              styleClass="imgTextTop" />&nbsp;<bean:message key="archigest.archivo.aceptar"/></a></td>
	                  <td width="10"></td>
	                  <td><a class="etiquetaAzul12Bold"
	                         href="javascript:cancel()"
	                      ><html:img page="/pages/images/Ok_No.gif" 
	                              altKey="archigest.archivo.cancelar" 
	                              titleKey="archigest.archivo.cancelar" 
	                              styleClass="imgTextTop" />&nbsp;<bean:message key="archigest.archivo.cancelar"/></a></td>
				    </c:when>

				  	<c:when test="${VIEW_MODE == appConstants.descripcion.MODO_VISUALIZACION_VISTA_PRELIMINAR}">
	                  <td><a class="etiquetaAzul12Bold"
	                         href="javascript:window.close()"
	                      ><html:img page="/pages/images/close.gif" 
	                              altKey="archigest.archivo.cerrar" 
	                              titleKey="archigest.archivo.cerrar" 
	                              styleClass="imgTextTop" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
				    </c:when>
				    
				  </c:choose>
                </tr>
              </table>
            </td>
          </tr>
        </table>
	</tiles:put>
	<tiles:put name="boxContent" direct="true">
    	 <div id="barra_errores"><archivo:errors /></div>
         <div class="separador1">&nbsp;</div>
         <pa:transform xmlParamName="xml" xslParamName="xsl"/>
	</tiles:put>
</tiles:insert>