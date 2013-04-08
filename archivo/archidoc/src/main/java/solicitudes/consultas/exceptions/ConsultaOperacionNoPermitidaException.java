package solicitudes.consultas.exceptions;

import java.util.Collection;

/**
 * Clase que encapsula los datos de lós errores sobre las operaciones no
 * permitidas sobre consultas.
 */
public class ConsultaOperacionNoPermitidaException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public final static int XESTADO = 1;

	public final static int XDETALLADA_SIN_DETALLE = 2;

	public final static int XFONDO_NO_MODIFCABLE_X_TENER_DETALLE_ASOCIADO = 3;

	public final static int XEXISTE_MAS_UNA_ORDINARIA = 4;

	public final static int XDETALLADA_SIN_ESTADO = 5;

	public final static int XELIMINACION = 6;

	public final static int XFECHA_RESERVA_NO_VALIDA = 7;

	private int motivo = 0;

	private Collection consultasVO = null;

	public ConsultaOperacionNoPermitidaException(int motivo) {
		super();
		this.motivo = motivo;
	}

	public int getMotivo() {
		return this.motivo;
	}

	public Collection getConsultasVO() {
		return consultasVO;
	}

	public void setConsultasVO(Collection consultasVO) {
		this.consultasVO = consultasVO;
	}

}
