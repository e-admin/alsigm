<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ieci.tld" prefix="ieci"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ page import="ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion" %>
<%@ page import="ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion" %>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>



<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<ieci:baseInvesDoc/>
<link rel="stylesheet" rev="stylesheet" href="include/css/tabs.css">
<link rel="stylesheet" rev="stylesheet" href="include/css/estilos.css">
<script src="include/js/docobj.js" type="text/javascript"></script>
<script src="include/js/tabs.js" type="text/javascript"></script>
<script src="include/js/validations.js" type="text/javascript"></script>

<script type="text/javascript">

	function activaPestanhaGeneral()
	{
		document.getElementById("img1").src='include/images/subme3_on.gif';
		document.getElementById("img2").src='include/images/subme3_on_of.gif';
		document.getElementById("img3").src='include/images/subme3_of_0.gif';
		document.getElementById("tab1").className='submen1on';
		document.getElementById("tab2").className='submen1off';
	}

	function activaPestanhaDetalle()
	{
		document.getElementById("img1").src='include/images/subme3_off.gif';
		document.getElementById("img2").src='include/images/subme3_of_on.gif';
		document.getElementById("img3").src='include/images/subme3_on_0.gif';
		document.getElementById("tab1").className='submen1off';
		document.getElementById("tab2").className='submen1on';
	}

	function cancel(){
    	var check = chequearSession('<html:rewrite page="/chequearSession.do"/>');
		if( check == "false" ) { 	
			document.location.href="<html:rewrite forward='cancel'/>";
		} else {
			window.parent.redirect('<%=AutenticacionAdministracion.obtenerUrlLogin(request, ConstantesGestionUsuariosAdministracion.APLICACION_REPOSITORIOS_DOCUMENTALES) %>');	
		}				
	}		
</script>

</head>

<body onload="choosebox(1,9);">


    <div id="contenedora">
		<div class="cuerpo" style="width:530px; height:170px;">
				<html:form action="/volume/repositoryProperties" method="post">
	   		<div class="cuerporight">
	    		<div class="cuerpomid">
						<h1><bean:message key="message.repositorio.propiedades.titulo"/></h1>
						<div class="submenu3">
							<ul>
	        					<li class="submen1on" id="tab1" onclick="choosebox(1,9);activaPestanhaGeneral();"><img id="img1" src="include/images/subme3_on.gif" />
	        						<label id="tabmiddle1"><bean:message key="message.comun.pestana.general"/></label>
	        					</li>	        					
								<li class="submen1off" id="tab2" onclick="choosebox(2,9);activaPestanhaDetalle();"><img id="img2" src="include/images/subme3_on_of.gif" />
									<label id="tabmiddle2"><bean:message key="message.comun.pestana.deatalle"/></label>
									<img id="img3" src="include/images/subme3_of_0.gif" />
								</li>
							</ul>
						</div>	
						<div class="cuadro" style="height: 170px;">
							<div id="box1">

								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.nombre"/></label>
									<label class="gr" style="width:350px;"><bean:write name="repositoryForm" property="name" /></label>
							    </div>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.repositorio"/></label>
									<label class="gr" style="width:350px;"><bean:write name="repositoryForm" property="type" /></label>
							    </div>	
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.id"/></label>
									<label class="gr" style="width:350px;"><bean:write name="repositoryForm" property="id" /></label>
							    </div>	
							    
							    <div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.descripcion"/></label>
									<html:textarea property="description" readonly="true" rows="2" cols="65" ></html:textarea>
							    </div>								    

								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.creado"/></label>
									<label class="gr" style="width:110px;"><bean:write name="repositoryForm" property="creationDate" /></label>
									<label class="gr" style="width:30px;"><bean:message key="message.comun.etiqueta.por"/></label>								                                      
									<label class="gr" style="width:120px;"><bean:write name="repositoryForm" property="creatorName" /></label>					
							    </div>
								<div class="col" style="width: 490px;">
									<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.modificado"/></label>
									<label class="gr" style="width:110px;"><bean:write name="repositoryForm" property="updateDate" /></label>
									<label class="gr" style="width:30px;"><bean:message key="message.comun.etiqueta.por"/></label>
									<label class="gr" style="width:120px;"><bean:write name="repositoryForm" property="updaterName" /></label>							
							    </div>
						
							</div>
							
							<div id="box2">
								
								<c:if test="${requestScope.type == 1 || requestScope.type == 3 }" >
									<div class="col" style="width: 320px;">
										<label class="gr" style="width:120px;"><bean:message key="message.comun.etiqueta.ruta"/></label>
										<label class="gr" style="width:190px;"><bean:write name="repositoryForm" property="path" /></label>
								    </div>
								</c:if>
								
								<div class="col" style="width: 320px;">
									<label class="gr" style="width:300px;"><bean:message key="message.comun.etiqueta.estado"/></label>
									<label class="gr" style="width:300px;">
									<logic:iterate name="statesRepositoryList" id="estado" property="states" scope="request">
									  <html:multibox  property="states" disabled="true" style="width:20px;">
									   <bean:write name="estado" property="value"/>
									  </html:multibox> 
								      <bean:define id="estadoRepo" name="estado" property="label" toScope="request"></bean:define>
									  <bean-el:message key="${estadoRepo}" />
									</logic:iterate>
									</label>
								</div>
							
							</div>
		       			</div>   				
     			</div>
      		</div>

		<html:hidden property="id"/>
		<input type="button" value="<bean:message key="message.comun.boton.aceptar"/>" class="okEO" onclick="cancel();" />
		</html:form>
			
	   	</div> 	
		
		<div class="cuerpobt" style="width:530px">
	    	<div class="cuerporightbt">
	      		<div class="cuerpomidbt"></div>
	    	</div>
		</div>
	</div>	
							

