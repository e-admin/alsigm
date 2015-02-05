<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<html:html>

  <HEAD>
    <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    %> 
    <base href="<%= basePath %>" />


    <link rel="stylesheet" href="css/estilos.css" type="text/css" />

    <!--[if lte IE 5]>
      <link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
    <![endif]-->	
  
    <!--[if IE 6]>
      <link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
    <![endif]-->	
  
    <!--[if IE 7]>
      <link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
    <![endif]-->	


    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <script type="text/javascript" language="javascript" src="js/navegador.js"></script>
    <META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <META name="GENERATOR" content="IBM Software Development Platform">
    <TITLE></TITLE>
    
    <script type="text/javascript" >
      function loading() {
        if( document.getElementById('arbolUnidades').src != "null") {
          document.getElementById('capaCargando').style.display = "none";
          document.getElementById('capaUnidades').style.display = "block";
        } else {
          setTimeout("loading()", 100);
        }
      }

      function redirect( url ) {
        document.location.href = url;
      }
      
       function accept(){   
            var form = document.getElementById("addresseeForm");
            if ((form.code==null) || (form.code.value==null) || (form.code.value=='')|| (form.code.value=='undefined')){
            	alert('<bean:message key="mensaje_error.seleccionar.nodo"/>');
            } else if ((form.code.value == '0')&&(form.importSelectedDept.checked==true)){
            	alert('<bean:message key="mensaje_error.importar.nodo.raiz"/>');
            } else {
          		form.userAction.value='import';
          		form.submit();
            }
         }
    </script>

  </HEAD>

  <body onload="loading()">
    

    <div class="cuerpo">
    <div class="cuerporight">
             <div class="cuerpomid">
               <h1><bean:message key="menu.configurador" /></h1>

        <div class="tabs_submenus">
          <ul>
            <li class="subOff">
              <h3><a href="procedure.do"><bean:message key="procedures.prococedures" /></a></h3>
            </li>
            <li class="subOff">
              <h3><a href="documents.do"><bean:message key="menu.tiposDocs" /></a></h3>
            </li>
            <li class="subOff">
              <h3><a href="hooks.do"><bean:message key="menu.auth.conn" /></a></h3>
            </li>
            <li class="subOff">
              <h3><a href="authHook.do"><bean:message key="menu.conectores_tramites" /></a></h3>
            </li>
            <li class="subOn">
              <h3><a><bean:message key="menu.organos" /></a></h3>
            </li>
            <li class="subOff">
              <h3><a href="calendar.do"><bean:message key="menu.calendario" /></a></h3>
            </li>
          </ul>
        </div>
        <div class="cuerpo_subs">

          <div class="cuerpo_sub visible clearfix">

          <html:form styleId="addresseeForm" action="addresseeList" method="post">	
  
          <html:hidden property="userAction"/>				
          <html:hidden property="code"/>
            
          <div class="seccion_large"> 
            <div class="cuerpo_sec fila_sub clearfix">
              <ul>
              <li id="acceptButton" name="acceptButton">
              <a href="javascript:accept()" class="col_aceptar" >
                <bean:message key="button.label.accept"/>
              </a>
              </li>
              <li id="backButton" name="backButton">
              <a href="addresseeList.do" class="col_eliminar">
                <bean:message key="button.label.back"/>
              </a>
              </li>
              </ul>
            </div>
          </div> <!-- fin seccion -->

          <div class="seccion_large">
            <div class="cuerpo_sec fila_sub clearfix">
              <div id="capaCargando" style="width:100px; height:100px;display:block">
                <table cellpadding="0" cellspacing="0" border="0" width="100">
                  <tr>
                    <td><bean:message key="cargando"/></td>
                    <td align="center">
                      <img src="<html:rewrite page="/img/loading.gif"/>" />
                    </td>
                  </tr>
                </table>
              </div>
              <div id="capaUnidades" style="display:none">
				<div class="sub" id="divChecks">
					<div style="float:left;"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.nodo.seleccionado"/>:
					<input type="text" id="nodoseleccionado" style="width:300px;" readonly="readonly" disabled="disabled"/>
					</div>
					<div style="float:left;">
					<html:checkbox styleClass="checkbox" property="importSelectedDept"><bean:message key="ieci.tecdoc.sgm.rpadmin.unidades.importar.nodo.seleccionado"/></html:checkbox>
					</div>
				</div>
				<p class="fila">
					<iframe src="<html:rewrite page="/cargarUnidades.do"/>" id="arbolUnidades" frameborder='0' width="100%" height="315"></iframe>
				</p>
              </div>
            </div>
          </div>

          <div class="clear">
          </div> <!-- fin clear -->

          </html:form>
          
          </div> <!-- fin cuerpo_sub visible -->
        </div> <!-- fin cuerpo_subs -->

      </div>
          </div>
        </div>
        <div class="cuerpobt">
          <div class="cuerporightbt">
            <div class="cuerpomidbt">&nbsp;</div>
          </div>
    </div>		
    
  </body>
</html:html>
