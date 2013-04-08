
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>




<html>
<head>
</head>
<body>
<%
	String seleccionado = (String)request.getAttribute("tipoDireccion");
	String esTercero= (String)request.getAttribute("esTercero");

%>
<script type="text/javascript">

e=top.document.getElementById('tipoDireccion');

seleccionado= '<%=seleccionado%>'
esTercero= '<%=esTercero%>'

if(top.document.getElementById('tipoDireccionVacio')){
top.document.getElementById('tipoDireccionVacio').style.display='none';
}

if(top.document.getElementById('estadoNotificacionVacio')){
top.document.getElementById('estadoNotificacionVacio').style.display='none';
}



if(seleccionado=='P'){


	top.document.getElementById('tipoDireccionT').style.display='none';
	top.document.getElementById('estadoNotificacionP').style.display='block';
	top.document.getElementById('estadoNotificacionT').style.display='none';
	

	if(esTercero=='true'){
	top.document.getElementById('tipoDireccionTercero').style.display='block';
	top.document.getElementById('tipoDireccionP').style.display='none';
	}
	else{
	top.document.getElementById('tipoDireccionP').style.display='block';
	top.document.getElementById('tipoDireccionTercero').style.display='none';
	}


}
else if(seleccionado=='T'){


	top.document.getElementById('tipoDireccionP').style.display='none';
	top.document.getElementById('estadoNotificacionP').style.display='none';
	top.document.getElementById('estadoNotificacionT').style.display='block';
	


	if(esTercero=='true'){
	top.document.getElementById('tipoDireccionTercero').style.display='block';
	top.document.getElementById('tipoDireccionT').style.display='none';
	}
	else{
	top.document.getElementById('tipoDireccionT').style.display='block';
	top.document.getElementById('tipoDireccionTercero').style.display='none';
	}

}
else{

	if (top.document.getElementById('tipoDireccionP')) {
		top.document.getElementById('tipoDireccionP').style.display='none';
	}
	
	if (top.document.getElementById('tipoDireccionT')) {
		top.document.getElementById('tipoDireccionT').style.display='none';
	}
	
	if (top.document.getElementById('estadoNotificacionP')) {
		top.document.getElementById('estadoNotificacionP').style.display='none';
	}
	
	if (top.document.getElementById('estadoNotificacionT')) {
		top.document.getElementById('estadoNotificacionT').style.display='none';
	}
	
	if (top.document.getElementById('estadoNotificacionVacio')) {
		top.document.getElementById('estadoNotificacionVacio').style.display='block';
	}
	
	if(top.document.getElementById('tipoDireccionVacio')){
		top.document.getElementById('tipoDireccionVacio').style.display='block';
	}

	if(esTercero=='true'){
		if(top.document.getElementById('tipoDireccionTercero')){
			top.document.getElementById('tipoDireccionTercero').style.display='block';
		}
		if(top.document.getElementById('tipoDireccionVacio')){
			top.document.getElementById('tipoDireccionVacio').style.display='none';
		}
	} else{
		if(top.document.getElementById('tipoDireccionVacio')){
			top.document.getElementById('tipoDireccionVacio').style.display='block';
		}
		if(top.document.getElementById('tipoDireccionTercero')){
			top.document.getElementById('tipoDireccionTercero').style.display='none';
		}
	}
}

</script>

<ispac:hideframe event="false"/>

</body>
</html>