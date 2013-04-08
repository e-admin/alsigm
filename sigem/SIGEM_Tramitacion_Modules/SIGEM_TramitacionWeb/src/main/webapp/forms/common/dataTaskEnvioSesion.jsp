<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>

<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>

<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>

<table cellspacing="0" cellpadding="0" align="center" width="90%">

	<tr>
		<td height="10px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
	</tr>
	<tr>
		<td class="textbar">

			<table cellspacing="0" cellpadding="0" border="0">

				<tr>
					<td class="textbar">
						<bean:message key="forms.task.task"/>:
					</td>
					<td>
						<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:NOMBRE)"/>
					</td>
					<td>
						<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
					<td class="textbar">
						<bean:message key="forms.task.initiated"/>:
					</td>
					<td>
						<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/></td>
					<td class="text11">
						<bean:write name="defaultForm" property="property(SPAC_DT_TRAMITES:FECHA_INICIO)"/>
					</td>
				</tr>

			</table>

		</td>
	</tr>
	<tr>
		<td width="100%" valign="bottom" height="5px" style="font-size:4px;">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td valign="top">

			<table class="caja" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td height="8px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
				</tr>
				<tr>
					<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/>
					</td>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" width="100%">


							<tr valign="top">
								<td height="20" class="formsTitleB">
									<nobr><bean:message key="forms.task.remarks"/>:</nobr>
								</td>
								<td height="20">
									<ispac:htmlTextarea property="property(SPAC_DT_TRAMITES:OBSERVACIONES)"
										readonly="false" styleClass="input" rows="2" cols="85" ></ispac:htmlTextarea>
								</td>
							</tr>

						</table>
					</td>
					<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/>
					</td>
				</tr>
				<tr>
					<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="10px"/></td>
				</tr>
				<tr height="50">
					<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/>
					</td>
					<td colspan="2">
						<table style="border:0px solid #616EAA;" cellspacing="0" cellpadding="0" width="100%">

							<tr>
								<td colspan="4" height="20" width="100%" class="formsTitleB">
									<bean:write name="defaultForm" property="entityApp.label(SEC_PROPUESTA:ENVIO_SESION)" />:</div>

						<logic:equal name="defaultForm"
							property="entityApp.property(sentToSession)" value='0'>
							<html:hidden property="property(SEC_PROPUESTA:ENVIO_SESION)" />
							<ispac:htmlTextImageFrame property="property(ENVIO_SESION_SEC_VLDTBL_ORG_COMPETENTE:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="64" imageTabIndex="true" id="SEARCH_SEC_PROPUESTA_ENVIO_SESION" target="workframe" action="selectSubstitute.do?entity=SEC_VLDTBL_ORG_COMPETENTE" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
								<ispac:parameter name="SEARCH_SEC_PROPUESTA_ENVIO_SESION" id="property(SEC_PROPUESTA:ENVIO_SESION)" property="VALOR" />
								<ispac:parameter name="SEARCH_SEC_PROPUESTA_ENVIO_SESION" id="property(ENVIO_SESION_SEC_VLDTBL_ORG_COMPETENTE:SUSTITUTO)" property="SUSTITUTO" />
							</ispac:htmlTextImageFrame>
							</logic:equal>



							<logic:equal name="defaultForm"
							property="entityApp.property(sentToSession)" value='1'>
								<ispac:htmlTextImageFrame property="property(ENVIO_SESION_SEC_VLDTBL_ORG_COMPETENTE:SUSTITUTO)" readonly="true" readonlyTag="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="64" imageTabIndex="true" id="SEARCH_SEC_PROPUESTA_ENVIO_SESION" target="workframe" action="selectSubstitute.do?entity=SEC_VLDTBL_ORG_COMPETENTE" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
								<ispac:parameter name="SEARCH_SEC_PROPUESTA_ENVIO_SESION" id="property(SEC_PROPUESTA:ENVIO_SESION)" property="VALOR" />
								<ispac:parameter name="SEARCH_SEC_PROPUESTA_ENVIO_SESION" id="property(ENVIO_SESION_SEC_VLDTBL_ORG_COMPETENTE:SUSTITUTO)" property="SUSTITUTO" />
							</ispac:htmlTextImageFrame>
						</logic:equal>
						<logic:notEmpty name="defaultForm" property="property(SEC_PROPUESTA:ENVIO_SESION)" >
								<c:url value="sendToSesion.do" var="_link">
									<c:param name="method" value="search"/>
								</c:url>

								<ispac:linkframe 	id="sendSesion"
													target="workframe"
													action="sendToSesion.do?method=search"
													titleKey="forms.task.envioSesion"
													showFrame="true"
													inputField=""
													styleClass="tdlink"
													width="500"
													height="300"
													needToConfirm="true">
								</ispac:linkframe>

						</logic:notEmpty>

						</td>
						<td width="18"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/>
								</td>
						</tr>
						<tr></tr>
						</table>

					</td>
					<td width="8"><img height="1" width="8px" src='<ispac:rewrite href="img/pixel.gif"/>'/>
					</td>
				</tr>




			</table>

		</td>
	</tr>
	<tr>
		<td height="4px"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="4px"/></td>
	</tr>

</table>