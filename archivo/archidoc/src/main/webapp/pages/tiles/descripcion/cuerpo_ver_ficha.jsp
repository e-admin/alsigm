<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<c:set var="id" value="${param['id']}"/>
<c:set var="xml" value="${requestScope[appConstants.descripcion.FICHA_XML_KEY]}"/>
<c:set var="xsl" value="${requestScope[appConstants.descripcion.FICHA_XSL_KEY]}"/>
<c:set var="MODO_VISUALIZACION" value="${sessionScope[appConstants.descripcion.MODO_VISUALIZACION_KEY]}"/>
<c:set var="TIPO_FICHA" value="${requestScope[appConstants.descripcion.TIPO_FICHA_KEY]}"/>
<c:set var="MOSTRAR_BOTON_AUTOMATICOS" value="${requestScope[appConstants.descripcion.MOSTRAR_BOTON_AUTOMATICOS_KEY]}"/>
<c:set var="objetoDescrito" value="${sessionScope[appConstants.descripcion.OBJETO_DESCRITO_KEY]}"/>
<c:if test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE}">
	<c:set var="vRelacion" value="${sessionScope[appConstants.transferencias.RELACION_KEY]}"/>
	<c:set var="unidadDocumental" value="${sessionScope[appConstants.transferencias.UNIDAD_DOCUMENTAL]}"/>
	<c:set var="prevUnidadDocumental" value="${unidadDocumental.prevUnidadDocumental}"/>
	<c:set var="nextUnidadDocumental" value="${unidadDocumental.nextUnidadDocumental}"/>
</c:if>
<c:if test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCFS}">
	<c:set var="divisionFS" value="${sessionScope[appConstants.fondos.DIVISION_FRACCION_SERIE]}"/>
	<c:set var="udocEnDivisionFS" value="${sessionScope[appConstants.fondos.UNIDAD_DOCUMENTAL_EN_FS]}"/>
	<c:set var="prevUnidadDocumental" value="${udocEnDivisionFS.prevUdocEnDivisionFs}"/>
	<c:set var="nextUnidadDocumental" value="${udocEnDivisionFS.nextUdocEnDivisionFs}"/>
</c:if>

<c:choose>
	<c:when test="${objetoDescrito.class.name == 'descripcion.vos.DescriptorVO'}">
		<c:set var="permisoEspecifico" value="${appConstants.permissions.EDICION_DESCRIPTOR}"/>
	</c:when>
	<c:otherwise>
		<c:set var="permisoEspecifico" value="${appConstants.permissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION}"/>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE || TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCFS}">
		<c:set var="permisoAction" value="${appConstants.descripcionActions.USO_FICHA_ALTA_TRANSFERENCIA_ACTION}"/>
	</c:when>
	<c:otherwise>
		<c:set var="permisoAction" value="${appConstants.descripcionActions.EDITAR_FICHA_ELEMENTO_ACTION}"/>
	</c:otherwise>
</c:choose>

<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/ficha.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" type="text/JavaScript">
	function saveAndDuplicate()
	{
		document.forms["fichaForm"].method.value = "save";
		var mInfo =	document.getElementById("mantenerInformacion");
		if (mInfo != null) mInfo.value = '1';
		document.forms["fichaForm"].submit();
	}
	function saveAndNew()
	{
		document.forms["fichaForm"].method.value = "save";
		var mInfo =	document.getElementById("mantenerInformacion");
		if (mInfo != null) mInfo.value = '0';
		document.forms["fichaForm"].submit();
	}
	function save()
	{
		document.forms["fichaForm"].method.value = "save";
		document.forms["fichaForm"].submit();
	}
	function edit()
	{
		document.forms["fichaForm"].method.value = "edit";
		document.forms["fichaForm"].submit();
	}
	function close()
	{
		document.forms["fichaForm"].method.value = "goBack";
		<c:if test="${sessionScope['cerrarAPantallaCompleta'] == '1'}">
			document.forms["fichaForm"].target = "_top";
		</c:if>
		document.forms["fichaForm"].submit();
	}
	function cancel()
	{
		document.forms["fichaForm"].method.value = "cancel";
		document.forms["fichaForm"].submit();
	}
	function update()
	{
		if (confirm('<bean:message key="archigest.archivo.descripcion.actualizar.confirm"/>'))
		{
			if (window.top.showWorkingDiv) {
				var title = '<bean:message key="archigest.archivo.descripcion.actualizar.caption"/>';
				var message = '<bean:message key="archigest.archivo.descripcion.actualizar.message"/>';
				var message2 = '<bean:message key="archigest.archivo.msgOperacionLenta"/>';
				window.top.showWorkingDiv(title, message, message2);
			}
			document.forms["fichaForm"].method.value = "update";
			document.forms["fichaForm"].submit();
		}
	}
	function cambiarFormato()
	{
		<c:choose>
			<c:when test="${MODO_VISUALIZACION == appConstants.descripcion.MODO_VISUALIZACION_EDICION}">
				if (confirm('<bean:message key="descripcion.msg.edicion.cambio.formato.ficha.preferente"/>')) {
					document.forms["fichaForm"].method.value = "formCambiarFormatoPreferente";
					document.forms["fichaForm"].submit();
				}
			</c:when>
			<c:otherwise>
				document.forms["fichaForm"].method.value = "formCambiarFormatoPreferente";
				document.forms["fichaForm"].submit();
			</c:otherwise>
		</c:choose>
	}

	function exportar(){
		document.forms["fichaForm"].method.value = "exportar";
		document.forms["fichaForm"].submit();
	}
