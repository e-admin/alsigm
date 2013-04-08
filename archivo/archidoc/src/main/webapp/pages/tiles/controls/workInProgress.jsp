<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<c:set var="id" value="workInProgress"/>
<div id="bg<c:out value="${id}"/>" style="visibility:hidden; background-image:url(<c:url value="/pages/images/fondo.gif" />);z-index:99;position:absolute;top:0px;left:0px;width:100%"></div>
<div id="<c:out value="${id}"/>" style="display:none;z-index:100;position:absolute;width:250px;height:150px;top:0px;left:0px">

	<div class="wip_title">
		<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="1px"/>
		<html:img page="/pages/images/info.gif" styleClass="imgTextMiddle"/>
		<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="1px"/>
		<c:set var="blockTitle"><tiles:insert attribute="title" ignore="true" /></c:set>
		<span id="wipTitle">wipTitle</span>
	</div>

	<div class="block_content" id="block_content_wip"> <%-- bloque de datos --%>
		<span id="wipContent">wipContent</span><br><br>
		<span id="wipContent2">wipContent2</span><br><br>
	</div>
</div>