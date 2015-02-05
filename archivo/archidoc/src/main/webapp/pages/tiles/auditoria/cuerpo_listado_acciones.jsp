<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/auditoriaAcciones" />
<c:set var="formBean" value="${requestScope[actionMapping.name]}"/>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
	<tr>
    <TD class="etiquetaAzul12Bold" nowrap="nowrap" width="30%">
   		<bean:message key="archigest.archivo.auditoria.criticidadActions"/>&nbsp;
	</TD>
    <TD width="70%" align="right">
	<TABLE>
		<TR>
			<html:form action="/auditoriaAcciones?method=listadoModulos">
			<td nowrap="nowrap" class="etiquetaAzul12Bold">
				<bean:message key="archigest.archivo.auditoria.modules" />:&nbsp;
			</td>
			<td nowrap="nowrap">
				<html:select property="module" size="1" styleClass="input"  onchange='javascript:document.forms[0].submit()'>
					<c:set var="modulos" value="${requestScope[appConstants.auditoria.LISTA_MODULOS_KEY]}" />
					<html:option key="archigest.archivo.auditoria.selectModule" value="-1"/>
              		<c:forEach items="${modulos}" var="modulo">
              			<option value="<c:out value="${modulo.id}"/>" <c:if test="${formBean.map.module==modulo.id}"> selected </c:if> >
              				<fmt:message key="${modulo.name}"/>
              			</option>
              		</c:forEach>
				</html:select>
			</td>
			<c:if test="${appConstants.configConstants.mostrarAyuda}">
				<td width="10">&nbsp;</td>
				<td>
					<c:set var="requestURI" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
					<c:set var="carpetaIdioma" value="${sessionScope[appConstants.commonConstants.LOCALEKEY]}" />
					<a class="etiquetaAzul12Bold" href="javascript:popupHelp('<c:out value="${requestURI}" escapeXml="false"/>/help/<c:out value="${carpetaIdioma}"/>/auditoria/CriticidadAcciones.htm');">
					<html:img page="/pages/images/help.gif"
					        altKey="archigest.archivo.ayuda"
					        titleKey="archigest.archivo.ayuda"
					        styleClass="imgTextMiddle"/>
					&nbsp;<bean:message key="archigest.archivo.ayuda"/></a>
				</td>
			</c:if>
			<td width="10px"></td>
			<TD>
				<tiles:insert definition="button.closeButton" />
			</TD>
			</html:form>
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

<span class="separador1"></span>

<DIV class="bloque">
		<html:form action="/auditoriaAcciones?method=establecerNivel">

		<c:choose>
		<c:when test="${requestScope[appConstants.auditoria.SHOW_ACTIONS]}">

			<c:set var="listaAcciones" value="${requestScope[appConstants.auditoria.LISTA_ACCIONES_KEY]}"/>
			<script>
				function addAccion(id) {
					var set = document.getElementById('acciones');

					if (set.value.length<=0)
						set.value = id;
					else
						set.value = set.value + '|' + id;
				}
			</script>

			<table class="formulario">
				<tr>
					<td width="10"></td>
					<TD class="etiquetaAzul12Bold" width="250px">
						<bean:message key="archigest.archivo.auditoria.accionesDeModulo"/>:
					</TD>
					<c:if test="${!empty listaAcciones}">
					<td align="right">
						<a class=etiquetaAzul12Bold href="javascript:document.forms[1].submit();" />
							<html:img page="/pages/images/nivel.gif" altKey="archigest.archivo.auditoria.establecerNivel" titleKey="archigest.archivo.auditoria.establecerNivel" styleClass="imgTextMiddle" />
						   	&nbsp;<bean:message key="archigest.archivo.auditoria.establecerNivel"/>&nbsp;&nbsp;
						</a>
					</td>
					<td width="10"></td>
					</c:if>
				</tr>
			</table>

			<%-- Hidden con las acciones modificadas--%>
			<input type="hidden" id="acciones" name="acciones" value="">

			<display:table name="pageScope.listaAcciones"
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaAcciones"
				requestURI='<%=request.getContextPath()+"/action/auditoriaAcciones?method=listadoModulos"%>'
				export="false">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.auditoria.noactions"/>
				</display:setProperty>

				<display:column titleKey="archigest.archivo.auditoria.accion" sortable="true" headerClass="sortable">
					<fmt:message key="${listaAcciones.name}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.auditoria.nivelCriticidad" style="width:150px">
					<select name="accion-<c:out value="${listaAcciones.id}"/>" onchange="javascript:addAccion('<c:out value="${listaAcciones.id}"/>')">
						<c:forEach items="${requestScope[appConstants.auditoria.LISTA_NIVELES_KEY]}" var="nivel" varStatus="status">
						    <option value="<c:out value="${nivel.id}"/>"
						    	<c:if test="${nivel.id==listaAcciones.logLevel}">selected</c:if>
						    >
							      <c:out value="${nivel.name}"/>
						    </option>
					  	</c:forEach>
					</select>
				</display:column>
			</display:table>

			<span class="separador5">&nbsp;</span>
		</c:when>
		<c:otherwise>
			<div class="separador8">&nbsp;</div>
			<div class="separador8">&nbsp;</div>
			<bean:message key="archigest.archivo.auditoria.seleccioneModulo"/>
		</c:otherwise>
		</c:choose>
		</html:form>
</DIV> <%-- bloque --%>

</div> <%-- cuerpo_izda --%>
</div> <%-- cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>