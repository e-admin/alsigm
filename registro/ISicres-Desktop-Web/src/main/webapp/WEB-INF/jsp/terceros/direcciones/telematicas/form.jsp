<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>

<sicres:statusMessage message="${flashScope.statusMessage}" messageId="statusMessage" />

	<form:form action="direccion/telematica/${direccionTelematica.method}.action"
		commandName="direccionTelematica" id="direccion-telematica-form" cssClass="ajax">

		<sicres:formHeader titleKey="label.form.direccion.telematica.title"
									submitId="saveDireccionTelematica"
									submitKey="label.form.button.aceptar"
									submitCssClass="aceptar"
									cancelId="cancelDireccionTelematica"
									cancelKey="label.form.button.cancelar"
									cancelCssClass="cancelar" />

		<form:errors path="*" cssClass="form-errors" element="div" htmlEscape="on" />

		<div class="formulario">
			<spring:nestedPath path="content">
				<p>
					<form:label path="tipoDireccionTelematica">
						<spring:message code="label.form.direccion.telematica.tipo" />
					</form:label>
					<form:select path="tipoDireccionTelematica.id" cssClass="medium" tabindex="40" onchange="javascript:changeValue(this)">
						<option value="">
							<spring:message code="label.form.direccion.telematica.tipo.choose" />
						</option>
						<c:forEach items="${tiposDirecciones}" var="tipo">
							<form:option value="${tipo.id}" htmlEscape="on">${tipo.descripcion}</form:option>
						</c:forEach>
					</form:select>
				</p>
				<p>
					<spring:bind path="direccion">
						<label for="${status.expression}">
							<spring:message code="label.form.direccion.telematica.direccion" />
						</label>
						<textarea cols="30" rows="3" id="${status.expression}" name="${status.expression}" class="medium ${sessionScope.texttransform}" tabindex="41">${status.value}</textarea>
					</spring:bind>
				</p>
				<spring:bind path="id">
					<input type="hidden" name="${status.expression}"
						value="${status.value}" />
				</spring:bind>
				<spring:bind path="tercero.id">
					<input type="hidden" name="${status.expression}"
						value="${status.value}" />
				</spring:bind>

				<spring:bind path="principal">
					<input type="hidden" name="${status.expression}"
						value="${status.value}" />
				</spring:bind>
			</spring:nestedPath>
		</div>
	</form:form>

	<script type="text/javascript">
	<!--

		function ocultarTextArea(combo){
			var textArea = document.getElementById("content.direccion");
			//si es comparecencia electrónica se bloquea el textArea y se añade el literal del combo
			if(combo.value==6){
				//ocultamos el textarea y le asignamos el valor del combo
				textArea.value=combo.options[combo.selectedIndex].text;
				textArea.style.visibility = "hidden";
			}else{
				//activamos el textarea
				textArea.style.visibility = "visible";
				textArea.value="";
			}
		}

		function changeValue(combo){
			//comprobamos si hay que ocultar el textarea
			ocultarTextArea(combo);
		}

		jQuery(document).ready(function($) {

			//validamos si se ha de mostrar/ocultar el textarea de la dirección
			var combo = document.getElementById("content.tipoDireccionTelematica.id");
			ocultarTextArea(combo);

			// El formulario no se envia por submit. Se hace por Ajax
			$("#direccion-telematica-form").submit(function(){
				return false;
			});

			// Los botones del formulario generan invocaciones por Ajax. Todo el contenido recuperado se muestra en los tabs
			$("#saveDireccionTelematica,#cancelDireccionTelematica").click(function(){
				$.ajax({
		           type: 'POST',
		           cache: false,
		           url: $("#direccion-telematica-form").attr('action'),
		           data: $("#direccion-telematica-form").serialize()+ "&" + $(this).attr("name") + "=" + $(this).val(),
		           dataType: 'html',
		           success: function(data) {
	           				$('#ui-tabs-2').html(data);
	           				return false;
		           }
				});

				contentLinksInsideTab('#ui-tabs-2');
				return false;
			});

			contentLinksInsideTab('#ui-tabs-2');

			$("#direccionTelematicaStatusMessage").hide();

		});
	//-->
	</script>