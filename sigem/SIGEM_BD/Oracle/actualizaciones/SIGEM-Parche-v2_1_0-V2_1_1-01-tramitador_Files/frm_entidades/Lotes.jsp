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