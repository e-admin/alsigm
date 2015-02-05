<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>

<bean:struts id="actionMapping" mapping="/gestionNivelesCuadro" />
<c:set var="puedeSerNivelInicial" value="${sessionScope[appConstants.fondos.PUEDE_SER_NIVEL_INICIAL]}"/>
<c:set var="listaHijosNivel" value="${sessionScope[appConstants.fondos.LISTA_NIVELES_HIJO]}"/>
<c:set var="nivel" value="${sessionScope[appConstants.fondos.NIVEL_CF_KEY]}"/>
<c:set var="fichaDescPref" value="${requestScope[appConstants.fondos.FICHA_DESCRIPCION_NIVEL]}"/>
<c:set var="repositorioEcmPref" value="${requestScope[appConstants.fondos.REPOSITORIO_ECM_NIVEL]}"/>
<c:set var="puedeTenerHijos" value="${requestScope[appConstants.fondos.PUEDE_TENER_HIJOS]}"/>
<c:set var="posiblesHijos" value="${requestScope[appConstants.fondos.LISTA_POSIBLES_NIVELES_HIJO]}"/>

<script language="javascript"><!--
function cargarDatos(){
	document.getElementById("method").value = "cargarDatos";
	document.getElementById("formulario").submit();
}

function agregarDescendiente(){
	<c:choose>
		<c:when test="${empty posiblesHijos}">
			alert('<bean:message key="archigest.archivo.fondos.cf.niveles.listaPosiblesHijosVacia"/>');
			return;
		</c:when>
		<c:otherwise>
			<c:url var="agregarDescendienteURL" value="/action/gestionNivelesCuadro">
				<c:param name="method" value="agregarNivelHijo"/>
				<c:param name="idNivel" value="${nivel.id}"/>
			</c:url>
			window.location = '<c:out value="${agregarDescendienteURL}" escapeXml="false"/>';
		</c:otherwise>
	</c:choose>
}

