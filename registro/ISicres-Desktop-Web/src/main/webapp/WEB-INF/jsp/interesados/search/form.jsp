<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>

<spring:url value="tercero/fisico/new.action" var="altaFisicoUrl" />
<spring:url value="tercero/juridico/new.action" var="altaJuridicoUrl" />

<div class="tercerosContent">
	<div class="twocolleftb">
		<div class="column first">
			<h1><spring:message code="label.form.terceros.search.page.title" /></h1>
		</div>
		<div class="column last">
			<a href="${altaFisicoUrl}" class="addPersonaFisica">
				<spring:message code="label.form.terceros.search.new.tercero.fisico" />
			</a>
			<a href="${altaJuridicoUrl}" class="addPersonaJuridica">
				<spring:message code="label.form.terceros.search.new.tercero.juridico" />
			</a>
		</div>
	</div>



	<form:form action="interesado/search.action"
			commandName="command" id="busqueda-tercero-form">

		<sicres:formHeader titleKey="label.form.terceros.search.title"
							submitId="searchTercero"
							submitKey="label.form.button.buscar"
							submitCssClass="aceptar"
							cancelId="cancelSearchTercero"
							cancelKey="label.form.button.cerrar"
							cancelCssClass="cancelar" />

		<form:errors path="*" />

		<c:set var="tabindex" value="0" />

		<div class="formulario">
			<p>
				<spring:bind path="type">
					<label for="${status.expression}">
						<spring:message code="label.form.terceros.search.type" />
					</label>
					<form:select path="${status.expression}" tabindex="${tabindex}">
						<c:forEach items="${searchTypes}" var="type">
							<form:option value="${type.value}" htmlEscape="on">
								<spring:message code="label.form.terceros.search.type.${type.value}" />
							</form:option>
						</c:forEach>
					</form:select>
				</spring:bind>
			</p>
				<c:forEach items="${command.filters}" varStatus="filter">
					<p id="filters${filter.index}">
						<spring:bind path="filters[${filter.index}].field">
							<label for="${status.expression}">
								<spring:message code="label.form.terceros.search.${status.expression}" />
							</label>
							<form:hidden path="${status.expression}"/>
						</spring:bind>
						<spring:bind path="filters[${filter.index}].operator">
							<form:select path="${status.expression}" tabindex="${filter.index+1}">
								<c:forEach items="${operators}" var="operator">
									<form:option value="${operator.value}" htmlEscape="on">
										<spring:message code="label.form.terceros.search.operator.${operator.value}" />
									</form:option>
								</c:forEach>
							</form:select>
						</spring:bind>
						<spring:bind path="filters[${filter.index}].value">
							<form:input path="${status.expression}" class="medium ${sessionScope.texttransform}" tabindex="${filter.index+1}"/>
						</spring:bind>
					</p>
				</c:forEach>
			</div>
	</form:form>

	<div id="contenedorInteresados" class="twocolleftb">
		<div class="column first">
			<h1>
				<spring:message code="label.form.terceros.search.interesados.title" />
			</h1>
		</div>
		<div id="botoneraInteresados" class="column last">
			<a id="editTerceroLink" href="#">
				<spring:message code="label.form.terceros.search.edit.interesado.link" />
			</a>
			<a id="selectAddressLink" href="#">
				<spring:message code="label.form.terceros.search.selectAddress.interesado.link" />
			</a>
			<a id="deleteInteresadoLink" href="#">
				<spring:message code="label.form.terceros.search.delete.interesado.link" />
			</a>
			<a id="slideUpInteresado" alt="Subir interesado">
				<spring:message code="label.form.terceros.search.slideUp.interesado.link" />
			</a>
			<a id="slideDownInteresado" alt="Bajar interesado">
				<spring:message code="label.form.terceros.search.slideDown.interesado.link" />
			</a>
		</div>
	</div>
	<div id="interesadosRegistro" class="onecol">
	</div>

