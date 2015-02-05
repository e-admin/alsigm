<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript">
function showHelp(){
	window.open('<ispac:onlinehelpHref tipoObj="42" />','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');
}
</script>

<div id="navmenu" style="heigth:100%;">

	<ispac:rewrite id="designerPage" page='components/designer/designer.jsp'/>
	
   	<iframe frameborder="0" src='<%=designerPage%>?pcdId=<c:out value="${param.pcdId}"/>&locale=<c:out value="${param.locale}"/>' 
	style="width: 100%; height: 100%; z-index: 4000"  ></iframe>
	
</div>