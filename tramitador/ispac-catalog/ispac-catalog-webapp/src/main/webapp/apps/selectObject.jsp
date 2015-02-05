<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<bean:define id="QueryString" name="selectObjForm" property="querystring"/>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<logic:present name="Formatter">
		<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
			<logic:equal name="format" property="fieldType" value="CHECKBOX">
				<c:set var="isMultiselect" value="true"/>
			</logic:equal>
		</logic:iterate>
</logic:present>

<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message name="CaptionKey"/></h4>
		<div class="acciones_ficha">
			<c:if test="${isMultiselect}">
				<a href="#" id="btnOkMultiBox" class="btnOk"><bean:message key="forms.button.accept"/></a>
			</c:if>
			<a href="#" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
		</div>
	</div>
</div>






<div id="navmenu">
<tiles:insert page="/ispac/common/tiles/AppErrorsTile.jsp" ignore="true" flush="false"/>
</div>

<div id="bodycontainer">

<c:if test="${TEXT_VALUE}">

	<div id="navSubMenuTitle">
		<bean:message key="select.object.textvalue.caption"/>
	</div>

	<div id="buscador">
		<form action='<%=request.getContextPath()%><c:out value="${TEXT_VALUE_ACTION}"/>?<%=QueryString%>' method="post">
			<bean:message key="select.object.textvalue.label"/>:&nbsp;&nbsp;
			<input type="text" class="input" size="40" maxlength="50" name="textvalue"/>
			<input type="submit" class="form_button_white" value='<bean:message key="select.button"/>'/>&nbsp;&nbsp;
		</form>
	</div>

	<logic:present name="ItemList">
	<div id="navSubMenuTitle">
		<bean:message key="select.object.nombre.caption"/>
	</div>
	</logic:present>
</c:if>

<html:form action="/selectObject.do" method="post">

	<html:hidden property="codetable"/>
	<html:hidden property="codefield"/>
	<html:hidden property="valuefield"/>
	<html:hidden property="decorator"/>
	<html:hidden property="parameters"/>
	<html:hidden property="querystring"/>
	<html:hidden property="caption"/>
	<html:hidden property="field"/>
	<html:hidden property="sqlquery"/>

<c:if test="${!NO_SEARCH}">
	<div id="buscador">

		<bean:message key="select.object.nombre.label"/>:&nbsp;&nbsp;
		<html:text styleClass="input" size="40" maxlength="50" property="searchvalue"/>
		<html:button property="filtra" styleClass="form_button_white" onclick="javascript:SearchObject();"> <bean:message key="search.button"/></html:button>&nbsp;&nbsp;

	</div>