</div>
	<script type="text/javascript" src="scripts/list.js"></script>
	<script type="text/javascript" src="scripts/frmint.js"></script>
	<script type="text/javascript">
	<!--
	//variable con la operativa ha realizar cuando se cierra la ventana de
	//edicion de interesados/representantes
	var actionCancelEditInteresado="";

	function OnOk(strInteresados){
				// Guardamos la cadena en el campo
				if ( top.g_Field ){
					top.g_Field.value = strInteresados;
					// Asignamos los valores de la lista a los campos interesados
					asignSustitutos(strInteresados, top.g_Field.getAttribute("FldId"), top.g_Field.getAttribute("tblvalidated"));

					top.g_WndVld.document.getElementById("Interesados").setAttribute("value",strInteresados);
					top.g_WndVld.document.getElementById("Interesados").contentWindow.LoadFrameInt();
					top.g_WndVld.document.getElementById("Interesados").contentWindow.SetChange();
					top.g_WndVld.cambioValor(top.g_Field);
   				}

   				if ((g_VldInterSaved) || (top.g_WndVld.bIsActiveSave)){
					g_VldInterSaved = false;
					//Activamos el guardar
					top.Main.Folder.FolderBar.ActivateSave();
				}

				// Cerramos los frames
				top.SetInnerText(parent.VldTitle.document.getElementById("V_Title"), "");
				parent.VldInter.rows = "100%, *";
				eval(top.g_VldPath + ".document.getElementById('Vld').style.display = 'none'");

				// Volvemos a mostrar el formulario
				top.g_FormVld.style.visibility = 'visible';

   				//Para evitar que el foco se situe en el campo oculto de busqueda. Quitar cuando se rehaga la edicion de interesados
				//top.setFocus(top.g_FormVld.document.Interesados.document.getElementById("validInt"));

				eval( top.g_VldPath + ".OnHelpWindow=false" );

				if ((top.Main != null)
				 && (top.Main.Folder != null)
				 && (top.Main.Folder.FolderData != null)
				 && (top.Main.Folder.FolderData.FolderFormTree != null)
				 && (top.Main.Folder.FolderData.FolderFormTree.document != null)
				 && (top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs") != null)
				 && (top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs").style != null)){
					top.Main.Folder.FolderData.FolderFormTree.document.getElementById("divDocs").style.visibility="visible";
				}
			}

		jQuery(document).ready(function($){

			//comprobamos si el usuario no tiene permisos para crear interesados
			if(top.g_CreateInterPerms == 0){
				//se reemplaza la capa que contiene los botones de crear interesados por un simple mensaje informativo de no hay resultados
				$(".addPersonaFisica").hide();
				$(".addPersonaJuridica").hide();
			}

			//comprobamos los permisos de edicion de interesados
			if(top.g_ModifyInterPerms == 0){
				//sino tiene permisos se oculta el boton editar
				$("#editTerceroLink").css("visibility","hidden");
			}

			function eventoNuevoInteresadoFisico(){
				$('.addPersonaFisica').click();
			}

			function eventoNuevoInteresadoJuridico(){
				$('.addPersonaJuridica').click();
			}

			//Obtiene un array con los ids de interesados a partir del checkbox del representante
			function obtenerIDInteresadosByRepresentantes(){
				var result = new Array();
				var i = 0;

				$($("#interesadosRegistro .representante:input:checked")).each(function (index) {
					result[i] = $(this).attr("interesado");
					i++;
				});

				return result;
			}


			// Cambios en el tipo de busqueda hace que se muestren unos controles u otros
			$("#busqueda-tercero-form #type").change(function(){
				if($(this).val() == "1") {
					 jQuery.each([$('#busqueda-tercero-form #filters0 > :input'),$('#busqueda-tercero-form #filters1 > :input'),$('#busqueda-tercero-form #filters2 > :input'),$('#busqueda-tercero-form #filters3 > :input')],function(){
						$(this).removeAttr('disabled');
					 });
					 jQuery.each([$('#busqueda-tercero-form #filters4 > :input'),$('#busqueda-tercero-form #filters5 > :input')],function(){
						$(this).attr('disabled', true);
					 });
					 jQuery.each([$('#busqueda-tercero-form #filters0'),$('#busqueda-tercero-form #filters1'),$('#busqueda-tercero-form #filters2'),$('#busqueda-tercero-form #filters3')],function(){
						$(this).show();
					 });
					 jQuery.each([$('#busqueda-tercero-form #filters4'),$('#busqueda-tercero-form #filters5')],function(){
						$(this).hide();
					 });
				}else if($(this).val() == "2"){
					 jQuery.each([$('#busqueda-tercero-form #filters0 > :input'),$('#busqueda-tercero-form #filters1 > :input'),$('#busqueda-tercero-form #filters2 > :input'),$('#busqueda-tercero-form #filters3 > :input')],function(){
						$(this).attr('disabled', true);
					 });
					 jQuery.each([$('#busqueda-tercero-form #filters4 > :input'),$('#busqueda-tercero-form #filters5 > :input')],function(){
						$(this).removeAttr('disabled');
					 });
					 jQuery.each([$('#busqueda-tercero-form #filters0'),$('#busqueda-tercero-form #filters1'),$('#busqueda-tercero-form #filters2'),$('#busqueda-tercero-form #filters3')],function(){
						$(this).hide();
					 });
					 jQuery.each([$('#busqueda-tercero-form #filters4'),$('#busqueda-tercero-form #filters5')],function(){
						$(this).show();
					 });
				}
			});
			$('#busqueda-tercero-form #type').trigger('change');

			// El formulario de busqueda no se envia por submit. Se hace por Ajax
			$('#busqueda-tercero-form').submit(function(e){
				return false;
			});

			// La busqueda se realiza mediante Ajax. El resultado se muestra usando fancybox
	        $('#searchTercero').click(function(e){
	            e.preventDefault();

	            $.fancybox.showActivity();

	          	$.ajax({
			           type: 'POST',
			           cache: false,
			           modal:true,
			           url: $("#busqueda-tercero-form").attr('action'),
			           data: $("#busqueda-tercero-form").serialize(),
			           dataType: 'html',
			           success: function(data) {
							$.fancybox({
									content:data,
									scrolling:'no'
								});
			           }
			       });

	        });

			// Al pulsar "Cerrar" se invoca la URL 'interesado/crud.action?method=flush' para recuperar una cadena con los interesados seleccionados segun
			// el formato que entiende el formulario de registro
	        $('#cancelSearchTercero').click(function(e){
				e.preventDefault();

				$.ajax({
			           type: 'POST',
			           cache: false,
			           url: 'interesado/crud.action?method=flush',
			           dataType: 'html',
			           success: function(data) {
			        	    // se invoca a la funcion OnOk (copia de una legada) que se encarga de actualizar el atributo "value"
			        	    // del frame "Interesados" con la cadena recuperada
			           		OnOk($.trim(data));
			           }
			       });
			    });


			// Se añade un tercero fisico como interesado. La seleccion se hace por Ajax para evitar refrescos de toda la pantalla
	        $('.addPersonaFisica').click(function(e){
				e.preventDefault();

				$.fancybox.showActivity();

	            $.ajax({
			           type: 'POST',
			           cache: false,
			           url: $(this).attr('href'),
			           dataType: 'html',
			           success: function(data) {
			           		$.fancybox({
				           		modal:true,
								content:data,
								onCleanup:function(){
				           			if($('input[name="content.id"]').val() != ""){
				    					$.ajax({
				    				           type: 'POST',
				    				           cache: false,
				    				           url: 'interesado/crud.action?method=addOrUpdateInteresado',
				    				           data: 'tercero.id='+$('input[name="content.id"]').val(),
				    				           dataType: 'html',
				    				           success: function(data) {
				    								$.get('interesado/crud.action?method=listInteresados', function(data) {
				    									$('#interesadosRegistro').html(data);
				    								});
				    				           }
				    						});
				    				}
			           			}
							});
			           }
			       });

		        });

	     	// Se añade un tercero juridico como interesado. La seleccion se hace por Ajax para evitar refrescos de toda la pantalla
	        $('.addPersonaJuridica').click(function(e){
	        	e.preventDefault();

	        	$.fancybox.showActivity();

	            $.ajax({
			           type: 'POST',
			           modal: true,
			           cache: false,
			           url: $(this).attr('href'),
			           dataType: 'html',
			           success: function(data) {
			           		$.fancybox({
				           		modal:true,
								content:data,
								onCleanup:function(){
			           			if($('input[name="content.id"]').val() != ""){
			    					$.ajax({
			    				           type: 'POST',
			    				           cache: false,
			    				           url: 'interesado/crud.action?method=addOrUpdateInteresado',
			    				           data: 'tercero.id='+$('input[name="content.id"]').val(),
			    				           dataType: 'html',
			    				           success: function(data) {
			    								$.get('interesado/crud.action?method=listInteresados', function(data) {
			    									$('#interesadosRegistro').html(data);
			    								});
			    				           }
			    						});
			    				}
		           			}
							});
			           }
			       });
		     });

	        $.ajax({
		           type: 'GET',
		           cache: false,
		           url: 'interesado/crud.action?method=listInteresados',
		           dataType: 'html',
		           success:function(data){
		           		$("#interesadosRegistro").html(data);
		           		}
		           }
		       );

	        // Edicion de un tercero. El formulario a buscar varia segun sea fisico o juridico
		   	$('#editTerceroLink').click(function(e){
				e.preventDefault();

				var checked = $("#interesadosRegistro :input:checked").size();
				switch(checked)
				{
				case 0: alert('seleccione un interesado');
				break;
				case 1:

					var openURL = null;
					var closeURL = null;
					var idTercero = null;
					var parameters = null;

					if($("#interesadosRegistro :input:checked").hasClass('interesado')){
						//id del tercero
						idTercero = $("#interesadosRegistro :input:checked").val();
						actionCancelEditInteresado = 'interesado/crud.action?method=addOrUpdateInteresado&tercero.id='+idTercero;
						parameters = "tercero.id=" + $("#interesadosRegistro :input:checked").val();
					}else{
						//id del representante
						idTercero = $("#interesadosRegistro :input:checked").val();
						actionCancelEditInteresado = 'interesado/crud.action?method=addOrUpdateRepresentante&tercero.id='+$('input[name="representante['+$("#interesadosRegistro :input:checked").val()+']"]').val() + '&representante.id='+$("#interesadosRegistro :input:checked").val();
						parameters = "tercero.id=" + $('input[name="representante['+$("#interesadosRegistro :input:checked").val()+']"]').val();
					}

						if($("#interesadosRegistro :input:checked").hasClass('fisico')){
							openURL='tercero/fisico/retrieve.action?content.id='+ idTercero;

							$.ajax({
						           type: 'GET',
						           cache: false,
						           url: openURL,
						           dataType: 'html',
						           success:function(data){
										$.fancybox.showActivity();
						           		$.fancybox({
							           		modal:true,
										content:data
							           	});
						           	}
						           }
						       );
						}else{
							openURL = 'tercero/juridico/retrieve.action?content.id='+idTercero;

							$.ajax({
						           type: 'GET',
						           cache: false,
						           url: openURL,
						           dataType: 'html',
						           success:function(data){
										$.fancybox.showActivity();
						           		$.fancybox({
							           		modal:true,
											content:data
							           	});
						           	}
						           }
						       );
						}


				break;
				default: alert('no se puede editar más de un interesado');
				}
			   	});

	        // Cambio/seleccion de la direccion de un interesado
		   	$('#selectAddressLink').click(function(e){
				e.preventDefault();

				var checkedItem = $("#interesadosRegistro :input:checked");
				switch(checkedItem.size()){
				case 0: alert('Debe seleccionar al menos un interesado/representante');
						break;
				case 1:
						$.fancybox.showActivity();
						if(checkedItem.hasClass('interesado')){
							$.ajax({
								type: 'GET',
						        cache: false,
						        url: 'interesado/changeDireccion.action?tercero.id='+checkedItem.val(),
						        dataType: 'html',
						        success:function(data){
						        	$.fancybox(data);
						         		}
						           }
								);
						}else{
							var selector = 'input[name="representante['+checkedItem.val()+']"]';
							var representante = $(selector);
							$.ajax({
								type: 'GET',
						        cache: false,
						        url: 'representante/changeDireccion.action?tercero.id='+checkedItem.val()+'&representante.id='+representante.val(),
						        dataType: 'html',
						        success:function(data){
						        	$.fancybox(data);
						         		}
						           }
								);
						}
					break;
				default:alert('No se puede editar la dirección de notificación de mas de un interesado/representante');
				}



			});

	        // Eliminacion de un interesado(el tercero o su representante). Se permite la eliminacion en bloque
		   	$('#deleteInteresadoLink').click(function(e){
				e.preventDefault();

				var checkedItems = $("#interesadosRegistro :input:checked");

				if(checkedItems.size()==0){
					alert('Debe seleccionar al menos un interesado/representante');
				}else{
					$.fancybox.showActivity();
					//Borrado de interesado
					if($("#interesadosRegistro .interesado:input:checked").size()>0){
						$.ajax({
					           type: 'GET',
					           cache: false,
					           url: 'interesado/crud.action?method=deleteInteresado',
					           data:$("#interesadosRegistro .interesado:input:checked").serializeArray(),
					           dataType: 'html',
					           success:function(data){
									$("#interesadosRegistro").html(data);
					           		}
					           }
					       );
					}

					//Borrado de representante
					if($("#interesadosRegistro .representante:input:checked").size()>0){
						$.ajax({
					           type: 'GET',
					           cache: false,
					           url: 'interesado/crud.action?method=deleteRepresentante',
					           data: "interesadosToResetRepresentante=" + obtenerIDInteresadosByRepresentantes(),
					           //data:$("#interesadosRegistro .representante:input:checked").serializeArray(),
					           dataType: 'html',
					           success:function(data){
									$("#interesadosRegistro").html(data);
					           		}
					           }
					       );
					}
					$.fancybox.hideActivity();
				}
			});

	        // Modificacion del orden de un interesado. Se "sube" el interesado
		   	$('#slideUpInteresado').click(function(e){
		   		e.preventDefault();

		   		var checkedItem = $("#interesadosRegistro :input:checked");

		   		switch(checkedItem.size()){
				case 0: alert('Debe seleccionar al menos un interesado');
						break;
				case 1: if($("#interesadosRegistro :input:checked").hasClass('interesado')){
							$.fancybox.showActivity();
							$.ajax({
						           type: 'GET',
						           cache: false,
						           url: 'interesado/crud.action?method=slideUpInteresado&tercero.id='+checkedItem.val(),
						           dataType: 'html',
						           success:function(data){
										$("#interesadosRegistro").html(data);
						           		}
						           }
						       );
							$.fancybox.hideActivity();
						}else{
							alert('No se puede alterar el orden de un representante');
						}
					break;
				case 2:alert('No se puede modificar el orden de más de un interesado a la vez');
					break;
		   		}
		   	});

	        // Modificacion del orden de un interesado. Se "baja" el interesado
		   	$('#slideDownInteresado').click(function(e){
		   		e.preventDefault();

		   		var checkedItem = $("#interesadosRegistro :input:checked");

		   		switch(checkedItem.size()){
				case 0: alert('Debe seleccionar al menos un interesado');
						break;
				case 1: if($("#interesadosRegistro :input:checked").hasClass('interesado')){
							$.fancybox.showActivity();
							$.ajax({
					           type: 'GET',
					           cache: false,
					           url: 'interesado/crud.action?method=slideDownInteresado&tercero.id='+checkedItem.val(),
					           dataType: 'html',
					           success:function(data){
									$("#interesadosRegistro").html(data);
					           		}
					           }
					       	);
							$.fancybox.hideActivity();
						}else{
							alert('No se puede alterar el orden de un representante');
						}
					break;
				case 2:alert('No se puede modificar el orden de más de un interesado a la vez');
					break;
		   		}
		   	});

			//comprobamos si en la carga de la pantalla tiene que realizar alguna operativa
	        if(top.g_actionInitFormInter){
				eval(top.g_actionInitFormInter + "();");
				//inicializamos de nuevo la variable
				top.g_actionInitFormInter = null;
	        }

		});
	//-->
	</script>
