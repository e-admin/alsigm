<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

	<c:set var="CENTRAL" value="0"/>
	<c:set var="POR_OFICINA" value="2"/>

	<div class="col" style="height: 30px; padding-top:4px;">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td align="left">
					<b><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.oficinas"/></b>
				</td>
				<td align="right">
					<table cellspacing="0" cellpadding="0" border="0">
						<tr align="right">
							<logic:notEqual name="estado" value="1">							
								<logic:notEmpty name="libroForm" property="lista">
									<c:set var="funcionllamadaActionComprobarCambios">
										llamadaActionComprobarCambios("<html:rewrite page='/permisosOficinasAsociadas.do'/>" + "?idLibro=" + "<bean:write name='libroForm' property='id' filter='false'/>", true,"<bean:message key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar'/>")																	 		
									</c:set>
									<td class="col_editar" onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.edicion.oficinas.permisos"/></td>
								</logic:notEmpty>
								
								<c:set var="funcionllamadaActionComprobarCambios">
									llamadaActionComprobarCambios("<html:rewrite page='/asociarUsuarios.do'/>" + "?idLibro=" + "<bean:write name='libroForm' property='id' filter='false'/>", true,"<bean:message key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar'/>")							 		
								</c:set>									
								<td class="col_editar" onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.permisos.usuario"/></td>
								
								<c:set var="funcionllamadaActionComprobarCambios">
									llamadaActionComprobarCambios("<html:rewrite page='/asociarOficinas.do'/>" + "?idLibro=" + "<bean:write name='libroForm' property='id' filter='false'/>", true,"<bean:message key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar'/>")																										 		
								</c:set>								
								<td class="col_nuevo" onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.usuarios.asociar.oficinas"/></td>  
							</logic:notEqual>							
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>	
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<display:table name="permisosSicres.lista" uid="fila" 
					 requestURI="/listadoAsociacion.do" class="tablaListado" sort="page" style="width:100%">
					 	<display:setProperty name="basic.msg.empty_list">
								<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.sin.oficinas.asociadas"/>
						</display:setProperty>
						<display:column property="nombre" titleKey="ieci.tecdoc.sgm.rpadmin.libros.oficina"
							 sortable="false" style="width: 32%;"/>
						<display:column style="width: 7%;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.consultar">
							<table cellspacing="0" cellpadding="0" border="0" align="center">
								<tr>
									<td>
										<input type="hidden" name="consultarCheck_<c:out value="${fila_rowNum-1}"/>"/>
										<c:if test="${fila.consultar}">
											<img src="<html:rewrite page="/img/guardar.gif"/>"/>
										</c:if>
									</td>
								</tr>
							</table>
						</display:column>
						<display:column style="width: 4%;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.crear">
							<table cellspacing="0" cellpadding="0" border="0" align="center">
								<tr>
									<td>
										<input type="hidden" name="crearCheck_<c:out value="${fila_rowNum-1}"/>"/>
										<c:if test="${fila.crear}">
											<img src="<html:rewrite page="/img/guardar.gif"/>"/>
										</c:if>				
									</td>
								</tr>
							</table>
						</display:column>
						<display:column style="width: 7%;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.asociacion.editar.modificar">
							<table cellspacing="0" cellpadding="0" border="0" align="center">
								<tr>
									<td>
										<input type="hidden" name="modificarCheck_<c:out value="${fila_rowNum-1}"/>"/>
										<c:if test="${fila.modificar}">
											<img src="<html:rewrite page="/img/guardar.gif"/>"/>
										</c:if>																						
									</td>
								</tr>
							</table>
						</display:column>
						<display:column style="width: 9%;" titleKey="ieci.tecdoc.sgm.rpadmin.libros.numeracion" sortable="false">
							<table>
								<tr>	
									<td width="80%">			
										<c:if test="${fila.numeracion == CENTRAL}">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.central"/>
										</c:if>		
										<c:if test="${fila.numeracion == POR_OFICINA}">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.configurar.por.oficina"/>
										</c:if>		
									</td>	
									<td width="20%">
										<c:set var="funcionllamadaActionComprobarCambios">
											llamadaActionComprobarCambios("<html:rewrite page='/actualizarNumeracion.do'/>" + "?idLibro=" + "<bean:write name='libroForm' property='id' filter='false'/>" + "&deptId=" +"<bean:write name='fila' property='id' filter='false'/>" + "&tipo=" + "<bean:write name='libroForm' property='tipo' filter='false'/>", true,"<bean:message key='ieci.tecdoc.sgm.rpadmin.cambios.sin.guardar'/>")	
										</c:set>																				
										<img src="<html:rewrite page="/img/refresh.gif"/>" onclick="<c:out value="${funcionllamadaActionComprobarCambios}"/>"/>									
									</td>
								</tr>	
							</table>																				
						</display:column>
						<display:column style="width: 10%;">
							<table>
								<tr>
									<c:set var="funcioncontrollerBookClosed">
										controllerBookClosed("<html:rewrite page='/eliminarAsociacion.do'/>","<bean:write name='libroForm' property='id' filter='false'/>","<bean:write name='fila' property='id' filter='false'/>","<bean:write name='fila' property='nombre' filter='false'/>","<bean:write name='libroForm' property='estado' filter='false'/>","<bean:write name='libroForm' property='tipo' filter='false'/>","<bean:message key='ieci.tecdoc.sgm.rpadmin.oficinas.eliminar.asociacion'/>")
									</c:set>
									
									<td class="col_eliminar" onclick="<c:out value="${funcioncontrollerBookClosed}"/>"> 
										<logic:equal name="estado" value="1">
											<font color="808080"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.eliminar.oficina"/></font>
										</logic:equal>
										<logic:notEqual name="estado" value="1">
											<bean:message key="ieci.tecdoc.sgm.rpadmin.libros.asociacion.eliminar.oficina"/>
										</logic:notEqual>										
									</td>
								</tr>
							</table>
						</display:column>	
						<display:column style="width: 5%;">
							<table><tr>
								<c:set var="funcionllamadaFiltrosOficina">
									llamadaFiltrosOficina("<html:rewrite page='/cargarFiltros.do'/>", "<bean:write name='libroForm' property='id' filter='false'/>", "<bean:write name='fila' property='id' filter='false'/>", "<bean:write name='fila' property='nombre' filter='false'/>")
								</c:set>
								<td class="col_editar" onclick="<c:out value="${funcionllamadaFiltrosOficina}"/>"><bean:message key="ieci.tecdoc.sgm.rpadmin.libros.aplicar.filtros"/></td> 
							</tr></table>
						</display:column>
				</display:table>
			</td>
		</tr>
	</table>