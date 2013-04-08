<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>

<bean:struts id="actionMapping" mapping="/gestionNivelesArchivo" />


<c:set var="listaNiveles" value="${sessionScope[appConstants.controlAcceso.LISTA_NIVELES_ARCHIVO_POSIBLES]}" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="form" value="${requestScope[formName]}" />


<script language="javascript">
function aceptar(){
	document.getElementById("formulario").submit();
}

function cargarArchivosReceptores(){
	document.getElementById("method").value = "cargarArchivosReceptoresAlta";
	document.getElementById("formulario").submit();
}

</script>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
  <tr>
    <td class="etiquetaAzul12Bold" height="25px">
		<bean:message key="archigest.archivo.crear"/>
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

<DIV class="bloque"> <%-- segundo bloque de datos --%>	
		<html:form action="/gestionArchivos" styleId="formulario">
		<input type="hidden" name="method" id="method" value="insertarArchivo"/>
		<html:hidden property="id"/>

			<table class="formulario" width="99%">
				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.codigo"/>:&nbsp;
					</td>
					<td class="tdDatos"><html:text  property="codigo" size="40" maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.nombre.corto"/>:&nbsp;
					</td>
					<td class="tdDatos"><html:text  property="nombrecorto" styleClass="input" size="40" maxlength="25"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos"><html:text  property="nombre" styleClass="input99" maxlength="128"/></td>
				</tr>
				
				<tr>
					<td class="tdTitulo" width="150px">
						<bean:message key="archigest.archivo.nivel"/>:&nbsp;
					</td>
					<td class="tdDatos">
					<html:select property="idnivel" styleId="idnivel" onchange="cargarArchivosReceptores()" >
							<option value=""></option>
							<c:forEach var="nivel" items="${listaNiveles}" varStatus="nNivel">
								<option value="<c:out value="${nivel.id}" />"
								<c:if test="${archivoForm.idnivel == nivel.id}">
									selected
								</c:if>
								><c:out value="${nivel.nombre}" />
							</c:forEach>
						</html:select>					
					</td>
				</tr>
				<c:if test="${appConstants.configConstants.permitirSeleccionSignaturacion}">
				<c:set var="tiposSignaturacion" value="${sessionScope[appConstants.controlAcceso.LISTA_TIPOS_SIGNATURACION_POSIBLES]}" />
				<c:if test="${!empty tiposSignaturacion}">
					<tr>
						<td class="tdTitulo" width="150px">
							<bean:message key="archigest.archivo.tipo.signaturacion"/>:&nbsp;
						</td>
						<td class="tdDatos">
							<html:select property="tiposignaturacion" styleId="tiposignaturacion">								
								<c:forEach var="signaturacion" items="${tiposSignaturacion}">
									<option value="<c:out value="${signaturacion.identificador}" />"
										<c:if test="${archivoForm.tiposignaturacion == signaturacion.identificador}">
											selected
										</c:if>
									><fmt:message key="${signaturacion.nombre}"/>
								</c:forEach>
							</html:select>					
						</td>
					</tr>
				</c:if>
				</c:if>
			</table>
			
			<c:set var="listaArchivos" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS]}" />
			<c:if test="${!empty listaArchivos}">
				
				<div class="separador10">&nbsp;</div>
				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" colspan="2">
							<bean:message key="archigest.archivo.admin.archivos.seleccione.archivo.defecto"/>
						</td>
						
					</tr>
				</table>	
				<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.listaArchivos" id="elemento" htmlId="listaNiveles" style="width:98%;margin-left:auto;margin-right:auto">
					<display:column style="width:20px">
						<input type="radio" name="idreceptordefecto" value='<c:out value="${elemento.id}"/>'
							<c:if test="${form.idreceptordefecto == elemento.id}">
								checked
							</c:if>
						>					</display:column>
					<display:column titleKey="archigest.archivo.codigo" style="width:100px" property="codigo"/>
					<display:column titleKey="archigest.archivo.nombre">
						<c:out value="${elemento.nombre}"  />
					</display:column>
				</display:table>
				<div class="separador10">&nbsp;</div>
			</c:if>	
	</html:form>

</div> <%-- bloque --%>


</div> <%-- cuerpo_drcha --%>
</div> <%-- cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>



