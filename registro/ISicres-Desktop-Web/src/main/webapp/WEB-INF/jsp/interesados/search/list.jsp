<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:url value="tercero/fisico/retrieve.action" var="editTerceroFisicoURI" />
<spring:url value="tercero/juridico/retrieve.action" var="editTerceroJuridicoURI" />
<spring:url value="interesado/crud.action?method=addOrUpdateInteresado" var="selectURI" />

<div id="resultadoSearch" style="width: auto; height: auto; overflow: auto; position: relative;">
	<div id="interesadosErrorMessage">
	</div>

	<c:choose>
		<c:when test="${not empty list}">
			<display:table name="list" id="interesados" defaultsort="2" requestURI="interesado/search.action" decorator="es.ieci.tecdoc.sicres.terceros.web.view.decorator.TercerosTableDecorator" pagesize="10">
				<display:column property="numeroDocumento" titleKey="label.list.terceros.search.numeroDocumento" sortable="true" sortName="numeroDocumento" />
				<!-- nombre completo -->
				<display:column property="descripcion" titleKey="label.list.terceros.search.descripcion" sortable="true" sortName="descripcion"/>
				<!-- direccion fisica principal -->
				<display:column property="direccionFisicaPrincipal" titleKey="label.list.terceros.search.direccionFisica" sortable="true" sortName="direccionFisicaPrincipal" />
				<!-- direccion telematica principal -->
				<display:column property="direccionTelematicaPrincipal" titleKey="label.list.terceros.search.direccionTelematica" sortable="true" sortName="direccionTelematicaPrincipal" />
				<display:column href="${selectURI}" paramId="tercero.id" paramProperty="id" class="selectTercero">
					<img src="images/terceros/add.png" />
				</display:column>
					<c:choose>
						<c:when test="${interesados['class'].simpleName eq 'TerceroValidadoFisicoVO'}">
							<display:column href="${editTerceroFisicoURI}" paramId="content.id" paramProperty="id" class="editTercero">
								<img src="images/terceros/editar.png" />
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column href="${editTerceroJuridicoURI}" paramId="content.id" paramProperty="id" class="editTercero">
								<img src="images/terceros/editar.png" />
							</display:column>
						</c:otherwise>
					</c:choose>
				<display:setProperty name="paging.banner.item_name">
					<spring:message code="label.list.terceros.search.item" />
				</display:setProperty>
				<display:setProperty name="paging.banner.items_name">
					<spring:message code="label.list.terceros.search.items" />
				</display:setProperty>
			</display:table>
		</c:when>
		<c:otherwise>
				<div id="notFoundInter" style="width: 90%;">
					<div class="onecol">
						<spring:message code="label.list.terceros.search.noResults" />&nbsp;<spring:message code="label.list.terceros.search.question.create" />
					</div>
					<div class="twocol">
						<div class="column first">
							<a class="addPersonaFisica" href="tercero/fisico/new.action">
								<spring:message code="label.list.terceros.search.new.tercero.fisico" />
							</a>
						</div>
						<div class="column last">
							<a class="addPersonaJuridica" href="tercero/juridico/new.action">
								<spring:message code="label.list.terceros.search.new.tercero.juridico" />
							</a>
						</div>
					</div>
				</div>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
<!--
	jQuery(document).ready(function($){

		//comprobamos si el usuario no tiene permisos para crear interesados
		if(top.g_CreateInterPerms == 0){
			//se reemplaza la capa que contiene los botones de crear interesados por un simple mensaje informativo de no hay resultados
			$("#notFoundInter").replaceWith('<div class="onecol"><spring:message code="label.list.terceros.search.noResults" /></div>');
		}

		//comprobamos los permisos de edicion de interesados
		if(top.g_ModifyInterPerms == 0){
			//sino tiene permisos se oculta el boton editar
			$(".editTercero").hide();
		}


		$('#interesadosErrorMessage').hide();

		$('.selectTercero > a').click(function(e){
			e.preventDefault();

			 $.ajax({
		           type: 'GET',
		           cache: false,
		           url: $(this).attr('href'),
		           dataType: 'html',
		           success:function(data){
						$('#interesadosErrorMessage').hide();
		           		$('#interesadosRegistro').html(data);
		           		$.fancybox.close();
		           },
		           error:function (xhr, ajaxOptions, thrownError){
						if(xhr.status == 412){
							$('#interesadosErrorMessage').html('<spring:message code="interesado.duplicated" />');
							$('#interesadosErrorMessage').show();
						}
	                }
		    	});
			});

			// Al pulsar en "editar" un tercero se muestra el formulario de edicion en una ventana de fancybox
			// y la actualizacion se gestiona mediante Ajax
			$('.editTercero > a').click(function(e){
				e.preventDefault();

				 $.ajax({
			           type: 'GET',
			           cache: false,
			           url: $(this).attr('href'),
			           dataType: 'html',
			           success:function(data){
							$('#interesadosErrorMessage').hide();
							$.fancybox({
								content:data,
								modal:true,
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
			           },
			           error:function (xhr, ajaxOptions, thrownError){
							if(xhr.status == 412){
								$('#interesadosErrorMessage').html('<spring:message code="interesado.duplicated" />');
								$('#interesadosErrorMessage').show();
							}
		                }
			    	});
			});

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
			           }
			    	});
				});

			// Al pulsar en crear tercero fisico/juridico se muestra en una ventana mediante fancybox
			$('.addPersonaFisica').fancybox();
			$('.addPersonaJuridica').fancybox();
		});
//-->
</script>