<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="lista" value="${requestScope[appConstants.salas.LISTA_USUARIOS_KEY]}" />
<bean:struts id="actionMapping" mapping="/gestionUsuarioSalasConsultaAction" />

<script language="JavaScript1.2" type="text/JavaScript">
	function select(id,nombre){
		if (opener.document.getElementById("nombre")){
			opener.document.getElementById("id").value = id;
			opener.document.getElementById("nombre").value = nombre;
		}
		window.close();
	}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<fmt:message key="archigest.archivo.salas.usuarios.listado"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<a class="etiquetaAzul12Bold" href="javascript:window.close()">
						<html:img page="/pages/images/close.gif"
                              altKey="archigest.archivo.cerrar"
                              titleKey="archigest.archivo.cerrar"
                              styleClass="imgTextMiddle" />&nbsp;
                        <bean:message key="archigest.archivo.cerrar"/>
                    </a>
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
			<tiles:put name="blockContent" direct="true">
				<div class="bloque">
					<html:form action="/gestionUsuarioSalasConsultaAction" styleId="formulario">
						<input type="hidden" name="method"/>

						<display:table name="pageScope.lista"
							id="usuario"
							requestURI="/action/gestionUsuarioSalasConsultaAction?method=listado"
							sort="list"
							export="false"
							pagesize="15"
							style="width:99%;margin-left:auto;margin-right:auto">

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.salas.lista.sin.usuarios"/>
							</display:setProperty>

							<display:column title="">
							  <a href="javascript:select('<bean:write name="usuario" property="id"/>', '<c:out value="${usuario.apellidos}"/>, <c:out value="${usuario.nombre}"/>')"
							    ><html:img page="/pages/images/aceptar.gif"
							    altKey="archigest.archivo.seleccionar"
							    titleKey="archigest.archivo.seleccionar"
							    styleClass="imgTextMiddle"/></a>
							</display:column>

							<display:column titleKey="archigest.archivo.doc.identificativo" sortable="true">
									<c:out value="${usuario.numDocIdentificacion}"/>
							</display:column>

							<display:column titleKey="archigest.archivo.nombre" sortable="true" headerClass="sortable">
									<c:out value="${usuario.nombre}"/>
							</display:column>
							<display:column titleKey="archigest.archivo.apellidos" sortable="true" headerClass="sortable">
									<c:out value="${usuario.apellidos}"/>
							</display:column>

							<display:column titleKey="archigest.archivo.salas.usuario.vigente" style="text-align: center;" sortable="true" >
								<c:choose>
									<c:when test="${usuario.vigente == 'S'}">
										<html:img page="/pages/images/checkbox-yes.gif"
														altKey="archigest.archivo.si"
														titleKey="archigest.archivo.si"
														styleClass="imgTextMiddle" />
									</c:when>
									<c:otherwise>
										<html:img page="/pages/images/checkbox-no.gif"
												altKey="archigest.archivo.no"
												titleKey="archigest.archivo.no"
												styleClass="imgTextMiddle" />
									</c:otherwise>
								</c:choose>
							</display:column>
						</display:table>
					</html:form>
				</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>
</tiles:insert>