<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<c:set var="bPrevision" value="${sessionScope[appConstants.transferencias.PREVISION_KEY]}"/>
<c:set var="estadoAbierta" value="${appConstants.transferencias.estadoPrevision.ABIERTA.identificador}" />
<c:set var="estadoAceptada" value="${appConstants.transferencias.estadoPrevision.ACEPTADA.identificador}" />
<c:set var="estadoCerrada" value="${appConstants.transferencias.estadoPrevision.CERRADA.identificador}" />
<c:set var="estadoEnviada" value="${appConstants.transferencias.estadoPrevision.ENVIADA.identificador}" />
<c:set var="previsionEnArchivo" value="${requestScope[appConstants.transferencias.PREVISION_EN_ARCHIVO]}"/>
<c:set var="previsionBorrable" value="${requestScope[appConstants.transferencias.PREVISION_BORRABLE_KEY]}"/>
<c:set var="previsionEditable" value="${requestScope[appConstants.transferencias.PREVISION_EDITABLE_KEY]}"/>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
	<TR>
		<TD class="tdTitulo" width="250px">
			<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:set var="keyTitulo">
				archigest.archivo.transferencias.tipooperacion<c:out value="${bPrevision.tipooperacion}"/>
			</c:set>
			<fmt:message key="${keyTitulo}" />
		</TD>
	</TR>
	<c:if test="${!bPrevision.entreArchivos}">
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${bPrevision.organoRemitente.nombreLargo}"/>
			</TD>
		</TR>
	</c:if>
	<c:if test="${bPrevision.entreArchivos}">
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.archivoRemitente"/>:&nbsp;
			</TD>
			<TD class="tdDatos" id="nombreArchivoRemitente">
				<c:out value="${bPrevision.nombrearchivoremitente}"/>
			</TD>
		</TR>
	</c:if>
	<c:if test="${!bPrevision.ordinaria}">
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${bPrevision.nombrearchivoreceptor}"/>
		</TD>
	</TR>
	<TR>
		<TD class="tdTitulo">
			<c:choose>
				<c:when test="${!bPrevision.entreArchivos}">
					<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
				</c:when>
				<c:otherwise>
					<bean:message key="archigest.archivo.cf.fondoDestino"/>:&nbsp;
				</c:otherwise>
			</c:choose>
		</TD>
		<TD class="tdDatos">
			<c:out value="${bPrevision.fondo.codReferencia}"/>
			<c:out value="${bPrevision.fondo.titulo}"/>
		</TD>
	</TR>
	</c:if>
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.transferencias.nUndInstal"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${bPrevision.numuinstalacion}"/>
		</TD>
	</TR>

<c:if test="${(bPrevision.estado==estadoAceptada) || (bPrevision.estado==estadoCerrada)}">
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<TABLE cellpadding="0" cellspacing="0">
			<TR>
				<TD width="50px" class="td2Datos">
					<fmt:formatDate value="${bPrevision.fechainitrans}" pattern="${FORMATO_FECHA}"/>
				</TD>
				<TD width="20px">
				</TD>
				<TD width="250px" class="td2Titulo">
					<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
				</TD>
				<TD width="60px" class="td2Datos">
					<fmt:formatDate value="${bPrevision.fechafintrans}" pattern="${FORMATO_FECHA}" />
				</TD>
			</TR>
			</TABLE>
		</TD>
	</TR>
</c:if>

<c:if test="${not empty bPrevision.motivorechazo}">
	<TR>
		<TD class="tdTitulo">
			<bean:message key="archigest.archivo.transferencias.notas"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${bPrevision.motivorechazo}"/>
		</TD>
	</TR>
</c:if>

	<TR>
		<TD class="tdTitulo" style="vertical-align:top">
			<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
		</TD>
		<TD class="tdDatos">
			<c:out value="${bPrevision.observaciones}"/>
		</TD>
	</TR>
</TABLE>