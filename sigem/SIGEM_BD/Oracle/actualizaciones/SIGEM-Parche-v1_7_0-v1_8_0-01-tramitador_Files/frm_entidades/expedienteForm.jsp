<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE)" />
</nobr>
</td>
<td width="5px"><img height="1" width="5px" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
<td class="unselect" id="tdlink2" align="center" onclick="showTab(2)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
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
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FINICIOPLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" tabindex="103">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 149px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 146px; left: 170px;">
<nobr>
<%--
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
--%>

<ispac:htmlText property="property(SPAC_EXPEDIENTES:NREG)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="16" tabindex="105">
</ispac:htmlText>

</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 151px; left: 392px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 148px; left: 505px;">
<nobr>
<%--
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
--%>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL" style="position: absolute; top: 285px; left: 10px; width: 620px" class="textbar">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL)" /></nobr>
<hr class="formbar"/>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript:clickValidatedThirdParty();enableSearchPostal();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript:clickNotValidatedThirdParty();disableSearchPostal();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
<nobr>
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

		<script language='JavaScript' type='text/javascript'><!--
			function show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ 'property(SPAC_EXPEDIENTES:DOMICILIO)' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ];
					if (idtitular.value != '') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{alert('<bean:message key="terceros.interested.notSelected"/>');}
				}

			}
			
			function show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ 'property(SPAC_EXPEDIENTES:IDTITULAR)' ];
				if (idtitular.value != '') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{alert('<bean:message key="terceros.interested.notSelected"/>');}
			}
			//-->
		</script>



</nobr>
</div>


<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>





<div id="label_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 480px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DOMICILIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 477px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:DOMICILIO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="114" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 514px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CIUDAD)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 511px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CIUDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="64" tabindex="115">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 547px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:REGIONPAIS)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 544px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:REGIONPAIS)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="117">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 514px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CPOSTAL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 511px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CPOSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="5" tabindex="116">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="113">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 690px; left: 10px;" class="formsTitleB">
<nobr>
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
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="4" cols="85" tabindex="104">
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
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FAPERTURA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" tabindex="102">
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
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FCIERRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" tabindex="202">
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
<td height="15"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
</tr>
</table>