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

		<!--  scrips de @firma cargados en este orden, en otro orden en algunas ocasiones generar errores -->
		<script type="text/javascript" language="javascript" src="afirma/common-js/deployJava.js"></script>
		<script type="text/javascript" language="javascript" src="afirma/common-js/instalador.js"></script>
		<script type="text/javascript" language="javascript" src="afirma/common-js/firma.js"></script>
		<script type="text/javascript" language="javascript" src="afirma/constantes.js"></script>

		<!-- javascript referente al proceso de compulsa -->
		<script type="text/javascript" language="javascript" src="scripts/compulsa.js"></script>




	</head>


	<body >
		<!-- el applet de @firma debe cargarse justamente en este punto, sino puede dar errores -->
		<script type="text/javascript">
			base = getBase() + '/afirma' ;
			baseDownloadURL=base;
			cargarAppletFirma('COMPLETA');
		</script>

		<!-- applet de escaneo-->
		<APPLET MAYSCRIPT="true" id="appletScanCompulsa" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.scan.applet.IdocAppletLauncher"
			ALT="If you could run this applet, you'd see some animation"
			ARCHIVE="applets/fwktd-scan-applet-0.7.jar;jsessionid=<%=session.getId()%>">
			<param name="java_arguments"  value="-Xmx512m"/>
			<param name="returnJSFunction" value="compulsar"/>
		</APPLET>

		<!--applet de conversion de ficheros, se usara para convertir Tiff2Pdf y Bmp2Pdf -->
		<applet id="appletConverter" code="es.com.ieci.invesicres.conversion.JISDocConverter.class" codebase="plugins/" height="1" width="1" alt="Conversión a PDF" name="converter" MAYSCRIPT VIEWASTEXT>
			<param name="archive" value="ISPlugins.jar;jsessionid=<%=session.getId()%>">
		</applet>

		<APPLET MAYSCRIPT="true" id="appletSendFiles" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.sendfiles.applet.SendFilesApplet"
			ALT="If you could run this applet, you'd see some animation"
			ARCHIVE="applets/fwktd-sendFiles-applet-0.8.jar;jsessionid=<%=session.getId()%>">
			<PARAM name="SESSIONID" value="<%=session.getId()%>"/>
			<PARAM name="codebase_lookup" value="false"/>
		</APPLET>


		<script type="text/javascript">

			//instancioamos la clase de compulsa en el obejto compulsa
			var idLibroP="<%= request.getParameter("idLibro") %>";
			var idRegistroP="<%= request.getParameter("idRegistro") %>";
			var sessionPIdP="<%= request.getParameter("sessionPId")%>";
			var compulsa = new CompulsarClass(document.getElementById("appletScanCompulsa"),document.getElementById("appletConverter"), clienteFirma ,document.getElementById("appletSendFiles"),idLibroP,idRegistroP,sessionPIdP);

			//llamamos al proceso de escaneo de compulsa
			compulsa.escanear();

			//declaramos esta funcion que usara el objeto javascript para compulsar, se necesita porque sino el applet necesita declarar la funcion a la que invocar
			function compulsar(files){
				compulsa.compulsarFicheros(files);

			}
		</script>

 	</body>
</html>


