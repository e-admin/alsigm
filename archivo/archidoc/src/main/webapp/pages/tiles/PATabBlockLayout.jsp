<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<c:set var="visibleTabValue"><tiles:insert attribute="visible" ignore="true" /></c:set>
<c:set var="visibleTab" value="${visibleTabValue}" />

<c:set var="tabName"><tiles:insert attribute="tabName" ignore="true" /></c:set>

<DIV id="<c:out value="${tabName}" />" class="bloque_tab" style="<c:if test="${empty visibleTab || !visibleTab}">display:none;</c:if>padding-bottom:5px">

<div class="cabecero_bloque_tab"> <%-- cabecero segundo bloque de datos --%>
<TABLE class="w98m1" height="100%" cellpadding=0 cellspacing=0>
  <TBODY>
  <TR>
    <TD class="etiquetaAzul12Bold">
		<c:set var="blockTitle"><tiles:insert attribute="blockTitle" ignore="true" /></c:set>
		<c:out value="${blockTitle}" />
	</TD>
    <TD align="right">
	<%--
		En la pagina llamante habra que hacer una definition con una plantilla de button bar 
		(en un principio unicamente la normal (que muestra imagenes y texto en tootip) en la 
		que se hace un putList de los botones a mostrar (puede haber en el tiles-defs una lista 
		de todos los botones disponibles). La plantilla esa recorre la lista de botones y la pinta y 
		finalmente esta pagina situa la button bar aqui en su sitio.
	--%>
		<tiles:insert attribute="buttonBar" ignore="true" />
	</TD>
  </TR></TBODY></TABLE>
</div> <%-- cabecero bloque --%>

<tiles:insert attribute="blockContent" ignore="true" />

</div>