<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script language='JavaScript' type='text/javascript'><!--

	function noSelectedResources(url) {
	
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.resource.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			document.forms['defaultForm'].action = url;
			document.forms['defaultForm'].submit();
		}	
	}
	
//--></script>

<c:set var="action">
	   <c:out value="${requestScope['ActionDestino']}" default="/showCTEntityToManage.do"/>
</c:set>
	
<jsp:useBean id="action" type="java.lang.String"/>
<html:form action='showCTEntityToManage.do?method=resources'>
	
	<%--MENU--%>	
	<div id="navmenu">
		<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
		<ul class="actionsMenuList">
			<li>
				<a href=javascript:ispac_submit('<c:url value="${action}?method=saveResources"/>')>
					<nobr>
						<bean:message key="forms.button.save"/>
					</nobr>
				</a>
			</li>
		</ul>
		</ispac:hasFunction>
	</div>	
	
	<%--SELECCION DEL IDIOMA--%>
	<div id="entityManage.label.selectLanguage">
		<h4>
			<ispac:tooltipLabel labelKey="entityManage.resources.label.selectLanguage" tooltipKey="entityManage.resources.label.selectLanguage.tooltip"/>
		</h4>
	</div>

	<table>
		<tr>
			<td>
				<html:select property="property(languageSelected)" onchange="javascript:ispac_submit('showCTEntityToManage.do?method=resources')">	
					<html:options collection="languages" property="property(CLAVE)" labelProperty="property(IDIOMA)" />
				</html:select>	
			</td>
		</tr>			
	</table>	
	
	<!-- NOMBRE DE LA ENTIDAD -->
	<div>
		<h4>
			<ispac:tooltipLabel labelKey="entityManage.resources.label.entity" tooltipKey="entityManage.resources.label.entity.tooltip"/>
		</h4>
	</div>
	
	<display:table name="entity" 
				   export="false" 
				   class="tableDisplay"
				   requestURI='<%=action%>'
				   uid='uid_entity'>
				   
			 <bean:define id='itemobj' name='uid_entity' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
		
			 <logic:iterate name="ResourcesEntityListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="CLAVE">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>

						<%=format.formatProperty(itemobj)%>
						
					</display:column>
				</logic:equal>
				
				<logic:equal name="format" property="fieldType" value="VALOR">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>
									
						<bean:define id="clave" name="itemobj" property="property(CLAVE)"/>
						<c:set var="propertyValue"><%=format.formatProperty(itemobj)%></c:set>
						
						<input type="text" class="input" size="80" maxlength="250" name="property(<%=clave%>)" value='<c:out value="${propertyValue}" escapeXml="true"/>'/>

					</display:column>
					
				</logic:equal>
				
			</logic:iterate>
			
	</display:table>
	
	<!-- CAMPOS DE LA ENTIDAD -->
	<div id="entityManage.label.listFields">
		<h4>
			<ispac:tooltipLabel labelKey="entityManage.resources.label.fields" tooltipKey="entityManage.resources.label.fields.tooltip"/>
		</h4>
	</div>	
		
	<display:table name="listFields" 
				   export="false" 
				   class="tableDisplay"
				   requestURI='<%=action%>'
				   uid='uid_listFields'>
				   
			 <bean:define id='itemobj' name='uid_listFields' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
		
			 <logic:iterate name="ResourcesFieldsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="CLAVE">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>

						<%=format.formatProperty(itemobj)%>
						
					</display:column>
					
				</logic:equal>
				
				<logic:equal name="format" property="fieldType" value="VALOR">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>

						<bean:define id="clave" name="itemobj" property="property(CLAVE)"/>
						<c:set var="propertyValue"><%=format.formatProperty(itemobj)%></c:set>
						
						<input type="text" class="input" size="100" maxlength="250" name="property(<%=clave%>)" value='<c:out value="${propertyValue}" escapeXml="true"/>'/>

					</display:column>
					
				</logic:equal>
				
			</logic:iterate>
			
	</display:table>
		
	<!-- OTROS RECURSOS -->
	<div id="entityManage.label.listOthersFields">
		<h4>
			<ispac:tooltipLabel labelKey="entityManage.resources.label.others" tooltipKey="entityManage.resources.label.others.tooltip"/>
		</h4>
	</div>	
	
	<div id="navmenu">
		<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
		<ul class="actionsMenuList">
			<li>
				<a href="javascript:showFrame('workframe','<c:url value="${action}?method=newOtherResource"/>',640,480)">
					<nobr>
						<bean:message key="forms.button.add"/>
					</nobr>
				</a>
			</li>				
			<c:choose>
				<c:when test="${!empty requestScope['listOthersFields']}">
					<li>
						<a href=javascript:noSelectedResources('<c:url value="${action}?method=deleteOthersResources"/>') >
							<nobr>
								<bean:message key="forms.button.delete"/>
							</nobr>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li style="background-color: #ddd; color: #aaa; cursor: default;">
						<nobr>
							<bean:message key="forms.button.delete"/>
						</nobr>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
		</ispac:hasFunction>
	</div>	

	<display:table name="listOthersFields" 
				   export="false" 
				   class="tableDisplay"
				   requestURI='<%=action%>'
				   uid='uid_listOthersFields'>
				   
			 <logic:present name="uid_listOthersFields">
			 
			 <bean:define id='itemobj' name='uid_listOthersFields' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>
								
			 <logic:iterate name="ResourcesOthersListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
		
				
				<logic:equal name="format" property="fieldType" value="CHECKBOX">
				
					<ispac:hasFunction functions="FUNC_COMP_ENTITIES_EDIT">
					<display:column title='<%="<center><input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/></center>"%>'
											headerClass="headerDisplay"
											style="text-align:center">
											
						<html:multibox property="multibox">
							<bean:define id="clave" name="itemobj" property="property(CLAVE)"/>
							<c:out value="${clave}"/>
						</html:multibox>
								
					</display:column>
					</ispac:hasFunction>
					
				</logic:equal>					
		
				<logic:equal name="format" property="fieldType" value="CLAVE">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>
									
						<%=format.formatProperty(itemobj)%>
						
					</display:column>
					
				</logic:equal>
				
				<logic:equal name="format" property="fieldType" value="VALOR">
				
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'>
									
						<bean:define id="clave" name="itemobj" property="property(CLAVE)"/>
						<c:set var="propertyValue"><%=format.formatProperty(itemobj)%></c:set>
						
						<input type="text" class="input" size="100" maxlength="250" name="property(<%=clave%>)" value='<c:out value="${propertyValue}" escapeXml="true"/>'/>

					</display:column>
					
				</logic:equal>
				
			</logic:iterate>
			</logic:present>
			
	</display:table>	

</html:form>