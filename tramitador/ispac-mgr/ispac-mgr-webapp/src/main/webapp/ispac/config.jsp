<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html>
	<head>
		<title><bean:message key="head.title"/></title>
		<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
	</head>
	<body>
		<ispac:rewrite id="appletsDir" href="../applets"/>
		
		<applet code="ieci.tdw.applets.applauncher.AppLauncherApplet"
				archive='<%=appletsDir + "/applauncherapplet.jar"%>'
				width='0' height='0'>
			<param name="config" value="true"/>
		</applet>
	</body>
</html>