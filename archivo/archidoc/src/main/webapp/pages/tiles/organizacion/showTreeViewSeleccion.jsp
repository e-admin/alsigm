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
			return false;
		}
	
		function isDocsTree(){
			return false;
		}

		function isOrganizacionTree(){
			return true;
		}
	</script>

	<link type="text/css" rel="stylesheet" href="<c:url value="/pages/css/frameArbol.css" />" />
</head>

<body>

<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.organizacion.ORGANIZACION_SEL_VIEW_NAME}" /></c:out></c:set>
<jsp:useBean id="viewName" type="java.lang.String" />

<tree:show name="<%=viewName%>" imageConfFile="/js/organizacion_tree_tpl.js" action="manageVistaOrganizacionSeleccion" target="basefrm">
	function getNodeImage(treeItem) {
		var customIcon = null;
		if (treeItem.o_root.a_tpl['imgs_icons'] && treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].TIPO]) {
			customIcon = treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].TIPO][(treeItem.a_config[3]?0:4)+(treeItem.a_config[3]&&treeItem.b_opened?2:0)+(treeItem.selected?1:0)];
		}
		return customIcon;
	}

		function viewNodeContent(tree_id, node_id) 
		{
			if (parent.setOrganizacion)
			{
				var tree = trees[tree_id];
				tree.select(node_id);
		
				var nodo = tree.a_index[node_id];				
				var nombre = nodo.a_config[0];				
				var tipoElem = nodo.a_config[1].TIPO;
				var idElem = nodo.a_config[1].ID_ELEMENTO;

				if (tipoElem)
					parent.setOrganizacion(idElem, nombre);
			}
		}
</tree:show>
</body>
	
</html>