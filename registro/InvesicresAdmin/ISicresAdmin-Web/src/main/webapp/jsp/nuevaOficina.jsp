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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/tabsOficina.js"></script>

<script>
	function init() {
		choosebox(1,2);
		tabout(2);
		tabout(3);
	}

	var urlSessionExpired = '<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REGISTRO) %>';

	function checquearSessionBuscarDepartamentos( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			abreDepartamentos( url );
		} else {
			window.document.location.href = urlSessionExpired;
		}
	}

	function checquearSessionBuscarGrupoLdap( url ) {
		var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false") {
			abreLdap( url );
		} else {
			window.document.location.href = urlSessionExpired;
		}
	}

	function selectTreeNode(nombre, id){
  		window.document.forms[0].nombreDept.value = nombre;
  		window.document.forms[0].deptId.value = id;
	}

	function enviarFormulario(url){
		var msgError = '<bean:message key="ieci.tecdoc.sgm.rpadmin.error.mensaje.intercambioRegistral.validate"/>';

		if(validateEntidadRegistralOficina(msgError)){
			llamadaAction(url);
		}
	}

</script>
</head>
<body onload="init()">
<html:form action="/nuevaOficina.do">
<html:hidden property="tipoDepartamento"/>
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
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nueva"/>
			</div>
			<div class="col" align="right">
				<table>
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<logic:equal name="oficinaForm" property="tipoDepartamento" value="2">
						<td class="col_guardar" onclick="enviarFormulario('<html:rewrite page="/guardarNuevaOficinaLdap.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					</logic:equal>
					<logic:notEqual name="oficinaForm" property="tipoDepartamento" value="2">
						<td class="col_guardar" onclick="enviarFormulario('<html:rewrite page="/guardarNuevaOficina.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					</logic:notEqual>

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
							<logic:equal name="oficinaForm" property="tipoDepartamento" value="1">
								<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.departamento"/>&nbsp;&nbsp;</td>
							</logic:equal>
							<td class="txt">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<html:text property="nombreDept" readonly="true" styleClass="textInput" maxlength="32" style="width:190px"/>
											<html:hidden property="deptId"/>
										</td>
										<td>
											<logic:equal name="oficinaForm" property="tipoDepartamento" value="2">
												<a href="#" onclick="checquearSessionBuscarGrupoLdap('<html:rewrite page="/jsp/buscarLdapGroupPopup.jsp"/>')"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a>
											</logic:equal>
											<logic:equal name="oficinaForm" property="tipoDepartamento" value="1">
												<a href="#" onclick="checquearSessionBuscarDepartamentos('<html:rewrite page="/jsp/buscarDepartamentosPopup.jsp"/>')"><img src="<html:rewrite page="/img/ico_buscar.gif"/>" border="0"/></a>
											</logic:equal>
										</td>
									</tr>
								</table>
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
							<td><html:text property="codigo" styleClass="textInput" maxlength="3"/></td>
							<td rowspan="2" valign="top" class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.sello"/>&nbsp;&nbsp;</td>
							<td rowspan="2" valign="top"><html:textarea property="sello" styleClass="textInput" style="height:48px"/></td>
						</tr>
						<tr class="col">
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.nombre"/>&nbsp;&nbsp;</td>
							<td><html:text property="nombre" styleClass="textInput" maxlength="32"/></td>
						</tr>
						<tr class="col">
							<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.oficinas.abreviatura"/>&nbsp;&nbsp;</td>
							<td><html:text property="abreviatura" styleClass="textInput" maxlength="12"/></td>
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
