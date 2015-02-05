<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title>sigem</title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script language="javascript">
function eliminar(iduser){
	var url = '<html:rewrite page="/eliminarUsuario.do"/>';
	var formulario = document.getElementById('formulario');
	var user = document.getElementById('idUser');
	if(confirm("<bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.message4"/>")){
        formulario.action = url;
		user.value=iduser;
		formulario.submit();
	}
	return;
}

function modificar(iduser){
	var url = '<html:rewrite page="/consultarUsuario.do"/>';
	var formulario = document.getElementById("formulario");
	var user = document.getElementById('idUser');
    formulario.action = url;
	user.value=iduser;
	formulario.submit();
	return;
}

function nuevo(){
	var url = '<html:rewrite page="/altaUsuario.do"/>';
	var formulario = document.getElementById("formulario");
    formulario.action = url;
	formulario.submit();
	return;
}

</script>
<form action="" id="formulario" method="post">
	<input name="user" type="hidden" value="" id="idUser"/>
</form>

<div id="contenedora">
  <!-- Inicio Cabecera -->
  <div id="cabecera">
  	
    <div id="logo">
    	<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
    	<img src="img/logo.gif" alt="sigem" />
    </div>
    
    <div class="salir"><img src="img/exit.gif" alt="salir" width="26" height="20" class="icono" /><span class="titular"><a href="<html:rewrite page="/desconectar.do"/>"><bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.exit"/></a></span> </div>
  </div>
  <div class="usuario">
    <div class="usuarioleft">
      <p><bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.title"/></p>
    </div>
    <div class="usuarioright">
    </div>
  </div>
  <div id="migas">
  </div>
  <!-- Fin Cabecera -->
  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
       <div class="cuadro" style="height:50px">
       	<html:form action="/buscarUsuarios.do">
       		<table border="0" cellspacing="2" cellpadding="2">
       			<tr>
       				<td width="10%">&nbsp;</td>
       				<td width="20%"><bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.usuario.usuario"/>:</td>
       				<td width="20%"><html:text property="finduser"></html:text></td>
       				<td width="20%">
       					<html:submit styleId="boton"><bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.search"/></html:submit>
					    <input type="button" name="Modificar" id="boton" onclick="nuevo()" value="<bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.new"/>"/>
       				</td>
       				<td width="30%">&nbsp;</td>
       			</tr>
       		</table>
       	</html:form>
       </div>

                    <div class="cuadro">

						<display:table name="usuarios" uid="usuario"
							pagesize="10"
							requestURI="buscarUsuarios.do"
							export="false"
							class="tablaListado"
							sort="list">
								<display:column property="user" titleKey="ieci.tecdoc.sgm.autenticacion.admin.struts.list.user.title" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="name" titleKey="ieci.tecdoc.sgm.autenticacion.admin.struts.list.name.title" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="lastname" titleKey="ieci.tecdoc.sgm.autenticacion.admin.struts.list.lastname.title" sortable="true" headerClass="cabeceraTabla"/>
								<display:column property="email" titleKey="ieci.tecdoc.sgm.autenticacion.admin.struts.list.email.title" sortable="true" headerClass="cabeceraTabla"/>
								<display:column media="html" headerClass="cabeceraTabla">
								    <input type="button" name="Eliminar" id="boton" onclick='eliminar("<bean:write name="usuario" property="user"/>")' value='<bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.delete"/>'/>
								    <input type="button" name="Modificar" id="boton" onclick='modificar("<bean:write name="usuario" property="user"/>")' value="<bean:message key="ieci.tecdoc.sgm.autenticacion.admin.struts.modify"/>"/>
								</display:column>
						</display:table>

                    </div>
      </div>
    </div>
    <div class="cuerpobt">
      <div class="cuerporightbt">
        <div class="cuerpomidbt"></div>
      </div>
    </div>
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
</body>
</html>