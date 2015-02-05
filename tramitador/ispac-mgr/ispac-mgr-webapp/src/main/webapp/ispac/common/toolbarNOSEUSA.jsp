<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
  <tr>
    <td>
      <table cellpadding="1" cellspacing="0" border="0" width="100%" class="title" height="100%">
        <tr>
        	<td><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
          <td width="69%">
            <table width="100%" border="0" cellspacing="0" cellpadding="1">
              <tr>
                <td class="title">
                	<logic:present name="menusToolbar">
                  	<ispac:menuBar id="menusTool" name="menusToolbar"></ispac:menuBar>
                  </logic:present>
              	</td>
              </tr>
            </table>
          </td>
          <logic:present name="Expedient">
          	<td>
          		<html:link action="showMilestone" name="Params" titleKey="header.milestone">
          			<img src='<ispac:rewrite href="img/hitos.gif"/>' width="32px" height="32px" border="0"/>
          		</html:link>
          	</td>
          	<td>
          		<html:link action="showTabExpedient" name="Params" titleKey="header.tabview">
          			<img src='<ispac:rewrite href="img/view.gif"/>' width="32px" height="32px" border="0"/>
          		</html:link>
          	</td>
          </logic:present>
          <td><img src='<ispac:rewrite href="img/pixel.gif"/>'/></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
