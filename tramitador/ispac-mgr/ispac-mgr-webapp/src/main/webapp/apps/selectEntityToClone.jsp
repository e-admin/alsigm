<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

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

</head>
<body>

<div id="contenido" class="move">
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
				<h4><bean:message key="select.entityToClone.title" /></h4>
				<div class="acciones_ficha">
					 <input type="button"
						value='<bean:message key="common.message.cancel"/>' class="btnCancel"
						onclick='<ispac:hideframe/>' />
				</div><%--fin acciones ficha --%>
			</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>
		
		<div class="cuerpo_ficha">
			<div class="seccion_ficha">
			<html:errors/>
				<logic:present name="ItemList">
		
					<bean:define id="displayTagOrderParams" name="displayTagOrderParams" />
				
					<!-- DisplayTag con formateador -->
					<display:table name="ItemList" id="item" export="false"
						class="tableDisplay" sort="list" pagesize="40"
						requestURI='<%=request.getContextPath() + "/selectEntityToClone.do"%>'>
				
						<logic:iterate name="Formatter" id="format"
							type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
				
							<logic:equal name="format" property="fieldType" value="LINK">
				
								<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									sortProperty='<%=format.getPropertyName()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
				
									<% 
																	  		String SEP2 = "?";
																	  		if ((format.getUrl() != null) 
																	  				&& (format.getUrl().indexOf("?") > 0)) {
																	  			SEP2 = "&";
																	  		}
																	  	%>
				
									<html:link action='<%=format.getUrl()+SEP2+displayTagOrderParams%>'
										styleClass='<%=format.getStyleClass()%>'
										paramId='<%=format.getId()%>' paramName='item'
										paramProperty='<%=format.getPropertyLink() %>'>
				
										<%=format.formatProperty(item)%>
				
									</html:link>
				
								</display:column>
				
							</logic:equal>
				
							<logic:equal name="format" property="fieldType" value="DATA">
				
								<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
				
									<%=format.formatProperty(item)%>
				
								</display:column>
				
							</logic:equal>
				
						</logic:iterate>
				
					</display:table>
		
				</logic:present>
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