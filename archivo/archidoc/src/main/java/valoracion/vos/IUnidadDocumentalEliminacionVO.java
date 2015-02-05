/**
 * 
 */
package valoracion.vos;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IUnidadDocumentalEliminacionVO {

	/**
	 * @return Returns the codfondo.
	 */
	public abstract String getTitulo();

	/**
	 * @param codfondo
	 *            The codfondo to set.
	 */
	public abstract void setTitulo(String codfondo);

	/**
	 * @return Returns the expedienteudoc.
	 */
	public abstract String getExpedienteudoc();

	/**
	 * @param expedienteudoc
	 *            The expedienteudoc to set.
	 */
	public abstract void setExpedienteudoc(String expedienteudoc);

	/**
	 * @return Returns the idudoc.
	 */
	public abstract String getIdudoc();

	/**
	 * @param idudoc
	 *            The idudoc to set.
	 */
	public abstract void setIdudoc(String idudoc);

	/**
	 * @return Returns the codigo.
	 */
	public abstract String getCodigo();

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public abstract void setCodigo(String codigo);

	/**
	 * @return Returns the signaturaudoc.
	 */
	public abstract String getSignaturaudoc();

	/**
	 * @param signaturaudoc
	 *            The signaturaudoc to set.
	 */
	public abstract void setSignaturaudoc(String signaturaudoc);

	/**
	 * @return Returns the idFondo.
	 */
	public abstract String getIdfondo();

	/**
	 * @param idFondo
	 *            The idFondo to set.
	 */
	public abstract void setIdfondo(String idFondo);

	public abstract int getTiposolicitud();

	public abstract void setTiposolicitud(int tipo);

	public abstract void setCodreferencia(String codReferencia);

	public abstract String getIduinstalacion();

	public abstract void setIduinstalacion(String iduinstalacion);

	public abstract String getUbicacion();

	public abstract void setUbicacion(String ubicacion);

	public abstract String getNumero();

	public abstract void setNumero(String numero);

	public abstract String getTipoDocumental();

	public abstract void setTipoDocumental(String tipoDocumental);

	public abstract String getCodsistproductor();

	public abstract void setCodsistproductor(String codsistproductor);

	public abstract Date getFechaFinUdoc();

	public abstract String getStrFechaFinUdoc();

	public abstract void setFechaFinUdoc(Date fechaFinUdoc);

	public abstract Date getFechaIniUdoc();

	public abstract String getStrFechaIniUdoc();

	public abstract void setFechaIniUdoc(Date fechaIniUdoc);

	public abstract boolean isCajaCompleta();

	public abstract boolean isCajaParcial();

	public abstract void setCajaParcial();

	public abstract void setCajaCompleta();

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyId#getId()
	 */
	public abstract String getId();

	public abstract void setTipoCaja(String tipoCaja);

	public abstract String getTipoCaja();

	public abstract void setIdEliminacion(String idEliminacion);

	public abstract String getIdEliminacion();

}