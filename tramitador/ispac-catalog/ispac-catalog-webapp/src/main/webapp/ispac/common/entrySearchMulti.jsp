<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<c:set var="ThirdPartyList" value="${requestScope.ThirdPartyList}" />

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/sorttable.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
<ispac:javascriptLanguage />
<script>

		$(document).ready(function() {
			$("#move").draggable();
			
		});
		
		 
		function hide(){
		 	
		 	eval(element=window.top.frames['workframe'].document.getElementById("move"));
		    <ispac:hideframe id="workframesearch" refresh="false"/>
		   	parent.showLayer();
		 	element.style.visibility="visible";
		 	element.style.top=document.getElementById("move").style.top;
		 	element.style.left=document.getElementById("move").style.left;
		 
		 
		 

		 	
	 	}
	 	
	 function submitPermission()
	{	
		var myform;
		var mymultibox;
		var uidsSel=new Array();
		
		myform=document.getElementById("formseleccion");
		if (myform) {
			mymultibox = myform.seleccion;
		}

		var checked = false;	
		
		if(typeof mymultibox=='undefined'){
			checked=false;
		}
	
		// cuando tenemos un checkbox
		else 
		{if (typeof mymultibox.length == 'undefined')
		{
      		if (mymultibox.checked)
      			checked = true;
      	}
      	else
	    { 
	    	var j=0;
	    	for (var i=0; i < mymultibox.length; i++){
		        if (mymultibox[i].checked){
		        	
		        	checked = true;
		        }
		    }
	    }
	    }
	    if (checked){
	   
	    	
	    	myform.submit();
	    	}
	    else
	    	jAlert('<bean:message key="catalog.report.permission.alert.selResponsibles"/>', '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
			
		
			
			
			
		}
	

		</script>

</head>
<body>





<c:url value="${param['actionSetResponsibles']}" var="_addResponsibles">
</c:url>
<jsp:useBean id="_addResponsibles" type="java.lang.String" />





<div id="move" class="move">

<div class="ficha">
<div class="encabezado_ficha">
<div class="titulo_ficha">
<h4><bean:message key="catalog.search.estructuraOrganizativa" /></h4>
<div class="acciones_ficha"><a class="btnOk"
	href="javascript:submitPermission();"><bean:message
	key="forms.button.accept" /></a> <a class="btnCancel"
	href='javascript:hide();'><bean:message key="common.message.cancel" /></a>
</div>
<%--fin acciones ficha --%></div>
<%--fin titulo ficha --%></div>
<%--fin encabezado ficha --%></div>
<div id="navmenu"><tiles:insert
	page="/ispac/common/tiles/AppErrorsTile.jsp" ignore="true"
	flush="false" /></div>
<div class="contenedorBuscador">
<div id="buscador"><c:set var="_executeSearch">
	<c:out value="/entrySearch.do?view=executeSearch&mode=Multi" /></c:set>
 <jsp:useBean id="_executeSearch" type="java.lang.String" />

 <html:form
	styleId="formbuscador"
	action='<%= _executeSearch %>'>

	<input type="hidden" name="circuitoId"
		value='<c:out value="${param['key']}"/>' />
	<input type="hidden" name="actionSetResponsibles"
		value='<c:out value="${param['actionSetResponsibles']}"/>' />
	<input type="hidden" name="id" value='<c:out value="${param['id']}"/>' />
	<input type="hidden" name="parameters"
		value='<c:out value="${param['parameters']}"/>' />
	<input type="hidden" name="action"
		value='<c:out value="${param['action']}"/>' />
	<input type="hidden" name="captionkey"
		value='<c:out value="${param['captionkey']}"/>' />
	<input type="hidden" name="onlyselectusers"
		value='<c:out value="${param['onlyselectusers']}"/>' />

	<input type="hidden" name="entity"
		value='<c:out value="${param['entity']}"/>' />
	<input type="hidden" name="selVarios"
		value='<c:out value="${param['selVarios']}"/>' />
	<input type="hidden" name="key"
		value='<c:out value="${param['key']}"/>' />
	<input type="hidden" name="entityAppName"
		value='<c:out value="${param['entityAppName']}"/>' />
	<input type="hidden" name="readonly"
		value='<c:out value="${param['readonly']}"/>' />
	<input type="hidden" name="uid"
		value='<c:out value="${param['uid']}"/>' />
	<input type="hidden" name="uidGroup"
		value='<c:out value="${param['uidGroup']}"/>' />	
	<input type="hidden" name="regId"
		value='<c:out value="${param['regId']}"/>' />	
	<input type="hidden" name="kindOfSuperv"
		value='<c:out value="${param['kindOfSuperv']}"/>' />
    <input type="hidden" name="supervDirView"
		value='<c:out value="${param['supervDirView']}"/>' />
	<input type="hidden" name="dirUid"
		value='<c:out value="${param['dirUid']}"/>' />

	<label class="popUp"> <label class="popUp"><bean:message
		key="select.object.nombre.label" />:</label> <ispac:htmlText
		property="filter" onfocus="javascript:return true;" readonly="false"
		size="38%" styleClass="input"></ispac:htmlText> &nbsp;&nbsp; <input
		type="submit" 
		value="<bean:message key="search.button"/>" class="form_button_white"
		name="buscar" /> </label>
</html:form></div>
</div>

<c:set var="actionParam">
	<c:out value="${param['actionSetResponsibles']}" />
</c:set> <jsp:useBean id="actionParam" type="java.lang.String" /> 
<html:form
	styleId="formseleccion"
	action='<%= actionParam %>'>
	<input type="hidden" name="circuitoId"
		value='<c:out value="${param['key']}"/>' />
	<input type="hidden" name="id"
		 value='<c:out value="${param['id']}"/>' />
	<input type="hidden" name="parameters"
		value='<c:out value="${param['parameters']}"/>' />
	<input type="hidden" name="action"
		value='<c:out value="${param['action']}"/>' />
	<input type="hidden" name="captionkey"
		value='<c:out value="${param['captionkey']}"/>' />
	<input type="hidden" name="onlyselectusers"
		value='<c:out value="${param['onlyselectusers']}"/>' />
	
	<input type="hidden" name="entity"
		value='<c:out value="${param['entity']}"/>' />
	<input type="hidden" name="selVarios"
		value='<c:out value="${param['selVarios']}"/>' />
	<input type="hidden" name="key" value='<c:out value="${param['key']}"/>' />
	<input type="hidden" name="entityAppName"
		value='<c:out value="${param['entityAppName']}"/>' />
	<input type="hidden" name="readonly"
		value='<c:out value="${param['readonly']}"/>' />
	<input type="hidden" name="uid"
		value='<c:out value="${param['uid']}"/>' />
	<input type="hidden" name="uidGroup"
		value='<c:out value="${param['uidGroup']}"/>' />	
	<input type="hidden" name="regId"
		value='<c:out value="${param['regId']}"/>' />	
	<input type="hidden" name="kindOfSuperv"
		value='<c:out value="${param['kindOfSuperv']}"/>' />
    <input type="hidden" name="supervDirView"
		value='<c:out value="${param['supervDirView']}"/>' />
	<input type="hidden" name="dirUid"
		value='<c:out value="${param['dirUid']}"/>' />
	<input type="hidden" name="nameFrame" value="workframeSearch" />
	
	<div class="stdform"><br />
	<logic:present name="items">
		<display:table name="items" id="item" export="false"
			class="tableDisplay" sort="list" pagesize="20" style="width:95%"
			requestURI="/entrySearch.do">
			<logic:iterate id="format" name="CTFrmSearchOrgListFormatter"
				type="ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt">
				<c:set var="link"></c:set>
				<logic:equal name="format" property="property" value="UID">

					<display:column media='<%=format.getMedia()%>'
						headerClass='<%=format.getHeaderClass()%>'
						sortable='<%=format.getSortable()%>'>

						<c:set var="uid">
							<bean:write name="item" property="property(UID)" />
						</c:set>

						<input type="checkbox" name="seleccion" id="seleccion"
							value='<c:out value="${uid}"/>' />




						<c:set var="isUser">
							<bean:write name='item' property='property(USER)' />
						</c:set>
						<c:set var="isGroup">
							<bean:write name='item' property='property(GROUP)' />
						</c:set>
						<c:set var="isOrguni">
							<bean:write name='item' property='property(ORGUNIT)' />
						</c:set>


						<c:choose>
							<c:when test="${isUser=='true'}">
								<img src='<ispac:rewrite href="img/user.gif"/>'
									class="borderNone" />
							</c:when>
							<c:when test="${isGroup=='true'}">
								<img src='<ispac:rewrite href="img/group.gif"/>'
									class="borderNone" />
							</c:when>
							<c:when test="${isOrguni=='true'}">
								<img src='<ispac:rewrite href="img/org.gif"/>'
									class="borderNone" />
							</c:when>
						</c:choose>

					</display:column>
				</logic:equal>

				<logic:equal name="format" property="property" value="RESPNAME">

					<display:column titleKey='<%=format.getTitleKey()%>'
						media='<%=format.getMedia()%>'
						headerClass='<%=format.getHeaderClass()%>'
						sortable='<%=format.getSortable()%>'>
						<%=format.formatProperty(item)%>

					</display:column>


				</logic:equal>
			</logic:iterate>
		</display:table>
	</logic:present></div>
</html:form></div>
</div>
</body>

</html>