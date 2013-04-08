package se.autenticacion.idoc.api;

import ieci.tecdoc.sbo.uas.std.UasDaoUserRecO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Lista de registros UasDaoRecO.
 */
public class IeciTdUasDaoUserRecOArrayList implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List m_al;

	public IeciTdUasDaoUserRecOArrayList() {
		m_al = new ArrayList();
	}

	public int count() {
		return m_al.size();
	}

	public void add(UasDaoUserRecO val) {
		m_al.add(val);
	}

	public UasDaoUserRecO get(int index) {
		return (UasDaoUserRecO) m_al.get(index);
	}

	public void remove(UasDaoUserRecO val) {
		m_al.remove(val);
	}
}
