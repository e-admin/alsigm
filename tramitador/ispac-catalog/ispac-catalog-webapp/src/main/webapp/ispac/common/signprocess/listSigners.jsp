<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<%@page import="ieci.tdw.ispac.ispaclib.sign.portafirmas.vo.Signer"%>
<bean:define id="urlparams" name="URLParams"/>

<script>
	function redirect(url)
	{
		submit('<%=request.getContextPath()%>' + url + "&" + '<c:out value="${urlparams}" escapeXml="false"/>');
	}

	function selectSigner(name, tipo, id){
		document.getElementById('property(SIGNER_NAME)').value=name;
		document.getElementById('property(SIGNER_TYPE)').value=tipo;
	}


	function noCheckedInputRadio(action) {

		var url = action + "&accept=true";
		var noSelect = true;

		var elementos = document.getElementsByName("uid");
		for ( var i = 0; i < elementos.length; i++) {
			if (elementos[i].checked) {
				noSelect = false;
				break;
			}
		}

		if (noSelect) {
			jAlert('<bean:message key="error.signers.noSelected"/>',
					'<bean:message key="common.alert"/>',
					'<bean:message key="common.message.ok"/>',
					'<bean:message key="common.message.cancel"/>');

		} else {
			var frm = document.forms[0];
			frm.action = url;
			var frameToReturn = '<c:out value="${param['workframeToReturn']}"/>';

			if (frameToReturn != null && frameToReturn != ''
					&& frameToReturn != 'undefined') {

				eval(element = window.top.frames['workframe'].document
						.getElementById('contenido'));
				element.style.top = document.getElementById("contenido").style.top;
				element.style.left = document.getElementById("contenido").style.left;
				element.style.visibility = "visible";
				element.style.display = "block";
			}
			frm.submit(url);
			showMsgInProgress();
		}
	}

	function showMsgInProgress() {
		document.getElementById('inProgress').style.display = 'block';

		document.getElementById('btnOk').disabled = true;
		document.getElementById('btnOk').href = '#';
		document.getElementById('btnCancel').disabled = true;
		document.getElementById('btnCancel').href = '#';
		document.getElementById('btnOrg').disabled = true;
		document.getElementById('btnOrg').href = '#';
		document.getElementById('btnGroup').disabled = true;
		document.getElementById('btnGroup').href = '#';
		document.getElementById('btnSearch').disabled = true;
		document.getElementById('btnSearch').href = '#';
		disabledLinks = true;
	}

	function hideShowFrame(target, action) {
		document.getElementById("move").style.visibility = "hidden";
		document.getElementById("move").style.top = "0px";
		document.getElementById("move").style.left = "0px";
		window.parent.showFrame(target, action, '', '');
	}
</script>


<div id="move" class="move">

		<div class="ficha">
			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="portafirmas.list.signers"/></h4>
					<div class="acciones_ficha">

						<c:url  var="urlviewSigners" value="/viewSignersAction.do?">
						</c:url>

						<a class="btnOk" href="javascript:noCheckedInputRadio('<c:out value="${urlviewSigners}" escapeXml="false"/><c:out value="${urlparams}" escapeXml="false"/>')">
							<nobr><bean:message key="forms.button.accept"/></nobr>
						</a>

						<a class="btnCancel" href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
							<bean:message key="forms.button.cancel"/>
						</a>

						<!--input type="button" value='<bean:message key="common.message.cancel"/>' class="btnCancel" id="btnCancel" onclick='<ispac:hideframe/>'/-->
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">

				<div class="seccion_ficha">
					<logic:messagesPresent>
						<div class="infoError">
							<bean:message key="forms.errors.messagesPresent" />
						</div>
					</logic:messagesPresent>

						<bean:define id="nombreS"><bean:message key="portafirmas.nombre"/></bean:define>
						<bean:define id="tipoFirmanteS"><bean:message key="portafirmas.tipoFirmante"/></bean:define>
						<bean:define id="identificadorS"><bean:message key="portafirmas.identificador"/></bean:define>

<div id="navmenu">
	<tiles:insert page="../tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>

<td width="5px"><img src='<ispac:rewrite href="img/pixel.gif"/>' align="center" valign="top" border="0"/></td>

						<html:form action='/showSignProcessList.do'>
							<div id="buscador"><bean:message key="catalog.search.firmantes.identificador"/>:&nbsp;
									<input type="text" id="txtFiltro" class="input" size="40" maxlength="50" value='<%=(request.getParameter("filtro")!=null?request.getParameter("filtro"):"")%>'/>
									<html:button property="filtra" styleClass="form_button_white" onclick="javascript:redirect('/viewSignersAction.do?busqueda=true&filtro=' + document.getElementById('txtFiltro').value)">
										<bean:message key="search.button"/>
									</html:button>&nbsp;&nbsp;
							</div>

							<input type="hidden" name="property(SIGNER_TYPE)" id="property(SIGNER_TYPE)"/>
							<input type="hidden" name="property(SIGNER_NAME)" id="property(SIGNER_NAME)"/>
							<display:table name="signers" id="signer" export="true" class="tableDisplay" sort="list" pagesize="15" style="width:95%"
									requestURI='<%=request.getContextPath() + "/viewSignersAction.do?busqueda=true"%>'  >

										<display:column>
											<bean:define name="signer" id="uid" property="identifier"/>
											<input type="radio" value="<%=uid%>" name="uid" id="uid" onclick="javascript:selectSigner('<bean:write name="signer" property="name"/>', '<bean:write name="signer" property="tipoFirmante"/>','<bean:write name="signer" property="identifier"/>');"/>
										</display:column>

										<display:column title="<%=tipoFirmanteS%>" sortable="true" >
												<bean:message key='<%="portafirmas.tipoCargo."+((Signer)signer).getTipoFirmante() %>'/>
										</display:column>

										<display:column property="identifier" title="<%=identificadorS%>" sortable="true"/>

										<display:column property="name" title="<%=nombreS%>" sortable="true" />

							</display:table>
							<br/>
						</html:form>
				</div>
	   		</div>
		</div>
	</div>

</body>
</html>

<script>

	$(document).ready(function(){

		$("#move").draggable();
	});
</script>