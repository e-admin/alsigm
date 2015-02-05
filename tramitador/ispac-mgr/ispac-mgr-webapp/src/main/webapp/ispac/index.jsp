<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
  <title><bean:message key="head.title"/></title>
  <ispac:skin/>
  <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
  <link rel="shortcut icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
  <link rel="icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>

	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab.css"/>'/>

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie5.css"/>'/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie6.css"/>'/>
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie7.css"/>'/>
	<![endif]-->	

  <script>
		window.name="ParentWindow";
  </script>

</head>
<body leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0" >


<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
	<jsp:include page="common/headerSup.jsp" />
	<jsp:include page="common/userIni.jsp" />

	<tr>
		<td height="100%" width="100%" colspan="2" align="center" valign="top">

		<div id="contenido_centrado">
		<div class="ficha_fixed">
			<div class="encabezado"><div class="left_encabezado"><h3></h3></div></div> <!-- fin encabezado -->

			<div class="cuerpo">
				<div class="contenido_cuerpo_login"> 
					<div class="seccion_login"> 
						<html:form action="login.do" focus="user">

						<html:errors/>
						<c:if test="${!empty requestScope[appConstants.actions.ACTIVE_SESSIONS]}"> 
							<c:set var="user" value="${requestScope[appConstants.actions.USER]}"/>
							<bean:define id="user" name="user" type="java.lang.String"/>
							<ul class="errores_ul">
							<li class="errores_li">
								<bean:message key="error.login.activeSessions" arg0='<%=user%>' />
							</li>
							</ul>
						</c:if>

						<h2>
							<bean:message key="index.title"/>
						</h2>
						<h3>
							<bean:message key="index.message"/>
						</h3>

						<p class="fila_sub">
							<label class="login"><bean:message key="index.user"/>:</label>
							<html:text property="user" styleClass="login"/>
						</p>

						<p class="fila_sub">
							<label class="login"><bean:message key="index.password"/>:</label>
							<html:password property="password" styleClass="login" redisplay="true"/>
						</p>

						<p class="fila">&nbsp;</p>
						<p class="fila">&nbsp;</p>

						<c:if test="${!empty requestScope[appConstants.actions.ACTIVE_SESSIONS]}"> 
							<p class="fila">
								<label class="login"><bean:message key="msg.forceExclussion"/></label>
								<html:checkbox styleClass="checkbox" property="forceExclussion" value="YES"/>
							</p>
							<%--
							<c:url var="_link" value="forceExclussion.do">
							<c:param name="${appConstants.actions.USER}" value="${requestScope[appConstants.actions.USER]}"/>
							</c:url>
							<a href='<c:out value="${_link}"/>'><bean:message key="msg.forceExclussion"/></a>
							<br/>
							--%>
							<%--
							No contemplamos por ahora esta opcion, poder loggearse aunque tengas otra sesion activa.
							<html:checkbox property="enterAnyWay" value="YES">Entrar de todas formas</html:checkbox>
							--%>
						</c:if>
						<p class="fila_right">
							<html:submit styleClass="botonFondo">
								<bean:message key="index.button"/>
							</html:submit>
						</p>
						<p class="fila">&nbsp;</p>

						</html:form>
					</div> <!-- fin seccion -->


				</div> <!-- contenido_cuerpo -->
			</div> <!-- fin cuerpo -->

		</div> <!-- fin ficha_fixed -->
		<div id="copyright">
		    <bean:message key="main.copyright"/>
	    </div>
		</div>

		</td>
	</tr>
</table>



</body>
</html>
