<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres" %>


<div class="tercerosContent">

	<sicres:statusMessage message="${flashScope.statusMessage}" messageId="statusMessage" />

	<form:form action="tercero/fisico/${terceroFisico.method}.action"
		commandName="terceroFisico" id="tercero-fisico-form">

		<sicres:formHeader titleKey="label.form.tercero.fisico.title"
						   submitId="saveTerceroFisico"
						   submitKey="label.form.button.guardar"
						   submitCssClass="guardar"
						   cancelId="cancelTerceroFisico"
						   cancelKey="label.form.button.cerrar"
						   cancelCssClass="cerrar" />

		<form:errors path="*" cssClass="form-errors" element="div" htmlEscape="on" />

		<div class="formulario">
			<spring:nestedPath path="content">
				<p>
					<form:label path="tipoDocumento.id">
						<spring:message code="label.form.tercero.fisico.tipoDocumento" />
					</form:label>
					<form:select path="tipoDocumento.id" tabindex="10">
						<option value="">
							<spring:message code="label.form.tercero.fisico.tipoDocumento.choose" />
						</option>
						<c:forEach items="${tiposDocumentos}" var="tipoDocumento">
							<form:option value="${tipoDocumento.id}">${tipoDocumento.descripcion}</form:option>
						</c:forEach>
					</form:select>
				</p>
				<p>
					<spring:bind path="numeroDocumento">
						<label for="${status.expression}">
							<spring:message code="label.form.tercero.fisico.numeroDocumento" />
						</label>
						<input type="text" name="${status.expression}" id="${status.expression}" class="small ${sessionScope.texttransform}"
							value="${status.value}" tabindex="11"/>
						<span id="errorNumeroDocumento"></span>
						<span id="infoNumeroDocumento"></span>
					</spring:bind>
				</p>
				<p>
					<spring:bind path="apellido1">
						<label for="${status.expression}">
							<spring:message code="label.form.tercero.fisico.apellido1" />
						</label>
						<input type="text" name="${status.expression}" class="medium ${sessionScope.texttransform}"
							value="${status.value}" maxlength="${maxlengthName}" tabindex="12" />
					</spring:bind>
				</p>
				<p>
					<spring:bind path="apellido2">
						<label for="${status.expression}">
							<spring:message code="label.form.tercero.fisico.apellido2" />
						</label>
						<input type="text" name="${status.expression}" class="medium ${sessionScope.texttransform}"
							value="${status.value}" maxlength="${maxlengthName}" tabindex="13"/>
					</spring:bind>
				</p>
				<p>
					<spring:bind path="nombre">
						<label for="${status.expression}">
							<spring:message code="label.form.tercero.fisico.nombre" />
						</label>
						<input type="text" name="${status.expression}" class="medium ${sessionScope.texttransform}"
							value="${status.value}" tabindex="14" />
					</spring:bind>
				</p>
				<spring:bind path="id">
					<input type="hidden" name="${status.expression}" id="${status.expression}"
						value="${status.value}" />
				</spring:bind>
			</spring:nestedPath>
		</div>
	</form:form>
	<c:if test="${terceroFisico.method == 'update'}">
		<div id="tabs">
			<ul>
				<li>
					<a href="direccion/fisica/list.action?tercero=${terceroFisico.content.id}">
						<span>
							<spring:message code="label.form.tercero.direcciones.fisicas.link" />
						</span>
					</a>
				</li>
				<li>
					<a href="direccion/telematica/list.action?tercero=${terceroFisico.content.id}">
						<span>
							<spring:message code="label.form.tercero.direcciones.telematicas.link" />
						</span>
					</a>
				</li>
			</ul>
		</div>
	</c:if>
	<div id="dialog-confirm" title="">
		<p>
			<spring:message code="label.form.tercero.duplicated.nif" />
		</p>
		<ul id="terceros-repetidos">
		</ul>
		<p>
			<spring:message code="label.form.tercero.duplicated.nif.question"/>
		</p>
	</div>
