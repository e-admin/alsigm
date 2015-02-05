<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/archigest.tld" prefix="archigest" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<%@ page import="solicitudes.vos.MotivoRechazoVO" %>
<script src="<%=request.getContextPath()%>/js/sigia_form.js" type="text/JavaScript"></script>

<c:set var="bConsulta" value="${sessionScope[appConstants.consultas.CONSULTA_KEY]}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.consultas.datosconsultas"/>
    </TD>
    <TD align="right">
		<TABLE cellpadding=0 cellspacing=0>
		 <TR>
		<TD>
				<%--boton Cancelar --%>						
				<c:url var="volver2URL" value="/action/gestionConsultas">
					<c:param name="method" value="goBack" />
				</c:url>				
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:img 
						page="/pages/images/close.gif" border="0" 
						altKey="archigest.archivo.cerrar"
						titleKey="archigest.archivo.cerrar" styleClass="imgTextTop" />
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

<DIV class="cabecero_bloque"> <%--primer bloque de datos (resumen siempre visible) --%>
			<jsp:include page="cuerpo_cabeceracte_consulta.jsp" flush="true" />
</DIV> <%--primer bloque de datos (resumen siempre visible) --%>

<div class="separador8">&nbsp; <%--8 pixels de separacion --%>
</div>

<div class="cabecero_bloque"> <%--cabecero segundo bloque de datos --%>

<TABLE class="w98m1" cellpadding=0 cellspacing=0>
  <TBODY>
  <TR>
    <TD class="etiquetaAzul12Bold" width="80%">
		<bean:message key="archigest.archivo.prestamos.udocsDenegadas"/>
	</TD>
    <TD width="20%" align="right">
		<TABLE cellpadding=0 cellspacing=0>
			  <TR>
				<TD>
					<IMG src="../images/pixel.gif" class="imgTextMiddle" height="18"/>
				</TD>
			  </TR>
	 
		</TABLE>
	</TD>
  </TR></TBODY></TABLE>
</div> <%--cabecero bloque --%>


<DIV class="bloque"> <%--segundo bloque de datos --%>

	<table class="formulario">
		<tr>
			<td class="tdTitulo">
				<bean:message key="archigest.archivo.prestamos.msgUdocsDenegadas"/>:
			</td>
		</tr>
	</table>

	<table class="formulario">
	<c:forEach items="${requestScope[appConstants.consultas.LISTA_NO_DISPONIBLES]}" var="indisponibilidad">
		<tr>
			<td width="150px" class="tdTitulo">&nbsp;</td>
			<td class="tdDatos">
				<c:out value="${indisponibilidad.signaturaudoc}"/>&nbsp;-&nbsp;<c:out value="${indisponibilidad.titulo}"/>
			</td>
		</tr>
	</c:forEach>
	</table>

</div> <%--bloque --%>

</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>