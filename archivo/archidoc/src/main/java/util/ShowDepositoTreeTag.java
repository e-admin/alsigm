package util;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringEscapeUtils;

import xml.config.ConfiguracionArchivoManager;

import common.actions.NodeSelectionHandlerAction;
import common.util.StringUtils;

/*
 * Tag para la visualizacion de una estructura de datos organizada como arbol
 * jerarquico
 *
 */
public class ShowDepositoTreeTag extends BodyTagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String contextPath = "";

	String name = null;

	String action = null;

	String target = null;

	// Una vez mostrado el arbol su manejo es posible en cliente mediante codigo
	// JavaScript
	String jsCodeFile = "/js/treeDeposito.js";

	String imageConfFile = null;

	String treeWindow = "self";

	int width;

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		if (this.name != null) {
			try {
				TreeView tree = (TreeView) pageContext.getAttribute(this.name,
						PageContext.SESSION_SCOPE);

				if (tree != null) {

					HttpServletRequest request = (HttpServletRequest) pageContext
							.getRequest();
					this.contextPath = request.getContextPath();
					out.print("<script type=\"text/javascript\" src=\"");
					out.print(contextPath);
					out.print(this.jsCodeFile);
					out.print("\">");
					out.println("</script>");

					if (StringUtils.isEmpty(imageConfFile)) {
						out.print(ShowDepositoTreeTag.getTreeTplDefinition());
					} else {
						out.print("<script type=\"text/javascript\" src=\"");
						out.print(contextPath);
						out.print(this.imageConfFile);
						out.print("\">");
						out.println("</script>");
					}

					out.print(getTreeTplDefinition());

					out.println("<script type=\"text/javascript\">");
					out.println(getBodyContent().getString());
					StringBuffer treeItems = new StringBuffer();
					treeItems.append("var TREE_ITEMS = [");
					if (tree.getName() != null) {
						treeItems.append("['").append(tree.getName())
								.append("',0,'/',");
						Collection rootNodes = tree.getRootNodes();
						treeItems.append(rootNodes != null
								&& rootNodes.size() > 0 ? 1 : 0);
						treeItems.append(",1");
						TreeNode unNodo = null;
						for (Iterator i = tree.getRootNodes().iterator(); i
								.hasNext();) {
							unNodo = (TreeNode) i.next();
							treeItems.append(",");
							getNodeTreeItem(unNodo, treeItems);
						}
						treeItems.append("]");
					}
					treeItems.append("];");
					out.print(treeItems.toString());
					out.println("</script>");
					if (action != null) {
						out.println("<div id=\"treeDepositoManagerFormHider\" style=\"display:none\">");
						out.print("<form name=\"treeDepositoManagerForm\" id=\"treeDepositoManagerForm\" method=\"POST\" ");
						out.print("action=\"");
						out.print(contextPath);
						out.print("/action/");
						out.print(action);
						out.print("\">");
						out.print("<input type=\"hidden\" name=\"viewNameDeposito\" value=\"");
						out.print(this.name);
						out.println("\">");
						out.println("<input type=\"hidden\" name=\"actionToPerform\" value=\"\">");
						out.println("<input type=\"hidden\" name=\"node\">");
						out.println("<input type=\"hidden\" name=\"selectedNode\">");
						out.println("<input type=\"hidden\" name=\"openNodes\">");
						out.println("<input type=\"hidden\" name=\"closedNodes\">");
						out.println("</form>");

						out.print("<form name=\"viewDepositoNodeForm\" method=\"POST\" ");
						out.print("action=\"");
						out.print(contextPath);
						NodeSelectionHandlerAction treeNodeSelectionHandler = tree
								.getNodeSelectionHandler();
						out.print(treeNodeSelectionHandler.getHandlerPath());
						out.print("\">");
						out.println("<input type=\"hidden\" name=\"node\">");
						out.println("</form>");

						out.println("</div>");
					}
					out.println("<script type=\"text/javascript\">");
					out.print("var treeDeposito = new treeDeposito (TREE_ITEMS, ");
					out.print("TREE_TPL, ");
					out.print("'");
					out.print(contextPath);
					out.print("/pages/images/arboles/'");
					if (this.target != null) {
						out.print(",'");
						out.print(this.target);
						out.println("'");
					}
					out.println(")");
					if (tree.getSelectedNode() != null) {
						out.print("treeDeposito.setSelectedNode('");
						out.print(tree.getSelectedNode().getNodePath());
						out.println("');");
					}
					out.println("treeDeposito.init()");
					out.println("</script>");

				}

			} catch (IOException ioe) {
				try {
					out.println(ioe);
				} catch (IOException e) {

				}
			}
		}
		return EVAL_PAGE;
	}

	public static String getTreeTplDefinition() {

		/*
		 * 
		 * var TREE_TPL = { 'target' : '_blank', 'icon_e' : 'empty.gif', //
		 * empty image 'icon_l' : 'ftv2vertline.gif', // vertical line 'images'
		 * : { 'mas':'ftv2pnode.gif', 'menos':'ftv2mnode.gif',
		 * 'nodo':'ftv2node.gif', 'lmas':'ftv2plastnode.gif',
		 * 'lmenos':'ftv2mlastnode.gif', 'lnodo':'ftv2lastnode.gif'},
		 * 'defaultIcons' : [ 'icono_raiz.gif', 'icono_raiz.gif',
		 * 'icono_raiz.gif', 'icono_raiz.gif', 'icono_raiz.gif',
		 * 'icono_raiz.gif'], 'icons' : { '00000000000000000000000000000001' :
		 * ['deposito0.gif', 'deposito1.gif', 'deposito2.gif', 'deposito3.gif',
		 * 'deposito4.gif', 'deposito5.gif'], '00000000000000000000000000000002'
		 * : ['puerta0.gif', 'puerta1.gif', 'puerta2.gif', 'puerta3.gif',
		 * 'puerta4.gif', 'puerta5.gif'], '00000000000000000000000000000003' :
		 * ['armario0.gif', 'armario1.gif', 'armario2.gif', 'armario3.gif',
		 * 'armario4.gif', 'armario5.gif'], '00000000000000000000000000000004' :
		 * ['balda0.gif', 'balda1.gif', 'balda0.gif', 'balda1.gif',
		 * 'balda0.gif', 'balda1.gif'], '00000000000000000000000000000005' :
		 * ['planero0.gif', 'planero1.gif', 'planero2.gif', 'planero3.gif',
		 * 'planero4.gif', 'planero5.gif'], '00000000000000000000000000000006' :
		 * ['cajonPlanero0.gif', 'cajonPlanero1.gif', 'cajonPlanero0.gif',
		 * 'cajonPlanero1.gif', 'cajonPlanero0.gif', 'cajonPlanero1.gif']
		 * 
		 * } };
		 */

		StringBuffer js = new StringBuffer("<script type=\"text/javascript\">")
				.append("var TREE_TPL = {")
				.append("'target'  : '_blank',")
				.append("'icon_e'  : 'empty.gif',")
				.append("'icon_l'  : 'ftv2vertline.gif',")
				.append("'images'  : {	")
				.append("'mas':'ftv2pnode.gif',")
				.append("'menos':'ftv2mnode.gif',")
				.append("'nodo':'ftv2node.gif',")
				.append("'lmas':'ftv2plastnode.gif',")
				.append("'lmenos':'ftv2mlastnode.gif',")
				.append("'lnodo':'ftv2lastnode.gif'},")
				.append("'defaultIcons'  : [")
				.append("'icono_raiz.gif',")
				.append("'icono_raiz.gif',")
				.append("'icono_raiz.gif',")
				.append("'icono_raiz.gif',")
				.append("'icono_raiz.gif',")
				.append("'icono_raiz.gif'],")
				.append("'icons': {")
				.append(ConfiguracionArchivoManager.getInstance()
						.getIconosJavascript()).append("}").append("};")
				.append("</script>");

		return js.toString();
	}

	private void getNodeTreeItem(TreeNode node, StringBuffer buffer) {
		buffer.append("['");
		buffer.append(StringEscapeUtils.escapeJavaScript(StringEscapeUtils
				.escapeHtml(node.getNodeLabel())));
		buffer.append("',");
		buffer.append("{");
		Map atributosNodo = node.getAtributos();
		for (Iterator i = atributosNodo.keySet().iterator(); i.hasNext();) {
			String nombreAtributo = (String) i.next();
			buffer.append("'");
			buffer.append(nombreAtributo).append("' : '")
					.append(atributosNodo.get(nombreAtributo));
			buffer.append("'");
			if (i.hasNext())
				buffer.append(",");

		}
		buffer.append("},'");
		buffer.append(node.getNodePath());
		buffer.append("',");
		buffer.append(node.isLeaf() ? "0" : ((node.getChilds() != null && node
				.getChilds().size() == 0) ? "0" : "1"));
		buffer.append(",");
		buffer.append(node.isCollapsed() ? "0" : "1");
		Collection nodeChilds = node.getChilds();
		if (nodeChilds != null && nodeChilds.size() > 0) {
			buffer.append(",");
			for (Iterator i = nodeChilds.iterator(); i.hasNext();) {
				getNodeTreeItem((TreeNode) i.next(), buffer);
				if (i.hasNext())
					buffer.append(",");
			}
		}
		buffer.append("]");
	}

	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJsCodeFile() {
		return jsCodeFile;
	}

	public void setJsCodeFile(String jsDir) {
		this.jsCodeFile = jsDir;
	}

	public String getTreeWindow() {
		return treeWindow;
	}

	public void setTreeWindow(String treeWindow) {
		this.treeWindow = treeWindow;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getImageConfFile() {
		return imageConfFile;
	}

	public void setImageConfFile(String imageConfFile) {
		this.imageConfFile = imageConfFile;
	}
}