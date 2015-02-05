<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaytreeAmbitoDeposito.tld" prefix="tree" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

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
			var treeManagerForm = document.forms['treeDepositoManagerForm'];
			treeManagerForm.actionToPerform.value = 'obtenerVista';
			treeManagerForm.openNodes.value = joinArray(treesAmbitoDeposito[0].openNodes, '-');
			treeManagerForm.closedNodes.value = joinArray(treesAmbitoDeposito[0].closedNodes, '-');
			treeManagerForm.selectedNode.value = selectedNode;
			treeManagerForm.target = self.name;
			treeManagerForm.submit();
		}
	</script>
	<link type="text/css" rel="stylesheet" href="<c:url value="/pages/css/frameArbol.css" />" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/pages/css/archivoPosition.css" />" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/pages/css/archivoGeneral.css" />"" />

</head>

<body>

	<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.deposito.AMBITO_DEPOSITO_SEL_VIEW_NAME}" /></c:out></c:set>
	<jsp:useBean id="viewName" type="java.lang.String" />
	<tree:show name="<%=viewName%>" action="ambitoDepositoSeleccion" target="basefrm">
		function getNodeImage(treeItem) {
			var customIcon = null;
			if (treeItem.o_root.a_tpl['imgs_icons'] && treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].ID_TIPO_ELEMENTO]) {
				customIcon = treeItem.o_root.a_tpl['imgs_icons'][treeItem.a_config[1].ID_TIPO_ELEMENTO][(treeItem.a_config[3]?0:4)+(treeItem.a_config[3]&&treeItem.b_opened?2:0)+(treeItem.selected?1:0)];
			}
			return customIcon;
		}

		function viewNodeContent(tree_id, node_id)
		{
			if (parent.setAmbitoDeposito)
			{
				var tree = treesAmbitoDeposito[tree_id];
				tree.select(node_id);

				var nodo = tree.a_index[node_id];
				var nombre = nodo.a_config[0];
				var tipoElem = nodo.a_config[1].ID_TIPO_ELEMENTO;
				var idElem = nodo.a_config[1].ID_ELEMENTO;
				var pathElem = nodo.a_config[1].PATHNAME_ELEMENTO;
				var codOrdenElem= nodo.a_config[1].CODORDEN_ELEMENTO;

				if (tipoElem && Number(tipoElem) > 1)
					parent.setAmbitoDeposito(idElem, pathElem, tipoElem ,codOrdenElem);
				else
					alert("<bean:message key="archigest.archivo.deposito.busqueda.ambito.invalido"/>");
			}
		}
	</tree:show>

</body>
</html>