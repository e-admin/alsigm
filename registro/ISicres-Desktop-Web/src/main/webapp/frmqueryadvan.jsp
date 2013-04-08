<%@ page language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="registrosconsulta" value="${sessionScope['valoresConsulta']}"/>
<c:set var="campos" value="${sessionScope['camposconsulta']}" />
<c:set var="contRegistro" value="${sessionScope['contRegistros']}"/>
<c:set var="alloperadores" value="${sessionScope['alloperadores']}" />
<c:set var="camposOrderSelec" value="${sessionScope['camposOrderSelec']}" />
<c:set var="camposOrderBy" value="${sessionScope['camposOrderBy']}"/>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<title>&nbsp;</title>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script language="javascript">
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');
		</script>
		<link rel="stylesheet" type="text/css" href="./css/global.css" />
		<link rel="stylesheet" type="text/css" href="./css/font.css" />
		<link rel="stylesheet" type="text/css" href="./css/table.css" />
		<script TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/colors.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/qryfmt.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/genmsg.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/global.js"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/jquery-1.6.2.min.js"></script>
    	<script type="text/javascript" language="javascript" src="./scripts/jquery.hotkeys-0.8.js"></script>
    	<script type="text/javascript" language="javascript" src="./scripts/custom_hotkeys.js"></script>
		<script type="text/javascript" language="javascript" src="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/tbltext.js"></SCRIPT>

		<script language="javascript">
			function newTableRow(statusIndex) {

				var numeroFila = addTableRow(statusIndex);
				var selectOperadorFila = document.getElementById('oSelectCampo_'+ numeroFila);
				var selectLength = 0;
				<c:forEach var="campo" items="${campos}">
					selectLength = selectOperadorFila.length;
					var newOpt = new Option('<c:out value="${campo.value.fieldConf.fieldLabel}"/>', '<c:out value="${campo.value.fieldConf.fieldId}"/>');
					selectOperadorFila.options[selectLength] = newOpt;
				</c:forEach>
			}

			function validateNotNull(campo){
				//comprobamos que el valor de Campo no sea nulo una vez que ha sido cambiado
				if((campo.value == null) || (campo.value == '') || (campo.value == ' ')){
					alert(top.GetIdsLan( "IDS_SEARCH_CAMPO_NOT_NULL" ));
					//seleccionamos el primer valor
					campo.value=1;
				}
			}

		</script>
	</head>

	<!-- Benito <body onload="HabilitarBotones(); getOrderQuery();" onfocus="AsigFocus()"   tabindex="-1">-->
	<!-- <body onload="OnWindowLoadAdvSearch();HabilitarBotones();" tabindex="-1"> -->

	<BODY onload="OnWindowLoadAdvSearch();top.Main.Workspace.EnabledTool();HabilitarBotones(); getOrderQuery();"
		onfocus="AsigFocus();" scroll="auto" ondragstart="return false;" tabIndex="-1">
		<!-- <form action="vldSearchOperator.jsp" method="post" id="QryFmtForm" name="QryFmtForm" target="TableData"> -->
		<form action="vldSearchOperator.jsp" method="post" id="QryFmtForm" name="QryFmtForm">

		<div id="divSearch">

		<table>
<%--			<tr>
				<td colspan="2">
					<label id="typeSearch" class="titleSearch"><script type="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_TYPE_SEARCH_AVAN"));</script></label>
				</td>
			</tr>
