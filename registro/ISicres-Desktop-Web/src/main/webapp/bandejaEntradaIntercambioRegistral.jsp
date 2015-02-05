	<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ page import="es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO" %>
	<%@ page import="es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO" %>

	<%
		// seteamos los valores constantes de los estados disponibles
	    session.setAttribute("ESTADO_ENTRADA_PENDIENTE", EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE_VALUE);
		session.setAttribute("ESTADO_ENTRADA_ACEPTADO", EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO_VALUE);
		session.setAttribute("ESTADO_ENTRADA_RECHAZADO", EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO_VALUE);
		session.setAttribute("ESTADO_ENTRADA_REENVIADO", EstadoIntercambioRegistralEntradaEnumVO.REENVIADO_VALUE);

	%>



<html>
	<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- ¿Skin CSS? -->
	<link rel="stylesheet" href="./css/terceros/reset.css" type="text/css"
		media="screen" />
	<link rel="stylesheet" href="./css/terceros/table.css" type="text/css"
		media="screen" />
	<link rel="stylesheet" href="./css/terceros/estilos.css" type="text/css"
		media="screen" />

	<link rel="stylesheet" type="text/css" href="css/global.css" />
	<link rel="stylesheet" type="text/css" href="css/font.css" />
	<link rel="stylesheet" type="text/css" href="css/estilos.css">
	<link rel="stylesheet" type="text/css" href="css/displaytag.css">
	<link rel="stylesheet" type="text/css"
		href="css/intercambioRegistral.css">
	<link rel="stylesheet"
		href="./css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css"
		media="screen" />
	<link rel="stylesheet"
		href="./css/terceros/fancybox/jquery.fancybox-1.3.4.css"
		type="text/css" media="screen" />


	<script type="text/javascript" src="./scripts/genmsg.js"></script>
	<script type="text/javascript"
		src="./scripts/bandejasIntercambioRegistral.js"></script>
	<script type="text/javascript" src="./scripts/global.js"></script>
	<script type="text/javascript" src="./scripts/qryfmt.js"></script>

	<script type="text/javascript" src="scripts/jquery-1.6.2.min.js"></script>

	<script type="text/javascript"
		src="scripts/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript"
		src="scripts/terceros/jquery.fancybox-1.3.4.pack.js"></script>
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/jquery.blockUI.js"></script>


	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
	<![endif]-->

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css">
	<![endif]-->

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css">
	<![endif]-->

	<!--[if lte IE 6]>
		<script type="text/javascript" src="./scripts/iepngfix_tilebg.js"></script>
	<![endif]-->
	<script type="text/javascript">
	var estado='<c:out value="${estado}" />';
	var tipoBandeja='<c:out value="${tipoBandeja}" />';
	var aceptar = false;
	window.onload=init;
	function init()
	{
	selectState();
	setRowsEvents();
	}

	</script>

	</head>
	<body class="cabecera">
	<fmt:setLocale value="<%=session.getAttribute(\"JLocale\")%>" scope="session"/>
	<c:if test="${ message == null }">
	    <fmt:setBundle  basename="resources.ISicres-IntercambioRegistral" scope="application"/>
	</c:if>

		<form action="BandejaEntradaIntercambioRegistral.do" method="post" id="formBandejaEntrada" name="formBandejaEntrada">
		<div class="barraBandeja">
		<!-- TODO ESTE COMBO VIENE EN VARIABLE -->
			<select id="tipoBandeja" style="display:inline;" onchange="javascript:changeSelectEstados()" name="tipoBandeja">
				<option value="0"><fmt:message key="intercambioRegistral.bandeja.salida" /></option>
				<option value="1"><fmt:message key="intercambioRegistral.bandeja.entrada" /></option>
			</select>

			<select id="estadosSalida" style="display:none;"    name="estadosSalida">
				<option value="1"><fmt:message key="intercambioRegistral.estados.enviados" /></option>
				<option value="5"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
				<option value="4"><fmt:message key="intercambioRegistral.estados.devueltos" /></option>
			</select>

			<select id="estadosEntrada" style="display:inline;" name="estadosEntrada">
				<option value="${ESTADO_ENTRADA_PENDIENTE}"><fmt:message key="intercambioRegistral.estados.pendientes" /></option>
				<option value="${ESTADO_ENTRADA_ACEPTADO}"><fmt:message key="intercambioRegistral.estados.aceptados" /></option>
				<option value="${ESTADO_ENTRADA_RECHAZADO}"><fmt:message key="intercambioRegistral.estados.rechazados" /></option>
				<option value="${ESTADO_ENTRADA_REENVIADO}"><fmt:message key="intercambioRegistral.estados.reenviados" /></option>
			</select>
			
			<select id="libroSeleccionado"  name="libroSeleccionado" style="display:none;">
				<c:forEach items="${librosIntercambio}" var="libro">
					<option value="<c:out value='${libro.id}' />"><c:out value='${libro.name}' /></option>
				</c:forEach>
			</select>
			<a href="#" onclick="javascript:submitRefrescarBandejaEntradaOSalida();" class="Options linkBarraBandeja"><fmt:message key="intercambioRegistral.boton.refrescar" /></a>
			<a class="linkBarraBandeja Options" href="frquery.htm"><fmt:message key="intercambioRegistral.boton.volver" /></a>

		</div>
		<br />
		<div class="barraAcciones">
			<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}" >
				<img align="middle" src="images/accept.png" style="margin-top:-5px;" />&nbsp;
				<a href="#" onclick="javascript:aceptarIntercambiosRegistrales();" class="linkBarraAcciones"><fmt:message key="intercambioRegistral.boton.aceptar" /></a>
				<img align="middle" src="images/refresh.gif" style="margin-top:-5px;" />&nbsp;
				<a href="#" onclick="javascript:reenviarIntercambiosRegistrales();" class="linkBarraAcciones"><fmt:message key="intercambioRegistral.boton.reenviar" /></a>
				<img align="middle" src="images/rechazar.png" style="margin-top:-5px;" />&nbsp;
				<a href="#" onclick="javascript:rechazarIntercambiosRegistrales();" class="linkBarraAcciones"><fmt:message key="intercambioRegistral.boton.rechazar" /></a>

			</c:if>

		</div>

		<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}" >
			<div style="text-align:right;">
				<input onclick="javascript:seleccionarODeseleccionarTodos();" name="seleccionarTodos" type="checkbox" value="1"/> <fmt:message key="intercambioRegistral.check.seleccionarTodos" />
				<input onclick="javascript:seleccionarODeseleccionarTodos();" name="deseleccionarTodos" type="checkbox" value="0"/> <fmt:message key="intercambioRegistral.check.quitarSeleccion" />
			</div>
		</c:if>

		<c:if test="${requestScope.msg!=null}">
		<div style="color:blue;">
			<c:out value="${requestScope.msg}" ></c:out>
		</div>
		</c:if>
		<c:if test="${requestScope.error!=null}">
		<div style="color:red;">
			<c:out value="${requestScope.error}" ></c:out>
		</div>
		</c:if>

		<div id="capaBloqueo" style="display:none;"></div>
		<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}" >
			<div id="librosEntrada" style="display:none;">
			<h5><fmt:message key="intercambioRegistral.seleccionarLibro" /></h5>
				<c:forEach items="${librosEntrada}"   var="libro">
					<input name="idLibro" type="radio" value="<c:out value='${libro.id}'/>"/> <c:out value="${libro.name}"/>
					<br />
				</c:forEach>
				 <input type="button" onclick="javascript:submitAceptarIntercambiosRegistrales();" value="<fmt:message key='intercambioRegistral.boton.aceptar' />" / >
				 <input type="button" onclick="javascript:ocultarLibros();"  value="<fmt:message key='intercambioRegistral.boton.cancelar' />" />
			</div>

			<div id="llegoDocumentacionFisica" style="display:none;">
			<h5><fmt:message key="intercambioRegistral.preguntaLlegoDocumentacionFisica" /></h5>
				 <input type="button" onclick="javascript:continuarDocRecibida();" value="<fmt:message key='intercambioRegistral.boton.continuarDocRecibida' />" / >
				 <input style="display:none;" onclick="javascript:continuarDocNoRecibida();" id="buttonContinuarDocNoRecibida" type="button"  value="<fmt:message key='intercambioRegistral.boton.continuarDocNoRecibida' />" / >
				 <input type="button" onclick="javascript:ocultarConfirmacionDocumentacionFisica();"  value="<fmt:message key='intercambioRegistral.boton.cancelarDocNoRecibida' />" / >
			</div>

			<div id="buscarNuevoDestino" style="display:none;">
				<div style="padding: 30px;">

					<h2><fmt:message key="intercambioRegistral.unidad.destinataria.dco"/></h2>
					<h4><fmt:message key="intercambioRegistral.busquedaDestino.entidadRegistral"/></h4>
					<div class="formRow">
						<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoEntidad"/></label><input type="text"  class="inputRO" name="entityCode" id="entityCode" value="<c:out value='${defaultEntityCode}' />"/><img src="images/buscar2.gif" class="buscarEntidadesRegistralesLupa imageClickable"></img>
					</div>
					<div class="formRow">
						<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreEntidad"/></label><input type="text"  class="inputRO" name="entityName" id="entityName" style="width:320px;" value="<c:out value='${defaultEntityName}' />"/>
					</div>

					<h4><fmt:message key="intercambioRegistral.busquedaDestino.unidadTramitacion"/></h4>

					<div class="formRow">
						<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.codigoUnidad"/></label><input type="text"  class="inputRO" name="tramunitCode" id="tramunitCode" value="<c:out value='${defaultTramunitCode}' />" /><img src="images/buscar2.gif" class="buscarUnidadesTramitacionLupa imageClickable"></img>
					</div>
					<div class="formRow">
						<label class="label" style="width:140px;float:left;"><fmt:message key="intercambioRegistral.busquedaDestino.nombreUnidad"/></label><input type="text"  class="inputRO" name="tramunitName" id="tramunitName" style="width:320px;" value="<c:out value='${defaultTramunitName}' />"/>
					</div>

					<div class="formRow">
					<label class="label" style="width:140px;float:left;font-weight: bold;" for="observaciones"><fmt:message key="intercambioRegistral.observaciones"/></label>
					<input type="text" id="observaciones" name="observaciones" />
					</div>

					<p>
						<input type="button" onclick="javascript:submitReenviarIntercambios();" value="<fmt:message key='intercambioRegistral.boton.aceptar' />" / >
						<input type="button" onclick="javascript:cancelarReenvio();"  value="<fmt:message key='intercambioRegistral.boton.cancelar' />" / >
					</p>

				</div>
			</div>
			<div id="divMotivoRechazo" style="display:none;">
			<h5><fmt:message key="intercambioRegistral.motivoRechazo" /></h5>
			<p>
				<textarea rows="3" cols="30" name="motivoRechazo"></textarea>
				</p>
				 <input type="button" onclick="javascript:submitRechazarIntercambiosRegistrales();" value="<fmt:message key='intercambioRegistral.boton.rechazar' />" / >
				 <input type="button" onclick="javascript:ocultarMotivoRechazo();"  value="<fmt:message key='intercambioRegistral.boton.cancelarMotivoRechazo' />" / >
			</div>
		</c:if>

			<%--
			Muestra una tabla con objetos de la clase BandejaEntradaItemVO
			 --%>

			<!-- tabla resultados estado pendiente -->
			<c:if test="${estado==ESTADO_ENTRADA_PENDIENTE}">

				<display:table htmlId="bandejaEntrada" name="bandejaEntrada" id="row" requestURI="/BandejaEntradaIntercambioRegistral.do" pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaEntradaTableDecorator">
					<display:column><input name="checkRegistro" type="checkbox" value="<c:out value='${row.idIntercambioInterno}'/>"/></display:column>
					<display:column sortable="true" property="numeroRegistroOriginal" titleKey="intercambioRegistral.tabla.numeroRegistro.original"></display:column>
					<display:column sortable="true" property="fechaRegistro" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaRegistro"></display:column>
					<display:column property="tipoLibro" titleKey="intercambioRegistral.tabla.tipoRegistro"></display:column>
					<display:column sortable="true" property="origenName" titleKey="intercambioRegistral.tabla.origen"></display:column>
					<display:column titleKey="intercambioRegistral.tabla.documentaionFisica">
						<c:out value='${row.documentacionFisicaIntercambioRegistral.name}'/>
						<input type="hidden" name="documentacionFisica" value="<c:out value='${row.documentacionFisicaIntercambioRegistral.value}'/>"/>
					</display:column>
					<display:column sortable="true" property="estado" titleKey="intercambioRegistral.tabla.estado"></display:column>
					<display:column property="fechaEstado" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaEstado"></display:column>

					
					<display:column titleKey="intercambioRegistral.tabla.masInfo">
						<!-- <a href="javascript:mostrarInfoSolicitudIntercambio('<c:out value='${row.idIntercambioInterno}'/>')">Más info</a>-->
						<a class="linkInfoSolicitudIntercambio" href="MostrarIntercambioRegistral.do?idIntercambio=<c:out value='${row.idIntercambioInterno}'/>">
						<fmt:message key="intercambioRegistral.tabla.masInfo"/>
						</a>
					</display:column>

					<display:setProperty name="basic.msg.empty_list">
						<fmt:message key="intercambioRegistral.tabla.vacia" />
					</display:setProperty>
					<display:setProperty name="paging.banner.item_name">
						<fmt:message key="intercambioRegistral.tabla.item_name" />
					</display:setProperty>
					<display:setProperty name="paging.banner.items_name">
						<fmt:message key="intercambioRegistral.tabla.items_name" />
					</display:setProperty>
				</display:table>


			<script type="text/javascript">
			jQuery("a.linkInfoSolicitudIntercambio").fancybox({
				'hideOnContentClick': true
			});
			</script>

			</c:if>



			<!-- tabla resultados estado aceptado -->
			<c:if test="${estado==ESTADO_ENTRADA_ACEPTADO}">
				<!-- aceptados -->
				<display:table htmlId="bandejaEntradaAceptados" name="bandejaEntrada" id="row" requestURI="/BandejaEntradaIntercambioRegistral.do" pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaEntradaTableDecorator">

					<display:column><input id="idLibroParaRegistro_<c:out value='${row.idRegistro}'/>" type="hidden" value="<c:out value='${row.idLibro}'/>"/></display:column>
					<display:column sortable="true" property="numeroRegistro" titleKey="intercambioRegistral.tabla.numeroRegistro"></display:column>
					<display:column sortable="true" property="fechaRegistro" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaRegistro"></display:column>
					<%-- Entidad Origen --%>
					<display:column sortable="true" property="nombreEntidadTramitacion" titleKey="intercambioRegistral.tabla.origen.entidadRegistral"></display:column>
					<%--Unidad Origen --%>
					<display:column sortable="true" property="nombreUnidadTramitacion" titleKey="intercambioRegistral.tabla.origen.unidadTramitacion"></display:column>
					
					<display:column property="estado" titleKey="intercambioRegistral.tabla.estado"></display:column>
					<display:column sortable="true" property="fechaIntercambioRegistral" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaIntercambio"></display:column>
					<display:column sortable="true" property="idIntercambioRegistral" titleKey="intercambioRegistral.tabla.idIntercambioRegistral"></display:column>
					<display:column sortable="true" property="username" title="usuario"></display:column>
					<display:setProperty name="basic.msg.empty_list">
						<fmt:message key="intercambioRegistral.tabla.vacia" />
					</display:setProperty>
					<display:setProperty name="paging.banner.item_name">
						<fmt:message key="intercambioRegistral.tabla.item_name" />
					</display:setProperty>
					<display:setProperty name="paging.banner.items_name">
						<fmt:message key="intercambioRegistral.tabla.items_name" />
					</display:setProperty>

				</display:table>
			</c:if>

			<!-- tabla resultados estado rechazado -->
			<c:if test="${estado==ESTADO_ENTRADA_RECHAZADO  || estado==ESTADO_ENTRADA_REENVIADO}">
				<!-- aceptados y rechazados  -->
				<display:table htmlId="bandejaEntrada" name="bandejaEntrada" id="row" requestURI="/BandejaEntradaIntercambioRegistral.do" pagesize="10" decorator="es.ieci.tecdoc.isicres.api.intercambioregistral.web.decorator.BandejaEntradaTableDecorator">
					<display:column><input  type="hidden" name="idIntercambioInterno" value="<c:out value='${row.idIntercambioInterno}'/>"/></display:column>

					<display:column sortable="true" property="idIntercambioRegistral" titleKey="intercambioRegistral.tabla.idIntercambioRegistral"></display:column>

					<display:column sortable="true" property="nombreEntidadTramitacion" title="Nombre Entidad"></display:column>

					<display:column sortable="true" property="nombreUnidadTramitacion" title="Unidad Tramitacion"></display:column>

					<display:column sortable="true" property="estado" titleKey="intercambioRegistral.tabla.estado"></display:column>

					<display:column property="fechaEstado" format="{0,date,dd-MM-yyyy HH:mm}" titleKey="intercambioRegistral.tabla.fechaEstado"></display:column>

					<display:column property="fechaIntercambioRegistral" format="{0,date,dd-MM-yyyy HH:mm}" title="Fecha Intercambio"></display:column>

					<display:column sortable="true" property="username" title="usuario"></display:column>

					<display:column sortable="true" property="comentarios" title="comentarios"></display:column>

					<display:setProperty name="basic.msg.empty_list">
						<fmt:message key="intercambioRegistral.tabla.vacia" />
					</display:setProperty>
					<display:setProperty name="paging.banner.item_name">
						<fmt:message key="intercambioRegistral.tabla.item_name" />
					</display:setProperty>
					<display:setProperty name="paging.banner.items_name">
						<fmt:message key="intercambioRegistral.tabla.items_name" />
					</display:setProperty>
				</display:table>
			</c:if>

				<script type="text/javascript">
				function abrirRegistroOInfoIntercambioRegistral(event)
				{
					if ( !event )
				    {
				        event = window.event;
				    }
				    var target = event.currentTarget ? event.currentTarget : event.srcElement;
					var idRegistro = target.id;

					if(idRegistro=='')
					{
						idRegistro = target.parentNode.id;
					}

					var idLibro = document.getElementById('idLibroParaRegistro_'+idRegistro).value;

					var URL = top.g_URL + "/AbrirLibroVerRegistroIntercambioRegistral.do?AppId=" + top.g_AppId.toString()
							+ "&SessionPId=" + top.g_SessionPId + "&FolderView=1&ArchiveId=" + idLibro
				            + "&ArchiveName=" + top.g_ArchiveName.toString()
				            + "&ArchivePId=" + top.g_ArchivePId.toString()
				            + "&FolderPId=" + top.g_FolderPId.toString()
				            + "&FolderId=" + idRegistro
				            + "&VldSave=1" + "&Idioma=" + top.Idioma.toString()
				            + "&numIdioma=" + top.numIdioma.toString()
				            + "&FdrQryPId=" + top.g_FdrQryPId.toString()
					        + "&OpenType=0";



					top.OpenNewWindow(URL, "", "10000", "10000", "auto","yes");
				}
				</script>


		<input type="hidden" id="docRecibida" name="docRecibida" value="false" />

		</form>
		<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no"
						style="height:345px;left:5px;position:absolute;top:5px;width:450px;display:none">
					</iframe>

					<script type="text/javascript">
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
				          dataType: 'html',
				          success:function(data){
				            $.fancybox({
				              modal:true,
				              content:data
				              });
				            putEventToFindTramUnit();
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
					        url: 'BuscarUnidadesTramitacionDCO.do?tramunitCodeToFind='+$('#tramunitCodeToFind').val()+'&tramunitNameToFind='+$('#tramunitNameToFind').val(),
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
					return true;
				}

				//-->
				</script>

	</body>
	</html>