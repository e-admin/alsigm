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
<title>Página de búsqueda</title>
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
	src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
<ispac:javascriptLanguage/>

<%
			// Entidad de sustituto
			String entity = request.getParameter("entity");

			// Nombre la variable de sesión donde se han salvado
			// los parámetros que utilizará el updateFields
			String parameters = request.getParameter("parameters");


			String multivalueId = request.getParameter("multivalueId");

			// Action a ejecutar al seleccionar
			String setAction = request.getParameter("setAction");
			if (setAction == null) {

				setAction = "geoLocalizacion.do";
			}


			// Clave para el título de la pantalla
			String captionKey = request.getParameter("captionKey");
			if (captionKey == null) {

				captionKey = "select.substitute.title";
			}
			String formName= request.getParameter("formName");
			if (formName == null) {

				formName = "defaultForm";
			}


		%>

<ispac:rewrite id="setSubstitute" action='<%=setAction%>' />

<script language='JavaScript' type='text/javascript'><!--

			function SelectSubstitute(value, sustituto) {
				document.defaultForm.action = '<%=setSubstitute%>' + "?method=setSubstitute&value=" + value+"&sustitute="+sustituto;
				document.defaultForm.submit();
			}

		//--></script>

</head>

<body>
<div id="contenido" class="move">
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
			<logic:notPresent name="refValidada">
				<h4><bean:message key='<%=captionKey%>' /></h4>
			</logic:notPresent>
			<div class="acciones_ficha">
				<input type="button"
					value='<bean:message key="common.message.close"/>' class="btnCancel"
					onclick='<ispac:hideframe/>' />
			</div><%--fin acciones ficha --%>
		</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
<html:form action="geoLocalizacion.do"
	method="post">
				<input type="hidden" name="entity" value='<%=entity%>' />
				<input type="hidden" name="parameters" value='<%=parameters%>' />
				<input type="hidden" name="formName" value='<%=formName%>' />

				<logic:present name="refValidada">
					<label class="boldlabel"   >

					<c:set var="keyValidaRef" value="${requestScope['refValidada']}"/>
					<bean:define id="keyValidaRef" name="keyValidaRef" type="java.lang.String"/>
					<bean:message key='<%=keyValidaRef%>'/>

					</label>
				</logic:present>

				<logic:present name="SubstituteList">
				<!-- displayTag con formateador -->
				<bean:define name="Formatter" id="formatter"
					type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter" />
			<div class="tableDisplay">
				<display:table name="SubstituteList" id="substitute"
					export='<%=formatter.getExport()%>'
					class='<%=formatter.getStyleClass()%>'



					excludedParams="*">
					<logic:iterate name="Formatter" id="format"
						type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
						<logic:equal name="format" property="fieldType" value="LINK">
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								class='<%=format.getColumnClass()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>
								<bean:define name="substitute" property="property(VALOR)"
									id="value" />
									<bean:define name="substitute" property="property(SUSTITUTO)"
									id="sustitute" />
								<a class="element"
									href="javascript:SelectSubstitute('<%= value.toString() %>' , '<%= sustitute.toString() %>');">
								<%=format.formatProperty(substitute)%> </a>
							</display:column>
						</logic:equal>
						<logic:equal name="format" property="fieldType" value="LIST">
							<display:column titleKey='<%=format.getTitleKey()%>'
								media='<%=format.getMedia()%>'
								headerClass='<%=format.getHeaderClass()%>'
								sortable='<%=format.getSortable()%>'
								sortProperty='<%=format.getPropertyName()%>'
								class='<%=format.getColumnClass()%>'
								decorator='<%=format.getDecorator()%>'
								comparator='<%=format.getComparator()%>'>
								<%=format.formatProperty(substitute)%>
							</display:column>
						</logic:equal>
					</logic:iterate>
				</display:table>
				<div>

			</logic:present >
		<ispac:layer />

		</html:form>
	</div><%--seccion ficha --%>
	</div><%--fin cuerpo ficha --%>

	</div><%--fin  ficha --%>
<div><%--fin contenido --%>
</body>

</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
</script>