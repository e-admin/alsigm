<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
	<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->

		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->

    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<c:set var="action">
   <c:out value="${requestScope['ActionDestino']}"/>
</c:set>
<jsp:useBean id="action" type="java.lang.String"/>
<div id="contenido" class="move" >
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="notificaciones.title" /></h4>
<div class="acciones_ficha"><a href="#" id="btnCancel"
	onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'
	class="btnCancel"><bean:message key="common.message.close" /></a></div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>

<div class="cuerpo_ficha">
<div class="seccion_ficha">
<c:choose>
	<c:when test="${!empty requestScope[error]}">
		<div class="errorRojo">
				<img height="1" src='<ispac:rewrite href="img/error.gif"/>'/>
				<bean:message key="exception.idsNotificacionAutomatic.empty"/>

		</div>
	</c:when>
	<c:otherwise>
		<logic:notPresent name="notificacionesGeneradas">
			<logic:notPresent name="notificacionesNOgeneradas">
				<img height="1" src='<ispac:rewrite href="img/error.gif"/>'/>
				<bean:message key="notificados.empty"/>
			</logic:notPresent>
		</logic:notPresent>
	</c:otherwise>
</c:choose>


<logic:present name="notificacionesGeneradas">
	<div class="cabecera_seccion">
				<h4><bean:message key="notificaciones.participantes.generadas"/></h4>
	</div>


<display:table name="notificacionesGeneradas"
	id="participante" export="true" class="tableDisplay" sort="list"
	pagesize="20"  requestURI='<%=action%>'
	decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">

	<display:column titleKey="participante.ndoc" headerClass="sortable"
		sortable="true">

			<bean:write name="participante" property="property(NDOC)" />

	</display:column>
	<display:column titleKey="participante.nombre" headerClass="sortable"
		sortable="true">

			<bean:write name="participante" property="property(NOMBRE)" />

	</display:column>

</display:table>
</logic:present>
<logic:present name="notificacionesNOgeneradas">
	<div class="cabecera_seccion">
		<h4><bean:message key="notificaciones.participantes.noGeneradas"/></h4>
	</div>

	<display:table name="notificacionesNOgeneradas"
		id="participante" export="true" class="tableDisplay" sort="list"
		pagesize="20"  requestURI='<%=action%>'
		decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">

		<display:column titleKey="participante.ndoc" headerClass="sortable"
			sortable="true">

				<bean:write name="participante" property="property(NDOC)" />

		</display:column>
		<display:column titleKey="participante.nombre" headerClass="sortable"
			sortable="true">

				<bean:write name="participante" property="property(NOMBRE)" />

		</display:column>

	</display:table>
</logic:present>
</div>
<%--seccion ficha --%> </div>
<%--fin cuerpo ficha --%></div>
<%--fin  ficha --%>
<div><%--fin contenido --%> <script>



positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});

</script>