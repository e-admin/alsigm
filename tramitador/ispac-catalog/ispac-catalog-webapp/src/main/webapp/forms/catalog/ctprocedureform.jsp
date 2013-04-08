<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<script>
	function save()
	{
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do"
		document.defaultForm.submit();
	}

	var block = 1;

	function showTab(i)
	{
		document.getElementById('block'+ block).style.display='none';
		document.getElementById('tdlink'+ block).className="unselect";
		document.getElementById('block'+ i).style.display='block';
		document.getElementById('tdlink'+ i).className = 'select';
		block = i;
	}
</script>



<div id="navmenutitle">
	<bean:message key="form.pproc.fichaCat"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="form.pproc.properties.subTitle"/>
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:submit('<%= request.getContextPath() + "/storeCTEntity.do"%>')">
			<nobr><bean:message key="forms.button.accept"/></nobr>
			</a>
		</li>
		<li>
			<html:link action="showPProcedure.do?entityId=22" paramId="regId" paramName="KeyId">
				<bean:message key="forms.button.cancel"/>
  		</html:link>
	  </li>
	</ul>
</div>
<html:form action='/showCTEntity.do'>

	<%--
		Nombre de Aplicación.
		 Necesario para realizar la validación

	<html:hidden property="entityAppName"/> --%>

	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Registro de solo lectura-->
	<html:hidden property="readonly"/>
	<!-- Registro de distribución -->
	<html:hidden property="property(ID)"/>

	<table cellpadding="1" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<table class="box" width="100%" border="0" cellspacing="1" cellpadding="0">
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
								<tr>
									<td width="8"><img height="1" width="8px" src="img/pixel.gif"/></td>
									<td width="100%">
									<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
													<bean:message key="forms.proc.div1"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink2" align="center" onclick="showTab(2)">
													<bean:message key="forms.proc.div2"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink3" align="center" onclick="showTab(3)">
													<bean:message key="forms.proc.div3"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink4" align="center" onclick="showTab(4)">
													<bean:message key="forms.proc.div4"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink5" align="center" onclick="showTab(5)">
													<bean:message key="forms.proc.div5"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink6" align="center" onclick="showTab(6)">
													<bean:message key="forms.proc.div6"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink7" align="center" onclick="showTab(7)">
													<bean:message key="forms.proc.div7"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink8" align="center" onclick="showTab(8)">
													<bean:message key="forms.proc.div8"/>
												</td>
												<td width="5px" class="tabSeparator"><img height="1" width="5px" src="img/pixel.gif"/></td>
												<td class="unselect" id="tdlink9" align="center" onclick="showTab(9)">
													<bean:message key="forms.proc.div9"/>
												</td>

											</tr>
										</table>
										<!-- BLOQUE DE CAMPOS -->
										<div style="display:block" id="block1" class="tabFormtable">
											<!-- TABLA DE CAMPOS -->
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.codigo" tooltipKey="procedure.card.identif.codigo.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(COD_PCD)" styleClass="inputSelectS" readonly="false" maxlength="100"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.tituloCorto" tooltipKey="procedure.card.identif.tituloCorto.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(TITULO)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.objeto" tooltipKey="procedure.card.identif.objeto.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(OBJETO)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.asunto" tooltipKey="procedure.card.identif.asunto.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(ASUNTO)" styleClass="inputSelectS" readonly="false" maxlength="512"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.actFunc" tooltipKey="procedure.card.identif.actFunc.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(ACT_FUNC)" styleClass="inputSelectS" readonly="false" maxlength="80"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.matComp" tooltipKey="procedure.card.identif.matComp.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(MTRS_COMP)" styleClass="input" size="5" readonly="false" maxlength="2"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
<%--															
															<tr>
																<td height="20" class="formsTitle">
																	<nobr><bean:message key="procedure.card.identif.servPresAct"/>:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(SERV_PREST_ACT)" styleClass="inputSelectS" readonly="false" maxlength="128"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
