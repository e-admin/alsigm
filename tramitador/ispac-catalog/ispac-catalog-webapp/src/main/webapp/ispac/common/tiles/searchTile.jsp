<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<div id="buscador"><bean:message key="catalog.search.generic.containsText"/>:&nbsp;
		<input type="text" id="txtFiltro" class="input" size="40" maxlength="50" value='<%=(request.getParameter("filtro")!=null?request.getParameter("filtro"):"")%>'/>
		<html:button property="filtra" styleClass="form_button_white" onclick="javascript:redirect('/showCTEntitiesList.do?filtro=' + document.getElementById('txtFiltro').value)">
			<bean:message key="search.button"/>
		</html:button>&nbsp;&nbsp;
</div>