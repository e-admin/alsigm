--
-- Información de versión
--
DECLARE @sequence_id int
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_INFOSISTEMA';
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (@sequence_id, 'VERSIONBD', '6.1.1', getdate());
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_INFOSISTEMA';
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (@sequence_id, 'VERSIONAPP', '6.1.1', getdate());
GO


--
---Actualizacion regla publicación para el pago de tasas
---


UPDATE pub_reglas 
SET atributos= '<?xml version=''1.0'' encoding=''ISO-8859-1''?><attributes><attribute name=''TASA_1''><tax><name>Resguardo del pago de tasa</name><labels><label locale=''es''>Resguardo del pago de tasa</label><label locale=''eu''>Tasaren ordainagiria</label><label locale=''ca''>Protegeixo del pagament de taxa</label><label locale=''gl''>Resgardo do pago de taxa</label></labels><import>1000</import><sender_entity_id>000000</sender_entity_id><self_settlement_id>200</self_settlement_id></tax></attribute><attribute name=''MENSAJE_SUBSANACION''><labels><label locale=''es''>Documentos a subsanar: ${NOMBRE_DOC}.</label><label locale=''eu''>Konpontzera dokumentuak: ${NOMBRE_DOC}.</label><label locale=''ca''>Documents a esmenar: ${NOMBRE_DOC}.</label><label locale=''gl''>Documentos a emendar: ${NOMBRE_DOC}.</label></labels></attribute><attribute name=''MENSAJE_PAGO''><labels><label locale=''es''>Durante la tramitación de su expediente ${NUMEXP} de ${NOMBREPROCEDIMIENTO} se le comunica que es necesario que acredite el pago de la tasa de ${NOMBRE_PAGO} por un valor de ${IMPORTE_PAGO} euros.</label><label locale=''eu''>${NOMBREPROCEDIMIENTO}(e)ko ${NUMEXP} espedientea bideratzen ari dela, jakinarazten zaizu ${NOMBRE_PAGO}ren tasa (${IMPORTE_PAGO} eurokoa) ordaindu izana ziurtatu behar duzula.</label><label locale=''ca''>Durant la tramitacio del vostre expedient ${NUMEXP} de ${NOMBREPROCEDIMIENTO} se us comunica que acrediteu el pagament de la taxa de ${NOMBRE_PAGO} per un valor de ${IMPORTE_PAGO} euros.</label><label locale=''gl''>Durante a tramitación do seu expediente ${NUMEXP} de ${NOMBREPROCEDIMIENTO} comunicaráselle que é necesario que acredite o pagamento da taxa de ${NOMBRE_PAGO} por un valor de ${IMPORTE_PAGO} euros.</label></labels></attribute></attributes>'
WHERE id IN (
	SELECT id
	FROM pub_reglas 
	WHERE atributos LIKE '%<self_settlement_id>100</self_settlement_id>%');
