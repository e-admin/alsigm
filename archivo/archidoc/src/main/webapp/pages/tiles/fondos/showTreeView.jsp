<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaytree.tld" prefix="tree" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">


<html>
<head>
	<title></title>
	<meta http-equiv="Cache-Control" content="no-cache" /> 
	<meta http-equiv="Expires" content="-1" />
	<meta http-equiv="Pragma" content="no-cache" /> 

	<script language="JavaScript1.2" src="<c:url value="/js/x_core.js"/>" type="text/JavaScript"></script>
	<script language="JavaScript1.2" src="<c:url value="/js/utils.js"/>" type="text/JavaScript"></script>

	<script type="text/javascript">
		function refreshWindow(selectedNode) {
			var treeManagerForm = document.forms['treeManagerForm'];
			treeManagerForm.actionToPerform.value = 'obtenerVista';
			//treeManagerForm.openNodes.value = trees[0].openNodes.toString("-");
			treeManagerForm.openNodes.value = joinArray(trees[0].openNodes, '-');
			treeManagerForm.closedNodes.value = joinArray(trees[0].closedNodes, '-');
			treeManagerForm.selectedNode.value = selectedNode;
			treeManagerForm.target = self.name;
			treeManagerForm.submit();

			//SetCookie('openNodes',trees[0].openNodes.toString("|"));
			//window.location.reload()
		}

		function isFondosTree(){
			return true;
		}

		function isDepositoTree(){
			return false;
		}

		function isDocsTree(){
			return false;
		}

		function isOrganizacionTree(){
			return false;
		}

	</script>
	<link type="text/css" rel="stylesheet" href="<c:url value="/pages/css/frameArbol.css" />" />

</head>

<body>

<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.fondos.CUADRO_CLF_VIEW_NAME}" /></c:out></c:set>
<jsp:useBean id="viewName" type="java.lang.String" />

<tree:show action="manageVistaCuadroClasificacion" name="<%=viewName%>" imageConfFile="/js/fondos_tree_tpl.js" target="basefrm">
	function getNodeImage(treeItem) {
		var customIcon = null;
		if (treeItem.o_root.a_tpl['imgs_icons'] && treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].TIPO_ELEMENTO]) {
			customIcon = treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].TIPO_ELEMENTO][(treeItem.a_config[3]?0:4)+(treeItem.a_config[3]&&treeItem.b_opened?2:0)+(treeItem.selected?1:0)];
		}
		return customIcon;
	}
</tree:show>

</body>
</html>