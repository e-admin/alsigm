<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.transportes"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<form name="frmListadoTransportes" action="" method="post">
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>


  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="transportes"/>
		  </jsp:include>

          <div class="cuadro">
			<div id="migas">
				<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.listado" />
			</div>
		 <div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_nuevo" onclick="llamadaAction('<html:rewrite page="/nuevoTransporte.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nuevo"/></td>
				</tr>
			</table>
		</div>
 			<display:table name="transportes.lista" uid="fila"
				 requestURI="/listadoTransportes.do" class="tablaListado" sort="page" style="width:100%">
					<display:column property="transport" titleKey="ieci.tecdoc.sgm.rpadmin.transportes.tipo"
						 sortable="false"/>
					<display:column style="width: 10%;">
						<table><tr>
							<td class="col_editar" onclick="llamadaActionIdTransporte('<html:rewrite page="/editarTransporte.do"/>','<bean:write name="fila" property="id" />')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar"/></td>
						</tr></table>
					</display:column>
					<display:column style="width: 5%;">
						<table><tr>
							<c:set var="funcionllamadaActionIdTransporteEliminar">
								llamadaActionIdTransporteEliminar("<html:rewrite page='/eliminarTransporte.do'/>","<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.transportes.eliminar.transporte'/>","<bean:write name='fila' property='transport' filter='false'/>")
							</c:set>
							<td class="col_eliminar" onclick="<c:out value="${funcionllamadaActionIdTransporteEliminar}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/></td>
						</tr></table>
					</display:column>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr><td height="8" class="col" width="100%"></td></tr>
					</table>
			</display:table>
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
</form>
</body>
</html>