GO
--
-- Actualización del formulario de búsqueda básico
--
UPDATE spac_ct_frmbusqueda set frm_bsq='<?xml version=''1.0'' encoding=''ISO-8859-1''?>
<?xml-stylesheet type=''text/xsl'' href=''SearchForm.xsl''?>
<queryform displaywidth=''95%'' defaultSort=''1''>
<!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA-->
<entity type=''main'' identifier=''1''>
<!--INICIO CUERPO BUSQUEDA-->
	<tablename>spac_expedientes</tablename>
	<description>DATOS DEL EXPEDIENTE</description>
	<field type=''query'' order=''01''>
		<columnname>ID_PCD</columnname>
		<description>search.form.procedimiento</description>
		<datatype>integer</datatype>
		<controltype size=''30''  maxlength=''30'' tipo=''validate'' table=''SPAC_P_PROCEDIMIENTOS'' field=''spac_expedientes:ID_PCD'' label=''NOMBRE''  value=''ID'' clause=''WHERE ESTADO IN (2,3) AND TIPO = 1''>text</controltype>
	</field>
	<field type=''query'' order=''04''>
		<columnname>NUMEXP</columnname>
		<description>search.form.numExpediente</description>
		<datatype>string</datatype>
		<operators>
		 	<operator><name>&gt;</name></operator>
			<operator><name>&lt;</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''30'' maxlength=''30''>text</controltype>
	</field>
	<field type=''query'' order=''05''>
		<columnname>ASUNTO</columnname>
		<description>search.form.asunto</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''30'' maxlength=''30''>text</controltype>
	</field>
	<field type=''query'' order=''06''>
		<columnname>NREG</columnname>
		<description>search.form.numRegistro</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''16'' maxlength=''16''>text</controltype>
	</field>
	<field type=''query'' order=''07''>
		<columnname>IDENTIDADTITULAR</columnname>
		<description>search.form.interesadoPrincipal</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''50'' maxlength=''50''>text</controltype> 
	</field>
	<field type=''query'' order=''08''>
		<columnname>NIFCIFTITULAR</columnname>
		<description>search.form.ifInteresadoPrincipal</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''16'' maxlength=''16''>text</controltype>
	</field>
	<field type=''query'' order=''09''>
		<columnname>FAPERTURA</columnname>
		<description>search.form.fechaApertura</description>
		<datatype>date</datatype> 
		<controltype size=''22'' maxlength=''22''>text</controltype>
	</field>
	<field type=''query'' order=''10''>
		<columnname>ESTADOADM</columnname>
		<description>search.form.estadoAdm</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''20'' maxlength=''20'' tipo=''validate'' table=''SPAC_TBL_004'' field=''spac_expedientes:ESTADOADM'' label=''SUSTITUTO'' value=''VALOR''>text</controltype>
	</field>
	<field type=''query'' order=''11''>     
		<columnname>HAYRECURSO</columnname>
		<description>search.form.recurso</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''2'' maxlength=''2''>text</controltype>
	</field>
	<field type=''query'' order=''12''>
		<columnname>CIUDAD</columnname>
		<description>search.form.ciudad</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype size=''50'' maxlength=''50''>text</controltype>
	</field>
		<field type=''query'' order=''13''>
		<columnname>DOMICILIO</columnname>
		<description>search.form.domicilio</description>
		<datatype>string</datatype>
		<operators>
			<operator><name>=</name></operator>
			<operator><name>&gt;</name></operator>
			<operator><name>&gt;=</name></operator>
			<operator><name>&lt;</name></operator>
			<operator><name>&lt;=</name></operator>
 			<operator><name>Contiene(Like)</name></operator>
		</operators>
		<controltype cols=''100'' rows=''5''>textarea</controltype>
	</field>
<!--FIN CUERPO BUSQUEDA-->
</entity>
<!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA-->

<!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA-->
<entity type=''secondary'' identifier=''52''>
	<tablename>spac_fases</tablename>
	<bindfield>NUMEXP</bindfield>
	<field type=''query'' order=''02''>
		<columnname>ID_FASE</columnname>
		<description>search.form.fases</description>
		<datatype>stringList</datatype>
        <binding>in (select ID FROM SPAC_P_FASES WHERE ID_CTFASE IN(@VALUES))</binding>
		<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_FASES'' field=''spac_fases:ID_FASE'' label=''NOMBRE''  value=''id''>text</controltype>	
	</field>
</entity>
<entity type=''secondary'' identifier=''51''>
	
	<tablename>spac_tramites</tablename>
		<field type=''query'' order=''03''>
			<columnname>ID_TRAMITE</columnname>
			<description>search.form.tramites</description>
			<datatype>stringList</datatype>
     		<binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>
			<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_TRAMITES'' field=''spac_tramites:ID_TRAMITE'' label=''NOMBRE''  value=''id''>text</controltype>
		</field>
	<bindfield>NUMEXP</bindfield>