--%>															
<%-- 
															<tr>
																<td height="20" class="formsTitle">
																	<nobr><bean:message key="procedure.card.identif.palClave"/>:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(PLBRS_CLV)" styleClass="inputSelectS" readonly="false" maxlength="64"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
--%>															
														</table>
													</td>
												</tr>
											</table>
										</div>
<%--
										<!-- BLOQUE DE CAMPOS -->
										<div style="display:none" id="block2" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.sitTele" tooltipKey="procedure.card.identif.sitTele.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(SIT_TLM)" styleClass="input" size="5" readonly="false" maxlength="2"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.identif.urlProc" tooltipKey="procedure.card.identif.urlProc.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(URL)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<nobr>Publicado:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(PUBLICADO)" styleClass="input" size="5" readonly="false" maxlength="2"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" colspan="2" class="formsTitle" width="1%">
																	<nobr><u><b>Clasificaci&oacute;n SRC</b></u></nobr>
																</td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	&nbsp;&nbsp;<nobr>Materia:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(MATERIA)" styleClass="input" size="5" readonly="false" maxlength="4"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<nobr>Tipo procedimiento:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(TP_PCD)" styleClass="input" size="5" readonly="false" maxlength="4"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" colspan="2" class="formsTitle" width="1%">
																	<nobr><u><b>Datos adicionales</b></u></nobr>
																</td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<nobr>Ambito:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(AMBITO)" styleClass="input" size="5" readonly="false" maxlength="1"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<nobr>Nivel de interes:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(NVL_INT)" styleClass="input" size="5" readonly="false" maxlength="10"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<nobr>Dispone de Hitos publicados:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(HITOS)" styleClass="input" size="5" readonly="false" maxlength="2"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
--%>
<%--										
										<div style="display:none" id="block3" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" colspan="2" class="formsTitle" width="1%">
																	<nobr><u><b>Catalogaci&oacute;n</b></u></nobr>
																</td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	&nbsp;&nbsp;<nobr>Funci&oacute;n:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(ARCH_FUNCION)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<nobr>Serie:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(ARCH_SERIE)" styleClass="inputSelectS" readonly="false" maxlength="254"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	&nbsp;&nbsp;<nobr>Caducidad Adminitrativa:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="property(ARCH_ANOS)" styleClass="input" size="5" readonly="false" maxlength="10"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
--%>										
										<div style="display:none" id="block4" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.sujetos.interesados" tooltipKey="procedure.card.sujetos.interesados.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(INTERESADO)" styleClass="inputSelectS" readonly="false" maxlength="64"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.sujetos.tipoRel" tooltipKey="procedure.card.sujetos.tipoRel.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(TP_REL)" styleClass="input" size="5" readonly="false" maxlength="4"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.sujetos.orgRes" tooltipKey="procedure.card.sujetos.orgRes.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(ORG_RSLTR)" styleClass="inputSelectS" readonly="false" maxlength="80"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.sujetos.formaIni" tooltipKey="procedure.card.sujetos.formaIni.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(FORMA_INIC)" styleClass="input" size="5" readonly="false" maxlength="1"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															
															<%--
															<tr>
																<td height="20" class="formsTitle">
																	<nobr>Roles posibles para terceros:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(ROLES_TERC)" styleClass="input" size="5" readonly="false" maxlength="4"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															--%>
															
														</table>
													</td>
												</tr>
											</table>
										</div>
										<div style="display:none" id="block5" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.plazo" tooltipKey="procedure.card.tramit.plazo.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(PLZ_RESOL)" styleClass="input" size="5" readonly="false" maxlength="10"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.udPlazo" tooltipKey="procedure.card.tramit.udPlazo.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(UNID_PLZ)" styleClass="input" size="5" readonly="false" maxlength="1"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.fechaIni" tooltipKey="procedure.card.tramit.fechaIni.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(FINICIO)" styleClass="inputSelectS" readonly="false" maxlength="23"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.fechaFin" tooltipKey="procedure.card.tramit.fechaFin.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(FFIN)" styleClass="inputSelectS" readonly="false" maxlength="23"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.efSilencio" tooltipKey="procedure.card.tramit.efSilencio.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(EFEC_SILEN)" styleClass="input" size="5" readonly="false" maxlength="1"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.finViaAdm" tooltipKey="procedure.card.tramit.finViaAdm.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(FIN_VIA_ADMIN)" styleClass="input" size="5" readonly="false" maxlength="2"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.tramit.recursos" tooltipKey="procedure.card.tramit.recursos.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(RECURSOS)" styleClass="inputSelectS" readonly="false" maxlength="500"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
										<div style="display:none" id="block6" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.disenio.fechaCatalog" tooltipKey="procedure.card.disenio.fechaCatalog.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(FCATALOG)" styleClass="inputSelectS" readonly="false" maxlength="23"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.disenio.autor" tooltipKey="procedure.card.disenio.autor.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(AUTOR)" styleClass="inputSelectS" readonly="false" maxlength="64"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
