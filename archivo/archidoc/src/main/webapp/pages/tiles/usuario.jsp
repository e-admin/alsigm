<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<script>
function showHideDiv(NDiv) {
	var divN="div"+NDiv;
	var imgN="img"+NDiv;
	if ((document.getElementById(divN).style.display == 'none')|| (document.getElementById(divN).style.display == ''))
	{
	document.getElementById(divN).style.display = 'block';
	document.getElementById(imgN).src = '../images/up.gif';
	}
	else
	{
	document.getElementById(divN).style.display = 'none';
	document.getElementById(imgN).src = '../images/down.gif';
	}
}


function changeImg(NDiv) {
	var divN="div"+NDiv;
	var imgN="imgPadre";
	if ((document.getElementById(divN).style.display == 'none')|| (document.getElementById(divN).style.display == ''))
	{
	document.getElementById(imgN).src = '../images/padre.gif';
	}
	else
	{
	document.getElementById(imgN).src = '../images/padre_up.gif';
	}
}

</script>

<c:set var="user" value="${sessionScope[appConstants.common.USUARIOKEY]}"/>

<%-- Datos de usuario --%>
<div class="content_container">
	<div id="barra_user">
	<TABLE class="user_tbl" cellspacing="0" cellpadding="0" width="100%">
	  <TR>
		<TD nowrap style="vertical-align:top;">
		<span class="user_title">
			<c:out value="${user.name}" /> 
			<c:out value="${user.surname}" />
		</span>
		</TD>
		<c:if test="${!empty user.organization}">
		<TD width="100%" style="vertical-align:top;">
			<span class="user_data" >
				<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="13px"/>
				<html:img page="/pages/images/padre_up.gif" styleClass="imgTextMiddle" styleId="imgPadre"/>
				<c:out value="${user.organization.nombre}" />
			</span>
			<div id="divUserCtxt" class="barra_userCtxt">
			<TABLE cellpadding="0" cellspacing="0">
				<c:forEach var="organo" items="${user.ancestorOrganizationList}" varStatus="nivel">
				<TR>
					 <TD width="100%" style="vertical-align:top;">
						<span style="padding-left:<c:out value="${nivel.count*10}px"/>;" class="user_data" >
							<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="8px"/>
  						    <c:choose>
								<c:when test="${nivel.last}">
									<html:img page="/pages/images/descendiente_last.gif" styleClass="imgTextMiddle"/>
							    </c:when>
							  	<c:otherwise>
									<html:img page="/pages/images/descendiente.gif" styleClass="imgTextMiddle"/>
								</c:otherwise>
							</c:choose>
							<c:out value="${organo.nombre}" />
						</span>
					</TD>
				 </TR>
				</c:forEach>
			</TABLE>
			</div>
		</TD>
		<c:if test="${!empty user.ancestorOrganizationList}">
			<TD valign="top">
				<html:img styleId="imgUserCtxt" page="/pages/images/down.gif" onclick="changeImg('UserCtxt');showHideDiv('UserCtxt')" styleClass="imgTextMiddle"/>
			</TD>
		</c:if>
		</c:if>
	  </TR>
	</TABLE>
	</div>

</div>
<%-- Datos de usuario --%>