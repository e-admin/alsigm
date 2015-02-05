package common.view.menu;

import net.sf.navigator.menu.MenuRepository;

/**
 * Extensión del repositorio de menus proporcionado por struts menu que nos
 * permite almacenar cual es la opción de menú activa en cada momento
 * 
 */
public class SelectableComponentMenuRepository extends MenuRepository {

	/** Última opción de menú que ha sido seleccionada */
	SelectableMenuComponent selectedMenu = null;

	public SelectableMenuComponent getSelectedMenu() {
		return selectedMenu;
	}

	/**
	 * Establece la opción de menú activa
	 * 
	 * @param selectedMenu
	 *            Opción de menú activa
	 */
	public void setSelectedMenu(SelectableMenuComponent selectedMenu) {
		SelectableMenuComponent aMenu = null;
		if (this.selectedMenu != null) {
			aMenu = this.selectedMenu;
			while ((aMenu = (SelectableMenuComponent) aMenu.getParent()) != null)
				aMenu.setCollapsed(true);
		}
		this.selectedMenu = selectedMenu;
		if (selectedMenu != null) {
			aMenu = this.selectedMenu;
			while ((aMenu = (SelectableMenuComponent) aMenu.getParent()) != null)
				aMenu.setCollapsed(false);
		}
	}
}