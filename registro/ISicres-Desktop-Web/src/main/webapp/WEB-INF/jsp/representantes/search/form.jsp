<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>


<div class="tercerosContent">

	<form:form action="representante/search.action"
			commandName="command" id="busqueda-representante-form">

		<form:errors path="*" />

		<sicres:formHeader titleKey="label.form.representante.search.title"
							submitId="searchRepresentante"
							submitKey="label.form.button.buscar"
							submitCssClass="aceptar"
							cancelId="cancelSearchRepresentante"
							cancelKey="label.form.button.cerrar"
							cancelCssClass="cancelar" />

		<div class="formulario">
			<p>
				<spring:bind path="type">
					<label for="${status.expression}">
						<spring:message code="label.form.terceros.search.type" />
					</label>
					<form:select path="${status.expression}" tabindex="0" class="selectTypeSearch">
						<c:forEach items="${searchTypes}" var="type">
							<form:option value="${type.value}" htmlEscape="on" >
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
							<form:select path="${status.expression}">
								<c:forEach items="${operators}" var="operator">
									<form:option value="${operator.value}" htmlEscape="on">
										<spring:message code="label.form.terceros.search.operator.${operator.value}" />
									</form:option>
								</c:forEach>
							</form:select>
						</spring:bind>
						<spring:bind path="filters[${filter.index}].value">
							<form:input path="${status.expression}" class="medium ${sessionScope.texttransform}"/>
						</spring:bind>
					</p>
				</c:forEach>
			</div>
			<input type="hidden" name="tercero.id" value="<%= request.getParameter("tercero.id") %>" />
	</form:form>
</div>
	<script type="text/javascript">
	<!--
		jQuery(document).ready(function($){

			var campoSelectBusqueda;
			//obtenemos el combo de tipo de busqueda
			if(top.IsExplorerBrowser()){
				//en caso de ser IE
				campoSelectBusqueda = $("#busqueda-representante-form .selectTypeSearch");
			}else{
				//otros navegadores
				campoSelectBusqueda = $("#busqueda-representante-form #type");
			}

			// El cambio en el tipo de busqueda hace que se muestren unos controles u otros
			$(campoSelectBusqueda).change(function(){
				if($(this).val() == "1") {
					 jQuery.each([$('#busqueda-representante-form #filters0 > :input'),$('#busqueda-representante-form #filters1 > :input'),$('#busqueda-representante-form #filters2 > :input'),$('#busqueda-representante-form #filters3 > :input')],function(){
						//habilitamos los campos input correspondientes
						$(this).removeAttr('disabled');
						//mostramos el elemento padre
						$(this).parent().show();
					 });
					 jQuery.each([$('#busqueda-representante-form #filters4 > :input'),$('#busqueda-representante-form #filters5 > :input')],function(){
						//deshabilitamos los campos input correspondientes
						$(this).attr('disabled', 'disabled');
						//ocultamos el elemento padre
						$(this).parent().hide();
					 });

				}else if($(this).val() == "2"){
					 jQuery.each([$('#busqueda-representante-form #filters0 > :input'),$('#busqueda-representante-form #filters1 > :input'),$('#busqueda-representante-form #filters2 > :input'),$('#busqueda-representante-form #filters3 > :input')],function(){
						//deshabilitamos los campos input correspondientes
						$(this).attr('disabled', 'disabled');
						//ocultamos el elemento padre
						$(this).parent().hide();
					 });
					 jQuery.each([$('#busqueda-representante-form #filters4 > :input'),$('#busqueda-representante-form #filters5 > :input')],function(){
						//habilitamos los campos input correspondientes
						$(this).removeAttr('disabled');
						//mostramos el elemento padre
						$(this).parent().show();
					 });
				}
			});

			$(campoSelectBusqueda).trigger('change');

			// El formulario de busqueda no se envia con submit. Se hace por Ajax
			 $('#busqueda-representante-form').submit(function(e){
				 return false;
			 });

			// La busqueda de representantes se hace por Ajax. El resultado se muestra en una ventana mediante fancybox
	        $('#searchRepresentante').click(function(e){
	            e.preventDefault();

	            $.ajax({
			           type: 'POST',
			           cache: false,
			           url: $("#busqueda-representante-form").attr('action'),
			           data: $("#busqueda-representante-form").serialize(),
			           dataType: 'html',
			           success: function(data) {
	            			$.fancybox(data);
				           }
			       });
	        });

			// Cerramos la ventana
	        $('#cancelSearchRepresentante').click(function(e){
				e.preventDefault();
				$.fancybox.close();
		        });
		});
	//-->
	</script>
