






<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<base href="http://10.229.36.15:8080/AdminApp/ "/>
<link rel="stylesheet" type="text/css" href="include/css/adminApp.css"/>

<script language="javascript">
var appBase = '/AdminApp';
var id = '1';
var tipo = '1';
function properties()
{
	if (tipo == 1 ) // repositorios
		parent.edicion.location.href = appBase + '/volume/repositoryProperties.do?id='+id;
	else
		parent.edicion.location.href = appBase + '/volume/listProperties.do?id='+id;
}
function edit()
{
	if (tipo == 1 ) // repositorios
		parent.edicion.location.href = appBase + '/volume/repositoryEdit.do?id='+id;
	else
		parent.edicion.location.href = appBase + '/volume/listEdit.do?id='+id;

	
}

function volNew()
{
	parent.edicion.location.href = appBase + '/volume/volumeNew.do?idRep='+id;
}


function volProperties(id)
{
	parent.edicion.location.href = appBase + '/volume/volumeProperties.do?id='+id;
	
}
function volEdit (id)
{
	parent.edicion.location.href = appBase + '/volume/volumeEdit.do?id='+id;
}

</script>

<style type="text/css">
<!--
a:link {
	font-size: 10pt;
	color: #5888b8;
}
a:hover {
background-color: #639ACE;
color: #ffffff;
}
.encabezado1 {
	font-size: 10pt; color: #5888b8; font-weight: bold;
}

table.tableBase{
	border-collapse: collapse;
	border: 1px solid #000000;
	margin-top: 5px;
	background-color: #D9E6F2;
	border-color: #008BD7;
}
th{
	background: #ACC9E3;
	color: #5888b8;
	
}
td{
	font: 11px verdana, arial, helvetica, sans-serif;
}

-->
</style>
</head>
<body>
	<div class="encabezado1">
	
		
			Volúmenes del repositorio: 
		
		
	
	FTPWARDA
	<br>
	</div>
	<a href="javascript:properties();">Propiedades</a>
	<a href="javascript:edit();">Editar</a>

<table border class="tableBase">

	<tr>
		<th>Nombre</th>
		<th>Capacidad(MB)</th>
		<th>Ocupacion(MB)</th>
		<th>Ficheros</th>
		<th>Estado</th>

		<th colspan="2">&nbsp;</th>
	</tr>
	
	
	<tr>
		<td>VOL1</td>
		<td>500</td>
		<td>29,33</td>				
		<td>44</td>

		<td>Disponible</td>
		<td><a href="javascript:volProperties('1');">Propiedades</a></td>
		<td><a href="javascript:volEdit('1');">Editar</a></td>
	</tr>
	
	
	<tr>
		<td>DDD</td>
		<td>500</td>

		<td>0</td>				
		<td>0</td>
		<td>No Disponible, Sólo lectura</td>
		<td><a href="javascript:volProperties('2');">Propiedades</a></td>
		<td><a href="javascript:volEdit('2');">Editar</a></td>
	</tr>
	
	
	<tr>

		<td>vol3</td>
		<td>500</td>
		<td>0</td>				
		<td>0</td>
		<td>No Disponible, Sólo lectura, Lleno</td>
		<td><a href="javascript:volProperties('3');">Propiedades</a></td>

		<td><a href="javascript:volEdit('3');">Editar</a></td>
	</tr>
	 
	 
	<tr> 
		<td colspan="7"><a href="javascript:volNew();">Nuevo Volumen</a></td>	
	</tr>
	
	</table>
</body>
</html>