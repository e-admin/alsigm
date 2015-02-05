<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaytree.tld" prefix="tree" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">


<html>
<head>
	<title></title>
	<meta http-equiv="Cache-Control" content="no-cache" /> 
	<meta http-equiv="Expires" content="-1" />
	<meta http-equiv="Pragma" content="no-cache" /> 

	<script type="text/javascript" src='<ispac:rewrite href="../scripts/tree/x_core.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/tree/utils.js"/>'></script>
   <link rel="shortcut icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
  	<link rel="icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/tablist.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/newstyles.css"/>'/>

    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/newutilities.js"/>'> </script>
    
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
    

	<script type="text/javascript">
		function refreshWindow(selectedNode) {

			var treeManagerForm = document.forms['treeManagerForm'];
			if (treeManagerForm) {
				treeManagerForm.method.value = 'obtenerVista';
				treeManagerForm.openNodes.value = joinArray(trees[0].openNodes, '-');
				treeManagerForm.closedNodes.value = joinArray(trees[0].closedNodes, '-');
				treeManagerForm.selectedNode.value = selectedNode;
				treeManagerForm.target = self.name;
				treeManagerForm.submit();
			}
		}
	</script>

	<link rel="stylesheet" href='<ispac:rewrite href="css/frameArbol.css"/>'/>

</head>

<body class="treeView">

<script>
function execConfirm(msg,url){

	window.top.jConfirm(msg, '<bean:message key="common.confirm"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>', function(r) {
		if(r){
		window.document.location.href=url;
		}
							
	});	

}
function execRedirect(url){
window.document.location.href=url;
}
</script>


<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="CUADRO_PROCEDIMIENTO" /></c:out></c:set>
<jsp:useBean id="viewName" type="java.lang.String" />

<c:set var="scripts_tree_path"><ispac:rewrite href="../scripts/tree/"/></c:set>
<jsp:useBean id="scripts_tree_path" type="java.lang.String" />
<c:set var="images_tree_path"><ispac:rewrite href="img/procedureTree/"/></c:set>
<jsp:useBean id="images_tree_path" type="java.lang.String" />
<form>
			<div class="divCabArbol">
					<c:set var="buttonBar" value="${sessionScope['TREE_BUTTONS']}"/>
					<c:if test="${!empty buttonBar}">
					<ul class="menuarbol">
						<c:forEach var="button" items="${buttonBar}">
						<c:set var="titleKey" value="${button.titleKey}"/>
						<bean:define id="titleKey" name="titleKey" type="java.lang.String"/>
						<c:choose>
							<c:when test="${button.active}">
								<li>
									<a href='<c:out value="${button.url}" escapeXml="false"/>'><nobr><bean:message key='<%=titleKey%>'/></nobr></a>
								</li>
							</c:when>
							<c:otherwise>
								<li style="background-color: #ddd; color: #aaa; cursor: default;">
									<nobr><bean:message key='<%=titleKey%>'/></nobr>
								</li>
							</c:otherwise>
						</c:choose>
						</c:forEach>
					</ul>
					</c:if>
					<c:if test="${empty buttonBar}">
						<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="23px"/>
					</c:if>
			</div>
</form>
			<div class="divArbol">

<tree:show action="manageVistaCuadroProcedimiento.do" name='<%=viewName%>' imagesPath='<%=images_tree_path%>' jsCodeFile='<%=scripts_tree_path+"tree.js"%>' imageConfFile='<%=scripts_tree_path+"entidades_tree_tpl.js"%>' 
target="ibasefrm">
	function getNodeImage(treeItem) {
		var customIcon = null;
		if (treeItem.n_id == 0 && treeItem.o_root.a_tpl['im_root'])
		{
			customIcon = treeItem.o_root.a_tpl['im_root']
		}
		else if (treeItem.o_root.a_tpl['imgs_icons'] && treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].TIPO_ELEMENTO]) 
		{
			customIcon = treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].TIPO_ELEMENTO][(treeItem.a_config[3]?0:4)+(treeItem.a_config[3]&&treeItem.b_opened?2:0)+(treeItem.selected?1:0)];
		}
		return customIcon;
	}
</tree:show>

			</div>

</body>
</html>