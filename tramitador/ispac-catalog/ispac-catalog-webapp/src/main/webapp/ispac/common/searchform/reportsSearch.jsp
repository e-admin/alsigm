<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="entityId" value="${param.entityId}"/>
<jsp:useBean id="entityId" type="java.lang.String"/>

<c:set var="regId" value="${param.regId}"/>
<jsp:useBean id="regId" type="java.lang.String"/>

<c:set var="frmId" value="${param.frmId}"/>
<jsp:useBean id="frmId" type="java.lang.String"/>

<script>
	function volver(){
		var actionURL = '<c:url value="/showCTSearchForm.do?"/>';
		actionURL = actionURL + "entityId=" + <%=entityId%>;
		actionURL = actionURL + "&regId=" + <%=regId%>;
		document.forms[0].action = actionURL;
		document.forms[0].submit();
	}

	function save(){
		var actionURL='<c:url value="/frmSearchReportAction.do?method=save"/>';
		actionURL = actionURL + "&frmId=" + <%=frmId%>;
		actionURL = actionURL + "&entityId=" + <%=entityId%>;
		actionURL = actionURL + "&regId=" + <%=regId%>;
		document.forms[0].action = actionURL;
		document.forms[0].submit();
	}
</script>

<div id="navmenutitle">
	<bean:message key="form.search.mainTitle"/>
</div>
<div id="navSubMenuTitle">
	<bean:message key="form.search.reportAsociate"/>
</div>
<div id="navmenu" >
	<div class="buttonList">
		<ul>
			<ispac:hasFunction functions="FUNC_COMP_SEARCH_FORMS_EDIT">
			<li>
				<a href='javascript:save()'><nobr><bean:message key="forms.button.save"/></nobr></a>
			</li>
			</ispac:hasFunction>
			<li>
					<a href="javascript:volver()"><nobr><bean:message key="forms.button.cancel"/></nobr></a>
			</li>
		</ul>
	</div>
</div>
<html:form action="/frmSearchReportAction.do" method="post">



	<logic:messagesPresent>
		<div id="formErrorsMessage">
			<html:errors />
		</div>
	</logic:messagesPresent>

	<%
		// Obtener los identificadores que se deben seleccionar
		java.util.List checkedIdList = null;
		String params[] = request.getParameterValues("multibox");
		if (params != null) {
			// Identificadores seleccionados
			checkedIdList = new java.util.ArrayList(java.util.Arrays.asList(params));
		} else {
			// Identificadores inicialemente marcados en base de datos
			ieci.tdw.ispac.ispaccatalog.action.form.EntityForm defaultForm = (ieci.tdw.ispac.ispaccatalog.action.form.EntityForm) request.getAttribute("defaultForm");
			checkedIdList = new java.util.ArrayList(java.util.Arrays.asList(defaultForm.getMultibox()));
		}
	%>
	<display:table name="CTReportsList"
				   id='itemobj'
				   form="defaultForm"
				   excludedParams="multibox"
				   export="true"
				   class="tableDisplay"
				   sort="list"
				   pagesize="45"
				   requestURI='/frmSearchReportAction.do'>

		<logic:present name="itemobj">

			<bean:define id='item' name='itemobj' type='ieci.tdw.ispac.ispaclib.bean.ObjectBean'/>

			 <logic:iterate name="CTReportsListFormatter" id="format" type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">

			 	<logic:equal name="format" property="fieldType" value="CHECKBOX">
					<display:column title='<%="<input type=\'checkbox\' onclick=\'javascript:checkAll(document.forms[0].multibox, this);\'/>"%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									class='<%=format.getColumnClass()%>'
									style="text-align:center;width:33px;">
            			<%=ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecoratorHelper.getCheckbox(checkedIdList, "multibox", String.valueOf(format.formatProperty(item)))%>
					</display:column>
				</logic:equal>
				<logic:equal name="format" property="fieldType" value="DATA">
					<display:column titleKey='<%=format.getTitleKey()%>'
									media='<%=format.getMedia()%>'
									headerClass='<%=format.getHeaderClass()%>'
									class='<%=format.getColumnClass()%>'
									sortable='<%=format.getSortable()%>'
									decorator='<%=format.getDecorator()%>'
									comparator='<%=format.getComparator()%>'><%=format.formatProperty(item)%></display:column>
				</logic:equal>
			</logic:iterate>

		</logic:present>

	</display:table>
	<%=ieci.tdw.ispac.ispacweb.decorators.CheckboxTableDecoratorHelper.getHiddenFields(checkedIdList, "multibox")%>

</html:form>