</div>

	<script type="text/javascript">
	<!--
		jQuery(document).ready(function($) {

			function saveTercero(){
				$.ajax({
					type: 'POST',
					cache: false,
					url: $("#tercero-fisico-form").attr('action'),
					data: $("#tercero-fisico-form").serialize()+ "&" + $("#saveTerceroFisico").attr("name") + "=" + $("#saveTerceroFisico").val(),
					dataType: 'html',
					success: function(data) {
						$('#fancybox-content').html(data);
					}
				});
			}

			// La perdida de foco del control Numero de documento provoca una validacion de formato del mismo
			$("#content\\.numeroDocumento").blur(function() {
				$tipoDocumento = $("#content\\.tipoDocumento\\.id").val();

				// Si el tipo de documento indicado es NIF ...
				if($tipoDocumento == '2'){
					if(valida_nif('content\\.numeroDocumento','errorNumeroDocumento','infoNumeroDocumento')){
						$('#errorNumeroDocumento').hide();
						$('#infoNumeroDocumento').hide();
					}
				// Si el tipo de documento indicado es NIE ...
				}else if($tipoDocumento == '4'){
					if(valida_nie('content\\.numeroDocumento','errorNumeroDocumento','infoNumeroDocumento')){
						$('#errorNumeroDocumento').hide();
						$('#infoNumeroDocumento').hide();
					}
				}

			});

			// El formulario no se envia por submit, se hace por AJAX
			$("#tercero-fisico-form").submit(function(e){
				return false;
			});


			// Al pulsar guardar....
			$("#saveTerceroFisico").click(function(){

				$("#dialog:ui-dialog").dialog("destroy");

				// Pedimos confirmacion al usuario
				$("#dialog-confirm").dialog({
					autoOpen:false,
					resizable: false,
					zIndex: 3999,
					modal: true,
					buttons: {
						"Si": function() {
							saveTercero();
							$( this ).dialog( "close" );
						},
						"No": function() {
							$( this ).dialog( "close" );

						}
					}
				});


				// Hacemos una busqueda por numero de doumento identificativo
				if($("#content\\.numeroDocumento").val() != ""){
					dwrService.getTercerosFisicos($("#content\\.numeroDocumento").val(),$("#content\\.id").val(),{
						callback:function(data){
							// Si existe algun tercero con el mismo numero de documento se pide al usuario confirmacion para seguir con la creacion
							if(data.length > 0){
								var $terceros = data;
								$("#terceros-repetidos").empty();
								jQuery.each($terceros,function(index,value){
										$("#terceros-repetidos").append("<li>" + value.descripcion + "</li>");
								});
								$("#dialog-confirm").dialog("open");
							}else{
								saveTercero();
							}
						}
					});
				}else{
					saveTercero();
				}

				return false;
			});

			// Se cierra la ventana con el formulario de alta del tercero fisico
			$("#cancelTerceroFisico").click(function(e){
				e.preventDefault();

				var id = $('input[name="content.id"]').val();

				if(id != ""){
					var urlClose;
					//Comprobamos si la url ha utilizar cuando se cierra la ventana es nula/vacia,
					//se ejecutará la operativa por defecto tratando los datos como interesado
					if(actionCancelEditInteresado!=""){
						//se utiliza la url que se recibe como variable (puede ser un interesado o un representante)
						urlClose = actionCancelEditInteresado;
					}else{
						//se esta trabajando con un interesado
						urlClose = 'interesado/crud.action?method=addOrUpdateInteresado&tercero.id='+ id;
					}

					$.ajax({
						   type: 'POST',
						   cache: false,
						   url: urlClose,
						   dataType: 'html',
						   success: function(data) {
								//recargamos el frame de busqueda de interesados
								top.Main.Folder.FolderData.FolderFormData.Vld.VldInter.location.reload();
								//asigmoas los resultados de los interesados insertados
								$.get('interesado/crud.action?method=listInteresados', function(data) {
									$('#interesadosRegistro').html(data);
								});
						   }
						});
				} else {
					//recargamos el frame de busqueda de interesados
					top.Main.Folder.FolderData.FolderFormData.Vld.VldInter.location.reload();
				}

			});

			// La gestion de direcciones del tercero se hace mediante el componentes Tabs de jQuery UI
			$("#tabs").tabs({
				spinner:'<spring:message code="label.tab.loading" />',
				cache:false,
				ajaxOptions: {
					error: function(xhr, status, index, anchor) {
						$(anchor.hash).html("No se ha podido cargar el contenido. Inténtelo de nuevo más tarde.");
					},
					cache:false
				}
			});

		});
	//-->
	</script>