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
<bean:write name="defaultForm" property="entityApp.label(OBRA_MENOR:OBRA_MENOR)" />
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
<td height="15"><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
</tr>
</table>