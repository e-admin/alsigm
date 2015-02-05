<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.INFO_RELACION_KEY]}"/>

<c:set var="relacionEntrega" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>

<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.enviar"/>
		<bean:message key="archigest.archivo.transferencias.relacion"/>:&nbsp;
		<bean:message key="archigest.archivo.informe"/>
	</TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
	       <TD>
				<c:url var="volverURL" value="/action/recepcionRelaciones?method=cancelar" />
		   		<a class=etiquetaAzul12Bold href="<c:out value="${volverURL}" escapeXml="false"/>" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.aceptar"/>
		   		</a>
		   </TD>
		 </TR>
		</TABLE>
	</TD>
  </TR>
</TABLE>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<DIV class="cabecero_bloque_sin_height"> <%--cabecero primer bloque de datos --%>
			<jsp:include page="cabeceracte_relacion.jsp" flush="true" />
</DIV>

<div class="separador8">&nbsp;</div>

<DIV class="bloque"> <%--cabecero primer bloque de datos --%>


	<TABLE class="formulario"> <%--para aspecto de formulario con lineas bottom de celda --%>
		<TR>
			<TD class="tdTitulo" width="250px">
				<bean:message key="archigest.archivo.transferencias.tipoTransf"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:set var="keyTitulo">
					archigest.archivo.transferencias.tipooperacion<c:out value="${vRelacion.tipooperacion}"/>
				</c:set>
				<fmt:message key="${keyTitulo}" />
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" >
			<bean:message key="archigest.archivo.transferencias.prevision"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="150px" class="td2Datos">
							<c:out value="${vRelacion.codigoTransferencia}"/>
						</TD>
						<TD width="20px">
						</TD>
						<c:if test="${vRelacion.ordendetalle>=1}">
							<TD width="230px" class="td2Titulo">
								<bean:message key="archigest.archivo.transferencias.lineaDet"/>:&nbsp;
							</TD>
							<TD width="80px" class="td2Datos">
								<c:out value="${vRelacion.ordendetalle}"/>
							</TD>
						</c:if>
					</TR>
				</TABLE>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.fIniTransf"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="100px" class="td2Datos">
							<fmt:formatDate value="${vRelacion.fechainitransf}" pattern="${FORMATO_FECHA}"/>
						</TD>
						<TD width="20px">
						</TD>
						<TD width="230px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fFinTransf"/>:&nbsp;
						</TD>
						<TD width="80px" class="td2Datos">
							<fmt:formatDate value="${vRelacion.fechafintransf}" pattern="${FORMATO_FECHA}" />
						</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>

		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.orgRem"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.idorganoremitente}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.archRecep"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.nombrearchivoreceptor}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.fondo"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.idfondo}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.procedimiento"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.idprocedimiento}"/> <c:out value="${vRelacion.nombreProcedimiento}"/>
			</TD>
		</TR>
		<c:if test="${!empty vRelacion.iddetprevision}">
		<c:set var="detallePrevision" value="${requestScope[appConstants.transferencias.DETALLEPREVISION_KEY]}" />
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.fechaInicioExps"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<TABLE cellpadding="0" cellspacing="0">
					<TR>
						<TD width="100px" class="td2Datos">
							<c:out value="${detallePrevision.fechaInicioExpedientes}" />
						</TD>
						<TD width="20px">
						</TD>
						<TD width="230px" class="td2Titulo">
							<bean:message key="archigest.archivo.transferencias.fechaFinExps"/>:&nbsp;
						</TD>
						<TD width="80px" class="td2Datos">
							<c:out value="${detallePrevision.fechaFinExpedientes}" />
						</TD>
					</TR>
					</TABLE>
				</TD>
		</TR>
		</c:if>

		<c:if test="${vRelacion.ordinaria}">
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.transferencias.sistProd"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${vRelacion.nombresistproductor}"/>
				</TD>
			</TR>
		</c:if>
		<TR>
			<TD class="tdTitulo">
				<bean:message key="archigest.archivo.transferencias.serie"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.idserie}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo">
	 			<bean:message key="archigest.archivo.transferencias.formato"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.nombreformato}"/>
			</TD>
		</TR>
		<TR>
			<TD class="tdTitulo" style="vertical-align:top">
				<bean:message key="archigest.archivo.transferencias.observaciones"/>:&nbsp;
			</TD>
			<TD class="tdDatos">
				<c:out value="${vRelacion.observaciones}"/>
			</TD>
		</TR>
	</TABLE>
</DIV>

<div class="separador8">&nbsp;</div>

<DIV class="bloque"> <%--cabecero primer bloque de datos --%>

			<bean:message key="archigest.archivo.transferencias.uDocsRel"/>:

	<c:set var="relacionEntrega" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
</DIV> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>
