<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html:html>
<head>

<ieci:baseInvesDoc/>
<link rel="stylesheet" rev="stylesheet" href="include/css/tabs.css">
<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>

<html:messages id="msg" message="true" bundle="general_errors">
	<script>
	window.alert("<bean:write name='msg'/>" );
	</script>
</html:messages>

<script language='javascript'>
	var permisos = new Array();
	permisos[0] = "document.getElementById('idocConsulta');"
	permisos[1] = "document.getElementById('idocModificacion');"
	permisos[2] = "document.getElementById('idocCreacion');"
	permisos[3] = "document.getElementById('idocBorrado');"
	permisos[4] = "document.getElementById('idocImpresion');"
	
	function valida(obj){
	
	if ( obj.name == 'idocModificacion')
		{
		if (obj.checked == true){
			eval (permisos[2]).disabled = false; // Habilitar creacion
			eval (permisos[3]).disabled = false; // habilitar borrado
			}
		else
			{
			eval (permisos[2]).disabled = true; // Desabilitar creacion
			eval (permisos[3]).disabled = true; // y borrado
			}			
		}
	else if (obj.name == 'idocCreacion' || obj.name=='idocBorrado'){
		if (!eval (permisos[2]).checked && !eval (permisos[3]).checked ) // si no esta pulsado creacion y borrado
			eval (permisos[1]).disabled=false; // desabilitar modificacion
		else
			eval (permisos[1]).disabled=true;
				
		}
	else if (obj.name == 'idocConsulta'	){
		if (obj.checked == false){ // desabilita todos los eltos
			for (i = 1; i <= 4; i ++ )
			{
				temp = eval(permisos[i]);
				temp.checked = false;
				temp.disabled = true
			}
		}
		else
			{ // habilita modificacion e impresion
				eval(permisos[1]).disabled = false;
				eval(permisos[4]).disabled = false;
				valida(eval(permisos[2]));
				valida(eval(permisos[3]));			
			}
			
		}
	}
	
	function carga()
	{
		//for (i = 1; i <= 4; i ++ )
			//valida (eval(eltos[i]));
			valida (eval(permisos[1]));
			valida (eval(permisos[2]));
			valida (eval(permisos[3]));
			valida (eval(permisos[4]));
			
	} // fin carga()
		
	function habilitar (obj)
	{
		obj.disabled = false;
	}
	
	function habilitarTodo()
	{
		for (i = 0; i <= 4; i ++ )
			habilitar ( eval(permisos[i]));
	}

	function cancel()
	{
		document.location.href="<html:rewrite forward='cancel'/>";
	}
	
	function confirmDelete()
	{
		return confirm('<bean:message key="message.confirm.delete.group"/>');
	}	
			
</script> 

</head>

<body onload="choosebox(1,9); carga();">
<div class="titulo" > Grupos - Editar </div>
<div id="capaFondo" class="capaFondo"> </div>
<html:form action="/user/ldap/groupEdit" onsubmit="habilitarTodo()" method="post" >

<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,9)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tbody><tr>
	<td class="tableft" height="24" width="8"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td class="tabmiddle1" id="tabmiddle1">Permisos</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</tbody></table>
</div>

<div id="box1">
			<table border="0" width="95%">
				<tr>
					<td><html:checkbox property="idocConsulta" onclick="valida(this);" styleId="idocConsulta" /></td>
					<td>Consulta</td>
				</tr>
				<tr>
					<td><html:checkbox property="idocModificacion" onclick="valida(this);" styleId="idocModificacion" /></td>
					<td>Modificación</td>
				</tr>				
				<tr>
					<td><html:checkbox property="idocCreacion" onclick="valida(this);" styleId="idocCreacion"/></td>
					<td>Creación</td>
				</tr>				
				<tr>
					<td><html:checkbox property="idocBorrado" onclick="valida(this);" styleId="idocBorrado"/></td>
					<td>Borrado</td>
				</tr>				
				<tr>
					<td><html:checkbox property="idocImpresion" onclick="valida(this);" styleId="idocImpresion"/></td>
					<td>Impresion</td>
				</tr>					
				</table>
			<br><br>
</div>

<html:hidden property="submitted" value="true"/>
<html:hidden property="guid"/>
<html:submit value="Aceptar" styleClass="boton1"/>

<input type="button" value="Cancelar" class="boton3" onclick="cancel();" />

</html:form>


<html:form action="/user/ldap/groupDelete" method="post" onsubmit="return confirmDelete();" >
<html:hidden property="guid" />
<html:submit value="Eliminar" styleClass="boton2"/>
</html:form>

</body>
</html:html>