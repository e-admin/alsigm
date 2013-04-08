<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-menu.tld" prefix="menu"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html:html>
<head>
	<title><bean:message key="message.common.title"/></title>
	
    <ieci:baseInvesDoc/>
	<link rel="stylesheet" type="text/css" href="include/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>
	<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<script type="text/javascript" >
var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
function cargaUsuarios()
{
	parent.data.location.replace(appBase + "/adminUser.do");
	//parent.header.document.getElementById('status').innerHTML='Inicio | Usuarios';
}
function cargaArchivadores()
{
	activaPestanhaArchivadores ();
	parent.header.document.getElementById('status').innerHTML='Inicio | Archivadores';
	parent.data.location.replace(appBase + "/archive/adminArchive.jsp");
}

function cargaVolumenes()
{
	parent.data.location.replace(appBase + "/volume/adminVolume.jsp");
	parent.header.document.getElementById('status').innerHTML='Inicio | Volumenes';
}

function activaPestanhaUsuarios ()
{
	document.getElementById("img1").src='include/images/subme3_on.gif';
	document.getElementById("img2").src='include/images/subme3_on_of.gif';
	document.getElementById("img3").src='include/images/subme3_of_of.gif';
	document.getElementById("img4").src='include/images/subme3_of_0.gif';
	document.getElementById("lista1").className='submen1on';
	document.getElementById("lista2").className='submen1off';
	document.getElementById("lista3").className='submen1off';
}

function activaPestanhaVolumenes ()
{
	document.getElementById("img1").src='include/images/subme3_off.gif';
	document.getElementById("img2").src='include/images/subme3_of_on.gif';
	document.getElementById("img3").src='include/images/subme3_on_of.gif';
	document.getElementById("img4").src='include/images/subme3_of_0.gif';
	document.getElementById("lista1").className='submen1off';
	document.getElementById("lista2").className='submen1on';
	document.getElementById("lista3").className='submen1off';
}

function activaPestanhaArchivadores ()
{
	document.getElementById("img1").src='include/images/subme3_off.gif';
	document.getElementById("img2").src='include/images/subme3_of_of.gif';
	document.getElementById("img3").src='include/images/subme3_of_on.gif';
	document.getElementById("img4").src='include/images/subme3_on_0.gif';
	document.getElementById("lista1").className='submen1off';
	document.getElementById("lista2").className='submen1off';
	document.getElementById("lista3").className='submen1on';	
}


<%-- 
function cargaTest()
{
	parent.header.document.getElementById('status').innerHTML='Inicio | Test';
	parent.data.location.replace(appBase + "/test/TestTags.jsp");
}




function procesa(obj)
{
	var appBase = '<c:out value="${pageContext.request.contextPath}"/>';
	// Actualiza la barra de Navegacion de la aplicacion
	if (obj.style.display == 'block'){
		parent.header.document.getElementById('status').innerHTML='Inicio | ' + obj.id;
		
    }
    else{ 
		parent.header.document.getElementById('status').innerHTML='Inicio ';
		}
	// Actualizar options para ocultarlas si estuvieran mostradas
	//parent.options.location.replace("/idoc/pages/layouts/options.jsp");
		
  	// Contrae los demas menus al pulsar un item
	if (obj.id == 'Usuarios'){
		if (obj.style.display == 'block'){ // Cargar en el frame central la gestion de usuarios
			parent.data.location.replace(appBase + "/adminUser.do");
		}
		else
			parent.data.location.replace(appBase + "/blank.do");
		
		menu = document.getElementById('Almacenamiento');
		if (menu.style.display == 'block'){ // si esta abierto
			menu.style.display = 'none'; // cerrarlo
		}
		menu = document.getElementById('Archivadores');
		if (menu.style.display == 'block'){ // si esta abierto
			menu.style.display = 'none'; // cerrarlo
		}
	}
	else if (obj.id == 'Almacenamiento'){
		menu = document.getElementById('Usuarios');
		if (menu.style.display == 'block'){ // si esta abierto
			menu.style.display = 'none'; // cerrarlo
		}
		menu = document.getElementById('Archivadores');
		if (menu.style.display == 'block'){ // si esta abierto
			menu.style.display = 'none'; // cerrarlo
		}
	}
	else if (obj.id == 'Archivadores'){
		menu = document.getElementById('Almacenamiento');
		if (menu.style.display == 'block'){ // si esta abierto
			menu.style.display = 'none'; // cerrarlo
		}
		menu = document.getElementById('Usuarios');
		if (menu.style.display == 'block'){ // si esta abierto
			menu.style.display = 'none'; // cerrarlo
		}
	}
	return true;
	
}
// Funcion llamada desde los submenu, actualiza la barra de navegacion y 
// recarga el frame de options

function carga(opcion)
{
	barraNavegacion = parent.header.document.getElementById('status').innerHTML;
    var r = 0;
    var pos = 0;
    for (i = 0; i < barraNavegacion.length; i ++)
    	{
    	if (barraNavegacion.charAt(i)=='|'){
    		r++;
    		pos=i;
    		}
    	}
    if (r==1)
    	barraNavegacion += " | "+opcion;  		
    else if (r == 2)
	   	barraNavegacion = barraNavegacion.substring(0,pos) + " | " + opcion;
    		
	parent.header.document.getElementById('status').innerHTML = barraNavegacion;
   	parent.options.location.replace("/idoc/pages/layouts/options.jsp?page="+opcion);
   
}
--%>
</script>
</head>

<body onload="cargaUsuarios();">
	<div class=contenedora align="center">
    	<div class="cuerpo" style="width:840px">
      		<div class="cuerporight">
        		<div class="cuerpomid">
        			<div class="submenu3">
           				<ul>
        					<li id="lista1" class="submen1on" onclick="cargaUsuarios();"><img id="img1" src="include/images/subme3_on.gif" />
        						<bean:message key="Usuarios"/>
        						<img id="img4" src="include/images/subme3_on_0.gif" />
        					</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- >table width="100%">
<c:if test="${sessionScope.user.hasAccessUser}">
<tr>
	<td class="menuItem" onclick="cargaUsuarios();">
		Usuarios
	</td>
</tr>
</c:if>
<c:if test="${sessionScope.user.hasAccessVol}">
<tr>
	<td class="menuItem" onclick="cargaVolumenes();">Volumenes</td>
</tr>
</c:if>

<c:if test="${sessionScope.user.hasAccessSys}">
<tr>
	<td class="menuItem" onclick="cargaArchivadores();">Archivadores</td>
</tr>
</c:if>
</table -->


</body>


</html:html>