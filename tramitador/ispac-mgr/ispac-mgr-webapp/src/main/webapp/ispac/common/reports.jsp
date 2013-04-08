<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>




<div id="contenido" class="move" >
<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="report.title" /></h4>
<div class="acciones_ficha"><a href="#" id="btnCancel"
	onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'
	class="btnCancel"><bean:message key="common.message.close" /></a></div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%>

<div class="cuerpo_ficha">
<div class="seccion_ficha">
<html:form action="/showReports.do">

<display:table name="ReportList"
	id="report" export="true" class="tableDisplay" sort="list"
	pagesize="20" requestURI="/showReports.do"
	decorator="ieci.tdw.ispac.ispacweb.decorators.SelectedRowTableDecorator">
	

	<display:column titleKey="report.nombre" headerClass="sortable"
		sortable="true" media="html">
		<logic:present name="report" property="property(ID)">
			
				<bean:define id="idReport" name="report"
				property="property(ID)" />
			
			<logic:notEmpty name="report" property="property(FRM_PARAMS)">
             	<logic:present parameter="tipo">
					<bean:parameter id="tipo" name="tipo"/>
					<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=params&id=" + idReport  +"&tipo="+tipo %>'/>');">
						<ispac:documenticon imageSrc="img/docs/" extension="pdf"
							styleClass="imgTextBottom" />
						<bean:write name="report" property="property(NOMBRE)" />
						
					</a>
				</logic:present>
			 	<logic:notPresent parameter="tipo">
					<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=params&id=" + idReport %>'/>');">
						<ispac:documenticon imageSrc="img/docs/" extension="pdf"
							styleClass="imgTextBottom" />
						<bean:write name="report" property="property(NOMBRE)" />
						
					</a>
				</logic:notPresent>	

			</logic:notEmpty>
			
			<logic:empty name="report" property="property(FRM_PARAMS)">
				<logic:empty name="report"
					property="property(FILAS)">
				
					<a href='<%="report.do?method=generate&id=" + idReport %>'> <ispac:documenticon
						imageSrc="img/docs/" extension="pdf" styleClass="imgTextBottom" />
					<bean:write name="report"
						property="property(NOMBRE)" /> </a>
	
				</logic:empty>
				<logic:notEmpty name="report" property="property(FILAS)">

					<bean:define id="filas" name="report" property="property(FILAS)" />
					<bean:define id="columnas" name="report"
						property="property(COLUMNAS)" />
	
						<logic:present parameter="tipo">
							<bean:parameter id="tipo" name="tipo"/>
							<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=select&id=" + idReport + "&filas=" + filas + "&columnas=" + columnas +"&tipo="+tipo %>'/>');">
								<ispac:documenticon imageSrc="img/docs/" extension="pdf"
								styleClass="imgTextBottom" />
								<bean:write name="report" property="property(NOMBRE)" />
		
							</a>
						</logic:present>
						<logic:notPresent parameter="tipo">
							<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=select&id=" + idReport + "&filas=" + filas + "&columnas=" + columnas %>'/>');">
								<ispac:documenticon imageSrc="img/docs/" extension="pdf"
								styleClass="imgTextBottom" />
								<bean:write name="report" property="property(NOMBRE)" />
		
							</a>
						</logic:notPresent>

			</logic:notEmpty>
			</logic:empty>

		</logic:present>


		<logic:present name="report" property="property(SPAC_CT_INFORMES:ID)">
			<bean:define id="idReportCT" name="report"
				property="property(SPAC_CT_INFORMES:ID)" />
			
			<logic:notEmpty name="report" property="property(SPAC_CT_INFORMES:FRM_PARAMS)">
				<logic:present parameter="tipo">
					<bean:parameter id="tipo" name="tipo"/>
					<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=params&id=" + idReportCT +"&tipo="+tipo%>'/>');">
						<ispac:documenticon imageSrc="img/docs/" extension="pdf"
							styleClass="imgTextBottom" />
						<bean:write name="report" property="property(SPAC_CT_INFORMES:NOMBRE)" />
					</a>
				</logic:present>
					
				<logic:notPresent parameter="tipo">
					<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=params&id=" + idReportCT %>'/>');">
						<ispac:documenticon imageSrc="img/docs/" extension="pdf"
							styleClass="imgTextBottom" />
						<bean:write name="report" property="property(SPAC_CT_INFORMES:NOMBRE)" />
					</a>
				</logic:notPresent>

			</logic:notEmpty>
			
			<logic:empty name="report" property="property(SPAC_CT_INFORMES:FRM_PARAMS)">
				<logic:empty name="report"
					property="property(SPAC_CT_INFORMES:FILAS)">
				
					<a href='<%="report.do?method=generate&id=" + idReportCT %>'> <ispac:documenticon
						imageSrc="img/docs/" extension="pdf" styleClass="imgTextBottom" />
					<bean:write name="report"
						property="property(SPAC_CT_INFORMES:NOMBRE)" /> </a>
	
				</logic:empty>
	
				<logic:notEmpty name="report"
					property="property(SPAC_CT_INFORMES:FILAS)">
	
					<bean:define id="filas" name="report"
						property="property(SPAC_CT_INFORMES:FILAS)" />
					<bean:define id="columnas" name="report"
						property="property(SPAC_CT_INFORMES:COLUMNAS)" />
					<logic:present parameter="tipo">
						<bean:parameter id="tipo" name="tipo"/>
						<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=select&id="+idReportCT+"&filas=" + filas + "&columnas=" + columnas +"&tipo="+tipo%>'/>');">
							<ispac:documenticon imageSrc="img/docs/" extension="pdf"
								styleClass="imgTextBottom" />
							<bean:write name="report"
								property="property(SPAC_CT_INFORMES:NOMBRE)" />
		
						</a>
					</logic:present>
					<logic:notPresent parameter="tipo">
					<a href="javascript:executeReport('<html:rewrite action='<%="report.do?method=select&id="+idReportCT+"&filas=" + filas + "&columnas=" + columnas%>'/>');">
							<ispac:documenticon imageSrc="img/docs/" extension="pdf"
								styleClass="imgTextBottom" />
							<bean:write name="report"
								property="property(SPAC_CT_INFORMES:NOMBRE)" />
		
						</a>
					</logic:notPresent>
					
	
				</logic:notEmpty>
			</logic:empty>
		</logic:present>


	</display:column>

	<display:column titleKey="report.nombre" headerClass="sortable"
		sortable="true" group="1" media="csv xml excel pdf">
		<logic:notEmpty name="report" property="property(NOMBRE)">
			<bean:write name="report" property="property(NOMBRE)" />
		</logic:notEmpty>
		<logic:notEmpty name="report"
			property="property(SPAC_CT_INFORMES:NOMBRE)">
			<bean:write name="report"
				property="property(SPAC_CT_INFORMES:NOMBRE)" />
		</logic:notEmpty>
	</display:column>

	<display:column titleKey="report.description" headerClass="sortable"
		sortable="true">
		<logic:notEmpty name="report" property="property(DESCRIPCION)">
			<bean:write name="report" property="property(DESCRIPCION)" />
		</logic:notEmpty>
		<logic:notEmpty name="report"
			property="property(SPAC_CT_INFORMES:DESCRIPCION)">
			<bean:write name="report"
				property="property(SPAC_CT_INFORMES:DESCRIPCION)" />
		</logic:notEmpty>
	</display:column>

</display:table>
</html:form>
</div>
<%--seccion ficha --%> 
</div>
<%--fin cuerpo ficha --%></div>
<%--fin  ficha --%>
<div><%--fin contenido --%> <script>


function  executeReport(action) {
			document.reportForm.action = action;
			document.reportForm.submit();
}
		
positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
	});
	

</script>