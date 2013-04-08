<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
	<head>
			
	</head>
	
	<body >
		
		<APPLET MAYSCRIPT="true" id="appletFileChooser" width="0" height="0" CODE="es.ieci.tecdoc.fwktd.applets.fileChooser.applet.FileChooserApplet" 
			ALT="If you could run this applet, you d see some animation"
			ARCHIVE="applets/fwktd-fileSystem-applet-0.6-SNAPSHOT.jar;jsessionid=<%=session.getId()%>">
			<param name="returnJSFunction" value="fileChooser_returnFiles"/> 
			<param name="finishLoadJSFunction" value="finishLoadJSFunctionFileChooser"/>
			
		</APPLET>
		
						
		<script type="text/javascript">
				
		var appletCargado=false;
		
		function finishLoadJSFunctionFileChooser(){
			appletCargado=true;
		}
				
		function fileChooser_returnFiles(files)
		{
			alert(files);
			
		}
		
		
		
		function waitUntilAppletLoaded(){
						
			if(appletCargado){
				appletFileChooser.selectFile();
			} else{
		
				setTimeout(waitUntilAppletLoaded,1000); 	
			}

		}

		waitUntilAppletLoaded();
				
			
		
	</script>
	
	
 	</body>
</html>


