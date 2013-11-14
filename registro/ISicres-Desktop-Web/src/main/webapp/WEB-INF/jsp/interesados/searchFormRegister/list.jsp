<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
	<head>
		<title><spring:message code="label.form.representante.search.title"/></title>
	 	<link rel="stylesheet" href="css/terceros/reset.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/terceros/form.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/terceros/table.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/terceros/estilos.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/terceros/terceros.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css" media="screen" />
	</head>
	<body>
		<div id="contenidoResultInteresados" onmouseover="this.style.cursor='cursor';">
			<div id="divCerrar" onmouseover="this.style.cursor='pointer';">
				<img id="imgCerrar" src="./css/terceros/fancybox/fancy_close.png" style="position: relative; top:-15px; left: 300px;" onClick="javascript:ocultarCapaInteresados();"/>
			</div>
			<c:choose>
				<c:when test="${not empty list}">
						<c:choose>
							<c:when test="${fn:length(list) == 1}">
								<h1><spring:message code="label.tab.loading" /></h1>
								<script type="text/javascript">
									//insertamos  en el formulario de registro el unico interesado encontrado
									addTerceroRegisterForm(${list[0].id});
								</script>
							</c:when>
							<c:otherwise>
								<div id="resultadoSearchIntFormReg" style="width: 100%; height: 90%; overflow: auto; position: relative;">
									<div id="contenidoResultInteresados" onmouseover="this.style.cursor='cursor';">
										<div id="resultSearchInt" style="width: 95%; margin-left:10px;">
											<display:table name="list" id="interesados" defaultsort="3" requestURI="interesado/seachTerceros.action" decorator="es.ieci.tecdoc.sicres.terceros.web.view.decorator.TercerosTableDecorator" pagesize="10">
												<!-- icono con el tipo de interesado (Fisico/Juridico) -->
												<display:column>
													<c:choose>
														<c:when test="${interesados['class'].simpleName eq 'TerceroValidadoFisicoVO'}">
																<img src="images/terceros/man.png" alt="<spring:message code='label.form.terceros.search.type.1'/>"/>
														</c:when>
														<c:otherwise>
																<img src="images/terceros/factory-icon.png" alt="<spring:message code='label.form.terceros.search.type.2'/>"/>
														</c:otherwise>
													</c:choose>
												</display:column>
												<display:column property="numeroDocumento" titleKey="label.list.terceros.search.numeroDocumento" sortable="true" sortName="numeroDocumento" />
												<!-- nombre completo -->
												<display:column property="descripcion" titleKey="label.list.terceros.search.descripcion" sortable="true" sortName="descripcion"/>
												<!-- direccion fisica principal -->
												<display:column property="direccionFisicaPrincipal" titleKey="label.list.terceros.search.direccionFisica" sortable="true" sortName="direccionFisicaPrincipal" />
												<!-- direccion telematica principal -->
												<display:column property="direccionTelematicaPrincipal" titleKey="label.list.terceros.search.direccionTelematica" sortable="true" sortName="direccionTelematicaPrincipal" />
												<!-- añadir interesado al formulario de registro -->
												<display:column class="selectTercero">
													<img src="images/terceros/add.png" onclick="javascript:addTerceroRegisterForm(${interesados.id});" onmouseover="this.style.cursor='pointer';"/>
												</display:column>

												<display:setProperty name="paging.banner.item_name">
													<spring:message code="label.list.terceros.search.item" />
												</display:setProperty>
												<display:setProperty name="paging.banner.items_name">
													<spring:message code="label.list.terceros.search.items" />
												</display:setProperty>
											</display:table>
										</div>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
				</c:when>
				<c:otherwise>
					<div id="notFoundInter" style="width: 95%;">
							<div class="onecol">
								<spring:message code="label.list.terceros.search.noResults" />&nbsp;<spring:message code="label.list.terceros.search.question.create" />
							</div>

							<div class="twocol" style="margin-top:20px;">
								<div class="column first">
									<a class="addPersonaFisica" href="javascript:newTerceroFisico();">
										<spring:message code="label.list.terceros.search.new.tercero.fisico" />
									</a>
								</div>
								<div class="column last">
									<a class="addPersonaJuridica" href="javascript:newTerceroJuridico();">
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

			// La navegacion de resultados se realiza por Ajax para mantener el foco en la ventana abierta
			$('.pagelinks > a, .sortable > a').click(function(e){
				e.preventDefault();

				$.ajax({
			           type: 'POST',
			           cache: false,
			           url: $(this).attr('href'),
			           dataType: 'html',
			           success:function(data){
							showCapaInteresados(data);
			           }
			    	});
				});

		});
	//-->
</script>
</body>
</html>
