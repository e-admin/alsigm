/*
 * CoolMenuDisplayer4.java
 *
 * Created on December 7, 2002, 12:22 AM
 */
package ieci.tdw.ispac.ispacweb.displayers;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.navigator.displayer.*;
import net.sf.navigator.menu.MenuComponent;

import org.apache.commons.lang.StringUtils;


/**
 *
 * @author  <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class CoolMenuDynamicDisplayer4 extends MessageResourcesMenuDisplayer {
    //~ Static fields/initializers =============================================

    
    /*Variables for each menu item: (** means that they have to be specified!)
       0 name: The name of the item. This must be unique for each item. Do not use spaces or strange characters in this one! **
       1 parent_name: The name of the menuitem you want this to "connect" to. This will be a submenu of the item that have the name you place in here. ** for all other then the topitems
       2 text: The text you want in the item. ** (except if you use images)
       3 link: The page you want this item to link to.
       4 target: The target window or frame you want the link to go to (Default is same window if you're not using frames, and the mainframe if you're using frames)
       5 width: The width of the element. If not specified it will get the default width specified above.
       height: The height of the element. If not specified it will get the default height specified above.
       img1: The "off" image for element if you want to use images.
       img2: The image that appears onmouseover if using images.
       regClass: The CSS class for a cell when it's not selected.
       overClass: The CSS class for a cell when it's moused over.
       align: The alignment for this item.
       rows: The number of rows for this item to expand.
       nolink:  If you have items that are meant to be info items only or something like that you can set this value to 1 and the item will not get a hand cursor and/or a link (the events will not work either)
       6 bonclick: If you want something to happen when the element is clicked (different from going to a link) spesifiy it here.
       7 onmouseover: This will happen when you mouseover the element. Could be status text, another imageswap or whatever.
       8 onmouseout: This will happen when you mouseout the element.
       Remember you can have as many levels/sublevels as you want. Just make sure you spesify the correct "parent" for each item.
       To set styles for each level see above.
     */

    // oCMenu(name, parent_name, text, link, target, width, height, 
    // regImage, overImage, regClass, overClass , align, rows, nolink, onclick, onmouseover, onmouseout) 

    /** main message format of the menu.  only 10 args max in jdk1.3 :( */

    // the zero (0) below before the number 5 is for the nolink attribute, since I'm 
    // hiding menus when they're not allowed, this is always 0.
    private Integer numMenus;
	private MessageFormat menuMessage;
    private static final String TAB = "    "; // four spaces
    private static final String SCRIPT_START =
        "\n<script type=\"text/javascript\">\n<!--";
    private static final String SCRIPT_END = "//-->\n</script>\n";
    private String END_STATEMENT;

    //~ Methods ================================================================

    public void init(PageContext context, MenuDisplayerMapping mapping) {
        super.init(context, mapping);
        Object obj = context.getAttribute("numMenus");
        if(obj != null)
        	numMenus = (Integer)obj;
        else
        	numMenus = new Integer(0);
        	
        menuMessage =
            new MessageFormat(
                "oCMenu"+numMenus+".makeMenu(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''''," +
                "'''','''','''','''','''','''',0,''{6}'',''{7}'',''{8}'');");
        END_STATEMENT = TAB + "oCMenu"+numMenus+".construct();\n";
        context.setAttribute("numMenus", new Integer(numMenus.intValue()+1));
        try {
            out.print(SCRIPT_START);
        } catch (Exception e) {}
    }

    /**
     * Prints the appropriate javascript for CoolMenu using \
     * <code>menuMessage</code> as the format.
     */
    public void display(MenuComponent menu) throws JspException, IOException {
        StringBuffer sb = new StringBuffer();
        buildMenuString(menu, sb, isAllowed(menu));
        out.print("\n" + TAB + sb);
    }

    /**
     * This will output the ending javascript statements defined in
     * <code>END_STATEMENT</code> and <code>SCRIPT_END</code>
     */
    public void end(PageContext context) {
        try {
            out.print(END_STATEMENT);
            out.print(SCRIPT_END);
        } catch (Exception e) {}
    }

    protected void buildMenuString(MenuComponent menu, StringBuffer sb,
        boolean allowed) {
        if (allowed) {
            sb.append(menuMessage.format(getArgs(menu)) + "\n" + TAB + TAB);

            MenuComponent[] subMenus = menu.getMenuComponents();

            if (subMenus.length > 0) {
                for (int i = 0; i < subMenus.length; i++) {
                    buildMenuString(subMenus[i], sb,
                        (allowed) ? isAllowed(subMenus[i]) : allowed);
                }
            }
        }
    }

    protected String[] getArgs(MenuComponent menu) {
        String[] args = new String[9];
        args[0] = menu.getName();
        args[1] = getParentName(menu);
        args[2] =
            (menu.getImage() != null)
            ? (displayStrings.getMessage("cm.image", menu.getImage()) + " " +
			getMessage(menu.getTitle())) : getMessage(menu.getTitle());
        args[3] = (menu.getUrl() == null) ? EMPTY : menu.getUrl();
        args[4] = getTarget(menu);
        args[5] = getWidth(menu);
        args[6] = (menu.getOnclick() == null) ? EMPTY : menu.getOnclick();
        args[7] =
            (menu.getOnmouseover() == null) ? EMPTY : menu.getOnmouseover();
        args[8] = (menu.getOnmouseout() == null) ? EMPTY : menu.getOnmouseout();

        // fix image HTML to escape double quotes for JavaScript
        args[2] = StringUtils.replace(args[2], "\"", "\\\"");

        return args;
    }

    /**
     * 
     * @param menu MenuComponent
     * @return width anchura de la capa de los submenus
     */
    protected String getWidth(MenuComponent menu) {
    	String strMaxWidth = menu.getWidth();
    	int maxWidth = Integer.parseInt(strMaxWidth);
    	int width = 0;
    	int characters = 0;
    	int NumPixelByCharacter = 6;// cálculo aproximado de un caracter en pixels
    	MenuComponent parent = menu.getParent();
    	if (parent != null) {
    		MenuComponent[] subMenus = parent.getMenuComponents();
    		for (int i = 0;i<subMenus.length; i++) {
    			MenuComponent submenu = subMenus[i];
    			characters = submenu.getTitle().length();
    			width = characters * NumPixelByCharacter;
    			if (width > maxWidth) {
    				maxWidth = width;
    			}
    		}
    		strMaxWidth = Integer.toString(maxWidth);
    	}
    	return strMaxWidth;
    }
    
    protected String getParentName(MenuComponent menu) {
        String name = null;

        if (menu.getParent() == null) {
            name = "";
        } else {
            name = menu.getParent().getName();
        }

        return name;
    }

    protected String getTarget(MenuComponent menu) {
        String theTarget = super.getTarget(menu);

        if (theTarget == null) {
            theTarget = EMPTY;
        }

        return theTarget;
    }
}
