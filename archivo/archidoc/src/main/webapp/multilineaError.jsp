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
		<td>
			<archivo:errors />
		</td>
	  </tr>
	  <tr>
		<td class="etiquetaAzul11Normal">
			<c:set var="mensaje" value="${requestScope[appConstants.common.LABEL_MENSAJE_MULTILINEA]}"/>
			<bean:message name="mensaje"/>&nbsp;
		</td>
	  </tr>
	</table>

	<div class="separador8">&nbsp;</div>

	<table width="90%" cellspacing="0" cellpadding="1">
	  <form>
		<c:set var="lineas" value="${requestScope[appConstants.common.LINEAS_MENSAJE_MULTILINEA]}"/>
  	  	<textarea disabled="disabled" rows="10" cols="80"><c:forEach var="linea" items="${lineas}"><c:out value="${linea}"/>
</c:forEach></textarea>
	  </form>
	</table>

	<div class="separador8">&nbsp;</div>
</div> 



<div class="separador5"></div>

</div> <%--contenido --%>
</div> <%--cuerpo --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--modulo --%>
</div> <%--conteiner --%>

</DIV> <%--Contenedor 2--%>
</DIV> <%--Contenedor 1--%>
	