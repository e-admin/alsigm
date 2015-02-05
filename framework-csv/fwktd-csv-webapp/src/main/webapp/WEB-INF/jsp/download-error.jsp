<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="content">

	<form:form action="action/documento/form" commandName="documento" cssClass="block">

		<input type="hidden" name="csv" value="${csv}" />

		<div class="twocolrightb cabecera">
			<div class="column first">
				<h4><spring:message code="label.download.error.title" /></h4>
			</div>
			<div class="column last textright">
				<p class="botones">
					<input type="submit" id="back" name="back"
						class="volver" value='<spring:message code="label.form.button.back" />'/>
				</p>
			</div>
		</div>

		<div id="errorMessage">
			<p>${mensajeError}</p>
		</div>

	</form:form>

</div>
