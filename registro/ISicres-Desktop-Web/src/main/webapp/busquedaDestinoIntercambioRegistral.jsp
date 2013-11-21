<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//ES"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${pageContext.response.locale.language}" lang="${pageContext.response.locale.language}">
	<head>
		<html:base />

		<meta http-equiv="content-type" content="text/html;charset=iso-8859-1" />
		<meta http-equiv="Content-Style-Type" content="text/css" />
		<link rel="stylesheet" href="./css/terceros/reset.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="./css/terceros/table.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="./css/terceros/estilos.css" type="text/css" media="screen" />


		<link rel="stylesheet" href="./css/global.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="./css/intercambioRegistral.css" type="text/css" media="screen" />
	 	<link rel="stylesheet" href="./css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css" media="screen" />
	 	<link rel="stylesheet" href="./css/terceros/fancybox/jquery.fancybox-1.3.4.css" type="text/css" media="screen" />

	 	<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/genmsg.js"></script>
	 	<script type="text/javascript" language="javascript" src="./scripts/jquery-ui-1.8.16.custom.min.js"></script>
	 	<script type="text/javascript" language="javascript" src="./scripts/terceros/jquery.fancybox-1.3.4.pack.js"></script>
	 	<script type="text/javascript" language="javascript" src="./scripts/jquery.blockUI.js"></script>
	 	<script type="text/javascript" language="javascript" src="./scripts/global.js"></script>

	</head>
	<body onunload="desbloqueoDePantallaRegistro()">
	<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
	<c:if test="${ message == null }">
	    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
	</c:if>

	<div style="padding: 30px;">
		<form action="EnviarIntercambioRegistral.do" method="post" onsubmit="javascript: return validarEntidadRequerida(); ">
			<h2><fmt:message key="intercambioRegistral.busquedaDestino.titulo" /></h2>

			<h4><fmt:message key="intercambioRegistral.busquedaDestino.entidadRegistral" /></h4>
			<div class="formRow">
				<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoEntidad" /></label><input type="text" readonly="readonly" class="inputRO" name="entityCode" id="entityCode" value="<c:out value='${defaultEntityCode}' />"/><img src="images/buscar2.gif" class="buscarEntidadesRegistralesLupa imageClickable"></img>
			</div>
			<div class="formRow">
				<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreEntidad" /></label><input type="text" readonly="readonly" class="inputRO" name="entityName" id="entityName" style="width:320px;" value="<c:out value='${defaultEntityName}' />"/>
			</div>

			<h4><fmt:message key="intercambioRegistral.busquedaDestino.unidadTramitacion" /></h4>

			<div class="formRow">
				<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoUnidad" /></label><input type="text" readonly="readonly" class="inputRO" name="tramunitCode" id="tramunitCode" value="<c:out value='${defaultTramunitCode}' />" /><img src="images/buscar2.gif" class="buscarUnidadesTramitacionLupa imageClickable"></img>
			</div>
			<div class="formRow">
				<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreUnidad" /></label><input type="text" readonly="readonly" class="inputRO" name="tramunitName" id="tramunitName" style="width:320px;" value="<c:out value='${defaultTramunitName}' />"/>
			</div>

			<input type="hidden" value="<c:out value='${bookId}' />" name="bookId" />
			<input type="hidden" value="<c:out value='${folderId}' />" name="folderId" />

			<div style="text-align:center;">
				<input type="submit" class="button" value="<fmt:message key="intercambioRegistral.busquedaDestino.enviar" />" /><input type="button" value="<fmt:message key="intercambioRegistral.busquedaDestino.cancelar" />" class="button" onclick="javascript:closeBusquedaDestinoIR();return false;"/>
			</div>
		</form>
	</div>


<script type="text/javascript">
<!--
  $(document).ready(function($){

    // La busqueda de representantes se hace por Ajax y se muestra con en plugin fancybox
    $('.buscarEntidadesRegistralesLupa').click(function(e){
      e.preventDefault();

      $.fancybox.showActivity();

      $.ajax({
        type: 'GET',
        cache: false,
        url: 'BuscarEntidadesRegistralesDCO.do',
        dataType: 'html',
        success:function(data){
          $.fancybox({
            modal:true,
            content:data
            });
         	putEventToFindER();

          }
      });
    });

    $('.buscarUnidadesTramitacionLupa').click(function(e){
        e.preventDefault();

        $.fancybox.showActivity();

        $.ajax({
          type: 'GET',
          cache: false,
          url: 'BuscarUnidadesTramitacionDCO.do',
          data: 'codeEntityToFind='+$('#entityCode').val(),
          dataType: 'html',
          success:function(data){
            $.fancybox({
              modal:true,
              content:data
              });
            putEventToFindTramUnit();
            putPaginateEvents();
            }
        });
      });



  });

function putPaginateEvents()
{
	 // La navegacion de resultados se realiza por Ajax para mantener el foco en la ventana abierta
    $('.pagelinks > a, .sortable > a').click(function(e){
      e.preventDefault();

      $.ajax({
               type: 'POST',
               cache: false,
               url: $(this).attr('href'),
               dataType: 'html',
               success:function(data){
                   $('#fancybox-content').html(data);
                   putEventToFindER();
   	          	   putPaginateEvents();
               }
          });
      });
}

function putEventToFindER(){
	 $('.buscarEntidadesRegistralesButton').click(function(e){
	      e.preventDefault();

	      $.fancybox.showActivity();

	      $.ajax({
	        type: 'GET',
	        cache: false,
	        url: 'BuscarEntidadesRegistralesDCO.do?codeEntityToFind='+$('#codeEntityToFind').val()+'&nameEntityToFind='+$('#nameEntityToFind').val(),
	        dataType: 'html',
	        success:function(data){
	          $.fancybox({
	            modal:true,
	            content:data
	            });
	          	putEventToFindER();
	          	putPaginateEvents();
	          }
	      });
	    });
}

function putEventToFindTramUnit(){
	 $('.buscarUnidadesTramitacionButton').click(function(e){
	      e.preventDefault();

	      $.fancybox.showActivity();

	      $.ajax({
	        type: 'GET',
	        cache: false,
	        url: 'BuscarUnidadesTramitacionDCO.do?tramunitCodeToFind='+$('#tramunitCodeToFind').val()+'&tramunitNameToFind='+$('#tramunitNameToFind').val()+'&codeEntityToFind='+$('#entityCode').val(),
	        dataType: 'html',
	        success:function(data){
	          $.fancybox({
	            modal:true,
	            content:data
	            });
	          	putEventToFindTramUnit();
	          	putPaginateEvents();
	          }
	      });
	    });
}

function closeBusquedaDestinoIR(){
	// Cerramos los frames
	top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");

	top.g_WndVld.document.getElementById("Vld").style.display = "none";

	// Volvemos a mostrar el formulario
	top.g_FormVld.style.visibility = 'visible';
}

function closeFancybox()
{
	$.fancybox.close();
}

function validarEntidadRequerida(){

	if($('#entityCode').val()=='')
	{
		alert('La Entidad Registral es obligatoria.');
		return false;
	}
	//bloqueamos la ventana hasta que responda el servidor
	bloqueoDePantallaRegistro();

	return true;
}


//-->
</script>

	</body>
</html>