<%--
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<nobr>Estado:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(ESTADO)" styleClass="input" size="5" readonly="false" maxlength="1"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<nobr>Versi&oacute;n:</nobr>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(NVERSION)" styleClass="input" size="5" readonly="false" maxlength="10"/>
																</td>
															</tr>
--%>															
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.disenio.observ" tooltipKey="procedure.card.disenio.observ.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(OBSERVACIONES)" styleClass="inputSelectS" readonly="false" maxlength="500"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
										<div style="display:none" id="block7" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.info.lugPres" tooltipKey="procedure.card.info.lugPres.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(LUGAR_PRESENT)" styleClass="inputSelectS" readonly="false" maxlength="500"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.info.condEcon" tooltipKey="procedure.card.info.condEcon.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(CNDS_ECNMCS)" styleClass="inputSelectS" readonly="false" maxlength="500"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.info.ingPub" tooltipKey="procedure.card.info.ingPub.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(INGRESOS)" styleClass="inputSelectS" readonly="false" maxlength="1024"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.info.formPagCobro" tooltipKey="procedure.card.info.formPagCobro.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(F_CBR_PGO)" styleClass="inputSelectS" readonly="false" maxlength="1024"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
										<div style="display:none" id="block8" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.info.infSanc" tooltipKey="procedure.card.info.infSanc.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(INFR_SANC)" styleClass="inputSelectS" readonly="false" maxlength="1024"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.info.calend" tooltipKey="procedure.card.info.calend.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(CALENDARIO)" styleClass="inputSelectS" readonly="false" maxlength="1024"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.info.descTram" tooltipKey="procedure.card.info.descTram.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(DSCR_TRAM)" styleClass="inputSelectS" readonly="false" maxlength="1024"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
										<div style="display:none" id="block9" class="tabFormtable">
											<table border="0" cellspacing="0" cellpadding="0" align="center" width="90%">
												<tr>
													<td>
														<table border="0" cellspacing="0" cellpadding="0" width="100%">
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle" width="1%">
																	<ispac:tooltipLabel labelKey="procedure.card.datosNorm.normativa" tooltipKey="procedure.card.datosNorm.normativa.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(NORMATIVA)" styleClass="inputSelectS" readonly="false" maxlength="1024"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.datosNorm.condPartic" tooltipKey="procedure.card.datosNorm.condPartic.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:text property="property(CND_PARTICIP)" styleClass="inputSelectS" readonly="false" maxlength="500"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
															<tr>
																<td height="20" class="formsTitle">
																	<ispac:tooltipLabel labelKey="procedure.card.datosNorm.docAportar" tooltipKey="procedure.card.datosNorm.docAportar.tooltip"/>
																</td>
																<td height="20">
																	&nbsp;&nbsp;<html:textarea property="property(DOCUMENTACION)" styleClass="input" readonly="false" cols="72" rows="27"
																		styleId="texta" onkeypress="javascript:maxlength('texta', 16000)"/>
																</td>
															</tr>
															<tr>
																<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>