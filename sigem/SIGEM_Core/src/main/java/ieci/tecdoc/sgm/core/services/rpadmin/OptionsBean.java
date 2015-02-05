package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de operadores sobre campos de registro
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.OptionBean
 */
public class OptionsBean {

	private List options;

	public OptionsBean() {
		options = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return options.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public OptionBean get(int index) {
		return (OptionBean) options.get(index);
	}

	/**
	 * @param entidad
	 */
	public void add(OptionBean entidad) {
		options.add(entidad);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return options;
	}

	/**
	 * @param options
	 */
	public void setLista(List options) {
		this.options = options;
	}
}
