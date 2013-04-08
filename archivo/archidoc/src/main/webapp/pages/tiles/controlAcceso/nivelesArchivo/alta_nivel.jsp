<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>

<bean:struts id="actionMapping" mapping="/gestionNivelesArchivo" />
<c:set var="listaNiveles" value="${sessionScope[appConstants.controlAcceso.LISTA_NIVELES_ARCHIVO]}" />



<script language="javascript">
function aceptar(){
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
		<bean:message key="NavigationTitle_ARCHIVOS_NIVELES_ALTA"/>
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
		<html:form action="/gestionNivelesArchivo" styleId="formulario">
		<input type="hidden" name="method" id="method" value="addNivelArchivo"/>
		<html:hidden  property="id"/>

			<table class="formulario" width="99%">
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

			<c:if test="${!empty listaNiveles}">
				<div class="separador10">&nbsp;</div>
				<table class="formulario" width="99%">
					<tr>
						<td class="tdTitulo" colspan="2">
							<bean:message key="archigest.archivo.admin.niveles.archivos.posicion"/>
						</td>
						
					</tr>
				</table>	
				<div class="separador5">&nbsp;</div>
				<display:table name="pageScope.listaNiveles" id="nivelesArchivo" htmlId="listaNiveles" style="width:98%;margin-left:auto;margin-right:auto">
					<display:column style="width:20px">
						<input type="radio" name="orden" value='<c:out value="${nivelesArchivo.orden}"/>'>
					</display:column>
					<display:column property="orden" titleKey="archigest.archivo.deposito.nOrden" style="width:70px"/>
					<display:column titleKey="archigest.archivo.nombre" property="nombre"/>
					<display:column property="descripcion" titleKey="archigest.archivo.descripcion"/>
				</display:table>				
				<div class="separador10">&nbsp;</div>
	
			</c:if>
			


	</html:form>

</div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>



