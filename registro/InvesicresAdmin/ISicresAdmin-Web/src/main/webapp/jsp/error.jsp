<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title><bean:message key="ieci.tecdoc.sgm.pgadmin.title.aplication"/> - <bean:message key="ieci.tecdoc.sgm.pgadmin.error.titulo"/></title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<link href="css/adminApp.css" rel="stylesheet" type="text/css" />
</head>

<body>

<script language="javascript">
function volver(){
	var url = "<html:rewrite page="/jsp/index.jsp"/>";
	var formulario = document.getElementById("formulario");
	formulario.action = url;
	formulario.target="";
	formulario.submit();
}
</script>
<form action="" id="formulario" method="post">
</form>

<div id="contenedora">
  <!-- Inicio Cabecera -->
  <jsp:include page="includes/cabecera.jsp"/>

  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
          <h1><bean:message key="ieci.tecdoc.sgm.rpadmin.error.descError"/></h1>
          <div class="submenu3">
            <ul>
            </ul>
          </div>
          <div class="cuadro" align="center">
          		<div id="migas">
					<bean:message key="ieci.tecdoc.sgm.rpadmin.error.paginaError"/>
				</div>
				<p class="error"><html:errors/></p><br/>
				<html:button property="Enviar" styleId="boton" onclick="javascript:volver();"><bean:message key="ieci.tecdoc.sgm.rpadmin.error.back"/></html:button>
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
</body>
</html>