</entity>
<!--FIN SEGUNDA ENTIDAD DE BUSQUEDA-->
<!--INICIO CUERPO RESULTADO-->
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_FASES.ID'' readOnly=''false'' dataType=''string'' fieldType=''CHECKBOX'' fieldLink=''SPAC_FASES.ID'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NUMEXP'' readOnly=''false'' dataType=''string'' titleKey=''search.numExp'' fieldType=''LINK'' fieldLink=''SPAC_EXPEDIENTES.NUMEXP'' comparator=''ieci.tdw.ispac.ispacweb.comparators.NumexpComparator'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NREG'' readOnly=''false'' dataType=''string'' titleKey=''search.numRegistro'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NREG'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.interesado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.nifInteresado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.FAPERTURA'' readOnly=''false'' dataType=''date'' titleKey=''search.fechaApertura'' fieldType=''DATE'' fieldLink=''SPAC_EXPEDIENTES.FAPERTURA'' />
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.ESTADOADM'' readOnly=''false'' dataType=''string'' titleKey=''search.estadoAdministrativo'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.ESTADOADM'' validatetable=''SPAC_TBL_004'' substitute=''SUSTITUTO'' showproperty=''SPAC_TBL_004.SUSTITUTO''  value=''VALOR''/>
<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.HAYRECURSO'' readOnly=''false'' dataType=''string'' titleKey=''search.recurrido'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.HAYRECURSO'' />
<!--FIN CUERPO RESULTADO-->
<!--INICIO ACCIONES-->
<action title=''Cerrar expedientes'' path=''/closeProcess.do'' titleKey=''ispac.action.expedients.close'' />
<action title=''Avanzar fases'' path=''/closeStage.do'' titleKey=''ispac.action.stages.next'' />
<action title=''Retroceder fases'' path=''/redeployPreviousStage.do'' titleKey=''ispac.action.stages.return'' />
<!--FIN ACCIONES-->
</queryform>' where id=1;
GO

