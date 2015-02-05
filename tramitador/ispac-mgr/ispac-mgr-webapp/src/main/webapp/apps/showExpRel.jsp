<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title><bean:message key="forms.expRelacionados.title" /></title>
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>' />
<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->

<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->

<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
<ispac:javascriptLanguage/>

</head>
<body>
<form>
<div id="contenido" class="move">
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="forms.expRelacionados.title" /></h4>
<div class="acciones_ficha"><a href="#" id="btnCancel"
	onclick='<ispac:hideframe refresh="true"/>' class="btnCancel"><bean:message
	key="common.message.close" /></a></div>
</div>
</div>

<div class="cuerpo_ficha">
<div class="seccion_ficha"><logic:messagesPresent>
	<div class="infoError"><bean:message
		key="forms.errors.messagesPresent" /></div>
</logic:messagesPresent> <logic:present name="ValueList">

	<logic:notEmpty name="ValueList">
		<bean:define name="Formatter" id="formatter"
			type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter" />

		<!-- displayTag con formateador -->
		<display:table name="ValueList"
			id="value"
			requestURI=''
			export='<%=formatter.getExport()%>'
			class='<%=formatter.getStyleClass()%>'
			sort='<%=formatter.getSort()%>'
			pagesize='<%=formatter.getPageSize()%>'
			defaultorder='<%=formatter.getDefaultOrder()%>'
			defaultsort='<%=formatter.getDefaultSort()%>'>

			<logic:iterate name="Formatter" id="format"
				type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="LINK">

					<display:column titleKey='<%=format.getTitleKey()%>'
						media='<%=format.getMedia()%>'
						sortable='<%=format.getSortable()%>'
						sortProperty='<%=format.getPropertyName()%>'
						decorator='<%=format.getDecorator()%>'
						comparator='<%=format.getComparator()%>'
						headerClass='<%=format.getHeaderClass()%>'
						class='<%=format.getColumnClass()%>'>

						<a class="tdlink"
							href="javascript:redirect(<%="'"+request.getContextPath()+"/selectAnActivity.do?numexp="%><bean:write name='value' property='property(EXP:NUMEXP)'/>');">
						<%=format.formatProperty(value)%> </a>

					</display:column>

				</logic:equal>

				<logic:equal name="format" property="fieldType" value="LIST">

					<display:column titleKey='<%=format.getTitleKey()%>'
						media='<%=format.getMedia()%>'
						sortable='<%=format.getSortable()%>'
						sortProperty='<%=format.getPropertyName()%>'
						decorator='<%=format.getDecorator()%>'
						comparator='<%=format.getComparator()%>'
						headerClass='<%=format.getHeaderClass()%>'
						class='<%=format.getColumnClass()%>'>

						<%=format.formatProperty(value)%>

					</display:column>

				</logic:equal>
				<logic:equal name="format" property="fieldType" value="DELETE">

					<logic:notEqual value="true" property="readonly" name="defaultForm">

						<display:column titleKey='<%=format.getTitleKey()%>'
							media='<%=format.getMedia()%>'
							sortable='<%=format.getSortable()%>'
							sortProperty='<%=format.getPropertyName()%>'
							decorator='<%=format.getDecorator()%>'
							comparator='<%=format.getComparator()%>'
							headerClass='<%=format.getHeaderClass()%>'
							class='<%=format.getColumnClass()%>' style="text-align:center;">

							<a
								href="javascript:sure(<%="'"+request.getContextPath()+"/deleteExpedientRelation.do?keyId="%><bean:write name='value' property='property(EXP_REL:ID)'/>','<bean:message key="ispac.action.expedient.relation.delete.msg"/>','<bean:message key="common.confirm"/>','<bean:message key="common.message.ok"/>','<bean:message key="common.message.cancel"/>')">
							<img border="0" style="cursor: pointer"
								src='<ispac:rewrite href="img/borrar.gif"/>'
								title='<bean:message key="forms.expRelacionados.delete.title"/>' />
							</a>
						</display:column>
					</logic:notEqual>

				</logic:equal>


			</logic:iterate>

		</display:table>

	</logic:notEmpty>

	<logic:empty name="ValueList">
		<bean:message key="forms.expRelacionados.noData" />
		<br>
		<br>
	</logic:empty>

</logic:present></div>
</div>
</div>
</div>


</form>
</body>
</html>

<script>
	function redirect(url){
		        top.window.location = url;
		  	}
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	</script>