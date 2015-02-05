package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.menu.Menu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 * Tag Menu Bar.
 */
public class MenuBarTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 1L;

    // Constantes --------------------------------------------------------------
    /** Nombre del atributo en el contexto de página que contiene los
     * identificadores de barras de menú existentes */
    public static final String ID_MAP = "ieci.tdw.ispac.ispacweb.menu.ID_MAP";

    // Atributos ---------------------------------------------------------------
    /** Lista de menús */
    private List/*<Menu>*/ menus = null;

    /** Nombre del atributo que contiene la lista de menús */
    private String name;

    /** Ámbito donde reside el atributo que contiene la lista de menús */
    private String scope;

    /** Nombre de la clase de estilo para los menus de nivel 0 */
    private String styleLevel0 = "menu_level0";

    /** Nombre de la clase de estilo para los menus de nivel 1 */
    private String styleLevel1 = "menu_level1";

    /** Posicionamiento del menú, vertical (cols) u horizontal (rows) */
    private String position = "rows";// por defecto

    /** Espacio entre menús */
    private String padding = "5px";
    
    
    /**Indica si solamente se "pintan" los botones del menu**/
    
    private String onlyDrawOptionsMenu="false";

    // Métodos -----------------------------------------------------------------
    /**
     * Chequea el identificador del barra de menús.
     */
    protected void checkMenuBarId() {
        int count = 0;
        Map idMap = (Map)this.pageContext.getAttribute(MenuBarTag.ID_MAP);
        if (idMap == null) {
            idMap = new HashMap();
            this.pageContext.setAttribute(MenuBarTag.ID_MAP, idMap);
        } else {
            Integer countInt = (Integer)idMap.get(this.id);
            if (countInt != null) {
                count = countInt.intValue();
            }
        }
        idMap.put(this.id, new Integer(count+1));
        if (count > 0) {
            this.id = new String(this.id+count);
        }
    }

    /**
     * Concatena url y parámetro a enviar.
     */
    protected String correctLink(String link, String paramName, String paramValue) {
    	String url = null;
    	String urlEnd = null;
    	int index = 0;
    	if (paramName != null && paramValue != null && !paramValue.equals("")) {
    		/*
			 * CONVENIO: Cuando queramos hacer referencia a una función javascript en el campo url,
			 * debe aparecer con la siguiente estructura "javascript:fuctionName(url,arg1,arg2,arg3,...)"
			 */
			if (link.startsWith("javascript:"))
			{
				index = link.indexOf(","); // si hay más de un argumento
				if (index == -1)
					index = link.indexOf(")");
				index = index-1;
				urlEnd = link.substring(index);
			}
			if (link.indexOf("?") == -1)
			{
				if (index > 0)
				{
					url = link.substring(0,index) + "?" + paramName + "=" + paramValue + urlEnd;
				} else {
					url = link + "?" + paramName + "=" + paramValue;
				}
			}
			else
			{
				if (index > 0)
				{
					url = link.substring(0,index) + "&" + paramName + "=" + paramValue + urlEnd;
				} else {
					url = link + "&" + paramName + "=" + paramValue;
				}
			}
    	} else {
    		url = link;
    	}
    	return url;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        this.checkMenuBarId();
        return super.doStartTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {

        if (menus == null) {
            menus = (List)pageContext.findAttribute(getName());
        }

        try {
            if (getPosition().equalsIgnoreCase("rows"))
                drawMenuRows();

            if (getPosition().equalsIgnoreCase("cols"))
               // drawMenuCols();
            	drawMenuOcultable();

        } catch (IOException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e);
        }
        catch (ISPACException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e);
        }
        menus = null;
        return EVAL_PAGE;
    }

    
    
    /**
     * 
     *<div id="contenido_menu" class="menu_desplegado">

		<div id="botonera">
			<p class="menu_bt" ><a href="javascript:menu();" id="imgMenu" class="imgMenuOn">&nbsp;</a></p>
		</div>

		<div id="navegacion">
			<div class="encabezado_nav"><h3> </h3></div> <!-- fin encabezado cuerpo -->
			
			<div class="cuerpo_nav">
			
				<div class="contenido_cuerpo_nav">

					<ul>
						<li class="menu_item"><a href="#">Inicio</a></li>
						<li id="item1" class="item_plegado"><a href="javascript:showItem(1);">Acciones</a>
							<ul>
								<li class="menu_item"><a href="#">Delegar Fase</a></li>
								<li class="menu_item"><a href="#">Comprobar Documentación</a></li>
								<li id="item2" class="item_plegado"><a href="javascript:showItem(2);">Acciones</a>
									<ul>
										<li class="menu_item"><a href="#">Delegar Fase</a></li>
										<li class="menu_item"><a href="#">Comprobar Documentación</a></li>
										<li id="item3" class="item_plegado"><a href="javascript:showItem(3);">Acciones</a>
											<ul>
												<li class="menu_item"><a href="#">Delegar Fase</a></li>
												<li class="menu_item"><a href="#">Comprobar Documentación</a></li>
												<li id="item4" class="item_plegado"><a href="javascript:showItem(4);">Acciones</a>
													<ul>
														<li class="menu_item"><a href="#">Delegar Fase</a></li>
														<li class="menu_item"><a href="#">Comprobar Documentación</a></li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
								</li>
							</ul>
						</li>
						<li class="menu_item"><a href="#">Nuevo Trámite</a></li>
					</ul>


					<ul class="menu_grupo">
						<li id="item5" class="item_plegado"><a href="javascript:showItem(5);">Datos de Trámites</a>
							<ul>
								<li class="tramite_abierto"><a href="#">Trámite abierto</a></li>
								<li class="tramite_cerrado"><a href="#">Trámite cerrado</a></li>
								<li class="tramite_delegado"><a href="#">Trámite delegado</a></li>
							</ul>
						</li>
					</ul>

					<ul class="menu_grupo">
						<li id="item6" class="item_plegado"><a href="javascript:showItem(6);">Datos de Trámites anteriores</a>
							<ul>
								<li class="tramite_abierto"><a href="#">Trámite abierto</a></li>
								<li class="tramite_cerrado"><a href="#">Trámite cerrado</a></li>
								<li class="tramite_delegado"><a href="#">Trámite delegado</a></li>
							</ul>
						</li>
					</ul>

				</div> <!-- contenido_cuerpo_nav -->
			</div> <!-- fin cuerpo_nav -->
		</div> <!-- fin navegacion -->
		</div>
     * 
     */

    
    public void drawMenuOcultable()throws IOException,ISPACException{
    	
    	 int im;
         JspWriter out = pageContext.getOut();
         HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
         String context =request.getContextPath();
         String link;
         String param = null;
         
        if(onlyDrawOptionsMenu.equalsIgnoreCase("false")){
         	
	         out.println("<!-- menu bar rows-->");
	         out.println("<div id=\"botonera\"><p class=\"menu_bt\" ><a href=\"javascript:menu();\" id=\"imgMenu\" class=\"imgMenuOn\">&nbsp;</a></p></div>");
	    	 out.println("<div id=\"contenido_menu\" class=\"menu_desplegado\">");
	    	 out.println("<div id=\"navegacion\" >");
	    	 out.println("<div class=\"encabezado_nav\" ><h3> </h3></div>");
	    	 out.println("<div class=\"cuerpo_nav\">");
	    	 out.println("<div class=\"contenido_cuerpo_nav\">");
        }
    	 //Acciones
    	
    	
    	 im = 0;
         if(getMenus().size()>0){
        	 out.println("<ul>");
	         for (Iterator ite = getMenus().iterator(); ite.hasNext(); im++) {
	        	  Menu menu = (Menu)ite.next();
	        	
	        	 
	        	 
	           
	             //No hay submenus
	             if(menu.getMsActionBean() != null)
	             {
	            	  out.println("<li  id=\"item"+im+"\" class=\"menu_item\"");
	            	  //Accion a ejecutar
	            	  link = TypeConverter.toString(menu.getMsActionBean().getProperty("ACTION"));
	            	  if(!link.startsWith("javascript:")){
	                      link = context + link;
	            	  }
	            	   if (menu.getJscond() != null) {
	                  	out.print(" style=\"display:none;\" >");
	                  }
	            	   else{
	            		   out.print(">");
	            	   }
	            	  out.println("<a class=\"" + getStyleLevel0() + "\"");
	  				if (menu.getTarget() != null) {
	  					out.println("\t\t\t\ttarget=\""+menu.getTarget()+"\"");
	  				}
	                  out.println("\t\t\t\thref='" + link + "'>" + menu.getLabel() + "</a>");
	            	  
	            	
	            	  out.println("</li>");
	            	  		
	             }
	             
	           //Tiene submenus
	             else{
	            	 
	            	 if (menu.getNameParameter() != null && !menu.getNameParameter().equals(""))
	                     param = menu.getNameParameter();
	             
	            	 //item_desplegado
	                 out.println("<li id=\"item"+im+"\" class=\"item_plegado\"");
	                 if (menu.getJscond() != null) {
		                  	out.print(" style=\"display:none;\" >");
		                  }
		            	   else{
		            		   out.print(">");
		           }
	                 out.println("<a href=\"#\" onclick=\"javascript:showItem("+im+");return false;\">"+menu.getLabel()+"</a>");
	                 //submenus
	                 out.println("<ul>");
	                 
	                 //Recorremos el listado de submenus
	                 int imi = 0;
	                 for (Iterator ite2 = menu.getItems().iterator(); ite2.hasNext(); imi++) {
	                     ActionBean menuItem = (ActionBean)ite2.next();
	                     if (menuItem.getProperty(menu.getPropertyId()) != null){
	                         link = correctLink(TypeConverter.toString(menuItem.getProperty(ActionBean.ACTION)),
	                                 param, TypeConverter.toString(menuItem.getProperty(menu.getPropertyId())));
	                     }
	                     else{
	                         link = TypeConverter.toString(menuItem.getProperty(ActionBean.ACTION));
	                     }
	                     if (!link.startsWith("javascript:")){
	                         link = context + link;
	                     }
	                     out.println("<li class=\"menu_item\"><a href='"+link+"'>"+menuItem.getProperty(menu.getPropertyTitle())+"</a></li>");
	                   
	                 }    
	                 
	                 
	                 out.println("</ul>");
	                 out.println(" </li>");
	             }
	             
	             if (menu.getJscond() != null) {
		             	out.println("<script>");
		             	out.println("if (" + menu.getJscond() + ") {");
		             	out.println("document.getElementById('item"+im+"').style.display = 'block';");
		 				out.println("}");
		             	out.println("</script>");
		             }
	                
	            	/*
	                 if (menu.getJscond() != null) {
	                 	out.print(" style=\"display:none;\"");
	                 }*/
	               
	                 
	            
	    
	         }
	         out.println("</ul>");
         }
    	
         if(onlyDrawOptionsMenu.equalsIgnoreCase("false")){
	    	 out.println("</div>");// <!-- contenido_cuerpo_nav -->
			 out.println("</div>");// <!-- fin cuerpo_nav -->
			 out.println("</div>");// <!-- fin navegacion -->
			 out.println("</div>");//<!--contenido_menu-->
         }
         
         
    	 
    	 
    }
    public void drawMenuRows() throws IOException, ISPACException{
        int im;
        String[] menuIds;
        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String context =request.getContextPath();

        out.println("<!-- menu bar rows-->");
        
        out.println("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\""+ styleLevel0 + "\" onMouseOut=\"hiddenMenu();\">");
        out.println("   <tr>");
        im = 0;
        menuIds = new String[getMenus().size()];
        for (Iterator ite = getMenus().iterator(); ite.hasNext(); im++) {
            Menu menu = (Menu)ite.next();
            menuIds[im] = this.id+'_'+im;

//	          [ildfns(v1.1)]	
//            out.println("       <td class=\""+ getStyleLevel0() +"\" onMouseOver=\"hiddenMenu(); showMenu('"+menuIds[im]+"');\">");
//            out.println("               <a class=\""+ getStyleLevel0() +"\" href=\"javascript:void(null);\">"+menu.getLabel()+"</a></td>");
//            out.println("       <td class=\""+ getStyleLevel0() +"\" width=\""+ getPadding() +"\"></td>");

            if (menu.getMsActionBean() != null){
        
            	String link = ""+menu.getMsActionBean().getProperty(ActionBean.ACTION);
            	if (!link.startsWith("javascript:"))
            		link = context + link;
            	
				out.print("		<td id=\"td_" + im + "\"");
                if (menu.getJscond() != null) {
                	out.print(" style=\"display:none;\"");
                }
				out.println(" class=\""+ styleLevel0 +"\" onMouseOver=\"hiddenMenu(); showMenu('"+menuIds[im]+"');\">");
				
				out.println("				<a class=\""+ styleLevel0 + "\"");
				if (menu.getTarget() != null) {
					out.println("                  target=\""+menu.getTarget()+"\"");
				}
				out.println("                  href='"+link+"\'>"+menu.getLabel()+"</a></td>");

            } else {
            	out.println("		<td id=\"td_" + im + "\" class=\""+ styleLevel0 +"\" onMouseOver=\"hiddenMenu(); showMenu('"+menuIds[im]+"');\">");
				out.println("				<a class=\""+ styleLevel0 +"\" href=\"javascript:void(null);\">"+menu.getLabel()+"</a></td>");
        	}

			out.print("		<td id=\"td_" + im + "_blank\"");
            if (menu.getJscond() != null) {
            	out.print(" style=\"display:none;\"");
            }
			out.println(" class=\""+ styleLevel0 +"\" width=\""+ padding +"\"></td>");

            if (menu.getJscond() != null) {
            	out.println("<script>");
            	out.println("if (" + menu.getJscond() + ") {");
            	out.println("document.getElementById('td_" + im + "').style.display = 'block';");
            	out.println("document.getElementById('td_" + im + "_blank').style.display = 'block';");
				out.println("}");
            	out.println("</script>");
            }
        }
        
        out.println("   </tr>");
        out.println("   <tr>");
        im = 0;

        for (Iterator ite = getMenus().iterator(); ite.hasNext(); im++) {
            String param = null;
            String link = null;
            Menu menu = (Menu)ite.next();
            if (menu.getNameParameter() != null && !menu.getNameParameter().equals(""))
                param = menu.getNameParameter();
            out.println("       <td>");
            out.println("           <!-- submenu "+im+" -->");
            out.println("           <div id=\""+menuIds[im]+"\" style=\"position: absolute; visibility: hidden;z-index:2;\" onMouseOver=\"showMenu('"+menuIds[im]+"');\">");
            out.println("               <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\""+ getStyleLevel1() +"\">");
            int imi = 0;
            for (Iterator ite2 = menu.getItems().iterator(); ite2.hasNext(); imi++) {
                ActionBean menuItem = (ActionBean)ite2.next();
                if (menuItem.getProperty(menu.getPropertyId()) != null)
                    link = correctLink(TypeConverter.toString(menuItem.getProperty(ActionBean.ACTION)),
                            param, TypeConverter.toString(menuItem.getProperty(menu.getPropertyId())));
                else
                    link = TypeConverter.toString(menuItem.getProperty(ActionBean.ACTION));
                if (!link.startsWith("javascript:"))
                    link = context + link;

                out.println("                   <tr>");
                out.println("                       <td class=\""+ getStyleLevel1() +"\">");
                out.println("                           <a href='"+ link+"' class=\""+ getStyleLevel1() +"\">");
                out.println("                               "+menuItem.getProperty(menu.getPropertyTitle())+"</a></td>");
                out.println("                   </tr>");
            }
            out.println("               </table>");
            out.println("           </div>");
            out.println("           <script>addMenu(\""+menuIds[im]+"\")</script>");
            out.println("           <!-- /submenu "+im+" -->");
            out.println("       </td>");
            out.println("       <td width=\""+ getPadding() +"\"></td>");
        }
        out.println("   </tr>");
        out.println("</table>");
        out.println("<!-- /menu bar rows-->");
    }

    public void drawMenuCols() throws IOException, ISPACException {
        int im;
        String[] menuIds;
        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String context =request.getContextPath();
        out.println("<!-- menu bar cols-->");
        out.println("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\""+ getStyleLevel0() + "\" onMouseOut=\"hiddenMenu();\">");
        im = 0;
        menuIds = new String[getMenus().size()];
        for (Iterator ite = getMenus().iterator(); ite.hasNext(); im++) {
            Menu menu = (Menu)ite.next();
            menuIds[im] = this.id+'_'+im;
            String param = null;
            String link = null;
            if (menu.getNameParameter() != null && !menu.getNameParameter().equals(""))
                param = menu.getNameParameter();

            if(menu.getMsActionBean() != null)
            {
                link = TypeConverter.toString(menu.getMsActionBean().getProperty("ACTION"));
                if(!link.startsWith("javascript:"))
                    link = context + link;
                out.print("\t<tr id=\"tr_" + im + "\"");
                if (menu.getJscond() != null) {
                	out.print(" style=\"display:none;\"");
                }
                out.println(">");
                out.println("\t\t<td class=\"" + getStyleLevel0() + "\" >");
                out.println("\t\t\t\t<a class=\"" + getStyleLevel0() + "\"");
				if (menu.getTarget() != null) {
					out.println("\t\t\t\ttarget=\""+menu.getTarget()+"\"");
				}
                out.println("\t\t\t\thref='" + link + "'>" + menu.getLabel() + "</a></td>");
                out.println("\t</tr>");
            } else
            {
                out.print("   <tr id=\"tr_" + im + "\"");
                if (menu.getJscond() != null) {
                	out.print(" style=\"display:none;\"");
                }
                out.println(">");
                out.println("       <td class=\""+ getStyleLevel0() +"\" onMouseOver=\"hiddenMenu(); showMenu('"+menuIds[im]+"');\">");
                out.println("               <a class=\""+ getStyleLevel0() +"\" href=\"javascript:void(null);\">"+menu.getLabel()+"</a></td>");
                out.println("       <td>");
                out.println("           <!-- submenu "+im+" -->");

                //TODO Revisar: Modificado para que los submenus salgan separados entre si (cellspacing=2)
                // y montados sobre el menu padre (margin-left:-4px; margin-top:-8px;)
                out.println("           <div id=\""+menuIds[im]+"\" style=\"position: absolute; visibility: hidden; z-index:2; margin-left:-4px; margin-top:-8px;\" onMouseOver=\"showMenu('"+menuIds[im]+"');\">");
                out.println("               <table cellpadding=\"0\" cellspacing=\"2\" border=\"0\" class=\""+ getStyleLevel1() +"\">");
                //out.println("         <div id=\""+menuIds[im]+"\" style=\"position: absolute; visibility: hidden;z-index:2;\" onMouseOver=\"showMenu('"+menuIds[im]+"');\">");
                //out.println("             <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\""+ styleLevel1 +"\">");


                int imi = 0;
                for (Iterator ite2 = menu.getItems().iterator(); ite2.hasNext(); imi++) {
                    ActionBean menuItem = (ActionBean)ite2.next();
                    if (menuItem.getProperty(menu.getPropertyId()) != null)
                        link = correctLink(TypeConverter.toString(menuItem.getProperty(ActionBean.ACTION)),
                                param, TypeConverter.toString(menuItem.getProperty(menu.getPropertyId())));
                    else
                        link = TypeConverter.toString(menuItem.getProperty(ActionBean.ACTION));
                    if (!link.startsWith("javascript:"))
                        link = context + link;

                    out.println("                   <tr>");
                    out.println("                       <td class=\""+ getStyleLevel1() +"\">");
                    out.println("                           <a href='"+ link+"' class=\""+ getStyleLevel1() +"\">");
                    out.println("                               "+menuItem.getProperty(menu.getPropertyTitle())+"</a></td>");
                    out.println("                   </tr>");
                }
                out.println("               </table>");
                out.println("           </div>");
                out.println("           <script>addMenu(\""+menuIds[im]+"\")</script>");
                out.println("           <!-- /submenu "+im+" -->");
                out.println("       </td>");
                out.println("   </tr>");
            }
            out.print("   <tr id=\"tr_" + im + "_blank\"");
            if (menu.getJscond() != null) {
            	out.print(" style=\"display:none;\"");
            }
            out.println("><td colspan=\"2\" height=\""+getPadding()+"\"></td></tr>");

            if (menu.getJscond() != null) {
            	out.println("<script>");
            	out.println("if (" + menu.getJscond() + ") {");
            	out.println("document.getElementById('tr_" + im + "').style.display = 'block';");
            	out.println("document.getElementById('tr_" + im + "_blank').style.display = 'block';");
				out.println("}");
            	out.println("</script>");
            }
        }
        out.println("</table>");
        out.println("<!-- /menu bar cols-->");
    }



    // Métodos Get/Set ---------------------------------------------------------
    /**
     * @return Devuelve el valor de <code>menus</code>.
     */
    public List getMenus() {
        return this.menus;
    }
    /**
     * Establece el valor de <code>menus</code>.
     * @param menus
     */
    public void setMenus(List menus) {
        this.menus = menus;
    }
    /**
     * @return Devuelve el valor de <code>name</code>.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Establece el valor de <code>name</code>.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Devuelve el valor de <code>scope</code>.
     */
    public String getScope() {
        return this.scope;
    }
    /**
     * Establece el valor de <code>scope</code>.
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
	/**
	 * @return Devuelve el estilo de los menus de nivel 0.
	 */
	public String getStyleLevel0()
	{
		return styleLevel0;
	}
	/**
	 * Establece el valor de <code>styleLevel0</code>.
	 * @param styleLevel0
	 */
	public void setStyleLevel0(String styleLevel0)
	{
		this.styleLevel0 = styleLevel0;
	}
	/**
	 * @return Devuelve el estilo de los menus de nivel 1.
	 */
	public String getStyleLevel1()
	{
		return styleLevel1;
	}
	/**
	 * Establece el valor de <code>styleLevel1</code>.
	 * @param styleLevel1
	 */
	public void setStyleLevel1(String styleLevel1)
	{
		this.styleLevel1 = styleLevel1;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the padding.
	 */
	public String getPadding() {
		return padding;
	}
	/**
	 * @param padding The padding to set.
	 */
	public void setPadding(String padding) {
		this.padding = padding;
	}

	/**
	 * 
	 * @return
	 */
	public String getOnlyDrawOptionsMenu() {
		return onlyDrawOptionsMenu;
	}

	/**
	 * Indica si se pintan solo las opciones del menu o no
	 * @param onlyDrawOptionsMenu
	 */
	public void setOnlyDrawOptionsMenu(String onlyDrawOptionsMenu) {
		this.onlyDrawOptionsMenu = onlyDrawOptionsMenu;
	}
	
	
}
