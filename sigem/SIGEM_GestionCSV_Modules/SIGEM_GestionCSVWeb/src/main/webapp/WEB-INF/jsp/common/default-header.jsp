<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<%
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";

String entidadId = (String)request.getParameter("idEntidad");
if (entidadId == null || entidadId.equals(""))
	entidadId = (String)request.getSession().getAttribute("idEntidad");
if (entidadId == null || entidadId.equals(""))
	entidadId = " ";
session.setAttribute("idEntidad", entidadId);

%>

	<div id="cabecera">
		<h1>
			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="SIGM" />
		</h1>
		<h3>&nbsp;</h3>
		<p class="salir">
			<c:set var="logoutUrl" value="${configProperties['fwktd-csv-webapp.logout.url']}" />
			<c:if test="${!empty logoutUrl}">
			<spring:url value="${logoutUrl}" var="url" />
			<a id="logout" href="${url}" title='<spring:message code="label.app.logout"/>'>
				<span>
					<spring:message code="label.salir"/>
				</span>
			</a>
			</c:if>
		</p>
	</div>

	<div id="usuario">
		<div id="barra_usuario">
			<h3><spring:message code="label.app.name"/></h3>
			<p class="ayuda">
				<a href="#">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</p>
		</div>
	</div>
