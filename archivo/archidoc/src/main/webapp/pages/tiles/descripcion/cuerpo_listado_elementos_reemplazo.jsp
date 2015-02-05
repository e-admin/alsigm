<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<c:set var="elementos" value="${sessionScope[appConstants.fondos.LISTA_ELEMENTOS_CF]}"/>

<div id="contenedor_ficha">
	<div class="ficha">
		<h1><span>
		<div class="w100">
		<table class="w98" cellpadding="0" cellspacing="0">
		<tr>
			<td class="etiquetaAzul12Bold" height="25px">
				<bean:message key="archigest.descripcion.reemplazo.simple.listadoElementosAfectados"/>
			</td>
			<td align="right">
				<tiles:insert definition="button.closeButton" flush="true" />
			</td>
		</tr>
		</table>
		</div>
		</span></h1>
		
		<div class="cuerpo_drcha">
		<div class="cuerpo_izda">
			<div id="barra_errores"><archivo:errors /></div>
			<div class="separador1">&nbsp;</div>
			
			<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>
			<div class="bloque">
				<tiles:insert page="../descripcion/campo_listado_reemplazo_elementos.jsp" flush="true"/>
			</div>
		
		</div> <%-- cuerpo_izda --%>
		</div> <%-- cuerpo_drcha --%>
		
		<h2><span>&nbsp;</span></h2>
	
	</div> <%-- ficha --%>
</div> <%-- contenedor_ficha --%>