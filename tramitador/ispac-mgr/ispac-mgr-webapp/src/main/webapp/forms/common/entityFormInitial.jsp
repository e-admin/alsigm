<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<script language='JavaScript' type='text/javascript'><!--

	function save() {
	
		document.defaultForm.target = "ParentWindow";
		document.defaultForm.action = "storeEntity.do";
		document.defaultForm.submit();
		ispac_needToConfirm = true;
	}

//--></script>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>
<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<html:form action="storeEntity.do">

	<!-- Solapa que se muestra -->
	<html:hidden property="block" value="1"/>
	
	<!-- Nombre de Aplicación -->
	<html:hidden property="entityAppName"/>
	<!-- Identificador de la entidad -->
	<html:hidden property="entity"/>
	<!-- Identificador del registro -->
	<html:hidden property="key"/>
	<!-- Indicador de sólo lectura -->
	<html:hidden property="readonly"/>

	<table cellpadding="0" cellspacing="0" width="100%">
	
		<tr>
			<td>
			
				<table class="boxTab" width="100%" border="0" cellspacing="0" cellpadding="0">
				
					<tr>
						<td class="title" height="28px">
						
							<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
							
								<tr>
									<td>
									
										<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
										
											<tr>
												<td>
												
													<!-- COMIENZO DE LAS ACCIONES -->
													<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
													
														<tr>
															<td width="4px" height="28px"><img height="1" width="4px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
															
															<!-- ACCION SALVAR -->
															<logic:equal value="false" property="readonly" name="defaultForm">
															
																<td class="formaction" height="28px">
																	<html:link onclick="javascript: ispac_needToConfirm = false;" href="javascript:save();" styleClass="formaction">
																		<bean:message key="forms.button.save"/>
																	</html:link>
																</td>
																
															</logic:equal>
															<!--FIN ACCION SALVAR -->
															
															<td height="28px"><img height="1" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
														</tr>
														
													</table>
													<!-- FINAL DE LAS ACCIONES -->
													
												</td>
											</tr>
											
										</table>
										
									</td>
								</tr>
								
							</table>
							
						</td>
					</tr>
					
					<!-- FORMULARIO -->
					<tr>
						<td class="blank">
						
							<table width="100%" border="0" cellspacing="2" cellpadding="2">
							
								<tr>
									<td height="5px" colspan="3"><html:errors/></td>
								</tr>
								<tr>
									<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
									<td width="100%">
