<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<c:set var="id" value="workInProgressIFrame"/>
<div id="bg<c:out value="${id}"/>" style="visibility:hidden; background-image:url(<c:url value="/pages/images/fondo.gif" />);z-index:99;position:absolute;top:0px;left:0px;width:100%"></div>
<div id="<c:out value="${id}"/>" style="display:none;z-index:100;position:absolute;width:250px;height:150px;top:0px;left:0px">

	<div class="wip_title">
		<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="1px"/>
		<html:img page="/pages/images/info.gif" styleClass="imgTextMiddle"/>
		<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="1px"/>
		<c:set var="blockTitle"><tiles:insert attribute="title" ignore="true" /></c:set>
		<span id="wipTitleIFrame">wipTitle</span>
	</div>

	<script>
		function detenerInforme(cancelProgressBarURL){
			//detener la barra de progreso
			parent.endGeneracion();
			document.getElementById("stateWipFrame").src=cancelProgressBarURL;
		}
	</script>

	<div class="block_content" id="block_content_wip"> <%-- bloque de datos --%>
		<span id="wipContentIFrame">wipContent</span><br/><br/>
		<!-- <span id="wipContent2IFrame">wipContent2</span> -->
		<iframe name="stateWipFrame" id="stateWipFrame" scrolling="no" style="visibility:hidden"
			src="javascript:''" width="188px" height="10px" frameborder="0">
		</iframe>
		<c:url var="cancelProgressBarURL" value="/action/progressBarRefresher" scope="request">
			<c:param name="method" value="cancelGenReport"/>
		</c:url>

		<div style="padding-top:10px;text-align:center;width:100%">
			<a name="cancelReportButton" id="cancelReportButton" class="etiquetaAzul12Bold" href="javascript:detenerInforme('<c:out value="${cancelProgressBarURL}"/>')" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.buscar" titleKey="archigest.archivo.buscar" styleClass="imgTextMiddle" />
				<bean:message key="archigest.archivo.cancelar"/>
			</a></div>
	</div>	
</div>
		
		