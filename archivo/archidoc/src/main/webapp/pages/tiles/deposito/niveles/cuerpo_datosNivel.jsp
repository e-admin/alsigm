<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>


<bean:struts id="actionMapping" mapping="/gestionNivelesDeposito" />

<c:set var="listaHijosNivel" value="${sessionScope[appConstants.deposito.LISTA_NIVELES_DEPOSITO_HIJO]}"/>
<c:set var="nivel" value="${sessionScope[appConstants.deposito.NIVEL_DEPOSITO_KEY]}"/>
<c:set var="puedeTenerHijos" value="${not nivel.tipoAsignable}"/>

<c:if test="${nivel.editable || nivel.eliminable}">
<script language="javascript">
function eliminar(){
	if (confirm("<bean:message key='archigest.archivo.menu.admin.deposito.niveles.eliminar.warning'/>")) {
		if (window.top.showWorkingDiv) {
			var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
			var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
			window.top.showWorkingDiv(title, message);
		}

		enviarFormulario("formulario","eliminarNivel");
	}
}

function editar(){
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}

	enviarFormulario("formulario","editarNivel");
}

</script>
</c:if>

<html:form action="/gestionNivelesDeposito" styleId="formulario">
<input type="hidden" name="id" value="<c:out value="${nivel.id}"/>"/>
<input type="hidden" name="asignable" value="<c:out value="${nivel.tipoAsignable}"/>"/>
<input type="hidden" id="method" name="method" value=""/>
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<c:choose>
			<c:when test="${nivel.tipoAsignable}">
				<bean:message key="archigest.archivo.depositos.niveles.asignable.titulo"/>
			</c:when>
			<c:otherwise>
				<bean:message key="archigest.archivo.depositos.niveles.no.asignable.titulo"/>
			</c:otherwise>
		</c:choose>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
			<c:if test="${not empty nivel.idpadre && !nivel.tipoDeposito}">
			<td>
				<c:url var="datosNivelURL" value="/action/gestionNivelesDeposito">
					<c:param name="method" value="verNivel"/>
					<c:param name="idNivel" value="${nivel.idpadre}"/>
				</c:url>
				<a class="etiquetaAzul12Bold" href="<c:out value="${datosNivelURL}" escapeXml="false"/>" target="_self">
					<html:img page="/pages/images/treePadre.gif" altKey="archigest.archivo.cf.verPadre" titleKey="archigest.archivo.cf.verPadre" styleClass="imgTextMiddle" />
					<bean:message key="archigest.archivo.cf.verPadre"/>
				</a>
			</td>
			<td width="10">&nbsp;</td>
			</c:if>

				<c:if test="${nivel.eliminable}">
				<td>
					<a class="etiquetaAzul12Bold" href='javascript:eliminar()'>
					<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
	      	  	</td>
	      	  	<td width="10">&nbsp;</td>
	      	  	</c:if>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="false" />
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
      			<html-el:img page="/pages/images/arboles/${nivel.imagen}" styleClass="imgTextMiddle" altKey="${nivel.nombre}" titleKey="${nivel.nombre}"/>
      			&nbsp;<bean:message key="archigest.archivo.informacion"/>
    		</tiles:put>

			<tiles:put name="buttonBar" direct="true">
				<c:if test="${nivel.editable}">
				<table cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<c:url var="editarNivelURL" value="/action/gestionNivelesDeposito">
								<c:param name="method" value="editarNivel"/>
								<c:param name="idNivel" value="${nivel.id}"/>
							</c:url>

							<a class="etiquetaAzul12Bold" href="<c:out value="${editarNivelURL}" escapeXml="false" />" >
								<html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.editar"
									titleKey="archigest.archivo.editar" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.editar"/>
							</a>
						</td>
					</tr>
				</table>
				</c:if>
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<table class="formulario" width="99%">
			    <tr>
					<td class="tdTitulo" width="200px">
			           <bean:message key="archigest.archivo.depositos.niveles.icono"/>:&nbsp;
			        </td>
			        <td class="tdDatos">
			        	<c:forEach items="${nivel.icono.listaIconos}" var="icono" varStatus="status">
							<html-el:img page="/pages/images/arboles/${icono}" styleClass="imgTextMiddle"
										altKey="archigest.archivo.depositos.niveles.iconos.${status.index}" titleKey="archigest.archivo.depositos.niveles.iconos.${status.index}"/>
			        	</c:forEach>
			        </td>
				</tr>


			    	<tr>
			          <td class="tdTitulo" width="200px">
			            <bean:message key="archigest.archivo.depositos.niveles.nombre"/>:&nbsp;
			          </td>
			          <td class="tdDatos"><bean:write name="nivel" property="nombre"/></td>
			        </tr>
			        <tr>
			          <td class="tdTitulo">
			            <bean:message key="archigest.archivo.depositos.niveles.nombre.abreviado"/>:&nbsp;
			          </td>
			          <td class="tdDatos"><bean:write name="nivel" property="nombreAbreviado"/>
					  </td>
			        </tr>
			        <tr>
			          <td class="tdTitulo">
			            <bean:message key="archigest.archivo.depositos.niveles.descripcion"/>:&nbsp;
			          </td>
			          <td class="tdDatos"><bean:write name="nivel" property="descripcion"/>
					  </td>
			        </tr>
			        <tr>
			          <td class="tdTitulo">
			            <bean:message key="archigest.archivo.depositos.niveles.caracter.ordenacion"/>:&nbsp;
			          </td>
			          <td class="tdDatos"><bean:write name="nivel" property="caracterorden"/>
					  </td>
			        </tr>
			    	<tr>
			          <td class="tdTitulo">
			            <bean:message key="archigest.archivo.depositos.niveles.asignable"/>:&nbsp;
			          </td>
			          <td class="tdDatos">
			          	<c:choose>
			          		<c:when test="${nivel.tipoAsignable}">
								<bean:message key="archigest.archivo.si"/>
			          		</c:when>
			          		<c:otherwise>
			          			<bean:message key="archigest.archivo.no"/>
			          		</c:otherwise>
			          	</c:choose>
			          </td>
			        </tr>
			    	<c:if test="${nivel.tipoAsignable}">
			    	<tr>
			          <td class="tdTitulo">
			            <bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion"/>:&nbsp;
			          </td>
			          <td class="tdDatos">
			          	<c:choose>
			          		<c:when test="${nivel.ordenacionEnAnchura}">
								<bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion.anchura"/>
			          		</c:when>
			          		<c:when test="${nivel.ordenacionEnProfundidad}">
								<bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion.profundidad"/>
			          		</c:when>
			          		<c:otherwise>
			          		</c:otherwise>
			          	</c:choose>
			          </td>
			        </tr>
			        </c:if>
			    </table>
			</tiles:put>
		</tiles:insert>

		<c:if test="${puedeTenerHijos}">
			<div class="separador8">&nbsp;</div>

			<tiles:insert template="/pages/tiles/PABlockLayout.jsp" flush="true">
				<tiles:put name="blockTitle" direct="true">
					<bean:message key="archigest.archivo.fondos.cf.nivel.descendientes"/>
				</tiles:put>

				<tiles:put name="buttonBar" direct="true">
					<c:if test="${!nivel.tipoUbicacion}">
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td nowrap>
								<c:url var="crearNuevoDescendienteURL" value="/action/gestionNivelesDeposito">
									<c:param name="method" value="nuevoNivel"/>
									<c:param name="idPadre" value="${nivel.id}"/>
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${crearNuevoDescendienteURL}" escapeXml="false" />" target="_self">
								<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.fondos.cf.boton.nuevo" titleKey="archigest.archivo.fondos.cf.boton.nuevo" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.fondos.cf.boton.nuevo"/></a>
							</td>
						</tr>
					</table>
					</c:if>
				</tiles:put>

				<tiles:put name="blockContent" direct="true">
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaHijosNivel"
						id="nivelHijo"
						htmlId="listaHijosNivel"
						style="width:98%;margin-left:auto;margin-right:auto"
						>

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.depositos.niveles.listaVacia"/>
					</display:setProperty>
					<display:column style="width:50px;text-align:center">
						<html-el:img page="/pages/images/arboles/${nivelHijo.imagen}" styleClass="imgTextMiddle"
								altKey="${nivelHijo.nombre}" titleKey="${nivelHijo.nombre}"/>
					</display:column>
					<display:column style="width:40px;text-align:center" titleKey="archigest.archivo.depositos.niveles.asignable">
						<c:choose>
			          		<c:when test="${nivelHijo.tipoAsignable}">
								<bean:message key="archigest.archivo.si"/>
			          		</c:when>
			          		<c:otherwise>
			          			<bean:message key="archigest.archivo.no"/>
			          		</c:otherwise>
			          	</c:choose>

					</display:column>

					<display:column titleKey="archigest.archivo.depositos.niveles.nombre" >
						<c:url var="datosNivelURL" value="/action/gestionNivelesDeposito">
							<c:param name="method" value="verSubNivel"/>
							<c:param name="idNivel" value="${nivelHijo.id}" />
						</c:url>

						<a href='<c:out value="${datosNivelURL}" escapeXml="false" />' class="tdlink">
							<c:out value="${nivelHijo.nombre}"/>
						</a>
					</display:column>

				</display:table>


					<div class="separador10">&nbsp;</div>
				</tiles:put>
			</tiles:insert>
		</c:if>
	</tiles:put>
</tiles:insert>
</html:form>