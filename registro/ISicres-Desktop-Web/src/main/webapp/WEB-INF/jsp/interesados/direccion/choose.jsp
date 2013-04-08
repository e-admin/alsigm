<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>

<form:form action="interesado/changeDireccion.action" id="change-direccion-interesado-form">

	<sicres:formHeader titleKey="label.form.interesados.choose.direccion.title"
							submitId="changeDireccionInteresado"
							submitKey="label.form.button.aceptar"
							submitCssClass="aceptar"
							cancelId="cancelChangeDireccionInteresado"
							cancelKey="label.form.button.cerrar"
							cancelCssClass="cancelar" />

	<display:table name="direccionesTercero" id="direccionesTercero" >
		<display:column>
			<spring:bind path="id">
				<form:radiobutton path="${status.expression}" value="${direccionesTercero.id}"/>
			</spring:bind>
		</display:column>
		<display:column titleKey="label.list.interesados.interesado.direccion" value="${direccionesTercero}">
		</display:column>
		<display:column>
			<c:choose>
				<c:when test="${direccionesTercero.tipo.name eq 'FISICA'}">
					<img src="images/terceros/home.png" alt="<spring:message code='label.form.direccion.fisica.title'/>"/>
				</c:when>
				<c:otherwise>
					<img src="images/terceros/phone.png" alt="<spring:message code='label.form.direccion.telematica.title'/>"/>
				</c:otherwise>
			</c:choose>
		</display:column>
	</display:table>

	<spring:bind path="tercero.id">
		<input type="hidden" name="${status.expression}" value="${requestScope['tercero.id']}" />
	</spring:bind>

</form:form>

<script type="text/javascript">
<!--
	jQuery(document).ready(function($){

		// El formulario no se envia por submit. Se hace por Ajax
		$('#change-direccion-interesado-form').submit(function(e){
			return false;
		});

		// El cambio de direccion se hace invocando por Ajax al servidor. El resultado se muestra en el div con id interesadosRegistro mediante fancybox
		$('#changeDireccionInteresado').click(function(e){
			e.preventDefault();

			$.fancybox.showActivity();

            $.ajax({
		           type: 'POST',
		           cache: false,
		           url: $("#change-direccion-interesado-form").attr('action'),
		           data: $("#change-direccion-interesado-form").serialize(),
		           dataType: 'html',
		           success: function(data) {
		           		$('#interesadosRegistro').html(data);
		           		$.fancybox.close();
		           }
		       });
		});

		// Se cierra la ventana
		$('#cancelChangeDireccionInteresado').click(function(e){
			e.preventDefault();

			$.fancybox.close();
		});

	});
//-->
</script>