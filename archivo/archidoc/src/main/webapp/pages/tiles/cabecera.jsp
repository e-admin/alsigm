<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<DIV id="cabecera">

<div id="barra_logo">

	<div id="logo_app">
		<div id="logo_sigia">
			<html:img altKey="logo.msg" titleKey="logo.msg" page="/pages/images/logo/ico_archivo.jpg" width="140" height="61"/> 
			<c:if test="${appConstants.configConstants.debug!=null}">
				<c:if test="${requestScope.memMax!=null}">
					Memoria Maxima (Xmx): <c:out value="${requestScope.memMax}"/> KB
					Memoria Reservada (Xms): <c:out value="${requestScope.memReservada}"/> KB
					Objetos en Session: <c:out value="${requestScope.nObjetosEnSession}"/>
					<c:url var="gcURI" value="/action/gestionReemplazarValores">
						<c:param name="method" value="refrescar"  />
						<c:param name="garbageCollector" value="true"  />
					</c:url>
			   		<a href="javascript:window.location='<c:out value="${gcURI}" escapeXml="false"/>'" >
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   		 	&nbsp;Llamar al recolector
			   		</a>
					<c:url var="gcURI" value="/action/gestionReemplazarValores">
						<c:param name="method" value="refrescar"  />
					</c:url>
			   		<a href="javascript:window.location='<c:out value="${gcURI}" escapeXml="false"/>'" >
						<html:img page="/pages/images/Ok_Si.gif" styleClass="imgTextBottom" />
			   		 	&nbsp;Refrescar
			   		</a>
			   		<BR>
			   		<div style="display:hidden">
			   			<c:out value="${requestScope.objects}" escapeXml="false"/>
			   		</div>
			   	</c:if>
			 </c:if>
		</div>
	</div>
</div>

</DIV> <%-- cabecera --%>

