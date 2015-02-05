<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<table cellpadding="5" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
   		<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
				<tr>
					<td class="title" height="18px">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
								<td width="100%" class="menuhead">
									<logic:iterate name="ContextHeaderList" id="contextHeader">
										<bean:write name="contextHeader" property="property(NAME)"/>
									</logic:iterate>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="blank">
            <logic:iterate id="stage" name="StagesList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
              			<table cellpadding="1" cellspacing="0" border="0" width="100%">
						   	<tr>
							  	<td class="menu">
							  	  <img src='<ispac:rewrite href="img/pixel.gif"/>' align="absmiddle" border="0" width="5px">
							      <img src='<ispac:rewrite href="img/stage.gif"/>' align="absmiddle" border="0">
							      <img src='<ispac:rewrite href="img/pixel.gif"/>' align="absmiddle" border="0" width="3px">
                				<html:link styleClass="menu" action="showProcessList.do" paramId="stagePcdId" paramName="stage" paramProperty="property(ID_FASE)">
                					<bean:write name="stage" property="property(NOMBRE)"/>&nbsp;(<bean:write name="stage" property="property(COUNT)"/>)
                				</html:link>
							    </td>
						   	</tr>
					   	</table>
            </logic:iterate>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>