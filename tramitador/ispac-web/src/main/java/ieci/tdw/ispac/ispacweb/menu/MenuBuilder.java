package ieci.tdw.ispac.ispacweb.menu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.util.*;

import javax.servlet.ServletContext;

import net.sf.navigator.menu.*;
/**
 * @author marisa
 *
 * Clase que construye los menus de capas
 * @deprecated
 */
public class MenuBuilder {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
    .getLogger(MenuBuilder.class);

	private MenuRepository repository;
	private List nameLevel0;
	private String widthDefault0;
	private String widthDefault1;


	public MenuBuilder(ServletContext servletContext) {

		this.repository = new MenuRepository();
		this.nameLevel0 = new ArrayList();
		this.widthDefault0 = (String)servletContext.getAttribute("width_level0");
		this.widthDefault1 = (String)servletContext.getAttribute("width_level1");
		MenuRepository defaultRepository =
			(MenuRepository)servletContext.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		repository.setDisplayers(defaultRepository.getDisplayers());
	}

	/*
	 * método que construye la estructura del menu de capas
	 */
	public MenuRepository builMenu(List menuList){
		for (Iterator ite = menuList.iterator(); ite.hasNext();) {
			StructureMenu smenu = (StructureMenu)ite.next();
			// nivel 0
			MenuComponent mc0 = new MenuComponent();
			mc0.setName(smenu.getId());
			mc0.setWidth(widthDefault0);
			mc0.setTitle(smenu.getTitle());
			repository.addMenu(mc0);
			// nivel 1
			List itemList = smenu.getItems();
			if (itemList != null) {
				for (Iterator item = itemList.iterator(); item.hasNext();) {
					Object obj = item.next();
					Class type = obj.getClass();
					Method method = null;
					String nameValue = null;
					//Method method1 = null;
					String titleValue = null;
					try {
						//getProperty
						method = type.getDeclaredMethod(
							"getProperty",new Class[] {Class.forName("java.lang.String")});
					} catch (NoSuchMethodException nsme) {
						logger.error("URLEncoder::builMenu() "+nsme.getMessage());
						throw new IllegalArgumentException("Argumentos no válidos");
					} catch (ClassNotFoundException cnfe) {
					    logger.warn("URLEncoder::builMenu() "+cnfe.getMessage());
					}
					try {
						nameValue = (method.invoke(obj, new Object[] {smenu.getItemId()})).toString();
						titleValue = (String)method.invoke(obj, new Object[] {smenu.getItemName()});
					} catch (IllegalArgumentException iare) {
					    logger.warn("URLEncoder::builMenu() "+iare.getMessage());
					} catch (IllegalAccessException iace) {
					    logger.warn("URLEncoder::builMenu() "+iace.getMessage());
					} catch (InvocationTargetException inte) {
					    logger.warn("URLEncoder::builMenu() "+inte.getMessage());
					}
					MenuComponent mc1 = new MenuComponent();
					mc1.setName(smenu.getId()+nameValue);
					mc1.setTitle(titleValue);
					String url = smenu.getUrl();
					if (smenu.getVarUrl() != null) {
						if (url.split("/?").length > 0) {
							// contiene el caracter ?
							url = url+"&"+smenu.getVarUrl()+"="+nameValue;
						} else {
							url = url+"?"+smenu.getVarUrl()+"="+nameValue;
						}
					}
					if (url.startsWith("javascript:")) {
						mc1.setLocation(url);
					} else {
						mc1.setAction(url);
					}
					mc1.setWidth(widthDefault1);
					mc1.setParent(mc0);
					repository.addMenu(mc1);
				}
			} else  {
				String url = smenu.getUrl();
				MenuComponent mc2 = new MenuComponent();
				mc2.setName(smenu.getId()+smenu.getItemId());
				mc2.setTitle(smenu.getItemName());
				if (smenu.getVarUrl() != null) {
					if (url.split("/?").length > 0) {
						// contiene el caracter ?
						url = url+"&"+smenu.getVarUrl()+"="+smenu.getId();
					} else {
						url = url+"?"+smenu.getVarUrl()+"="+smenu.getId();
					}
				}
				if (url.startsWith("javascript:")) {
					mc2.setLocation(url);
				} else {
					mc2.setAction(url);
				}
				mc2.setWidth(widthDefault1);
				mc2.setParent(mc0);
				repository.addMenu(mc2);
			}
		}

		return repository;
	}

	/*
	 * método que devuelve la lista de los nombres del menu de capas del nivel 0
	 */
	public List getNamesLevel0(List menuList) {
		List namesLevel0 = new ArrayList();
		for (Iterator ite = menuList.iterator(); ite.hasNext();) {
			StructureMenu smenu = (StructureMenu)ite.next();
			namesLevel0.add(smenu.getId());
		}
		return namesLevel0;
	}

	/*
	 * Añade un menú popup
	 */
	public void addMenuItem(MenuItem menuItem, List actionList) throws ISPACException
	{
		// nivel 0
		MenuComponent mc0 = new MenuComponent();
		mc0.setName(menuItem.getName());
		mc0.setWidth(widthDefault0);
		mc0.setTitle(menuItem.getTitle());
		repository.addMenu(mc0);

		nameLevel0.add(menuItem.getName());

		// nivel 1
		int nAction = 1;

		for (Iterator iterator = actionList.iterator(); iterator.hasNext(); nAction++) {
			String sId = null;
			int index = 0;
			String urlEnd = null;

			ActionBean action = (ActionBean) iterator.next();

			if (menuItem.getPropertyId() != null) {
				sId = TypeConverter.toString(action.getProperty(menuItem.getPropertyId()));
			}
			String sTitle = TypeConverter.toString(action.getProperty(menuItem.getPropertyTitle()));
			String url = TypeConverter.toString(action.getProperty(ActionBean.ACTION));
			String sName = menuItem.getName() + Integer.toString( nAction);

			MenuComponent mc1 = new MenuComponent();

			mc1.setName( sName);
			mc1.setTitle( sTitle);

			if (menuItem.getParameter() != null && !sId.equals(""))
			{
				/*
				 * CONVENIO: Cuando queramos hacer referencia a una función javascript en el campo url,
				 * debe aparecer con la siguiente estructura "javascript:fuctionName(url,arg1,arg2,arg3,...)"
				 */
				if (url.startsWith("javascript:"))
				{
					index = url.indexOf(","); // si hay más de un argumento
					if (index == -1)
						index = url.indexOf(")");
					index = index-1;
					urlEnd = url.substring(index);
				}
				if (url.indexOf("?") == -1)
				{
					if (index > 0)
					{
						url = url.substring(0,index) + "?" + menuItem.getParameter() + "=" + sId + urlEnd;
					} else {
						url = url + "?" + menuItem.getParameter() + "=" + sId;
					}
				}
				else
				{
					if (index > 0)
					{
						url = url.substring(0,index) + "&" + menuItem.getParameter() + "=" + sId + urlEnd;
					} else {
						url = url + "&" + menuItem.getParameter() + "=" + sId;
					}
				}

			}

			if (url.startsWith("javascript:"))
			{
				mc1.setLocation(url);
			}
			else
			{
				mc1.setAction(url);
			}
			mc1.setWidth(widthDefault1);
			mc1.setParent(mc0);
			repository.addMenu(mc1);
		}
	}

	public MenuRepository getRepository()
	{
		return repository;
	}

	public List getMenuNames() {

		return nameLevel0;
	}
}
