<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<DIV id="cabecera">

<div id="barra_logo">
	<div id="logo_app">
	
		<div id="logo_sigia">
			<html:img altKey="logo.msg" titleKey="logo.msg" page="/pages/images/logo/ico_archivo.jpg" width="140" height="61"/> 
		</div>

		<div id="salir">
			 <a href="#" onclick="javascript:logout(event)" class="etiquetaExit">
				 <html:img altKey="label.exit" titleKey="label.exit" page="/pages/images/logo/exit.gif" 
				 	width="26" height="20" styleClass="imgTextMiddle"/>
				 &nbsp;<bean:message key="label.exit"/>
			 </a>
		</div>
		<c:if test="${requestScope.memMax!=null}">
			Memoria Maxima (Xmx): <c:out value="${requestScope.memMax}"/> KB
			Memoria Reservada (Xms): <c:out value="${requestScope.memReservada}"/> KB<BR>
			Memoria Reservada Libre (Xms): <c:out value="${requestScope.memReservadaLibre}"/> KB
			Memoria Reservada Usada (Xms): <c:out value="${requestScope.memReservadaUsada}"/> KB
			Objetos en Session: <c:out value="${requestScope.nObjetosEnSession}"/><BR>
			<c:url var="gcURI" value="/action/gestionReemplazarValores">
				<c:param name="method" value="refrescar"  />
				<c:param name="garbageCollector" value="true"  />
			</c:url>
	   		<a href="javascript:window.location='<c:out value="${gcURI}" escapeXml="false"/>'" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
	   		 	&nbsp;Llamar al recolector
	   		</a>
	   		&nbsp;&nbsp;&nbsp;&nbsp;
			<c:url var="gcURI" value="/action/gestionReemplazarValores">
				<c:param name="method" value="refrescar"  />
			</c:url>
	   		<a href="javascript:window.location='<c:out value="${gcURI}" escapeXml="false"/>'" >
				<html:img page="/pages/images/Ok_Si.gif" styleClass="imgTextBottom" />
	   		 	&nbsp;Refrescar
	   		</a>
	   		<BR>
	   		<div style="display:none">
	   			<c:out value="${requestScope.objects}" escapeXml="false"/>
	   		</div>
	   	</c:if>
	</div>
</div>

</DIV> <%-- cabecera --%>

<script>
	function logout(event) {
		xStopPropagation(event);
		xPreventDefault(event);
		if (window.opener && !window.opener.closed) {
			window.opener.location = "<c:url value="/action/logoutAction" />";
			window.opener.focus();
			window.close();
		} else {
			window.location = "<c:url value="/action/logoutAction" />";
		}
	}
</script>