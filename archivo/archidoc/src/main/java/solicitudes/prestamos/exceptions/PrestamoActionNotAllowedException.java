package solicitudes.prestamos.exceptions;

import java.util.Collection;

import common.exceptions.ActionNotAllowedException;

/**
 * Exception lanzada si se produce un intento de realización de una operación no
 * permitida en el módulo de préstamos por parte de un usuario de la aplicación.
 */
public class PrestamoActionNotAllowedException extends
		ActionNotAllowedException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** MOTIVOS */
	public final static String XESTADO = "Acción no permitida por estado";

	public final static String XDETALLADA_SIN_DETALLE = "Acción no permitida por no tener detalle";

	public final static String XDETALLADA_DETALLE_NO_DISPONIBLE = "Acción no permitida por no tener detalle disponible";

	public final static String XDETALLADA_SIN_ESTADO = "Acción no permitida por no tener estado";

	public final static String XFECHA_RESERVA_NO_VALIDA = "Acción no permitida por fecha de reserva no válida";

	public final static String XPRORROGA_YA_TRATADA = "Acción no permitida por prórroga ya tratada";

	public final static String XOTRA_ABIERTA = "Acción no permitida por estar otro préstamo abierto";

	public final static String XARCHIVO = "Acción no permitida por los archivos de custodia del usuario";

	public final static String XUSUARIO = "Acción no permitida por usuario";

	public final static String XFECHA = "Acción no permitida por fecha";

	public final static String XPRORROGA_SOLICITADA = "Acción no permitida por prórroga solicitada";

	/** Motivo de que la operación no se permitida */
	private String motivo = null;

	/** Prestamos asociados que impiden la realizacion de la accion */
	private Collection prestamos = null;

	public PrestamoActionNotAllowedException(int codError, String motivo) {
		super(codError);

		this.motivo = motivo;
	}

	/**
	 * Devuelve el motido de que la operación no sea permitida
	 * 
	 * @return Motivo de que la operacion no sea permitida {@see
	 *         PrestamoOperacionNoPermitidaException}
	 */
	public String getMotivo() {
		return this.motivo;
	}

	public Collection getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(Collection prestamos) {
		this.prestamos = prestamos;
	}

}
