<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
String rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (rutaEstilos == null) rutaEstilos = "";
String rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (rutaImagenes == null) rutaImagenes = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title>sigem</title>
<link href="css/<%=rutaEstilos%>estilos.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
</head>
<body>
<script language="javascript">
function volver(){
	var url = "<html:rewrite page="/buscarTasas.do"/>";
	var formulario = document.getElementById("formulario");
	formulario.action = url;
	formulario.target="";
	formulario.submit();
}

function justificante(){
	var formulario = document.getElementById("formulario");
	formulario.action="<html:rewrite page="/obtenerJustificante.do"/>"
	formulario.target="_blank";
	formulario.submit();	
}
</script>
<form action="" id="formulario" method="post">
	<input name="referencia" type="hidden" value="<bean:write name="referencia"/>" />
</form>
<div id="contenedora">
  <!-- Inicio Cabecera -->
	<jsp:include page="cabecera.jsp"/>
	
  <div id="migas">
  <img src="img/<%=rutaImagenes%>flecha_migas.gif" width="13" height="9" class="margen" alt=""/><a href="<html:rewrite page="/buscarTasas.do"/>"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.autoliquidaciones"/></a> &gt; <span class="activo"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.obtenerJustificante"/></span> </div>
  <!-- Fin Cabecera -->
  <!-- Inicio Contenido -->
  <div id="contenido">
    <div class="cuerpo">
      <div class="cuerporight">
        <div class="cuerpomid">
          <h1><bean:message key="ieci.tecdoc.sgm.pe.struts.common.autoliquidaciones.formulario.title"/>: <bean:message key="ieci.tecdoc.sgm.pe.struts.common.obtenerJustificante"/></h1>
          <div class="submenu3">
            <ul>
            </ul>
          </div>
          <div class="cuadro">
			<%=request.getAttribute("documentoPago")%>
			<div style="position:relative;left:300px;height:40px;">          							
				<html:button property="Enviar" styleId="boton" onclick="javascript:justificante();"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.receipt"/></html:button>			
			</div>
			<div style="position:relative;left:300px;height:40px;">          										
				<html:button property="Enviar" styleId="boton" onclick="javascript:volver();"><bean:message key="ieci.tecdoc.sgm.pe.struts.common.back"/></html:button>			
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
</body>
</html>
