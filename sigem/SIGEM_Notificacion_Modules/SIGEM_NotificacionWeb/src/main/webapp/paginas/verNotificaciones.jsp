<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<jsp:useBean id="cnifDest" type="String" scope="request" />

<%
String _rutaEstilos = (String)session.getAttribute("PARAMETRO_RUTA_ESTILOS");
if (_rutaEstilos == null) _rutaEstilos = "";
String _rutaImagenes = (String)session.getAttribute("PARAMETRO_RUTA_IMAGENES");
if (_rutaImagenes == null) _rutaImagenes = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
	    <link rel="stylesheet" href="css/<%=_rutaEstilos%>estilos.css" type="text/css" />
	    <script type="text/javascript" language="javascript" src="js/idioma.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="ieci.tecdoc.sgm.nt.verNotificaciones.titulo"/></title>
        <script language="Javascript">
    		function cambioColor(filaRegistro,event){
				if (window.event) {
					filaRegistro.style.cursor = "hand";
					var miArray = filaRegistro.getElementsByTagName("td");
					for (i=0;i<miArray.length;i++){
						miArray[i].style.backgroundColor="#FFCC80";
					}
				} else {
					filaRegistro.style.cursor = "pointer";
					filaRegistro.style.backgroundColor="#FFCC80";
				}
			}
		
			function cambioColorBlanco(filaRegistro,event){
				if (window.event) {
					var miArray = filaRegistro.getElementsByTagName("td");
					for (i=0;i<miArray.length;i++){
						miArray[i].style.backgroundColor="#FFFFFF";
					}
				} else {
					filaRegistro.style.backgroundColor="#FFFFFF";
				}
			}
   
		   function changeColor(tabla, color){
				if (color == 'true'){
					document.getElementById('tabla0'+tabla).style.backgroundColor = "#FFCC80";
					document.getElementById('tabla1'+tabla).style.backgroundColor = "#FFCC80";
					document.getElementById('tabla2'+tabla).style.backgroundColor = "#FFCC80";
					document.getElementById('tabla3'+tabla).style.backgroundColor = "#FFCC80";
					document.getElementById('tabla4'+tabla).style.backgroundColor = "#FFCC80";
					document.getElementById('tabla5'+tabla).style.backgroundColor = "#FFCC80";
				}else {
					document.getElementById('tabla0'+tabla).style.backgroundColor = "transparent";
					document.getElementById('tabla1'+tabla).style.backgroundColor = "transparent";
					document.getElementById('tabla2'+tabla).style.backgroundColor = "transparent";
					document.getElementById('tabla3'+tabla).style.backgroundColor = "transparent";
					document.getElementById('tabla4'+tabla).style.backgroundColor = "transparent";
					document.getElementById('tabla5'+tabla).style.backgroundColor = "transparent";
				}
			}
   
    		function verDetalle (notiId){
		        document.form1.action = "./detalle.do";
    		    //document.form1.expediente.value=expediente;
        		//document.form1.cnifDest.value='<%=cnifDest%>';
        		document.form1.notiid.value=notiId;
	        	document.form1.submit();
    		}
   
		    function hazSubmit(id,name){       
        		document.form1.idFichero.value=id;
		        document.form1.nameFichero.value=name;     
        		document.form1.submit();
		    }
    
		    function hazSubmitCambiarEstado (notiId){
        		//document.form1.expediente.value = expediente;
        		document.form1.notiid.value = notiId;
		        if (confirm('<bean:message key="ieci.tecdoc.sgm.nt.verNotificaciones.preguntaConsentimiento"/>'))
        		    document.form1.submit();
		    }
		</script>
    </head>
    
    <body>
   		<div id="contenedora">
   			<jsp:include flush="true" page="Cabecera.jsp"></jsp:include>
			<div class="cuerpo">
				<div class="cuerporight">
          			<div class="cuerpomid">
    					<h1><bean:message key="ieci.tecdoc.sgm.nt.verNotificaciones.notificacionesInteresado"/> <%=cnifDest%></h1>
    					<div class="submenu3">
            				<ul>
              					<li class="submen1on">
              						<img src="img/<%=_rutaImagenes%>subme3_on.gif" alt=""/>
              						<bean:message key="ieci.tecdoc.sgm.nt.verNotificaciones.titulo"/>
              					</li>
								<li class="submen1off">
									<img src="img/<%=_rutaImagenes%>subme3_on_of.gif" alt=""/>
              						<a href="./BusquedaNotificaciones.do">
              							<bean:message key="ieci.tecdoc.sgm.nt.buscarNotificaciones.titulo"/>
              						</a>
              					</li>
              					<img src="img/<%=_rutaImagenes%>subme3_of_0.gif" alt=""/>
            				</ul>
          				</div>
 						<div class="cuadro">
    						<form action="./firmar.do" name="form1">
          						<input name="idFichero" type="hidden"/>
						        <input name="nameFichero" type="hidden"/>       
						        <input name="expediente" type="hidden"/>
								<input name="notiid" type="hidden"/>     
						        <input name="cnifDest" type="hidden" value="<%=cnifDest%>"/>
         						<% int i = 0; %>
       							<display:table name="Notificaciones.notificaciones" uid="expediente" 
									pagesize="10" 
									class="tablaListado"
									sort="list"
									requestURI="verNotificaciones.do"
									style="width:100%;">
									<display:column paramId="tabla<%=i%>" paramName="tabla<%=i%>"  style="width:13%" media="html" titleKey="ieci.tecdoc.sgm.nt.verNotificaciones.tabla.codNotificacion" sortable="false" headerClass="cabeceraTabla">
										<table id="tabla0<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr height="100%" style="cursor:pointer;" onClick="verDetalle('<bean:write name="expediente" property="notiId"/>');">
												<td>
										   			<bean:write name="expediente" property="codigoNoti"/>&nbsp;
											   	</td>
											</tr>
										</table>
								   </display:column>
								   <display:column style="width:13%" media="html" titleKey="ieci.tecdoc.sgm.nt.verNotificaciones.tabla.codNotificacion" sortable="false" headerClass="cabeceraTabla">
										<table id="tabla1<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr height="100%" style="cursor:pointer;" onClick="verDetalle('<bean:write name="expediente" property="notiId"/>');">
												<td>
										   			<bean:write name="expediente" property="numeroExpediente"/>&nbsp;
											   	</td>
											</tr>
										</table>
								   </display:column>
								   <display:column style="width:14%" media="html" titleKey="ieci.tecdoc.sgm.nt.verNotificaciones.tabla.deu" sortable="false" headerClass="cabeceraTabla">
										<table id="tabla2<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr height="100%" style="cursor:pointer;" onClick="verDetalle('<bean:write name="expediente" property="notiId"/>');">
												<td>
										   			<bean:write name="expediente" property="usuario"/>&nbsp;
											   	</td>
											</tr>
										</table>
								   </display:column>
									<display:column style="width:30%" media="html" titleKey="ieci.tecdoc.sgm.nt.verNotificaciones.tabla.asunto" sortable="false" headerClass="cabeceraTabla">
										<table id="tabla3<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr height="100%" style="cursor:pointer;" onClick="verDetalle('<bean:write name="expediente" property="notiId"/>');">
												<td>
										   			<bean:write name="expediente" property="asunto"/>&nbsp;
											   	</td>
											</tr>
										</table>
								   </display:column>
								   <display:column style="width:10%" media="html" titleKey="ieci.tecdoc.sgm.nt.verNotificaciones.tabla.fecha" sortable="false" headerClass="cabeceraTabla">
										<table id="tabla4<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr height="100%" style="cursor:pointer;" onClick="verDetalle('<bean:write name="expediente" property="notiId"/>');">
												<td>
										   			<bean:write name="expediente" property="fechaEntrega"/>&nbsp;
											   	</td>
											</tr>
										</table>
								   	</display:column>
									<display:column style="width:30%" media="html" titleKey="ieci.tecdoc.sgm.nt.verNotificaciones.tabla.estado" sortable="false" headerClass="cabeceraTabla">
										<table id="tabla5<%=i%>" width="100%" height="100%" onmouseover="javascript:changeColor(<%=i%>, 'true');" onmouseout="javascript:changeColor(<%=i%>, 'false');">
											<tr height="100%" style="cursor:pointer;" onClick="verDetalle('<bean:write name="expediente" property="notiId"/>');">
												<td>
										   			<bean:write name="expediente" property="descripcionEstado"/>&nbsp;
											   	</td>
											</tr>
										</table>
								   </display:column>
								   <%i++; %>
								</display:table>
							</form>
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
	</body>
</html>
