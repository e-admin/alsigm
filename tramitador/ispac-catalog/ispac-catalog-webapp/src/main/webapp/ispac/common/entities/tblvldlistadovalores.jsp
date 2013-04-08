<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language='JavaScript' type='text/javascript'><!--
	function deleteListaValores(url) {
		document.forms['defaultForm'].action = url;
		if (checkboxElement(document.defaultForm.multibox) == "") {
			jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		} else {
			document.forms['defaultForm'].submit();
		}	
	}
	
	
	
	function subir(accion){
	
		var noSelect = true;
		elementos = document.getElementsByName("multibox");
		for(var i=0; i<elementos.length; i++) {
			if(elementos[i].checked){
				noSelect = false;
				break;
			}
		}
		
		if (noSelect) {
				jAlert('<bean:message key="error.entity.noSelected"/>', '<bean:message key="common.alert"/>' , '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
		}
		else {
			ispac_submit(accion)
		}
	}

//--></script>


<c:set var="action">
   <c:out value="${requestScope['Action']}" default="1"/>
</c:set>
<jsp:useBean id="action" type="java.lang.String"/>

<c:set var="actionAdd">
   <c:out value="${requestScope['ActionAdd']}" default="1"/>
</c:set>




<c:set var="actionOrdenar">
   <c:out value="${requestScope['ActionOrdenar']}" default="1"/>
</c:set>
<jsp:useBean id="actionOrdenar" type="java.lang.String"/>

<jsp:useBean id="actionAdd" type="java.lang.String"/>

<c:set var="actionDelete">
   <c:out value="${requestScope['ActionDelete']}" default="1"/>
</c:set>
<jsp:useBean id="actionDelete" type="java.lang.String"/>

<c:set var="actionSubir">
   <c:out value="${requestScope['ActionSubir']}" default="1"/>
</c:set>
<jsp:useBean id="actionSubir" type="java.lang.String"/>


<c:set var="actionBajar">
   <c:out value="${requestScope['ActionBajar']}" default="1"/>
</c:set>
<jsp:useBean id="actionBajar" type="java.lang.String"/>

<c:set var="TblVldValuesList" value="${requestScope['TblVldValuesList']}"/>
<jsp:useBean id="TblVldValuesList" type="java.util.List"></jsp:useBean>
<c:set var="masDeUnElemento"><%= TblVldValuesList!=null && TblVldValuesList.size()>1%> </c:set>

<html:form action='<%=action%>'>

	<logic:present scope="request" name="EDITABLE">
		<div class="buttonList">
			<ul>
				<li>
					<a href="javascript:ispac_submit('<c:url value="${actionAdd}"/>')"><nobr><bean:message key="forms.button.add"/></nobr></a>
				</li>
				
				<c:choose>
				<c:when test="${!empty requestScope['TblVldValuesList']}">
				<li>
					<a href="javascript:deleteListaValores('<c:url value="${actionDelete}"/>')"><nobr><bean:message key="forms.button.delete"/></nobr></a>				
				</li>
				</c:when>
				<c:otherwise>
					<li style="background-color: #ddd; color: #aaa; cursor: default;">
						<nobr><bean:message key="forms.button.delete"/></nobr>
					</li>
				</c:otherwise>
				</c:choose>
				
				<li style="border: none; margin-left: 35%;">
					<label><bean:message key="form.tblvld.tipoOrder"/>:</label>
					 <select name="orderBy" id="orderBy" class="inputSelect">
                		<option value="valor" ><bean:message key="form.tblvld.valor"/> </option>
                		<logic:present name="haySustituto" scope="request">
                			<option value="sustituto" ><bean:message key="form.tblvld.sustituto"/> </option>
                		</logic:present>
                		
            		</select>	
				</li>
				<li>
				 	<a href="javascript: submitOrden('<c:url value="${actionOrdenar}"/>')"><nobr><bean:message key="common.message.ordenar"/></nobr></a>
				
				</li>
			</ul>
		</div>
		
	</logic:present>
	
	<logic:messagesPresent>
		<div id="formErrorsMessage">
			<html:errors />
		</div>	
	</logic:messagesPresent>
	
	<tiles:insert page="../tiles/displaytagList.jsp" ignore="true" flush="false" >
		<tiles:put name="ItemListAttr" value="TblVldValuesList"/>
		<tiles:put name="ItemFormatterAttr" value="TblVldValuesListFormatter"/>
		<tiles:put name="ItemActionAttr" value='<%=action%>'/>
		
			</tiles:insert>
			
	<logic:present scope="request" name="EDITABLE">
		<c:if test="${masDeUnElemento}">
		
			<div class="buttonList">
				<ul>
					<li>
						<a href="javascript:subir('<c:url value="${actionSubir}"/>')"><nobr><bean:message key="forms.button.subir"/></nobr></a>
					</li>
						
					<li>
						<a href="javascript:deleteListaValores('<c:url value="${actionBajar}"/>')"><nobr><bean:message key="forms.button.bajar"/></nobr></a>				
					</li>
				</ul>
			</div>
		</c:if>	
	</logic:present>

</html:form>

<script>
function submitOrden( url){
document.forms['defaultForm'].action = url +"&orderBy="+ document.getElementById('orderBy').value;
document.forms['defaultForm'].submit();


}
</script>
