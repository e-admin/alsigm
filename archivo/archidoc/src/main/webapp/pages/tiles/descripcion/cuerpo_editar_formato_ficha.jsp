<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<bean:struts id="actionMapping" mapping="/gestionFormatoFicha" />
<c:set var="listasControlAcceso" value="${sessionScope[appConstants.descripcion.LISTAS_CONTROL_ACCESO_KEY]}"/>
<c:set var="listaFichas" value="${sessionScope[appConstants.controlAcceso.LISTA_FICHAS]}" />


<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/utils.js" type="text/JavaScript"></script>
<script language="JavaScript1.2" src="<%=request.getContextPath()%>/js/gestion_ficha.js" type="text/JavaScript"></script>
<script language="javascript">
	function exportar(){
		document.getElementById("method").value="export";
		document.getElementById("formulario").submit();
	}

</script>
<div id="contenedor_ficha">
<html:form action="/gestionFormatoFicha" styleId="formulario">
	<html:hidden property="id" />
	<input type="hidden" id="method" name="method"/>

	<div class="ficha">

		<h1><span>
			<div class="w100">
				<table class="w98" cellpadding=0 cellspacing=0>
					<tr>
				    	<td class="etiquetaAzul12Bold" height="25px">
				    		<bean:message key="archigest.archivo.descripcion.fichas.form.formatos.fichas.caption"/>
				    	</td>
				    	<td align="right">
							<table cellpadding=0 cellspacing=0>
								<tr>

								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:create(document.forms['formatoFichasForm'],'<bean:message key='archigest.archivo.descripcion.fichas.form.nombre.empty.msg'/>','<bean:message key='archigest.archivo.descripcion.fichas.form.definicion.empty.msg'/>')"
										><input type="image"
												src="../images/Ok_Si.gif"
										        alt="<bean:message key="archigest.archivo.aceptar"/>"
										        title="<bean:message key="archigest.archivo.aceptar"/>"
										        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
									</td>
									<td width="10">&nbsp;</td>
								   	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:call(document.forms['formatoFichasForm'],'goBack')"
										><html:img page="/pages/images/Ok_No.gif"
										        border="0"
										        altKey="archigest.archivo.cancelar"
										        titleKey="archigest.archivo.cancelar"
										        styleClass="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.cancelar"/></a>
									</td>
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



			<div class="bloque"><%--bloque datos tabla --%>
				<table class="formulario">
					<tr>
						<td class="tdTitulo" width="180"><bean:message key="archigest.archivo.descripcion.fichas.form.nombre"/>:&nbsp;</td>
						<td class="tdDatos"><html:text property="nombre" size="128" maxlength="128"/>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdTitulo"><bean:message key="archigest.archivo.cf.ficha"/></td>
						<td class="tdDatos">
							<html:select property="idFicha" styleClass="input">
								<html:optionsCollection name="listaFichas" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.fmtfichas.form.tipo"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="tipo">
								<html:option key="archigest.archivo.consultas.consulta" value="1"/>
								<html:option key="archigest.archivo.formato.tipo.edicion" value="2"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.nivelAcceso"/>:&nbsp;</td>
						<td class="tdDatos">
							<script>
								function checkListaControlAcceso()
								{
									var form = document.forms["<c:out value="${actionMapping.name}" />"];
									if (form.nivelAcceso.value == 1)
									{
										form.idlca.value = "";
										document.getElementById('trListaControlAcceso').style.visibility="hidden";
									}
									else
										document.getElementById('trListaControlAcceso').style.visibility="visible";
								}
							</script>
							<html:select property="nivelAcceso" onchange="javascript:checkListaControlAcceso()">
								<html:option key="archivo.nivel_acceso.publico" value="1"/>
								<html:option key="archivo.nivel_acceso.archivo" value="2"/>
								<html:option key="archivo.nivel_acceso.restringido" value="3"/>
							</html:select>
						</td>
					</tr>

					<tr id="trListaControlAcceso">
						<td class="tdTitulo" width="250"><bean:message key="archigest.archivo.descripcion.descriptor.form.listaControlAcceso"/>:&nbsp;</td>
						<td class="tdDatos">
							<html:select property="idlca">
								<html:option key="" value=""/>
								<html:optionsCollection name="listasControlAcceso" label="nombre" value="id"/>
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="tdTitulo" style="vertical-align:top;"><bean:message key="archigest.archivo.descripcion.fichas.form.descripcion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="descripcion" rows="2" onchange="maxlength(this,254,true)" onkeypress="maxlength(this,254,false)" />&nbsp;</td>
					</tr>
				</table>

				<script>checkListaControlAcceso()</script>

			</div><%--bloque datos tabla --%>

			<div class="separador5">&nbsp;</div>



			<div class="cabecero_bloque"><%--cabecero valores de la tabla --%>
				<table class="w98m1" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td>&nbsp;</td>
							<td align="right">
								<table cellpadding=0 cellspacing=0>
									<tr>
										<td>
										   	<a class="etiquetaAzul12Bold"
												   href="javascript:call(document.forms['formatoFichasForm'],'import')"
												><input type="image"
														src="../images/importar.gif"
												        alt="<bean:message key="archigest.archivo.importar"/>"
												        title="<bean:message key="archigest.archivo.importar"/>"
												        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.importar"/></a>
										</td>
										<td width="10">&nbsp;</td>

									  	<td>
										<a class="etiquetaAzul12Bold"
										   href="javascript:exportar()"
										>
												   <input type="image"
														src="../images/exportar.gif"
												        alt="<bean:message key="archigest.archivo.exportar"/>"
												        title="<bean:message key="archigest.archivo.exportar"/>"
												        class="imgTextMiddle"/>&nbsp;<bean:message key="archigest.archivo.exportar"/></a>
										</td>
										<td width="10">&nbsp;</td>
					                  </tr>
					             </table>
				             </td>
		                </tr>
					</tbody>
				</table>
			</div> <%--cabecero valores de la tabla --%>

			<div class="bloque"><%--bloque valores tabla --%>
				<div class="separador1">&nbsp;</div>



				<div class="w100">
					<table class="formulario" cellpadding="0" cellspacing="0">
						<tr>

						<td class="tdTitulo" width="180" style="vertical-align:top;"><bean:message key="archigest.archivo.descripcion.fichas.form.definicion"/>:&nbsp;</td>
						<td class="tdDatos"><html:textarea property="definicion" rows="20" />&nbsp;</td>

					</tr>
					</table>
				</div>


		</div> <%--cuerpo_izda --%>
		</div> <%--cuerpo_drcha --%>

		<h2><span>&nbsp;</span></h2>
	</div> <%--ficha --%>
  </html:form>

</div> <%--contenedor_ficha --%>


