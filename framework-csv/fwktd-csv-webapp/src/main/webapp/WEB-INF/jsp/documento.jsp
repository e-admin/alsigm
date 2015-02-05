<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="content">

	<form:form action="action/documento/search" commandName="documento" cssClass="block">

		<div class="onecol">
				<p class="enlaces">
					<a href='<spring:url value="action/documento/form"/>' title='<spring:message code="label.form.button.home"/>' class="inicio">
						<span>
							<spring:message code="label.form.button.home"/>
						</span>
					</a>
				</p>
		</div>

		<div class="twocolrightb cabecera">
			<div class="column first">
				<h4><spring:message code="label.form.csv.title" /></h4>
			</div>
			<div class="column last textright">
				<p class="botones">
					<input type="submit" id="search" name="search"
						class="buscar" value='<spring:message code="label.form.button.search" />'/>
				</p>
			</div>
		</div>

		<form:errors path="*" cssClass="form-errors" htmlEscape="on" />

		<c:if test="${!empty mensajeError}">
		<div id="errorMessage">
			<p>${mensajeError}</p>
		</div>
		</c:if>

		<div class="formulario">

			<p>
				<label for="csv">
					<spring:message code="label.form.csv.csv" />
				</label>
				<input type="text" id="csv" name="csv" class="small" value="${csv}" />
			</p>

			<c:if test="${configProperties['fwktd-csv-webapp.useCaptcha']}">
			<p>
				<label for="captcha">
					<spring:message code="label.form.csv.captcha" />
				</label>
				<img id="captcha" src='<spring:url value="action/captcha" />' />
				<br/>
				<label for="captcha_answer">&nbsp;</label>
				<input type="text" id="captcha_answer" name="captcha_answer" class="small" />
			</p>
			</c:if>
		</div>
	</form:form>

	<c:if test="${!empty infoDocumento}">
	<div class="infoDoc">
			<div class="twocolrightb cabecera">
			<div class="column first">
				<h4><spring:message code="label.document.title" /></h4>
			</div>
			<div class="column last textright">
					<p class="botones">
					<c:if test="${infoDocumento.disponible}">
						<a href='<spring:url value="action/documento/download" />' title='<spring:message code="label.form.button.download"/>' class="descargar">
							<span>
								<spring:message code="label.form.button.download"/>
							</span>
						</a>
					</c:if>
				</p>
			</div>
		</div>

		<c:if test="${!infoDocumento.disponible}">
			<div id="errorMessage">
				<p><spring:message code="error.csv.download.notAvailable" /></p>
			</div>
		</c:if>

		<dl class="form">

			<dt>
				<spring:message code="label.document.description" />
			</dt>
			<dd>${infoDocumento.descripcion}</dd>

			<dt>
				<spring:message code="label.document.date" />
			</dt>
			<dd><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${infoDocumento.fechaCreacion}" /></dd>

			<c:if test="${!empty infoDocumento.fechaCaducidad}">
			<dt>
				<spring:message code="label.document.expiration_date" />
			</dt>
			<dd><fmt:formatDate pattern="yyyy-MM-dd" value="${infoDocumento.fechaCaducidad}" /></dd>
			</c:if>
		</dl>
	</div>
	</c:if>

</div>

<script type="text/javascript">
//<!--
	$(document).ready(function() {
		$("#csv").focus();
	});
//-->
</script>
