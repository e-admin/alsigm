package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.BeanPropertyFmt;
import ieci.tdw.ispac.ispaclib.bean.TreeItemBean;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

public class ListTreeTag extends TagSupport {

    private String strNameList;
    private String strNameFormatter;
    private String strStyleButton;
    private String strSelectedId;
    private String select = "false";
	private JspWriter out;
	
	private String keyExpandButton = "tree.expand.button";
	private String keyCompressButton = "tree.compress.button";
	
	public ListTreeTag() {
		super();
	}
	
    public int doStartTag() throws JspException {
    	out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        TreeItemBean tree = (TreeItemBean)request.getAttribute(strNameList);
        BeanFormatter formatter = (BeanFormatter)request.getAttribute(strNameFormatter);
   	 	
        try {
        	printButtons(request);
			printTree(tree, formatter);
			printJavaScript((String)request.getAttribute(getSelectedId()));
		} catch (IOException e) {
			throw new JspException();
		} catch (ISPACException e) {
			throw new JspException();
		}
        
    	return (EVAL_BODY_INCLUDE);
    }
    
    public int doEndTag() throws JspException, JspException {
    	return EVAL_PAGE;
    }
    
	private String getMessage(HttpServletRequest request, String key) {
		
	   MessageResources resources = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
	   return (resources.getMessage(request.getLocale(), key));
	}
    
    private void printButtons(HttpServletRequest request) throws IOException {
    	
    	StringBuffer htmlButtons = new StringBuffer();
    	
    	// Botón para expandir el árbol
    	htmlButtons.append("<input type='button' name='' value='")
    			   .append(getMessage(request, keyExpandButton))
    			   .append("' onclick=\"expandTree('tree1'); return false;\" class='")
    			   .append(getStyleButton())
    			   .append("'>&nbsp;&nbsp;");
    	
    	// Botón para comprimir el árbol
    	htmlButtons.append("<input type='button' name='' value='")
    			   .append(getMessage(request, keyCompressButton))
    			   .append("' onclick=\"collapseTree('tree1'); return false;\" class='")
    			   .append(getStyleButton())
    			   .append("'>&nbsp;&nbsp;");
    	
		out.println(htmlButtons.toString());
    }
    
    private void printJavaScript(String id) throws IOException{
    	String fin = new String();
    	fin = "<SCRIPT>convertTrees();expandToItem('tree1','";
    			if( id != null){
    				fin += id;
    			}
    	fin += "');</SCRIPT>";
    	out.println(fin);
    }
    
	private void printTree(TreeItemBean treeBean, BeanFormatter formatter) throws IOException, ISPACException{
		out.println("<ul class='mktree' id='tree1'>");
 		Iterator it = treeBean.getChildren().iterator();
 		while(it.hasNext()){
 			printTreeTopDown((TreeItemBean)it.next(), formatter);
 		}
		out.println("</ul>");
 	}
	
 	private void printTreeTopDown(TreeItemBean treeBean, BeanFormatter formatter) throws IOException, ISPACException {
 		
 		if(treeBean.getItem()!=null) {

 			Iterator itformatter = formatter.iterator();
			BeanPropertyFmt format = (BeanPropertyFmt)itformatter.next();
 				
 			String li = "<li ID='" + treeBean.getItem().get(format.getFieldLink()) + "'>";
 	 		out.println(li);
 	 	 		
			if(format.getFieldType().equals("LINK")){
				
				Boolean bSelect = new Boolean(getSelect());
				if (bSelect.booleanValue()) {
					
				    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				    String context = request.getContextPath();
					
					String actionWithPath = StaticContext.rewriteAction((request.getRequestURL()).toString(), context, format.getUrl());
					
					out.println("<a href=\"javascript:SelectObject('" +
							actionWithPath +
							"&" + format.getId() + "=" + treeBean.getItem().get(format.getFieldLink()) +
							"');\" " +
							" class='" + format.getStyleClass() + "' " +
 							">");
				}
				else {				
					out.println("<a href='" +
								format.getUrl() +
								"&" + format.getId() + "=" + treeBean.getItem().get(format.getFieldLink()) +
								"' " +
								" class='" + format.getStyleClass() + "' " +
	 							">");
				}
 			}

			out.println(treeBean.getItem().get(format.getProperty()));
			out.println("</a>");
 		}
 		
 		/*
 		if(treeBean.getItem()!=null){
 			String li = "<li ID='" + treeBean.getItem().get("ID") + "'>";
 	 		out.println(li);
 			Iterator itformatter = formatter.iterator();
 			while(itformatter.hasNext()){
 				BeanPropertyFmt format = (BeanPropertyFmt)itformatter.next();
 				if(format.getFieldType().equals("LINK")){
 					out.println("<a href='" +
 										format.getUrl() +
 										"&" + format.getId() + "=" + treeBean.getItem().get("ID") +
 										"' " +
 										" class='" + format.getStyleClass() + "' " +
 									">");	
 				}
 			}
			out.println(treeBean.getItem().get("NOMBRE"));
			out.println("</a>");
 		}
 		*/
 		if(treeBean.hasChildren()){
 			out.println("<ul>");
 			Iterator it = treeBean.getChildren().iterator();
 	 		while(it.hasNext()){
 	 			printTreeTopDown((TreeItemBean)it.next(), formatter);
 	 		}
 	 		out.println("</ul>");
 		}
 		out.println("</li>");
 	}

    public String getNameFormatter() {
        return strNameFormatter;
    }
    public void setNameFormatter(String strNameFormatter) {
        this.strNameFormatter = strNameFormatter;
    }
    
    public String getNameList() {
        return strNameList;
    }

    public void setNameList(String strNameList) {
        this.strNameList = strNameList;
    }

	public String getStyleButton() {
		return strStyleButton;
	}

	public void setStyleButton(String strStyleButton) {
		this.strStyleButton = strStyleButton;
	}

	public String getSelectedId() {
		return strSelectedId;
	}

	public void setSelectedId(String strSelectedId) {
		this.strSelectedId = strSelectedId;
	}

	/**
	 * @return Returns the select.
	 */
	public String getSelect() {
		return select;
	}
	/**
	 * @param select The select to set.
	 */
	public void setSelect(String select) {
		this.select = select;
	}
	
}