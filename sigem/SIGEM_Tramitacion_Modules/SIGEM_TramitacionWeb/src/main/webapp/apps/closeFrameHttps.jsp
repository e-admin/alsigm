<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ page import="ieci.tecdoc.sgm.core.config.ports.PortsConfig"%>
<html:html>
	<head>
	</head>
	<body>
		<c:set var="_refresh" value="${param['refresh']}"/>
		<jsp:useBean id="_refresh" type="java.lang.String" />
		<script language="Javascript">
			if (window.location.protocol == 'https:'){
				var newURL = "http://" +window.location.hostname + ":"+ <%="'"+PortsConfig.getHttpPort()+"'"%>+ window.location.pathname + window.location.search;
				window.location.href = newURL;
			}
			<ispac:hideframe refresh='<%=_refresh%>'/>
		</script>
	</body>
</html:html>