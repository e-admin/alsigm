<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/archigest-adds.tld" prefix="pa" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="parameters" value="'${fechaComp}','${fechaFormato}','${fechaIniFormato}','${fechaFinFormato}','${suffix}'"/>

<tr id="<c:out value="${idTr}" escapeXml="false"/>">
	<td class="tdTituloFicha" width="180px"><bean-el:message key="${title}"/>:&nbsp;</td>
	<td class="tdDatosFicha">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<script>
						function checkFechaComp(fechaComp, fechaFormato, fechaIniFormato, fechaIniFormato, suffix){
							if (document.getElementById(fechaComp).value == "[..]")
							{
								document.getElementById("idFecha"+suffix).style.display = 'none';
								document.getElementById("idRangoFechas"+suffix).style.display = 'block';
								if(!document.all){
									document.getElementById("idRangoFechas"+suffix).style.display = 'table-cell';
								}

								checkFechaFormato(document.getElementById(fechaIniFormato), "idFechaIni", suffix);
								checkFechaFormato(document.getElementById(fechaFinFormato), "idFechaFin", suffix);
							}else{
								document.getElementById("idRangoFechas"+suffix).style.display = 'none';
								document.getElementById("idFecha"+suffix).style.display = 'block';
								if(!document.all){
									document.getElementById("idFecha"+suffix).style.display = 'table-cell';
								}

								checkFechaFormato(document.getElementById(fechaFormato), "idFecha", suffix);
							}
						}

						function checkFechaFormato(formato, prefix, suffix){
							checkFechaFormatoSuffix(formato,prefix,suffix);
						}
					</script>
					<html-el:select styleId="${fechaComp}" property="mapFormValues(${fechaComp})" styleClass="input" onchange="javascript:checkFechaComp(${parameters})">
						<html:option key="archigest.archivo.simbolo.igual.texto" value="="/>
						<html:option key="archigest.archivo.simbolo.mayor.texto" value="&gt;"/>
						<html:option key="archigest.archivo.simbolo.menor.texto" value="&lt;"/>
						<html:option key="archigest.archivo.simbolo.mayoroigual.texto" value="&gt;="/>
						<html:option key="archigest.archivo.simbolo.menoroigual.texto" value="&lt;="/>
						<html:option key="archigest.archivo.simbolo.rango.texto" value="[..]"/>
				    </html-el:select>
				</td>
			    <td width="10">&nbsp;</td>
				<td id="idFecha<c:out value="${suffix}"/>" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<html-el:select property="mapFormValues(${fechaFormato})" styleId="${fechaFormato}" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFecha','${suffix}')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html-el:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaDValor<c:out value="${suffix}"/>" style="display:none;" nowrap="nowrap">
						    	<html-el:text property="mapFormValues(${fechaD})" size="2" maxlength="2" styleClass="input"/>-
						    </td>
						    <td id="idFechaMValor<c:out value="${suffix}"/>" style="display:none;" nowrap="nowrap">
						    	<html-el:text property="mapFormValues(${fechaM})" size="2" maxlength="2" styleClass="input"/>-
						    </td>
						    <td id="idFechaAValor<c:out value="${suffix}"/>" style="display:none;">
						    	<html-el:text property="mapFormValues(${fechaA})" size="4" maxlength="4" styleClass="input"/>
						    </td>
						    <td id="idFechaSValor<c:out value="${suffix}"/>" style="display:none;">
						    	<html-el:text property="mapFormValues(${fechaS})" size="5" maxlength="5" styleClass="input"/>
						    </td>
					    </tr>
				    </table>
				</td>
				<td id="idRangoFechas<c:out value="${suffix}"/>" style="display:none;">
					<table cellpadding="0" cellspacing="0">
						<tr>
						    <td class="user_data">Desde</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html-el:select property="mapFormValues(${fechaIniFormato})" styleId="${fechaIniFormato}" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFechaIni','${suffix}')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html-el:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaIniDValor<c:out value="${suffix}"/>" style="display:none;" nowrap="nowrap">
						    	<html-el:text property="mapFormValues(${fechaIniD})" size="2" maxlength="2" styleClass="input"/>-
						    </td>
						    <td id="idFechaIniMValor<c:out value="${suffix}"/>" style="display:none;" nowrap="nowrap">
						    	<html-el:text property="mapFormValues(${fechaIniM})" size="2" maxlength="2" styleClass="input"/>-
						    </td>
						    <td id="idFechaIniAValor<c:out value="${suffix}"/>" style="display:none;">
						    	<html-el:text property="mapFormValues(${fechaIniA})" size="4" maxlength="4" styleClass="input"/>
						    </td>
						    <td id="idFechaIniSValor<c:out value="${suffix}"/>" style="display:none;">
						    	<html-el:text property="mapFormValues(${fechaIniS})" size="5" maxlength="5" styleClass="input"/>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td class="user_data">Hasta</td>
						    <td width="5">&nbsp;</td>
							<td>
								<html-el:select property="mapFormValues(${fechaFinFormato})" styleId="${fechaFinFormato}" styleClass="input" onchange="javascript:checkFechaFormato(this, 'idFechaFin','${suffix}')">
									<html:option key="archigest.archivo.formato.AAAA" value="AAAA"/>
									<html:option key="archigest.archivo.formato.MMAAAA" value="MMAAAA"/>
									<html:option key="archigest.archivo.formato.DDMMAAAA" value="DDMMAAAA"/>
									<html:option key="archigest.archivo.formato.S" value="S"/>
							    </html-el:select>
						    </td>
						    <td width="10">&nbsp;</td>
						    <td id="idFechaFinDValor<c:out value="${suffix}"/>" style="display:none;" nowrap="nowrap">
						    	<html-el:text property="mapFormValues(${fechaFinD})" size="2" maxlength="2" styleClass="input"/>-
						    </td>
						    <td id="idFechaFinMValor<c:out value="${suffix}"/>" style="display:none;" nowrap="nowrap">
						    	<html-el:text property="mapFormValues(${fechaFinM})" size="2" maxlength="2" styleClass="input"/>-
						    </td>
						    <td id="idFechaFinAValor<c:out value="${suffix}"/>" style="display:none;">
						    	<html-el:text property="mapFormValues(${fechaFinA})" size="4" maxlength="4" styleClass="input"/>
						    </td>
						    <td id="idFechaFinSValor<c:out value="${suffix}"/>" style="display:none;">
						    	<html-el:text property="mapFormValues(${fechaFinS})" size="5" maxlength="5" styleClass="input"/>
						    </td>
					    </tr>
				    </table>
				</td>
			</tr>
		</table>
		<script>checkFechaComp(<c:out value="${parameters}" escapeXml="false"/>);</script>
	</td>
</tr>