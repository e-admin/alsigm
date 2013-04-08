<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<div id="contenedor_ficha_centrada">
<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
  <TR>
    <TD class="etiquetaAzul12Bold" height="25px">
		Información de Errores
	</TD>
    <TD align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
       <TD>
       	<tiles:insert definition="button.closeButton" flush="false" />
	   </TD>
     </TR>
	</TABLE>
	</TD>
  </TR>
</TABLE>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<div class="separador1">&nbsp;</div>

<div class="bloque"> <%--primer bloque de datos --%>
	<table width="90%" cellspacing="0" cellpadding="2">
	  <tr>
		<td class="etiquetaAzul12Bold">
			<bean:message key="global.error.body"/>
		</td>
	  </tr>
	  <tr>
		<td>
			<archivo:errors />
		</td>
	  </tr>
	  <tr>
		<td class="etiquetaAzul11Normal">
			<c:set var="excepcion" value="${requestScope[appConstants.common.EXCEPCION_KEY]}" />
			<c:if test="${!empty excepcion.message}">
				- <c:out value="${excepcion.message}" />
			</c:if>
		</td>
	  </tr>
	</table>

	<div class="separador8">&nbsp;</div>

	<table width="90%" cellspacing="0" cellpadding="1">
  	  <c:set var="stackElements" value="${excepcion.stackTrace}" />
	  <c:forEach var="stackElement" items="${stackElements}">
	  <tr>
		<td class="etiquetaAzul11Normal">
			- <c:out value="${stackElement}" />
		</td>
	  </tr>
	  </c:forEach>
	</table>

</div> <%--transferencias --%>



<div class="separador5"></div>

</div> <%--contenido --%>
</div> <%--cuerpo --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--modulo --%>
</div> <%--conteiner --%>

</DIV> <%--Contenedor 2--%>
</DIV> <%--Contenedor 1--%>
<script language="">
if (window.top.hideWorkingDiv){
	hideWorkingDiv();
}
</script>
