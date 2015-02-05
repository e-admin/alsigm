<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<div id="navmenutitle">
	<bean:message key="catalog.exportpcd"/>
</div>

<%--
<div id="helpLink">
	<ispac:onlinehelp fileName="newProcedure" image="img/help.gif" titleKey="title.help"/>
</div>
--%>

<div id="navSubMenuTitle">
	<bean:message key="procedure.export.subtitle"/>
</div>

<div id="navmenu">
	<ul class="actionsMenuList">
		<li>
			<a href="javascript:parent.hideFrame('workframe','<ispac:rewrite page="wait.jsp"/>')">
				<bean:message key="forms.button.cancel"/>
			</a>
		</li>
		<li>
			<a href="javascript:ispac_submit('<%= request.getContextPath() + "/exportProcedure.do?method=export"%>')">
				<bean:message key="forms.button.accept"/>
			</a>
		</li>
	</ul>
</div>

<html:form action="exportProcedure.do?method=export">

	<html:hidden property="pcdId"/>

	<div id="formErrors">
		<html:errors />
	</div>

	<div id="stdform">
		<table border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td height="20" class="formsTitle" width="1%">
				</td>
				<td height="20">
				</td>
			</tr>

		</table>
	</div>

</html:form>

