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


<div id="contenido" class="move" >
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="forms.task.envioSesion" /></h4>
<div class="acciones_ficha"><a href="#" id="btnCancel"
	onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'
	class="btnCancel"><bean:message key="common.message.close" /></a></div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>

<div class="cuerpo_ficha">
<div class="seccion_ficha">
	<div class="cabecera_seccion">
		<c:set var="titleKey" value="${requestScope['titleKey']}"/>
		<bean:define id="titleKey" name="titleKey" type="java.lang.String"></bean:define>
		<h4><bean:message  key='<%=titleKey%>'  /></h4>
	</div>


	<bean:define name="Formatter" id="formatter" type="ieci.tdw.ispac.ispaclib.bean.BeanFormatter"/>

											<display:table name="ResultsList"
														   id="result"
											  			   requestURI="/sendToSesion.do"
														   export='<%=formatter.getExport()%>'
												   		   class='<%=formatter.getStyleClass()%>'
														   sort='<%=formatter.getSort()%>'
														   pagesize='<%=formatter.getPageSize()%>'
														   defaultorder='<%=formatter.getDefaultOrder()%>'
														   defaultsort='<%=formatter.getDefaultSort()%>'>

												<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

											<!-- Si es un enlace con parametros -->
					           					<logic:equal name="format" property="fieldType" value="LINKPARAM">
												  	<display:column titleKey='<%=format.getTitleKey()%>'
												  					media='<%=format.getMedia()%>'
												  					headerClass='<%=format.getHeaderClass()%>'
												  					sortable='<%=format.getSortable()%>'
												  					sortProperty='<%=format.getPropertyName()%>'
												  					decorator='<%=format.getDecorator()%>'
												  					class='<%=format.getColumnClass()%>'>

												  		<html:link action='<%=format.getUrl()%>' styleClass='<%=format.getStyleClass()%>'
												  			name="format" property='<%=format.prepareLinkParams(result)%>'>

															<%=format.formatProperty(result)%>

												  		</html:link>

													 </display:column>

													  	<display:column titleKey='<%=format.getTitleKey()%>'
													  					media='csv excel xml pdf'
													  					headerClass='<%=format.getHeaderClass()%>'
													  					sortable='<%=format.getSortable()%>'
													  					sortProperty='<%=format.getPropertyName()%>'
													  					class='<%=format.getColumnClass()%>'>
															<%=format.formatProperty(result)%>
													  	</display:column>

													</logic:equal>

									   				<logic:equal name="format" property="fieldType" value="LIST" >

														<display:column titleKey='<%=format.getTitleKey()%>'
																		media='<%=format.getMedia()%>'
													  					headerClass='<%=format.getHeaderClass()%>'
													  					sortable='<%=format.getSortable()%>'
													  					sortProperty='<%=format.getPropertyName()%>'
																		decorator='<%=format.getDecorator()%>'
													  					comparator='<%=format.getComparator()%>'
													  					class='<%=format.getColumnClass()%>'>

															<%=format.formatProperty(result)%>

														</display:column>

									   				</logic:equal>

									   				<logic:equal name="format" property="fieldType" value="DATE">

														<display:column titleKey='<%=format.getTitleKey()%>'
																		media='<%=format.getMedia()%>'
													  					headerClass='<%=format.getHeaderClass()%>'
													  					sortable='<%=format.getSortable()%>'
													  					sortProperty='<%=format.getPropertyName()%>'
																		decorator='<%=format.getDecorator()%>'
													  					comparator='<%=format.getComparator()%>'
													  					class='<%=format.getColumnClass()%>'>

															<%=format.formatProperty(result)%>

														</display:column>

									   				</logic:equal>

												</logic:iterate>

											</display:table>


</div>
<%--seccion ficha --%> </div>
<%--fin cuerpo ficha --%></div>
<%--fin  ficha --%>
<div><%--fin contenido --%> <script>



positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});

function selectExp(numexp) {

	window.parent.location.href = 'sendToSesion.do?method=relate&numexp=' + numexp;
	}

</script>