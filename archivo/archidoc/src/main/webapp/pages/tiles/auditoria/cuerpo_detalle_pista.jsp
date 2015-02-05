<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>

<c:set var="bPista" value="${requestScope[appConstants.auditoria.PISTA_KEY]}"/>
<c:set var="bDetalles" value="${requestScope[appConstants.auditoria.DETALLE_PISTA_KEY]}"/>
<c:set var="xsl" value="${requestScope[appConstants.auditoria.FICHA_XSL_KEY]}"/>
<c:set var="FORMATO_FECHA_DETALLADA" value="${appConstants.common.FORMATO_FECHA_DETALLADA}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.auditoria.detallepista"/>
    </TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
	      	  <TD>
				<c:url var="volver2URL" value="/action/auditoriaBuscar">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:img page="/pages/images/close.gif" altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cerrar"/>
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

<DIV id="barra_errores"><archivo:errors/></DIV>

<div class="separador1">&nbsp;</div>

<DIV class="bloque"> <%-- segundo bloque de datos --%>	
	
	<TABLE class="formulario"> <%-- para aspecto de formulario con lineas bottom de celda --%>
			<TR>
				<TD class="tdTitulo" width="150px">
					<bean:message key="archigest.archivo.auditoria.modulo"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:message key="${bPista.moduloName}"/>		
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.auditoria.accion"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:message key="${bPista.accionName}"/>		
				</TD>
			</TR>
			<TR><TD colspan="2">&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.auditoria.usuario"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPista.usuario}"/>		
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.auditoria.ip"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:out value="${bPista.dirIP}"/>		
				</TD>
			</TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.auditoria.fecha"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<fmt:formatDate value="${bPista.timeStamp}" pattern="${FORMATO_FECHA_DETALLADA}" />
				</TD>
			</TR>
			<TR><TD colspan="2">&nbsp;</TD></TR>
			<TR>
				<TD class="tdTitulo">
					<bean:message key="archigest.archivo.auditoria.error"/>:&nbsp;
				</TD>
				<TD class="tdDatos">
					<c:choose>
						<c:when test="${bPista.codError>0}">
							<c:out value="${bPista.error}"/>						
						</c:when>
						<c:otherwise>
							<bean:message key="archigest.archivo.auditoria.noerror"/>
						</c:otherwise>
					</c:choose>
				</TD>
			</TR>
	</TABLE>

	<%-- Mostramos los detalles de la pista--%>
	<c:forEach var="detalle" items="${bDetalles}">			
		<div class="separador5"></div>
		<TABLE class="formulario" cellpadding="0" cellspacing="0"> 
			<TR>
				<TD width="10px">
				</TD>
				<TD class="etiquetaAzul11Bold">
					<bean:message key="archigest.archivo.auditoria.detalle"/>:&nbsp;
				</TD>
			</TR>
			<TR>
				<TD>
				</TD>
				<TD>
					<c:if test="${detalle.idObjeto!=null}">
					<TABLE cellpadding="0" cellspacing="0">
						<TR>
							<TD width="80px"></TD>
							<TD class="tdTitulo" width="280px"><bean:message key="archigest.archivo.auditoria.objeto"/>:&nbsp;</TD>
							<TD width="10px" class="tdDatos">&nbsp;</TD>
							<TD class="tdDatos">
								<c:out value="${detalle.objeto}"/>
							</TD>
						</TR>
					</TABLE>
					</c:if>
				</TD>
			</TR>
			<TR>
				<TD>
				</TD>
				<TD>
					<c:set var="xml" value="${detalle.contenido}"/>
					<pa:transform xmlParamName="xml" xslParamName="xsl"/>
				</TD>
			</TR>
			
		</TABLE>
	</c:forEach>

</div> <%-- bloque --%>


</div> <%-- cuerpo_drcha --%>
</div> <%-- cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>