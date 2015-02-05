<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<table cellpadding="5" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="1" border="0" width="100%">
			
				<tr>
	  				<td>
						<logic:present name="menus">
							<ispac:menuBar id="menu" name="menus" position="cols" styleLevel0="menu0" styleLevel1="menu1" padding="4px"></ispac:menuBar>
						</logic:present>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>