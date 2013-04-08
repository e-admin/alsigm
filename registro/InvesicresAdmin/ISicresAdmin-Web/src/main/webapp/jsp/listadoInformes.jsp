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
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.botones.informes"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<form name="frmListadoInformes" action="" method="post">
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="includes/cabecera.jsp"/>


  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="informes"/>
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
					<td align="right" class="col_nuevo" onclick="llamadaAction('<html:rewrite page="/nuevoInforme.do"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.nuevo"/></td>
				</tr>
			</table>
		</div>
 			<display:table name="informes.lista" uid="fila" class="table-display-tag"
				 requestURI="/listadoInformes.do" sort="page" style="width:100%">
					<display:column titleKey="ieci.tecdoc.sgm.rpadmin.informes.titulo" sortable="true">
						<span><bean:write name="fila" property="description"/></span>
					</display:column>
					<display:column style="width: 20%;" titleKey="ieci.tecdoc.sgm.rpadmin.informes.tipo.informe" sortable="true">
						<span><bean:write name="fila" property="nameTypeReport"/></span>
					</display:column>
					<display:column style="width: 5%;" titleKey="ieci.tecdoc.sgm.rpadmin.informes.entrada" sortable="false" >
						<logic:equal name="fila" property="typeArch" value="1">
							<center><img src="<html:rewrite page="/img/guardar.gif"/>"/></center>
						</logic:equal>
					</display:column>
					<display:column style="width: 5%;" titleKey="ieci.tecdoc.sgm.rpadmin.informes.salida" sortable="false">
						<logic:equal name="fila" property="typeArch" value="2">
							<center><img src="<html:rewrite page="/img/guardar.gif"/>"/></center>
						</logic:equal>
					</display:column>
					<display:column style="width: 5%;">
						<table><tr>
							<td class="col_editar" onclick="llamadaActionNameId('<html:rewrite page="/editarInforme.do"/>','idInforme','<bean:write name="fila" property="id" />')" title="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.propiedadesEditar"/>">&nbsp;&nbsp;&nbsp;</td>
						</tr></table>
					</display:column>
					<display:column style="width: 5%;">
						<table><tr>
							<c:set var="funcionllamadaActionEliminarId">
								llamadaActionEliminarId("<html:rewrite page='/eliminarInforme.do'/>","idInforme=<bean:write name='fila' property='id' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.informes.eliminar.informes'/>","<bean:write name='fila' property='description' filter='false'/>")
							</c:set>
							<td class="col_eliminar" onclick="<c:out value="${funcionllamadaActionEliminarId}"/>" title="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.eliminar"/>">&nbsp;&nbsp;&nbsp;</td>
						</tr></table>
					</display:column>
					<display:column style="width: 5%;">
						<table><tr>
							<td class="col_descargar" onclick="llamadaActionNameId('<html:rewrite page="/descargarInforme.do"/>','idInforme','<bean:write name="fila" property="id" />')" title="<bean:message key="ieci.tecdoc.sgm.rpadmin.botones.descargar"/>">&nbsp;&nbsp;&nbsp;</td>
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
