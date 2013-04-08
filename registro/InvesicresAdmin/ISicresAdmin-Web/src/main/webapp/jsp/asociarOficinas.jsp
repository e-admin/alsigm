<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/common.js"></script>
</head>

<body>

<html:form action="/asociarOficinas.do">
<div id="contenedora">
  <!-- Inicio Cabecera -->
  <jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido" >
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
	      <jsp:include page="includes/pestanas.jsp">
			<jsp:param name="pestana" value="libros"/>
		  </jsp:include>

          <div class="cuadro">
			<div class="col" style="height: 30px;">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="100%" align="left">
						<jsp:include page="includes/errores.jsp"/>
					</td>
					<td align="right" class="col_nuevo" onclick="llamadaActionAsociar('<html:rewrite page="/asociarOficinas.do"/>','<bean:write name="idLibro"/>','ASOCIAR', '<bean:write name="tipoLibro"/>', '<bean:write name="estado"/>', '<bean:write name="nombre"/>' )"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.asociar"/></td>
					<td align="right" class="col_eliminar" onclick="llamadaActionAsociar('<html:rewrite page="/asociarOficinas.do"/>','<bean:write name="idLibro"/>','CANCELAR', '<bean:write name="tipoLibro"/>', '<bean:write name="estado"/>', '<bean:write name="nombre"/>')"><bean:message key="ieci.tecdoc.sgm.rpadmin.botones.cancelar"/></td>
				</tr>
			</table></div>
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td height="40">
						<logic:equal name="tipoLibro" value="1">
							<font color="#950101" ><b>>> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.entrada"/></b>:</font>&nbsp;&nbsp;<bean:write name="nombre" />
						</logic:equal>
						<logic:notEqual name="tipoLibro" value="1">
							<font color="#006699" ><b>>> <bean:message key="ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.salida"/></b>:</font>&nbsp;&nbsp;<bean:write name="nombre" />
						</logic:notEqual>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<display:table name="oficinas.lista" uid="fila"
					     requestURI="/asociarOficinas.do" class="tablaListado" sort="page" style="width:100%">
						 	<display:column style="width:30px" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.select">
						 		<table cellpadding="0" cellspacing="0" border="0" align="center">
						 			<tr>
						 				<td>
						 					<input type="checkbox" name="ids[<c:out value="${fila_rowNum-1}"/>]" style="width:20px"
						 					value='<bean:write name="fila" property="id"/>' />
						 				</td>
						 			</tr>
						 		</table>
						 	</display:column>
							<display:column property="codigo" titleKey="ieci.tecdoc.sgm.rpadmin.oficinas.codigo"
								 sortable="false" style="width: 10px;"/>
							<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.libros.oficina"
								 sortable="false" style="width: 85%"/>
						</display:table>
					</td>
				</tr>
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
  <!-- Fin Contenido -->
  <div id="pie"></div>
</div>

</html:form>
</body>
</html>