--
--Formulario de expedientes
---
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script>

	function acceptRegistryInput(){
	
		setReadOnly(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NREG)'' ]);
		
		if (document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)''].value != '''') {
			
			checkRadioById(''validatedThirdParty'');
			setReadOnly(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NIFCIFTITULAR)'' ]);
			setReadOnlyIdentidad(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)'' ]);
		}
		else {
			checkRadioById(''notValidatedThirdParty'');
			setNotReadOnly(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NIFCIFTITULAR)'' ]);
			setNotReadOnlyIdentidad(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)'' ]);
		}
		hideInfo();
		

}

	function cancelRegistryInput(){
	
		hideInfo();
	}
	
	function delete_EXPEDIENT_SEARCH_INPUT_REGISTRY(){ 
	
	  jConfirm(''<bean:message key="msg.delete.data.register"/>'', ''<bean:message key="common.confirm"/>'' , ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'', function(r) {
		if(r){
			nreg = document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NREG)'' ];
	 		setNotReadOnly(nreg);
	 		nreg.value = '''';
	 		nreg.focus();

			document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:FREG)'' ].value = '''';
		}
							
	});	
	
		
	 	ispac_needToConfirm = true;
	}


	

</script>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE)" />
</nobr>
</td>
<td width="5px"><img height="1" width="5px" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
<td class="unselect" id="tdlink2" align="center" onclick="document.defaultForm.name = ''Expedientes'';if (validateExpedientes(document.defaultForm)){showTab(2);}">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE" style="position: relative; height: 710px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NUMEXP)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 20px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:NUMEXP)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="30" tabindex="101">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 54px; left: 391px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FINICIOPLAZO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 51px; left: 505px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FINICIOPLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="103">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 149px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NREG)" />:</nobr>
</div>

<div id="data_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 146px; left: 170px;">
</nobr>
<c:choose>
<c:when test="${!empty sicresConnectorClass}">
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NREG)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="25"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NREG"
				  target="workframe"
			  	  action="searchInputRegistry.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.intray" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NREG"
			  	  width="500"
			  	  height="300"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_INPUT_REGISTRY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.register" tabindex="105">
		
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:ASUNTO)" property="ASUNTO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:FREG)" property="FREG"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="JAVASCRIPT" property="accept_EXPEDIENT_SEARCH_INPUT_REGISTRY"/>
		
	</ispac:htmlTextImageFrame>
</c:when>
<c:otherwise>
	<ispac:htmlText property="property(SPAC_EXPEDIENTES:NREG)" readonly="false" styleClass="input" size="25" maxlength="16" tabindex="105"/>
</c:otherwise>
</c:choose>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 151px; left: 392px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 148px; left: 505px;">
</nobr>
<c:choose>
<c:when test="${!empty sicresConnectorClass}">
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</c:when>
<c:otherwise>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</c:otherwise>
</c:choose>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL" style="position: absolute; top: 285px; left: 10px; width: 620px" class="textbar">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL)" /></nobr>
<hr class="formbar"/>
</div>
<c:url value="searchThirdParty.do" var="_searchThirdParty">
	<c:param name="search">1</c:param>
</c:url>

<jsp:useBean id="_searchThirdParty" type="java.lang.String"/>
<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
</nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NIFCIFTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" 
				  tabindex="112"
				  >

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="JAVASCRIPT" property="return_EXPEDIENT_SEARCH_THIRD_PARTY"/>
		
	</ispac:htmlTextImageFrame>

		<ispac:imageframe 
					  id="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do" 
					  image="img/icon_barra_3.gif" 
					  showFrame="true"
					  inputField="SPAC_EXPEDIENTES:IDTITULAR"
					  jsShowFrame="show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" 
					  >
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>
		<ispac:imageframe 
					  id="SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do" 
					  image="img/icon_barra_2.gif" 
					  showFrame="true"
					  inputField="SPAC_EXPEDIENTES:IDTITULAR"
					  jsShowFrame="show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS" 
					  >
					  
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:DOMICILIO)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{jAlert(''<bean:message key="terceros.interested.notSelected"/>'' ,  ''<bean:message key="common.alert"/>'' , ''<bean:message key="common.message.ok"/> '', ''<bean:message key="common.message.cancel"/>'');}
				}

			}
			
			function show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{jAlert(''<bean:message key="terceros.interested.notSelected"/>'', ''<bean:message key="common.alert"/>'' , ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'');}
			}
			//-->
		</script>



</nobr>
</div>


<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>

</c:when>
<c:otherwise>
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
	<ispac:htmlText property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" readonly="false" styleClass="input" size="34" maxlength="16" tabindex="112"/>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>''
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:IDENTIDADTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="113"
				  >
		

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
	</ispac:htmlTextareaImageFrame>
</div>

<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" tabindex="110" disabled="true"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" checked="checked" disabled="true" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>



</c:otherwise>
</c:choose>

<div id="label_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 480px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DOMICILIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 477px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:DOMICILIO)" readonly="true" propertyReadonly="readonly" styleClass="textareaDir" styleClassReadonly="textareaDirRO" rows="2" cols="85" tabindex="114" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 514px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CIUDAD)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 511px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CIUDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="64" tabindex="115">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 547px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:REGIONPAIS)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 544px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:REGIONPAIS)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="117">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 514px; left: 370px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CPOSTAL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 511px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CPOSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="5" tabindex="116">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>''
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:IDENTIDADTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="113"
				  >
		

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
	</ispac:htmlTextareaImageFrame>
</div>
<div id="label_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 690px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ROLTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 687px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ROLTITULAR)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="122">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(SPAC_EXPEDIENTES:ROLTITULAR)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 83px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ASUNTO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 80px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="textareaAsunto" styleClassReadonly="textareaAsuntoRO" rows="4" cols="85" tabindex="104">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 181px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FORMATERMINACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 178px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:FORMATERMINACION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_003" image="img/search-mg.gif" titleKeyLink="select.termination" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="107">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(SPAC_EXPEDIENTES:FORMATERMINACION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 214px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ESTADOADM)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 211px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ESTADOADM)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_004" image="img/search-mg.gif" titleKeyLink="select.estadoadm" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="108">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(SPAC_EXPEDIENTES:ESTADOADM)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 52px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FAPERTURA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 49px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FAPERTURA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="102">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 655px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 652px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="121">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 247px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SECCIONINICIADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 244px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:SECCIONINICIADORA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="250" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 583px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOFIJO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 580px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOFIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="118">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 583px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOMOVIL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 580px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOMOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="119">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 620px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 617px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="120">
</ispac:htmlText>
</div>
</div>
</div>

<!-- BLOQUE2 DE CAMPOS -->
<div style="display:none" id="block2">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL" style="position: relative; height: 280px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:UTRAMITADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 20px; left: 170px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:UTRAMITADORA)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="50"
			  	  id="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA"
				  target="workframe"
			  	  action="viewUsersManager.do?captionkey=select.unidadTramitadora&noviewusers=true&noviewgroups=true"
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="select.unidadTramitadora" 
			  	  showFrame="true" tabindex="201">
						  	  
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA" id="property(SPAC_EXPEDIENTES:UTRAMITADORA)" property="NAME"/>
		
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 58px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FCIERRE)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 55px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FCIERRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="202">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 58px; left: 306px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:HAYRECURSO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 55px; left: 364px;">
<ispac:htmlCheckbox property="property(SPAC_EXPEDIENTES:HAYRECURSO)" readonly="false" propertyReadonly="readonly" valueChecked="SI" styleClassCheckbox="inputSelectP" tabindex="203"></ispac:htmlCheckbox>
</div>
<div id="label_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 93px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:OBSERVACIONES)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 90px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="204">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_TERRITORIO" style="position: absolute; top: 140px; left: 10px; width: 620px" class="textbar">
<nobr><bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_TERRITORIO)" />
<hr class="formbar"/>
</div>
<div id="label_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 183px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:POBLACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 180px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:POBLACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="205">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 213px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:MUNICIPIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 210px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:MUNICIPIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="206">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LOCALIZACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 240px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:LOCALIZACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="256" tabindex="207">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = frm_version + 1
WHERE id = 1;

GO

---
---Formulario de intervinientes
----

UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>
<c:url value="searchThirdParty.do" var="_searchThirdParty">
	<c:param name="search">1</c:param>
</c:url>

<jsp:useBean id="_searchThirdParty" type="java.lang.String"/>
<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE" style="position: relative; height: 400px; width: 600px;">

<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_DT_INTERVINIENTES:NDOC)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="THIRD_SEARCH_THIRD_PARTY"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="false"
			  	  inputField="SPAC_DT_INTERVINIENTES:NDOC"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.participantes.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" 
				  tabindex="112"
				  >

		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="JAVASCRIPT" property="return_THIRD_SEARCH_THIRD_PARTY"/>
		
	</ispac:htmlTextImageFrame>

		<ispac:imageframe 
					  id="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do" 
					  image="img/icon_barra_3.gif" 
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" 
					  >
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>

		<ispac:imageframe 
					  id="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do" 
					  image="img/icon_barra_2.gif" 
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" 
					  >
					  
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_DT_INTERVINIENTES:DIRNOT)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{jAlert(''<bean:message key="terceros.thirdnotSelected"/>'' , ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'', ''<bean:message key="common.message.cancel"/>'');}
				}
			}
			
			function show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{jAlert(''<bean:message key="terceros.thirdnotSelected"/>'', ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'', ''<bean:message key="common.message.cancel"/>'');}
			}
			//-->
		</script>
</nobr>






</div>


<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_DT_INTERVINIENTES:NOMBRE)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="THIRD_SEARCH_THIRD_PARTY_NOMBRE"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>'' 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="true"
			  	  inputField="SPAC_DT_INTERVINIENTES:NOMBRE"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="104"
				  >
		
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NDOC)" property="NIFCIFTITULAR"/>
	
	</ispac:htmlTextareaImageFrame>
</div>
</c:when>
<c:otherwise>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
	<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:NDOC)" readonly="false" styleClass="input" size="30" maxlength="12" tabindex="103"/>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_DT_INTERVINIENTES:NOMBRE)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="THIRD_SEARCH_THIRD_PARTY_NOMBRE"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>'' 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="true"
			  	  inputField="SPAC_DT_INTERVINIENTES:NOMBRE"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="104"
				  >
		
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NDOC)" property="NIFCIFTITULAR"/>
	
	</ispac:htmlTextareaImageFrame>
</div>
</c:otherwise>
</c:choose>


<div id="label_SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES" style="position: absolute; top: 103px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_LIBRE)" /></nobr>
</div>






<div id="label_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 371px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:ROL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 368px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:ROL)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROL_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_DT_INTERVINIENTES_ROL" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="113">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(SPAC_DT_INTERVINIENTES:ROL)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(ROL_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 153px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRNOT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 150px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:DIRNOT)" readonly="true" propertyReadonly="readonly" styleClass="textareaDir" styleClassReadonly="textareaDirRO" rows="2" cols="80" tabindex="105">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 240px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:C_POSTAL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 237px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" tabindex="108">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 189px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LOCALIDAD)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 186px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 216px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:CAUT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 213px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:CAUT)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="107">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 267px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 264px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 293px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 290px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="110">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 345px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 342px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="35" id="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="112">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 319px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 316px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="111">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = frm_version + 1
WHERE id = 3;
GO

--
-- Formulario 19: frm_fase_instruccion_cerrado
--
update spac_ct_aplicaciones set frm_jsp='<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 480px; width: 600px;">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="5" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 130px; height: 19px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 196px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 194px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 169px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 168px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 169px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 287px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 286px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 286px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 142px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 141px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 115px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 114px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 317px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CLASIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 316px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CLASIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=OBRA_MENOR" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(OBRA_MENOR:CLASIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 342px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CUALIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 341px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CUALIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(OBRA_MENOR:CUALIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 389px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_TASAS)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 388px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 388px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 414px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_ICIO)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 413px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_ICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 413px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 439px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:TOTAL_LIQUIDACION)" />:</div>
<div id="data_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 438px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:TOTAL_LIQUIDACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 438px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>', frm_version=frm_version+1 where id=19;
GO

--
-- Formulario 20: frm_fase_inicio
--
update spac_ct_aplicaciones set frm_jsp='<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 280px; width: 600px;">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="5" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 130px; height: 19px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 196px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 194px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 169px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 168px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 169px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 142px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 141px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 115px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 114px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', frm_version=frm_version+1 where id=20;
GO

--
-- Formulario 21: FrmCrEnt_OBRA_MENOR
--
update spac_ct_aplicaciones set frm_jsp='<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_OBRA_MENOR" style="position: relative; height: 480px; width: 600px;">
<div id="label_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_SOLICITADA)" />:</div>
<div id="data_OBRA_MENOR:OBRA_SOLICITADA" style="position: absolute; top: 6px; left: 130px;">
<ispac:htmlTextarea property="property(OBRA_MENOR:OBRA_SOLICITADA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="5" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:SITUACION_OBRA)" />:</div>
<div id="data_OBRA_MENOR:SITUACION_OBRA" style="position: absolute; top: 85px; left: 130px; height: 19px;">
<ispac:htmlText property="property(OBRA_MENOR:SITUACION_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 196px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PROMOTOR" style="position: absolute; top: 194px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_OBRA)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_OBRA" style="position: absolute; top: 217px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:DIRECTOR_EJECUCION)" />:</div>
<div id="data_OBRA_MENOR:DIRECTOR_EJECUCION" style="position: absolute; top: 241px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:DIRECTOR_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 169px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_PROMOTOR" style="position: absolute; top: 168px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_PROMOTOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 169px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 287px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" />:</div>
<div id="data_OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL" style="position: absolute; top: 286px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:PRESUPUESTO_TÉCNICO_MUNICIPAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 286px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 142px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:AUTOR_PROYECTO)" />:</div>
<div id="data_OBRA_MENOR:AUTOR_PROYECTO" style="position: absolute; top: 141px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:AUTOR_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="100" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 115px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:USO_OBRA)" />:</div>
<div id="data_OBRA_MENOR:USO_OBRA" style="position: absolute; top: 114px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:USO_OBRA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="200" >
</ispac:htmlText>
</div>
<div id="label_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 317px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CLASIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CLASIFICACION_SUELO" style="position: absolute; top: 316px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CLASIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=OBRA_MENOR" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(OBRA_MENOR:CLASIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CLASIFICACION_SUELO" id="property(CLASIFICACION_SUELO_VLDTBL_CLASIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 342px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:CUALIFICACION_SUELO)" />:</div>
<div id="data_OBRA_MENOR:CUALIFICACION_SUELO" style="position: absolute; top: 341px; left: 130px;">
<nobr>
<html:hidden property="property(OBRA_MENOR:CUALIFICACION_SUELO)" />
<ispac:htmlTextImageFrame property="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" target="workframe" action="selectSubstitute.do?entity=" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(OBRA_MENOR:CUALIFICACION_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_OBRA_MENOR_CUALIFICACION_SUELO" id="property(CUALIFICACION_SUELO_VLDTBL_CALIF_SUELO:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 389px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_TASAS)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_TASAS" style="position: absolute; top: 388px; left: 130px; width: 78px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_TASAS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 388px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 414px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:IMPORTE_ICIO)" />:</div>
<div id="data_OBRA_MENOR:IMPORTE_ICIO" style="position: absolute; top: 413px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:IMPORTE_ICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 413px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
<div id="label_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 439px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:TOTAL_LIQUIDACION)" />:</div>
<div id="data_OBRA_MENOR:TOTAL_LIQUIDACION" style="position: absolute; top: 438px; left: 130px;">
<ispac:htmlText property="property(OBRA_MENOR:TOTAL_LIQUIDACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="18" >
</ispac:htmlText>
</div>
<div style="position: absolute; top: 438px; left: 225px; width: 110px;" class="formsTitleB">
&nbsp;&#8364;</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', frm_version=frm_version+1 where id=21;
GO

--
-- Formulario 25: Contrato
--
update spac_ct_aplicaciones set frm_jsp='<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:CONTRATO)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_CONTRATO" style="position: relative; height: 335px; width: 600px;">
<div id="label_CONTRATO:TIPO" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:TIPO)" />:</div>
<div id="data_CONTRATO:TIPO" style="position: absolute; top: 10px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONTRATO:TIPO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" id="SEARCH_CONTRATO_TIPO" target="workframe" action="selectValue.do?entity=TIPO_CONTRATO" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONTRATO_TIPO" id="property(CONTRATO:TIPO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONTRATO:FORMA_ADJUDICACION" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FORMA_ADJUDICACION)" />:</div>
<div id="data_CONTRATO:FORMA_ADJUDICACION" style="position: absolute; top: 35px; left: 130px;">
<nobr>
<ispac:htmlTextImageFrame property="property(CONTRATO:FORMA_ADJUDICACION)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" id="SEARCH_CONTRATO_FORMA_ADJUDICACION" target="workframe" action="selectValue.do?entity=FORMA_ADJUDICACION" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONTRATO_FORMA_ADJUDICACION" id="property(CONTRATO:FORMA_ADJUDICACION)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONTRATO:PRESUPUESTO_MAXIMO" style="position: absolute; top: 63px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:PRESUPUESTO_MAXIMO)" />:</div>
<div id="data_CONTRATO:PRESUPUESTO_MAXIMO" style="position: absolute; top: 68px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:PRESUPUESTO_MAXIMO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:APLICACION" style="position: absolute; top: 95px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:APLICACION)" />:</div>
<div id="data_CONTRATO:APLICACION" style="position: absolute; top: 95px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:APLICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:PRECIO_ADJUDICACION" style="position: absolute; top: 63px; left: 310px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:PRECIO_ADJUDICACION)" />:</div>
<div id="data_CONTRATO:PRECIO_ADJUDICACION" style="position: absolute; top: 68px; left: 430px;">
<ispac:htmlText property="property(CONTRATO:PRECIO_ADJUDICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:PLAZO_EJECUCION" style="position: absolute; top: 126px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:PLAZO_EJECUCION)" />:</div>
<div id="data_CONTRATO:PLAZO_EJECUCION" style="position: absolute; top: 126px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:PLAZO_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="10" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:UD_PLAZO" style="position: absolute; top: 126px; left: 261px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:UD_PLAZO)" />:</div>
<div id="data_CONTRATO:UD_PLAZO" style="position: absolute; top: 126px; left: 381px;">
<nobr>
<html:hidden property="property(CONTRATO:UD_PLAZO)" />
<ispac:htmlTextImageFrame property="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" id="SEARCH_CONTRATO_UD_PLAZO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_001" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_CONTRATO_UD_PLAZO" id="property(CONTRATO:UD_PLAZO)" property="VALOR" />
<ispac:parameter name="SEARCH_CONTRATO_UD_PLAZO" id="property(UD_PLAZO_SPAC_TBL_001:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_CONTRATO:GARANTIA_PROVISIONAL" style="position: absolute; top: 154px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:GARANTIA_PROVISIONAL)" />:</div>
<div id="data_CONTRATO:GARANTIA_PROVISIONAL" style="position: absolute; top: 154px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:GARANTIA_PROVISIONAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:GARANTIA_DEFINITIVA" style="position: absolute; top: 154px; left: 312px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:GARANTIA_DEFINITIVA)" />:</div>
<div id="data_CONTRATO:GARANTIA_DEFINITIVA" style="position: absolute; top: 154px; left: 430px; width: 99px;">
<ispac:htmlText property="property(CONTRATO:GARANTIA_DEFINITIVA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="26" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:CLASIFICACION" style="position: absolute; top: 185px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:CLASIFICACION)" />:</div>
<div id="data_CONTRATO:CLASIFICACION" style="position: absolute; top: 185px; left: 130px;">
<ispac:htmlText property="property(CONTRATO:CLASIFICACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="500" >
</ispac:htmlText>
</div>
<div id="label_CONTRATO:FECHA_FIN_EJECUCION" style="position: absolute; top: 210px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FECHA_FIN_EJECUCION)" />:</div>
<div id="data_CONTRATO:FECHA_FIN_EJECUCION" style="position: absolute; top: 215px; left: 130px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONTRATO:FECHA_FIN_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 222px; left: 313px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FECHA_FIN_GARANTIA)" />:</div>
<div id="data_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 215px; left: 433px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONTRATO:FECHA_FIN_GARANTIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', frm_version=frm_version+1 where id=25;
GO

--
-- Formulario 24: Lotes
--
update spac_ct_aplicaciones set frm_jsp='<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(LOTES:LOTES)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_LOTES" style="position: relative; height: 85px; width: 600px">
<div id="label_LOTES:NOMBRE" style="position: absolute; top: 10px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(LOTES:NOMBRE)" />:</div>
<div id="data_LOTES:NOMBRE" style="position: absolute; top: 10px; left: 130px;">
<ispac:htmlText property="property(LOTES:NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="250" >
</ispac:htmlText>
</div>
<div id="label_LOTES:PRESUPUESTO" style="position: absolute; top: 35px; left: 10px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(LOTES:PRESUPUESTO)" />:</div>
<div id="data_LOTES:PRESUPUESTO" style="position: absolute; top: 35px; left: 130px;">
<ispac:htmlText property="property(LOTES:PRESUPUESTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="26" >
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>
', frm_version=frm_version+1 where id=24;
GO
