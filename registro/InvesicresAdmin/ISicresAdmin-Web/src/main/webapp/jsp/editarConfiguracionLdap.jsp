<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
<html:form action="/editarConfiguracionLdap.do">
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="configuracionLdap"/>
		  </jsp:include>
          <div class="cuadro">
          	<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.configuracionLdap"/>
			</div>
			<div class="col" align="right">
				<table>
				<tr>
					<td width="75%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td class="col_guardar" onclick="llamadaAction('<html:rewrite page="/guardarConfiguracionLdap.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.guardar"/></td>
					<td class="col_testLdap" onclick="llamadaAction('<html:rewrite page="/testConfiguracionLdap.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.configuracionLdap.probarConexion"/></td>
				</tr>
				</table>
			</div>

				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="10" rowspan="80"></td>
						<td class="col" height="20"></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.tipoServidor"/>&nbsp;&nbsp;</td>
						<td>
							<html:select property="tipoServidor" styleId="tipoServidor" styleClass="textInput">
								<html:optionsCollection name="tiposServidor" property="lista" value="codigo" label="descripcion"/>
							</html:select>
						</td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.direccion"/>&nbsp;&nbsp;</td>
						<td><html:text property="direccion" styleClass="textInput" maxlength="255"/></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.puerto"/>&nbsp;&nbsp;</td>
						<td><html:text property="puerto" styleClass="textInput" maxlength="255"/></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.usuario"/>&nbsp;&nbsp;</td>
						<td><html:text property="usuario" styleClass="textInput" maxlength="255"/></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.password"/>&nbsp;&nbsp;</td>
						<td><html:password property="password" styleClass="textInput" maxlength="255"/></td>
					</tr>
					<tr class="col">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.nodoRaiz"/>&nbsp;&nbsp;</td>
						<td><html:text property="nodoRaiz" styleClass="textInput" maxlength="255"/></td>
					</tr>
					<tr class="col" id="usarSOAuthRow">
						<td class="txt"><bean:message key="ieci.tecdoc.sgm.rpadmin.configuracionLdap.usarSOAuth"/>&nbsp;&nbsp;</td>
						<td><html:checkbox property="usarSOAuth" style="width:20px" /></td>
					</tr>
					<tr id="usarSOAuthSepRow"><td height="100%" colspan="2"></td></tr>
			</table>
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