function eliminarDescendiente(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	if (numElementsSelected(form.descendientes) == 0)
		alert('<bean:message key="archigest.archivo.fondos.cf.nivel.seleccionNivelHijo"/>');
	else if (window.confirm("<bean:message key='archigest.archivo.fondos.cf.niveles.eliminar.descendiente'/>"))
		form.submit();
}
--></script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="NavigationTitle_CUADRO_CLASIFICACION_NIVELES_DATOS"/>
	</tiles:put>

	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<c:url var="eliminarNivelURL" value="/action/gestionNivelesCuadro">
						<c:param name="method" value="eliminarNivelCuadro"/>
						<c:param name="idNivel" value="${nivel.id}" />
					</c:url>

					<a class="etiquetaAzul12Bold" href='<c:out value="${eliminarNivelURL}" escapeXml="false" />'>
					<html:img page="/pages/images/deleteDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.eliminar"/></a>
	      	  	</td>
	      	  	<td width="10">&nbsp;</td>
				<td nowrap>
					<tiles:insert definition="button.closeButton" flush="false" />
				</td>
			</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert template="/pages/tiles/PABlockLayout.jsp">
			<tiles:put name="blockTitle" direct="true">
      			<bean:message key="archigest.archivo.informacion"/>
    		</tiles:put>

			<tiles:put name="buttonBar" direct="true">
				<table cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<c:url var="editarNivelURL" value="/action/gestionNivelesCuadro">
								<c:param name="method" value="editarNivelCuadro"/>
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
			</tiles:put>

			<tiles:put name="blockContent" direct="true">
				<table class="formulario" width="99%">
			    	<tr>
			          <td class="tdTitulo" width="200px">
			            <bean:message key="archigest.archivo.nombre"/>:&nbsp;
			          </td>
			          <td class="tdDatos"><bean:write name="nivel" property="nombre"/></td>
			        </tr>
			    </table>
			    <table class="formulario" width="99%">
			        <tr>
			          <td class="tdTitulo" width="200px">
			            <bean:message key="archigest.archivo.tipo"/>:&nbsp;
			          </td>
			          <td class="tdDatos"><bean:write name="nivel" property="tipoNivel.nombre"/>
			          <c:if test="${puedeSerNivelInicial}">
						<span class="td2Titulo">&nbsp;&nbsp;&nbsp;&nbsp;
							<html:img page="/pages/images/check-yes.gif" titleKey="archigest.archivo.tipo" />
							<bean:message key="archigest.archivo.fondos.cf.nivel.inicial"/>
						</span>
					  </c:if>
					  </td>
			        </tr>
			    </table>
			    <c:if test="${nivel.tipoNivel.tieneSubtipo}">
			    <table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" width="200px">
			            	<bean:message key="archigest.archivo.subtipo"/>:&nbsp;
				        </td>
				        <td class="tdDatos">
				          <bean-el:message key="archigest.archivo.subtipo.${nivel.subtipo}"/>
						</td>
					</tr>
				</table>
				</c:if>
				<table class="formulario" width="99%">
			    	<tr>
			          <td class="tdTitulo" width="200px">
			            <bean:message key="archigest.archivo.fondos.cf.niveles.fichaPref"/>:&nbsp;
			          </td>
			          <td class="tdDatos">
			          	<logic:notEmpty name="fichaDescPref">
							<bean:write name="fichaDescPref" property="nombre"/>
						</logic:notEmpty>
			          </td>
			        </tr>
			    </table>
				<table class="formulario" width="99%">
			    	<tr>
			          <td class="tdTitulo" width="200px">
			            <bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;
			          </td>
			          <td class="tdDatos">
			          	<logic:notEmpty name="repositorioEcmPref">
							<bean:write name="repositorioEcmPref" property="nombre"/>
						</logic:notEmpty>
					  </td>
			        </tr>
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
					<table cellpadding=0 cellspacing=0>
						<tr>
							<c:if test="${!empty listaHijosNivel}">
								<td nowrap>
									<a class="etiquetaAzul12Bold" href="javascript:eliminarDescendiente();" >
									<html:img page="/pages/images/delDoc.gif" altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar" styleClass="imgTextMiddle" />
									<bean:message key="archigest.archivo.eliminar"/></a>
								</td>
								<td width="10px"></td>
							</c:if>
							<td nowrap>
								<c:url var="agregarDescendienteURL" value="/action/gestionNivelesCuadro">
									<c:param name="method" value="agregarNivelHijo"/>
									<c:param name="idNivel" value="${nivel.id}"/>
								</c:url>

								<a class="etiquetaAzul12Bold" href="javascript:agregarDescendiente();" target="_self">
								<html:img page="/pages/images/addDoc.gif" altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.anadir"/></a>
							</td>
							<td width="10px"></td>
							<td nowrap>
								<c:url var="crearNuevoDescendienteURL" value="/action/gestionNivelesCuadro">
									<c:param name="method" value="crearNuevoNivelHijo"/>
									<c:param name="idNivel" value="${nivel.id}"/>
									<c:param name="tipoNivel" value="${nivel.tipo}"/>
								</c:url>
								<a class="etiquetaAzul12Bold" href="<c:out value="${crearNuevoDescendienteURL}" escapeXml="false" />" target="_self">
								<html:img page="/pages/images/newDoc.gif" altKey="archigest.archivo.fondos.cf.boton.nuevo" titleKey="archigest.archivo.fondos.cf.boton.nuevo" styleClass="imgTextMiddle" />
								<bean:message key="archigest.archivo.fondos.cf.boton.nuevo"/></a>
							</td>
						</tr>
					</table>
				</tiles:put>

				<c:url var="paginationURL" value="/action/gestionNivelesCuadro" >
					<c:param name="method" value="verNivelCuadro"/>
					<c:param name="idNivel" value="${nivel.id}"/>
				</c:url>
				<jsp:useBean id="paginationURL" type="java.lang.String" />
				<tiles:put name="blockContent" direct="true">
					<div class="separador10">&nbsp;</div>
					<html:form action="/gestionNivelesCuadro" styleId="formulario">
						<input type="hidden" name="method" id="method" value="eliminarNivelHijo"/>
						<html:hidden property="idNivel"/>
						<display:table name="pageScope.listaHijosNivel"
								id="nivelHijo"
								htmlId="listaHijosNivel"
								sort="list"
								style="width:98%;margin-left:auto;margin-right:auto"
								requestURI='<%=paginationURL%>'
								excludedParams="*"
								pagesize="15">

							<display:setProperty name="basic.msg.empty_list">
								<bean:message key="archigest.archivo.fondos.cf.niveles.listaHijosVacia"/>
							</display:setProperty>

							<display:column style="width:10px" title="">
								<input type="checkbox" name="descendientes" value="<c:out value="${nivelHijo.id}" />" >
							</display:column>

							<display:column titleKey="archigest.archivo.tipo" style="width:50px" sortProperty="tipo" sortable="true" headerClass="sortable">
								<center>
									<html-el:img page="/pages/images/arboles/${nivelHijo.tipoNivel.imagen}" styleClass="imgTextMiddle"
										  altKey="archigest.archivo.nivel.${nivelHijo.tipoNivel.identificador}" titleKey="archigest.archivo.nivel.${nivelHijo.tipoNivel.identificador}"/>
								</center>
							</display:column>

							<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
								<c:url var="datosNivelURL" value="/action/gestionNivelesCuadro">
									<c:param name="method" value="verNivelCuadro"/>
									<c:param name="idNivel" value="${nivelHijo.id}" />
								</c:url>

								<a href='<c:out value="${datosNivelURL}" escapeXml="false" />' class="tdlink">
									<c:out value="${nivelHijo.nombre}"/>
								</a>
							</display:column>
						</display:table>
					</html:form>
					<div class="separador10">&nbsp;</div>
				</tiles:put>
			</tiles:insert>
		</c:if>
	</tiles:put>
</tiles:insert>