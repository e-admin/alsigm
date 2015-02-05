package common.view.menu;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.sf.navigator.displayer.MenuDisplayerMapping;
import net.sf.navigator.displayer.MessageResourcesMenuDisplayer;
import net.sf.navigator.menu.MenuComponent;

import org.apache.struts.Globals;

import common.Messages;
import common.util.UrlHelper;

/**
 * Menú Displayer para la presentación del menú con struts menú.
 */
public class ExpandedClasifiersMenuDisplayer extends
		MessageResourcesMenuDisplayer {

	public void init(PageContext pageContext, MenuDisplayerMapping mapping) {
		// Para que struts-menu obtenga los mensajes de struts le establecemos
		// el locale de struts
		locale = (Locale) pageContext.getSession().getAttribute(
				Globals.LOCALE_KEY);
		if (locale != null)
			super.setLocale(locale);

		super.init(pageContext, mapping);

		StringBuffer sb = new StringBuffer();
		sb.append(super.displayStrings.getMessage("dd.js.start"));
		sb.append(super.displayStrings.getMessage("dd.js.image.src.expand",
				super.displayStrings.getMessage("dd.image.src.expand")));
		sb.append(super.displayStrings.getMessage("dd.js.image.src.expanded",
				super.displayStrings.getMessage("dd.image.src.expanded")));
		sb.append(super.displayStrings.getMessage("dd.js.collapse.menu"));
		sb.append(super.displayStrings.getMessage("dd.js.toggle.menu.display"));
		sb.append(super.displayStrings
				.getMessage("dd.js.toggle.submenu.display"));
		sb.append(super.displayStrings
				.getMessage("dd.js.toggle.submenu.display"));
		sb.append(super.displayStrings.getMessage("dd.js.end"));
		try {
			super.out.print(sb.toString());
		} catch (Exception e) {
		}
	}

	public void display(MenuComponent menu) throws JspException, IOException {
		String title = null;
		if (super.getLocale() != null)
			title = Messages.getString(menu.getTitle(), super.getLocale());
		else
			title = super.getMessage(menu.getTitle());

		StringBuffer sb = new StringBuffer();
		String img = "";
		if (menu.getImage() != null)
			img = super.displayStrings.getMessage("dd.image", menu.getImage());
		MenuComponent components[] = menu.getMenuComponents();
		sb.append(super.displayStrings.getMessage("dd.menu.top"));
		if (components.length > 0) {
			String actionImg = "dd.image.src.expand";
			if (!isCollapsed(menu))
				actionImg = "dd.image.src.expanded";

			sb.append(super.displayStrings.getMessage(
					"dd.menu.expander",
					menu.getName(),
					super.displayStrings.getMessage("dd.image.expander",
							menu.getName(),
							super.displayStrings.getMessage(actionImg))
							+ "&nbsp;" + img + title));
			displayComponents(menu, 0, sb);
			sb.append(super.displayStrings.getMessage("dd.menu.restore",
					menu.getName()));
		} else {
			sb.append(super.displayStrings.getMessage("dd.image.expander",
					menu.getName(),
					super.displayStrings.getMessage("dd.image.src.expand")));
			sb.append(super.displayStrings.getMessage("dd.menu.top.link",
					title, menu.getLocation()));
		}
		sb.append(super.displayStrings.getMessage("dd.menu.bottom"));
		super.out.println(sb.toString());
	}

	private void displayComponents(MenuComponent menu, int level,
			StringBuffer sb) throws JspException, IOException {
		String title = null;
		String name = menu.getName();
		StringBuffer href = new StringBuffer();
		String img = "";
		MenuComponent components[] = menu.getMenuComponents();
		if (!isCollapsed(menu))
			sb.append(super.displayStrings.getMessage("dd.menu.openitem.top",
					name, String.valueOf((level + 1) * 6)));
		else
			sb.append(super.displayStrings.getMessage("dd.menu.item.top", name,
					String.valueOf((level + 1) * 6)));
		for (int i = 0; i < components.length; i++) {
			title = super.getMessage(components[i].getTitle());

			if (super.getLocale() != null)
				title = Messages.getString(components[i].getTitle(),
						super.getLocale());
			else
				title = super.getMessage(components[i].getTitle());

			if (components[i].getImage() != null)
				img = super.displayStrings.getMessage("dd.image",
						components[i].getImage());
			else
				img = "";
			href.setLength(0);
			href.append(UrlHelper.addUrlParameter(components[i].getLocation(),
					"menuName", components[i].getName()));
			sb.append(super.displayStrings.getMessage("dd.menu.item.row.start"));
			if (components[i].getMenuComponents().length > 0) {
				String actionImg = "dd.image.src.expand";
				if (!isCollapsed(components[i]))
					actionImg = "dd.image.src.expanded";
				sb.append(super.displayStrings.getMessage(
						"dd.submenu.expander",
						components[i].getName(),
						super.displayStrings.getMessage("dd.image.expander",
								components[i].getName(),
								super.displayStrings.getMessage(actionImg))
								+ "&nbsp;" + img + title));
				displayComponents(components[i], level + 1, sb);
				sb.append(super.displayStrings.getMessage("dd.menu.restore",
						components[i].getName()));
			} else {
				sb.append(super.displayStrings.getMessage("dd.link.start",
						href, super.getMenuTarget(components[i]),
						super.getMenuToolTip(components[i])));
				sb.append(img);
				sb.append(title);
				sb.append(super.displayStrings.getMessage("dd.link.end"));
			}
			sb.append(super.displayStrings.getMessage("dd.menu.item.row.end"));
		}

		sb.append(super.displayStrings.getMessage("dd.menu.item.bottom"));
	}

	private boolean isCollapsed(MenuComponent menu) {
		boolean collapsed = true;
		collapsed = ((SelectableMenuComponent) menu).isCollapsed();
		return collapsed;
	}

	protected int getLevel(MenuComponent menu) {
		int level = 0;
		MenuComponent parentMenu = menu.getParent();
		while (parentMenu != null) {
			parentMenu = parentMenu.getParent();
			level++;
		}
		return level;
	}
}
