<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<bean:struts id="actionMapping" mapping="/gestionNivelesArchivo" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />
<c:set var="listaNiveles" value="${sessionScope[appConstants.controlAcceso.LISTA_NIVELES_ARCHIVO]}" />
<c:set var="CAMBIOS_SIN_GUARDAR" value="${sessionScope[appConstants.controlAcceso.CAMBIOS_SIN_GUARDAR]}" />
<c:set var="hayNuevosElementos" value="${sessionScope[appConstants.controlAcceso.HAY_NUEVOS_ELEMENTOS]}" />
<c:set var="ordenSeleccionado" value="${sessionScope[appConstants.controlAcceso.ORDEN_SELECCIONADO]}" />

<script>

var numElementos=0;

function ejecutarAccion(accion){
	document.getElementById("method").value= accion;
	document.getElementById("formulario").submit();
}


function eliminar(){
	ejecutarAccion("eliminar");
}

function bajar(){
	ejecutarAccion("bajar");
}


function subir(){
	ejecutarAccion("subir");
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


<c:set var="FORMATO_FECHA" value="${appConstants.common.FORMATO_FECHA}"/>
<c:set var="delimiter" value="${appConstants.common.DELIMITER_IDS}"/>
<html:form action="/gestionNivelesArchivo" styleId="formulario">
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">

	<input type="hidden" name="method" id="method"/>
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="NavigationTitle_ARCHIVOS_NIVELES"/>
	</tiles:put>
	<tiles:put name="buttonBar" direct="true">

		<table cellpadding=0 cellspacing=0>
		<tr>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:grabar()"  onclick="">
					<html:img page="/pages/images/save.gif" styleClass="imgTextMiddle"
						altKey="archigest.archivo.guardar" titleKey="archigest.archivo.guardar"/>
					<bean:message key="archigest.archivo.guardar"/>
				</a>
				<html:img page="/pages/images/pixel.gif" border="0" />
			</td>
			<td width="20px"/>
			<td>
				<a class="etiquetaAzul12Bold" href="javascript:cerrar()"  onclick="">
					<html:img page="/pages/images/close.gif" styleClass="imgTextMiddle"
						altKey="archigest.archivo.cerrar" titleKey="archigest.archivo.cerrar"/>
					<bean:message key="archigest.archivo.cerrar"/>
				</a>
			</td>
		</tr>
		</table>
	</tiles:put>

	<tiles:put name="boxContent" direct="true">
		<tiles:insert page="/pages/tiles/PABlockLayout.jsp">

			<tiles:put name="blockTitle" direct="true"></tiles:put>
			<tiles:put name="buttonBar" direct="true">
				<table cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<c:url var="altaNivelURL" value="/action/gestionNivelesArchivo">
								<c:param name="method" value="altaNivelArchivo"/>
							</c:url>

							<a class="etiquetaAzul12Bold" href="<c:out value="${altaNivelURL}" escapeXml="false" />" >
								<html:img page="/pages/images/new.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.anadir" titleKey="archigest.archivo.anadir"/>
								<bean:message key="archigest.archivo.anadir"/>
							</a>
						</td>
						<td width="20">&nbsp;</td>

						<c:if test="${!empty listaNiveles}">

						<td id="celdaEliminar">
							<html:img page="/pages/images/pixel.gif" border="0" />
							<a class="etiquetaAzul12Bold" href="javascript:eliminar()">
								<html:img page="/pages/images/delete.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.eliminar" titleKey="archigest.archivo.eliminar"/>
								&nbsp;<bean:message key="archigest.archivo.eliminar"/>
							</a>
							<html:img page="/pages/images/pixel.gif" width="20" styleClass="imgTextMiddle"/>
						</td>

						<td id="celdaSubir">
							<html:img page="/pages/images/pixel.gif" border="0" />
							<a class="etiquetaAzul12Bold" href="javascript:subir()" >
								<html:img page="/pages/images/caja_subir.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.subir" titleKey="archigest.archivo.subir"/>
								&nbsp;<bean:message key="archigest.archivo.subir"/>
							</a>
							<html:img page="/pages/images/pixel.gif" width="20" styleClass="imgTextMiddle"/>

						</td>

						<td id="celdaBajar">
							<html:img page="/pages/images/pixel.gif" border="0" />
							<a class="etiquetaAzul12Bold" href="javascript:bajar()">
								<html:img page="/pages/images/caja_bajar.gif" styleClass="imgTextMiddle"
									altKey="archigest.archivo.bajar" titleKey="archigest.archivo.bajar"/>
								&nbsp;<bean:message key="archigest.archivo.bajar"/>
							</a>
							<html:img page="/pages/images/pixel.gif" width="20" styleClass="imgTextMiddle"/>
						</td>

						</c:if>
					</tr>
				</table>
			</tiles:put>
			<tiles:put name="blockContent" direct="true">
				<div class="separador10">&nbsp;</div>
				<display:table name="pageScope.listaNiveles" id="nivelesArchivo" htmlId="listaNiveles" style="width:98%;margin-left:auto;margin-right:auto">
					<display:column style="width:40px">
						<c:choose>
							<c:when test="${!empty nivelesArchivo.nuevoElemento and  nivelesArchivo.nuevoElemento == 'true'}" >
								<input type="radio" name="orden" value='<c:out value="${nivelesArchivo.orden}"/>' onclick="seleccionarElemento(<c:out value="${nivelesArchivo.orden}"/>,true)"
									<c:if test="${ordenSeleccionado==nivelesArchivo.orden}">
										checked="true"
									</c:if>
								/>

								<html:img page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"
								altKey="archigest.archivo.nuevo" titleKey="archigest.archivo.nuevo" style="margin-bottom:10%"/>
							</c:when>
							<c:otherwise>
								<input type="radio" name="orden" value='<c:out value="${nivelesArchivo.orden}"/>' onclick="seleccionarElemento(<c:out value="${nivelesArchivo.orden}"/>,false)"
									<c:if test="${ordenSeleccionado==nivelesArchivo.orden}">
										checked="true"
									</c:if>
								/>
							</c:otherwise>
						</c:choose>
					</display:column>

					<display:column titleKey="archigest.archivo.deposito.nOrden" style="width:70px" property="orden"/>

					<display:column titleKey="archigest.archivo.nombre" >
							<c:url var="infoNivelURL" value="/action/gestionNivelesArchivo">
								<c:param name="method" value="editarNivelArchivo"/>
								<c:param name="ordenNivel" value="${nivelesArchivo.orden}" />
							</c:url>

						<a href="<c:out value="${infoNivelURL}" escapeXml="false" />" class="tdlink">
							<c:out value="${nivelesArchivo.nombre}"  />
						</a>
					</display:column>

					<display:column property="descripcion" titleKey="archigest.archivo.descripcion"/>

					<display:column titleKey="archigest.archivo.nivelesArchivo.archivosAsociados" style="width:140px">
						<c:url var="URL" value="/action/gestionNivelesArchivo">
							<c:param name="method" value="listaArchivosAsociados"/>
							<c:param name="idNivel" value="${nivelesArchivo.id}" />
						</c:url>

						<c:if test="${nivelesArchivo.archivoAsociado==true}">
							<a href="<c:out value="${URL}" escapeXml="false" />" class="tdlink">
								<html:img page="/pages/images/building_go.gif" styleClass="imgTextMiddle"
								altKey="archigest.archivo.nivelesArchivo.verArchivos" titleKey="archigest.archivo.nivelesArchivo.verArchivos" />
							</a>
						</c:if>

					</display:column>
				</display:table>
				<div class="separador10">&nbsp;</div>
			</tiles:put>
		</tiles:insert>
	</tiles:put>

</tiles:insert>
	</html:form>

