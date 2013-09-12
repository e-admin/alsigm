<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title><bean:message key="head.title"/></title>
	<meta http-equiv="Content Type" content="text/html; charset=iso-8859-1" />
	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos.css"/>'/>
	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/styles.css"/>'/>

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
	<![endif]-->

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'/>
	<![endif]-->

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'/>
	<![endif]-->

	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
 	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
	<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
	<ispac:javascriptLanguage/>

</head>

<body>

	<div id="contenido" class="move">

		<div class="ficha">

			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="forms.taskRegESList.title"/></h4>
					<div class="acciones_ficha">
						<a href="#" id="btnClose" class="btnClose"><bean:message key="common.message.close"/></a>
					</div>
					<div class="acciones_ficha">
						<a href="#" id="btnReturn" class="btnReturn"><bean:message key="common.message.return"/></a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">

				<%-- ERROR --%>
				<%--
						<div class="seccion_ficha popUp">
							<p class="error">
								<span><html:errors/></span>
							</p>
						</div>
 				--%>

					<div class="seccion_ficha">
						<%--
						<div class="cabecera_seccion">
							<h4><bean:message key="__registro.salida.creado"/></h4>
						</div>
						--%>
						<logic:empty name="RegisterESTaskList">
							<h4><bean:message key="forms.taskRegESList.empty"/></h4>
						</logic:empty>
						<logic:notEmpty name="RegisterESTaskList">
							<display:table name="RegisterESTaskList"
										   id="object"
										   export="true"
										   class="tableDisplay"
										   sort="list"
										   pagesize="15"
										   requestURI=''>
								<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
									<logic:equal name="format" property="fieldType" value="LINK">
										<display:column titleKey='<%=format.getTitleKey()%>'
														media='<%=format.getMedia()%>'
														sortable='<%=format.getSortable()%>'
														sortProperty='<%=format.getPropertyName()%>'
														decorator='<%=format.getDecorator()%>'
														class='<%=format.getColumnClass()%>'>
											<c:url value="annexIntrayProcess.do" var="link">
												<c:param name="method" value="selectTypeDocTask"/>
												<c:param name="stageId" value="${stageId}"/>
												<c:param name="taskPcdId" >
													<bean:write name="object" property="property(ID_TASK)"/>
												</c:param>
												<c:param name="numexp" >
													<bean:write name="object" property="property(NUMEXP)"/>
												</c:param>
												<c:param name="taskId" >
													<bean:write name="object" property="property(ID)"/>
												</c:param>
												<c:param name="tp_reg" value="${requestScope.registerType}"/>
												<c:param name="register" value="${requestScope.register}"/>
											</c:url>
											<table border="0" width="100%" cellpadding="1" cellspacing="1">
												<tr>
													<td width="23px" height="17px" align="center">
														<img src='<ispac:rewrite href="img/tramiteestado1.gif"/>'/>
													</td>
													<td>
														<nobr>
														<a href='<c:out value="${link}"/>' class="displayLink">
															<%=format.formatProperty(object)%>
														</a></nobr>
													</td>
												</tr>
											</table>
										</display:column>
									 </logic:equal>
									<logic:equal name="format" property="fieldType" value="LIST">
										<display:column titleKey='<%=format.getTitleKey()%>'
														media='<%=format.getMedia()%>'
														class='<%=format.getColumnClass()%>'
														sortable='<%=format.getSortable()%>'
														decorator='<%=format.getDecorator()%>'>

											<nobr><%=format.formatProperty(object)%></nobr>
										</display:column>
									</logic:equal>
								</logic:iterate>
							</display:table>
						</logic:notEmpty>

					<!--  Capa para presentar el mensaje de Operacion en progreso al seleccionar un tramite -->
					<div id="inProgress" style="display:none; text-align: center">
						<b><bean:message key="msg.layer.operationInProgress" /></b>
					</div>

					</div> <!-- fin seccion ficha-->

			</div> <!-- fin cuerpo_ficha -->

		</div> <!-- fin ficha -->

	</div>  <!-- fin contenido -->
</body>
</html>


	<script type="text/javascript">

	positionMiddleScreen('contenido', 50 );
		$(document).ready(function() {
			$("#contenido").draggable();
			$("#btnReturn").click(function() {
				history.back();
			});
			$("#btnClose").click(function() {
				<ispac:hideframe/>
			});
		});

	</script>