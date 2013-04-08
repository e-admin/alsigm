<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<bean:struts id="mapping" mapping="/gestionNivelesArchivo" />
<c:set var="formName" value="${mapping.name}" />
<c:set var="formulario" value="${requestScope[formName]}" />
<c:set var="listaArchivosAsociados" value="${sessionScope[appConstants.controlAcceso.LISTA_ARCHIVOS_ASOCIADOS]}" />

<script language="javascript">
function actualizar(){
	document.getElementById("formulario").submit();
}

function eliminar(){
	if (confirm("<bean:message key='archigest.archivo.descripcion.textoTablasValidacion.delete.confirm.msg'/>")){ 
		document.getElementById("method").value="eliminar";
		document.getElementById("formulario").submit();
	}
}
function call(form, method)
{
	form.action += "?method="+method;
	form.submit();
}
</script>

<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
	<tiles:put name="boxTitle" direct="true">
		<bean:message key="NavigationTitle_ARCHIVOS_NIVELES_EDICION"/>
	</tiles:put>
	
	<tiles:put name="buttonBar" direct="true">
		<table cellpadding=0 cellspacing=0>
			<tr>
		      	  <td>
					<a class="etiquetaAzul12Bold" href="javascript:actualizar()" >
						<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
					&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>	      	  
		      	  </td>
				  <td width="10">&nbsp;</td>	      	  
		      	  <td>
					<c:url var="volver2URL" value="/action/gestionNivelesArchivo">
						<c:param name="method" value="listaNivelesArchivo" />
					</c:url>
			   		<a class=etiquetaAzul12Bold href="javascript:call(document.forms['nivelesArchivoForm'],'goBack')">
						<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
			   		 	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			   		</a>
			   </td>
			 </tr>
		</table>
	</tiles:put>
	
	
	<tiles:put name="boxContent" direct="true">	
		<html:form action="/gestionNivelesArchivo" styleId="formulario">
			<input type="hidden" name="method" id="method" value="actualizar" />
			<html:hidden property="id"/>
			<html:hidden  property="orden"/>
			<div id="barra_errores"><archivo:errors/></div>
			<div class="bloque">
				<table class="formulario" width="99%;margin-left:auto;margin-right:auto;margin-bottom:7px;">
				<tr>
					<td class="tdTitulo" width="120px">
						<bean:message key="archigest.archivo.nombre"/>:&nbsp;
					</td>
					<td class="tdDatos"><html:text  property="nombre" styleClass="input60" maxlength="64"/></td>
				</tr>
				<tr>
					<td class="tdTitulo" width="120px">
						<bean:message key="archigest.archivo.descripcion"/>:&nbsp;
					</td>
					<td class="tdDatos"><html:text  property="descripcion" styleClass="input99" maxlength="254"/></td>
				</tr>
				</table>
			</div>
		</html:form>
	</tiles:put>

</tiles:insert>
	

