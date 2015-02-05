<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ page language="java" %>

<table cellpadding="2" cellspacing="0" width="100%" height="100%" border="0">
   <tr>
      <td>
        <html:form action="selectSearch.do" method="get">
        <logic:present name="Proc">
          <bean:define id="pcd_id" name="Proc" property="property(ID)"/>
          <html:hidden property="pcdId" value='<%= pcd_id.toString() %>'/>
        </logic:present>
        <table cellpadding="2" cellspacing="0" width="100%" border="0">
          <tr>
            <td colspan="2"><html:errors/></td>
          </tr>
          <tr>
            <td class="field" align="right" height="64px" valign="middle">
              <bean:message key="forms.title"/>
            </td>
             <td class="field" align="left">
              <html:select property="urlForm" styleClass="input" onchange="submit();">
                <logic:iterate id="form" name="formList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
	                <html:option value='<%= form.getProperty ("ID").toString() %>'><%= (String)form.getProperty ("DESCRIPCION") %></html:option>
	            </logic:iterate>
              </html:select>
             </td>
          </tr>
        </table>
        </html:form>
      </td>
   </tr>
</table>
