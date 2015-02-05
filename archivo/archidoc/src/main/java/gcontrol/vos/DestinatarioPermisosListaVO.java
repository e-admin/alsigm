package gcontrol.vos;

import java.util.LinkedList;
import java.util.List;

import common.vos.BaseVO;

/**
 * Vo que almacena la información de un grupo de destinatarios de listas de
 * control de acceso.
 */
public class DestinatarioPermisosListaVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de destinatario. */
	private int tipoDest = -1;

	/** Lista de identificadores de destinatarios. */
	private List idDestList = null;

	/**
	 * Constructor.
	 */
	public DestinatarioPermisosListaVO() {
		super();
		this.idDestList = new LinkedList();
	}

	/**
	 * Constructor.
	 */
	public DestinatarioPermisosListaVO(int tipoDest) {
		this();
		setTipoDest(tipoDest);
	}

	/**
	 * @return Returns the idDestList.
	 */
	public List getIdDestList() {
		return idDestList;
	}

	/**
	 * @param idDestList
	 *            The idDestList to set.
	 */
	public void setIdDestList(List idDestList) {
		this.idDestList = idDestList;
	}

	/**
	 * @return Returns the tipoDest.
	 */
	public int getTipoDest() {
		return tipoDest;
	}

	/**
	 * @param tipoDest
	 *            The tipoDest to set.
	 */
	public void setTipoDest(int tipoDest) {
		this.tipoDest = tipoDest;
	}
}