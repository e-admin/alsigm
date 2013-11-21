<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%String idTercero = request.getParameter("tercero.id");%>

<spring:url value="interesado/crud.action?method=addOrUpdateRepresentante" var="selectURI">
	<spring:param name="tercero.id" value="<%=idTercero%>"/>
</spring:url>

<div id="resultSearchRepresentante" style="width: auto; overflow: auto; position: relative;">
	<div id="representanteErrorMessage">
	</div>

	<display:table name="list" id="interesados" requestURI="representante/search.action" decorator="es.ieci.tecdoc.sicres.terceros.web.view.decorator.TercerosTableDecorator" pagesize="10">
		<display:column property="numeroDocumento" titleKey="label.list.terceros.search.numeroDocumento" sortable="true" />
		<!-- nombre completo -->
		<display:column property="descripcion" titleKey="label.list.terceros.search.descripcion" sortable="true" />
		<!-- direccion fisica principal -->
		<display:column property="direccionFisicaPrincipal" titleKey="label.list.terceros.search.direccionFisica" sortable="true" />
		<!-- direccion telematica principal -->
		<display:column property="direccionTelematicaPrincipal" titleKey="label.list.terceros.search.direccionTelematica" sortable="true" />
		<display:column href="${selectURI}" paramId="representante.id" paramProperty="id" class="selectTercero">
			<img src="images/terceros/add.png" />
		</display:column>
		<display:setProperty name="paging.banner.item_name">
			<spring:message code="label.list.terceros.search.item" />
		</display:setProperty>
		<display:setProperty name="paging.banner.items_name">
			<spring:message code="label.list.terceros.search.items" />
		</display:setProperty>
	</display:table>
</div>

<script type="text/javascript">
<!--
	jQuery(document).ready(function($){

		$('#interesadosErrorMessage').hide();

		$('.selectTercero > a').click(function(e){
			e.preventDefault();
			 $.ajax({
		           type: 'GET',
		           cache: false,
		           url: $(this).attr('href'),
		           dataType: 'html',
		           success:function(data){
		           		$('#interesadosRegistro').html(data);
		           		$.fancybox.close();
		           },
		           error:function (xhr, ajaxOptions, thrownError){
						if(xhr.status == 412){
							$('#representanteErrorMessage').html('<spring:message code="representante.duplicated" />');
							$('#representanteErrorMessage').show();
						}
		           }
			 	}
		       );
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
						$('#fancybox-content').html(data);
		           },
		           error:function (xhr, ajaxOptions, thrownError){
						if(xhr.status == 412){
							$('#interesadosErrorMessage').html('<spring:message code="representante.duplicated" />');
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
	});
//-->
</script>