--%>
			<tr>
				<td>
					<table id="tblCondiciones">
						<thead>
							<tr class="searchTitle2">
								<th class="searchTitle">
									<input type="hidden" name="method" id="method" value=""/>
									<input type="hidden" name="contadorreg" id="contadorreg" value="<c:out value="${contRegistros}"/>"/>
									<script type="text/javascript">document.write(top.GetIdsLan("IDS_SEARCH_LABEL_FIELD"));</script>
								</th>
								<th class="searchTitle"><script type="text/javascript">document.write(top.GetIdsLan("IDS_SEARCH_LABEL_OPERATOR"));</script></th>
								<th class="searchTitle"><script type="text/javascript">document.write(top.GetIdsLan("IDS_SEARCH_LABEL_VALUE"));</script></th>
								<th class="searchTitle"><script type="text/javascript">document.write(top.GetIdsLan("IDS_SEARCH_LABEL_NEXO"));</script></th>
								<th class="searchTitle">&nbsp;</th>
							</tr>
						</thead>
						<tbody>

						<c:forEach var="registroconsulta" items="${registrosconsulta.fieldSearchAvanced}" varStatus="status">

    						<tr>
    							<td>
    								<input type="hidden" name="numregistro" id="numregistro" value="<c:out value="${status.index}"/>"/>
     								<c:set var="selectedFieldId" value="0"/>
     								<c:set var="selectedFieldIdNum" value="0"/>

     								<select class="comboSearchAvanced" name="oSelectCampo_<c:out value="${status.index}"/>" id="oSelectCampo_<c:out value="${status.index}"/>" onchange="validateNotNull(this);CargarOperadores(<c:out value="${status.index}"/>)" tabindex="1">
										<option value=" " selected="selected"/>
										<c:forEach var="campo" items="${campos}">
											<option value="<c:out value="${campo.value.fieldConf.fieldId}" />"
												<c:if test="${registroconsulta.fieldConf.fieldId==campo.value.fieldConf.fieldId}"><c:set var="selectedFieldId" value="FLD${campo.value.fieldConf.fieldId}"/> <c:set var="selectedFieldIdNum" value="${campo.value.fieldConf.fieldId}"/> selected="selected"</c:if>>
												<c:out value="${campo.value.fieldConf.fieldLabel}" />
											</option>
										</c:forEach>
    								</select>
    							</td>

    							<td>
    								<c:choose>
    									<c:when test='${registroconsulta.fieldConf.fieldId==503}'>
    										<select class="comboSearchAvanced" name="oSelectOperador_<c:out value="${status.index}"/>" id="oSelectOperador_<c:out value="${status.index}"/>" tabindex="2" style="display:none;">
    									</c:when>
    									<c:otherwise>
											<select class="comboSearchAvanced" name="oSelectOperador_<c:out value="${status.index}"/>" id="oSelectOperador_<c:out value="${status.index}"/>" tabindex="2">
    									</c:otherwise>
    								</c:choose>
											<c:forEach var="operador" items="${alloperadores[selectedFieldId]}">
												<option value="<c:out value="${operador.idOperator}" />"
												 	<c:if test="${registrosconsulta.idOperator[status.index] == operador.idOperator}"> selected="selected"</c:if>>
													<c:out value="${operador.literal}" />
												</option>
											</c:forEach>
	    								</select>

	    							<c:if test="${registroconsulta.fieldConf.fieldId==503}">
										<!-- Generamos un combo select tonto, informativo unicamente para el usuario -->
										<select class="comboSearchAvanced" name="oSelectOperadorFld503_<c:out value="${status.index}"/>" id="oSelectOperadorFld503_<c:out value="${status.index}"/>" tabindex="2" disabled="disabled">
											<c:forEach var="operador" items="${alloperadores[selectedFieldId]}">
												<option value="<c:out value="${operador.idOperator}" />">
													<c:out value="${operador.literal}" />
												</option>
											</c:forEach>
										</select>
	    							</c:if>
    							</td>
    							<td width="154">

    							    <c:choose>
    									<c:when test='${registroconsulta.fieldConf.fieldId==503}'>
										      		<input type="text"
				    									   name="where_<c:out value="${status.index}"/>"
				    									   id="where_<c:out value="${status.index}"/>"
				    									   value="<c:out value="${registrosconsulta.valueWhere[status.index]}"/>"
				    									   class="inputTextSearchAvanced"
				    									   style="display:none;"
				    									   tabIndex="1">
				    								</input>

													<select id="whereCombo_<c:out value="${status.index}"/>"
															name="whereCombo_<c:out value="${status.index}"/>" tabIndex="1" class="inputTextSearchAvanced" onchange="changeComboIntRegistralSearchAdvan(this, <c:out value="${status.index}"/>)">
															<option value="0" <c:if test="${registrosconsulta.valueWhere[status.index] == 0}"> selected="selected"</c:if>>
																<SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_COMBO_INTER_REG_NO"));</SCRIPT>
															</option>
															<option value="1" <c:if test="${registrosconsulta.valueWhere[status.index] == 1}"> selected="selected"</c:if>>
																<SCRIPT TYPE="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_COMBO_INTER_REG_SI"));</SCRIPT>
															</option>
													</select>
    										</input>
    									</c:when>
    									<c:otherwise>
	    									<c:choose>
		    									<c:when test='${registroconsulta.caseSensitive != "CI"}'>
		    										<input type="text"
				    									   name="where_<c:out value="${status.index}"/>"
				    									   id="where_<c:out value="${status.index}"/>"
				    									   value="<c:out value="${registrosconsulta.valueWhere[status.index]}"/>"
				    									   class="inputTextSearchAvanced"
				    									   style="text-transform:uppercase;"
				    									   onblur="evaluateCaseSensitive('where_<c:out value="${status.index}"/>', '<c:out value="${registroconsulta.caseSensitive}"/>');"
				    									   tabIndex="1">
		    										</input>
										      	</c:when>
										      	<c:otherwise>
										      		<input type="text"
				    									   name="where_<c:out value="${status.index}"/>"
				    									   id="where_<c:out value="${status.index}"/>"
				    									   value="<c:out value="${registrosconsulta.valueWhere[status.index]}"/>"
				    									   class="inputTextSearchAvanced"
				    									   tabIndex="1">
				    								</input>
										      	</c:otherwise>
										    </c:choose>

		    								<c:if test="${registroconsulta.validation>0}">
		    									<script>
		    										document.getElementById("where_<c:out value="${status.index}"/>").className = "inputTextSearchAvancedImg";
			    								</script>
		    									<img src="images/buscar2.gif" class="imgHelpSearch" id="imghelp_<c:out value="${status.index}"/>"  name="imghelp_<c:out value="${status.index}"/>"
		    										 onclick="VldHelp(event,'where_<c:out value="${status.index}"/>','<c:out value="${selectedFieldIdNum}"/>','<c:out value="${registroconsulta.validation}"/>','<c:out value="${registroconsulta.caseSensitive}"/>')"/>
		    								</c:if>
		    								<c:if test="${(registroconsulta.fldType==2) or (registroconsulta.fldType==3)}">
		    									<script>
		    										document.getElementById("where_<c:out value="${status.index}"/>").className = "inputTextSearchAvancedImg";
			    								</script>
		    									<img src="images/calendarM.gif" id="imghelp_<c:out value="${status.index}"/>"  name="imghelp_<c:out value="${status.index}"/>"
		    										 class="imgHelpCalendar"
		    										 onclick='showCalendarQueryAdvan(this, "where_<c:out value="${status.index}"/>" , top.Idioma)'"/>

		    								</c:if>

		    								<c:if test="${registrosconsulta.hasInvalidValue[status.index]}">
		    									<script>
		    										if (document.getElementById("where_<c:out value="${status.index}"/>").value == ""){
		    											document.getElementById("where_<c:out value="${status.index}"/>").style.backgroundColor = "red";
		    										}else{
			    										document.getElementById("where_<c:out value="${status.index}"/>").style.color = "red";
			    									}
			    								</script>
		    								</c:if>
    									</c:otherwise>
    								</c:choose>
    							</td>
    							<td>
    								<select name="nexo_<c:out value="${status.index}"/>" id="nexo_<c:out value="${status.index}"/>" class="comboSearchAvancednexo" tabIndex="-1">
    									<option value="or"
    										<c:if test='${registrosconsulta.nexo[status.index]=="or"}'> selected="selected"</c:if>><script type="text/javascript">document.write(top.GetIdsLan("IDS_SEARCH_LABEL_NEXO_OR"));</script></option>
    									<option value="and"
    										<c:if test='${registrosconsulta.nexo[status.index]=="and"}'> selected="selected"</c:if>><script type="text/javascript">document.write(top.GetIdsLan("IDS_SEARCH_LABEL_NEXO_AND"));</script></option>
    								</select>
    							</td>
    							<td>
    								<img src="images/masCampos.gif" id="btnAdd_<c:out value="${status.index}"/>"  name="btnAdd_<c:out value="${status.index}"/>" onclick="javascript:newTableRow();HabilitarBotones()" style="visibility:hidden"/>
    								<img src="images/menosCampos.gif" id="btnDelete_<c:out value="${status.index}"/>" name="btnDelete_<c:out value="${status.index}"/>" style= "visibility:hidden" onclick="javascript:HabilitarBotones(); removeTableRows(<c:out value="${status.index}"/>)" style="visibility:hidden"/>
    							</td>
    						</tr>

						</c:forEach>
    					</tbody>
    				</table>

				</td>
			</tr>
		</table>
    	</div>

    	<div id="divOrder" style="display:none">
    		<table class="title" width="100%">
    			<tr>
    				<td>
    					<script type="text/javascript"> document.write(top.GetIdsLan("IDS_LABEL_ORDER"));</script>
    				</td>
    				<td width="3%">
    					<img src="images/pcalclose.gif" id="btnClose"  name="btnClose" onclick="closeOrder(); getOrderQuery();"/>
    					<script type="text/javascript">document.getElementById("btnClose").alt = top.GetIdsLan("IDS_OPCCERRAR");</script>
    				</td>
    			</tr>
    		</table>

    		<table width="100%">
    			<tr height="10">
    				<td colspan="4"/>
    			</tr>
    			<tr>
  					<td width="6%">&nbsp;</td>
    				<td class="tdLabel" width="40%">
    					<label class="label">
    						<script type="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_AVAILABLEFIELDS"));</script>
    					</label>
    				</td>
    				<td width="5%"></td>
    				<td class="tdLabel" width="40%">
    					<label class="label">
    						<script type="text/javascript">document.write(top.GetIdsLan("IDS_LABEL_ORDERBY"));</script>
    					</label>
    				</td>
    				<td></td>
    			</tr>
    			<tr>
    				<td>&nbsp;</td>
    				<td class="tdBody">
    					<select id="listFields" name="listFields" class="label" size="25" multiple="multiple" style="width:100%">
							<c:forEach var="campo" items="${camposOrderBy}">
								<option value="<c:out value="fld${campo.value.fieldId}"/>">
									<c:out value="${campo.value.fieldLabel}" />
								</option>
							</c:forEach>
    					</select>
    				</td>

    				<td class="tdBody" valign="middle" align="center">
    					<table>
    						<tr>
    							<td>
    								<img src="images/flecha_dcha.png" id="btnAgreg"  name="btnAgreg" onclick="aggregateOrder(false);"/>
    								<script type="text/javascript">document.getElementById("btnAgreg").alt = top.GetIdsLan("IDS_BTNAGREGAR");</script>
    							</td>
    						</tr>
    						<tr>
    							<td>
    								<img src="images/flecha_doble_dcha.png" id="btnAgregAll"  name="btnAgregAll" onclick="aggregateOrder(true);"/>
    								<script type="text/javascript">document.getElementById("btnAgregAll").alt = top.GetIdsLan("IDS_BTN_AGGREGALL");</script>
    							</td>
    						</tr>
    						<tr height="30">
    							<td></td>
    						</tr>
    						<tr>
    							<td>
    								<img src="images/flecha_izda.png" id="btnRemove"  name="btnRemove" onclick="removeOrder(false);"/>
    								<script type="text/javascript">document.getElementById("btnRemove").alt = top.GetIdsLan("IDS_BTN_REMOVE");</script>
								</td>
							</tr>
							<tr>
								<td>
									<img src="images/flecha_doble_izda.png" id="btnRemoveAll"  name="btnRemoveAll" onclick="removeOrder(true);"/>
									<script type="text/javascript">document.getElementById("btnRemoveAll").alt = top.GetIdsLan("IDS_BTN_REMOVEALL");</script>
								</td>
							</tr>
						</table>
    				</td>
					<td class="tdBody">
						<select id="listOrder" name="listOrder" class="label" multiple="multiple" size="25" style="width:100%" ondblclick="changeSense();">
							<c:forEach var="campo" items="${camposOrderSelec}">
								<option value="<c:out value="fld${campo.fieldConf.fieldId}"/>" label="<c:out value="${campo.fieldConf.fieldLabel}" />(<c:out value="${campo.sense}"/>)"  sense="<c:out value="${campo.sense}"/>">
									<c:out value="${campo.fieldConf.fieldLabel}" />(<c:out value="${campo.sense}"/>)
								</option>
							</c:forEach>
						</select>
					</td>
					<td align="left" valign="bottom">
						<table>
							<tr>
								<td>
									<img src="images/arriba.gif" id="btnUp"  name="btnUp" onclick="upOrder();" />
									<script type="text/javascript">document.getElementById("btnUp").alt = top.GetIdsLan("IDS_BTN_UP");</script>
								</td>
							</tr>
							<tr>
								<td>
									<img src="images/abajo.gif" id="btnDown"  name="btnDown" onclick="downOrder();"/>
									<script type="text/javascript">document.getElementById("btnDown").alt = top.GetIdsLan("IDS_BTN_DOWN");</script>
								</td>
							</tr>
						</table>
					</td>
    			</tr>
    			<tr height="12">
    				<td colspan="4"></td>
    			</tr>
			</table>
		</div>

    	<input type="hidden" id="dataOrder" name="99999"></input>


    	</form>
    	<iframe id="Vld" name="Vld" SRC="blank.htm" frameborder="0" scrolling="no" style="display:none">
		</iframe>

	</body>
</html>