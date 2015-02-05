<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>' />
<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->

<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->

<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->

<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<c:set var="action">
	<c:out value="generateDocumentsBloque.do?method=selectParticipantes" />
</c:set>
<jsp:useBean id="action" type="java.lang.String" />
<div id="contenido" class="move">
<div class="ficha"><html:form
	action="generateDocumentsBloque.do?method=generateDocumentsBloque"
	styleClass="formWithOutMarginPadding">
	<div class="encabezado_ficha">
	<div class="titulo_ficha">
	<h4><bean:message key="forms.listdoc.generateDocsEnBloque" /></h4>
	<div class="acciones_ficha">
	<c:if test="${empty requestScope['error']}">
		<a href="#" id="btnOk"
			onclick='javascript:generateDocument()' class="btnOk"> <bean:message
			key="common.message.ok" /> </a>
	</c:if>
	<a href="#" id="btnCancel"
		onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'
		class="btnCancel"><bean:message key="common.message.close" /></a></div>
	<%--fin acciones ficha --%></div>
	<%--fin titulo ficha --%></div>
	<%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
	<div class="seccion_ficha"><c:choose>
		<c:when test="${!empty requestScope['error']}">
			<div class="errorRojo"><img height="1"
				src='<ispac:rewrite href="img/error.gif"/>' /> <bean:message
				key="errors.generateDocumentsBloque" /></div>
		</c:when>

	</c:choose> <logic:present name="participantes">
		<div class="cabecera_seccion">
		<h4><bean:message
			key="forms.task.select.participantes.generateDocument" /></h4>
		</div>

		<c:set var="_templateId"><c:out value="${param.templateId}" default="" /></c:set>
		<jsp:useBean id="_templateId" type="java.lang.String" />
		<c:set var="_documentTypeId"><c:out value="${param.documentTypeId}" default="" /></c:set>
		<jsp:useBean id="_documentTypeId" type="java.lang.String" />
		<input type="hidden" name="templateId" value='<%=_templateId%>' />
		<input type="hidden" name="documentTypeId" value='<%=_documentTypeId%>' />

		<display:table name="participantes" id="participante" export="true"
			class="tableDisplay" sort="list" pagesize="20"
			requestURI='<%=action%>'
			decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">


			<display:column
				title='<%="<input type=\'checkbox\' id=\'allCheckbox\' onclick=\'javascript:_checkAll(document.defaultForm.multibox, this);\'/>"%>'>

				<html:multibox property="multibox">
					<bean:write name="participante" property="property(ID)" />
				</html:multibox>
			</display:column>
			<display:column titleKey="forms.participantes.nombre" headerClass="sortable"
				sortable="true">

				<bean:write name="participante" property="property(NOMBRE)" />
			</display:column>
			<display:column titleKey="forms.participantes.rol" headerClass="sortable"
				sortable="true">
				<bean:write name="participante" property="property(ROL)" />
			</display:column>
			<display:column titleKey="forms.participantes.nif_cif" headerClass="sortable"
				sortable="true">
				<bean:write name="participante" property="property(NDOC)" />
			</display:column>
		</display:table>

	</logic:present>

	</div>
	<%--seccion ficha --%></div>
	<%--fin cuerpo ficha --%></div>
<%--fin  ficha --%>
<div>


</html:form><%--fin contenido --%>
<ispac:layer id="waitInProgress"  msgKey="msg.layer.operationInProgress" showCloseLink="false" styleClassMsg="messageShowLayerInFrame" />  <script>


function generateDocument() {
	var data = checkboxElement(document.forms["defaultForm"].multibox);
	if (data != "") {
		document.defaultForm.target = "_self";

		showLayer("waitInProgress");
		document.defaultForm.submit();
	} else {
		jAlert('<bean:message key="forms.tasks.participantes.multibox.empty.message"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
	}

}
positionMiddleScreen('contenido');

	$(document).ready(function(){
		$("#contenido").draggable();

	});

</script>