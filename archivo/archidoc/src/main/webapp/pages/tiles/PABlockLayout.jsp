<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div class="separador1">&nbsp;</div>
<div class="cabecero_bloque_sin_height"> <%-- cabecero segundo bloque de datos --%>
<TABLE class="w98m1" cellpadding=0 cellspacing=0>
  <TBODY>
  <TR>
    <TD class="etiquetaAzul12Bold">
		<c:set var="blockTitle"><tiles:insert attribute="blockTitle" ignore="true" flush="false" /></c:set>
		<c:out value="${blockTitle}" escapeXml="false" />
	</TD>
	<TD>
		<c:url var="pixelURL" value="/pages/images/pixel.gif"/>
		<img src="<c:out value="${pixelURL}"/>" height="18" class="imgTextMiddle">
	</TD>
    <TD align="right">
	<%--
		En la pagina llamante habra que hacer una definition con una plantilla de button bar
		(en un principio unicamente la normal (que muestra imagenes y texto en tootip) en la
		que se hace un putList de los botones a mostrar (puede haber en el tiles-defs una lista
		de todos los botones disponibles). La plantilla esa recorre la lista de botones y la pinta y
		finalmente esta pagina situa la button bar aqui en su sitio.
	--%>
		<tiles:insert attribute="buttonBar" ignore="true"  flush="false" />
	</TD>
  </TR></TBODY></TABLE>
</div> <%-- cabecero bloque --%>

<DIV class="bloque"> <%-- bloque de datos --%>
	<tiles:insert attribute="blockContent" ignore="true"  flush="false" />
</div>