</body>


<!-- 
<body onload="choosebox(1,9);">


<div class="titulo" > Repositorio - Propiedades </div>

<html:form action="/volume/repositoryProperties" method="post">


<div id="tab1" onmouseover="tabover(1)" onmouseout="tabout(1)" onclick="choosebox(1,9)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="tableft" ><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td id="tabmiddle1">General</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</table>
</div>


<div id="box1">
<table>
    <tr>
      <td>&nbsp;</td>
      <td colspan="2">Nombre:</td>
      <td><bean:write name="repositoryForm" property="name" /></td>
    </tr>

    <tr>
      <td>&nbsp;</td>
      <td colspan="2">Repositorio:</td>
      <td><bean:write name="repositoryForm" property="type" /></td>
    </tr>
    <tr>
    
      <td>&nbsp;</td>
      <td colspan="2">Id:</td>
      <td><bean:write name="repositoryForm" property="id" /></td>
    </tr>
    <tr>
      <td colspan="2">Descripcion</td>
      <td colspan="2" >
      <html:textarea property="description" readonly="true" rows="3" ></html:textarea>
      </td>
    </tr>
    <%-- 
    <tr>
      <td colspan="4"><bean:write name="repositoryForm" property="description" /></td>
    </tr>
    --%>
	<tr>
      <td colspan="4" align="center"><hr></td>
    </tr>
    
    <tr>
      <td>Creado:</td>
	  <td>
	    <html:text property="creationDate" readonly="true" size="10" style="border: 0;"/>
      </td>
	  <td>por</td>
      <td><bean:write name="repositoryForm" property="creatorName" /></td>
    </tr>
    <tr>
      <td>Modificado:</td>
      <td>
      <html:text property="creationDate" readonly="true" size="10" style="border: 0;"/>
      </td>
  	  <td>por</td>
      <td><bean:write name="repositoryForm" property="updaterName" /></td>
    </tr>
  </table> 
</div>

<div id="tab2" onmouseover="tabover(2)" onmouseout="tabout(2)" onclick="choosebox(2,9)">
<table summary="" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td class="tableft" ><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
	<td id="tabmiddle2">Detalle</td>
	<td class="tabright"><img src="include/images/dot.gif" alt="" border="0" height="17" width="7"></td>
</tr>
</table>
</div>



<div id="box2">
<table summary="" border="0" cellpadding="0" cellspacing="0" style="margin-top: 20px; margin-left: 15px;">
<c:if test="${requestScope.type == 1 || requestScope.type == 3 }" >
	<tr>
		<td>Ruta acceso:</td> <td>
		<html:text property="path" readonly="true" style="border:0" size="30"/>
		<%-- <bean:write name="repositoryForm" property="path" /> --%>
		
		</td>
	</tr>	
</c:if>

<tr>
	<td colspan=2>Estado</td>
</tr>
<tr>
	<td colspan=2>
    <logic:iterate name="statesRepositoryList" id="estado" property="states" scope="request">
	  <html:multibox property="states" disabled="true">
	   <bean:write name="estado" property="value"/> 
	  </html:multibox> 
      <bean:write name="estado" property="label"/> 
	</logic:iterate>		
	</td>
</tr>
</table>
</div>

<html:hidden property="id"/>

	<input type="button" value="Aceptar" class="boton1" onclick="cancel();" />
	
</html:form>





</body>
 -->
</html>