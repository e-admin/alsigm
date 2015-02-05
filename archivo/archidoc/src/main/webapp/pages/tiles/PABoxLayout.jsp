<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>


<div id="contenedor_ficha" style="-moz-box-sizing: border-box;padding-bottom:25px;margin:0px">

<div class="ficha">

<h1><span>
	<div class="w100">
		<TABLE class="w98" cellpadding=0 cellspacing=0>
		  <TR>
			<TD class="etiquetaAzul12Bold" height="25px">
				<c:set var="boxTitle"><tiles:insert attribute="boxTitle" ignore="true" /></c:set>
				<c:out value="${boxTitle}" />
			</TD>
			<TD align="right">
				<tiles:insert attribute="buttonBar" ignore="true" />
			</TD>
		  </TR>
		</TABLE>
	</div>
</span></h1>


<div class="cuerpo_drcha">
<div class="cuerpo_izda" style="-moz-box-sizing: border-box;padding-left:4px;padding-right:12px">

	<div id="barra_errores">
		<archivo:errors />
	</div>
	<div id="barra_mensajes">
		<archivo:errors/>
		<archivo:messages/>
	</div>

	<tiles:insert attribute="boxContent" ignore="true" />

</div> <%-- cuerpo_izda --%>
</div> <%-- cuerpo_drcha --%>

<h2><span>&nbsp;</span></h2>

</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>