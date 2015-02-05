<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="actionMapping" mapping="/gestionNivelesCuadro" />
<c:set var="tiposNivel" value="${sessionScope[appConstants.fondos.LISTA_TIPOS_NIVEL_KEY]}"/>
<c:set var="listaFichas" value="${sessionScope[appConstants.controlAcceso.LISTA_FICHAS]}"/>
<c:set var="puedeSerNivelInicial" value="${sessionScope[appConstants.fondos.PUEDE_SER_NIVEL_INICIAL]}"/>
<c:set var="mostrarSubtipos" value="${sessionScope[appConstants.fondos.MOSTRAR_SUBTIPOS]}"/>
<c:set var="listaSubtipos" value="${sessionScope[appConstants.fondos.LISTA_SUBTIPOS_NIVEL]}"/>
<c:set var="tipoEditable" value="${sessionScope[appConstants.fondos.TIPO_NIVEL_CUADRO_EDITABLE]}"/>

<script language="javascript">
function aceptar(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.submit();
}

function cargarDatos(){
	var form = document.forms['<c:out value="${actionMapping.name}" />'];
	form.method.value = "cargarDatos";
	form.submit();
}
</script>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
  <tr>
    <td class="etiquetaAzul12Bold" height="25px">
    	<c:choose>
			<c:when test="${!empty nivelesCuadroForm.idNivel}">
				<bean:message key="NavigationTitle_CUADRO_CLASIFICACION_NIVELES_EDICION"/>
			</c:when>
			<c:when test="${!empty nivelesCuadroForm.idNivelPadre}">
				<bean:message key="NavigationTitle_CUADRO_CLASIFICACION_NIVELES_NUEVO_HIJO"/>
			</c:when>
			<c:otherwise>
				<bean:message key="NavigationTitle_CUADRO_CLASIFICACION_NIVELES_ALTA"/>
			</c:otherwise>
		</c:choose>
    </td>
    <td align="right">
		<table cellpadding=0 cellspacing=0>
		 <tr>
	      	  <td>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
	      	  </td>
			  <td width="10">&nbsp;</td>
	      	  <td>
				<c:url var="volver2URL" value="/action/gestionNivelesArchivo">
					<c:param name="method" value="goBack" />
				</c:url>
		   		<a class=etiquetaAzul12Bold href="javascript:window.location='<c:out value="${volver2URL}" escapeXml="false"/>'">
					<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
		   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
		   		</a>
		   </td>
		 </tr>
		</table>
	</td>
  </tr>
</table>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
	<archivo:errors/>
</DIV>

<div class="separador1">&nbsp;</div>

<DIV class="bloque"> <%--segundo bloque de datos --%>
	<html:form action="/gestionNivelesCuadro" styleId="formulario">
		<input type="hidden" name="method" id="method" value="addNivelCuadro"/>
		<html:hidden  property="idNivel"/>
		<html:hidden  property="idNivelPadre"/>
		<html:hidden  property="tipoNivelPadre"/>
		<html:hidden  property="checkInicial"/>

		<table class="formulario" width="99%">
			<tr>
				<td class="tdTitulo" nowrap="nowrap" width="120px">
					<bean:message key="archigest.archivo.nombre"/>:&nbsp;
				</td>
				<td class="tdDatos"><html:text property="nombre" styleClass="input60" maxlength="64"/></td>
			</tr>
			<tr>
				<td class="tdTitulo" nowrap="nowrap">
					<bean:message key="archigest.archivo.tipo"/>:&nbsp;
				</td>
				<td class="tdDatos" nowrap="nowrap">
					<c:choose>
						<c:when test="${tipoEditable}">
							<c:set var="disabled" value="false"/>
						</c:when>
						<c:otherwise>
							<c:set var="disabled" value="true"/>
							<html:hidden  property="tipoNivel"/>
							<html:hidden  property="subtipoNivel"/>
						</c:otherwise>
					</c:choose>
					<html-el:select property="tipoNivel" onchange="cargarDatos()" disabled="${disabled}">
						<html:option value=""/>
						<html:optionsCollection name="tiposNivel" label="nombre" value="identificador"/>
					</html-el:select>&nbsp;&nbsp;&nbsp;&nbsp;

				<c:if test="${puedeSerNivelInicial}">
					<span class="td2Titulo">
						<html:checkbox property="nivelInicial" styleClass="checkbox" styleId="nivelInicial"/>
						<bean:message key="archigest.archivo.fondos.cf.nivel.inicial"/>
					</span>
				</c:if>
				</td>
			</tr>
			<c:if test="${mostrarSubtipos}">
				<tr>
					<td class="tdTitulo" nowrap="nowrap">&nbsp;</td>
					<td class="tdDatos">
						<c:forEach var="subtipo" items="${listaSubtipos}" varStatus="nSubtipo">
							<span class="td2Titulo">
								<input type="radio" name="subtipoNivel" value='<c:out value="${subtipo.codigo}"/>' class="radio"
									<c:if test="${!tipoEditable}">disabled="disabled"</c:if>
									<c:if test="${nivelesCuadroForm.subtipoNivel == subtipo.codigo}">checked="checked"</c:if>
								>	<bean-el:message key="archigest.archivo.subtipo.${subtipo.codigo}"/>
							</span>
						</c:forEach>
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="tdTitulo" nowrap="nowrap"><bean:message key="archigest.archivo.fondos.cf.niveles.fichaPref"/>:&nbsp;</td>
				<td class="tdDatos">
					<html:select property="idFichaPref">
						<html:option value=""/>
						<html:optionsCollection name="listaFichas" label="nombre" value="id"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="tdTitulo" nowrap="nowrap"><bean:message key="archigest.archivo.repositorio.ecm"/>:&nbsp;</td>
				<td class="tdDatos">
					<bean:define id="nombreCampo" value="idRepEcm" toScope="request"/>
					<tiles:insert name="lista.repositorios.ecm" flush="true"/>
				</td>
			</tr>
		</table>
	</html:form>

</div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

