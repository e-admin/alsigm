/**
 *
 */
package common.vos;

import java.util.ArrayList;
import java.util.List;

import common.Constants;
import common.util.ListUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class NavegadorElementosVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List listaElementos;
	private String seleccionado;
	private int posSeleccionado = -1;
	private int numElementos = -1;

	private int posFirst = 0;
	private int posLast = 0;

	public NavegadorElementosVO(List listaElementos, String seleccionado) {
		super();

		if (listaElementos == null) {
			listaElementos = new ArrayList();
		}

		if (seleccionado == null) {
			seleccionado = Constants.STRING_EMPTY;
		}

		for (int i = 0; i < listaElementos.size(); i++) {
			IKeyId keyId = (IKeyId) listaElementos.get(i);
			if (keyId != null && seleccionado.equals(keyId.getId())) {
				this.posSeleccionado = i;
			}
		}

		this.listaElementos = listaElementos;
		this.numElementos = listaElementos.size();

		if (numElementos > 0) {
			posLast = numElementos - 1;
		}

		this.seleccionado = seleccionado;
	}

	public List getListaElementos() {
		if (ListUtils.isEmpty(listaElementos)) {
			return new ArrayList();
		}
		return listaElementos;
	}

	public void setListaElementos(List listaElementos) {
		if (listaElementos == null)
			listaElementos = new ArrayList();
		this.listaElementos = listaElementos;
	}

	public String getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(String seleccionado) {
		if (seleccionado == null)
			seleccionado = Constants.STRING_EMPTY;
		this.seleccionado = seleccionado;
	}

	public String getIdNext() {
		return getId(posSeleccionado + 1);
	}

	public String getIdPrevious() {
		return getId(posSeleccionado - 1);
	}

	public String getIdLast() {
		if (numElementos > 2 && posSeleccionado < posLast
				&& posSeleccionado < (posLast - 1)) {
			return getId(numElementos - 1);
		}
		return null;
	}

	public String getIdFirst() {
		if (numElementos > 2 && posSeleccionado > posFirst
				&& posSeleccionado > 1) {
			return getId(0);
		}
		return null;
	}

	/**
	 * Obtiene el elemento de la lista seleccionado.
	 * 
	 * @param pos
	 *            Posicion
	 * @return Identificador del elemento
	 */
	private String getId(int pos) {
		String id = null;
		if (pos >= 0 && pos < numElementos) {
			IKeyId keyId = (IKeyId) listaElementos.get(pos);
			if (keyId != null) {
				id = keyId.getId();
			}
		}
		return id;
	}

	public int getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(int numElementos) {
		this.numElementos = numElementos;
	}

	public String getResumen() {
		if (posSeleccionado >= 0 && numElementos > 0) {
			return "" + (posSeleccionado + 1) + Constants.SLASH + numElementos;
		}
		return null;
	}

}