</c:if>



	<!--
		PRESENTACIÓN DE LA LISTA DE OBJETOS
	-->
	<logic:present name="ItemList">

	<%
		java.util.List checkedIdList = ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecoratorHelper.getCheckedIdList(request, "multibox");
	%>

		<display:table name="ItemList" id="substitute" export="false"
				form="selectObjForm"
				excludedParams="multibox codetable codefield valuefield decorator parameters querystring caption field sqlquery searchvalue textvalue"
				class="tableDisplay" sort="list" pagesize="40" style="width:95%"
				requestURI="selectObject.do">

			<logic:iterate name="Formatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

				<logic:equal name="format" property="fieldType" value="CHECKBOX">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
						<script>
						<%
				  		String sep1 = "?";
				  		if ((format.getUrl() != null)
				  				&& (format.getUrl().indexOf("?") > 0)) {
				  			sep1 = "&";
				  		}
				  		%>
						urlSubmitMultibox = '<html:rewrite action='<%=format.getUrl()+sep1+QueryString%>'/>';
						</script>
            			<%=ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecoratorHelper.getCheckbox(checkedIdList, "multibox", String.valueOf(format.formatProperty(substitute)))%>
					</display:column>
			    </logic:equal>

				<logic:equal name="format" property="fieldType" value="SUBMIT">
				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  	                media='<%=format.getMedia()%>'
				  	                headerClass='<%=format.getHeaderClass()%>'
		  					        sortable='<%=format.getSortable()%>'
		  					        sortProperty='<%=format.getPropertyName()%>'
		  					        decorator='<%=format.getDecorator()%>'
		  					        class='<%=format.getColumnClass()%>'>
				  	<%
				  		String sep2 = "?";
				  		if ((format.getUrl() != null)
				  				&& (format.getUrl().indexOf("?") > 0)) {
				  			sep2 = "&";
				  		}
				  	%>
		            <a class='<%=format.getStyleClass()%>'
		               href="javascript:SelectObject('<html:rewrite
		                     action='<%=format.getUrl()+sep2+QueryString%>'
		              			 paramId='<%=format.getId()%>'
		              			 paramName='substitute'
		  						       paramProperty='<%=format.getPropertyLink() %>'/>');">
								<%=format.formatProperty(substitute)%>
			          </a>
				  	</display:column>
				 </logic:equal>

				 <logic:equal name="format" property="fieldType" value="LINK">
				  	<display:column titleKey='<%=format.getTitleKey()%>'
				  					media='<%=format.getMedia()%>'
				  					headerClass='<%=format.getHeaderClass()%>'
				  					sortable='<%=format.getSortable()%>'
				  					sortProperty='<%=format.getPropertyName()%>'
				  					decorator='<%=format.getDecorator()%>'
				  					class='<%=format.getColumnClass()%>'>

				  	<%
				  		String sep3 = "?";
				  		if ((format.getUrl() != null)
				  				&& (format.getUrl().indexOf("?") > 0)) {
				  			sep3 = "&";
				  		}
				  	%>

				  		<html:link action='<%=format.getUrl()+sep3+QueryString%>' styleClass='<%=format.getStyleClass()%>' paramId='<%=format.getId()%>' paramName='substitute'
				  			paramProperty='<%=format.getPropertyLink() %>'>


							<%=format.formatProperty(substitute)%>

				  		</html:link>
				  	</display:column>
				 </logic:equal>

				<logic:equal name="format" property="fieldType" value="DATA">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'>
						<%=format.formatProperty(substitute)%>
					</display:column>
				</logic:equal>

				<logic:equal name="format" property="fieldType" value="LINKPARAM">
		  			<display:column titleKey='<%=format.getTitleKey()%>'
		  					media='<%=format.getMedia()%>'
		  					headerClass='<%=format.getHeaderClass()%>'
		  					sortable='<%=format.getSortable()%>'
		  					sortProperty='<%=format.getPropertyName()%>'
		  					decorator='<%=format.getDecorator()%>'
		  					class='<%=format.getColumnClass()%>'>
		  					<%
				  		String sep4 = "?";
				  		if ((format.getUrl() != null)
				  				&& (format.getUrl().indexOf("?") > 0)) {
				  			sep4 = "&";
				  		}
				  		%>
		  				<html:link action='<%=format.getUrl()+sep4+QueryString%>' styleClass='<%=format.getStyleClass()%>'
		  					name="format" property='<%=format.prepareLinkParams(substitute)%>'>

							<%=format.formatProperty(substitute)%>
		  				</html:link>
		  			</display:column>
		 		</logic:equal>


			</logic:iterate>
		</display:table>
    	<%=ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecoratorHelper.getHiddenFields(checkedIdList, "multibox")%>
	</logic:present>


</html:form>


</div>
</div>

<script>
	var urlSubmitMultibox;
	function SearchObject()
	{
		document.selectObjForm.action = '<%=request.getContextPath() + "/selectObject.do"%>'
		document.selectObjForm.submit();
	}
	function SelectObject(url)
	{
		document.selectObjForm.action = url;
		document.selectObjForm.submit();
	}
	function SelectMultibox(){
		var data = checkboxElement(document.selectObjForm.multibox);
		if (data != "") {
			SelectObject(urlSubmitMultibox);
		} else {
			jAlert('<bean:message key="select.object.warning.empty"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
	}

	$("#btnCancel").click(function() {
				<ispac:hideframe />
	});

	$("#btnOkMultiBox").click(function() {
				SelectMultibox();
	});

	$(document).ready(function(){
		$("#move").draggable();
	});
</script>
