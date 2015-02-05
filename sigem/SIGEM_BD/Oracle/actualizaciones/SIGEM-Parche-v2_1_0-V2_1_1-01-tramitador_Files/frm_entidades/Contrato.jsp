 <%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
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
<td><img height="8" src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
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
<ispac:htmlTextCalendar property="property(CONTRATO:FECHA_FIN_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 222px; left: 313px; width: 110px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(CONTRATO:FECHA_FIN_GARANTIA)" />:</div>
<div id="data_CONTRATO:FECHA_FIN_GARANTIA" style="position: absolute; top: 215px; left: 433px;">
<nobr>
<ispac:htmlTextCalendar property="property(CONTRATO:FECHA_FIN_GARANTIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image='<%= buttoncalendar %>' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
</tr>
</table>