</script>

<div id="contenedor_ficha">

  <form name="fichaForm" method="post"
  	<c:choose>
  	   	<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE}">
      		action="<c:url value="/action/isadgudocre"/>"
    	</c:when>
    	<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCFS}">
      		action="<c:url value="/action/isadgudocfs"/>"
    	</c:when>
	    <c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_ISADG}">
	      action="<c:url value="/action/isadg"/>"
    	</c:when>
	    <c:otherwise>
    	  action="<c:url value="/action/isaar"/>"
    	</c:otherwise>
    </c:choose>
  >
	<c:set var="fichaAccesible" value="false"/>
	<security:permissions action="${permisoAction}">
		<c:choose>
	  		<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE || TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCFS}">
		  	  	<c:set var="fichaAccesible" value="true"/>
	  	  	</c:when>
	  	  	<c:otherwise>
		  	  	<security:access object="${objetoDescrito}" permission="${permisoEspecifico}">
			  	  	<c:set var="fichaAccesible" value="true"/>
	  	  		</security:access>
		  	</c:otherwise>
	 	  </c:choose>
	</security:permissions>

  <input type="hidden" name="id" value="<c:out value="${id}"/>"/>
  <input type="hidden" id="contextPath" name="contextPath" value="<c:out value="${fichaForm.contextPath}"/>"/>
  <input type="hidden" id="idficha" name="idficha" value="<c:out value="${fichaForm.idFicha}"/>"/>
  <input type="hidden" id="idformato" name="idformato" value="<c:out value="${fichaForm.idFormato}"/>"/>
  <input type="hidden" id="tipoacceso" name="tipoacceso" value="<c:out value="${fichaForm.tipoAcceso}"/>"/>
  <input type="hidden" id="mantenerInformacion" name="mantenerInformacion" value="-1"/>
  <input type="hidden" id="method" name="method"/>

  <c:set var="idFormato" value="${fichaForm.idFormato}"/>

 <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
			  <c:choose>
			  	<c:when test="${MODO_VISUALIZACION == appConstants.descripcion.MODO_VISUALIZACION_EDICION}">
			  		<bean:message key="archigest.archivo.descripcion.editarFicha"/>
			    </c:when>
			  	<c:otherwise>
			  		<bean:message key="archigest.archivo.descripcion.verFicha"/>
			    </c:otherwise>
			  </c:choose>
            </td>
           <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                	<c:choose>
		                <c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE}">
			                <c:if test="${vRelacion.validada && !empty unidadDocumental.udocEnCuadroClasificacion && !empty vRelacion.nivelDocumental && unidadDocumental.udocEnCuadroClasificacion.subtipo == vRelacion.nivelDocumental.subtipo}">
								<security:permissions action="${appConstants.fondosActions.CONSULTA_ACTION}">
								<TD>
									<c:url var="showCFURL" value="/action/gestionUdocsCF">
										<c:param name="method" value="verEnFondos" />
										<c:param name="unidadDocumental" value="${unidadDocumental.udocEnCuadroClasificacion.id}" />
									</c:url>
									<a class="etiquetaAzul12Bold" href="<c:out value="${showCFURL}" escapeXml="false" />" target="_self">
										<html:img page="/pages/images/tree.gif" altKey="archigest.archivo.cf.verEnFondos" titleKey="archigest.archivo.cf.verEnFondos" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cf.verEnFondos"/>
									</a>
								</TD>
								 <td width="10">&nbsp;</td>
								</security:permissions>
							</c:if>
						</c:when>
						<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCFS}">
							<c:if test="${divisionFS.validada && !empty udocEnDivisionFS.udocEnCuadroClasificacion && !empty divisionFS.nivelDocumental && udocEnDivisionFS.udocEnCuadroClasificacion.subtipo == divisionFS.nivelDocumental.subtipo}">
								<security:permissions action="${appConstants.fondosActions.CONSULTA_ACTION}">
								<TD>
									<c:url var="showCFURL" value="/action/gestionUdocsCF">
										<c:param name="method" value="verEnFondos" />
										<c:param name="unidadDocumental" value="${udocEnDivisionFS.udocEnCuadroClasificacion.id}" />
									</c:url>
									<a class="etiquetaAzul12Bold" href="<c:out value="${showCFURL}" escapeXml="false" />" target="_self">
										<html:img page="/pages/images/tree.gif" altKey="archigest.archivo.cf.verEnFondos" titleKey="archigest.archivo.cf.verEnFondos" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.cf.verEnFondos"/>
									</a>
								</TD>
								 <td width="10">&nbsp;</td>
								</security:permissions>
							</c:if>
						</c:when>
					</c:choose>
					<c:if test="${unidadDocumental!=null}">
						<c:url var="anteriorUdocUrl" value="/action/gestionUdocsRelacion">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${prevUnidadDocumental.id}" />
							<c:param name="numOrden" value="${prevUnidadDocumental.orden}"  />
							<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
						</c:url>
						<c:url var="siguienteUdocUrl" value="/action/gestionUdocsRelacion">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${nextUnidadDocumental.id}" />
							<c:param name="numOrden" value="${nextUnidadDocumental.orden}"  />
							<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
						</c:url>
						<c:if test="${(prevUnidadDocumental!=null)||(nextUnidadDocumental!=null)}">
							<c:if test="${prevUnidadDocumental!=null}">

								<td>
									<a class="etiquetaAzul12Bold" href="<c:out value="${anteriorUdocUrl}" escapeXml="false" />" target="_self">
										<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.prev.unidad.documental" titleKey="archigest.archivo.prev.unidad.documental" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.prev.unidad.documental"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
							</c:if>
							<c:if test="${nextUnidadDocumental!=null}">
								<td>
									<a class="etiquetaAzul12Bold" href="<c:out value="${siguienteUdocUrl}" escapeXml="false" />" target="_self">
										<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.next.unidad.documental" titleKey="archigest.archivo.next.unidad.documental" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.next.unidad.documental"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
							</c:if>
						</c:if>
					</c:if>
					<c:if test="${udocEnDivisionFS!=null}">
						<c:url var="anteriorUdocUrl" value="/action/gestionUDocsEnFS">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${prevUnidadDocumental.idUDoc}" />
							<c:param name="numOrden" value="${prevUnidadDocumental.orden}"  />
							<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
						</c:url>
						<c:url var="siguienteUdocUrl" value="/action/gestionUDocsEnFS">
							<c:param name="method" value="infoUnidadDocumental" />
							<c:param name="udocID" value="${nextUnidadDocumental.idUDoc}" />
							<c:param name="numOrden" value="${nextUnidadDocumental.orden}"  />
							<c:param name="navegacion" value="${appConstants.common.TRUE_STRING}"  />
						</c:url>
						<c:if test="${(prevUnidadDocumental!=null)||(nextUnidadDocumental!=null)}">
							<c:if test="${prevUnidadDocumental!=null}">

								<td>
									<a class="etiquetaAzul12Bold" href="<c:out value="${anteriorUdocUrl}" escapeXml="false" />" target="_self">
										<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.prev.unidad.documental" titleKey="archigest.archivo.prev.unidad.documental" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.prev.unidad.documental"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
							</c:if>
							<c:if test="${nextUnidadDocumental!=null}">
								<td>
									<a class="etiquetaAzul12Bold" href="<c:out value="${siguienteUdocUrl}" escapeXml="false" />" target="_self">
										<html:img page="/pages/images/Next.gif" altKey="archigest.archivo.next.unidad.documental" titleKey="archigest.archivo.next.unidad.documental" styleClass="imgTextMiddle" />
										&nbsp;<bean:message key="archigest.archivo.next.unidad.documental"/>
									</a>
								</td>
								<td width="10">&nbsp;</td>
							</c:if>
						</c:if>
					</c:if>
					<td>
						<a class="etiquetaAzul12Bold" href="javascript:exportar();">
						<html:img page="/pages/images/displaytag/ico_file_pdf.png"
					        altKey="archigest.archivo.exportar"
					        titleKey="archigest.archivo.exportar"
					        styleClass="imgTextMiddle"/>
						&nbsp;<bean:message key="archigest.archivo.exportar"/></a>
					</td>
					<td width="10">&nbsp;</td>
	               <c:if test="${!empty xml}">
						<td><a class="etiquetaAzul12Bold"
			                         href="javascript:cambiarFormato()"
			                      ><html:img page="/pages/images/formato.gif"
			                              altKey="descripcion.cambio.formato.ficha.preferente"
			                              titleKey="descripcion.cambio.formato.ficha.preferente"
			                              styleClass="imgTextMiddle" />&nbsp;
			                      <bean:message key="descripcion.formato"/></a></td>
	                  	<td width="10">&nbsp;</td>
					</c:if>
				  	<c:choose>
				  	<c:when test="${MODO_VISUALIZACION == appConstants.descripcion.MODO_VISUALIZACION_SOLO_LECTURA}">
				  	  <c:if test="${TIPO_FICHA != appConstants.descripcion.TIPO_FICHA_UDOCRE && TIPO_FICHA != appConstants.descripcion.TIPO_FICHA_UDOCFS}">
					  	  <c:if test="${fichaAccesible == 'true'}">
						  	  <c:if test="${MOSTRAR_BOTON_AUTOMATICOS}">
			                  <td><a class="etiquetaAzul12Bold"
			                         href="javascript:update()"
			                      ><html:img page="/pages/images/actualizar.gif"
			                              altKey="archigest.archivo.actualizar"
			                              titleKey="archigest.archivo.actualizar"
			                              styleClass="imgTextMiddle" />&nbsp;
			                      <bean:message key="archigest.archivo.actualizar"/></a></td>
			                  <td width="10">&nbsp;</td>
			                  </c:if>
						  	  <c:if test="${!empty xml}">
			                  <td><a class="etiquetaAzul12Bold"
			                         href="javascript:edit()"
			                      ><html:img page="/pages/images/editDoc.gif"
			                              altKey="archigest.archivo.editar"
			                              titleKey="archigest.archivo.editar"
			                              styleClass="imgTextMiddle" />&nbsp;
			                      <bean:message key="archigest.archivo.editar"/></a></td>
			                  <td width="10">&nbsp;</td>
			                  </c:if>
			              </c:if>
			          </c:if>
	                  <td><a class="etiquetaAzul12Bold"
	                         href="javascript:close()"
	                      ><html:img page="/pages/images/close.gif"
	                              altKey="archigest.archivo.cerrar"
	                              titleKey="archigest.archivo.cerrar"
	                              styleClass="imgTextMiddle" />&nbsp;
	                      <bean:message key="archigest.archivo.cerrar"/></a></td>
				    </c:when>

				  	<c:when test="${MODO_VISUALIZACION == appConstants.descripcion.MODO_VISUALIZACION_EDICION}">
				  		<c:if test="${!empty xml}">
				  			<c:if test="${fichaAccesible == 'true'}">
				  				<c:choose>
									<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCRE}">
										<c:if test="${vRelacion.abierta || vRelacion.rechazada}">
										  	<TD style="vertical-align:top">
												<a class="etiquetaAzul12Bold" href="javascript:saveAndDuplicate();">
														<html:img page="/pages/images/duplicarDoc.gif" altKey="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar"
																  titleKey="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar" styleClass="imgTextMiddle" />
														&nbsp;<bean:message key="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar"/>
												</a>
											</TD>
											<TD width="10px">&nbsp;</TD>
											<TD>
												<a class="etiquetaAzul12Bold" href="javascript:saveAndNew();">
													<html:img titleKey="archigest.archivo.guardarYNueva" altKey="archigest.archivo.guardarYNueva" page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"/>
													<bean:message key="archigest.archivo.guardarYNueva"/>
												</a>
											</TD>
											<TD width="10px">&nbsp;</TD>
								  		</c:if>
								  		<c:if test="${vRelacion.abierta || vRelacion.rechazada || vRelacion.conErroresCotejo}">
									   		<td><a class="etiquetaAzul12Bold" href="javascript:save()">
					    	            	<html:img page="/pages/images/save.gif"
					    		                  altKey="archigest.archivo.guardar"
						        	              titleKey="archigest.archivo.guardar"
						            	          styleClass="imgTextMiddle" />&nbsp;
						                     <bean:message key="archigest.archivo.guardar"/></a></td>
						                     <td width="10">&nbsp;</td>
						            	</c:if>
									</c:when>
									<c:when test="${TIPO_FICHA == appConstants.descripcion.TIPO_FICHA_UDOCFS}">
										<c:if test="${divisionFS.abierta}">
										  	<TD style="vertical-align:top">
												<a class="etiquetaAzul12Bold" href="javascript:saveAndDuplicate();">
														<html:img page="/pages/images/duplicarDoc.gif" altKey="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar"
																  titleKey="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar" styleClass="imgTextMiddle" />
														&nbsp;<bean:message key="archigest.archivo.transferencias.udocRelacion.guardarYDuplicar"/>
												</a>
											</TD>
											<TD width="10px">&nbsp;</TD>
											<TD>
												<a class="etiquetaAzul12Bold" href="javascript:saveAndNew();">
													<html:img titleKey="archigest.archivo.guardarYNueva" altKey="archigest.archivo.guardarYNueva" page="/pages/images/newDoc.gif" styleClass="imgTextMiddle"/>
													<bean:message key="archigest.archivo.guardarYNueva"/>
												</a>
											</TD>
											<TD width="10px">&nbsp;</TD>
								  		</c:if>
								  		<c:if test="${divisionFS.abierta}">
									   		<td><a class="etiquetaAzul12Bold" href="javascript:save()">
					    	            	<html:img page="/pages/images/save.gif"
					    		                  altKey="archigest.archivo.guardar"
						        	              titleKey="archigest.archivo.guardar"
						            	          styleClass="imgTextMiddle" />&nbsp;
						                     <bean:message key="archigest.archivo.guardar"/></a></td>
						                     <td width="10">&nbsp;</td>
						            	</c:if>
									</c:when>
									<c:otherwise>
										<td><a class="etiquetaAzul12Bold" href="javascript:save()">
				    	            	<html:img page="/pages/images/save.gif"
				    		                  altKey="archigest.archivo.guardar"
					        	              titleKey="archigest.archivo.guardar"
					            	          styleClass="imgTextMiddle" />&nbsp;
					                     <bean:message key="archigest.archivo.guardar"/></a></td>
					                     <td width="10">&nbsp;</td>
									</c:otherwise>
								</c:choose>
				  			</c:if>
	                  </c:if>
	                  <td><a class="etiquetaAzul12Bold"
	                         href="javascript:cancel()"
	                      ><html:img page="/pages/images/close.gif"
	                              altKey="archigest.archivo.cerrar"
	                              titleKey="archigest.archivo.cerrar"
	                              styleClass="imgTextMiddle" />&nbsp;
	                      <bean:message key="archigest.archivo.cerrar"/></a></td>
				    </c:when>
			  </c:choose>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </span></h1>
    <div class="cuerpo_drcha">
      <div class="cuerpo_izda">
    	 <div id="barra_errores"><archivo:errors /></div>
         <div class="separador1">&nbsp;</div>
         <pa:transform xmlParamName="xml" xslParamName="xsl"/>
      </div><%--cuerpo_izda --%>
    </div><%--cuerpo_drcha --%>
    <h2><span>&nbsp;</span></h2>
  </div><%--ficha --%>
  </form>
</div><%--contenedor_ficha --%>
