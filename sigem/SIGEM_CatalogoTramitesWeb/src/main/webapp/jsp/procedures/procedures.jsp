<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html:html>

<head>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%> 
	<base href="<%= basePath %>" />

	<link rel="stylesheet" href="css/estilos.css" type="text/css" />

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
	<![endif]-->	
	
	<!--[if IE 7]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
	<![endif]-->	

	<script language="javascript">
        function openDocsWnd()
        {
           window.open('<html:rewrite page="/procedureDocuments.do"/>', "docs");
        }
        
        function manageDocs()
        {
           document.getElementById("proc").style.display = "none";
           document.getElementById("docs").style.display = "block";
        }
        
        function modificaDocumentos(operacion, codigo, descripcion)
        {
        	if (operacion == "nuevo") 
	           document.getElementById("docs").contentWindow.nuevo(codigo, descripcion);
	        else 	if (operacion == "modificar") 
	           document.getElementById("docs").contentWindow.modificar(codigo, descripcion);
	        else 	if (operacion == "eliminar") 
	           document.getElementById("docs").contentWindow.eliminar(codigo);
        }

        function manageProc()
        {
           document.getElementById("proc").style.display = "block";
           document.getElementById("docs").style.display = "none";
     }

	function addDoc(value,id){
	   document.getElementById("proc").contentWindow.addDoc(value,id);
	}
		
	function estoyEnFrame() { return; }
    </script>
</head>

<body onload="openDocsWnd()">


	<div id="contenedora">
		<jsp:include flush="true" page="../Cabecera.jsp"></jsp:include>

		<div class="centered">
		<div class="contenedor_centrado">

<!-- 		<iframe name="proc" id="proc" src='<html:rewrite page="/procedure.do"/>' frameborder="0" scrolling="no" width="100%" height="100%"
      		style="position: relative; margin:0px; padding:0px; width:100%; height:600px; display:block;"></iframe>
-->

 		<iframe name="proc" id="proc" src='<html:rewrite page="/procedure.do"/>' frameborder="0" scrolling="no"
      		class="frameProcs"></iframe>
	
	    	<iframe name="docs" id="docs" frameborder="0" scrolling="no" src="jsp/blank.html" class="frameProcs"
	        style="display:none;"></iframe>
	      
		<iframe name="work" id="work" frameborder="0" tabindex="-1" src="jsp/blank.html"
	        style="position:absolute; left:0px; top:100px; width:0px; height:0px; "></iframe>

	    	</div>
    		</div>
    	</div>

</body>
</html:html>