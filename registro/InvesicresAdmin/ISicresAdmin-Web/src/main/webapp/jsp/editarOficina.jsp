<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page import="ieci.tecdoc.isicres.rpadmin.struts.util.AutenticacionAdministracion" %>
<%@ page import="es.ieci.tecdoc.isicres.admin.core.services.ConstantesGestionUsuariosAdministracion" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/calendar-win2k-1.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsOficina.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script>

	var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';

	function init() {
		choosebox(1,2);
		tabout(2);
		tabout(3);
	}


	function enviarFormulario(){
		var msgError = '<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.intercambioRegistral.validate"/>';

		if(validateEntidadRegistralOficina(msgError)){
			llamadaActionId('<html:rewrite page="/guardarEdicionOficina.do"/>', '<bean:write name="id" />');
		}
	}

</script>
</head>
<body onload="init()">
<html:form action="/editarOficina.do">
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="oficinas"/>
		  </jsp:include>
          <div class="cuadro">
          	<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar"/>&nbsp;
				<span class="migasElementoSeleccionado"><bean:write name="oficinaForm" property="nombre"/></span>&nbsp;
			</div>
			<div class="col" align="right">
				<table>
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td class="col_guardar" onclick="enviarFormulario()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td class="col_eliminar" onclick="llamadaAction('<html:rewrite page="/listadoOficinas.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
				</table></div>
 			<div class="cuerpomidUsuarios">

		      <div class="submenuUsuario" id="nuevoUsuarioPestana" >
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle1" id="tabmiddle1" onclick="nuevaOficinaClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficina.nuevo"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,2)">
									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle2" id="tabmiddle2" onclick="direccionOficinaClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.oficina.direccion"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td>
								<logic:equal value="true" scope="session" name="enabledIntercambioRegistral">
									<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)">
								</logic:equal>
								<logic:notEqual value="true" scope="session" name="enabledIntercambioRegistral">
									<div id="tab3" onmouseover="tabover(3)" onmouseout="tabout(3)" onclick="choosebox(3,2)" style="visibility:hidden;">
								</logic:notEqual>

									<table summary="" border="0" cellpadding="0" cellspacing="0">
									<tbody><tr>
										<td class="tableft" height="17" width="7">
											<img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
										<td class="tabmiddle3" id="tabmiddle3" onclick="intercambioRegistralOficinaClick()"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.intercambioRegistral"/></td>
										<td class="tabright"><img src="img/dot.gif" alt="" border="0" height="17" width="7"/></td>
									</tr>
									</tbody></table>
								</div>
							</td>
							<td></td>
						</tr>
						</table>
				</div>


				<html:hidden property="fechaVista"/>
				<html:hidden property="fechaBajaVista"/>

	          <div class="cuadroUsuario">
				<div style="height: 200px;">
					<div id="nuevaOficina">
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" rowspan="80"></td>
							<td class="col" height="20" colspan="2"></td>
							<td width="100" rowspan="30" ></td>
							<td class="col" height="20" colspan="2"></td>
						</tr>
						<tr class="col">
							<logic:equal name="oficinaForm" property="tipoDepartamento" value="2">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.distribucion.grupo"/>&nbsp;&nbsp;</td>
							</logic:equal>
							<logic:notEqual name="oficinaForm" property="tipoDepartamento" value="2">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.departamento"/>&nbsp;&nbsp;</td>
							</logic:notEqual>
							<td class="txt">
								<logic:equal name="oficinaForm" property="tipoDepartamento" value="2">
									<html:hidden property="deptId"/>
									<html:text property="ldapDescription" styleClass="textInput" readonly="true" style="color:#808080"/>
								</logic:equal>
								<logic:notEqual name="oficinaForm" property="tipoDepartamento" value="2">
									<html:hidden property="deptId"/>
									<html:select property="deptId" styleId="id" styleClass="textInput" disabled="true">
										<html:optionsCollection name="departamento" property="lista" value="codigo" label="descripcion"/>
									</html:select>
								</logic:notEqual>
							</td>
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.tipo"/>&nbsp;&nbsp;</td>
							<td>
								<html:select property="idTipoOficina" styleId="idTipoOficina" styleClass="textInput">
									<html:optionsCollection name="tipoOficina" property="lista" value="codigo" label="descripcion"/>
								</html:select>
							</td>
						</tr>
						<tr class="col">
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.codigo"/>&nbsp;&nbsp;</td>
							<td><html:text property="codigo" styleClass="textInput" readonly="true" style="color:#808080"/></td>
							<td class="txt" valign="top" rowspan="2"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.sello"/>&nbsp;&nbsp;</td>
							<td rowspan="2" valign="top">
								<html:textarea property="sello" styleClass="textInput" style="height:48px"/>
							</td>
						</tr>
						<tr class="col">
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.nombre"/>&nbsp;&nbsp;</td>
							<td><html:text property="nombre" styleClass="textInput" maxlength="32"/></td>
						</tr>
						<tr>
							<td class="txtCol"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.abreviatura"/>&nbsp;&nbsp;</td>
							<td class="txtCol"><html:text property="abreviatura" styleClass="textInput" maxlength="12"/></td>
						</tr>
						<tr>
							<td colspan="5" style="visibility:hidden">
								<html:select property="idOrgs" styleId="idOrgs" styleClass="textInput">
									<html:optionsCollection name="entidadRegistral" property="lista" value="codigo" label="descripcion"/>
								</html:select>
							</td>
						</tr>
						<tr><td height="100%" colspan="4"></td></tr>
					</table>
					</div>

					<div id="direccionOficina" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="2"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col" colspan="2"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.domicilio"/>&nbsp;&nbsp;</td>
								<td><html:text property="direccion" styleClass="textInput" maxlength="255"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.uri"/>&nbsp;&nbsp;</td>
								<td><html:text property="uri" styleClass="textInput" maxlength="255"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.ciudad"/>&nbsp;&nbsp;</td>
								<td><html:text property="ciudad" styleClass="textInput" maxlength="100"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.fax"/>&nbsp;&nbsp;</td>
								<td><html:text property="fax" styleClass="textInput" maxlength="160"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.codigoPostal"/>&nbsp;&nbsp;</td>
								<td><html:text property="codigoPostal" styleClass="textInput" maxlength="10"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.telefono"/>&nbsp;&nbsp;</td>
								<td><html:text property="telefono" styleClass="textInput" maxlength="160"/></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.direccion.provincia"/>&nbsp;&nbsp;</td>
								<td><html:text property="provincia" styleClass="textInput" maxlength="100"/></td>
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.representante"/>&nbsp;&nbsp;</td>
								<td><html:text property="representante" styleClass="textInput" maxlength="160"/></td>
							</tr>
							<tr><td height="100%" colspan="4"></td></tr>
						</table>
					</div>

					<div id="intercambioRegistralOficina" style="display:none">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td width="10" rowspan="80"></td>
								<td class="col" height="20" colspan="2"></td>
								<td width="100" rowspan="30" ></td>
								<td class="col" colspan="2"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.intercambio.codigo"/>&nbsp;&nbsp;</td>
								<td class="txt">
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<html:hidden property="idEntidadRegistral"/>
												<html:text property="codEntidadReg" styleClass="textInput" maxlength="21" style="width:190px"/>&nbsp;&nbsp;
											</td>
											<td>
												<a href="#" onclick="javascript:chequearSessionBusquedaDC('<html:rewrite page="/jsp/iFrameBusquedaOficinasDirectorioComun.jsp"/>', urlSessionExpired);"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a>
											</td>
										</tr>
									</table>
								</td>
								<td colspan="4"></td>
							</tr>
							<tr class="col">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.intercambio.nombre"/>&nbsp;&nbsp;</td>
								<td><html:text property="nameEntidadReg" styleClass="textInput" maxlength="80" style="width:450px;"/></td>
								<td colspan="4"></td>
							</tr>
							<tr>
								<td height="100%" colspan="4"></td>
							</tr>
						</table>
					</div>

				</div>
	          </div>
          </div>
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
</html:form>
</body>
</html>
