<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/sicres.tld" prefix="sicres"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<sicres:statusMessage message="${flashScope.statusMessage}" messageId="statusMessage" />

	<form:form action="direccion/fisica/${direccionFisica.method}.action"
		commandName="direccionFisica" id="direccion-fisica-form" class="ajax">

		<sicres:formHeader titleKey="label.form.direccion.fisica.title"
							submitId="saveDireccionFisica"
							submitKey="label.form.button.aceptar"
							submitCssClass="aceptar"
							cancelId="cancelDireccionFisica"
							cancelKey="label.form.button.cancelar"
							cancelCssClass="cancelar" />

		<form:errors path="*" cssClass="form-errors" element="div" htmlEscape="on" />

		<div class="formulario">
			<spring:nestedPath path="content">
				<p>
					<spring:bind path="direccion">
						<label for="${status.expression}">
							<spring:message code="label.form.direccion.fisica.direccion" />
						</label>
						<textarea name="${status.expression}" class="medium ${sessionScope.texttransform}" tabindex="30" rows="3" cols="30">${status.value}</textarea>
					</spring:bind>
				</p>
				<p>
					<spring:bind path="codigoPostal">
						<label for="${status.expression}">
							<spring:message code="label.form.direccion.fisica.codigoPostal" />
						</label>
						<input type="text" name="${status.expression}" value="${status.value}" class="small ${sessionScope.texttransform}" tabindex="31" />
					</spring:bind>
				</p>
				<c:choose>
					<c:when test="${direccionFisica.method ne 'update'}">
						<p>
							<form:label path="pais.codigo">
								<spring:message code="label.form.direccion.fisica.pais" />
							</form:label>

							<form:select path="pais.codigo" cssClass="medium" tabindex="32">
								<option value="">
									<spring:message code="label.form.direccion.fisica.pais.choose" />
								</option>
								<c:forEach items="${paises}" var="pais">
									<c:choose>
										<c:when test="${pais.codigo eq 'ES'}">
											<form:option value="${pais.codigo}" selected="selected" htmlEscape="on">${pais.nombre}</form:option>
										</c:when>
										<c:otherwise>
											<form:option value="${pais.codigo}" htmlEscape="on">${pais.nombre}</form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</p>
						<p>
							<form:label path="provincia.nombre">
								<spring:message code="label.form.direccion.fisica.provincia" />
							</form:label>
							<form:select path="provincia.nombre" cssClass="medium" tabindex="33">
								<option value="">
									<spring:message code="label.form.direccion.fisica.provincia.choose" />
								</option>
								<c:forEach items="${provincias}" var="provincia">
									<c:choose>
										<c:when test="${provincia.nombre eq defaultProvincia}">
											<option value="${provincia.nombre}" selected="selected">${provincia.nombre}</option>
										</c:when>
										<c:otherwise>
											<form:option value="${provincia.nombre}" htmlEscape="on">${provincia.nombre}</form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</p>
						<p>
							<form:label path="ciudad.nombre">
								<spring:message code="label.form.direccion.fisica.ciudad" />
							</form:label>
							<form:select path="ciudad.nombre" cssClass="medium" tabindex="34">
								<option value="">
									<spring:message code="label.form.direccion.fisica.ciudad.choose" />
								</option>
								<c:forEach items="${ciudades}" var="ciudad">
									<option value="${ciudad.nombre}" htmlEscape="on">${ciudad.nombre}</option>
								</c:forEach>
							</form:select>
						</p>
					</c:when>
					<c:otherwise>
						<p>
							<form:label path="pais.codigo">
								<spring:message code="label.form.direccion.fisica.pais" />
							</form:label>

							<form:select path="pais.codigo" cssClass="medium" tabindex="32">
								<option value="">
									<spring:message code="label.form.direccion.fisica.pais.choose" />
								</option>
								<form:options items="${paises}" itemValue="codigo" itemLabel="nombre"/>
							</form:select>
						</p>
						<p>
							<form:label path="provincia.nombre">
								<spring:message code="label.form.direccion.fisica.provincia" />
							</form:label>
							<form:select path="provincia.nombre" cssClass="medium" tabindex="33">
								<option value="">
									<spring:message code="label.form.direccion.fisica.provincia.choose" />
								</option>
								<form:options items="${provincias}" itemValue="nombre" itemLabel="nombre"/>
							</form:select>
						</p>
						<p>
							<form:label path="ciudad.nombre">
								<spring:message code="label.form.direccion.fisica.ciudad" />
							</form:label>
							<form:select path="ciudad.nombre" cssClass="medium" tabindex="34">
								<option value="">
									<spring:message code="label.form.direccion.fisica.ciudad.choose" />
								</option>
								<form:options items="${ciudades}" itemValue="nombre" itemLabel="nombre"/>
							</form:select>
						</p>
					</c:otherwise>
				</c:choose>
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
		// Funcion que recupera por Ajax las ciudades asociadasa a la provincia que recibe como parametro
		function loadCiudades(provincia){
			dwrService.getCiudades(provincia,
					{
						callback:function(data){
							dwr.util.removeAllOptions("content.ciudad.nombre");
							dwr.util.addOptions("content.ciudad.nombre",[{key:'""', value:'<spring:message code="label.form.direccion.fisica.ciudad.choose" />'}], "key", "value");
							dwr.util.addOptions("content.ciudad.nombre",data,"nombre","nombre");
						}
					}
			);
		};

		jQuery(document).ready(function($) {

			//Cuando carga la pagina validamos si el pais es distinto a España, se habilitan los campos input en vez de combos
			if ($("#content\\.pais\\.codigo").val() != 'ES'){
				// Una valor de pais diferente a España hace que los controles provincia y ciudad sean texto libre
				$("#content\\.provincia\\.nombre").replaceWith('<input type="text" name="content.provincia.nombre" id="content.provincia.nombre" class="medium ${sessionScope.texttransform}" value="${direccionFisica.content.provincia.nombre}" tabindex="33" />');
				$("#content\\.ciudad\\.nombre").replaceWith('<input type="text" name="content.ciudad.nombre" id="content.ciudad.nombre" class="medium ${sessionScope.texttransform}" value="${direccionFisica.content.ciudad.nombre}"  tabindex="34" />');
			}else{
				//comprobamos si la provincia esta rellena sino inicializamos el campo Ciudades
				var provincia = $("#content\\.provincia\\.nombre").val();
				if(provincia == ''){
					//Cargamos las ciudades para una provincia vacia
					loadCiudades(provincia)
				}
			}



			// El formulario no se envia con el submit. Se hace por Ajax
			$("#direccion-fisica-form").submit(function(){
				return false;
			});

			// Los botones del formulario generan invocaciones por Ajax. Todo el contenido recuperado se muestra en los tabs
			$("#saveDireccionFisica,#cancelDireccionFisica").click(function(){
				$.ajax({
		           type: 'POST',
		           cache: false,
		           url: $("#direccion-fisica-form").attr('action'),
		           data: $("#direccion-fisica-form").serialize()+ "&" + $(this).attr("name") + "=" + $(this).val(),
		           dataType: 'html',
		           success: function(data) {
	           				$('#ui-tabs-1').html(data);
	           				return false;
		           }
				});

				contentLinksInsideTab('#ui-tabs-1');
				return false;
			});

			contentLinksInsideTab('#ui-tabs-1');

			$("#direccionFisicaStatusMessage").hide();

			// Cambios en el valor del pais
			$("#content\\.pais\\.codigo").change(function(){
				// Si se selecciona España se cargan provincias y ciudades. Si existe una provincia por defecto configurada sera esa la mostrada
				if ($("#content\\.pais\\.codigo").val() == 'ES'){
					$("#content\\.provincia\\.nombre").replaceWith('<select class="medium" name="content.provincia.nombre" id="content.provincia.nombre" tabindex="33"><option value="">'+'<spring:message code="label.form.direccion.fisica.provincia.choose" />'+'</option></select>');
					// al cambiar la provincia se actualizan las ciudades asociadas
					$('#content\\.provincia\\.nombre').bind('change',function(){
						$("#content\\.ciudad\\.nombre").replaceWith('<select class="medium" name="content.ciudad.nombre" id="content.ciudad.nombre" tabindex="34"><option value="">'+'<spring:message code="label.form.direccion.fisica.ciudad.choose" />'+'</option></select>');
						loadCiudades($(this).val())
					});

					$("#content\\.ciudad\\.nombre").replaceWith('<select class="medium" name="content.ciudad.nombre" id="content.ciudad.nombre" tabindex="34"><option value="">'+'<spring:message code="label.form.direccion.fisica.ciudad.choose" />'+'</option></select>');
					// recuperamos las provincias
					dwrService.getProvincias(
						{
							callback:function(data){
								dwr.util.addOptions("content.provincia.nombre", data, "nombre", "nombre");
							}
						}
					);
					// recuperamos la provincia por defecto
					dwrService.getProvinciaPorDefecto(
						{
							callback:function(data){
								dwr.util.setValue("content.provincia.nombre", data);
								loadCiudades(data);
							}
						}
					);
				}else{
					// Una valor de pais diferente a España hace que los controles provincia y ciudad sean texto libre
					$("#content\\.provincia\\.nombre").replaceWith('<input type="text" name="content.provincia.nombre" id="content.provincia.nombre" class="medium ${sessionScope.texttransform}" tabindex="33" />');
					$("#content\\.ciudad\\.nombre").replaceWith('<input type="text" name="content.ciudad.nombre" id="content.ciudad.nombre" class="medium ${sessionScope.texttransform}" tabindex="34" />');
				}
			});

			// Cuando se cambia la provincia se actualizan las ciudades asociadas
			$('#content\\.provincia\\.nombre').bind('change',function(){
				//le asignamos los valores correspondientes
				loadCiudades($(this).val());
			});

		});
	//-->
	</script>