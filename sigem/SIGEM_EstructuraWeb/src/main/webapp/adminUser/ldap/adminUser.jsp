<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
<head>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<ieci:baseInvesDoc/>
<link rel="stylesheet" type="text/css" href="include/css/common.css"/>
<link rel="stylesheet" type="text/css" href="include/css/error.css"/>
<link rel="stylesheet" type="text/css" href="include/css/estilos.css"/>
<style>
	.menu0Ldap {
		padding: 2 6 2 6;border-color: #D3D5DD;border-width: 1px;border-style: solid;color: #636DAD;
		text-decoration: none;white-space: nowrap;width:100%;}
	.menu0Ldap:hover {color: #636DAD;text-decoration: none;padding: 2 6 2 6;
		background-color: #D7EDFA;width:100%;}
</style>
<script>
	var typeTree;
	/*
	var user;
	var pwd;
	*/
	function redirect( url )  {
	  parent.document.location.href = url;
	}
	
	function loading() {
		if( document.getElementById('iFrameTree').src != "null") {
			document.getElementById('capaCargando').style.display = "none"
			document.getElementById('capaTree').style.display = "block";
		} else {
			setTimeout("loading()", 100);
		}
	}
	
	function setAyuda(){
		parent.header.linkAyuda = '/help/adminUserLdap.htm';
	}
	
	/*
	function darAlta() {
		estirarIframePropiedades();
		emptyBotones();
		document.getElementById('iFramePropiedades').style.height= 355;
		document.getElementById("titulo").innerHTML = '<bean:message key="message.usuario.titulo.alta.aplicaciones.backoffice" />';
		document.frames['iFramePropiedades'].location.href = '<html:rewrite page="/adminUser/ldap/darDeAlta.jsp" />?user='+user;
	}
	*/
	/*
	function encogerIframePropiedades() {
		document.getElementById('iFramePropiedades').style.height= 20;
		document.getElementById('propiedades').style.visibility = "hidden";
		document.getElementById('propiedadesText').style.visibility = "hidden";
	}
	
	function estirarIframePropiedades() {
		if( typeTree != undefined ) {
			if(typeTree == "1") {
				document.getElementById('iFramePropiedades').style.height= 315;
			} else {
				document.getElementById('iFramePropiedades').style.height= 355;
			}
		} else {
			document.getElementById('iFramePropiedades').style.height= 315;
		}
		document.getElementById('propiedades').style.visibility = "visible";
		document.getElementById('propiedadesText').style.visibility = "visible";
		//document.propiedades.location.href = '<html:rewrite page="/layouts/blank.jsp"/>';
	}
	
	function emptyBotones() {
		document.getElementById("tblBotonesUsuario").innerHTML = "";
		document.getElementById('iFramePropiedades').style.height= 355;
		//document.propiedades.location.href = '<html:rewrite page="/layouts/blank.jsp"/>';
	} 
	
	function fillBotones() {
		var html = '<table cellpadding="0" cellspacing="0" border="0" width="100%"/>';
		html += '<tr>';
		html += '<td><a href="javascript:darAlta();" class="menu0Ldap">';
		html += '<bean:message key="message.usuario.nuevo.aplicaciones.backoffice" />';
		html += '</a></td>';
		html += '</tr>';
		html += '<tr><td height="3" colspan="2"></td></tr>';
		html += '<tr>';
		html += '<td><a href="javascript:darBaja();" class="menu0Ldap">';
		html += '<bean:message key="message.usuario.baja.aplicaciones.backoffice" />';
		html += '</a></td>';
		html += '</tr>';
		html += '<tr><td height="3" colspan="2"></td></tr>';
		html += '<tr>';
		html += '<td><a href="javascript:modificar();" class="menu0Ldap">';
		html += '<bean:message key="message.usuario.modificar.aplicaciones.backoffice" />';
		html += '</a></td>';
		html += '</tr>';
		html += '<tr><td height="5"></td></tr>';
		html += '</table>'
		document.getElementById("tblBotonesUsuario").innerHTML = html;
		document.getElementById('iFramePropiedades').style.height= 315;
	}
	*/
</script>
</head>
<body onload="loading();setAyuda();">
<div id="error"></div>
<div class="contenedora" align="center">
		<div class="cuerpoEO" style="width:840px;height:504px">
   			<div class="cuerporightEO">
     			<div class="cuerpomidEO"> 				
					<div class="cuadro" style="height: 504px;">
						<table cellspacing="0" cellpadding="0" border="0" style="width:780px;">
							<tr>
								<td valign="top">
									<table cellspacing="0" cellpadding="0" border="0" width="300">
										<tr>
											<td>
												<div id="capaCargando" style="width:300px; z-index:1; height:430px;display:block">
													<table cellpadding="0" cellspacing="0" style="border-right: 1px solid #000; border-left: 1px solid #000; border-top: 1px solid #000; border-bottom: 1px solid #000;" width="100%" height="430">
														<tr>
															<td height="100%" width="100%">
																<table cellpadding="0" cellspacing="0" border="0" height="100%" width="100%">
																	<tr><td height="180"></td></tr>
																	<tr>
																		<td align="center"><bean:message key="cargando"/></td>
																	</tr>
																	<tr>
																		<td align="center">
																			<img src='<html:rewrite page="/include/images/loading.gif"/>' />
																		</td>
																	</tr>
																	<tr><td height="100%"></td></tr>
																</table>
															</td>
														</tr>
													</table>
												</div>
												<div id="capaTree" style="width:300px; height:430px; z-index:1; border: 1px solid #000000; overflow: auto;display:none" >
													<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
														<tr>
															<td><iframe name="tree" id="iFrameTree" src='<html:rewrite page="/user/ldap/userTree.do"/>' frameborder="0" width="100%" height="100%"></iframe></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div id="search" style="width:300px; height:25px; z-index:4; border: 1px none #000000;	overflow: auto;">
											   		<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
														<tr>
															<td><iframe name="busqueda" src='<html:rewrite page="/user/ldap/search.do"/>' frameborder="0" width="100%" height="100%"></iframe></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td width="20">&nbsp;&nbsp;</td>
								<td valign="top">
									<table cellspacing="0" cellpadding="0" border="0" width="300">
										<tr>
											<td>
												<div id="properties" style="width:470px; height:370px; z-index:5; border: 1px none #000000;scrollbar-base-color: #f2f2f2;">
													<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
														<tr>
															<td id="propiedadesText" style="visibility:hidden;">
																
																<div id="contenedora">
																	<div class="cuerpo" style="width:470px; height:405px;">
																   		<div class="cuerporight">
																    		<div class="cuerpomid">
																    			<h1 id="titulo">
																    				<bean:message key="message.comun.etiqueta.propiedades" />
																    			</h1>
																    			<br/>
																    			<div  style="height:405px">
																    				<%--<div class="cuadro" id="tblBotonesUsuario"></div>--%> 
																					<div id="propiedades" style="position:absolute; width:450px; height:405px; z-index:1; border: 1px none #000000; margin-left: 5px">
																						<iframe name="propiedades" id="iFramePropiedades" src='<html:rewrite page="/blank.do"/>' frameborder="0" width="100%" height="100%"></iframe>	
																					</div>       		
																       			</div>
																       		</div>  				
															     		</div>
																   	</div> 	
																</div>
															
																<div class="cuerpobt" style="width:470px">
															    	<div class="cuerporightbt">
															      		<div class="cuerpomidbt"></div>
															    	</div>
																</div>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										
									</table>
								</td>
								<td width="100%"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>