package deposito.forms;

import deposito.vos.HuecoID;

public class HuecoForm {

	public static HuecoID getHuecoID(String idHueco) {
		if (idHueco != null) {
			String idPadre = null;
			int numOrden = 0;
			if (idHueco.indexOf("%3A") > 0) {
				idPadre = idHueco.split("%3A")[0];
				numOrden = Integer.parseInt(idHueco.split("%3A")[1]);
			} else {
				idPadre = idHueco.split(":")[0];
				numOrden = Integer.parseInt(idHueco.split(":")[1]);
			}
			return new HuecoID(idPadre, numOrden);
		} else
			return null;
	}

	String idpadre;
	String numorden;
	String iddeposito;
	String estado;
	String iduinstalacion;
	String codprevision;
	String idHueco;
	String codigoRelacion;
	String signaturauinstalacion;

	public String getSignaturauinstalacion() {
		return this.signaturauinstalacion;
	}

	public void setSignaturauinstalacion(String signaturauinstalacion) {
		this.signaturauinstalacion = signaturauinstalacion;
	}

	public String getCodigoRelacion() {
		return this.codigoRelacion;
	}

	public void setCodigoRelacion(String codigoRelacion) {
		this.codigoRelacion = codigoRelacion;
	}

	/**
	 * @return Returns the idHueco.
	 */
	public String getIdhueco() {
		return idHueco;
	}

	/**
	 * @param idHueco
	 *            The idHueco to set.
	 */
	public void setIdhueco(String idHueco) {
		this.idHueco = idHueco;
	}

	/**
	 * @return Returns the codprevision.
	 */
	public String getCodprevision() {
		return codprevision;
	}

	/**
	 * @param codprevision
	 *            The codprevision to set.
	 */
	public void setCodprevision(String codprevision) {
		this.codprevision = codprevision;
	}

	/**
	 * @return Returns the estado.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the iddeposito.
	 */
	public String getIddeposito() {
		return iddeposito;
	}

	/**
	 * @param iddeposito
	 *            The iddeposito to set.
	 */
	public void setIddeposito(String iddeposito) {
		this.iddeposito = iddeposito;
	}

	/**
	 * @return Returns the idpadre.
	 */
	public String getIdpadre() {
		return idpadre;
	}

	/**
	 * @param idpadre
	 *            The idpadre to set.
	 */
	public void setIdpadre(String idpadre) {
		this.idpadre = idpadre;
	}

	/**
	 * @return Returns the iduinstalacion.
	 */
	public String getIduinstalacion() {
		return iduinstalacion;
	}

	/**
	 * @param iduinstalacion
	 *            The iduinstalacion to set.
	 */
	public void setIduinstalacion(String iduinstalacion) {
		this.iduinstalacion = iduinstalacion;
	}

	/**
	 * @return Returns the numorden.
	 */
	public String getNumorden() {
		return numorden;
	}

	/**
	 * @param numorden
	 *            The numorden to set.
	 */
	public void setNumorden(String numorden) {
		this.numorden = numorden;
	}
}
