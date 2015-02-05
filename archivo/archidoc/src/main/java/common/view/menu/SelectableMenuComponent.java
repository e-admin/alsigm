package common.view.menu;

import net.sf.navigator.menu.MenuComponent;

public class SelectableMenuComponent extends MenuComponent {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private boolean collapsed = true;

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean selected) {
		this.collapsed = selected;
	}
}
