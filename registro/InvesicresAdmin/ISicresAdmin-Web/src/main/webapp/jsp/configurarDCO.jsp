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
	<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.rpadmin.dco.gestion"/></title>
	<link href="<html:rewrite page="/css/estilos.css"/>" rel="stylesheet" type="text/css" />
	<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" language="javascript" src="js/jquery.blockUI.js"></script>
	<script>
		var action = '<html:rewrite page="/gestionDCO.do"/>';

		function inicializarDCO(){
			cargarCapa('<bean:message key="ieci.tecdoc.rpadmin.dco.msg.init.system"/>');
			document.forms[0].action = action + "?accion=INICIALIZAR";
			document.forms[0].submit();
		}


		function actualizarDCO(){
			cargarCapa('<bean:message key="ieci.tecdoc.rpadmin.dco.msg.update.system"/>');
			document.forms[0].action = action + "?accion=ACTUALIZAR";
			document.forms[0].submit();
		}

		function getCodOfic(idOfic) {
			idOficina = idOfic;
		}

		function cargarCapa(mensaje){
			$.blockUI.defaults.css.border = 'none';
			$.blockUI.defaults.css.backgroundColor = "#ffffff";
			$.blockUI.defaults.overlayCSS.backgroundColor = "#ffffff";
			$.blockUI({ message: null });

			$.blockUI({ message: '<img src="./img/loading.gif" /><br><nobr><bean:message key="ieci.tecdoc.rpadmin.dco.msg.wait"/><br>' + mensaje + '</nobr>'});
		}

		function ocultarCapa(){
			 $.unblockUI();
		}

	</script>
</head>

<body onload="ocultarCapa();">
<form name="gestionDCO" action="" method="post">
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="configuracionDCO"/>
		  </jsp:include>
          <div class="cuadro">
          	<div id="migas">
				<bean:message key="ieci.tecdoc.rpadmin.dco.gestion"/>
			</div>
			<div class="col" align="right">
				<table cellspacing="2" cellpadding="2" border="0" width="100%">
					<tr>
						<td width="100%" align="left">
							<jsp:include page="includes/errores.jsp"/>
						</td>
					</tr>
				</table>
			</div>
 			<div class="cuerpomidUsuarios">
				<table cellpadding="0" cellspacing="0" border="0">
					<logic:equal value="false" scope="request" name="inicializadoDCO">
						<tr id="tdInicializar">
							<td>
								<bean:message key="ieci.tecdoc.rpadmin.dco.msg.action.init.system"/>
							</td>
							<td align="right" class="col_config" onclick="inicializarDCO();">
								<bean:message key="ieci.tecdoc.rpadmin.dco.boton.init"/>
							</td>
						</tr>
					</logic:equal>
					<logic:notEqual value="false" scope="request" name="inicializadoDCO">
						<tr id="tdActualizar">
							<td>
								<bean:message key="ieci.tecdoc.rpadmin.dco.msg.action.update.system"/>
							</td>
							<td align="right" class="col_config" onclick="actualizarDCO();">
								<bean:message key="ieci.tecdoc.rpadmin.dco.boton.update"/>
							</td>
						</tr>
					</logic:notEqual >
				</table>
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
  </div>
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>
</form>
</body>
</html>