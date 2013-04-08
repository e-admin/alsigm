<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionNivelesCuadro" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="listaNivelesCuadro" value="${sessionScope[appConstants.controlAcceso.LISTA_NIVELES_CUADRO]}" />

<script>
var numElementos=0;

function ejecutarAccion(accion){
	document.getElementById("method").value= accion;
	document.getElementById("formulario").submit();
}

function eliminar(){
	ejecutarAccion("eliminar");
}

function grabar(){
	ejecutarAccion("grabarNivelesArchivo");
}

function cerrar(){
	var hayCambiosSinGuardar = false;
	<c:if test="${CAMBIOS_SIN_GUARDAR && !empty listaNiveles}">
		hayCambiosSinGuardar = true;
	</c:if>

	if(hayCambiosSinGuardar){
		if (confirm("<bean:message key='archigest.warning.formularioModificado'/>")){
			ejecutarAccion("cerrar");
		}
	}
	else{
		ejecutarAccion("cerrar");
	}
}

function seleccionarElemento(orden,permitirSubirOBajar){
	var tablaListaNiveles = document.getElementById("listaNiveles");
	var numElementos = tablaListaNiveles.rows.length -1;
}
</script>

	<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

		<tiles:put name="boxTitle" direct="true">
			<bean:message key="NavigationTitle_CUADRO_CLASIFICACION_NIVELES"/>
		</tiles:put>
		<tiles:put name="buttonBar" direct="true">

			<table cellpadding=0 cellspacing=0>
			<tr>
				<td>
					<c:url var="createURL" value="/action/gestionNivelesCuadro">
						<c:param name="method" value="nuevoNivel" />
					</c:url>
					<a class="etiquetaAzul12Bold" href="<c:out value="${createURL}" escapeXml="false"/>">
	                    <html:img page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"
								altKey="archigest.archivo.crear" titleKey="archigest.archivo.crear"/>
						<bean:message key="archigest.archivo.crear"/>
	                </a>
	            </td>
				<td width="20">&nbsp;</td>
				<td>
					<tiles:insert definition="button.closeButton" flush="true"/>
				</td>
			</tr>
			</table>
		</tiles:put>

		<c:url var="paginationURL" value="/action/gestionNivelesCuadro" >
			<c:param name="method" value="init"/>
		</c:url>
		<jsp:useBean id="paginationURL" type="java.lang.String" />
		<tiles:put name="boxContent" direct="true">
			<div class="bloque">
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaNivelesCuadro"
						id="nivel"
						htmlId="listaNivelesCuadro"
						sort="list"
						style="width:98%;margin-left:auto;margin-right:auto"
						requestURI='<%=paginationURL%>'
						excludedParams="*"
						pagesize="15">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.fondos.cf.niveles.listaVacia"/>
					</display:setProperty>

					<display:column titleKey="archigest.archivo.tipo" style="width:50px" sortProperty="tipo" sortable="true" headerClass="sortable">
						<center>
							<html-el:img page="/pages/images/arboles/${nivel.tipoNivel.imagen}" styleClass="imgTextMiddle"
								  altKey="archigest.archivo.nivel.tipo.${nivel.tipoNivel.identificador}" titleKey="archigest.archivo.nivel.tipo.${nivel.tipoNivel.identificador}"/>
						</center>
					</display:column>

					<display:column titleKey="archigest.archivo.nombre" sortProperty="nombre" sortable="true" headerClass="sortable">
						<c:url var="datosNivelURL" value="/action/gestionNivelesCuadro">
							<c:param name="method" value="verNivelCuadro"/>
							<c:param name="idNivel" value="${nivel.id}" />
						</c:url>

						<a href='<c:out value="${datosNivelURL}" escapeXml="false" />' class="tdlink">
							<c:out value="${nivel.nombre}"/>
						</a>
					</display:column>
				</display:table>
				<div class="separador10">&nbsp;</div>
			</div>
		</tiles:put>
	</tiles:insert>