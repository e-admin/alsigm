<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/calendarlibtag.tld" prefix="calendar" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>

<script language="JavaScript1.2" type="text/JavaScript">
	function cambiarFormato()
	{
		document.forms["fichaForm"].method.value = "saveFormatoPreferente";
		document.forms["fichaForm"].submit();	
	}
</script>	

<c:set var="formatos" value="${requestScope[appConstants.descripcion.LISTA_FORMATOS_FICHA_KEY]}"/>
<c:set var="TIPO_FICHA" value="${requestScope[appConstants.descripcion.TIPO_FICHA_KEY]}"/>

<c:choose>
	<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE}">
		<c:set var="urlaction" value="/action/isadgudocre"/>
	</c:when>
	<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_ISADG}">
		<c:set var="urlaction" value="/action/isadg"/>
	</c:when>
	<c:otherwise>
		<c:set var="urlaction" value="/action/isaar"/>
	</c:otherwise>	
</c:choose>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="descripcion.cambio.formato.ficha.preferente"/>
	</tiles:put>	

	<tiles:put name="buttonBar" direct="true">
		<table>
			<tr>
	      	  <td>
				<a class="etiquetaAzul12Bold" href="javascript:cambiarFormato()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>	      	  
	      	  </td>
			  <td width="10">&nbsp;</td>	      	  
	      	  <td>
				<c:url var="volverURL" value="${urlaction}">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volverURL}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
			  </td>
			</tr>
		</table>
	</tiles:put>
	
	<tiles:put name="boxContent" direct="true">
	
		
	 	<form name="fichaForm" method="post" action="<c:url value="${urlaction}"/>" >
		  
		 	<input type="hidden" name="method" value="saveFormatoPreferente"/>
		 	<input type="hidden" id="idformato" name="idformato" value="<c:out value="${fichaForm.idFormato}"/>"/>
			<input type="hidden" id="idficha" name="idficha" value="<c:out value="${fichaForm.idFicha}"/>"/> 	
			<input type="hidden" id="tipoacceso" name="tipoacceso" value="<c:out value="${fichaForm.tipoAcceso}"/>"/>    				 	
		 	<c:set var="idFormato" value="${fichaForm.idFormato}"/>
		 	
		 	<div id="barra_errores"><archivo:errors /></div>
	
			<div class="bloque">
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="45%">
							<bean:message key="descripcion.formato.presentacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<select style="width:100%" name="idnuevoformato">
								<c:forEach var="formato" items="${formatos}">
								<option value="<c:out value="${formato.id}" />" <c:if test="${formato.id == idFormato}">selected</c:if>> <c:out value="${formato.nombre}" />
								</c:forEach>
							</select>
						</td>
		          	</tr>
				</table>
			</div>
		 </form>	
	</tiles:put>
</tiles:insert>