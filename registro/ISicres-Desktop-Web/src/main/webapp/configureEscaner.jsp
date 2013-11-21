<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
	<head>
		<script language="javascript">
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');

		</script>

		<LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/font.css" />
		<LINK REL="stylesheet" TYPE="text/css" HREF="css/table.css" />

		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery-1.6.2.min.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery.blockUI.js"></script>

		<!-- javascript de internalizacion de mensajes -->
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>

		<script type="text/javascript" language="javascript" src="scripts/applet_scanner.js"></script>




	</head>


	<body >

		<!-- applet de escaneo-->
		<APPLET MAYSCRIPT="true" id="appletScanConfigure" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.scan.applet.IdocAppletLauncher"
			ALT="If you could run this applet, you'd see some animation"
			ARCHIVE="applets/fwktd-scan-applet-0.7.jar;jsessionid=<%=session.getId()%>">
			<param name="java_arguments" value="-Xmx512m"/>
		</APPLET>


		<script type="text/javascript">

			//instancioamos la clase de compulsa en el obejto compulsa
			var idLibroP="<%= request.getParameter("idLibro") %>";
			var idRegistroP="<%= request.getParameter("idRegistro") %>";
			var sessionPIdP="<%= request.getParameter("sessionPId")%>";

			function finishLoadJSFunction(){
				//llamamos al proceso de configuracion
				var appletScanConfigure=document.getElementById("appletScanConfigure");
				appletScanConfigure.configure();
			}

		</script>

 	</body>
</html>


