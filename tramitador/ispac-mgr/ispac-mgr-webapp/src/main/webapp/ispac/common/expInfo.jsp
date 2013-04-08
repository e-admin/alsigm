<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<bean:define id="exp" name="Expedient" type="ieci.tdw.ispac.ispaclib.bean.ItemBean"/>
<table cellpadding="0" cellspacing="1" border="0" class="box" width="100%">
	<tr>
		<td class="title" height="18px" width="100%">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/></td>
  				<td width="100%" class="menuhead"><bean:message key="scheme.info.title"/></td>
				</tr>
			</table>
		</td>
	</tr>
  	<tr>
  	  <td class="blank">
  	  	<table cellpadding="1" cellspacing="1" border="0" width="100%">
  	  	  <logic:iterate name="FormatterInfo" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
  	  	  <tr>
  	  	  	<td class="schema"><bean:write name="format" property="title"/></td>
  	  	  	<td class="menu">
       	  		<%=format.formatProperty(exp)%>
  	  	  	</td>
  	  	  </tr>
  	  	  </logic:iterate>
  	  	</table>
  	  </td>
  	</tr>
</table>