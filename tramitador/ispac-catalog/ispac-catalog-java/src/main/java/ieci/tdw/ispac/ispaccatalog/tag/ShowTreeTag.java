package ieci.tdw.ispac.ispaccatalog.tag;






import ieci.tdw.ispac.ispaclib.bean.NodeSelectionHandlerAction;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

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


/*
 * Tag para la visualizacion de una estructura de datos organizada como arbol
 * jerarquico
 *  
 */
public class ShowTreeTag extends BodyTagSupport {

   String contextPath = "";

   String name = null;

   String action = null;

   String target = null;

   // Una vez mostrado el arbol su manejo es posible en cliente mediante codigo
   // JavaScript
   String jsCodeFile = "/js/tree.js";

   String imageConfFile = "/js/tree_tpl.js";

   String treeWindow = "self";
   
   String imagesPath = "";

   int width;

   public int doEndTag() throws JspException {
      JspWriter out = pageContext.getOut();
      if (this.name != null) {
         try {
            TreeView tree = (TreeView) pageContext.getAttribute(this.name,
                  PageContext.SESSION_SCOPE);
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            this.contextPath = request.getContextPath();
            out.print("<script type=\"text/javascript\" src=\"");
            //out.print(contextPath);
            out.print(this.jsCodeFile);
            out.print("\">");
            out.println("</script>");
            out.print("<script type=\"text/javascript\" src=\"");
            //out.print(contextPath);
            out.print(this.imageConfFile);
            out.print("\">");
            out.println("</script>");
            out.println("<script type=\"text/javascript\">");
            out.println(getBodyContent().getString());
            StringBuffer treeItems = new StringBuffer();
            treeItems.append("var TREE_ITEMS = [");
            //treeItems.append("[");
            //mostramos el nodo raiz solo si lo tiene
            if (tree.getViewName() != null){
               treeItems.append("[");
               treeItems.append("'").append(tree.getViewName()).append("',0,'/',");
            
               Collection rootNodes = tree.getRootNodes();
               treeItems.append(rootNodes != null && rootNodes.size() > 0 ? 1 : 0);
               treeItems.append(",1");
            }
           TreeNode unNodo = null;
           boolean rootNodes = false;
           for (Iterator i = tree.getRootNodes().iterator(); i.hasNext();) {
              unNodo = (TreeNode) i.next();
              if (rootNodes)
            	  treeItems.append(",");
              else 
            	  rootNodes = true;
              getNodeTreeItem(unNodo, treeItems);
           }

           treeItems.append("]");
            
           if (tree.getViewName() != null){
        	   treeItems.append("]");
           }
       
           treeItems.append(";");
               
            out.print(treeItems.toString());
            out.println("</script>");
            if (action != null) {
               out.println("<div id=\"treeManagerFormHider\" style=\"display:none\">");
               out.print("<form name=\"treeManagerForm\" method=\"POST\" ");
               out.print("action=\"");
               out.print(contextPath);
               //out.print("/action/");
               out.print("/");
               out.print(action);
               out.print("\">");
               out.print("<input type=\"hidden\" name=\"viewName\" value=\"");
               out.print(this.name);
               out.println("\">");
               out.println("<input type=\"hidden\" name=\"method\" value=\"\">");
               out.println("<input type=\"hidden\" name=\"node\">");
               out.println("<input type=\"hidden\" name=\"selectedNode\">");
               out.println("<input type=\"hidden\" name=\"openNodes\">");
               out.println("<input type=\"hidden\" name=\"closedNodes\">");
               out.println("</form>");

               out.print("<form name=\"viewNodeForm\" method=\"POST\" ");
               out.print("action=\"");
               out.print(contextPath);
               NodeSelectionHandlerAction treeNodeSelectionHandler = tree.getNodeSelectionHandler();
               out.print(treeNodeSelectionHandler.getHandlerPath());
               out.print("\" target=\"" + target + "\" >");
               out.println("<input type=\"hidden\" name=\"node\">");
               out.println("</form>");

               out.println("</div>");
            }
            out.println("<script type=\"text/javascript\">");
            out.print("var tree = new tree (TREE_ITEMS, ");
            out.print("TREE_TPL, ");
            out.print("'");
            //out.print(contextPath);
            out.print(imagesPath+ "'");
            if (this.target != null) {
               out.print(",'");
               out.print(this.target);
               out.println("'");
            }
            out.println(")");
            if (tree.getSelectedNode() != null) {
               out.print("tree.setSelectedNode('");
               out.print(tree.getSelectedNode().getNodePath());
               out.println("');");
            }
            out.println("tree.init()");
            out.println("</script>");
         } catch (IOException ioe) {
         }
      }
      return EVAL_PAGE;
   }

   private void getNodeTreeItem(TreeNode node, StringBuffer buffer) {
//      AppUser user = (AppUser) pageContext.getAttribute(Constants.USUARIOKEY,
//              PageContext.SESSION_SCOPE);
        
      buffer.append("['");
      buffer.append(StringEscapeUtils.escapeJavaScript(node.getNodeLabel()));
      buffer.append("',");
      buffer.append("{");
      Map atributosNodo = node.getAtributos();
      for (Iterator i = atributosNodo.keySet().iterator(); i.hasNext();) {
         String nombreAtributo = (String) i.next();
         buffer.append("'");
         buffer.append(nombreAtributo).append("' : '").append(atributosNodo.get(nombreAtributo));
         buffer.append("'");
         if (i.hasNext())
            buffer.append(",");

      }
      buffer.append("},'");
      buffer.append(node.getNodePath());
      buffer.append("',");
      buffer.append(node.isLeaf() ? "0"
            : ((node.getChilds() != null && node.getChilds().size() == 0) ? "0" : "1"));
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

   public void setImagesPath(String imagesPath) {
      this.imagesPath = imagesPath;
   }
   
   public String getImagesPath() {
	      return imagesPath;
   }

}