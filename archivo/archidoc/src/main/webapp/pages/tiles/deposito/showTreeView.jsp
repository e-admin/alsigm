<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/taglibs/displaytree.tld" prefix="tree" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html>
<head>
	<title></title>
	<script language="JavaScript1.2" src="<c:url value="/js/x_core.js"/>" type="text/JavaScript"></script>
	<script language="JavaScript1.2" src="<c:url value="/js/utils.js"/>" type="text/JavaScript"></script>

	<script type="text/javascript">

		function refreshWindow(selectedNode) {
			var treeManagerForm = document.forms['treeManagerForm'];
			if(treeManagerForm != null){
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

		}

		function isFondosTree(){
			return false;
		}

		function isDepositoTree(){
			return true;
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

<c:set var="viewName" value="${appConstants.deposito.DEPOSITO_VIEW_NAME}" />
<jsp:useBean id="viewName" type="java.lang.String" />
<tree:show name="<%=viewName%>" action="manageVistaDeposito" target="basefrm">
	function getNodeImage(treeItem) {
		var customIcon = null;
		if (treeItem.o_root.a_tpl['imgs_icons'] && treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].ID_TIPO_ELEMENTO]) {
			customIcon = treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].ID_TIPO_ELEMENTO][(treeItem.a_config[3]?0:4)+(treeItem.a_config[3]&&treeItem.b_opened?2:0)+(treeItem.selected?1:0)];
		}
		return customIcon;
	}
</tree:show>
</body>
</html>