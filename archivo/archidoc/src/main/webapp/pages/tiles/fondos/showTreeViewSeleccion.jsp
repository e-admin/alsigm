<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaytree.tld" prefix="tree" %>
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
			var treeManagerForm = document.forms['treeManagerForm'];
			treeManagerForm.actionToPerform.value = 'obtenerVista';
			treeManagerForm.openNodes.value = joinArray(trees[0].openNodes, '-');
			treeManagerForm.closedNodes.value = joinArray(trees[0].closedNodes, '-');
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

	<c:set var="viewName"><c:out value="${param.viewName}"><c:out value="${appConstants.fondos.CUADRO_CLF_SEL_VIEW_NAME}" /></c:out></c:set>
	<jsp:useBean id="viewName" type="java.lang.String" />

	<tree:show action="cuadroSeleccion" name="<%=viewName%>" imageConfFile="/js/fondos_tree_tpl.js">
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
		function viewNodeContent(tree_id, node_id)
		{
			if (parent.setAmbito)
			{
				var tree = trees[tree_id];
				tree.select(node_id);

				var nodo = tree.a_index[node_id];
				var nombre = nodo.a_config[0];
				var tipoElem = nodo.a_config[1].TIPO_ELEMENTO;
				var codRef = nodo.a_config[1].COD_REF;
				var idElementocf = nodo.a_config[1].ID;

				var nivelElementoPermtido = 1;
				var msgAmbitoInvalido = "<bean:message key="archigest.archivo.cf.busqueda.ambito.invalido"/>"


				if(parent.getNivelAmbitoPermtido){
					nivelElementoPermtido = parent.getNivelAmbitoPermtido();
				}

				if(parent.getMsgAmbitoInvalido){
					msgAmbitoInvalido = parent.getMsgAmbitoInvalido()
				}

				if(codRef == 'null') codRef = null;

				if (tipoElem && Number(tipoElem) > nivelElementoPermtido)
					parent.setAmbito(idElementocf, nombre, tipoElem);
				else
					alert(msgAmbitoInvalido);
			}
		}
	</tree:show>

</body>
</html>