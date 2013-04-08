package ieci.tdw.ispac.designer.client.menuPopUp;

import ieci.tdw.ispac.designer.client.helper.Html;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MenuPopUpPanel extends PopupPanel {
	
	private MenuPopUpPanel parent = null;

    private VerticalPanel panel = new VerticalPanel();

    private MenuPopUpPanel openChildMenu = null;

    /**
     * A constructor for this class.
     * 
     */
    public MenuPopUpPanel()
    {
        super(true);
        setStyleName("gwtcomp-PopupMenu");

        panel = new VerticalPanel();
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        setWidget(panel);

    }

    /**
     * Adds a child menu item that is a cascaded menu.
     * 
     * @param widget
     *            normally a HTML or Label object is used to print the menu item
     *            name.
     * @param cascadedMenu
     *            the popup menu that is popped up when the user mouses over the
     *            widget.
     * 
     */
    public void addMenuItem(Widget widget, MenuPopUpPanel cascadedMenu)
    {
        class ItemSelectionListener extends MouseListenerAdapter
        {
            private MenuPopUpPanel parent;

            private FocusPanel panel;

            private MenuPopUpPanel cascadedMenu;

            public ItemSelectionListener(MenuPopUpPanel parent, FocusPanel panel,
            		MenuPopUpPanel cascadedMenu)
            {
                this.parent = parent;
                this.panel = panel;
                this.cascadedMenu = cascadedMenu;
            }

            public void onMouseEnter(Widget sender)
            {
                panel.addStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");

                // hide the previous cascaded menu
                if (parent.openChildMenu != null)
                {
                    parent.openChildMenu.hide();
                    parent.openChildMenu = null;
                }

                // open the cascaded menu if one is available
                if (cascadedMenu != null)
                {
                    int top = sender.getAbsoluteTop();
                    int left = sender.getAbsoluteLeft()
                            + sender.getOffsetWidth();
                    cascadedMenu.setPopupPosition(left, top);
                    cascadedMenu.show();
                    parent.openChildMenu = cascadedMenu;
                }
            }

            public void onMouseLeave(Widget sender)
            {
                panel.removeStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");
            }
        }

        Widget w = null;
        if (cascadedMenu != null)
        {
            HorizontalPanel hp = new HorizontalPanel();
            hp.addStyleName("gwtcomp-PopupMenu-MenuItem");
            hp.setWidth("100%");
            hp.add(widget);
            hp
                    .setCellHorizontalAlignment(hp,
                            HasHorizontalAlignment.ALIGN_LEFT);
            Image im = new Image("gwtcomp-icons/1rightarrow.png");
            hp.add(im);
            hp.setCellHorizontalAlignment(im,
                    HasHorizontalAlignment.ALIGN_RIGHT);

            cascadedMenu.parent = this;
            w = hp;
        }
        else
        {
            w = widget;
        }

        FocusPanel fp = new FocusPanel(w);
        panel.add(fp);
        fp.setWidth("100%");
        fp.addStyleName("gwtcomp-PopupMenu-MenuItem");
        fp.addMouseListener(new ItemSelectionListener(this, fp, cascadedMenu));
    }

    /**
     * Add a child menu item.
     * 
     * @param widget
     *            normally a HTML segment or a text lable is passed but other
     *            input widgets can be passed as well.
     */
    public void addMenuItem(Widget widget)
    {
        addMenuItem(widget, null);
    }

    /**
     * Removes a child menu at the given index.
     * 
     * @param index
     *            index
     */
    public void removeMenuItem(int index)
    {
        panel.remove(index);
    }

    /**
     * Add a separator item.
     */
    public void addSeparator()
    {
    	
    	//BARRA BR HR
        panel.add(new HTML(Html.hr("#AAAAAA")));
    }

    
   
    /**
     * Hides the popup menu. In case the menu is a cascaded menu and has
     * parents, the parents are hidden as well.
     */
    public void hideAll()
    {
    	MenuPopUpPanel parent = this.parent;
        while (parent != null)
        {
            parent.hide();
            parent = parent.parent;
        }
    }

    /**
     * Gets the root menu for a given popup menu. Cascaded menus have parents
     * and possibly grand-parents. This method iterates through the chain and
     * returns the root popup menu.
     * 
     * @param menu a given popup menu object
     * @return the root popup menu
     */
    public static MenuPopUpPanel getRootMenu(MenuPopUpPanel menu)
    {
    	MenuPopUpPanel element = menu;
    	MenuPopUpPanel parent = element.parent;
        while (parent != null)
        {
            element = parent;
            parent = element.parent;
        }

        return element;
    }
}

	


