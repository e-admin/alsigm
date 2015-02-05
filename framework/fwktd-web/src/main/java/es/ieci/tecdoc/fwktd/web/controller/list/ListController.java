package es.ieci.tecdoc.fwktd.web.controller.list;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.ieci.tecdoc.fwktd.web.delegate.ListDelegate;

/**
 * 
 * @author IECISA
 * 
 * @param <T>
 *            tipo de las entidades a listar
 */
public class ListController<T> extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(getListView()).addObject(LIST_NAME_DEFAULT,
				getAll(request));
	}

	/**
	 * Devuelve todas las entidades de tipo <code>T</code> existentes. Es
	 * responsabilidad del delegate asociado al controller su recuperación.
	 * 
	 * @return
	 */
	protected List<T> getAll(HttpServletRequest request) {
		return getDelegate().getAll();
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public ListDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(ListDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public String getEntityListName() {
		return (StringUtils.isEmpty(entityListName)) ? LIST_NAME_DEFAULT
				: this.entityListName;
	}

	public void setEntityListName(String entityListName) {
		this.entityListName = entityListName;
	}

	// Members
	protected static final String LIST_NAME_DEFAULT = "list";

	protected String entityListName;

	protected String listView;

	protected ListDelegate<T> delegate;

}
