<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:set var="elementos" value="${sessionScope[appConstants.descripcion.ELEMENTOS_KEY]}"/>

<c:set var="beanBusqueda" value="${sessionScope[appConstants.fondos.CFG_BUSQUEDA_KEY]}"/>

<c:set var="ref" value="${param['ref']}"/>
<bean:struts id="actionMapping" mapping="/elementos" />

<script language="JavaScript1.2" type="text/JavaScript">
	function select(id,codigo)
	{
		var ref = document.forms["<c:out value="${actionMapping.name}" />"].elements["ref"].value;
		if (opener.document.getElementById(ref))
		{
			opener.document.getElementById(ref + "_idref").value = id;
			opener.document.getElementById(ref + "_tiporef").value = 2;
			opener.document.getElementById(ref).value = codigo;
		}

		window.close();
	}
</script>

<div id="contenedor_ficha">
  <html:form action="/elementos">
  <html:hidden property="ref" styleId="ref"/>

  <div class="ficha">
    <h1><span>
      <div class="w100">
        <table class="w98" cellpadding="0" cellspacing="0">
          <tr>
            <td class="etiquetaAzul12Bold" height="25px">
		  	  <bean:message key="archigest.archivo.descripcion.listado"/>
            </td>
            <td align="right">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td><a class="etiquetaAzul12Bold"
                         href="javascript:window.close()"
                      ><html:img page="/pages/images/close.gif"
                              altKey="archigest.archivo.cerrar"
                              titleKey="archigest.archivo.cerrar"
                              styleClass="imgTextMiddle" />&nbsp;<bean:message key="archigest.archivo.cerrar"/></a></td>
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

			<div class="bloque">
				<display:table name="pageScope.elementos"
					style="width:99%;margin-left:auto;margin-right:auto"
					id="elemento"
					pagesize="0"
					sort="external"
					defaultsort="0"
					requestURI="../../action/buscarElementos?method=buscar"
					export="true"
					decorator="common.view.VisitedRowDecorator">

					<display:setProperty name="basic.msg.empty_list">
						<bean:message key="archigest.archivo.cf.busqueda.listado.vacio"/>
					</display:setProperty>
					<display:column title="">
					  <a href="javascript:select('<bean:write name="elemento" property="id"/>', '<c:out value="${elemento.codReferencia}"/>')"
					    ><html:img page="/pages/images/aceptar.gif"
					    altKey="archigest.archivo.seleccionar"
					    titleKey="archigest.archivo.seleccionar"
					    styleClass="imgTextMiddle"/></a>
					</display:column>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO] != null}">
						<c:set var="campoCodigo" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO]}"/>
						<display:column titleKey="archigest.archivo.cf.codigo" property="codigo" media="html"/>
						<display:column titleKey="archigest.archivo.cf.codigo" property="codigo" media="csv excel xml pdf"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA] != null}">
						<c:set var="campoCodReferencia" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_CODIGO_REFERENCIA]}"/>
						<c:set var="codigoReferencia" value="${elemento.codReferencia}"/>
							<display:column titleKey="archigest.archivo.cf.codReferencia" sortable="true" sortProperty="codReferencia" headerClass="sortable"  media="html" >
									<c:if test="${campoCodReferencia.abreviado == 'S'}">
										<c:if test="${elemento.codReferencia != elemento.codReferenciaAbreviado}">
											<span title='<c:out value="${elemento.codReferencia}"/>'><html:img page="/pages/images/abreviado.gif" styleClass="imgTextMiddle"/></span>
										</c:if>
										<c:set var="codigoReferencia" value="${elemento.codReferenciaAbreviado}"/>
									</c:if>
									<c:out value="${codigoReferencia}"/>
								</display:column>
						<display:column titleKey="archigest.archivo.cf.codReferencia" property="codReferencia" media="csv excel xml pdf"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE] != null}">
						<c:set var="campoNumExp" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NUMERO_EXPEDIENTE]}"/>
						<display:column titleKey="archigest.archivo.cf.numExpediente" property="numexp" sortable="true" media="html"/>
						<display:column titleKey="archigest.archivo.cf.numExpediente" property="numexp" media="csv excel xml pdf"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO] != null}">
						<c:set var="campoTitulo" value="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_TITULO]}"/>
						<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" sortable="true" media="html"/>
						<display:column titleKey="archigest.archivo.cf.titulo" property="titulo" media="csv excel xml pdf"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FONDO] != null}">
						<display:column titleKey="archigest.archivo.cf.fondo" property="nombreFondo" headerClass="sortable" style="width:10%;"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS] != null}">
						<display:column titleKey="archigest.archivo.cf.rango" property="nombreRangos" headerClass="sortable" style="width:10%;"/>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_RANGOS_NUMERO_EXPEDIENTE] != null}">
						<c:choose>
							<c:when test="${campoTitulo.mostrarLink == 'S'}">
								<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" sortable="true" sortProperty="numexp" headerClass="sortable"  media="html">
									<c:choose>
										<c:when test="${elemento.subtipoCaja}">
											<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
											<c:out value="${elemento.nombreRangos}"/>
										</c:when>
										<c:otherwise>
											<c:out value="${elemento.numexp}"/>
										</c:otherwise>
									</c:choose>
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="html">
									<c:choose>
										<c:when test="${elemento.subtipoCaja}">
											<html:img page="/pages/images/rango2.gif" width="26" styleClass="imgTextMiddle" />
											<c:out value="${elemento.nombreRangos}"/>
										</c:when>
										<c:otherwise>
											<c:out value="${elemento.numexp}"/>
										</c:otherwise>
									</c:choose>
								</display:column>
							</c:otherwise>
						</c:choose>
						<display:column titleKey="archigest.archivo.cf.numExpediente" style="width:18%;" media="csv xml excel pdf">
							<c:choose>
								<c:when test="${elemento.subtipoCaja}">
									<bean:message key="archigest.archivo.simbolo.rango"/> <c:out value="${elemento.nombreRangos}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${elemento.numexp}"/>
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_NIVEL] != null}">
						<display:column titleKey="archigest.archivo.cf.nivel" property="nombre" sortable="true" headerClass="sortable" />
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR] != null}">
						<display:column titleKey="archigest.archivo.cf.productor" property="nombreProductor" headerClass="sortable" />
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_ESTADO] != null}">
						<display:column titleKey="archigest.archivo.estado" sortProperty="estado" sortable="true" headerClass="sortable">
							<c:set var="keyTituloEstado">
								archigest.archivo.cf.estadoElementoCF.<c:out value="${elemento.estado}"/>
							</c:set>
							<fmt:message key="${keyTituloEstado}" />
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_INICIAL] != null}">
						<display:column titleKey="archigest.archivo.fechaInicial" sortable="true" sortProperty="valorFInicial">
							<logic:equal name="elemento" property="formatoFInicial" value="S"><fmt:message key="archigest.archivo.abreviatura.siglo"/></logic:equal>
							<bean:write name="elemento" property="valorFInicial"/>
							<logic:present name="elemento" property="calificadorFInicial">
								(<bean:write name="elemento" property="calificadorFInicial"/>)
							</logic:present>
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_FECHA_FINAL] != null}">
						<display:column titleKey="archigest.archivo.fechaFinal" sortable="true" sortProperty="valorFFinal">
							<logic:equal name="elemento" property="formatoFFinal" value="S"><fmt:message key="archigest.archivo.abreviatura.siglo"/></logic:equal>
							<bean:write name="elemento" property="valorFFinal"/>
							<logic:present name="elemento" property="calificadorFFinal">
								(<bean:write name="elemento" property="calificadorFFinal"/>)
							</logic:present>
						</display:column>
					</c:if>
					<c:if test="${beanBusqueda.mapSalida[appConstants.fondos.camposBusquedas.CAMPO_SALIDA_BUSQUEDA_BLOQUEO] != null}">
						<display:column style="text-align: center;"  media="html">
							<c:choose>
								<c:when test="${elemento.marcasBloqueoUnidad>0}" >
									<html:img
										page="/pages/images/udocBloqueada.gif"
										titleKey="archigest.archivo.bloqueada"
										altKey="archigest.archivo.bloqueada"
										styleClass="imgTextBottom" />
								</c:when>
								<c:otherwise>
									<html:img
										page="/pages/images/udocDesbloqueada.gif"
										titleKey="archigest.archivo.desbloqueada"
										altKey="archigest.archivo.desbloqueada"
										styleClass="imgTextBottom" />
								</c:otherwise>
							</c:choose>
						</display:column>
						<display:column titleKey="archigest.archivo.bloqueada" media="csv excel xml pdf">
							<c:choose>
								<c:when test="${elemento.marcasBloqueoUnidad>0}" >
									<bean:message key="archigest.archivo.si" />
								</c:when>
								<c:otherwise>
									<bean:message key="archigest.archivo.no" />
								</c:otherwise>
							</c:choose>
						</display:column>
					</c:if>
				</display:table>


      </div><%--cuerpo_izda --%>
    </div><%--cuerpo_drcha --%>
    <h2><span>&nbsp;</span></h2>
  </div><%--ficha --%>
  </html:form>
</div><%--contenedor_ficha --%>
